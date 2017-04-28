<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width,initial-scale=1.0" />
<title>loginPro.jsp</title>
</head>
<body>
	<c:if test="${check==1}">
		<c:set var="id" value="${id}" scope="session" />
	</c:if>
	<p id="ck">${check}
</body>
</html>