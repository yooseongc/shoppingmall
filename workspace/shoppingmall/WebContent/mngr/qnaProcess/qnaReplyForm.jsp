<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="/shoppingmall/css/style.css"/>
<script src="/shoppingmall/js/jquery-1.11.0.min.js"></script>
<script src="/shoppingmall/mngr/qnaProcess/qnawrite.js"></script>

<c:if test="${empty sessionScope.id}">
  <meta http-equiv="Refresh" content="0;url=/shoppingmall/mg/managerMain.do" >
</c:if>

<input type="hidden" id="qna_writer" value="manager">
<input type="hidden" id="qna_id" value="${ qna_id }">
<input type="hidden" id="book_id" value="${ book_id }">
<input type="hidden" id="book_title" value="${ book_title }">
<input type="hidden" id="qora" value="${ qora }">

<div id="writeForm" class="box">
	<ul>
		<li>
			<p>[${ book_title }] 의 QnA</p>
			<p>QnA내용 : ${ qna_content }</p>
		</li>
		<li>
			<label for="rContent">답변</label>
			<textarea id="rContent" rows="13" cols="50"></textarea>
		</li>
		<li class="label2">
			<button id="replyPro">답변하기</button>
			<button id="cancel">취소</button>
		</li>
	</ul>
</div>