function updateUser() {
	if(confirm("회원 정보를 수정하시겠습니까?")) {
	    var userName = $('#userName').val();
	    var userPwd = $('#userPwd').val();
	    var pwdCheck = $('#pwdCheck').val();
	    
	    if(userPwd != ""){
		    if(userPwd === pwdCheck){
		    	$('#updateUser').submit();
		    }else {
		    	alert("비밀번호가 맞지 않습니다.");
		    }
	    }else {
			alert("비밀번호를 입력해주세요");
			return;
		}
	}
}

function update() {
	console.log("함수 실행");
	$("input[type=text]").removeAttr("readonly");
	$("input[type=text]").removeAttr("disabled");
	$("input[type=password]").removeAttr("readonly");
	$('#email').attr("readonly", "readonly"); // This line adds the readonly attribute
	$('#birth').attr("readonly", "readonly"); // This line adds the readonly attribute
	$('.update').stop().hide();
	$('.updateUser').stop().show();
}