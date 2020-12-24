<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="layui-header">
	<div class="layui-logo">图书管理系统</div>
	<!-- 头部区域（可配合layui已有的水平导航） -->
	
	<!-- <ul class="layui-nav layui-layout-left">
		<li class="layui-nav-item"><a href="javascript:;">其它系统</a>
			<dl class="layui-nav-child">
				<dd>
					<a href="###">邮件管理</a>
				</dd>
			</dl></li>
	</ul> -->
	
	<ul class="layui-nav layui-layout-right">
		<li class="layui-nav-item">
			<a href="javascript:;">
				<img src="${baseUri}/static/images/head.jpg" class="layui-nav-img"> ${msAdmin.adminName }
			</a>
			<dl class="layui-nav-child">
				<dd>
					<a href="showUserDetail" target="iframe">个人信息</a>
				</dd>
				<dd>
					<a href="toUpdatePwd" target="iframe">修改密码</a>
				</dd>
			</dl></li>
		<li class="layui-nav-item"><a href="adminLogout" onclick="return confirm('确认退出管理员${msAdmin.adminName }？')">安全退出</a></li>
	</ul>
</div>