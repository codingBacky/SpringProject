<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
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
<script type="text/javascript">
	var result = "${result}";
	if(result == "noPwd"){
		alert("비밀번호가 맞지 않습니다.");
	}
</script>
</head>
<body>
	<jsp:include page="../header.jsp" />

	<div class="QnAWrap" style="margin:70px auto 50px;">
		<h1>PASSWORD CHECK</h1>

		<div class="QnAform" >
			<form action="/shard/userCheck" method="post">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />				
				<input type="hidden" name="email" value='<sec:authentication property="principal.member.email"/>' />				
				<table style="margin:0 auto; width:260px;">
					<tbody>
						<tr>
							<th><div>비밀번호</div></th>
							<td><div>
									<input type="password" name="userPwd" id="userPwd" />
								</div></td>
						</tr>
					</tbody>
				</table>
				<div class="checkBtn">
					<button type="submit">CHECK</button>
				</div>
			</form>
		</div>
	</div>

	<jsp:include page="../footer.jsp" />
</body>
</html>