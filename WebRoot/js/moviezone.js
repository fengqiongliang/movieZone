$(document).ready(function(){
	init();
	adjustWidth();
	moveImg();
	moveCmmt();
	moveTheater();
	statWords();
	showEmotion();
	initAdminPic();
	initAdminTable();
});

/* 基本样式支持 */
function init(){
	$(document).on('click','[clickClass]',function(){
		var me = $(this);
		var clickClass = me.attr('clickClass');
		me.addClass(clickClass);
		setTimeout(function(){me.removeClass(clickClass);},100);
	});
	$(document).on('mouseover','[hoverClass]',function(){
		$(this).addClass($(this).attr('hoverClass'))
	});
	$(document).on('mouseout','[hoverClass]',function(){
		$(this).removeClass($(this).attr('hoverClass'))
	});
	$(document).on('focus','[focusClass]',function(){
		$(this).addClass($(this).attr('focusClass'));
	});
	$(document).on('blur','[focusClass]',function(){
		$(this).removeClass($(this).attr('focusClass'));
	});
	$(document).on('blur','[blurClass]',function(){
		$(this).addClass($(this).attr('blurClass'));
	});
	$(document).on('focus','[searchHover]',function(){
		$(this).addClass($(this).attr('searchHover'));
	});
	$(document).on('blur','[searchHover]',function(){
		$(this).removeClass($(this).attr('searchHover'))
	});
	$(document).on('keyup','.search',function(event){
		if(event.which == 13)goSearch();  //回车
	});
	var oldNav;
	var oldNavColor;
	$('.nav').each(function(event){
		var color  = $(this).css('background-color');
		if( color != undefined && color != 'transparent'){oldNav = $(this);oldNavColor=color;};
	});
	$('.nav').bind('mouseover',function(event){
		$('.nav').each(function(event){$(this).css('background-color','');});
		$(this).css('background-color',oldNavColor);
	});
	$('.nav').bind('mouseout',function(event){
		$('.nav').each(function(event){$(this).css('background-color','');});
		oldNav.css('background-color',oldNavColor);
	});
	initSWFUpload();
}

/* 搜索窗口支持 */
function goSearch(){
	var sText = $('.search').val().replace(/\s/g,'');
	if(sText.length<1){
		if($(".searchE").attr('isHiding') == 'true')return;
		$(".searchE").text('请输入搜索内容').css('display','inline-block').attr('isHiding','true');
		setTimeout(function(){$(".searchE").hide().attr('isHiding','false');},2000);
		return;
	}
	location.href = 'search.do?search='+sText;
}



/* 注册/登录窗口 */
function goReg(){
	var winWidth  = $(window).width();
	var winHeight = $(window).height();
	var regWidth  = $('.regWindow').width();
	var regHeight = $('.regWindow').height();
	var scrollLeft = navigator.userAgent.indexOf("MSIE 6")>0?$(document.body).scrollLeft():$(window).scrollLeft();
	var scrollTop  = navigator.userAgent.indexOf("MSIE 6")>0?$(document.body).scrollTop():$(window).scrollTop();
	var rightLeft = (winWidth - regWidth)/2 + scrollLeft;;
	var rightRight= (winHeight - regHeight)/2 + scrollTop;;
	$('.layer').width($(document).width());
	$('.layer').height($(document).height());
	$('.layer').show();
	$('.regWindow').show();
	$(document.body).css('overflow-y','hidden');
	$('.regWindow').css('left',rightLeft+'px');
	$('.regWindow').css('top',rightRight+'px');
	$('.regUl input:eq(0)')[0].focus();
}
function closeReg(){
	$('.layer').hide();
	$('.regWindow').hide();
	$(document.body).css('overflow-y','auto');
}
function tabReg(type,source){
	//显示注册窗口
	if(type == 1){
		$('.regText').css('background-color','#45B6D8').css('color','#FFFFFF');
		$('.loginText').css('background-color','#DDDEE3').css('color','#000000');
		$('.regUl > li:eq(3)').show();
		$('.regBut').text('立即注册');
	}
	//显示登录窗口
	if(type == 2){
		$('.regText').css('background-color','#DDDEE3').css('color','#000000');
		$('.loginText').css('background-color','#45B6D8').css('color','#FFFFFF');
		$('.regUl > li:eq(3)').hide();
		$('.regBut').text('登录');
	}
}
function clickFeild(source){
	$('span',$(source).parent()).hide();
	$('span.iTip',$(source).parent()).css('display','inline-block');
}
function checkUName(source){
	//去空格
	var val = $(source).val().replace(/\s/g,'');
	$(source).val(val);
	//用户名长度小于5位
	if(val.length<6){
		$('span',$(source).parent()).hide();
		$('span.iError',$(source).parent()).text('→ 用户名长度小于6位').css('display','inline-block');
		return;
	}
	//用户名长度大于20位
	if(val.length>20){
		$('span',$(source).parent()).hide();
		$('span.iError',$(source).parent()).text('→ 用户名长度大于20位').css('display','inline-block');
		return;
	}
	//发送ajax请求验证
	$.ajax({
        type:'POST',
        url:'checkUName.json',
        data:{username:val},
		dataType:'json',
        beforeSend:function(){
        	$('span',$(source).parent()).hide();
			$('img',$(source).parent()).show();
        }
    }).done(function(data){
    	$('span',$(source).parent()).hide();
		$('img',$(source).parent()).hide();
		//正确用户名
		if(Number(data.resultCode)==0)$('span.iSuccess',$(source).parent()).css('display','inline-block');
		//非法用户名
		if(Number(data.resultCode)!=0)$('span.iError',$(source).parent()).text('→ '+data.resultInfo).css('display','inline-block');
    }).fail(function(){
    	$('span',$(source).parent()).hide();
		$('img',$(source).parent()).hide();
		$('span.iError',$(source).parent()).text('→ 发生网络错误').css('display','inline-block');
    });
}
function checkPWD(source){
	//去空格
	var val = $(source).val().replace(/\s/g,'');
	//密码长度小于5位
	if(val.length<6){
		$('span',$(source).parent()).hide();
		$('span.iError',$(source).parent()).text('→ 密码长度小于6位').css('display','inline-block');
		return;
	}
	//密码名长度大于20位
	if(val.length>20){
		$('span',$(source).parent()).hide();
		$('span.iError',$(source).parent()).text('→ 密码长度大于20位').css('display','inline-block');
		return;
	}
	$('span',$(source).parent()).hide();
	$('span.iSuccess',$(source).parent()).css('display','inline-block');
}
function checkCode(source){
	//去空格
	var val = $(source).val().replace(/\s/g,'');
	$(source).val(val);
	//验证码长度不等于4位
	if(val.length != 4){
		$('span',$(source).parent()).hide();
		$('span.iError',$(source).parent()).text('→ 验证码长度不等于4位').css('display','inline-block');
		return;
	}
	$('span',$(source).parent()).hide();
	$('span.iSuccess',$(source).parent()).css('display','inline-block');
}
function regOrLogin(){
	var errorTip = $('.errorTip');
	var username = $('.regUl input:eq(0)').val().replace(/\s/g,'');
	var password = $('.regUl input:eq(1)').val().replace(/\s/g,'');
	var regCode  = $('.regCode').val().replace(/\s/g,'');
	var regBut   = $('.regBut');
	var type     = regBut.text().indexOf("注册",0)>0?1:2;
	//用户名长度小于5位
	if(username.length<6){
		errorTip.text('▲ 用户名长度小于6位').css('display','inline-block');
		return;
	}
	//用户名长度大于20位
	if(username.length>20){
		errorTip.text('▲ 用户名长度大于20位').css('display','inline-block');
		return;
	}
	//密码长度小于5位
	if(password.length<6){
		errorTip.text('▲ 密码长度小于6位').css('display','inline-block');
		return;
	}
	//密码名长度大于20位
	if(password.length>20){
		errorTip.text('▲ 密码长度大于20位').css('display','inline-block');
		return;
	}
	//验证码长度不等于4位
	if(type == 1 && regCode.length != 4){
		errorTip.text('▲ 验证码长度不等于4位').css('display','inline-block');
		return;
	}
	$.ajax({
        type:'POST',
        url:'regOrlog.json',
        data:{'username':username,'password':password,'regCode':regCode,'type':type},
		dataType:'json',
        beforeSend:function(){
        	regBut.hide();
			$('.regWindow > img').show();
        }
    }).done(function(data){
		regBut.show();
		$('.regWindow > img').hide();
		//操作失败
		if(Number(data.resultCode)!=0)errorTip.text('▲ '+data.resultInfo).css('display','inline-block');
		//操作成功
		if(Number(data.resultCode)==0)location.reload();
    }).fail(function(){
		errorTip.text('▲ 发生网络错误，请稍微尝试').css('display','inline-block');
    	regBut.show();
		$('.regWindow > img').hide();
    });
}

