<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.FileReader"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>간편 회원가입</title>
<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.7.1.min.js"></script>
<script src="/resources/js/kakaoJoin.js" type="text/javascript"></script>
<script src="/resources/js/address.js" type="text/javascript"></script>
<link rel="stylesheet" href="/resources/css/common.css" />
<link rel="stylesheet" href="/resources/css/join.css" />
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400&family=Noto+Sans:ital,wght@0,200;1,300&display=swap"
	rel="stylesheet">
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
</head>
<body>
	<script>
		var userEmail = "${userEmail}";

		if (!(userEmail == "")) {
			var shouldSignUp = confirm("아직 The Shard의 회원이 아니십니다. 회원가입을 하시겠습니까?");

			if (!shouldSignUp) {
				// 사용자가 "취소"를 선택한 경우 현재 페이지에 머무르기
				location.href = '/shard/';
			}
			// "확인"을 선택한 경우에는 아무 동작도 하지 않음
		}
	</script>

	<jsp:include page="../header.jsp"></jsp:include>

	<div class="joinWrap">
		<div class="join">
			<h2>sns 간편가입</h2>

			<div class="join_box">
				<form action="/shard/kakaoJoin" method="post" id="joinForm">
					<input type="hidden" name="email" value="${userEmail}" />
					<input type="hidden" name="userName" value="${nickName}" />
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					<ul>
						<p style="font-size: 18px; font-weight: 600; color: #000;">주소</p>
						<li class="address"><input type="text" id="postcode"
							placeholder="우편번호" name="postcode">
							<button type="button" onclick="execDaumPostcode()"
								class="adress_find">우편번호 찾기</button> <br> <input
							type="text" id="roadAddress" placeholder="도로명주소"
							name="roadAddress"> <input type="text" id="jibunAddress"
							placeholder="지번주소" name="jibunAddress">
							<p id="guide" style="color: #999; display: none"></p> <input
							type="text" id="detailAddr" placeholder="상세주소"
							name="detailAddr"> <input type="text"
							id="extraAddress" placeholder="참고항목" name="extraAddress">
						</li>
						<li class="birth">
							<div class="birth_box">
								<label for="birthYear">생년</label><input type="text"
									name="birthYear" id="birthYear" maxlength="4" />
							</div>
							<div class="birth_box">
								<label for="birthMonth">월</label><input type="text"
									name="birthMonth" id="birthMonth" maxlength="2" />
							</div>
							<div class="birth_box">
								<label for="birthDay">일</label><input type="text"
									name="birthDay" id="birthDay" maxlength="2" />
							</div>
							<div class="gender_box">
								<label for="gender">성별</label>
								<input type="radio" name="gender" value="M" class="gender"/><span>남</span>
								<input type="radio" name="gender" value="W" style="margin:15px 0 0 90px;" class="gender"/><span style="margin:0 0 0 20px;">여</span>
							</div>
						</li>
						<li><label for="phone">휴대폰번호 ex)01012345678</label> <input type="text"
							name="phone" id="phone" maxlength="11" /></li>
					</ul>
				</form>
				<ul class="join_msg">
					<li><span>14세 이상입니다.(필수)</span></li>
					<li>*회원가입에 필요한 최소한의 정보만 입력 받음으로써 고객님의 개인정보 수집을 최소화하고 <br />
						편리한 회원가입을 제공합니다.
					</li>
				</ul>
				<div class="chkWrap">
					<div class="all_chk">
						<label for="all_check"><input type="checkbox"
							name="all_check" id="all_check" /> 전체동의</label>
					</div>
					<div class="userInfo">
						<ul>
							<li><label for="playmall"><input type="checkbox"
									name="playmall" id="playmall" /> 이용약관</label> <a href="#tit1">내용보기</a>
							</li>
							<li><label for="userinfo"><input type="checkbox"
									name="userinfo" id="userinfo" /> 개인정보 수집 및 이용안내</label> <a
								href="#tit2">내용보기</a></li>
						</ul>
					</div>
				</div>
				<div class="submitButton">
					<a href="javascript:formSubmit()" class="buttonJoin">동의하고 가입하기</a>
				</div>
				<h4 class="chk_cont1" id="tit1">이용약관</h4>
				<div class="privercy-contract">
					<textarea rows="10" cols="80" wrap="off" readonly="readonly"
						style="height: 194px; widht: 650px;">
						<%
						BufferedReader reader = null;
						try {
							String filePath = application.getRealPath("/WEB-INF/terms.txt");
							reader = new BufferedReader(new FileReader(filePath));
							while (true) {
								String str = reader.readLine();
								if (str == null)
							break;
								out.println(str);
							}
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							try {
								reader.close();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						%>
					</textarea>
				</div>
				<h4 class="chk_cont1" id="tit2">개인정보 수집·이용 (필수)</h4>
				<table class="contract-table">
					<tr>
						<th scope="col"><div>구분</div></th>
						<th scope="col"><div>목적</div></th>
						<th scope="col"><div>항복</div></th>
						<th scope="col"><div>보유기간</div></th>
						<th scope="col"><div>
								동의 <input type="checkbox" name="all_agree" class="all_agree"
									checked />
							</div></th>
					</tr>
					<tr>
						<th scope="row" rowspan="1"><div>필수정보</div></th>
						<td><div>회원제 서비스 이용 / 본인확인</div></td>
						<td><div>이름, 아이디, 비밀번호, 이메일, 휴대 전화, 생년월일</div></td>
						<td><div style="font-weight: bold">회원 탈퇴 후 즉시</div></td>
						<td rowspan="1"><div>
								<input type="checkbox" name="all_agree" class="all_agree"
									checked />
							</div></td>
					</tr>
				</table>
				<h2
					style="margin-top: 10px; color: #666; font-size: 12px; font-weight: normal;">*
					서비스 제공을 위한 최소한의 개인정보이므로 동의를 해주셔야 서비스를 이용하실 수 있습니다.</h2>
			</div>
		</div>
	</div>

	<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>