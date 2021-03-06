<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.iptv.com/UrlUtil" prefix="u"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/assets/inc_tv.jsp"%>
<link rel="stylesheet" href="${basePath}assets/Content/common.css"
	type="text/css" />
<link rel="stylesheet" href="${basePath}assets/Content/search_chen.css"
	type="text/css" />
<script type='text/javascript'>
	$(function () {
		var html = ''
		for (var i = 0; i < 26; i++) {
			var key = String.fromCharCode(i + 65)
			html += '<li><a href="#" data-key="'+key+'" onclick="keyboard(this)">'+key+'</a><div class="link"><span>' + key + '</span></div></li>'
		}
		$('.english ul').append(html)
		
		$('.english li:first-child a')[0].focus();
		$('.english li:first-child a').siblings('.link').addClass('focus');

		$('.overflow').css('width', ($('.item').length + 1) * $('.item').outerWidth(true))

		var lastPage=0;
		$('.items .item a').live('focus',function(){
			$('.link').removeClass('focus')
			$(this).siblings('.link').addClass('focus')
			var w = $('.overflow .item').outerWidth(true)
			var index=$(this).parents('.item').index();
			var $items=$('.items .item');
			if(index>lastPage&&(lastPage>=0)){
				var right=($items.length)>4&&(parseFloat($('.overflow').css('margin-left')) >= 10-($items.length-4)*w);
				var leftC=-(index-2)*w;
				if(leftC<-($items.length-4)*w){
					leftC=-($items.length-4)*w
				}
				if((index>2)&&right){
					$('.overflow').animate({
						marginLeft:leftC
					})
				}
			}else if(index<$items.length-3&&(index<lastPage)&&(lastPage>=0)){
				if (parseFloat($('.overflow').css('margin-left')) <= 0&&(index>0)) {
					/* $('.header .link').html(-(index-1)*w) */
					$('.overflow').animate({
						marginLeft:-(index-1)*w
					})
				}
			}
			lastPage=index;
		})

		$('.session a,.more a').on('focus',function(){
			$('.link').removeClass('focus')
			$(this).siblings('.link').addClass('focus')
			lastPage=-1;
		})
		$('.clear a').on('click',function(){
			$('.input span').html('');
			$('.items .overflow').html("");
			$('.overflow').css('margin-left',0);

            $('.input span').addClass("null")
			$('.js-s-default').show();
			$('.js-s-default span').html("请输入查找视频首字母");
		})

		$('a').live('focus',function(){
			$('.link').removeClass('focus')
			$(this).siblings('.link').addClass('focus')
		})

		$('.items .overflow a').on('click',function(){
			window.location.href = '${basePath}/video/detail?id='+ $(self).find("input:hidden").val();
		})
		
		$(document).on('keydown', function(e) {
			if(e.keyCode == 0x0008) {	// 返回
				window.location.href = basePath + "home/index";
			}
		});
	})

	function keyboard(obj){
		var key=$(obj).attr('data-key');
        var val=$('.input span').html();
        if(key==='del'){
            $('.input span').html(val.substring(0,val.length-1))
            $('.input span').addClass("null")
        }else{
            $('.input span').html(val+key)
            $('.input span').removeClass("null")
        }
		
		var param = {name:$('.input span').html()};
		$.get('${basePath}video/searchList',param,function(data){
			$('.items .overflow').html("");

			var jmz = {};
			jmz.GetLength = function(str) {
			  return str.replace(/[\u0391-\uFFE5]/g,"aa").length;  //先把中文替换成两个字节的英文，在计算长度
			};  
			var len = jmz.GetLength($('.input span').html());

			if(data.total > 0){
				$.each(data.data,function(i,v){
						$('.js-s-default').hide();

						var html = '<div class="item">'
								 + '<a href="${basePath}video/detail?id='+v.Id+'">kengdie<input type="hidden" value="'+v.Id+'"></a>'
								 + '<div class="link">'
								 + ' <div class="img"><img src="${staticUrl}/'+v.ImageUrl+'" alt=""></div>'
								 + '  <div class="info">'
								 + '   <div class="name">'	
								 + '    <span>' + v.Name.substr(0,len) + '</span>'+v.Name.substr(len)
								 + '   </div>'				
								 + ' </div>'
								 + '</div>'
								 + '</div>';

						$('.items .overflow').append(html);
				});

				if(data.total>19){
					var more = '<div class="more">'
							 +  '<a href="${basePath}video/more?key='+encodeURI($('.input span').html())+'">kengdie </a>'
							 +  '<div class="link">'
							 +  '<span>更多视频</span>'
							 +  '</div></div>';
					$('.items .overflow').append(more);
				}
			}else{
				$('.js-s-default').show();
				$('.js-s-default span').html("没有搜索到相关视频");
			}
	
			$('.overflow').css('width', ($('.item').length + 1) * $('.item').outerWidth(true))
		});
	}
