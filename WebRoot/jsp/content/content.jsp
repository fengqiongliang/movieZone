<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<jsp:include page="/jsp/common/head.jsp" />

<div id="main">
	<div id="show"></div>
	<div class="left">
		<div class="title">
			<p class="des" style="margin:0 0 0 10px">高清电影</p>
		</div>
		<div>
			<div style="line-height:25px">
				<div style="float:right;margin:0" class="btnAct btnHelp"  onclick="sure(this,'movie')">确定</div>	
				<div style="float:right;margin:0">
					<input type="checkbox" value="480p"  checked="checked"/><span style="color:gray">480p&nbsp</span>
					<input type="checkbox" value="720p"  checked="checked"/><span style="color:gray">720p&nbsp</span>
					<input type="checkbox" value="1080p" checked="checked"/><span style="color:gray">1080p&nbsp</span>
					<input type="checkbox" value="other" checked="checked"/><span style="color:gray">其它&nbsp&nbsp</span>
				</div>
			</div>
			<jsp:include page="conNav.jsp" />
		</div>
		
	</div>
	<div class="right">
		<div class="title">
			<p class="des" style="margin:0 0 0 10px">忠烈杨家将</p>
		</div>
		<jsp:include page="detail.jsp" />
		<div class="inputCont">
			<div class="cmmtHead">
					<p class="from">来自:海南</p>
						<img src="img/movie1.jpg" width="65px" height="65px"/>
					<p class="nickname">最美爱好者最美爱好者最美爱好者最美爱好者</p>
			</div>
			<div class="talkArrow talkArrowExt"></div>
			<div style="float:left;margin-left:-3px">
				<div class="areaCont">
					<textarea id="commentInput" class="textInput" onkeyup="feedInput(this,event)" maxLength="250"></textarea>
					<span id="fontCount" class="fontCount">0/250</span>
				</div>
				<div class="inputHelper">
					<img src="img/qqemotion/0.gif" onclick="showEmotion(this,event)" class="emotionImg"/>
					<button id="pubBtn" class="pubBtn" onclick="feedPub(this,event)">发布</button>
				</div>
			</div>
		</div>
	</div>	
</div>

<jsp:include page="/jsp/common/foot.jsp" />
