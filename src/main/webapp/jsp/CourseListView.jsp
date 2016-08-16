<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.raystec.proj4.controller.CourseCtl"%>
<%@page import="com.raystec.proj4.bean.CourseBean"%>
<%@page import="com.raystec.proj4.controller.CourseListCtl"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.raystec.proj4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

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

</head>
<body>
 <%@include file="Header.jsp"%>

    <center>
        <h1>Course List </h1>
        <H2>
	             <font color="green"><%=ServletUtility.getSuccessMessage(request) %></font>
		</H2>

        <form action="<%=ORSView.COURSE_LIST_CTL%>" method="post">
        
            <table width="100%">
                <tr>
                    <td align="center"><label>Course Name :</label> <input type="text"
                        name="name"
                        value="<%=ServletUtility.getParameter("name", request)%>">
                       <!-- <label>Subject Code *:</label> -->
                      <%--  <input type="text" name="subjectcode" 
                       value="<%=ServletUtility.getParameter("subject", request) %>">&nbsp; --%>
                         &nbsp; <input type="submit" name="operation" value="<%=CourseListCtl.OP_SEARCH %>">
                   </td>
                </tr>
            </table>
             &nbsp;<table border="1" width="100%">
                <tr>
              <%  if (userBean.getRoleId() == RoleBean.ADMIN)
                    {
                %>  
                
                <th align="center"><input type="checkbox" id="selectall" />Select All</th>
                
               <%} %>
                   
                    <th>S.No.</th>
                    <!-- <th>Id</th> -->
                    <th>Course Name</th>
                    <th>Subject</th>
                  <!--  <th>Subject Code</th> -->
                     <th>Description</th>
<%  if (userBean.getRoleId() == RoleBean.ADMIN)
            {
       %>

                    <th>Edit</th>
          <%} %>       
                </tr>
                <tr>
                    <td colspan="8"><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></td>
                </tr>

                <%
                    int pageNo = ServletUtility.getPageNo(request);            	
                    int pageSize = ServletUtility.getPageSize(request);
                    int index = ((pageNo - 1) * pageSize) + 1;

                    List list = ServletUtility.getList(request);
                    Iterator<CourseBean> it = list.iterator();
                    while (it.hasNext()) {
                        CourseBean bean = it.next();
                %>
                <tr align="center">
          <%  if (userBean.getRoleId() == RoleBean.ADMIN)
                    {
                	   %>      
                
                <td><input type="checkbox" class="ids" name="ids"
						value="<%=bean.getId()%>"></td>
						
			<%} %> 			
						
                    <td><%=index++%></td>
                  <%--   <td><%=bean.getId()%></td> %> --%>
                    <td><%=bean.getName()%></td>
                    <td><%=bean.getSubject() %></td>
                   <%--  <td><%=bean.getSubjectCode()%></td> --%>
                    <td><%=bean.getDescription()%></td>
                 <%  if (userBean.getRoleId() == RoleBean.ADMIN)
                    {
                   %>    
                    
                <td><a href="<%=ORSView.COURSE_CTL%>?id=<%=bean.getId()%>">Edit</a></td>
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
                     <%if(pageNo==1){ %>
                    
                    <input type="submit" name="operation"
                       disabled value="<%=CourseListCtl.OP_PREVIOUS%>">
                         <%
                } else{
                        %>
                        
                         <input type="submit" name="operation"
                        value="<%=CourseListCtl.OP_PREVIOUS%>">
                         <%} %>
                         </td>
                          <%  if (userBean.getRoleId() == RoleBean.ADMIN)
                    {
                    	%>
                        
                     <td> <input type="submit" name="operation"
                        value="<%=CourseListCtl.OP_NEW%>"></td> 
                       <td> <input type="submit" name="operation"
                        value="<%=CourseListCtl.OP_DELETE%>"></td>
                         <%
                    }
                     %>
                        
                        
                        
                    <td align="right">
                    <%if(list.size()<pageSize){ %>
                    
                    <input type="submit" name="operation"
                       disabled value="<%=CourseListCtl.OP_NEXT%>">
                            <%
                } else{
                        %>
                        
                        
                        <input type="submit" name="operation"
                        value="<%=CourseListCtl.OP_NEXT%>">
                        
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