$(function () {
	// 회원가입 form태그에 있는 input을 focus했을 때 css의 opcity가 0이었던걸 0.1초만에
	// 1로 바꾸고 focus가 out되면 다시 0.3초만에 css의 opacity가 0이 되는 제이쿼리 구문
	$("#joinForm input").focus(function () {
		$(this).animate({ opacity: 1 }, 100);
	}).focusout(function () {
		if ($(this).val().trim() === '') {
			$(this).animate({ opacity: 0 }, 300);
		}
	});
	


	// 아이디를 입력할 때마다 실시간으로 DB에 있는 userid를 검사해서 밑에 text나오게 하는 구문
	$('#email').on('input', checkUserId);

	$('.emailButton').on('click', emailSecurity);

	$('.emailSecurity').on('click', emailCodeCheck);

	// 비밀번호를 입력할 때마다 실시간으로 비밀번호를 알맞게 썼는지 검사해서 밑에 text나오게 하는 구문
	$('#userPwd').on('input', pwdCheck);

	// 비밀번호 확인에 입력할 때마다 비밀번호 입력한 값을 받아서 같은지 확인하는 구문
	$('#pwd_check').on('input', pwdDoubleCheck);

	// "전체 선택" 체크박스의 상태가 변경되었을 때
	$('#all_check').change(function () {
		// "전체 선택" 체크박스의 상태에 따라 다른 체크박스 상태 변경
		var isChecked = $(this).is(':checked');
		$('#playmall, #userinfo').prop('checked', isChecked);
	});

	// 개별 체크박스의 상태가 변경되었을 때
	$('#playmall, #userinfo').change(function () {
		// 개별 체크박스 상태에 따라 "전체 선택" 체크박스 상태 변경
		var playmallChecked = $('#playmall').is(':checked');
		var userinfoChecked = $('#userinfo').is(':checked');
		$('#all_check').prop('checked', playmallChecked && userinfoChecked);
	});
	// "전체 선택" 체크박스의 상태가 변경되었을 때
	$('.all_agree').change(function () {
		// "전체 선택" 체크박스의 상태 가져오기
		var isChecked = $(this).is(':checked');
		// 모든 "전체 선택" 체크박스의 상태 변경
		$('.all_agree').prop('checked', isChecked);
	});
});

// 아이디 ajax로 사용가능한지 체크
function checkUserId() {
	const email = $('#email').val();
	const userEmailCheckElement = $('.emailCheck');
	var userEmailPatternEnd = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;


	// Promise 객체를 반환합니다.
	return new Promise(function (resolve, reject) {
		$.ajax({
			type: 'get',
			url: '/shard/idCheck',
			data: { email: email },
			dataType : 'json',
			success: function (response) {
				var result = response.result;
				console.log(result);
				if (result === 0) { // 데이터베이스에 사용중인 아이디가 없을 때
					if (userEmailPatternEnd.test(email)) {
						userEmailCheckElement.text("사용 가능한 이메일입니다.").css({ color: "#000" });
						resolve(true); // Promise를 성공 상태로 처리
					} else if (!(userEmailPatternEnd.test(email))) {
						userEmailCheckElement.text("올바른 이메일 형식을 작성해주세요").css({ color: "#0095ff" });
						resolve(false); // Promise를 실패 상태로 처리
					}
				} else {
					userEmailCheckElement.text("사용 중인 이메일입니다.").css({ color: "#0095ff" });
					resolve(false); // Promise를 실패 상태로 처리
				}
			},
			error: function () {
				// 에러 처리
				console.log("에러 발생");
			}
		});
	});
}

var code = "";
// 이메일 인증을 위한 번호 전송
function emailSecurity() {
	var email = $("#email").val();
	var emailCheck = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;

	if (email != "") {
		if (emailCheck.test(email)) {
			$.ajax({
				type: 'get',
				url: '/shard/emailSecurity?email=' + email,
				success: function (data) {
					console.log(data);
					code = data;
					alert("인증번호가 전송되었습니다.");
					return false;
				},

				error: function () {
					console.log("에러에러에러");
				}
			});
		} else {
			alert("올바른 이메일 형식을 입력해주세요");
			return false;
		}
	} else {
		alert("이메일을 입력해주세요");
		return false;
	}
}

// 이메일 인증 번호 체크 -> 맞으면 true | 틀리면 fasle
function emailCodeCheck() {
	var inputCode = $('#emailSecurity').val();
	var emailText = $('.emailSecurityText');
	emailText.css("fontSize", "13px");
	var check4 = false;


	if (inputCode != "") {
		if (inputCode === code) {
			emailText.text("인증번호가 일치합니다.");
			emailText.css("color", "#333");
			check4 = true;
		} else {
			emailText.text("인증번호가 불일치 합니다. 다시 확인해주세요");
			emailText.css("color", "red");
		}
	} else {
		emailText.text("인정번호를 입력해주세요");
	}

	return check4;
}

