<%@page import="com.raystec.proj4.controller.TimetableListCtl"%>
<%@page import="com.raystec.proj4.controller.BaseCtl"%>
<%@page import="com.raystec.proj4.util.ServletUtility"%>
<%@page import="com.raystec.proj4.bean.TimeTableBean"%>
<%@page import="com.raystec.proj4.bean.UserBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<html>

<header>
<script type="text/javascript" src="../js/jqueryui.min.js"></script>
<script language="javascript">
function checkAll(master){
var checked = master.checked;
var col = document.getElementsByTagName("INPUT");
for (var i=0;i<col.length;i++) {
col[i].checked= checked;
}
}
</script>


</header>

<body>
    <%@include file="Header.jsp"%>
    <center>
        <h1>Time Table List</h1>
         	<H2>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</H2>
        <form action="<%=ORSView.TIMETABLE_LIST_CTL%>" method="POST">

            <table width="100%">
                <tr>
                    <td align="center"><label> Faculty Name :</label> <input type="text"
                        name="firstName" value="<%=ServletUtility.getParameter("facultyName", request)%>">
                        &emsp;
                         <label> Subject :</label> <input type="text" name="subject"
                        value="<%=ServletUtility.getParameter("subject", request)%>">
                         
                        &emsp;
                        <input type="submit" name="operation" value="<%=TimetableListCtl.OP_SEARCH %>"></td>
                </tr>
            </table>
            <br>
            <table border="1" width="100%">
            <%UserBean uBean = (UserBean) session.getAttribute("user"); %>
                <tr>
                <%if(uBean.getRoleId()==2) {%>
                    <th>S.No.</th>
                     <th>Faculty Name</th>
                    <th>Course</th>
                  <th>Subject</th>
                  <th>Day</th>
                   
                    <th>Time</th>
                     
                    <%} %>
                    <%if(uBean.getRoleId()==3) {%>
                    <th align="left"><input type="checkbox" onclick="checkAll(this)">&nbsp;&nbsp;SelectAll</th>
                    <th>S.No.</th>
                    <th>Faculty Name</th>
                    <th>Course</th>
                  <th>Subject</th>
                   <th>Day</th>
                    <th>Time</th>
                    <th>Edit</th>
                    <%} %>
                    <%if(uBean.getRoleId()==5) {%>
                    <th>S.No.</th>
                    <th>Faculty Name</th>
                    <th>Course</th>
                    <th>Subject</th>
                    <th>Day</th>
                    <th>Time</th>
                    <%} %>
                    <%if(uBean.getRoleId()==1) {%>
                    <th align="left"><input type="checkbox" onclick="checkAll(this)">&nbsp;&nbsp;SelectAll</th>
                    
                    <th>S.No.</th>
                    <th>Faculty Name</th>
                    <th>Course</th>
                  <th>Subject</th>
                   <th>Day</th>
                   
                    <th>Time</th>
                    <th>Edit</th>
                    <%} %>
                    
           
       <tr>
			<td colspan="8"><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></td>
		</tr>                    
                 
                
                <!-- <tr>
                    <td colspan="7"></td>
                </tr> -->
<tr> 
                <%
                    int pageNo = ServletUtility.getPageNo(request);
                    int pageSize = ServletUtility.getPageSize(request);
                    int index = ((pageNo - 1) * pageSize) + 1;

                    List list = ServletUtility.getList(request);
                    Iterator<TimeTableBean> it = list.iterator();

                    while (it.hasNext()) {

                        TimeTableBean bean = it.next();
                %>
                <tr>
                <%if(uBean.getRoleId()==2) {%>
                    <td><%=index++%></td>
                  
                    <td><%=bean.getFirstName()%></td>
                    <td><%=bean.getCourseName()%></td>
                     <td><%=bean.getSubject() %></td>
                     <td><%=bean.getDay() %></td>
                   <td><%=bean.getTime() %></td>
                     
                    <%} %>
                    <%if(uBean.getRoleId()==3) {%>
                    <td><input type="checkbox" class="chkReq1" name="ids" value="<%=bean.getId()%>"></td>
                    <td><%=index++%></td>
                  
                    <td><%=bean.getFirstName()%></td>
                    <td><%=bean.getCourseName()%></td>
                     <td><%=bean.getSubject()%></td>
                     <td><%=bean.getDay()%></td>
                   <td><%=bean.getTime() %></td>
                    <td><a href="TimetableCtl.do?id=<%=bean.getId()%>">Edit</a></td>
                <%} %>
                <%if(uBean.getRoleId()==5) {%>
                    
                    <td><%=index++%></td>
                    
                    <td><%=bean.getFirstName()%></td>
                    <td><%=bean.getCourseName()%></td>
                      <td><%=bean.getSubject()%></td>
                       <td><%=bean.getDay()%></td>
                   <td><%=bean.getTime() %></td>
                                    <%} %>
                    <%if(uBean.getRoleId()==1) {%>
                    <td><input type="checkbox" class="chkReq1" name="ids" value="<%=bean.getId()%>"></td>
                    <td><%=index++%></td>
                    
                    <td><%=bean.getFirstName()%></td>
                    <td><%=bean.getCourseName()%></td>
                      <td><%=bean.getSubject()%></td>
                       <td><%=bean.getDay()%></td>
                   <td><%=bean.getTime() %></td>
                    <td><a href="TimetableCtl.do?id=<%=bean.getId()%>">Edit</a></td>
                <%} %>
                </tr>

                <%
                    }
                %>
            </table>
            <table width="100%">
                <tr>
                    <td>
                    <%if(pageNo==1){ %>
                    <input type="submit" name="operation" disabled value="<%=TimetableListCtl.OP_PREVIOUS%>">
                    <%}else{ %>
                    <input type="submit" name="operation" value="<%=TimetableListCtl.OP_PREVIOUS%>">
                    <%} %>
                    </td>
                    <%if(uBean.getRoleId()==1) {%>
                    <td><input type="submit" name="operation" value="<%=TimetableListCtl.OP_NEW%>"></td>
                    <td><input type="submit"name="operation" value="<%=TimetableListCtl.OP_DELETE%>"></td>
                    <%} %>
                    <td align="right">
                      <%if(list.size()<pageSize){ %>
                    <input type="submit" name="operation" disabled value="<%=TimetableListCtl.OP_NEXT%>">
                    <%}else{ %>
                    <input type="submit" name="operation" value="<%=TimetableListCtl.OP_NEXT%>">
                    <%} %>
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