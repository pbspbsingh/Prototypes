<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>Responsive Search Widget</title>
<style type="text/css">
/* Reset CSS */
html,body,div,span,applet,object,iframe,h1,h2,h3,h4,h5,h6,p,blockquote,pre,a,abbr,acronym,address,big,cite,code,del,dfn,em,img,ins,kbd,q,s,samp,small,strike,strong,sub,
sup,tt,var,b,u,i,center,dl,dt,dd,ol,ul,li,fieldset,form,label,legend,table,caption,tbody,tfoot,thead,tr,th,td,article,aside,canvas,details,embed,figure,figcaption,footer,
header,hgroup,menu,nav,output,ruby,section,summary,time,mark,audio,video{margin:0;padding:0;border:0;font-size:100%;font:inherit;vertical-align:baseline;}
article,aside,details,figcaption,figure,footer,header,hgroup,menu,nav,section{display:block;}body{line-height:1;}ol,ul{list-style:none;}blockquote,q{quotes:none;}
blockquote:before,blockquote:after,q:before,q:after{content:'';content:none;}table{border-collapse:collapse;border-spacing:0;}body{-webkit-text-size-adjust:none;}
* {
	overflow: hidden;
	font-family: Arial,sans-serif;
}
body {
	width: 99%;
	height: auto;
	margin: auto;
	overflow: hidden;
	border-top:3px solid #f59c38;
	border-left:1px solid #ccc; 
	border-right:1px solid #ccc; 
	border-bottom:1px solid #ccc;
	background: none;
	border-radius: 3px;
	font-family: Arial,sans-serif;
} 
ul {
	list-style: none;
}
#container {
	border: none;
	background: none #FFF;
}
.gradient-background {
				background: #f6f6f6;
		background: -moz-linear-gradient(top, rgba(255,255,255,0.9), rgba(238,238,238,0.9)); /* (255,255,255)==FFF, (238,238,238)==EEE */
		background: -webkit-linear-gradient(top, rgba(255,255,255,0.9), rgba(238,238,238,0.9));
		background: -o-linear-gradient(top, rgba(255,255,255,0.9), rgba(238,238,238,0.9));
		background: -ms-linear-gradient(top, rgba(255,255,255,0.9), rgba(238,238,238,0.9));
		background: linear-gradient(top, rgba(255,255,255,0.9), rgba(238,238,238,0.9));
		filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#D8FFFFFF', endColorstr='#D8EEEEEE', GradientType=0);
		}
.left {
	float: left;
}
.right {
	float: right;
}
.clear-all {
	clear: both;
}
#criteriaSection {
	padding: 0 10px;
	border-bottom: 1px solid #ccc;
}
header {
	width: auto;
	display: block;
	overflow: hidden;
	padding: 0;
	margin: 7px 0 5px;
	min-width: 180px;
}
#amazonLogo {
	height: 20px;
	width: 70px;
	display: inline-block;
	cursor: pointer;
	background: transparent url('http://g-ecx.images-amazon.com/images/G/01/associates/widgets/965/20070822/US/img/search-widget-sprit._V340316229_.png') -167px -1px;
			text-indent: -9999px;
}
#categoryHolder {
	cursor: pointer;
	font-size: 13px;
	display: block;
	color: #333;
	padding: 1px 4px;
	border-radius: 3px;
		}
#categoryHolder:hover, #categoryHolder:active {
	background-color: #ebebeb;
		}
#categoryHolder:active, #categoryHolder:focus {
	outline:none;
}
#categoryHolder.clicked {
	background-color: #EBEBEB;
	outline: 0 none;
	box-shadow: 0 3px 5px rgba(0, 0, 0, 0.05) inset;
	-webkit-box-shadow: inset 0 3px 5px rgba(0,0,0,.05);
		}