/* 弹出窗口 */
function openWnd(url){
	var layer  = $('.layer');
	var layWnd = $('.layerWindow');
	var layImg = $('>img',layWnd);
	var container = $('>div.contContainer',layWnd);
	$.ajax({
        type:'GET',
        url:url,
		dataType:'html',
        beforeSend:function(){
			container.empty();
			layImg.show();
			layer.width($(document).width());
			layer.height($(document).height());
			layer.show();
			layWnd.show();
        	$(document.body).css('overflow-y','hidden');
			centerWnd(layWnd);
        }
    }).done(function(data){
    	layImg.hide();
		container.append(data);
		var winHeight  = $(window).height();
		var contHeight = container.height();
		if(contHeight>= winHeight - 100)container.height((winHeight - 100));
		container.height(container.height());
		container.width(container.width());
		centerWnd(layWnd);
		initSWFUpload();
    }).fail(function(){
    	container.empty().append("<span style='color:red'>发生网络错误,内容加载失败</span>");
    });
}
function closeLayerWnd(){
	$('.layer').hide();
	$('.layerWindow').hide();
	$(document.body).css('overflow-y','auto');
}
function centerWnd(myWindow){
	var winWidth  = $(window).width();
	var winHeight = $(window).height();
	var myWinWidth  = $(myWindow).width();
	var myWinHeight = $(myWindow).height();
	var scrollLeft = navigator.userAgent.indexOf("MSIE 6")>0?$(document.body).scrollLeft():$(window).scrollLeft();
	var scrollTop  = navigator.userAgent.indexOf("MSIE 6")>0?$(document.body).scrollTop():$(window).scrollTop();
	var rightLeft = (winWidth - myWinWidth)/2 + scrollLeft;
	var rightRight= (winHeight - myWinHeight)/2 + scrollTop;
	$(myWindow).css('left',rightLeft+'px');
	$(myWindow).css('top',rightRight+'px');
}


/* 昵称/退出窗口,清除所有cookie */
function logout(){
	var keys = document.cookie.match(/[^ =;]+(?=\=)/g);
	for (var i = 0; keys && i < keys.length; i++){
		if(keys[i]=="JSESSIONID")continue;
		document.cookie=keys[i]+'=0;expires=' + new Date( 0).toUTCString()+";path=/";
	}
	location.reload();
}

