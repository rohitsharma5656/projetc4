<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.raystec.proj4.controller.UserListCtl"%>
<%@ page import="com.raystec.proj4.util.ServletUtility"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.raystec.proj4.bean.RoleBean"%>
<%@page import="com.raystec.proj4.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<script type="text/javascript" src="../js/jquery-1.4.2.min.js"></script>
<SCRIPT language="javascript">
	$(function() {

		// add multiple select / deselect functionality
		$("#selectall").click(function() {
			$('.ids').attr('checked', this.checked);
		});

		// if all checkbox are selected, check the selectall checkbox
		// and viceversa
		$(".ids").click(function() {

			if ($(".ids").length == $(".ids:checked").length) {
				$("#selectall").attr("checked", "checked");
			} else {
				$("#selectall").removeAttr("checked");
			}

		});
	});
</SCRIPT>




</head>
<body>

	<%@ include file="Header.jsp"%>
	<center>
		<h1>User List</h1>
		<H2>
			<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
		</H2>
		<form action="<%=ORSView.USER_LIST_CTL%>" method="post">
			<table width="100%">

				<jsp:useBean id="bean" class="com.raystec.proj4.bean.UserBean"
					scope="request"></jsp:useBean>
				<%
					List l = (List) request.getAttribute("roleList");
				%>

				<tr>



					<td align="center">
					<label>FirstName :</label> 
					<input type="text" name="firstName" value="<%=ServletUtility.getParameter("firstName", request)%>">
                   <label>LastName :</label> 
	               <input type="text" name="lastName"  value="<%=ServletUtility.getParameter("lastName", request)%>">
				 <label>LoginId :</label> 
				<input type="text" name="login" value="<%=ServletUtility.getParameter("login", request)%>">

                  <label>Role :</label>
					<%=HTMLUtility.getList("roleId",
					String.valueOf(bean.getRoleId()), l)%>
					
					&nbsp;<input type="submit" name="operation" value="<%=UserListCtl.OP_SEARCH%>">




				</tr>
			</table>
			<br>

			<table border="1" width="100%">
				<tr>
					<th><input type="checkbox" id="selectall" />Select</th>
					<th>S.No.</th>
					<th>FirstName</th>
					<th>LastName</th>
					<th>LoginId</th>
					<th>Gender</th>
					<th>DOB</th>
					<th>Edit</th>
				</tr>

				<tr>
					<td colspan="8"><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></td>
				</tr>

				<%
					int pageNo = ServletUtility.getPageNo(request);
					int pageSize = ServletUtility.getPageSize(request);
					int index = ((pageNo - 1) * pageSize) + 1;

					List list = ServletUtility.getList(request);
					Iterator<UserBean> it = list.iterator();
					while (it.hasNext()) {
						UserBean bean1 = it.next();
				%>

				<tr>

					<td align="center"><input type="checkbox" class="ids"
						name="ids" value="<%=bean1.getId()%>"></td>
					<td align="center"><%=index++%></td>
					<td align="center"><%=bean1.getFirstName()%></td>
					<td align="center"><%=bean1.getLastName()%></td>
					<td align="center"><%=bean1.getLogin()%></td>
					<td align="center"><%=bean1.getGender()%></td>
					<td align="center"><%=bean1.getDob()%></td>
					<td align="center"><a href="UserCtl.do?id=<%=bean1.getId()%>">Edit</a></td>

				</tr>

				<%
					}
				%>
			</table>
			<table width="100%">
				<tr>
					<%
						if (pageNo == 1) {
					%>
					<td><input type="submit" name="operation" disabled
						value="<%=UserListCtl.OP_PREVIOUS%>"></td>
					<%
						} else {
					%>
					<td><input type="submit" name="operation"
						value="<%=UserListCtl.OP_PREVIOUS%>"></td>
					<%
						}
					%>
					<td>
					<td><input type="submit" name="operation"
						value="<%=UserListCtl.OP_NEW%>"></td>
					<td><input type="submit" name="operation"
						value="<%=UserListCtl.OP_DELETE%>"></td>
					<td align="right">
						<%
							if (list.size() < pageSize) {
						%> <input type="submit" name="operation" disabled
						value="<%=UserListCtl.OP_NEXT%>"> <%
 	} else {
 %> <input type="submit" name="operation"
						value="<%=UserListCtl.OP_NEXT%>"> <%
 	}
 %>
					</td>
				</tr>
			</table>
			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">


		</form>
	</center>

	<p
		style="margin-top: 85px; background-color: red; color: white; text-align: center;">
		<%@ include file="Footer.jsp"%>
	</p>
</body>
</html>