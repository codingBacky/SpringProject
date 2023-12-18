<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Item Management</title>
<link rel="stylesheet" href="/resources/css/common.css" />
<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.7.1.min.js"></script>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
	crossorigin="anonymous"></script>
<link rel="stylesheet" href="/resources/css/adminItem.css" />
<script type="text/javascript" src="/resources/js/admin.js"></script>
<script type="text/javascript">
	var result = "${result}";
	if(result == "deleteSuccess"){
		alert("상품이 정상적으로 삭제되었습니다.");
	}
</script>
</head>
<body>
	<jsp:include page="adminHeader.jsp" />

	<div class="adminItemWrap">
		<div class="adminItem">
			<h2>관리자 상품 관리</h2>
			
			<a href="/admin/itemInsert" class="itemInsert">상품추가</a>

			<form action="/admin/itemSearch" method="post">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
				<input type="hidden" name="pageNum" value="1" />
				<div class="adminItemSearch">
					<label for="itemSearch">상품검색</label> <input type="text"
						name="itemName" id="itemName" /> <input type="submit"
						value="검색" />
				</div>
			</form>
		</div>

		<table class="table">
			<thead>
				<tr>
					<th scope="col">상품번호</th>
					<th scope="col">메인 이미지</th>
					<th scope="col">상품명</th>
					<th scope="col">가격</th>
					<th scope="col">M 재고</th>
					<th scope="col">L 재고</th>
					<th scope="col">XL 재고</th>
					<th scope="col">상픔등록일</th>
					<th scope="col">조회수</th>
					<th scope="col">카테고리 번호</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${itemList}" var="item">
					<tr>
						<th scope="row">${item.itemNum}</th>
						<td><a href="/admin/itemGet?itemNum=${item.itemNum}&pageNum=${pageNum}"><img src="${item.mainImg}" alt="상품 메인 사진" width="100"
							height="100" /></a></td>
						<td><a href="/admin/itemGet?itemNum=${item.itemNum}&pageNum=${pageNum}" class="itemNameLink">${item.itemName}</a></td>
						<td>${item.sale}</td>
						<td>${item.itemCountM}</td>
						<td>${item.itemCountL}</td>
						<td>${item.itemCountXL}</td>
						<td><fmt:formatDate pattern="yyyy-MM-dd"
								value="${item.itemRegDate}" /></td>
						<td>${item.readCount}</td>
						<td>${item.categoryNum}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<!-- 페이징처리 -->
		<div class="pull-right">
			<ul class="pagination">
				<c:if test="${page.prev}">
					<li class="page-item"><a class="page-link"
						href="/admin/item?pageNum=${page.startPage -1}&itemName=${itemName}">Previous</a></li>
				</c:if>

				<c:forEach var="pagging" begin="${page.startPage}"
					end="${page.endPage}">
					<li class="page-item ${page.pageNum == pagging ? 'active' : '' } "><a
						class="page-link" href="/admin/item?pageNum=${pagging}&itemName=${itemName}">${pagging}</a></li>
				</c:forEach>

				<c:if test="${page.next}">
					<li class="page-item"><a class="page-link"
						href="/admin/item?pageNum=${page.endPage +1}&itemName=${itemName}">Next</a></li>
				</c:if>
			</ul>
		</div>
	</div>

	<jsp:include page="../footer.jsp" />
</body>
</html>