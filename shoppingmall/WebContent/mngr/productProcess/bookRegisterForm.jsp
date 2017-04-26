<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" href="/shoppingmall/css/style.css"/>
<script src="/shoppingmall/js/jquery-1.11.0.min.js"></script>
<script src="/shoppingmall/js/jquery.form.min.js"></script>
<script src="/shoppingmall/mngr/productProcess/bookregist.js"></script>

<c:if test="${ empty sessionScope.id }">
	<meta http-equiv="Refresh" content="0;url=/shoppingmall/mg/managerMain.do">
</c:if>

<div id="listHeader">
	<button id="bookMain">관리자 메인으로</button>
	<button id="bookList">목록으로</button>
</div>

<form id="upForm1" action="/shoppingmall/mg/bookRegisterPro.do" 
	method="post" enctype="multipart/form-data">
	
	<div id="bookRegistForm" class="box">
		<ul>
			<li>
				<label for="book_kind">분류선택</label>
				<select id="book_kind" name="book_kind">
					<option value="100">문학</option>
					<option value="200">외국어</option>
					<option value="300">컴퓨터</option>
				</select>
			</li>
			<li>
				<label for="book_title">제목</label>
				<input id="book_title" name="book_title" type="text" 
					size="50" placeholder="제목" maxlength="50">
			</li>
			<li>
				<label for="book_price">가격</label>
				<input id="book_price" name="book_price" type="text"
					size="10" placeholder="가격" maxlength="9">원
			</li>
			<li>
				<label for="book_count">수량</label>
				<input id="book_count" name="book_count" type="text"
					size="10" placeholder="수량" maxlength="5">권
			</li>
			<li>
				<label for="author">저자</label>
				<input id="author" name="author" type="text"
					size="20" placeholder="저자" maxlength="30">
			</li>
			<li>
				<label for="publishing_com">출판사</label>
				<input id="publishing_com" name="publishing_com" type="text"
					size="20" placeholder="출판사" maxlength="30">
			</li>
			<li>
				<label for="publishing_date">출판일</label>
				<div id="publishing_date">
					<jsp:useBean id="nowTime" class="java.util.Date"/>
					<fmt:formatDate var="nowTimeStr" pattern="yyyy-MM-dd"
						value="${ nowTime }"/>
					<fmt:parseNumber var="lastYear" type="NUMBER" 
						value="${ nowTimeStr.toString().substring(0,4) }"/>
					<select name="publishing_year">
						<c:forEach var="i" begin="2010" end="${ lastYear }">
							<option value="${ i }">${ i }</option>
						</c:forEach>
					</select>년
					<select name="publishing_month">
						<c:forEach var="i" begin="1" end="12">
							<option value="${ i }">${ i }</option>
						</c:forEach>
					</select>월
					<select name="publishing_day">
						<c:forEach var="i" begin="1" end="31">
							<option value="${ i }">${ i }</option>
						</c:forEach>
					</select>일
				</div>
			</li>
			<li>
				<label for="book_image">책 이미지</label>
				<input id="book_image" name="book_image" type="file">${ book.book_image }
			</li>
			<li>
				<label for="book_content">내용</label>
				<textarea id="book_content" name="book_content" rows="13" cols="50">${ book.content }</textarea>
			</li>
			<li>
				<label for="discount_rate">할인율</label>
				<input id="discount_rate" name="discount_rate" type="text" size="5" 
					placeholder="10" maxlength="2" value="${ book.discount_rate }">
			</li>
			<li class="label2">
				<input type="submit" id="updateBook" value="책등록">
			</li>
		</ul>
	</div>
</form>