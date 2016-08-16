<%@page import="com.raystec.proj4.controller.GetMarksheetCtl"%>
<%@page import="com.raystec.proj4.controller.BaseCtl"%>
<%@page import="com.raystec.proj4.util.DataUtility"%>
<%@page import="com.raystec.proj4.util.ServletUtility"%>
<html>

<body>
	<%@ include file="Header.jsp"%>

	<jsp:useBean id="bean" class="com.raystec.proj4.bean.MarksheetBean"
		scope="request">
	</jsp:useBean>

	<center>
		<h1>Get Marksheet</h1>

		<%--    <font  color="red"> <%=ServletUtility.getErrorMessage(request)%>
        </font>
       

        <H2>
            <font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
            </font> --%>
		<!--   </H2>
 -->
		<form action="<%=ORSView.GET_MARKSHEET_CTL%>">

			<input type="hidden" name="id" value="<%=bean.getId()%>">
			<table border="px" align="center">

				<label>RollNo : </label>
				<input type="text" name="rollNo"
					value="<%=ServletUtility.getParameter("rollNo", request)%>">&emsp;
				<input type="submit" name="operation"
					value="<%=GetMarksheetCtl.OP_GO%>">

				<br>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font>
				<font color="red"><%=ServletUtility.getErrorMessage("rollNo", request)%></font>

				<%
					if (bean.getRollNo() != null
							&& bean.getRollNo().trim().length() > 0) {
				%>

			&emsp;<tr align="center">
					<td>Rollno :</td>
					<td><%=DataUtility.getStringData(bean.getRollNo())%></td>
				</tr>
				<tr align="center">
					<td>Name :</td>
					<td><%=DataUtility.getStringData(bean.getName())%></td>
				</tr>
				<tr align="center">
					<td>Physics :</td>
					<td><%=DataUtility.getStringData(bean.getPhysics())%></td>
				</tr>
				<tr align="center">
					<td>Chemistry :</td>
					<td><%=DataUtility.getStringData(bean.getChemistry())%></td>
				</tr>
				<tr align="center">
					<td>Maths :</td>
					<td><%=DataUtility.getStringData(bean.getMaths())%></td>

				</tr>
				<tr>
					</td>
				</tr>
			</table>
			<%
				}
			%>
		</form>
		</centre>
		<%@ include file="Footer.jsp"%>
</body>
</html>