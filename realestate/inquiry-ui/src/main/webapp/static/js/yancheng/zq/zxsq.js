/**
 * 征迁注销1：产权查询页面JS
 */
layui.use(['form', 'jquery', 'element', 'layer', 'laytpl'], function () {
    var form = layui.form;
    var $ = layui.jquery;

    $(function () {
        addModel();

        var zdList = getMulZdList("zqshzt,qllx,djlx,bdclx,zqzxyy");
        var xmid = $.getUrlParam('xmid');
        var sqxxid = $.getUrlParam('sqxxid');
        var state = $.getUrlParam('state');

        if("edit" == state) {
            $("#fjcl").hide();
        } else if("view" == state) {
            $("#btn").hide();
        }

        if(isNullOrEmpty(sqxxid) && isNullOrEmpty(xmid)) {
            failMsg("未传有效参数，无法查询数据！");
            return;
        }

        if(isNullOrEmpty(sqxxid)) {
            /**
             * 新增状态
             */
            // 获取产权项目信息
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/zq/xmxx?xmid=" + xmid,
                type: "GET",
                dataType: "json",
                success: function (data) {
                    if(data && !isNullOrEmpty(data.xmid)){
                        if(zdList) {
                            if(zdList.qllx) {
                                data.qllxmc = getZdMc(zdList.qllx, data.qllx);
                            }
                            if(zdList.djlx) {
                                data.djlxmc = getZdMc(zdList.djlx, data.djlx);
                            }
                            if(zdList.bdclx) {
                                data.bdclxmc = getZdMc(zdList.bdclx, data.bdclx);
                            }
                            if(zdList.zqzxyy) {
                                if(zdList.zqzxyy.length > 1) {
                                    $('#zxyy').append('<option value="">请选择</option>');
                                }

                                $.each(zdList.zqzxyy, function (index, item) {
                                    $('#zxyy').append('<option value="' + item.DM + '">' + item.MC + '</option>');
                                });
                                form.render('select');
                            }
                        }
                        $("#qlrmc").val(data.qlr);
                        $("#jzmj").val(data.dzwmj);
                        form.val('form', data);
                    } else {
                        failMsg("未获取到项目信息");
                    }
                },
                complete: function () {
                    removeModal();
                }
            });

            // 获取产权权利人信息
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/zq/qlr?xmid=" + xmid,
                type: "GET",
                dataType: "json",
                success: function (data) {
                    if(data && data.length > 0){
                        var lxdh = "";
                        $.each(data, function (index, qlr) {
                            if(!isNullOrEmpty(qlr.dh)) {
                                lxdh = isNullOrEmpty(lxdh) ? qlr.dh : lxdh + "," + qlr.dh;
                            }
                        });
                        $("#lxdh").val(lxdh);
                    } else {
                        failMsg("未获取到权利人信息");
                    }
                }
            });
        } else {
            /**
             * 页面编辑状态
             */
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/zq/zxsq?sqxxid=" + sqxxid,
                type: "GET",
                dataType: "json",
                success: function (data) {
                    if(data && !isNullOrEmpty(data.sqxxid)){
                        if(zdList) {
                            if(zdList.qllx) {
                                data.qllxmc = getZdMc(zdList.qllx, data.qllx);
                            }
                            if(zdList.djlx) {
                                data.djlxmc = getZdMc(zdList.djlx, data.djlx);
                            }
                            if(zdList.bdclx) {
                                data.bdclxmc = getZdMc(zdList.bdclx, data.bdclx);
                            }
                            if(zdList.zqzxyy) {
                                if(zdList.zqzxyy.length > 1) {
                                    $('#zxyy').append('<option value="">请选择</option>');
                                }

                                $.each(zdList.zqzxyy, function (index, item) {
                                    $('#zxyy').append('<option value="' + item.DM + '">' + item.MC + '</option>');
                                });
                                form.render('select');
                            }
                        }
                        form.val('form', data);
                    } else {
                        failMsg("未获取到注销申请信息");
                    }
                },
                complete: function () {
                    removeModal();
                }
            });
        }

        function getZdMc(zd, zdx) {
            var zdmc = "";
            $.each(zd, function (index, item) {
                if(item.DM == zdx) {
                    zdmc = item.MC;
                }
            });
            return zdmc;
        }

        /**
         * 新增或更新注销申请信息
         */
        form.on('submit(submitBtn)', function(data) {
            parent.setStateOfSave("save");
            var val = data.field;

            addModel();
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/zq/zxsq",
                type: "POST",
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(val),
                dataType: "text",
                success: function (data) {
                    if(!isNullOrEmpty(data)){
                        sqxxid = data;
                        $("#sqxxid").val(data);

                        if("edit" == state) {
                            successMsg("保存成功！");
                        } else {
                            successMsg("保存成功，请上传附件！");
                        }
                    } else {
                        failMsg("保存注销申请信息失败，请重试");
                    }
                },
                error: function (xhr, status, error) {
                    failMsg("保存注销申请信息失败，请重试");
                },
                complete: function () {
                    removeModal();
                }
            });
        });

        // 上传附件材料
        $('.fjcl').on('click',function () {
            if(isNullOrEmpty(sqxxid)) {
                warnMsg("请先保存注销申请信息！");
                return;
            }
            uploadFj(sqxxid);
            parent.setStateOfSave("fjcl");
        });

        $(document).keydown(function (event) {
            if (event.keyCode == 13) {
                $("#submitBtn").click();
            }
        });

        $('.bdc-frame-close').on('click',function () {
            closeWin();
        });

        function closeWin() {
            var index=parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        }
    });
});

/**
 *  创建附件文件夹，附件上传
 */
var storageId = "";
function uploadFj(sqxxid){
    if(isNullOrEmpty(storageId)) {
        $.ajax({
            url: '/realestate-inquiry-ui/rest/v1.0/zq/zxsq/fjcl?sqxxid=' + sqxxid,
            type: 'GET',
            dataType: "text",
            success: function (id) {
                if (isNullOrEmpty(id)) {
                    layer.msg("文件夹生成失败");
                } else {
                    storageId = id;
                    upload(sqxxid);
                }
            }, error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        });
    } else {
        upload(sqxxid);
    }
}

/**
 * 附件上传
 * @param sqxxid 申请信息ID
 */
function upload(sqxxid) {
    var bdcSlWjscDTO = queryBdcSlWjscDTO();
    bdcSlWjscDTO.spaceId = sqxxid;
    bdcSlWjscDTO.storageUrl =document.location.protocol + "//" + document.location.host + "/storage";
    var url = "/realestate-inquiry-ui/yancheng/zq/sjd.html?paramJson=" + encodeURI(JSON.stringify(bdcSlWjscDTO));
    parent.openSjcl(url, "附件上传");
}

/**
 * 查询收件材料所需参数（打开附件上传使用）
 */
function queryBdcSlWjscDTO() {
    var bdcSlWjscDTO = {};
    $.ajax({
        url: '/realestate-inquiry-ui/rest/v1.0/dwgz/getFileCs',
        type: 'GET',
        async: false,
        contentType: 'application/json;charset=utf-8',
        success: function (data) {
            if (isNotBlank(data)) {
                bdcSlWjscDTO = data;
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
    return bdcSlWjscDTO;
}
