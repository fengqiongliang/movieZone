<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<% pageContext.setAttribute("focusIndex", 1);  %>
<%@ include file="head.jsp" %>

<div class="movie">
	<span class="movieLabel">电影</span>
	<div class="movieCont">
		<ul class="movieItem">
			<li class="mItem mItemClick" hoverClass="mItemHover" otherClass="mItemClick" onclick="mvClick(this)">480p</li>
			<li class="mItem" hoverClass="mItemHover" otherClass="mItemClick" onclick="mvClick(this)">720p</li>
			<li class="mItem" hoverClass="mItemHover" otherClass="mItemClick" onclick="mvClick(this)">1080p</li>
			<li class="mItem" hoverClass="mItemHover" otherClass="mItemClick" onclick="mvClick(this)">其它</li>
		</ul>
		<div class="movieShow">
			<ul>
				<c:forEach var="mv" items="${Movies480p}" begin="0" end="1" varStatus="status">
				<li class="showBig">
					<a href="${base}/content.do?id=${mv.movieid}&fromModule=indexMovie480p"  title="《${mv.name}》"><img src="${static}${mv.face150x220}" style="float:left" width="115px" height="169px"/></a>
					<div style="float:left">
						<a href="${base}/content.do?id=${mv.movieid}&fromModule=indexMovie480p" class="sBigTitle" title="《${mv.name}》">《${mv.name}》</a>
						<a href="${base}/content.do?id=${mv.movieid}&fromModule=indexMovie480p" class="sBigDes" title="${mv.shortdesc}">${mv.shortdesc}</a>
					</div>
				</li>
				</c:forEach>
			</ul>
			<ul>
				<c:forEach var="mv" items="${Movies480p}" begin="2" end="6" varStatus="status">
				<li class="showSmall">
					<a href="${base}/content.do?id=${mv.movieid}&fromModule=indexMovie480p"  title="《${mv.name}》"><img src="${static}${mv.face150x220}" width="90px" height="132px" /></a>
					<a href="${base}/content.do?id=${mv.movieid}&fromModule=indexMovie480p"  title="《${mv.name}》" class="sSmallDes">《${mv.name}》</a>
				</li>
				</c:forEach>
			</ul>
		</div>
		<div class="movieShow" style="display:none">
			<ul>
				<c:forEach var="mv" items="${Movies720p}" begin="0" end="1" varStatus="status">
				<li class="showBig">
					<a href="${base}/content.do?id=${mv.movieid}&fromModule=indexMovie720p" title="《${mv.name}》"><img src="${static}${mv.face150x220}" style="float:left" width="115px" height="169px"/></a>
					<div style="float:left">
						<a href="${base}/content.do?id=${mv.movieid}&fromModule=indexMovie720p" class="sBigTitle" title="《${mv.name}》">《${mv.name}》</a>
						<a href="${base}/content.do?id=${mv.movieid}&fromModule=indexMovie720p" class="sBigDes" title="${mv.shortdesc}">${mv.shortdesc}</a>
					</div>
				</li>
				</c:forEach>
			</ul>
			<ul>
				<c:forEach var="mv" items="${Movies720p}"  begin="2"  end="6"  varStatus="status">
				<li class="showSmall">
					<a href="${base}/content.do?id=${mv.movieid}&fromModule=indexMovie720p" title="《${mv.name}》"><img src="${static}${mv.face150x220}" width="90px" height="132px" /></a>
					<a href="${base}/content.do?id=${mv.movieid}&fromModule=indexMovie720p" title="《${mv.name}》" class="sSmallDes">《${mv.name}》</a>
				</li>
				</c:forEach>
			</ul>
		</div>
		<div class="movieShow" style="display:none">
			<ul>
				<c:forEach var="mv" items="${Movies1080p}" begin="0" end="1" varStatus="status">
				<li class="showBig">
					<a href="${base}/content.do?id=${mv.movieid}&fromModule=indexMovie1080p" title="《${mv.name}》"><img src="${static}${mv.face150x220}" style="float:left" width="115px" height="169px"/></a>
					<div style="float:left">
						<a href="${base}/content.do?id=${mv.movieid}&fromModule=indexMovie1080p" class="sBigTitle" title="《${mv.name}》">《${mv.name}》</a>
						<a href="${base}/content.do?id=${mv.movieid}&fromModule=indexMovie1080p" class="sBigDes" title="${mv.shortdesc}">${mv.shortdesc}</a>
					</div>
				</li>
				</c:forEach>
			</ul>
			<ul>
				<c:forEach var="mv" items="${Movies1080p}" begin="2" end="6" varStatus="status">
				<li class="showSmall">
					<a href="${base}/content.do?id=${mv.movieid}&fromModule=indexMovie1080p" title="《${mv.name}》"><img src="${static}${mv.face150x220}" width="90px" height="132px" /></a>
					<a href="${base}/content.do?id=${mv.movieid}&fromModule=indexMovie1080p" title="《${mv.name}》" class="sSmallDes">《${mv.name}》</a>
				</li>
				</c:forEach>
			</ul>
		</div>
		<div class="movieShow" style="display:none">
			<ul>
				<c:forEach var="mv" items="${otherMVMovies}" begin="0" end="1" varStatus="status">
				<li class="showBig">
					<a href="${base}/content.do?id=${mv.movieid}&fromModule=indexMovieOther" title="《${mv.name}》"><img src="${static}${mv.face150x220}" style="float:left" width="115px" height="169px"/></a>
					<div style="float:left">
						<a href="${base}/content.do?id=${mv.movieid}&fromModule=indexMovieOther" class="sBigTitle" title="《${mv.name}》">《${mv.name}》</a>
						<a href="${base}/content.do?id=${mv.movieid}&fromModule=indexMovieOther" class="sBigDes" title="${mv.shortdesc}">${mv.shortdesc}</a>
					</div>
				</li>
				</c:forEach>
			</ul>
			<ul>
				<c:forEach var="mv" items="${otherMVMovies}" begin="2" end="6" varStatus="status">
				<li class="showSmall">
					<a href="${base}/content.do?id=${mv.movieid}&fromModule=indexMovieOther" title="《${mv.name}》"><img src="${static}${mv.face150x220}" width="90px" height="132px" /></a>
					<a href="${base}/content.do?id=${mv.movieid}&fromModule=indexMovieOther" title="《${mv.name}》" class="sSmallDes">《${mv.name}》</a>
				</li>
				</c:forEach>
			</ul>
		</div>
		<div class="movieRank">
			<ul>
				<c:forEach var="mv" items="${rankMVMovies}" begin="0" end="2" varStatus="status">
				<li class="topMovie" title="${mv.name}"><span class="rank${status.count}">${status.count}</span><span class="rankScore">${mv.score}</span><a href="${base}/content.do?id=${mv.movieid}&fromModule=indexMovieRank"><img src="${static}${mv.face80x80}" /><p class="topMName">${mv.name}</p></a></li>
				</c:forEach>
				<li class="clear"></li>
			</ul>
			<ul style="margin: 10px 15px 0 20px;">
				<c:forEach var="mv" items="${rankMVMovies}" begin="3" end="11" varStatus="status">
				<li class="bottomMovie" title="${mv.name}"><span class="rank${status.count+3}">${status.count+3}</span><span class="btmMName"><a href="${base}/content.do?id=${mv.movieid}&fromModule=indexMovieRank">${mv.name}</a></span><span class="btmMScore">${mv.score}</span></li>
				</c:forEach>
			</ul>
		</div>
		<div class="clear"></div>
	</div>
	<div class="clear"></div>
