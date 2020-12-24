<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.io.*,java.util.*, javax.servlet.*" %>

<c:set var="baseUri" value="${pageContext.request.contextPath }"
	scope="request"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>图书管理系统</title>
<link rel="stylesheet" href="${baseUri}/static/layui/css/layui.css">
</head>
<!-- <style>
.pageshow {
	background-color: orage;
	float: right;
	line-height: 50px;
	margin-right: 100px;
}
</style> -->
<body class="layui-anim layui-anim-scaleSpring">
	<!-- 内容主体区域 -->
	<fieldset class="layui-elem-field layui-field-title"
		style="margin-top: 20px;">
		<legend>图书借阅信息</legend>
	</fieldset>
	
	<p><c:if test="${readerName != null }">
		用户：${readerName }&nbsp;&nbsp;&nbsp; 
		</c:if>
		借阅图书数量：&nbsp;&nbsp;&nbsp; 
		超期罚款总额：${sumPenalty }</p>
		
	<div class="layui-form">
		<table class="layui-table">
			<colgroup>
				<col width="150">
				<col width="150">
				<col width="150">
				<col width="80">
				<col width="80">
				<col width="350">
				<col width="130">
				<col width="50">
				<col width="50">
				<col width="50">
				<col>
			</colgroup>
			<thead>
				<tr>
					<th>ISBN</th>
					<th>书名</th>
					<th>作者</th>
					<th>类型</th>
					<th>价格(元)</th>
					<th>简介</th>
					<th>图片</th>
					<th>数量(剩/总)</th>
					<th>时间(借出/归还)</th>
					<th>图书操作</th>
					<th>借阅操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${requestScope.pageMsg.lists}" var="book" varStatus="loop">
					<tr>
						<td>${book.isbn }</td>
						<td>${book.title }</td>
						<td>${book.author }</td>
						<td><c:if test="${book.categoryId==1 }">小说</c:if> <c:if
								test="${book.categoryId==2 }">文学</c:if> <c:if
								test="${book.categoryId==3 }">动漫</c:if> <c:if
								test="${book.categoryId==4 }">文化</c:if> <c:if
								test="${book.categoryId==5 }">传记</c:if> <c:if
								test="${book.categoryId==6 }">艺术</c:if> <c:if
								test="${book.categoryId==7 }">童书</c:if> <c:if
								test="${book.categoryId==8 }">古籍</c:if> <c:if
								test="${book.categoryId==9 }">历史</c:if></td>
						<td>${book.price }</td>
						<td>${book.introduction }</td>
						<td>
						<c:if test="${book.image==null}">
								<img src="${baseUri}/static/images/book.jpg"  width="80px" height="100px;">
						</c:if> 
						<c:if test="${book.image !=null }">
							<c:if test="${book.image=='' }">
								<img src="${baseUri}/static/images/book.jpg" width="80px" height="100px;">
							</c:if>
							<c:if test="${book.image !='' }">
								<img src="${imgPath}/${book.image}" alt="" width="80px" height="100px;">
							</c:if>
						</c:if>
						</td>
						<td>${book.remainder }/${book.sum }</td>
						<!-- 同时遍历两个List的方法 ${键名[loop.count-1].属性名}  注：forEach里加varStatus="loop"-->
						<td><fmt:formatDate value="${borrowList[loop.count-1].borrowTime }" pattern="yyyy-MM-dd HH:mm"/>
							 / <fmt:formatDate value="${borrowList[loop.count-1].borrowReturnTime }" pattern="yyyy-MM-dd HH:mm"/> 
							<c:if test="${borrowList[loop.count-1].borrowReturnTime < date }">	<!-- 超期提示 -->
								<ul class="layui-nav" style="background-color: rgb(211, 26, 81); width: 300">
									<li class="layui-nav-item">
										<a class="layui-btn" id="penalty" 
										style="background-color: rgb(211, 26, 81)" >已超期，需缴费${borrowList[loop.count-1].borrowPenalty }元</a>
										<dl class="layui-nav-child">
											<dd>
												<a href="toPayAndReturn?id=${book.id }&readerId=${readerId }&from=borrowBook" 
												onclick="return confirm('读者已付款？清除记录？')">缴费，并且还书</a>
											</dd>
											<dd>
												<a href="toPayNoReturn?id=${book.id }&readerId=${readerId }&from=borrowBook" 
												onclick="return confirm('读者已付款？清除记录？')">缴费，但未还书</a>
											</dd>
										</dl>
									</li>
								</ul>
							</c:if>
						</td>
						<td><a class="layui-btn"
							style="background-color: cadetblue"
							href="bookDetail?id=${book.id }">查看</a>
							<c:if test="${msAdmin.identityId==0 }">	<!-- 管理员身份 -->
								<br><br>
								<a class="layui-btn"
									style="background-color: rgb(204, 139, 41)" href="toUpdateBook?id=${book.id }">编辑</a>
								<br><br>
								<a class="layui-btn" id="soldBook"
									style="background-color: rgb(211, 26, 81)"
									href="deleteBook?id=${book.id }">下架</a>
							</c:if>
						</td>
						<td>
						<c:if test="${borrowList[loop.count-1].borrowPenalty > 0 }">	<!-- 欠费后，不可进行借阅操作 -->
							<p>缴费后，方可进行借阅操作</p>
						</c:if>
						<c:if test="${(borrowList[loop.count-1].borrowBooking == null) && (borrowList[loop.count-1].borrowPenalty == 0) }">	<!-- 借阅中并且不欠费 -->
							<a class="layui-btn"
								style="background-color: rgb(204, 139, 41)" 
								href="toReturnBook?id=${book.id }&readerId=${readerId }&from=borrowBook">归还</a>
								<br><br>
							<c:if test="${isPenalty == null }">	<!-- 没有逾期图书 -->
								<a class="layui-btn"
									style="background-color: rgb(204, 139, 41)" 
									href="toRenewBook?id=${book.id }&readerId=${readerId }&from=borrowBook">续借</a>
							</c:if>
							<c:if test="${isPenalty != null }">	<!-- 有逾期图书 -->
								<a class="layui-btn layui-btn-disabled"
									style="background-color: rgb(204, 139, 41)"
									href="javascript:void(0)">续借</a>
							</c:if>
						</c:if>
						<c:if test="${(borrowList[loop.count-1].borrowBooking != null) && (book.remainder == 0) }">	<!-- 预约中 -->
							<a class="layui-btn"
								style="background-color: rgb(204, 139, 41)">预约中</a>
						</c:if>
						<c:if test="${(borrowList[loop.count-1].borrowBooking != null) && (book.remainder > 0) }">	<!-- 可领取 -->
							<a class="layui-btn"
								style="background-color: rgb(204, 139, 41)"
								href="toTakeBook?id=${book.id }&readerId=${readerId }&from=borrowBook">可领取</a>
						</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<!-- 页码显示 -->
	<%-- <div class="pageshow">
		<span> 第${requestScope.pageMsg.currPage }/${requestScope.pageMsg.totalPage}页</span>
		&nbsp;&nbsp; <span>总记录数：${requestScope.pageMsg.totalCount }&nbsp;&nbsp;每页显示:${requestScope.pageMsg.pageSize}</span>
		&nbsp;&nbsp; <span> <c:if
				test="${requestScope.pageMsg.currPage != 1}">
				<a href="${baseUri}/showBook?currentPage=1">[首页]</a>&nbsp;&nbsp;
         				<a
					href="${baseUri}/showBook?currentPage=${requestScope.pageMsg.currPage-1}">[上一页]</a>
         				&nbsp;&nbsp;
     				</c:if> <c:if
				test="${requestScope.pageMsg.currPage != requestScope.pageMsg.totalPage}">
				<a
					href="${baseUri}/showBook?currentPage=${requestScope.pageMsg.currPage+1}">[下一页]</a>&nbsp;&nbsp;
         				<a
					href="${baseUri}/showBook?currentPage=${requestScope.pageMsg.totalPage}">[尾页]</a>&nbsp;&nbsp;
     				</c:if>
		</span>
	</div> --%>

	<script src="${baseUri}/static/layui/layui.js"></script>
	<script>
		//JavaScript代码区域
		layui.use('element', function() {
			var element = layui.element;

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
		layer.open({
			type : 4,
			content : [ '已下架', '#soldBook' ]
		//数组第二项即吸附元素选择器或者DOM
		});
	</script>
</body>
</html>