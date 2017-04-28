<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0">
<link rel="stylesheet" href="/shoppingmall/css/style.css"/>
<title>Molla Inc.</title>
</head>
<body>
	
	<div id="header">
		<div id="logo" class="box">
			<img class="noborder" id="logo" src="/shoppingmall/images/mollalogo3.png">
		</div>
		<div id="auth" class="box">
			<%-- type이 0이면 관리자, 1이면 사용자 로그인 페이지 --%>
			<c:if test="${ type == 0 }">
				<c:import url="mngr/logon/mLoginForm.jsp" charEncoding="UTF-8"/>
			</c:if>
			<c:if test="${ type == 1 }">
				<c:import url="member/loginForm.jsp" charEncoding="UTF-8"/>
			</c:if>
		</div>
	</div>
	<div id="content" class="box2">
		<%-- 실제 콘텐츠가 표시되는 영역 --%>
		<c:import url="${ cont }" charEncoding="UTF-8"/>
	</div>
	
</body>
</html>