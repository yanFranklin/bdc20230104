/**
 * author: <a href="mailto:zhuyong@gtmap.cn>zhuyong</a>
 * version 1.0.0  2019/02/21
 * describe: 不动产单元登记历史关系处理JS
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
        }
    });




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
        where: {"bdcdyh": bdcdyh, "sfcxls": '1'},
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
                                return zdData.djlx[index].MC;
                            }
                        }
                        return '';
                    } else {
                        return '';
                    }
                }
            },
            {width: 200, sort: false, field: 'gzldymc', title: '流程名称'},
            {width: 200, sort: false, field: 'djyy', title: '登记原因'},
            {width: 120, sort: false, field: 'qlr', title: '权利人'},
            {width: 120, sort: false, field: 'ywr', title: '义务人'},
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

            {width: 270, sort: false, field: 'bdcqzh', title: '不动产权证号'},
            {minWidth: 230, sort: false, field: 'zl', title: '坐落'},
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
                width: 100, sort: true, field: 'djsj', title: '登簿时间',
                templet: function (d) {
                    return format(d.djsj);
                }
            },
            {
                field: 'qszt', width: 100, title: '权属状态', fixed: 'right', sort: false,
                templet: function (d) {
                    return formatLsgxQszt(d.qszt, d.ajzt);
                }
            },
            {width: 140, sort: false, toolbar: '#barAction' , title: '操作', fixed: 'right'}
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
        }
    });

    //监听滚动事件
    var scrollTop = 0,
        scrollLeft = 0;
    var tableTop = 0, tableLeft = 0;
    var $nowBtnMore = '';
    $(window).on('scroll', function () {
        scrollTop = $(this).scrollTop();
        scrollLeft = $(this).scrollLeft();

        if ($nowBtnMore != '') {
            if (tableTop + 26 + $nowBtnMore.height() < document.body.offsetHeight) {
                // $nowBtnMore.css({top: tableTop + 26 -scrollTop,right: 20});
                $nowBtnMore.css({top: tableTop + 26 - scrollTop, left: tableLeft - 30});
            } else {
                // $nowBtnMore.css({top: tableTop -scrollTop - $nowBtnMore.height(),right: 20});
                $nowBtnMore.css({top: tableTop - scrollTop - $nowBtnMore.height(), left: tableLeft - 30});
            }
        }
    });
    //点击表格中的更多
    $('.bdc-table-box').on('click', '.bdc-more-btn', function (event) {
        tableTop = $(this).offset().top;
        tableLeft = $(this).offset().left;
        event.stopPropagation();
        $nowBtnMore = $(this).siblings('.bdc-table-btn-more');
        var $btnMore = $(this).siblings('.bdc-table-btn-more');
        if ($(this).offset().top + 26 + $btnMore.height() < document.body.offsetHeight) {
            // $btnMore.css({top: $(this).offset().top + 26 -scrollTop,right: 20});
            $btnMore.css({top: tableTop + 26 - scrollTop, left: tableLeft - 30});
        } else {
            // $btnMore.css({top: $(this).offset().top -scrollTop - $btnMore.height(),right: 20});
            $btnMore.css({top: tableTop - scrollTop - $btnMore.height(), left: tableLeft - 30});
        }
        $('.bdc-table-btn-more').hide();
        $btnMore.show();
    });
    //点击更多内的任一内容，隐藏
    $('.bdc-table-btn-more a').on('click', function () {
        $(this).parent().hide();
    });
    //点击页面任一空白位置，消失
    $('body').on('click', function () {
        $('.bdc-table-btn-more').hide();
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
                window.open('/realestate-portal-ui/view/new-page.html?type=lsgx&processinsid=' + data.gzlslid);
            } else {
                window.open('/realestate-register-ui/view/lsgx/new-page.html?xmid=' + data.xmid + '&processInsId=' + data.gzlslid);
            }
        }
        if (obj.event === 'dady') {// 南通特有需求，档案调用
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
        if (obj.event === 'fcda') {
            // modify 2020-10-26 33502 合肥档案增加type参数，1是产权，2是抵押权
            var type = 1;
            if (data.qllx == 95 ||data.qllx == 37) {
                type = 2;
            }
            var url = "/realestate-register-ui/view/daxx/scan.html?type=" + type + "&slbh=" + data.slbh + "&bdcqzh=";
            if (!isNullOrEmpty(data.bdcqzh)) {
                var strs = [];
                strs = data.bdcqzh.split(","); //字符分割
                url += encodeURI(strs[0]);
            }
            window.open(url);
        }
        if (obj.event === 'lpb') {
            addModel();
            getReturnData("/rest/v1.0/bdcdy/fwbdcdy",{bdcdyh:data.bdcdyh,qjgldm:data.qjgldm},"GET",function (obj) {
                if(obj &&isNotBlank(obj.fwDcbIndex)) {
                    window.open("/building-ui/lpb/view?code=analysis&bdcdyh=" + data.bdcdyh +"&qjgldm="+data.qjgldm);
                }else{
                    showAlertDialog("未找到楼盘表");
                }
                removeModel();
            },function (error) {
                delAjaxErrorMsg(error)
            })
        }
        if(obj.event === 'djdcb'){
            window.open('/building-ui/djdcb/initview?bdcdyh=' + data.bdcdyh+"&qjgldm="+data.qjgldm);
        }
        if (obj.event === 'ompRedirect') {
            window.open('/building-ui/omp/redirect/' + data.bdcdyh + '/' + data.bdclx);
        }

        if(obj.event === 'bdcdjda'){
            openBdcDjDa(data);
        }

        if(obj.event === 'djls') {
            window.open('/realestate-register-ui/view/lsgx/bdcdyDjLsgx.html?bdcdyh=' + data.bdcdyh);
        }
    });

    /**
     * 点击查询
     */
    $('#search').on('click', function () {
        var slbh = $("#slbh").val();
        var bdcqzh = $("#bdcqzh").val();
        var djyy = $("#djyy").val();
        if (isNullOrEmpty(slbh) && isNullOrEmpty(bdcqzh) && isNullOrEmpty(djyy)) {
            layer.alert("请输入查询条件！", {title: '提示'});
            return false;
        }

        // 重新请求
        table.reload("lsgxTable", {
            where: {"slbh": slbh, "bdcqzh": bdcqzh, "bdcdyh": bdcdyh, "djyy" : djyy}
            , page: {
                curr: 1,  //重新从第 1 页开始
            }
        });
    });

    /**
     * 重置
     */
    $('#reset').on('click', function () {
        table.reload("lsgxTable", {
            where: {"bdcdyh": bdcdyh, "slbh": '', "bdcqzh": '', 'djyy':''}
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
    });

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

/**
 * 不动产登记档案
 * @param data
 */
function openBdcDjDa(data){
    var xmid = data.xmid;
    if(xmid){
        $.ajax({
            url: "/realestate-register-ui/rest/v1.0/url/common/bdcda?xmid="+xmid+"&gzlslid=" ,
            type: 'get',
            success: function (data) {
                console.log("不动产档案url:"+data);
                if(data){
                    window.open(data);
                }
            },
            error: function (err) {
                removeModal();
                layer.closeAll();
            }
        });
    }else{
        warnMsg("请求失败，该数据xmid为空！");
    }
}

