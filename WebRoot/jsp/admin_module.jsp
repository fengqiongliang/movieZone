<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<% pageContext.setAttribute("focusIndex", 4);  %>
<%@ include file="head.jsp" %>

<div class="adminWrap">
	<div class="adminLeft" style="height:12710px;_height:12900px">
		<a href="${base}/admin_movie.do"  class="adminLeftA">影片管理</a>
		<a href="${base}/admin_module.do" class="adminLeftA adminLeftAClick">版块管理</a>
		<a href="${base}/admin_cmmt.do"   class="adminLeftA">留言管理</a>
		<a href="${base}/admin_search.do"   class="adminLeftA">搜索管理</a>
		<a href="${base}/admin_user.do"   class="adminLeftA">用户管理</a>
		<a href="${base}/admin_data.do"   class="adminLeftA">数据分析</a>
		<div class="clear"></div>
	</div>
	
	<div class="adminRight" style="height:12720px;_height:12910px">
		<div class="adminPicContainer">
			<div class="adminPic" url="${base}/statModule.json" interval="5000"></div>
			<p class="adminPicTitle">版块浏览量</p>
		</div>
		<div class="clear"></div>
		
		<c:forEach var="module"  items="${modules}"  varStatus="status">
			<div class="moduleTables">
				<h1 class="moduleTitle">${module['modname']}</h1>
				<table class="adminTable" style="float:left;width:335px" url="${base}/admin_module_online.json?modname=${module['modname']}">
					<thead><tr class="evenTr"><th style="width:20px">id</th><th style="width:120px">名称</th><th style="width:55px">浏览</th><th style="width:45px">下载</th><th style="width:85px;">操作</th></tr></thead>
					<tbody>
						<c:set var="movies" value="${module['onlineMovies']}" />
						<%@ include file="admin_module_online.jsp"  %>
					</tbody>
				</table>
				<div style="float:left;font-size:20px;font-family:'微软雅黑'"><-</div>
				<table class="adminTable" style="float:left;width:490px" url="${base}/admin_module_offline.json?modname=${module['modname']}">
					<thead><tr class="evenTr"><th style="width:20px">id</th><th style="width:120px">名称</th><th style="width:55px">浏览</th><th style="width:45px">下载</th><th style="width:110px">上线时间</th><th style="width:100px">还有</th><th style="width:35px">操作</th></tr></thead>
					<tbody>
						<c:set var="movies" value="${module['offlineMovies']}" />
						<%@ include file="admin_module_offline.jsp"  %>
					</tbody>
				</table>
				<div class="clear"></div>
			</div>
		</c:forEach>
		
	</div>
</div>


<%@ include file="foot.jsp" %>