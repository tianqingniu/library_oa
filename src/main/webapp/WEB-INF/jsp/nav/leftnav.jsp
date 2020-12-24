<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="layui-side layui-bg-black">
	<div class="layui-side-scroll">
		<!-- 左侧导航区域（可配合layui已有的垂直导航） -->
		<ul class="layui-nav layui-nav-tree" lay-filter="test">
			<li class="layui-nav-item layui-nav-itemed">	<!-- layui-nav-itemed 展开 -->
			<a class="" href="javascript:;">管理图书</a>
				<dl class="layui-nav-child">
					<dd>
						<a href="showBook" target="iframe">所有图书</a>
					</dd>
					<dd>
						<a href="searchBook" target="iframe">查询图书</a>
					</dd>
				<c:if test="${msAdmin.identityId==0 }">	<!-- 管理员身份 -->
					<dd>
						<a href="toAddNewBook" target="iframe">上架新图书</a>
					</dd>
				</c:if>
					<dd>
						<a href="newBookList" target="iframe">最新图书列表</a>
					</dd>
				<c:if test="${msAdmin.identityId==0 }">	<!-- 管理员身份 -->
					<dd>
						<a href="deleteBookList" target="iframe">下架图书列表</a>
					</dd>
				</c:if>
					<dd>
						<a href="showBorrowBook" target="iframe">图书借阅记录</a>
					</dd>
					<!-- <dd>
						<a href="###">读者想看的图书</a>
					</dd> -->
				</dl></li>
				
			<c:if test="${msAdmin.identityId==0 }">	<!-- 管理员身份 -->
				<li class="layui-nav-item layui-nav-itemed">
					<a href="javascript:;">管理读者</a>
					<dl class="layui-nav-child">
						<dd>
							<a href="showReader" target="iframe">所有读者</a>
						</dd>
						<dd>
							<a href="searchReader" target="iframe">查询读者</a>
						</dd>
						<dd>
							<a href="toAddUser" target="iframe">添加读者</a>
						</dd>
						<dd>
							<a href="showOverdue" target="iframe">全部逾期</a>
						</dd>
					</dl>
				</li>
			</c:if>
			<!-- <li class="layui-nav-item"><a href="###">###</a></li> -->
		</ul>
	</div>
</div>