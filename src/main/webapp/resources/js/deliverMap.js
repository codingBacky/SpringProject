var map;
var markerInfo;
//출발지,도착지 마커
var marker_s, marker_e, marker_p;
//경로그림정보
var drawInfoArr = [];
var drawInfoArr2 = [];

var chktraffic = [];
var resultdrawArr = [];
var resultMarkerArr = [];

// 맵 한 개만 생성하기
var mapInitialized = false;

var customerAddr = "";

// 회원 주소 좌표
var endX = "";
var endY = "";

// 회원 이메일
var email = "";

// 배송완료를 위한 orderId
var orderId;

$(function () {
    // email = $('#email').val();

    customerAddr = $('#customerAddr').val(); //여기에 구매자의 주소를 초기화하는 구문 추가 customerAddr = $

    email = $('#email').val();

    orderId = $('#orderId').val();

    console.log(customerAddr);

    $.ajax({
        url: "https://api.vworld.kr/req/address?",
        type: "GET",
        dataType: "jsonp",
        data: {
            service: "address",
            request: "GetCoord",
            version: "2.0",
            crs: "EPSG:4326",
            type: "ROAD",
            address: customerAddr,
            format: "json",
            errorformat: "json",
            key: "234CD2F2-E8EE-3C69-8EC5-9832C9EDF99E"
        },
        success: function (result) {
            endX = result.response.result.point.x;
            endY = result.response.result.point.y;
            console.log(endX);
            console.log(endY);

            initTmap(endX, endY);
            
            var headers = {};

            headers["appKey"] = "SRhj0RpxOR37VRnfRFGGl9tRMPfKn40d546q5pUO";

            //JSON TYPE EDIT [S]
            $.ajax({
                type: "POST",
                headers: headers,
                url: "https://apis.openapi.sk.com/tmap/routes?version=1&format=json&callback=result&appKey=SRhj0RpxOR37VRnfRFGGl9tRMPfKn40d546q5pUO",
                async: false,
                data: {
                    "startX": "127.0007",
                    "startY": "37.2687",
                    "endX": endX,
                    "endY": endY,
                    "reqCoordType": "WGS84GEO",
                    "resCoordType": "EPSG3857",
                    "searchOption": "0",
                    "trafficInfo": "Y"
                },
                success: function (response) {
                    var resultData = response.features;

                    var tDistance = "총 거리 : "
                        + (resultData[0].properties.totalDistance / 1000)
                            .toFixed(1) + "km,";
                    var tTime = " 총 시간 : "
                        + (resultData[0].properties.totalTime / 60)
                            .toFixed(0) + "분,";

                    var tFare = " 총 요금 : "
                        + resultData[0].properties.totalFare
                        + "원,";
                    var taxiFare = " 예상 택시 요금 : "
                        + resultData[0].properties.taxiFare
                        + "원";

                    $("#result").text(
                        tDistance + tTime + tFare
                        + taxiFare);

                    for (var i in resultData) { //for문 [S]
                        var geometry = resultData[i].geometry;
                        var properties = resultData[i].properties;

                        if (geometry.type == "LineString") {
                            //교통 정보도 담음
                            chktraffic
                                .push(geometry.traffic);
                            var sectionInfos = [];
                            var trafficArr = geometry.traffic;

                            for (var j in geometry.coordinates) {
                                // 경로들의 결과값들을 포인트 객체로 변환 
                                var latlng = new Tmapv2.Point(
                                    geometry.coordinates[j][0],
                                    geometry.coordinates[j][1]);
                                // 포인트 객체를 받아 좌표값으로 변환
                                var convertPoint = new Tmapv2.Projection.convertEPSG3857ToWGS84GEO(
                                    latlng);

                                sectionInfos
                                    .push(convertPoint);
                            }

                            drawLine(sectionInfos,
                                trafficArr);
                        } else {

                            var markerImg = "";
                            var pType = "";

                            if (properties.pointType == "S") { //출발지 마커
                                markerImg = "/resources/marker/pin_r_m_s.png";
                                pType = "S";
                            } else if (properties.pointType == "E") { //도착지 마커
                                markerImg = "/resources/marker/pin_r_m_e.png";
                                pType = "E";
                            } else { //각 포인트 마커
                                markerImg = "http://topopen.tmap.co.kr/imgs/point.png";
                                pType = "P"
                            }

                            // 경로들의 결과값들을 포인트 객체로 변환 
                            var latlon = new Tmapv2.Point(
                                geometry.coordinates[0],
                                geometry.coordinates[1]);
                            // 포인트 객체를 받아 좌표값으로 다시 변환
                            var convertPoint = new Tmapv2.Projection.convertEPSG3857ToWGS84GEO(
                                latlon);

                            var routeInfoObj = {
                                markerImage: markerImg,
                                lng: convertPoint._lng,
                                lat: convertPoint._lat,
                                pointType: pType
                            };
                            // 마커 추가
                            addMarkers(routeInfoObj);
                        }
                    }//for문 [E]

                    setTimeout(function(){
                        arriveEmail(email, orderId)
                    }, 5000);
                },
                error: function (request, status, error) {
                    console.log("code:"
                        + request.status + "\n"
                        + "message:"
                        + request.responseText
                        + "\n" + "error:" + error);
                }
            });
        },

        error: function () {
            console.log("에러 발생");
        }
    });

    
})

// 처음 지도를 띄웠을 떄 고객의 집을 찍는다 
function initTmap(start, end) {
    console.log(start);
    console.log(end);
    if (!mapInitialized) {
        // 1. 지도 띄우기
        map = new Tmapv2.Map("map_div", {
            center: new Tmapv2.LatLng(end, start),
            width: "100%",
            height: "900px",
            zoom: 10,
            zoomControl: true,
            scrollwheel: true
        });
        mapInitialized = true;
    }
}

