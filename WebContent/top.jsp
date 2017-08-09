<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored= "false"%>
<%@page import="java.util.*" %>
<%@page import="java.text.*" %>
<%@ page import="java.util.Date, java.text.DateFormat" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>○○社INFORMATION</title>

<script type="text/javascript">

function check(){

	if(window.confirm('削除してよろしいですか？')){ // 確認ダイアログを表示

		return true; // 「OK」時は送信を実行

	}
	else{ // 「キャンセル」時の処理

		return false; // 送信を中止

	}

}

</script>

</head>
<body>
<h3>○○社INFORMATION</h3>
<c:out value="${loginUser.name}でログイン中です"></c:out>
<div class="main-contents">

<a href="newMessage">新規投稿入力ページ</a>

<c:if test="${loginUser.jobId == 1}" >
	<a href="status">社員アカウント管理ページ</a>
</c:if>

<a href="logout">ログアウト</a>

<div class="header"></div>

</div>
</body>
<div class="messages">
<form action="./"><br />
カテゴリー指定
<select name="categories">
	<option value="" selected>カテゴリーを選ぶ</option>
	<c:forEach items="${categories}" var="category">
		<option value="${category}"><c:out value="${category}"></c:out></option>
	</c:forEach>
</select>
日付指定
	<input type="date" name="startDate" min="2017-07-31" max="${diaryDate}">
	<input type="date" name="endDate" min="2017-07-31" max="${diaryDate}">
	<button type="submit">絞込み</button>
</form>



<c:forEach items="${messages}" var="message">
	<div class="message">
		<div class="account-name">
			名前：<span class="name"><c:out value="${message.name}" /></span><br />
			タイトル：<span class="title"><c:out value="${message.title}" /></span><br />
			カテゴリー:<span class="category"><c:out value="${message.category}" /></span><br />
			<p></p>
		</div>
	本文<div class="text"><c:out value="${message.text}" /></div>
	投稿日時<div class="date"><fmt:formatDate value="${message.createdAt}"
	pattern="yyyy/MM/dd HH:mm:ss" /></div>

	<c:choose>
		<c:when test="${loginUser.jobId == 2}">
			<form action="Delete" method="post" onSubmit="return check()"><br />
				<button type="submit" name="message_id" value="${message.id}">削除</button>
			</form>
		</c:when>
		<c:when test="${loginUser.id == message.userId}">
			<form action="Delete" method="post" onSubmit="return check()"><br />
				<button type="submit" name="message_id" value="${message.id}">削除</button>
			</form>
		</c:when>
		<c:when test="${loginUser.branchId == message.branchId && loginUser.jobID == 3}">
			<form action="Delete" method="post" onSubmit="return check()"><br />
				<button type="submit" name="message_id" value="${message.id}">削除</button>
			</form>
		</c:when>
	</c:choose>

	</div>

		<c:forEach items="${comments}" var="comment">
			<c:if test="${message.id == comment.messageId}" >
				<c:out value="${comment.text}" /><br />

					<c:forEach items="${allusers}" var="alluser">
						<c:if test="${alluser.id == comment.userId}">
							コメント記入者:<c:out value="${alluser.name}" /><br />
						</c:if>
					</c:forEach>

					<c:forEach items="${allbranches}" var="allbranche">
						<c:if test="${comment.branchId == allbranche.id}">
							所属:<c:out value="${allbranche.name}" /><br />
						</c:if>
					</c:forEach>

					<c:forEach items="${alljobs}" var="alljob">
						<c:if test="${comment.jobId == alljob.id}">
							役職:<c:out value="${alljob.name}" /><br />
						</c:if>
					</c:forEach>


				<c:choose>
					<c:when test="${loginUser.jobId == 2}">
						<form action="Delete" method="post" onSubmit="return check()"><br />
							<button type="submit" name="comment_id" value="${comment.id}">削除</button>
						</form>
					</c:when>
					<c:when test="${loginUser.id == comment.userId}">
						<form action="Delete" method="post" onSubmit="return check()"><br />
							<button type="submit" name="comment_id" value="${comment.id}">削除</button>
						</form>
					</c:when>
					<c:when test="${loginUser.branchId == comment.branchId}">
						<form action="Delete" method="post" onSubmit="return check()"><br />
							<button type="submit" name="comment_id" value="${comment.id}">削除</button>
						</form>
					</c:when>
				</c:choose>
			</c:if>
		</c:forEach>


		<form action="newComment" method="post"><br />
			<textarea name="text" rows="4" cols="40"></textarea><br />
			<input type="hidden" name="message_id" value="${message.id}" />
			<button type="submit"  name="loginUser" value="${loginUser.id}">コメント</button>
			<p></p>
		</form>

</c:forEach>
</div>
</html>