$(document).ready(function() {
	$("#update").click(function() {
		var qna_id = $("#qna_id").val();
		var query = {
			qna_content: $("#uRContent").val(),
			qna_id: $("#qna_id").val()
		};
		$.ajax({
			type: "POST",
			url: "/shoppingmall/mg/qnaReplyUpdatePro.do",
			data: query,
			success: function(data) {
				window.location.href = "/shoppingmall/mg/qnaList.do";
			}
		});
	});
	$("#cancel").click(function() {
		window.location.href = "/shoppingmall/mg/qnaList.do";
	});
});