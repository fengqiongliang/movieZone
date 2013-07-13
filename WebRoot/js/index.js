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

$.movieCheckBoxes = $('#movie > div:eq(1) > div:eq(0) > div:eq(1) > input:checkbox');
$.tvCheckBoxes    = $('#tv > div:eq(1) > div:eq(0) > div:eq(1) > input:checkbox');
$.musicCheckBoxes = $('#music > div:eq(1) > div:eq(0) > div:eq(1) > input:checkbox');
$.extraCheckBoxes = $('#extra > div:eq(1) > div:eq(0) > div:eq(1) > input:checkbox');

$.movieArrow      = $('#movie > div:eq(0) > div:eq(0)');
$.tvArrow         = $('#tv > div:eq(0) > div:eq(0)');
$.musicArrow      = $('#music > div:eq(0) > div:eq(0)')
$.extraArrow      = $('#extra > div:eq(0) > div:eq(0)')

$.movieContent    = $('#movie > div:eq(1)');
$.tvContent       = $('#tv > div:eq(1)');
$.musicContent    = $('#music > div:eq(1)')
$.extraContent    = $('#extra > div:eq(1)')

$.movieLength     = $.movieContent.children().length;
$.tvLength        = $.tvContent.children().length;
$.musicLength     = $.musicContent.children().length;
$.extraLength     = $.extraContent.children().length;

$.movieSearch     = $('#movie > div:eq(2)');
$.tvSearch        = $('#tv > div:eq(2)');
$.musicSearch     = $('#music > div:eq(2)');
$.extraSearch     = $('#extra > div:eq(2)');

$.movieNetError   = $('#movie > div:eq(3)');
$.tvNetError      = $('#tv > div:eq(3)');
$.musicNetError   = $('#music > div:eq(3)');
$.extraNetError   = $('#extra > div:eq(3)');
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
	if(hash.length < 2){
		setTimeout(setDefaultShow,200);
		return;
	}
	if(hash == lastHash) return;
	$.lastHash    = hash;
	location.hash = hash;
	var newHash   = processHash(hash.split(',',1000),hash);
	newHash       = sortHash(newHash);
	$.lastHash    = newHash;
	location.hash = newHash;
	
},100);
}

