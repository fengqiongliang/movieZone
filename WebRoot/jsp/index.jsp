<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<% pageContext.setAttribute("focusIndex", 1);  %>
<%@ include file="head.jsp" %>

<div class="movie">
	<span class="movieLabel">电影</span>
	<div class="movieCont">
		<ul class="movieItem">
			<li class="mItem mItemClick" hoverClass="mItemHover" otherClass="mItemClick" onclick="mvClick(this)">480p</li>
			<li class="mItem" hoverClass="mItemHover" otherClass="mItemClick" onclick="mvClick(this)">720p</li>
			<li class="mItem" hoverClass="mItemHover" otherClass="mItemClick" onclick="mvClick(this)">1080p</li>
			<li class="mItem" hoverClass="mItemHover" otherClass="mItemClick" onclick="mvClick(this)">其它</li>
		</ul>
		<div class="movieShow">
			<ul>
				<li class="showBig">
					<a href="content.html?id=1" title="《邪恶的力量 第九秀》"><img src="./img/blank150x220.gif" style="float:left" width="115px" height="169px"/></a>
					<div style="float:left">
						<a href="content.html?id=1" class="sBigTitle" title="《邪恶的力量 第九秀》">《邪恶的力量 第九秀》</a>
						<a href="content.html?id=1" class="sBigDes" title="欧美版“聊斋”，各方妖魔鬼怪大过招，英俊打鬼兄弟基情四射。">欧美版“聊斋”，各方妖魔鬼怪大过招，英俊打鬼兄弟基情四射。</a>
					</div>
				</li>
				<li class="showBig">
					<a href="content.html?id=1" title="《邪恶的力量 第九秀》"><img src="./img/blank150x220.gif" style="float:left" width="115px" height="169px"/></a>
					<div style="float:left">
						<a href="content.html?id=1" class="sBigTitle" title="《邪恶的力量 第九秀》">《邪恶的力量 第九秀》</a>
						<a href="content.html?id=1" class="sBigDes" title="欧美版“聊斋”，各方妖魔鬼怪大过招，英俊打鬼兄弟基情四射。">欧美版“聊斋”，各方妖魔鬼怪大过招，英俊打鬼兄弟基情四射。</a>
					</div>
				</li>
			</ul>
			<ul>
				<li class="showSmall">
					<a href="content.html?id=1" title="《奇皇后》"><img src="./img/blank150x220.gif" width="90px" height="132px" /></a>
					<a href="content.html?id=2" title="《奇皇后》" class="sSmallDes">《奇皇后》《奇皇后》《奇皇后》《奇皇后》《奇皇后》</a>
				</li>
				<li class="showSmall">
					<a href="content.html?id=1" title="《奇皇后》"><img src="./img/blank150x220.gif" width="90px" height="132px" /></a>
					<a href="content.html?id=2" title="《奇皇后》" class="sSmallDes">《奇皇后》《奇皇后》《奇皇后》《奇皇后》《奇皇后》</a>
				</li>
				<li class="showSmall">
					<a href="content.html?id=1" title="《奇皇后》"><img src="./img/blank150x220.gif" width="90px" height="132px" /></a>
					<a href="content.html?id=2" title="《奇皇后》" class="sSmallDes">《奇皇后》《奇皇后》《奇皇后》《奇皇后》《奇皇后》</a>
				</li>
				<li class="showSmall">
					<a href="content.html?id=1" title="《奇皇后》"><img src="./img/blank150x220.gif" width="90px" height="132px" /></a>
					<a href="content.html?id=2" title="《奇皇后》" class="sSmallDes">《奇皇后》《奇皇后》《奇皇后》《奇皇后》《奇皇后》</a>
				</li>
				<li class="showSmall">
					<a href="content.html?id=1" title="《奇皇后》"><img src="./img/blank150x220.gif" width="90px" height="132px" /></a>
					<a href="content.html?id=2" title="《奇皇后》" class="sSmallDes">《奇皇后》《奇皇后》《奇皇后》《奇皇后》《奇皇后》</a>
				</li>
			</ul>
		</div>
		<div class="movieShow" style="display:none">
			<ul>
				<li class="showBig">
					<a href="content.html?id=1" title="《邪恶的力量 第九秀》"><img src="./img/blank150x220.gif" style="float:left" width="115px" height="169px"/></a>
					<div style="float:left">
						<a href="content.html?id=1" class="sBigTitle" title="《邪恶的力量 第九秀》">《邪恶的力量 第九秀》</a>
						<a href="content.html?id=1" class="sBigDes" title="欧美版“聊斋”，各方妖魔鬼怪大过招，英俊打鬼兄弟基情四射。">欧美版“聊斋”，各方妖魔鬼怪大过招，英俊打鬼兄弟基情四射。</a>
					</div>
				</li>
				<li class="showBig">
					<a href="content.html?id=1" title="《邪恶的力量 第九秀》"><img src="./img/blank150x220.gif" style="float:left" width="115px" height="169px"/></a>
					<div style="float:left">
						<a href="content.html?id=1" class="sBigTitle" title="《邪恶的力量 第九秀》">《邪恶的力量 第九秀》</a>
						<a href="content.html?id=1" class="sBigDes" title="欧美版“聊斋”，各方妖魔鬼怪大过招，英俊打鬼兄弟基情四射。">欧美版“聊斋”，各方妖魔鬼怪大过招，英俊打鬼兄弟基情四射。</a>
					</div>
				</li>
			</ul>
			<ul>
				<li class="showSmall">
					<a href="content.html?id=1" title="《奇皇后》"><img src="./img/blank150x220.gif" width="90px" height="132px" /></a>
					<a href="content.html?id=2" title="《奇皇后》" class="sSmallDes">《奇皇后》《奇皇后》《奇皇后》《奇皇后》《奇皇后》</a>
				</li>
				<li class="showSmall">
					<a href="content.html?id=1" title="《奇皇后》"><img src="./img/blank150x220.gif" width="90px" height="132px" /></a>
					<a href="content.html?id=2" title="《奇皇后》" class="sSmallDes">《奇皇后》《奇皇后》《奇皇后》《奇皇后》《奇皇后》</a>
				</li>
				<li class="showSmall">
					<a href="content.html?id=1" title="《奇皇后》"><img src="./img/blank150x220.gif" width="90px" height="132px" /></a>
					<a href="content.html?id=2" title="《奇皇后》" class="sSmallDes">《奇皇后》《奇皇后》《奇皇后》《奇皇后》《奇皇后》</a>
				</li>
				<li class="showSmall">
					<a href="content.html?id=1" title="《奇皇后》"><img src="./img/blank150x220.gif" width="90px" height="132px" /></a>
					<a href="content.html?id=2" title="《奇皇后》" class="sSmallDes">《奇皇后》《奇皇后》《奇皇后》《奇皇后》《奇皇后》</a>
				</li>
				<li class="showSmall">
					<a href="content.html?id=1" title="《奇皇后》"><img src="./img/blank150x220.gif" width="90px" height="132px" /></a>
					<a href="content.html?id=2" title="《奇皇后》" class="sSmallDes">《奇皇后》《奇皇后》《奇皇后》《奇皇后》《奇皇后》</a>
				</li>
			</ul>
		</div>
		<div class="movieShow" style="display:none">
			<ul>
				<li class="showBig">
					<a href="content.html?id=1" title="《邪恶的力量 第九秀》"><img src="./img/blank150x220.gif" style="float:left" width="115px" height="169px"/></a>
					<div style="float:left">
						<a href="content.html?id=1" class="sBigTitle" title="《邪恶的力量 第九秀》">《邪恶的力量 第九秀》</a>
						<a href="content.html?id=1" class="sBigDes" title="欧美版“聊斋”，各方妖魔鬼怪大过招，英俊打鬼兄弟基情四射。">欧美版“聊斋”，各方妖魔鬼怪大过招，英俊打鬼兄弟基情四射。</a>
					</div>
				</li>
				<li class="showBig">
					<a href="content.html?id=1" title="《邪恶的力量 第九秀》"><img src="./img/blank150x220.gif" style="float:left" width="115px" height="169px"/></a>
					<div style="float:left">
						<a href="content.html?id=1" class="sBigTitle" title="《邪恶的力量 第九秀》">《邪恶的力量 第九秀》</a>
						<a href="content.html?id=1" class="sBigDes" title="欧美版“聊斋”，各方妖魔鬼怪大过招，英俊打鬼兄弟基情四射。">欧美版“聊斋”，各方妖魔鬼怪大过招，英俊打鬼兄弟基情四射。</a>
					</div>
				</li>
			</ul>
			<ul>
				<li class="showSmall">
					<a href="content.html?id=1" title="《奇皇后》"><img src="./img/blank150x220.gif" width="90px" height="132px" /></a>
					<a href="content.html?id=2" title="《奇皇后》" class="sSmallDes">《奇皇后》《奇皇后》《奇皇后》《奇皇后》《奇皇后》</a>
				</li>
				<li class="showSmall">
					<a href="content.html?id=1" title="《奇皇后》"><img src="./img/blank150x220.gif" width="90px" height="132px" /></a>
					<a href="content.html?id=2" title="《奇皇后》" class="sSmallDes">《奇皇后》《奇皇后》《奇皇后》《奇皇后》《奇皇后》</a>
				</li>
				<li class="showSmall">
					<a href="content.html?id=1" title="《奇皇后》"><img src="./img/blank150x220.gif" width="90px" height="132px" /></a>
					<a href="content.html?id=2" title="《奇皇后》" class="sSmallDes">《奇皇后》《奇皇后》《奇皇后》《奇皇后》《奇皇后》</a>
				</li>
				<li class="showSmall">
					<a href="content.html?id=1" title="《奇皇后》"><img src="./img/blank150x220.gif" width="90px" height="132px" /></a>
					<a href="content.html?id=2" title="《奇皇后》" class="sSmallDes">《奇皇后》《奇皇后》《奇皇后》《奇皇后》《奇皇后》</a>
				</li>
				<li class="showSmall">
					<a href="content.html?id=1" title="《奇皇后》"><img src="./img/blank150x220.gif" width="90px" height="132px" /></a>
					<a href="content.html?id=2" title="《奇皇后》" class="sSmallDes">《奇皇后》《奇皇后》《奇皇后》《奇皇后》《奇皇后》</a>
				</li>
			</ul>
		</div>
		<div class="movieShow" style="display:none">
			<ul>
				<li class="showBig">
					<a href="content.html?id=1" title="《邪恶的力量 第九秀》"><img src="./img/blank150x220.gif" style="float:left" width="115px" height="169px"/></a>
					<div style="float:left">
						<a href="content.html?id=1" class="sBigTitle" title="《邪恶的力量 第九秀》">《邪恶的力量 第九秀》</a>
						<a href="content.html?id=1" class="sBigDes" title="欧美版“聊斋”，各方妖魔鬼怪大过招，英俊打鬼兄弟基情四射。">欧美版“聊斋”，各方妖魔鬼怪大过招，英俊打鬼兄弟基情四射。</a>
					</div>
				</li>
				<li class="showBig">
					<a href="content.html?id=1" title="《邪恶的力量 第九秀》"><img src="./img/blank150x220.gif" style="float:left" width="115px" height="169px"/></a>
					<div style="float:left">
						<a href="content.html?id=1" class="sBigTitle" title="《邪恶的力量 第九秀》">《邪恶的力量 第九秀》</a>
						<a href="content.html?id=1" class="sBigDes" title="欧美版“聊斋”，各方妖魔鬼怪大过招，英俊打鬼兄弟基情四射。">欧美版“聊斋”，各方妖魔鬼怪大过招，英俊打鬼兄弟基情四射。</a>
					</div>
				</li>
			</ul>
			<ul>
				<li class="showSmall">
					<a href="content.html?id=1" title="《奇皇后》"><img src="./img/blank150x220.gif" width="90px" height="132px" /></a>
					<a href="content.html?id=2" title="《奇皇后》" class="sSmallDes">《奇皇后》《奇皇后》《奇皇后》《奇皇后》《奇皇后》</a>
				</li>
				<li class="showSmall">
					<a href="content.html?id=1" title="《奇皇后》"><img src="./img/blank150x220.gif" width="90px" height="132px" /></a>
					<a href="content.html?id=2" title="《奇皇后》" class="sSmallDes">《奇皇后》《奇皇后》《奇皇后》《奇皇后》《奇皇后》</a>
				</li>
				<li class="showSmall">
					<a href="content.html?id=1" title="《奇皇后》"><img src="./img/blank150x220.gif" width="90px" height="132px" /></a>
					<a href="content.html?id=2" title="《奇皇后》" class="sSmallDes">《奇皇后》《奇皇后》《奇皇后》《奇皇后》《奇皇后》</a>
				</li>
				<li class="showSmall">
					<a href="content.html?id=1" title="《奇皇后》"><img src="./img/blank150x220.gif" width="90px" height="132px" /></a>
					<a href="content.html?id=2" title="《奇皇后》" class="sSmallDes">《奇皇后》《奇皇后》《奇皇后》《奇皇后》《奇皇后》</a>
				</li>
				<li class="showSmall">
					<a href="content.html?id=1" title="《奇皇后》"><img src="./img/blank150x220.gif" width="90px" height="132px" /></a>
					<a href="content.html?id=2" title="《奇皇后》" class="sSmallDes">《奇皇后》《奇皇后》《奇皇后》《奇皇后》《奇皇后》</a>
				</li>
			</ul>
		</div>
		<div class="movieRank">
			<ul>
				<li class="topMovie" title="功夫"><span class="rank1">1</span><span class="rankScore">9.1</span><a href="content.html?id=1"><img src="./img/blank80x80.gif" /><p class="topMName">功夫1</p></a></li>
				<li class="topMovie" title="功夫"><span class="rank2">2</span><span class="rankScore">8.9</span><a href="content.html?id=1"><img src="./img/blank80x80.gif" /><p class="topMName">功夫1</p></a></li>
				<li class="topMovie" title="功夫"><span class="rank3">3</span><span class="rankScore">7.5</span><a href="content.html?id=1"><img src="./img/blank80x80.gif" /><p class="topMName">功夫1</p></a></li>
				<li class="clear"></li>
			</ul>
			<ul style="margin: 10px 15px 0 20px;">
				<li class="bottomMovie" title="权力的游戏"><span class="rank4">4</span><span class="btmMName"><a href="content.html?id=2">权力的游戏</a></span><span class="btmMScore">7.2</span></li>
				<li class="bottomMovie" title="权力的游戏"><span class="rank5">5</span><span class="btmMName"><a href="content.html?id=2">权力的游戏权力的游戏权力的</a></span><span class="btmMScore">7.2</span></li>
				<li class="bottomMovie" title="权力的游戏"><span class="rank6">6</span><span class="btmMName"><a href="content.html?id=2">权力的游戏</a></span><span class="btmMScore">7.2</span></li>
				<li class="bottomMovie" title="权力的游戏"><span class="rank7">7</span><span class="btmMName"><a href="content.html?id=2">权力的游戏</a></span><span class="btmMScore">7.2</span></li>
				<li class="bottomMovie" title="权力的游戏"><span class="rank8">8</span><span class="btmMName"><a href="content.html?id=2">权力的游戏</a></span><span class="btmMScore">7.2</span></li>
				<li class="bottomMovie" title="权力的游戏"><span class="rank9">9</span><span class="btmMName"><a href="content.html?id=2">权力的游戏</a></span><span class="btmMScore">7.2</span></li>
				<li class="bottomMovie" title="权力的游戏"><span class="rank10">10</span><span class="btmMName"><a href="content.html?id=2">权力的游戏</a></span><span class="btmMScore">7.2</span></li>
				<li class="bottomMovie" title="权力的游戏"><span class="rank11">11</span><span class="btmMName"><a href="content.html?id=2">权力的游戏</a></span><span class="btmMScore">7.2</span></li>
				<li class="bottomMovie" title="权力的游戏"><span class="rank12">12</span><span class="btmMName"><a href="content.html?id=2">权力的游戏</a></span><span class="btmMScore">7.2</span></li>
			</ul>
		</div>
		<div class="clear"></div>
	</div>
	<div class="clear"></div>
