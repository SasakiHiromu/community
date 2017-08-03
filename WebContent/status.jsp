<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ユーザー管理</title>
</head>
<body>
<h3>ユーザー管理</h3>
<a href="signup">新規登録</a>

<table>
	<tr>
		<th>id</th>
		<th>名前</th>
		<th>所属</th>
		<th>役職</th>
	</tr>


	<c:forEach items="${allusers}" var="alluser">
		<tr>
			<td>${alluser.loginId}</td>
			<td>${alluser.name}</td>
				<c:forEach items="${allbranches}" var="allbranches">
					<c:if test="${alluser.id == allbranches.id}">
						<td>${allbranches.name}</td>
					</c:if>
				</c:forEach>
				<c:forEach items="${alljobs}" var="alljobs">
					<c:if test="${alluser.id == alljobs.id}">
						<td>${alljobs.name}</td>
					</c:if>
				</c:forEach>
			<td>
				<form action="settings" method="get">
					<button type="submit"  name="id" value="${alluser.id}">編集</button>

				</form>
			</td>
			<td>
				<form action="stopped" method="post">
					<input type="hidden" name="id" value="${alluser.id}">
					<c:if test="${alluser.isStopped == 0}">
						<input type="hidden" name="isStopped" value=1>
						<button type="submit">停止</button>
					</c:if>

					<c:if test="${alluser.isStopped == 1}">
						<input type="hidden" name="isStopped" value=0>
						<button type="submit">復活</button>
					</c:if>
				</form>
			</td>
		</tr>
		<br />
	</c:forEach>
</table>
<a href="./">トップへ戻る</a>
</body>
</html>