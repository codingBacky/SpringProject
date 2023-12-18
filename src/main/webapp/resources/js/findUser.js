var code = "";

function findUser() {
	var email = $('#email').val();


	if (email != "") {
		$.ajax({
			type: 'get',
			url: '/shard/findPwd?email=' + email,
			success: function (data) {
				if (data == "noUser") {
					alert("가입된 이메일이 없습니다.");
					location.href = '/shard/join';
				}else if(data == "kakaoUser") {
					alert("카카오회원은 비밀번호 찾기를 이용할 수 없습니다.");
					location.href = '/shard/';
				} else {
					$('#findPwdCode').stop().show();
					$('#PwdCodeBtn').stop().show();
					console.log(data);
					code = data;
					alert("인증번호가 전송되었습니다.");
					return false;
				}
			},

			error: function () {
				console.log("에러에러에러");
			}
		});
	} else {
		alert("이메일을 입력해주세요");
		return false;
	}
}

//이메일 인증 번호 체크 -> 맞으면 true | 틀리면 fasle
function finePwdCodeCheck() {
	var inputCode = $('#findPwdCode').val();
	var check4 = false;


	if (inputCode != "") {
		if (inputCode === code) {
			alert("인증번호가 일치합니다.");
			$("#changePwd").submit();
		} else {
			alert("인증번호가 불일치 합니다. 다시 확인해주세요");
		}
	} else {
		emailText.text("인정번호를 입력해주세요");
	}

	return check4;
}

//비밀번호 확인 체크
function pwdDoubleCheck() {
	var pwd = $('#userPwd').val();
	var doublePwd = $('#userPwdCheck').val();

	if (pwd == doublePwd) {
		alert("비밀번호가 일치합니다.");
		$('#updatePwd').submit();
	} else {
		alert("비밀번호가 일치하지 않습니다.");
		return; 
	}
}