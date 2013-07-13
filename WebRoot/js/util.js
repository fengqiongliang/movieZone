//dream工具类
/**
 * 使表格可编辑
 * 使用方法
 * <td onclick='editTable(this)' onblur='finish()'></td>
 * finish()是在编辑完表格后触发
 * @param source
 */
function editTable(source){
	var tdObj = $(source);
	//防止trigger触发自定义函数
	tdObj.blur(function(){return false;});
	/**虽然点击input不会错，但单击td还是会出差bug
	 * 解决方法：判断td中是否有input*/
	if(tdObj.children('input').length > 0)return false;
	var tdWidth = tdObj.width();
	var text = tdObj.html();
	tdObj.html('');
	/**
	 * 1、创建一个文本框（创建一个节点）
	 * 2、去掉文本框的边框
	 * 3、设置文本框中文字的大小
	 * 4、文本框的宽度和td的宽度相同
	 * 5、设置文本框的背景色
	 * 6、插入之前需要将当前td中的内容放置到文本框
	 * 7、插入一个文本框到td中
	 */ 
	var inputObj = $('<input type="text" />')
		.css('border-width', '0')
		.css('font-size',tdObj.css('font-size'))
		.css('text-align',tdObj.css('text-align'))
		.css('background-color', tdObj.css('background-color'))
		.width(tdWidth)
		.val(text)
		.appendTo(tdObj);
	inputObj.trigger('focus').trigger('select');
	//再次点击文本框，会有bug，是因为文本框属于TD,因此屏蔽文本框的click事件
	inputObj.click(function(){return false;});
	//处理文本框上回车和esc按键的操作
	inputObj.keyup(
		function(event){
			//获得键值
			var keyCode = event.which;
			//处理回车
			if(13 == keyCode){
				var inputText = $(this).val();
				tdObj.html(inputText);
				tdObj.remove('input');
				tdObj.trigger('blur');
			}
			//处理esc的情况
			if(27 == keyCode){
				//将td中的内容还原
				tdObj.html(text);
				tdObj.remove('input');
				tdObj.trigger('blur');
			}
		}
	);
	//处理文本框失去焦点
	inputObj.blur(
		function(){
			//获取当前文本框中的内容
			var inputText = $(this).val();
			tdObj.html(inputText);
			tdObj.remove('input');
			tdObj.trigger('blur');
		}
	);
	//清除td下的文本框
	tdObj.remove('input');
}
$.extend({
	getUrlVars: function(){
	   var vars = [], hash;
	   var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
	   for(var i = 0; i < hashes.length; i++){
	     hash = hashes[i].split('=');
	     vars.push(hash[0]);
	     vars[hash[0]] = hash[1];
	   }
	   return vars;
	},
	getUrlVar: function(name){
	   return $.getUrlVars()[name];
	}
});