$(function () {
    $('.content_box').not(":first").hide();

    $('.menu a').click(function (e) {
        $('.menu a').css({
            "background": "rgb(243, 243, 243)",
            "color": "rgb(117, 117, 117)",
            "border-right": "1px solid rgb(221, 221, 221)",
            "border-left": "1px solid rgb(221, 221, 221)"
        })

        $(this).css({
            "background": "#0e0e0e",
            "color": "#fff",
            "border-right": "1px solid #0e0e0e",
            "border-left": "1px solid #0e0e0e"
        });



        e.preventDefault();

        var index = $(this).index();

        $('.content_box').stop().hide();
        $('.content_box').eq(index).stop().show();
    });

    $('.myPageDeliver').click(function(e){
        e.preventDefault();
        var addr = $(this).attr("href");

        var orderId = parseInt($(this).closest("tr").find(".orderId").text());

        $('#Deliverytatus').append("<input type='hidden' name='roadAddress' value='"+ addr +"' />");
        $('#Deliverytatus').append("<input type='hidden' name='orderId' value='"+ orderId +"' />");

        $('#Deliverytatus').submit();
    });

    $('.deleteUser').click(function(e){
        e.preventDefault();

        if(confirm("정말 회원탈퇴를 하시겠습니까?")){
            var email = $(this).attr("href");
            $('#myPageDeleteUser').append("<input type='hidden' name='email' value='"+email+"' />");
            $('#myPageDeleteUser').submit();
        }
    })
});
