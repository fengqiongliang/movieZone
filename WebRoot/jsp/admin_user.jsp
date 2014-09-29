<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<% pageContext.setAttribute("focusIndex", 4);  %>
<%@ include file="head.jsp" %>


<div class="adminWrap">
	<div class="adminLeft">
		<a href="admin_movie.html"  class="adminLeftA">影片管理</a>
		<a href="admin_module.html" class="adminLeftA">版块管理</a>
		<a href="admin_user.html"   class="adminLeftA adminLeftAClick">用户管理</a>
		<a href="admin_data.html"   class="adminLeftA">数据分析</a>
		<div class="clear"></div>
	</div>
	
	<div class="adminRight">
		<div class="adminPicContainer">
			<div class="adminPic" url="statArea.json" interval="1000"></div>
			<p class="adminPicTitle">地区浏览量</p>
		</div>
		<div class="adminPicContainer" style="margin-left:30px">
			<div class="adminPic" url="statActiveUser.json" interval="1000"></div>
			<p class="adminPicTitle">活跃用户</p>
		</div>
		<div class="adminTableContainer">
			<ul class="adminTableQuery" url="adminForbidUser.json">
				<li class="adminTableTitle">禁止用户</li>
				<li class="adminLi">I &nbsp&nbsp D：<input class="adminInput" focusclass="adminInputFocus" type="text" name="id"></input> 昵称：<input class="adminInput" focusclass="adminInputFocus" type="text" name="name"></input></li>
				<li class="adminLi">地区：<input class="adminInput" focusclass="adminInputFocus" type="text" name="province"></input> I &nbsp &nbsp P：<input class="adminInput" focusclass="adminInputFocus" type="text" name="ip"></input></li>
				<li class="adminBtns"><input class="adminBtn" hoverClass="adminBtnHover" type="button" value="查询" onclick="adminQuery(this)"></input><input class="adminBtn" hoverClass="adminBtnHover" type="button" value="添加" onclick="openWnd('admin_movieAction.html')"></input></li>
			</ul>
			<table class="adminTable">
				<thead><tr><th style="width:20px">id</th><th>昵称</th><th style="width:55px">地区</th><th style="width:150px">ip</th><th style="width:75px">操作</th></tr></thead>
				<tbody>
					<tr hoverClass="adminTrHover"><td>1</td><td style="text-align:left;padding-left:10px">系统禁用ip</td><td>海南</td><td style="text-align:left;padding-left:10px">192.168.3.28</td><td><span class="adminAction" onclick="delForbidUser(this,1)">删除</span></td></tr>
					<tr hoverClass="adminTrHover"><td>2</td><td style="text-align:left;padding-left:10px">神的天堂</td><td>海南</td><td style="text-align:left;padding-left:10px">192.168.3.28</td><td><span class="adminAction" onclick="delForbidUser(this,1)">删除</span></td></tr>
					<tr hoverClass="adminTrHover"><td>3</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>海南</td><td style="text-align:left;padding-left:10px">192.168.3.28</td><td><span class="adminAction" onclick="delForbidUser(this,1)">删除</span></td></tr>
					<tr hoverClass="adminTrHover"><td>4</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>海南</td><td style="text-align:left;padding-left:10px">192.168.3.28</td><td><span class="adminAction" onclick="delForbidUser(this,1)">删除</span></td></tr>
					<tr hoverClass="adminTrHover"><td>5</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>海南</td><td style="text-align:left;padding-left:10px">192.168.3.28</td><td><span class="adminAction" onclick="delForbidUser(this,1)">删除</span></td></tr>
					<tr hoverClass="adminTrHover"><td>6</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>上海</td><td style="text-align:left;padding-left:10px">192.168.3.12</td><td><span class="adminAction" onclick="delForbidUser(this,1)">删除</span></td></tr>
					<tr hoverClass="adminTrHover"><td>7</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>北京</td><td style="text-align:left;padding-left:10px">192.168.3.28</td><td><span class="adminAction" onclick="delForbidUser(this,1)">删除</span></td></tr>
					<tr hoverClass="adminTrHover"><td>8</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>上海</td><td style="text-align:left;padding-left:10px">192.168.3.12</td><td><span class="adminAction" onclick="delForbidUser(this,1)">删除</span></td></tr>
					<tr hoverClass="adminTrHover"><td>9</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>上海</td><td style="text-align:left;padding-left:10px">192.168.3.12</td><td><span class="adminAction" onclick="delForbidUser(this,1)">删除</span></td></tr>
					<tr hoverClass="adminTrHover"><td>10</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>北京</td><td style="text-align:left;padding-left:10px">192.168.3.12</td><td><span class="adminAction" onclick="delForbidUser(this,1)">删除</span></td></tr>
					<tr hoverClass="adminTrHover"><td>11</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>北京</td><td style="text-align:left;padding-left:10px">192.168.3.12</td><td><span class="adminAction" onclick="delForbidUser(this,1)">删除</span></td></tr>
					<tr hoverClass="adminTrHover"><td>12</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>北京</td><td style="text-align:left;padding-left:10px">192.168.3.12</td><td><span class="adminAction" onclick="delForbidUser(this,1)">删除</span></td></tr>
					<tr hoverClass="adminTrHover"><td>13</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>北京</td><td style="text-align:left;padding-left:10px">192.168.3.12</td><td><span class="adminAction" onclick="delForbidUser(this,1)">删除</span></td></tr>
					<tr hoverClass="adminTrHover"><td>14</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>北京</td><td style="text-align:left;padding-left:10px">192.168.3.12</td><td><span class="adminAction" onclick="delForbidUser(this,1)">删除</span></td></tr>
					<tr hoverClass="adminTrHover"><td>15</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>北京</td><td style="text-align:left;padding-left:10px">192.168.3.1</td><td><span class="adminAction" onclick="delForbidUser(this,1)">删除</span></td></tr>
					<tr hoverClass="adminTrHover"><td>16</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>云南</td><td style="text-align:left;padding-left:10px">192.168.3.12</td><td><span class="adminAction" onclick="delForbidUser(this,1)">删除</span></td></tr>
					<tr hoverClass="adminTrHover"><td>17</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>北京</td><td style="text-align:left;padding-left:10px">192.168.3.12</td><td><span class="adminAction" onclick="delForbidUser(this,1)">删除</span></td></tr>
					<tr hoverClass="adminTrHover"><td>18</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>北京</td><td style="text-align:left;padding-left:10px">192.168.3.12</td><td><span class="adminAction" onclick="delForbidUser(this,1)">删除</span></td></tr>
					<tr hoverClass="adminTrHover"><td>19</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>云南</td><td style="text-align:left;padding-left:10px">192.168.3.12</td><td><span class="adminAction" onclick="delForbidUser(this,1)">删除</span></td></tr>
					<tr hoverClass="adminTrHover"><td>20</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>北京</td><td style="text-align:left;padding-left:10px">192.168.3.12</td><td><span class="adminAction" onclick="delForbidUser(this,1)">删除</span></td></tr>
					<tr hoverClass="adminTrHover"><td>21</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>北京</td><td style="text-align:left;padding-left:10px">192.168.3.12</td><td><span class="adminAction" onclick="delForbidUser(this,1)">删除</span></td></tr>
					<tr hoverClass="adminTrHover"><td>22</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>北京</td><td style="text-align:left;padding-left:10px">192.168.3.12</td><td><span class="adminAction" onclick="delForbidUser(this,1)">删除</span></td></tr>
					<tr hoverClass="adminTrHover"><td>23</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>海南</td><td style="text-align:left;padding-left:10px">192.168.3.12</td><td><span class="adminAction" onclick="delForbidUser(this,1)">删除</span></td></tr>
					<tr hoverClass="adminTrHover"><td>24</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>海南</td><td style="text-align:left;padding-left:10px">192.168.3.1</td><td><span class="adminAction" onclick="delForbidUser(this,1)">删除</span></td></tr>
					<tr hoverClass="adminTrHover"><td>25</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>海南</td><td style="text-align:left;padding-left:10px">192.168.3.12</td><td><span class="adminAction" onclick="delForbidUser(this,1)">删除</span></td></tr>
					<tr height="40" valign="middle"><td colspan="20" style="text-align:right;background:#F1F8FE"><input  style="width:60px" class="adminBtn" hoverClass="adminBtnHover" type="button" value="<上一页" onclick="adminQuery(this,'up')"></input><input style="width:60px" class="adminBtn" hoverClass="adminBtnHover" type="button" value="下一页>" onclick="adminQuery(this,'down')"></input></td></tr>
				</tbody>
			</table>
		</div>
		<div class="adminTableContainer">
			<ul class="adminTableQuery" url="adminUser.json">
				<li class="adminTableTitle">正常用户</li>
				<li class="adminLi">I &nbsp&nbsp D：<input class="adminInput" focusclass="adminInputFocus" type="text" name="id"></input> 昵称：<input class="adminInput" focusclass="adminInputFocus" type="text" name="name"></input></li>
				<li class="adminLi">地区：<input class="adminInput" focusclass="adminInputFocus" type="text" name="province"></input> I &nbsp &nbsp P：<input class="adminInput" focusclass="adminInputFocus" type="text" name="ip"></input></li>
				<li class="adminBtns"><input class="adminBtn" hoverClass="adminBtnHover" type="button" value="查询" onclick="adminQuery(this)"></input><input class="adminBtn" hoverClass="adminBtnHover" type="button" value="添加" onclick="openWnd('admin_movieAction.html')"></input></li>
			</ul>
			<table class="adminTable">
				<thead><tr><th style="width:20px">id</th><th>昵称</th><th style="width:55px">地区</th><th style="width:150px">ip</th><th style="width:90px">操作</th></tr></thead>
				<tbody>
					<tr hoverClass="adminTrHover"><td>1</td><td style="text-align:left;padding-left:10px">系统禁用ip</td><td>海南</td><td style="text-align:left;padding-left:10px">192.168.3.28</td><td>跟踪 禁止 删除</td></tr>
					<tr hoverClass="adminTrHover"><td>2</td><td style="text-align:left;padding-left:10px">神的天堂</td><td>海南</td><td style="text-align:left;padding-left:10px">192.168.3.28</td><td>跟踪 禁止 删除</td></tr>
					<tr hoverClass="adminTrHover"><td>3</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>海南</td><td style="text-align:left;padding-left:10px">192.168.3.28</td><td>跟踪 禁止 删除</td></tr>
					<tr hoverClass="adminTrHover"><td>4</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>海南</td><td style="text-align:left;padding-left:10px">192.168.3.28</td><td>跟踪 禁止 删除</td></tr>
					<tr hoverClass="adminTrHover"><td>5</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>海南</td><td style="text-align:left;padding-left:10px">192.168.3.28</td><td>跟踪 禁止 删除</td></tr>
					<tr hoverClass="adminTrHover"><td>6</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>上海</td><td style="text-align:left;padding-left:10px">192.168.3.12</td><td>跟踪 禁止 删除</td></tr>
					<tr hoverClass="adminTrHover"><td>7</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>北京</td><td style="text-align:left;padding-left:10px">192.168.3.28</td><td>跟踪 禁止 删除</td></tr>
					<tr hoverClass="adminTrHover"><td>8</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>上海</td><td style="text-align:left;padding-left:10px">192.168.3.12</td><td>跟踪 禁止 删除</td></tr>
					<tr hoverClass="adminTrHover"><td>9</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>上海</td><td style="text-align:left;padding-left:10px">192.168.3.12</td><td>跟踪 禁止 删除</td></tr>
					<tr hoverClass="adminTrHover"><td>10</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>北京</td><td style="text-align:left;padding-left:10px">192.168.3.12</td><td>跟踪 禁止 删除</td></tr>
					<tr hoverClass="adminTrHover"><td>11</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>北京</td><td style="text-align:left;padding-left:10px">192.168.3.12</td><td>跟踪 禁止 删除</td></tr>
					<tr hoverClass="adminTrHover"><td>12</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>北京</td><td style="text-align:left;padding-left:10px">192.168.3.12</td><td>跟踪 禁止 删除</td></tr>
					<tr hoverClass="adminTrHover"><td>13</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>北京</td><td style="text-align:left;padding-left:10px">192.168.3.12</td><td>跟踪 禁止 删除</td></tr>
					<tr hoverClass="adminTrHover"><td>14</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>北京</td><td style="text-align:left;padding-left:10px">192.168.3.12</td><td>跟踪 禁止 删除</td></tr>
					<tr hoverClass="adminTrHover"><td>15</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>北京</td><td style="text-align:left;padding-left:10px">192.168.3.1</td><td>跟踪 禁止 删除</td></tr>
					<tr hoverClass="adminTrHover"><td>16</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>云南</td><td style="text-align:left;padding-left:10px">192.168.3.12</td><td>跟踪 禁止 删除</td></tr>
					<tr hoverClass="adminTrHover"><td>17</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>北京</td><td style="text-align:left;padding-left:10px">192.168.3.12</td><td>跟踪 禁止 删除</td></tr>
					<tr hoverClass="adminTrHover"><td>18</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>北京</td><td style="text-align:left;padding-left:10px">192.168.3.12</td><td>跟踪 禁止 删除</td></tr>
					<tr hoverClass="adminTrHover"><td>19</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>云南</td><td style="text-align:left;padding-left:10px">192.168.3.12</td><td>跟踪 禁止 删除</td></tr>
					<tr hoverClass="adminTrHover"><td>20</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>北京</td><td style="text-align:left;padding-left:10px">192.168.3.12</td><td>跟踪 禁止 删除</td></tr>
					<tr hoverClass="adminTrHover"><td>21</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>北京</td><td style="text-align:left;padding-left:10px">192.168.3.12</td><td>跟踪 禁止 删除</td></tr>
					<tr hoverClass="adminTrHover"><td>22</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>北京</td><td style="text-align:left;padding-left:10px">192.168.3.12</td><td>跟踪 禁止 删除</td></tr>
					<tr hoverClass="adminTrHover"><td>23</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>海南</td><td style="text-align:left;padding-left:10px">192.168.3.12</td><td>跟踪 禁止 删除</td></tr>
					<tr hoverClass="adminTrHover"><td>24</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>海南</td><td style="text-align:left;padding-left:10px">192.168.3.1</td><td>跟踪 禁止 删除</td></tr>
					<tr hoverClass="adminTrHover"><td>25</td><td style="text-align:left;padding-left:10px">大闹天宫</td><td>海南</td><td style="text-align:left;padding-left:10px">192.168.3.12</td><td>跟踪 禁止 删除</td></tr>
					<tr height="40" valign="middle"><td colspan="20" style="text-align:right;background:#F1F8FE"><input  style="width:60px" class="adminBtn" hoverClass="adminBtnHover" type="button" value="<上一页" onclick="adminQuery(this,'up')"></input><input style="width:60px" class="adminBtn" hoverClass="adminBtnHover" type="button" value="下一页>" onclick="adminQuery(this,'down')"></input></td></tr>
				</tbody>
			</table>
		</div>
		
		
	</div>
</div>


<%@ include file="foot.jsp" %>