// 비밀번호 체크
function pwdCheck() {
	var pwd_check = /^(?=.*[a-z\d].*[a-z\d].*[a-z\d])[a-z\d]{10,}|^(?=.*[a-zA-Z\d@#$%^&+=!])[a-zA-Z\d@#$%^&+=!]{8,16}$/;
	var pwd = $('#userPwd').val();
	var pwdCheckSpan1 = $('.pwd_check1');
	pwdCheckSpan1.css({ color: "#0080ff" });
	var check = false;

	if (pwd_check.test(pwd)) {
		pwdCheckSpan1.text("정상입력 입니다.").css({ color: "#000" });
		check = true;
	} else {
		pwdCheckSpan1.text("비밀번호는 영문 대소문자/숫자/특수문자를 2종류 이상 혼용하여 10~16자 또는 3종류 이상 혼용하여 8~16자로 설정하셔야 합니다.");
		check = false;
	}

	if (pwd == "") {
		pwdCheckSpan1.text("비밀번호를 입력해주세요");
		check = false;
	}

	return check;
}

// 비밀번호 확인 체크
function pwdDoubleCheck() {
	var pwd = $('#userPwd').val();
	var doublePwd = $('#pwd_check').val();
	var pwdCheckSpan2 = $('.pwd_check2');
	var pwdDoubleCheck = false;

	if (pwd == doublePwd) {
		pwdCheckSpan2.text("비밀번호가 일치합니다.").css({ color: "#000" });
		pwdDoubleCheck = true;
	} else {
		pwdCheckSpan2.text("비밀번호가 일치하지 않습니다.").css({ color: "#0080ff" });
		return;
	}

	return pwdDoubleCheck;
}



// 주소가 정상적으로 입력되었는지 체크
function addressCheck() {
	var zipCode = $('#postcode').val();
	var userAddr = $("#roadAddress").val();
	var detailAddr = $("#detailAddress").val();

	if (!zipCode || !userAddr || !detailAddr) {
		console.log("찍혀");
		return false;
	} else {
		return true;
	}
}

function birthCheck() {
	var year = $("#birthYear").val();
	var month = $("#birthMonth").val();
	var day = $("#birthDay").val();
	var check = false;

	if (year == "" || month == "" || day == "") {
		check = false;
	} else {
		check = true;
	}

	return check;
}

function genderCheck() {
	var gender = $('.gender').is(":checked");
	var check6 = false;

	if (gender) {
		check6 = true;
	} else {
		return false;
	}

	return check6;
}

function phoneCheck(){ 
	var phone = $('#phone').val();
	var check = false;

	if(phone == ""){
		return false;
	}else {
		check = true;
	}

	return check;
}

function formSubmit() {
	checkUserId().then(function (checkId) {
		var checkPwd = pwdCheck(); // 패스워드 체크
		var checkInfo = $('.all_agree').is(":checked"); // 개인정보 동의 체크
		var playMall = $('#playmall').is(":checked");
		var userinfo = $('#userinfo').is(":checked");
		var checkEmailSe = emailCodeCheck(); // 이메일 인증 체크
		var checkAddress = addressCheck(); // 주소 체크
		var checkGender = genderCheck(); // 성별 체크
		var checkName = $('#userName').val() == "" ? false : true; // 이름 체크
		var checkBirth = birthCheck(); // 생일 체크
		var checkPhone = phoneCheck(); // 휴대폰 번호 체크
		var pwdMoreCheck = pwdDoubleCheck();

		// 모든 인증이 다 들어가야 한다.

		if (checkName) {
			if (checkId) {
				if (checkEmailSe) {
					if (checkPwd) {
						if(pwdMoreCheck){
							if (checkAddress) {
								if (checkBirth) {
									if (checkGender) {
										if(checkPhone){
											if(checkInfo){
												if(playMall){
													if(userinfo){
														$('#joinForm').submit();
													}else {
														alert("개인정보 수집·이용 동의가 필요합니다.");
														$('#userinfo').focus();
														return false;
													}
												}else {
													alert("이용약관 동의는 필수입니다.");
													$('#playmall').focus();
													return false;
												}
											}else {
												alert("개인정보 수집·이용 동의가 필요합니다.");
												$('.all_agree').focus();
												return false;
											}
										}else {
											alert("휴대폰 번호는 필수입력입니다.")
											$('#phone').focus();
											return false;
										}
									} else {
										alert("성별은 필수 선택입니다.");
										$("#gender").focus();
										return false;
									}
								} else {
									alert("회원님의 생년월일 입력은 필수입니다.");
									return false;
								}
							} else {
								alert("회원님의 주소는 필수입력입니다.");
								return false;
							}
						}else {
							alert("비밀번호 확인을 해주세요");
							$('#pwd_check').focus();
							return false;
						}
					} else {
						alert("비밀번호를 입력해주세요");
						$("#userPwd").focus();
						return false;
					}
				} else {
					alert("이메일 인증을 진행해주세요");
					$('.emailButton').focus();
					return false;
				}
			} else {
				alert("이메일을 입력해주세요");
				$('#email').focus();
				return false;
			}
		} else {
			alert("회원님의 이름을 필수입력입니다.");
			$("#userName").focus();
			return false;
		}
	});
}