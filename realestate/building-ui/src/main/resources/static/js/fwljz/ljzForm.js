var storageUrl = $("#storageUrl").val();
var fwDcbIndex = $("#fwDcbIndex").val();
// 户室图主键
var fwHstIndex = '';
var zdList = {};
$.ajax({
    url: "../zd/mul",
    dataType: "json",
    data: {
        zdDoNames: "SZdFwytDO,SZdQtgsDO,SZdBdcdyFwlxDO,SZdFwjgDO,SZdJzwztDO,SZdGyfsDO,SZdQlrxzDO,SZdQlrxbDO,SZdZjllxDO"
    },
    async: false,
    success: function (data) {
        zdList = $.extend({}, data)
    }
});


layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate', 'upload'], function () {
    var form = layui.form;
    var $ = layui.jquery;
    var laydate = layui.laydate;
    var laytpl = layui.laytpl;
    var element = layui.element;
    var upload = layui.upload;
    //处理列表选择
    var tpl = $("#DmMcTpl").html();
    if (tpl && zdList) {
        $.each(zdList, function (key, value) {
            laytpl(tpl).render(value, function (html) {
                $("." + key).append(html);
            });
        })
    }
    laydate.render({
        elem: '#jgrq'
    });
    laydate.render({
        elem: '#ztrq'
        , type: 'datetime'
    });
    laydate.render({
        elem: '#dcsj'
        , type: 'datetime'
    });
    //form初始化
    form.render();

    //表单初始化
    if ($("#fwDcbIndex").val()) {
        disableBdcdyfwlx();
        loadFwLjz();
    } else {
        // 如果是新增 BDCDYFWLX 则 默认选中 独幢
        $("select[name='bdcdyfwlx']").val("2");
        form.render();
        toggleTab(2);
        if (!$("#lszd").val()) {
            $("#gjzd").removeClass("layui-hide");
            $("#qxgjzd").addClass("layui-hide");
        } else {
            $("#gjzd").addClass("layui-hide");
        }
        if (!$("#zrzh").val()) {
            $("#gjzrz").removeClass("layui-hide");
        }
    }

    //如果从外部传递了房屋项目主键，说明为多幢
    var fwXmxxIndex = $("#fwXmxxIndex").val();
    if (fwXmxxIndex) {
        disableBdcdyfwlx();
        toggleTab(1);
        form.val("form", {bdcdyfwlx: '1'})
    }
    //提交表单
    form.on("submit(ljzForm)", function (data) {
        var postData = data.field;
        //删除不动产单元号中的空格
        postData.bdcdyh = postData.bdcdyh.replace(/\s*/g, "");
        var bdcdyh = postData.bdcdyh;
        var lszd = postData.lszd;
        var bdcdyfwlx = $("#bdcdyfwlx").val();
        if (bdcdyh && bdcdyfwlx == '2') {
            // 如果是BDCDYH 和  LSZD 不符
            var bdcdyhDjh = bdcdyh.substring(0, 19);
            if (bdcdyhDjh != lszd) {
                layer.confirm('挂接宗地与已有BDCDYH不一致，是否重新生成？', function (index) {
                    submit(postData);
                });
            } else {
                submit(postData);
            }
        } else {
            submit(postData);
        }
        return false;
    });

    function submit(postData) {
        var formLayer = layer;
        addModel();
        $.ajax({
            url: "../ljz/saveorupdate",
            dataType: "json",
            type: "post",
            data: {jsonData: JSON.stringify(formSubmitDealJson(postData))},
            success: function (data) {
                if (data && data.success) {
                    disableBdcdyfwlx();
                    if (data.data) {
                        if (data.data.bdcdyh) {
                            data.data.bdcdyh = splitBdcdyh(data.data.bdcdyh);
                        }
                        form.val("form", data.data);
                    }
                    if (!data.data.lszd) {
                        $("#gjzd").removeClass("layui-hide");
                        $("#qxgjzd").addClass("layui-hide");
                    } else {
                        $("#gjzd").addClass("layui-hide");
                        $("#qxgjzd").removeClass("layui-hide");
                    }
                    if (!data.data.zrzh) {
                        $("#gjzrz").removeClass("layui-hide");
                    } else {
                        $("#gjzrz").addClass("layui-hide");
                    }
                    loadQlrList();
                    removeModal();
                    refreshAndDeleteLater("提交成功",true,false);
                    if(!GetQueryString("fwDcbIndex")){
                        addParamToFrameUrl("fwDcbIndex=" + data.data.fwDcbIndex);
                    }
                } else if (data && data.msg) {
                    formLayer.msg(data.msg)
                } else {
                    formLayer.msg("提交失败")
                }
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr,this);
            }
        });
    }

    $("#slickQlr").click(function () {
        if($("#qlrList").children().length == 0){
            if ($("#fwDcbIndex").val()) {
                loadQlrList();
            } else {
                generateQlrList();
            }
        }
    });

    $("#slickPmt").click(function () {
        if ($("#fwDcbIndex").val()) {
            var imgElementSrc = $("#img").attr("src");
            if (!imgElementSrc) {
                // 查询 户室图
                $.ajax({
                    url: "../hst/queryfwljzpmt",
                    dataType: "json",
                    data: {
                        fwDcbIndex: $("#fwDcbIndex").val()
                    },
                    async: false,
                    success: function (data) {
                        if (data.fwHstIndex) {
                            fwHstIndex = data.fwHstIndex;
                        }
                        if (data.src) {
                            renderTpl({srcUrl: data.src});
                        } else {
                            renderTpl({});
                        }
                    },
                    error: function (xhr, status, error) {
                        delAjaxErrorMsg(xhr,this)
                    }
                });
            }
        } else {
            $("#hst").addClass("layui-hide");
            layer.alert("请先保存逻辑幢信息");
        }
    });

    $("#viewLpbBtn").click(function () {
        if ($("#fwDcbIndex").val()) {
            var backUrl = window.location.href;
            if (!GetQueryString("fwDcbIndex")) {
                backUrl += "&fwDcbIndex=" + $("#fwDcbIndex").val();
            }
            toView(getIP() + "/lpb/view?code=hslist&fwDcbIndex=" + $("#fwDcbIndex").val(),
                {fromurl: backUrl,tabname:"楼盘表"});
        } else {
            layer.msg("请先保存逻辑幢");
        }
    });

    //根据不动产类型判断显示哪些
    form.on('select(bdcdyfwlx)', function (data) {
        if (data.value != "2") {
            $("#addQlr").addClass("layui-hide")
            $("#slickDcxx").addClass("layui-hide")
            $("#slickQlr").addClass("layui-hide")
            $("#slickPmt").addClass("layui-hide")
            $("#qlrForm").addClass("layui-hide")
            $("#dcxxForm").addClass("layui-hide")
            $("#bdcdyhFrom").addClass("layui-hide")
        } else {
            $("#addQlr").removeClass("layui-hide")
            $("#slickDcxx").removeClass("layui-hide")
            $("#slickQlr").removeClass("layui-hide")
            $("#slickPmt").removeClass("layui-hide")
            $("#qlrForm").removeClass("layui-hide")
            $("#dcxxForm").removeClass("layui-hide")
            $("#bdcdyhFrom").removeClass("layui-hide")
        }
    });

    //拖拽上传
    upload.render({
        elem: '#hst'
        , url: '../hst/uploadfwljzpmt'
        , accept: 'images'
        , exts: 'jpg|png|jpeg'
        , data: {
            fwDcbIndex: function () {
                return $("#fwDcbIndex").val()
            }
        }
        , auto: true
        , done: function (res) {
            removeModal();
            if (res.success && res.imgId && res.fwHstIndex) {
                fwHstIndex = res.fwHstIndex;
                var imgUrl = storageUrl + "/rest/files/download/" + res.imgId;
                renderTpl({srcUrl: imgUrl});
                layer.msg("上传成功");
                layer.closeAll();
            } else {
                layer.msg("上传失败");
                layer.closeAll();

            }
        }
        , before: function () {
            addModel();
        }
        , error: function () {
            removeModal();
        }
    });

});

