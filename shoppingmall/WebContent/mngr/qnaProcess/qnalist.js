$(document).ready(function() {
	$("#bookMain").click(function() {
		window.location.href = "/shoppingmall/mg/managerMain.do";
	});
});

function reply(replyBtn) {
	var rStr = replyBtn.name;
	var query = "/shoppingmall/mg/qnaReplyForm.do?qna_id=" + rStr;
	window.location.href = query;
}

function edit(editBtn) {
	var rStr = editBtn.name;
	var query = "/shoppingmall/mg/qnaReplyUpdateForm.do?qna_id=" + rStr;
	window.location.href = query;
}