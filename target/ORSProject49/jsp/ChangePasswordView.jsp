<%@page import="com.raystec.proj4.controller.ChangePasswordCtl"%>
<%@page import="com.raystec.proj4.util.DataUtility"%>
<%@page import="com.raystec.proj4.util.ServletUtility"%>
<html>
<body>
	<form action="<%=ORSView.CHANGE_PASSWORD_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="com.raystec.proj4.bean.UserBean"
			scope="request"></jsp:useBean>

		<center>
			<h1>Change Password</h1>


		 	<H2>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font>
			</H2>
		 	<H2>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</H2>

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
					<th align="left">Old Password*</th>
					<td><input type="password" name="OldPassword"
						value=<%=DataUtility
					.getString(request.getParameter("OldPassword") == null ? ""
							: DataUtility.getString(request
									.getParameter("OldPassword")))%>></td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("OldPassword", request)%></font></td>
				</tr>

				<tr>
					<th align="left">New Password*</th>
					<td><input type="password" name="newPassword"
						value=<%=DataUtility
					.getString(request.getParameter("newPassword") == null ? ""
							: DataUtility.getString(request
									.getParameter("newPassword")))%>></td>
					<td><font color="red"> <%=ServletUtility.getErrorMessage("newPassword", request)%></font></td>
				</tr>

				<tr>
					<th align="left">Confirm Password*</th>
					<td><input type="password" name="confirmPassword"
						value=<%=DataUtility.getString(request
					.getParameter("confirmPassword") == null ? "" : DataUtility
					.getString(request.getParameter("confirmPassword")))%>></td>
					<td><font color="red"> <%=ServletUtility
					.getErrorMessage("confirmPassword", request)%></font></td>
				</tr>

				<tr>
					<th></th>
					<td colspan="2"><input type="submit" name="operation"
						value="<%=ChangePasswordCtl.OP_SAVE%>"> &nbsp; <input
						type="submit" name="operation"
						value="<%=ChangePasswordCtl.OP_CHANGE_MY_PROFILE%>"></td>
				</tr>

			</table>
	</form>
	</center>
	<%@ include file="Footer.jsp"%>
</body>
</html>