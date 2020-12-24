<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="baseUri" value="${pageContext.request.contextPath }"
       scope="request"></c:set>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>图书管理系统</title>
    <link rel="stylesheet" href="${baseUri}/static/layui/css/layui.css">
</head>
<style>
    <!--
    .pageshow {
        background-color: orage;
        float: right;
        line-height: 50px;
        margin-right: 100px;
    }

    -->
</style>
<body class="layui-anim layui-anim-scaleSpring">
<!-- 内容主体区域 -->
<fieldset class="layui-elem-field layui-field-title"
          style="margin-top: 20px;">
    <legend>所有图书</legend>
</fieldset>

<c:if test="${isPenalty != null }"> <!-- 逾期时不可预约和借阅图书 -->
    <p style="color: red;">提醒：当前有借阅图书已逾期，预约和借阅功能已被暂停！</p></c:if>

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
            <th>查看</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${requestScope.pageMsg.lists}" var="book">
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
                        <img src="${baseUri}/static/images/book.jpg" width="80px" height="100px;">
                    </c:if>
                    <c:if test="${book.image !=null }">
                        <c:if test="${book.image=='' }">
                            <img src="${baseUri}/static/images/book.jpg" width="80px" height="100px;">
                        </c:if>
                        <c:if test="${book.image !='' }">
                            <img src="${baseUri}/${book.image}" alt="" width="80px" height="100px;">
                        </c:if>
                    </c:if>
                </td>
                <td>${book.remainder }/${book.sum }</td>
                <td><a class="layui-btn"
                       style="background-color: cadetblue"
                       href="bookDetail?id=${book.id }">查看</a></td>
                <td>
                    <c:if test="${isPenalty == null }">
                        <c:if test="${book.remainder == 0 && book.sum > 0 }">
                            <a class="layui-btn"
                               style="background-color: rgb(204, 139, 41)"
                               href="toBook?id=${book.id }&from=showAllBook">预约</a>
                        </c:if>
                        <c:if test="${book.remainder > 0 && book.sum > 0 }">
                            <a class="layui-btn"
                               style="background-color: rgb(204, 139, 41)"
                               href="toBorrowBook?id=${book.id }&from=showAllBook">借阅</a>
                        </c:if>
                    </c:if>
                    <c:if test="${isPenalty != null }">
                        <c:if test="${book.remainder == null }">
                            <a class="layui-btn layui-btn-disabled"
                               style="background-color: rgb(204, 139, 41)"
                               href="javascript:void(0)">预约</a>
                        </c:if>
                        <c:if test="${book.remainder > 0 }">
                            <a class="layui-btn layui-btn-disabled"
                               style="background-color: rgb(204, 139, 41)"
                               href="javascript:void(0)">借阅</a>
                        </c:if>
                    </c:if>
                    <c:if test="${msAdmin.identityId==0 }"> <!-- 管理员身份 -->
                        <br><br>
                        <a class="layui-btn"
                           style="background-color: rgb(204, 139, 41)" href="toUpdateBook?id=${book.id }">编辑</a>
                        <br><br>
                        <a class="layui-btn" id="soldBook"
                           style="background-color: rgb(211, 26, 81)"
                           href="deleteBook?id=${book.id }">下架</a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<!-- 页码显示 -->
<div class="pageshow">
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
</div>

<script src="${baseUri}/static/layui/layui.js"></script>
<script>
    //JavaScript代码区域
    layui.use('element', function () {
        var element = layui.element;

    });
    layui.use('carousel', function () {
        var carousel = layui.carousel;
        //建造实例
        carousel.render({
            elem: '#test1',
            width: '100%' //设置容器宽度
            ,
            arrow: 'always' //始终显示箭头
            //,anim: 'updown' //切换动画方式
        });
    });
    layer.open({
        type: 4,
        content: ['已下架', '#soldBook']
        //数组第二项即吸附元素选择器或者DOM
    });
</script>
</body>
</html>