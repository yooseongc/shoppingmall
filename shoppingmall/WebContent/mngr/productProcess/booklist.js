$(document).ready(function() {
	$("#regist").click(function() {
		window.location.href = "/shoppingmall/mg/bookRegisterForm.do";
	});
	$("#bookMain").click(function() {
		window.location.href = "/shoppingmall/mg/managerMain.do";
	});
});

function edit(editBtn) {
	var rStr = editBtn.name;
	var arr = rStr.split(",");
	var query = "/shoppingmall/mg/bookUpdateForm.do?book_id=" + arr[0];
	query += "&book_kind=" + arr[1];
	window.location.href = query;
}

function del(delBtn) {
	var rStr = delBtn.name;
	var arr = rStr.split(",");
	var query = "/shoppingmall/mg/bookDeletePro.do?book_id" + arr[0];
	query += "&book_kind" + arr[1];
	window.location.href = query;
}