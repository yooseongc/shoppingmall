<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width,initial-scale=1.0" />
<link rel="style" href="/shoppingmall/css/style.css" />
<script src="/shoppingmall/js/jquery-1.11.0.min.js"></script>
<script src="/shoppingmall/member/login.js"></script>
<title>Insert title here</title>
</head>
<body>
	<c:if test="${empty sessionScope.id}">
		<div id="lStatus">
			<ul>
				<li><label for="cid">아이디</label><input id="cid" name="cid"
					type="email" size="20" maxlength="50"
					placeholder="example@kings.com"><label for="cpasswd">비밀번호</label><input
					id="cpasswd" name="cpasswd" type="password" size="20"
					placeholder="6~16자 문자/숫자" maxlength="16">
					<button id="uLogin">로그인</button>
					<button id="uRes">회원가입</button></li>
			</ul>
		</div>
	</c:if>
	<c:if test="${!empty sessionScope.id}">
		<div id="lStatus">
			<ul>
				<li>${sessionScope.id}님이로그인하셨습니다.</li>
				<div id="info">
					<table>
						<tr height="10">
							<td><button id="uLogout">로그아웃</button></td>
							<td><button id="uUpdate">회원정보변경</button></td>
							<td><form id="cartForm" method="post"
									action="/shoppingmall/cartList.do">
									<input type="hidden" name="buyer" value="${sessionScope.id}"><input
										type="submit" name="cart" value="장바구니">
								</form></td>
							<td><form id="buyForm" method="post"
									action="/shoppingmall/buyList.do">
									<input type="hidden" name="buyer" value="${sessionScope.id}"><input
										type="submit" name="buy" value="구매내역">
								</form></td>
						</tr>
					</table>
				</div>
			</ul>
		</div>
	</c:if>
</body>
</html>