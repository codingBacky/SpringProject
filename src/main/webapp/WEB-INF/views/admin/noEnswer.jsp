<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>Admin Enswer</title> 
<meta charset="UTF-8">
<link rel="stylesheet" href="/resources/css/common.css" />
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
	crossorigin="anonymous"></script>
<link rel="stylesheet" href="/resources/css/adminNoEnswer.css" />
<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.7.1.min.js"></script>
<script type="text/javascript" href="/resources/js/admin.js" ></script>
</head>
<body>
	<jsp:include page="adminHeader.jsp" />

	<div class="adminUserWrap">
		<div class="adminUser">
			<h2>관리자 답변 관리</h2>
		</div>

		<table class="table">
			<thead>
				<tr>
					<th scope="col">문의NUM</th>
					<th scope="col">문의 제목</th>
					<th scope="col">문의 카테고리</th>
					<th scope="col">문의 날짜</th>
					<th scope="col">문의 이메일</th>
					<th scope="col"></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${noEnswer}" var="noEnswer">
					<tr>
						<th scope="row">${noEnswer.replyNum}</th>
						<td><c:choose>
								<c:when test="${not empty noEnswer.replyTitle}">
            						${noEnswer.replyTitle}
       						 	</c:when>
								<c:otherwise>
						            재문의
						        </c:otherwise>
							</c:choose></td>

						<td>${noEnswer.replyCategory}</td>
						<td><fmt:formatDate pattern="yyyy-MM-dd"
								value="${noEnswer.replyRegDate}" /></td>
						<td>${noEnswer.email}</td>
						<td><a href="/qna/enswer/?replyNum=${noEnswer.replyNum}&inquiryNum=${noEnswer.inquiryNum}">답변하기</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<!-- 페이징처리 -->
		<div class="pull-right">
			<ul class="pagination">
				<c:if test="${page.prev}">
					<li class="page-item"><a class="page-link"
						href="/admin/noEnswer?pageNum=${page.startPage -1}">Previous</a></li>
				</c:if>

				<c:forEach var="pagging" begin="${page.startPage}"
					end="${page.endPage}">
					<li class="page-item ${page.pageNum == pagging ? 'active' : '' } "><a
						class="page-link" href="/admin/noEnswer?pageNum=${pagging}">${pagging}</a></li>
				</c:forEach>

				<c:if test="${page.next}">
					<li class="page-item"><a class="page-link"
						href="/admin/noEnswer?pageNum=${page.endPage +1}">Next</a></li>
				</c:if>
			</ul>
		</div>
	</div>
</body>
</html>