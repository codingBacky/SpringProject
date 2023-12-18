<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/resources/css/QnA.css">
<link rel="stylesheet" href="/resources/css/QnAlist.css" />
<link rel="stylesheet" href="/resources/css/common.css" />
<script src="https://kit.fontawesome.com/f21f7d3508.js"
	crossorigin="anonymous"></script>
<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.7.1.min.js"></script>
<script src="/resources/js/admin.js"></script>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Open+Sans&display=swap"
	rel="stylesheet">
</head>
<body>
	<script type="text/javascript">
		var file = "${file}";
		if (file == "up") {
			alert("파일의 용량이 5MB가 넘습니다.");
		} else if (file == "noImg") {
			alert("파일의 확장자가 사진이 아닙니다.");
		}
		
		var result = "${deleteNotice}";
		if(result == "deleteNotice"){
			alert("성공적으로 공지사항이 삭제되었습니다.");
		}
	</script>
	<jsp:include page="header.jsp" />

	<div class="QnAlistWrap">
		<h2 style="margin-bottom:30px;">NOTICE</h2>

		<sec:authorize access="hasRole('ROLE_ADMIN')">
			<div class="writeBtn">
				<a href="/admin/noticeInsert">글쓰기</a>
			</div>
		</sec:authorize>

		<form action="/shard/noticeGet" method="get" id="noticeGet">
			<input type="hidden" name="pageNum" value="${page.pageNum}" />
		</form>

		<div class="listTable">
			<table>
				<colgroup>
					<col width="80">
					<col width="30">
					<col width="*">
					<col width="110">
					<col width="110">
				</colgroup>

				<thead>
					<tr>
						<th scope="col">NO.</th>
						<th scope="col">&nbsp;</th>
						<th scope="col">TITLE</th>
						<th scope="col">NAME</th>
						<th scope="col">DATE</th>
					</tr>
				</thead>

				<c:forEach var="list" items="${list}">
					<tr style="position: relative">
						<td>${list.noticeNum}</td>
						<td><i class="fa-solid fa-star" style="color: #000;"></i></td>
						<td style="padding-left: 30px; text-align: left;"><a
							href="${list.noticeNum}" class="getNotice">${list.noticeTitle}</a></td>
						<td style="text-align: center">관리자</td>
						<td><fmt:formatDate value="${list.noticeRegiDate}"
								pattern="yyyy-MM-dd" /></td>
					</tr>
				</c:forEach>
			</table>
		</div>


		<div class="pageButton">
			<ul>
				<c:if test="${page.prev}">
					<li><a href="/shard/notice?pageNum=${page.startPage -1}">이전</a></li>
				</c:if>
				<c:forEach var="num" begin="${page.startPage}" end="${page.endPage}">
					<li><a href="/shard/notice?pageNum=${num}"
						class="${page.pageNum eq num ? 'active' : '' }">${num}</a></li>
				</c:forEach>
				<c:if test="${page.next}">
					<li><a href="/shard/notice?pageNum=${page.endPage + 1}">다음</a></li>
				</c:if>
			</ul>
		</div>
	</div>

	<jsp:include page="footer.jsp" />
</body>
</html>