#categorySelection {
	max-width: 120px;
	height: 14px;
	display: inline-block;
	overflow: hidden;
	white-space: nowrap;
	text-overflow: ellipsis;
}
#categorySelection.full {
	max-width: 160px;
}
#categorySelection.mid {
	max-width: 140px;
}
#categorySelection.low {
	max-width: 120px;
}
#arrow {
	display: inline-block;
	vertical-align: middle;
	width: 10px;
	height: 5px;
 	background: transparent url('http://g-ecx.images-amazon.com/images/G/01/associates/widgets/965/20070822/US/img/search-widget-sprit._V340316229_.png') -239px -2px no-repeat;
 		 	text-indent: -9999px;
 	margin-top: -4px;
}
#searchBoxDiv {
	margin:0; padding:0;
	height: 40px;
	position: relative;
}
#searchBoxDiv > form {
	position: relative;
	width: 100%;
	display: table;
}
#searchBoxDiv #searchFieldHolder {
	display: table-cell;
	width: 100%;
	margin: 0; padding: 0;
	background: #FFF;
	height: 30px;
	border-top:1px solid #ccc;
	border-right:none;
	border-bottom:1px solid #ccc;
	border-left:1px solid #ccc;
	outline: none;
	border-radius: 4px 0 0 4px;
}
#searchBoxDiv #searchFieldHolder.focus { 
	border-color: #e49747;
	-webkit-box-shadow: 0 0 3px rgba(228, 121, 17, 0.5), 0 1px 0 rgba(0, 0, 0, 0.07) inset;
	-moz-box-shadow: 0 0 3px rgba(228, 121, 17, 0.5), 0 1px 0 rgba(0, 0, 0, 0.07) inset;
	box-shadow: 0 0 3px rgba(228, 121, 17, 0.5), 0 1px 0 rgba(0, 0, 0, 0.07) inset; 
}
#searchBoxDiv #searchFieldHolder input {
	width: 100%;
	height: 26px;
	border: none;
	outline: none;
	vertical-align: baseline;
	font-size: 13px;
	background: #fff;
}
input.placeholder {
	color: #a9a9a9;
}
#searchBoxDiv #submitHolder {
	display: table-cell;
	margin: 0; padding: 0;
	background: #5B626A;
	border-color: #485059 #2C3137 #363C43 #485059;
	border-radius: 0 4px 4px 0;
	-webkit-appearance: none;
 		-webkit-border-radius: 0 4px 4px 0;
    border-style: solid;
    border-width: 1px;
}
#searchBoxDiv #submitHolder #submit {
	color: #FFF;
	width: 40px;
	height: 30px;
	vertical-align: middle;
	text-align: center;
	text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.25);
	padding: 0; margin: 0;
	background: #5B626A;
	border:0;
	border-radius: 0 4px 4px 0;
	-webkit-appearance: none;
 		-webkit-border-radius: 0 4px 4px 0;	
    overflow: hidden;
    cursor:pointer;
    font-weight: bold;
}
#searchBoxDiv #submitHolder #submit:hover {
	background: #232323;
	background: -moz-linear-gradient(top, #232323, #000000);
	background: -webkit-linear-gradient(top, #232323, #000000);
	background: -o-linear-gradient(top, #232323, #000000);
	background: -ms-linear-gradient(top, #232323, #000000);
	background: linear-gradient(top, #232323, #000000);
}
#loader {
	width:auto; background:rgba(255,255,255,0.9); display:none; padding:10px; text-align:center;
		}
#searchResult {
	display: block;
	width: 100%;
	display: none;
	max-height: 224px;
	overflow-y:scroll;
	background: rgba(255,255,255,.95);
		}
#searchResult::-webkit-scrollbar {
    -webkit-appearance: none;
    width: 7px;
}
#searchResult::-webkit-scrollbar-thumb {
    border-radius: 4px;
    background-color: rgba(0,0,0,.5);
    -webkit-box-shadow: 0 0 1px rgba(255,255,255,.5);
    	}
#searchResult.full-width .__ma-sw-search-item {
	width: 100%;
}
#searchResult.half-width .__ma-sw-search-item {
	width: 50%;
}
#searchResult .__ma-sw-search-item {
	border-top: 1px solid #ccc;
	height:74px; 
	float:left;
}
#searchResult .__ma-sw-search-item:first-child {
	border-top: none;
}
#searchResult.half-width .__ma-sw-search-item:nth-child(2) {
	border-top: none;
}
#searchResult .__ma-sw-search-item .search-item {
	 margin: 7px; 
	 height:60px;
}
#searchResult .__ma-sw-search-item .search-item a {
	color:#2b4c97;
	text-decoration:none; 
		}
#searchResult .__ma-sw-search-item .thumbnail-holder {
	float:left; 
	width:25%; 
	height:100%; 
	vertical-align:middle;
	text-align:left;
		}
#searchResult .__ma-sw-search-item .details-holder {
	float:left; 
	width:75%; 
	height:100%;
}
#searchResult .__ma-sw-search-item .details-holder .details p { 
	float: left;
}
#searchResult .__ma-sw-search-item .details-holder .price {
	color: #9b0000;
	font-weight: bold;
	font-size: 12px;
	margin-left: 5px;
	/*float: left;*/
	margin-top: 2px;
		}
#searchResult .__ma-sw-search-item .reviews-count-link {
	color: #000;
	text-decoration: none;
		}
#searchResult .__ma-sw-search-item .reviews-count-link:hover {
	text-decoration: none;
}
#searchResult .__ma-sw-search-item span.star-rating {
	display: inline-block;
	vertical-align: middle;
	height: 16px;
	width: 80px;
	text-indent:-9999px;
	background: url('http://g-ecx.images-amazon.com/images/G/01/associates/widgets/965/20070822/US/img/search-widget-sprit._V340316229_.png') -21px -31px;
}
#searchResult .__ma-sw-search-item .amazon-prime {
	display: inline-block;
	vertical-align: middle;
	height: 15px;
	width: 50px;
	background: url('http://g-ecx.images-amazon.com/images/G/01/associates/widgets/965/20070822/US/img/search-widget-sprit._V340316229_.png') -83px 0px;
}
#footer {
	width:auto; border-top:1px solid #ccc; text-align:left; padding:5px 10px; display:none;
}
#footer > a {
	color:#2b4c97; 
	text-decoration:none;
	font-size: 13px;
		}