function processHash(content,hash){
	var newHash = "";
	for(var i = 0 ; i < content.length ; i++){
		var pos  = -1;
		var cont = content[i];
		if((pos = cont.indexOf('movie', 0))>-1){
			var checkItems = cont.substring(pos+6,cont.length);
			var checkBoxes = $.movieCheckBoxes;
			if(checkItems.length>0){
				var result = check(checkBoxes,checkItems);
				if(result.length>0){
					showDiv($.movieArrow,$.movieContent,$.movieSearch,$.movieNetError,"movie_"+result,result,$.getMovieUrl,$.movieLength);
					newHash = newHash+"movie_"+result+",";
				}
			}
		}
		if((pos = cont.indexOf('tv', 0))>-1){
			var checkItems = cont.substring(pos+3,cont.length);
			var checkBoxes = $.tvCheckBoxes;
			if(checkItems.length>0){
				var result = check(checkBoxes,checkItems);
				if(result.length>0){
					showDiv($.tvArrow,$.tvContent,$.tvSearch,$.tvNetError,"tv_"+result,result,$.getTvUrl,$.tvLength);
					newHash = newHash+"tv_"+result+",";
				}
			}
		}
		if((pos = cont.indexOf('music', 0))>-1){
			var checkItems = cont.substring(pos+6,cont.length);
			var checkBoxes = $.musicCheckBoxes;
			if(checkItems.length>0){
				var result = check(checkBoxes,checkItems);
				if(result.length>0){
					showDiv($.musicArrow,$.musicContent,$.musicSearch,$.musicNetError,"music_"+result,result,$.getMusicUrl,$.musicLength);
					newHash = newHash+"music_"+result+",";
				}
			}
		}
		if((pos = cont.indexOf('extra', 0))>-1){
			var checkItems = cont.substring(pos+6,cont.length);
			var checkBoxes = $.extraCheckBoxes;
			if(checkItems.length>0){
				var result = check(checkBoxes,checkItems);
				if(result.length>0){
					showDiv($.extraArrow,$.extraContent,$.extraSearch,$.extraNetError,"extra_"+result,result,$.getExtraUrl,$.extraLength);
					newHash = newHash+"extra_"+result+",";
				}
			}
		}
		if((pos = cont.indexOf('search', 0)>-1)){
			var searchText = cont.substring(pos+6,cont.length);
			if(searchText.length>0){
				$.searchInput.val(searchText);
				showSearch(searchText);
				newHash = hash;
			}
		}
	}
	return newHash;
}
function setDefaultShow(){
	var hash      = "";
	var movieHash = getHash($.movieCheckBoxes,'movie')+'_1,';
	var tvHash    = getHash($.tvCheckBoxes,'tv')+'_1,';;
	var musicHash = getHash($.musicCheckBoxes,'music')+'_1,';;
	var extraHash = getHash($.extraCheckBoxes,'extra')+'_1,';;
	if(movieHash.length>3)hash = hash + movieHash;
	if(tvHash.length>3)   hash = hash + tvHash;
	if(musicHash.length>3)hash = hash + musicHash;
	if(extraHash.length>3)hash = hash + extraHash;
	if(hash.length>3)     location.hash = hash.substring(0,hash.length-1);
}
function getHash(checkBoxes,hash){
	var result = hash;
	var length = hash.length;
	checkBoxes.each(function(){
		var checkBox = $(this);
		var val      = checkBox.val();
		if(checkBox.is(':checked'))result = result+"_"+val;
	});
	return result.length>length?result:"";
}
function check(checkBoxes,checkItems){
	var result = "";
	var items  = checkItems.split('_',1000);
	var page   = 1;
	checkBoxes.each(function(){
		var checkBox = $(this);
		var isCheck  = false;
		for(var i=0;i<items.length;i++){
			var val = checkBox.val();
			if(val==items[i])isCheck = true;
			if(/^\d*$/.test(items[i]))page = items[i]; //如果为数字
		}
		if(isCheck){
			checkBox.attr('checked',true);
			checkBox.trigger('change');
			result = result+checkBox.val()+"_";
		}else{
			checkBox.attr('checked',false);
			checkBox.trigger('change');
		}
	});
	return result.length>1?result+page:result;
}
function sortHash(newHash){
	var result = ""
	var tmp    = new Array();
	var items  = newHash.split(',',1000);
	for(var i=0;i<items.length;i++){
		if(items[i].indexOf('movie',0)>-1) tmp[0]=items[i];
		if(items[i].indexOf('tv',0)>-1)    tmp[1]=items[i];
		if(items[i].indexOf('music',0)>-1) tmp[2]=items[i];
		if(items[i].indexOf('extra',0)>-1) tmp[3]=items[i];
		if(items[i].indexOf('search',0)>-1)tmp[4]=items[i];
	}
	for(var j=0;j<tmp.length;j++){
		if(tmp[j]!=undefined&&tmp[j].length>0)result = result + tmp[j]+",";
	}
	return result.length>1?result.substring(0,result.length-1):result;
}

