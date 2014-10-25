<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="head.jsp" %>


<div class="contentH">
	 <img class="cntImg" src="${static}${movie.face400x308}" />
	 <ul class="cntR">
		<li class="cntRli">【推荐】：<c:forEach begin="1" end="${fullStarCount}" ><span class="starFull"></span></c:forEach><c:forEach begin="1" end="${partStarCount}" ><span class="starPart"></span></c:forEach><c:forEach begin="1" end="${blankStarCount}" ><span class="starBlank"></span></c:forEach><span class="cntScore">${movie.score}</span></li>
		<c:if test="${fn:length(type)>1}">
		<li class="cntRli">【类型】：<span class="liDes" style="font-size:16px;">${type}</span></li>
		</c:if>
		<li class="cntRli">【名称】：<span class="liDes">${movie.name}</span></li>
		<c:if test="${!empty attachs}">
		<li class="cntRli">
			<span style="float:left">【附件】：</span>
			<div style="float:left;font-size:12px;width:440px;">
				<c:forEach var="attach"  items="${attachs}"  varStatus="status">
				<a href="${attach. attach_url}"><span class="zip"></span>${attach.new_name}</a><br/>
				</c:forEach>
			</div>
		</li>
		</c:if>
	 </ul>
	 <p class="contentDes">${movie.longdesc}</p>
</div>

<div class="cntImgs">
	<h1 class="cntImgH">影视截图</h1>
	<c:forEach var="p" items="${movie.pictureAsArray}"  varStatus="status">
		<img class="cntShowImg" src="${static}${p}" />
	</c:forEach>
</div>

<div class="cntImgs" id="cmmts">
	<h1 class="cntImgH">影视评论</h1>
	<%@ include file="content_cmmts.jsp" %>
	<div class="moreComment" hoverClass="moreCmmtHover" movieid="${movie.movieid}" onclick="getMoreCmmt(this)" ${fn:length(cmmtReplys)<10?"style='display:none'":""}><img class="moreCmmtLoading" src="${static}/img/loading.gif"   />加载更多</div>
	<div class="cmmtSubmit">
		<div class="cmmtHead">
			<p class="nickname">${!empty user?user.nickname:"请先登录"}</p>
			<img class="cmmtImg" src='${static}${!empty user?user.faceurl:"/img/blank92x71.gif"}' />
			<c:if test="${empty user}">
				<p class="imgModifyTip"><a href="javascript:goReg()">登录</a></p>
			</c:if>
			<c:if test="${!empty user}">
				<img class="nickImgLoading" src="${static}/img/loading.gif" />
				<p class="from">来自:${createarea}</p>
			</c:if>
		</div>
		<div class="cmntCont">
			<span class="cmntArrow" style="color:#ffffff">◀</span>
			<textarea class="submitText" maxlength="250" onkeyup="statWords()" onclick="closeEmotion();closeSubSure();" movieid="${movie.movieid}"></textarea>
			<div class="subInfo subFailure">你的输入不正确~~~~~</div>
			<div class="subTip">
				<span class="tipStat">0/250</span>
				<img  class="tipImg" src="${static}/img/qqemotion/0.gif" onclick="showEmotion()"/>
				<c:if test="${!empty user}"><a class="tipBtn" hoverClass="tipBtnHover" href="javascript:showSubSure()">提交</a></c:if>
			</div>
			<div class="emotion"></div>
			<div class="subSure">
				<img class="sureImg" src="http://weibo.com/signup/v5/pincode/pincode.php?lang=zh&sinaId=5a7bb5c5930b3d6e00e0dd972c9c5b6a&r=1403229441" onclick="this.src = this.src+'&1=1'"/>
				<input class="sureInput" type="text" maxLength="4" focusClass="inputF"></input>
				<a class="tipBtn" hoverClass="tipBtnHover" href="javascript:submitCmmt()">确定</a>
				<img class="loadCmmt" src="${static}/img/loading.gif" />
				<span class="subPointer">▼</span>
			</div>
		</div>
		<div class="clear"></div>
	</div>
</div>

<div class="centerCmmt">
	<a href="javascript:closeReply();" hoverclass="closeRegHover" class="closeReg" style="right:-14px;top:10px;z-index:1;"></a>
</div>

<%@ include file="foot.jsp" %>