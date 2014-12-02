<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<% pageContext.setAttribute("focusIndex", 4);  %>
<%@ include file="head.jsp" %>


<div class="adminWrap">
	<div class="adminLeft">
		<a href="${base}/admin_movie.do"  class="adminLeftA">影片管理</a>
		<a href="${base}/admin_module.do" class="adminLeftA">版块管理</a>
		<a href="${base}/admin_cmmt.do"   class="adminLeftA">留言管理</a>
		<a href="${base}/admin_search.do"   class="adminLeftA adminLeftAClick">搜索管理</a>
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
			<ul class="adminTableQuery" url="${base}/search_hot.json">
				<li class="adminTableTitle"><span style="color:red">【热门】</span> 关键字</li>
				<li class="adminLi">I D：      <input class="adminInput" focusclass="adminInputFocus" type="text" name="id"></input></li>
				<li class="adminLi">关键字：<input class="adminInput" focusclass="adminInputFocus" type="text" name="keyword"></input></li>
				<li class="adminLi">新关键字：<input class="adminInput" focusclass="adminInputFocus" type="text" name="userid"></input></li>
				<li class="adminBtns"><input class="adminBtn" hoverClass="adminBtnHover" type="button" value="查询" onclick="adminQuery(this)"></input><input class="adminBtn" hoverClass="adminBtnHover" type="button" value="添加" onclick="addSystemForbit(this)"></input></li>
			</ul>
			<table class="adminTable">
				<thead><tr><th style="width:20px">id</th><th>关键字</th><th style="width:110px">时间</th><th style="width:65px">操作</th></tr></thead>
				<tbody>
					<%@ include file="admin_search_hot.jsp"  %>
				</tbody>
			</table>
		</div>
		<div class="adminTableContainer">
			<ul class="adminTableQuery" url="${base}/search_word.json">
				<li class="adminTableTitle"><span style="color:red">【单词】</span> 关键字</li>
				<li class="adminLi">I D：      <input class="adminInput" focusclass="adminInputFocus" type="text" name="id"></input></li>
				<li class="adminLi">关键字：<input class="adminInput" focusclass="adminInputFocus" type="text" name="keyword"></input></li>
				<li class="adminLi">新关键字：<input class="adminInput" focusclass="adminInputFocus" type="text" name="userid"></input></li>
				<li class="adminBtns"><input class="adminBtn" hoverClass="adminBtnHover" type="button" value="查询" onclick="adminQuery(this)"></input><input class="adminBtn" hoverClass="adminBtnHover" type="button" value="添加" onclick="addSystemForbit(this)"></input></li>
			</ul>
			<table class="adminTable">
				<thead><tr><th style="width:20px">id</th><th>关键字</th><th style="width:110px">时间</th><th style="width:65px">操作</th></tr></thead>
				<tbody>
					<%@ include file="admin_search_word.jsp"  %>
				</tbody>
			</table>
		</div>
		<div class="adminTableContainer">
			<ul class="adminTableQuery" url="${base}/search_unword.json">
				<li class="adminTableTitle"><span style="color:red">【禁用】</span> 关键字</li>
				<li class="adminLi">I D：      <input class="adminInput" focusclass="adminInputFocus" type="text" name="id"></input></li>
				<li class="adminLi">关键字：<input class="adminInput" focusclass="adminInputFocus" type="text" name="keyword"></input></li>
				<li class="adminLi">新关键字：<input class="adminInput" focusclass="adminInputFocus" type="text" name="userid"></input></li>
				<li class="adminBtns"><input class="adminBtn" hoverClass="adminBtnHover" type="button" value="查询" onclick="adminQuery(this)"></input><input class="adminBtn" hoverClass="adminBtnHover" type="button" value="添加" onclick="addSystemForbit(this)"></input></li>
			</ul>
			<table class="adminTable">
				<thead><tr><th style="width:20px">id</th><th>关键字</th><th style="width:110px">时间</th><th style="width:65px">操作</th></tr></thead>
				<tbody>
					<%@ include file="admin_search_unword.jsp"  %>
				</tbody>
			</table>
		</div>
	</div>
</div>

<%@ include file="foot.jsp" %>