/* 上部 */
function modifyNick(source){
	var oldValue   = $.trim($(source).attr("oldValue"));
	var nowValue =  $.trim($(source).val());
	if(oldValue == nowValue) return;
	$.ajax({
        type:'POST',
        url:'modifyNick.json',
        data:{"nickname":nowValue}
    }).done(function(data){
    	$(source).attr("oldValue",nowValue);
    	$(source).val(nowValue);
    }).fail(function(){
    	$(source).val(oldValue);
    });
}
function adjustWidth(){
	var maxWidth = $('.nickField').attr('maxWidth');
	var nowWidth = strlen($('.nickField').val())*13/2;
	$('.nickField').css('width',nowWidth>maxWidth?maxWidth:nowWidth);
}
function strlen(str) {
	var len = 0;
	for (var i = 0; str && i < str.length; i++) {
		var c = str.charCodeAt(i);
		//单字节加1 
		if ((c >= 0x0001 && c <= 0x007e) || (0xff60 <= c && c <= 0xff9f)) {
			len++;
		}else {
			len += 2;
		}
	}
	return len;
}
function getCookie(name)
{
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
 
    if(arr=document.cookie.match(reg))
 
        return unescape(arr[2]);
    else
        return null;
} 
function initSWFUpload(){
	$('[uploadUrl]').each(function(){
		var span = this;
		new SWFUpload({
			//样式设置
			button_placeholder:span,
			button_width: $(span).parent().width(),
			button_height: $(span).parent().height(),
			button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
			button_cursor: SWFUpload.CURSOR.HAND,
			
			//文件设置
			button_action:SWFUpload.BUTTON_ACTION.SELECT_FILE, /*单个文件选择*/
			file_size_limit : $(span).attr('maxSize')?$(span).attr('maxSize'):"0",
			file_types : $(span).attr('types')?$(span).attr('types'):"*.*",
			file_types_description : $(span).attr('desc')?$(span).attr('desc'):"",
			file_upload_limit : "0",
			file_queue_limit : "0",
			
			//上传设置
			flash_url : "./swf/swfupload.swf",
			upload_url: $(span).attr("uploadUrl")+";jsessionid="+getCookie("JSESSIONID"),
			file_post_name: "upFile",
			
			//选择文件窗口事件处理
			file_queued_handler : function(selFile){
				this.selFile = selFile;
				this.setPostParams({'creationdate':selFile.creationdate,'modificationdate':selFile.modificationdate});
			},
			file_queue_error_handler : function(file,code,msg){
				if(code == SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT)alert("文件大小超过"+this.settings.file_size_limit+"，请重新选择");
				if(code == SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE)alert("不能选择文件快捷方式，文件大小为零");
				if(code == SWFUpload.QUEUE_ERROR.INVALID_FILETYPE)alert("无效的文件类型，合法类型："+this.settings.file_types);
			},
			file_dialog_complete_handler : function(selFileCount,queueFileCount,totalFileCount){
				if(queueFileCount>0)this.startUpload(this.selFile.id);
			},
			
			//文件事件上传处理
			upload_start_handler : function(file){
				this.setButtonDisabled(true);
				
				if($(span).attr('upstart'))eval($(span).attr('upstart')+"(file,this)");
			},
			upload_success_handler : function(file,serverdata,resp){
				this.setButtonDisabled(false);
				if($(span).attr('upsuccess'))eval($(span).attr('upsuccess')+"(file,serverdata,resp,this)");
			},
			upload_error_handler : function(file,errorCode,msg){
				this.setButtonDisabled(false);
				if(errorCode == SWFUpload.UPLOAD_ERROR.MISSING_UPLOAD_URL)alert("请设置上传url");
				if(errorCode == SWFUpload.UPLOAD_ERROR.HTTP_ERROR)alert("无效上传url："+this.settings.upload_url+" 请确认url post请求能访问");
				if($(span).attr('uperror'))eval($(span).attr('uperror')+"(file,errorCode,msg,this)");
			},
			debug: false
		});
	});
}
function upStart(file){
	$(".loadFace").show();
}
function upSuccess(file,serverdata,resp){
	$(".loadFace").hide();
	$(".myFace").attr("src",$.parseJSON(serverdata).faceImgUrl);
}
function upError(file,errorCode,msg){
	$(".loadFace").hide();
}

/* 展示区 */
var effects = [{effect:"fade",easing:"easeInCubic",duration:1000},
			   {effect:"fold",easing:"easeInCirc",duration:1000},
			   {effect:"bounce",easing:"easeInSine",duration:1000},
			   {effect:"clip",easing:"linear",duration:1000},
			   {effect:"blind",easing:"swing",duration:1000},
			   {effect:"drop",easing:"swing",duration:1000},
			   {effect:"puff",easing:"swing",duration:2000},
			   {effect:"pulsate",easing:"easeInExpo",duration:1000},
			   {effect:"scale",easing:"easeInQuint",duration:1000},
			   {effect:"shake",easing:"easeInSine",duration:1000},
			   {effect:"slide",easing:"linear",duration:1000},
			   {effect:"size",easing:"swing",duration:1000}];
var bigImgs;
var smallImgs;
var nowImgIndex;
var effectTimer; 
function moveImg(){
	if(bigImgs==undefined)bigImgs = $(".showLeft > a");
	if(smallImgs==undefined)smallImgs = $(".imgContainer > a");
	if(nowImgIndex==undefined){nowImgIndex = 0;$(smallImgs[nowImgIndex]).addClass($(smallImgs[nowImgIndex]).attr('hoverClass'));}
	if(effectTimer!=undefined)return;
	effectTimer = setInterval(function(){
		smallImgs.each(function(index){
			$(this).removeClass($(this).attr('hoverClass'));
		});
		bigImgs.each(function(index){
			if(index == nowImgIndex)$(this).css('z-index',0);
			if(index != nowImgIndex)$(this).css('z-index',0).hide();
		});
		nowImgIndex = (nowImgIndex+1) % bigImgs.length;
		var randEffect = Math.floor(Math.random()*effects.length);
		$(bigImgs[nowImgIndex]).css('z-index',2).show(effects[randEffect]);
		$(smallImgs[nowImgIndex]).addClass($(smallImgs[nowImgIndex]).attr('hoverClass'));
	},4000);
}
function imgOver(dom){
	if(effectTimer==undefined)return;
	clearInterval(effectTimer);
	effectTimer = undefined;
	smallImgs.each(function(index){if($(this).is($(dom))) nowImgIndex = index;});
	bigImgs.each(function(index){if($(this).is($(dom))) nowImgIndex = index;});
	smallImgs.each(function(index){
		if(index == nowImgIndex)$(this).addClass($(this).attr('hoverClass'));
		if(index != nowImgIndex)$(this).removeClass($(this).attr('hoverClass'));
	});
	bigImgs.each(function(index){
		if(index == nowImgIndex)$(this).css('z-index',1).show();
		if(index != nowImgIndex)$(this).css('z-index',0).hide();
	});
}
var divH;
var ulH;
var cmmtTimer;
function moveCmmt(){
	if(divH == undefined){
		divH = $(".showRight").height();
		ulH  = $(".showRight > ul").height();
		if(ulH - divH >= 0)$(".showRight > ul").css('margin-top','-'+(ulH - divH)+'px');
		if(ulH - divH < 0 ) return;
	}
	if(cmmtTimer!=undefined)return;
	cmmtTimer = setInterval(function(){
		var firstLi = $(".showRight > ul > li:first-child");
		var lastLi  = $(".showRight > ul > li:last-child");
		var lastH   = lastLi.height();
		$(".showRight > ul").css('margin-top','-'+(ulH - divH - lastH)+'px');
		var slideEffect = {effect:"slide",easing:"swing",direction:"up",distance:lastH,duration:1000,complete:function(){
			firstLi.before(lastLi);
			$(".showRight > ul").css('margin-top','-'+(ulH - divH)+'px');
		}};
	$(".showRight > ul").effect(slideEffect);
	},4000);
}
function cmmtOver(){
	if(cmmtTimer==undefined)return;
	clearInterval(cmmtTimer);
	cmmtTimer = undefined;
}