</div>
<div class="movie">
	<span class="movieLabel">电视剧</span>
	<div class="movieCont">
		<ul class="movieItem">
			<li class="mItem mItemClick" hoverClass="mItemHover" otherClass="mItemClick" onclick="mvClick(this)">英美</li>
			<li class="mItem" hoverClass="mItemHover" otherClass="mItemClick" onclick="mvClick(this)">日韩</li>
			<li class="mItem" hoverClass="mItemHover" otherClass="mItemClick" onclick="mvClick(this)">港台</li>
			<li class="mItem" hoverClass="mItemHover" otherClass="mItemClick" onclick="mvClick(this)">内地</li>
		</ul>
		<div class="movieShow">
			<ul>
				<li class="showBig">
					<a href="content.html?id=1" title="《邪恶的力量 第九秀》"><img src="./img/blank150x220.gif" style="float:left" width="115px" height="169px"/></a>
					<div style="float:left">
						<a href="content.html?id=1" class="sBigTitle" title="《邪恶的力量 第九秀》">《邪恶的力量 第九秀》</a>
						<a href="content.html?id=1" class="sBigDes" title="欧美版“聊斋”，各方妖魔鬼怪大过招，英俊打鬼兄弟基情四射。">欧美版“聊斋”，各方妖魔鬼怪大过招，英俊打鬼兄弟基情四射。</a>
					</div>
				</li>
				<li class="showBig">
					<a href="content.html?id=1" title="《邪恶的力量 第九秀》"><img src="./img/blank150x220.gif" style="float:left" width="115px" height="169px"/></a>
					<div style="float:left">
						<a href="content.html?id=1" class="sBigTitle" title="《邪恶的力量 第九秀》">《邪恶的力量 第九秀》</a>
						<a href="content.html?id=1" class="sBigDes" title="欧美版“聊斋”，各方妖魔鬼怪大过招，英俊打鬼兄弟基情四射。">欧美版“聊斋”，各方妖魔鬼怪大过招，英俊打鬼兄弟基情四射。</a>
					</div>
				</li>
			</ul>
			<ul>
				<li class="showSmall">
					<a href="content.html?id=1" title="《奇皇后》"><img src="./img/blank150x220.gif" width="90px" height="132px" /></a>
					<a href="content.html?id=2" title="《奇皇后》" class="sSmallDes">《奇皇后》《奇皇后》《奇皇后》《奇皇后》《奇皇后》</a>
				</li>
				<li class="showSmall">
					<a href="content.html?id=1" title="《奇皇后》"><img src="./img/blank150x220.gif" width="90px" height="132px" /></a>
					<a href="content.html?id=2" title="《奇皇后》" class="sSmallDes">《奇皇后》《奇皇后》《奇皇后》《奇皇后》《奇皇后》</a>
				</li>
				<li class="showSmall">
					<a href="content.html?id=1" title="《奇皇后》"><img src="./img/blank150x220.gif" width="90px" height="132px" /></a>
					<a href="content.html?id=2" title="《奇皇后》" class="sSmallDes">《奇皇后》《奇皇后》《奇皇后》《奇皇后》《奇皇后》</a>
				</li>
				<li class="showSmall">
					<a href="content.html?id=1" title="《奇皇后》"><img src="./img/blank150x220.gif" width="90px" height="132px" /></a>
					<a href="content.html?id=2" title="《奇皇后》" class="sSmallDes">《奇皇后》《奇皇后》《奇皇后》《奇皇后》《奇皇后》</a>
				</li>
				<li class="showSmall">
					<a href="content.html?id=1" title="《奇皇后》"><img src="./img/blank150x220.gif" width="90px" height="132px" /></a>
					<a href="content.html?id=2" title="《奇皇后》" class="sSmallDes">《奇皇后》《奇皇后》《奇皇后》《奇皇后》《奇皇后》</a>
				</li>
			</ul>
		</div>
		<div class="movieShow" style="display:none">
			<ul>
				<li class="showBig">
					<a href="content.html?id=1" title="《邪恶的力量 第九秀》"><img src="./img/blank150x220.gif" style="float:left" width="115px" height="169px"/></a>
					<div style="float:left">
						<a href="content.html?id=1" class="sBigTitle" title="《邪恶的力量 第九秀》">《邪恶的力量 第九秀》</a>
						<a href="content.html?id=1" class="sBigDes" title="欧美版“聊斋”，各方妖魔鬼怪大过招，英俊打鬼兄弟基情四射。">欧美版“聊斋”，各方妖魔鬼怪大过招，英俊打鬼兄弟基情四射。</a>
					</div>
				</li>
				<li class="showBig">
					<a href="content.html?id=1" title="《邪恶的力量 第九秀》"><img src="./img/blank150x220.gif" style="float:left" width="115px" height="169px"/></a>
					<div style="float:left">
						<a href="content.html?id=1" class="sBigTitle" title="《邪恶的力量 第九秀》">《邪恶的力量 第九秀》</a>
						<a href="content.html?id=1" class="sBigDes" title="欧美版“聊斋”，各方妖魔鬼怪大过招，英俊打鬼兄弟基情四射。">欧美版“聊斋”，各方妖魔鬼怪大过招，英俊打鬼兄弟基情四射。</a>
					</div>
				</li>
			</ul>
			<ul>
				<li class="showSmall">
					<a href="content.html?id=1" title="《奇皇后》"><img src="./img/blank150x220.gif" width="90px" height="132px" /></a>
					<a href="content.html?id=2" title="《奇皇后》" class="sSmallDes">《奇皇后》《奇皇后》《奇皇后》《奇皇后》《奇皇后》</a>
				</li>
				<li class="showSmall">
					<a href="content.html?id=1" title="《奇皇后》"><img src="./img/blank150x220.gif" width="90px" height="132px" /></a>
					<a href="content.html?id=2" title="《奇皇后》" class="sSmallDes">《奇皇后》《奇皇后》《奇皇后》《奇皇后》《奇皇后》</a>
				</li>
				<li class="showSmall">
					<a href="content.html?id=1" title="《奇皇后》"><img src="./img/blank150x220.gif" width="90px" height="132px" /></a>
					<a href="content.html?id=2" title="《奇皇后》" class="sSmallDes">《奇皇后》《奇皇后》《奇皇后》《奇皇后》《奇皇后》</a>
				</li>
				<li class="showSmall">
					<a href="content.html?id=1" title="《奇皇后》"><img src="./img/blank150x220.gif" width="90px" height="132px" /></a>
					<a href="content.html?id=2" title="《奇皇后》" class="sSmallDes">《奇皇后》《奇皇后》《奇皇后》《奇皇后》《奇皇后》</a>
				</li>
				<li class="showSmall">
					<a href="content.html?id=1" title="《奇皇后》"><img src="./img/blank150x220.gif" width="90px" height="132px" /></a>
					<a href="content.html?id=2" title="《奇皇后》" class="sSmallDes">《奇皇后》《奇皇后》《奇皇后》《奇皇后》《奇皇后》</a>
				</li>
			</ul>
		</div>
		<div class="movieShow" style="display:none">
			<ul>
				<li class="showBig">
					<a href="content.html?id=1" title="《邪恶的力量 第九秀》"><img src="./img/blank150x220.gif" style="float:left" width="115px" height="169px"/></a>
					<div style="float:left">
						<a href="content.html?id=1" class="sBigTitle" title="《邪恶的力量 第九秀》">《邪恶的力量 第九秀》</a>
						<a href="content.html?id=1" class="sBigDes" title="欧美版“聊斋”，各方妖魔鬼怪大过招，英俊打鬼兄弟基情四射。">欧美版“聊斋”，各方妖魔鬼怪大过招，英俊打鬼兄弟基情四射。</a>
					</div>
				</li>
				<li class="showBig">
					<a href="content.html?id=1" title="《邪恶的力量 第九秀》"><img src="./img/blank150x220.gif" style="float:left" width="115px" height="169px"/></a>
					<div style="float:left">
						<a href="content.html?id=1" class="sBigTitle" title="《邪恶的力量 第九秀》">《邪恶的力量 第九秀》</a>
						<a href="content.html?id=1" class="sBigDes" title="欧美版“聊斋”，各方妖魔鬼怪大过招，英俊打鬼兄弟基情四射。">欧美版“聊斋”，各方妖魔鬼怪大过招，英俊打鬼兄弟基情四射。</a>
					</div>
				</li>
			</ul>
			<ul>
				<li class="showSmall">
					<a href="content.html?id=1" title="《奇皇后》"><img src="./img/blank150x220.gif" width="90px" height="132px" /></a>
					<a href="content.html?id=2" title="《奇皇后》" class="sSmallDes">《奇皇后》《奇皇后》《奇皇后》《奇皇后》《奇皇后》</a>
				</li>
				<li class="showSmall">
					<a href="content.html?id=1" title="《奇皇后》"><img src="./img/blank150x220.gif" width="90px" height="132px" /></a>
					<a href="content.html?id=2" title="《奇皇后》" class="sSmallDes">《奇皇后》《奇皇后》《奇皇后》《奇皇后》《奇皇后》</a>
				</li>
				<li class="showSmall">
					<a href="content.html?id=1" title="《奇皇后》"><img src="./img/blank150x220.gif" width="90px" height="132px" /></a>
					<a href="content.html?id=2" title="《奇皇后》" class="sSmallDes">《奇皇后》《奇皇后》《奇皇后》《奇皇后》《奇皇后》</a>
				</li>
				<li class="showSmall">
					<a href="content.html?id=1" title="《奇皇后》"><img src="./img/blank150x220.gif" width="90px" height="132px" /></a>
					<a href="content.html?id=2" title="《奇皇后》" class="sSmallDes">《奇皇后》《奇皇后》《奇皇后》《奇皇后》《奇皇后》</a>
				</li>
				<li class="showSmall">
					<a href="content.html?id=1" title="《奇皇后》"><img src="./img/blank150x220.gif" width="90px" height="132px" /></a>
					<a href="content.html?id=2" title="《奇皇后》" class="sSmallDes">《奇皇后》《奇皇后》《奇皇后》《奇皇后》《奇皇后》</a>
				</li>
			</ul>
		</div>
		<div class="movieShow" style="display:none">
			<ul>
				<li class="showBig">
					<a href="content.html?id=1" title="《邪恶的力量 第九秀》"><img src="./img/blank150x220.gif" style="float:left" width="115px" height="169px"/></a>
					<div style="float:left">
						<a href="content.html?id=1"><h1 class="sBigTitle" title="《邪恶的力量 第九秀》">《邪恶的力量 第九秀》</h1></a>
						<a href="content.html?id=1" class="sBigDes" title="欧美版“聊斋”，各方妖魔鬼怪大过招，英俊打鬼兄弟基情四射。">欧美版“聊斋”，各方妖魔鬼怪大过招，英俊打鬼兄弟基情四射。</a>
					</div>
				</li>
				<li class="showBig">
					<a href="content.html?id=1" title="《邪恶的力量 第九秀》"><img src="./img/blank150x220.gif" style="float:left" width="115px" height="169px"/></a>
					<div style="float:left">
						<a href="content.html?id=1" class="sBigTitle" title="《邪恶的力量 第九秀》">《邪恶的力量 第九秀》</a>
						<a href="content.html?id=1" class="sBigDes" title="欧美版“聊斋”，各方妖魔鬼怪大过招，英俊打鬼兄弟基情四射。">欧美版“聊斋”，各方妖魔鬼怪大过招，英俊打鬼兄弟基情四射。</a>
					</div>
				</li>
			</ul>
			<ul>
				<li class="showSmall">
					<a href="content.html?id=1" title="《奇皇后》"><img src="./img/blank150x220.gif" width="90px" height="132px" /></a>
					<a href="content.html?id=2" title="《奇皇后》" class="sSmallDes">《奇皇后》《奇皇后》《奇皇后》《奇皇后》《奇皇后》</a>
				</li>
				<li class="showSmall">
					<a href="content.html?id=1" title="《奇皇后》"><img src="./img/blank150x220.gif" width="90px" height="132px" /></a>
					<a href="content.html?id=2" title="《奇皇后》" class="sSmallDes">《奇皇后》《奇皇后》《奇皇后》《奇皇后》《奇皇后》</a>
				</li>
				<li class="showSmall">
					<a href="content.html?id=1" title="《奇皇后》"><img src="./img/blank150x220.gif" width="90px" height="132px" /></a>
					<a href="content.html?id=2" title="《奇皇后》" class="sSmallDes">《奇皇后》《奇皇后》《奇皇后》《奇皇后》《奇皇后》</a>
				</li>
				<li class="showSmall">
					<a href="content.html?id=1" title="《奇皇后》"><img src="./img/blank150x220.gif" width="90px" height="132px" /></a>
					<a href="content.html?id=2" title="《奇皇后》" class="sSmallDes">《奇皇后》《奇皇后》《奇皇后》《奇皇后》《奇皇后》</a>
				</li>
				<li class="showSmall">
					<a href="content.html?id=1" title="《奇皇后》"><img src="./img/blank150x220.gif" width="90px" height="132px" /></a>
					<a href="content.html?id=2" title="《奇皇后》" class="sSmallDes">《奇皇后》《奇皇后》《奇皇后》《奇皇后》《奇皇后》</a>
				</li>
			</ul>
		</div>
		<div class="movieRank">
			<ul>
				<li class="topMovie" title="功夫"><span class="rank1">1</span><span class="rankScore">9.1</span><a href="content.html?id=1"><img src="./img/blank80x80.gif" /><p class="topMName">功夫1</p></a></li>
				<li class="topMovie" title="功夫"><span class="rank2">2</span><span class="rankScore">8.9</span><a href="content.html?id=1"><img src="./img/blank80x80.gif" /><p class="topMName">功夫1</p></a></li>
				<li class="topMovie" title="功夫"><span class="rank3">3</span><span class="rankScore">7.5</span><a href="content.html?id=1"><img src="./img/blank80x80.gif" /><p class="topMName">功夫1</p></a></li>
				<li class="clear"></li>
			</ul>
			<ul style="margin: 10px 15px 0 20px;">
				<li class="bottomMovie" title="权力的游戏"><span class="rank4">4</span><span class="btmMName"><a href="content.html?id=2">权力的游戏</a></span><span class="btmMScore">7.2</span></li>
				<li class="bottomMovie" title="权力的游戏"><span class="rank5">5</span><span class="btmMName"><a href="content.html?id=2">权力的游戏权力的游戏权力的</a></span><span class="btmMScore">7.2</span></li>
				<li class="bottomMovie" title="权力的游戏"><span class="rank6">6</span><span class="btmMName"><a href="content.html?id=2">权力的游戏</a></span><span class="btmMScore">7.2</span></li>
				<li class="bottomMovie" title="权力的游戏"><span class="rank7">7</span><span class="btmMName"><a href="content.html?id=2">权力的游戏</a></span><span class="btmMScore">7.2</span></li>
				<li class="bottomMovie" title="权力的游戏"><span class="rank8">8</span><span class="btmMName"><a href="content.html?id=2">权力的游戏</a></span><span class="btmMScore">7.2</span></li>
				<li class="bottomMovie" title="权力的游戏"><span class="rank9">9</span><span class="btmMName"><a href="content.html?id=2">权力的游戏</a></span><span class="btmMScore">7.2</span></li>
				<li class="bottomMovie" title="权力的游戏"><span class="rank10">10</span><span class="btmMName"><a href="content.html?id=2">权力的游戏</a></span><span class="btmMScore">7.2</span></li>
				<li class="bottomMovie" title="权力的游戏"><span class="rank11">11</span><span class="btmMName"><a href="content.html?id=2">权力的游戏</a></span><span class="btmMScore">7.2</span></li>
				<li class="bottomMovie" title="权力的游戏"><span class="rank12">12</span><span class="btmMName"><a href="content.html?id=2">权力的游戏</a></span><span class="btmMScore">7.2</span></li>
			</ul>
		</div>
		<div class="clear"></div>
	</div>
	<div class="clear"></div>
