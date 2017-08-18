<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html >
<html>
<head>
	<link href="css/message.css" rel="stylesheet" type="text/css">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>新規投稿</title>

	<script>
   function DisableButton(b)
   {
      b.disabled = true;

      b.form.submit();
   }
</script>
</head>
<body>
<h3 class="topTitle" >新 規 投  稿 ペ ー ジ</h3>

<div class="nav">
<ul class="nl clearFix">
<li><a href="./">～TOPに戻る～</a></li>
<li><a href="logout">～ログアウト～</a></li>
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
<form action="newMessage" method="post" ><br />
<div class="list">
	<label for="title">件名</label><br />
	<input name="title" value="${message.title}" maxlength="30"/>30文字以下<br />
既存のカテゴリーから指定<br />
	<select name="category">
		<option value="" selected>カテゴリーを選ぶ</option>
			<c:forEach items="${categories}" var="category">
				<c:if test="${oldCategory == category.category}">
					<option value="${category.category}" selected>
					<c:out value="${category.category}"></c:out></option>
				</c:if>
				<c:if test="${oldCategory != category.category}">
					<option value="${category.category}" >
					<c:out value="${category.category}"></c:out></option>
				</c:if>
			</c:forEach>
	</select><br />
	<label for="newCategory">新規カテゴリー作成</label><br />
	<input name="newCategory" value="${message.category}" maxlength="10"/>10文字以下<br />
</div>
	<br><label for="text">本文</label><br />
	<div class="text">
	<textarea style="resize: none;" name="text" cols="90" rows="10" maxlength="1000">${message.text}</textarea>
	<label>1000文字以下</label><br />
	</div>

	<input class="newButton" type="submit" value="新規投稿" onclick="DisableButton(this);" /><br />


</form>
</div>
</body>
</html>
