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

		<div class="QnAReform">
			<form action="/qna/Reinsert" method="post" id="QnAReForm">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				<input type="hidden" name="inquiryNum" value="${inquiryNum}" />
				<input type="hidden" name="replyTitle" value="" />
				<input type="hidden" name="email" value='<sec:authentication property="principal.member.email"/>' />
				<table>
					<tbody>
					<tr>
						<th><div style="margin-right:15px;">CONTENT</div></th>
						<td colspan="3" style="border:0;">
							<div>
								<textarea name="replyContent" id="replyContent" class="replyContent"></textarea>
							</div>
						</td>
						
					</tr>
					</tbody>
				</table>
				<div class="writeBtn">
					<button type="button" onclick="javascript:history.go(-1)">이전</button>
					<button type="button" onclick="QnAReFormCheck()">WRITE</button>
				</div>
			</form>
		</div>
	</div>
	
	<jsp:include page="../footer.jsp" />
</body>
</html>