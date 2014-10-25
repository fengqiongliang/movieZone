<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>

<c:if test="${fn:length(replys)>0}">
	<div class="reply">
		<span class="leftArrow">←</span>
		<ul class="replyCont" onmouseover="moreShow(this)" onmouseout="moreHide(this,event)">
			<c:forEach var="reply"  items="${replys}"  varStatus="status">
				<li class="rpyli" title="${reply.content}"><img class="replyImg" src="${static}${reply.user.faceurl}"  title="${reply.user.nickname}" />${reply.content}</li>
			</c:forEach>
		</ul>
		<c:if test="${fn:length(replys)>=5}"><div class="moreTxt" onclick="getMoreReply(this)" onmouseout="moreHide(this,event)" commentid="${replys[0].commentid}"><img class="moreReplyLoading"  style="display:ninline" src="${static}/img/loading.gif" />获 得 更 多</div></c:if>
	</div>
</c:if>