#footer > a b {
	font-weight:bold;
}
#resultTemplate {
	display: none;
}
#micHolder {
	position: absolute;
	z-index: 9999;
	top: -7px;
	right: 38px;
	width: 44px;
}
#youMean {
	padding: 5px 10px;
	border-bottom: 1px solid #CCC;
	display: none;
}
#youMean span {
	color: #9b0000;
	font-weight: bold;
}
.speak {
	text-decoration: none;
}
</style>
<body>
	<div id="container">
		<div id="criteriaSection" class="gradient-background">
			<header>
				<div class="left">
					<a href="http://www.amazon.com/ref=assoc_res_sw_logo?tag=pbs-44&linkCode=w13&linkID=junkLinkID" id="amazonLogo" target="_blank">amazon</a>
				</div>
				<div id="categoryHolder" class="right" tabindex="0">
					<span id="categorySelection" data-value="aps" data-search="All">Amazon.com</span>
					&nbsp;<span id="arrow">arrow</span>
				</div>
				<div class="clear-all"></div>
			</header>
			<div id="searchBoxDiv">
				<form action="http://www.amazon.com/s/ref=assoc_res_sw_view_all?tag=pbs-44&linkCode=w13&linkID=junkLinkID" method="get" target="_blank">
					<div id="searchFieldHolder">
						<div style="padding:0 5px;">
							<input id="searchField" name="field-keywords" type="text" autocomplete="off" placeholder="Search Amazon" />
							<span id="micHolder"><a href="#" id="mic"><img src="images/mic.gif" alt="mic" style="width: 100%;" /></a></span>
						</div>
					</div>
					<div id="submitHolder">
						<input id="submit" type="submit" value="Go" />
					</div>
				</form>
			</div>
		</div>
		<div id="youMean" class="gradient-background">
			<p>Did you mean? "<span>Harry potter</span>" &nbsp; &nbsp; <a href="#" id="yes" class="speak">Yes</a> / <a href="#" class="speak" id="no">No</a></p>
		</div>
		<div id="loader">
			<img src="http://g-ecx.images-amazon.com/images/G/01/associates/widgets/965/20070822/US/img/loader._V379259286_.gif" alt="Loading..." />
		</div>
		<div id="searchResult" class="half-width">
		</div>
		<div id="footer" class="gradient-background">
			<a href="#" target="_blank">View all</a>
			<a href="http://www.amazon.com/ref=assoc_res_sw_no_result?tag=pbs-44&linkCode=w13&linkID=junkLinkID" style="display:none;" target="_blank">No result found</a>
		</div>
		<div id="resultTemplate">
			<div class="__ma-sw-search-item">
				<div class="search-item">
					<div style="width:100%; height:100%;">
						<div class="thumbnail-holder">
							<a href="http://www.amazon.com/?tag=pbs-44&linkCode=w13&linkID=junkLinkID" target="_blank" class="thumbnail">
								<img src="" style="max-width:100%;max-height:100%;" />
							</a>
						</div>
						<div class="details-holder">
							<ul style="list-style:none; overflow:hidden;">
								<li style="height:24px; overflow: hidden; margin-bottom:5px;">
									<p class="title" style="font-size:12px; font-weight:bold; margin:0 5px;overflow:hidden;">
										<a href="http://www.amazon.com/?tag=pbs-44&linkCode=w13&linkID=junkLinkID" target="_blank">Title</a>
									</p>
								</li>
								<li class="details">
									<p>
										<span class="price">--</span> <span class="amazon-prime" style="margin-left:10px;"></span>
									</p>
									<p style="font-size:12px; font-weight:normal; margin-left:5px;">
										<a href="#" target="_blank" class="star-rating-link"><span class="star-rating">star-rating</span></a>
										&nbsp;
										<a href="#" target="_blank" class="reviews-count-link"><span class="reviews-count" style="font-size:11px; height:15px;"></span></a>
									</p>
									<div style="clear: both;"></div>
								</li>
							</ul>
						</div>
						<div style="clear: both;"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
