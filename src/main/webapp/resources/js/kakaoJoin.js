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

// 주소가 정상적으로 입력되었는지 체크
function addressCheck() {
   var zipCode = $('#postcode').val();
   var userAddr = $("#roadAddress").val();
   var detailAddr = $("#detailAddress").val();

   if (zipCode == "" || userAddr == "" || detailAddr == "") {
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

function phoneCheck() {
   var phone = $('#phone').val();
   var check = false;

   if (phone == "") {
      return false;
   } else {
      check = true;
   }

   return check;
}


function formSubmit() {
   var checkInfo = $('.all_agree').is(":checked"); // 개인정보 동의 체크
   var playMall = $('#playmall').is(":checked");
   var userinfo = $('#userinfo').is(":checked");
   var checkAddress = addressCheck(); // 주소 체크
   var checkBirth = birthCheck(); // 생일 체크
   var checkPhone = phoneCheck(); // 휴대폰 번호 체크
   var checkGender = genderCheck(); // 성별 체크

   // 휴대폰 인증
   // 생일 인증
   // 약관 인증까지

   // 모든 인증이 다 들어가야 한다.


   if (checkAddress) {
      if (checkBirth) {
         if (checkGender) {
            if (checkPhone) {
               if (checkInfo) {
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
               } else {
                  alert("개인정보 수집·이용 동의가 필요합니다.");
                  $('.all_agree').focus();
                  return false;
               }
            } else {
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

};