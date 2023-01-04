var zdList = {};
// 文档中心地址
var storageUrl = $("#storageUrl").val();

// 房屋户室主键
var fwHsIndex = $("#fwHsIndex").val();

// 户室图主键
var fwHstIndex = '';
var bdcdyfwlx = "";
$.ajax({
    url: "../zd/mul",
    dataType: "json",
    data: {
        zdDoNames: "SZdFwhxDO,SZdJczxcdDO,SZdFwytDO,SZdDldmDO,SZdTdsyqlxDO,SZdFwlxDO,SZdFwxzDO,SZdQtgsDO,SZdBoolDO,SZdHxjgDO,SZdGyfsDO,SZdQlrxzDO,SZdQlrxbDO,SZdZjllxDO,SZdSyfttdmjjsDO"
    },
    async: false,
    success: function (data) {
        zdList = $.extend({}, data)
    }
});

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
    })

    laydate.render({
        elem: '#qsrq'
    });
    laydate.render({
        elem: '#zzrq'
    });
    laydate.render({
        elem: '#dcsj'
        , type: 'datetime'
    });

    //form初始化
    form.render();

    if ($("#fwDcbIndex").val()) {
        //根据逻辑幢类型判断是否显示不动产单元号
        $.ajax({
            url: "../ljz/infoljz",
            dataType: "json",
            data: {
                fwDcbIndex: $("#fwDcbIndex").val()
            },
            success: function (data) {
                if (data && data.bdcdyfwlx) {
                    bdcdyfwlx = data.bdcdyfwlx;
                    if (data.bdcdyfwlx == "4") {
                        $("input[name='bdcdyh']").parent().parent().removeClass("layui-hide")
                    }
                }
            }
        });
    }

    if ($("#fwHsIndex").val()) {
        loadFwhs();
        //loadQlrList()
        //加载表格
    }

    //提交表单
    form.on("submit(saveForm)", function (data) {
        var postData = data.field;
        //删除不动产单元号中的空格
        postData.bdcdyh = postData.bdcdyh.replace(/\s*/g, "");
        if ($("#fwDcbIndex").val()) {
            addModel();
            $.ajax({
                url: "../fwhs/saveorupdate",
                dataType: "json",
                type: "post",
                data: {jsonData: JSON.stringify(formSubmitDealJson(postData))},
                success: function (data) {
                    removeModal();
                    if (data && data.success) {
                        if (data.data) {
                            var fwhsDate = data.data;
                            form.val("form", fwhsDate);
                            loadQlrList();
                            refreshAndDeleteLater("提交成功",true,false);
                            if(!GetQueryString("fwHsIndex")){
                                addParamToFrameUrl("fwHsIndex=" + fwhsDate.fwHsIndex);
                            }
                        }
                    } else if (data && data.msg && !data.success) {
                        layer.msg(data.msg)
                    } else {
                        layer.msg("提交失败")
                    }
                },
                error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr,this)
                }
            });
        } else {
            layer.alert("逻辑幢主键为空，不能提交");
        }
        return false;
    });

    $("#qlrTab").click(function () {
        if($("#qlrList").children().length == 0){
            if ($("#fwHsIndex").val()) {
                loadQlrList();
            } else {
                generateQlrList();
            }
        }
    });

    $("#zhsTab").click(function () {
        if($("#fwzhsList").next().length == 0){
            loadDataTablbeByUrl("#fwzhsList", tableConfig);
        }
    });

    $("#hstTab").click(function () {
        if ($("#fwHsIndex").val()) {
            var imgElementSrc = $("#img").attr("src");
            if (!imgElementSrc) {
                // 查询 户室图
                $.ajax({
                    url: "../hst/queryfwhst?fwlx=sc",
                    dataType: "json",
                    data: {
                        fwHsIndex: $("#fwHsIndex").val()
                    },
                    async: false,
                    success: function (data) {
                        if (data.fwHstIndex) {
                            fwHstIndex = data.fwHstIndex;
                        }
                        if (data.src) {
                            renderTpl({srcUrl: data.src});
                            $("#downHst").removeClass("layui-hide");
                            $("#deletHst").removeClass("layui-hide");
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
            layer.alert("请先保存户室信息");
        }
    });

    if (sessionParamObjet.curTab == "zhsTab") {
        $("#zhsTab").click();
        loadDataTablbeByUrl("#fwzhsList", tableConfig);
    }

    //拖拽上传
    upload.render({
        elem: '#hst'
        , url: '../hst/uploadfwhst?fwHstIndex=' + fwHstIndex + "&fwlx=sc"
        , data: {
            fwHsIndex: function () {
                return $("#fwHsIndex").val()
            }
        }
        , accept: 'file'
        , auto: true
        , exts: 'jpg|png|jpeg'
        , done: function (res) {
            removeModal();
            if (res.success && res.imgId && res.fwHstIndex) {
                fwHstIndex = res.fwHstIndex;
                var imgUrl = storageUrl + "/rest/files/download/" + res.imgId;
                renderTpl({srcUrl: imgUrl});
                $("#downHst").removeClass("layui-hide");
                $("#deletHst").removeClass("layui-hide");
                layer.closeAll();
                layer.msg("上传成功");
            } else {
                layer.closeAll();
                layer.msg("上传失败");
            }
        }
        , before: function () {
            addModel();
        }
        , error: function () {
            removeModal();
        }
    });

    $("#jcyyhs").click(function () {
        var index = layer.open({
            type: 2,
            title: "选择已有户室",
            area: ['960px', '420px'],
            fixed: false, //不固定
            content: '../fwhs/listbyljz?fwDcbIndex=' + $("#fwDcbIndex").val()
        });
    });

    $(".layui-tab-title li").click(function () {
        saveSessionParam({curTab: $(this).attr("id")});
    });

    function writeFwhsxx(data) {
        if (data.bdcdyh) {
            data.bdcdyh = splitBdcdyh(data.bdcdyh);
        }
        form.val("form", data)
    }

    //页面入口
    function loadFwhs() {
        //获取数据
        addModel();
        $.ajax({
            url: "../fwhs/infofwhs",
            dataType: "json",
            data: {
                fwHsIndex: $("#fwHsIndex").val()
            },
            success: function (data) {
                removeModal();
                layer.closeAll();
                //处理查询出来的数据
                writeFwhsxx(data)

            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr,this)
            }
        });
    }

    //头工具栏事件
    table.on('toolbar(dataTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        var data = checkStatus.data;
        // 删除子户室
        if (obj.event === "delzhs") {
            if (data && data.length >= 1) {
                layer.confirm('确认删除？', function (index) {
                    var fwZhsIndexList = [];
                    for (var i = 0; i < data.length; i++) {
                        fwZhsIndexList.push(data[i].fwZhsIndex);
                    }
                    deleteFwzhsFun(fwZhsIndexList);
                });
            } else {
                layer.msg("至少选择一条数据");
            }
        }
        // 新增子户室
        if (obj.event == "addzhs") {
            if ($("#fwHsIndex").val()) {
                toView(getIP() + '/fwzhs/info?fwHsIndex=' + $("#fwHsIndex").val() + "&hslx=sc",
                    {fromurl: getBackUrl(),tabname:"新增子户室"});
                /** 打开合并页面
                 layer.open({
                        type: 2,
                        title: "新增子户室",
                        area: ['100%', '100%'],
                        content: "../fwzhs/info?fwHsIndex=" + $("#fwHsIndex").val() + "&hslx=" + "sc"
                        , end: function (index, layero) {
                            tableReload('fwzhsList',{fwHsIndex:$("#fwHsIndex").val()});
                            return false;
                        }
                    });*/
            } else {
                layer.alert("请先保存户室信息");
            }

        }
    });

    // 修改子户室
    table.on('tool(dataTable)', function (obj) {
        var data = obj.data;
        if (obj.event === "updatezhs") {
            if (data && data.fwZhsIndex) {
                var zhsIndex = data.fwZhsIndex;
                toView(getIP() + "/fwzhs/info?fwHsIndex=" + $("#fwHsIndex").val() + "&fwZhsIndex=" + zhsIndex + "&hslx=sc",
                    {fromurl: getBackUrl(),tabname:"修改子户室"});
            } else {
                layer.msg("子户室主键缺失");
            }
        }
    });

    function deleteFwzhsFun(fwZhsIndexList) {
        addModel();
        $.ajax({
            url: "../fwzhs/batchdel",
            type: "post",
            data: {
                fwZhsIndexListJson: JSON.stringify(fwZhsIndexList)
            },
            success: function (data) {
                removeModal();
                layer.closeAll();
                if (!data || !data.success) {
                    layer.alert("删除失败");
                }
                tableReload('fwzhsList', {fwHsIndex: $("#fwHsIndex").val()});
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr,this)
            }
        });
    }
});

