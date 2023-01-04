/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @description 不动产单元
 */
layui.config({
    base: '../../static/' //静态资源所在路径
}).extend({response:'lib/bdcui/js/response'});

var zdList = [];
layui.use(['table', 'laytpl', 'laydate', 'layer', 'form','jquery','laytpl','response'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer,
        formSelects = layui.formSelects,
        laydate = layui.laydate,
        laytpl = layui.laytpl,
        response = layui.response,
        form = layui.form;

    var BASE_URL = '/realestate-inquiry-ui/rest/v1.0/';
    zdList = getMulZdList("sdlx");

    var bdcdyhcxLxArr=[{"DM":"TDFW","MC":"定着物","selected":"selected"},{"DM":"TD","MC":"土地"},{"DM":"HY","MC":"海域"},{"DM":"GZW","MC":"构筑物"}];
    formSelects.config('selectDyhcxlx', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.data('selectDyhcxlx','local',{arr:bdcdyhcxLxArr});
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
        {type: 'checkbox', fixed: 'left'},
        {
            field: 'bdcdyh', title: '不动产单元号', templet: function (d) {
                return formatBdcdyh(d.bdcdyh);
            }},
        {field: 'qlr', title: '权利人'},
        {field: 'zl', title: '坐落'}
    ];
    /**
     * 加载Table数据列表
     */
    var reverseList = ['bdcdyh','zl'];
    table.render({
        elem: '#bdcdyListTable',
        toolbar: '#toolbar',
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

    //头工具栏事件
    table.on('toolbar(bdcdyListTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'sd':sd(checkStatus.data);
                break;
        }
    });

    /**
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 修改备注
     */
    function sd(data){
        if(!data || data.length == 0){
            layer.alert("请选择需要编辑记录！");
            return;
        }
        if(data.length >1){
            layer.alert("请选择一条信息加入黑名单！");
            return;
        }
        showDialog(data[0]);
    }

    function showDialog(data) {
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/zszm/userinfo",
            type: 'GET',
            dataType: 'json',
            success: function (result) {
                var obj = {
                    ztmc : data.bdcdyh,
                    hmdztlb : 2,
                    hmdzt: 1,
                    cjr : result.alias,
                    cjsj : Format(formatDate(new Date()), "yyyy-MM-dd hh:mm:ss")
                };
                layer.open({
                    title: '黑名单',
                    type: 2,
                    content: "/realestate-inquiry-ui/view/hmd/hmdEdit.html",
                    area: ['960px', '450px'],
                    success: function(layero,index){
                        var iframeWin = window[layero.find('iframe')[0]['name']];
                        iframeWin.renderForm(obj);
                    },
                });
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr);
            }
        });
    }


    /**
     * 监听排序事件
     */
    table.on('sort(bdcdyListTable)', function (obj) {
        table.reload('bdcdyListTable', {
            initSort: obj,
            where: {
                field: obj.field, //排序字段
                order: obj.type //排序方式
            }
        });
    });

    /**
     * 点击查询
     */
    $('#search').on('click', function () {
        // 获取查询内容
        var obj = {};

        $(".search").each(function (i) {
            var value = $(this).val();
            var name = $(this).attr('name');
            obj[name] = value;
        });
        var dyhcxlxArr=formSelects.value('selectDyhcxlx');
        if(dyhcxlxArr.length!=0){
            var dm =dyhcxlxArr[0].DM;
            var dycxlxObj =dycxlxCxtj[dm];
            obj.dyhcxlx =dycxlxObj.dyhcxlx;
            obj.dzwtzm =dycxlxObj.dzwtzm;
        }
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
            obj[name] = value;
        });
        var dyhcxlxArr=formSelects.value('selectDyhcxlx');
        if(dyhcxlxArr.length!=0){
            var dm =dyhcxlxArr[0].DM;
            var dycxlxObj =dycxlxCxtj[dm];
            obj.dyhcxlx =dycxlxObj.dyhcxlx;
            obj.dzwtzm =dycxlxObj.dzwtzm;
        }
        table.reload("bdcdyListTable", {
            where: obj
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
    };



});