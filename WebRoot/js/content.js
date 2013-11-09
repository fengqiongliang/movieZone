$(document).ready(function(){
	init();
});

function init(){
//初始化自定义样式
$('[clickClass]').bind('click',function(){
	var me = $(this);
	var clickClass = me.attr('clickClass');
	me.addClass(clickClass);
	setTimeout(function(){me.removeClass(clickClass);},100);
});

$('[hoverClass]').bind('mouseover',function(){
	$(this).addClass($(this).attr('hoverClass'))
});
$('[hoverClass]').bind('mouseout',function(){
	$(this).removeClass($(this).attr('hoverClass'))
});

$('[focusClass]').bind('focus',function(){
	$(this).addClass($(this).attr('focusClass'));
});
 
$('[blurClass]').bind('blur',function(){
	$(this).addClass($(this).attr('blurClass'));
});

$('.searchText').bind('focus',function(){
	$(this).parent().addClass('searchHover');
});
$('.searchText').bind('blur',function(){
	$(this).parent().removeClass('searchHover');
});
$('.searchText').bind('keyup',function(event){
	if(event.which == 13)goSearch();  //回车
});

$('.checkboxes input:checkbox').change(function(){
	var me   = $(this);
	var span = me.next();
	if(me.is(':checked')){
		span.css('color','');
	}else{
		span.css('color','gray');
	}
});

$.getMovieUrl     = "http://www.baidu.com";
$.getTvUrl        = "http://www.baidu.com";
$.getMusicUrl     = "http://www.baidu.com";
$.getExtraUrl     = "http://www.baidu.com";
$.searchUrl       = "http://www.baidu.com";


$.searchNetError  = $('#search > div:eq(4)');

$.search          = $('#search');
$.searchInput     = $('.searchText');
$.searchTitle     = $('.searchAct');
$.searchGif       = $('#searchGif');
$.searchMore      = $('#searchMore');
$.searchDivs      = new Array();
$.searchInterval  = 1*60*60*1000; //间隔1小时(ms为单位)

//初始化监听查询
setInterval(function(){
	var hash      = location.hash.substring(1,location.hash.length).replace(/\s/g,"");
	var lastHash  = $.lastHash;
	if(hash == lastHash) return;
	$.lastHash    = hash;
	location.hash = hash;
	var pos = -1;
	if((pos = hash.indexOf('search', 0)>-1)){
		var searchText = hash.substring(pos+6,hash.length);
		if(searchText.length>0){
			$.searchInput.val(searchText);
			showSearch(searchText);
		}
	}
},100);
}

function showSearch(content){
	$('#main').slideUp(400,function(){$('#search').fadeIn(1000)});
	search(content);
}
function goSearch(){
	var searchText = $.searchInput.val();
	if(searchText.length<1)return;
	location.hash  = "search_"+searchText;
}

function search(content){
	//设置标题
	clearInterval($.lastSearchIntervalId);
	var count = 0;
	$.searchTitle.text('正在搜索 ( '+content+' ) ');
	$.lastSearchIntervalId = setInterval(function(){
		++count;
		if(count % 4 == 0)$.searchTitle.text('正在搜索 ( '+content+' ) ');
		if(count % 4 == 1)$.searchTitle.text('正在搜索 ( '+content+' ) 。');
		if(count % 4 == 2)$.searchTitle.text('正在搜索 ( '+content+' ) 。 。');
		if(count % 4 == 3)$.searchTitle.text('正在搜索 ( '+content+' ) 。 。 。');
	},1000);
	
	var searchId     = 'search_'+hex_md5(content);
	var searchResult = $('#'+searchId);
	var now          = new Date();
	var expireDate   = new Date();
	//隐藏操作div
	$.searchNetError.css('display','none');
	$.searchGif.css('display','none');
	$.searchMore.css('display','none');
	for(var i = 0; i < $.searchDivs.length; i++){
		if($.searchDivs[i].attr('id') != searchId){
			$.searchDivs[i].css('display','none');
		}
	}
	
	//查找是否已经存在，并且搜索没有过期
	if(searchResult.length>0){
		expireDate.setMilliseconds(searchResult.attr('expireDate'));
		if(now.getMilliseconds()<=expireDate.getMilliseconds()){
			searchResult.fadeIn();
			return;
		}
		searchResult.remove();
	}
	
	$.ajax({
        async:true,
        type:'GET',
        url:$.searchUrl,
        data:{content:content,page:1},
        cache:true,
        beforeSend:function(){
        	$.searchGif.css('display','block');
        },
        complete:function(){
        	$.searchGif.css('display','none');
        }
    }).done(function(data){
    	var html = $("<div id='"+searchId+"' style='display:none' expireDate='"+(now.getMilliseconds()+$.searchInterval)+"'>"+data+"</div>");
    	$.search.append(html);
    	html.fadeIn();
        if(html.children().length >= 20 ){
        	$.searchMore.css('display','block');
        }
        $.searchDivs.push(html);
        searchDone();
    }).fail(function(){
    	$.searchNetError.css('display','block');
    	$.searchMore.css('display','none');
    });
}

