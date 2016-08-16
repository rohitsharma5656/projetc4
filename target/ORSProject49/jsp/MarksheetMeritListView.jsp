<%@page import="com.raystec.proj4.controller.MarkSheetMeritListCtl"%>
<%@page import="com.raystec.proj4.util.ServletUtility"%>
<%@page import="com.raystec.proj4.bean.MarksheetBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<html>
<body>
	<%@include file="Header.jsp"%>
	<center>
		<h1>Marksheet Merit List</h1>

		<form action="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>" method="POST">
			<br>
			<table border="1" width="100%">
				<tr>

					<th>S.No.</th>
					<th>Roll No</th>
					<th>Name</th>
					<th>Physics</th>
					<th>Chemistry</th>
					<th>Maths</th>
					<th>Total</th>
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

					<td align="center"><%=index++%></td>
					<td align="center"><%=bean.getRollNo()%></td>
					<td align="center"><%=bean.getName()%></td>
					<td align="center"><%=bean.getPhysics()%></td>
					<td align="center"><%=bean.getChemistry()%></td>
					<td align="center"><%=bean.getMaths()%></td>
					<td align="center"><%=bean.getPhysics() + bean.getChemistry()+ bean.getMaths()%></td>
				</tr>

				<%
					}
				%>
			</table>
			<table width="100%">
				<tr>

					<td align="center"><input type="submit" name="operation"
						value="<%=MarkSheetMeritListCtl.OP_BACK%>"></td>
				</tr>
			</table>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">
		</form>
	</center>
	<%@include file="Footer.jsp"%>
</body>
</html>