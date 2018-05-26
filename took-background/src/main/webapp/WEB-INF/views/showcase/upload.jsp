<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上传管理</title>
<script type="text/javascript" src="${ctx}/static/plugins/jquery.min.js"></script>
<script type="text/javascript">
	var ctx = '${ctx}';
</script>
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/css/upload.css">
</head>
<body>
	<div id="uploader" class="uploaderPic"></div>

	<script type="text/javascript" src="${ctx}/static/js/common-upload.js"></script>
	<script type="text/javascript">
		$(function() {
			$("#uploader").powerWebUpload({
				auto : false,
				onAllComplete : function(event) {
					alert(event);
				}
			});
		})
	</script>
</body>
</html>