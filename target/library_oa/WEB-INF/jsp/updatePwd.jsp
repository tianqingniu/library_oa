<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="baseUri" value="${pageContext.request.contextPath }"
	scope="request"></c:set>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>修改密码</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="${baseUri}/static/layui/css/layui.css">
	<script src="${baseUri}/static/layui/layui.js"></script>
	
  </head>
  
  <body>
  	<fieldset class="layui-elem-field layui-field-title">
		<legend>修改密码</legend>
	</fieldset>
	<!-- 修改密码表单 -->
	<div class="layui-form-item">
		<form action="updatePwd" method="post">
			<div class="layui-form-item">
				<label for="L_nowpass" class="layui-form-label">当前密码</label>
				<div class="layui-input-inline">
					<input type="password" required lay-verify="required" autocomplete="off" class="layui-input" name="oldPwd">
				</div>
			</div>
			<div class="layui-form-item">
				<label for="L_pass" class="layui-form-label">新密码</label>
				<div class="layui-input-inline">
					<input type="password" required lay-verify="required" autocomplete="off" class="layui-input" name="newPwd">
				</div>
				<div class="layui-form-mid layui-word-aux">6到16个字符</div>
			</div>
			<div class="layui-form-item">
				<label for="L_repass" class="layui-form-label">确认密码</label>
				<div class="layui-input-inline">
					<input type="password" required lay-verify="required" autocomplete="off" class="layui-input" name="confirmPwd">
				</div>
			</div>
			<div class="layui-form-item">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input class="layui-btn" value="确认修改" type="submit">
			</div>
			<!-- 错误提示 -->
			<div class="checkbox mg-b25">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<label>
					<c:if test="${UPDATE_PWD_MSG == null }">
						提示：<span class="login_msg" style="color: black">修改密码成功后，需要重新登录。</span>
					</c:if>
					<c:if test="${UPDATE_PWD_MSG != null }">
						提示：<span class="login_msg" style="color: red">${UPDATE_PWD_MSG }</span>
					</c:if>
				</label>
			</div>
		</form>
	</div>
	
  </body>
</html>
