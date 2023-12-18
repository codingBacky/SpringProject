$(function(){
	$('.logoutBtn').click(function(e){
		e.preventDefault();
		$('#logoutForm').submit();
	});

	$('.adminDeleteUser').click(function(e){
		e.preventDefault();

		if(confirm("정말 회원을 삭제하시겠습니까?")){
			var email = $(this).attr("href");

			$('#deleteUser').append("<input type='hidden' name='email' value="+ email +" />");
			$("#deleteUser").submit();
		}
	});
	
	$('.getNotice').click(function(e){
		e.preventDefault();
		var noticeNum = $(this).attr("href");
		
		$('#noticeGet').append("<input type='hidden' name='noticeNum' value='"+noticeNum+"' /> ");
		$('#noticeGet').submit();
	});
});

function NoticeCheck() {
	if ($('#noticeTitle').val() == "") {
    	alert("제목은 필수입력 사항입니다.");
        $('#noticeTitle').focus();
        return false;
    }
	
	if ($('#noticeContent').val() == "") {
    	alert("내용은 필수입력 사항입니다.");
        $('#noticeContent').focus();
        return false;
    }
	
	$('#noticeInsert').submit();
}

function deleteNotice(){
	if(confirm("정말 공지사항을 삭제하시겠습니까?")){
		$('#noticeDeleteForm').submit();
	}
}

function noticeInsertImg(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
            var imgSrc = e.target.result;

            // 형제 엘리먼트에 이미지 추가
			$(input).parent().append("<img src='" + imgSrc + "' width='300' height='100' style='float:right'/>");
        };
        reader.readAsDataURL(input.files[0]);
    }
}

function noticeUpdateImg(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
            var imgSrc = e.target.result;

            // 형제 엘리먼트에 이미지 추가
            $(input).siblings("img").attr("src", imgSrc);
        };
        reader.readAsDataURL(input.files[0]);
    }
}



