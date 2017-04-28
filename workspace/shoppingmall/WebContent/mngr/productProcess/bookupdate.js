$(document).ready(function() {
	$("#upForm1").ajaxForm({
		success: function(data, status) {
			window.location.href = "/shoppingmall/mg/bookList.do?book_kind=all";
		}
	});
	$("#bookMain").click(function() {
		window.location.href = "/shoppingmall/mg/managerMain.do";
	});
	$("#bookList").click(function() {
		window.location.href = "/shoppingmall/mg/bookList.do?book_kind=all";
	});
});