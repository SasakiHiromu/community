<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="css/login.css" rel="stylesheet" type="text/css">
	<title>ログイン</title>
</head>
<body>
<div id="form">
<h3 class="topTitle">CHOKO CHOKO (株) ログインページ</h3>
<c:if test="${ not empty errorMessages }">
	<div class="errorMessages">
		<ul>
			<c:forEach items="${errorMessages}" var="message">
				<c:out value="${message}" />
			</c:forEach>
		</ul>
	</div>
	<c:remove var="errorMessages" scope="session"/>
</c:if>
<div class="box">
<form  class="sign-up" action="login" method="post" ><br />
<div class="list">
	<label for="loginId">ログインID</label>
	 <input class="sign-up-input" name="loginId"  id="loginId" value="${loginId}" placeholder="What's your LoginID?" autofocus/><br />

	<label for="password">パスワード</label>
	<input class="sign-up-input"  name="password" type="password" id="password" placeholder="Choose a Password"/><br />
</div>
	<p class="submit"><input class="sign-up-button" type="submit" value="ログイン" /></p><br />

</form>
</div>
</div>
</body>
</html>
