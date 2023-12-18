<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Q&A</title>
<link rel="stylesheet" href="/resources/css/common.css" />
<link rel="stylesheet" href="/resources/css/QnA.css" />
<link rel="stylesheet" href="/resources/css/main.css" />
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
		<h1>Q&A 답변</h1>

		<div class="QnAform">
			<form action="/qna/enswer" method="post" id="enswerForm">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
				<input type="hidden" name="replyNum" value="${reply.replyNum}" />
				<input type="hidden" name="inquiryNum" value="${reply.inquiryNum}" />
				<input type="hidden" name="email" value="${reply.email}" />
				<table>
					<tbody>
						<tr>
							<th><div>TITLE</div></th>
							<td colspan="3">
								<div class="title">
									<select name="replyCategory" id="replyCategory">
										<option value="${reply.replyCategory}">${reply.replyCategory}</option>
									</select> <br /> <input type="text" name="replyTitle" id="replyTitle"
										readonly="readonly" value="${reply.replyTitle}"
										style="width: 460px;" />
								</div>
							</td>
						</tr>
						<tr>
							<th><div>CONTENT</div></th>
							<td>
								<div>
									<input type="text" name="replyContent" id="replyContent"
										readonly="readonly" value="${reply.replyContent}"
										style="width: 460px;" />
								</div>
							</td>
						</tr>
						<tr>
							<th><div>답변</div></th>
							<td colspan="3">
								<div>
									<textarea name="enswerContent" id="enswerContent"
										class="QnAtextarea"></textarea>
								</div>
							</td>

						</tr>
					</tbody>
				</table>

				<div class="writeBtn">
					<button type="reset">RESET</button>
					<button type="button" onclick="QnAEnswerCheck()">WRITE</button>
				</div>
			</form>
		</div>
	</div>

	<jsp:include page="../footer.jsp" />
</body>
</html>