var token = "";
var email = "";

// selectOpion중 `사이즈선택` 에 대한 선택 불가 처리
document.addEventListener('DOMContentLoaded', function () {
    var sizeSelect = document.getElementById('sizeSelect');
    sizeSelect.options[0].disabled = true;
    console.log("이메일", $("#email").val());
    email = $("#email").val();
});

// ********************************************** //
// 상품 구매 
// ********************************************** //
$(function () {
    token = $('#token').val();
    // 상품의 재고를 DB로부터 가져옴
    var itemCountM = parseInt(document.querySelector('input[name="itemCountM"]').value);
    var itemCountL = parseInt(document.querySelector('input[name="itemCountL"]').value);
    var itemCountXL = parseInt(document.querySelector('input[name="itemCountXL"]').value);

    // itemCountM, itemCountL, itemCountXL 값 확인
    console.log('재고 값 확인 M, L, XL : ' + itemCountM, itemCountL, itemCountXL);

    // 각 사이즈 옵션을 가져옴
    var sizeOptionM = document.getElementById('sizeOptionM');
    var sizeOptionL = document.getElementById('sizeOptionL');
    var sizeOptionXL = document.getElementById('sizeOptionXL');

    // 모든 사이즈의 itemCount가 0일 경우 '모든 상품이 품절되었습니다' 메시지
    if (itemCountM === 0 && itemCountL === 0 && itemCountXL === 0) {
        var selectResult = document.querySelector('.selectResult');
        if (selectResult) {
            selectResult.textContent = '모든 상품이 품절되었습니다';
        } else {
            var innerOpt01 = document.getElementById('InnerOpt_01');
            innerOpt01.innerHTML = '<span class="selectResult">모든 상품이 품절되었습니다</span>';
        }
    }

    // itemCountM, itemCountL, itemCountXL이 0일 경우, 해당 사이즈 옵션을 비활성화
    if (itemCountM === 0) {
        sizeOptionM.disabled = true;
    }
    if (itemCountL === 0) {
        sizeOptionL.disabled = true;
    }
    if (itemCountXL === 0) {
        sizeOptionXL.disabled = true;
    }


    // 사용자가 수량을 변경할 때마다 totalPrice 업데이트
    $(document).on('input', '.quantity input', function () {
        var totalPrice = calculateTotalPrice();
        $('#innerOptTotal').text('총 상품 금액 : ' + totalPrice + '원');
    });

    $(document).on('change', '#sizeSelect', function () {
    });

    // input 부분 값 수정 시
    $(document).on('input', '.quantity input', function () {
        console.log("input 도착");
        var enteredValue = parseInt($(this).val()); // 입력된 값 가져오기
        console.log(enteredValue);

        if (enteredValue > 50 || enteredValue === 0) {
            alert('입력 가능한 범위는 1부터 50까지입니다!');
            $(this).val("50");
        }
    });
});


function calculateTotalPrice() {
    var totalPrice = 0;
    var firstOption = $('#sizeSelect option:selected').val(); // 첫 번째 선택된 옵션 값 가져오기
    //console.log('고객이 선택된 옵션 : ' + firstOption);
    var firstOptionPrice = parseFloat($('#itemSale').val());

    var firstOptionQuantity = 0;

    // '사이즈 선택'이 아닐 때만 첫 번째 옵션의 수량을 totalPrice에 추가
    if (firstOption !== '사이즈 선택') {
        firstOptionQuantity = parseInt($('#InnerOpt_01 li:first-child .quantity input').val()) || 0;
        totalPrice += firstOptionPrice * firstOptionQuantity;
    }

    // 나머지 옵션들의 가격을 totalPrice에 추가
    $('#InnerOpt_01 li:not(:first-child)').each(function () {
        var selectedOption = $(this).find('.selectResult').text();
        var quantity = parseInt($(this).find('.quantity input').val()) || 0;

        if (selectedOption === 'M' || selectedOption === 'L' || selectedOption === 'XL') {
            totalPrice += firstOptionPrice * quantity;
        }
        // 다른 사이즈에 대한 가격 계산 추가 가능
    });

    console.log('totalPrice >>> ' + totalPrice);
    return totalPrice;
}