</div>
<div class="coverflow">
	<ul class="roundabout-holder">
		<a href="javascript:void(0);" class="raNext"><</a>
		<a href="javascript:void(0);" class="raPrev">></a>
		<li><a href="content.html?id=1" title="jfldsjlfjl"><img src="./img/blank220x169.gif" width="100%" height="100%"/></a></li>
		<li><a href="content.html?id=1" title="jfldsjlfjl"><img src="./img/blank220x169.gif" width="100%" height="100%"/></a></li>
		<li><a href="content.html?id=1" title="jfldsjlfjl"><img src="./img/blank220x169.gif" width="100%" height="100%"/></a></li>
		<li><a href="content.html?id=1" title="jfldsjlfjl"><img src="./img/blank220x169.gif" width="100%" height="100%"/></a></li>
		<li><a href="content.html?id=1" title="jfldsjlfjl"><img src="./img/blank220x169.gif" width="100%" height="100%"/></a></li>
		<li><a href="content.html?id=1" title="jfldsjlfjl"><img src="./img/blank220x169.gif" width="100%" height="100%"/></a></li>
		<li><a href="content.html?id=1" title="jfldsjlfjl"><img src="./img/blank220x169.gif" width="100%" height="100%"/></a></li>
		<li><a href="content.html?id=1" title="jfldsjlfjl"><img src="./img/blank220x169.gif" width="100%" height="100%"/></a></li>
		<li><a href="content.html?id=1" title="jfldsjlfjl"><img src="./img/blank220x169.gif" width="100%" height="100%"/></a></li>
		<li><a href="content.html?id=1" title="jfldsjlfjl"><img src="./img/blank220x169.gif" width="100%" height="100%"/></a></li>
		<li><a href="content.html?id=1" title="jfldsjlfjl"><img src="./img/blank220x169.gif" width="100%" height="100%"/></a></li>
		<li><a href="content.html?id=1" title="jfldsjlfjl"><img src="./img/blank220x169.gif" width="100%" height="100%"/></a></li>
		<li><a href="content.html?id=1" title="jfldsjlfjl"><img src="./img/blank220x169.gif" width="100%" height="100%"/></a></li>
	</ul>
	<div class="clear"></div>