</div>
<div class="movie">
	<span class="movieLabel">电视剧</span>
	<div class="movieCont">
		<ul class="movieItem">
			<li class="mItem mItemClick" hoverClass="mItemHover" otherClass="mItemClick" onclick="mvClick(this)">英美</li>
			<li class="mItem" hoverClass="mItemHover" otherClass="mItemClick" onclick="mvClick(this)">日韩</li>
			<li class="mItem" hoverClass="mItemHover" otherClass="mItemClick" onclick="mvClick(this)">港台</li>
			<li class="mItem" hoverClass="mItemHover" otherClass="mItemClick" onclick="mvClick(this)">内地</li>
		</ul>
		<div class="movieShow">
			<ul>
				<c:forEach var="mv" items="${americaMovies}" begin="0" end="1" varStatus="status">
				<li class="showBig">
					<a href="${base}/content.do?id=${mv.movieid}&fromModule=indexTvAmerica" title="《${mv.name}》"><img src="${static}${mv.face150x220}" style="float:left" width="115px" height="169px"/></a>
					<div style="float:left">
						<a href="${base}/content.do?id=${mv.movieid}&fromModule=indexTvAmerica" class="sBigTitle" title="《${mv.name}》">《${mv.name}》</a>
						<a href="${base}/content.do?id=${mv.movieid}&fromModule=indexTvAmerica" class="sBigDes" title="${mv.shortdesc}">${mv.shortdesc}</a>
					</div>
				</li>
				</c:forEach>
			</ul>
			<ul>
				<c:forEach var="mv" items="${americaMovies}" begin="2" end="6" varStatus="status">
				<li class="showSmall">
					<a href="${base}/content.do?id=${mv.movieid}&fromModule=indexTvAmerica" title="《${mv.name}》"><img src="${static}${mv.face150x220}" width="90px" height="132px" /></a>
					<a href="${base}/content.do?id=${mv.movieid}&fromModule=indexTvAmerica" title="《${mv.name}》" class="sSmallDes">《${mv.name}》</a>
				</li>
				</c:forEach>
			</ul>
		</div>
		<div class="movieShow" style="display:none">
			<ul>
				<c:forEach var="mv" items="${japanMovies}" begin="0" end="1" varStatus="status">
				<li class="showBig">
					<a href="${base}/content.do?id=${mv.movieid}&fromModule=indexTvJapan" title="《${mv.name}》"><img src="${static}${mv.face150x220}" style="float:left" width="115px" height="169px"/></a>
					<div style="float:left">
						<a href="${base}/content.do?id=${mv.movieid}&fromModule=indexTvJapan" class="sBigTitle" title="《${mv.name}》">《${mv.name}》</a>
						<a href="${base}/content.do?id=${mv.movieid}&fromModule=indexTvJapan" class="sBigDes" title="${mv.shortdesc}">${mv.shortdesc}</a>
					</div>
				</li>
				</c:forEach>
			</ul>
			<ul>
				<c:forEach var="mv" items="${japanMovies}" begin="2" end="6" varStatus="status">
				<li class="showSmall">
					<a href="${base}/content.do?id=${mv.movieid}&fromModule=indexTvJapan" title="《${mv.name}》"><img src="${static}${mv.face150x220}" width="90px" height="132px" /></a>
					<a href="${base}/content.do?id=${mv.movieid}&fromModule=indexTvJapan" title="《${mv.name}》" class="sSmallDes">《${mv.name}》</a>
				</li>
				</c:forEach>
			</ul>
		</div>
		<div class="movieShow" style="display:none">
			<ul>
				<c:forEach var="mv" items="${hongkongMovies}" begin="0" end="1" varStatus="status">
				<li class="showBig">
					<a href="${base}/content.do?id=${mv.movieid}&fromModule=indexTvHongkong" title="《${mv.name}》"><img src="${static}${mv.face150x220}" style="float:left" width="115px" height="169px"/></a>
					<div style="float:left">
						<a href="${base}/content.do?id=${mv.movieid}&fromModule=indexTvHongkong" class="sBigTitle" title="《${mv.name}》">《${mv.name}》</a>
						<a href="${base}/content.do?id=${mv.movieid}&fromModule=indexTvHongkong" class="sBigDes" title="${mv.shortdesc}">${mv.shortdesc}</a>
					</div>
				</li>
				</c:forEach>
			</ul>
			<ul>
				<c:forEach var="mv" items="${hongkongMovies}" begin="2" end="6" varStatus="status">
				<li class="showSmall">
					<a href="${base}/content.do?id=${mv.movieid}&fromModule=indexTvHongkong" title="《${mv.name}》"><img src="${static}${mv.face150x220}" width="90px" height="132px" /></a>
					<a href="${base}/content.do?id=${mv.movieid}&fromModule=indexTvHongkong" title="《${mv.name}》" class="sSmallDes">《${mv.name}》</a>
				</li>
				</c:forEach>
			</ul>
		</div>
		<div class="movieShow" style="display:none">
			<ul>
				<c:forEach var="mv" items="${chinaMovies}" begin="0" end="1" varStatus="status">
				<li class="showBig">
					<a href="${base}/content.do?id=${mv.movieid}&fromModule=indexTvChina" title="《${mv.name}》"><img src="${static}${mv.face150x220}" style="float:left" width="115px" height="169px"/></a>
					<div style="float:left">
						<a href="${base}/content.do?id=${mv.movieid}&fromModule=indexTvChina" class="sBigTitle" title="《${mv.name}》">《${mv.name}》</a>
						<a href="${base}/content.do?id=${mv.movieid}&fromModule=indexTvChina" class="sBigDes" title="${mv.shortdesc}">${mv.shortdesc}</a>
					</div>
				</li>
				</c:forEach>
			</ul>
			<ul>
				<c:forEach var="mv" items="${chinaMovies}" begin="2" end="6" varStatus="status">
				<li class="showSmall">
					<a href="${base}/content.do?id=${mv.movieid}&fromModule=indexTvChina" title="《${mv.name}》"><img src="${static}${mv.face150x220}" width="90px" height="132px" /></a>
					<a href="${base}/content.do?id=${mv.movieid}&fromModule=indexTvChina" title="《${mv.name}》" class="sSmallDes">《${mv.name}》</a>
				</li>
				</c:forEach>
			</ul>
		</div>
		<div class="movieRank">
			<ul>
				<c:forEach var="mv" items="${rankMVMovies}" begin="0" end="2" varStatus="status">
				<li class="topMovie" title="${mv.name}"><span class="rank${status.count}">${status.count}</span><span class="rankScore">${mv.score}</span><a href="${base}/content.do?id=${mv.movieid}&fromModule=indexTvRank"><img src="${static}${mv.face80x80}" /><p class="topMName">${mv.name}</p></a></li>
				</c:forEach>
				<li class="clear"></li>
			</ul>
			<ul style="margin: 10px 15px 0 20px;">
				<c:forEach var="mv" items="${rankMVMovies}" begin="3" end="11" varStatus="status">
				<li class="bottomMovie" title="${mv.name}"><span class="rank${status.count+3}">${status.count+3}</span><span class="btmMName"><a href="${base}/content.do?id=${mv.movieid}&fromModule=indexTvRank">${mv.name}</a></span><span class="btmMScore">${mv.score}</span></li>
				</c:forEach>
			</ul>
		</div>
		<div class="clear"></div>
	</div>
	<div class="clear"></div>
