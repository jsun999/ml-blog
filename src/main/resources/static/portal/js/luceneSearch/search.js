;(function() {

    AOS.init({
        easing: 'ease-out-back',
        duration: 1000,
        disable: 'mobile'
    });
    AOS.refresh(true);

    // 返回顶部
    var goBack = function() {
        var toTop = $("#toTop");
        $(window).scroll(function(e) {
            var scrollTop = $(this).scrollTop();

            if (scrollTop > 500) {
                toTop.removeClass("to-hide");
            } else {
                if (!toTop.hasClass("to-hide")) {
                    toTop.addClass("to-hide");
                }
            }
        });
        toTop.on("click",function() {
            $('html, body').animate({
                scrollTop: $('html').offset().top
            }, 500);
        });
    }

    // 获取文章列表
    var posts = function () {
        var postList = sessionStorage.getItem("postList");
        if (postList) {
            return;
        }

        $.ajax({
            "type": "GET",
            "url": "/postList",
            "async": false,
            "success": function (resp) {
                if (resp.code == 200) {
                    sessionStorage.setItem("postList",JSON.stringify(resp.data));
                }
            }
        });
    }

    // 搜索
    var search = function () {
        posts();
        var postListStr = sessionStorage.getItem("postList");
        var postList = [];
        if (postListStr) {
            postList = JSON.parse(postListStr);
        }
        $("#searchBtn").realtimeSearch({"data":postList});
    }

    $(function() {
        goBack();
        search();
    });

})();

