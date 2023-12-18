function passCheck() {
	if (document.frm.pass.value.length == 0) {
		alert("비밀번호를 입력하세요.");
		return false;
	}
	return true;
}

function replyCheck() {
	if ($('#replyContent').val() == "") {
		alert("답글은 무조건 입력해야합니다.");
		$('#replyContent').focus();
		return false;
	}
	return true;
}

// 문의 보기
$(function () {
	$('.getReply').click(function (e) {
		e.preventDefault();
		var replyNum = $(this).attr("href");

		$('#getReplyForm').append("<input type='hidden' name='replyNum' value=" + replyNum + " />");
		$('#getReplyForm').submit();
	});
});

function deleteQnA() {
	if (confirm("정말 등록하신 문의를 삭제하시겠습니까?")) {
		$('#QnADeleteForm').submit();
	}
}