function addComma(num) {
    var regexp = /\B(?=(\d{3})+(?!\d))/g;
    return num.toString().replace(regexp, ',');
}

//마커 생성하기
function addMarkers(infoObj) {
    var size = new Tmapv2.Size(24, 38);//아이콘 크기 설정합니다.

    if (infoObj.pointType == "P") { //포인트점일때는 아이콘 크기를 줄입니다.
        size = new Tmapv2.Size(8, 8);
    }

    marker_p = new Tmapv2.Marker({
        position: new Tmapv2.LatLng(infoObj.lat, infoObj.lng),
        icon: infoObj.markerImage,
        iconSize: size,
        map: map
    });

    resultMarkerArr.push(marker_p);
}

//라인그리기
function drawLine(arrPoint, traffic) {
    var polyline_;

    if (chktraffic.length != 0) {

        // 교통정보 혼잡도를 체크
        // strokeColor는 교통 정보상황에 다라서 변화
        // traffic :  0-정보없음, 1-원활, 2-서행, 3-지체, 4-정체  (black, green, yellow, orange, red)

        var lineColor = "";

        if (traffic != "0") {
            if (traffic.length == 0) { //length가 0인것은 교통정보가 없으므로 검은색으로 표시

                lineColor = "#06050D";
                //라인그리기[S]
                polyline_ = new Tmapv2.Polyline({
                    path: arrPoint,
                    strokeColor: lineColor,
                    strokeWeight: 6,
                    map: map
                });
                resultdrawArr.push(polyline_);
                //라인그리기[E]
            } else { //교통정보가 있음

                if (traffic[0][0] != 0) { //교통정보 시작인덱스가 0이 아닌경우
                    var trafficObject = "";
                    var tInfo = [];

                    for (var z = 0; z < traffic.length; z++) {
                        trafficObject = {
                            "startIndex": traffic[z][0],
                            "endIndex": traffic[z][1],
                            "trafficIndex": traffic[z][2],
                        };
                        tInfo.push(trafficObject)
                    }

                    var noInfomationPoint = [];

                    for (var p = 0; p < tInfo[0].startIndex; p++) {
                        noInfomationPoint.push(arrPoint[p]);
                    }

                    //라인그리기[S]
                    polyline_ = new Tmapv2.Polyline({
                        path: noInfomationPoint,
                        strokeColor: "#06050D",
                        strokeWeight: 6,
                        map: map
                    });
                    //라인그리기[E]
                    resultdrawArr.push(polyline_);

                    for (var x = 0; x < tInfo.length; x++) {
                        var sectionPoint = []; //구간선언

                        for (var y = tInfo[x].startIndex; y <= tInfo[x].endIndex; y++) {
                            sectionPoint.push(arrPoint[y]);
                        }

                        if (tInfo[x].trafficIndex == 0) {
                            lineColor = "#06050D";
                        } else if (tInfo[x].trafficIndex == 1) {
                            lineColor = "#61AB25";
                        } else if (tInfo[x].trafficIndex == 2) {
                            lineColor = "#FFFF00";
                        } else if (tInfo[x].trafficIndex == 3) {
                            lineColor = "#E87506";
                        } else if (tInfo[x].trafficIndex == 4) {
                            lineColor = "#D61125";
                        }

                        //라인그리기[S]
                        polyline_ = new Tmapv2.Polyline({
                            path: sectionPoint,
                            strokeColor: lineColor,
                            strokeWeight: 6,
                            map: map
                        });
                        //라인그리기[E]
                        resultdrawArr.push(polyline_);
                    }
                } else { //0부터 시작하는 경우

                    var trafficObject = "";
                    var tInfo = [];

                    for (var z = 0; z < traffic.length; z++) {
                        trafficObject = {
                            "startIndex": traffic[z][0],
                            "endIndex": traffic[z][1],
                            "trafficIndex": traffic[z][2],
                        };
                        tInfo.push(trafficObject)
                    }

                    for (var x = 0; x < tInfo.length; x++) {
                        var sectionPoint = []; //구간선언

                        for (var y = tInfo[x].startIndex; y <= tInfo[x].endIndex; y++) {
                            sectionPoint.push(arrPoint[y]);
                        }

                        if (tInfo[x].trafficIndex == 0) {
                            lineColor = "#06050D";
                        } else if (tInfo[x].trafficIndex == 1) {
                            lineColor = "#61AB25";
                        } else if (tInfo[x].trafficIndex == 2) {
                            lineColor = "#FFFF00";
                        } else if (tInfo[x].trafficIndex == 3) {
                            lineColor = "#E87506";
                        } else if (tInfo[x].trafficIndex == 4) {
                            lineColor = "#D61125";
                        }

                        //라인그리기[S]
                        polyline_ = new Tmapv2.Polyline({
                            path: sectionPoint,
                            strokeColor: lineColor,
                            strokeWeight: 6,
                            map: map
                        });
                        //라인그리기[E]
                        resultdrawArr.push(polyline_);
                    }
                }
            }
        } else {

        }
    } else {
        polyline_ = new Tmapv2.Polyline({
            path: arrPoint,
            strokeColor: "#DD0000",
            strokeWeight: 6,
            map: map
        });
        resultdrawArr.push(polyline_);
    }

}

function arriveEmail(email, orderId){
    console.log("함수 실행");
    console.log(email);
    $.ajax({
        type: 'get',
        url: '/shard/arriveEmail?email='+email+'&orderId='+orderId,
        success: function () {
            console.log("전송완료");
        },
        error: function () {
            console.log("에러에러에러");
        }
    });
}