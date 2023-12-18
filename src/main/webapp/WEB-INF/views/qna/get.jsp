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
<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.7.1.min.js"></script>
<script src="/resources/js/QnAview.js"></script>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Open+Sans&display=swap"
	rel="stylesheet">
</head>
<body>
	<jsp:include page="../header.jsp" />

	<div class="QnAform">
		<h3>문의 내역</h3>

		<form action="/qna/delete" method="post" name="frm" id="QnADeleteForm">
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" /> <input type="hidden" name="replyNum"
				value="${replyNum}" /> <input type="hidden" name="pageNum"
				value="${pageNum}" />
			<c:forEach var="reply" items="${reply}">
				<div class="viewHeader">
					<c:choose>
						<c:when test="${reply.replyComplete == 1}">
							<span>답변완료</span>
						</c:when>
						<c:otherwise>
							<span>답변 중</span>
						</c:otherwise>
					</c:choose>
					<p class="replyTitle">${reply.replyTitle}</p>
					<p>${reply.replyCategory}<span style="margin: 0 15px;">|</span>
						<fmt:formatDate pattern="yyyy-MM-dd" value="${reply.replyRegDate}" />
					</p>
				</div>

				<div class="content">
					<div class="reQ_box">
						<div>${reply.replyContent}</div>

						<c:if test="${not empty reply.replyImg}">
							<img src="${reply.replyImg}" alt="" />
						</c:if>
					</div>
					<c:forEach var="enswer" items="${enswer}">
						<c:if test="${reply.replyNum == enswer.replyNum}">
							<c:if test="${reply.replyComplete == 1}">
								<div class="a_box">
									<div class="a_regDate">
										<fmt:formatDate pattern="yyyy-MM-dd"
											value="${enswer.enswerRegDate}" />
									</div>
									<p>
										<strong>[답변]</strong> [TheShard] 문의에 답변 드립니다.
									</p>
									<div>
										<p>${enswer.enswerContent}</p>
									</div>
								</div>
							</c:if>
						</c:if>
					</c:forEach>
				</div>
			</c:forEach>


			<div class="getBtn">
				<button type="button"
					onclick="location.href='/qna/list?pageNum=${pageNum}'">LIST</button>
				<sec:authentication property="principal" var="pinfo" />
				<sec:authorize access="isAuthenticated()">
					<c:set var="showReinsertButton" value="true" />

					<c:if test="${pinfo.member.email == user.email}">
						<button type="button" onclick="deleteQnA()">DELETE</button>
					</c:if>

					<sec:authorize access="hasRole('ROLE_ADMIN')">
						<button type="button" onclick="deleteQnA()">DELETE</button>
					</sec:authorize>

					<c:forEach var="replyItem" items="${reply}">
						<c:if test="${replyItem.replyComplete == 0}">
							<c:set var="showReinsertButton" value="false" />
						</c:if>
					</c:forEach>

					<c:if test="${showReinsertButton}">
						<sec:authorize access="hasRole('ROLE_USER')">
							<c:if test="${pinfo.member.email == user.email}">
								<button type="button"
									onclick="location.href='/qna/Reinsert?pageNum=${pageNum}&inquiryNum=${replyNum}'">재문의</button>
							</c:if>
						</sec:authorize>
					</c:if>

					<c:forEach var="replyItem" items="${reply}">
						<c:if test="${replyItem.replyComplete == 0}">
							<sec:authorize access="hasRole('ROLE_ADMIN')">
								<button type="button"
									onclick="location.href='/qna/enswer?replyNum=${replyItem.replyNum}&inquiryNum=${replyNum}'">답글달기</button>
							</sec:authorize>
						</c:if>
					</c:forEach>

				</sec:authorize>
			</div>
		</form>
	</div>
</body>
</html>