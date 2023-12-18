<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<title>check out</title>
<link href="/resources/css/common.css" rel="stylesheet">
<link href="/resources/css/order.css" rel="stylesheet">
<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.7.1.min.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://kit.fontawesome.com/e73d217c71.js" crossorigin="anonymous"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
</head>

<body>
	<form>
	</form><jsp:include page="../header.jsp"></jsp:include>
	<!-- Page Header Start -->

	<div class="container-fluid pt-5">
		<div class="row px-xl-5">
			<div class="col-lg-8">
				<div class="mb-4">
					<h4 class="font-weight-semi-bold mb-4">장바구니 담기 완료</h4>
				</div>
				<div class="col-lg-8">
					<form action="/order/cart" id="purchaseForm">
						<input type="hidden" name="email" value='<sec:authentication property="principal.member.email"/>'/>
<%-- 					<c:forEach var="item" items="${itemList}" varStatus="loop"> --%>
<!-- 						<div> -->
<%-- 							<img src="${item.mainImg}" alt="" style="width: 50px;">${item.itemName} --%>
<!-- 						</div> -->
<%-- 					</c:forEach> --%>
						<button type="submit" class="btn btn-block btn-dark my-3 py-3" onclick="goCart()">Go Cart</button>
						<button class="btn btn-block btn-dark my-3 py-3"
								onclick="window.location.href='/main'">Go Home</button>
					</form>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	function goCart(){
		// 폼 서브밋
	    $("#purchaseForm").submit();
	}
	</script>
	
</body>

</html>