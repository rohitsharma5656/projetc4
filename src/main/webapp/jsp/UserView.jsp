
<%@page import="com.raystec.proj4.controller.UserCtl"%>
<%@page import="com.raystec.proj4.controller.UserCtl"%>
<%@page import="com.raystec.proj4.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.raystec.proj4.util.DataUtility"%>
<%@page import="com.raystec.proj4.util.ServletUtility"%>
<%@page import="java.util.List"%>
<html>
<body>
	<form action="<%=ORSView.USER_CTL%>" method="post">
		<%@ include file="Header.jsp"%>

		<script type="text/javascript" src="../js/calendar.js"></script>

		<jsp:useBean id="bean" class="com.raystec.proj4.bean.UserBean"
			scope="request"></jsp:useBean>

		<%
			List l = (List) request.getAttribute("roleList");
		%>

		<center>
			<%
				if (bean.getId() == 0) {
			%>

			<h1>Add User</h1>
			<%
				} else {
			%>
			<h1>Update User</h1>
			<%
				}
			%>

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
					<th align="left">Role*</th>
					<td><%=HTMLUtility.getList("roleId",
					String.valueOf(bean.getRoleId()), l)%><font
						color="red"> <%=ServletUtility.getErrorMessage("roleId", request)%></font>
					</td>
				</tr> 
				
				<tr>
					<th align="left">First Name*</th>
					<td><input type="text" name="firstName"
						value="<%=DataUtility.getStringData(bean.getFirstName())%>"><font
						color="red"> <%=ServletUtility.getErrorMessage("firstName", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Last Name*</th>
					<td><input type="text" name="lastName"
						value="<%=DataUtility.getStringData(bean.getLastName())%>"><font
						color="red"> <%=ServletUtility.getErrorMessage("lastName", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Login Id*</th>
					<td><input type="text" name="login"
						value="<%=DataUtility.getStringData(bean.getLogin())%>"
						<%=(bean.getId() > 0) ? "readonly" : ""%>><font
						color="red"> <%=ServletUtility.getErrorMessage("login", request)%></font></td>
				</tr>
				<%
					if (bean.getId() == 0) {
				%>
				<tr>
					<th align="left">Password*</th>
					<td><input type="password" name="password"
						value="<%=DataUtility.getStringData(bean.getPassword())%>"><font
						color="red"> <%=ServletUtility.getErrorMessage("password", request)%></font></td>

					<%
						} else {
					%>
					<td><input type="hidden" name="password"
						value="<%=DataUtility.getStringData(bean.getPassword())%>"><font
						color="red"> <%=ServletUtility.getErrorMessage("password", request)%></font></td>
				</tr>


				<%
					}
				%>

				<tr>

					<%
						if (bean.getId() == 0) {
					%>
					<th align="left">Confirm Password*</th>
					<td><input type="password" name="confirmPassword"
						value="<%=DataUtility.getStringData(bean.getConfirmPassword())%>"><font
						color="red"> <%=ServletUtility.getErrorMessage("confirmPassword",
						request)%></font></td>
				</tr>
				<%
					} else {
				%>


				<td><input type="hidden" name="confirmPassword"
					value="<%=DataUtility.getStringData(bean.getPassword())%>"><font
					color="red"> <%=ServletUtility.getErrorMessage("confirmPassword",
						request)%></font></td>
				</tr>

				<%
					}
				%>
               <tr>
					<th align="left">Gender*</th>
					<td>
						<%
							HashMap map = new HashMap();
							map.put("M", "Male");
							map.put("F", "Female");

							String htmlList = HTMLUtility.getList("gender", bean.getGender(),
									map);
						%> <%=htmlList%><font
						color="red"> <%=ServletUtility
					.getErrorMessage("gender", request)%></font>

					</td>
				</tr>
				<tr>
					<th align="left">Role*</th>
					<td><%=HTMLUtility.getList("roleId",
					String.valueOf(bean.getRoleId()), l)%><font
						color="red"> <%=ServletUtility.getErrorMessage("roleId", request)%></font>
					</td>
				</tr>

				<tr>
					<th align="left">Date Of Birth*</th>
					<td><input type="text" name="dob" readonly="readonly"
						value="<%=DataUtility.getDateString(bean.getDob())%>"> <a
						href="javascript:getCalendar(document.forms[0].dob);"> <img
							src="../img/cal.jpg" width="16" height="15" border="0"
							alt="Calender">
					</a><font color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font></td>
				</tr>
				
				<tr>
					<th></th>
					<td colspan="2"><input type="submit" name="operation"
						value="<%=UserCtl.OP_SAVE%>">&emsp; <%
 	            if (bean.getId() > 0) {
             %><input type="submit" name="operation"
						value="<%=UserCtl.OP_DELETE%>"> &emsp; <input
						type="submit" name="operation" value="<%=UserCtl.OP_CANCEL%>">

						<%
							} else {
						%> &emsp; <input type="submit" name="operation"
						value="<%=UserCtl.OP_CANCEL%>"> <%
 	}
 %></td>
				</tr>
			</table>
	</form>
	</center>
	<%@ include file="Footer.jsp"%>
</body>
</html>