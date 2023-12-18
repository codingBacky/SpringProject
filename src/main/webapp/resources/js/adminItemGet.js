function itemUpdateSubmit() {
    var itemName = $('#itemName').val();
    var sale = $('#sale').val();
    var countM = $('#itemCountM').val();
    var countL = $('#itemCountL').val();
    var countXL = $('#itemCountXL').val();
    var category = $('#categoryNum').val();
    var mainImg = $('#mainImg')[0].files.length;
    var subImg1 = $('#subImg1')[0].files.length;
    var subImg2 = $('#subImg2')[0].files.length;
    var subImg3 = $('#subImg3')[0].files.length;
    var subImg4 = $('#subImg4')[0].files.length;

    if (itemName == "" || sale == "" || countM == "" || countL == "" || countXL == "" || category== "") {
        alert("상품 정보를 정확히 입력해주세요");
    } else if (mainImg === 0) {
        alert("메인 이미지는 반드시 등록해야합니다.");
    } else if (subImg1 === 0 || subImg2 === 0 || subImg3 === 0 || subImg4 === 0) {
        alert("서브 이미지는 반드시 등록해야합니다.");
    } else {
        $('#itemGet').submit();
    }
}

function itemDelete() {
	var form = $('#itemGet').attr("action");
	console.log(form);
	if(confirm("정말 상품을 삭제하시겠습니까?")) {
	    // 새로운 액션 설정
	    $("#itemGet").attr("action", "/admin/deleteItem");
	    $("#itemGet").attr("method", "get");
	    
	    // form 제출
	    $("#itemGet").submit();
	}
}

function itemUpdate() {
	$("input[type=text]").removeAttr("readonly");
	$("input[type=file]").removeAttr("disabled");
	$('.updateStart').stop().hide();
	$('.updateEnd').stop().show();
}


function itemInsertImg(input) {
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
