<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored= "false"%>
<%@page import="java.util.*" %>
<%@page import="java.text.*" %>
<%@ page import="java.util.Date, java.text.DateFormat" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
	<link href="css/top.css" rel="stylesheet" type="text/css">
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


//送信ボタンを押した際に送信ボタンを無効化する（連打による多数送信回避）
(function(){
	('[type="submit"]').click(function(){
		(this).prop('disabled',true);//ボタンを無効化する
		(this).closest('form').submit();//フォームを送信する
	});
});

function DisableButton(b)
{
   b.disabled = true;

   b.form.submit();
}

</script>

</head>
<body onLoad="init();">
<h3 class="topTitle">CHOKO CHOKO (株) TOP</h3>

<div class="nav">
<ul class="nl clearFix">
<li><a href="newMessage">～新規投稿ページ～</a></li>

<c:if test="${loginUser.jobId == 1}" >
	<li><a href="status">～社員アカウント管理ページ～</a></li>
</c:if>

<li><a href="logout">～ログアウト～</a></li>
</ul>

</div>
<br ><p class="loginUser"><c:out value="${loginUser.name}でログイン中です"></c:out></p>
</body>
<script type="text/javascript"></script>
<div class="categoryBox">
<p class="selectategory" ${絞込みこーなー}></p>
<form action="./" style="display: inline"><br />

カテゴリー指定
<select name="categories">
	<option value="" selected>カテゴリーを選ぶ</option>
		<c:forEach items="${categories}" var="category">
			<c:if test="${lastCategory == category.category}">
				<option value="${category.category}" selected>${category.category}</option>
			</c:if>
			<c:if test="${lastCategory != category.category}">
				<option value="${category.category}">${category.category}</option>
			</c:if>
		</c:forEach>
</select>
日付指定
	<input type="date" name="startDate"  max="${diaryDate}" value="${lastStart}">→
	<input type="date" name="endDate" max="${diaryDate}" value="${lastEnd}">
	<button type="submit">絞込み</button>
</form>
<form action="./" style="display: inline"><button>全投稿表示する</button></form>
<form action="./" style="display: inline" ><button value="toDay" name="toDay">本日"${toDayDate}"の投稿を表示する</button></form><br />
</div>
<c:forEach items="${messages}" var="message">
	<div class="box">
		<div class="subBox">
			<div class="from">
				投稿者：<span class="name"><c:out value="${message.name}" /></span>　
				タイトル：<span class="title"><c:out value="${message.title}" /></span>　
				カテゴリー:<span class="category"><c:out value="${message.category}" /><br ></span>
				<c:forEach items="${allbranches}" var="allbranche">
					<c:if test="${message.branchId == allbranche.id}">
						所属:<span><c:out value="${allbranche.name}" /></span>　
					</c:if>
				</c:forEach>

				<c:forEach items="${alljobs}" var="alljob">
					<c:if test="${message.jobId == alljob.id}">
						役職:<c:out value="${alljob.name}" /><br />
					</c:if>
				</c:forEach>
				<p></p>


			内容
			</div>
			<c:forEach var="text" items="${fn:split(message.text, '
		')}">
		    <div class="textBreak">${text}</div>
		</c:forEach>

		</div>
		<div class="time">
			投稿日時
			<fmt:formatDate value="${message.createdAt}"
			pattern="yyyy/MM/dd HH:mm:ss" />
		</div>

	<c:choose>
		<c:when test="${loginUser.jobId == 2}">
			<form action="Delete" method="post" onSubmit="return check()"><br />
				<button class="deleteButton" type="submit" name="message_id" value="${message.id}">投稿の削除</button>
			</form>
		</c:when>
		<c:when test="${loginUser.id == message.userId}">
			<form action="Delete" method="post" onSubmit="return check()"><br />
				<button class="deleteButton" type="submit" name="message_id" value="${message.id}">投稿の削除</button>
			</form>
		</c:when>
		<c:when test="${loginUser.branchId == message.branchId && loginUser.jobId == 3}">
			<form action="Delete" method="post" onSubmit="return check()"><br />
				<button class="deleteButton" type="submit" name="message_id" value="${message.id}">投稿の削除</button>
			</form>
		</c:when>
	</c:choose>



		<c:forEach items="${comments}" var="comment">
			<c:if test="${message.id == comment.messageId}" >
			<div class="commentBox">
				<div class="from">
					<c:forEach items="${allusers}" var="alluser">
						<c:if test="${alluser.id == comment.userId}">
							コメント記入者:<c:out value="${alluser.name}" /><br />
						</c:if>
					</c:forEach>

					<c:forEach items="${allbranches}" var="allbranche">
						<c:if test="${comment.branchId == allbranche.id}">
							所属:<c:out value="${allbranche.name}" />　
						</c:if>
					</c:forEach>

					<c:forEach items="${alljobs}" var="alljob">
						<c:if test="${comment.jobId == alljob.id}">
							役職:<c:out value="${alljob.name}" /><br />
						</c:if>
					</c:forEach>
					内容
						</div>
					<c:forEach var="text" items="${fn:split(comment.text, '
					')}">
					    <div class="commentBreak">${text}</div>
					</c:forEach>
					</div>

				<c:choose>
					<c:when test="${loginUser.jobId == 2}">
						<form action="Delete" method="post" onSubmit="return check()"><br />
							<button class="deleteButton" type="submit" name="comment_id" value="${comment.id}">コメントの削除</button>
						</form>
					</c:when>
					<c:when test="${loginUser.id == comment.userId}">
						<form action="Delete" method="post" onSubmit="return check()"><br />
							<button class="deleteButton" type="submit" name="comment_id" value="${comment.id}">コメントの削除</button>
						</form>
					</c:when>
					<c:when test="${loginUser.branchId == comment.branchId}">
						<form action="Delete" method="post" onSubmit="return check()"><br />
							<button class="deleteButton" type="submit" name="comment_id" value="${comment.id}">コメントの削除</button>
						</form>
					</c:when>
				</c:choose>

			</c:if>
		</c:forEach>


		<form action="newComment" method="post"><br />
			<textarea class="text"  name="text"  maxlength='500'></textarea>
			<label style ="right">500文字以下</label><br />
			<input type="hidden" name="message_id" value="${message.id}" />
			<button class="newComment" type="submit"  name="loginUser" value="${loginUser.id}"
			onclick="DisableButton(this);"  >コメント投稿</button>
			<p></p>
		</form>

	</div>

</c:forEach>

</html>