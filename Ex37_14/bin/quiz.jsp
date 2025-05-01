<%@ page import="java.util.Random" %>
<%
    Random rand = new Random();
    int[][] questions = new int[10][2];

    for (int i = 0; i < 10; i++) {
        questions[i][0] = rand.nextInt(50); // first operand
        questions[i][1] = rand.nextInt(50); // second operand
    }

    session.setAttribute("questions", questions);
%>
<html>
<head><title>Addition Quiz</title></head>
<body>
    <h2>Addition Quiz</h2>
    <form action="result.jsp" method="post">
        <%
            for (int i = 0; i < 10; i++) {
        %>
            <%= questions[i][0] %> + <%= questions[i][1] %> = 
            <input type="text" name="answer<%=i%>"><br>
        <%
            }
        %>
        <br><input type="submit" value="Submit Answers">
    </form>
</body>
</html>
