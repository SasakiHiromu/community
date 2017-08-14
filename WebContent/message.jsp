<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
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
<h3>新規投稿入力ページ</h3>
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
<form action="newMessage" method="post"><br />
	<label for="title">件名</label><br />
	<input name="title" value="${message.title}"/><30文字以下><br />
既存のカテゴリーから指定<br />
	<select name="category">
		<option value="" selected>カテゴリーを選ぶ</option>
			<c:forEach items="${categories}" var="category">
				<c:if test="${oldCategory == category}">
					<option value="${category}" selected>
					<c:out value="${category}"></c:out></option>
				</c:if>
				<c:if test="${oldCategory != category}">
					<option value="${category}" ><c:out value="${category}"></c:out></option>
				</c:if>
			</c:forEach>
	</select><br />
	<label for="newCategory">新規カテゴリー作成</label><br />
	<input name="newCategory" value="${message.category}"/><10文字以下><br />

	<label for="text">本文</label><br />
	<textarea style="resize:none" name="text" cols="100" rows="10" >${message.text}</textarea>
	<label style ="right"><1000文字以下></label><br />


	<input type="submit" value="新規投稿" onclick="DisableButton(this);" /><br />

	<a href="./">INFORMATIONへ戻る</a>
</form>
</div>
</body>
</html>
