<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link type="image/x-icon" href="../img/favicon.ico" rel="shortcut icon">
	<link type="text/css" rel="stylesheet" href="../css/all.css"></link>
	<script type="text/javascript" src="../js/jquery-1.10.1.min.js"></script>
	<script type="text/javascript" src="../js/md5.js"></script>
	<script type="text/javascript" src="../js/index.js"></script>
	<title>影集网</title>
</head>
<body style="background:#F3F3F3">
<div id="wrapper">
	<div id="logo">
		<div style="float:left">
			<a href="http://www.baidu.com" class="logoA"><div class="logo logoPos"></div></a>
		</div>
		<div style="float:right">
			<div class="search searchExt" hoverClass="searchHover"><input class="searchText" /></div>
			<a href="javascript:goSearch();" class="searchIcon searchIconExt"></a>
		</div>
	</div>
	<div id="main">
		<div id="show"></div>
		<div id="movie">
			<div class="title" hoverClass="titleHover" onclick="togMovie()">
				<div class="groupArrow2 extraGArrow"></div>
				<p class="des">高清电影</p>
			</div>
			<div class="movieContent" style="display:none">
				<div>
					<div class="btnAct btnHelp" onclick="sure(this,'movie')">确定</div>	
					<div class="checkboxes">
						<input type="checkbox" value="480p"  checked="checked"/><span style="color:gray">480p&nbsp</span>
						<input type="checkbox" value="720p"  checked="checked"/><span style="color:gray">720p&nbsp</span>
						<input type="checkbox" value="1080p" checked="checked"/><span style="color:gray">1080p&nbsp</span>
						<input type="checkbox" value="other" checked="checked"/><span style="color:gray">其它&nbsp&nbsp</span>
					</div>
				</div>
				<div id="movie_480p_720p_1080p_1" totalCount="10" pageSize="5" style="clear:both;">
					<div class="item itemFirst">
						<em>【480p】</em>
						<a href="#movie" target="_blank">忠烈杨家将1忠烈杨家将1忠烈杨家将1忠烈杨家将1忠烈杨家将1忠烈杨家将1忠烈杨家将1忠烈杨家将1忠烈杨家将1忠烈杨家将1忠烈杨家将1忠烈杨家将1忠烈杨家将1忠烈杨家将1忠烈杨家将1忠烈杨家将1111111111111111111111111111111111111111111</a>
						<span class="star" style="display:inline-block"></span>
					</div>
					<div class="item">
						<em>【720p】 </em>
						<a href="#movie" target="_blank">忠烈杨家将1忠烈杨家将1忠烈杨家将1忠烈杨家将1忠烈杨家将1忠烈杨家将1忠烈杨家将1忠烈杨家将</a>
						<span class="star" style="display:inline-block"></span>
					</div>
					<div class="item">
						<em>【1080p】 </em>
						<a href="#movie" target="_blank">忠烈杨家将1忠烈杨家将1忠烈杨家将1忠烈杨家将1忠烈杨家将1忠烈杨家将1忠烈杨家将1忠烈杨家将1忠烈杨家将1忠烈杨家将1忠烈杨家将1忠烈杨家将1忠烈杨家将1忠烈杨家将1忠烈杨家将1忠烈杨家将1111111111111111111111111111111111111111111</a>
						<span class="fire" style="display:inline-block"></span>
					</div>
					<div class="pageDiv">
						<a href="#movie_480p_1" class="page firstPage">首页</a><a href="#movie_480p_1" class="page">上一页</a>
						<a href="#movie_480p_1" class="page">3</a>
						<a href="#movie_480p_1" class="page">4</a>
						<a href="#movie_480p_1" class="page">5</a>
						<a href="#movie_480p_1" class="page pageClick">6</a>
						<a href="#movie_480p_1" class="page">7</a>
						<a href="#movie_480p_1" class="page">...10</a>
						<a href="#movie_480p_1" class="page">下一页</a>
						<a href="#movie_480p_18" class="page">尾页</a>
					</div>
				</div>
			</div>
			<div class="doSearch" style="display:none">
				<img src="../img/search.gif" />
			</div>
			<div class="networkError" style="display:none">
				<p>网络发生错误请重新刷新</p>
			</div>
		</div>
		<div id="tv">
			<div class="title" hoverClass="titleHover" onclick="togTv()">
				<div class="groupArrow2 extraGArrow"></div>
				<p class="des">电视剧连载</p>
			</div>
			<div class="tvContent" style="display:none">
				<div>
					<div class="btnAct btnHelp" onclick="sure(this,'tv')">确定</div>	
					<div class="checkboxes">
						<input type="checkbox" value="america" checked="checked"/>美剧&nbsp
						<input type="checkbox" value="korea" checked="checked"/>韩剧&nbsp
						<input type="checkbox" value="hongkong" checked="checked"/>港台&nbsp
						<input type="checkbox" value="other" checked="checked"/>其它&nbsp&nbsp
					</div>
				</div>
			</div>
			<div class="doSearch" style="display:none">
				<img src="../img/search.gif" />
			</div>
			<div class="networkError" style="display:none">
				<p>网络发生错误请重新刷新</p>
			</div>
		</div>
		<div id="music">
			<div class="title" hoverClass="titleHover" onclick="togMusic()">
				<div class="groupArrow2 extraGArrow"></div>
				<p class="des">音乐欣赏</p>
			</div>
			<div class="musicContent" style="display:none">
				<div>
					<div class="btnAct btnHelp" onclick="sure(this,'music')">确定</div>	
					<div class="checkboxes">
						<input type="checkbox" value="mp3" checked="checked"/>mp3&nbsp
						<input type="checkbox" value="mv" checked="checked"/>mv&nbsp
						<input type="checkbox" value="dvd" checked="checked"/>dvd&nbsp
						<input type="checkbox" value="concert" checked="checked"/>演唱会&nbsp
						<input type="checkbox" value="other" checked="checked"/>其它&nbsp&nbsp
					</div>
				</div>
			</div>
			<div class="doSearch" style="display:none">
				<img src="../img/search.gif" />
			</div>
			<div class="networkError" style="display:none">
				<p>网络发生错误请重新刷新</p>
			</div>
		</div>
		<div id="extra">
			<div class="title" hoverClass="titleHover" onclick="togExtra()">
				<div class="groupArrow2 extraGArrow"></div>
				<p class="des">其它资源</p>
			</div>
			<div class="extraContent" style="display:none">
				<div>
					<div class="btnAct btnHelp" onclick="sure(this,'extra')">确定</div>	
					<div class="checkboxes">
						<input type="checkbox" value="yugao" checked="checked"/>新片预告&nbsp
						<input type="checkbox" value="favorite" checked="checked"/>最高得分&nbsp
						<input type="checkbox" value="better" checked="checked"/>好样的&nbsp
						<input type="checkbox" value="other" checked="checked"/>其它&nbsp&nbsp
					</div>
				</div>
			</div>
			<div class="doSearch" style="display:none">
				<img src="../img/search.gif" />
			</div>
			<div class="networkError" style="display:none">
				<p>网络发生错误请重新刷新</p>
			</div>
		</div>
	</div>
	<div id="search" style="display:none">
		<div class="title">
			<p class="searchAct">正在搜索</p>
			<a class="searchClose" href="javascript:searchCancel()">X</a>
		</div>
		<div id="search_${md5}" nextQuery="2012.12.01 01:00:03">
			<div class="resultItem">
				<a href="#movie_480p">
					<p class="resultTitle">指<span style="color:red">环</span>王2011年最火热的电影</p>
					<p class="resultDes">魔戒又称指环王（TheLordoftheRings），是英国作家约翰·罗纳德·鲁埃尔·托尔金的史诗奇幻小说，最初在1954年至1955年之间出版，是托尔金早期作品《霍比特人》（TheHobbit）的续作，在内容的深度和广度上都得到了扩展，目前已被翻译成38种语言。后来被改编拍摄为同名电影，也...</p>
					<img src="../img/search_result1.jpg" />
					<img src="../img/search_result2.jpg" />
					<img src="../img/search_result3.jpg" />
					<img src="../img/search_result4.jpg" />
				</a>
			</div>
			<div class="resultItem">
				<a href="#movie_480p">
					<p class="resultTitle">指<span style="color:red">环</span>王2011年最火热的电影</p>
					<p class="resultDes">魔戒又称指环王（TheLordoftheRings），是英国作家约翰·罗纳德·鲁埃尔·托尔金的史诗奇幻小说，最初在1954年至1955年之间出版，是托尔金早期作品《霍比特人》（TheHobbit）的续作，在内容的深度和广度上都得到了扩展，目前已被翻译成38种语言。后来被改编拍摄为同名电影，也...</p>
					<img src="../img/search_result1.jpg" />
					<img src="../img/search_result2.jpg" />
					<img src="../img/search_result3.jpg" />
					<img src="../img/search_result4.jpg" />
				</a>
			</div>
			<div class="resultItem">
				<a href="#movie_480p">
					<p class="resultTitle">指<span style="color:red">环</span>王2011年最火热的电影</p>
					<p class="resultDes">魔戒又称指环王（TheLordoftheRings），是英国作家约翰·罗纳德·鲁埃尔·托尔金的史诗奇幻小说，最初在1954年至1955年之间出版，是托尔金早期作品《霍比特人》（TheHobbit）的续作，在内容的深度和广度上都得到了扩展，目前已被翻译成38种语言。后来被改编拍摄为同名电影，也...</p>
					<img src="../img/search_result1.jpg" />
					<img src="../img/search_result2.jpg" />
					<img src="../img/search_result3.jpg" />
					<img src="../img/search_result4.jpg" />
				</a>
			</div>
		</div>
		<div id="searchGif" style="text-align:center;display:none">
			<div style="display:inline-block"><img src="../img/search.gif" /></div>
		</div>
		<div id="searchMore" style="text-align:center;display:none">
			<a href="#aaa" class="more moreText" style="display:inline-block">获得更多...</a>
		</div>
		<div class="networkError" style="display:none">
			<p>网络发生错误请重新刷新</p>
		</div>
	</div>
</div>
<div style="background:#2892c9;margin-top:100px;display:none">
	<div class="logo"></div>
	<div class="search"></div>
	<div class="searchActive"></div>
	<div class="searchIcon"></div>
	<div class="searchIconAct"></div>
	<div class="leftArrow"></div>
	<div class="leftArrowAct"></div>
	<div class="rightArrow"></div>
	<div class="rightArrowAct"></div>
	<div class="groupArrow1"></div>
	<div class="groupArrow2"></div>
	<div class="btnUnAct"></div>
	<div class="btnAct"></div>
	<div class="pageBtnPre"></div>
	<div class="pageBtnNext"></div>
	<div class="pageBtn1"></div>
	<div class="pageBtn2"></div>
	<div class="pageBtn3"></div>
	<div class="topicPre"></div>
	<div class="topicPreAct"></div>
	<div class="topicNext"></div>
	<div class="topicNextAct"></div>
	<div class="talkArrow"></div>
	<div class="more"></div>
</div>
</body>
</html>