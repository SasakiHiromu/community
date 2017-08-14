<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
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
<h3>○○社　社員編集ページ</h3>

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

<form action="settings" method="post" onSubmit="return check()"><br />
	<label for="loginId">ID</label>
	<input name="loginId" value="${editUser.loginId}"/> <br />

	<label for="password">パスワード</label>
	<input name="password" type="password" /> <br />

	<label for="password">パスワード(確認用)</label>
	<input name="newPassword" type="password" /> <br />

	<label for="name">名前</label>
	<input name="name" value="${editUser.name}"/> <br />


所属名
	<select name="branchId">
		<option selected>所属</option>
			<c:forEach items="${allbranches}" var="allbranche">
				<c:if test="${allbranche.id == editUser.branchId}">
					<option value="${allbranche.id}" selected>${allbranche.name}</option>
				</c:if>
				<c:if test="${allbranche.id != editUser.branchId}">
					<option value="${allbranche.id}" >${allbranche.name}</option>
				</c:if>
			</c:forEach>
	</select><br />


役職名
	<select name="jobId">
		<option selected>役職</option>
			<c:forEach items="${alljobs}" var="alljob">
				<c:if test="${alljob.id == editUser.jobId}">
					<option value="${alljob.id}" selected>${alljob.name}</option>
				</c:if>
				<c:if test="${alljob.id != editUser.jobId}">
					<option value="${alljob.id}" >${alljob.name}</option>
				</c:if>
			</c:forEach>
	</select><br />


	<button type="submit"  name="id" value="${editUser.id}">編集</button><br />
	<a href="status">ユーザー管理画面に戻る</a>
</form>
</div>
</body>
</html>
