<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.raystec.proj4.controller.TimetableCtl"%>
<%@page import="com.raystec.proj4.util.DataUtility"%>
<%@page import="com.raystec.proj4.util.ServletUtility"%>
<%@page import="com.raystec.proj4.bean.DropdownListBean"%>
<%@page import="com.raystec.proj4.util.HTMLUtility"%>
<%@page import="com.raystec.proj4.controller.BaseCtl"%>
<%@page import="java.util.List"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Time Table</title>
</head>
<body>
	<form action="<%=ORSView.TIMETABLE_CTL%>" method="post">
		<%@include file="Header.jsp"%>
		<script type="text/javascript" src="/ORS_Project4/js/calendar.js"></script>

		<jsp:useBean id="bean" class="com.raystec.proj4.bean.TimeTableBean"
			scope="request"></jsp:useBean>

		<%
			List ul = (List) request.getAttribute("facultyList");
			List cl = (List) request.getAttribute("courseList");
			
		%>

		<center>
			<%
				if (bean.getId() == 0) {
			%>
			<h1>Add Time Table</h1>
			<%
				} else {
			%>
			<h1>Update Time Table</h1>
			<%
				}
			%>
			<h2>
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
					<th align="left">Faculty *</th>
					<td><%=HTMLUtility.getList("userId",
					String.valueOf(bean.getUserId()), ul)%> <font color="red"><%=ServletUtility.getErrorMessage("userId", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Subject *</th>
					<td><%=HTMLUtility.getList("courseId",
					String.valueOf(bean.getCourseId()), cl)%> <font color="red"><%=ServletUtility.getErrorMessage("courseId", request)%></font></td>

				</tr>
 
     
 

				<tr>
					<th align="left">Day *</th>
					<td>
						<%
							LinkedHashMap mapd = new LinkedHashMap();
							mapd.put("Monday", "Monday");
							mapd.put("Tuesday", "Tuesday");
							mapd.put("Wednesday", "Wednesday");
							mapd.put("Thursday", "Thursday");
							mapd.put("Friday", "Friday");
							mapd.put("Saturday", "Saturday");
							String htmlListDay = HTMLUtility
									.getList("day", bean.getDay(), mapd);
						%> <%=htmlListDay%> <font color="red"> <%=ServletUtility.getErrorMessage("day", request)%></font>
                 </td>
           </tr>

				<tr>
					<th align="left">Time *</th>
					<td>
						<%
							LinkedHashMap map = new LinkedHashMap();
							map.put("07.00AM-08.00AM", "07.00AM-08.00AM");
							map.put("08.00AM-09.00AM", "08.00AM-09.00AM");
							map.put("09.00AM-10.00AM", "09.00AM-10.00AM");
							map.put("10.00AM-11.00AM", "10.00AM-11.00AM");
							map.put("11.00AM-12.00AM", "11.00AM-12.00AM");
							map.put("12.00AM-1.00PM", "12.00AM-1.00PM");
							map.put("2.00PM-3.00PM", "2.00PM-3.00PM");
							map.put("3.00PM-4.00PM", "3.00PM-4.00PM");
							map.put("4.00PM-5.00PM", "4.00PM-5.00PM");

							String htmlList = HTMLUtility.getList("time", bean.getTime(), map);
						%> <%=htmlList%> <font color="red"> <%=ServletUtility.getErrorMessage("time", request)%></font>

					</td>
				</tr>


				<tr>
					<th></th>
					<td colspan="2" align="left"><input type="submit"
						name="operation" value="<%=TimetableCtl.OP_SAVE%>"> <%
 	if (bean.getId() > 0) {
 %> &emsp;<input type="submit" name="operation"
						value="<%=TimetableCtl.OP_DELETE%>"> <%
 	}
 %>&emsp; <input type="submit" name="operation"
						value="<%=TimetableCtl.OP_CANCEL%>"></td>
				</tr>
			</table>
	</form>
	</center>
	<%@ include file="Footer.jsp"%>

</body>
</html>