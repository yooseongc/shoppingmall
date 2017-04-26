<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" href="/shoppingmall/css/style.css"/>
<script src="/shoppingmall/js/jquery-1.11.0.min.js"></script>
<script src="/shoppingmall/mngr/orderedProduct/orderlist.js"></script>

<c:if test="${ empty sessionScope.id }">
	<meta http-equiv="Refresh" content="0;url=/shoppingmall/mg/managerMain.do">
</c:if>

<div id="listHeader">
	<p>주문 목록(전체 주문: ${ count })
	<button id="bookMain">관리자 메인으로</button></p>
</div>

<div id="orders">
	<c:if test="${ count == 0 }">
		<ul>
			<li>주문 목록이 없습니다.</li>
		</ul>
	</c:if>
	<c:if test="${ count }">
		<table>
			<tr class="title">
				<td>주문번호</td>
				<td>주문자</td>
				<td>상품명</td>
				<td>가격</td>
				<td>주문수량</td>
				<td>금액</td>
				<td>주문일</td>
				<td>결제계좌</td>
				<td>배송명</td>
				<td>배송지전화</td>
				<td>배송지주소</td>
				<td>배송지상황</td>
			</tr>
			<c:set var="total" value="0"/>
			<c:forEach var="i" begin="0" end="${ buyLists.size() - 1 }">
				<c:set var="buyList" value="${ buyLists.get(i) }"/>
				<c:set var="pid" value="${ buyList.getBuy_id() }"/>
				<c:if test="${ i+1 > buyLists.size()-1 }">
					<c:set var="nid" value="0"/>
				</c:if>
				<c:if test="${ i+1 <= buyLists.size()-1 }">
					<c:set var="nid" value="${ buyLists.get(i+1).getBuy_id() }"/>
				</c:if>
				<tr>
					<td>${ buyList.getBuy_id() }</td>
					<td>${ buyList.getBuyer() }</td>
					<td>${ buyList.getBook_title() }</td>
					<td>
						<fmt:formatNumber value="${ buyList.getBuy_price() }" type="number" pattern="#,##0"/>원
					</td>
					<td>${ buyList.getBuy_count() }</td>
					<td>
						<c:set var="amount" value="${ buyList.getBuy_count() * buyList.getBuy_price() }"/>
						<c:set var="total" value="${ total + amount }"/>
						<fmt:formatNumber value="${ amount }" type="number" pattern="#,##0"/>원
					</td>
					<td>${ buyList.getBuy_date().toString() }</td>
					<td>${ buyList.getAccount() }</td>
					<td>${ buyList.getDeliveryName() }</td>
					<td>${ buyList.getDeliveryTel() }</td>
					<td>${ buyList.getDeliveryAddress() }</td>
					<td>${ buyList.getSanction() }</td>
				</tr>
				
				<c:if test="${ pid != nid }">
					<tr>
						<td colspan="12" class="b">주문금액 : 
							<fmt:formatNumber value="${ total }" type="number" pattern="#,##0"/>원
						</td>
					</tr>
					<c:set var="total" value="0"/>
					<c:set var="pid" value="${ nid }"/>
				</c:if>
			</c:forEach>
		</table>
	</c:if>
</div>
