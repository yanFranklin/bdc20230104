var jjdid = '';
layui.use(['table', 'laytpl','laydate', 'layer'], function () {
    var table = layui.table,
        $ = layui.jquery,
        laydate = layui.laydate,
        layer = layui.layer;
    $(function () {

        var qssj = laydate.render({
            elem: '#qssj',
            type: 'datetime',
            trigger: 'click',
            done: function (value, date) {
                //选择的结束时间大
                if ($('#jssj').val() != '' && !completeDate($('#jssj').val(), value)) {
                    $('#jssj').val('');
                    $('.laydate-disabled.layui-this').removeClass('layui-this');
                }
                jssj.config.min = {
                    year: date.year,
                    month: date.month - 1,
                    date: date.date,
                    hours: date.hours,
                    minutes: date.minutes,
                    seconds: date.seconds
                }
            }
        });
        var jssj = laydate.render({
            elem: '#jssj',
            type: 'datetime',
            value: new Date(),
            trigger: 'click'
        });

        zdList = getZdList();

        /**
         * 点击查询
         */
        $('#search').on('click', function () {
            // 获取查询内容
            var obj = {};
            $(".search").each(function (i) {
                var value = $(this).val();
                if (!isNullOrEmpty(value)) {
                    var name = $(this).attr('name');
                    obj[name] = value;
                }
            });
            // 重新请求
            table.reload("tableOne", {
                url: BASE_URL + "/jjd/page/all"
                , where: obj
                , page: {
                    curr: 1  //重新从第 1 页开始
                }
            });
            return false;
        });

        table.render({
            elem: '#tableOne',
            title: '交接单信息',
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            even: true,
            limits: commonLimits,
            url: BASE_URL + "/jjd/page/all",
            cols: [[
                {type: 'radio', width: 50, fixed: 'left'},
                {field: 'jjdh', title: '交接单号', width: 200, sort: true},
                {
                    field: 'jjdlx', title: '交接单类型', width: 200,
                    templet: function (d) {
                        if (zdList && zdList.jjdlx && !isNullOrEmpty(d.jjdlx + '')) {
                            for (var index in zdList.jjdlx) {
                                if (zdList.jjdlx[index].DM == d.jjdlx) {
                                    return zdList.jjdlx[index].MC;
                                }
                            }
                            return '';
                        } else {
                            return '';
                        }
                    }
                },
                {field: 'zfr', title: '转发人', width: 100},
                {field: 'zfsj', title: '转发时间', width: 200},
                {field: 'zfks', title: '转发科室', width: 100},
                {field: 'jsr', title: '接收人'},
                {field: 'jssj', title: '接收时间'},
                {field: 'jsks', title: '接收科室'},
                {field: 'jjdzt', title: '交接单状态', templet: '#jjdztTpl'}
            ]],
            page: true,
            parseData: function (res) {
                res.content.forEach(function (v) {
                    if (v.zfsj) {
                        var newStartTime = new Date(v.zfsj);
                        v.zfsj = format(newStartTime);
                    }
                    if (v.jssj) {
                        var newEndTime = new Date(v.jssj);
                        v.jssj = format(newEndTime);
                    }
                });
                return {
                    code: res.code, //解析接口状态
                    msg: res.msg, //解析提示文本
                    count: res.totalElements, //解析数据长度
                    data: res.content //解析数据列表
                }
            },
            done: function () {
                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');

                if ($('.layui-table-body>.layui-table').height() == 0) {
                    $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
                }
            }
        });

        var clickIndex = 0;
        table.on('radio(oneFilter)', function (obj) {
            jjdid = obj.data.jjdid;
            checkJjd = obj.data;
            clickIndex++;
            // 第一次选择需要渲染页面，以后重新加载就可以
            if (clickIndex == 1) {
                renderTable(jjdid);
            } else {
                reloadTable(jjdid);
            }
        });

        // 首次渲染表格
        function renderTable(jjdid) {
            table.render({
                elem: '#tableTwo',
                id: 'twoId',
                title: '交接单项目信息',
                even: true,
                request: {
                    limitName: 'size' //每页数据量的参数名，默认：limit
                },
                limits: commonLimits,
                cols: [[
                    {type: 'checkbox', width: 50, fixed: 'left'},
                    {field: 'jjdxmid', title: '交接单项目ID', hide: true},
                    {field: 'slbh', title: '受理编号', sort: true},
                    {field: 'djlxmc', title: '登记类型名称'},
                    {field: 'gzldymc', title: '流程名称'},
                    {field: 'zl', title: '房屋地址'},
                    {field: 'qlr', title: '申请人', templet: '#qlrTpl'},
                    {field: 'cqr', title: '产权人', templet: '#ywrTpl'}
                ]],
                url: BASE_URL + "/jjd/xm?jjdid=" + jjdid,
                parseData: function (res) {
                    return {
                        code: res.code, //解析接口状态
                        msg: res.msg, //解析提示文本
                        count: res.totalElements, //解析数据长度
                        data: res.content //解析数据列表
                    }
                },
                done: function () {
                    $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');

                    if ($('.layui-table-body>.layui-table').height() == 0) {
                        $('.layui-table-body .layui-none').html('<img src="../images/table-none.png" alt="">无数据');
                    }
                }
            });
        }

        // 重新加载
        function reloadTable(jjdid) {
            table.reload('twoId', {
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                url: BASE_URL + "/jjd/xm?jjdid=" + jjdid
            });
        }

    });
});

// 获取项目申请人和产权人，针对组合流程中包含抵押流程的项目需要显示产权类的信息
function ddyqlr(gzlslid) {
    $.ajax({
        url: BASE_URL + "/jjd/ddyqlr?gzlslid=" + gzlslid,
        type: 'GET',
        dataType: 'json',
        async: true,
        success: function (data) {
            if (!isNullOrEmpty(data)) {
                $("."+ gzlslid +"_qlr").html('<p>'+ data.qlr + '</p>');
                $("."+ gzlslid +"_ywr").html('<p>'+ data.ywr + '</p>');
            } else {
                $("."+ gzlslid +"_qlr").html('<p></p>');
                $("."+ gzlslid +"_ywr").html('<p></p>');
            }
        }
    });
}

function completeDate(date1,date2){
    var oDate1 = new Date(date1);
    var oDate2 = new Date(date2);
    if(oDate1.getTime() >= oDate2.getTime()){
        return true;
    } else {
        return false;
    }
}