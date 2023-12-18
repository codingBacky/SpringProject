<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.FileReader"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>간편 회원가입</title>
<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.7.1.min.js"></script>
<script src="/resources/js/address.js" type="text/javascript"></script>
<script src="/resources/js/userGet.js" type="text/javascript"></script>
<link rel="stylesheet" href="/resources/css/common.css" />
<link rel="stylesheet" href="/resources/css/join.css" />
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400&family=Noto+Sans:ital,wght@0,200;1,300&display=swap"
	rel="stylesheet">
<script
	src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
</head>
<body>

	<jsp:include page="../header.jsp"></jsp:include>

	<div class="joinWrap">
		<div class="join">
			<h2>회원정보</h2>

			<div class="join_box">
				<form action="/shard/updateUser" method="post" id="updateUser">
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
					<ul>
						<li><label for="userName">이름</label> <input type="text"
							name="userName" id="userName" value="${user.userName}"
							style="opacity: 1; left: 80px;" readonly="readonly" /></li>
						<li><label for="email">이메일</label> <input type="text"
							name="email" id="email" value="${user.email}"
							style="opacity: 1; left: 80px;" disabled="disabled" />
						<li><label for="phone">휴대폰번호</label> <input type="text"
							name="phone" id="phone" maxlength="11" value="${user.phone}"
							style="opacity: 1; left: 110px;" readonly="readonly" /></li>
						<li><label for="birth">생년월일</label> <input type="text"
							name="birth" id="birth" value='<fmt:formatDate value="${user.dob}" pattern="yyyy-MM-dd" />'
							style="opacity: 1; left: 110px;" disabled="disabled" /></li>
						<%
							String token = (String)session.getAttribute("token");
						%>
						<c:if test="${token eq null}">
							<li><label for="userPwd">비밀번호</label> <input type="password"
							name="userPwd" id="userPwd" style="opacity: 1; left: 80px;" readonly="readonly" value=""/></li>
						<li><label for="pwdCheck">비밀번호 확인</label> <input type="password"
							name="pwdCheck" id="pwdCheck" style="opacity: 1; left: 110px;" readonly="readonly" /></li>
						</c:if>
					</ul>
				</form>

				<div class="getBtn">
					<sec:authentication property="principal" var="pinfo" />
					<sec:authorize access="isAuthenticated()">
						<c:set var="showReinsertButton" value="true" />

						<c:if test="${pinfo.member.email == user.email}">
							<button type="button" onclick="update()" class="update">회원정보 수정</button>
						</c:if>

						<c:if test="${pinfo.member.email == user.email}">
							<button type="button" onclick="updateUser()" class="updateUser" style="display:none">수정</button>
						</c:if>
					</sec:authorize>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>