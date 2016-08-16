<%@page import="com.raystec.proj4.controller.RoleCtl"%>
<%@page import="com.raystec.proj4.util.DataUtility"%>
<%@page import="com.raystec.proj4.util.ServletUtility"%>
<%@page import="com.raystec.proj4.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<form action="<%=ORSView.ROLE_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="com.raystec.proj4.bean.RoleBean"
			scope="request"></jsp:useBean>

		<center>
			<%if(bean.getId()==0){ %>
            <h1>Add Role</h1>
            <%}else{ %>
            <h1>Update Role</h1>
            <%} %>
			<h2>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h2>
			<h2>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h2>
			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

			<table>

				<tr>
					<th align="left">Name*</th>
					<td><input type="text" name="name"
						value="<%=DataUtility.getStringData(bean.getName())%>"><font
						color="red"> <%=ServletUtility.getErrorMessage("name", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Description*</th>
					<td><textarea rows="2" cols="20" name="description"><%=DataUtility.getStringData(bean.getDescription())%></textarea>
						<font color="red"> <%=ServletUtility.getErrorMessage("description", request)%></font></td>
				</tr>
				
				<tr>
					<th></th>
					<td colspan="2"><input type="submit" name="operation"
						value="<%=RoleCtl.OP_SAVE%>">&emsp; <%
 	if (bean.getId() > 0) {
 %> <%-- <input type="submit" name="operation"
						value="<%=RoleCtl.OP_DELETE%>"> &emsp; --%> <input type="submit"
						name="operation" value="<%=RoleCtl.OP_CANCEL%>"> <%
 	} else {
 %> <input type="submit" name="operation" value="<%=RoleCtl.OP_CANCEL%>">
						<%
							}
						%></td>
				</tr>
			</table>
		</center>

	</form>
	<%@ include file="Footer.jsp"%>
</body>
</html>