var zdNames = "gxbmbz";
var zdList = {};
var type = GetQueryString("type");
var interfaceId = GetQueryString("interfaceId");
var cxbm = GetQueryString("cxbm");
// 分页重置查询参数
var paramD =[];
layui.extend({
    formSelects: '../static/lib/form-select/formSelects-v4'
});
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl','laydate', 'util','formSelects'], function () {
    var $ = layui.jquery;
    var element = layui.element;
    var form = layui.form;
    var table = layui.table;
    var laytpl = layui.laytpl;
    var laydate = layui.laydate;
    var util = layui.util;
    var reverseList = ['bdcdz', 'dsfdz'];
    var formSelects = layui.formSelects;
    $(function () {
        if (interfaceId){
            $("#jkmcDiv").hide();
            $("#jkdzDiv").hide();
        }
        if (cxbm){
            $("#bmmcDiv").show();
            $("#gxbmbzDiv").hide();
            zdNames += ",gxcxjkmc";
        }else {
            zdNames += ",gxjkmc";
        }
        // 加载字典
        getMulZdList();
        var tpl = $("#DmMcTpl").html();
        if (zdList) {
            $.each(zdList, function (key, value) {
                laytpl(tpl).render(value, function (html) {
                    $("." + key).append(html);
                });
            });
        } else {
            laytpl(tpl).render(value, function (html) {
                $("").append(html);
            });
        }

        form.render();

        //下拉框多选
        formSelects.render("selectgxbmbz");
        formSelects.render("selectjkmc");

        //初始化日期控件
        laydate.render({
            elem: '#kssj'
            , type: 'datetime'
        });
        laydate.render({
            elem: '#jssj'
            , type: 'datetime'
        });

        //点击高级查询--4的倍数
        $('#seniorSearch').on('click', function () {
            $('.pf-senior-show').toggleClass('bdc-hide');
            $(this).parent().toggleClass('bdc-button-box-four');
            $('.bdc-percentage-container').css('padding-top', $('.bdc-search-content').height() + 10);

            $('.layui-table-body').height($('.bdc-table-box').height() - 129);
            $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 129 - 17);
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

        //提交表单
        form.on("submit(query)", function (data) {
            paramD = data.field;
            var url;
            if (interfaceId) {
                url = '../bdcDsfLog/getBdcDsfLogPagesJson?jkid=' + interfaceId + '&type=' + type;
            } else {
                url = '../bdcDsfLog/getBdcDsfLogPagesJson?type=' + type;
            }
            loadDataTablbeByParam("#bdcdsfLogTable", tableConfig, url, paramD);
            // tableReload('bdcdsfLogTable', data.field);
            return false;
        });

        var tableTitle = [ //表头
            {field: 'GXBMBZ', title: '共享部门', width: '8%',templet:function(d){
                    if (d.GXBMBZ == null)
                    {
                        return "";
                    }
                    return convertZdDmToMc("gxbmbz", d.GXBMBZ);
                }}
            //, {field: 'FZXMC', title: '分中心名称'}
            , {field: 'JKMC', title: '接口名称', width: '18%'}
            , {field: 'SLBH', title: '受理编号', width: '10%'}
            , {field: 'QXDM', title: '区县代码', width: '10%'}
            , {field: 'QQCS', title: '请求参数', width: '10%'}
            , {title: '响应结果', width: '10%',templet: '#xyjgTpl'}
            , {field: 'YCXX', title: '异常信息', width: '7%'}
            , {field: 'CZR', title: '操作人', width: '8%'}
            , {field: 'ALIAS', title: '用户姓名', width: '8%'}
            , {field: 'QQSJ', title: '操作时间', width: '12%',
                templet:function(d){
                    if (d.QQSJ == null) {
                        return "";
                    } else if (typeof d.QQSJ === "string") {
                        return formatDate(new Date(d.QQSJ.toString().replace(/-/g, "/")));
                    } else {
                        return formatDate(new Date(d.QQSJ));
                    }
                }
            }
            , {field: 'BDCDZ', title: '不动产地址', width: '10%'}
            , {field: 'DSFDZ', title: '第三方地址', width: '10%'}
        ];

        var url;
        if (interfaceId) {
            url = '../bdcDsfLog/getBdcDsfLogPagesJson?jkid=' + interfaceId + '&type=' + type;
        } else {
            url = '../bdcDsfLog/getBdcDsfLogPagesJson?type=' + type;
        }
        var tableConfig = {
            method: "POST",
            url: url,
            toolbar: true,
            defaultToolbar: ['filter'],
            cols: [tableTitle],
            page: true,  //开启分页
            parseData: function (res) {
                res.hasContent = true;
                res.code = 0;
                res.data = res.content
                return res;
            },
            done: function (res, curr, count) {
                //无数据时显示内容调整
                setHeight();
            }
        };
        //加载表格
        loadDataTablbeByUrl("#bdcdsfLogTable", tableConfig);
        //表格初始化
        // table.init('dataTable', tableConfig);

    });
    function getMulZdList() {
        $.ajax({
            url: "../bdcDsfLog/zdMul",
            dataType: "json",
            data: {
                zdNames: zdNames
            },
            async: false,
            success: function (data) {
                if(!isNullOrEmpty(data['gxcxjkmc'])){
                    data['gxjkmc'] = data['gxcxjkmc'];
                }
                zdList = $.extend({}, data);
            },
            error: function (e) {}
        });
    }

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

    //前台的字典转换，代码转换为名称
    function convertZdDmToMc(zdname, zdDm, zdListName) {
        if (zdDm) {
            if (!zdListName) {
                zdListName = "zdList"
            }
            var zdVals = eval(zdListName + "." + zdname);
            if (zdVals) {
                for (var i = 0; i < zdVals.length; i++) {
                    var zdVal = zdVals[i];
                    if (zdDm == zdVal.DM) {
                        return zdVal.MC;
                    }
                }
            }
            return zdDm;
        }
        return "";
    }
    //公共方法里图片路径不对此处重新设置
    function setHeight(searchHeight) {
        if (isNullOrEmpty(searchHeight)) {
            searchHeight = 131;
        }
        $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');

        if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
            $('.layui-table-body .layui-none').html('<img src="../static/images/table-none.png" alt="">无数据');
        }else {
            $('.layui-table-body').height($('.bdc-table-box').height() - searchHeight);
            $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - searchHeight - 17);
        }
    }
});