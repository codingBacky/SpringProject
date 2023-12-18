<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>The shard</title>
<link rel="stylesheet" href="/resources/css/common.css" />
<link rel="stylesheet" href="/resources/css/index.css" />
<link rel="stylesheet" href="/resources/css/home.css" />
<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.7.1.min.js"></script>
<link
   href="https://cdn.jsdelivr.net/npm/swiper@11.0.5/swiper-bundle.min.css"
   rel="stylesheet"></link>
<body>
   <script type="text/javascript">
      var result = "${result}";
      if (result == "logout") {
         alert("성공적으로 로그아웃되었습니다.");
      }
   </script>

   <jsp:include page="header.jsp"></jsp:include>

   <div class="container">
      <div
         class="swiper-container swiper-container-initialized swiper-container-horizontal">
         <div class="swiper-wrapper">
            <div class="swiper-slide">
               <a href="/item/itemInfo?itemNum=2155&pageNum=1"><img src="/resources/index/main_new_00.png" alt="" /></a>
               <div class="slide_txt_box">
                  <a href=""> <span>힙하다</span>
                     <p>세렌디 파티</p>
                  </a>

                  <div class="tag_box">
                     <a href="">#NEW</a> <a href="">#23FW</a> <a href="">#4color</a>
                  </div>
               </div>
            </div>
            <div class="swiper-slide">
               <a href="/item/itemInfo?itemNum=2178&pageNum=1"><img src="/resources/index/main_new_01.png" alt="" /></a>
               <div class="slide_txt_box">
                  <a href=""> <span>남다른 디테일의 신상!</span>
                     <p>떠프라 쓰고 완벽이라 읽다.</p>
                  </a>

                  <div class="tag_box">
                     <a href="">#BEST</a> <a href="">#4color</a>
                  </div>
               </div>
            </div>
            <div class="swiper-slide">
               <a href="/item/itemInfo?itemNum=2175&pageNum=1"><img src="/resources/index/main_new_02.png" alt="" /></a>
               <div class="slide_txt_box">
                  <a href=""> <span>따뜻한 코듀로이 팬츠 하나면</span>
                     <p>캠핑 끄떡 없어요</p>
                  </a>

                  <div class="tag_box">
                     <a href="">#BEST</a> <a href="">#6color</a>
                  </div>
               </div>
            </div>
            <div class="swiper-slide">
               <a href="/item/itemInfo?itemNum=2171&pageNum=1"><img src="/resources/index/main_new_03.png" alt="" /></a>
               <div class="slide_txt_box">
                  <a href=""> <span>TheShard프로젝트 23FW추천템</span>
                     <p>런던~ 디프런~</p>
                  </a>

                  <div class="tag_box">
                     <a href="">#NEW</a> <a href="">#23FW</a> <a href="">#4color</a>
                  </div>
               </div>
            </div>
            <div class="swiper-slide">
               <a href="/item/itemInfo?itemNum=2136&pageNum=1"><img src="/resources/index/main_new_04.png" alt="" /></a>
               <div class="slide_txt_box">
                  <a href=""> <span>이런 색상 흔치 않지~</span>
                     <p>아보카도 색상 추가</p>
                  </a>

                  <div class="tag_box">
                     <a href="">#NEW</a> <a href="">#23FW</a> <a href="">#4color</a>
                  </div>
               </div>
            </div>
         </div>
         <div class="swiper-pagination"></div>
      </div>
   </div>

   <div class="title">
      <h2>Category</h2>
   </div>
   <div class="categoryList">
      <ul class="cate">
         <li><a href="/itemSearch/list/category/1"> <img
               src="https://image.msscdn.net/images/goods_img/20210812/2060994/2060994_16928433530278_500.jpg"
               alt="후드">후드
         </a></li>

         <li><a href="/itemSearch/list/category/2"> <img
               src="https://image.msscdn.net/images/goods_img/20210910/2122596/2122596_2_500.jpg"
               alt="후드집업">후드집업
         </a></li>
         <li><a href="/itemSearch/list/category/3"> <img
               src="https://image.msscdn.net/images/goods_img/20200205/1291017/1291017_1_500.jpg"
               alt="집업">집업
         </a></li>
         <li><a href="/itemSearch/list/category/4"> <img
               src="https://image.msscdn.net/images/goods_img/20230803/3444190/3444190_16951073068985_500.jpg"
               alt="셔츠">셔츠
         </a></li>
         <li><a href="/itemSearch/list/category/5"> <img
               src="https://image.msscdn.net/images/goods_img/20160322/324416/324416_16824008874265_500.jpg"
               alt="무지">무지
         </a></li>
         <li><a href="/itemSearch/list/category/6"> <img
               src="https://image.msscdn.net/images/goods_img/20230710/3400300/3400300_16964807034853_500.jpg"
               alt="데님펜츠">데님펜츠
         </a></li>
         <li><a href="/itemSearch/list/category/7"> <img
               src="https://image.msscdn.net/images/goods_img/20210121/1758197/1758197_16746129975024_500.jpg"
               alt="자켓">자켓
         </a></li>
         <li><a href="/itemSearch/list/category/8"> <img
               src="https://image.msscdn.net/images/goods_img/20231006/3607360/3607360_16975953449038_500.jpg"
               alt="코튼펜츠">코튼펜츠
         </a></li>
         <li><a href="/itemSearch/list/category/9"> <img
               src="https://image.msscdn.net/images/goods_img/20221108/2928316/2928316_1_500.jpg"
               alt="ACC">ACC
         </a></li>
         <li><a href="/itemSearch/list/category/10"> <img
               src="https://image.msscdn.net/images/goods_img/20220727/2684358/2684358_16945862914546_500.jpg"
               alt="니트">니트
         </a></li>
         <li><a href="/itemSearch/list/category/11"> <img
               src="https://image.msscdn.net/images/goods_img/20230801/3437355/3437355_16925831814491_500.jpg"
               alt="맨투맨">맨투맨
         </a></li>
         <li><a href="/itemSearch/list/category/12"> <img
               src="https://image.msscdn.net/images/goods_img/20231018/3637264/3637264_17013272004950_500.jpg"
               alt="티셔츠">티셔츠
         </a></li>

      </ul>
   </div>
   <div class="title">
      <h2>New +</h2>
   </div>
   <div class="itemlist" class="prdlist_rollup">
      <ul>
         <c:forEach var="item" items="${list}">
            <li class="item">
               <div class="box">
                  <div class="prdImg">
                     <a href="/item/itemInfo?itemNum=${item.itemNum}&pageNum=1"> <img src="<c:url value='${item.mainImg}'/>"
                        class="Main Img">
                     </a>
                  </div>
                  <div class="prd_info_box" id="prd_info_boxs">
                     <div class="name">
                        <a href="/item/itemInfo?itemNum=${item.itemNum}&pageNum=1"> <font><b>${item.itemName}</b></font>
                        </a>
                     </div>
                     <div class="list_info">
                        <ul>
                           <li class="cash text-center"><fmt:formatNumber pattern="#,###원" value="${item.sale}"/></li>
                        </ul>
                     </div>
                  </div>
               </div>
            </li>
         </c:forEach>
      </ul>
   </div>

   <script src="https://cdn.jsdelivr.net/npm/swiper@11.0.5/swiper-bundle.min.js"></script>

   <script type="text/javascript" src="/resources/js/index.js"></script>

   <jsp:include page="footer.jsp"></jsp:include>

</body>
</html>