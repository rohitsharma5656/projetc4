<%@page import="com.raystec.proj4.controller.MarksheetListCtl"%>
<%@page import="com.raystec.proj4.controller.BaseCtl"%>
<%@page import="com.raystec.proj4.controller.ORSView"%>
<%@page import="com.raystec.proj4.util.ServletUtility"%>
<%@page import="com.raystec.proj4.bean.MarksheetBean"%>
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
		<h1>Marksheet List</h1>
		<H2>
			<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
		</H2>
		<form action="<%=ORSView.MARKSHEET_LIST_CTL%>" method="post">

			<table width="100%">
				<tr>
					<td align="center"><label> Name  :</label> <input type="text"
						name="name"
						value="<%=ServletUtility.getParameter("name", request)%>">
						&emsp; <label>RollNo :</label> <input type="text" name="rollNo"
						value="<%=ServletUtility.getParameter("rollNo", request)%>">&emsp;
						<input type="submit" name="operation"
						value="<%=MarksheetListCtl.OP_SEARCH%>"></td>
				</tr>
			</table>
			<br>
			<table border="1" width="100%">
				<tr>
					<th><input type="checkbox" id="selectall" />Select</th>
					<th>S.NO</th>
					<th>RollNo</th>
					<th>Name</th>
					<th>Physics</th>
					<th>Chemistry</th>
					<th>Maths</th>
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
					Iterator<MarksheetBean> it = list.iterator();

					while (it.hasNext()) {

						MarksheetBean bean = it.next();
				%>
				<tr>
					<td  align="center"><input type="checkbox" class="ids" name="ids"
						value="<%=bean.getId()%>"></td>
					<td align="center"><%=bean.getId()%></td>
					<td align="center"><%=bean.getRollNo()%></td>
					<td align="center"><%=bean.getName()%></td>
					<td align="center"><%=bean.getPhysics()%></td>
					<td align="center"><%=bean.getChemistry()%></td>
					<td align="center"><%=bean.getMaths()%></td>
					<td  align="center"><a href="MarksheetCtl.do?id=<%=bean.getId()%>">Edit</a></td>
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
						value="<%=MarksheetListCtl.OP_PREVIOUS%>"> <%
 	} else {
 %> <input type="submit" name="operation"
						value="<%=MarksheetListCtl.OP_PREVIOUS%>"> <%
 	}
 %>
					</td>
					<%
						if (userBean.getRoleId() == RoleBean.ADMIN) {
					%>
					<td><input type="submit" name="operation"
						value="<%=MarksheetListCtl.OP_NEW%>"></td>

					<td><input type="submit" name="operation"
						value="<%=MarksheetListCtl.OP_DELETE%>"></td>
					<%
						}
					%>
					<td align="right">
						<%
							if (list.size() < pageSize) {
						%> <input type="submit" name="operation" disabled
						value="<%=MarksheetListCtl.OP_NEXT%>"> <%
 	} else {
 %> <input type="submit" name="operation"
						value="<%=MarksheetListCtl.OP_NEXT%>"> <%
 	}
 %>
					</td>
				</tr>
			</table>
			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">
		</form>
	</center>
	<%@include file="Footer.jsp"%>
</body>
</html>