<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="css/loginstyle.css" rel="stylesheet" type="text/css">
	<title>ログイン</title>
</head>
<body>
<div id="form">
<p class="form-title">○○社INFORMATION　ログインページ</p>
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
<form action="login" method="post" ><br />

	<label for="loginId">ログインID</label>
	 <p class="id"><input name="loginId"  id="loginId" value="${loginId}"/></p><br />

	<label for="password">パスワード</label>
	<p class="pass"><input name="password" type="password" id="password"/></p><br />

	<p class="submit"><input type="submit" value="ログイン" /></p><br />

</form>
</div>
</body>
</html>
