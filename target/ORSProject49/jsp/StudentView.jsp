<%@page import="com.raystec.proj4.controller.StudentCtl"%>
<%@page import="com.raystec.proj4.util.HTMLUtility"%>
<%@page import="com.raystec.proj4.controller.StudentListCtl"%>
<%@page import="com.raystec.proj4.util.ServletUtility"%>
<%@page import="com.raystec.proj4.util.DataUtility"%>
<%@page import="com.raystec.proj4.bean.StudentBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<html>
<body>
	<form action="<%=ORSView.STUDENT_CTL%>" method="post">
		<%@ include file="Header.jsp"%>
		
		<script type="text/javascript" src="../js/calendar.js"></script>
		
		<jsp:useBean id="bean" class="com.raystec.proj4.bean.StudentBean"
			scope="request"></jsp:useBean>

		<%
			List l = (List) request.getAttribute("collegeList");
		%>

		<center>
			<%
				if (bean.getId() == 0)
            {
			%>

			<h1>Add Student</h1>
			<%
				} else {
			%>
			<h1>Update Student</h1>
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
					<th align="left">College*</th>
					<td><%=HTMLUtility.getList("collegeId",String.valueOf(bean.getCollegeId()), l)%>
					<font color="red"><%=ServletUtility.getErrorMessage("collegeId", request)%></font></td>

				</tr>
				<tr>
					<th align="left">First Name*</th>
					<td><input type="text" name="firstName" value="<%=DataUtility.getStringData(bean.getFirstName())%>"
						<%=(bean.getId() > 0)    %>><font color="red"> <%=ServletUtility.getErrorMessage("firstName", request)%></font>
					</td>
				</tr>
				<tr>
					<th align="left">Last Name*</th>
					<td><input type="text" name="lastName"
						value="<%=DataUtility.getStringData(bean.getLastName())%>"><font
						color="red"> <%=ServletUtility.getErrorMessage("lastName", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Date Of Birth*</th>
					<td><input type="text" name="dob" readonly="readonly" value="<%=DataUtility.getDateString(bean.getDob())%>"> 
					<a href="javascript:getCalendar(document.forms[0].dob);"> 
					<img src="../img/cal.jpg" width="16" height="15" border="0" alt="Calender">
					</a><font color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font></td>
				</tr>
				<tr>
					<th align="left">MobileNo*</th>
					<td><input type="text" name="mobileNo"
						value="<%=DataUtility.getStringData(bean.getMobileNo())%>"><font
						color="red"> <%=ServletUtility.getErrorMessage("mobileNo", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Email ID*</th>
					<td><input type="text" name="email"
						value="<%=DataUtility.getStringData(bean.getEmail())%>"><font
						color="red"> <%=ServletUtility.getErrorMessage("email", request)%></font></td>
				</tr>
				<th></th>
					<td colspan="2"><input type="submit" name="operation"
						value="<%=StudentCtl.OP_SAVE%>">&emsp; <%
 	if (bean.getId() > 0) {
 %>
						<input type="submit" name="operation"
						value="<%=StudentCtl.OP_DELETE%>">&emsp; <input
						type="submit" name="operation" value="<%=StudentCtl.OP_CANCEL%>">


						<%
							} else {
						%> <input type="submit" name="operation"
						value="<%=StudentCtl.OP_CANCEL%>"> <%
 	}
 %></td>
				</tr>
			


			<%-- 	<tr>
					<th></th>
	<td colspan="2"><input type="submit" name="operation" value="<%=StudentCtl.OP_SAVE%>"> 
    <%
 	if (bean.getId() > 0) 
 	{
    %> 
    &emsp;<input type="submit" name="operation" value="<%=StudentCtl.OP_DELETE%>"> 
    <%
 	}
    %>
    &emsp; <input type="submit" name="operation" value="<%=StudentCtl.OP_CANCEL%>">
    </td>
				</tr> --%>
			</table>
	</form>
	</center>
	<%@ include file="Footer.jsp"%>
</body>
</html>