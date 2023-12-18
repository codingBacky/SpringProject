var orderName = "";
var orderPhone = "";
var postcode = 0;
var roadAddress = "";
var detailAddr = "";
var email = "";
var cartNum = 0;
var pointInput = 0;
var token = "";
var point = 0;
var addressSave = "";
var issueNum = 0;
var req = "";
var TotalPriceWithShipping="";
var cntArray = [];
var sizeArray = [];
var itemNumArray = [];

$(function () {
	cntArray = $('input[name=cnts]').map(function () {
		// input의 값을 가져와서 숫자로 변환하여 배열에 추가
		return parseInt($(this).val(), 10);
	}).get();
	console.log("cntArray >>>",cntArray);
	sizeArray = $('input[name=sizes]').map(function () {
		return $(this).val();
	}).get();
	itemNumArray = $('input[name=itemNum]').map(function () {
		// input의 값을 가져와서 숫자로 변환하여 배열에 추가
		return parseInt($(this).val(), 10);
	}).get();
	console.log("sizeArrays >>",sizeArray);
	orderName = $(".userName").val();
	orderPhone = $(".phone").val();
	postcode = parseInt($(".postcode").val());
	roadAddress = $(".roadAddress").val();
	detailAddr = $(".detailAddr").val();
	email = $("#email").val();
	cartNum = parseInt($('#cartNum').val());
	TotalPriceWithShipping = $('.TotalPriceWithShipping').text();
	 // '원' 제거 및 쉼표(,) 제거
	var stringWithoutWonAndComma = TotalPriceWithShipping.replace(/[원,]/g, '');
	 // 정수로 변환
	TotalPriceWithShipping = parseInt(stringWithoutWonAndComma, 10);
	token = $('#token').val();
	
	
})
function issueNumInput() {
	var selectElement = document.getElementById("selectbox");
	var selectedOption = selectElement.options[selectElement.selectedIndex];
	issueNum = parseInt(selectedOption.getAttribute("data-issue-num"));
	console.log("issueNum>>>>>>" + issueNum);
}
function statusCheckboxChange() {
	var checkbox = document.getElementById("shipto");
	if (checkbox.checked) {
		
		orderName = $(".orderName").val();
		orderPhone = $(".orderPhone").val();
		postcode = $("#postcode").val();
		roadAddress = $("#roadAddress").val();
		detailAddr = $("#detailAddress").val();
		addressSave = $(".addressSaveInput").val();
		pointInput = parseInt($('.pointDiscount').text());

	   // 필수 입력 필드 중 하나라도 비어 있는지 확인
	   if (!orderName || !orderPhone || !postcode || !roadAddress || !detailAddr ) {
		alert("주문정보를 모두 입력해주세요.");

		// 비어 있는 필드에 커서 이동
		if (!orderName) {
			$(".orderName").focus();
		} else if (!orderPhone) {
			$(".orderPhone").focus();
		} else if (!postcode) {
			$("#postcode").focus();
		} else if (!roadAddress) {
			$("#roadAddress").focus();
		} else if (!detailAddr) {
			$("#detailAddress").focus();
		} else if (!addressSave) {
			$("#addressSave").focus();
		}

		return false; // 필드가 비어 있으면 false 반환

	} else {
		return true; // 필드가 전부 값이 있으면 true 반환
	}
}
return true; // checkbox가 체크되어 있지 않으면 true 반환
}

function reqInput(){
	var deliveryMessageSelect = document.getElementById("deliveryMessageSelect");
	var selectedMessage = deliveryMessageSelect.options[deliveryMessageSelect.selectedIndex].text;
	var textarea = document.getElementById("txta").value;
	var textareaValue = document.getElementById("txta").value.trim();
	console.log("배송선택메세지>>>"+selectedMessage);
	if (textareaValue.length > 0) {
		// 직접 입력한 내용이 있는 경우
		req = textarea;
		console.log("배송메세지 직접입력>>>"+req);
	} else {
		// 선택된 메세지가 있으면
		if(selectedMessage !== ""){
		req = selectedMessage;
		console.log(req);
		}
		else req = "조심히 안전히 와주세요";
	}
}
//import 실행
function requestPay() {

	//다른주소 입력 클릭확인/주문정보변경
	if (!statusCheckboxChange()) {
        return; // statusCheckboxChange가 false를 반환하면 더 이상 실행하지 않음
    }
	reqInput();
	IMP.init('imp21466554');

	IMP.request_pay({
		pg: 'inicis', // version 1.1.0부터 지원.
		pay_method: 'card',
		merchant_uid: 'merchant_' + new Date().getTime(),
		name: '주문명:상품결제',
		amount: 100, //판매 가격
		buyer_email: email,
		buyer_name: orderName,
		buyer_tel: orderPhone,
		buyer_addr: roadAddress,
		buyer_postcode: postcode
	}, function (rsp) {
		console.log(rsp);
		// 결제검증
		$.ajax({
			type: "POST",
			headers: {
				"X-CSRF-TOKEN": token
			},
			url: "/verifyIamport/" + rsp.imp_uid
		}).done(function (data) {


			// 위의 rsp.paid_amount 와 data.response.amount를 비교한후 로직 실행 (import 서버검증)
			if (rsp.paid_amount == data.response.amount) {
				var msg = '결제 및 결제검증완료 - 결제가 완료되었습니다.';
				msg += '고유ID : ' + rsp.imp_uid;
				msg += '상점 거래ID : ' + rsp.merchant_uid;
				msg += '결제 금액 : ' + rsp.paid_amount;
				msg += '카드 승인번호 : ' + rsp.apply_num;

				var itemNum = JSON.stringify(itemNumArray);
				var cnts = JSON.stringify(cntArray);
				var sizes = JSON.stringify(sizeArray);
				console.log("여기는 비교하는 곳이야")
				$.ajax({
					type: 'POST',
					url: '/order/payComplete',
					headers: {
						"X-CSRF-TOKEN": token
					},
					dataType: 'application/json',
					data: {
						itemNum: itemNum,
						postcode: postcode,
						roadAddress: roadAddress,
						detailAddr: detailAddr,
						email: email,
						cartNum: cartNum,
						usePoint: point,
						addressSave: addressSave,
						issueNum: issueNum,
						cntArray: cnts,
						sizeArray: sizes,
						req: req,
						TotalPriceWithShipping: TotalPriceWithShipping,
						orderName: orderName,
						orderPhone: orderPhone

					},
					success: function () {
						console.log("전송 완료");
					},
					error: function () {
						console.log("전송 완료");
						location.href = "/order/orderComplete";
					}
				});
			} else {
				var msg = '결제에 실패하였습니다.';
				msg += '에러내용 : ' + rsp.error_msg;
				alert(msg);
			}
		});
	});
}

