<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="head.jsp" %>


<div class="contentH">
	 <img class="cntImg" src="${static}${movie.face400x308}" />
	 <ul class="cntR">
		<li class="cntRli">【推荐】：<c:forEach begin="1" end="${fullStarCount}" ><span class="starFull"></span></c:forEach><c:forEach begin="1" end="${partStarCount}" ><span class="starPart"></span></c:forEach><c:forEach begin="1" end="${blankStarCount}" ><span class="starBlank"></span></c:forEach><span class="cntScore">${movie.score}</span></li>
		<li class="cntRli">【类型】：<span class="liDes">中国 480p/720p/其它</span></li>
		<li class="cntRli">【名称】：<span class="liDes">${movie.name}</span></li>
		<li class="cntRli">【附件】：<a href="./zip/a.xlsx"><span class="zip"></span>大闹天宫.torrent</a></li>
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
	<div id="cmmt1" class="cmmtDiv">
		<div class="cmmtHead">
			<p class="nickname">最美爱好者</p>
			<img class="cmmtImg" src="./img/blank92x71.gif">
			<p class="from">来自:海南</p>
		</div>
		<div class="cmntCont">
			<span class="cmntArrow">◀</span>
			<p class="cmmtText" title="感谢这电影让我学会了如何" onmouseover="replyShow(this)" onmouseout="replyHide(this,event)">感谢这电影让我学会了如何去面对人生感谢这电影让我学会了如何去面对人生感谢这电影让我学会了如何去面对人生感谢这电影让我学会了如何去面对人生感谢这电影让我学会了如何去面对人生感谢这电影让我学会了如何去面对人生感谢这电影让我学会了如何去面对人生感谢这电影让我学会了如何去面对人生</p>
			<div class="replyTxt" onclick="reply(this)" onmouseout="replyHide(this,event)">回 复</div>
		</div>
		<div class="reply">
			<span class="leftArrow">←</span>
			<ul class="replyCont" onmouseover="moreShow(this)" onmouseout="moreHide(this,event)">
				<li class="rpyli"><img class="replyImg" src="./img/blank92x71.gif" />这是什么情况?aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa是什么情况?aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa是什么情况?aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa是什么情况?aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa</li>
				<li class="rpyli"><img class="replyImg" src="./img/blank92x71.gif" />这是什么情况?aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa是什么情况?aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa是什么情况?aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa是什么情况?aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa</li>
				<li class="rpyli"><img class="replyImg" src="./img/blank92x71.gif" />这是什么情况?aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa是什么情况?aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa是什么情况?aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa是什么情况?aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa</li>
				<li class="rpyli"><img class="replyImg" src="./img/blank92x71.gif" />这是什么情况?aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa是什么情况?aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa是什么情况?aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa是什么情况?aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa</li>
			</ul>
			<div class="moreTxt" onclick="getMoreReply(this)" onmouseout="moreHide(this,event)" commentId="1"><img class="moreReplyLoading" src="./img/loading.gif" />获 得 更 多</div>
		</div>
		<div class="clear"></div>
	</div>
	<div class="moreComment" hoverClass="moreCmmtHover" contentId="1" onclick="getMoreCmmt(this)"><img class="moreCmmtLoading" src="./img/loading.gif" />加载更多</div>
	<div class="cmmtSubmit">
		<div class="cmmtHead">
			<input class="nickInput" value="最美爱好者" type="text" maxLength="10"></input>
			<img class="cmmtImg" src="./img/blank92x71.gif"/>
			<img class="nickImgLoading" src="./img/loading.gif" />
			<div class="nickImgUploader">
				<span uploadUrl="http://localhost:8080/moviezone/helloWorld.do" maxSize="1MB" types="*.bmp;*.jpg;*.jpeg;*.png;*.gif;" desc="请选择图片文件" upstart="upNickStart" upsuccess="upNickSuccess" uperror="upNickError"></span>
			</div>
			<p class="imgModifyTip">单击图片修改</p>
		</div>
		<div class="cmntCont">
			<span class="cmntArrow" style="color:#ffffff">◀</span>
			<textarea class="submitText" maxlength="250" onkeyup="statWords()" onclick="closeEmotion();closeSubSure();"></textarea>
			<div class="subInfo subFailure">你的输入不正确~~~~~</div>
			<div class="subTip">
				<span class="tipStat">0/250</span>
				<img  class="tipImg" src="./img/qqemotion/0.gif" onclick="showEmotion()"/>
				<a class="tipBtn" hoverClass="tipBtnHover" href="javascript:showSubSure()">提交</a>
			</div>
			<div class="emotion"></div>
			<div class="subSure">
				<img class="sureImg" src="http://weibo.com/signup/v5/pincode/pincode.php?lang=zh&sinaId=5a7bb5c5930b3d6e00e0dd972c9c5b6a&r=1403229441" onclick="this.src = this.src+'&1=1'"/>
				<input class="sureInput" type="text" maxLength="4" focusClass="inputF"></input>
				<a class="tipBtn" hoverClass="tipBtnHover" href="javascript:submitCmmt()">确定</a>
				<img class="loadCmmt" src="./img/loading.gif" />
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