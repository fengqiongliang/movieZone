<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>
<%
	String Scheme      = request.getScheme();
	String ServerName  = request.getServerName();
	int    ServerPort  = request.getServerPort();
	String contextPath = request.getContextPath();
	String webPath = Scheme+"://"+ServerName+(ServerPort==80?"":":"+ServerPort)+(contextPath.length()>0?contextPath:"");
	request.setAttribute("base",webPath);
	request.setAttribute("staticDomain",webPath);
%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Refresh" contect="10;url=http://yourlink" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link type="image/x-icon" href="${base}/img/favicon.ico" rel="shortcut icon" />
	<link type="text/css" rel="stylesheet" href="${base}/css/moviezone.css"></link>
	<script type="text/javascript" src="${base}/js/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="${base}/js/jquery-ui-1.10.4.min.js"></script>
	<script type="text/javascript" src="${base}/js/jquery.roundabout.min.js"></script>
	<script type="text/javascript" src="${base}/js/jquery.flot.min.js"></script>
	<script type="text/javascript" src="${base}/js/swfupload.js"></script>
	<script type="text/javascript" src="${base}/js/moviezone.js"></script>
	<title>影集网</title>
</head>
<body>
<div class="layer"></div>
<div class="regWindow">
	<a class="closeReg" hoverClass="closeRegHover" href="javascript:closeReg();"></a>
	<span class="regText" hoverClass="regLoginHover" onclick="tabReg(1,this)">注册</span><span class="loginText" hoverClass="regLoginHover" onclick="tabReg(2,this)">登录</span>
	<ul class="regUl">
		<li style="text-align:left"><span class="errorTip"></span></li>
		<li><i class="redStar">*</i>用户名：<input class="regInput" focusClass="regInputFocus" type="text" onblur="checkUName(this)" onclick="clickFeild(this)" maxlength="20"></input><img class="loadImg" src="./img/loading.gif" /><span class="iTip">长度大于6，小于20</span><span class="iError"></span><span class="iSuccess">√</span></li>
		<li><i class="redStar">*</i>密&nbsp&nbsp&nbsp码：<input class="regInput" focusClass="regInputFocus" type="password" onblur="checkPWD(this)" onclick="clickFeild(this)" maxlength="20"></input><span class="iTip">长度大于6，小于20</span><span class="iError"></span><span class="iSuccess">√</span></li>
		<li><i class="redStar">*</i>验证码：<input class="regCode" focusClass="regInputFocus" type="text" onblur="checkCode(this)" maxlength="4" onclick="clickFeild(this)"></input><img id="validateCode" class="codeImg" title="单击换一换" alt="验证码加载失败" src="http://weibo.com/signup/v5/pincode/pincode.php?lang=zh&sinaId=5a7bb5c5930b3d6e00e0dd972c9c5b6a&r=1403229441" onclick="$('#validateCode').attr('src',$('#validateCode').attr('src')+'&1=1');"/><em style="cursor:pointer;color:blue;" onclick="$('#validateCode').attr('src',$('#validateCode').attr('src')+'&1=1');">换一换</em><span class="iTip">请输入四位验证码</span><span class="iError"></span><span class="iSuccess"></span></li>
	</ul>
	<a class="regBut" hoverClass="regButHover" onclick="regOrLogin()">立即注册</a>
	<img class="loadImg" src="./img/loading.gif" />
</div>
<div class="top">
	<div class="topL">
		<a class="logo"  href="${base}/index.do" ></a>
		<a class="nav"   href="${base}/index.do"   ${focusIndex==1?"style='background:#055078'":""} >影集网</a>
		<a class="nav"   href="${base}/movie.do"  ${focusIndex==2?"style='background:#055078'":""} >电影</a>
		<a class="nav"   href="${base}/tv.do"        ${focusIndex==3?"style='background:#055078'":""} >电视剧</a>
		<c:if test='${user.role =="admin" && empty searchTitle && empty movie}'><a class="nav"   href="${base}/admin_movie.do"  ${focusIndex==4?"style='background:#055078'":""} >管理后台</a></c:if>
		<c:if test='${!empty searchTitle}'><a class="nav searchT" href="#"  title="搜：${search}" style='background:#055078'>搜：${searchTitle}</a></c:if>
		<c:if test='${!empty movie}'><a class="nav searchT"  href="#" style="background:#055078" title="${movie.name}">${fn:substring(movie.name,0,7)}${fn:length(movie.name)>7?"...":""}</a></c:if>
	</div>
	<div class="topR"><span class="nick" style="display:inline"><input class="nickField" type="text" value="梦的翅膀膀膀膀膀膀膀膀膀" onblur="modifyNick()" onkeyup="adjustWidth()" maxlength="12" maxWidth="150"></input><a href="javascript:logout();" class="logout">退出</a></span><a class="reg" href="javascript:goReg();" style="display:none">注册/登录</a><input class="search" type="text" searchHover="searchF"  value="${search}"></input><a class="searchIcon" href="javascript:goSearch();"></a><span class='searchE'></span></div>
	<div class="face" title="可单击修改"><img class="myFace" src="./img/blank92x71.gif"/><img class="loadFace" src="./img/loading.gif" /><div class="uploadContainer"><span uploadUrl="http://localhost:8080/moviezone/helloWorld.do" maxSize="1MB" types="*.bmp;*.jpg;*.jpeg;*.png;*.gif;" desc="请选择图片文件" upstart="upStart" upsuccess="upSuccess" uperror="upError"></span></div></div>
	<div class="clear"></div>
