;(function($) {
    "use strict"

    var defaultSetting = {
        searchList: "#searchList",
        keywordInput: "#keywordInput",
        data: []
    };

    $.fn.realtimeSearch = function(options) {
        defaultSetting = $.extend(defaultSetting, options || {});
        var that = this;
        function init() {
            method.bindClick(that);
        }

        init();
    }

    var method = {
        bindClick:  function(obj) {
            $(obj).on("click",function () {
                var input = $(".search-input");
                if (input.css("opacity") == 0) {
                    input.addClass("search-input-show");
                    var htmlArr = [];
                    for(var i=0; i<defaultSetting.data.length; i++) {
                        var post = defaultSetting.data[i];
                        htmlArr.push("<li><a href='/"+post.postUrl+"'>"+post.title+"</a></li>");
                    }
                    $(defaultSetting.searchList).html(htmlArr.join(""));
                    $(defaultSetting.keywordInput).hideseek({
                        highlight: true
                    });

                } else {
                    input.removeClass("search-input-show");
                }
            });
        }
    }

})(jQuery)

