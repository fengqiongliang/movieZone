<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<% pageContext.setAttribute("focusIndex", 4);  %>
<%@ include file="head.jsp" %>


<div class="adminWrap">
	<div class="adminLeft">
		<a href="${base}/admin_movie.do"  class="adminLeftA adminLeftAClick">影片管理</a>
		<a href="${base}/admin_module.do" class="adminLeftA">版块管理</a>
		<a href="${base}/admin_user.do"   class="adminLeftA">用户管理</a>
		<a href="${base}/admin_data.do"   class="adminLeftA">数据分析</a>
		<div class="clear"></div>
	</div>
	
	<div class="adminRight">
		<div class="adminPicContainer">
			<div class="adminPic" url="${base}/statMovie.json?sort=broswer" interval="1000"></div>
			<p class="adminPicTitle">按浏览量进行排名</p>
		</div>
		<div class="adminPicContainer" style="margin-left:30px">
			<div class="adminPic" url="${base}/statMovie.json?sort=down" interval="1000"></div>
			<p class="adminPicTitle">按下载量进行排名</p>
		</div>
		<div class="adminPicContainer">
			<div class="adminPic" url="${base}/statMovie.json?sort=approve" interval="1000"></div>
			<p class="adminPicTitle">按点赞数进行排名</p>
		</div>
		<div class="adminPicContainer" style="margin-left:30px">
			<div class="adminPic" url="${base}/statMovie.json?sort=comment" interval="100000"></div>
			<p class="adminPicTitle">按留言数进行排名</p>
		</div>
		<div class="adminTableContainer">
			<ul class="adminTableQuery" url="${base}/offlineMovies.json">
				<li class="adminTableTitle">队列上线</li>
				<li class="adminLi">I D：<input class="adminInput" focusclass="adminInputFocus" type="text" name="id"></input> 名称：<input class="adminInput" focusclass="adminInputFocus" type="text" name="name"></input></li>
				<li class="adminLi">类型：<input onclick="checkType(this)" type="checkbox" value="mv" autocomplete="off"/> 电影 <input onclick="checkType(this)" type="checkbox" value="tv" autocomplete="off"/> 电视剧<span name="type" class="checkLabel" style="margin-left:276px;display:none;width:230px"></span><a href="javascript:void(0)" onclick="delQuery(this)" style="font-size:12px;display:none">删除</a></li>
				<li class="adminBtns"><input class="adminBtn" hoverClass="adminBtnHover" type="button" value="查询" onclick="adminQuery(this)"></input><input class="adminBtn" hoverClass="adminBtnHover" type="button" value="添加" onclick="openWnd('${base}/admin_movieAction.json')"></input></li>
			</ul>
			<table class="adminTable">
				<thead><tr><th style="width:20px">id</th><th>名称</th><th style="width:55px">类型</th><th style="width:45px">评分</th><th style="width:110px">上线时间</th><th style="width:100px">还有</th><th style="width:110px">作用版块</th><th style="width:75px">操作</th></tr></thead>
				<tbody>
					<c:set var="wrappers" value="${offlineMovies}" />
					<%@ include file="admin_movie_offline.jsp"  %>
				</tbody>
			</table>
		</div>
		<div class="adminTableContainer">
			<ul class="adminTableQuery" url="${base}/onlineMovies.json">
				<li class="adminTableTitle">线上影片</li>
				<li class="adminLi">I D：<input class="adminInput" focusclass="adminInputFocus" type="text" name="id"></input> 名称：<input class="adminInput" focusclass="adminInputFocus" type="text" name="name"></input></li>
				<li class="adminLi">类型：<input onclick="checkType(this)" type="checkbox" value="mv" autocomplete="off"/> 电影 <input onclick="checkType(this)" type="checkbox" value="tv" autocomplete="off"/> 电视剧<span name="type" class="checkLabel" style="margin-left:276px;display:none;width:230px"></span><a href="javascript:void(0)" onclick="delQuery(this)" style="font-size:12px;display:none">删除</a></li>
				<!--  <li class="adminLi">排序：<input onclick="checkType(this)" type="checkbox" value="time" autocomplete="off"/> 时间 <input onclick="checkType(this)" type="checkbox" value="score" autocomplete="off"/> 评分 <input onclick="checkType(this)" type="checkbox" value="favorite" autocomplete="off"/> 收藏 <input onclick="checkType(this)" type="checkbox" value="down" autocomplete="off"/> 下载 <input onclick="checkType(this)" type="checkbox" value="browse" autocomplete="off"/> 浏览 <input onclick="checkType(this)" type="checkbox" value="good" autocomplete="off"/> 赞数 <input onclick="checkType(this)" type="checkbox" value="comment" autocomplete="off"/> 留言<span name="sort" class="checkLabel" style="margin-left:50px;display:none;width:230px"></span><a href="javascript:void(0)" onclick="delQuery(this)" style="font-size:12px;display:none">删除</a></li>-->
				<li class="adminBtns"><input class="adminBtn" hoverClass="adminBtnHover" type="button" value="查询" onclick="adminQuery(this)"></input><input class="adminBtn" hoverClass="adminBtnHover" type="button" value="添加" onclick="openWnd('${base}/admin_movieAction.json')"></input></li>
			</ul>
			<table class="adminTable">
				<thead><tr><th style="width:20px">id</th><th>名称</th><th style="width:55px">类型</th><th style="width:45px">评分</th><th style="width:45px">收藏</th><th style="width:45px">下载</th><th style="width:45px">浏览</th><th style="width:45px">赞数</th><th style="width:45px">留言</th><th style="width:110px">上线时间</th><th style="width:110px">作用版块</th><th style="width:75px">操作</th></tr></thead>
				<tbody>
					<c:set var="wrappers" value="${onlineMovies}" />
					<%@ include file="admin_movie_online.jsp"  %>
				</tbody>
			</table>
		</div>
	</div>
</div>

<%@ include file="foot.jsp" %>