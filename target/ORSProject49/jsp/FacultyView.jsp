<%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.raystec.proj4.controller.FacultyCtl"%>
<%@page import="com.raystec.proj4.controller.BaseCtl"%>
<%@page import="com.raystec.proj4.util.DataUtility"%>
<%@page import="com.raystec.proj4.util.ServletUtility"%>
<%@page import="com.raystec.proj4.util.HTMLUtility"%>
<%@page import="java.util.List"%>

<html>
<head>
<title>Add Faculty</title>
</head>
<body>
	<form action="<%=ORSView.FACULTY_CTL%>" method="post">
		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="com.raystec.proj4.bean.FacultyBean"
			scope="request"></jsp:useBean>
		<%
			List ul = (List) request.getAttribute("userList");

			List l = (List) request.getAttribute("collegeList");
		%>

		<center>

			<%
				if (bean.getId() == 0)

				{
			%>
			<h1>Add Faculty</h1>

			<%
				} else {
			%>
			<h1>Update Faculty</h1>
			<%
				}
			%>



			<H2>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</H2>
			<H2>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font>
			</H2>
			<input type="hidden" name="id" value="<%=bean.getId()%>">

			<table>

				<tr>
					<th align="left">College Name*</th>

					<td><%=HTMLUtility.getList("collegeId",
					String.valueOf(bean.getCollegeId()), l)%><font color="red">
							<%=ServletUtility.getErrorMessage("collegeId", request)%>
					</font></td>



				</tr>
				<tr>
					<th align="left">Faculty Name*</th>
					<td><%=HTMLUtility.getList("userId",
					String.valueOf(bean.getUserId()), ul)%><font color="red"> <%=ServletUtility.getErrorMessage("userId", request)%>
					</font></td> 


				</tr> 

           







				
			
				<tr>
					<th align="left">Faculty Name*</th>
                    	<td>
						<%
							if (bean.getId() == 0) {
						%> <%=HTMLUtility.getList("userId",
						String.valueOf(bean.getUserId()), l)%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font
						color="red"> <%=ServletUtility.getErrorMessage("userId", request)%></font>
						<%
							} else {
						%> <label><%=bean.getFacultyName()%></label>
                        <input type="hidden" name="userId" value=<%=bean.getUserId()%>>
						<%
							}
						%>
         
                    </td>
                    </tr>
				
				
				
				
				<tr>
					<th></th>
					<td colspan="2"><input type="submit" name="operation"
						value="<%=FacultyCtl.OP_SAVE%>">&emsp; <%
 	if (bean.getId() > 0) {
 %> <input type="submit" name="operation"
						value="<%=FacultyCtl.OP_DELETE%>">&emsp; <input
						type="submit" name="operation" value="<%=FacultyCtl.OP_CANCEL%>">&emsp;<%
 	} else {
 %> <input type="submit" name="operation"
						value="<%=FacultyCtl.OP_CANCEL%>"> <%
 	}
 %></td>
				</tr>


			</table>
		</center>
	</form>
	<%@ include file="Footer.jsp"%>

</body>
</html> --%>
<%@page import="com.raystec.proj4.util.HTMLUtility"%>
<%@page import="java.util.List"%>
<%@page import="com.raystec.proj4.controller.FacultyCtl"%>
<%@page import="com.raystec.proj4.controller.BaseCtl"%>
<%@page import="com.raystec.proj4.util.DataUtility"%>
<%@page import="com.raystec.proj4.util.ServletUtility"%>
<html>
<body>
	<form action="<%=ORSView.FACULTY_CTL%>" method="post">
		<%@ include file="Header.jsp"%>


		<jsp:useBean id="bean" class="com.raystec.proj4.bean.FacultyBean"
			scope="request"></jsp:useBean>


		<%
			List l = (List) request.getAttribute("userList");
			List l1 = (List) request.getAttribute("collegeList");
		%>

		<center>
			<%
				if (bean.getId() == 0) {
			%>

			<h1>Add Faculty</h1>
			<%
				} else {
			%>
			<h1>Update Faculty</h1>
			<%
				}
			%>

			<H2>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</H2>
			<H2>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
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
					<th align="left">Faculty Name*</th>
					<td>
						<%
							if (bean.getId() == 0) {
						%> <%=HTMLUtility.getList("userId",
						String.valueOf(bean.getUserId()), l)%>&nbsp;<font
						color="red"> <%=ServletUtility.getErrorMessage("userId", request)%></font>
						<%
							} else {
						%> <label><%=bean.getFacultyName()%></label> <%--    <input type="text"  name="abc"   value=<%=bean.getFacultyName()%>> 
 --%> <input type="hidden" name="userId" value=<%=bean.getUserId()%>>
						<%
							}
						%>


					</td>
									</tr>
				
				

				<tr>
					<th align="left">College Name*</th>

					<td><%=HTMLUtility.getList("collegeId",
					String.valueOf(bean.getCollegeId()), l1)%>
						&nbsp;&nbsp;<font color="red">
							<%=ServletUtility.getErrorMessage("collegeId", request)%></font></td>
				</tr>
				<tr>
				</tr>
				<tr>
				</tr>
				
				<tr>
					<th></th>
					<td colspan="2"><input type="submit" name="operation"
						value="<%=FacultyCtl.OP_SAVE%>">&emsp; <%
 	if (bean.getId() > 0) {
 		
 %> 
                   <input type="submit" name="operation"
						value="<%=FacultyCtl.OP_DELETE%>">&emsp;
						<input type="submit" name="operation"
						value="<%=FacultyCtl.OP_CANCEL%>">
		
						
						
						 <%
 	} else {
 %> <input type="submit" name="operation"
						value="<%=FacultyCtl.OP_RESET%>">
						<%
						} %>
						</td>
				</tr>
			</table>
	</form>
	</center>
	<%@ include file="Footer.jsp"%>
</body>
</html>
