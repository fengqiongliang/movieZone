<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>

<ul class="mvItems">
	<c:forEach var="mv" items="${movies.data}"  varStatus="status">
	<li class="mvItem" title="${mv.name}"><span class="rankScore">${mv.score}</span><a href="${base}/content.do?id=${mv.movieid}&fromModule=${fromModule}" class="mvItemDes"><img src="${static}${mv.face150x220}" width="120px" height="176" />${mv.name}</a></li>
	</c:forEach>
	<li style="clear:both"></li>
</ul>
<div class="mvPageNav">
	<c:if test="${!movies.isfirstPage}"><a class="mvAction" href="javascript:void(0)" onclick="pageMtv(this)">&lt; 上一页</a></c:if>
	<c:if test="${!movies.islastPage}"><a class="mvAction" href="javascript:void(0)" onclick="pageMtv(this)">下一页 &gt;</a></c:if>
</div>
