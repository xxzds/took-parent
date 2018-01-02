<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.lang.management.ManagementFactory"%><%
ManagementFactory.getMemoryMXBean().gc();
%>
<b>Init Memory: </b><%=ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getInit()/1000000%>M<br>
<b>MAX Memory: </b><%=ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getMax()/1000000%>M<br>
<b>Used Memory: </b><%=ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed()/1000000%>M<br>

