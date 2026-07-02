import html
import re
from http.server import BaseHTTPRequestHandler, HTTPServer
from pathlib import Path
from urllib.parse import parse_qs, urlparse
from urllib.request import Request, urlopen

try:
    from exa_py import Exa
except ImportError:
    Exa = None

BASE_DIR = Path(__file__).resolve().parent
FRONTEND_DIR = BASE_DIR / "frontend"
INDEX_TEMPLATE_PATH = FRONTEND_DIR / "index.html"


def get_url_preview(url: str) -> str:
    parsed = urlparse(url)
    if not parsed.scheme or not parsed.netloc:
        return "Video result"

    try:
        request = Request(url, headers={"User-Agent": "Mozilla/5.0"})
        with urlopen(request, timeout=4) as response:
            html_text = response.read().decode("utf-8", errors="ignore")
    except Exception:
        domain = parsed.netloc.replace("www.", "")
        if "/video/" in url:
            return f"Video preview from {domain}"
        return f"Preview from {domain}"

    for pattern in [
        r'<meta[^>]+property=["\']og:description["\'][^>]+content=["\']([^"\']+)["\']',
        r'<meta[^>]+name=["\']description["\'][^>]+content=["\']([^"\']+)["\']',
        r'<meta[^>]+content=["\']([^"\']+)["\'][^>]+name=["\']description["\']',
    ]:
        match = re.search(pattern, html_text, re.IGNORECASE | re.DOTALL)
        if match:
            preview = re.sub(r"\s+", " ", html.unescape(match.group(1))).strip()
            return preview[:220]

    title_match = re.search(r"<title[^>]*>(.*?)</title>", html_text, re.IGNORECASE | re.DOTALL)
    if title_match:
        title = re.sub(r"\s+", " ", html.unescape(title_match.group(1))).strip()
        return title[:220]

    domain = parsed.netloc.replace("www.", "")
    return f"Video preview from {domain}"


def run_search(query: str):
    if Exa is None:
        return [
            {
                "title": "Python backend is connected",
                "url": "/",
                "snippet": f"Received query: {query}",
            }
        ]

    exa = Exa("e92a8670-2a71-4607-9504-39b0b4e2f775")
    response = exa.search(
        f"site:tiktok.com {query}",
        num_results=6,
        type="keyword",
    )


    return [
        {
            "title": result.title,
            "url": result.url,
            "snippet": (getattr(result, "snippet", "") or "").strip() or get_url_preview(result.url),
        }
        for result in response.results
    ]


def build_results_html(results):
    if not results:
        return "<li>No results yet.</li>"

    items = []
    for item in results:
        url = item.get("url", "")
        title = item.get("title", "")
        snippet = item.get("snippet", "")
        embed_html = ""

        video_id = ""
        match = re.search(r"/video/(\d+)", url)
        if match:
            video_id = match.group(1)

        if video_id:
            embed_html = f"""
            <div class="results__video">
                <blockquote class="tiktok-embed"
                    cite="{html.escape(url)}"
                    data-video-id="{video_id}">
                    <section></section>
                </blockquote>
            </div>
            """
            
        title_markup = f"<a class=\"results__title\" href=\"{html.escape(url)}\">{html.escape(title)}</a>" if title else ""
        snippet_markup = f"<p class=\"results__snippet\">{html.escape(snippet)}</p>" if snippet else ""

        items.append(
            f"<li class=\"results__card\">{title_markup}{embed_html}{snippet_markup}</li>"
        )

    return "".join(items)


def render_index_page(query: str = "", results=None):
    results = results if results is not None else []
    template = INDEX_TEMPLATE_PATH.read_text(encoding="utf-8")

    results_markup = ""
    if query:
        results_markup = f"""
        <section class=\"results\" aria-live=\"polite\">
          <h3>Results</h3>
          <ul class=\"results__list\">{build_results_html(results)}</ul>
        </section>
        """

    rendered_page = template.replace("{{query_value}}", html.escape(query))
    rendered_page = rendered_page.replace("{{results}}", results_markup)
    return rendered_page


class SearchHandler(BaseHTTPRequestHandler):
    def do_GET(self):
        parsed_path = urlparse(self.path)
        if parsed_path.path == "/":
            self.send_html(render_index_page())
            return

        requested_file = parsed_path.path.lstrip("/")
        if requested_file:
            self.serve_file(requested_file)
            return

        self.send_error(404, "File not found")

    def do_POST(self):
        parsed_path = urlparse(self.path)
        if parsed_path.path not in {"/", "/search"}:
            self.send_error(404, "Route not found")
            return

        length = int(self.headers.get("Content-Length", "0"))
        body = self.rfile.read(length).decode("utf-8")
        data = parse_qs(body, keep_blank_values=True)
        query = data.get("query", [""])[0].strip()
        results = run_search(query) if query else []
        self.send_html(render_index_page(query=query, results=results))

    def send_html(self, page: str):
        self.send_response(200)
        self.send_header("Content-Type", "text/html; charset=utf-8")
        self.end_headers()
        self.wfile.write(page.encode("utf-8"))

    def serve_file(self, filename: str):
        file_path = FRONTEND_DIR / filename
        if not file_path.exists() or not file_path.is_file():
            self.send_error(404, "File not found")
            return

        content = file_path.read_bytes()
        self.send_response(200)

        if filename.endswith(".css"):
            content_type = "text/css; charset=utf-8"
        elif filename.endswith(".html"):
            content_type = "text/html; charset=utf-8"
        elif filename.endswith(".png"):
            content_type = "image/png"
        else:
            content_type = "application/octet-stream"

        self.send_header("Content-Type", content_type)
        self.end_headers()
        self.wfile.write(content)


def main():
    server = HTTPServer(("0.0.0.0", 8000), SearchHandler)
    print("Server running at http://127.0.0.1:8000")
    server.serve_forever()


if __name__ == "__main__":
    main()