$(function () {
    photoManager.init();
});

var photoManager = {
    myEditor: null,
    categoryId: 0,
    init: function () {
        photoManager.getList();
        photoManager.registerEvent();
        photoManager.createFileComponent();
        $("#showExample").on("click",function() {
           $("#mdExample").fadeToggle("fast","linear");
        });
    },
    getList: function (pageNum) {
        $.getJSON("/admin/photoAlbum/list/"+ (pageNum || 1),function (resp) {
            if (resp.code == 200) {
                var pageInfo = resp.data;
                if (pageInfo.list.length > 0) {
                    var htmlArr = [];
                    for (var i=0; i< pageInfo.list.length; i++) {
                        var photo = pageInfo.list[i];

                        htmlArr.push("<tr>");
                        htmlArr.push("<td class='mail-select'><div class='checkbox checkbox-primary'><input id='checkbox_"+photo.id+"' type='checkbox'><label for='checkbox_"+photo.id+"'></label> </div></td>");
                        htmlArr.push("<td>"+(photo.description || '')+"</td>");
                        htmlArr.push("<td>"+(photo.status == 1 ? '显示' : '隐藏')+"</td>");
                        htmlArr.push("<td><img src='"+photo.imgUrl+"' width='30' height='30' alt=''></td> ");
                        htmlArr.push("<td>"+photo.createTime+"</td>");
                        htmlArr.push("<td class='actions'><button type='button' class='btn btn-info waves-effect m-b-5 edit' data-id='"+photo.id+"'>编辑</button>");
                        htmlArr.push("  <button type='button' class='btn btn-danger waves-effect m-b-5 delete' data-id='"+photo.id+"'>删除</button></td>");
                        htmlArr.push("</tr>");

                    }
                    $("#photoTable").find("tbody").html(htmlArr.join(""));
                    photoManager.setPageBar(pageInfo);
                    photoManager.bindClick();
                } else {
                    $("#photoTable").find("tbody").html("<tr><td colspan='9' align='center'>暂无数据</td></tr>");
                    $("#pageBar").html("");
                }
            } else {
                swal("查询失败", resp.msg,"error");
            }
        });
    },
    setPageBar: function (pageInfo) {
        var html = "<div class='col-xs-7' id='totalCount'></div>";
            html += "<div class='col-xs-5'>";
            html += "<div class='btn-group pull-right'>";
            html += "<button type='button' class='btn btn-default waves-effect' id='preBtn'><i class='fa fa-chevron-left'></i></button>";
            html += "<button type='button' class='btn btn-default waves-effect' id='nextBtn'><i class='fa fa-chevron-right'></i></button>";
            html += "</div>";
            html += "</div>";
        $("#pageBar").html(html);
        $("#totalCount").text("当前第 "+ pageInfo.pageNum +"/" + pageInfo.pages + " 页 - 总共 " + pageInfo.total + " 条记录");

        $("#preBtn").on("click",function () {
            if (!pageInfo.hasPreviousPage) {
                return;
            }
            photoManager.getList(pageInfo.pageNum - 1);
        });

        $("#nextBtn").on("click",function () {
            if (!pageInfo.hasNextPage) {
                return;
            }
            photoManager.getList(pageInfo.pageNum + 1);
        });
    },
    bindClick: function () {
        $(".edit").on("click",function () {
            var id = $(this).data("id");
            $.getJSON("/admin/photoAlbum/get/"+id,function (resp) {
                if (resp.code == 200) {

                    for(var key in resp.data) {
                        $("#" + key).val(resp.data[key]);
                    }

                    photoManager.createEditor(function() {
                        var photo = resp.data;
                        for(var key in photo) {

                            if(key == "tags") {
                                $("#tags").tagsinput('add', photo[key]);
                            } else if(key == "status") {
                                $("#showStatus").prop('checked', photo[key] == 1).change()
                            } else {
                                $("#" + key).val(photo[key]);
                            }

                        }
                        photoManager.myEditor.setMarkdown(photo.content);
                    });

                    photoManager.createFileComponent();

                    $("#saveUI").modal("show");
                } else {
                    swal("查询失败", resp.msg,"error");
                }
            })
        });

        $(".delete").on("click",function () {
            var that = this;
            swal({
                    title: "确定删除吗？",
                    text: "",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#DD6B55",
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    closeOnConfirm: false
                },
                function(){
                    var id = $(that).data("id");
                    $.post("/admin/photoAlbum/delete/"+id,null,function (resp) {
                        if (resp.code == 200) {
                            swal("删除成功", "","success");
                            photoManager.getList();
                        } else {
                            swal("删除失败", resp.msg,"error");
                        }
                    },"json");
                });
        });
    },
    registerEvent: function () {

        $("#checkboxAll").on("click",function () {
            $("#photoTable").find("input[type='checkbox']").prop("checked",$(this).prop("checked"));
        });

        $("#deletesBtn").on("click",function () {
            var idArr = [];
            $("#photoTable").find("tbody").find("input[type='checkbox']:checked").each(function (index,domEle) {
                var id = $(domEle).attr("id").split("_")[1];
                idArr.push(id);
            });

            if (idArr.length > 0) {
                swal({
                        title: "确定删除吗？",
                        text: "",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#DD6B55",
                        confirmButtonText: "确定",
                        cancelButtonText: "取消",
                        closeOnConfirm: false
                    },
                    function(){
                        $.photo("/admin/photo/deleteBatch/"+idArr.join(","),null,function (resp) {
                            if (resp.code == 200) {
                                swal("删除成功", "","success");
                                $("#checkboxAll").prop("checked",false);
                                photoManager.getList();
                            } else {
                                swal("删除失败", resp.msg,"error");
                            }
                        },"json");
                    });
            } else {
                swal("删除失败", "请勾选文章后进行删除","error");
            }
        });


        $("#submitBtn").on("click",function () {
            var parameter = {
                "id": $("#id").val(),
                "title": $("#title").val(),
                "keyword": $("#keyword").val(),
                "categoryId": $("#categoryId").val(),
                "tags": $("#tags").val(),
                "status": ($("#showStatus").prop("checked") ? 1: 0 ),
                "imgUrl": $("#imgUrl").val(),
                "content": photoManager.myEditor.getMarkdown()
            };

            $.photo("/admin/photo/save",parameter,function (resp) {
                if (resp.code == 200) {
                    $("#saveUI").modal("hide");
                    swal("保存成功", "","success");
                    photoManager.getList();
                } else {
                    swal("保存失败", resp.msg,"error");
                }
            },"json");
        });

        $("#importUIBtn").on("click",function () {
            $("#path").val("");
            $("#importUI").modal("show");
        });

        // md 文件导入事件
        $("#importBtn").on("click",function () {
            var that = this;
            var path = $("#path").val();
            if (!path) {
                swal("导入目录不能为空", "","error");
                return;
            }
            var index = layer.load(1);
            $(that).prop("disabled",true);
           $.photo("/admin/photo/importFiles",{"path": path},function (resp) {
               layer.close(index);
               $(that).prop("disabled",false);
               if (resp.code == 200) {
                   $("#importUI").modal("hide");
                   swal("导入成功", "","success");
                   window.location.reload(true);
               } else {
                   swal("导入失败", resp.msg,"error");
               }
           },"json");
        });

        // 查询事件
        $("#queryBtn").on("click",function () {
            photoManager.title = $("#query").val();
            photoManager.getList();
        });
    },

    createEditor: function (callback) {
        $("#editorContainer").html("<div class='col-lg-12' id='my-editormd'></div>");
        photoManager.myEditor = editormd("my-editormd", {
            width   : "100%",
            height  :  document.documentElement.clientHeight - 260,
            syncScrolling : "single",
            path    : "/admin/assets/plugins/editormd/lib/",
            placeholder: "写点内容吧(配置七牛云后可在该编辑器上实现图片上传)~~",
            toolbarIcons : function() {
                return ["undo","redo","bold","del","italic","quote","ucwords","uppercase","lowercase","h1","h2","h3","h4","h5","h6","list-ul","list-ol","hr","link","reference-link","image","code","preformatted-text","code-block","table","datetime","pagebreak","watch","unwatch","preview","clear","search"]
            },
            codeFold : true,
            imageUpload : true,
            imageFormats : ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
            imageUploadURL : "/admin/mdUploadfile",
            onload: function () {
                callback();
            }
        });
    },
    createFileComponent: function () {
        $("#upload").dropzone({
            url: "/admin/uploadfile", //上传地址
            method: "POST", //方式
            addRemoveLinks: true,
            maxFiles: 50,
            maxFilesize: 10,
            uploadMultiple: false,
            parallelUploads: 100,
            previewsContainer: false,
            acceptedFiles: ".jpg, .jpeg,.png",
            success: function(file, resp, e) {
                if (resp.code == 200) {
                    $("#fileContainer").html("<img src='"+resp.data+"' style='width:100%;height:80px'/>");
                    $("#imgUrl").val(resp.data);
                } else {
                    swal("文件上传失败", resp.msg,"error");
                    // 重新创建，否则需要刷新页面才能继续上传文件
                    // photoManager.createFileComponent();
                }
            }
        });
    }
}