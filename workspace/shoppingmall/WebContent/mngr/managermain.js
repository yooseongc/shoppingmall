var status = true;

$(document).ready(function() {
	$("#registProduct").click(function() {
		window.location.href = "/shoppingmall/mg/bookRegisterForm.do";
	});
	$("#updateProduct").click(function() {
		window.location.href = "/shoppingmall/mg/bookList.do?book_kind=all";
	});
	$("#orderedProduct").click(function() {
		window.location.href = "/shoppingmall/mg/orderList.do";
	});
	$("#qna").click(function() {
		window.location.href = "/shoppingmall/mg/qnaList.do";
	});
});