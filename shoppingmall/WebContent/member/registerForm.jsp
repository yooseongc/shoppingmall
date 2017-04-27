<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width,initial-scale=1.0" />
<link rel="stylesheet" href="/shoppingmall/css/style.css" />
<script src="/shoppingmall/js/jquery-1.11.0.min.js"></script>
<script src="/shoppingmall/member/register.js"></script>
<title>Insert title here</title>
</head>
<body>
	<div id="regForm" class="box">
		<ul>
			<li><label for="id">아이디</label><input id="id" name="id"
				type="email" size="20" placeholder="example@kings.com"
				maxlength="50" autofocus>
				<button id="chechid">ID중복확인</button></li>
			<li><label for="passwd">비밀번호</label><input id="passwd"
				name="passwd" type="password" size="20" placeholder="6~16자 숫자/문자"
				maxlength="16"></li>
			<li><label for="repass">비밀번호 재입력</label><input id="repass"
				name="repass" type="password" size="20" placeholder="비밀번호재입력"
				maxlength="16"></li>
			<li><label for="name">이름</label><input id="name" name="name"
				type="text" size="20" placeholder="홍길동" maxlength="10"></li>
			<li><label for="address">주소</label><input id="address"
				name="address" type="text" size="30" placeholder="주소 입력"
				maxlength="50"></li>
			<li><label for="tel">전화번호</label><input id="tel" name="tel"
				type="tel" size="20" placeholder="전화번호입력" maxlength="20"></li>
			<li class="label2"><button id="process">가입하기</button>
				<button id="cancel">취소</button></li>
		</ul>
	</div>
</body>
</html>