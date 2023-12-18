var token = "";
var point = 0;
$(function () {
    token = $('#token').val();
    $(".usePoint").click(function () {
        // 입력된 포인트 가져오기
        point = parseInt($("#pointInput").val());
        console.log("입력포인트 >>" + point);
        var customerPoint = $(".customerPoint").text();
        console.log(customerPoint);
        // 포인트가 5000 이상이고 적립되어있는 금액보다 적어야함
        $(".pointAlert").css('color', 'black');

        if (point >= 5000 && point <= customerPoint) {
            var totalPricetest = $("#totalPrice").val();
            var deliveryChargetest = $("#deliveryCharge").val();
            // ','와 '원'을 제거하고 숫자만 남기기
            var totalPriceInt = parseInt(totalPricetest.replace(/,|원/g, ''), 10);
            // ','와 '원'을 제거하고 숫자만 남기기
            var deliveryChargeInt = parseInt(deliveryChargetest.replace(/,|원/g, ''), 10);
            var totalPrice = totalPriceInt + deliveryChargeInt;
            console.log("totalPricetest", totalPricetest);
            console.log("deliveryChargetest", deliveryChargetest);
            //var totalPrice = parseInt(TotalPriceWithShipping.replace(/[^\d]/g, '')); // 숫자가 아닌 문자와 콤마 제거 후 정수로 변환
            console.log("totalPrice", totalPrice);
            if (point <= totalPrice) {
                //100원 단위로 내림
                point = Math.floor(point / 100) * 100;
                console.log("----포인트---");
                $(".pointAlert").remove();
                $(".pointAlert + div").remove();
                handleDiscountedAmount(point);

            } else {

                //결제금액보다 많은 포인트 입력시 결제금액으로 입력포인트 변경
                point = totalPrice;
                $("#pointInput").val(point);
                handleDiscountedAmount();
                console.log("----포인트 오바---");
            }

        } else if (point < 5000) {
            // 5000 미만의 포인트는 처리하지 않음 또는 에러 메시지 등을 표시
            $(".pointAlert").css('color', 'red');
            console.log("포인트는 5000 이상이어야 합니다.");
        } else if (point > customerPoint) {
            $(".pointAlert").after('<div>❗ 가용포인트보다 높은 금액을 입력했습니다. </div>').next().css('color', 'red');
            $(".pointAlert").css('color', 'black');
            console.log("가용포인트보다 높은 금액을 입력했습니다.");
        }
    });
});


//쿠폰,적립금 선택
function handleDiscountedAmount() {
    // 선택된 옵션 요소 가져오기
    var selectedOption = $("#selectbox option:selected");

    // 선택된 쿠폰의 데이터 추출
    var discount = selectedOption.data("coupon-discount");
    var totalPrice = $("#totalPrice").val();
    var deliveryCharge = $("#deliveryCharge").val();
    console.log("가져온 포인트" + point);

    point = (point) ? point : 0;
    discount = (discount) ? discount : 0;
    console.log(point);
    console.log(discount);
    console.log(totalPrice);
    console.log(deliveryCharge);
    $(".couponAlert + div").remove();
    // Ajax를 사용하여 서버에 데이터 전송
    $.ajax({
        url: "/order/discountedAmount",
        type: "POST",
        dataType: 'json',
        headers: {
            "X-CSRF-TOKEN": token
        },
        data: {
            selectCouponDiscountRate: discount,
            totalPrice: totalPrice,
            deliveryCharge: deliveryCharge,
            point: point
        },
        success: function (response) {
            // 서버 응답 처리
            console.log("서버 응답:", response.TotalPriceWithShipping);
            console.log("서버 응답:", response.couponDiscount);
            console.log("서버 응답:", response.point);

            var isAlertAdded = false;

            if (response.couponDiscount <= 0) {
                $('#selectbox').val($('select option:first').val());

                if (!isAlertAdded) {
                    // 알림 추가
                    $(".couponAlert").after('<div class="alertMessage">❗ 결제 금액이 없어 쿠폰 적용이 불가합니다 </div>').next().css('color', 'red');
                    isAlertAdded = true;
                }
            } else {
                // 이미 생성된 알림이 있을 경우 삭제
                $(".alertMessage").remove();
            }
            if (response.TotalPriceWithShipping > 0) {
                // 이미 생성된 알림이 있을 경우 삭제
                $(".alertMessage").text("");
            }
            // 숫자를 #,### 형식으로 변환
            var TotalPriceWithShippingformattedData = (response.TotalPriceWithShipping).toLocaleString();
            var couponDiscountformattedData = (response.couponDiscount).toLocaleString();
            var pointDiscountformattedData = (response.point).toLocaleString();
            $(".couponDiscount").text(couponDiscountformattedData + "원"); // 쿠폰 할인 업데이트
            $(".TotalPriceWithShipping").text(TotalPriceWithShippingformattedData + "원");//최종 결제금액 업데이트
            $(".pointDiscount").text(pointDiscountformattedData + "원");
        },
        error: function (xhr) {
            console.error("Ajax 오류:", xhr.responseText);
        }
    });
}
//배송지 입력시 BYTE 계산하여 넘길경우 알림창 띄우기
function fc_chk_byte(aro_name, ari_max) {
    var ls_str = aro_name.value;
    var li_str_len = ls_str.length;
    var li_max = ari_max;
    var i = li_byte = li_len = 0;
    var ls_one_char = ls_str2 = "";
    for (i = 0; i < li_str_len; i++) {
        ls_one_char = encodeURIComponent(ls_str.charAt(i));
        if (ls_one_char.length == 1) li_byte++;
        else if (ls_one_char.indexOf("%u") != -1) li_byte += 2;//Db가 한글을 3byte로 인식하여 2->3
        else if (ls_one_char.indexOf("%") != -1) li_byte += ls_one_char.length / 3;
        if (li_byte <= li_max) {
            li_len = li_byte;
        }
    }
    if (li_byte > li_max) {
        alert(li_max + "byte, 초과 ");
        ls_str2 = ls_str.substr(0, li_len);
        aro_name.value = ls_str2;
        document.sms.char_byte.value = 200;
    } else {
        document.sms.char_byte.value = li_byte;
    }
    aro_name.focus();
}

//배송메세지 TEXTAREA 입력하면 배송메세지 선택 0으로 돌려줌
function updateSelect(textarea) {
    var select = document.getElementById("deliveryMessageSelect");
    select.selectedIndex = 0;
}

//배송메세지 선택하면 TEXTAREA 비워줌
function clearTextarea() {
    $("#txta").val(""); // 배송메세지 선택 시 텍스트 영역 비우기
}

//연락처 검증 여기서부터
// 플래그 변수
var alertDisplayed = false;

// 연락처 검증
function checkPhoneNumber() {
    // 입력된 연락처 가져오기
    var phoneNumber = $('.orderPhone').val();

    // 숫자로만 이루어져 있고 11자리인지 확인
    if (/^\d{11}$/.test(phoneNumber)) {
        // 올바른 경우
        console.log("올바른 연락처입니다.");
        return;
    } else {
        // 잘못된 경우
        if (!alertDisplayed) {
            alert("올바른 연락처를 입력해주세요.");
            alertDisplayed = true;

            // 입력 필드로 커서 이동
            $('.orderPhone').focus();
        }

        // focusout 이벤트 제거
        $('.orderPhone').off('focusout', checkPhoneNumber);

        return;
    }
}

// 포커스 이벤트 리스너 등록
$('.orderPhone').on('focusout', checkPhoneNumber);
//연락처검증 여기까지