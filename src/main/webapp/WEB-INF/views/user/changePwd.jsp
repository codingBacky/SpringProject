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
<script src="/resources/js/findUser.js" type="text/javascript"></script>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@500&family=Noto+Sans:ital,wght@0,200;1,300&display=swap"
	rel="stylesheet">
</head>
<body>
	<jsp:include page="../header.jsp"></jsp:include>

	<!-- 회원가입을 성공적으로 마쳤을 때 login페이지로 이동하면서 환영한다는 경고창을 띄우고 로그인을 할 수 있게 한다. -->

	<div class="loginWrap">
		<div class="login">
			<ul class="tab">
				<li>비밀번호 변경</li>
			</ul>
			<form action="/shard/updatePwd" method="post" id="updatePwd">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
				<input type="hidden" name="email" value="${email}" />
				<fieldset>
					<label for="email" class="email">
						<span style="width:70px">새 비밀번호</span>
						<input type="password" name="userPwd" id="userPwd" style="width:280px" />
					</label> 
					<label for="findPwd" class="email"> 
						<span style="width:70px">새 비밀번호 확인</span>
					 	<input type="password" id="userPwdCheck" style="width:280px"/>
					</label> 
						<button type="button" onclick="pwdDoubleCheck()" style="width:200px; margin:0 auto; font-size:16px; padding:10px 0;">확인</button>
				</fieldset>
			</form>
		</div>
	</div>


	<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>