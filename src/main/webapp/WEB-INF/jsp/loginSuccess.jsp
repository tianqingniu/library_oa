<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="baseUri" value="${pageContext.request.contextPath }"
	scope="request"></c:set>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>图书管理系统</title>
<link rel="stylesheet" href="${baseUri}/static/layui/css/layui.css">
</head>
<body>
	<!-- 内容主体区域 -->
	<fieldset class="layui-elem-field layui-field-title">
		<legend>用户信息</legend>
	</fieldset>
	<c:if test="${msAdmin.identityId==0 }">	<!-- 管理员身份 -->
		<h1>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;你好，管理员 ${msAdmin.adminName } </h1><br>
	</c:if>
	<c:if test="${msAdmin.identityId==1 }">	<!-- 读者身份 -->
		<h1>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;你好，同学 ${msAdmin.adminName } </h1><br>
	</c:if>
	<script src="${baseUri}/static/layui/layui.js"></script>
</body>
</html>