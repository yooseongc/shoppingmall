$(document).ready(function(){
	$("#conShopping").click(function(){ //[쇼핑계속] 버튼 클릭
		window.location.href="/shoppingmall/list.do?book_kind=all";
	});
	
	$("#shopMain").click(function(){ //[메인으로] 버튼 클릭
		window.location.href="shoppingmall/index.do";
	});
});