// ********************************************** //
// 상품 삭제 버튼
// ********************************************** //
function addDeleteButton($li) {
    if ($li.find('.deleteOptionButton').length === 0) {
        var $deleteButton = $('<a class="deleteOptionButton"><img src="/resources/marker/btn_comment_del.gif" /></a>');
        $li.find('.option-input-container').append($deleteButton);

        $deleteButton.on('click', function () {
            var selectedOption = $li.find('.selectResult').text();
            $li.remove();

            var select = $('#sizeSelect').get(0);
            var options = select.options;
            for (var i = 0; i < options.length; i++) {
                if (options[i].value === selectedOption) {
                    options[i].disabled = false;
                    break;
                }
            }
            var totalPrice = calculateTotalPrice();
            $('#innerOptTotal').text('총 상품 금액 : ' + totalPrice + '원');
        });
    }
}

// ********************************************** //
// cart & orders 페이지로 넘겨주기
// ********************************************** //

//선택 옵션과 수량을 리스트로 받아오기
var selectedOptionsList = [];
var selectOptions = [];
var selectQuantity = [];

// 옵션 추가 함수
function optionSelected(option) {
    var selectedOption = option;
    var selectResult = $('#InnerOpt_01');

    var isOptionSelected = false;
    selectResult.find('li').each(function () {
        if ($(this).find('.selectResult').text() === selectedOption) {
            isOptionSelected = true;

            // 이미 선택된 옵션이 있으면 반복문 종료
            return false;
        }
    });

    if (isOptionSelected) {
        alert('이미 선택한 상품입니다.'); // 이미 선택한 상품이면 alert를 통해 알림
        return;
    }

    var defaultQuantity = 1; // 입력된 수량의 기본값

    var newIdNumber = selectResult.find('li').length;
    var newId = 'basic_' + newIdNumber;

    var newItem = $('<li id="' + newId + '">' +
       '<span class="selectResult">' + selectedOption + '</span>' +
        '<div class="quantity">' +
        '<div class="option-input-container">' +
        '<input type="text" name="cnts" class="quantity-input" min="1" value="' + defaultQuantity + '">' +
        '<input type="hidden" name="sizes" class="quantity-input" min="1" value="' + option + '">' +
        '</div>' +
        '</div>' +
        '</li>');

    selectResult.prepend(newItem);

    addDeleteButton(newItem);

    var inputQuantity = defaultQuantity; // 초기에는 입력된 수량의 기본값으로 설정

    newItem.find('.quantity-input').on('input', function () {
        inputQuantity = $(this).val(); // 사용자가 입력한 값을 갱신
        console.log(inputQuantity);
        var existingOptionIndex = selectedOptionsList.findIndex(item => item.option === selectedOption);
        if (existingOptionIndex !== -1) {
            selectedOptionsList[existingOptionIndex].inputQuantity = inputQuantity; // 기존 값 수정
        } else {
            selectedOptionsList.push({ option: selectedOption, inputQuantity: inputQuantity });
            selectOptions.push(selectedOption);
            selectQuantity.push(inputQuantity);
        }

        // 배열로 선택한 옵션 리스트 및 상세 정보 출력
        var optionsInfoArray = selectedOptionsList.map(function (item) {
            return { option: item.option, inputQuantity: item.inputQuantity };
        });

        console.log('고객이 선택한 옵션 리스트:');
        console.log(optionsInfoArray);

        var totalPrice = calculateTotalPrice();
        $('#innerOptTotal').text('총 상품 금액 ' + totalPrice + '원');
    });

    // 선택한 옵션과 입력된 수량을 리스트에 추가
    selectedOptionsList.push({ option: selectedOption, inputQuantity: defaultQuantity });
    selectOptions.push(selectedOption);
    selectQuantity.push(inputQuantity);

    // 배열로 선택한 옵션 리스트 및 상세 정보 출력
    var optionsInfoArray = selectedOptionsList.map(function (item) {
        return { option: item.option, inputQuantity: item.inputQuantity };
    });

    console.log('고객이 선택한 옵션 리스트:');
    console.log(optionsInfoArray);

    var totalPrice = calculateTotalPrice();
    $('#innerOptTotal').text('총 상품 금액: ' + totalPrice + '원');
}



// ********************************************** //
// Q n A
// ********************************************** //

// redirectToInsertQnA 함수를 사용해서 href링크를 상품문의로 보냄
function redirectToInsertQnA() {
    var queryString = window.location.search;
    var urlParams = new URLSearchParams(queryString);

    var itemNum = urlParams.get('itemNum');
    var pageNum = 1;

    if (itemNum) {
        var url = '/item/insertQna?itemNum=' + itemNum + '&pageNum=' + pageNum;
        window.location.href = url; // 페이지 이동
    } else {
        console.error('Item number not found.');
        // 처리할 수 없는 경우 에러 메시지 출력 또는 다른 작업 수행
    }
}

