<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html >
<html>
<head>
<link href="css/status.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>社員アカウント管理</title>
<script type="text/javascript">

function check(){

	if(window.confirm('登録しますか？')){ // 確認ダイアログを表示

		return true; // 「OK」時は送信を実行

	}
	else{ // 「キャンセル」時の処理

		return false; // 送信を中止

	}

}

</script>

</head>
<body>
<h3 class="topTitle">ア カ ウ ン ト 管 理 ペ ー ジ</h3>
<div class="nav">
<ul class="nl clearFix">
<li><a href="signup">～新規入社登録～</a></li>
<li><a href="./">～TOPへ戻る～</a></li>
<li><a href="logout">～ログアウト～</a></li>
</ul>
</div>
<table class="table">
	<tr>
		<th scope="col">id</th>
		<th scope="col">名前</th>
		<th scope="col">所属</th>
		<th scope="col">役職</th>
		<th scope="col">社員編集</th>
		<th scope="col">復活＆停止</th>
	</tr>
	<c:forEach items="${allusers}" var="alluser">
		<tr>
			<td>${alluser.loginId}</td>
			<td>${alluser.name}</td>

				<c:forEach items="${allbranches}" var="allbranch">
					<c:if test="${alluser.branchId == allbranch.id}">
						<td><c:out value="${allbranch.name}"></c:out></td>
					</c:if>
				</c:forEach>

				<c:forEach items="${alljobs}" var="alljob">
					<c:if test="${alluser.jobId == alljob.id}">
						<td><c:out value="${alljob.name}"></c:out></td>
					</c:if>
				</c:forEach>
			<td>
				<form action="settings" method="get">
					<button type="submit"  name="id" value="${alluser.id}">編集ページへ</button>
				</form>
			</td>
			<td>
				<form action="stopped" method="post" onSubmit="return check()">
					<c:if test="${loginUser.id != alluser.id}">
						<input type="hidden" name="id" value="${alluser.id}">
						<c:if test="${alluser.isStopped == 0}">
							<input type="hidden" name="isStopped" value=1>
							<button type="submit">ユーザーの停止</button>
						</c:if>

						<c:if test="${alluser.isStopped == 1}">
							<input type="hidden" name="isStopped" value=0>
							<button type="submit">ユーザーの復活</button>
						</c:if>
					</c:if>
					<c:if  test="${loginUser.id == alluser.id}">
						<c:out  value="ログイン中"></c:out>
					</c:if>
				</form>
			</td>
		</tr>
	</c:forEach>
</table>

</body>
</html>