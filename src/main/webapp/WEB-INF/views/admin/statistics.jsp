<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link rel="stylesheet" type="text/css"
   href="/resources/css/adminstatistics.css">
<link rel="stylesheet" href="/resources/css/common.css" />
<title>판매 통계</title>
</head>
<body>
<jsp:include page="adminHeader.jsp" />
<h1 class="adminstatisticstitle">판매 통계</h1>
    <div class="star-bot-right">
        <div>
            <ul class="rank">
                <li><em>10대</em> <span class="star-bot-right-bar" style="height: 100%; background-color: rgb(202, 53, 199);"> <em>${t1}</em></span></li>
                <li><em>20대</em> <span class="star-bot-right-bar" style="height: 80%;"> <em >${t2}</em></span></li>
                <li><em>30대</em> <span class="star-bot-right-bar" style="height: 60%; background-color: rgb(102, 204, 153);"><em>${t3}</em></span></li>
               <li><em>40대이상</em> <span class="star-bot-right-bar" style="height: 20%; background-color: rgb(44, 73, 211);"><em>${t4}</em></span></li>
            </ul>
        </div>
    </div>

   <div class="mostItem">
      <ul>
         <li>10대 : 엔카운터 후드집업 블랙</li>
         <li>20대 : 브레스 쇼트점퍼 블랙</li>
         <li>30대 : 레벨 스웨트팬츠 블랙</li>
         <li>40대이상 : 라이닝 니트 베스트 블랙<li>
      </ul>
   </div>
   <jsp:include page="../footer.jsp" />
</body>
</html>