</div>

<c:if test="${!empty sceneMovies && !empty sceneCmmts}">
<div class="show">
	<div class="showLeft">
		<a href="content.html?id=1" class="bigImg" title="变形金刚，今年最火的电影" onmouseover="imgOver(this)" onmouseout="moveImg(this)"><img src="./img/blank650x500.gif" width="100%" height="100%"/></a>
		<a href="content.html?id=2" class="bigImg" title="变形金刚，今年最火的电影" onmouseover="imgOver(this)" onmouseout="moveImg(this)" style="display:none"><img src="./img/blank650x500.gif" width="100%" height="100%"/></a>
		<a href="content.html?id=3" class="bigImg" title="变形金刚，今年最火的电影" onmouseover="imgOver(this)" onmouseout="moveImg(this)" style="display:none"><img src="./img/blank650x500.gif" width="100%" height="100%"/></a>
		<a href="content.html?id=4" class="bigImg" title="变形金刚，今年最火的电影" onmouseover="imgOver(this)" onmouseout="moveImg(this)" style="display:none"><img src="./img/blank650x500.gif" width="100%" height="100%"/></a>
		<a href="content.html?id=4" class="bigImg" title="变形金刚，今年最火的电影" onmouseover="imgOver(this)" onmouseout="moveImg(this)" style="display:none"><img src="./img/blank650x500.gif" width="100%" height="100%"/></a>
		<div class="showLayer"></div>
		<div class="imgContainer">
			<a href="content.html?id=1" class="smallImg" hoverClass="smallImgHover" title="变形金刚，今年最火的电影" onmouseover="imgOver(this)" onmouseout="moveImg(this)"><img src="./img/blank650x500.gif" width="100%" height="100%" /></a>
			<a href="content.html?id=2" class="smallImg" hoverClass="smallImgHover" title="变形金刚，今年最火的电影" onmouseover="imgOver(this)" onmouseout="moveImg(this)"><img src="./img/blank650x500.gif" width="100%" height="100%" /></a>
			<a href="content.html?id=2" class="smallImg" hoverClass="smallImgHover" title="变形金刚，今年最火的电影" onmouseover="imgOver(this)" onmouseout="moveImg(this)"><img src="./img/blank650x500.gif" width="100%" height="100%"  /></a>
			<a href="content.html?id=2" class="smallImg" hoverClass="smallImgHover" title="变形金刚，今年最火的电影" onmouseover="imgOver(this)" onmouseout="moveImg(this)"><img src="./img/blank650x500.gif" width="100%" height="100%"  /></a>
			<a href="content.html?id=2" class="smallImg" hoverClass="smallImgHover" title="变形金刚，今年最火的电影" onmouseover="imgOver(this)" onmouseout="moveImg(this)"><img src="./img/blank650x500.gif" width="100%" height="100%"  /></a>
		</div>
	</div>
	<div class="showRight">
		<ul onmouseover="cmmtOver()" onmouseout="moveCmmt()">
			<li class="cmmtLi">
				<img class="nickImg" src="./img/blank92x71.gif" title="风之恋人1"/>
				<div class="commentCont">
					<p class="cmmtTitle"><span style="color:#71b775">风之恋人1</span> 评 <a href="content.html?id=1" class="movieName" title="《澳门风云》">《澳门风云》</a> <span class="movieScore">8.0</span></p>
					<a href="content.html?id=1" class="cmmtSpeak" title="这是我见过最好看的电影了">这是我见过最好看的电影了，男主人公帅，女主人公美！这是我见过最好看的电影了，男主人公帅，女主人公美abcccccccccc！这是我见过最好看的电影了，男主人公帅，女主人公美abcccccccccc！这是我见过最好看的电影了，男主人公帅，女主人公美abcccccccccc！这是我见过最好看的电影了，男主人公帅，女主人公美abcccccccccc！这是我见过最好看的电影了，男主人公帅，女主人公美abcccccccccc！</a>
				</div>
			</li>
			<li class="cmmtLi">
				<img class="nickImg" src="./img/blank92x71.gif" title="风之恋人2"/>
				<div class="commentCont">
					<p class="cmmtTitle"><span style="color:#71b775">风之恋人2</span> 评 <a href="content.html?id=1" class="movieName" title="《澳门风云》">《澳门风云》</a> <span class="movieScore">8.0</span></p>
					<a href="content.html?id=1" class="cmmtSpeak" title="这是我见过最好看的电影了">这是我见过最好看的电影了，男主人公帅，女主人公美！这是我见过最好看的电影了，男主人公帅，女主人公美abcccccccccc！这是我见过最好看的电影了，男主人公帅，女主人公美abcccccccccc！这是我见过最好看的电影了，男主人公帅，女主人公美abcccccccccc！这是我见过最好看的电影了，男主人公帅，女主人公美abcccccccccc！这是我见过最好看的电影了，男主人公帅，女主人公美abcccccccccc！</a>
				</div>
			</li>
			<li class="cmmtLi">
				<img class="nickImg" src="./img/blank92x71.gif" title="风之恋人3"/>
				<div class="commentCont">
					<p class="cmmtTitle"><span style="color:#71b775">风之恋人3</span> 评 <a href="content.html?id=1" class="movieName" title="《澳门风云》">《澳门风云》</a> <span class="movieScore">8.0</span></p>
					<a href="content.html?id=1" class="cmmtSpeak" title="这是我见过最好看的电影了">这是我见过最好看的电影了，男主人公帅，女主人公美！这是我见过最好看的电影了，男主人公帅，女主人公美abcccccccccc！这是我见过最好看的电影了，男主人公帅，女主人公美abcccccccccc！这是我见过最好看的电影了，男主人公帅，女主人公美abcccccccccc！这是我见过最好看的电影了，男主人公帅，女主人公美abcccccccccc！这是我见过最好看的电影了，男主人公帅，女主人公美abcccccccccc！</a>
				</div>
			</li>
			<li class="cmmtLi">
				<img class="nickImg" src="./img/blank92x71.gif" title="风之恋人4"/>
				<div class="commentCont">
					<p class="cmmtTitle"><span style="color:#71b775">风之恋人4</span> 评 <a href="content.html?id=1" class="movieName" title="《澳门风云》">《澳门风云》</a> <span class="movieScore">8.0</span></p>
					<a href="content.html?id=1" class="cmmtSpeak" title="这是我见过最好看的电影了">这是我见过最好看的电影了，男主人公帅，女主人公美！这是我见过最好看的电影了，男主人公帅，女主人公美abcccccccccc！这是我见过最好看的电影了，男主人公帅，女主人公美abcccccccccc！这是我见过最好看的电影了，男主人公帅，女主人公美abcccccccccc！这是我见过最好看的电影了，男主人公帅，女主人公美abcccccccccc！这是我见过最好看的电影了，男主人公帅，女主人公美abcccccccccc！</a>
				</div>
			</li>
			<li class="cmmtLi">
				<img class="nickImg" src="./img/blank92x71.gif" title="风之恋人5"/>
				<div class="commentCont">
					<p class="cmmtTitle"><span style="color:#71b775">风之恋人5</span> 评 <a href="content.html?id=1" class="movieName" title="《澳门风云》">《澳门风云》</a> <span class="movieScore">8.0</span></p>
					<a href="content.html?id=1" class="cmmtSpeak" title="这是我见过最好看的电影了">这是我见过最好看的电影了，男主人公帅，女主人公美！这是我见过最好看的电影了，男主人公帅，女主人公美abcccccccccc！这是我见过最好看的电影了，男主人公帅，女主人公美abcccccccccc！这是我见过最好看的电影了，男主人公帅，女主人公美abcccccccccc！这是我见过最好看的电影了，男主人公帅，女主人公美abcccccccccc！这是我见过最好看的电影了，男主人公帅，女主人公美abcccccccccc！</a>
				</div>
			</li>
		</ul>
	</div>
	<div class="clear"></div>
</div>
</c:if>