//由子页面调用覆盖数据
function jchsxx(dom) {
    if (dom) {
        dom.bdcdyh = $("#bdcdyh").val();
        layui.use(['form'], function () {
            var form = layui.form;
            form.val("form", $.extend({}, dom))
        });
    }
}

//添加权利人
$("#addQlr").click(function () {
    $("html,body").animate({scrollTop: $('#qlrForm').offset().top - 119 + "px"}, 200);
    generateQlrList([{}], $(".layui-colla-item").length);
})

//加载权利人
function loadQlrList() {
    //获取数据
    addModel();
    $.ajax({
        url: "../fwqlr/listqlr",
        dataType: "json",
        data: {
            fwIndex: $("#fwHsIndex").val()
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
                                    layer.msg("成功")
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
    addModel();
    $.ajax({
        url: "../hst/delfwhst",
        dataType: "json",
        data: {
            fwHsIndex: $("#fwHsIndex").val(),
            hslx:"sc"
        },
        async: false,
        success: function (data) {
            removeModal();
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

// 编辑页面回调函数
function reloadTable(layerIndex) {
    layer.msg("提交成功");
    tableReload('fwzhsList', {fwHsIndex: $("#fwHsIndex").val()});
}

var tableConfig = {
    toolbar: "#toolbar"
    , url: '../fwzhs/listbypage' //数据接口
    , where: {fwHsIndex: ($("#fwHsIndex").val() ? $("#fwHsIndex").val() : "ttttttt")}
    , cols: [[
        {type: 'checkbox', fixed: 'left', align: 'center', width: '50'},
        {field: 'fjh', title: '房间号', width: '100'},
        {field: 'dyh', title: '单元号', width: '100'},
        {field: 'wlcs', title: '物理层数', width: '90'},
        {field: 'scjzmj', title: '实测建筑面积(㎡)', width: '180'},
        {field: 'sctnjzmj', title: '实测套内建筑面积(㎡)', width: '180'},
        {field: 'scftjzmj', title: '实测分摊建筑面积(㎡)', width: '180'},
        {field: 'ycjzmj', title: '预测建筑面积(㎡)', width: '180'},
        {field: 'yctnjzmj', title: '预测套内建筑面积(㎡)', width: '180'},
        {field: 'ycftjzmj', title: '预测分摊建筑面积(㎡)', width: '180'},
        {
            field: 'fwlx', title: '房屋类型', width: '100',
            templet: function (d) {
                return convertZdDmToMc("SZdFwlxDO", d.fwlx);
            }
        },
        {
            field: 'hxjg', title: '户型结构', width: '100',
            templet: function (d) {
                return convertZdDmToMc("SZdHxjgDO", d.hxjg);
            }
        },
        {title: '操作', align: 'center', fixed: 'right', width: '100', toolbar: '#fwzhsListToolBarTmpl'}
    ]]
}

function getBackUrl() {
    var backUrl = window.location.href;
    if (!GetQueryString("fwHsIndex")) {
        backUrl += "&fwHsIndex=" + $("#fwHsIndex").val();
    }
    return backUrl;
}