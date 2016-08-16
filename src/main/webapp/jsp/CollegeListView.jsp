<%@page import="com.raystec.proj4.controller.CollegeListCtl"%>
<%@page import="com.raystec.proj4.util.ServletUtility"%>
<%@page import="com.raystec.proj4.bean.CollegeBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>

<html>

<header>
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

</header>

<body>
	<%@include file="Header.jsp"%>
	<center>
		<h1>College List</h1>
		<H2>
			<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
			</font>
		</H2>

		<form action="<%=ORSView.COLLEGE_LIST_CTL%>" method="POST">

			<table width="100%">
				<tr>
					<td align="center"><label>College Name :</label> <input
						type="text" name="name"
						value="<%=ServletUtility.getParameter("name", request)%>">&nbsp;
						<label>City :</label> <input type="text" name="city"
						value="<%=ServletUtility.getParameter("city", request)%>">&nbsp;
						&emsp;<input type="submit" name="operation"
						value="<%=CollegeListCtl.OP_SEARCH%>"></td>
				</tr>
			</table>
			<br>
			<table border="1" width="100%">
				<tr>
					<%
						if (userBean.getRoleId() == RoleBean.ADMIN) {
					%>
					<th  align="center"><input type="checkbox" id="selectall" />Select</th>
					<%
						}
					%>

					<th>S.No.</th>
					<th>College Name</th>
					<th>Address</th>
					<th>State</th>
					<th>City</th>
					<th>Phone Number</th>

					<%
						if (userBean.getRoleId() == RoleBean.ADMIN) {
					%>

					<th>Edit</th>
					<%
						}
					%>



				</tr>
				<tr>
					<td colspan="8"><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></td>
				</tr>
				<%
					int pageNo = ServletUtility.getPageNo(request);
					int pageSize = ServletUtility.getPageSize(request);
					int index = ((pageNo - 1) * pageSize) + 1;

					List list = ServletUtility.getList(request);
					Iterator<CollegeBean> it = list.iterator();

					while (it.hasNext()) {

						CollegeBean bean = it.next();
				%>
				<tr>
					<%
						if (userBean.getRoleId() == RoleBean.ADMIN) {
					%>
					<td  align="center"><input type="checkbox" class="ids" name="ids"
						value="<%=bean.getId()%>"></td>
					<%
						}
					%>

					<td align="center"><%=index++%></td>
					<td align="center"><%=bean.getName()%></td>
					<td align="center"><%=bean.getAddress()%></td>
					<td align="center"><%=bean.getState()%></td>
					<td align="center"><%=bean.getCity()%></td>
					<td align="center"><%=bean.getPhoneNo()%></td>
					<%
						if (userBean.getRoleId() == RoleBean.ADMIN) {
					%>


					<td><a href="CollegeCtl.do?id=<%=bean.getId()%>">Edit</a></td>
					<%
						}
					%>

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
						value="<%=CollegeListCtl.OP_PREVIOUS%>"> <%
 	} else {
 %> <input type="submit" name="operation"
						value="<%=CollegeListCtl.OP_PREVIOUS%>"> <%
 	}
 %>
					</td>
					<%
						if (userBean.getRoleId() == RoleBean.ADMIN) {
					%>

					<td><input type="submit" name="operation"
						value="<%=CollegeListCtl.OP_NEW%>"></td>


					<td><input type="submit" name="operation"
						value="<%=CollegeListCtl.OP_DELETE%>"></td>
					<%
						}
					%>
					<td align="right">
						<%
							if (list.size() < pageSize) {
						%> <input type="submit" name="operation" disabled
						value="<%=CollegeListCtl.OP_NEXT%>"> <%
 	} else {
 %> <input type="submit" name="operation"
						value="<%=CollegeListCtl.OP_NEXT%>"> <%
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