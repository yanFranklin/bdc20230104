/**
 * author: <a href="mailto:zhuyong@gtmap.cn>zhuyong</a>
 * version 1.0.0  2019/02/21
 * describe: 不动产单元登记历史关系处理JS
 */
layui.use(['table', 'laytpl', 'laydate', 'layer', 'form'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer,
        formSelects = layui.formSelects,
        form = layui.form;

    // 获取参数
    var bdcdyh = getQueryString('bdcdyh');
    var hideDjbBtn = getQueryString('hideDjbBtn');
    formSelects.config('selectDjxl', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('selectQllx', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    if(isNullOrEmpty(bdcdyh)){
        bdcdyh = "-1";
    }


    var cxlx=$('#cxlx').val();
    /**
     * 排序
     * @param obj1
     * @param obj2
     * @returns {number}
     */
    var compare = function (obj1, obj2) {
        var val1 = obj1.DM;
        var val2 = obj2.DM;
        if (val1 < val2) {
            return -1;
        } else if (val1 > val2) {
            return 1;
        } else {
            return 0;
        }
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
            if (data) {
                if (data.djxl) {
                    formSelects.data('selectDjxl', 'local', {arr: data.djxl})
                }
                if (data.qllx) {
                    formSelects.data('selectQllx', 'local', {arr: data.qllx.sort(compare)})
                }
            }
        }
    });





    // 设置字符过多，省略样式
    var reverseList = ['zl'];
    /**
     * 加载Table数据列表
     */
    generateTable();

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

    function generateTable() {
        table.render({
            elem: '#lsgxTable',
            toolbar: '#toolbar',
            title: '不动产单元登记历史关系',
            defaultToolbar: ['filter'],
            url: "/realestate-register-ui/rest/v1.0/bdcdy/cfdjls",
            where: {"bdcdyh": bdcdyh},
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            even: true,
            limits: [10,20,50,100,200,500],
            cols: [[
                {width: 50, sort: false, type: 'numbers', title: '序号', unresize: true},
                {minWidth: 200, sort: false, field: 'zl', title: '坐落'},
                {
                    width: 90, sort: false, field: 'djlx', title: '查封类型',
                    templet: function (d) {
                        if (zdData && zdData.cflx && d.cflx) {
                            for (var index in zdData.cflx) {
                                if (zdData.cflx[index].DM == d.cflx) {
                                    return zdData.cflx[index].MC;
                                }
                            }
                            return '';
                        } else {
                            return '';
                        }
                    }
                },
                {width: 120, sort: false, field: 'slsj', title: '受理时间'},
                {width: 120, sort: false, field: 'cfbh', title: '查封编号'},
                {width: 120, sort: false, field: 'cfqssj', title: '查封起始时间'},
                {width: 120, sort: false, field: 'cfjssj', title: '查封结束时间'},
                {width: 120, sort: false, field: 'cfjg', title: '查封机关'},

                {width: 120, sort: false, field: 'cfwh', title: '查封文号'},
                {
                    width: 90, sort: false, field: 'qszt', title: '查封状态',
                    templet: function (d) {
                        if (zdData && zdData.qszt && d.qszt) {
                            for (var index in zdData.qszt) {
                                if (zdData.qszt[index].DM == d.qszt) {
                                    return zdData.qszt[index].MC;
                                }
                            }
                            return '';
                        } else {
                            return '';
                        }
                    }
                },
                {width: 120, sort: false, field: 'jfdjsj', title: '解封日期'},

                {width: 120, sort: false, field: 'ycfbh', title: '原查封编号'},

                {width: 120, sort: false, field: 'ycfsj', title: '原查封时间'},

                {width: 140, sort: false, toolbar: '#barAction', title: '操作', fixed: 'right'}

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
    }

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
                window.open('/realestate-register-ui/view/lsgx/new-page.html?xmid=' + data.xmid + '&processInsId=' + data.gzlslid + '&version=changzhou');
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
        var djxlArray = formSelects.value('selectDjxl');
        var qllxArray = formSelects.value('selectQllx');
        var djxl;
        var qllx;
        if(djxlArray.length!=0){
            djxl = djxlArray[0].DM;
        }
        if(qllxArray.length!=0){
            qllx = qllxArray[0].DM;
        }
        if (isNullOrEmpty(slbh) && isNullOrEmpty(bdcqzh) && isNullOrEmpty(djyy) && isNullOrEmpty(djxl) && isNullOrEmpty(qllx)) {
            generateTable();
            return false;
        }

        // 重新请求
        table.reload("lsgxTable", {
            where: {"slbh": slbh, "bdcqzh": bdcqzh, "bdcdyh": bdcdyh, "djyy" : djyy,"djxlc":djxl,"qllx":qllx}
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
            where: {"bdcdyh": bdcdyh, "slbh": '', "bdcqzh": '', 'djyy':'', 'djxlc': '', "qllx": ''}
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

function formatLsgxQszt(qszt, ajzt) {
    if (qszt == 0) {
        return '<span class="" style="color:orange;">临时</span>';
    } else if (qszt == 1) {
        return '<span class="" style="color:green;">现势</span>';
    } else if (qszt == 2) {
        return '<span class="" style="color:gray;">历史</span>';
    } else if (qszt == 3) {
        if (ajzt == 5) {
            return '<span class="" style="color:black;">撤回</span>';
        } else {
            return '<span class="" style="color:black;">终止</span>';
        }
    } else {
        return '';
    }
}
function cfqx(d) {
    var cfqx;
    if (d) {
        if (d.cfqssj && d.cfjssj) {
            cfqx = d.cfqssj ||"" +"起" + d.cfjssj ||"" +"止";
        }
    }

    return cfqx == undefined ? '' : cfqx;
}