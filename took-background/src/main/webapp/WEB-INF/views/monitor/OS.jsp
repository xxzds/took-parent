<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.lang.management.ManagementFactory"%>
<%@ page import="java.lang.management.OperatingSystemMXBean" %>
<%
OperatingSystemMXBean os = ManagementFactory.getOperatingSystemMXBean();
%>
<b>OS Name: </b><%=os.getName()%><br>
<b>OS Versin: </b><%=os.getVersion()%><br>
<b>OS Available Processors: </b><%=os.getAvailableProcessors()%><br>
<b>OS Architecture: </b><%=os.getArch()%><br>
