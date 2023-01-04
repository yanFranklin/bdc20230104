/**
 * @author <a href ="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @description 不动产证书
 */
layui.use(['table','layer', 'form','jquery',], function () {
    var $ = layui.jquery,
        table = layui.table,
        form = layui.form,
        layer = layui.layer;

    var BASE_URL = '/realestate-inquiry-ui/rest/v1.0';
    var zdList = getAllMulZdList("lsylwtlx");
    //绑定回车键
    $(document).keydown(function (event) {
        if (event.keyCode == 13) {
            $("#search").click();
        }
    });

    var cols=[
        {type: 'checkbox', fixed: 'left'},
        {field: 'bdcqzh', title: '不动产权证号'
        },
        {
            field: 'bdcdyh', title: '不动产单元号', templet: function (d) {
                return formatBdcdyh(d.bdcdyh);
            }},
        {field: 'zl', title: '坐落'},
        {field: 'zsid', title: 'zsid',hide:true},
        {field: 'qlrmc', title: '权利人'}

    ];
    /**
     * 加载Table数据列表
     */
    var reverseList = ['bdcqzh','zl','qlr'];
    table.render({
        elem: '#bdczsListTabel',
        toolbar: '#toolbar',
        title: '不动产证书列表',
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
    table.on('toolbar(bdczsListTabel)', function (obj) {
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
            if(isNullOrEmpty(item.bdcqzh)){
                tsxx ="不动产权证号为空！";
            }
            var wtsj = queryWtsj(item.bdcqzh);
            if(wtsj!='' && wtsj.wtsjzt!=undefined && wtsj.wtsjzt=='1'){
                tsxx =item.bdcqzh +"已存在问题数据";
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
        $.each(data, function (index, item) {
            item.wtnr =$('#wtnr').val();
            item.lsylwtlx =$('#lsylwtlx').val();
            item.wtsj =item.bdcqzh;
            item.wtsjlb =2;
        });
        addWtsj(index, data);
    }


    function addWtsj(index,data) {
        $.ajax({
            url: BASE_URL + '/wtsj/addWtsj',
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

    function queryWtsj(bdcqzh){
        var data ={};
        data.wtsj =bdcqzh;
        var result='';
        $.ajax({
            url: BASE_URL + '/wtsj/wtsjzt',
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

    /**
     * 监听排序事件
     */
    table.on('sort(bdczsListTabel)', function (obj) {
        table.reload('bdczsListTabel', {
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
            if(!isNullOrEmpty(value)){
                obj[name] = value;
            }
        });

        if(!isNotBlank(obj)){
            warnMsg(" 请输入必要查询条件，例如权利人名称");
            return false;
        }

        // 重新请求
        table.reload("bdczsListTabel", {
            url: BASE_URL+'/zszm',
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
        table.reload("bdczsListTabel", {
            where: obj
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
    };



});