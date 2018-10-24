$(function () {
    comment.init();
});

var comment = {
    imgUrl: "",
    captchaObj: null,
    init: function () {
        comment.bindClick();
    },
    bindClick: function () {
        // 打开留言框
        $("#commentBtn").on("click",function () {
            comment.createUI("");
        });

        $(".replyBtn").on("click",function () {
            var commentId = $(this).data("commentid");
            comment.createUI(commentId);
        });

        $(".showbtn").on("click",function () {
            $("#replylist_"+$(this).data("id")).fadeToggle("fast","linear");
        });

    },
    createUI: function (commentId) {
        comment.imgUrl = "/portal/images/comment_" + (Math.floor(Math.random() * 5) + 1) + ".jpg";
        var html = "<div id='respond' class='comment-respond'>"+
            "<form id='commentForm' class='clearfix'>"+
            "<div class='clearfix'></div>"+
            "<div class='author-info'>"+
            "<input type='hidden' name='commentId' id='commentId' value='"+commentId+"'/>"+
            "<input type='text' name='nickname' id='nickname' placeholder='* 昵  称 : ' value='' tabindex='1' title='Name (required)'/>"+
            "<input type='text' name='email' id='email' placeholder='* 邮  箱 : ' value='' tabindex='2' title='E-mail(will not be published)'/>"+
            "<input type='text' name='homeUrl' id='url' placeholder='主  页 : ' value='' tabindex='3' title='Website'/>"+
            "</div>"+
            "<div class='clearfix'></div>"+
            "<div class='comment-form-info'>"+
            "<div class='real-time-gravatar'><img id='real-time-gravatar' src='"+comment.imgUrl+"' alt='gravatar头像'/></div>"+
            "<p class='input-row'>"+
            "<i class='row-icon'></i>"+
            "<textarea class='text_area' style='resize:none' rows='3' cols='80' name='content' id='content' tabindex='4' placeholder='* 你不说两句吗？(°∀°)ﾉ……'></textarea>"+
            "</p>"+
            "</div>"+
            "<div style='padding-top: 70px;'>"+
            "<span id='c1' style='display: inline-block;padding-left: 65px;'><input type='text' placeholder='* 验证码' id='captcha'> &nbsp;<img src='/admin/captcha.do' id='captchaImg' onclick='comment.changeCapche(this)' style='cursor:pointer' /></span>"+
            "<input type='button' class='button' id='submit' tabindex='5' value='发送' onclick='comment.submitClick()'/>"+
            "</div>"+
            "</form>"+
            "</div>";

        layer.open({
            title: "留言",
            type: 1,
            area: ['680px', '300px'],
            resize: false,
            zIndex: 2,
            content: html,
            cancel: function(index, layero){
                $("#respond").hide();
                layer.close(index);
                return true;
            }
        });
    },
    submitClick: function () {
        var nickname = $("#nickname").val();
        if (!nickname) {
            swal("昵称不能为空", "","error");
            return;
        }

        var content = $("#content").val();
        if (!content) {
            swal("留言内容不能为空", "","error");
            return;
        }

        if (content.length > 255) {
            swal("留言内容长度不能超过255", "","error");
            return;
        }

        var captcha = $("#captcha").val();
        if (!captcha) {
            swal("验证码不能为空", "","error");
            return;
        }

        var commentId = $("#commentForm").find("input[type='hidden']").val();

        var parameter = {
            "nickname": comment.checkSafe(nickname),
            "content": comment.checkSafe(content),
            "email": comment.checkSafe($("#email").val()),
            "homeUrl": comment.checkSafe($("#homeUrl").val()),
            "captcha": captcha,
            "commentId": commentId || null,
            "imgUrl": comment.imgUrl
        };

        var index = layer.load(1);
        $.post("/comment",parameter,function (resp) {
            layer.close(index);
            if (resp.code == 200) {
                window.location.reload(true);
            }else if(resp.code == 400) {
                sweetAlert("留言失败", resp.msg,"error");
                comment.changeCapche($("#captchaImg").get(0));
            } else {
                swal({
                    title: "留言失败",
                    text: resp.msg,
                    type: "error",
                    showCancelButton: false,
                    confirmButtonColor: "#DD6B55",
                    confirmButtonText: "确定"
                },
                function(){
                    window.location.reload(true);
                });
            }
        });
    },
    changeCapche: function (obj) {
        $(obj).attr("src","/admin/captcha.do");
    },
    checkSafe: function (str) {
        if(!str) {
            return "";
        }
        return str.replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/"/g, "&quot;").replace(/'/g, "&#039;");
    }
}