<script type="text/javascript">
var widgetId = '__mobileAssociatesSearchWidget_pl_test_tryitout_1';
var postToParent = function(data) {
	parent.postMessage(JSON.stringify(data), '*');
};
var resizeFrame = function(){
	postToParent({
		'widgetId': widgetId,
		'operation': 'resizeAd',
		'height': document.getElementsByTagName('body')[0].clientHeight
	});
};
(function(){
	var result_count = 10;
	{
		if(252 < 178){
			result_count = 0; //No area to show the results
			if(console && console.log)
				console.log("No Area to display results, converting search widget to search box.");
		} else
			document.getElementById('searchResult').style['maxHeight']=(476-24-80+2-1) + 'px'; //Hack to hide border-bottom of result 
							
			if(result_count==0) document.getElementById('criteriaSection').style['borderBottom'] = 'none';
	}
	{ //Category handling
		window.categoryVisible = false;
		var categoryHolder = document.getElementById('categoryHolder'); 
		categoryHolder.onclick = function(){
			postToParent({
				'widgetId': widgetId,
				'operation': 'hideSuggestion'
			});
			if(window.categoryVisible) {
				postToParent({
					'widgetId': widgetId,
					'operation': 'hideCategory'
				});
				window.categoryVisible = false;
				categoryHolder.setAttribute('class', 'right');
			} else {
				postToParent({
					'widgetId': widgetId,
					'operation': 'showCategory'
				});
				window.categoryVisible = true;		
				categoryHolder.setAttribute('class', 'right clicked');
			}
		};
		window.hideCategory = function() {
			if(window.categoryVisible) {
				postToParent({
					'widgetId': widgetId,
					'operation': 'hideCategory'
				});
				window.categoryVisible = false;
				categoryHolder.setAttribute('class', 'right');
			}
		};
	}
	window.addEventListener('message', function(event){ //Communication between parent and iframe
		if(!event.data) return;
		var mydata = JSON.parse(event.data);
		if(!(mydata &&  mydata.operation)) return;
		
		if(mydata.operation=='selectCategory') {
			var category = document.getElementById('categorySelection');
			category.innerHTML = mydata.html;
			category.setAttribute('data-value', mydata.value);
			category.setAttribute('data-search', mydata.search);
			document.getElementById('categoryHolder').click();
			if(result_count){
				var searchField = document.getElementById('searchField'); 
				var loadResult = searchField.getAttribute('class') == 'placeholder' ? false : (searchField.value ? true : false);
				if(loadResult)
					document.getElementById('submit').click();
				else if(typeof loadTopSeller == "function")
					loadTopSeller();
			}
		} else if (mydata.operation == 'selectSuggestion') {
			document.getElementById('searchField').value = mydata.text;
			document.getElementById('searchField').focus();
			
			var category = document.getElementById('categorySelection');
			if(mydata.catValue && mydata.catHTML && mydata.catSearch && (mydata.catValue!=category.getAttribute('data-value'))) {
				category.innerHTML = mydata.catHTML;
				category.setAttribute('data-value', mydata.catValue);
				category.setAttribute('data-search', mydata.catSearch);
				postToParent({
					'widgetId': widgetId,
					'operation': 'updateCategory',
					'catValue': mydata.catValue
				});
			}
			if(mydata.clicked && result_count>0)
				document.getElementById('submit').click();
		} else if(mydata.operation == 'hideCategoriesInSrchWidget') {
			document.getElementById('categoryHolder').click();
		} else if(mydata.operation == 'loseFocus') {
			document.getElementById('amazonLogo').focus();
		}
	}, false);
	if(true){  //Autocomplete of search
		var searchField = document.getElementById('searchField');
		var oldLength = searchField.value ? searchField.value.length : 0;
		searchField.addEventListener('keyup', function(event) {
			if(searchField.value && (oldLength!=searchField.value.length) && (event.keyCode!=38 && event.keyCode!=40 && event.keyCode!=13)){
				oldLength = searchField.value.length;
				var searchKey = searchField.value;
				if(!(searchKey && searchKey.length>=2)) return;
				
				window.isSearchStarted = false;
				searchKey = encodeURIComponent(searchKey);
				var searchAlias = document.getElementById('categorySelection');
				if(searchAlias)
					searchAlias = searchAlias.getAttribute('data-value');
				else
					searchAlias = 'aps';
				if(window.__maSwJsNode)
					if(window.__maSwJsNode.parentNode){
						window.__maSwJsNode.parentNode.removeChild(window.__maSwJsNode);
						//console.log('Trying to abort JSONP call.'); // Removing console log, as it is creating problem in IE9
					}
				window.__maSwJsNode = document.createElement('script');
				window.__maSwJsNode.src = location.protocol + '//' + 'completion.amazon.com/search/complete?method=completion&q='+searchKey+'&search-alias='+searchAlias+'&mkt=1&fb=1&xcat=0&x=__maSwAutocomplete&sc=1&noCacheIE=1391185589270';
				document.getElementsByTagName('head')[0].appendChild(window.__maSwJsNode);
			}
		}, false);
		searchField.addEventListener('keydown', function(event) {
			if(event.keyCode==38 || event.keyCode==40) { // UP & DOWN key
				event.stopImmediatePropagation();
				event.preventDefault();
			}
			if(event.keyCode==9 || event.keyCode==13 || event.keyCode==27) { // TAB, ENTER & ESC key
				postToParent({
					'widgetId': widgetId,
					'operation': 'hideSuggestion'
				});
			} else if(event.keyCode==40){ //Down Arrow
				postToParent({
					'widgetId': widgetId,
					'operation': 'selectSuggestion',
					'key': 'DOWN',
					'oldValue': searchField.value
				});
			} else if(event.keyCode==38){ // Up Arrow
				postToParent({
					'widgetId': widgetId,
					'operation': 'selectSuggestion',
					'key': 'UP', 
					'oldValue': searchField.value
				});
			}
		}, false);
	}
	{ // Handle keyboard event for category selection
		var categoryHolder = document.getElementById('categoryHolder');
		categoryHolder.addEventListener('keydown', function(event) {
			if(!window.categoryVisible) return;
			if(event.keyCode==9 || event.keyCode==13 || event.keyCode==27 || event.keyCode==38 || event.keyCode==40) {
				event.preventDefault();
				postToParent({
					'widgetId': widgetId,
					'operation': 'keyPressCategory',
					'key': event.keyCode
				});
			}
		});
	}
	{ // Handle the submit event
		var resultCount = result_count; //Set the search result size
		if(resultCount < 0) resultCount = 0;
		else if (resultCount > 20) resultCount = 20;
		document.getElementById('searchBoxDiv').getElementsByTagName('form')[0].addEventListener('submit', function(event){
			event.preventDefault();
			postToParent({
				'widgetId': widgetId,
				'operation': 'hideSuggestion'
			});
			var searchField = document.getElementById('searchField');
			window.isSearchStarted = true;
			if(!searchField.value) return;
			
			document.getElementById('searchResult').style['display'] = 'none';
			document.getElementById('youMean').style['display'] = 'none';
			document.getElementById('footer').style['display'] = 'none';
			if(resultCount) {
				document.getElementById('loader').style['display'] = 'block';
				resizeFrame();				
			}
			if(window.__maSwJsNode2) 
				if(window.__maSwJsNode2.parentNode){
					window.__maSwJsNode2.parentNode.removeChild(window.__maSwJsNode2);
					//console.log('Trying to aboring JSONP-2 call.'); //IE9 fix
				}
			var selectedCat = document.getElementById('categorySelection');
			var searchTerm = encodeURIComponent(searchField.value);
			var href = 'http://www.amazon.com/s/ref=assoc_res_sw_view_all?field-keywords='+ searchTerm + '&search-alias=' + 
				(selectedCat != null ? selectedCat.getAttribute('data-value') : 'aps') + '&tag=pbs-44&linkCode=w13&linkID=junkLinkID'; 
			document.getElementById('footer').getElementsByTagName('a')[0].setAttribute('href', href);  
			{
				var img = new Image();
				img.src = location.protocol + '//' + 'ir-na.amazon-adsystem.com/s/wa?o=1&t=pbs-44&l=w13&field-keywords='
				          + searchTerm + '&search-alias=' + (selectedCat != null ? selectedCat.getAttribute('data-value') : 'aps')
				          +'&src=res-srch-wgt';
			}
			if(resultCount) {
				var category =  selectedCat ? (selectedCat.getAttribute('data-search') ? selectedCat.getAttribute('data-search') : "All") : "All";			
				window.__maSwJsNode2 = document.createElement('script');
				window.__maSwJsNode2.src = 'http://ws-na.amazon-adsystem.com/widgets/q?Operation=GetResults&Keywords=' + searchTerm
						+'&SearchIndex='+ category +'&multipageStart=0&InstanceId=0&multipageCount='+resultCount+'&TemplateId=MobileSearchResults&ServiceVersion=20070822&MarketPlace=US';
				document.getElementsByTagName('head')[0].appendChild(window.__maSwJsNode2);
				if(window.submitTimer) {
                	clearTimeout(window.submitTimer);
                	delete window.submitTimer;
            	}
            	window.submitTimer = setTimeout(function(){
           			document.getElementById('submit').click();
            	}, 3000);
			} else {
				window.open(href);
			}
			if(window.XMLHttpRequest){
				if(window.xmlhttp) window.xmlhttp.abort();
				window.xmlhttp = new XMLHttpRequest();
				xmlhttp.onreadystatechange = function() {
			        if (xmlhttp.readyState == 4 && xmlhttp.status == 200 && xmlhttp.responseText) {
			            document.querySelector("#youMean > p > span").innerHTML = xmlhttp.responseText;
			            document.getElementById('youMean').style.display = 'block';
			            resizeFrame();
			            if(document.querySelector('#micHolder > a > img').getAttribute('src').indexOf('mic-animate.gif')!=-1) {
			            	var msg = new SpeechSynthesisUtterance('Did you mean ' + xmlhttp.responseText + '?');
			            	window.speechSynthesis.speak(msg);
			            }
			        }
			    };
			    xmlhttp.open("GET", "spellchecker?searchKey="+searchTerm+"&category="+selectedCat.getAttribute('data-value'), true);
			    xmlhttp.send();
			}
		}, false);	
	}
	{ /* Handling focus of Search field */
		var searchField = document.getElementById('searchField');
		searchField.onfocus = function(event){
			document.getElementById('searchFieldHolder').setAttribute('class', 'focus');
			if(window.hideCategory)
				window.hideCategory();
		};
		searchField.onblur = function(event){
			document.getElementById('searchFieldHolder').setAttribute('class', '');
		};
	}
	{ // Changing from one to two layout
		var resultContainer = document.getElementById('searchResult');
		var body = document.getElementsByTagName('body')[0];
		var cat = document.getElementById('categorySelection');
		var resizeHandler = function(event) {
			if(body.offsetWidth > 600) {
				resultContainer.setAttribute('class', 'half-width');
			} else {
				resultContainer.setAttribute('class', 'full-width');
			}
			if(body.offsetWidth > 285)
				cat.setAttribute('class', 'full');
			else if(body.offsetWidth > 260)
				cat.setAttribute('class', 'mid');
			else if(body.offsetWidth > 240)
				cat.setAttribute('class', 'low');
			resizeFrame();
			if(typeof improviseTitle == 'function') improviseTitle();
		};
		resizeHandler();
		window.addEventListener('resize', resizeHandler, false);
	}
	{
		if(navigator.userAgent && navigator.userAgent.length >= 84 && navigator.userAgent.substring(75, 81)=="Chrome") {
			var chromeVersion = parseInt(navigator.userAgent.substring(82, 84));
			if(!isNaN(chromeVersion) && chromeVersion <=30)
				document.getElementById('submitHolder').style['display']='-webkit-inline-box';
		}
	}
	window.loadTopSeller = function() {
		window.topseller_display_callback = window.search_callback;
		
		document.getElementById('loader').style['display'] = 'block';
		document.getElementById('searchResult').style['display'] = 'none';
		document.getElementById('youMean').style['display'] = 'none';
		document.getElementById('footer').style['display'] = 'none';
		resizeFrame();
		var category = document.getElementById('categorySelection') ? document.getElementById('categorySelection').getAttribute('data-search') : 'All';
		var cat = document.getElementById('categorySelection') ? document.getElementById('categorySelection').getAttribute('data-value') : 'aps';
		
		var href = 'http://www.amazon.com/s/ref=assoc_res_sw_top_seller?search-alias=' + cat + '&tag=pbs-44&linkCode=w13&linkID=junkLinkID'; 
		document.getElementById('footer').getElementsByTagName('a')[0].setAttribute('href', href);
		
		window.__maSwJsNode2 = document.createElement('script');
		window.__maSwJsNode2.src = 'http://ws-na.amazon-adsystem.com/widgets/q?Operation=GetTopSellers&URL=' + encodeURIComponent('http://ws-na.amazon-adsystem.com/') +  
			'&InstanceId=0&ResponseCount=10&TemplateId=8002&ServiceVersion=20070822&MarketPlace=US&CategoryRestriction=' + category;
		document.getElementsByTagName('head')[0].appendChild(window.__maSwJsNode2);
		
		var img = new Image();
		img.src = location.protocol + '//' + 'ir-na.amazon-adsystem.com/s/wa?o=1&t=pbs-44&l=w13&search-alias=' + cat
		          + '&src=res-srch-wgt-best-seller';
	};
	window.addEventListener('DOMContentLoaded', function(event){
		if(''.length){ // Default Search Load
			var searchField = document.getElementById('searchField');
			searchField.value = '';
			searchField.removeAttribute('class');
			if(result_count && result_count>0)
				document.getElementById('submit').click();
		} else if(result_count && result_count>0) {
			window.submitTimer = setInterval(loadTopSeller, 3000);
			loadTopSeller();
		}
		{
			var img = new Image();
			img.src = location.protocol + '//' + 'ir-na.amazon-adsystem.com/e/ir?o=1&t=pbs-44&l=w13';
          	var img2 = new Image();
			img2.src = location.protocol + '//' + 'ir-na.amazon-adsystem.com/s/wa?o=1&t=pbs-44&l=w13&src=res-search-wgt'
			          + '&height=252&width=1568';
		}
	}, false);
	{ // Hide the category and suggestion when clicked anywhere in body 
		var isFromSameDiv = function(node, target) {
			if(node==target) return true;
			while(target.parentNode) {
				target = target.parentNode;
				if(node==target) return true;		
			}
			return false;			
		};
		var isIOS = (navigator.userAgent.match(/iPad|iPhone|iPod/i)) ? true : false;
		document.getElementById('container').addEventListener('click', function(event){
			if(event.target && !isFromSameDiv(document.getElementById('categoryHolder'), event.target)) {
				if(window.hideCategory)
					window.hideCategory();
				if(isIOS && !isFromSameDiv(document.getElementById('searchField'), event.target)) document.getElementById('amazonLogo').focus();
			}
			postToParent({
				'widgetId': widgetId,
				'operation': 'hideSuggestion'
			});
		});
	}
	{
		var resultPane = document.getElementById('searchResult');
		window.improviseTitle = function(){
			var titles = resultPane.getElementsByClassName('title');
			if(titles && titles.length) {
				var width = 2 * (titles[0].clientWidth ? titles[0].clientWidth : titles[0].scrollWidth);
				var widthPerChar = 6;
				var totalNumberOfChar = Math.floor(width/widthPerChar);
				for(var i=0; i<titles.length; i++) {
					var anchorTag = titles[i].getElementsByTagName('a')[0];
					var title = anchorTag.getAttribute('data-title');
					if(title.length >= totalNumberOfChar) 
						title = title.substring(0, totalNumberOfChar-2) + '&hellip;';
					anchorTag.innerHTML = title;
				}
			}
		};
	}
	{
		window.parseCommand = function(command) {
			command = command.trim();
			if(command.indexOf("search ")!=-1) {
				var index = command.indexOf("search ") + 7;
				command = command.substring(index).trim();
				if(command.indexOf(' in ') == -1) {
					document.getElementById('searchField').value = command;
					document.getElementById('submit').click();
				} else {
					var index = command.indexOf(' in ');
					var searchKey = command.substring(0, index);
					var category = command.substring(index+4);
					document.getElementById('searchField').value = searchKey;
					postToParent({
						'widgetId': widgetId,
						'operation': 'findCategory',
						'key': category.trim()
					});
				}
			} else if(command.trim()=="reset" || command.trim()=="clear"){
				document.getElementById('searchField').value = '';
				document.getElementById('searchResult').style.display = 'none';
				document.getElementById('footer').style.display = 'none';
			} else if(command.indexOf("category ")!=-1) {
				command = command.substring(command.indexOf("category")+9).trim();
				postToParent({
					'widgetId': widgetId,
					'operation': 'findCategory',
					'key': command.trim()
				});
			} else if(command.indexOf("open ")==0) {
				var openStr = command.substring(command.indexOf("open")+4).trim();
				var openIndex = null;
				if(openStr=="one" || openStr=="first")
					openIndex = 0;
				else if(openStr=="two" || openStr=="second")
					openIndex = 1;
				else if(openStr=="third")
					openIndex = 2;
				else {
					openIndex = parseInt(openStr);
					if(isNaN(openIndex))
						return;
					else
						openIndex--;
				}
				if(openIndex!=null){
					var results = document.querySelectorAll("#searchResult .__ma-sw-search-item .details-holder p.title > a");
					window.open(results[openIndex].getAttribute('href'));
				}
			} else if(document.getElementById('youMean').style.display!='none' && (command=="yes" || command=="no")) {
				document.getElementById(command).click();
			}
		};
	}
	{
		document.getElementById('yes').onclick = function(event){
			event.preventDefault();
			document.getElementById('youMean').style['display'] = 'none';
			document.getElementById('searchField').value = document.querySelector('#youMean > p > span').innerHTML;
			document.getElementById('submit').click();
		};
		document.getElementById('no').onclick = function(event){
			event.preventDefault();
			document.getElementById('youMean').style['display'] = 'none';
		};
	}
	{ // Speech recognition code
		var micAnchor = document.getElementById('mic'); 
		var micImage = micAnchor.getElementsByTagName('img')[0]; 
		var recognition = null;
		if(('webkitSpeechRecognition' in window)) {
			micAnchor.onclick = function(event){
				event.preventDefault();
				if(micImage.getAttribute('src').indexOf('mic.gif')!=-1) {
					recognition = new webkitSpeechRecognition();
					recognition.continuous = true;
					recognition.interimResults = false;
					recognition.lang = "en";
					recognition.onstart = function(){
						micImage.setAttribute('src', 'images/mic-animate.gif');	
					};
					recognition.start();
					recognition.onresult = function (event) {
						for (var i = event.resultIndex; i < event.results.length; ++i) {
					        if (event.results[i].isFinal) {
					            console.log("Accepted Command-->", event.results[i][0].transcript);
					            window.parseCommand(event.results[i][0].transcript.toLowerCase());     
					        } else {
					            // Outputting the interim result to the text field and adding
					            // an interim result marker - 0-length space
					            console.log("Not final result ->", event.results[i][0].transcript + '\u200B');
					        }
					    }
					};
					recognition.onerror = function (event) {
						if(event.error=="not-allowed") {
							micImage.setAttribute('src', 'images/mic-slash.gif');			
						} else if(event.error=="no-speech") {
							micImage.setAttribute('src', 'images/mic.gif');			
						} else {
							micImage.setAttribute('src', 'images/mic.gif');							
							console.error(event);
						}
					};
				} else {
					if(recognition) {
						recognition.stop();
						micImage.setAttribute('src', 'images/mic.gif');
					}					
				}
			};
		} else {
			micImage.setAttribute('src', 'images/mic-slash.gif');
		}
	}
}());
window.search_callback = function(result) {
	if(window.submitTimer) {
		clearTimeout(window.submitTimer);
		delete window.submitTimer;
	}
	var findDisplacement = function(ratings){
		if(ratings && ratings.length) {
			var intVal = parseInt(ratings);
			var floatVal = parseFloat(ratings);
			if(intVal == floatVal) {
				return 20 + ((5-intVal)*16);				
			} else {
				return 191 + ((4-intVal)*15);
			}
		}
		return 100;
	};
	var roundToHundred = function(num) {
		var intVal = parseInt(num);
		if(isNaN(intVal) || intVal<10)
			return "";
		else if(num.length==2)
			return num.substring(0,1) + "0+ ";
		else if(num.length>=3 && num.length<=5)
			return num.substring(0, num.length-2) + "00+ ";
		else if(num.length>5)
			return num.substring(0, num.length-3) + "000+ ";
		return "";
	};
	var resultPane = document.getElementById('searchResult');
	resultPane.innerHTML = '';
	if(result && result.results) 
		for(var i=0; i<result.results.length; i++) {
			var data = result.results[i];
			var template = document.getElementById('resultTemplate').getElementsByClassName('__ma-sw-search-item')[0].cloneNode(true);
			var href = data.DetailPageURL+'?tag=pbs-44&linkCode=w13&linkID=junkLinkID&ref_=assoc_res_sw_result_'+(i+1);
			template.getElementsByClassName('thumbnail')[0].setAttribute('href', href);
			var thumb = template.getElementsByClassName('thumbnail')[0].getElementsByTagName('img')[0];
			if(data.ImageUrl)
				thumb.setAttribute('src', data.ImageUrl);
			else
				thumb.setAttribute('src', location.protocol + '//' + 'g-ecx.images-amazon.com/images/G/01/x-site/icons/no-img-lg._V192198674_.gif');
			var title = template.getElementsByClassName('title')[0].getElementsByTagName('a')[0]; 
			title.setAttribute('href', href);
			title.setAttribute('data-title', data.Title);
			title.innerHTML = data.Title;
			template.getElementsByClassName('price')[0].innerHTML = data.Price;
			if(!(data.IsPrimeEligible && data.IsPrimeEligible=="1"))
				template.getElementsByClassName('amazon-prime')[0].removeAttribute('class');
			if(data.TotalReviews)
				template.getElementsByClassName('reviews-count')[0].innerHTML = '('+data.TotalReviews+')';
			
			var starRating = template.getElementsByClassName('star-rating')[0]; 
			if(data.Rating)
				starRating.style.backgroundPosition = '-'+ findDisplacement(data.Rating) +'px -31px';
			else
				starRating.style.display = 'none';
				
			var reviewURL = 'http://www.amazon.com' + '/product-reviews/' + data.ASIN + '/ref=assoc_res_sw_ratings?tag=pbs-44&linkCode=w13&linkID=junkLinkID';
			template.getElementsByClassName('star-rating-link')[0].setAttribute('href', reviewURL);
			template.getElementsByClassName('reviews-count-link')[0].setAttribute('href', reviewURL);
			resultPane.appendChild(template);
		}
	var clearDiv = document.createElement('div');
	clearDiv.style['clear'] = 'both';
	resultPane.appendChild(clearDiv);
	
	var footer = document.getElementById('footer');
	var anchor = footer.getElementsByTagName('a');
	if(typeof result.NumRecords == "undefined")
		anchor[0].innerHTML = "View all top seller on <b>Amazon.com</b>";
	else
		anchor[0].innerHTML = "View all " + roundToHundred(result.NumRecords) + "results on <b>Amazon.com</b>";
	
	document.getElementById('loader').style['display'] = 'none';
	resultPane.style['display'] = 'block';
	footer.style['display'] = 'block';
	if(result.NumRecords=="0") {
		anchor[0].style['display']='none';
		anchor[1].style['display']='block';
		resultPane.style['display'] = 'none';
	} else {
		anchor[1].style['display']='none';
		anchor[0].style['display']='block';
	}
	if(typeof improviseTitle == "function") improviseTitle();
	resizeFrame();
	resultPane.scrollTop = 0;
	if(window.__maSwJsNode2)
		if(window.__maSwJsNode2.parentNode){
			window.__maSwJsNode2.parentNode.removeChild(window.__maSwJsNode2);
			//console.log('Removing already used js2 nodes.'); // IE9 fix
		}
	delete window.__maSwJsNode2;
};
window.__maSwAutocomplete = function(){
	if(!completion) return;
	
	var data = completion;
	var searchField = document.getElementById('searchField');
	if(searchField.value && data[0]==searchField.value.toLowerCase() && !window.isSearchStarted) {
		if(data[1] && data[1].length) {
			postToParent({
				'widgetId': widgetId,
				'operation': 'showSuggestion',
				'suggestion': data[1],
				'category': data[2]
			});
		}
	}
	if(window.__maSwJsNode)
		if(window.__maSwJsNode.parentNode){
			window.__maSwJsNode.parentNode.removeChild(window.__maSwJsNode);
			//console.log('Removing already used js nodes.'); // IE9 fix
		}
	delete window.__maSwJsNode;
};
</script>
<!--[if lte IE 9]>
  <script type="text/javascript">
    (function(){
    	var searchField = document.getElementById('searchField');
    	var putPlaceHolder = function(){
    		if(searchField.value.length==0) {
    			searchField.value = searchField.getAttribute('placeholder');
    			searchField.setAttribute('class', 'placeholder');
    		}
    	};
    	var resetPlaceHolder = function(){
    		if(searchField.getAttribute('class')=='placeholder') {
    			searchField.value = '';
    			searchField.setAttribute('class', '');
    		}
    	};
    	putPlaceHolder();
    	searchField.addEventListener('focus', function(event){
    		resetPlaceHolder();
    	});
    	searchField.addEventListener('blur', function(event){
    		putPlaceHolder();
    	});
    }());
  </script>
<![endif]-->
</body>
</html>