function createHTML(data) {
	var iconPath = data.icon.substring(0, data.icon.lastIndexOf('.')-7)+"._SL160_"+data.icon.substring(data.icon.lastIndexOf('.'), data.icon.length);
	
	var html = "<p><b>"+data.title+"</b></p>"
	+"<div style='display:block; width: 400px; height:480px; overflow:hidden;'>"
	+"<img src='"+iconPath+"' style='float:left; margin: 0 5px 5px 0;' />"
	+"<span>"+data.asinDesc+"</span>"
	+"</div>";
	return html;
}

function getCumulativeOffset(obj) {
    var left, top;
    left = top = 0;
    if (obj.offsetParent) {
        do {
            left += obj.offsetLeft;
            top  += obj.offsetTop;
        } while (obj = obj.offsetParent);
    }
    return {
        x : left,
        y : top
    };
};

function listener(event){
	if(event.data.asinDesc) {
		var content = document.getElementById('__asinDesc');
		if(content)
			content.parentNode.removeChild(content);
		
		content = document.createElement('div');
		content.id='__asinDesc';
		
		content.style.position='absolute';
		var iframe = getCumulativeOffset(document.getElementById('__amznWishList'));
		console.log(iframe.x+", "+iframe.y);
		
		var divLeft = (iframe.x - 420); if(divLeft<0) divLeft=iframe.x+365;
		var divtop = iframe.y - 50; if(divtop<0) divtop = 0;
		content.style.top = divtop +'px';
		content.style.left = divLeft+'px';
		
		content.style.background = '#FFFFFF';
		content.style.display = 'block';
		content.style.width = '400px';
		content.style.height = '600px';
		content.style.border = '1px solid #CCC';
		content.style.borderRadius = '0.5em';
		content.style.boxShadow = '0 0 3px rgba(0, 0, 0, 0.5)';
		content.style.padding = '10px';
		content.style.margin = '0';
		content.style.zIndex = 1000;
		
		html = createHTML(event.data);
		
		content.innerHTML = html;

		document.getElementsByTagName('body')[0].appendChild(content);
	} else if(event.data.error || event.data.mouseout) {
		var content = document.getElementById('__asinDesc');
		if(content)
			content.parentNode.removeChild(content);
	}
}


if (window.addEventListener) {
	addEventListener("message", listener, false);
} else {
	attachEvent("onmessage", listener);
}