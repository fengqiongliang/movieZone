<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<% pageContext.setAttribute("focusIndex", 4);  %>
<%@ include file="head.jsp" %>


<div class="adminWrap">
	<div class="adminLeft">
		<a href="${base}/admin_movie.do"  class="adminLeftA">影片管理</a>
		<a href="${base}/admin_module.do" class="adminLeftA">版块管理</a>
		<a href="${base}/admin_cmmt.do"   class="adminLeftA">留言管理</a>
		<a href="${base}/admin_search.do"   class="adminLeftA">搜索管理</a>
		<a href="${base}/admin_user.do"   class="adminLeftA adminLeftAClick">用户管理</a>
		<a href="${base}/admin_data.do"   class="adminLeftA">数据分析</a>
		<div class="clear"></div>
	</div>
	
	<div class="adminRight">
		<div class="adminPicContainer">
			<div class="adminPic" url="${base}/statArea.json" interval="5000"></div>
			<p class="adminPicTitle">地区用户</p>
		</div>
		<div class="adminPicContainer" style="margin-left:30px">
			<div class="adminPic" url="${base}/statActiveUser.json" interval="5000"></div>
			<p class="adminPicTitle">活跃用户</p>
		</div>
		<div class="adminTableContainer">
			<ul class="adminTableQuery" url="${base}/adminForbitUser.json">
				<li class="adminTableTitle">禁止用户</li>
				<li class="adminLi">I &nbsp&nbsp D：<input class="adminInput" focusclass="adminInputFocus" type="text" name="userid"></input> 昵称：<input class="adminInput" focusclass="adminInputFocus" type="text" name="nickname"></input></li>
				<li class="adminLi">地区：<input class="adminInput" focusclass="adminInputFocus" type="text" name="createarea"></input> I &nbsp &nbsp P：<input class="adminInput" focusclass="adminInputFocus" type="text" name="createip"></input></li>
				<li class="adminLi">添加禁用ip：<input class="adminInput" focusclass="adminInputFocus" type="text" id="forbitIp"></input></li>
				<li class="adminBtns"><input class="adminBtn" hoverClass="adminBtnHover" type="button" value="查询" onclick="adminQuery(this)"></input><input class="adminBtn" hoverClass="adminBtnHover" type="button" value="添加" onclick="addSystemForbit(this)"></input></li>
			</ul>
			<table class="adminTable">
				<thead><tr><th style="width:20px">id</th><th>昵称</th><th style="width:55px">地区</th><th style="width:150px">ip</th><th style="width:75px">操作</th></tr></thead>
				<tbody>
					<%@ include file="admin_user_forbit.jsp"  %>
				</tbody>
			</table>
		</div>
		<div class="adminTableContainer">
			<ul class="adminTableQuery" url="${base}/adminNormalUser.json">
				<li class="adminTableTitle">正常用户</li>
				<li class="adminLi">I &nbsp&nbsp D：<input class="adminInput" focusclass="adminInputFocus" type="text" name="userid"></input> 昵称：<input class="adminInput" focusclass="adminInputFocus" type="text" name="nickname"></input></li>
				<li class="adminLi">地区：<input class="adminInput" focusclass="adminInputFocus" type="text" name="createarea"></input> I &nbsp &nbsp P：<input class="adminInput" focusclass="adminInputFocus" type="text" name="createip"></input></li>
				<li class="adminBtns"><input class="adminBtn" hoverClass="adminBtnHover" type="button" value="查询" onclick="adminQuery(this)"></input></li>
			</ul>
			<table class="adminTable">
				<thead><tr><th style="width:20px">id</th><th>昵称</th><th style="width:55px">地区</th><th style="width:150px">ip</th><th>修改昵称</th><th>修改关像</th><th style="width:130px">操作</th></tr></thead>
				<tbody>
					<%@ include file="admin_user_normal.jsp"  %>
				</tbody>
			</table>
		</div>
		
		
	</div>
</div>


<%@ include file="foot.jsp" %>