/* 电影区 */
function moveMovie(source){
	if(source == undefined){
		 $(".movieItem").each(function(){
			var ul = this;
		    ul.mvIndex = 0;
			ul.mvLis   = $(">li",ul);
			ul.mvDivs  = $(">div.movieShow",$(ul).parent());
			ul.mvTimer = setInterval(function(){
				ul.mvIndex = (ul.mvIndex+1) % ul.mvLis.length;
				$(ul.mvLis).each(function(i){
					if( i != ul.mvIndex)$(this).removeClass($(this).attr("hoverClass"));
					if( i == ul.mvIndex)$(this).addClass($(this).attr("hoverClass"));
				});
				$(ul.mvDivs).each(function(i){
					if( i != ul.mvIndex)$(this).hide();
					if( i == ul.mvIndex)$(this).fadeIn(700);
				});
			},5000)
		});
	}
	if(source != undefined){
		var ul = $(source).parent()[0];
		ul.mvTimer = setInterval(function(){
			ul.mvIndex = (ul.mvIndex+1) % ul.mvLis.length;
			$(ul.mvLis).each(function(i){
				if( i != ul.mvIndex)$(this).removeClass($(this).attr("hoverClass"));
				if( i == ul.mvIndex)$(this).addClass($(this).attr("hoverClass"));
			});
			$(ul.mvDivs).each(function(i){
				if( i != ul.mvIndex)$(this).hide();
				if( i == ul.mvIndex)$(this).fadeIn(700);
			});
		},5000)
	}
}
function mvClick(source){
	var index = 0;
	$(">li",$(source).parent()).each(function(i){
		if( !$(this).is(source))$(this).removeClass($(this).attr("otherClass"));
		if( $(this).is(source)){$(this).addClass($(this).attr("otherClass"));index = i;}
	});
	$(">div.movieShow",$(source).parent().parent()).each(function(i){
		if( i != index)$(this).hide();
		if( i == index)$(this).fadeIn(700);
	});
}

/* 图片轮播区 */
function moveTheater(){
	$('.roundabout-holder').roundabout({
		clickToFocus:false,
		minZ:1,
		maxZ:13,
		minScale:-1.0,
		maxScale:1.4,
		btnNext: '.raNext',
		btnPrev: '.raPrev',
		autoplay: true,
		autoplayDuration: 4000,
		autoplayPauseOnHover: true
	});
}

/* 猜你喜欢/站长推荐 */
function guestLClick(source){
	var index = 0;
	$('li',$(source).parent()).each(function(i){
		if($(this).is(source)){
			index = i;
			$(this).addClass($(this).attr('otherClass'));
		}else{
			$(this).removeClass($(this).attr('otherClass'));
		}
	});
	$('ul:gt(0)',$(source).parent().parent()).each(function(i){
		if(i == index)$(this).show();
		if(i != index)$(this).hide();
	});
}

function sortMtv(source){
	var sort = $(source).attr('sort');
	var text = $(source).text().substring(2);
	$(source).attr('sort',sort=='asc'?'desc':'asc');
	$(source).empty().append("<span class='mvArrow'>"+(sort=='asc'?'↓':'↑')+" </span>"+text);
	$(source).parent().parent().attr('pageNo',1);
	findMtv($(source).parent().parent());
}
function pageMtv(source){
	var urlDiv     = $(source).parent().parent().parent();
	var pageNo  = urlDiv.attr('pageNo')?Number(urlDiv.attr('pageNo')):1;
	var actType   = $(source).text().indexOf('上一页');
	urlDiv.attr('pageNo',actType>0?pageNo-1:pageNo+1);
	findMtv(urlDiv);
}
function findMtv(urlDiv){
	var isRunning = $(urlDiv).attr('isRunning')?$(urlDiv).attr('isRunning'):'false';
	if(isRunning == 'true')return;
	var optDiv    = $('>div:eq(0)',urlDiv);
	var wrapDiv   = $('>div:eq(1)',urlDiv);
	var urlDivH   = wrapDiv.height();
	var urlDivImg = "<div style='text-align:center;margin-top:100px;'><img src='./img/loading.gif' /></div>";
	var pageType  = $('input:checked',optDiv).val();
	var timeSort  = $('a:eq(0)',optDiv).attr('sort');
	var scoreSort = $('a:eq(1)',optDiv).attr('sort');
	var pageNo    = $(urlDiv).attr('pageNo');
	$.ajax({
        type:'GET',
        url:$(urlDiv).attr('url'),
        data:{'pageType':pageType,'timeSort':timeSort,'scoreSort':scoreSort,'pageNo':pageNo},
		dataType:'html',
        beforeSend:function(){
			$(urlDiv).attr('isRunning','true');
			wrapDiv.css('height',urlDivH);
        	wrapDiv.empty().append(urlDivImg);
        }
    }).done(function(data){
		$(urlDiv).attr('isRunning','false');
    	wrapDiv.empty().append(data).removeAttr('style');
    }).fail(function(){
		$(urlDiv).attr('isRunning','false');
    	wrapDiv.empty().append("<p style='text-align:center;color:red;'>数据加载失败，请稍微尝试或重新刷新</p>").removeAttr('style');
    });
}


