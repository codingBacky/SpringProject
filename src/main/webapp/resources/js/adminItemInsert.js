function itemInsert() {
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
        $('#itemInsertForm').submit();
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
