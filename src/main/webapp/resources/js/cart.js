var token = "";
$(function () {
    token = $('#token').val();
    console.log(token);
    // 전체 선택을 위한 마스터 체크박스
    const masterCheckbox = document.getElementById('selectAllCheckbox');

    // 각 행의 체크박스들
    const itemCheckboxes = document.querySelectorAll('.itemCheckbox');

    // 페이지 로드 시 마스터 체크박스와 모든 하위 체크박스 선택
    masterCheckbox.checked = true;
    itemCheckboxes.forEach(function (checkbox) {
        checkbox.checked = true;
    });

    // 전체 선택을 위한 마스터 체크박스 상태 변경 시
    masterCheckbox.addEventListener('change', function () {
        // 전체 체크박스들의 상태를 마스터 체크박스에 맞게 설정
        itemCheckboxes.forEach(function (checkbox) {
            checkbox.checked = masterCheckbox.checked;
        });
    });

    // 각 행의 체크박스 상태 변경 시
    itemCheckboxes.forEach(function (checkbox) {
        checkbox.addEventListener('change', function () {
            // 모든 체크박스가 선택되었는지 확인
            const allChecked = Array.from(itemCheckboxes).every(function (checkbox) {
                return checkbox.checked;
            });
            // 마스터 체크박스의 상태를 변경
            masterCheckbox.checked = allChecked;
        });
    });
    //+버튼 클릭
    $('.plus').click(function (e) {
        var pointRate = parseInt($('#pointRate').val());
        e.preventDefault();
        var plusrealSale = parseInt($(this).closest("tr").find(".itemRealSale").val());
        var realPoint = parseInt(plusrealSale * (pointRate / 100));
        var itemCount = $(this).parent().siblings(".itemCount").val();
        console.log("plusrealSale",plusrealSale);
        console.log("realPoint",realPoint);
        console.log("itemCount",itemCount);
        itemCount++;
        if (itemCount > 50) {
            alert("상품의 최대 수량은 50개입니다.");
            $(this).parent().siblings(".itemCount").val(50);
            $(this).parents().siblings(".itemSale").text(plusrealSale);
            $(this).parents().siblings(".reward").text(realPoint);
        } else {
            $(this).parent().siblings(".itemCount").val(itemCount);
            var sale = parseInt($(this).parents().siblings(".itemSale").text());
            var point = parseInt($(this).parents().siblings(".reward").text());
            var size = $(this).parents().siblings(".sizes").val();
            console.log("sale",sale);
            console.log("point",point);
            console.log("size",size);
            sale = sale + plusrealSale;
            point = point + realPoint;
            $(this).parents().siblings(".itemSale").text(sale);
            $(this).parents().siblings(".reward").text(point);
            $(this).parents().siblings(".totalPriceList").val(sale);
            $(this).parents().siblings(".itemRewardPoints").val(point);
            var itemNum = parseInt($(this).closest("tr").find(".itemNum").val());
            console.log("itemNum", itemNum);
            updateTotalPrice();
            //detailcart itemNum update
            $.ajax({
                type: "POST",
                url: "/order/updateExpectedPlusAmount",
                headers: {
                    "X-CSRF-TOKEN": token
                },
                data: {
                    itemNum: itemNum,
                    size: size
                },
                success: function (response) {
                    console.log(response);
                },
                error: function (error) {
                    // 요청이 실패한 경우의 동작
                    console.log(error);
                }
            });
        }
    });
    //-버튼 클릭
    $('.minus').click(function (e) {
        var pointRate = parseInt($('#pointRate').val());
        e.preventDefault();
        var minusrealSale = parseInt($(this).closest("tr").find(".itemRealSale").val());
        var realPoint = parseInt(minusrealSale * (pointRate / 100));
        var itemCount = $(this).parent().siblings(".itemCount").val();
        itemCount--;
        if (itemCount < 1) {
            alert("상품의 최소 수량은 1개입니다.");
            $(this).parent().siblings(".itemCount").val(1);
            $(this).parents().siblings(".itemSale").text(minusrealSale);
            $(this).parents().siblings(".reward").text(realPoint);
        } else {
            $(this).parent().siblings(".itemCount").val(itemCount);
            var sale = parseInt($(this).parents().siblings(".itemSale").text());
            var point = parseInt($(this).parents().siblings(".reward").text());
            var size = $(this).parents().siblings(".sizes").val();
            sale = sale - minusrealSale;
            point = point - realPoint;
            $(this).parents().siblings(".itemSale").text(sale);
            $(this).parents().siblings(".reward").text(point);
            $(this).parents().siblings(".totalPriceList").val(sale);
            $(this).parents().siblings(".itemRewardPoints").val(point);

            var itemNum = parseInt($(this).closest("tr").find(".itemNum").val());
            console.log("itemNum", itemNum);
            console.log(realPoint);
            console.log(minusrealSale);
            updateTotalPrice();
            //detailcart itemNum update
            $.ajax({
                type: "POST",
                url: "/order/updateExpectedMinusAmount",
                headers: {
                    "X-CSRF-TOKEN": token
                },
                data: {
                    itemNum: itemNum,
                    size: size
                },
                success: function (response) {
                    console.log(response);
                },
                error: function (error) {
                    // 요청이 실패한 경우의 동작
                    console.log(error);
                }
            });
        }


    });


    // 전체상품삭제 버튼 클릭 시
    $("#allDeleteButton").click(function () {
        // cartNum 값을 가져오기
        var cartNum = $("#cartNum").val();
        var email = $("#email").val();
        console.log(cartNum);
        // AJAX 호출
        $.ajax({
            type: "POST",
            url: "/order/allDetailCartDelete",
            headers: {
                "X-CSRF-TOKEN": token
            },
            data: {
                cartNum: cartNum,
            },
            success: function () {
                console.log(response);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                // 에러 처리
                console.error("AJAX 요청 중 에러 발생:", textStatus, errorThrown);
                alert("에러가 발생했습니다.");
            }
        });
    });
    // 체크박스의 상태가 변경될 때마다 updateTotalPrice 함수 호출
    $(".itemCheckbox, #selectAllCheckbox").change(function () {
        updateTotalPrice();
    });

});

