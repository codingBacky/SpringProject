<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
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
<link rel="stylesheet" href="/resources/css/adminUser.css" />
<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="/resources/js/admin.js"></script>
<title>Admin User Management</title>
<script type="text/javascript">
	var result = "${deleteUser}";
	if(result == "deleteUser"){
		alert("성공적으로 회원이 삭제되었습니다.");
	}
</script>
</head>
<body>
	<jsp:include page="adminHeader.jsp" />

	<div class="adminUserWrap">
		<div class="adminUser">
			<h2>관리자 회원 관리</h2>

			<form action="/admin/userSearch" method="post">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<input type="hidden" name="pageNum" value="1" />
				<div class="adminUserSearch">
					<label for="userSearch">회원검색</label> <input type="text"
						name="userName" id="userName" /> <input type="submit"
						value="검색" />
				</div>
			</form>
		</div>

		<table class="table">
			<thead>
				<tr>
					<th scope="col">email</th>
					<th scope="col">이름</th>
					<th scope="col">생년월일</th>
					<th scope="col">포인트</th>
					<th scope="col">휴대폰 번호</th>
					<th scope="col">멤버쉽 번호</th>
					<th scope="col">성별</th>
					<th scope="col"></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${userList}" var="user">
					<tr>
						<th scope="row">${user.email}</th>
						<td>${user.userName}</td>
						<td><fmt:formatDate pattern="yyyy-MM-dd" value="${user.dob}" /></td>
						<td>${user.point}</td>
						<td>${user.phone}</td>
						<td>${user.memNum}</td>
						<td>${user.gender}</td>
						<td><a href="${user.email}" class="adminDeleteUser">회원 삭제</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<form action="/admin/deleteUser" method="post" id="deleteUser">
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
		</form>

		<!-- 페이징처리 -->
		<div class="pull-right">
			<ul class="pagination">
				<c:if test="${page.prev}">
					<li class="page-item"><a class="page-link"
						href="/admin/user?pageNum=${page.startPage -1}&userName=${userName}">Previous</a></li>
				</c:if>

				<c:forEach var="pagging" begin="${page.startPage}"
					end="${page.endPage}">
					<li class="page-item ${page.pageNum == pagging ? 'active' : '' } "><a
						class="page-link" href="/admin/user?pageNum=${pagging}&userName=${userName}">${pagging}</a></li>
				</c:forEach>

				<c:if test="${page.next}">
					<li class="page-item"><a class="page-link"
						href="/admin/user?pageNum=${page.endPage +1}&userName=${userName}">Next</a></li>
				</c:if>
			</ul>
		</div>
	</div>
</body>
</html>