/* 发布留言 */
function statWords(){
	if(!$('.submitText')[0])return;
	var source = $('.submitText');
	var tipStat= $('.tipStat');
	var max    = source.attr('maxlength');
	var text   = source.val().substring(0,max);
	source.val(text);
	tipStat.empty().append(text.length+'/'+max);
}
function showEmotion(){
	closeSubSure();
	if($('.emotion').children().length>0){
		$('.emotion').show();
		return;
	}else{
		var children ="";
		for(var i=0;i<=134;i++){
			children += "<a href='javascript:selEmotion("+i+")'><img src='./img/qqemotion/"+i+".gif'></a>";
		}
		children +=   "<span class='closeEmotion' onclick='closeEmotion()' href='javascript:void(0)'>X</span>";
		children += "";
		$('.emotion').append(children);
		
	}
}
function selEmotion(i){
	var textarea  = $('.submitText');
	var fontCount = $('.tipStat');
	var comment   = textarea[0];
	var txt = "{emotion:"+i+"}";
	var val = textarea.val();
	var maxSize = Number(textarea.attr('maxlength'));
	if((val+txt).length>maxSize){
		closeEmotion();
		return;
	}
	//插入文本 （IE）
    if (document.selection) {
    	comment.focus();
        var sel = document.selection.createRange();
        sel.text = txt;
        sel.select();
        fontCount.text(textarea.val().length+"/"+maxSize);
        closeEmotion();
        return;
    }
    //插入文本 （MOZILLA/NETSCAPE）  
    if (comment.selectionStart || comment.selectionStart == '0') {
        var startPos = comment.selectionStart;
        var endPos   = comment.selectionEnd;
        // save scrollTop before insert  
        var restoreTop = comment.scrollTop;
        comment.value = comment.value.substring(0, startPos) + txt + comment.value.substring(endPos, comment.value.length);
        if (restoreTop > 0) {
            // restore previous scrollTop  
        	comment.scrollTop = restoreTop;
        }
        comment.focus();
        comment.selectionStart = startPos + txt.length;
        comment.selectionEnd = startPos + txt.length;
        fontCount.text(textarea.val().length+"/"+maxSize);
        closeEmotion();
        return;
    }
    comment.value += txt;
    comment.focus();
    closeEmotion();
}
function closeEmotion(){
	$('.emotion').hide();
}
function showSubSure(){
	closeEmotion();
	$('.subSure').show();
	$('.sureInput')[0].focus();
}
function closeSubSure(){
	$('.subSure').hide();
}
function submitCmmt(){
	var sureInput   = $('.sureInput');
	var submitText = $('.submitText');
	var isReply       = $('.cmmtSubmit').attr('isReply')=='true';
	var movieid      = !isReply?submitText.attr('movieid').replace(/\s/g,''):undefined;
	var commentid = isReply?submitText.attr('commentid').replace(/\s/g,''):undefined;
	var content       = submitText.val().replace(/\s/g,'');
	var captcha       = sureInput.val().replace(/\s/g,'');
	if(captcha.length!=4){
		alert('请输入四位的验证码');
		sureInput[0].focus();
		return;
	}
	$.ajax({
        type:'POST',
        url:isReply?'reply.json':'comment.json',
        data:{'commentid':commentid,'movieid':movieid,'captcha':captcha,'content':content},
		dataType:'html',
        beforeSend:function(){
			$('.subSure > .tipBtn').hide();
			$('.loadCmmt').show();
        }
    }).done(function(data){
		$('.subSure > .tipBtn').show();
		$('.loadCmmt').hide();
		closeSubSure();
		var dataHtml = '<div>'+data+'</div>';
		//failure
		if($('.error',dataHtml).length>0){
			$('.subInfo').text($('.error',dataHtml).text());
			$('.subInfo').slideDown();
			setTimeout(function(){$('.subInfo').fadeOut();},1000);			
		}
		//留言成功
		if($('.cmmtDiv',dataHtml).length>0){
			$('.moreComment').before(data);
			location.hash = $('.cmmtDiv',dataHtml).attr('id');
		}
		//回复成功
		if($('.rpyli',dataHtml).length>0){
			if($('.reply',$.cmmtDom).length<=0){
				$('.clear',$.cmmtDom).before(data);
			}else{
				$('.rpyli:eq(0)',$.cmmtDom).before($('.rpyli',dataHtml));
			}
		}
		submitText.val('');
		sureInput.val('');
		$('.sureImg').attr('src',$('.sureImg').attr('src')+'&1=1');
		statWords();
		closeReply();
    }).fail(function(){
		$('.subSure > .tipBtn').show();
		$('.loadCmmt').hide();
		closeSubSure();
		$('.subInfo').text('网络发生错误，请重新刷新');
		$('.subInfo').slideDown();
		setTimeout(function(){$('.subInfo').fadeOut();},1000);
    });
}
function replyShow(source){
	$(source).next().slideDown();
}
function replyHide(source,event){
	var toElement   = event.relatedTarget||event.toElement;
	if($(toElement).hasClass('replyTxt')||$(toElement).hasClass('cmmtText'))return;
	if($(source).hasClass('cmmtText'))$(source).next().slideUp();
	if($(source).hasClass('replyTxt'))$(source).slideUp();
}
function moreShow(source){
	$(source).next().slideDown();
}
function moreHide(source,event){
	var toElement   = event.relatedTarget||event.toElement;
	if($(toElement).hasClass('replyCont'))return;
	if($(toElement).hasClass('rpyli'))return;
	if($(toElement).hasClass('replyImg'))return;
	if($(toElement).hasClass('moreTxt'))return;
	if($(source).hasClass('replyCont'))$(source).next().slideUp();
	if($(source).hasClass('moreTxt'))$(source).slideUp();
}
function reply(source){
	$('.centerCmmt').append($('.cmmtSubmit'));
	var winWidth   = $(window).width();
	var winHeight  = $(window).height();
	var cmmtWidth  = 514;
	var cmmtHeight = 270;
	var scrollLeft = navigator.userAgent.indexOf("MSIE 6")>0?$(document.body).scrollLeft():$(window).scrollLeft();
	var scrollTop  = navigator.userAgent.indexOf("MSIE 6")>0?$(document.body).scrollTop():$(window).scrollTop();
	var rightLeft  = (winWidth - cmmtWidth)/2 + scrollLeft;
	var rightTop   = (winHeight - cmmtHeight)/2 + scrollTop;
	$('.centerCmmt').css('left',rightLeft+'px');
	$('.centerCmmt').css('top',rightTop+'px');
	$('.centerCmmt').show();
	$('.layer').width($(document).width());
	$('.layer').height($(document).height());
	$('.layer').show();
	$('.submitText').attr('commentid',$(source).attr('commentid'));
	$('.submitText')[0].focus();
	$('.cmmtSubmit').attr('isReply','true');
	$(document.body).css('overflow-y','hidden');
	$.cmmtDom = $(source).parent().parent();
}
function getMoreReply(source){
	var isRunning   = $(source).attr('isRunning');
	var commentid = $(source).attr('commentid');
	var pageNo      = $(source).attr('pageNo')?$(source).attr('pageNo'):2;
	var loadImg      = $('.moreReplyLoading',source);
	if( isRunning == 'true')return;
	$.ajax({
        type:'GET',
        url:'moreReply.json',
        data:{'commentid':commentid,'pageNo':pageNo},
		dataType:'html',
        beforeSend:function(){
			$(source).attr('isRunning','true');
			$(source).attr('text',$(source).text());
			$(source).empty().append(loadImg);
			loadImg.show();
        }
    }).done(function(data){
    	var lis = $('.replyCont > li','<div>'+data+'</div>');
		$('.replyCont',$(source).parent()).append(lis);
		$(source).attr('isRunning','false');
		$(source).attr('pageNo',Number(pageNo)+1);
		$(source).empty().text($(source).attr('text'));
		if(lis.length<5)$(source).remove();
    }).fail(function(){
		$(source).empty().text('加载失败').css('color','red');
    });
}
function getMoreCmmt(source){
	var isRunning = $(source).attr('isRunning');
	var movieid    = $(source).attr('movieid');
	var pageNo    = $(source).attr('pageNo')?$(source).attr('pageNo'):2;
	var pageSize   = $(source).attr('pageSize')?$(source).attr('pageSize'):10;
	var loadImg    = $('.moreCmmtLoading');
	if( isRunning == 'true')return;
	$.ajax({
        type:'GET',
        url:'moreCmmt.json',
        data:{'movieid':movieid,'pageNo':pageNo},
		dataType:'html',
        beforeSend:function(){
			$(source).attr('isRunning','true');
			$(source).attr('text',$(source).text());
			$(source).empty().append(loadImg);
			loadImg.show();
        }
    }).done(function(data){
		$(source).before(data);
		$(source).attr('isRunning','false');
		$(source).attr('pageNo',Number(pageNo)+1);
		$(source).empty().text($(source).attr('text'));
		if($(">div","<div>"+data+"</div>").length<pageSize)$(source).hide();
    }).fail(function(){
		$(source).empty().text('加载失败').css('color','red');
    });
}
function closeReply(){
	$('#cmmts').append($('.cmmtSubmit'));
	$('.layer').hide();
	$('.centerCmmt').hide();
	$('.cmmtSubmit').attr('isReply','false');
	$(document.body).css('overflow-y','auto');
}


