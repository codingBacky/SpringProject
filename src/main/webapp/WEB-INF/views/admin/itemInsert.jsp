<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Q&A</title>
<link rel="stylesheet" href="/resources/css/common.css" />
<link rel="stylesheet" href="/resources/css/QnA.css" />
<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.7.1.min.js"></script>
<script src="/resources/js/QnACheck.js" type="text/javascript"></script>
<script src="/resources/js/adminItemInsert.js" type="text/javascript"></script>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Open+Sans&display=swap" rel="stylesheet">
</head>
<body>
	<jsp:include page="../header.jsp" />

	<div class="QnAWrap">
		<h1>상품 추가</h1>

		<div class="QnAform">
			<form action="/admin/itemInsert?${_csrf.parameterName}=${_csrf.token}" method="post" id="itemInsertForm" enctype="multipart/form-data">
				<table>
					<tbody>
						<tr>
						<th><div>상품 이름</div></th>
						<td><div><input type="text" name="itemName" id="itemName"/></div></td>
					</tr>
					<tr>
						<th><div>가격</div></th>
						<td colspan="3">
							<div class="title">
								<input type="text" name="sale" id="sale" style="width:300px;"/>
							</div>
						</td>
					</tr>
					<tr>
						<th><div>사이즈별 수량</div></th>
						<td colspan="3">
							<div>
								M<input type="text" name="itemCountM" id="itemCountM" style="width:100px;"/>
								L<input type="text" name="itemCountL" id="itemCountL" style="width:100px;"/>
								XL<input type="text" name="itemCountXL" id="itemCountXL" style="width:100px;"/>
							</div>
						</td>
						
					</tr>
					<tr>
						<th><div>MAIN IMG</div></th>
						<td colspan="3"><div><input type="file" name="img" onchange="itemInsertImg(this)" id="mainImg"/>이미지 파일만 등록 가능합니다.</div></td>
					</tr>
					<tr>
						<th><div>SubImg1</div></th>
						<td colspan="3"><div><input type="file" name="img" onchange="itemInsertImg(this)" id="subImg1"/>이미지 파일만 등록 가능합니다.</div></td>
					</tr>
					<tr>
						<th><div>SubImg2</div></th>
						<td colspan="3"><div><input type="file" name="img" onchange="itemInsertImg(this)" id="subImg2"/>이미지 파일만 등록 가능합니다.</div></td>
					</tr>
					<tr>
						<th><div>SubImg3</div></th>
						<td colspan="3"><div><input type="file" name="img" onchange="itemInsertImg(this)" id="subImg3"/>이미지 파일만 등록 가능합니다.</div></td>
					</tr>
					<tr>
						<th><div>SubImg4</div></th>
						<td colspan="3"><div><input type="file" name="img" onchange="itemInsertImg(this)" id="subImg4"/>이미지 파일만 등록 가능합니다.</div></td>
					</tr>
					<tr>
						<th><div>상품 카테고리</div></th>
						<td colspan="3"><div><input type="text" name="categoryNum" id="categoryNum" placeholder="숫자를 입력하세요"/></div></td>
					</tr>
					</tbody>
				</table>
				
				<div class="writeBtn">
					<button type="reset">리셋</button>
					<button type="button" onclick="itemInsert()">추가</button>
				</div>
			</form>
		</div>
	</div>
	
	<jsp:include page="../footer.jsp" />
</body>
</html>