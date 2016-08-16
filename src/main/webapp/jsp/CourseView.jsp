<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.Map"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="com.raystec.proj4.controller.CourseListCtl"%>
<%@page import="com.raystec.proj4.controller.CourseCtl"%>
<%@page import="com.raystec.proj4.controller.BaseCtl"%>
<%@page import="com.raystec.proj4.util.DataUtility"%>
<%@page import="com.raystec.proj4.util.ServletUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.raystec.proj4.util.HTMLUtility"%>
<html>
<body>
	<%@ include file="Header.jsp"%>
	<center>

		<form action="<%=ORSView.COURSE_CTL%>" method="post">

			<jsp:useBean id="bean" class="com.raystec.proj4.bean.CourseBean"
				scope="request"></jsp:useBean>

			<%
				if (bean.getId() > 0) {
			%>

			<h1>Update Course</h1>
			<%
				} else {
			%>
			<h1>Add Course</h1>
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
					<th align="left">Course Name*</th>
					<td>
						<%
							if (bean.getId() == 0) {
						%> <%
 	HashMap map = new HashMap();
 		map.put("Hadoop", "Hadoop");
 		map.put("BE", "BE");
 		map.put("BCA", "BCA");
 		map.put("BCOM", "BCOM");
 		map.put("MCA", "MCA");
 		map.put("MBA", "MBA");

 		/* String htmlList = HTMLUtility
 				.getListVal("course", bean.getName(), map); */

 		String htmlList = HTMLUtility.getList("name", bean.getName(),
 				map);
 %> <%=htmlList%>&nbsp;&nbsp;<font color="red"> <%=ServletUtility.getErrorMessage("name", request)%></font>
						<%
							} else {
						%><%-- <label ><%=bean.getName() %></label> --%> <input
						readonly="readonly" name="name" value=<%=bean.getName()%>></br>
						<%
							}
						%>

					</td>
				</tr>


				<tr>
					<th align="left">Subject *</th>
					<td><input type="text" name="subject"
						value="<%=DataUtility.getStringData(bean.getSubject())%>"><font
						color="red"> <%=ServletUtility.getErrorMessage("subject", request)%></font></td>
				</tr>
				<%-- <tr>
					<th align="left">Subject Code*</th>
					<td><input type="text" name="duration"
						value="<%=DataUtility.getStringData(bean.getSubjectCode())%>"><font
						color="red"> <%=ServletUtility.getErrorMessage("duration", request)%></font></td>
				</tr> --%>
				<tr>
					<th align="left">Description*</th>
					<td><textarea rows="3" cols="22" name="description"><%=DataUtility.getStringData(bean.getDescription())%></textarea>
						<font color="red"> <%=ServletUtility.getErrorMessage("description", request)%></font></td>
				</tr>

				<tr>
					<th></th>

					<td colspan="2"><input type="submit" name="operation"
						value="<%=CourseCtl.OP_SAVE%>">&emsp; <%
 	if (bean.getId() > 0) {
 %><input type="submit" name="operation"
						value="<%=CourseCtl.OP_DELETE%>">&emsp; <input
						type="submit" name="operation" value="<%=CourseCtl.OP_CANCEL%>">&emsp;<%
 	} else {
 %> <input type="submit" name="operation"
						value="<%=CourseCtl.OP_CANCEL%>"> <%
 	}
 %></td>

				</tr>
			</table>


		</form>
	</center>
	<%@ include file="Footer.jsp"%>
</body>
</html>