</div>
<div class="coverflow">
	<ul class="roundabout-holder">
		<a href="javascript:void(0);" class="raNext">&lt;</a>
		<a href="javascript:void(0);" class="raPrev">&gt;</a>
		<c:forEach var="mv" items="${circuitMovies}" begin="0" end="12" varStatus="status">
		<li><a href="${base}/content.do?id=${mv.movieid}&fromModule=indexCarrousel" title="${mv.name}"><img src="${static}${mv.face220x169}" width="100%" height="100%"/></a></li>
		</c:forEach>
	</ul>
	<div class="clear"></div>
</div>
<div class="guest" >
	<ul class="guestLabels">
		<li class="siterLabel">站长推荐</li>
	</ul>
	<ul class="guestItems">
		<c:forEach var="mv" items="${siterMovies}" begin="0" end="5" varStatus="status">
		<li class="guestItem" title="${mv.name}"><span class="rankScore">${mv.score}</span><a href="${base}/content.do?id=${mv.movieid}&fromModule=indexSiter" class="guestDes"><img src="${static}${mv.face150x220}" />${mv.name}</a></li>
		</c:forEach>
		<li style="clear:both"></li>
	</ul>
	<div class="clear"></div>
</div>
<div class="guest">
	<ul class="guestLabels">
		<li class="guestLabel guestLClick" hoverClass="guestLabelHover" otherClass="guestLClick" onclick="guestLClick(this)">猜你喜欢</li>
		<c:if test="${fn:length(recentMovies)>0}"><li class="guestLabel" hoverClass="guestLabelHover" otherClass="guestLClick" onclick="guestLClick(this)">最近浏览</li></c:if>
		<c:if test="${fn:length(favoriteMovies)>0}"><li class="guestLabel" hoverClass="guestLabelHover" otherClass="guestLClick" onclick="guestLClick(this)">个人收藏</li></c:if>
		<li style="clear:both"></li>
	</ul>
	<div class="guestLine"></div>
	<ul class="guestItems">
		<c:forEach var="mv" items="${youLikeMovies}"  varStatus="status">
		<li class="guestItem" title="${mv.name}"><span class="rankScore">${mv.score}</span><a href="${base}/content.do?id=${mv.movieid}" class="guestDes"><img src="${static}${mv.face150x220}" />${mv.name}</a></li>
		</c:forEach>
		<li style="clear:both"></li>
	</ul>
	<c:if test="${fn:length(recentMovies)>0}">
		<ul class="guestItems" style="display:none">
			<c:forEach var="mv" items="${recentMovies}"  begin="0" end="5"  varStatus="status">
			<li class="guestItem" title="${mv.name}"><span class="rankScore">${mv.score}</span><a href="${base}/content.do?id=${mv.movieid}" class="guestDes"><img src="${static}${mv.face150x220}" />${mv.name}</a></li>
			</c:forEach>
			<li style="clear:both"></li>
		</ul>
	</c:if>
	<c:if test="${fn:length(favoriteMovies)>0}">
		<ul class="guestItems" style="display:none">
			<c:forEach var="mv" items="${favoriteMovies}"  varStatus="status">
			<li class="guestItem" title="${mv.name}"><span class="rankScore">${mv.score}</span><a href="${base}/content.do?id=${mv.movieid}" class="guestDes"><img src="${static}${mv.face150x220}" />${mv.name}</a></li>
			</c:forEach>
			<li style="clear:both"></li>
		</ul>
	</c:if>
	<div class="clear"></div>
</div>

<%@ include file="foot.jsp" %>