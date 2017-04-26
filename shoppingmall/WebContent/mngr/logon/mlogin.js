$(document).ready(function() {
	$("#login").click(function() {
		var query = { 
			id: $("#id").val(),
			passwd: $("#passwd").val()
		};
		$.ajax({
			type: "POST",
			url: "/shoppingmall/mg/managerLoginPro.do",
			data: query,
			success: function(data) {
				window.location.href = "/shoppingmall/mg/managerMain.do";
			}
		});
	});
	$("#logout").click(function() {
		$.ajax({
			type: "POST",
			url: "/shoppingmall/mg/managerLogout.do",
			success: function(data) {
				window.location.href = "/shoppingmall/mg/managerMain.do";
			}
		});
	});
});