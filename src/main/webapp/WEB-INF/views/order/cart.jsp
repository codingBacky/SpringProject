<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<title>Cart</title>
<link href="/resources/css/common.css" rel="stylesheet">
<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="/resources/js/cart.js"></script>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://kit.fontawesome.com/e73d217c71.js"
	crossorigin="anonymous"></script>
</head>

<body>
	<jsp:include page="../header.jsp"></jsp:include>
	<!-- Page Header Start -->

	<div
		class="d-flex flex-column align-items-center justify-content-center"
		style="min-height: 300px">
		<h1 class="font-weight-semi-bold text-uppercase mb-3">ORDER LIST</h1>
		<div class="d-inline-flex">
			<p>
				<a href="">01 장바구니 >&nbsp;</a>
			</p>
			<p>02 주문결제 > 03 주문완료</p>

		</div>
	</div>

	<!-- Cart Start -->
	<div class="container-fluid">
		<div class="row px-xl-5">
			<div class="col-lg-8 table-responsive">
				<table class="table text-left">
					<colgroup>
						<col width="*"/>
						<col width="90"/>
						<col width="140"/>
						<col width="70"/>
						<col width="70"/>
						<col width="90"/>
					</colgroup>
					<thead>
						<tr>
							<th><input type="checkbox" id="selectAllCheckbox" /> 상품명</th>
							<th style="text-align:center;">사이즈</th>
							<th style="text-align:center;">수량</th>
							<th>적립</th>
							<th>가격</th>
							<th></th>
						</tr>
					</thead>

					<tbody class="align-middle">
						<c:if test="${empty itemList}">
							<td>
								<div>장바구니에 담긴 상품이 없습니다.</div>
							</td>
						</c:if>
						<c:if test="${not empty itemList}">
							<form id="checkoutForm" action="/order/checkOut" method="POST">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" id="token"/>
								<input type="hidden" id="email" name="email" value="${email}" />
								<input type="hidden" id="cartNum" name="cartNum" value="${cartNum}" /> 
								<input type="hidden" id="totalPrice" name="totalPrice" value="${totalPrice}" /> 
								<input type="hidden" id="TotalPriceWithShipping" name="TotalPriceWithShipping" value="${TotalPriceWithShipping}" />
								<input type="hidden" id="pointRate" name="pointRate" value="${pointRate}" /> 
								<input type="hidden" id="deliveryCharge" name="deliveryCharge" value="${deliveryCharge}" />
								<c:forEach var="itemNum" items="${itemNumList}" varStatus="loop">
									<c:forEach var="item" items="${itemList}" varStatus="itemLoop">
                                 		<c:if test="${itemNum == item.itemNum}">
											<tr>
												<td><label class="form-check-label" for="flexCheckChecked_${loop.index}"> 
												<input type="checkbox" class="itemCheckbox" id="flexCheckChecked_${loop.index}">
												<input type="hidden" name="itemList[${loop.index}].itemName" value="${item.itemName}" /> 
												<input type="hidden" name="itemList[${loop.index}].mainImg" value="${item.mainImg}" /> 
												<input type="hidden" name="itemNum" class="itemNum" value="${itemNum}" />
												<img src="${item.mainImg}" alt="" style="width: 50px;">
													${item.itemName}
												</label></td>
												<td class="align-middle" style="text-align: center;">
												${sizes[loop.index]}
												</td>
												<td class="align-middle">
													<div class="input-group quantity mx-auto"
														style="width: 100px;">
														<div class="input-group-btn">
															<button class="btn btn-sm btn-light btn-minus minus">
																<i class="fa fa-minus"></i>
															</button>
														</div>
														<input type="text"
															class="form-control form-control-sm text-center itemCount"
															value="${extractCartItemCnts[loop.index]}" min="1" max="50"
															name="extractCartItemCnts" />
														<input type="hidden" class="itemRealSale" value="${sale[loop.index]}" /> 
														<input type="hidden" class="itemRealPoint" value="${itemRewardPoints[loop.index]}" /> 
														<input type="hidden" class="totalPriceList" name="totalPriceList" value="${totalPriceList[loop.index]}" /> 
														<input type="hidden" class="itemRewardPoints" name="itemRewardPoints" value="${itemRewardPoints[loop.index]}" />
														<input type="hidden" class="sizes" name="sizes" value="${sizes[loop.index]}" />
														<div class="input-group-btn">
															<button class="btn btn-sm btn-light btn-plus plus">
																<i class="fa fa-plus"></i>
															</button>
														</div>
													</div>
												</td>
												<td class="align-middle reward">${itemRewardPoints[loop.index]}</td>
												<td class="align-middle itemSale">${totalPriceList[loop.index]}</td>
												<td class="align-middle">
													<button class="btn btn-sm btn-light delete-item-btn" data-item-num="${itemNum}">
														<i class="fa fa-times"></i>
													</button>
												</td>
											</tr>
										 </c:if>
                             		</c:forEach>
								</c:forEach>
							</form>
						</c:if>
					</tbody>
					<tfoot class="align-middle">
						<tr>
							<td>
								<div class="cart-tfoot text-right">
									<span class="cart-price">총 상품금액</span> <span class="cart-won">
										<fmt:formatNumber pattern="#,###원">${totalPrice}</fmt:formatNumber></span>
								</div>
							</td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
					</tfoot>
				</table>

				<c:if test="${not empty itemList}">
					<!-- itemList가 비어있지 않을 때만 버튼을 보여줌 -->
					<button type="button" class="btn btn-dark" id="allDeleteButton">전체상품삭제</button>
					<button type="button" class="btn btn-light"
						onclick="deleteSelectedItems()">선택상품삭제</button>
				</c:if>
			</div>

			<div class="col-lg-4">
				<div class="card border-secondary">
					<div class="card-header bg-dark border-0">
						<h5 class="font-weight-semi-bold text-white text-center m-0">예상
							결제금액</h5>
					</div>
					<div class="card-body">
						<div class="d-flex justify-content-between">
							<h6 class="font-weight-medium">총 상품금액</h6>
							<h6 class="font-weight-medium totalPriceUpdate"><fmt:formatNumber pattern="#,###원">${totalPrice}</fmt:formatNumber></h6>
						</div>
						<div class="d-flex justify-content-between">
							<h6 class="font-weight-medium">배송비</h6>
							<h6 class="font-weight-medium"><fmt:formatNumber pattern="#,###원">${deliveryCharge}</fmt:formatNumber></h6>
						</div>
					</div>
					<div class="card-footer border-secondary bg-transparent">
						<div class="d-flex justify-content-between">
							<h5 class="font-weight-bold">총 주문금액</h5>
							<h5 class="font-weight-bold TotalPriceWithShippingUpdate"><fmt:formatNumber pattern="#,###원">${TotalPriceWithShipping}</fmt:formatNumber></h5>
						</div>
						<button class="btn btn-block btn-dark my-3 py-3"
							onclick="selectOrder()">주문하기</button>
					</div>
				</div>
			</div>
			<button type="button" onclick="placeOrder()"
				class="btn btn-block btn-dark my-3 py-3">전체상품 주문하기</button>
		</div>
	</div>
	<!-- Cart End -->



	<jsp:include page="../footer.jsp"></jsp:include>




	<script>
		//선택한 항목만 더한 값을 보여줌

		//주문으로 데이터 넘기기
		function placeOrder() {
			document.getElementById("checkoutForm").submit();

		}
	</script>
</body>

</html>