function searchCancel(){
	clearInterval($.lastSearchIntervalId);
	$.lastHash    = "";
	location.hash = "";
	$('#search').fadeOut(400,function(){
		$('#main').slideDown(1000);
	});
}

function searchDone(){
	//设置标题
	clearInterval($.lastSearchIntervalId);
	$.searchTitle.text('搜索结果')
}

function showLastDiv(source){
	var last    = $(source).children().last();
	if(last.css('display') == 'none')last.show();
}
function hideLastDiv(source){
	var last    = $(source).children().last();
	if(last.css('display')!= 'none')last.hide();
}
function feedInput(source,event){
	var me = $(source);
	var fontCount = me.next();
	me.val(me.val().substring(0,250));
	fontCount.text(me.val().length+"/"+250);
}
function showReply(){
	var width  = $(document).width();
	var height = $(document.body).height();
	$('#coverDiv').width(width);
	$('#coverDiv').height(height);
	$('#coverDiv').show();
	$('#coverContent').append($('#inputCont'));
	var left =  ($(window).width() - $('#coverContent').width())/2;
	var top  =  ($(window).height()- $('#coverContent').height())/2;
	$('#coverContent').css('left',left+'px');
	$('#coverContent').css('top',top+'px');
	$('#coverContent').css('_top','expression(documentElement.scrollTop +'+191+ ')'); //ie6支持兼容
	$('#coverContent').show();
}
function hideReply(){
	$('#coverDiv').hide();
}
function showMore(source){
	alert("获得更多");
}
function showEmotion(source,e){
	var btn = $(source);
	var clientX = btn.offset().left;
	var clientY = btn.offset().top;
	var emotion = $('#emotion');
	if(emotion.length<1){
		var html="<div id='emotion' class='emotion' style='display:none'>";
		html   +=   "<span href='javascript:void(0)' onclick='closeEmotion()' class='closeEmotion'>X</span>";
		for(var i=0;i<=134;i++){
			if(i<=15)        html += "<a href='javascript:void(0)' onclick='selEmotion(this,"+i+")' style='border-top:1px solid #D3E4F0;border-right:1px solid #D3E4F0;border-bottom:1px solid #D3E4F0;'><img src='/moviezone/img/qqemotion/"+i+".gif'></a>";
			if(i%15==0&&i>0) html += "<a href='javascript:void(0)' onclick='selEmotion(this,"+i+")' style='border-left:1px solid #D3E4F0;border-right:1px solid #D3E4F0;border-bottom:1px solid #D3E4F0;'><img src='/moviezone/img/qqemotion/"+i+".gif'></a>";
			if(i%15!=0&&i>15)html += "<a href='javascript:void(0)' onclick='selEmotion(this,"+i+")' style='border-right:1px solid #D3E4F0;border-bottom:1px solid #D3E4F0;'><img src='/moviezone/img/qqemotion/"+i+".gif'></a>";
		}
		html += "</div>";	
		emotion = $(html);
		$(document.body).append(emotion);
	}
	emotion.css('left',clientX);
	emotion.css('top',clientY);
	emotion.fadeIn();
}
function selEmotion(source,i){
	var commentInput = $('#commentInput');
	var fontCount = commentInput.next();
	var comment = commentInput[0];
	var txt = "{emotion:"+i+"}";
	var val = commentInput.val();
	if((val+txt).length>250){
		closeEmotion();
		return;
	}
	//插入文本 （IE）
    if (document.selection) {
    	comment.focus();
        var sel = document.selection.createRange();
        sel.text = txt;
        sel.select();
        fontCount.text(commentInput.val().length+"/"+250);
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
        fontCount.text(commentInput.val().length+"/"+250);
        closeEmotion();
        return;
    }
    comment.value += txt;
    comment.focus();
    closeEmotion();
}

function closeEmotion(){
	$('#emotion').fadeOut();
	$('#emotion').hide();
}