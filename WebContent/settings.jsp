<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html >
<html>
<head>
	<link href="css/settings.css" rel="stylesheet" type="text/css">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ユーザー編集</title>
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
<h3 class="topTitle">社 員 編 集 ペ ー ジ</h3>
<div class="nav">
<ul class="nl clearFix">
<li><a href="status">～アカウント管理に戻る～</a></li>
</ul>
</div>

<c:if test="${ not empty errorMessages }">
	<div class="errorMessages">
		<ul>
			<c:forEach items="${errorMessages}" var="message">
				<c:out value="${message}" /><br>
			</c:forEach>
		</ul>
	</div>
	<c:remove var="errorMessages" scope="session"/>
</c:if>
<div class="box">
<form action="settings" method="post" onSubmit="return check()"><br />
<div class="list">
	<label for="loginId">ID</label>
	<input name="loginId" value="${editUser.loginId}" /><br />6文字以上20文字以下、半角英数字のみ<br />

	<label for="password">パスワード</label>
	<input name="password" type="password" /><br />6文字以上20文字以下、半角英数字のみ<br />

	<label for="newPassword">パスワード(確認用)</label>
	<input name="newPassword" type="password" /><br />6文字以上20文字以下、半角英数字のみ<br />

	<label for="name">名前</label>
	<input name="name" value="${editUser.name}"/><br />10文字以下<br />

	<c:if test="${editUser.jobId == 1}">
		<c:forEach items="${allbranches}" var="allbranche">
			<c:if test="${allbranche.id == editUser.branchId}">
				<input name="branchId" value="${allbranche.id}" type="hidden" />
			</c:if>
		</c:forEach>
	</c:if>

	<c:if test="${editUser.jobId != 1}">
	所属名
		<select name="branchId">
			<option value="0" selected>所属</option>
				<c:forEach items="${allbranches}" var="allbranche">
					<c:if test="${allbranche.id == editUser.branchId}">
						<option value="${allbranche.id}" selected>${allbranche.name}</option>
					</c:if>
					<c:if test="${allbranche.id != editUser.branchId}">
						<option value="${allbranche.id}" >${allbranche.name}</option>
					</c:if>
				</c:forEach>
		</select><br />
	</c:if>


	<c:if test="${editUser.jobId == 1}">
		<c:forEach items="${alljobs}" var="alljob">
			<c:if test="${alljob.id == editUser.jobId}">
				<input name="jobId" value="${alljob.id}" type="hidden" />
			</c:if>
		</c:forEach>
	</c:if>

	<c:if test="${editUser.jobId != 1}">
	役職名
		<select name="jobId">
			<option value="0" selected>役職</option>
				<c:forEach items="${alljobs}" var="alljob">
					<c:if test="${alljob.id == editUser.jobId}">
						<option value="${alljob.id}" selected>${alljob.name}</option>
					</c:if>
					<c:if test="${alljob.id != editUser.jobId}">
						<option value="${alljob.id}" >${alljob.name}</option>
					</c:if>
				</c:forEach>
		</select><br />
	</c:if>
</div>
	<button class="newButton" type="submit"  name="id" value="${editUser.id}">編集する</button><br />

</form>
</div>
</body>
</html>
