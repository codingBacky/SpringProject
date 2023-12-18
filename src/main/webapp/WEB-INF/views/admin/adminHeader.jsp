<%@page import="com.shard.domain.ShardMemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<div class="header">
	<div class="topRightNav">
		<form action="/shard/logout" method="post" id="logoutForm">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<ul>
				<li><span
					style="font-size: 14px; color: #333; font-weight: 400;"> <sec:authentication
							property="principal.member.userName" />님
				</span>
				<li><a href="#" class="logoutBtn">로그아웃</a></li>
				<li><a href="/admin/">관리자페이지</a></li>
				<li><a href="/qna/list?pageNum=1">Q&A</a></li>
			</ul>
		</form>
	</div>
</div>