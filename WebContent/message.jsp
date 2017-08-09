<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>新規投稿</title>
</head>
<body>
<h3>新規投稿入力ページ</h3>
<div class="main-contents">
<c:if test="${ not empty errorMessages }">
	<div class="errorMessages">
		<ul>
			<c:forEach items="${errorMessages}" var="message">
				<li><c:out value="${message}" />
			</c:forEach>
		</ul>
	</div>
	<c:remove var="errorMessages" scope="session"/>
</c:if>
<form action="newMessage" method="post"><br />
	<label for="title">件名</label>
	<input name="title"/> <br />

	<label for="category">カテゴリー</label>
	<input name="category"/> <br />

	<label for="text">本文</label>
	<input name="text"/> <br />

	<input type="submit" value="登録" /> <br />
	<a href="./">INFORMATIONへ戻る</a>
</form>
</div>
</body>
</html>
