// 文档中心地址
var storageUrl =  $("#storageUrl").val();
// 户室图主键
var fwHstIndex = '';

var zdList = {};
$.ajax({
    url: "../zd/mul",
    dataType: "json",
    data: {zdDoNames: "SZdFwlxDO,SZdFwxzDO,SZdGyfsDO,SZdQlrxzDO,SZdQlrxbDO,SZdZjllxDO"},
    async: false,
    success: function (data) {
        zdList = $.extend({}, data)
    }
});
if ($("#lszd").val()) {
    $("#gjzd").addClass("layui-hide");
}

layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate', 'table', 'upload'], function () {
    var form = layui.form;
    var $ = layui.jquery;
    var laydate = layui.laydate;
    var laytpl = layui.laytpl;
    var element = layui.element;
    var table = layui.table;
    var upload = layui.upload;

    //处理列表选择
    var tpl = $("#DmMcTpl").html();
    $.each(zdList, function (key, value) {
        laytpl(tpl).render(value, function (html) {
            $("." + key).append(html);
        });
    });

    //form初始化
    form.render();

    laydate.render({
        elem: '#dcsj'
        , type: 'datetime'
    });

    if ($("#fwXmxxIndex").val()) {
        loadFwXmxx();
    }
    //提交表单
    form.on("submit(xmxxForm)", function (data) {
        var postData = data.field;
        //删除不动产单元号中的空格
        postData.bdcdyh = postData.bdcdyh.replace(/\s*/g, "");
        var bdcdyh = postData.bdcdyh;
        var lszd = postData.lszd;
        if (bdcdyh) {
            // 如果是BDCDYH 和  LSZD 不符
            var bdcdyhDjh = bdcdyh.substring(0, 19);
            if (bdcdyhDjh != lszd) {
                layer.confirm('挂接宗地与已有BDCDYH不一致，是否重新生成？', function (index) {
                    submitXmxx(postData);
                });
            } else {
                submitXmxx(postData);
            }
        } else {
            submitXmxx(postData);
        }
        return false;
    });

    function submitXmxx(postData) {
        var formLayer = layer;
        addModel();
        $.ajax({
            url: "../xmxx/saveorupdate",
            dataType: "json",
            type: "post",
            data: {jsonData: JSON.stringify(formSubmitDealJson(postData))},
            success: function (data) {
                if (data && data.success) {
                    if (data.data) {
                        if (data.data.bdcdyh) {
                            data.data.bdcdyh = splitBdcdyh(data.data.bdcdyh);
                        }
                        form.val("form", data.data)
                    }
                    if (!data.data.lszd) {
                        $("#gjzd").removeClass("layui-hide");
                        $("#qxgjzd").addClass("layui-hide");
                    } else {
                        $("#gjzd").addClass("layui-hide");
                        $("#qxgjzd").removeClass("layui-hide");
                    }
                    loadQlrList();
                    removeModal();
                    refreshAndDeleteLater("提交成功",true,false);
                    if(!GetQueryString("fwXmxxIndex")){
                        addParamToFrameUrl("fwXmxxIndex=" + data.data.fwXmxxIndex);
                    }
                } else if (data && data.msg) {
                    formLayer.alert(data.msg)
                } else {
                    formLayer.alert("提交失败")
                }
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr,this)
            }
        });
    }

    $("#qlrTab").click(function () {
        if($("#qlrList").children().length == 0){
            if ($("#fwXmxxIndex").val()) {
                loadQlrList();
            } else {
                generateQlrList();
            }
        }
    });

    $("#ljzTab").click(function () {
        if($("#ljzTable").next().length == 0){
            loadDataTablbeByUrl("#ljzTable", tableConfig);
        }
    });

    $("#pmtTab").click(function () {
        loadPmt();
    });

    $(".layui-tab-title li").click(function () {
        saveSessionParam({curTab: $(this).attr("id")});
    });

    if (sessionParamObjet.curTab == "ljzTab") {
        $("#ljzTab").click();
        loadDataTablbeByUrl("#ljzTable", tableConfig);
    }

    upload.render({
        elem: '#hst'
        , url: '../hst/uploadfwljzpmt'
        , data: {
            fwDcbIndex: function () {
                return $("#fwXmxxIndex").val()
            }
        }
        , accept: 'file'
        , auto: true
        , exts: 'jpg|png|jpeg'
        , before: function () {
            addModel();
        }
        , done: function (res) {
            removeModal();
            if (res.success && res.imgId && res.fwHstIndex) {
                fwHstIndex = res.fwHstIndex;
                var imgUrl = storageUrl + "/rest/files/download/" + res.imgId;
                renderTpl({srcUrl: imgUrl});
                layer.closeAll();
                layer.msg("上传成功");
            } else {
                layer.closeAll();
                layer.msg("上传失败");
            }
        }
        , error: function () {
            removeModal();
        }
    });

    //头工具栏事件
    table.on('toolbar(dataTable)', function (obj) {
        if ($("#fwXmxxIndex").val() && $("#lszd").val()) {
            if (obj.event == "addLjz") {
                toView(getIP() + '/ljz/saveorupdateljz?fwXmxxIndex=' + $("#fwXmxxIndex").val()
                    + '&djh=' + $("#lszd").val(), {fromurl: getBackUrl(),tabname:"新增逻辑幢"});
            }
            if (obj.event == "relevanceLjz") {
                var index = layer.open({
                    type: 2,
                    title: "关联逻辑幢",
                    area: ['960px', '430px'],
                    fixed: false, //不固定
                    content: '../xmxx/relevanceljzlist?lszd=' + $("#lszd").val()
                });
            }
        } else {
            layer.alert("请先保存项目信息后维护项目下逻辑幢")
        }

    });

    table.on('tool(dataTable)', function (obj) {
        var data = obj.data;
        if (data && data.fw_dcb_index) {
            if (obj.event === "editLjz") { //修改
                toView(getIP() + '/ljz/saveorupdateljz?fwDcbIndex=' + data.fw_dcb_index + '&djh=' + data.lszd
                    + '&djh=' + $("#lszd").val(), {fromurl: getBackUrl(),tabname:"修改逻辑幢"});
            }
            if (obj.event === "deleteLjz") { //删除
                layer.confirm('确定删除？', function (index) {
                    deleteLjzFun(data.fw_dcb_index);
                });
            }
            if (obj.event === "cancelRelevanceLjz") { //删除
                layer.open({
                    type: 2,
                    title: "不动产单元房屋类型",
                    maxmin: true,
                    area: ['400px', '250px'],
                    fixed: false, //不固定
                    content: '../xmxx/cancelrelevanceljzview?fwDcbIndex=' + data.fw_dcb_index
                    , end: function (index, layero) {
                        refreshView();
                        return false;
                    }
                });
            }
        } else {
            layer.alert("当前数据主键缺失，请检查数据");
            return false
        }
    });
});

