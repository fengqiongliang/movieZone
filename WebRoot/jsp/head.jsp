<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link type="image/x-icon" href="${static}/img/favicon.ico" rel="shortcut icon" />
	<link type="text/css" rel="stylesheet" href="${static}/css/moviezone.css"></link>
	<script type="text/javascript" src="${static}/js/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="${static}/js/jquery-ui-1.10.4.min.js"></script>
	<script type="text/javascript" src="${static}/js/jquery.roundabout.min.js"></script>
	<script type="text/javascript" src="${static}/js/jquery.flot.min.js"></script>
	<script type="text/javascript" src="${static}/js/swfupload.js"></script>
	<script type="text/javascript" src="${static}/js/moviezone.js"></script>
	<title>影集网</title>
</head>
<body>
<div class="layer"></div>
<c:if test='${empty user}'>
<div class="regWindow">
	<a class="closeReg" hoverClass="closeRegHover" href="javascript:closeReg();"></a>
	<span class="regText" hoverClass="regLoginHover" onclick="tabReg(1,this)">注册</span><span class="loginText" hoverClass="regLoginHover" onclick="tabReg(2,this)">登录</span>
	<ul class="regUl">
		<li style="text-align:left"><span class="errorTip"></span></li>
		<li><i class="redStar">*</i>用户名：<input class="regInput" focusClass="regInputFocus" type="text" onblur="checkUName(this)" onclick="clickFeild(this)" maxlength="20"></input><img class="loadImg" src="${static}/img/loading.gif" /><span class="iTip">长度大于6，小于20</span><span class="iError"></span><span class="iSuccess">√</span></li>
		<li><i class="redStar">*</i>密&nbsp&nbsp&nbsp码：<input class="regInput" focusClass="regInputFocus" type="password" onblur="checkPWD(this)" onclick="clickFeild(this)" maxlength="20"></input><span class="iTip">长度大于6，小于20</span><span class="iError"></span><span class="iSuccess">√</span></li>
		<li><i class="redStar">*</i>验证码：<input class="regCode" focusClass="regInputFocus" type="text" onblur="checkCode(this)" maxlength="4" onclick="clickFeild(this)"></input><img id="validateCode" class="codeImg" title="单击换一换" alt="验证码加载失败" src="http://weibo.com/signup/v5/pincode/pincode.php?lang=zh&sinaId=5a7bb5c5930b3d6e00e0dd972c9c5b6a&r=1403229441" onclick="$('#validateCode').attr('src',$('#validateCode').attr('src')+'&1=1');"/><em style="cursor:pointer;color:blue;" onclick="$('#validateCode').attr('src',$('#validateCode').attr('src')+'&1=1');">换一换</em><span class="iTip">请输入四位验证码</span><span class="iError"></span><span class="iSuccess"></span></li>
	</ul>
	<a class="regBut" hoverClass="regButHover" onclick="regOrLogin()">立即注册</a>
	<img class="loadImg" src="${static}/img/loading.gif" />
</div>
</c:if>
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
	<div class="topR">
		<c:if test='${!empty user}'><span class="nick" style="display:inline"><input class="nickField" type="text" value="${user.nextnick!=null?user.nextnick:user.nickname}"  oldValue="${user.nextnick!=null?user.nextnick:user.nickname}"  onblur="modifyNick(this)" onkeyup="adjustWidth()" maxlength="12" maxWidth="150"  autocomplete="off" ></input><a href="javascript:logout();" class="logout">退出</a></span></c:if>
		<c:if test='${empty user}'><a class="reg" href="javascript:goReg();" >注册/登录</a></c:if>
		<input class="search" type="text" searchHover="searchF"  value="${search}"></input><a class="searchIcon" href="javascript:goSearch();"></a><span class='searchE'></span>
	</div>
	<c:if test='${!empty user}'><div class="face" title="可单击修改"><img class="myFace" src="${static}${user.nextface != null?user.nextface:user.faceurl}"/><img class="loadFace" src="${static}/img/loading.gif" /><div class="uploadContainer"><span uploadUrl="${base}/upUserPic.json" maxSize="1MB" types="*.bmp;*.jpg;*.jpeg;*.png;*.gif;" desc="请选择图片文件" upstart="upStart" upsuccess="upSuccess" uperror="upError"></span></div></div></c:if>
	<div class="clear"></div>
</div>
<c:forEach var="mv" items="1..6" begin="2" end="5" varStatus="status">
			${status.count}  -- ${mv}  -- ${status.first}  -- ${status.last} <br/>
		</c:forEach>
<c:if test="${!empty sceneMovies && !empty sceneCmmts}">
<div class="show">
	<div class="showLeft">
		<c:forEach var="mv" items="${sceneMovies}"  begin="0" end="5" varStatus="status">
		<a href="${base}/content.do?id=${mv.movieid}" class="bigImg" title="${mv.name}" onmouseover="imgOver(this)" onmouseout="moveImg(this)" ${status.first?"":"style='display:none'" }><img src="${static}${mv.face650x500}" width="100%" height="100%"/></a>
		</c:forEach>
		<div class="showLayer"></div>
		<div class="imgContainer">
			<c:forEach var="mv" items="${sceneMovies}"  begin="0" end="5" varStatus="status">
			<a href="${base}/content.do?id=${mv.movieid}" class="smallImg" hoverClass="smallImgHover" title="${mv.name}" onmouseover="imgOver(this)" onmouseout="moveImg(this)"><img src="${static}${mv.face650x500}" width="100%" height="100%" /></a>
			</c:forEach>
		</div>
	</div>
	<div class="showRight">
		<ul onmouseover="cmmtOver()" onmouseout="moveCmmt()">
			<c:forEach var="mv" items="1,2,3,4,5,6,7,8,9" begin="0" end="9" varStatus="status">
			<li class="cmmtLi">
				<img class="nickImg" src="${static}/img/blank92x71.gif" title="风之恋人1"/>
				<div class="commentCont">
					<p class="cmmtTitle"><span style="color:#71b775">风之恋人1</span> 评 <a href="content.html?id=1" class="movieName" title="《澳门风云》">《澳门风云》</a> <span class="movieScore">8.0</span></p>
					<a href="${base}/content.do?id=${mv}" class="cmmtSpeak" title="这是我见过最好看的电影了">这是我见过最好看的电影了，男主人公帅，女主人公美！这是我见过最好看的电影了，男主人公帅，女主人公美abcccccccccc！这是我见过最好看的电影了，男主人公帅，女主人公美abcccccccccc！这是我见过最好看的电影了，男主人公帅，女主人公美abcccccccccc！这是我见过最好看的电影了，男主人公帅，女主人公美abcccccccccc！这是我见过最好看的电影了，男主人公帅，女主人公美abcccccccccc！</a>
				</div>
			</li>
			</c:forEach>
		</ul>
	</div>
	<div class="clear"></div>
</div>
</c:if>
