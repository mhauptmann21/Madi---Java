import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoanServlet extends HttpServlet{
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        double loanAmount = Double.parseDouble(request.getParameter("loanAmount"));
        double annualInterestRate = Double.parseDouble(request.getParameter("annualInterestRate"));
        int numberOfYears = Integer.parseInt(request.getParameter("numberOfYears"));

        Loan loan = new Loan(annualInterestRate, numberOfYears, loanAmount);
        double monthlyPayment = loan.getMonthlyPayment();
        double totalPayment = loan.getTotalPayment();

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>Loan Payment Result</title></head><body>");
        out.println("<h2>Loan Amount: " + loanAmount + "</h2>");
        out.println("<h2>Annual Interest Rate: " + annualInterestRate + "</h2>");
        out.println("<h2>Number of Years: " + numberOfYears + "</h2>");
        out.println("<h2>Monthly Payment: " + monthlyPayment + "</h2>");
        out.println("<h2>Total Payment: " + totalPayment + "</h2>");
        out.println("</body></html>");
    }
}
