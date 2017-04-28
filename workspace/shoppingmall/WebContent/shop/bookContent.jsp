<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link rel="stylesheet" href="/shoppingmall/css/style.css"/>
<script src="/shoppingmall/js/jquery-1.11.0.min.js"></script>
<script src="/shoppingmall/shop/bookcontent.js"></script>

<div id="cata" class="box2">
	<ul>
		<li><a href="/shoppingmall/list.do?book_kind=100">문학</a></li>
		<li><a href="/shoppingmall/list.do?book_kind=200">어학</a></li>
		<li><a href="/shoppingmall/list.do?book_kind=300">컴퓨터</a></li>
		<li><a href="/shoppingmall/list.do?book_kind=all">전체</a></li>
	</ul>
</div>

<div id="showBook">
	<table class="vhcenter">
		<tr height="30">
			<td rowspan="6" width="150">
				<img src="/shoppingmall/bookImage/${ book.getBook_image() }" class="contentimage">
			</td>
			<td width="500">
				<b>${ book.getBook_title() }</b>
			</td>
		</tr>
		<tr>
			<td width="500">저자 : ${ book.getAuthor() }</td>
		</tr>
		<tr>
			<td width="500">출판사 : ${ book.getPublishing_com() }</td>
		</tr>
		<tr>
			<td width="500">출판일 : ${ book.getPublishing_date() }</td>
		</tr>
		<tr>
			<td width="500">
				<c:set var="price" value="${ book.getBook_price() }"/>
				<c:set var="rate" value="${ book.getDiscount_rate() }"/>
				정가 : <fmt:formatNumber value="${ price }" type="number" pattern="#,##0"/>원
				<br>
				<strong class="bred">
					<c:set var="rPrice" value="${ price*((100.0-rate)/100) }"/>
					판매가 : <fmt:formatNumber value="${ rPrice }" type="number" pattern="#,##0"/>원
				</strong>
			</td>
		</tr>
		<tr>
			<td width="500">
				<c:if test="${ !empty sessionScope.id }">
					<c:if test="${ book.getBook_count() == 0 }">
						<p>일시품절</p>
					</c:if>
					<c:if test="${ book.getBook_count() >= 1 }">
						수량 : <input type="text" size="5" id="buy_count" value="1">개
					</c:if>
					<input type="hidden" id="book_id" value="${ book_id }">
					<input type="hidden" id="book_image" value="${ book.getBook_image() }">
					<input type="hidden" id="book_title" value="${ book.getBook_title() }">
					<input type="hidden" id="buy_price" value="${ rPrice }">
					<input type="hidden" id="book_kind" value="${ book_kind }">
					<input type="hidden" id="buyer" value="${ sessionScope.id }">
					<button id="insertCart">장바구니에 담기</button>
				</c:if>
				<c:if test="${ empty sessionScope.id }">
					<c:if test="${ book.getBook_count() == 0 }">
						<p>일시품절</p>
					</c:if>
					<p>제품을 구매하시려면 로그인하세요.</p>
				</c:if>
				<button id="list">목록으로</button>
				<button id="shopMain">메인으로</button>
			</td>
		</tr>
		<tr class="ch">
			<td colspan="2" class="hleft">${ book.getBook_content() }</td>
		</tr>
	</table>
</div>

<div id="showQna">
	<p class="b">상품 QnA 
		<c:if test="${ !empty sessionScope.id }">
			<button id="writeQna">상품QnA쓰기</button>
		</c:if>
		<c:if test="${ empty sessionScope.id }">
			<p>상품QnA를 쓰시려면 로그인하세요.</p>
		</c:if>
	</p>
	<c:if test="${ count == 0 }">
		<ul>
			<li>등록된 상품QnA가 없습니다.</li>
		</ul>
	</c:if>
	<c:if test="${ count > 0 }">
		<c:forEach var="qna" items="${ qnaLists }">
			<ul>
				<li>
					<c:set var="writer" value="${ qna.getQna_writer() }"/>
					${ fn:substring(writer, 0, 4) }****
					<small class="date">(${ qna.getReg_date() })</small>
				</li>
				<li>
					${ qna.getQna_content() }
				</li>
				<li>
					<c:if test="${ sessionScope.id == writer }">
						<button id="edit" name="${ qna.getQna_id() },${ book_kind }" onclick="edit(this)">수정</button>
						<button id="delete" name="${ qna.getQna_id() },${ book_id },${ book_kind }" onclick="del(this)">삭제</button>
					</c:if>
				</li>
			</ul>
		</c:forEach>
	</c:if>
</div>