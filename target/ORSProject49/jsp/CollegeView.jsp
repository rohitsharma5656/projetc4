<%@page import="com.raystec.proj4.bean.CollegeBean"%>
<%@page import="com.raystec.proj4.controller.CollegeCtl"%>
<%@page import="com.raystec.proj4.controller.BaseCtl"%>
<%@page import="com.raystec.proj4.util.DataUtility"%>
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
	<form action="<%=ORSView.COLLEGE_CTL%>" method="post"> <!-- Expression Tag -->
		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="com.raystec.proj4.bean.CollegeBean"
			scope="request">

		</jsp:useBean>
		<center>
			<%
				if (bean.getId() == 0) {
			%>
			<h1>Add College</h1>
			<%
				} else {
			%>
			<h1>Update College</h1>
			<%
				}
			%>
			<h2>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h2>
			<h2>
				<font color="red" <%=ServletUtility.getErrorMessage(request)%>></font>
			</h2>
			<input type="hidden" name="id" value="<%=bean.getId()%>">
			<table>
				<tr>
					<th align="left">Name*</th>
					<td><input type="text" name="name"
						value="<%=DataUtility.getStringData(bean.getName())%>"> <font
						color="red"> <%=ServletUtility.getErrorMessage("name", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Address*</th>
					<td><input type="text" name="address"
						value="<%=DataUtility.getStringData(bean.getAddress())%>">
						<font color="red"> <%=ServletUtility.getErrorMessage("address", request)%></font></td>
				</tr>
				<tr>
					<th align="left">State*</th>
					<td><input type="text" name="state"
						value="<%=DataUtility.getStringData(bean.getState())%>"> <font
						color="red"> <%=ServletUtility.getErrorMessage("state", request)%></font></td>
				</tr>
				<tr>
					<th align="left">City*</th>
					<td><input type="text" name="city"
						value="<%=DataUtility.getStringData(bean.getCity())%>"><font
						color="red"> <%=ServletUtility.getErrorMessage("city", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Mobile No*</th>
					<td><input type="text" name="phoneNo"
						value="<%=DataUtility.getStringData(bean.getPhoneNo())%>"><font
						color="red"> <%=ServletUtility.getErrorMessage("phoneNo", request)%></font></td>
				</tr>
				<tr>
					<th></th>
					<td colspan="2"><input type="submit" name="operation"
						value="<%=CollegeCtl.OP_SAVE%>">&emsp; <%
 	if (bean.getId() > 0) {
 %> <input type="submit" name="operation"
						value="<%=CollegeCtl.OP_DELETE%>">&emsp; <input
						type="submit" name="operation" value="<%=CollegeCtl.OP_CANCEL%>">


						<%
							} else {
						%> <input type="submit" name="operation"
						value="<%=CollegeCtl.OP_CANCEL%>"> <%
 	}
 %></td>
				</tr>


			</table>
		</center>

	</form>
	<%@ include file="Footer.jsp"%>
</body>
</html>