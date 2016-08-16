<%@page import="com.raystec.proj4.util.ServletUtility"%>
<%@page import="com.raystec.proj4.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Error_View</title>
</head>
<body>

        <td width="90%" ><a href="<%=ORSView.WELCOME_CTL%>">Welcome</b></a> 
</td>
            <H2>
                <font  color="red"> <%=ServletUtility.getErrorMessage(request)%>
                </font>
            </H2>


<h1 align="Center">
		<img src="<%=ORSView.APP_CONTEXT%>/img/error3.jpg" width="918" height="627" border="0">
	</h1>


</body>
</html>