function writeFwXmxx(data) {
    layui.use(['form'], function () {
        var form = layui.form;
        if (data.bdcdyh) {
            data.bdcdyh = splitBdcdyh(data.bdcdyh);
        }
        form.val("form", data);
        if (!$("#lszd").val()) {
            $("#gjzd").removeClass("layui-hide");
            $("#qxgjzd").addClass("layui-hide");
        } else {
            $("#qxgjzd").removeClass("layui-hide");
            $("#gjzd").addClass("layui-hide");
        }
    });
}

//页面入口
function loadFwXmxx() {
    //获取数据
    addModel();
    $.ajax({
        url: "../xmxx/infofwxmxx",
        dataType: "json",
        data: {
            fwXmxxIndex: $("#fwXmxxIndex").val()
        },
        success: function (data) {
            removeModal();
            layer.closeAll();
            //处理查询出来的数据
            writeFwXmxx(data)
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr,this)
        }
    });
}

//添加权利人
$("#addQlr").click(function () {
    $("html,body").animate({scrollTop: $('#qlrForm').offset().top - 119 + "px"}, 200);
    generateQlrList([{}], $(".layui-colla-item").length);
});

var tableConfig = {
    toolbar: "#toolbar"
    , url: '../ljz/listbypage' //数据接口
    , where: {fwXmxxIndex: $("#fwXmxxIndex").val() ? $("#fwXmxxIndex").val() : "rrrrrrr"}
    , cols: [[
        {type: 'radio', fixed: 'left', align: 'center', width: '3%'},
        {type: 'numbers', fixed: 'left', width: '3%'},
        {
            field: 'lszd', title: '隶属宗地', width: '15%'
            , templet: function (d) {
                return splitDjh(d.lszd || '');
            }
        },
        {field: 'zrzh', title: '自然幢号', width: '10%'},
        {field: 'ljzh', title: '逻辑幢号', width: '15%'},
        {
            field: 'bdcdyh', title: '不动产单元号', width: '20%'
            , templet: function (d) {
                return splitBdcdyh(d.bdcdyh);
            }
        },
        {
            field: 'bdcdyfwlx', title: '房屋类型', width: '10%',
            templet: function (d) {
                return convertZdDmToMc("SZdBdcdyFwlxDO", d.bdcdyfwlx);
            }
        },
        {field: 'fwmc', title: '房屋名称', width: '10%'},
        {title: '操作', align: 'center', fixed: 'right', toolbar: '#ljzListToolBarTmpl', width: '14%'}
    ]]
}

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

