<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
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
<script src="/resources/js/QnAview.js"></script>
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

		var result = "${result}";
		if (result == "adari") {
			alert("당신은 문의를 등록한 사람이 아닙니다.");
		} else if (result == "deleteSuccess") {
			alert("정상적으로 문의가 삭제되었습니다.");
		}
	</script>
	<jsp:include page="../header.jsp" />

	<div class="QnAlistWrap">
		<h2>
			Q&A 
			<br /> 
			<a href="/shard/notice?pageNum=1" style="color: #333; font-size: 13px;">공지사항</a>
		</h2>


		<div class="writeBtn">
			<a href="/qna/insert">글쓰기</a>
		</div>

		<form action="/qna/check" method="get" id="getReplyForm">
			<input type="hidden" name="pageNum" value="${page.pageNum}" />
		</form>

		<div class="listTable">
			<table>
				<colgroup>
					<col width="80">
					<col width="30">
					<col width="60">
					<col width="*">
					<col width="110">
					<col width="110">
				</colgroup>

				<thead>
					<tr>
						<th scope="col">NO.</th>
						<th scope="col">&nbsp;</th>
						<th scope="col">CATEGORY</th>
						<th scope="col">TITLE</th>
						<th scope="col">EMAIL</th>
						<th scope="col">DATE</th>
					</tr>
				</thead>

				<c:forEach var="list" items="${list}">
					<c:set var="email" value="${list.email}" />
					<c:set var="hiddenEmail"
						value="${fn:substring(email, 0, 2)}****${fn:substring(email, fn:length(email) - 2, fn:length(email))}" />
					<tr style="position: relative">
						<td>${list.replyNum}</td>
						<td><i class="fa-solid fa-lock"></i></td>
						<td style="font-size: 13px;">${list.replyCategory}</td>
						<td style="padding-left: 30px; text-align: left;"><a
							href="${list.replyNum}" class="getReply">${list.replyTitle}</a></td>
						<td>${hiddenEmail}</td>
						<td><fmt:formatDate value="${list.replyRegDate}"
								pattern="yyyy-MM-dd" /></td>
					</tr>

					<c:forEach var="enswer" items="${enswer}">
						<c:if test="${list.replyComplete == 1}">
							<c:if test="${enswer.replyNum == list.replyNum}">
								<tr>
									<td></td>
									<td><i class="fa-solid fa-lock"></i></td>
									<td></td>
									<td style="padding-left: 30px;"><a href="${list.replyNum}"
										class="reply getReply"><span class="replyComplete"><i
												class="fa-solid fa-arrow-turn-up"></i>답변완료</span> 답변드립니다.</a></td>
									<td>관리자</td>
									<td><fmt:formatDate value="${enswer.enswerRegDate}"
											pattern="yyyy-MM-dd" /></td>
								</tr>
								<c:set var="uniqueReplyNums[enswer.replyNum]" value="true"
									scope="page" />
								<c:set var="alreadyProcessed" value="true" />
							</c:if>
						</c:if>
					</c:forEach>
				</c:forEach>


			</table>
		</div>

		<div class="writeBtn">
			<a href="/qna/insert">글쓰기</a>
		</div>

		<div class="pageButton">
			<ul>
				<c:if test="${page.prev}">
					<li><a href="/qna/list?pageNum=${page.startPage -1}">이전</a></li>
				</c:if>
				<c:forEach var="num" begin="${page.startPage}" end="${page.endPage}">
					<li><a href="/qna/list?pageNum=${num}"
						class="${page.pageNum eq num ? 'active' : '' }">${num}</a></li>
				</c:forEach>
				<c:if test="${page.next}">
					<li><a href="/qna/list?pageNum=${page.endPage + 1}">다음</a></li>
				</c:if>
			</ul>
		</div>
	</div>

	<jsp:include page="../footer.jsp" />
</body>
</html>