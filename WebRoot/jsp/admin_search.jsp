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
			<div class="adminPic" url="${base}/statSearch.json?sort=total" interval="5000"></div>
			<p class="adminPicTitle">关键字排名（总数）</p>
		</div>
		<div class="adminPicContainer" style="margin-left:30px">
			<div class="adminPic" url="${base}/statSearch.json?sort=year" interval="5000"></div>
			<p class="adminPicTitle">关键字排名（本年）</p>
		</div>
		<div class="adminPicContainer">
			<div class="adminPic" url="${base}/statSearch.json?sort=month" interval="5000"></div>
			<p class="adminPicTitle">关键字排名（本月）</p>
		</div>
		<div class="adminPicContainer" style="margin-left:30px">
			<div class="adminPic" url="${base}/statSearch.json?sort=week" interval="5000"></div>
			<p class="adminPicTitle">关键字排名（本周）</p>
		</div> 
		<div class="reIndexBtnCont"><input class="adminBtn" hoverClass="adminBtnHover"  style="width:120px" type="button" value="重新新成索引" onclick="reCreateIndex(this)"></input></div>
		<div class="adminTableContainer" style="padding-top:20px">
			<ul class="adminTableQuery" url="${base}/search_hot.json">
				<li class="adminTableTitle"><span style="color:red">【热门】</span> 关键字</li>
				<li class="adminLi"><span style="display:inline-block;width:70px">I D：</span><input class="adminInput" focusclass="adminInputFocus" type="text" name="id"></input></li>
				<li class="adminLi"><span style="display:inline-block;width:70px">关键字：</span><input class="adminInput" focusclass="adminInputFocus" type="text" name="keyword"></input></li>
				<li class="adminLi" style="height:160px"><span style="display:inline-block;width:70px;float:left;line-height:20px;">新关键字：</span><textarea name="longDesc" class="mvTextareaInput" focusClass="inputF"  style="width:308px;height:153px;" id="newHotword"></textarea></li>
				<li class="adminBtns"><input class="adminBtn" hoverClass="adminBtnHover" type="button" value="查询" onclick="adminQuery(this)"></input><input class="adminBtn" hoverClass="adminBtnHover" type="button" value="添加" onclick="addHotword(this)"></input></li>
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
				<li class="adminLi"><span style="display:inline-block;width:70px">I D：</span><input class="adminInput" focusclass="adminInputFocus" type="text" name="id"></input></li>
				<li class="adminLi"><span style="display:inline-block;width:70px">关键字：</span><input class="adminInput" focusclass="adminInputFocus" type="text" name="keyword"></input></li>
				<li class="adminLi" style="height:160px"><span style="display:inline-block;width:70px;float:left;line-height:20px;">新关键字：</span><textarea name="longDesc" class="mvTextareaInput" focusClass="inputF"  style="width:308px;height:153px;" id="newWord"></textarea></li>
				<li class="adminBtns"><input class="adminBtn" hoverClass="adminBtnHover" type="button" value="查询" onclick="adminQuery(this)"></input><input class="adminBtn" hoverClass="adminBtnHover" type="button" value="添加" onclick="addWord(this)"></input></li>
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
				<li class="adminLi"><span style="display:inline-block;width:70px">I D：</span><input class="adminInput" focusclass="adminInputFocus" type="text" name="id"></input></li>
				<li class="adminLi"><span style="display:inline-block;width:70px">关键字：</span><input class="adminInput" focusclass="adminInputFocus" type="text" name="keyword"></input></li>
				<li class="adminLi" style="height:160px"><span style="display:inline-block;width:70px;float:left;line-height:20px;">新关键字：</span><textarea name="longDesc" class="mvTextareaInput" focusClass="inputF"  style="width:308px;height:153px;" id="newUnword"></textarea></li>
				<li class="adminBtns"><input class="adminBtn" hoverClass="adminBtnHover" type="button" value="查询" onclick="adminQuery(this)"></input><input class="adminBtn" hoverClass="adminBtnHover" type="button" value="添加" onclick="addUnword(this)"></input></li>
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