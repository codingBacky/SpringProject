<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Tmap Example</title>
<link rel="stylesheet" href="/resources/css/common.css" />
<!-- Tmap API 스크립트 추가 -->
<script src="https://apis.openapi.sk.com/tmap/jsv2?version=1&appKey=SRhj0RpxOR37VRnfRFGGl9tRMPfKn40d546q5pUO"></script>
<!-- jQuery 추가 -->
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
</head>
<body>
	<jsp:include page="../header.jsp" />

	<input type="hidden" id="customerAddr" value="${roadAddress}" />
	<input type="hidden" id="orderId" value="${orderId}" />
	
	<input type="hidden" id="email" value='<sec:authentication property="principal.member.email"/>' />

	<div id="map_div" style="margin-bottom:40px;"></div>

	<jsp:include page="../footer.jsp" />
	<script src="/resources/js/deliverMap.js"></script>
</body>
</html>