//页面入口
function loadFwLjz() {
    //获取数据
    addModel();
    $.ajax({
        url: "../ljz/infoljz",
        dataType: "json",
        data: {
            fwDcbIndex: $("#fwDcbIndex").val()
        },
        success: function (data) {
            removeModal();
            layer.closeAll();
            //处理查询出来的数据
            if (data) {
                writeFormxx(data);
            }
        },
        error: function (xhr, status, error) {
            layer.msg("没有权利人数据");
        }
    });
}

function writeFormxx(data) {
    layui.use(['form'], function () {
        var form = layui.form;
        if (data.bdcdyh) {
            data.bdcdyh = splitBdcdyh(data.bdcdyh);
        }
        form.val("form", data);
        if (!$("#bdcdyh").val() && data.bdcdyfwlx == '2') {
        }
        if (data.bdcdyfwlx == 2) {
            // generateQlrList(data.qlrList, 0)
            toggleTab(data.bdcdyfwlx);
        }
        if (!$("#lszd").val()) {
            $("#gjzd").removeClass("layui-hide");
            $("#qxgjzd").addClass("layui-hide");
        } else {
            $("#gjzd").addClass("layui-hide");
            $("#qxgjzd").removeClass("layui-hide");
        }
        if (!$("#zrzh").val()) {
            $("#gjzrz").removeClass("layui-hide");
        }
    });
}

