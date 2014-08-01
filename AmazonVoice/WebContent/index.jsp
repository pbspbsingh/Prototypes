<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width, user-scalable=no" />
	<meta name="description" content="Demo for Srujan 2014, Amazon internel only" />
	<meta name="keywords" content="amazon, voice, search, demo, srujan" />
	<meta name="author" content="Prashant Bhushan Singh (singhpra)" />
	<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
	<link rel="icon" href="favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" href="layout.css" />
	<title>Amazon Voice Search</title>
</head>
<body>
	<section id="message">
		<p>This browser is currently not supported. Please upgrade your browser to Google Chrome 25 or later.</p>
	</section>
	<div id="container">
		<jsp:include page="main.jsp"></jsp:include>
	</div>
	<script type="text/javascript">
	(function(){
		if(!('webkitSpeechRecognition' in window))
			document.getElementById('message').style.display = 'block';
	}());
	</script>
</body>
</html>