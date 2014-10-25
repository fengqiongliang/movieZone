<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>

<c:forEach var="cmmtReply"  items="${cmmtReplys}"  varStatus="status">
<div id="cmmt${cmmtReply.cmmt.commentid}" class="cmmtDiv">
	<div class="cmmtHead">
		<p class="nickname">${cmmtReply.cmmt.user.nickname}</p>
		<img class="cmmtImg" src="${static}${cmmtReply.cmmt.user.faceurl}">
		<p class="from">来自:${cmmtReply.cmmt.createarea}</p>
	</div>
	<div class="cmntCont">
		<span class="cmntArrow">◀</span>
		<p class="cmmtText" title="${cmmtReply.cmmt.content}" onmouseover="replyShow(this)" onmouseout="replyHide(this,event)">${cmmtReply.cmmt.content}</p>
		<div class="replyTxt" onclick="reply(this)" onmouseout="replyHide(this,event)" commentid="${cmmtReply.cmmt.commentid}">回 复</div>
	</div>
	<c:set var="replys"  value="${cmmtReply.replys}" />
	<%@ include file="content_replys.jsp" %>
	<div class="clear"></div>
</div>
</c:forEach>