<%@ page language="java" contentType="text/javascript; charset=UTF-8" pageEncoding="UTF-8"%>
var voter = {};

voter.registerClickHandlers = function(){
	$('a.widget-close').on('click', function(event){
		event.preventDefault();
		$(this).parent().parent().slideUp();
	});
	
	$('ul#voter-widget').on('click', 'li.download > div.voteup > a', voter.voteHandler);

	$('ul#voter-widget').on('click', 'li.download > span.addToRecommendation', function(event){
		event.preventDefault();
		$('#recommend').hide();
		var $self = $(this);
		if($self.hasClass('disabled')) return;
		$self.addClass('disabled');
		
		$.ajax({
		  url: "${pageContext.request.contextPath}/ajax/init.html",
		  dataType: 'jsonp',
		  data: {
		  	'asin' : $self.parent().attr('id'),
		  	'context' : $('#__context').val(),
		  	'domain' : $('#__domain').val()
		  }
		}).done(function(data){
			data = JSON.parse(data);
			if(data.isError) return;
			
			var $voteupDiv = $self.siblings('div.voteup');
			$self.remove();
			$voteupDiv.show();
			$voteupDiv.children('span.voteCount').text(data.voteCount);
		}). fail(function(){
			$self.removeClass('disabled');
		});
		
	});
};

voter.voteHandler = function(event){
	event.preventDefault();
	var $self = $(this);
	var $self = $(this);
	if($self.hasClass('disabled')) return;
	$self.addClass('disabled');
	
	$.ajax({
		  url: "${pageContext.request.contextPath}/ajax/vote.html",
		  dataType: 'jsonp',
		  data: {
		  	'asin' : $self.parent().parent().attr('id'),
		  	'context' : $('#__context').val(),
		  	'domain' : $('#__domain').val(),
		  	'voteUp' : $self.hasClass('upVote')
		  }
		}).done(function(data){
			data = JSON.parse(data);
			if(data.isError) return;
			
			$self.siblings('span.voteCount').text(data.voteCount);
			$self.siblings('a').addClass('disabled');
			var direction = 'up';
			if($self.hasClass('upVote')) {
				$self.removeClass('upVote');
				$self.addClass('upVoted');
			} else {
				$self.removeClass('downVote');
				$self.addClass('downVoted');
				direction = 'down';
			}
		}). fail(function(){
			$self.removeClass('disabled');
		});
};
voter.init = function(){
	voter.registerClickHandlers();
	
	var $addToWishList = $('#recommend');
	$('ul#voter-widget').on('mouseenter', 'li.download > span.addToRecommendation', function() {
		$addToWishList.hide();
		var offset = $(this).offset();
		$addToWishList.css({
			'left' : (offset.left+40)+'px',
			'top' : (offset.top-40)+'px'
		});
		$addToWishList.fadeIn();
	});
	
	$('ul#voter-widget').on('mouseleave', 'li.download > span.addToRecommendation', function() {
		$addToWishList.fadeOut();
	});
};

voter.asinDescAjax = null;

voter.onIconHover = function(){
	if(voter.asinDescAjax)
		voter.asinDescAjax.abort();
	var $self = $(this);
	voter.asinDescAjax = $.ajax({
		  url: "${pageContext.request.contextPath}/ajax/asinDesc.html",
		  dataType: 'jsonp',
		  data: {
		  	'asin' : $self.parent().attr('id')
		  }
		}).done(function(data){
			data = JSON.parse(data);
			if(data.descNotAvailable)
				return;
			var desc = decodeURIComponent(data.asinDesc).replace(/\+/g, ' ');
			if(desc.length>=1400)
				desc = desc.substring(0, 1400) +" ...";
			top.postMessage({
				'title' : $self.siblings('p.download-name').text(),
				'icon' : $self.children('img').attr('src'),
				'asinDesc' : desc,
				'offset' : $self.offset()
			}, 
			document.referrer);
		}).fail(function(){
			top.postMessage({
				'error' : true
			}, 
			document.referrer);
		});
};
voter.onIconOut = function(){
	if(voter.asinDescAjax)
		voter.asinDescAjax.abort();
		
		top.postMessage({
				'mouseout' : true
			}, 
			document.referrer);
};
$(function(){
	voter.init();
	$('ul#voter-widget').on({
		mouseenter: voter.onIconHover,
		mouseleave: voter.onIconOut
	}, 'li.download > a');
});