<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<script type="text/javascript">
(function(){
	if(!('webkitSpeechRecognition' in window))
		return;
	
	var recognition = new webkitSpeechRecognition();
	recognition.continuous = true;
	recognition.interimResults = false;
	recognition.lang = "en";
	recognition.start();
	recognition.onresult = function (event) {
		for (var i = event.resultIndex; i < event.results.length; ++i) {
	        if (event.results[i].isFinal) {
	            console.log("Final result ->", event.results[i][0].transcript);
	        } else {
	            // Outputting the interim result to the text field and adding
	            // an interim result marker - 0-length space
	            console.log("Not final result ->", event.results[i][0].transcript + '\u200B');
	        }
	    }
	};
	recognition.onerror = function (event) {
		console.error(event);
	};
}());
</script>
</body>
</html>