<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<title>check out</title>
<link href="/resources/css/common.css" rel="stylesheet">
<link href="/resources/css/order.css" rel="stylesheet">
<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="/resources/js/order.js"></script>
<script type="text/javascript" src="/resources/js/address.js"></script>
<script type="text/javascript" src="/resources/js/payment.js"></script>
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
<script type="text/javascript"
	src="https://cdn.iamport.kr/js/iamport.payment-1.1.8.js"></script>
</head>

<body>
	<jsp:include page="../header.jsp"></jsp:include>
	<!-- Page Header Start -->

	<input type="hidden" name="${_csrf.parameterName}"
		value="${_csrf.token}" id="token" />

	<div
		class="d-flex flex-column align-items-center justify-content-center"
		style="min-height: 300px">
		<h1 class="font-weight-semi-bold text-uppercase mb-3">Checkout</h1>
		<div class="d-inline-flex">
			<p>01 장바구니 ></p>
			<p>
				<a href="">&nbsp;02 주문결제 >&nbsp;</a>
			</p>
			<p>03 주문완료</p>

		</div>
	</div>

	<div class="container-fluid pt-5">
		<div class="row px-xl-5">
			<div class="col-lg-8">
				<div class="mb-4">
					<h4 class="font-weight-semi-bold mb-4">Billing Address</h4>
					<div class="row">
						<div class="col-md-6 form-group">
							<label>주문자 이름</label> <input class="form-control userName"
								type="text" value="${customer.userName}" readonly="readonly">
						</div>
						<div class="col-md-6 form-group">
							<label>Mobile No</label> <input class="form-control phone"
								type="text" value="${customer.phone}" readonly="readonly">
						</div>
						<div class="col-md-6 form-group">
							<label>기본배송지 이름</label> <input class="form-control " type="text"
								value="${deliverAddr.addrName}" readonly="readonly">
						</div>
						<div class="col-md-6 form-group">
							<label>우편번호</label> <input class="form-control postcode"
								type="text" value="${deliverAddr.postcode}" readonly="readonly">
						</div>
						<div class="col-md-6 form-group">
							<label>도로명주소</label> <input class="form-control roadAddress"
								type="text" value="${deliverAddr.roadAddress}"
								readonly="readonly">
						</div>
						<div class="col-md-6 form-group">
							<label>상세주소</label> <input class="form-control detailAddr"
								type="text" value="${deliverAddr.detailAddr}"
								readonly="readonly">
						</div>
						<div class="col-md-12 form-group">
							<div class="custom-control custom-checkbox">
								<input type="checkbox" class="custom-control-input"
									id="newaccount"> <label class="custom-control-label"
									for="newaccount">주문자정보 변경</label>
							</div>
						</div>
						<div class="col-md-12 form-group">
							<div class="custom-control custom-checkbox">
								<input type="checkbox" class="custom-control-input" id="shipto">
								<label class="custom-control-label" for="shipto"
									data-toggle="collapse" data-target="#shipping-address">다른주소
									입력</label>
							</div>
						</div>
					</div>
				</div>

				<div class="collapse mb-4" id="shipping-address">
					<h4 class="font-weight-semi-bold mb-4">Shipping Address</h4>
					<div class="row">
						<div class="col-md-6 form-group">
							<label>주문자 이름</label> <input class="form-control orderName"
								type="text" placeholder="이름을 입력해주세요"
								onfocus="this.placeholder = ''"
								onblur="this.placeholder = '이름을 입력해주세요'">
						</div>
						<div class="col-md-6 form-group">
							<label>주문자 연락처</label> <input class="form-control orderPhone"
								type="text" placeholder="-제외하고 입력해주세요"
								onfocus="this.placeholder = ''"
								onblur="checkPhoneNumber(); this.placeholder = '-제외하고 입력해주세요'">
						</div>

						<p style="font-size: 18px; font-weight: 600; color: #000;">주소</p>
						<div class="address">
							<input type="text" id="postcode" placeholder="우편번호"
								name="postcode">
							<button type="button" onclick="execDaumPostcode()"
								class="adress_find">우편번호 찾기</button>
							<br> <input type="text" id="roadAddress" placeholder="도로명주소"
								name="roadAddress"> <input type="text" id="jibunAddress"
								placeholder="지번주소" name="jibunAddress">
							<p id="guide" style="color: #999; display: none"></p>
							<input type="text" id="detailAddress" placeholder="상세주소"
								name="detailAddr" onfocus="this.placeholder = ''"
								onblur="this.placeholder = '상세주소'">
						</div>

						<div class="col-md-12 form-group">
							<div class="custom-control custom-checkbox">
								<input type="checkbox" class="custom-control-input"
									id="addressSaveCheck"> <label
									class="custom-control-label" for="addressSaveCheck"
									data-toggle="collapse" data-target="#addressSave">주소
									저장하기</label>
							</div>
							<div class="collapse mb-4" id="addressSave">
								<div class="col-md-6 form-group">
									<label>배송지 이름</label> <input
										class="form-control addressSaveInput" type="text"
										placeholder="저장할 배송지 이름을 입력해주세요  ex) 집, 회사"
										onfocus="this.placeholder = ''"
										onblur="this.placeholder = '저장할 배송지 이름을 입력해주세요 ex) 집, 회사'">
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-lg-4">
				<div class="card border-secondary mb-5">
					<div class="card-header bg-secondary border-0">
						<h4 class="font-weight-semi-bold m-0">결제금액</h4>
					</div>
					<div class="card-body">
						<div class="d-flex justify-content-between">
							<input type="hidden" name="totalPrice" id="totalPrice"
								value="${totalPrice}" />
							<h5 class="font-weight-medium mb-3">총 상품금액</h5>
							<h5 class="totalPrice">
								<fmt:formatNumber pattern="#,###원">${totalPrice}</fmt:formatNumber>
							</h5>
						</div>
						<hr class="mt-0">
						<div class="d-flex justify-content-between">
							<input type="hidden" name="deliveryCharge" id="deliveryCharge"
								value="${deliveryCharge}" />
							<h6 class="font-weight-medium">배송비</h6>
							<h6 class="font-weight-medium deliveryCharge">
								<fmt:formatNumber pattern="#,###원">${deliveryCharge}</fmt:formatNumber>
							</h6>
						</div>
						<div class="d-flex justify-content-between mb-3 pt-1">
							<h6 class="font-weight-medium">총 주문금액</h6>
							<h6 class="font-weight-medium">
								<fmt:formatNumber pattern="#,###원">${TotalPriceWithShipping}</fmt:formatNumber>
							</h6>
						</div>
						<hr class="mt-0">
						<div class="d-flex justify-content-between">
							<h6 class="font-weight-medium">적립금 할인</h6>
							<h6 class="font-weight-medium pointDiscount">
								<fmt:formatNumber pattern="#,###원">0</fmt:formatNumber>
							</h6>
						</div>
						<div class="d-flex justify-content-between mb-3 pt-1">
							<h6 class="font-weight-medium">쿠폰 할인</h6>
							<h6 class="font-weight-medium couponDiscount">
								<fmt:formatNumber pattern="#,###원">0</fmt:formatNumber>
							</h6>
						</div>
						<div class="card-footer border-secondary bg-transparent">
							<div class="d-flex justify-content-between mt-2">
								<h5 class="font-weight-bold">최종 결제금액</h5>
								<h5 class="font-weight-bold TotalPriceWithShipping">
									<fmt:formatNumber pattern="#,###원">${TotalPriceWithShipping}</fmt:formatNumber>
								</h5>
							</div>
						</div>
					</div>
				</div>

				<div class="card border-secondary mb-5">
					<div class="card-header bg-secondary border-0">
						<h4 class="font-weight-semi-bold m-0">Payment</h4>
					</div>
					<div class="card-body">
						<div class="form-group">
							<div class="custom-control custom-radio">
								<input type="radio" class="custom-control-input" name="payment"
									id="paypal"> <label class="custom-control-label"
									for="paypal">Paypal</label>
							</div>
						</div>
						<div class="form-group">
							<div class="custom-control custom-radio">
								<input type="radio" class="custom-control-input" name="payment"
									id="directcheck"> <label class="custom-control-label"
									for="directcheck">Direct Check</label>
							</div>
						</div>
						<div class="">
							<div class="custom-control custom-radio">
								<input type="radio" class="custom-control-input" name="payment"
									id="banktransfer"> <label class="custom-control-label"
									for="banktransfer">Bank Transfer</label>
							</div>
						</div>
					</div>
					<div class="card-footer border-secondary bg-transparent">
						<button
							class="btn btn-lg btn-block btn-primary font-weight-bold my-3 py-3"
							onclick="requestPay()">Place Order</button>
					</div>

				</div>
			</div>
			<div class="col-lg-8">
				<div class="col-md-5 form-group">
					<label>배송메세지</label> <select id="deliveryMessageSelect"
						class="form-select pt-1 mt-4 w-100" onchange="clearTextarea()"
						style="height: 33px; vertical-align: middle;">
						<option>배송메세지 선택</option>
						<option>조심히 안전히 와주세요</option>
						<option>문 앞에 두고 가주세요</option>
						<option>관리실에 맡겨주세요</option>
						<option>도착전 전화부탁드려요</option>

					</select>
				</div>
				<div class="col-md-7 form-group">
					<label>배송메세지 입력</label>          
					<FORM METHOD=POST name="sms">
						   
						<TEXTAREA NAME="txta" id="txta" ROWS="5" COLS="52"
							onkeyup="fc_chk_byte(this, 200)" oninput="updateSelect(this)"
							placeholder="기타 내용을 직접 입력해주세요(100자 내외)"
							onfocus="this.placeholder = ''"
							onblur="this.placeholder = '기타 내용을 직접 입력해주세요(100자 내외)'"></TEXTAREA>
						    <br />     <input type="text" size="4" name="char_byte"
							id="char_byte" readonly> /200 bytes (* 영문/숫자 기준 200자, 한글
						기준 100자까지 입력 가능합니다.)  
					</FORM>
				</div>
			</div>
		</div>
	</div>

	<div class="container-fluid">
		<div class="row px-xl-5">
			<div class="col-lg-8 table-responsive">
				<table class="table text-left">
					<thead>
						<tr>
							<th>상품명</th>
							<td>사이즈</td>
							<th>수량</th>
							<th>적립</th>
							<th>가격</th>
							<th></th>
						</tr>
					</thead>

					<tbody class="align-middle">
						<c:if test="${empty itemList}">
							<tr>
								<td>
									<div class="text-center">
										<h3 class="font-weight-semi-bold">주문하려는 상품이 없습니다.</h3>
									</div>
								</td>
							</tr>
						</c:if>
						<c:if test="${not empty itemList}">
							<input type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" id="token" />
							<input type="hidden" id="email" name="email"
								value="${customer.email}" />
							<input type="hidden" id="cartNum" name="cartNum"
								value="${cartNum}" />

							<c:forEach var="itemNum" items="${itemNumList}" varStatus="loop">
								<c:forEach var="item" items="${itemList}" varStatus="itemLoop">
									<c:if test="${itemNum == item.itemNum}">
										<input type="hidden" name="itemNum" value="${item.itemNum}" />
										<input type="hidden" name="cnts"
											value="${extractCartItemCnts[loop.index]}" />
										<input type="hidden" name="sizes" value="${sizes[loop.index]}" />
										<tr>
											<td><img src="${item.mainImg}" alt=""
												style="width: 50px;"> ${item.itemName}</td>
											<td>${sizes[loop.index]}</td>
											<td>
												<div class="input-group quantity mx-auto"
													style="width: 100px;">
													<!-- 추출된 extractCartItemCnts 리스트의 현재 인덱스에 해당하는 값 -->
													<input type="text"
														class="form-control form-control-sm text-center itemCount"
														name="cnt" value="${extractCartItemCnts[loop.index]}"
														readonly="readonly" /> <input type="hidden"
														class="itemRealSale" name="itemList[${loop.index}].sale"
														value="${item.sale}" />
												</div>
											</td>
											<!-- 추출된 itemRewardPoints 리스트의 현재 인덱스에 해당하는 값 -->
											<td class="align-middle">${itemRewardPoints[loop.index]}</td>
											<td class="align-middle itemSale">${totalPriceList[loop.index]}</td>

											<td class="align-middle">
											</td>
										</tr>
									</c:if>
								</c:forEach>
							</c:forEach>
						</c:if>
					</tbody>
					<tfoot class="align-middle">
						<tr>
							<td>
								<div class="cart-tfoot text-right">
									<span class="cart-price">총 상품금액</span> <span class="cart-won">
										<fmt:formatNumber pattern="#,###원">${totalPrice}</fmt:formatNumber>
									</span>
								</div>
							</td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
					</tfoot>
				</table>
			</div>
			<div class="container-fluid pt-5">
				<div class="row px-xl-5">
					<div class="col-lg-8">
						<div class="mb-4">
							<h4 class="font-weight-semi-bold mb-4">적립금 사용</h4>
							<!-- 추출된 extractCartItemCnts 리스트의 현재 인덱스에 해당하는 값 -->
							<span class="mr-2">적립예정금액 : ${totalRewardPoints} 원</span>
							<div class="d-flex mb-4">
								<input type="text"
									class="form-control form-control-sm text-center mr-2 w-25"
									id="pointInput" /> <input type="hidden" name="point"
									id="point" value="${customer.point}">
								<button class="btn btn-primary usePoint">사용하기</button>
								<span>가용적립금 : </span> <span class="ml-2 customerPoint">${customer.point}</span>
								<span> 원</span>
							</div>
							<span class="mr-2 pointAlert">❗ 적립금은 5000원 이상 100원 단위로
								사용가능합니다 :) </span>
						</div>
					</div>
				</div>
			</div>

			<div class="container-fluid pt-5">
				<div class="row px-xl-5">
					<div class="col-lg-8">
						<div class="mb-4">
							<h4 class="font-weight-semi-bold mb-4">쿠폰 사용</h4>
							<select id="selectbox" name="selectbox" onchange="handleDiscountedAmount(); issueNumInput(); reqInput();"
						        class="form-select pt-1 mt-4" style="height: 33px; width: 40%; vertical-align: middle;">
						    <c:if test="${not empty couponList}">
						        <option>사용가능한 쿠폰</option>
						    </c:if>
						    <c:choose>
						        <c:when test="${empty couponList}">
						            <option>사용가능한 쿠폰이 없습니다</option>
						        </c:when>
						        <c:otherwise>
						            <c:forEach var="coupon" items="${couponList}" varStatus="loop">
						                <option data-issue-num="${coupon.issueNum}" data-coupon-discount="${matchedCouponInfoList[loop.index].discountRate}">
						                    ${coupon.issueDate} ~ ${coupon.issueED}
						                    ${matchedCouponInfoList[loop.index].couponName}
						                    ${matchedCouponInfoList[loop.index].discountRate}
						                    ${discountDivision[loop.index]}</option>
						            </c:forEach>
						        </c:otherwise>
						    </c:choose>
						</select> <span class="mr-2 couponAlert">❗ 적립금을 뺀 결제금액에서 할인율이 적용됩니다</span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>