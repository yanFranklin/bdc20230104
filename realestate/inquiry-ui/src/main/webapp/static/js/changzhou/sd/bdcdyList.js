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

    //加载地区下拉
    renderQjgldm();

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
        showDialog(data);
    }

    function showDialog(data) {
        layer.open({
            title: '锁定原因',
            type: 1,
            area: ['450px','350px'],
            btn: ['提交', '取消'],
            content: $('#bdc-popup-sdxx'),
            yes: function (index, layero) {
                saveSdxx(data,index)
                layer.close(index);
            },
            cannel: function (index, layero) {
                layer.close(index)
            },
            success: function () {
                zdList = getMulZdList("sdlx");
                if(isNotBlank(zdList) && isNotBlank(zdList['sdlx'])){
                    $.each(zdList['sdlx'], function (index, item) {
                        $('#sdlx').append('<option value="' + item.DM + '">' + item.MC + '</option>');
                    });
                }
                form.render();
            }
        });
    }

    function saveSdxx(data,index) {
        if (isNullOrEmpty($('#sdyy').val())){
            layer.alert("请输入锁定原因！");
            return;
        }
        var sddyh = new Array();
        $.each(data, function (index, item) {
            item.sdyy = null;
            var sdxx = querySdbdcdy(item);
            if(sdxx!='' && sdxx.sdzt!=undefined && sdxx.sdzt=='1'){
                sddyh.push(item.bdcdyh);
            }
            item.sdyy = $('#sdyy').val();
            item.sdlx = $("#sdlx").val();
        })
        if (sddyh.length != 0){
            var sddyhstr;
            if (sddyh.length <= 5){
                sddyhstr = sddyh.join('、');
            } else {
                for (var i =0;i<sddyh.length;i++){
                    sddyhstr+= sddyh[i] + '、';
                }
                sddyhstr = sddyhstr.substring(0,sddyhstr.length -1) + '等';
            }
            layer.confirm("单元号" + sddyhstr + "已锁定，是否继续锁定？", {
                title: "提示",
                btn: ["确认", "取消"],
                btn1: function (index2) {
                    layer.close(index2);
                    sdSdxx(index, data);
                },
                btn2: function (index) {
                    layer.close(index);
                }
            });
        } else {
            sdSdxx(index, data);
        }
    }

    function sdSdxx(index,data) {
        $.ajax({
            url: BASE_URL + '/bdcdysd/sd',
            type: "POST",
            data: JSON.stringify(data),
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                saveSuccessMsg();
            },
            error: function (e) {
                saveFailMsg();
            },complete:function () {
                $('#sdyy').val('');
                $("#sdlx").val("");
                layer.close(index);
            }
        });
    }

    function querySdbdcdy(data){
        var result='';
        $.ajax({
            url: BASE_URL + '/bdcdysd/sdzt',
            type: "POST",
            data: JSON.stringify(data),
            contentType: 'application/json',
            dataType: "json",
            async:false,
            success: function (data) {
                result=data;
            },
            error: function (e) {
               response.fail(e)
            },complete:function () {

            }
        });
        return result;
    }

    //地区下拉选项
    function renderQjgldm(){
        getReturnData("/rest/v1.0/qjgldm/list",{},"GET",function (data){
            if(data &&data.length >0) {
                $("#dyh-qjgldm").show();
                $(".bdc-button-box").addClass("bdc-button-box-four");
                $.each(data, function (index, item) {
                    $('#qjgldm').append('<option value="' + item.dm + '">' + item.mc + '</option>');
                });
            }
            form.render("select");

        },function (error){

        },false);
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