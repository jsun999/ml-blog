 /**
 *
 * @authors Your Name (you@example.org)
 * @date    2018-08-30 16:11:44
 * @version $Id$
 */
;(function () {
     // 代码高亮
     hljs.initHighlightingOnLoad();
     // 文章目录
     $("#tocContainer").autoToc({offsetTop: 520});
     // 自定义滚动条
     $(".post-content code").mCustomScrollbar({axis: "x", theme: "minimal-dark", autoHideScrollbar: true});

    var commentManager = {
        "getCommentList": function (pageNum) {
            pageNum = pageNum || 1;
            $.ajax({
                "type": "GET",
                "url": "/commentList/" + $("#postId").val() + "/" + pageNum,
                "dataType": "json",
                "success": function (resp) {
                    if (resp.code == 200) {
                        var pageInfo = resp.data;
                        if (pageInfo.data.length > 0) {
                            var htmlArr = [];
                            for (var i=0; i<pageInfo.data.length; i++) {
                                var comment = pageInfo.data[i];
                                htmlArr.push("<div class='post post_content ml-content' aos='fade-up' aos-once='true' aos-delay='"+(i*100)+"'><div class='row'><div class='col-md-12 col-sm-12 col-xs-12'><div class='row comment-list'>");
                                htmlArr.push("<div class='comment'><div class='comment-img'>");
                                htmlArr.push("<img src='"+comment.imgUrl+"' alt='' >");
                                htmlArr.push("</div>");
                                htmlArr.push("<div class='comment-contents'>");
                                htmlArr.push("<div class='comment-author'><b>"+comment.nickname+"</b></div>");
                                htmlArr.push("<div class='comment-date'>"+comment.createTime+"</div>");
                                htmlArr.push("<div class='comment-content'>");
                                htmlArr.push("<p>" + comment.content + "</p>");
                                htmlArr.push("</div>");
                                htmlArr.push("<div class='comment-reply'>");
                                htmlArr.push("<div role='tab' id='heading_"+comment.id+"'>");
                                htmlArr.push("<h4>");
                                htmlArr.push("<a class='collapsed' role='button' data-toggle='collapse' data-parent='#accordion' href='#collapse_"+comment.id+"' aria-expanded='false'><i class='fa fa-reply'></i></a>");
                                htmlArr.push("</h4>");
                                htmlArr.push("</div>");
                                htmlArr.push("<div id='collapse_"+comment.id+"' class='panel-collapse collapse' role='tabpanel' aria-labelledby='heading_"+comment.id+"'>");
                                htmlArr.push("<div class='panel-body'>");
                                htmlArr.push("<div class='comment-body-msg'></div>");
                                htmlArr.push("<div class='comment-body-input'>");
                                htmlArr.push("<textarea class='comment-form-control form-control' name='content' id='' rows='5' placeholder='说些内容吧~' style='resize: none;'></textarea>");
                                htmlArr.push("<form class='form-inline'>");
                                htmlArr.push("<div class='form-group'>");
                                htmlArr.push("<span style='display: inline-block;padding-left: 10px'><img src='/portal/images/comment_2.jpg' width='32' height='32'></span>");
                                htmlArr.push("</div>");
                                htmlArr.push("<div class='form-group'>");
                                htmlArr.push("<input type='text' class='form-control' placeholder='昵称(必填)'>");
                                htmlArr.push("</div>");
                                htmlArr.push("<div class='form-group'>");
                                htmlArr.push("<input type='email' class='form-control' placeholder='邮箱(必填)'>");
                                htmlArr.push("</div>");
                                htmlArr.push("<div class='form-group'>");
                                htmlArr.push("<input type='text' class='form-control'  placeholder='主页(选填)'>");
                                htmlArr.push("</div>");
                                htmlArr.push("<div class='form-group text-center'>");
                                htmlArr.push("<button type='button' class='btn btn-primary reply-btn' id='reply_"+comment.id+"'><i class='fa fa-paper-plane'></i>&nbsp;&nbsp;回复</button>");
                                htmlArr.push("</div>");
                                htmlArr.push("</form>");
                                htmlArr.push("</div>");
                                htmlArr.push("</div>");
                                htmlArr.push("</div>");
                                htmlArr.push("</div>");

                                if (comment.replyList.length > 0) {
                                    for (var j=0; j<comment.replyList.length; j++) {
                                        var reply = comment.replyList[j];
                                        htmlArr.push("<div class='comment'>");
                                        htmlArr.push("<div class='comment-img'>");
                                        htmlArr.push("<img src='"+reply.imgUrl+"' alt='' >");
                                        htmlArr.push("</div>");
                                        htmlArr.push("<div class='comment-contents'>");
                                        htmlArr.push("<div class='comment-author'><b>"+reply.nickname+"</b></div>");
                                        htmlArr.push("<div class='comment-date'>"+reply.createTime+"</div>");
                                        htmlArr.push("<div class='comment-content'>");
                                        htmlArr.push("<p><b>[@"+comment.nickname+"</b>] "+reply.content+"</p></div></div></div>");
                                    }
                                }
                                htmlArr.push("</div></div></div></div></div></div>");
                            }
                            $("#commentListContainer").html(htmlArr.join(""));

                            AOS.init({
                                easing: 'ease',
                                duration: 1000,
                                disable: 'mobile'
                            });

                            commentManager.setPageBar(pageInfo);
                        }
                    }
                }
            });
        },
        "setPageBar": function (pageInfo) {
            var htmlArr = [];
            for (var i=0; i < pageInfo.navigatepageNums.length; i ++) {
                var pageNum = pageInfo.navigatepageNums[i];
                if (pageNum == pageInfo.pageNum) {
                    htmlArr.push("<span><a href='javascript:void(0)' class='page-numbers current'>"+pageNum+"</a></span>");
                } else {
                    htmlArr.push("<span><a href='javascript:void(0)' class='page-numbers chage'>"+pageNum+"</a></span>");
                }
            }

            $("#pageBar").html(htmlArr.join(""));

            $(".chage").on("click",function() {
                commentManager.getCommentList($(this).text());
            });
        },
        "bindComment": function () {
           $(document).off("click");
           $(document).on("click",".reply-btn",function() {
               var $inputs = $(this).parents(".comment-body-input");
               var $inputArr = $inputs.find("input");

               var content = $inputs.children().get(0).value;
               var nickname = $inputArr.get(0).value;
               var email =  $inputArr.get(1).value;
               var homeUrl = $inputArr.get(2).value;

               var container = $inputs.prev();
               if (nickname.length == 0 || email.length == 0 || content.length == 0) {
                   commentManager.showAlertMessage(container,2,"必填项不能为空哦");
                   return;
               }

               if (content.length > 255) {
                   commentManager.showAlertMessage(container,2,"评论内容长度不能超过 255 个字哦");
                   return;
               }

               var commentId = 0;
               var idStr = $(this).attr("id");
               if (idStr && idStr.indexOf("reply_") > -1) {
                   commentId = idStr.split("_")[1];
               }
               var imgUrl = "/portal/images/comment_" + (Math.floor(Math.random() * 5) + 1) + ".jpg";
               var parameter = {
                   "nickname": commentManager.checkSafe(nickname),
                   "content": commentManager.checkSafe(content),
                   "email": commentManager.checkSafe(email),
                   "homeUrl": commentManager.checkSafe(homeUrl),
                   "postId": $("#postId").val(),
                   "commentId": commentId,
                   "imgUrl": imgUrl
               };

               $.post("/postComment",parameter,function(resp) {
                   if (resp.code == 200) {
                       commentManager.getCommentList();
                       $(".form-control").val("");
                       commentManager.showAlertMessage(container,1,"评论成功~~");
                   } else {
                       commentManager.showAlertMessage(container,2,resp.msg);
                   }

               },"json");
           });
        },
        "checkSafe": function (str) {
            if(!str) {
                return "";
            }
            return str.replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/"/g, "&quot;").replace(/'/g, "&#039;");
        },
        "showAlertMessage": function (container,type,msg) {
            var color = (type == 1) ? "alert-success" : "alert-danger";
            var html =  "<div class='alert "+color+" alert-dismissible fade in' role='alert'>"+
                "<button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>×</span></button>"+
                "<span class='glyphicon glyphicon-exclamation-sign' aria-hidden='true'></span> <strong>"+ msg +"</strong></div>"
            container.html(html);
        }
    };

    commentManager.bindComment();

    var flag = false;
    var commentContainer = $("#commentContainer");
    // $(window).scroll(function(e) {
    //     var scrollTop = $(this).scrollTop();
    //     if (!flag && (scrollTop > parseInt(commentContainer.offset().top + commentContainer.height()- 890))) {
    //         // 获取评论列表
    //         commentManager.getCommentList();
    //         flag = true;
    //     }
    // });
     var scrollFunc=function(e){
         var scrollTop = $(this).scrollTop();
         if (!flag && (scrollTop > parseInt(commentContainer.offset().top + commentContainer.height()- window.innerHeight))) {
             // 获取评论列表
             commentManager.getCommentList();
             flag = true;
         }
     }
     /*注册事件*/
     if(document.addEventListener){
         document.addEventListener('DOMMouseScroll',scrollFunc,false);
     }//W3C
     window.onmousewheel=document.onmousewheel=scrollFunc;//IE/Opera/Chrome


    // 点赞
    $("#priseBtn").on("click",function () {
        var postId = $("#postId").val();
        if (sessionStorage.getItem("hasPrize" + postId)) {
            var html =  "<div class='alert alert-warning alert-dismissible fade in' role='alert'>"+
                "<button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>×</span></button>"+
                "<span class='glyphicon glyphicon-exclamation-sign' aria-hidden='true'></span> <strong>已点赞，请勿重复操作</strong></div>";
            $("#prizeMsg").html(html);
            return;
        }

        $.post("/prizePost/" + postId,null,function (resp) {
            if (resp.code == 200) {
                $("#prizeCount").text(resp.data);
                sessionStorage.setItem("hasPrize" + postId, "y");
            } else {
                var html =  "<div class='alert alert-warning alert-dismissible fade in' role='alert'>"+
                    "<button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>×</span></button>"+
                    "<span class='glyphicon glyphicon-exclamation-sign' aria-hidden='true'></span> <strong>"+resp.msg+"</strong></div>";
                $("#prizeMsg").html(html);
            }
        },"json");

    });

    // 分享
    $("#shareOpenBtn").on("click",function () {
       var shareBtns = $("#shareBtns");
       if (shareBtns.hasClass("share-open")) {
           shareBtns.removeClass("share-open");
       } else {
           shareBtns.addClass("share-open");
       }
    });

})(jQuery);