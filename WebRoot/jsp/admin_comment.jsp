<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<% pageContext.setAttribute("focusIndex", 4);  %>
<%@ include file="head.jsp" %>


<div class="adminWrap">
	<div class="adminLeft">
		<a href="${base}/admin_movie.do"  class="adminLeftA">影片管理</a>
		<a href="${base}/admin_module.do" class="adminLeftA">版块管理</a>
		<a href="${base}/admin_cmmt.do"   class="adminLeftA adminLeftAClick">留言管理</a>
		<a href="${base}/admin_search.do"   class="adminLeftA">搜索管理</a>
		<a href="${base}/admin_user.do"   class="adminLeftA">用户管理</a>
		<a href="${base}/admin_data.do"   class="adminLeftA">数据分析</a>
		<div class="clear"></div>
	</div>
	
	<div class="adminRight">
		<div class="adminPicContainer">
			<div class="adminPic" url="${base}/statComment.json?sort=user" interval="5000"></div>
			<p class="adminPicTitle">活跃留言用户排名</p>
		</div>
		<div class="adminPicContainer" style="margin-left:30px">
			<div class="adminPic" url="${base}/statComment.json?sort=movie" interval="5000"></div>
			<p class="adminPicTitle">活跃留言影片排名</p>
		</div>
		<div class="adminPicContainer">
			<div class="adminPic" url="${base}/statComment.json?sort=userMonth" interval="5000"></div>
			<p class="adminPicTitle">活跃留言用户排名（近一月）</p>
		</div>
		<div class="adminPicContainer" style="margin-left:30px">
			<div class="adminPic" url="${base}/statComment.json?sort=movieMonth" interval="5000"></div>
			<p class="adminPicTitle">活跃留言影片排名（近一月）</p>
		</div>
		<div class="adminTableContainer">
			<ul class="adminTableQuery" url="${base}/onlineCmmt.json">
				<li class="adminTableTitle">精选留言</li>
				<li class="adminLi">类型：<input name="type1" type="radio" value="" autocomplete="off" checked="checked"/> 全部 <input name="type1" type="radio" value="mv" autocomplete="off" /> 电影 <input name="type1" type="radio" value="tv" autocomplete="off"/> 电视剧</li>
				<li class="adminBtns"><input class="adminBtn" hoverClass="adminBtnHover" type="button" value="查询" onclick="adminQuery(this)"></input></li>
			</ul>
			<table class="adminTable">
				<thead><tr><th style="width:20px">id</th><th style="width:100px">用户昵称</th><th style="width:40px">地区</th><th style="width:80px">名称</th><th style="width:30px">类型</th><th>内容</th><th style="width:110px">时间</th><th style="width:80px">操作</th></tr></thead>
				<tbody>
					<c:set var="sceneCmmts" value="${onlineSceneCmmts}" />
					<%@ include file="admin_cmmt_online.jsp"  %>
				</tbody>
			</table>
		</div>
		<div class="adminTableContainer">
			<ul class="adminTableQuery" url="${base}/totalCmmt.json">
				<li class="adminTableTitle">所有留言</li>
				<li class="adminLi">I D：      <input class="adminInput" focusclass="adminInputFocus" type="text" name="commentid"></input></li>
				<li class="adminLi">影片I D：<input class="adminInput" focusclass="adminInputFocus" type="text" name="movieid"></input></li>
				<li class="adminLi">用户I D：<input class="adminInput" focusclass="adminInputFocus" type="text" name="userid"></input></li>
				<li class="adminLi">类型：    <input name="type2" type="radio" value="" autocomplete="off" checked="checked"/> 全部 <input name="type2" type="radio" value="mv" autocomplete="off" /> 电影 <input name="type2" type="radio" value="tv" autocomplete="off"/> 电视剧</li>
				<li class="adminBtns"><input class="adminBtn" hoverClass="adminBtnHover" type="button" value="查询" onclick="adminQuery(this)"></input></li>
			</ul>
			<table class="adminTable">
				<thead><tr><th style="width:20px">id</th><th style="width:100px">用户昵称</th><th style="width:40px">用户id</th><th style="width:40px">地区</th><th style="width:80px">名称</th><th style="width:40px">影片id</th><th style="width:30px">类型</th><th style="width:30px">下载</th><th>内容</th><th style="width:110px">时间</th><th style="width:65px">操作</th></tr></thead>
				<tbody>
					<c:set var="sceneCmmts" value="${totalSceneCmmts}" />
					<%@ include file="admin_cmmt_total.jsp"  %>
				</tbody>
			</table>
		</div>
	</div>
</div>

<%@ include file="foot.jsp" %>