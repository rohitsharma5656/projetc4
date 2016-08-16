<%@page import="com.raystec.proj4.controller.LoginCtl"%>
<%@page import="com.raystec.proj4.bean.RoleBean"%>
<%@page import="com.raystec.proj4.controller.ORSView"%>
<%@page import="com.raystec.proj4.bean.UserBean"%>

<html>
<body>


	<%
		UserBean userBean = (UserBean) session.getAttribute("user");

		boolean userLoggedIn = userBean != null;

		String welcomeMsg = "Hi, ";

		if (userLoggedIn) {
			String role = (String) session.getAttribute("role");
			welcomeMsg += userBean.getFirstName() + " (" + role + ")";
		} else {
			welcomeMsg += "Guest";
		}
	%>

	<table width="100%" border="0">
		<tr>
			<td width="90%"><a href="<%=ORSView.WELCOME_CTL%>">Welcome</b></a> |
				<%
				if (userLoggedIn) {
			%> <a
				href="<%=ORSView.LOGIN_CTL%>?operation=<%=LoginCtl.OP_LOG_OUT%>">Logout</b></a>

				<%
					} else {
				%> <a href="<%=ORSView.LOGIN_CTL%>">Login</b></a> <%
 	}
 %></td>
			<td rowspan="2">
				<h1 align="Right">
					<img src="<%=ORSView.APP_CONTEXT%>/img/customLogo.png" width="318"
						height="90">
				</h1>
			</td>

		</tr>

		<tr>
			<td>
				<h3>
					<%=welcomeMsg%></h3>
			</td>
		</tr>


		<%
			if (userLoggedIn) {
		%>

		<tr>
			<td colspan="2"><a href="<%=ORSView.MY_PROFILE_CTL%>">My
					Profile</b>
			</a> | <a href="<%=ORSView.CHANGE_PASSWORD_CTL%>">Change Password</b></a> | <%--  <a href="<%=ORSView.JAVA_DOC_VIEW%>">Java Doc</b></a> | --%>
				<a href="<%=ORSView.GET_MARKSHEET_CTL%>">Get Marksheet</b></a> | <a
				href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>">Marksheet Merit
					List</b>
			</a> | 
			
			<%
				if (userBean.getRoleId() == RoleBean.ADMIN) {
			%> 
			      <a href="<%=ORSView.MARKSHEET_CTL%>">Add Marksheet</b></a> | <a
				href="<%=ORSView.MARKSHEET_LIST_CTL%>">Marksheet List</b></a> | <a
				href="<%=ORSView.USER_CTL%>">Add User</b></a> | <a
				href="<%=ORSView.JAVA_DOC_VIEW%>" target="_blank">Java Doc</b></a> | <a
				href="<%=ORSView.USER_LIST_CTL%>">User List</b></a> | <a
				href="<%=ORSView.COLLEGE_CTL%>">Add College</b></a> | <a
				href="<%=ORSView.COLLEGE_LIST_CTL%>">College List</b></a> | <a
				href="<%=ORSView.STUDENT_CTL%>">Add Student</b></a> | <a
				href="<%=ORSView.STUDENT_LIST_CTL%>">Student List</b></a> | <a
				href="<%=ORSView.COURSE_CTL%>">Add Course</b></a> | <a
				href="<%=ORSView.COURSE_LIST_CTL%>">Course List</b></a> | <a
				href="<%=ORSView.FACULTY_CTL%>">Add Faculty</b></a> | <a
				href="<%=ORSView.FACULTY_LIST_CTL%>">Faculty List</b></a> | <a
				href="<%=ORSView.TIMETABLE_CTL%>">Add TimeTable</b></a> | <a
				href="<%=ORSView.TIMETABLE_LIST_CTL%>">TimeTable List</b></a> | <a
				href="<%=ORSView.ROLE_CTL%>">Add Role</b></a> | <a
				href="<%=ORSView.ROLE_LIST_CTL%>">Role List</b></a> | <%
 	}
 %> <%
 	if (userBean.getRoleId() == RoleBean.COLLEGE_SCHOOL) {
 %> <a href="<%=ORSView.MARKSHEET_LIST_CTL%>">Marksheet List</b></a> | <a
				href="<%=ORSView.COLLEGE_LIST_CTL%>">College List</b></a> | <a
				href="<%=ORSView.STUDENT_CTL%>">Add Student</b></a> | <a
				href="<%=ORSView.STUDENT_LIST_CTL%>">Student List</b></a> | <a
				href="<%=ORSView.COURSE_LIST_CTL%>">Course List</b></a> | <a
				href="<%=ORSView.FACULTY_CTL%>">Add Faculty</b></a> | <a
				href="<%=ORSView.FACULTY_LIST_CTL%>">Faculty List</b></a> | | <a
				href="<%=ORSView.TIMETABLE_LIST_CTL%>">TimeTable List</b></a> | <%
 	}
 %> <%
 	if (userBean.getRoleId() == RoleBean.FACULTY) {
 %> | <a href="<%=ORSView.TIMETABLE_LIST_CTL%>">TimeTable List</b>
			</a> | | <%
				}
			%></td>

		</tr>
		<%
			}
		%>
	</table>
	<hr>
<body>