</div>
<div class="guest" >
	<ul class="guestLabels">
		<li class="siterLabel">站长推荐</li>
	</ul>
	<ul class="guestItems">
		<li class="guestItem" title="变形金刚"><span class="rankScore">9.1</span><a href="content.html?id=1" class="guestDes"><img src="./img/blank150x220.gif" />变形金刚4</a></li>
		<li class="guestItem" title="变形金刚"><span class="rankScore">9.1</span><a href="content.html?id=1" class="guestDes"><img src="./img/blank150x220.gif" />变形金刚4</a></li>
		<li class="guestItem" title="变形金刚"><span class="rankScore">9.1</span><a href="content.html?id=1" class="guestDes"><img src="./img/blank150x220.gif" />变形金刚4</a></li>
		<li class="guestItem" title="变形金刚"><span class="rankScore">9.1</span><a href="content.html?id=1" class="guestDes"><img src="./img/blank150x220.gif" />变形金刚4</a></li>
		<li class="guestItem" title="变形金刚"><span class="rankScore">9.1</span><a href="content.html?id=1" class="guestDes"><img src="./img/blank150x220.gif" />变形金刚4</a></li>
		<li class="guestItem" title="变形金刚"><span class="rankScore">9.1</span><a href="content.html?id=1" class="guestDes"><img src="./img/blank150x220.gif" />变形金刚4</a></li>
		<li style="clear:both"></li>
	</ul>
	<div class="clear"></div>
