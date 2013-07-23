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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link type="image/x-icon" href="${staticDomain}/img/favicon.ico" rel="shortcut icon">
	<link type="text/css" rel="stylesheet" href="${staticDomain}/css/all.css"></link>
	<script type="text/javascript" src="${staticDomain}/js/jquery-1.8.2.js"></script>
	<script type="text/javascript" src="${staticDomain}/js/jquery-ui-1.9.1.custom.js"></script>
	<script type="text/javascript" src="${staticDomain}/js/md5.js"></script>
	<script type="text/javascript" src="${staticDomain}/js/content.js"></script>
	<title>影集网</title>
</head>
<body style="background:#F3F3F3">
<div id="wrapper">
	<div id="logo">
		<div style="float:left">
			<a href="http://www.baidu.com" class="logoA"><div class="logo logoPos"></div></a>
		</div>
		<div style="float:right">
			<div class="search searchExt" hoverClass="searchHover"><input class="searchText" /></div>
			<a href="javascript:goSearch();" class="searchIcon searchIconExt"></a>
		</div>
	</div>