function showDiv(arrow,content,doSearch,networkError,hash,params,url,contentLength){
	$('#search').css('display','none');
	$('#main').slideDown(1000);
	doSearch.css('display','none');
	networkError.css('display','none');
	arrow.removeClass('groupArrow2');
	arrow.addClass('groupArrow1');
	content.slideDown(1000);
	var lastShowChild = content.attr('lastShowChild');
	var lastDiv  = $('#'+lastShowChild);
	var nowDiv   = $('#'+hash);
	lastDiv.css('display','none');
	if(nowDiv.length > 0){
		nowDiv.fadeIn();
		content.attr('lastShowChild',hash);
		return ;
	}
	$.ajax({
        async:true,
        type:'POST',
        url:$.url,
        data:{type:params},
        cache:true,
        beforeSend:function(){
			doSearch.css('display','block');
        },
        complete:function(){
        	doSearch.css('display','none');
        }
    }).done(function(data){
    	var html = $("<div id='"+hash+"' style='display:none'>"+data+"/div>");
    	content.append(html);
    	html.fadeIn();
        content.attr('lastShowChild',hash);
        contentLength = contentLength + 1;
    }).fail(function(){
    	networkError.css('display','block');
		setTimeout(function(){networkError.fadeOut(400,function(){lastDiv.fadeIn();});},10000);
    });
	
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
function togMovie(){
	var isHide = $.movieContent.css('display')=="none"; 
	if(!isHide){
		$.movieArrow.removeClass('groupArrow1');
		$.movieArrow.addClass('groupArrow2');
		$.movieContent.slideUp();
		return ;
	}
	
	if($.movieLength > 1){
		$.movieArrow.removeClass('groupArrow2');
		$.movieArrow.addClass('groupArrow1');
		$.movieContent.slideDown();
		return ;
	}
	
	var hash       = "movie";
	var length     = hash.length;
	$.movieCheckBoxes.each(function(){
		var inputBox = $(this);
		var val      = inputBox.val();
		if(inputBox.is(':checked'))hash = hash+"_"+val;
	});
	if(hash.length>length)location.hash = hash+"_1";
}
function togTv(){
	var isHide = $.tvContent.css('display')=="none"; 
	if(!isHide){
		$.tvArrow.removeClass('groupArrow1');
		$.tvArrow.addClass('groupArrow2');
		$.tvContent.slideUp();
		return ;
	}
	$.tvArrow.removeClass('groupArrow2');
	$.tvArrow.addClass('groupArrow1');
	$.tvContent.slideDown();
	if($.tvLength > 1)return ;
	
	var hash       = "tv";
	var length     = hash.length;
	$.tvCheckBoxes.each(function(){
		var inputBox = $(this);
		var val      = inputBox.val();
		if(inputBox.is(':checked'))hash = hash+"_"+val;
	});
	if(hash.length>length)location.hash = hash+"_1";
}
function togMusic(){
	var isHide = $.musicContent.css('display')=="none"; 
	if(!isHide){
		$.musicArrow.removeClass('groupArrow1');
		$.musicArrow.addClass('groupArrow2');
		$.musicContent.slideUp();
		return ;
	}
	$.musicArrow.removeClass('groupArrow2');
	$.musicArrow.addClass('groupArrow1');
	$.musicContent.slideDown();
	if($.musicLength > 1)return ;
	
	var hash   = "music";
	var length = hash.length;
	$.musicCheckBoxes.each(function(){
		var inputBox = $(this);
		var val      = inputBox.val();
		if(inputBox.is(':checked'))hash = hash+"_"+val;
	});
	if(hash.length>length)location.hash = hash+"_1";
}
function togExtra(){
	var isHide = $.extraContent.css('display')=="none"; 
	if(!isHide){
		$.extraArrow.removeClass('groupArrow1');
		$.extraArrow.addClass('groupArrow2');
		$.extraContent.slideUp();
		return ;
	}
	$.extraArrow.removeClass('groupArrow2');
	$.extraArrow.addClass('groupArrow1');
	$.extraContent.slideDown();
	if($.extraLength > 1)return ;
	
	var hash   = "extra";
	var length = hash.length;
	$.extraCheckBoxes.each(function(){
		var inputBox = $(this);
		var val      = inputBox.val();
		if(inputBox.is(':checked'))hash = hash+"_"+val;
	});
	if(hash.length>length)location.hash = hash+"_1";
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
	location.hash = "";
}

function searchDone(){
	//设置标题
	clearInterval($.lastSearchIntervalId);
	$.searchTitle.text('搜索结果')
}
function sure(source,hash){
	var me = $(source);
	var checkBoxes = me.next().children('input:checkbox');
	var length = hash.length;
	checkBoxes.each(function(){
		var checkBox = $(this);
		if(checkBox.is(':checked'))hash = hash + '_'+checkBox.val();
	});
	if(hash.length>length)location.hash = hash+'_1';
}
function proccess(data){
	
}
function getPageHtml(hash,page,total,size){
	var partHash  = hash.substring(0,hash.lastIndexOf('_'));
	var pageTotal = Math.floor((total+size-1)/size);
	var pageGroup = 5;
	var start     = Math.floor(page/pageGroup)*pageGroup;
	if(start==0)start = 1;
	var end       = start == 1?start+pageGroup-1:start+pageGroup;
	if(end>pageTotal)end = pageTotal;
	var html = "<div class='pageDiv'>";
	if(page>1)html += "<a href='#"+partHash+"_"+(1)+"' class='page firstPage'>首页</a>";
	if(page>1)html += "<a href='#"+partHash+"_"+(page-1)+"'"+" class='page'>上一页</a>";
	for(var i=start;i<=end;i++){
		if(i==page){
			html += "<a href='#"+partHash+"_"+(i)+"'"+" class='page pageClick'>"+(i)+"</a>";
		}else{
			html += "<a href='#"+partHash+"_"+(i)+"'"+" class='page'>"+(i)+"</a>";
		}
	}
	if(end<pageTotal)html += "<a href='#"+partHash+"_"+(i)+"'"+" class='page'>..."+(start+pageGroup*2<pageTotal?end+pageGroup*2:pageTotal)+"</a>";
	if(page<pageTotal)html += "<a href='#"+partHash+"_"+(page+1)+"'"+" class='page'>下一页</a>";
	if(page<pageTotal)html += "<a href='#"+partHash+"_"+(pageTotal)+"'"+" class='page'>尾页</a>";
	html += "</div>";
	return html;
}