//页面入口
function loadQlrList() {
    //获取数据
    addModel();
    $.ajax({
        url: "../fwqlr/listqlr",
        dataType: "json",
        data: {
            fwIndex: $("#fwXmxxIndex").val()
        },
        success: function (data) {
            removeModal();
            //处理查询出来的数据
            if (data && data.success) {
                if (data.data) {
                    generateQlrList(data.data, 0)
                }
            } else{
                generateQlrList();
            }
            //if ($("#qlrList").find(".layui-colla-item").length < 1)
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

function deleteLjzFun(fwDcbIndex) {
    addModel();
    //获取数据
    $.ajax({
        url: "../ljz/delbyfwdcbindex",
        contentType: "application/json;charset=utf-8",
        type: "GET",
        data: {
            fwDcbIndex: fwDcbIndex
        },
        success: function (data) {
            removeModal();
            layer.closeAll();
            if (!data || !data.success) {
                layer.alert("删除失败");
            }
            refreshView();
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr,this)
        }
    });
}

function refreshView() {
    $("#queryLjz").click();
}

function loadPmt() {
    // 查询 户室图
    var imgElementSrc = $("#img").attr("src");
    if ($("#fwXmxxIndex").val()) {
        if (!imgElementSrc) {
            $.ajax({
                url: "../hst/queryfwljzpmt",
                dataType: "json",
                data: {
                    fwDcbIndex: $("#fwXmxxIndex").val()
                },
                async: false,
                success: function (data) {
                    if (data.fwHstIndex) {
                        fwHstIndex = data.fwHstIndex;
                    }
                    if (data.src) {
                        renderTpl({srcUrl: data.src});
                        $('#pmtTab').attr('id', 'newPmtTab');
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
        layer.alert("请先保存项目信息");
    }
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

// 取消挂接宗地
$("#qxgjzd").click(function () {
    var lszd = $("#lszd").val();
    var fwXmxxIndex = $("#fwXmxxIndex").val();
    if (lszd) {
        layer.confirm('取消挂接宗地会清空项目的不动产单元号，确认取消？', function (index) {
            $.ajax({
                url: "../xmxx/qxgjzd",
                dataType: "json",
                data: {
                    fwXmxxIndex: fwXmxxIndex
                },
                success: function (data) {
                    loadFwXmxx();
                },
                error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr,this)
                }
            });
        });
    }
})

$("#gjzd").click(function () {
    layer.open({
        type: 2,
        title: "挂接宗地",
        area: ['960px', '437px'],
        fixed: false, //不固定
        content: '../zd/picklist'
    });
});

$("#queryLjz").click(function () {
    if ($("#fwXmxxIndex").val()) {
        tableReload('ljzTable', {ljzh: $("#search-ljzh").val(), fwXmxxIndex: $("#fwXmxxIndex").val()})
    }
});

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
            fwDcbIndex: $("#fwXmxxIndex").val()
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

//修改逻辑幢对应的地籍号，主要是给挂接宗地用
function pickZdlistCallback(changeDjh) {
    $("#lszd").val(changeDjh);
}

function relevanceLjz(data) {
    var fwDcbIndex = data.fw_dcb_index;
    addModel();
    $.ajax({
        url: "../xmxx/relevanceljz",
        type: "post",
        traditional: true,
        data: {
            fwDcbIndex: fwDcbIndex,
            fwXmxxIndex: $("#fwXmxxIndex").val()
        },
        success: function (data) {
            if (data && data.success) {
                removeModal()
                layer.closeAll()
                layer.msg(data.msg)
                refreshView();
            }
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr,this)
        }
    });
}

function getBackUrl() {
    var backUrl = window.location.href;
    if (!GetQueryString("fwXmxxIndex")) {
        backUrl += "&fwXmxxIndex=" + $("#fwXmxxIndex").val();
    }
    return backUrl;
}