// ********************************************** //
//			옵션 미선택 alert 생성				  //
// ********************************************** //

// whislist 관련
function changeColor() {
    console.log("너 작동하냐");

    var heartIcon = $('#heartIcon');
    var currentColor = heartIcon.css('color');

    // 현재 색상이 #fff(흰색)이면 빨간색으로, 그렇지 않으면 흰색으로 변경
    heartIcon.css('color', currentColor === "rgb(255, 255, 255)" ? "#FF0000" : "#fff");
}

// ********************************************** //
//			비로그인시 로그인 안내				  //
// ********************************************** //
function gologin() {
    // 사용자에게 경고창을 표시합니다.
    var confirmation = confirm("로그인이 필요한 페이지입니다. 로그인 페이지로 이동하시겠습니까?");

    // 사용자가 확인을 선택했을 때만 이동합니다.
    if (confirmation) {
        window.location.href = "/shard/login"; // 페이지 이동
    }
}

// ********************************************** //
//	     review 필수 작성 요소 alert 생성         //
// ********************************************** //
function validateReviewForm() {
    var title = document.forms["reviewForm"]["replyTitle"].value;
    var content = document.forms["reviewForm"]["replyContent"].value;
    var score = document.forms["reviewForm"]["starScore"].value;

    if (title === "" || content === "" || score === "") {
        alert("리뷰 제목, 내용, 별점은 필수 입력 사항입니다.");
        return false;
    }

    // 별점이 1에서 5 사이인지 확인
    if (score < 1 || score > 5) {
        alert("별점은 1에서 5 사이어야 합니다.");
        return false;
    }


    return true; // 유효성 검사가 통과되었을 때 폼 제출
}





// ********************************************** //
//					cart로 넘기기				  //
//					코드 추가 필요				  //
// ********************************************** //


// ********************************************** //
//					orders로 넘기기				  //
//					코드 추가 필요				  //
// ********************************************** //
$(document).ready(function () {
    // 바로구매 버튼 클릭 시
    $("#directPurchaseBtn").click(function () {
        var innerOptUl = $('#InnerOpt_01');
        if (innerOptUl.children().length === 0) {
            alert('사이즈를 선택하지 않았습니다.');
            $('#sizeSelect').focus();
            return;
            // 추가적으로 필요한 처리를 여기에 추가할 수 있습니다.
        }

        // 이메일 값을 가져오기
        if (email === undefined) {
            if (confirm("이메일이 필요합니다. 로그인 페이지로 이동하시겠습니까?")) {
                // 사용자가 확인을 눌렀을 때
                var returnUrl = encodeURIComponent(location.href);
                location.href = '/shard/login?returnUrl=' + returnUrl;
            }
            // 사용자가 취소를 눌렀을 때는 아무 작업도 수행하지 않음
            return;
        }
        // 이메일 필드가 이미 폼에 있는지 확인
        var emailField = $("#purchaseForm [name='email']");

        // 이메일 필드가 존재하면 값을 업데이트하고, 없으면 새로운 필드 추가
        if (emailField.length > 0) {
            emailField.val(emailValue);
        } else {
            $("#purchaseForm").append('<input type="hidden" name="email" value="' + email + '">');
        }
        // 폼 서브밋
        $("#purchaseForm").submit();
    });
});
// ********************************************** //
//cart로 넘기기
// ********************************************** //   

function addToCart() {
    var innerOptUl = $('#InnerOpt_01');
    if (innerOptUl.children().length === 0) {
        alert('사이즈를 선택하지 않았습니다.');
        $('#sizeSelect').focus();
        return;
    }
    console.log("email", email);
    console.log("토큰토큰", token);
    console.log("addtoCart 들어옴");
    // 필요한 데이터를 가져와서 동적으로 form을 생성하고 제출
    //var email = $('#email').val;  // 이메일을 가져오는 방법에 따라 값을 설정
    // 기존의 form 엘리먼트를 가져와서 액션 값을 변경
    var form = $('#purchaseForm');
    form.attr('action', '/order/addCart');
    // CSRF 토큰 추가
    //form.append('<input type="hidden" name="${_csrf.parameterName}" value="'+ token +'}" id="token" />');
    //form.append('<input type="hidden" name="cnts" value="' + selectQuantity + '" id="cnts" />');
    //form.append('<input type="hidden" name="sizes" value="' + selectOptions + '" id="sizes" />');
    form.append('<input type="hidden" name="email" value="' + email + '" />');

    // form을 body에 추가하고 제출
    form.submit();

}
