<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 정보</title>
<link rel="stylesheet" href="/resources/css/common.css" />
<link rel="stylesheet" href="/resources/css/QnA.css" />
<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.7.1.min.js"></script>
<script src="/resources/js/QnACheck.js" type="text/javascript"></script>
<script src="/resources/js/adminItemGet.js" type="text/javascript"></script>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Open+Sans&display=swap" rel="stylesheet">
</head>
<body>
	<jsp:include page="../header.jsp" />

	<div class="QnAWrap">
		<h1>상품 정보</h1>

		<div class="QnAform">
			<form action="/admin/itemUpdate?${_csrf.parameterName}=${_csrf.token}" method="post" id="itemGet" enctype="multipart/form-data">
				<input type="hidden" name="itemNum" value="${item.itemNum}" />
				<table>
					<tbody>
						<tr>
						<th><div>상품 이름</div></th>
						<td><div><input type="text" name="itemName" id="itemName" value="${item.itemName}" readonly="readonly"/></div></td>
					</tr>
					<tr>
						<th><div>가격</div></th>
						<td colspan="3">
							<div class="title">
								<input type="text" name="sale" id="sale" style="width:300px;" value="${item.sale}" readonly="readonly"/>
							</div>
						</td>
					</tr>
					<tr>
						<th><div>사이즈별 수량</div></th>
						<td colspan="3">
							<div>
								M<input type="text" name="itemCountM" id="itemCountM" style="width:100px;" value="${item.itemCountM}" readonly="readonly"/>
								L<input type="text" name="itemCountL" id="itemCountL" style="width:100px;" value="${item.itemCountL}" readonly="readonly"/>
								XL<input type="text" name="itemCountXL" id="itemCountXL" style="width:100px;" value="${item.itemCountXL}" readonly="readonly"/>
							</div>
						</td>
						
					</tr>
					<tr>
						<th><div>MAIN IMG</div></th>
						<td colspan="3"><div><input type="file" name="img" onchange="itemInsertImg(this)" id="mainImg" disabled="disabled"/>이미지 파일만 등록 가능합니다.<img src="${item.mainImg}" alt="메인 사진" width="100" height="100" style="float:right"/></div></td>
					</tr>
					<tr>
						<th><div>SubImg1</div></th>
						<td colspan="3"><div><input type="file" name="img" onchange="itemInsertImg(this)" id="subImg1" disabled="disabled"/>이미지 파일만 등록 가능합니다.<img src="${item.subImg1}" alt="서브 사진1" width="100" height="100" style="float:right"/></div></td>
					</tr>
					<tr>
						<th><div>SubImg2</div></th>
						<td colspan="3"><div><input type="file" name="img" onchange="itemInsertImg(this)" id="subImg2" disabled="disabled"/>이미지 파일만 등록 가능합니다.<img src="${item.subImg2}" alt="서브 사진2" width="100" height="100" style="float:right"/></div></td>
					</tr>
					<tr>
						<th><div>SubImg3</div></th>
						<td colspan="3"><div><input type="file" name="img" onchange="itemInsertImg(this)" id="subImg3" disabled="disabled"/>이미지 파일만 등록 가능합니다.<img src="${item.subImg3}" alt="서브 사진3" width="100" height="100" style="float:right"/></div></td>
					</tr>
					<tr>
						<th><div>SubImg4</div></th>
						<td colspan="3"><div><input type="file" name="img" onchange="itemInsertImg(this)" id="subImg4" disabled="disabled"/>이미지 파일만 등록 가능합니다.<img src="${item.subImg4}" alt="서브 사진4" width="100" height="100" style="float:right"/></div></td>
					</tr>
					<tr>
						<th><div>상품 카테고리</div></th>
						<td colspan="3"><div><input type="text" name="categoryNum" id="categoryNum" value="${item.categoryNum}" readonly="readonly"/></div></td>
					</tr>
					</tbody>
				</table>
				
				<div class="writeBtn">
					<button type="button" onclick="location.href='/admin/item?pageNum=${pageNum}&itemName='">상품 리스트</button>
					<button type="button" onclick="itemDelete()">삭제</button>
					<button type="button" onclick="itemUpdate()" class="updateStart">수정</button>
					<button type="button" onclick="itemUpdateSubmit()" class="updateEnd" style="display:none;">수정하기</button>
				</div>
			</form>
		</div>
	</div>
	
	<jsp:include page="../footer.jsp" />
</body>
</html>