<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>上传管理</title>
	<jsp:include page="../common/comm.jsp"></jsp:include>
</head>
<body>
<div id="uploader" style="margin-left:10px"></div>
<input type="button" value="1111"  onclick="GetFiles1()"/>

<script type="text/javascript" src="${ctx}/static/js/common-upload.js"></script>
<script type="text/javascript">
$(function () {
    $("#uploader").powerWebUpload({ auto: false,fileNumLimit:5 });
})
function GetFiles1() {
    var data = $("#uploader").GetFilesAddress();
    alert(data[0])
}
</script>
</body>
</html>