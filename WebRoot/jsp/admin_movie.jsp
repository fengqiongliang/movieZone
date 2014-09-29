<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<% pageContext.setAttribute("focusIndex", 4);  %>
<%@ include file="head.jsp" %>


<div class="adminWrap">
	<div class="adminLeft">
		<a href="admin_movie.html"  class="adminLeftA adminLeftAClick">影片管理</a>
		<a href="admin_module.html" class="adminLeftA">版块管理</a>
		<a href="admin_user.html"   class="adminLeftA">用户管理</a>
		<a href="admin_data.html"   class="adminLeftA">数据分析</a>
		<div class="clear"></div>
	</div>
	
	<div class="adminRight">
		<div class="adminPicContainer">
			<div class="adminPic" url="stat.json" interval="1000"></div>
			<p class="adminPicTitle">按浏览量进行排名</p>
		</div>
		<div class="adminPicContainer" style="margin-left:30px">
			<div class="adminPic" url="stat.json" interval="1000"></div>
			<p class="adminPicTitle">按下载量进行排名</p>
		</div>
		<div class="adminPicContainer">
			<div class="adminPic" url="stat.json" interval="1000"></div>
			<p class="adminPicTitle">按点赞数进行排名</p>
		</div>
		<div class="adminPicContainer" style="margin-left:30px">
			<div class="adminPic" url="stat.json" interval="1000"></div>
			<p class="adminPicTitle">按留言数进行排名</p>
		</div>
		<div class="adminTableContainer">
			<ul class="adminTableQuery" url="adminMovie1.json">
				<li class="adminTableTitle">队列上线</li>
				<li class="adminLi">I D：<input class="adminInput" focusclass="adminInputFocus" type="text" name="id"></input> 名称：<input class="adminInput" focusclass="adminInputFocus" type="text" name="name"></input></li>
				<li class="adminLi">类型：<input onclick="checkType(this)" type="checkbox" value="movie"/> 电影 <input onclick="checkType(this)" type="checkbox" value="tv"/> 电视剧<span name="type" class="checkLabel" style="margin-left:276px;display:none;width:230px"></span><a href="javascript:void(0)" onclick="delQuery(this)" style="font-size:12px;display:none">删除</a></li>
				<li class="adminBtns"><input class="adminBtn" hoverClass="adminBtnHover" type="button" value="查询" onclick="adminQuery(this)"></input><input class="adminBtn" hoverClass="adminBtnHover" type="button" value="添加" onclick="openWnd('admin_movieAction.html')"></input></li>
			</ul>
			<table class="adminTable">
				<thead><tr><th style="width:20px">id</th><th>名称</th><th style="width:55px">类型</th><th style="width:45px">评分</th><th style="width:110px">上线时间</th><th style="width:100px">还有</th><th style="width:110px">作用版块</th><th style="width:75px">操作</th></tr></thead>
				<tbody>
					<tr><td>1</td><td style="text-align:left;padding-left:10px">大闹天宫大闹天宫大闹天宫大闹天宫大闹天大闹天宫大闹天宫大闹天宫大闹天宫大闹天宫大闹天宫宫大闹天宫大闹天宫大闹天宫</td><td>电影</td><td>8.0</td><td>2014-01-01 12:30</td><td>1天 01:20:01</td><td style="text-align:left;padding-left:10px">首页-480p<br/>内容页</td><td>修改 删除</td></tr>
					<tr><td>2</td><td style="text-align:left;padding-left:10px">神的天堂</td><td>电影剧</td><td>8.0</td><td>2012-01-01 12:30</td><td>36天 01:20:01</td><td style="text-align:left;padding-left:10px">首页-720p<br/>电视剧</td><td>修改 删除</td></tr>
					<tr><td>3</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>电影</td><td>8.0</td><td>2014-01-01 12:30</td><td>1天 01:20:01</td><td style="text-align:left;padding-left:10px">首页-480p<br/>内容页</td><td>修改 删除</td></tr>
					<tr><td>4</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>电影</td><td>8.0</td><td>2014-01-01 12:30</td><td>1天 01:20:01</td><td style="text-align:left;padding-left:10px">首页-480p<br/>内容页</td><td>修改 删除</td></tr>
					<tr><td>5</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>电影</td><td>8.0</td><td>2014-01-01 12:30</td><td>1天 01:20:01</td><td style="text-align:left;padding-left:10px">首页-480p<br/>内容页</td><td>修改 删除</td></tr>
					<tr><td>6</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>电影</td><td>8.0</td><td>2014-01-01 12:30</td><td>1天 01:20:01</td><td style="text-align:left;padding-left:10px">首页-480p<br/>内容页</td><td>修改 删除</td></tr>
					<tr><td>7</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>电影</td><td>8.0</td><td>2014-01-01 12:30</td><td>1天 01:20:01</td><td style="text-align:left;padding-left:10px">首页-480p<br/>内容页</td><td>修改 删除</td></tr>
					<tr><td>8</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>电影</td><td>8.0</td><td>2014-01-01 12:30</td><td>1天 01:20:01</td><td style="text-align:left;padding-left:10px">首页-480p<br/>内容页</td><td>修改 删除</td></tr>
					<tr><td>9</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>电影</td><td>8.0</td><td>2014-01-01 12:30</td><td>1天 01:20:01</td><td style="text-align:left;padding-left:10px">首页-480p<br/>内容页</td><td>修改 删除</td></tr>
					<tr><td>10</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>电影</td><td>8.0</td><td>2014-01-01 12:30</td><td>1天 01:20:01</td><td style="text-align:left;padding-left:10px">首页-480p<br/>内容页</td><td>修改 删除</td></tr>
					<tr><td>11</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>电影</td><td>8.0</td><td>2014-01-01 12:30</td><td>1天 01:20:01</td><td style="text-align:left;padding-left:10px">首页-480p<br/>内容页</td><td>修改 删除</td></tr>
					<tr><td>12</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>电影</td><td>8.0</td><td>2014-01-01 12:30</td><td>1天 01:20:01</td><td style="text-align:left;padding-left:10px">首页-480p<br/>内容页</td><td>修改 删除</td></tr>
					<tr><td>13</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>电影</td><td>8.0</td><td>2014-01-01 12:30</td><td>1天 01:20:01</td><td style="text-align:left;padding-left:10px">首页-480p<br/>内容页</td><td>修改 删除</td></tr>
					<tr><td>14</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>电影</td><td>8.0</td><td>2014-01-01 12:30</td><td>1天 01:20:01</td><td style="text-align:left;padding-left:10px">首页-480p<br/>内容页</td><td>修改 删除</td></tr>
					<tr><td>15</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>电影</td><td>8.0</td><td>2014-01-01 12:30</td><td>1天 01:20:01</td><td style="text-align:left;padding-left:10px">首页-480p<br/>内容页</td><td>修改 删除</td></tr>
					<tr><td>16</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>电影</td><td>8.0</td><td>2014-01-01 12:30</td><td>1天 01:20:01</td><td style="text-align:left;padding-left:10px">首页-480p<br/>内容页</td><td>修改 删除</td></tr>
					<tr><td>17</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>电影</td><td>8.0</td><td>2014-01-01 12:30</td><td>1天 01:20:01</td><td style="text-align:left;padding-left:10px">首页-480p<br/>内容页</td><td>修改 删除</td></tr>
					<tr><td>18</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>电影</td><td>8.0</td><td>2014-01-01 12:30</td><td>1天 01:20:01</td><td style="text-align:left;padding-left:10px">首页-480p<br/>内容页</td><td>修改 删除</td></tr>
					<tr><td>19</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>电影</td><td>8.0</td><td>2014-01-01 12:30</td><td>1天 01:20:01</td><td style="text-align:left;padding-left:10px">首页-480p<br/>内容页</td><td>修改 删除</td></tr>
					<tr><td>20</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>电影</td><td>8.0</td><td>2014-01-01 12:30</td><td>1天 01:20:01</td><td style="text-align:left;padding-left:10px">首页-480p<br/>内容页</td><td>修改 删除</td></tr>
					<tr><td>21</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>电影</td><td>8.0</td><td>2014-01-01 12:30</td><td>1天 01:20:01</td><td style="text-align:left;padding-left:10px">首页-480p<br/>内容页</td><td>修改 删除</td></tr>
					<tr><td>22</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>电影</td><td>8.0</td><td>2014-01-01 12:30</td><td>1天 01:20:01</td><td style="text-align:left;padding-left:10px">首页-480p<br/>内容页</td><td>修改 删除</td></tr>
					<tr><td>23</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>电影</td><td>8.0</td><td>2014-01-01 12:30</td><td>1天 01:20:01</td><td style="text-align:left;padding-left:10px">首页-480p<br/>内容页</td><td>修改 删除</td></tr>
					<tr><td>24</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>电影</td><td>8.0</td><td>2014-01-01 12:30</td><td>1天 01:20:01</td><td style="text-align:left;padding-left:10px">首页-480p<br/>内容页</td><td>修改 删除</td></tr>
					<tr><td>25</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>电影</td><td>8.0</td><td>2014-01-01 12:30</td><td>1天 01:20:01</td><td style="text-align:left;padding-left:10px">首页-480p<br/>内容页</td><td>修改 删除</td></tr>
					<tr height="40" valign="middle"><td colspan="20" style="text-align:right;background:#F1F8FE"><input  style="width:60px" class="adminBtn" hoverClass="adminBtnHover" type="button" value="<上一页" onclick="adminQuery(this,'up')"></input><input style="width:60px" class="adminBtn" hoverClass="adminBtnHover" type="button" value="下一页>" onclick="adminQuery(this,'down')"></input></td></tr>
				</tbody>
			</table>
		</div>
		<div class="adminTableContainer">
			<ul class="adminTableQuery" url="adminMovie2.json">
				<li class="adminTableTitle">线上影片</li>
				<li class="adminLi">I D：<input class="adminInput" focusclass="adminInputFocus" type="text" name="id"></input> 名称：<input class="adminInput" focusclass="adminInputFocus" type="text" name="name"></input></li>
				<li class="adminLi">类型：<input onclick="checkType(this)" type="checkbox" value="movie"/> 电影 <input onclick="checkType(this)" type="checkbox" value="tv"/> 电视剧<span name="type" class="checkLabel" style="margin-left:276px;display:none;width:230px"></span><a href="javascript:void(0)" onclick="delQuery(this)" style="font-size:12px;display:none">删除</a></li>
				<li class="adminLi">排序：<input onclick="checkType(this)" type="checkbox" value="time"/> 时间 <input onclick="checkType(this)" type="checkbox" value="score"/> 评分 <input onclick="checkType(this)" type="checkbox" value="favorite"/> 收藏 <input onclick="checkType(this)" type="checkbox" value="down"/> 下载 <input onclick="checkType(this)" type="checkbox" value="browse"/> 浏览 <input onclick="checkType(this)" type="checkbox" value="good"/> 赞数 <input onclick="checkType(this)" type="checkbox" value="comment"/> 留言<span name="sort" class="checkLabel" style="margin-left:50px;display:none;width:230px"></span><a href="javascript:void(0)" onclick="delQuery(this)" style="font-size:12px;display:none">删除</a></li>
				<li class="adminBtns"><input class="adminBtn" hoverClass="adminBtnHover" type="button" value="查询" onclick="adminQuery(this)"></input><input class="adminBtn" hoverClass="adminBtnHover" type="button" value="添加" onclick="openWnd('admin_movieAction.html')"></input></li>
			</ul>
			<table class="adminTable">
				<thead><tr><th style="width:20px">id</th><th>名称</th><th style="width:55px">类型</th><th style="width:45px">评分</th><th style="width:45px">收藏</th><th style="width:45px">下载</th><th style="width:45px">浏览</th><th style="width:45px">赞数</th><th style="width:45px">留言</th><th style="width:110px">上线时间</th><th style="width:110px">作用版块</th><th style="width:75px">操作</th></tr></thead>
				<tbody>
					<tr><td>1</td><td style="text-align:left;padding-left:10px">大闹天宫大闹天宫</td><td>电影</td><td>8.0</td><td>10</td><td>800</td><td>810</td><td>9</td><td>21</td><td>2014-01-01 12:30</td><td style="text-align:left;padding-left:10px">首页-480p<br/>内容页</td><td>修改 删除</td></tr>
					<tr><td>2</td><td style="text-align:left;padding-left:10px">大闹天宫大闹天宫</td><td>电影</td><td>8.0</td><td>10</td><td>800</td><td>810</td><td>9</td><td>21</td><td>2014-01-01 12:30</td><td style="text-align:left;padding-left:10px">首页-480p<br/>内容页</td><td>修改 删除</td></tr>
					<tr><td>3</td><td style="text-align:left;padding-left:10px">大闹天宫大闹天宫</td><td>电影</td><td>8.0</td><td>10</td><td>800</td><td>810</td><td>9</td><td>21</td><td>2014-01-01 12:30</td><td style="text-align:left;padding-left:10px">首页-480p<br/>内容页</td><td>修改 删除</td></tr>
					<tr><td>4</td><td style="text-align:left;padding-left:10px">大闹天宫大闹天宫</td><td>电影</td><td>8.0</td><td>10</td><td>800</td><td>810</td><td>9</td><td>21</td><td>2014-01-01 12:30</td><td style="text-align:left;padding-left:10px">首页-480p<br/>内容页</td><td>修改 删除</td></tr>
					<tr><td>5</td><td style="text-align:left;padding-left:10px">大闹天宫大闹天宫</td><td>电影</td><td>8.0</td><td>10</td><td>800</td><td>810</td><td>9</td><td>21</td><td>2014-01-01 12:30</td><td style="text-align:left;padding-left:10px">首页-480p<br/>内容页</td><td>修改 删除</td></tr>
					<tr><td>6</td><td style="text-align:left;padding-left:10px">大闹天宫大闹天宫</td><td>电影</td><td>8.0</td><td>10</td><td>800</td><td>810</td><td>9</td><td>21</td><td>2014-01-01 12:30</td><td style="text-align:left;padding-left:10px">首页-480p<br/>内容页</td><td>修改 删除</td></tr>
					<tr><td>7</td><td style="text-align:left;padding-left:10px">大闹天宫大闹天宫</td><td>电影</td><td>8.0</td><td>10</td><td>800</td><td>810</td><td>9</td><td>21</td><td>2014-01-01 12:30</td><td style="text-align:left;padding-left:10px">首页-480p<br/>内容页</td><td>修改 删除</td></tr>
					<tr><td>8</td><td style="text-align:left;padding-left:10px">大闹天宫大闹天宫</td><td>电影</td><td>8.0</td><td>10</td><td>800</td><td>810</td><td>9</td><td>21</td><td>2014-01-01 12:30</td><td style="text-align:left;padding-left:10px">首页-480p<br/>内容页</td><td>修改 删除</td></tr>
					<tr><td>9</td><td style="text-align:left;padding-left:10px">大闹天宫大闹天宫</td><td>电影</td><td>8.0</td><td>10</td><td>800</td><td>810</td><td>9</td><td>21</td><td>2014-01-01 12:30</td><td style="text-align:left;padding-left:10px">首页-480p<br/>内容页</td><td>修改 删除</td></tr>
					<tr><td>10</td><td style="text-align:left;padding-left:10px">大闹天宫大闹天宫</td><td>电影</td><td>8.0</td><td>10</td><td>800</td><td>810</td><td>9</td><td>21</td><td>2014-01-01 12:30</td><td style="text-align:left;padding-left:10px">首页-480p<br/>内容页</td><td>修改 删除</td></tr>
					<tr><td>11</td><td style="text-align:left;padding-left:10px">大闹天宫大闹天宫</td><td>电影</td><td>8.0</td><td>10</td><td>800</td><td>810</td><td>9</td><td>21</td><td>2014-01-01 12:30</td><td style="text-align:left;padding-left:10px">首页-480p<br/>内容页</td><td>修改 删除</td></tr>
					<tr><td>12</td><td style="text-align:left;padding-left:10px">大闹天宫大闹天宫</td><td>电影</td><td>8.0</td><td>10</td><td>800</td><td>810</td><td>9</td><td>21</td><td>2014-01-01 12:30</td><td style="text-align:left;padding-left:10px">首页-480p<br/>内容页</td><td>修改 删除</td></tr>
					<tr><td>13</td><td style="text-align:left;padding-left:10px">大闹天宫大闹天宫</td><td>电影</td><td>8.0</td><td>10</td><td>800</td><td>810</td><td>9</td><td>21</td><td>2014-01-01 12:30</td><td style="text-align:left;padding-left:10px">首页-480p<br/>内容页</td><td>修改 删除</td></tr>
					<tr><td>14</td><td style="text-align:left;padding-left:10px">大闹天宫大闹天宫</td><td>电影</td><td>8.0</td><td>10</td><td>800</td><td>810</td><td>9</td><td>21</td><td>2014-01-01 12:30</td><td style="text-align:left;padding-left:10px">首页-480p<br/>内容页</td><td>修改 删除</td></tr>
					<tr><td>15</td><td style="text-align:left;padding-left:10px">大闹天宫大闹天宫</td><td>电影</td><td>8.0</td><td>10</td><td>800</td><td>810</td><td>9</td><td>21</td><td>2014-01-01 12:30</td><td style="text-align:left;padding-left:10px">首页-480p<br/>内容页</td><td>修改 删除</td></tr>
					<tr><td>16</td><td style="text-align:left;padding-left:10px">大闹天宫大闹天宫</td><td>电影</td><td>8.0</td><td>10</td><td>800</td><td>810</td><td>9</td><td>21</td><td>2014-01-01 12:30</td><td style="text-align:left;padding-left:10px">首页-480p<br/>内容页</td><td>修改 删除</td></tr>
					<tr><td>17</td><td style="text-align:left;padding-left:10px">大闹天宫大闹天宫</td><td>电影</td><td>8.0</td><td>10</td><td>800</td><td>810</td><td>9</td><td>21</td><td>2014-01-01 12:30</td><td style="text-align:left;padding-left:10px">首页-480p<br/>内容页</td><td>修改 删除</td></tr>
					<tr><td>18</td><td style="text-align:left;padding-left:10px">大闹天宫大闹天宫</td><td>电影</td><td>8.0</td><td>10</td><td>800</td><td>810</td><td>9</td><td>21</td><td>2014-01-01 12:30</td><td style="text-align:left;padding-left:10px">首页-480p<br/>内容页</td><td>修改 删除</td></tr>
					<tr><td>19</td><td style="text-align:left;padding-left:10px">大闹天宫大闹天宫</td><td>电影</td><td>8.0</td><td>10</td><td>800</td><td>810</td><td>9</td><td>21</td><td>2014-01-01 12:30</td><td style="text-align:left;padding-left:10px">首页-480p<br/>内容页</td><td>修改 删除</td></tr>
					<tr><td>20</td><td style="text-align:left;padding-left:10px">大闹天宫大闹天宫</td><td>电影</td><td>8.0</td><td>10</td><td>800</td><td>810</td><td>9</td><td>21</td><td>2014-01-01 12:30</td><td style="text-align:left;padding-left:10px">首页-480p<br/>内容页</td><td>修改 删除</td></tr>
					<tr><td>21</td><td style="text-align:left;padding-left:10px">大闹天宫大闹天宫</td><td>电影</td><td>8.0</td><td>10</td><td>800</td><td>810</td><td>9</td><td>21</td><td>2014-01-01 12:30</td><td style="text-align:left;padding-left:10px">首页-480p<br/>内容页</td><td>修改 删除</td></tr>
					<tr><td>22</td><td style="text-align:left;padding-left:10px">大闹天宫大闹天宫</td><td>电影</td><td>8.0</td><td>10</td><td>800</td><td>810</td><td>9</td><td>21</td><td>2014-01-01 12:30</td><td style="text-align:left;padding-left:10px">首页-480p<br/>内容页</td><td>修改 删除</td></tr>
					<tr><td>23</td><td style="text-align:left;padding-left:10px">大闹天宫大闹天宫</td><td>电影</td><td>8.0</td><td>10</td><td>800</td><td>810</td><td>9</td><td>21</td><td>2014-01-01 12:30</td><td style="text-align:left;padding-left:10px">首页-480p<br/>内容页</td><td>修改 删除</td></tr>
					<tr><td>24</td><td style="text-align:left;padding-left:10px">大闹天宫大闹天宫</td><td>电影</td><td>8.0</td><td>10</td><td>800</td><td>810</td><td>9</td><td>21</td><td>2014-01-01 12:30</td><td style="text-align:left;padding-left:10px">首页-480p<br/>内容页</td><td>修改 删除</td></tr>
					<tr><td>25</td><td style="text-align:left;padding-left:10px">大闹天宫大闹天宫</td><td>电影</td><td>8.0</td><td>10</td><td>800</td><td>810</td><td>9</td><td>21</td><td>2014-01-01 12:30</td><td style="text-align:left;padding-left:10px">首页-480p<br/>内容页</td><td>修改 删除</td></tr>
					<tr height="40" valign="middle"><td colspan="20" style="text-align:right;background:#F1F8FE"><input  style="width:60px" class="adminBtn" hoverClass="adminBtnHover" type="button" value="<上一页" onclick="adminQuery(this,'up')"></input><input style="width:60px" class="adminBtn" hoverClass="adminBtnHover" type="button" value="下一页>" onclick="adminQuery(this,'down')"></input></td></tr>
				</tbody>
			</table>
		</div>
	</div>
</div>

<%@ include file="foot.jsp" %>