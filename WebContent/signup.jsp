<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html >
<html>
<head>
	<link href="css/signup.css" rel="stylesheet" type="text/css">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ユーザー登録</title>

<script type="text/javascript">

function check(){

	if(window.confirm('以上の内容で登録してよろしいですか？')){ // 確認ダイアログを表示

		return true; // 「OK」時は送信を実行

	}
	else{ // 「キャンセル」時の処理

		return false; // 送信を中止

	}

}

</script>
</head>
<body>
<h3 class="topTitle" >新 規 入 社 登 録</h3>
<div class="nav">
<ul class="nl clearFix">
<li><a href="status">～アカウント管理ページ～</a></li>
<li><a href="logout">～ログアウト～</a></li>
</ul>
</div>
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
<div class="box">
<form action="signup" method="post" onSubmit="return check()"><br />
<div class="list">
	<label for="loginId">ID</label>
	<input name="loginId" id="loginId" value="${user.loginId}" maxlength="20"/><br />6文字以上20文字以下、半角英数字のみ<br />

	<label for="password">パスワード</label>
	<input name="password" type="password" id="password" value="${user.password}" maxlength="20"/><br />6文字以上20文字以下、半角英数字のみ<br />

	<label for="password">パスワード(確認用)</label>
	<input name="newPassword" type="password" maxlength="20"/><br />6文字以上20文字以下、半角英数字のみ<br />

	<label for="name">名前</label>
	<input name="name"  id="name" value="${user.name}" maxlength="10"/><br />10文字以下<br />

	<label for="branchId">所属名</label>

	<select name="branchId">
		<option value="0" selected>所属</option>
			<c:forEach items="${allbranches}" var="allbranche">
				<c:if test="${allbranche.id == user.branchId}">
					<option value="${allbranche.id}" selected>${allbranche.name}</option>
				</c:if>
				<c:if test="${allbranche.id != user.branchId}">
					<option value="${allbranche.id}" >${allbranche.name}</option>
				</c:if>
			</c:forEach>
	</select><br />


	<label for="branchId">役職名</label>

	<select name="jobId">
		<option value="0" selected>役職</option>
			<c:forEach items="${alljobs}" var="alljob">
				<c:if test="${alljob.id == user.jobId}">
					<option value="${alljob.id}" selected>${alljob.name}</option>
				</c:if>
				<c:if test="${alljob.id != user.jobId}">
					<option value="${alljob.id}" >${alljob.name}</option>
				</c:if>
			</c:forEach>
	</select><br />
	</div>
	<input class="newButton" type="submit" value="新規登録" /> <br />


</form>
</div>

</body>
</html>
