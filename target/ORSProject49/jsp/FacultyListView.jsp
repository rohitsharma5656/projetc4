<%@page import="com.raystec.proj4.controller.FacultyListCtl"%>
<%@page import="com.raystec.proj4.bean.FacultyBean"%>
<%@page import="com.raystec.proj4.controller.FacultyCtl"%>
<%@page import="com.raystec.proj4.controller.CollegeListCtl"%>
<%@page import="com.raystec.proj4.controller.CollegeListCtl"%>
<%@page import="com.raystec.proj4.util.ServletUtility"%>
<%@page import="com.raystec.proj4.bean.CourseBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<html>
<header>

	<script type="text/javascript" src="../js/jqueryui.min.js"></script>
	<script>
		function checkAll1(bx) {
			//alert("running javascript function ");

			var cbs = document.getElementsByClassName('chkReq1');
			//alert(cbs);
			for ( var i = 0; i < cbs.length; i++) {
				if (cbs[i].type == 'checkbox') {
					cbs[i].checked = bx.checked;
				}
			}
		}
	</script>


</header>


<body>
	<%@include file="Header.jsp"%>
	<center>
		<h1>Faculty List</h1>
		<H2>
			<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
			</font>
		</H2>

		<form action="<%=ORSView.FACULTY_LIST_CTL%>" method="post">
			<table width="100%">
				<tr>
					<td align="center"><label> Faculty Name :</label> <input
						type="text" name="facultyName"
						value="<%=ServletUtility.getParameter("facultyName", request)%>">&nbsp;

						<label>Login Id :</label> <input type="text" name="loginId"
						value="<%=ServletUtility.getParameter("loginId", request)%>">&nbsp;


						<input type="submit" name="operation"
						value="<%=FacultyCtl.OP_SEARCH%>"></td>
				</tr>
			</table>
			<br>
			<table border="1" width="100%">
				<tr>
					<th align="left"><input type="checkbox"
						onclick="checkAll1(this)">&nbsp;&nbsp;Select All</th>

					<th>S.No.</th>
					<th>Faculty</th>
					<th>Login Id</th>
					<th>Date Of Birth</th>
					<th>Gender</th>
					<th>College</th>

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
					Iterator it = list.iterator();

					while (it.hasNext()) {

						FacultyBean fbean = (FacultyBean) it.next();
						
						System.out.println("date and Time =="+fbean.getId());
				%>
				<tr>
					<td><input type="checkbox" class="chkReq1" name="ids"
						value="<%=fbean.getId()%>"></td>

					<td><%=index++%></td>
					<td><%=fbean.getFacultyName()%></td>
					<td><%=fbean.getLogin()%></td>
					<td><%=fbean.getDate()%></td>
					<td><%=fbean.getGender()%></td>
					<td><%=fbean.getName()%></td>
					<td><a href="FacultyCtl.do?id=<%=fbean.getId()%>">Edit</a></td>
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
						value="<%=FacultyListCtl.OP_PREVIOUS%>"> <%
 	} else {
 %> <input type="submit" name="operation"
						value="<%=FacultyListCtl.OP_PREVIOUS%>"> <%
 	}
 %>
					
					<td><input type="submit" name="operation"
						value="<%=FacultyListCtl.OP_NEW%>"></td>
						
					<td><input type="submit" name="operation"
						value="<%=FacultyListCtl.OP_DELETE%>"></td>



					<td align="right">
						<%
							if (list.size() < pageSize) {
						%> <input type="submit" name="operation" disabled
						value="<%=FacultyListCtl.OP_NEXT%>"> <%
 	} else {
 %> <input type="submit" name="operation"
						value="<%=FacultyListCtl.OP_NEXT%>"> <%
 	}
 %>
					</td>
				</tr>
			</table>
			<input type="hidden" name="pageNo" value="<%=pageNo%>"><input
				type="hidden" name="pageSize" value="<%=pageSize%>">


		</form>
	</center>
</body>
</html>