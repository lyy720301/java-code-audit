<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*" %>
<%@ page import="com.example.jspdemo.model.AnimalBean" %>
<html>
<head>
    <title>自动刷新实例</title>
</head>
<body>

<h2>自动刷新实</h2>
<%! String CT;%>


<%
    // 设置每隔5秒刷新一次
    response.setIntHeader("Refresh", 5);
    // 获取当前时间
    Calendar calendar = new GregorianCalendar();
    String am_pm;
    int hour = calendar.get(Calendar.HOUR);
    int minute = calendar.get(Calendar.MINUTE);
    int second = calendar.get(Calendar.SECOND);
    if(calendar.get(Calendar.AM_PM) == 0)
        am_pm = "AM";
    else
        am_pm = "PM";
    CT = hour+":"+ minute +":"+ second +" "+ am_pm;
    System.out.println("当前时间为: " + CT + "\n");
%>
<%= "当前时间 " + CT%>

</body>
</html>
