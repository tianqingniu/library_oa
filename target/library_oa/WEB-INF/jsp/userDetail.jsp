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
		<h1>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;你好，${msAdmin.adminName } 职工</h1><br>
	</c:if>
	<c:if test="${msAdmin.identityId==1 }">	<!-- 读者身份 -->
		<h1>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;你好，${msAdmin.adminName } 同学</h1><br>
	</c:if>
	<div class="layui-row layui-col-space0">
		<c:if test="${msAdmin.identityId==0 }">	<!-- 管理员身份 -->
			<label class="layui-form-label">工号</label>
			<div class="layui-col-xs2" style="width: 250px;">
				<input type="text" name="title" lay-verify="required"
					autocomplete="off" value="160330140" class="layui-input" disabled>
			</div>
		</c:if>
		<c:if test="${msAdmin.identityId==1 }">	<!-- 读者身份 -->
			<label class="layui-form-label">学号</label>
			<div class="layui-col-xs2" style="width: 250px;">
				<input type="text" name="title" lay-verify="required"
					autocomplete="off" value="10330140" class="layui-input" disabled>
			</div>
		</c:if>
		<label class="layui-form-label">姓名</label>
		<div class="layui-col-xs2" style="width: 250px;">
			<input type="text" name="isbn" lay-verify="required"
				autocomplete="off" value="${msAdmin.adminName }" class="layui-input" disabled>
		</div>
	</div>
	<br>
	<div class="layui-row layui-col-space0">
		<label class="layui-form-label">系所</label>
		<div class="layui-col-xs2" style="width: 250px;">
			<input type="text" name="title" lay-verify="required"
				autocomplete="off" value="计算机院" class="layui-input" disabled>
		</div>
		<label class="layui-form-label">性别</label>
		<div class="layui-col-xs2" style="width: 250px;">
			<input type="text" name="isbn" lay-verify="required"
				autocomplete="off" value="男" class="layui-input" disabled>
		</div>
	</div>
	<br>
	<c:if test="${msAdmin.identityId==1 }">	<!-- 读者身份 -->
	<div class="layui-row layui-col-space0">
		<label class="layui-form-label">专业</label>
		<div class="layui-col-xs2" style="width: 250px;">
			<input type="text" name="title" lay-verify="required"
				autocomplete="off" value="计算机科学与技术" class="layui-input" disabled>
		</div>
		<label class="layui-form-label">年级</label>
		<div class="layui-col-xs2" style="width: 250px;">
			<input type="text" name="isbn" lay-verify="required"
				autocomplete="off" value="2016级" class="layui-input" disabled>
		</div>
	</div>
	</c:if>
	<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<%--<a class="layui-btn" style="background-color: cadetblue" href="updateUserDetail?id=${msAdmin.adminName }">修改信息</a>--%>
	
	<script src="${baseUri}/static/layui/layui.js"></script>
</body>
</html>