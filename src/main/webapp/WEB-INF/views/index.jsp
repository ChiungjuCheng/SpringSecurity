<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>Insert title here</title>
</head>
<body>
 login sussess!
 	<!-- 登出 -->
	<form:form action="${pageContext.request.contextPath}/logoutProcess">
		<input type ="submit" value="Logout"/>
	</form:form>
</body>
</html>