//선택항목 주문
function selectOrder() {
    var selectedItems = [];
    $(".itemCheckbox:not(:checked)").each(function () {
        // 체크되어 있는 경는
        var $currentTr = $(this).closest("tr");

        // 현재 <tr> 및 그 다음 형제 <tr>을 모두 제거
        $currentTr.remove();

    });
    $('#checkoutForm').submit();
};

//선택항목 예상결제 update
function updateTotalPrice() {
    var totalPrice = 0;
    var deliveryCharge = parseInt($('#deliveryCharge').val());
    console.log(totalPrice);
    console.log(deliveryCharge);
    // 모든 선택된 체크박스에 대해 반복
    $(".itemCheckbox:checked").each(function () {
        // 현재 체크된 체크박스의 부모 tr을 찾음
        var row = $(this).closest("tr");
        // 해당 행의 가격과 수량을 가져와서 가격 합계를 계산
        var itemSale = parseInt(row.find(".itemSale").text());
        // 체크박스가 체크된 경우 합산
        if ($(this).prop("checked")) {
            totalPrice += itemSale;
        }
    });
    oriTotalPrice = totalPrice;
    oriTotalPriceWithShipping = totalPrice + deliveryCharge;
    deliveryCharge = (deliveryCharge + totalPrice).toLocaleString();
    totalPrice = totalPrice.toLocaleString();
    // 계산된 가격을 표시할 엘리먼트를 찾아 업데이트
    $(".totalPriceUpdate").text(totalPrice + "원");
    $(".cart-won").text(totalPrice + "원");
    $(".TotalPriceWithShippingUpdate").text(deliveryCharge + "원");
    $("#totalPrice").val(oriTotalPrice);
    $("#TotalPriceWithShipping").val(oriTotalPriceWithShipping);
}