</script>
</head>
<body>
	<div class="main">
		<div class="top">
			<div class="header">
				<div class="link"><img src="${basePath}assets/Images/iptv_logo.png" alt=""></div>
			</div>
		</div>
		<div class="nav">
			<div class="arrow arrow_left">
				<div class="link">
					<img src="${basePath}assets/Images/search_left.png" alt="">
				</div>
			</div>
			<div class="items">
				<div class="overflow">
				</div>
				<div class="hint js-s-default">
                <div class="link js-s-default">
                    <span>请输入查找视频首字母</span>
                </div>
            </div>
			</div>
			<div class="arrow arrow_right">
				<div class="link">
					<img src="${basePath}assets/Images/search_right.png" alt="">
				</div>
			</div>
		</div>

		<div class="session">
			<div class="enter">
				<div class="input">
					<span class="null"></span>
				</div>
				<div class="clear">
					<a href="#">kengdie</a>
					<div class="link">清空条件</div>
				</div>
			</div>
			<div class="keyboard">
				<div class="english">
					<ul></ul>
				</div>
				<div class="num">
					<ul>
						<li><a href="#" data-key="1" onclick="keyboard(this)"> </a>
							<div class="link">
								<span>1</span>
							</div></li>
						<li><a href="#" data-key="2" onclick="keyboard(this)"> </a>
							<div class="link">
								<span>2</span>
							</div></li>
						<li><a href="#" data-key="3" onclick="keyboard(this)"> </a>
							<div class="link">
								<span>3</span>
							</div></li>
						<li class="chinese"><a href="#" data-key="del"
							onclick="keyboard(this)"> </a>
							<div class="link">
								<span>删除</span>
							</div></li>
						<li><a href="#" data-key="4" onclick="keyboard(this)"> </a>
							<div class="link">
								<span>4</span>
							</div></li>
						<li><a href="#" data-key="5" onclick="keyboard(this)"> </a>
							<div class="link">
								<span>5</span>
							</div></li>
						<li><a href="#" data-key="6" onclick="keyboard(this)"> </a>
							<div class="link">
								<span>6</span>
							</div></li>
						<li class="chinese"><a href="#" data-key=" "
							onclick="keyboard(this)"> </a>
							<div class="link">
								<span>空格</span>
							</div></li>
						<li><a href="#" data-key="7" onclick="keyboard(this)"> </a>
							<div class="link">
								<span>7</span>
							</div></li>
						<li><a href="#" data-key="8" onclick="keyboard(this)"> </a>
							<div class="link">
								<span>8</span>
							</div></li>
						<li><a href="#" data-key="9" onclick="keyboard(this)"> </a>
							<div class="link">
								<span>9</span>
							</div></li>
						<li><a href="#" data-key="0" onclick="keyboard(this)"> </a>
							<div class="link">
								<span>0</span>
							</div></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</body>
</html>