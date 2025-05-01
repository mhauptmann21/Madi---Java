<%@ page import="javax.servlet.http.*, java.util.*" %>
<%
    int[][] questions = (int[][]) session.getAttribute("questions");
    int correctCount = 0;
%>
<html>
<head><title>Quiz Results</title></head>
<body>
    <h2>Quiz Results</h2>
    <%
        for (int i = 0; i < 10; i++) {
            int a = questions[i][0];
            int b = questions[i][1];
            int correctAnswer = a + b;
            String userInput = request.getParameter("answer" + i);
            int userAnswer = 0;

            try {
                userAnswer = Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
                // treat non-integer as wrong
            }

            if (userAnswer == correctAnswer) {
                correctCount++;
                out.println(a + " + " + b + " = " + userAnswer + " → Correct<br>");
            } else {
                out.println(a + " + " + b + " = " + userAnswer + " → Wrong<br>");
            }
        }
    %>
    <br><b>Total correct count is <%= correctCount %></b>
</body>
</html>
