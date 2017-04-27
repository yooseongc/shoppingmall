<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta name="viewport" content="width=device-width,initial-scale=1.0"/>
<link rel="stylesheet" href="../css/style.css"/>
<script src="/shoppingmall/js/jquery-1.11.0.min.js"></script>
<script src="/shoppingmall/qna/update.js"></script>

<c:if test="${empty sessionScope.id}">
  <meta http-equiv="Refresh" content="0;url=/shoppingmall/index.do">
</c:if>

<input type="hidden" id="qna_id" value="${qna_id}">
<input type="hidden" id="book_kind" value="${book_kind}">
<input type="hidden" id="book_id" value="${qna.getBook_id()}">

<div id="editForm" class="box">
   <ul>
      <li><label for="content">내용</label>
          <textarea id="updateCont" rows="13" cols="50">${qna.getQna_content()}</textarea>
      <li class="label2">
          <button id="update">수정</button>
          <button id="cancle">취소</button> 
   </ul>
</div>