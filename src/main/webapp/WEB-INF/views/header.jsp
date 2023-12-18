<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<script src="https://kit.fontawesome.com/f21f7d3508.js" crossorigin="anonymous"></script>
<script type="text/javascript">
	$(function() {
		$('.logoutBtn').click(function(e) {
			e.preventDefault();
			$('#logoutForm').submit();
		});

		$('.myPage')
				.click(
						function(e) {
							e.preventDefault();
							var email = $(this).attr("href");
							$('#logoutForm')
									.append(
											"<input type='hidden' name='email' value="+email+" />");
							$('#logoutForm').attr("action", "/shard/myPage");
							$('#logoutForm').submit();
						});
		$('.cartBtn').click(function(e){
			e.preventDefault();
			
			$('#cartForm').submit();
		})
	})
</script>
<div class="header">
	<div class="topRightNav">
		<sec:authorize access="isAuthenticated()">
			<!-- 사용자가 로그인되어 있는 경우에만 이 부분이 표시됩니다. -->
			<form action="/shard/logout" method="post" id="logoutForm">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
				<ul>
					<li><span
						style="font-size: 14px; color: #333; font-weight: 400;"> <sec:authentication
								property="principal.member.userName" />님
					</span></li>

					<%
					String token = (String) session.getAttribute("token");
					%>
					<!-- 기타 로그인된 상태에서의 메뉴 등 추가 -->
					<c:choose>
						<c:when test="${not empty token}">
							<li><a
								href="https://kauth.kakao.com/oauth/logout?client_id=4f8fd0ea2b58d54fc209c75db615c0e7&logout_redirect_uri=http://localhost:8181/shard/kakaoLogout">로그아웃</a></li>
						</c:when>
						<c:otherwise>
							<li><a href="#" class="logoutBtn">로그아웃</a></li>
						</c:otherwise>
					</c:choose>
					<sec:authorize access="hasRole('ROLE_ADMIN')">
						<li><a href="/admin/">관리자페이지</a></li>
					</sec:authorize>
					<sec:authorize access="hasRole('ROLE_USER')">
						<li><a
							href='<sec:authentication property="principal.member.email"/>'
							class="myPage">마이페이지</a></li>
					</sec:authorize>
					<li><a href="/shard/notice?pageNum=1">공지사항</a></li>
					<li><a href="/qna/list?pageNum=1">Q&A</a></li>
				</ul>
			</form>
		</sec:authorize>

		<sec:authorize access="!isAuthenticated()">
			<!-- 사용자가 로그인되어 있지 않은 경우에만 이 부분이 표시됩니다. -->
			<ul>
				<!-- 로그인되지 않은 상태일 때 표시할 내용 -->
				<li><a href="/shard/login">로그인</a></li>
				<li><a href="/shard/join">회원가입</a></li>
				<li><a href="/shard/notice?pageNum=1">공지사항</a></li>
				<li><a href="/qna/list?pageNum=1">Q&A</a></li>

			</ul>
		</sec:authorize>
	</div>
	<div class="mainNav">
		<a href="/shard/" style="color: #000; font-family: cursive;"> <img
			src="/resources/logo.png" alt="로고 사진" />
		</a>
		<ul>
			<li><a href="/itemSearch/list/category/2">HoodZipup</a></li>
			<li><a href="/itemSearch/list/category/11">Mtm</a></li>
			<li><a href="/itemSearch/list/category/1">Hood</a></li>
			<li><a href="/itemSearch/list/category/8">Cotton</a></li>
			<li><a href="/itemSearch/list/category/6">Denim</a></li>
			<li><a href="/itemSearch/list/category/4">Shirts</a></li>
			<li><a href="/itemSearch/list/category/9">Acc</a></li>
		</ul>

		<form action="/itemSearch/list" method="get">
			<input type="text" class="search" name="itemName" value="" /> <input
				type="hidden" name="color" value="" />
			<button type="submit" class="searchBtn">
				<i class="fa-solid fa-magnifying-glass" id="glass"></i>
			</button>
		</form>
	</div>

	<form action="/order/cart" method="post" id="cartForm">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<sec:authorize access="isAuthenticated()">
			<input type="hidden" name="email" value='<sec:authentication property="principal.member.email"/>' />
		</sec:authorize>
		<div class="Cartbtn">
			<a href="#" class="cartBtn"><img src="/resources/shopping-bag.svg" />
				<c:choose>
					<c:when test="${not empty cartItemCount}">
						<span>${cartItemCount}</span>
					</c:when>
					<c:otherwise>
						<span>0</span>
					</c:otherwise>
				</c:choose>
			</a>
		</div>
	</form>
</div>
