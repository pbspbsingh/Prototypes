<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<!--[if lt IE 7]> <html class="lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]> <html class="lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]> <html class="lt-ie9" lang="en"> <![endif]-->
<!--[if gt IE 8]><!--> <html lang="en"> <!--<![endif]-->
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <title>${title}</title>
  <link rel="stylesheet" href="css/style.css">
  <!--[if lt IE 9]><script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
  <script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
  <script src="${pageContext.request.contextPath}/script/script.jsp"></script>
</head>

<body>
  <section class="widget">
    <div class="widget-heading">
      <h3 class="widget-title">${title}</h3>
      <a href="index.html" class="widget-close"><span class="icon-close">Close</span></a>
      <form>
      	<input type="hidden" id="__context" value="${context}"/>
      	<input type="hidden" id="__domain" value="${domain}"/>
      </form>
    </div>
  
    <ul id="voter-widget" class="downloads" style="display: block; overflow-y:scroll; height:${height}px">
		<c:if test="${not empty asinDetails}">
			<c:forEach items="${asinDetails}" var="prodInfo">
				<li class="download" id="${prodInfo.asin}">
					<c:choose>
						<c:when test="${not empty  prodInfo.count}">
							<div class="voteup">
								<c:set var="upVoteClass" value="upVote" />
								<c:set var="downVoteClass" value="downVote" />
								<c:if test="${not empty alreadyVote[prodInfo.asin]}">
									<c:if test="${alreadyVote[prodInfo.asin]}">
										<c:set var="upVoteClass" value="upVoted disabled" />
										<c:set var="downVoteClass" value="downVote disabled" />
									</c:if>
									<c:if test="${!alreadyVote[prodInfo.asin]}">
										<c:set var="upVoteClass" value="upVote disabled" />
										<c:set var="downVoteClass" value="downVoted disabled" />
									</c:if>
								</c:if>
								<a class="${upVoteClass}" href="#"></a>
								<span class="voteCount">${prodInfo.count}</span>
								<a class="${downVoteClass}" href="#"></a>
							</div>
						</c:when>
						<c:otherwise>
		        			<span class="icon-ai-24 download-icon addToRecommendation"></span>
		        			<div class="voteup" style="display: none">
								<a class="upVote" href="#"></a>
								<span class="voteCount"></span>
								<a class="downVote" href="#"></a>
							</div>
						</c:otherwise>
					</c:choose>
        			<a href="${prodInfo.detailPageURL}" target="_blank"><img src="${prodInfo.iconPath}" style="float:left; padding-right:10px;" /></a>
        			<p class="download-name">${prodInfo.title}</p>
        			<span style="display:block; height:16px; width:66px; background: url('http://d159blje5992j1.cloudfront.net/images/ps-spr-1.png') no-repeat -${prodInfo.rating}px -74px; float:left;"></span>
      				<c:if test="${empty  prodInfo.count}">
      					<img alt="" src="${pageContext.request.contextPath}/img/amazon-recommend.png" style="float: right;"/>
      				</c:if>
      				<div style="clear:both"></div>
      			</li>
			</c:forEach>
		</c:if>      
    </ul>
    <div id="recommend" style="top:0; left:0; display:none;">
    	Add to Recommended List
    </div>
  </section>

</body>
</html>
