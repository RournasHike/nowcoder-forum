$(function(){
	$("#sendBtn").click(send_letter);
	$(".close").click(delete_msg);
});

function send_letter() {
	$("#sendModal").modal("hide");

	let toName = $("#recipient-name").val();
	let messageContent = $("#message-text").val();

	$.post(PROJECT_ROOT + "/message/letter/send",
		{"toName":toName,"content":messageContent},
		function (data) {
			data = $.parseJSON(data);
			if(data.code == 0){
				$("#hintBody").text("发送成功");
			}else{
				$("#hintBody").text(data.message);
			}

			$("#hintModal").modal("show");
			setTimeout(function(){
				$("#hintModal").modal("hide");
			}, 2000);
			window.location.reload();
		}
	 );


}

function delete_msg() {
	// TODO 删除数据
	$(this).parents(".media").remove();
}