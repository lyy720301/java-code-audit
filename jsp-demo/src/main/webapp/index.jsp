<%@ page import="com.example.jspdemo.model.AnimalBean" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>JSP - Hello World</title>
</head>
<body>
<h1>
  <%= "Hello World!" %>
  <%! int i = 0;%>
  <%
    i++;
  %>
  <%="I: " + i%>
  <%!
    AnimalBean dog;
    public void jspInit(){

      dog = new AnimalBean(12, "dog");
      System.out.println("jspInit(): JSP init...");
    }

  %>

  <p>动物年龄:
    <%=dog.getAge()%>
  </p>
  <p>动物名字:
    <%=dog.getName()%>
  </p>
</h1>
<script>
  console.log(new Date())
</script>
<br/>
<a href="hello-servlet">Hello Servlet</a>
</body>
</html>