//添加权利人
$("#addQlr").click(function () {
    $("html,body").animate({scrollTop: $('#qlrForm').offset().top - 99 + "px"}, 200);
    generateQlrList([{}], $(".layui-colla-item").length);
});

//页面入口
function loadQlrList() {
    //获取数据
    addModel();
    $.ajax({
        url: "../fwqlr/listqlr",
        dataType: "json",
        data: {
            fwIndex: $("#fwDcbIndex").val()
        },
        success: function (data) {
            removeModal();
            //处理查询出来的数据
            if (data && data.success) {
                if (data.data) {
                    generateQlrList(data.data, 0)
                }
            } else {
                generateQlrList();
            }

        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr,this)
        }
    });
}

//生成权利人list表单 qlrList：权利人列表 listStartWhith：从第几个开始生成 isFg：是否需要覆盖
function generateQlrList(qlrList, listStartWhith) {
    layui.use(['form', 'jquery', 'laytpl', 'element'], function () {
        var form = layui.form;
        var $ = layui.jquery;
        var laytpl = layui.laytpl;
        var element = layui.element;
        if (qlrList == null) {
            qlrList = [{}]
        }
        if (listStartWhith == null) listStartWhith = 0;
        var json = {
            start: listStartWhith,
            data: qlrList,
            zdList: zdList
        }
        //获取模板
        var tpl = $("#qlrTpl").html();
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            if (listStartWhith === 0) {
                //如果覆盖之前的
                $("#qlrList").html(html);
            } else {
                $("#qlrList").append(html);
            }
        });
        form.render();
        element.render();
        listStartWhith = listStartWhith + qlrList.length;
        if (qlrList.length == 0) {
            listStartWhith++;
        }
        return listStartWhith;
    })
}


//删除权利人
function delQlr(delDom) {
    event.stopPropagation();
    layer.open({
        content: '删除操作后会重新加载数据，确定是否有未提交数据？'
        , btn: ['确定', '取消']
        , yes: function (index, layero) {
            if (delDom.id) {
                layer.open({
                    content: '这条权利人已经入库，将直接从数据库删除，是否确定？'
                    , btn: ['确定', '取消']
                    , yes: function (index, layero) {
                        $.ajax({
                            url: "../fwqlr/deleteqlr",
                            dataType: "json",
                            data: {
                                fwFcqlrIndex: delDom.id
                            },
                            success: function (data) {
                                if (data && data.success) {
                                    layer.msg("成功");
                                    loadQlrList();
                                } else {
                                    layer.alert("删除失败，请检查数据!")
                                }
                                layer.closeAll();
                            },
                            error: function (xhr, status, error) {
                                delAjaxErrorMsg(xhr,this)
                            }
                        });
                    }

                });
            } else {
                $(delDom).parent().parent().remove();
                layer.closeAll();
            }
        }
    });
}

