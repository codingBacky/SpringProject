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
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Open+Sans&display=swap"
	rel="stylesheet">
</head>
<body>
	<jsp:include page="../header.jsp" />

	<div class="QnAWrap">
		<h1>Q&A</h1>

		<div class="QnAform">
			<form action="/qna/insert?${_csrf.parameterName}=${_csrf.token}" method="post" id="QnAForm" enctype="multipart/form-data">
				<input type="hidden" name="email" value='<sec:authentication property="principal.member.email"/>' />
				<table>
					<tbody>
						<tr>
						<th><div>PASSWORD</div></th>
						<td><div><input type="password" name="replyPwd" id="replyPwd"/><font color="red">자동 잠금 기능</font></div></td>
					</tr>
					<tr>
						<th><div>TITLE</div></th>
						<td colspan="3">
							<div class="title">
								<select name="replyCategory" id="replyCategory">
									<option value="">선택</option>
									<option value="배송">배송</option>
									<option value="입금확인">입금확인</option>
									<option value="기타">기타</option>
									<option value="단체복문의">단체복문의</option>
								</select>
								<br />
								<input type="text" name="replyTitle" id="replyTitle" value="" style="width:460px;"/>
							</div>
						</td>
					</tr>
					<tr>
						<th><div>CONTENT</div></th>
						<td colspan="3">
							<div>
								<textarea name="replyContent" id="replyContent" class="QnAtextarea"></textarea>
							</div>
						</td>
						
					</tr>
					<tr>
						<th><div>FILE</div></th>
						<td colspan="3"><div><input type="file" name="img" onchange="itemInsertImg(this)"/>이미지 파일만 등록 가능합니다.</div></td>
					</tr>
					</tbody>
				</table>
				
				<div class="writeBtn">
					<button type="button" onclick="location.href='/qna/list?pageNum=1'">LIST</button>
					<button type="button" onclick="QnACheck()">WRITE</button>
				</div>
			</form>
		</div>
	</div>
	
	<jsp:include page="../footer.jsp" />
</body>
</html>