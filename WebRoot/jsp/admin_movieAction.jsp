<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>


<div class="actionMovie">
	<input type="hidden" name="id" value="${wrapper.movie.movieid}" ></input>
	<h1 class="actMvTitle">基本属性</h1>
	<div class="actMvTr">
		<span class="actMvColumn">名称：</span>
		<input name="name" type="text" value="${wrapper.movie.name}" class="mvColInput" focusClass="inputF" autocomplete="off" />
	</div>
	<div class="actMvTr">
		<span class="actMvColumn">类型：</span>
		<input name="type" type="radio" value="mv"  ${wrapper.movie.type=="mv"?"checked='checked'":""} autocomplete="off" /> 电影 <input name="type" type="radio" value="tv"  ${wrapper.movie.type=="tv"?"checked='checked'":""} autocomplete="off" /> 电视剧 
	</div>
	<div class="actMvTr">
		<span class="actMvColumn">精短简介：</span>
		<input name="shortDesc" type="text" value="${wrapper.movie.shortdesc}" class="mvColInput" focusClass="inputF" autocomplete="off" /><span class="mvColInputTip">示例：超级英雄在天朝的'苦逼'岁月</span>
	</div>
	<div class="actMvTr">
		<span class="actMvColumn" style="float:left">详细简介：</span> 
		<textarea name="longDesc" class="mvTextareaInput" focusClass="inputF" style="float:left;margin:6px 0 10px 3px">${wrapper.movie.longdesc}</textarea>
	</div>
	<div class="actMvTr">
		<span class="actMvColumn">附加属性：</span>
		评分-<input name="score" type="text" value="${wrapper.movie.score}" class="mvExtraInput" focusClass="inputF" maxlength="3" autocomplete="off" /> 赞数-<input name="approve" type="text" value="${wrapper.movie.approve}" class="mvExtraInput" focusClass="inputF" maxlength="10" autocomplete="off" />  下载-<input name="download" type="text" value="${wrapper.movie.download}" class="mvExtraInput" focusClass="inputF" maxlength="10" autocomplete="off" /> 浏览-<input name="browse" type="text" value="${wrapper.movie.broswer}" class="mvExtraInput" focusClass="inputF" maxlength="10" autocomplete="off" />
	</div>
	<div class="actMvTr">
		<span class="actMvColumn">发布时间：</span>
		<input name="publishDate" type="text" value="${wrapper.movie.strPubTime}" class="Wdate"  autocomplete="off"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
	</div>
	<div class="actMvTr">
		<span class="actMvColumn">附件管理：</span>
		<input id="attachUrl"  type="text"  title="示例：http://www.baidu.com/1.rar" class="mvColInput" focusClass="inputF" autocomplete="off" /><input id="attachName" type="text" title="示例：百度网盘" class="mvColInput" focusClass="inputF"  autocomplete="off" />
		<span class="mvUpContainer">
			<input type="button" value="上传" hoverclass="adminBtnHover" class="adminBtn" />
			<div class="mvUploader"><span uploadUrl="${base}/upMoviePic.json" maxSize="1024GB" types="*.*" desc="请选择附件" upsuccess="upMovieSuccess" uperror="upMovieError"></span></div>
		</span>
		<input type="button" onclick="addAttach(this)" value="添加" hoverclass="adminBtnHover" class="adminBtn" />
		<ul class="attachContainer">
			<c:forEach var="item" items="${wrapper.attachs}"  varStatus="status">
				<li><a name="attachs" href="${item.absoluteAttachUrl}" value="${item.new_name}_${item.absoluteAttachUrl}_${item.old_name}" class="attachItem" title="${item.old_name}">${item.new_name}</a><a href="javascript:void(0)" onclick="$(this).parent().remove()" style="color:#f41c54">删除</a></li>
			</c:forEach>
		</ul>
	</div>
	<div class="actMvTr" style="margin-bottom:10px">
		<span class="actMvColumn" style="float:left">发布版块：</span>
		<ul style="float:left;line-height:15px;margin:6px 0 0 0;">
			<li><input name="modules" type="checkbox"  autocomplete="off"  ${wrapper.inIndexShow?"checked='checked' ":""}           value="首页-展示区"  /> 首页-展示区 </li>
			<li><input name="modules" type="checkbox"  autocomplete="off"  ${wrapper.inIndexCarrousel?"checked='checked' ":""}     value="首页-论播区"/> 首页-论播区 </li>
			<li><input name="modules" type="checkbox"  autocomplete="off"  ${wrapper.inIndexSiter?"checked='checked' ":""}             value="首页-站长区"/> 首页-站长区 </li>
			<li><input name="modules" type="checkbox"  autocomplete="off"  ${wrapper.inIndexMovie480p?"checked='checked' ":""}   value="首页-电影-480p"/> 首页-电影-480p </li>
			<li><input name="modules" type="checkbox"  autocomplete="off"  ${wrapper.inIndexMovie720p?"checked='checked' ":""}   value="首页-电影-720p"/> 首页-电影-720p </li>
			<li><input name="modules" type="checkbox"  autocomplete="off"  ${wrapper.inIndexMovie1080p?"checked='checked' ":""} value="首页-电影-1080p"/> 首页-电影-1080p </li>
			<li><input name="modules" type="checkbox"  autocomplete="off"  ${wrapper.inIndexMovieOther?"checked='checked' ":""}  value="首页-电影-其它"/> 首页-电影-其它 </li>
			<li><input name="modules" type="checkbox"  autocomplete="off"  ${wrapper.inIndexMovieRank?"checked='checked' ":""}    value="首页-电影-排行榜"/> 首页-电影-排行榜 </li>
			<li><input name="modules" type="checkbox"  autocomplete="off"  ${wrapper.inIndexTvAmerica?"checked='checked' ":""}     value="首页-电视剧-英美"/> 首页-电视剧-英美 </li>
			<li><input name="modules" type="checkbox"  autocomplete="off"  ${wrapper.inIndexTvJapan?"checked='checked' ":""}         value="首页-电视剧-日韩"/> 首页-电视剧-日韩 </li>
			<li><input name="modules" type="checkbox"  autocomplete="off"  ${wrapper.inIndexTvHongkong?"checked='checked' ":""} value="首页-电视剧-港台"/> 首页-电视剧-港台 </li>
			<li><input name="modules" type="checkbox"  autocomplete="off"  ${wrapper.inIndexTvChina?"checked='checked' ":""}         value="首页-电视剧-内地"/> 首页-电视剧-内地 </li>
			<li><input name="modules" type="checkbox"  autocomplete="off"  ${wrapper.inIndexTvRank?"checked='checked' ":""}          value="首页-电视剧-排行榜"/> 首页-电视剧-排行榜 </li>
		</ul>
		<ul style="float:left;line-height:15px;margin:6px 0 0 10px;">
			<li><input name="modules" type="checkbox"  autocomplete="off"  ${wrapper.inMovieShow?"checked='checked' ":""}value="电影-展示区"/> 电影-展示区 </li>
			<li><input name="modules" type="checkbox"  autocomplete="off"  ${wrapper.inMovie480p?"checked='checked' ":""}value="电影-480p"/> 电影-480p </li>
			<li><input name="modules" type="checkbox"  autocomplete="off"  ${wrapper.inMovie720p?"checked='checked' ":""}value="电影-720p"/> 电影-720p </li>
			<li><input name="modules" type="checkbox"  autocomplete="off"  ${wrapper.inMovie1080p?"checked='checked' ":""}value="电影-1080p"/> 电影-1080p </li>
			<li><input name="modules" type="checkbox"  autocomplete="off"  ${wrapper.inMovieOther?"checked='checked' ":""}value="电影-其它"/> 电影-其它 </li>
		</ul>
		<ul style="float:left;line-height:15px;;margin:6px 0 0 30px;">
			<li><input name="modules" type="checkbox"  autocomplete="off"  ${wrapper.inTvShow?"checked='checked' ":""}value="电视剧-展示区"/> 电视剧-展示区 </li>
			<li><input name="modules" type="checkbox"  autocomplete="off"  ${wrapper.inTvAmerica?"checked='checked' ":""}value="电视剧-英美"/> 电视剧-英美 </li>
			<li><input name="modules" type="checkbox"  autocomplete="off"  ${wrapper.inTvJapan?"checked='checked' ":""}value="电视剧-日韩"/> 电视剧-日韩 </li>
			<li><input name="modules" type="checkbox"  autocomplete="off"  ${wrapper.inTvHongkong?"checked='checked' ":""}value="电视剧-港台"/> 电视剧-港台 </li>
			<li><input name="modules" type="checkbox"  autocomplete="off"  ${wrapper.inTvChina?"checked='checked' ":""}value="电视剧-内地"/> 电视剧-内地 </li>
		</ul>
	</div>
	<h1 class="actMvTitle">基本图片</h1>
	<ul class="baseMvImgContainer">
		<li class="baseImgItem">
			<p>650x500（展示区图）</p>
			<img name="face650x500" src="${static}${wrapper.movie.face650x500}"/>
			<div>
				<input type="button" value="上传" hoverclass="adminBtnHover" class="adminBtn" />
				<div class="mvUploader"><span uploadUrl="${base}/upMoviePic.json" maxSize="1024GB" types="*.*" desc="650x500（展示区图）" upsuccess="upImgSuccess" uperror="upMovieError"></span></div>
			</div>
		</li>
		<li class="baseImgItem">
			<p>400x308（内容页）</p>
			<img name="face400x308" src="${static}${wrapper.movie.face400x308}"/>
			<div>
				<input type="button" value="上传" hoverclass="adminBtnHover" class="adminBtn" />
				<div class="mvUploader"><span uploadUrl="${base}/upMoviePic.json" maxSize="1024GB" types="*.*" desc="请选择附件" upsuccess="upImgSuccess" uperror="upMovieError"></span></div>
			</div>
		</li>
		<li class="baseImgItem">
			<p>220x169（轮播区图、搜索页）</p>
			<img name="face220x169" src="${static}${wrapper.movie.face220x169}"/>
			<div>
				<input type="button" value="上传" hoverclass="adminBtnHover" class="adminBtn" />
				<div class="mvUploader"><span uploadUrl="${base}/upMoviePic.json" maxSize="1024GB" types="*.*" desc="请选择附件" upsuccess="upImgSuccess" uperror="upMovieError"></span></div>
			</div>
		</li>
		<li class="baseImgItem">
			<p>150x220（站长、电影、电视）</p>
			<img name="face150x220" src="${static}${wrapper.movie.face150x220}"/>
			<div>
				<input type="button" value="上传" hoverclass="adminBtnHover" class="adminBtn" />
				<div class="mvUploader"><span uploadUrl="${base}/upMoviePic.json" maxSize="1024GB" types="*.*" desc="请选择附件" upsuccess="upImgSuccess" uperror="upMovieError"></span></div>
			</div>
		</li>
		<li class="baseImgItem">
			<p>80x80（排行榜）</p>
			<img name="face80x80" src="${static}${wrapper.movie.face80x80}"/>
			<div>
				<input type="button" value="上传" hoverclass="adminBtnHover" class="adminBtn" />
				<div class="mvUploader"><span uploadUrl="${base}/upMoviePic.json" maxSize="1024GB" types="*.*" desc="请选择附件" upsuccess="upImgSuccess" uperror="upMovieError"></span></div>
			</div>
		</li>
		<li style="clear:both"></li>
	</ul>
	<div style="height:10px"></div>
	<h1 class="actMvTitle">影视截图</h1>
	<ul class="baseMvImgContainer">
		<li style="text-align:right;margin-top:10px;">
			<div>
				<input type="button" value="上传" hoverclass="adminBtnHover" class="adminBtn" />
				<div class="mvUploader"><span uploadUrl="${base}/upMoviePic.json" maxSize="1024GB" types="*.*" desc="请选择附件" upsuccess="mvPreviewImg" uperror="upMovieError"></span></div>
			</div>
		</li>
		<c:forEach var="pictue" items="${wrapper.movie.pictureAsArray}"  varStatus="status">
			<li class="baseImgItem"><img name="pictures" src="${static}${pictue}" /> <input type="button" value="删除" hoverclass="adminBtnHover" class="adminBtn" onclick="$(this).parent().remove()"/> </li>
		</c:forEach>
		<li style="clear:both"></li>
	</ul>
	<div style="text-align:right;margin-bottom:20px;">
		<img src="${static}/img/loading.gif" class="addMvLoad"/>
		<input type="button" value="提交" hoverclass="adminBtnHover" class="adminBtn" onclick="addMovie(this)"/>
	</div>
</div>