// 挂接宗地
$("#gjzd").click(function () {
    var bdcdyfwlx = $("#bdcdyfwlx").val();
    var fwXmxxIndex = $("#fwXmxxIndex").val();
    // 如果是户室类型
    if (bdcdyfwlx == '4') {
        if (!checkHasFwhs()) {
            showPickZdView();
        } else {
            layer.confirm('逻辑幢下已有户室，挂接宗地后BDCDYH会发生变化，是否确认挂接？', function (index) {
                showPickZdView();
                layer.close(index);
            });
        }
    } else {
        showPickZdView();
    }
});

// 选择宗地列表
function showPickZdView() {
    layer.open({
        type: 2,
        title: "挂接宗地",
        area: ['960px', '437px'],
        fixed: false, //不固定
        content: '../zd/picklist'
    });
}

// 如果是项目类型  则获取项目的隶属宗地 和 BDCDYH
function getXmxxLszdAndSetXmBdcdyh() {
    var xmLszd = "";
    var fwXmxxIndex = $("#fwXmxxIndex").val();
    $.ajax({
        url: "../xmxx/infofwxmxx",
        dataType: "json",
        data: {
            fwXmxxIndex: fwXmxxIndex
        },
        async: false,
        success: function (data) {
            if (data && data.lszd) {
                xmLszd = data.lszd;
            }
            if (data && data.bdcdyh) {
                $("#xmBdcdyh").val(data.bdcdyh);
            }
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr,this)
        }
    });
    return xmLszd;
}

// 挂接自然幢
$("#gjzrz").click(function () {
    var lszd = $("#lszd").val();
    if (lszd) {
        var index = layer.open({
            type: 2,
            title: "挂接自然幢",
            area: ['960px', '437px'],
            fixed: false, //不固定
            content: '../zrz/picklist?lszd=' + lszd
        });
    } else {
        layer.msg("请先挂接宗地");
    }
});


// 取消挂接宗地
$("#qxgjzd").click(function () {
    var lszd = $("#lszd").val();
    var fwDcbIndex = $("#fwDcbIndex").val();
    if (lszd) {
        layer.confirm('取消挂接宗地会清空相关不动产单元的不动产单元号，确认取消？', function (index) {
            $.ajax({
                url: "../ljz/qxgjzd",
                dataType: "json",
                data: {
                    fwDcbIndex: fwDcbIndex
                },
                success: function (data) {
                    loadFwLjz();
                },
                error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr,this)
                }
            });
        });
    }
});

$("#getLjzh").click(function () {
    var lszd = $("#lszd").val();
    if (lszd) {
        $.ajax({
            url: "../ljz/getljzh",
            dataType: "json",
            data: {
                lszd: lszd
            },
            success: function (data) {
                $("#ljzh").val(data);
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr,this)
            }
        });
    } else {
        layer.msg("请挂接宗地");
    }
});

function changeViewByBdcdyfwlx() {
    $("#addQlr").removeClass("layui-hide");
    $("#slickDcxx").removeClass("layui-hide");
    $("#slickQlr").removeClass("layui-hide");
    $("#slickPmt").removeClass("layui-hide");
    $("#qlrForm").removeClass("layui-hide");
    $("#dcxxForm").removeClass("layui-hide");
    $("#bdcdyhFrom").removeClass("layui-hide");
}

function disableBdcdyfwlx() {
    $("#bdcdyfwlx").attr("disabled", true);
    layui.use(['form'], function () {
        var form = layui.form;
        form.render();
    })
}