/* 搜索页 */
function deepSearch(type){
	$('.searchRight').attr('searchType',type);
	$('.searchRight').attr('pageNo',1);
	$('.searchLeftA').each(function(){
		if($(this).attr('href').indexOf(type)>=0){
			$(this).addClass('searchLeftAClick');
		}else{
			$(this).removeClass('searchLeftAClick');
		}
	});
	goDeepSearch();
}
function deepPage(source){
	var sRight = $('.searchRight');
	var pageNo = sRight.attr('pageNo')?sRight.attr('pageNo'):1;
	if($(source).text().indexOf('上一页')>=0)sRight.attr('pageNo',Number(pageNo)-1);
	if($(source).text().indexOf('下一页')>=0)sRight.attr('pageNo',Number(pageNo)+1);
	goDeepSearch();
}
function goDeepSearch(){
	var sRight = $('.searchRight');
	var search = sRight.attr('search');
	var type   = sRight.attr('searchType');
	var pageNo = sRight.attr('pageNo');
	$.ajax({
        type:'GET',
        url:'search.json',
        data:{'search':search,'type':type,'pageNo':pageNo},
		dataType:'html',
        beforeSend:function(){
			sRight.empty().append("<img class='searchLoading' src='./img/loading.gif' />");
        }
    }).done(function(data){
		sRight.empty().append(data);
    }).fail(function(){
		sRight.empty().text('网络发生错误，加载失败，请稍后尝试').css('text-align','center').css('color','red');
    });
}


