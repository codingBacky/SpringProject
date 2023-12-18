<%@page import="org.springframework.ui.Model"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.shard.domain.QnAVO"%>
<%@page import="com.shard.service.QnAService"%>
<%@page import="com.shard.service.QnAServiceImpl"%>
<%@page import="java.util.List"%>
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
<link rel="stylesheet" href="/resources/css/common.css" />
<link rel="stylesheet" href="/resources/css/QnAview.css" />
<link rel="stylesheet" href="/resources/css/noticeGet.css" />
<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.7.1.min.js"></script>
<script src="/resources/js/admin.js"></script>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Open+Sans&display=swap"
	rel="stylesheet">
</head>
<body>
	<jsp:include page="header.jsp" />

	<div class="content">
		<h3>NOTICE</h3>

		<form action="/admin/noticeDelete" method="post" name="frm" id="noticeDeleteForm">
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
			<input type="hidden" name="noticeNum" value="${notice.noticeNum}" />
			<div class="noticeGet">
				<p>${notice.noticeTitle}</p>
				<p>
					<span>Date : </span>
					<fmt:formatDate pattern="yyyy-MM-dd"
						value="${notice.noticeRegiDate}" />
				</p>
				<p>
					<span>Name : </span>관리자
				</p>
				<p>${notice.noticeContent}</p>
				<img src="${notice.noticeImg}" alt="" />

				<div class="getBtn">
					<button type="button"
						onclick="location.href='/shard/notice?pageNum=${pageNum}'">LIST</button>
					<sec:authorize access="hasRole('ROLE_ADMIN')">
						<button type="button" onclick="deleteNotice()">DELETE</button>
						<button type="button" onclick="location.href='/admin/noticeUpdate?noticeNum=${notice.noticeNum}&pageNum=${pageNum}'">UPDATE</button>
					</sec:authorize>
				</div>
			</div>


		</form>
	</div>

	<jsp:include page="footer.jsp" />
</body>
</html>