//修改逻辑幢对应的地籍号，主要是给挂接宗地用
function pickZdlistCallback(changeDjh) {
    var bdcdyfwlx = $("#bdcdyfwlx").val();
    var bdcdyh = $("#bdcdyh").val();
    // 如果项目的BDCDYH和逻辑幢的不一致 给出提示。
    if (changeDjh && bdcdyfwlx == '1') {
        // 查询项目的LSZD 和 BDCDYH
        var xmlszd = getXmxxLszdAndSetXmBdcdyh();
        var xmBdcdyh = $("#xmBdcdyh").val();

        if (xmBdcdyh && changeDjh == xmBdcdyh.substring(0, 19) && xmlszd == changeDjh) {
            $("#lszd").val(changeDjh);
        } else {
            layer.confirm('所选宗地与逻辑幢所属项目的不动产单元号不一致，是否继续？', function (index) {
                $("#lszd").val(changeDjh);
                layer.close(index);
            });
        }
    } else {
        $("#lszd").val(changeDjh);
    }
}

// 下载图片
$("#downHst").click(function () {
    var srcURL = $("#img").attr("src");
    if (srcURL && srcURL.indexOf("storage") > 0) {
        window.open(srcURL + "?attachment=1");
    } else {
        layer.msg("当前图片不支持下载");
    }
});


// 删除按钮
$("#deletHst").click(function () {
    // 删除户室图
    $.ajax({
        url: "../hst/delfwljzpmt",
        dataType: "json",
        data: {
            fwDcbIndex: $("#fwDcbIndex").val()
        },
        async: false,
        success: function (data) {
            if (data.success) {
                renderTpl({});
                // 户室图主键清空
                fwHstIndex = "";
                layer.msg("删除成功");
            }
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr,this)
        }
    });

});


function renderTpl(json) {
    $("#hst").removeClass("layui-hide");
    if (json.srcUrl) {
        $("#downHst").removeClass("layui-hide");
        $("#deletHst").removeClass("layui-hide");
    } else {
        $("#downHst").addClass("layui-hide");
        $("#deletHst").addClass("layui-hide");
    }
    layui.use('laytpl', function () {
        //获取模板
        var tpl = $("#uploadTpl").html();
        var laytpl = layui.laytpl;
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            $("#hst").html(html);
        });
    })
}

function pickZrzCallBack(data) {
    if (data.zrzh) {
        $("#zrzh").val(data.zrzh);
        $("#gjzrz").addClass("layui-hide");
    }
}

function checkHasFwhs() {
    var result = true;
    var fwDcbIndex = $("#fwDcbIndex").val();
    if (fwDcbIndex) {
        $.ajax({
            url: "../fwhs/listljzidbypage",
            dataType: "json",
            data: {
                fwDcbIndex: fwDcbIndex
            },
            async: false,
            success: function (data) {
                if (!data.content || data.content.length == 0) {
                    result = true;
                } else {
                    $.ajax({
                        url: "../fwychs/listbypage",
                        dataType: "json",
                        data: {
                            fwDcbIndex: fwDcbIndex
                        },
                        async: false,
                        success: function (data) {
                            if (!data.content || data.content.length == 0) {
                                result = true;
                            } else {
                                result = false;
                            }
                        },
                        error: function (xhr, status, error) {
                            delAjaxErrorMsg(xhr,this)
                        }
                    });
                }
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr,this)
            }
        });
    }
    return result;
}

function toggleTab(bdcdyfwlx){
    if (bdcdyfwlx != "2") {
        $("#addQlr").addClass("layui-hide")
        $("#slickDcxx").addClass("layui-hide")
        $("#slickQlr").addClass("layui-hide")
        $("#slickPmt").addClass("layui-hide")
        $("#qlrForm").addClass("layui-hide")
        $("#dcxxForm").addClass("layui-hide")
        $("#bdcdyhFrom").addClass("layui-hide")
    } else {
        $("#addQlr").removeClass("layui-hide")
        $("#slickDcxx").removeClass("layui-hide")
        $("#slickQlr").removeClass("layui-hide")
        $("#slickPmt").removeClass("layui-hide")
        $("#qlrForm").removeClass("layui-hide")
        $("#dcxxForm").removeClass("layui-hide")
        $("#bdcdyhFrom").removeClass("layui-hide")
    }
}
