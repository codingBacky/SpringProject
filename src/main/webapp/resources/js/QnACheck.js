function QnACheck() {
    // 오류 메시지 초기화
	if ($('#replyPwd').val() == "") {
		alert("비밀번호는 필수입력 사항입니다.");
        $('#replyPwd').focus();
        return false;
    }    

    if ($('#replyTitle').val() == "") {
    	alert("제목은 필수입력 사항입니다.");
        $('#replyTitle').focus();
        return false;
    }

    if ($('#replyCategory').val() == "") {
        alert("카테고리은 필수입력 사항입니다.");
        $('#replyCategory').focus();
        return false;
    }

    if ($('#replyContent').val() == "") {
    	alert("내용은 필수입력 사항입니다.");
        $('#content').focus();
        return false;
    }
    
    var form = document.getElementById("QnAForm");
    var csrfParameter = "${_csrf.parameterName}";
    var csrfToken = "${_csrf.token}";

    // CSRF 토큰을 폼 데이터에 추가
    var csrfInput = document.createElement("input");
    csrfInput.setAttribute("type", "hidden");
    csrfInput.setAttribute("name", csrfParameter);
    csrfInput.setAttribute("value", csrfToken);
    form.appendChild(csrfInput);

    // 폼 전송
    form.submit();
}


function QnAEnswerCheck(){
    var enswer = $("#enswerContent").val();

    if(enswer == ""){
        alert("문의 사항은 필수 입력입니다.");
        return false;
    }else {
        $("#enswerForm").submit();
    }
}

function QnAReFormCheck(){
    var reply = $("#replyContent").val();

    if(reply == ""){
        alert("문의 사항은 필수 입력입니다.");
        return false;
    }else {
        $("#QnAReForm").submit();
    }
}

function itemInsertImg(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
            var imgSrc = e.target.result;

            // 부모 엘리먼트에 이미지 추가
            $(input).parent().append("<img src='" + imgSrc + "' width='100' height='100' style='float:right'/>");
        };
        reader.readAsDataURL(input.files[0]);
    }
}