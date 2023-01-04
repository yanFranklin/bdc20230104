/**
 * @author <a href ="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @description 不动产单元
 */
layui.use(['table', 'laytpl', 'layer', 'form','jquery'], function () {
    var $ = layui.jquery,
        table = layui.table,
        form = layui.form,
        layer = layui.layer;

    var BASE_URL = '/realestate-inquiry-ui/rest/v1.0/';
    var qjgldm =getQueryString("qjgldm");
    //绑定回车键
    $(document).keydown(function (event) {
        if (event.keyCode == 13) {
            $("#search").click();
        }
    });

    var dycxlxCxtj ={
        "TD": {
            "dyhcxlx": 1,
            "dzwtzm": "W"
        },
        "TDFW": {
            "dyhcxlx": 1,
            "dzwtzm": "F"
        },
        "LDLM": {
            "dyhcxlx": 1,
            "dzwtzm": "L"
        },
        "HY": {
            "dyhcxlx": 2,
            "dzwtzm": ""
        },
        "GZW": {
            "dyhcxlx": 3,
            "dzwtzm": ""
        }
    };

    cols=[
        {
            field: 'bdcdyh', title: '不动产单元号', width: 300,templet: function (d) {
                return formatBdcdyh(d.bdcdyh);
            }},
        {field: 'qlr', title: '权利人',width: 100},
        {field: 'zl', title: '坐落', width: 300},
        {field: 'cz', title: '操作', templet: '#dyhcz',align: "center", fixed: "right"}
    ];
    /**
     * 加载Table数据列表
     */
    var reverseList = ['bdcdyh','zl'];
    table.render({
        elem: '#bdcdyListTable',
        title: '不动产单元列表',
        defaultToolbar: ['filter'],
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        data: [],
        cellMinWidth: 80,
        where: {},
        even: true,
        cols: [cols],
        text: {
            none: '未查询到数据'
        },
        autoSort: false,
        page: true,
        limits: [10, 50, 100, 200, 500],
        parseData: function (res) {
            return {
                code: res.code, //解析接口状态
                msg: res.msg, //解析提示文本
                count: res.totalElements, //解析数据长度
                data: res.content //解析数据列表
            }
        },
        done: function () {
            reverseTableCell(reverseList);
            setHeight();
        }
    });

    $('.bdc-table-box').on('mouseenter','td',function () {
        if($(this).text() && reverseList.indexOf($(this).attr("data-field")) != -1){
            $(this).append('<div class="layui-table-grid-down"><i class="layui-icon layui-icon-down"></i></div>');
        }
        $('.layui-table-grid-down').on('click',function () {
            setTimeout(function () {
                $('.layui-table-tips-main .bdc-table-date').html(reverseString($('.layui-table-tips-main .bdc-table-date').html()));
            },20);
        });
    });

    table.on('tool(bdcdyListTable)', function (obj) {
        if (obj.event === 'updateBdcdyh') {
            var xmsdData ={};
            if(sessionStorage.getItem("dyhgz_yxxmsdxx") &&sessionStorage.getItem("dyhgz_yxxmsdxx") !="undefined") {
                xmsdData = JSON.parse(sessionStorage.getItem("dyhgz_yxxmsdxx"));
            }
            if(xmsdData ==null ||!xmsdData.yxcount||xmsdData.yxcount ===0){
                layer.alert("请选择需要更新的数据");
                return false;
            }
            var bdcdyh=obj.data.bdcdyh;
            if(!isNotBlank(bdcdyh)){
                layer.alert("不动产单元号为空");
            }
            addModel();
            if (xmsdData.ygxx && xmsdData.ygxx.length > 0) {
                for (var i = 0; i < xmsdData.ygxx.length; i++) {
                    xmsdData.xmxx.push(xmsdData.ygxx[i]);
                }
            }

            var bdcGzYzQO = {};
            bdcGzYzQO.zhbs = "DYHGZ_THDYH";
            var paramMap = {};
            paramMap.bdcdyh = bdcdyh;
            bdcGzYzQO.paramMap = paramMap;
            gzyzCommon(bdcGzYzQO, function (data) {
                var dyhGzQO={};
                dyhGzQO.bdcXmDOList =xmsdData.xmxx;
                dyhGzQO.bdcDysdDOList =xmsdData.dysdxx;
                dyhGzQO.bdcdyh =bdcdyh;
                if(sessionStorage.getItem("dyhgz_sdglxx") &&sessionStorage.getItem("dyhgz_sdglxx") !="undefined") {
                    var sdxmsdData = JSON.parse(sessionStorage.getItem("dyhgz_sdglxx"));
                    if (sdxmsdData.xmxx && sdxmsdData.xmxx.length > 0) {
                        for (var i = 0; i < sdxmsdData.xmxx.length; i++) {
                            dyhGzQO.bdcXmDOList.push(sdxmsdData.xmxx[i]);
                        }
                    }
                    if (sdxmsdData.dysdxx && sdxmsdData.dysdxx.length > 0) {
                        if(dyhGzQO.bdcDysdDOList ==null){
                            dyhGzQO.bdcDysdDOList=[];
                        }
                        for (var i = 0; i < sdxmsdData.dysdxx.length; i++) {
                            dyhGzQO.bdcDysdDOList.push(sdxmsdData.dysdxx[i]);
                        }
                    }
                }
                doGzyz('/rest/v1.0/dyhgz/xzxx',dyhGzQO,function (){
                    dyhgz(dyhGzQO,bdcdyh);
                });
            });


        }
    });

    //单元号更正方法
    function dyhgz(dyhGzQO,bdcdyh){
        $.ajax({
            url: getContextPath() + '/rest/v1.0/dyhgz',
            type: 'POST',
            dataType: 'json',
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(dyhGzQO),
            success: function (data) {
                removeModal();
                successMsg("更新成功");
                parent.parent.layer.closeAll();
                parent.parent.$("input[name=bdcdyh]").val(bdcdyh);
                parent.parent.$("#search").click();

            }
            , error: function (xhr, status, error) {
                removeModal();
                delAjaxErrorMsg(xhr);
            }
        });
    }

    /**
     * 监听排序事件
     */
    table.on('sort(bdcdyListTable)', function (obj) {
        table.reload('bdcdyListTable', {
            initSort: obj
            , where: {
                field: obj.field //排序字段
                , order: obj.type //排序方式
            }
        });
    });

    /**
     * 点击查询
     */
    $('#search').on('click', function () {
        // 获取查询内容
        var obj = {};
        obj.qjgldm =qjgldm;

        $(".search").each(function (i) {
            var value = $(this).val();
            var name = $(this).attr('name');
            if(name ==="dycxlx"){
                var dycxlxObj =dycxlxCxtj[value];
                obj.dyhcxlx =dycxlxObj.dyhcxlx;
                obj.dzwtzm =dycxlxObj.dzwtzm;
            }else {
                obj[name] = value;
            }
        });
        // 重新请求
        table.reload("bdcdyListTable", {
            url: BASE_URL+'bdcdy/page',
            where: obj
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
    });


    /**
     * 重新加载数据
     */
    window.reload = function () {
        var obj = {};
        $(".search").each(function (i) {
            var value = $(this).val();
            var name = $(this).attr('name');
            if(name ==="dycxlx"){
                var dycxlxObj =dycxlxCxtj[value];
                obj.dyhcxlx =dycxlxObj.dyhcxlx;
                obj.dzwtzm =dycxlxObj.dzwtzm;
            }else {
                obj[name] = value;
            }
        });
        table.reload("bdcdyListTable", {
            where: obj
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
    };

});