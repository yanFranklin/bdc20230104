/**
 * author: <a href="mailto:zhuyong@gtmap.cn>yaoyi</a>
 * version 1.0.0  2019/10/29
 * describe: 新增南通版本，不动产单元登记历史关系处理JS
 */
layui.use(['table', 'laytpl', 'laydate', 'layer', 'form'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer,
        form = layui.form;

    // 获取参数
    var bdcdyh = $.getUrlParam('bdcdyh');
    var hideDjbBtn = $.getUrlParam('hideDjbBtn');

    if(isNullOrEmpty(bdcdyh)){
        bdcdyh = "-1";
    }

    var zdData;
    // 获取字典
    $.ajax({
        url: '/realestate-register-ui/rest/v1.0/qlxx/zd',
        type: "GET",
        async: false,
        dataType: "json",
        success: function (data) {
            zdData = data;

            // 设置权利类型下拉框
            if(zdData && zdData.qllx){
                $.each(data.qllx, function(index,item) {
                    $('#qllx').append('<option value="'+item.DM +'">'+item.MC +'</option>');
                });
            }

            // 默认查询现势数据
            $("#qszt").val(1);
            // 根据历史关系查询 默认否
            $("#sfcxls").val(2);

            form.render('select');
        }
    });


    // 下拉选择添加删除按钮
    renderSelectClose(form);



    // 设置字符过多，省略样式
    var reverseList = ['zl'];
    /**
     * 加载Table数据列表
     */
    table.render({
        elem: '#lsgxTable',
        toolbar: '#toolbar',
        title: '不动产单元登记历史关系',
        defaultToolbar: ['filter'],
        url: "/realestate-register-ui/rest/v1.0/bdcdy/lsgx",
        where: {"bdcdyh": bdcdyh, "qszt": 1, "sfcxls": '2',"version":"nantong"},
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        even: true,
        limits: commonLimits,
        cols: [[
            {width: 50, sort: false, type: 'numbers', title: '序号', unresize: true},
            {width: 110, sort: false, field: 'ytdzh', title: '原土地证号',hide:true},
            {width: 250, sort: false, field: 'ybdcdyh', title: '原不动产单元号',hide:true},
            {width: 250, sort: false, field: 'bdcdyh', title: '不动产单元号',hide:true},
            {width: 110, sort: false, field: 'slbh', title: '受理编号'},
            {
                width: 90, sort: false, field: 'djlx', title: '登记类型',
                templet: function (d) {
                    if (zdData && zdData.djlx && d.djlx) {
                        for (var index in zdData.djlx) {
                            if (zdData.djlx[index].DM == d.djlx) {
                                return djlx(d, zdData.djlx[index].MC);
                            }
                        }
                        return '';
                    } else {
                        return '';
                    }
                }
            },
            {minWidth: 230, sort: false, field: 'zl', title: '坐落'},
            {width: 270, sort: false, field: 'bdcqzh', title: '不动产权证号'},
            {width: 120, sort: false, field: 'qlr', title: '权利人'},
            {width: 200, sort: false, field: 'gzldymc', title: '流程名称'},
            {
                width: 200, sort: false, field: 'qllx', title: '权利类型',
                templet: function (d) {
                    if (zdData && zdData.qllx && d.qllx) {
                        var qllx = '';
                        for (var index in zdData.qllx) {
                            if (zdData.qllx[index].DM == d.qllx) {
                                qllx = zdData.qllx[index].MC;
                                break;
                            }
                        }
                        return qllx;
                    } else {
                        return '';
                    }
                }
            },
            {width: 120, sort: false, field: 'ywr', title: '义务人'},
            {width: 200, sort: false, field: 'djyy', title: '登记原因'},
            {minWidth: 100, sort: false, field: 'dyfs', title: '抵押方式'},
            {minWidth: 100, sort: false, field: 'fkfs', title: '付款方式'},
            {width: 100, sort: false, field: 'slr', title: '受理人'},
            {
                width: 100, sort: true, field: 'slsj', title: '受理时间',
                templet: function (d) {
                    return format(d.slsj);
                }
            },
            {
                width: 100, sort: false, field: 'sfzx', title: '是否注销',
                templet: function (d) {
                    if (zdData && zdData.sf && d.sfzx) {
                        var sfzx = '';
                        for (var index in zdData.sf) {
                            if (zdData.sf[index].DM == d.sfzx) {
                                sfzx = zdData.sf[index].MC;
                                break;
                            }
                        }
                        return sfzx;
                    } else {
                        return '';
                    }
                }
            },
            {
                width: 100, sort: true, field: 'djsj', title: '登簿时间',
                templet: function (d) {
                    return format(d.djsj);
                }
            },
            {
                field: 'qszt', width: 100, title: '权属状态', fixed: 'right', sort: false,
                templet: function (d) {
                    return formatQszt(d.qszt);
                }
            },
            {width: 140, sort: false, templet: '#barAction', title: '操作', fixed: 'right'}
        ]],
        text: {
            none: '未查询到数据'
        },
        autoSort: false,
        page: true,
        parseData: function (res) {
            return {
                code: res.code, //解析接口状态
                msg: res.msg, //解析提示文本
                count: res.totalElements, //解析数据长度
                data: res.content //解析数据列表
            }
        },
        done: function () {
            var searchHeight = 131;
            setTableHeight(searchHeight);

            reverseTableCell(reverseList);
            if (hideDjbBtn && hideDjbBtn == 'true') {
                $("#djb").attr("style", "display:none");
                $("#djb").removeAttr("lay-event");
            }

            // 根据状态显示背景颜色
            setColor();
        }
    });
    $('.bdc-table-box').on('mouseenter', 'td', function () {
        if ($(this).text() && reverseList.indexOf($(this).attr("data-field")) != -1) {
            $(this).append('<div class="layui-table-grid-down"><i class="layui-icon layui-icon-down"></i></div>');
        }
        $('.layui-table-grid-down').on('click', function () {
            setTimeout(function () {
                $('.layui-table-tips-main .bdc-table-date').html(reverseString($('.layui-table-tips-main .bdc-table-date').html()));
            }, 20);
        });
    });
    if (!(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion.match(/9./i) == "9.")) {
        //监听input点击
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
    }
    //头工具栏事件
    table.on('toolbar(lsgxTable)', function (obj) {
        if(isNullOrEmpty(bdcdyh) || "-1" == bdcdyh){
            warnMsg(" 未指定不动产单元，无法查看！");
            return;
        }

        if ("djb" == obj.event) {
            if (isNullOrEmpty(bdcdyh)) {
                layer.alert("不动产单元号为空，无法查看！", {
                    title: '提示'
                });
                return;
            }

            window.open('/realestate-register-ui/view/djbxx/bdcDjb.html?param=' + bdcdyh);
        }
    });

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

    function add0(time) {
        if (time < 10) {
            return '0' + time;
        }
        return time;
    }

    /**
     * 监听排序事件
     */
    table.on('sort(lsgxTable)', function (obj) {
        table.reload('lsgxTable', {
            initSort: obj
            , where: {
                field: obj.field //排序字段
                , order: obj.type //排序方式
            }
        });

    });

    // 项目办理查看
    table.on('tool(lsgxTable)', function (obj) {
        var data = obj.data;
        if (obj.event === 'process') {
            if(!isNullOrEmpty(data) && '1' == data.xmly){
                window.open( '/realestate-portal-ui/view/new-page.html?type=lsgx&processinsid=' + data.gzlslid);
            } else {
                window.open('/realestate-register-ui/view/lsgx/new-page.html?xmid=' + data.xmid + '&processInsId=' + data.gzlslid);
            }
        } if (obj.event === 'dady') {// 南通特有需求，档案调用
            dady(data);
        }
    });

    /**
     * 点击查询
     */
    $('#search').on('click', function () {
        var slbh = $("#slbh").val();
        var bdcqzh = $("#bdcqzh").val();
        var qllx = $("#qllx").val();
        var qszt = $("#qszt").val();
        var sfcxls = $("#sfcxls").val();

        // 重新请求
        table.reload("lsgxTable", {
            where: {"slbh": slbh, "bdcqzh": bdcqzh, "bdcdyh": bdcdyh, "qllx": qllx, "qszt": qszt, "sfcxls": sfcxls}
            , page: {
                curr: 1,  //重新从第 1 页开始
            }
        });
    });

    /**
     * 重置
     */
    $('#reset').on('click', function () {
        // 根据历史关系查询 默认否
        $("#sfcxls").val(2);

        table.reload("lsgxTable", {
            where: {"bdcdyh": bdcdyh, "slbh": '', "bdcqzh": '', "qllx": '', "qszt": '', "sfcxls": '2'}
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
    });

    /**
     * 根据状态显示背景颜色
     */
    function setColor() {
        //根据权利状态、办理状态整行显色
        var colorList = [];
        $.ajax({
            url: '/realestate-register-ui/rest/v1.0/bdcdy/status/color',
            type: "GET",
            success: function (data) {
                for (var i in data) {
                    colorList.push({name: i, color: '#333', background: data[i]});
                }
                changeTrBackgroundExceptRight(colorList);
            }
        });
    }

    function djlx(data, djlxmc) {
        if(400 == data.djlx) {
            // 如果是注销类流程不展示颜色则直接显示登记类型名称
            return djlxmc;
        }


        if(98 == data.qllx) {
            // 查封
            $.ajax({
                url: '/realestate-register-ui/rest/v1.0/bdcdy/xmjbxx?xmid=' + data.xmid,
                type: "GET",
                async: false,
                dataType: "json",
                success: function (xmxx) {
                    if (!(xmxx && xmxx.sfjf && 1 == xmxx.sfjf)) {
                        // 如果是解封不展示颜色则直接显示登记类型名称
                        djlxmc = '<span class="bdc-cf">' + djlxmc + '</span>';
                    }
                }
            });
        } else if(95 == data.qllx ||37 == data.qllx) {
            // 抵押
            djlxmc = '<span class="bdc-dya">' + djlxmc + '</span>';
        } else if(97 == data.qllx) {
            // 异议
            djlxmc = '<span class="bdc-yy">' + djlxmc + '</span>';
        } else if(96 == data.qllx) {
            // 预告
            djlxmc = '<span class="bdc-yg">' + djlxmc + '</span>';
        }

        return djlxmc;
    }
});

function tellTdFromBdc(xmid){
    var dataObj = {};
    $.ajax({
        url: '/realestate-register-ui/rest/v1.0/bdcdy/tellTdFromBdc?xmid='+xmid,
        type: "GET",
        async: false,
        dataType: "json",
        success: function (data) {
            dataObj =  data;
        }
    });
    return dataObj;
}

// 档案调用
function dady(data) {
    var dadyInfo = tellTdFromBdc(data.xmid)
    if (dadyInfo.version == "haimen") {
        // 海门版本
        if (!dadyInfo || isNullOrEmpty(dadyInfo.hmDadyUrl)) {
            failMsg("未配置档案调用地址，请联系管理员!");
            return;
        }
        window.open(dadyInfo.hmDadyUrl + data.slbh);
    } else {
        // 南通版本
        /**
         * 【32413】 【南通】档案系统需求
         * 根据当前用户的部门判断跳转的地址。例如，市区的用户点击档案调用后调取市区的档案数据，开发区的用户点击档案调用后调取开发区的档案数据
         */
        if (dadyInfo.kfqFlag) {
            var now = new Date();
            var dateStr = now.getFullYear() + "-" + (now.getMonth() + 1) + '-' + now.getDate();
            var keycode = data.slbh;
            var user = dadyInfo.userName;
            var key = hex_md5((keycode + user + "VIEWPIC" + dateStr).toUpperCase()).toUpperCase();
            var url = dadyInfo.kfqDadyUrl + "?keycode=" + keycode + "&user=" + user + "&key=" + key;
            window.open(url);
        } else {
            if(dadyInfo.XMLY == "2"){// 土地
                var url = dadyInfo.tdDadyUrl;
                url+="?userName="+encodeURI(dadyInfo.userName);
                url+="&qlrmc="+encodeURI(data.qlr?data.qlr:"");
                url+="&tdzl="+encodeURI(data.zl?data.zl:"");
                url+="&tdzh="+encodeURI(data.ytdzh?data.ytdzh:"");
                url+="&djh="+encodeURI(data.ybdcdyh?data.ybdcdyh:"");
                url+="&password=";
                window.open(url);
            }else{// 不动产
                var now = new Date();
                var dateStr = now.getFullYear()+"-"+(now.getMonth()+1)+'-'+now.getDate();
                var keycode = data.slbh;
                var user = dadyInfo.userName;
                var key = hex_md5((keycode+user+"VIEWPIC"+dateStr).toUpperCase()).toUpperCase();
                var url = dadyInfo.dadyUrl+"?keycode="+keycode+"&user="+user+"&key="+key;
                window.open(url);
            }
        }

    }

}
