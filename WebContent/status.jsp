<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html >
<html>
<head>
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
<h3>社員アカウント管理</h3>
<a href="signup">入社登録</a>
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
					<button type="submit"  name="id" value="${alluser.id}">編集</button>
				</form>
			</td>
			<td>
				<form action="stopped" method="post" onSubmit="return check()">
					<input type="hidden" name="id" value="${alluser.id}">
					<c:if test="${alluser.isStopped == 0}">
						<input type="hidden" name="isStopped" value=1>
						<button type="submit">停止可能</button>
					</c:if>

					<c:if test="${alluser.isStopped == 1}">
						<input type="hidden" name="isStopped" value=0>
						<button type="submit">停止解除</button>
					</c:if>
				</form>
			</td>
		</tr>
	</c:forEach>
</table>
<a href="./">INFORMATIONへ戻る</a>
</body>
</html>