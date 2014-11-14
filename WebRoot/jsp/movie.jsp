<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<% pageContext.setAttribute("focusIndex", 2);  %>
<%@ include file="head.jsp" %>

<div class="movieDetail">
	<span class="movieDLabel">480p</span>
	<div class="movieDLine"></div>
	<div class="movieDCont">
		<div url="${base}/movie.json?type=480p" pageNo="1">
			<div class="mvOption">
				<input type="radio" name="pageType1" value="14" checked="checked"  onclick="findMtv($(this).parent().parent())" autocomplete="off"></input>14条/页
				<input type="radio" name="pageType1" value="28" onclick="findMtv($(this).parent().parent())" autocomplete="off"></input>28条/页
				<a class="mvBtn" href="javascript:void(0)" onclick="sortMtv(this)" sort="desc"><span class="mvArrow">↓ </span>按时间</a>
				<a class="mvBtn" href="javascript:void(0)" onclick="sortMtv(this)" sort="asc"><span class="mvArrow">↑ </span>按评分</a>
			</div>
			<div>
				<c:set var="movies" value="${Movies480p}" />
				<c:set var="fromModule" value="movie480p" />
				<%@ include file="movie_tv_list.jsp" %>
			</div>
		</div>
		<div class="clear"></div>
	</div>
	<div class="clear"></div>
</div>
<div class="movieDetail">
	<span class="movieDLabel">720p</span>
	<div class="movieDLine"></div>
	<div class="movieDCont">
		<div url="${base}/movie.json?type=720p" pageNo="1">
			<div class="mvOption">
				<input type="radio" name="pageType2" value="14" checked="checked" onclick="findMtv($(this).parent().parent())" autocomplete="off"></input>14条/页
				<input type="radio" name="pageType2" value="28" onclick="findMtv($(this).parent().parent())" autocomplete="off"></input>28条/页
				<a class="mvBtn" href="javascript:void(0)" onclick="sortMtv(this)" sort="desc"><span class="mvArrow">↓ </span>按时间</a>
				<a class="mvBtn" href="javascript:void(0)" onclick="sortMtv(this)" sort="asc"><span class="mvArrow">↑ </span>按评分</a>
			</div>
			<div>
				<c:set var="movies" value="${Movies720p}" />
				<c:set var="fromModule" value="movie720p" />
				<%@ include file="movie_tv_list.jsp" %>
			</div>
		</div>
		<div class="clear"></div>
	</div>
	<div class="clear"></div>
</div>
<div class="movieDetail">
	<span class="movieDLabel">1080p</span>
	<div class="movieDLine"></div>
	<div class="movieDCont">
		<div url="${base}/movie.json?type=1080p" pageNo="1">
			<div class="mvOption">
				<input type="radio" name="pageType3" value="14" checked="checked" onclick="findMtv($(this).parent().parent())" autocomplete="off"></input>14条/页
				<input type="radio" name="pageType3" value="28" onclick="findMtv($(this).parent().parent())" autocomplete="off"></input>28条/页
				<a class="mvBtn" href="javascript:void(0)" onclick="sortMtv(this)" sort="desc"><span class="mvArrow">↓ </span>按时间</a>
				<a class="mvBtn" href="javascript:void(0)" onclick="sortMtv(this)" sort="asc"><span class="mvArrow">↑ </span>按评分</a>
			</div>
			<div>
				<c:set var="movies" value="${Movies1080p}" />
				<c:set var="fromModule" value="movie1080p" />
				<%@ include file="movie_tv_list.jsp" %>
			</div>
		</div>
		<div class="clear"></div>
	</div>
	<div class="clear"></div>
</div>
<div class="movieDetail">
	<span class="movieDLabel">其它</span>
	<div class="movieDLine"></div>
	<div class="movieDCont">
		<div url="${base}/movie.json?type=other" pageNo="1">
			<div class="mvOption">
				<input type="radio" name="pageType4" value="14" checked="checked" onclick="findMtv($(this).parent().parent())" autocomplete="off"></input>14条/页
				<input type="radio" name="pageType4" value="28" onclick="findMtv($(this).parent().parent())" autocomplete="off"></input>28条/页
				<a class="mvBtn" href="javascript:void(0)" onclick="sortMtv(this)" sort="desc"><span class="mvArrow">↓ </span>按时间</a>
				<a class="mvBtn" href="javascript:void(0)" onclick="sortMtv(this)" sort="asc"><span class="mvArrow">↑ </span>按评分</a>
			</div>
			<div>
				<c:set var="movies" value="${otherMVMovies}" />
				<c:set var="fromModule" value="movieOther" />
				<%@ include file="movie_tv_list.jsp" %>
			</div>
		</div>
		<div class="clear"></div>
	</div>
	<div class="clear"></div>
</div>

<%@ include file="foot.jsp" %>