//선택한 항목 삭제를 처리하는 함수  
function deleteSelectedItems() {
    var selectedItems = [];
    var size;
    // 모든 체크박스를 순회
    $('.itemCheckbox:checked').each(function () {
        var itemIndex = parseInt(($(this).siblings(".itemNum").val()).replace(/,|원/g, ''), 10);
        console.log(itemIndex);
        // 체크박스 ID에서 항목 번호를 추출
        selectedItems.push(itemIndex);
        size = $(this).closest('tr').find('.sizes').val();

        var $currentTr = $(this).closest("tr");

        // 현재 <tr> 및 그 다음 형제 <tr>을 모두 제거
        $currentTr.remove();
    });
    var cartNum = parseInt($("#cartNum").val());
    console.log(size);

    

    console.log(selectedItems);
    //var selectedItemJSON= JSON.stringify(selectedItem);
    // 선택한 항목이 있는지 확인
    if (selectedItems.length > 0) {
       
        $.ajax({
            type: 'POST',
            url: '/order/chooseDetailCartDelete',
            headers: {
                "X-CSRF-TOKEN": token
            },
            contentType: "application/json",
            data: JSON.stringify({
                selectedItems: selectedItems,
                cartNum: cartNum,
                size: size
            }),
            success: function (response) {
                
                $(".itemCheckbox:not(:checked)").each(function () {


                    var itemSales = parseInt($(this).closest("tr").find(".itemSale").text());
                    console.log("아이템가격",itemSales);
                    chooseTotal += itemSales;
                    console.log("아이템가격 합계",chooseTotal);
                    
                    var deliveryCharge = parseInt($('#deliveryCharge').val());
                    console.log(chooseTotal);
                    console.log(deliveryCharge);
                    var oriTotalPrice = chooseTotal;
                    var oriTotalPriceWithShipping = chooseTotal + deliveryCharge;
                    deliveryCharge = (deliveryCharge + chooseTotal).toLocaleString();
                    var totalPrice = chooseTotal.toLocaleString();
                    // 계산된 가격을 표시할 엘리먼트를 찾아 업데이트
                    $(".totalPriceUpdate").text(totalPrice + "원");
                    $(".cart-won").text(totalPrice + "원");
                    $(".TotalPriceWithShippingUpdate").text(deliveryCharge + "원");
                    $("#totalPrice").val(oriTotalPrice);
                    $("#TotalPriceWithShipping").val(oriTotalPriceWithShipping);
                });
            },
            error: function (jqXHR, textStatus, errorThrown) {
                // 에러 처리
                console.error("AJAX 요청 중 에러 발생:", textStatus, errorThrown);
                alert("에러가 발생했습니다.");
            }
        });
    } else {
        alert('삭제할 항목을 선택하세요.');
    }
}
var chooseTotal = 0;
//상품 삭제
$(document).ready(function () {
    var selectedItem = [];
    // 삭제 버튼 클릭 이벤트
    $(".delete-item-btn").on("click", function (e) {
        e.preventDefault();
        // 해당 상품의 번호 가져오기
        var itemNum = $(this).data("item-num");
        selectedItem.push(itemNum);
        var cartNum = parseInt($("#cartNum").val());
 
        var size = $(this).closest('tr').find('.sizes').val();
        console.log("삭제할 size",size);
        console.log("삭제할 selectedItem",selectedItem);
        // Ajax 요청을 통해 서버에 상품 삭제 요청 보내기
       // var selectedItemJSON= JSON.stringify(selectedItem);

       	var $currentTr = $(this).closest("tr");
        // 현재 <tr> 및 그 다음 형제 <tr>을 모두 제거
        $currentTr.remove();
        
        $('.itemCheckbox:checked').each(function () {
            var itemSales = parseInt($(this).closest("tr").find(".itemSale").text());
            console.log("아이템가격",itemSales);
            chooseTotal += itemSales;
            console.log("아이템가격 합계",chooseTotal);
        });
        var deliveryCharge = parseInt($('#deliveryCharge').val());
        console.log(chooseTotal);
        console.log(deliveryCharge);
        var oriTotalPrice = chooseTotal;
        var oriTotalPriceWithShipping = chooseTotal + deliveryCharge;
        deliveryCharge = (deliveryCharge + chooseTotal).toLocaleString();
        var totalPrice = chooseTotal.toLocaleString();
        // 계산된 가격을 표시할 엘리먼트를 찾아 업데이트
        $(".totalPriceUpdate").text(totalPrice + "원");
        $(".cart-won").text(totalPrice + "원");
        $(".TotalPriceWithShippingUpdate").text(deliveryCharge + "원");
        $("#totalPrice").val(oriTotalPrice);
        $("#TotalPriceWithShipping").val(oriTotalPriceWithShipping);

        $.ajax({
            type: 'POST',
            url: '/order/chooseDetailCartDelete',
            headers: {
                "X-CSRF-TOKEN": token
            },
            contentType: "application/json",
            data: JSON.stringify({
                selectedItems: selectedItem,
                cartNum: cartNum,
                size: size
            }),
            success: function (response) {
                // 삭제에 성공한 경우, 해당 상품 행을 화면에서 제거
                //    var row = $(this).closest("tr");
                //   row.remove();
                 // 체크되어 있는 경우


            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Ajax 요청 실패:", textStatus, errorThrown);
                console.log("서버 응답:", jqXHR.responseText);
            }
        });
    });
});