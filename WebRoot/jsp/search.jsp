<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="head.jsp" %>


<div class="searchWrap">
	<div class="searchLeft">
		<c:forEach var="type"  items="${searchResult.types}"  varStatus="status">
		<a href="javascript:deepSearch('${type[0]}')"  class='searchLeftA ${type[0] eq searchResult.matchType?"searchLeftAClick":""}'>${type[0]}<span class="matchCount">(${type[1]})</span></a>
		</c:forEach>
		<div class="clear"></div>
	</div>

	<div class="searchRight" search="${searchResult.keyword}" searchType="${searchResult.matchType}" pageNo="1" >
		<c:set var="movies" value="${searchResult.movies}"  />
		<%@ include file="search_data.jsp" %>
	</div>
</div>

<%@ include file="foot.jsp" %>