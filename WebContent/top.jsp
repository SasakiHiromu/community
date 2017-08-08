<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored= "false"%>
<%@page import="java.util.*" %>
<%@page import="java.text.*" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>わったいな掲示板</title>
</head>
<body>
<h3>わったいな掲示板</h3>
<c:out value="${loginUser.name}でログイン中です"></c:out>
<div class="main-contents">

<a href="newMessage">新規投稿</a>

<a href="status">ユーザー管理</a>

<a href="logout">ログアウト</a>

<div class="header"></div>

</div>
</body>
<div class="messages">
<form action="./"><br />
<input name="startDate">
<input name="endDate">
<input type="submit" value="絞り込む">
</form>
<c:forEach items="${messages}" var="message">
	<div class="message">
		<div class="account-name">
			名前：<span class="name"><c:out value="${message.name}" /></span><br />
			タイトル：<span class="title"><c:out value="${message.title}" /></span><br />
			件名:<span class="category"><c:out value="${message.category}" /></span><br />
			<p></p>
		</div>
	本文<div class="text"><c:out value="${message.text}" /></div>
	投稿日時<div class="date"><fmt:formatDate value="${message.createdAt}"
	pattern="yyyy/MM/dd HH:mm:ss" /></div>
	<form action="Delete" method="post"><br />
		<button type="submit" name="message_id" value="${message.id}">削除</button>
	</form>
	</div>

		<c:forEach items="${comments}" var="comment">
			<c:if test="${message.id == comment.messageId}" >
				<span class="text"><c:out value="${comment.text}" /></span><br />
				<c:forEach items="${allbranches}" var="allbranche">
					<c:if test="${comment.branchId == allbranche.id}">
						<span class="branch_id"><c:out value="${allbranche.name}" /></span><br />
					</c:if>
				</c:forEach>
				<c:forEach items="${alljobs}" var="alljob">
					<c:if test="${comment.jobId == alljob.id}">
						<span class="job_id"><c:out value="${alljob.name}" /></span>
					</c:if>
				</c:forEach>
			</c:if>
			<form action="Delete" method="post"><br />
				<button type="submit" name="comment_id" value="${comment.id}">削除</button>
			</form>
		</c:forEach>


		<form action="newComment" method="post"><br />
			<textarea name="text" rows="4" cols="40"></textarea><br />
			<input type="hidden" name="message_id" value="${message.id}" />
			<button type="submit"  name="loginUser" value="${loginUser.id}">コメント</button>
			<p></p>
		</form>


</c:forEach>
</div>
</html>