</div>
<div class="guest">
	<ul class="guestLabels">
		<li class="guestLabel guestLClick" hoverClass="guestLabelHover" otherClass="guestLClick" onclick="guestLClick(this)">猜你喜欢</li>
		<li class="guestLabel" hoverClass="guestLabelHover" otherClass="guestLClick" onclick="guestLClick(this)">最近浏览</li>
		<li class="guestLabel" hoverClass="guestLabelHover" otherClass="guestLClick" onclick="guestLClick(this)">个人收藏</li>
		<li style="clear:both"></li>
	</ul>
	<div class="guestLine"></div>
	<ul class="guestItems">
		<li class="guestItem" title="变形金刚"><span class="rankScore">9.1</span><a href="content.html?id=1" class="guestDes" style=""><img src="./img/blank150x220.gif" />变形金刚4</a></li>
		<li class="guestItem" title="变形金刚"><span class="rankScore">9.1</span><a href="content.html?id=1" class="guestDes" style=""><img src="./img/blank150x220.gif" />变形金刚4</a></li>
		<li class="guestItem" title="变形金刚"><span class="rankScore">9.1</span><a href="content.html?id=1" class="guestDes" style=""><img src="./img/blank150x220.gif" />变形金刚4</a></li>
		<li class="guestItem" title="变形金刚"><span class="rankScore">9.1</span><a href="content.html?id=1" class="guestDes" style=""><img src="./img/blank150x220.gif" />变形金刚4</a></li>
		<li class="guestItem" title="变形金刚"><span class="rankScore">9.1</span><a href="content.html?id=1" class="guestDes" style=""><img src="./img/blank150x220.gif" />变形金刚4</a></li>
		<li class="guestItem" title="变形金刚"><span class="rankScore">9.1</span><a href="content.html?id=1" class="guestDes" style=""><img src="./img/blank150x220.gif" />变形金刚4</a></li>
		<li style="clear:both"></li>
	</ul>
	<ul class="guestItems" style="display:none">
		<li class="guestItem" title="变形金刚"><span class="rankScore">9.1</span><a href="content.html?id=1" class="guestDes"><img src="./img/blank150x220.gif" />变形金刚4</a></li>
		<li class="guestItem" title="变形金刚"><span class="rankScore">9.1</span><a href="content.html?id=1" class="guestDes"><img src="./img/blank150x220.gif" />变形金刚4</a></li>
		<li class="guestItem" title="变形金刚"><span class="rankScore">9.1</span><a href="content.html?id=1" class="guestDes"><img src="./img/blank150x220.gif" />变形金刚4</a></li>
		<li class="guestItem" title="变形金刚"><span class="rankScore">9.1</span><a href="content.html?id=1" class="guestDes"><img src="./img/blank150x220.gif" />变形金刚4</a></li>
		<li class="guestItem" title="变形金刚"><span class="rankScore">9.1</span><a href="content.html?id=1" class="guestDes"><img src="./img/blank150x220.gif" />变形金刚4</a></li>
		<li class="guestItem" title="变形金刚"><span class="rankScore">9.1</span><a href="content.html?id=1" class="guestDes"><img src="./img/blank150x220.gif" />变形金刚4</a></li>
		<li style="clear:both"></li>
	</ul>
	<ul class="guestItems" style="display:none">
		<li class="guestItem" title="变形金刚"><span class="rankScore">9.1</span><a href="content.html?id=1" class="guestDes"><img src="./img/blank150x220.gif" />变形金刚4</a></li>
		<li class="guestItem" title="变形金刚"><span class="rankScore">9.1</span><a href="content.html?id=1" class="guestDes"><img src="./img/blank150x220.gif" />变形金刚4</a></li>
		<li class="guestItem" title="变形金刚"><span class="rankScore">9.1</span><a href="content.html?id=1" class="guestDes"><img src="./img/blank150x220.gif" />变形金刚4</a></li>
		<li class="guestItem" title="变形金刚"><span class="rankScore">9.1</span><a href="content.html?id=1" class="guestDes"><img src="./img/blank150x220.gif" />变形金刚4</a></li>
		<li class="guestItem" title="变形金刚"><span class="rankScore">9.1</span><a href="content.html?id=1" class="guestDes"><img src="./img/blank150x220.gif" />变形金刚4</a></li>
		<li class="guestItem" title="变形金刚"><span class="rankScore">9.1</span><a href="content.html?id=1" class="guestDes"><img src="./img/blank150x220.gif" />变形金刚4</a></li>
		<li style="clear:both"></li>
	</ul>
	<div class="clear"></div>
</div>

<%@ include file="foot.jsp" %>