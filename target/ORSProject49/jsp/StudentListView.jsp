<%@page import="com.raystec.proj4.util.HTMLUtility"%>
<%@page import="com.raystec.proj4.controller.StudentListCtl"%>
<%@page import="com.raystec.proj4.util.ServletUtility"%>
<%@page import="com.raystec.proj4.util.DataUtility"%>
<%@page import="com.raystec.proj4.bean.StudentBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
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
	<%@include file="Header.jsp"%>
	<center>
		<h1>Student List</h1>
		<H2>
			<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</td>
		</H2>
		<form action="<%=ORSView.STUDENT_LIST_CTL%>" method="post">
			<table width="100%">
				<tr>
					<td align="center"><label> FirstName :</label> <input
						type="text" name="firstName"
						value="<%=ServletUtility.getParameter("firstName", request)%>">
						<label>LastName :</label> <input type="text" name="lastName"
						value="<%=ServletUtility.getParameter("lastName", request)%>">
						<label>Email_Id :</label> <input type="text" name="email"
						value="<%=ServletUtility.getParameter("email", request)%>">
				&emsp;<input type="submit" name="operation"
						value="<%=StudentListCtl.OP_SEARCH%>"></td>
				</tr>
			</table>
			<br>
			<table border="1" width="100%">
				<tr>

					<th><input type="checkbox" id="selectall" />Select</th>
					<th>S No.</th>

					<th>College Name</th>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Date Of Birth</th>
					<th>Mobile No.</th>
					<th>Email ID</th>
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
					Iterator<StudentBean> it = list.iterator();

					while (it.hasNext()) {

						StudentBean bean = it.next();
				%>
				<tr>
					<td align="center"><input type="checkbox" class="ids"
						name="ids" value="<%=bean.getId()%>"></td>
					<td align="center"><%=index++%></td>

					<td align="center"><%=bean.getCollegeName()%></td>
					<td align="center"><%=bean.getFirstName()%></td>
					<td align="center"><%=bean.getLastName()%></td>
					<td align="center"><%=bean.getDob()%></td>
					<td align="center"><%=bean.getMobileNo()%></td>
					<td align="center"><%=bean.getEmail()%></td>
					<td align="center"><a
						href="StudentCtl.do?id=<%=bean.getId()%>">Edit</a></td>
				</tr>
				<%
					}
				%>
			</table>
			<table width="100%">
				<tr>
					<td>
						<%
							if (pageNo == 1) {
						%> <input type="submit" name="operation" disabled
						value="<%=StudentListCtl.OP_PREVIOUS%>"> <%
 	} else {
 %> <input type="submit" name="operation"
						value="<%=StudentListCtl.OP_PREVIOUS%>"> <%
 	}
 %>
					
					<td><input type="submit" name="operation"
						value="<%=StudentListCtl.OP_NEW%>"></td>
					<td><input type="submit" name="operation"
						value="<%=StudentListCtl.OP_DELETE%>"></td>



					<td align="right">
						<%
							if (list.size() < pageSize) {
						%> <input type="submit" name="operation" disabled
						value="<%=StudentListCtl.OP_NEXT%>"> <%
 	} else {
 %> <input type="submit" name="operation"
						value="<%=StudentListCtl.OP_NEXT%>"> <%
 	}
 %>
					</td>
				</tr>
			</table>
			<input type="hidden" name="pageNo" value="<%=pageNo%>"><input
				type="hidden" name="pageSize" value="<%=pageSize%>">


		</form>
	</center>
	<%@include file="Footer.jsp"%>
</body>
</html>