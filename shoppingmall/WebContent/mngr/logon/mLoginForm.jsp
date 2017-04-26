<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="/shoppingmall/css/style.css"/>
<script src="/shoppingmall/js/jquery-1.11.0.min.js"></script>
<script src="/shoppingmall/mngr/logon/mlogin.js"></script>

<c:if test="${ empty sessionScope.id }">
	<div id="status">
		<ul>
			<li>
				<label for="id">아이디</label>
				<input id="id" name="id" type="email" size="20"
					maxlength="50" placeholder="example@kings.com">
				<label for="passwd">비밀번호</label>
				<input id="passwd" name="passwd" type="password"
					size="20" placeholder="6~16자 숫자/문자" maxlength="16">
				<button id="login">로그인</button>
			</li>
		</ul>
	</div>
</c:if>
<c:if test="${ !empty sessionScope.id }">
	<div id="status">
		<ul>
			<li>관리자 로그인 성공!!. 작업중...
				<button id="logout">로그아웃</button>
			</li>
		</ul>
	</div>
</c:if>