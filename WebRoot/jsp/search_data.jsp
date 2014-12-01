<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>

<c:if test="${fn:length(movies.data)==0}">
	<div class="searchNotFound">
		对不起，找到不任何与“<span style="color:red;font-size:14px;">${search}</span>”相关内容！
	</div>
</c:if>


<c:if test="${fn:length(movies.data)>0}">
	<c:forEach var="movie" items="${movies.data}"  varStatus="status">
		<div class="searchItem">
			<a href="${base}/content.do?id=${movie.movieid}" target="_blank"><img class="searchImg" src="${static}${movie.face220x169}" /></a>
			<ul class="searchUl">
				<li class="searchli">【推荐】：<c:forEach begin="1" end="${movie.fullStarCount}" ><span class="starFull"></span></c:forEach><c:forEach begin="1" end="${movie.partStarCount}" ><span class="starPart"></span></c:forEach><c:forEach begin="1" end="${movie.blankStarCount}" ><span class="starBlank"></span></c:forEach><span class="cntScore">${movie.score}</span></li>
				<li class="searchli">【名称】：<span class="liDes"><a href="${base}/content.do?id=${movie.movieid}" style="font-size:13px" target="_blank">${movie.name}</a></span></li>
				<li class="searchli">【附件】：<a href="${base}/content.do?id=${movie.movieid}" style="font-size:13px" target="_blank"><span class="zip"></span>查看</a></li>
			<ul>
			<div class="clear"></div>
		</div>
		</c:forEach>
		
		<div class="searchBtns" style="clear:both">
			<c:if test="${!movies.isfirstPage}"><a onclick="deepPage(this)" href="javascript:void(0)" class="mvAction">&lt; 上一页</a></c:if>
			<c:if test="${!movies.islastPage}"><a onclick="deepPage(this)" href="javascript:void(0)" class="mvAction">下一页 &gt;</a></c:if>
		</div>
</c:if>

