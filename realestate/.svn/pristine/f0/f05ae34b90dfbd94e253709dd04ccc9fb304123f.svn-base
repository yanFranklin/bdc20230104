//统一每页条数的选择项
var commonLimits = [10, 20, 50, 100, 200, 500];
layui.use(['jquery', 'table', 'form', 'layer'], function () {
    var $ = layui.jquery,
        table = layui.table,
        form = layui.form,
        layer = layui.layer;

    $(function () {
        // 获取参数（工作流定义 id）
        var processInsId = $.getUrlParam('processInsId');

        /**
         * 监听台账查询 input 点击 显示 x 清空 input 中的内容
         */
        $('.layui-form-item .layui-input-inline').on('click', '.layui-icon-close', function () {
            $(this).siblings('.layui-input').val('');
        });
        $('.layui-form-item .layui-input-inline .layui-input').on('focus', function () {
            $(this).siblings('.layui-icon-close').removeClass('bdc-hide');
        }).on('blur', function () {
            var $this = $(this);
            setTimeout(function () {
                $this.siblings('.layui-icon-close').addClass('bdc-hide');
            }, 150)
        });
        $('.bdc-content').css('min-height', $('body').height() - 112);


        // 与地籍调查表用同一个查询接口
        var url = getContextPath() +"/slym/xm/djdcb/page";
        var tableId = '#listTable';
        renderListTable(url, tableId);

        /**
         * 渲染表格
         * @param url 地址
         * @param tableId 表格 id
         */
        function renderListTable(url, tableId) {
            addModel();
            table.render({
                elem: tableId,
                url: url,
                title: '档案调用表格',
                method: 'GET',
                even: true,
                request: {
                    limitName: 'size' //每页数据量的参数名，默认：limit
                },
                where: {
                    processInsId: processInsId
                },
                defaultToolbar: ['filter'],
                limits: commonLimits,
                cols: [[
                    {field: 'ycqzh', title: '原产权证号', width: "35%"},
                    {field: 'bdcdyh', title: '不动产单元号', width: "20%"},
                    {field: 'zl', title: '坐落', width: "35%"},
                    {title: '操作', width: "10%", templet: '#editBar', align: "center"}
                ]],
                parseData: function (res) { //res 即为原始返回的数据
                    return {
                        code: res.code, //解析接口状态
                        msg: res.msg, //解析提示文本
                        count: res.totalElements, //解析数据长度
                        data: res.content //解析数据列表
                    }
                },
                page: true,
                done: function () {
                    removeModal();
                    $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
                    if ($('.bdc-table-box .layui-table-main>.layui-table').height() == 0) {
                        $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
                    } else {
                        $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                        $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
                    }
                }
            });
        }

        /**
         * 查询点击事件
         */
        $('#search').on('click', function () {
            // 获取查询内容
            var obj = {};
            $(".search").each(function (i) {
                var value = $(this).val();
                var name = $(this).attr('name');
                // 此处去除查询条件中的空格
                obj[name] = deleteWhitespace(value);
            });
            obj['processInsId'] = processInsId;
            // 查询时间长，添加遮罩
            addModel();
            // 重新请求
            table.reload("listTable", {
                where: obj,
                page: {
                    curr: 1  //重新从第 1 页开始
                },
                done: function(){
                    removeModal();
                }
            });
        });


        /**
         * 监听工具栏事件，工具栏进入到编辑页面可以对于数据进行操作
         */
        table.on('tool(listTable)', function (obj) {
            var layEvent = obj.event; //获得 lay-event 对应的值
            var bdcdyh = obj.data.bdcdyh;
            var xmid = obj.data.xmid;
            if (isNullOrEmpty(bdcdyh)) {
                layer.msg("数据异常，未获取到bdcdyh");
            } else {
                if (layEvent === 'more') {
                    // 判断是否是多个不动单元
                    getReturnData("/slym/xm/dady/more", {
                        bdcdyh: bdcdyh,
                        xmid: xmid
                    }, 'GET', function (data) {
                        if (data) {
                            if (data === "empty") {
                                layer.msg("未查询到当前 bdcdyh 上一手的产权")
                            } else {
                                var dadyInfo = tellTdFromBdc(data.xmid);
                                if(dadyInfo.XMLY == "2"){// 土地
                                    var url = dadyInfo.tdDadyUrl;
                                    url+="?userName="+encodeURI(dadyInfo.userName);
                                    url+="&qlrmc="+encodeURI(data.qlr?data.qlr:"");
                                    url+="&tdzl="+encodeURI(data.zl?data.zl:"");
                                    url+="&tdzh="+encodeURI(data.ytdzh?data.ytdzh:"");
                                    url+="&djh="+encodeURI(data.ybdcdyh?data.ybdcdyh:"");
                                    url+="&password=";
                                    window.open(url);
                                } else {// 不动产
                                    var now = new Date();
                                    var dateStr = now.getFullYear()+"-"+(now.getMonth()+1)+'-'+now.getDate();
                                    var keycode = data.slbh;
                                    var user = dadyInfo.userName;
                                    var key = hex_md5((keycode+user+"VIEWPIC"+dateStr).toUpperCase()).toUpperCase();
                                    var url = dadyInfo.dadyUrl+"?keycode="+keycode+"&user="+user+"&key="+key;
                                    window.open(url);
                                }
                            }
                        }});
                }
            }
        });
    });


});

function tellTdFromBdc(xmid){
    var dataObj = {};
    $.ajax({
        url: '/realestate-accept-ui/slym/xm/tellTdFromBdc?xmid='+xmid,
        type: "GET",
        async: false,
        dataType: "json",
        success: function (data) {
            dataObj =  data;
        }
    });
    return dataObj;
}