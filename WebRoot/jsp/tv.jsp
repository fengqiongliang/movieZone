<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<% pageContext.setAttribute("focusIndex", 3);  %>
<%@ include file="head.jsp" %>

<div class="movieDetail">
	<span class="movieDLabel">英美</span>
	<div class="movieDLine"></div>
	<div class="movieDCont">
		<div url="${base}/movie.json?type=america" pageNo="1">
			<div class="mvOption">
				<input type="radio" name="pageType1" value="14" checked="checked" onclick="findMtv($(this).parent().parent())" autocomplete="off"></input>14条/页
				<input type="radio" name="pageType1" value="28" onclick="findMtv($(this).parent().parent())" autocomplete="off"></input>28条/页
				<a class="mvBtn" href="javascript:void(0)" onclick="sortMtv(this)" sort="desc"><span class="mvArrow">↓ </span>按时间</a>
				<a class="mvBtn" href="javascript:void(0)" onclick="sortMtv(this)" sort="asc"><span class="mvArrow">↑ </span>按评分</a>
			</div>
			<div>
				<c:set var="movies" value="${americaMovies}" />
				<c:set var="fromModule" value="tvAmerica" />
				<%@ include file="movie_tv_list.jsp" %>
			</div>
		</div>
		<div class="clear"></div>
	</div>
	<div class="clear"></div>
</div>
<div class="movieDetail">
	<span class="movieDLabel">日韩</span>
	<div class="movieDLine"></div>
	<div class="movieDCont">
		<div url="${base}/movie.json?type=japan" pageNo="1">
			<div class="mvOption">
				<input type="radio" name="pageType2" value="14" checked="checked" onclick="findMtv($(this).parent().parent())" autocomplete="off"></input>14条/页
				<input type="radio" name="pageType2" value="28" onclick="findMtv($(this).parent().parent())" autocomplete="off"></input>28条/页
				<a class="mvBtn" href="javascript:void(0)" onclick="sortMtv(this)" sort="desc"><span class="mvArrow">↓ </span>按时间</a>
				<a class="mvBtn" href="javascript:void(0)" onclick="sortMtv(this)" sort="asc"><span class="mvArrow">↑ </span>按评分</a>
			</div>
			<div>
				<c:set var="movies" value="${japanMovies}" />
				<c:set var="fromModule" value="tvJapan" />
				<%@ include file="movie_tv_list.jsp" %>
			</div>
		</div>
		<div class="clear"></div>
	</div>
	<div class="clear"></div>
</div>
<div class="movieDetail">
	<span class="movieDLabel">港台</span>
	<div class="movieDLine"></div>
	<div class="movieDCont">
		<div url="${base}/movie.json?type=hongkong" pageNo="1">
			<div class="mvOption">
				<input type="radio" name="pageType3" value="14" checked="checked" onclick="findMtv($(this).parent().parent())" autocomplete="off"></input>14条/页
				<input type="radio" name="pageType3" value="28" onclick="findMtv($(this).parent().parent())" autocomplete="off"></input>28条/页
				<a class="mvBtn" href="javascript:void(0)" onclick="sortMtv(this)" sort="desc"><span class="mvArrow">↓ </span>按时间</a>
				<a class="mvBtn" href="javascript:void(0)" onclick="sortMtv(this)" sort="asc"><span class="mvArrow">↑ </span>按评分</a>
			</div>
			<div>
				<c:set var="movies" value="${hongkongMovies}" />
				<c:set var="fromModule" value="tvHongkong" />
				<%@ include file="movie_tv_list.jsp" %>
			</div>
		</div>
		<div class="clear"></div>
	</div>
	<div class="clear"></div>
</div>
<div class="movieDetail">
	<span class="movieDLabel">内地</span>
	<div class="movieDLine"></div>
	<div class="movieDCont">
		<div url="${base}/movie.json?type=china" pageNo="1">
			<div class="mvOption">
				<input type="radio" name="pageType4" value="14" checked="checked" onclick="findMtv($(this).parent().parent())" autocomplete="off"></input>14条/页
				<input type="radio" name="pageType4" value="28" onclick="findMtv($(this).parent().parent())" autocomplete="off"></input>28条/页
				<a class="mvBtn" href="javascript:void(0)" onclick="sortMtv(this)" sort="desc"><span class="mvArrow">↓ </span>按时间</a>
				<a class="mvBtn" href="javascript:void(0)" onclick="sortMtv(this)" sort="asc"><span class="mvArrow">↑ </span>按评分</a>
			</div>
			<div>
				<c:set var="movies" value="${chinaMovies}" />
				<c:set var="fromModule" value="tvChina" />
				<%@ include file="movie_tv_list.jsp" %>
			</div>
		</div>
		<div class="clear"></div>
	</div>
	<div class="clear"></div>
</div>

<%@ include file="foot.jsp" %>