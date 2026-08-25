<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tomcat Practice App</title>
</head>
<body>
    <h1>Tomcat + Maven + Git Practice App</h1>
    <p>If you can see this page, your WAR built with Maven deployed successfully to Tomcat.</p>

    <p><strong>Build version:</strong> <%= application.getInitParameter("app.version") != null ? application.getInitParameter("app.version") : "not set" %></p>
    <p><strong>Server info:</strong> <%= application.getServerInfo() %></p>

    <p><a href="count">Visit the counter page</a></p>
</body>
</html>
