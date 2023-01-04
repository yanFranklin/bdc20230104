/**
 * 异常办件预警——超快办件台账
 */
layui.config({
    base: '../../static/lib/form-select/'
}).extend({
    formSelects: 'formSelects-v4'
});
var formSelects;
// 人员下拉数组
var rymcSelList = [];
var zdList = getZdList();
var ycbjyy = zdList.ycbjyy;
var defaultDate = new Date();
defaultDate.setDate(1);
defaultDate.setHours(0,0,0,0);
var defaultDateStr = defaultDate.Format("yyyy-MM-dd hh:mm:ss");

layui.use(['form', 'jquery', 'laydate', 'element', 'table','formSelects'], function () {
    var $ = layui.jquery,
        form = layui.form,
        laydate = layui.laydate,
        element = layui.element,
        table = layui.table;
    formSelects = layui.formSelects;

    var beginTime = laydate.render({
        elem: '#lckssj',
        type: 'datetime',
        value: defaultDate,
        trigger: 'click',
        done: function (value, date) {
            //选择的结束时间大
            if ($('#lcjssj').val() != '' && !completeDate($('#lcjssj').val(), value)) {
                $('#lcjssj').val('');
                $('.laydate-disabled.layui-this').removeClass('layui-this');
            }
            endTime.config.min = {
                year: date.year,
                month: date.month - 1,
                date: date.date,
                hours: date.hours,
                minutes: date.minutes,
                seconds: date.seconds
            }

        }
    });
    var endTime = laydate.render({
        elem: '#lcjssj',
        type: 'datetime',
        trigger: 'click'
    });


    //比较起止时间
    function completeDate(date1, date2) {
        var oDate1 = new Date(date1);
        var oDate2 = new Date(date2);
        if (oDate1.getTime() > oDate2.getTime()) {
            return true;
        } else {
            return false;
        }
    }

    // 加载工作流下拉框
    $.ajax({
        url: "/realestate-supervise/rest/v1.0/ycbjyj/process",
        type: "GET",
        dataType: "json",
        success: function (data) {
            if(data){
                var formSelectData = new Array();
                $.each(data, function (index, item) {
                    formSelectData.push({"name":item.name, "value":item.name});
                });
                formSelects.data('gzldymc', 'local', {arr: formSelectData});
                form.render('select');
            }
        },
        complete: function () {
        }
    });

    // 默认获取所有用户
    $.ajax({
        url: '/realestate-supervise/rest/v1.0/ztree/user/list',
        type: "GET",
        dataType: "json",
        success: function (data) {
            if (data) {
                $.each(data, function (index, item) {
                    $('#rymc').append('<option value="' + item.alias + '">' + item.alias + '</option>');
                    rymcSelList.push({
                        name: item.alias,
                        value: item.alias
                    })
                });
                formSelects.data('selectBjry', 'local', {
                    arr: rymcSelList
                });
            }
        }
    });

    // 获取页面表单标识，用于权限控制
    var moduleCode = $.getUrlParam('moduleCode');
    if (moduleCode) {
        setElementAttrByModuleAuthority(moduleCode);
    }
    if(zdList) {
        // 质检类型查询条件下拉框
        if (ycbjyy) {
            $.each(ycbjyy, function (index, item) {
                $('#ycbjyy').append('<option value="' + item.DM + '">' + item.MC + '</option>');
            });
        }
        form.render('select');
    }

    table.render({
        elem: '#pageTable',
        url: "/realestate-supervise/rest/v1.0/ycbjyj/ckbj",
        title: '超期办件台账',
        defaultToolbar: ['filter'],
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        where: {
            'lckssj': defaultDateStr
        },
        cellMinWidth: 80,
        even: true,
        cols: [[
            {width: 150, sort: false, field: 'slbh', title: '受理编号'},
            {minWidth: 250, sort: false, field: 'gzldymc', title: '流程名称'},
            {width: 150, sort: false, field: 'gzlslid', title: '工作流实例ID', hide: true},
            {width: 200, sort: false, field: 'lckssj', title: '流程开始时间'},
            {width: 200, sort: false, field: 'lcjssj', title: '流程结束时间'},
            {width: 150, sort: false, field: 'bjsc', title: '实际办件时长',
                templet: function (d) {
                    return isNullOrEmpty(d.bjsc) ? "0分钟" : toHourMinute(d.bjsc);
                }
            },
            {width: 150, sort: false, field: 'ckbjscbz', title: '超快办件时长标准',
                templet: function (d) {
                    return isNullOrEmpty(d.ckbjscbz) ? "0分钟" : toHourMinute(d.ckbjscbz);
                }
            },
            {width: 180, sort: false, field: 'slr', title: '受理人员'},
            {width: 200, sort: false, field: 'bmmc', title: '部门名称'},
            {width: 200, sort: false, field: 'ycbjyy', title: '异常办件原因'},
            {width: 200, sort: false, field: 'yysm', title: '异常详情描述'},
            {width: 280, sort: false, templet: '#barAction', title: '操作', fixed: 'right'}
        ]],
        text: {
            none: '未查询到数据'
        },
        data: [],
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
            $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');


            if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
                $('.layui-table-body .layui-none').html('<img src="../../static/lib/registerui/image/table-none.png" alt="">无数据');
            }else {
                $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
            }
        }
    });

    /**
     * 点击查询
     */
    $('#search').on('click', function () {
        search();
        return false;
    });


    //点击高级查询
    $('#seniorSearch').on('click', function () {
        $('.pf-senior-show').toggleClass('bdc-hide');
        $(this).parent().toggleClass('bdc-button-box-four');
        $('.bdc-percentage-container').css('padding-top',$('.bdc-search-content').height() + 10);
        setHeight();
    });


    function search() {

        addModel();

        // 获取查询内容
        var obj = {};
        $(".search").each(function (i) {
            // 获取部门id
            if ($(this).attr("id") == "bmmc") {
                var name = "bmid";
                var value = $("#djbmdm").val();
            } else if ($(this).attr("id") == "rymc") {
                var value = formSelects.value($(this).attr('xm-select'), 'name').join(",");
                var name = $(this).attr("id");
            } else {
                var value = $(this).val();
                var name = $(this).attr('name');
            }
            obj[name] = value;
        });
        obj.gzldymc =  formSelects.value('gzldymc', 'valStr');

        // 重新请求
        table.reload("pageTable", {
            url: "/realestate-supervise/rest/v1.0/ycbjyj/ckbj"
            ,where: obj
            , page: {
                curr: 1  //重新从第 1 页开始
            },
            done: function () {
                $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');

                if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
                    $('.layui-table-body .layui-none').html('<img src="../../static/lib/registerui/image/table-none.png" alt="">无数据');
                }else {
                    $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                    $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
                }
                removeModal();
            }
        });
        return false;
    }

    //绑定回车键
    $(document).keydown(function (event) {
        if (event.keyCode == 13) {
            search();
        }
    });

    //监听工具条
    table.on('tool(pageTable)', function (obj) {
        var data = obj.data;
        if (obj.event === 'lct') {
            lct(data);
        } else if(obj.event === "ycyyfj") {
            ycyyfj(data);
        }else if(obj.event === "djyw") {
            djyw(data);
        }
    });

    function lct(data) {
        if(!data || isNullOrEmpty(data.gzlslid)) {
            warnMsg("未查询到流程实例信息，无法查看！");
            return;
        }

        window.open("/bpm-ui/process/processDetail/" + data.gzlslid);
    }

    function djyw(data) {
        if(!data || isNullOrEmpty(data.gzlslid)) {
            warnMsg("未查询到流程实例信息，无法查看！");
            return;
        }

        window.open("/realestate-portal-ui/view/new-page.html?type=lsgx&processinsid=" + data.gzlslid);
    }

    /**
     * 异常原因附件
     * @param data
     */
    function ycyyfj(data) {
        if(isNullOrEmpty(data.ycbjid)) {
            warnMsg("未查询到异常原因附件");
            return;
        }

        layer.open({
            type: 2,
            title: '附件信息',
            anim: -1,
            shadeClose: true,
            maxmin: true,
            shade: false,
            area: ['960px', '500px'],
            offset: 'auto',
            content: [ "../file/files.html?page=view&ywid=" + data.ycbjid, 'yes'],
            success: function (layero, index) {
            },
            end:function(){
            }
        });
    }
});
