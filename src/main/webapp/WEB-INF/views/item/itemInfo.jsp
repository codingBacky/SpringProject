<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- ajax 사용시 필수 -->
<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.7.1.min.js"></script>

<script src="/resources/js/itemInfo.js"></script>

<link rel="stylesheet" href="/resources/css/common.css" />
<link rel="stylesheet" href="/resources/css/ItemReply.css">
<link rel="stylesheet" href="/resources/css/ItemDetail.css">
<title>${itemInfo.itemName}</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
<script
	src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://kit.fontawesome.com/f21f7d3508.js"
	crossorigin="anonymous"></script>
<script type="text/javascript">
	var noBuy = "${noBuy}";
	if(noBuy == "noBuy"){
		alert("고객님은 해당 상품을 구매하지 않아 리뷰를 작성할 수 없습니다.");
	}
</script>
</head>
<body>
	<script
		src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.7.1.min.js"></script>
	<script>
		function toggleWishlist() {
			var email = $("#email").val();
			var itemNum = "${itemInfo.itemNum}";
			$.ajax({
				type : 'GET',
				url : '/item/wishlist',
				data : {
					itemNum : itemNum,
					email : email
				},
				success : function(response) {
					console.log(response);

					// 서버 응답에 따라 다른 alert 메시지 표시
					if (response === "ADD") {
						$("#heartIcon").css("color", "#FF0000");
					} else if (response === "REMOVE") {
						alert("위시리스트에서 제거되었습니다.");
						$("#heartIcon").css("color", "#fff");
					} else {
						alert("알 수 없는 응답입니다.");
					}
				},
				error : function(error) {
					console.error(error);
					// 에러 발생 시 동작을 추가할 수 있습니다.
				}
			});
		}
	</script>
	<jsp:include page="../header.jsp" />

	<sec:authorize access="isAuthenticated()">
		<input type="hidden" name="email" id="email"
			value='<sec:authentication property="principal.member.email"/>' />
	</sec:authorize>

	<input type="hidden" value="${itemInfo.sale}" id="itemSale" />

	<div class="itemDetailWrap">
		<div class="thumb-info">
			<img src="${itemInfo.mainImg}" alt="Main Image">
		</div>

		<div class="info">
			<div class="detil-title">
				<h3>${itemInfo.itemName}</h3>
				<div class="detil-sale">
					<fmt:formatNumber pattern="#,###원" value="${itemInfo.sale}" />
				</div>
			</div>
			<div class="option-tbl">
				<div class="made-where">제조사/제조국</div>
				<div class="made-from">대한민국/자체제작</div>
			</div>
			<div class="sizeSelect">
				<div class="form-group">
					옵션 <select class="form-control" id="sizeSelect"
						onchange="optionSelected(this.value)">
						<option value="사이즈 선택">사이즈 선택</option>
						<option value="M" id="sizeOptionM">M</option>
						<option value="L" id="sizeOptionL">L</option>
						<option value="XL" id="sizeOptionXL">XL</option>
					</select>
				</div>
			</div>

			<div class="container">
				<form action="/order/quickCheckOut" method="post" id="purchaseForm">
					<!-- 아이템 정보의 각 사이즈별 재고 정보를 담고 있는 hidden inputs -->
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" id="token" /> 
					<input type="hidden" name="itemCountM" value="${itemInfo.itemCountM}" /> 
					<input type="hidden" name="itemCountL" value="${itemInfo.itemCountL}" />
					<input type="hidden" name="itemCountXL" value="${itemInfo.itemCountXL}" />

					<!-- 기타 아이템 정보를 담고 있는 hidden inputs -->
					<input type="hidden" name="itemNum" value="${itemInfo.itemNum}" />
					<input type="hidden" name="itemRegDate" value="${itemInfo.itemRegDate}" /> 
					<input type="hidden" name="readCount" value="${itemInfo.readCount}" /> 
					<input type="hidden" name="categoryNum" value="${itemInfo.categoryNum}" />
					<input type="hidden" name="sale" value="${itemInfo.sale}"/>
					<input type="hidden" name="itemNum" value="${itemInfo.itemNum}" /> 
					<ul id="InnerOpt_01" class="option-row">
					</ul>
				</form>
			</div>
			<!-- 사이즈 옵션과 수량 입력을 담고 있는 영역 -->
			

			<div class="button_set">
				<!-- 장바구니 / 바로구매 비회원 -->
				<sec:authorize access="!isAuthenticated()">
					<a class="btn_cart" onclick="gologin()">장바구니 담기</a>
					<a class="btn_buy" onclick="gologin()">바로구매</a>
				</sec:authorize>

				<!-- 장바구니 / 바로구매 회원 -->
				<sec:authorize access="isAuthenticated()">
					<a class="btn_cart" onclick="addToCart()">장바구니 담기</a>
					<a class="btn_buy" id="directPurchaseBtn">바로구매</a>
				</sec:authorize>

				<sec:authorize access="!isAuthenticated()">
					<a href="#" class="ws_icon" onclick="gologin()"
						style="background-color: #fff;"> <span> <i
							id="heartIcon" class="fa-solid fa-heart" style="color: #fff;"></i>
					</span>
					</a>
				</sec:authorize>

				<sec:authorize access="isAuthenticated()">
					<a href="#" class="ws_icon" onclick="toggleWishlist()"
						style="${result == 1 ? 'color: red' : 'color: #fff'}"> <span>
							<i id="heartIcon" class="fa-solid fa-heart"></i>
					</span>
					</a>
				</sec:authorize>
			</div>

			<div id="innerOptTotal">
				<!-- 총 가격이 입력되는 부분 -->
			</div>
		</div>
	</div>
	<!-- itemDetailWrap -->

	<script type="text/javascript">
		$(function() {
			var email = $("#email").val();
			var itemNum = "${itemInfo.itemNum}";
			$.ajax({
				type : 'GET',
				url : '/item/checkWishlist',
				data : {
					itemNum : itemNum,
					email : email
				},
				success : function(response) {
					console.log(response);

					// 서버 응답에 따라 다른 alert 메시지 표시
					if (response == 1) {
						$("#heartIcon").css("color", "#FF0000");
					} else {
						$("#heartIcon").css("color", "#fff");
					}
				},
				error : function(error) {
					console.error(error);
					// 에러 발생 시 동작을 추가할 수 있습니다.
				}
			});
		});
	</script>

	<!-- 페이지 네비게이션 -->
	<div class="container" id="pageNav">
		<ul class="nav nav-pills nav-justified">
			<li class="nav-item"><a class="nav-link" href="#">상품상세</a></li>
			<li class="nav-item"><a class="nav-link" href="#replyAddFrom">REVIEW</a>
			</li>
			<li class="nav-item"><a class="nav-link" id="insertQnALink"
				href="#" onclick="redirectToInsertQnA()">QnA</a></li>
		</ul>
	</div>
	<!-- 페이지 네비게이션 -->

	<!-- 서브 이미지 영역 -->
	<div class="itemIMGWrap">
		<img src="${itemInfo.subImg1}" alt="Sub Image 1"><br> <img
			src="${itemInfo.subImg2}" alt="Sub Image 2"><br> <img
			src="${itemInfo.subImg3}" alt="Sub Image 3"><br> <img
			src="${itemInfo.subImg4}" alt="Sub Image 4"><br>
	</div>

	<div class="itemReplyWrap">
		<div class="container">
			<h2>Review List</h2>
			<input type="hidden" name="replyNum" value="${reply.replyNum}" /> <input
				type="hidden" name="itemNum" value="${reply.itemNum}" />
			<table class="table table-hover">
				<thead>
					<tr>
						<th>상품이미지</th>
						<th>제목</th>
						<th>내용</th>
						<th>평점</th>
						<th>리뷰 등록일</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="reply" items="${getReplyList}">
						<tr>
							<td class="reply-img"><c:choose>
									<c:when test="${not empty reply.img1}">
										<img src="${reply.img1}" alt="리뷰 이미지" />
									</c:when>
									<c:otherwise>
					                    -
					                </c:otherwise>
								</c:choose></td>
							<td>${reply.replyTitle}</td>
							<td>${reply.replyContent}</td>
							<td>${reply.starScore}</td>
							<td><fmt:formatDate pattern="yyyy-MM-dd" value="${reply.itemRepRegDate}" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>

		<div class="pageButton">
			<ul>
				<c:if test="${page.prev}">
					<li><a href="item/itemInfo/list?pageNum=${page.startPage -1}">이전</a></li>
				</c:if>
				<c:forEach var="num" begin="${page.startPage}" end="${page.endPage}">
					<li><a
						href="/item/itemInfo?pageNum=${num}&itemNum=${itemInfo.itemNum}"
						class="${page.pageNum eq num ? 'active' : '' }">${num}</a></li>
				</c:forEach>
				<c:if test="${page.next}">
					<li><a href="/item/itemInfo?pageNum=${page.endPage + 1}">다음</a></li>
				</c:if>
			</ul>
		</div>

		<!-- 리뷰 추가 폼 -->
		<div class="container" id="replyAddFrom">
		    <sec:authorize access="isAuthenticated()">
			    <h2>Review</h2>
			    <p>고객님의 소중한 리뷰를 작성해주세요</p>
		    <!-- sec:authorize로 인증된 사용자만 폼을 보이도록 설정 -->
		        <form id="reviewForm" name="reviewForm" action="/item/insertReply?${_csrf.parameterName}=${_csrf.token}" method="post" enctype="multipart/form-data" onsubmit="return validateReviewForm();"> 
		        	<input type="hidden" name="email" value='<sec:authentication property="principal.member.email"/>' />
		            <input type="hidden" name="itemNum" value="${itemInfo.itemNum}" />
		            <input type="hidden" name="pageNum" value="${page.pageNum}" />
		            <input type="text" class="form-control" name="replyTitle" placeholder="리뷰 제목" /><br />
		            <textarea class="form-control" rows="4" id="comment" name="replyContent" placeholder="이곳에 리뷰 내용을 작성해 주세요"></textarea><br />
		            <input type="number" name="starScore" class="form-control" placeholder="별점(1~5)" min="1" max="5" /><br />
		            <input type="file" name="img" /><br />
		            이미지 파일만 등록 가능합니다.<br />
		           <input type="submit" id="submitReviewBtn" class="btn btn-primary value="리뷰 작성" />
		        </form>
		    </sec:authorize>
		</div>
		<div class="writeBtn">
			<button type="button" onclick="redirectToInsertQnA()">상품 문의</button>
		</div>
	</div>
	<!-- itemReplyWrap -->
	<jsp:include page="../footer.jsp" />

</body>
</html>