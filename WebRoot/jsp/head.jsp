<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>
<%
	String Scheme      = request.getScheme();
	String ServerName  = request.getServerName();
	int    ServerPort  = request.getServerPort();
	String contextPath = request.getContextPath();
	String webPath = Scheme+"://"+ServerName+(ServerPort==80?"":":"+ServerPort)+(contextPath.length()>0?contextPath:"");
	System.out.println(webPath);
	request.setAttribute("myDomain",webPath);
	request.setAttribute("staticDomain",webPath);
%>