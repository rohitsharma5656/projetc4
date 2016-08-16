<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%@include file="Header.jsp"%>
	<a href="<%=ORSView.LOGIN_CTL%>"><b>Login</b></a>&nbsp;&nbsp;&nbsp;
	<form action="">
		<h1 align="center">
			<font size="10px" color="red">Welcome to ORS</font>
		</h1>
		<br> <br> <a href="<%=ORSView.MARKSHEET_LIST_VIEW%>"><b>Click
				Here</b></a>

	</form>
	<%@include file="Footer.jsp"%>
</body>
</html>