<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<% pageContext.setAttribute("focusIndex", 3);  %>
<%@ include file="head.jsp" %>

<div class="movieDetail">
	<span class="movieDLabel">英美</span>
	<div class="movieDLine"></div>
	<div class="movieDCont">
		<div url="movie.json?type=america" pageNo="1">
			<div class="mvOption">
				<input type="radio" name="pageType" value="14" checked="checked" onclick="findMtv($(this).parent().parent())"></input>14条/页
				<input type="radio" name="pageType" value="28" onclick="findMtv($(this).parent().parent())"></input>28条/页
				<a class="mvBtn" href="javascript:void(0)" onclick="sortMtv(this)" sort="desc"><span class="mvArrow">↓ </span>按时间</a>
				<a class="mvBtn" href="javascript:void(0)" onclick="sortMtv(this)" sort="asc"><span class="mvArrow">↑ </span>按评分</a>
			</div>
			<div>
				<ul class="mvItems">
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li style="clear:both"></li>
				</ul>
				<div class="mvPageNav">
					<a class="mvAction" href="javascript:void(0)" onclick="pageMtv(this)">&lt; 上一页</a>
					<a class="mvAction" href="javascript:void(0)" onclick="pageMtv(this)">下一页 &gt;</a>
				</div>
			</div>
		</div>
		<div class="clear"></div>
	</div>
	<div class="clear"></div>
</div>
<div class="movieDetail">
	<span class="movieDLabel">日韩</span>
	<div class="movieDLine"></div>
	<div class="movieDCont">
		<div url="movie.json?type=japan" pageNo="1">
			<div class="mvOption">
				<input type="radio" name="pageType" value="14" checked="checked" onclick="findMtv($(this).parent().parent())"></input>14条/页
				<input type="radio" name="pageType" value="28" onclick="findMtv($(this).parent().parent())"></input>28条/页
				<a class="mvBtn" href="javascript:void(0)" onclick="sortMtv(this)" sort="desc"><span class="mvArrow">↓ </span>按时间</a>
				<a class="mvBtn" href="javascript:void(0)" onclick="sortMtv(this)" sort="asc"><span class="mvArrow">↑ </span>按评分</a>
			</div>
			<div>
				<ul class="mvItems">
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li style="clear:both"></li>
				</ul>
				<div class="mvPageNav">
					<a class="mvAction" href="javascript:void(0)" onclick="pageMtv(this)">&lt; 上一页</a>
					<a class="mvAction" href="javascript:void(0)" onclick="pageMtv(this)">下一页 &gt;</a>
				</div>
			</div>
		</div>
		<div class="clear"></div>
	</div>
	<div class="clear"></div>
</div>
<div class="movieDetail">
	<span class="movieDLabel">港台</span>
	<div class="movieDLine"></div>
	<div class="movieDCont">
		<div url="movie.json?type=hongkong" pageNo="1">
			<div class="mvOption">
				<input type="radio" name="pageType" value="14" checked="checked" onclick="findMtv($(this).parent().parent())"></input>14条/页
				<input type="radio" name="pageType" value="28" onclick="findMtv($(this).parent().parent())"></input>28条/页
				<a class="mvBtn" href="javascript:void(0)" onclick="sortMtv(this)" sort="desc"><span class="mvArrow">↓ </span>按时间</a>
				<a class="mvBtn" href="javascript:void(0)" onclick="sortMtv(this)" sort="asc"><span class="mvArrow">↑ </span>按评分</a>
			</div>
			<div>
				<ul class="mvItems">
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li style="clear:both"></li>
				</ul>
				<div class="mvPageNav">
					<a class="mvAction" href="javascript:void(0)" onclick="pageMtv(this)">&lt; 上一页</a>
					<a class="mvAction" href="javascript:void(0)" onclick="pageMtv(this)">下一页 &gt;</a>
				</div>
			</div>
		</div>
		<div class="clear"></div>
	</div>
	<div class="clear"></div>
</div>
<div class="movieDetail">
	<span class="movieDLabel">内地</span>
	<div class="movieDLine"></div>
	<div class="movieDCont">
		<div url="movie.json?type=china" pageNo="1">
			<div class="mvOption">
				<input type="radio" name="pageType" value="14" checked="checked" onclick="findMtv($(this).parent().parent())"></input>14条/页
				<input type="radio" name="pageType" value="28" onclick="findMtv($(this).parent().parent())"></input>28条/页
				<a class="mvBtn" href="javascript:void(0)" onclick="sortMtv(this)" sort="desc"><span class="mvArrow">↓ </span>按时间</a>
				<a class="mvBtn" href="javascript:void(0)" onclick="sortMtv(this)" sort="asc"><span class="mvArrow">↑ </span>按评分</a>
			</div>
			<div>
				<ul class="mvItems">
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li class="mvItem" title="《奇皇后》"><span class="rankScore">7.4</span><a href="content.html?id=1" class="mvItemDes"><img src="./img/blank150x220.gif" width="120px" height="176" />《奇皇后》</a></li>
					<li style="clear:both"></li>
				</ul>
				<div class="mvPageNav">
					<a class="mvAction" href="javascript:void(0)" onclick="pageMtv(this)">&lt; 上一页</a>
					<a class="mvAction" href="javascript:void(0)" onclick="pageMtv(this)">下一页 &gt;</a>
				</div>
			</div>
		</div>
		<div class="clear"></div>
	</div>
	<div class="clear"></div>
</div>

<%@ include file="foot.jsp" %>