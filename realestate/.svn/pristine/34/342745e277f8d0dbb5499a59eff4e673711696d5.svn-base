var fwDcbIndex = $("#fwDcbIndex").val();
var qjgldm = $("#qjgldm").val();
layui.use(['jquery', 'form', 'table'], function () {
    var $ = layui.jquery;
    var form = layui.form;
    var table = layui.table;
    var cols = [[
        {type: 'checkbox', fixed: 'left', align: 'center', width: '50'},
        {field: 'fjh', title: '房间号', width: '80'},
        {field: 'dyh', title: '单元号', width: '70'},
        {field: 'glhs', title: '已关联', width: '90'},
        {field: 'bdcdyh', title: '不动产单元号', width: '200'
            ,templet: function (d) {
                return splitBdcdyh(d.bdcdyh);
            }
        },
        {field: 'wlcs', title: '物理层数', width: '100'},
        {field: 'zl', title: '坐落', width: '200'}
    ]]
    var tableYcConfig = {
        url: '../fwychs/gl/listbypage?sort=fjh&fwDcbIndex=' + fwDcbIndex+"&qjgldm="+qjgldm
        , cols: cols
    };
    var tableScConfig = {
        url: '../fwhs/gl/listbypage?sort=fjh&fwDcbIndex=' + fwDcbIndex+"&qjgldm="+qjgldm
        , cols: cols
    };

    loadDataTablbeByUrl("#fwhsTable", tableScConfig);
    loadDataTablbeByUrl("#fwychsTable", tableYcConfig);

    var $ = layui.$, active = {
        ycglsc: function () {
            var fwychsData = table.checkStatus('fwychsTable').data;
            var fwhsData = table.checkStatus('fwhsTable').data;
            if (fwychsData.length < 1 || fwhsData.length < 1) {
                layer.alert("请左右各最少选择一个")
            } else if (fwychsData.length == 1 || fwhsData.length == 1) {
                var postData = {};
                postData.qjgldm =qjgldm;
                $.each(fwychsData, function (index, data) {
                    postData["ychsIndexList[" + index + "]"] = data.fwHsIndex
                })
                var schsHasYcfwbm = false;
                $.each(fwhsData, function (index, data) {
                    if (data.ycfwbm) {
                        schsHasYcfwbm = true;
                        return false;
                    }
                    postData["schsIndexList[" + index + "]"] = data.fwHsIndex
                })
                if (schsHasYcfwbm) {
                    layer.alert("当前选择的实测户室已经有关联，请先取消");
                    return false;
                }
                addModel();
                $.ajax({
                    url: "../fwhsgl/ycschsgl",
                    dataType: "json",
                    data: postData,
                    success: function (data) {
                        removeModal();
                        if (data && data.success) {
                            layer.msg("关联成功");
                            reloadTableLsit()
                        } else {
                            layer.alert(data.msg)
                        }
                    },
                    error: function (xhr, status, error) {
                        delAjaxErrorMsg(xhr,this)
                    }
                })
            } else {
                layer.alert("不能同时选择多个实测户室和多个预测户室")
            }
        }
        , qxgl: function () {
            var fwhsData = table.checkStatus('fwhsTable').data;
            if (fwhsData.length < 1) {
                layer.alert("请最少选择一个实测户室取消关联")
            } else {
                var postData = {};
                postData.qjgldm =qjgldm;
                var schsHasYcfwbm = true;
                $.each(fwhsData, function (index, data) {
                    if (!data.ycfwbm) {
                        schsHasYcfwbm = false;
                        return false;
                    }
                    postData["schsIndexList[" + index + "]"] = data.fwHsIndex
                })
                if (!schsHasYcfwbm) {
                    layer.alert("当前选择的实测户室有未关联户室，不需要取消")
                }else{
                    addModel();
                    $.ajax({
                        url: "../fwhsgl/cancleycscgl",
                        dataType: "json",
                        data: postData,
                        success: function (data) {
                            removeModal();
                            if (data && data.success) {
                                layer.msg("取消成功");
                                reloadTableLsit()
                            } else {
                                layer.alert("取消失败")
                            }
                        },
                        error: function (xhr, status, error) {
                            delAjaxErrorMsg(xhr,this)
                        }
                    })
                }
            }
        }
        , ckglschs: function () {
            var fwychsData = table.checkStatus('fwychsTable').data;
            if (fwychsData.length < 1) {
                layer.alert("请最少选择一个户室进行查看")
            } else {
                var fwychsIndexs = "";
                $.each(fwychsData, function (index, data) {
                    fwychsIndexs = fwychsIndexs + data.fwbm + ","
                })
                fwychsIndexs = fwychsIndexs.substr(0, fwychsIndexs.length - 1);
                tableReload('fwhsTable', {
                    ycfwbm: fwychsIndexs
                });
            }
        }
        , ckglychs: function () {
            var fwhsData = table.checkStatus('fwhsTable').data;
            if (fwhsData.length < 1) {
                layer.alert("请最少选择一个户室进行查看")
            } else {
                var fwhsIndexs = ""
                $.each(fwhsData, function (index, data) {
                    fwhsIndexs = fwhsIndexs + data.ycfwbm + ","
                })
                fwhsIndexs = fwhsIndexs.substr(0, fwhsIndexs.length - 1)
                tableReload('fwychsTable', {
                    fwbm: fwhsIndexs
                });
            }
        }
        ,zzgl : function(){
            var index = layer.open({
                type: 2,
                title: "整幢关联",
                area: ['400px', '300px'],
                fixed: false, //不固定
                content: '../fwhsgl/zzglview?fwDcbIndex=' + fwDcbIndex+"&qjgldm="+qjgldm
                , end: function (index, layero) {
                    reloadTableLsit();
                    return false;
                }
            });
            return false;
        }
        ,qxzzgl: function(){
            addModel();
            $.ajax({
                url: "../fwhsgl/canclezzgl?fwDcbIndex=" + fwDcbIndex+"&qjgldm="+qjgldm,
                dataType: "json",
                success: function (data) {
                    removeModal();
                    if (data && data.success) {
                        layer.msg(data.msg);
                        reloadTableLsit();
                    } else {
                        layer.alert("取消失败")
                    }
                },
                error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr,this)
                }
            })
        }
    };

    $('.demoTable .layui-btn').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

    form.on("submit(ycsearch)", function (data) {
        var postDada = data.field;
        postDada.fwbm = ''
        tableReload('fwychsTable', postDada);
        return false;
    });
    form.on("submit(scsearch)", function (data) {
        var postDada = data.field;
        postDada.ycfwbm = ''
        tableReload('fwhsTable', postDada);
        return false;
    });

})

function reloadTableLsit() {
    $("#ycsearch").click()
    $("#scsearch").click()
}