/* 后台页 */
function initAdminPic(){
	var color  = ['#f41c54','#53b2f0','#f3552e','#451cf4','#1cf4e2','#d194d7','#1cf472','#bff41c','#f4681c','#f41c1c'];
	var option = {
		xaxis: {show:false},
		yaxis: {show:false},
		grid: {show:false,margin:{left:70,right:0,top:0,bottom:0}},
		legend: {
			show:true,
			backgroundOpacity:0,
			labelFormatter: function(label, series) {
				if(!series.id)return label+'<span class="adminData">('+series.data[0][1]+')</span>';
				return '<a href="content.do?id='+series.id+'" target="_blank">'+label+'<span class="adminData">('+series.data[0][1]+')</span></a>';
			}
		}
	};
	$(".adminPic").plot([],option).each(function(){
		var plotDiv  = $(this);
		var url      = plotDiv.attr('url');
		var interval = plotDiv.attr('interval')||500;
		function drawPic(){
			$.ajax({
				type:'GET',
				url:url,
				dataType:'json'
			}).done(function(data){
				var d = [];
				for(var i=0;i<data.result.length;i++){
					var item = {
						id:data.result[i].id,
						label:data.result[i].name,
						color:color[i]||'blue',
						data: [[i,data.result[i].value]],
						bars: {show:true,fill:1.0}
					};
					d.push(item);
				}
				plotDiv.plot(d,option);
			});
		}
		drawPic();
		setInterval(drawPic,interval);
	});
}	
function initAdminTable(){
	$('.adminTable>tbody>tr:odd').addClass('evenTr');
}
function checkType(source){
	var checkbox    = $(source);
	var checkLabel  = $('.checkLabel',checkbox.parent());
	var checkLabelA = $('a',checkbox.parent());
	var checkVal    = checkbox.val();
	var checkText   = source.nextSibling.data.replace(/\s/g,'');
	var nowText     = checkLabel.text().replace(/\s/g,'');
	var nowVal      = checkLabel.attr('tmpVal')||'';
	if(checkbox.is(':checked')){
		nowText = nowText.length>0?nowText+'+'+checkText:checkText;
		nowVal  = nowVal.length>0?nowVal+','+checkVal:checkVal;
		checkLabel.css('display','inline-block').text(nowText).attr('tmpVal',nowVal).val(nowVal.split(',',10000));
		checkLabelA.show();
	}else{
		var pos1     = nowText.indexOf(checkText);
		var pos2     = nowVal.indexOf(checkVal);
		var prevStr1 = nowText.charAt(pos1-1);
		var prevStr2 = nowVal.charAt(pos2-1);
		var nextStr1 = nowText.charAt(pos1+checkText.length);
		var nextStr2 = nowVal.charAt(pos2+checkVal.length);
		var rpStr1 = checkText;
		var rpStr2 = checkVal;
		if(pos1 == 0 && '+' == nextStr1)rpStr1 = checkText+'+';
		if(pos2 == 0 && ',' == nextStr2)rpStr2 = checkVal+',';
		if(pos1 != 0 && '+' == prevStr1)rpStr1 = '+'+checkText;
		if(pos2 != 0 && ',' == prevStr2)rpStr2 = ','+checkVal;
		nowText = nowText.replace(rpStr1,'');
		nowVal = nowVal.replace(rpStr2,'');
		checkLabel.text(nowText).attr('tmpVal',nowVal).val(nowVal.split(',',10000));
		if(nowText.length<1)checkLabel.hide();
		if(nowText.length<1)checkLabelA.hide();
	}
}
function delQuery(source){
	var checkLabel  = $(source).prev();
	var checkLabelA = $(source);
	checkLabel.text('').attr('tmpVal','').val([]).hide();
	checkLabelA.hide();
}
function adminQuery(source,action){
	var tableQuery = action?$('.adminTableQuery',$(source).parent().parent().parent().parent().parent()):$(source).parent().parent();
	var table    = tableQuery.next();
	var nameDoms = $('[name]',tableQuery);
	var pageNo   = tableQuery.attr('pageNo')||1;
	var nextPageNo = 1;
	if('up'==action)nextPageNo=Number(pageNo)-1;
	if('down'==action)nextPageNo=Number(pageNo)+1;
	var params   = {};
	if(tableQuery.attr('isRunning')=='true')return;
	nameDoms.each(function(){
		var nameVal = $(this).attr('name');
		var val     = $(this).val();
		if(val == undefined)return true;
		if(val.length<1)return true;
		if(val.replace && "" == val.replace(/\s/g,''))return true;
		if(val.replace)val = val.replace(/\s/g,'');
		$(this).val(val);
		params[nameVal] = val;
	});
	
	params.pageNo = nextPageNo;
	$.ajax({
        type:'GET',
        url:tableQuery.attr('url'),
        data:params,
        traditional:true,
		dataType:'html',
        beforeSend:function(){
			tableQuery.attr('isRunning','true');
			$('tbody',table).empty().append("<tr><td colspan='50' style='text-align:center'><img src='./img/loading.gif' /></td></tr>");
        }
    }).done(function(data){
		tableQuery.attr('isRunning','false');
		tableQuery.attr('pageNo',nextPageNo);
		$('tbody',table).empty().append(data);
		initAdminTable();
    }).fail(function(){
		$('tbody',table).empty().append("<tr><td colspan='50' style='text-align:center;color:red'>网络发生错误，加载失败，请稍后尝试</td></tr>");
    });
}
function upMovieSuccess(file,serverdata,resp){
	$('#attachUrl').val($.parseJSON(serverdata).faceImgUrl);
	$('#attachName').val(file.name);
	$('#attachName').attr('old_url',$.parseJSON(serverdata).faceImgUrl);
	$('#attachName').attr('old_name',$.parseJSON(serverdata).upFileName);
}
function upMovieError(file,errorCode,msg){
	alert("上传失败");
}
function addAttach(source){
	var url            = $('#attachUrl').val().replace(/\s/g,'');
	var name       = $('#attachName').val().replace(/\s/g,'');
	var old_name = "";
	if(url==$('#attachName').attr('old_url'))old_name = $('#attachName').attr('old_name');
	if(url.length<1){
		$('#attachUrl').val('');
		$('#attachUrl')[0].focus();
		return;
	}
	if(name.length<1){
		$('#attachName').val('');
		$('#attachName')[0].focus();
		return;
	}
	var li   = "<li><a name='attachs' href='"+url+"' value='"+name+"_"+url+"_"+old_name+"' class='attachItem'>"+name+"</a><a href='javascript:void(0)' onclick='$(this).parent().remove()' style='color:#f41c54'>删除</a></li>";
	$('.attachContainer').append(li);
}
function upImgSuccess(file,serverdata,resp,swfObj){
	var url= $.parseJSON(serverdata).faceImgUrl;
	var li = $('#'+swfObj.movieName).parent().parent().parent();
	$('>img',li).attr('src',url);
}
function mvPreviewImg(file,serverdata,resp,swfObj){
	var url= $.parseJSON(serverdata).faceImgUrl;
	var ul = $('#'+swfObj.movieName).parent().parent().parent().parent();
	
	var li = "<li class='baseImgItem'><img name='pictures' src='"+url+"'/><input type='button' value='删除' hoverclass='adminBtnHover' class='adminBtn' onclick='$(this).parent().remove()'/></li>";
	$('>li:last',ul).before(li);
}
function addMovie(source){
	var loadImg= $('img',$(source).parent());
	var params = {};
	$('[name]',$(source).parent().parent()).each(function(){
		if($(this).is('param'))return true;
		var name       = $(this).attr('name');
		var val        = params[name]||[];
		var isA        = $(this).is('a');
		var isImg      = $(this).is('img');
		var isRadio    = $(this).is(':radio');
		var isCheckbox = $(this).is(':checkbox');
		var isOther    = !isA && !isImg && !isRadio && !isCheckbox;
		if(isA) val.push($(this).attr('value'));
		if(isImg) val.push($(this).attr('src'));
		if(isRadio && $(this).is(':checked')) val.push($(this).val());
		if(isCheckbox && $(this).is(':checked')) val.push($(this).val());
		if(isOther) val.push($(this).val());
		params[name] = val;
	});
	$.ajax({
        type:'POST',
        url:'addMovie.json',
        data:params,
		dataType:'html',
		traditional:true,
        beforeSend:function(){
			loadImg.show();
			$(source).hide();
		}
    }).done(function(data){
		loadImg.hide();
		$(source).show();
		location.reload();
    }).fail(function(){
		alert('网络发生错误,添加失败');
    });
}
function delMovie(source,movieid){
	if($(source).attr('isRunning')=='true')return;
	$(source).attr('isRunning','true');
	$.ajax({
        type:'POST',
        url:'delMovie.json?movieid='+movieid,
		dataType:'html'
    }).done(function(data){
		$(source).attr('isRunning','false');
		$(source).parent().parent().remove();
    }).fail(function(){alert('删除失败');});
}
function queryModuleMvs(source,direction){
	var loadImgHtml = "<tr><td colspan='10'><img src='./img/loading.gif' /></td></tr>";
	var table       = $(source).parent().parent().parent().parent();
	var tbody       = $('tbody',table);
	var pageNo      = table.attr('pageNo')||1;
	var goPageNo    = direction=='up'?Number(pageNo)-1:Number(pageNo)+1;
	$.ajax({
        type:'GET',
        url:table.attr('url'),
		data:{'pageNo':goPageNo},
		dataType:'html',
		traditional:true,
        beforeSend:function(){tbody.empty().append(loadImgHtml);}
    }).done(function(data){
		table.attr('pageNo',goPageNo);
		tbody.empty().append(data);
		initAdminTable();
    }).fail(function(){
		tbody.empty().append('<tr><td colspan="10" style="color:red">网络发生错误,添加失败</td></tr>');
    });
}
function mvModuleMv(source,direction){
	var isUp   = direction == 'up'?true:false;
	var tr     = $(source).parent().parent();
	var prevTr = tr.prev().is('tr')?tr.prev():undefined;
	var nextTr = tr.next().is('tr')&&tr.next().next().is('tr')?tr.next():undefined;
	if(isUp  && !prevTr)return;
	if(!isUp && !nextTr)return;
	var fromid = tr.children('td:eq(0)').text().replace(/\s/g,'');
	var toid     = isUp?prevTr.children('td:eq(0)').text().replace(/\s/g,''):nextTr.children('td:eq(0)').text().replace(/\s/g,'');
	$.ajax({
        type:'GET',
        url:'mvModule.json',
        data:{'fromid':fromid,'toid':toid},
		dataType:'html'
    }).done(function(data){
		if(isUp  && prevTr)tr.after(prevTr);
		if(!isUp && nextTr)tr.before(nextTr);
    }).fail(function(){alert('移动失败');});
}
function delModuleMv(source,modmvid){
	if($(source).attr('isRunning')=='true')return;
	$(source).attr('isRunning','true');
	$.ajax({
        type:'GET',
        url:'delModule.json?modmvid='+modmvid,
		dataType:'html'
    }).done(function(data){
		$(source).attr('isRunning','false');
		$(source).parent().parent().remove();
    }).fail(function(){alert('删除失败');});
}
function delForbidUser(source,userid){
	if($(source).attr('isRunning')=='true')return;
	$(source).attr('isRunning','true');
	$.ajax({
        type:'GET',
        url:'delForbidUser.json?userid='+userid,
		dataType:'html'
    }).done(function(data){
		$(source).attr('isRunning','false');
		$(source).parent().parent().remove();
    }).fail(function(){alert('删除失败');});
}
function delNormalUser(source,userid){
	if($(source).attr('isRunning')=='true')return;
	$(source).attr('isRunning','true');
	$.ajax({
        type:'GET',
        url:'delNormalUser.json?userid='+userid,
		dataType:'html'
    }).done(function(data){
		$(source).attr('isRunning','false');
		$(source).parent().parent().remove();
    }).fail(function(){alert('删除失败');});
}
function permitModify(source,userid){
	if($(source).attr('isRunning')=='true')return;
	$(source).attr('isRunning','true');
	$.ajax({
        type:'GET',
        url:'permitModify.json?userid='+userid,
		dataType:'html'
    }).done(function(data){
		$(source).attr('isRunning','false');
		$('td:eq(4)',$(source).parent().parent()).empty();
		$('td:eq(5)',$(source).parent().parent()).empty();
    }).fail(function(){alert('删除失败');});
}
function addNormalForbit(source,userid){
	if($(source).attr('isRunning')=='true')return;
	$(source).attr('isRunning','true');
	$.ajax({
        type:'GET',
        url:'addNormalForbit.json?userid='+userid,
		dataType:'html'
    }).done(function(data){
		$(source).attr('isRunning','false');
		$(source).parent().parent().remove();
    }).fail(function(){alert('删除失败');});
}
function addSystemForbit(source){
	var createip = $('#forbitIp').val().replace(/\s/g,'');
	if(createip=="")return;
	if($(source).attr('isRunning')=='true')return;
	$(source).attr('isRunning','true');
	$.ajax({
        type:'GET',
        url:'addSystemForbit.json?createip='+createip,
		dataType:'html'
    }).done(function(data){
		$(source).attr('isRunning','false');
		location.reload();
    }).fail(function(){alert('添加失败');});
}












