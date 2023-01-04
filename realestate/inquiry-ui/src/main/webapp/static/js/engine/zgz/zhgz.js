var base_url = "/realestate-inquiry-ui";
var gzid = getQueryString("gzid");
var url = base_url + '/bdcZhGz/listBdcZhGzByPageJson?sort=pzrq';

layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    var form = layui.form,
        $ = layui.jquery,
        table = layui.table,
        layer = layui.layer;

    // 当前页数据
    var currentPageData = new Array();
    // 保存当前选中的组合规则ID
    var releatedZhidList = [];//关联的组合规则id；
    // 保存更改前的选中的组合规则ID
    var beforeGzgx = [];
    // 组合规则数据
    var zhgzList = [];

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
        }
    ]

    var tableConfig = {
        id: 'zhTable',
        // defaultToolbar: [],
        defaultToolbar: ["filter"],
        cols: [unitTableTitle],
        data: zhgzList,
        toolbar: "#toolbarDemo",
        even: true
    }

    //回车事件
    $("body").keydown(function () {
        if (event.keyCode == "13") {//keyCode=13是回车键
            $('#queryZhGz').click();
        }
    });

    // 获取流程信息
    $.ajax({
        url: '/realestate-inquiry-ui/bdcZhGz/process/definitions',
        type: "GET",
        dataType: "json",
        success: function (data) {
            if (data) {
                $.each(data, function (index, item) {
                    $('#lcmc').append('<option value="' + item.processDefKey + '">' + item.name + '</option>');
                });
                form.render('select');
            }
        }
    });


    //获得已经关联的组合规则集合 releatedZhidList
    initZhGxSelect(gzid);
    function initZhGxSelect(gzid) {
        $.ajax({
            url: base_url + "/zgz/listBdcGzGx",
            dataType: "json",
            async: false,
            cache: false,
            data: {gzid: gzid},
            success: function (data) {
                if (data) {
                    // dataContent = data.content;
                    data.forEach(function (v) {
                        releatedZhidList.push(v.zhid)
                    })
                    beforeGzgx = releatedZhidList;
                }
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            },
            complete: function(){
                getZhgzList();
            }
        });
    }

    // 加载组合规则列表
    function getZhgzList(data) {
        $.ajax({
            url: base_url + '/bdcZhGz/listBdcZhGzByPageJson?sort=pzrq&page=1&size=100000',
            type: "GET",
            dataType: "json",
            data: data,
            cache: false,
            async: false,
            timeout: 10000,
            success: function (res) {
                if (res && releatedZhidList) {
                    zhgzList = res.content;
                    zhgzList.forEach(function (value) {
                        releatedZhidList.forEach(function (v) {
                            if (value.zhid == v) {
                                value.LAY_CHECKED = true;
                            }
                        })
                    });
                } else {
                    zhgzList = res.content;
                }
            },
        });
    }

    //加载表格
    loadDataTablbeByUrl('#zhgzList', tableConfig);
    //表格初始化
    table.init('dataTable',tableConfig);

    reloadGz('zhgzList', null, zhgzList);
    function reloadGz(tableid, postData, gzData) {
        layui.use(['table'], function () {
            var table = layui.table;
            table.reload(tableid, {
                data: gzData,
                where: postData,
                page: {
                    //重新从第 1 页开始
                    curr: 1,
                    limits: [10, 50, 100, 200, 500]
                }
                ,done: function (res, curr, count) {
                    currentPageData = res.data;
                    if(currentPageData == undefined) {
                        currentPageData = res.content;
                    }

                    if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
                        $('.layui-table-body .layui-none').html('<img src="../../../static/lib/registerui/image/table-none.png" alt="">无数据');
                    }
                }
            });
        });
    }

    // 子规则列表选中事件
    table.on('checkbox(dataTable)', function (obj) {
        // 获取选中或者取消的数据
        var data = obj.type == 'one' ? [obj.data] : currentPageData;

        if (obj.checked) {
            //.增加已选中项
            for (var index in data) {
                releatedZhidList.push(data[index].zhid);
            }
        } else {
            //.删除
            for (var index in releatedZhidList) {
                data.forEach(function (v) {
                    if (releatedZhidList[index] == v.zhid) {
                        delete releatedZhidList[index];
                    }
                });
            }
        }

        releatedZhidList = $.unique(releatedZhidList);
    });


    //查询表单信息
    form.on("submit(queryZhGz)", function (data) {
        var paragram =  data.field;
        paragram.gzid = gzid;//根据关联关系查询组合规则
        getZhgzList(paragram);
        reloadGz('zhTable', paragram, zhgzList);
        return false;//阻止表单提交
    })

    //头工具栏事件
    table.on('toolbar(dataTable)', function(obj) {
        var layEvent = obj.event; //获得 lay-event 对应的值
        var data = releatedZhidList;
        var loadMsg = layer.msg('正在保存中', {
            icon: 16, shade: 0.01
        });
        switch (layEvent) {
            case 'addGlgx':
                addGlgx(data,gzid);
                break;
        }
        ;
    });

    /**
     * 重置
     */
    $('#reset').on('click', function () {
        getZhgzList();
        reloadGz('zhTable', null, zhgzList);

    });


    //批量添加关联关系；
    function addGlgx(zhidList,gzid){
        var glgx = $("#glgx").val();
        var loadMsg = layer.msg('正在保存中', {
            icon: 16, shade: 0.01
        });
        $.ajax({
            url: base_url+"/bdcZhGz/glgx?gzid="+gzid,
            type: "POST",
            dataType: "json",
            async: true,
            contentType: 'application/json;charset=utf-8',
            data: JSON.stringify(zhidList),
            success: function (result) {
                    successMsg("保存成功！")
            },
            complete: function(){
                layer.close(loadMsg);
            }
        });

    }


    /**
     * 日期格式化
     * @param timestamp
     * @returns {*}
     */
    function format(timestamp) {
        if (!timestamp) {
            return '';
        }

        var time = new Date(timestamp);
        var y = time.getFullYear();
        var m = time.getMonth() + 1;
        var d = time.getDate();
        var h = time.getHours();
        var mm = time.getMinutes();
        var s = time.getSeconds();
        return y + '-' + add0(m) + '-' + add0(d) + ' ' + add0(h) + ':' + add0(mm) + ':' + add0(s);
    }

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
        } else if(zhbs.indexOf("_WWSQCJ") != -1) {
            return '<span class="" style="color:#B23AEE;">外网相关</span>';
        }else if(zhbs.indexOf("_CJYZ") != -1) {
            return '<span class="" style="color:#177de4;">创建验证</span>';
        } else {
            return "";
        }
    }
});

//获取URL参数
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (null !== r) {
        if (decodeURI(r[2]) !== "null") {
            return decodeURI(r[2]);
        } else {
            return null
        }
    }
    return null;
}