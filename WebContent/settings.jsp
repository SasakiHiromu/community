<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ユーザー編集</title>
</head>
<body>
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

<form action="settings" method="post"><br />
	<label for="login_id">ID</label>
	<input name="login_id" value="${editUser.login_id}"/> <br />

	<label for="password">パスワード</label>
	<input name="password" type="password" /> <br />

	<label for="name">名前</label>
	<input name="name" value="${editUser.name}"/> <br />

	<label for="branch_id">支店名</label>
	<c:forEach items="${allbranches}" var="allbranche">
		<c:if test="${editUser.id == allbranche.id}">
	<input name="branch_id" value="${allbranche.name}"/> <br />
		</c:if>
	</c:forEach>

	<label for="job_id">役職</label>
	<c:forEach items="${alljobs}" var="alljob">
		<c:if test="${editUser.id == alljob.id}">
	<input name="branch_id" value="${alljob.name}"/> <br />
		</c:if>
	</c:forEach>

	<button type="submit"  name="id" value="${editUser.id}">登録</button><br />
	<a href="status">ユーザー管理画面に戻る</a>
</form>
</div>
</body>
</html>
