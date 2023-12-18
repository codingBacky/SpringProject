<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 페이지</title>
<link rel="stylesheet" href="/resources/css/common.css" />
<link rel="stylesheet" href="/resources/css/admin.css" />
<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="/resources/js/admin.js" ></script>
</head>
<body>
	<jsp:include page="adminHeader.jsp" />


	<div class="adminWrap">
		<h2>관리자 페이지</h2>

		<ul>
			<li><a href="/admin/user?pageNum=1&userName=">회원관리</a></li>
			<li><a href="/admin/item?pageNum=1&itemName=">제품 관리</a></li>
			<li><a href="/admin/noEnswer?pageNum=1">문의 답변</a></li>
			<li><a href="/shard/notice?pageNum=1">공지사항</a></li>
			<li><a href="/admin/statistics?pageNum=1">통계</a></li>
		</ul>
	</div>

	<jsp:include page="../footer.jsp" />
</body>
</html>