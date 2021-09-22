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

	<form:form action="${pageContext.request.contextPath}/loginProcess">
		<p>
			User name <input type ="text" name="username"/>
		</p>
		
		<p>
			Password <input type ="text" name="password"/>
		</p>
		<input type ="submit" value="Login"/>
	</form:form>

</body>
</html>