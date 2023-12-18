<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<title>check out</title>
<link href="/resources/css/common.css" rel="stylesheet">
<link href="/resources/css/order.css" rel="stylesheet">
<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.7.1.min.js"></script>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://kit.fontawesome.com/e73d217c71.js"
	crossorigin="anonymous"></script>
<script
	src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
</head>

<body>
	<jsp:include page="../header.jsp"></jsp:include>
	<!-- Page Header Start -->

	<div
		class="d-flex flex-column align-items-center justify-content-center"
		style="height: 200px">
		<h1 class="font-weight-semi-bold text-uppercase mb-3">Order
			Complete</h1>
		<div class="d-inline-flex">
			<p>01 장바구니 ></p>
			<p>&nbsp;02 주문결제 ></p>
			<p>
				<a href="">&nbsp;03 주문완료</a>
			</p>
		</div>
	</div>

	<div class="container-fluid text-center">
		<div style="margin-bottom:50px;">
			<h4 class="font-weight-semi-bold mb-4">주문이 성공적으로 완료되었습니다</h4>
		</div>
		<div class="row px-xl-5">
			<div class="col-lg-8 mx-auto">
				<div>
					<form action="/shard/myPage" method="post">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						<input type="hidden" name="email"
							value='<sec:authentication property="principal.member.email"/>' />

						<div class="d-flex justify-content-between" style="margin: 0 auto">
							<!-- justify-content-between 클래스 추가 -->
							<button type="submit"
								class="btn btn-block btn-dark my-3 py-3 flex-fill mr-2">주문확인</button>
							<button type="button"
								class="btn btn-block btn-dark my-3 py-3 flex-fill" onclick="location.href='/shard/'">home</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>

</html>