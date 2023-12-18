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
<script src="/resources/js/admin.js" type="text/javascript"></script>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Open+Sans&display=swap"
	rel="stylesheet">
</head>
<body>
	<jsp:include page="../header.jsp" />

	<div class="QnAWrap">
		<h1>공지사항 추가</h1>

		<div class="QnAform">
			<form action="/admin/noticeInsert?${_csrf.parameterName}=${_csrf.token}" method="post" id="noticeInsert" enctype="multipart/form-data">
				<table>
					<tbody>
					<tr>
						<th><div>TITLE</div></th>
						<td colspan="3">
							<div class="title">
								<input type="text" name="noticeTitle" id="noticeTitle" value="" style="width:460px;"/>
							</div>
						</td>
					</tr>
					<tr>
						<th><div>CONTENT</div></th>
						<td colspan="3">
							<div>
								<textarea name="noticeContent" id="noticeContent" class="QnAtextarea"></textarea>
							</div>
						</td>
						
					</tr>
					<tr>
						<th><div>FILE</div></th>
						<td colspan="3"><div><input type="file" name="img" onchange="noticeInsertImg(this)"/>이미지 파일만 등록 가능합니다.</div></td>
					</tr>
					</tbody>
				</table>
				
				<div class="writeBtn">
					<button type="button" onclick="location.href='/shard/notice?pageNum=1'">LIST</button>
					<button type="button" onclick="NoticeCheck()">WRITE</button>
				</div>
			</form>
		</div>
	</div>
	
	<jsp:include page="../footer.jsp" />
</body>
</html>