<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>jvm</title>
</head>
<body>
<%long start = System.currentTimeMillis();%>
<h1>Java Runtime Info</h1>
<%@include file="runtime.jsp"%><hr>
<h1>JVM OS Info</h1>
<%@include file="OS.jsp"%><hr>
<h1>JVM Memory Info</h1>
<%@include file="memory.jsp"%><hr>
<h1>JVM Thread Info</h1>
<%@include file="thread.jsp"%><hr>
<h1> Execute Cost Time <%=System.currentTimeMillis()-start%>ms </h1>
</body>
</html>