var zhid = "${zhid}";
var base_url = "/realestate-inquiry-ui";

var options;

layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl', 'upload'], function() {
    var $ = layui.jquery;
    var element = layui.element;
    var form = layui.form;
    var table = layui.table;
    var laytpl = layui.laytpl;
    var layer = layui.layer;
    var upload = layui.upload;
    options = {
        elem: '#import' //绑定元素
        ,url: base_url + '/bdcZhGz/import' //上传接口
        ,accept: 'file'
        ,acceptMime: 'file/txt'
        ,exts: 'txt'
        ,before: function(obj){
            addModel();
        }
        ,done: function(res, index, upload){
            removeModal();
            //上传完毕回调
            if(res.code == 1){
                successMsg(res.message);
                setTimeout(function () {
                    tableReload('zhid', null, url,fun);
                }, 500);
            } else if (res.code == 0){
                failMsg(res.message)
            } else {
                failMsg("导入失败，请选择正确文件！")
            }
        }
        ,error: function(){
            removeModal();
            failMsg("导入失败，请选择正确文件！")
        }
    }

    // 导入组合规则文件
    var fun = function importZh(){
        upload.render(options);
    }

    var yzcj = xmSelect.render({
        el: '#yzcj',
        language: 'zn',
        data: [
            {name: '强制验证', value: '_QZYZ'},
            {name: '流程转发', value: '_LCZF'},
            {name: '新建项目', value: '_XZBDCDY'},
            {name: '登簿验证', value: '_DBYZ'},
            {name: '流程退回', value: '_LCTH'},
            {name: '创建验证', value: '_CJYZ'}
        ]
    });
    // 获取流程信息
    $.ajax({
        url: base_url + '/bdcZhGz/process/definitions',
        type: "GET",
        dataType: "json",
        success: function (data) {
            if (data) {
                $.each(data, function (index, item) {
                    $('#lcmc').append('<option value="' + item.processDefKey + '">' + item.name + '</option>');
                    // $('#lcmc-popup').append('<option value="' + item.processDefKey + '">' + item.name + '</option>');
                });
                form.render('select');
            }
        }
    });

    //不动产规则组合的表头
    var unitTableTitle = [
        {type: 'checkbox', fixed: 'left', width: 50},
        {type: 'numbers', fixed:'left', title: '序号', width: 70 },
        {field: 'zhid', title: '规则id', hide: true},
        {field: 'zhmc', sort: true, title: '规则组合名称', width: 350, style: 'text-align:left'},
        {field: 'zhsm', sort: true, title: '规则组合说明', minWidth:200, style: 'text-align:left'},
        {field: 'zhbs', title: '规则组合标识', width: 250, style: 'text-align:left'},
        {field: 'zhbs', title: '验证场景', align: 'center', width: 120,
            templet: function (d) {
                return formatYzcj(d.zhbs);
            }
        },
        {field: 'pzrq', sort: true, title: '配置日期', width: 180,
            templet: function (d) {
                return format(d.pzrq);
            }
        },
        {title: '查看', fixed: 'right', toolbar: '#barDemo', width: 130}
    ]

    function formatYzcj(zhbs){
        if(isNullOrEmpty(zhbs)){
            return "";
        } else if(zhbs.indexOf("ZHCX_") != -1) {
            return '<span class="" style="color:#4169E1;">综合查询</span>';
        } else if(zhbs.indexOf("_QZYZ") != -1) {
            return '<span class="" style="color:red;">强制验证</span>';
        } else if(zhbs.indexOf("_LCZF") != -1) {
            return '<span class="" style="color:#FF00FF;">流程转发</span>';
        } else if(zhbs.indexOf("_XZBDCDY") != -1) {
            return '<span class="" style="color:#32CD32;">新建项目</span>';
        } else if(zhbs.indexOf("_DBYZ") != -1) {
            return '<span class="" style="color:coral;">登簿验证</span>';
        } else if(zhbs.indexOf("_LCTH") != -1) {
            return '<span class="" style="color:#4B0082;">流程退回</span>';
        } else if(zhbs.substring(zhbs.length - 6) == 'WWSQCJ') {
            return '<span class="" style="color:#B23AEE;">外网相关</span>';
        }else if(zhbs.indexOf("_CJYZ") != -1) {
            return '<span class="" style="color:#177de4;">创建验证</span>';
        } else if(zhbs.indexOf("_WLZS") != -1) {
            return '<span class="" style="color:#00BBF2;">外联验证</span>';
        } else if(zhbs.indexOf("_LCSC") != -1 || zhbs.indexOf("_SQSC") != -1) {
            return '<span class="" style="color:#D5193D;">流程删除</span>';
        } else {
            return "<span>其他验证</span>";
        }
    }

    var url = base_url + '/bdcZhGz/listBdcZhGzByPageJson?sort=pzrq';

    //回车事件
    $("body").keydown(function () {
        if (event.keyCode == "13") {//keyCode=13是回车键
            $('#queryZhGz').click();
        }
    });

    var tableConfig = {
        id: 'zhid',
        // toolbar: "#toolbarDemo",
        defaultToolbar: ["filter"],
        cols: [unitTableTitle]
    }

    //加载表格
    loadDataTablbeByUrl('#zhgzList', tableConfig);
    //表格初始化
    table.init('dataTable',tableConfig);
    tableReload('zhid', null, url,fun);

    //头工具栏事件
    table.on('toolbar(dataTable)', function(obj) {
        var layEvent = obj.event; //获得 lay-event 对应的值
        var checkStatus = table.checkStatus(obj.config.id);

        console.log("-=-=-=checkStatus" + checkStatus.data);
        switch (layEvent) {
            case 'addZhGz':
                addZhGz();
                break;
            case 'updateZhGz':
                updateZhGz(checkStatus.data);
                break;
            case 'deleteZhGz':
                deleteZhGz(checkStatus.data);
                break;
            case 'export':
                exportZhGz(checkStatus.data);
                break;
            case 'copyZhGz':
                copyZhGz(checkStatus.data);
                break;
            case 'exportQzyz':
                exportQzyz(checkStatus.data);
                break;
            case 'checkRelated':
                checkRelated(checkStatus.data);
                break;

        }
        ;
    });

    //监听工具条
    table.on('tool(dataTable)', function (obj) {
        var data = obj.data;
        if (isNullOrEmpty(data.zhid)) {
            warnMsg(" 没有组合规则信息，无法查看！");
            return;
        }

        if (obj.event === 'yglzgz') {
            layer.open({
                title: '已关联子规则',
                type: 2,
                area: ['960px','450px'],
                content: 'zgz.html?zhid=' + data.zhid
                ,cancel: function(){
                }
                ,success: function () {
                }
            });
        }
    });

    /**
     * 行双击编辑
     */
    table.on('rowDouble(dataTable)', function (obj) {
        var array = new Array();
        array.push(obj.data);
        updateZhGz(array);
    });

    /**
     * 监听排序事件
     */
    table.on('sort(zhgzList)', function (obj) {
        table.reload('zhgzList', {
            initSort: obj
            , where: {
                field: obj.field //排序字段
                , order: obj.type //排序方式
            }
        });
    });

    /**
     * 新增
     */
    function addZhGz() {
        window.open("addOrEditZhgz.html");
    }

    /**
     * 修改
     * @param zhid
     * @param obj
     */
    function updateZhGz(data) {
        if (!data || data.length != 1) {
            layer.alert("<div style='text-align: center'>请选择需要编辑的某一条记录！</div>", {title: '提示'});
            return;
        }
        window.open("addOrEditZhgz.html?zhid=" + data[0].zhid);

        saveDetailLog("ZHGZBJ", "组合规则配置-编辑", {"GZNR": JSON.stringify(data[0])});
    }

    /**
     * 删除事件
     */
    function deleteZhGz(data) {
        if (!data || data.length != 1) {
            layer.alert("<div style='text-align: center'>请选择需要删除的某一条记录！</div>", {title: '提示'});
            return;
        }

        var deleteIndex = layer.open({
            type: 1,
            title: '确认删除',
            area: ['320px'],
            content: '确定要删除所选规则？',
            btn: ['确定','取消'],
            skin: 'bdc-small-tips',
            btnAlign: 'c',
            yes: function(){
                $.ajax({
                    url: base_url + '/bdcZhGz/deleteBdcGzZhGzByZhid',
                    type: 'POST',
                    dataType: 'json',
                    data: {zhid: data[0].zhid},
                    success: function (data) {
                        if (data.msg == "success") {
                            layer.msg('<img src="../../../static/lib/bdcui/images/success-small.png" alt="">删除成功');
                            tableReload('zhid', null, url,fun);
                        } else {
                            layer.msg('<img src="../../../static/lib/bdcui/images/error-small.png" alt="">删除失败，请重试');
                        }
                    },
                    error: function (data) {
                        layer.msg('<img src="../../../static/lib/bdcui/images/error-small.png" alt="">删除失败，请重试');
                    }
                });

                layer.close(deleteIndex);
            },
            btn2: function(){
                //取消
            }
        });

        saveDetailLog("ZHGZSC", "组合规则配置-删除", {"GZNR": JSON.stringify(data[0])});
    }

    /**
     * 导出文件事件
     */
    function exportZhGz(data) {
        if (!data || data.length != 1) {
            layer.alert("<div style='text-align: center'>请选择需要导出的某一条规则！</div>", {title: '提示'});
            return;
        }
        var index = layer.open({
            type: 1,
            title: '确认导出',
            area: ['320px'],
            content: '确定要导出么？',
            btn: ['确定','取消'],
            skin: 'bdc-small-tips',
            btnAlign: 'c',
            yes: function(){
                window.open(base_url + '/bdcZhGz/exportFile?zhid=' + data[0].zhid);
                successExport();

                layer.close(index);
            },
            btn2: function(){
                //取消
            }
        });

        saveDetailLog("ZHGZDC", "组合规则配置-导出", {"GZNR": JSON.stringify(data[0])});
    }

    /**
     * 导出强制验证规则
     */
    function exportQzyz(data) {
        // 先验证强制验证
        addModel();

        $.ajax({
            url: "/realestate-inquiry-ui/bdcZhGz/qzyz/check",
            type: 'GET',
            dataType: "json",
            success: function (result) {
                removeModal();

                if(result && !isNullOrEmpty(result.code)){
                    var code = result.code, msg = result.msg, detail = result.detail;

                    if("0" == code){
                        window.open('/realestate-inquiry-ui/bdcZhGz/exportQzyz');
                    } else if("3" == code){
                        var submitIndex = layer.open({
                            type: 1,
                            title: '验证提示',
                            skin: 'bdc-small-tips',
                            area: ['450px'],
                            content: '<p style="padding-bottom:5px">' +
                                '<b>验证不通过，可能原因:</b></p>' +
                                '1、部分流程未配置其强制验证组合规则<br>' +
                                '2、部分流程强制验证未关联基础子规则',
                            btn: ['继续导出','查看详情','取消导出'],
                            btnAlign: 'c',
                            yes: function(){
                                layer.close(submitIndex);
                                window.open('/realestate-inquiry-ui/bdcZhGz/exportQzyz');
                            },
                            btn2: function(index, layero){
                                layui.sessionData('qzyz', {
                                    key: 'data'
                                    ,value: JSON.stringify(result)
                                });
                                window.open("result.html");
                                return false;
                            }

                        });
                    } else {
                        layer.alert(detail);
                    }
                } else {
                    failMsg("导出失败，请重试！");
                }
            },
            error: function () {
                removeModal();
                failMsg("导出失败，请重试！");
            }
        });
    }

    /**
     * 拷贝组合规则
     */
    function copyZhGz(data){
        if (!data || data.length != 1) {
            layer.alert("<div style='text-align: center'>请选择需要复制的某一条记录！</div>", {title: '提示'});
            return;
        }
        $.ajax({
            url: base_url + "/bdcZhGz/copyBdcZhgz/"+data[0].zhid,
            type: 'POST',
            contentType: 'application/json',
            dataType: "text",
            success: function (result) {
                if(!isNullOrEmpty(result)){
                    layer.msg('<img src="../../../static/lib/bdcui/images/success-small.png" alt="">复制成功');
                    tableReload('zhid', null, url,fun);
                } else {
                    layer.msg('<img src="../../../static/lib/bdcui/images/error-small.png" alt="">复制失败，请重试');
                }
            },
            error: function ($xhr, textStatus, errorThrown) {
                layer.msg('<img src="../../../static/lib/bdcui/images/error-small.png" alt="">复制失败，请重试');
            }
        });

        saveDetailLog("ZHGZFZ", "组合规则配置-复制", {"GZNR": JSON.stringify(data[0])});
    }


    function checkRelated(data) {
        //小弹出层
        layer.open({
            title: '规则组合验证',
            type: 1,
            area: ['450px','400px'],
            btn: ['确认', '取消'],
            content: $('#bdc-popup-small')
            ,yes: function(index, layero){
                //提交 的回调
                let selectArr = yzcj.getValue();
                let zhbsArr = [];
                $.each(selectArr,function (index, element) {
                    zhbsArr[index] = element.value;
                });
                let param = {
                    "zhBsList" :zhbsArr
                };

                $.ajax({
                    url: "/realestate-inquiry-ui/bdcZhGz/checkRelated",
                    type: 'POST',
                    data : JSON.stringify(param),
                    contentType: 'application/json;chartset=utf-8',
                    dataType: "json",
                    success: function (result) {
                        if(result){
                            sessionStorage.setItem("processDefData",JSON.stringify(result.bdcGzZhGzYzCjCheckDTOList));
                            sessionStorage.setItem("zhgzData",JSON.stringify(result.bdcGzZhgzDOList));
                            window.open("/realestate-inquiry-ui/view/engine/zhgz/checkResult.html")
                        }else{
                            layer.msg('<img src="../../../static/lib/bdcui/images/error-small.png" alt="">验证失败,请重试!');
                        }
                     },
                    error: function () {
                        layer.msg('<img src="../../../static/lib/bdcui/images/error-small.png" alt="">验证失败,请重试!');
                    }
                });
            }
            ,btn2: function(index, layero){
                //取消 的回调
                console.log("btn2")
            }
            ,cancel: function(){
                console.log("cancel")
                //右上角关闭回调
                //return false 开启该代码可禁止点击该按钮关闭
            }
            ,success: function () {
                console.log("success")
            }
        });
    };

    //查询表单信息
    form.on("submit(queryZhGz)", function (data) {
        tableReload('zhid', data.field, url,fun)
        return false;
    })

    /**
     * 重置
     */
    $('#reset').on('click', function () {
        tableReload('zhid', null, url,fun);
    });

    window.successExport = function () {
        layer.msg('<img src="../../../static/lib/bdcui/images/success-small.png" alt="">导出成功');
    };

    window.failExport = function () {
        layer.msg('<img src="../../../static/lib/bdcui/images/error-small.png" alt="">导出失败，请检查内容');
    }

    /**
     * 失败提示
     * @param data
     */
    window.failMsg = function(data){
        layer.msg('<img src="../../../static/lib/bdcui/images/error-small.png" alt="">' + data);
    }
});