var moduleCode = GetQueryString("moduleCode");
var gzlslid= $.getUrlParam("gzlslid");
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl', 'laydate'], function () {
    var $ = layui.jquery;
    var form = layui.form;
    var table = layui.table;
    var laydate = layui.laydate;
    var laytpl = layui.laytpl;
    $(function () {
        // 加载字典
        form.render();
        //初始化日期控件
        laydate.render({
            elem: '#kssj'
            , type: 'datetime'
        });
        laydate.render({
            elem: '#jssj'
            , type: 'datetime'
        });
        // 查询参数
        getSearch();

        var gcspcxtableConfig = {
            url: '../gcsp/list/project'
            , limits: [10, 50, 100, 200, 500]
            // ,method: 'post'
            , cols: [[
                {type: 'checkbox', width: 50, fixed: 'left'},
                {field: 'applyinstCode', title: '申报流水号', width: 150},
                {field: 'localCode', title: '项目代码', width: 150},
                {field: 'gcbm', title: '工程代码', width: 150},
                {field: 'projName', title: '项目工程名称', width: 150},
                {field: 'projAddr', title: '建设地点', width: 150},
                {field: 'scaleContent', title: '建设规模及内容', width: 150},
                {field: 'dtmc', title: '单体名称', width: 150},
                {field: 'certinstCode', title: '竣工验收备案表编号', width: 150},
                {field: 'jzmj', title: '单体建筑面积', width: 150},
                {field: 'dsjzmj', title: '地上建筑面积', width: 150},
                {field: 'dxjzmj', title: '地下建筑面积', width: 150},
                {
                    title: '操作',
                    fixed: 'right',
                    align: 'center',
                    toolbar: '#gcspxmListToolBarTmpl',
                    width: 150
                }
            ]],
            data: [],
            done: function () {
                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');

                if ($('.bdc-table-box .layui-table-main>.layui-table').height() == 0) {
                    $('.layui-table-body').height('60px');
                    $('.layui-table-fixed .layui-table-body').height('60px');
                    $('.layui-table-body .layui-none').html('<img src="../static/images/table-none.png" alt="">无数据');
                } else {
                    $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                    $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
                }
            }
        };
        //台账查询地址
        var gcspcxUrl = "../gcsp/list/project";

        addModel();
        loadDataTablbeByUrl("#gcspcxTable", gcspcxtableConfig);
        removeModal();
        //提交表单
        form.on("submit(query)", function (data) {
            table.reload("gcspcxTable", {
                where: data.field
                , page: {
                    //重新从第 1 页开始
                    curr: 1
                },
                url: gcspcxUrl
            });
            return false;
        });


        table.on('tool(dataTable)', function (obj) {
            var data = obj.data;
            if (data) {
                if (obj.event === "download") {
                    if (data.certinstCode) {
                        addModel();
                        $("#certinstCode").val(data.certinstCode);
                        $("#downloadZip").submit();
                    } else {
                        layer.msg("所选数据没有工程编码或竣工验收备案表编号")
                    }
                }
            } else {
                layer.alert("当前数据缺失，请检查数据");
                return false
            }
        });

    });

    function loadDataTablbeByParam(_domId, _tableConfig,url,_param) {
        layui.use(['table', 'laypage', 'jquery'], function () {
            if (_domId) {
                var table = layui.table;
                var $ = layui.jquery;
                var tableDefaultConfig = {
                    toolbar: true,
                    defaultToolbar: ['filter'],
                    even: true,
                    elem: _domId,
                    method: "POST",
                    url:url,
                    cellMinWidth: 80,
                    page: true, //开启分页
                    limit: 10,
                    limits: [10, 50, 100, 200, 500],
                    parseData: function (res) { //res 即为原始返回的数据
                        res.hasContent=true
                        return res;
                    }
                    , request: {
                        limitName: 'size' //每页数据量的参数名，默认：limit
                    }
                    , response: {
                        countName: 'totalElements' //数据总数的字段名称，默认：count
                        , dataName: 'content' //数据列表的字段名称，默认：data
                        , statusName: 'hasContent' //规定数据状态的字段名称，默认：code
                        , statusCode: true //规定成功的状态码，默认：0
                    }
                    ,where : _param
                };
                if (!_tableConfig.cols || !_tableConfig.cols[0] || _tableConfig.cols[0].length < 1) {
                    _tableConfig.cols = [[]]
                }
                var tableRenderConfig = $.extend({}, tableDefaultConfig, _tableConfig);
                table.render(tableRenderConfig);
            }
        });
    }

    // 获取查询参数
    function getSearch() {
        $.ajax({
            url:"/jyxx/jgysbabhlist",
            type: "POST",
            data: JSON.stringify({"gzlslid":gzlslid}),
            contentType: 'application/json',
            success: function (data) {
                console.log('查询参数:');
                console.log(data);
                removeModal();

                if (data && data.length > 0) {
                    var getData = {
                        "cxywcs": []
                    };
                    for (var i = 0; i < data.length; i++) {
                        getData.cxywcs.push({"jgysbabh": data[i].jgysbabh});
                    }

                    var searchData = getData;

                    console.log('searchData:');
                    console.log(searchData);
                    // generateTable(searchData, false);
                    generateSearchCondition(searchData);

                    // 查询条件不为空进行查询
                    if(searchData &&searchData.cxywcs &&searchData.cxywcs.length >0){
                        $('#query').trigger('click');
                    }
                }


            },
            error: function (xhr, status, error) {
                removeModal();
                delAjaxErrorMsg(xhr)
            }
        });
    }

    function generateSearchCondition(searchData) {
        $('#jgysbabh').val(searchData.cxywcs[0].jgysbabh);

        searchData.cxywcs.forEach(function(item, index) {
            $('#jgysbabh-select').append('<option value="' + item.jgysbabh + '">' + item.jgysbabh + '</option>');
        });

        form.render();
    }

    form.on('select(jgysbabh-select)', function (data) {
        $(this).parents('.layui-input-inline').find("input[name='jgysbabh']").val(data.value);
        $(this).parents('.layui-input-inline').find("dl").css({"display": "none"});
        form.render();
        resetSelectDisabledCss();
    });
});