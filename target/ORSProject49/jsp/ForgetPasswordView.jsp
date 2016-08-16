<%@page import="com.raystec.proj4.controller.ForgetPasswordCtl"%>
<%@page import="com.raystec.proj4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="<%=ORSView.FORGET_PASSWORD_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="com.raystec.proj4.bean.UserBean"
			scope="request"></jsp:useBean>

		<center>
			<h1>Forgot your password?</h1>
			<input type="hidden" name="id" value="<%=bean.getId()%>">
			
			<H1>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</H1>

			<table>
				<lable>Submit your email address and we'll send you
				password.</lable>
			<br>
				<br> 
				<label>Email Id  * : </label>
				<input type="text" name="login" placeholder="Enter ID Here"
					value="<%=ServletUtility.getParameter("login", request)%>">&nbsp;&nbsp;
				<input type="submit" name="operation"
					value="<%=ForgetPasswordCtl.OP_GO%>">
				<br>
				<font color="red"> <%=ServletUtility.getErrorMessage("login", request)%></font>
			</table>

			
			<H1>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font>
			</H1>
	</form>
	</center>
	<%@ include file="Footer.jsp"%>
</body>
</html>