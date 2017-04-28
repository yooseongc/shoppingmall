<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<meta name="viewport" content="width=device-width,initial-scale=1.0" />
<link rel="stylesheet" href="/shoppingmall/css/style.css" />
<script src="/shoppingmall/js/jquery-1.11.0.min.js"></script>
<script src="/shoppingmall/buy/buyForm.js"></script>

<c:if test="${empty sesstionScope.id }">
	<meta http-equiv="Refresh" content="0;url=/shoppingmall/index.do">
</c:if>

<div id="cartArea">
	<table>
		<tr class="cen">
			<td width="300">상품명</td>
			<td width="100">판매가격</td>
			<td width="50">수량</td>
			<td width="100">금액</td>
		</tr>
		<c:set var="total" value="0" />
		<c:forEach var="cart" items="${cartLists }">
			<tr>
				<td width="300"><img
					src="/shoppingmal/bookImage/${cart.getBook_image() }"
					class="cartimage">${cart.getBook_title() }</td>
				<td width="100" class="cen"><fmt:formatNumber
						value="${cart.getBuy_price() }" type="number" pattern="#,##0" />원</td>
				<td width="50" class="cen">${cart.getBuy_count() }</td>
				<td width="100" class="cen"><c:set var="amount"
						value="${cart.getBuy_count()*cart.getBuy_price() }" /> <c:set
						var="total" value="${total+amount }" /> <fmt:formatNumber
						value="${amount }" type="number" pattern="#,##0" />원</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="4" align="right" class="b">총 금액 : <fmt:formatNumber
					value="${total }" type="number" pattern="#,##0" />원
			</td>
		</tr>
	</table>
</div>

<div id="buyArea">
	<form name="buyForm" method="post" action="/shoppingmall/buyPro.do">
		<table>
			<tr>
				<td colspan="2"><font size="+1"><b>주문자 정보</b></font></td>
			</tr>
			<tr>
				<td width="200" align="left">성명</td>
				<td width="400" align="left">${member.getName() }</td>
			</tr>
			<tr>
				<td width="200" align="left">결제계좌</td>
				<td width="400" align="left"><select name="account">
						<c:forEach var="accountList" items="${accountLists }">
							<option value="${accountList }">${accountList }</option>
						</c:forEach>
				</select></td>
			</tr>
		</table>
		<br>

		<table>
			<tr>
				<td colspan="2" align="center"><font size="+1"><b>배송지
							정보</b></font></td>
			</tr>
			<tr>
				<td width="200" align="left">성명</td>
				<td width="400" align="left"><input type="text"
					name="deliveryName" value="${member.getName() }"></td>
			</tr>
			<tr>
				<td width="200" align="left">전화번호</td>
				<td width="400" align="left"><input type="text"
					name="deliveryTel" value="${member.getTel() }"></td>
			</tr>
			<tr>
				<td width="200" align="left">주소</td>
				<td width="400" align="left"><input type="text"
					name="deliveryAddress" value="${member.getAddress() }"> <input
					type="hidden" name="buyer" value="${sessionScope.id }"></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="submit" value="주문">
					<button id="cancle">취소</button></td>
			</tr>
		</table>
	</form>
</div>