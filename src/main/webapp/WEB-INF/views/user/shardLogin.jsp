<%@page import="java.math.BigInteger"%>
<%@page import="java.security.SecureRandom"%>
<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<link rel="stylesheet" href="/resources/css/common.css" />
<link rel="stylesheet" href="/resources/css/login.css" />
<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.7.1.min.js"></script>
<script src="/resources/js/loginCheck.js" type="text/javascript"></script>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@500&family=Noto+Sans:ital,wght@0,200;1,300&display=swap"
	rel="stylesheet">
<script>
	var result = "${result}";

	if (result == "success") {
		alert("성공적으로 회원가입이 완료되었습니다.\n저희 The Shard의 회원이 되주셔서 감사합니다.\n회원가입 기념으로 웰컴 쿠폰이 지급되었습니다.");
	} else if (result == "faild") {
		alert("회원가입 도중 에러가 발생했습니다.");
	}
	
	if(result == "updatePwd"){
		alert("비밀번호가 변경되었습니다.");
	}
	
	if(result == "noUser"){
		alert("아이디 또는 비밀번호가 틀렸습니다.");
	}
	
</script>
</head>
<body>
<%
    String clientId = "wpeMKe4_8YEoNX1CpngP";//애플리케이션 클라이언트 아이디값";
    String redirectURI = URLEncoder.encode("http://localhost:8181/shard/login/naver", "UTF-8");
    SecureRandom random = new SecureRandom();
    String state = new BigInteger(130, random).toString();
    String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code"
         + "&client_id=" + clientId
         + "&redirect_uri=" + redirectURI
         + "&state=" + state;
    session.setAttribute("state", state);
 %>
	<jsp:include page="../header.jsp"></jsp:include>

	<!-- 회원가입을 성공적으로 마쳤을 때 login페이지로 이동하면서 환영한다는 경고창을 띄우고 로그인을 할 수 있게 한다. -->

	<div class="loginWrap">
		<div class="login">
			<ul class="tab">
				<li>회원 로그인</li>
			</ul>
			<p>로그인</p>
			<form action="/login" method="post" id="loginForm" role="form">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
				<fieldset>
					<label for="username" class="email"> <span>이메일</span> <input
						type="text" name="username" id="email" />
					</label> <label for="userPwd" class="pwd"> <span>비밀번호</span> <input
						type="password" name="password" id="userPwd" />
					</label>
				</fieldset>
				<div class="findIDandPwd">
					<a href="/shard/findUser">비밀번호 찾기</a>
				</div>
				<a href="#" class="buttonLogin"><span>로그인</span></a> <a
					href="/shard/join" class="buttonJoin"><span>회원가입</span></a>

				<div class="sns_login">
					<p>SNS로그인</p>
					<ul>
						<li><a
							href="https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=4f8fd0ea2b58d54fc209c75db615c0e7&redirect_uri=http://localhost:8181/shard/login/oauth">
								<img
								src="https://k.kakaocdn.net/14/dn/btroDszwNrM/I6efHub1SN5KCJqLm1Ovx1/o.jpg"
								 alt="카카오 로그인 버튼" />
						</a>
							</li>
					</ul>
				</div>
			</form>
		</div>
	</div>


	<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>