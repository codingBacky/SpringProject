$(function () {
	$('.buttonLogin').click(function(e) {
		e.preventDefault();
		if (loginCheck()) {
			$('#loginForm').submit();
		}
	});

	function loginCheck() {
		if ($('#email').val() == "") {
			alert("이메일을 입력해주세요");
			$('#email').focus();
			return false;
		}
		
		if ($('#userPwd').val() == "") {
			alert("비밀번호는 반드시 입력해야 합니다.");
			$('#userPwd').focus();
			return false;
		}

		return true; // 모든 조건을 통과하면 true 반환
	}
});