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
    var zdList = getAllMulZdList("lsylwtlx");
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
            case 'addWtsj':wtsj(checkStatus.data);
                break;
        }
    });

    //标记问题数据
    function wtsj(data){
        if(!data || data.length == 0){
            layer.alert("请选择需要编辑的记录！");
            return;
        }
        var tsxx ="";
        $.each(data, function (index, item) {
            item.wtnr = null;
            if(isNullOrEmpty(item.bdcdyh)){
                tsxx ="不动产单元号为空！";
            }
            var wtsj = queryWtsj(item.bdcdyh);
            if(wtsj!='' && wtsj.wtsjzt!=undefined && wtsj.wtsjzt=='1'){
                tsxx =item.bdcdyh +"已存在问题数据";
            }
        });
        if(!isNullOrEmpty(tsxx)){
            layer.alert(tsxx);
            return false;
        }
        showDialog(data);
    }

    //
    function showDialog(data) {
        var cxtjDom = "";
        $.each(zdList["lsylwtlx"], function (i, zd) {
            cxtjDom += '<option value="' + zd.DM + '">' + zd.MC + '</option>';
        });
        var div = " <div id=\"bdc-popup-textarea\"><form class=\"layui-form\" action=\"\">" +
            "            <div class=\"layui-form-item pf-form-item\">" +
            "               <div class=\"layui-inline\">\n" +
            "                    <label class=\"layui-form-label change-label-width\">问题类型</label>\n" +
            "                    <div class=\"layui-input-inline select-height\">\n" +
            "                        <select id=\"lsylwtlx\" name=\"lsylwtlx\" lay-search=\"\">\n" +
            "                            <option value=\"\">请选择</option>\n" +
                                         cxtjDom +
            "                        </select>\n" +
            "                        <i class=\"layui-icon layui-icon-close bdc-hide\"></i>\n" +
            "                    </div>\n" +
            "                </div>" +
            "                <div class=\"layui-inline\">" +
            "                    <label class=\"layui-form-label\"><span class=\"required-span\"><sub>*</sub></span>问题内容</label>" +
            "                    <div class=\"layui-input-inline bdc-end-time-box\">" +
            "                        <textarea name=\"wtnr\" id='wtnr' placeholder=\"请输入内容\" class=\"layui-textarea\"></textarea>" +
            "                    </div>" +
            "                </div>" +
            "            </div>" +
            "        </form>" +
            "    </div>";
        //小弹出层
        layer.open({
            title: '问题内容',
            type: 1,
            area: ['460px', '350px'],
            btn: ['提交', '取消'],
            content: div
            , yes: function (index, layero) {
                //提交 的回调
                saveWtsj(data,index)
            }
            , btn2: function (index, layero) {
                //取消 的回调
                layer.close(index)
            }
            , cancel: function (index) {
                //右上角关闭回调
                //return false 开启该代码可禁止点击该按钮关闭
                layer.close(index)
            }
            , success: function () {

            }
        });
        form.render("select");
    }

    //保存
    function saveWtsj(data,index) {
        if (isNullOrEmpty($('#wtnr').val())){
            layer.alert("请输入问题内容！");
            return;
        }
        var wtsjxx = [];
        $.each(data, function (index, item) {
            item.wtnr =$('#wtnr').val();
            item.lsylwtlx =$('#lsylwtlx').val();
            item.wtsj =item.bdcdyh;
            item.wtsjlb =1;
        });
        addWtsj(index, data);
    }


    function addWtsj(index,data) {
        $.ajax({
            url: BASE_URL + 'wtsj/addWtsj',
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
                $('#wtnr').val('');
                layer.close(index);
            }
        });
    }

    function queryWtsj(bdcdyh){
        var data ={};
        data.wtsj =bdcdyh;
        var result='';
        $.ajax({
            url: BASE_URL + 'wtsj/wtsjzt',
            type: "POST",
            data: JSON.stringify(data),
            contentType: 'application/json',
            dataType: "json",
            async:false,
            success: function (data) {
                result=data;
            },
            error: function (e) {
               delAjaxErrorMsg(e);
            },complete:function () {

            }
        });
        return result;
    }

    //地区下拉选项
    function renderQjgldm(){
        getReturnData("/rest/v1.0/qjgldm/list",{},"GET",function (data){
            if(data &&data.length >0) {
                $(".bdc-percentage-container").removeClass("bdc-line-search-container");
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