<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>

<c:forEach var="cmmt"  items="${comments}"  varStatus="status">
<div id="cmmt${cmmt.commentid}" class="cmmtDiv">
	<div class="cmmtHead">
		<p class="nickname">${cmmt.user.nickname}</p>
		<img class="cmmtImg" src="${static}${cmmt.user.faceurl}">
		<p class="from">来自:${cmmt.createarea}</p>
	</div>
	<div class="cmntCont">
		<span class="cmntArrow">◀</span>
		<p class="cmmtText" title="${cmmt.content}" onmouseover="replyShow(this)" onmouseout="replyHide(this,event)">${cmmt.content}</p>
		<div class="replyTxt" onclick="reply(this)" onmouseout="replyHide(this,event)">回 复</div>
	</div>
	<div class="reply">
		<span class="leftArrow">←</span>
		<ul class="replyCont" onmouseover="moreShow(this)" onmouseout="moreHide(this,event)">
			<li class="rpyli"><img class="replyImg" src="./img/blank92x71.gif" />这是什么情况?aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa是什么情况?aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa是什么情况?aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa是什么情况?aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa</li>
			<li class="rpyli"><img class="replyImg" src="./img/blank92x71.gif" />这是什么情况?aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa是什么情况?aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa是什么情况?aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa是什么情况?aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa</li>
			<li class="rpyli"><img class="replyImg" src="./img/blank92x71.gif" />这是什么情况?aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa是什么情况?aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa是什么情况?aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa是什么情况?aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa</li>
			<li class="rpyli"><img class="replyImg" src="./img/blank92x71.gif" />这是什么情况?aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa是什么情况?aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa是什么情况?aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa是什么情况?aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa</li>
		</ul>
		<div class="moreTxt" onclick="getMoreReply(this)" onmouseout="moreHide(this,event)" commentId="${cmmt.commentid}"><img class="moreReplyLoading" src="./img/loading.gif" />获 得 更 多</div>
	</div>
	<div class="clear"></div>
</div>
</c:forEach>