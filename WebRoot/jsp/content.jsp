<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link type="image/x-icon" href="../img/favicon.ico" rel="shortcut icon">
	<link type="text/css" rel="stylesheet" href="../css/all.css"></link>
	<script type="text/javascript" src="../js/jquery-1.10.1.min.js"></script>
	<script type="text/javascript" src="../js/md5.js"></script>
	<script type="text/javascript" src="../js/content.js"></script>
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
		<div class="left">
			<div class="title">
				<p class="des" style="margin:0 0 0 10px">高清电影</p>
			</div>
			<div>
				<div style="line-height:25px">
					<div style="float:right;margin:0" class="btnAct btnHelp"  onclick="sure(this,'movie')">确定</div>	
					<div style="float:right;margin:0">
						<input type="checkbox" value="480p"  checked="checked"/><span style="color:gray">480p&nbsp</span>
						<input type="checkbox" value="720p"  checked="checked"/><span style="color:gray">720p&nbsp</span>
						<input type="checkbox" value="1080p" checked="checked"/><span style="color:gray">1080p&nbsp</span>
						<input type="checkbox" value="other" checked="checked"/><span style="color:gray">其它&nbsp&nbsp</span>
					</div>
				</div>
				<div class="contNav"><a class="contDes"><span class="contDesType">[1080p]</span>忠烈杨家将忠烈杨家将忠烈杨家将忠烈杨家将忠烈杨家将v忠烈杨家将忠烈杨家将忠烈杨家将忠烈杨家将忠烈杨家将v忠烈杨家将忠烈杨家将忠烈杨家将忠烈杨家将忠烈杨家将v</a></div>
				<div class="contNav"><a class="contDes"><span class="contDesType">[1080p]</span>忠烈杨家将</a></div>
				<div class="contNav"><a class="contDes"><span class="contDesType">[1080p]</span>忠烈杨家将</a></div>
				<div class="contNav"><a class="contDes"><span class="contDesType">[1080p]</span>忠烈杨家将</a></div>
				<div class="contNav"><a class="contDes"><span class="contDesType">[720p]</span>忠烈杨家将</a></div>
				<div class="contNav"><a class="contDes"><span class="contDesType">[520p]</span>忠烈杨家将</a></div>
				<div class="contNav"><a class="contDes"><span class="contDesType">[1080p]</span>忠烈杨家将</a></div>
				<div class="contNav"><a class="contDes"><span class="contDesType">[480p]</span>忠烈杨家将</a></div>
				<div class="contNav"><a class="contDes"><span class="contDesType">[720p]</span>忠烈杨家将</a></div>
				<div class="contNav"><a class="contDes"><span class="contDesType">[1080p]</span>忠烈杨家将</a></div>
				<div class="contNav"><a class="contDes"><span class="contDesType">[1080p]</span>忠烈杨家将</a></div>
				<div class="contNav"><a class="contDes"><span class="contDesType">[1080p]</span>忠烈杨家将</a></div>
				<div class="contNav"><a class="contDes"><span class="contDesType">[1080p]</span>忠烈杨家将</a></div>
				<div class="contNav"><a class="contDes"><span class="contDesType">[1080p]</span>忠烈杨家将</a></div>
				<div class="contNav"><a class="contDes"><span class="contDesType">[1080p]</span>忠烈杨家将</a></div>
				<div class="contNav"><a class="contDes"><span class="contDesType">[1080p]</span>忠烈杨家将</a></div>
				<div class="contNav"><a class="contDes"><span class="contDesType">[1080p]</span>忠烈杨家将</a></div>
				<div class="contNav"><a class="contDes"><span class="contDesType">[1080p]</span>忠烈杨家将</a></div>
				<div class="contNav"><a class="contDes"><span class="contDesType">[1080p]</span>忠烈杨家将</a></div>
				<div class="contNav"><a class="contDes"><span class="contDesType">[1080p]</span>忠烈杨家将</a></div>
				<div class="contNav"><a class="contDes"><span class="contDesType">[1080p]</span>忠烈杨家将</a></div>
				<div class="contNav"><a class="contDes"><span class="contDesType">[1080p]</span>忠烈杨家将</a></div>
				<div class="contNav"><a class="contDes"><span class="contDesType">[1080p]</span>忠烈杨家将</a></div>
				<div class="contNav"><a class="contDes"><span class="contDesType">[520p]</span>忠烈杨家将</a></div>
				<div class="contNav"><a class="contDes"><span class="contDesType">[1080p]</span>忠烈杨家将</a></div>
				<div class="contNav"><a class="contDes"><span class="contDesType">[480p]</span>忠烈杨家将</a></div>
				<div class="contNav"><a class="contDes"><span class="contDesType">[720p]</span>忠烈杨家将</a></div>
				<div class="contNav"><a class="contDes"><span class="contDesType">[1180p]</span>忠烈杨家将</a></div>
				<div class="contNav"><a class="contDes"><span class="contDesType">[720p]</span>忠烈杨家将</a></div>
				<div class="contNav"><a class="contDes"><span class="contDesType">[520p]</span>忠烈杨家将</a></div>
			</div>
			<div class="more1Cont">
				<a href="#aaa" class="more1">获得更多...</a>
			</div>
		</div>
		<div class="right">
			<div class="title">
				<p class="des" style="margin:0 0 0 10px">忠烈杨家将</p>
			</div>
			<div>
				<div><img src="../img/movie1.jpg"/></div>
				<div>这是新电影,高清的格式哦。。。。</div>
			</div>
			<div class="cmmtWrapper">
				<div class="cmmtLeft">
					<div class="cmmtHead">
						<p class="from">来自:海南</p>
							<img src="../img/movie1.jpg" width="65px" height="65px"/>
						<p class="nickname">最美爱好者</p>
					</div>
					<div class="talkArrow talkArrowExt"></div>
					<div class="cmmt" onmouseover="showLastDiv(this)" onmouseout="hideLastDiv(this)" onclick="showReply(this)">
						<p class="cmmtText">感谢这影</p>
						<div class="reply" style="display:none">回&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp复</div>
					</div>
				</div>
				<div class="cmmtArrow"></div>
				<div class="cmmtRight" onmouseover="showLastDiv(this)" onmouseout="hideLastDiv(this)" onclick="showMore(this)">
					<div class="cmmtItem">
						<img src="../img/movie1.jpg" class="cmmtItemHead"/>
						这是什么情况?aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
					</div>
					<div class="cmmtItem">
						<img src="../img/movie1.jpg" class="cmmtItemHead"/>
						这是什么情况?aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
					</div>
					<div class="cmmtItem">
						<img src="../img/movie1.jpg" class="cmmtItemHead"/>
						这是什么情况?aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
					</div>
					<div class="cmmtMore" style="display:none">
						获&nbsp&nbsp得&nbsp&nbsp更&nbsp&nbsp多
					</div>
				</div>
			</div>
			<div class="cmmtWrapper">
				<div class="cmmtLeft">
					<div class="cmmtHead">
						<p class="from">来自:海南</p>
							<img src="../img/movie1.jpg" width="65px" height="65px"/>
						<p class="nickname">最美爱好者</p>
					</div>
					<div class="talkArrow talkArrowExt"></div>
					<div class="cmmt" onmouseover="showLastDiv(this)" onmouseout="hideLastDiv(this)" onclick="showReply(this)">
						<p class="cmmtText">感谢这电影让我学会了如何去面对人生感谢这电影让我学会了如何去面对人生感谢这电影让我学会了如何去面对人生感谢这电影让我学会了如何去面对人生感谢这电影让我学会了如何去面对人生感谢这电影让我学会了如何去面对人生感谢这电影让我学会了如何去面对人生感谢这电影让我学会了如何去面对人生</p>
						<div class="reply" style="display:none">回&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp复</div>
					</div>
				</div>
				<div class="cmmtArrow"></div>
				<div class="cmmtRight" onmouseover="showLastDiv(this)" onmouseout="hideLastDiv(this)" onclick="showMore(this)">
					<div class="cmmtItem">
						<img src="../img/movie1.jpg" class="cmmtItemHead"/>
						这是什么情况?aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
					</div>
					<div class="cmmtItem">
						<img src="../img/movie1.jpg" class="cmmtItemHead"/>
						这是什么情况?aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
					</div>
					<div class="cmmtItem">
						<img src="../img/movie1.jpg" class="cmmtItemHead"/>
						这是什么情况?aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
					</div>
					<div class="cmmtMore" style="display:none" >
						获&nbsp&nbsp得&nbsp&nbsp更&nbsp&nbsp多
					</div>
				</div>
			</div>
			<div class="cmmtWrapper">
				<div class="cmmtLeft">
					<div class="cmmtHead">
						<p class="from">来自:海南</p>
							<img src="../img/movie1.jpg" width="65px" height="65px"/>
						<p class="nickname">最美爱好者最美爱好者最美爱好者最美爱好者</p>
					</div>
					<div class="talkArrow talkArrowExt"></div>
					<div class="cmmt" onmouseover="showLastDiv(this)" onmouseout="hideLastDiv(this)" onclick="showReply(this)">
						<p class="cmmtText">感谢这影</p>
						<div class="reply" style="display:none">回&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp复</div>
					</div>
				</div>
				<div class="cmmtArrow"></div>
				<div class="cmmtRight" onmouseover="showLastDiv(this)" onmouseout="hideLastDiv(this)" onclick="showMore(this)">
					<div class="cmmtItem">
						<img src="../img/movie1.jpg" class="cmmtItemHead"/>
						这是什么情况?aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
					</div>
					<div class="cmmtItem">
						<img src="../img/movie1.jpg" class="cmmtItemHead"/>
						这是什么情况?aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
					</div>
					<div class="cmmtItem">
						<img src="../img/movie1.jpg" class="cmmtItemHead"/>
						这是什么情况?aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
					</div>
					<div class="cmmtMore" style="display:none">
						获&nbsp&nbsp得&nbsp&nbsp更&nbsp&nbsp多
					</div>
				</div>
			</div>
			<div class="more1Cont">
				<a href="#aaa" class="more1">获得更多...</a>
			</div>
			<div class="inputCont">
				<div class="inputTitle">期待您精彩的评论哦~~^_^</div>
				<div class="cmmtHead">
						<p class="from">来自:海南</p>
							<img src="../img/movie1.jpg" width="65px" height="65px"/>
						<p class="nickname">最美爱好者最美爱好者最美爱好者最美爱好者</p>
				</div>
				<div class="talkArrow talkArrowExt"></div>
				<div style="float:left;margin-left:-3px">
					<div class="areaCont">
						<textarea id="commentInput" class="textInput" onkeyup="feedInput(this,event)" maxLength="250"></textarea>
						<span id="fontCount" class="fontCount">0/250</span>
					</div>
					<div class="inputHelper">
						<img src="../qqemotion/0.gif" onclick="showEmotion(this,event)" class="emotionImg"/>
						<button id="pubBtn" class="pubBtn" onclick="feedPub(this,event)">发布</button>
					</div>
				</div>
				
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
		<div id="searchMore" style="text-align:center;margin-top:30px;display:none">
			<a href="#aaa" class="more moreText" style="display:inline-block">获得更多...</a>
		</div>
		<div class="networkError" style="display:none">
			<p>网络发生错误请重新刷新</p>
		</div>
	</div>
</div>
</body>
</html>