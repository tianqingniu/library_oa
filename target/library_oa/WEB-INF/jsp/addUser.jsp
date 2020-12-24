﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="baseUri" value="${pageContext.request.contextPath }"
	scope="request"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>图书管理系统</title>
<link rel="stylesheet" href="${baseUri}/static/layui/css/layui.css">
</head>
<script type="text/javascript">
	function showPreview(source) {
		var file = source.files[0];
		if (window.FileReader) {
			var fr = new FileReader();
			console.log(fr);
			var portrait = document.getElementById('portrait');
			fr.onloadend = function(e) {
				portrait.src = e.target.result;
			};
			fr.readAsDataURL(file);
			portrait.style.display = 'block';
		}
	}
</script>
<body>
	<!-- 内容主体区域 -->
	<fieldset class="layui-elem-field layui-field-title">
		<legend>添加用户</legend>
	</fieldset>

	<form class="layui-form" action="addUser" method="POST"
		enctype="multipart/form-data">

		<div class="layui-form-item">
			<label class="layui-form-label">用户账号</label>
			<div class="layui-input-block" style="width: 400px;">
				<input type="text" name="adminNumber" lay-verify="required"
					autocomplete="off" placeholder="请输入账号" class="layui-input">
			</div>
		</div>

		<div class="layui-form-item">
			<label class="layui-form-label">姓名</label>
			<div class="layui-input-block" style="width: 400px;">
				<input type="text" name="adminName" lay-verify="required"
					autocomplete="off" placeholder="请输入姓名" class="layui-input">
			</div>
		</div>

		<div class="layui-form-item">
			<label class="layui-form-label">密码</label>
			<div class="layui-input-inline" style="width: 400px;">
				<input type="password" name="adminPwd" placeholder="" autocomplete="off"
					class="layui-input">
			</div>
		</div>

		<div class="layui-form-item">
	    	<label for="L_sex" class="layui-form-label">性别</label>
	        <div class="layui-input-inline">
	        	<input type="radio" title="男" name="sex" checked="checked">
	        	<input type="radio" title="女" name="sex">
	        </div>
	    </div>
		
		<div class="layui-form-item">
			<label class="layui-form-label">身份</label>
			<div class="layui-input-inline" style="width: 400px;">
				<select name="identityId">
					<option value="1">读者</option>
					<option value="0">管理员</option>
				</select>
			</div>
		</div>
		
		<div class="layui-form-item">
			<div class="layui-input-block">
				<button type="submit" class="layui-btn" lay-filter="demo1">立即提交</button>
				<button type="reset" class="layui-btn layui-btn-primary">重置</button>
			</div>
		</div>
	</form>

	<script src="${baseUri}/static/layui/layui.js"></script>
	<script>
		//JavaScript代码区域
		layui.use('element', function() {
			var element = layui.element;

		});
		layui.use([ 'form' ], function() {
			var form = layui.form;
		});
		layui.use('carousel', function() {
			var carousel = layui.carousel;
			//建造实例
			carousel.render({
				elem : '#test1',
				width : '100%' //设置容器宽度
				,
				arrow : 'always' //始终显示箭头
			//,anim: 'updown' //切换动画方式
			});
		});
	</script>
</body>
</html>