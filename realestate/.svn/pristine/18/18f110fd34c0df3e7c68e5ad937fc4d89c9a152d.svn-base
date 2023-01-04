/**
 * author: <a href="mailto:yaoyi@gtmap.cn>yaoyi</a>
 * version 1.0.0  2021/3/30
 * describe: 质检核查
 */
var laydate, form;

layui.use(['form', 'jquery', 'element', 'table', 'laydate'], function () {
    var $ = layui.jquery,
        laydate = layui.laydate,
        table = layui.table,
        form = layui.form;


    $(function () {

        //1、流程名称
        $.ajax({
            type: "GET",
            url: getContextPath() + "/rest/v1.0/process/gzldymcs",
            contentType: "application/json;charset=utf-8",
            dataType: "json",
            async: false,
            success: function (data) {
                $.each(data, function (index, item) {
                    $('#gzldyid').append(new Option(item.name, item.processDefKey));// 下拉菜单里添加元素
                });
                form.render("select");
            }, error: function (e) {
                delAjaxErrorMsg(e, "加载失败");
            }
        });

        //2.渲染起止时间，限制截止时间大于开始时间
        lay('.test-item').each(function () {
            //初始化日期控件
            var startDate = laydate.render({
                elem: '#kssj'
                , format: 'yyyy-MM-dd'
                , trigger: 'click',
                done: function (value, date) {
                    //选择的结束时间大
                    if ($('#jssj').val() != '' && !completeDate($('#jzsj').val(), value)) {
                        $('#jssj').val('');
                        $('.laydate-disabled.layui-this').removeClass('layui-this');
                        layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
                    }
                    endDate.config.min = {
                        year: date.year,
                        month: date.month - 1,
                        date: date.date
                    }
                }
            });
            var endDate = laydate.render({
                elem: '#jssj'
                , format: 'yyyy-MM-dd'
                , trigger: 'click'
            });

        });

        // 加载Table
        table.render({
            elem: "#pageTable",
            toolbar: "#toolbarDemo",
            title: "质检核查",
            even: true,
            request: {
                limitName: 'size', //每页数据量的参数名，默认：limit
            },
            limits: [10, 30, 50, 70, 90, 110, 130, 150],
            defaultToolbar: ['filter'],
            cols: [[
                {type: 'checkbox', fixed: 'left'},
                {field: 'slbh', minWidth: 100, title: '受理编号'},
                {field: 'gzldymc', minWidth: 200, title: '流程名称'},
                {field: 'zl', minWidth: 250, title: '坐落'},
                {field: 'bdcdyh', minWidth: 270, title: '不动产单元号'},
                {field: 'qlr', minWidth: 100, title: '权利人'},
                {field: 'ywr', minWidth: 100, title: '义务人'},
            ]],
            parseData: function (res) { //res 即为原始返回的数据
                // 设置选中行
                if (res.content && res.content.length > 0) {
                    var checkedData = layui.sessionData('checkedData');
                    res.content.forEach(function (v) {
                        $.each(checkedData, function (key, value) {
                            if (key == v.xmid) {
                                v.LAY_CHECKED = true;
                            }
                        })
                    });
                }
                return {
                    "code": res.code, //解析接口状态
                    "msg": res.message, //解析提示文本
                    "count": res.totalElements, //解析数据长度
                    "data": res.content //解析数据列表
                };
            },
            data:[],
            page: true,
            done: function () {
                removeModal();
                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
                if ($('.bdc-list-tab .layui-tab-content .layui-show .layui-table-main>.layui-table').height() == 0) {
                    $('.bdc-list-tab .layui-tab-content .layui-show .layui-table-body .layui-none').html('<img src="../static/lib/bdcui/images/table-none.png" alt="">无数据');
                } else {
                    $('.bdc-list-tab .layui-tab-content .layui-show .layui-table-body').height($('.bdc-content-box').height() - 196 - $('.bdc-task-tab').innerHeight() - $('.bdc-list-tab .layui-show .bdc-search-box').height());
                    $('.bdc-list-tab .layui-tab-content .layui-show .layui-table-fixed .layui-table-body').height($('.bdc-content-box').height() - 196 - $('.bdc-task-tab').innerHeight() - $('.bdc-list-tab .layui-show .bdc-search-box').height() - 17);
                }
                var reverseList = ['zl'];
                reverseTableCell(reverseList,"zjhcTable");
            }
        });

        //4.查询 按钮
        $('#search').on('click', function () {
            // 每次查询清除下导出缓存数据
            layui.sessionData("checkedData", null);
            search();
        });

        //监听工具条
        table.on('toolbar(pageTable)', function (obj) {
            if (obj.event === 'create') {
                var checkedData = layui.sessionData('checkedData');
                var data = [];
                $.each(checkedData, function (key, value) {
                    data.push(value);
                })
                manualScreen(data);
            }
        });

        // 监听复选框事件，缓存选中的行数据
        table.on('checkbox(pageTable)', function (obj) {
            // 获取选中或者取消的数据
            var data = obj.type == 'one' ? [obj.data] : currentPageData;
            $.each(data, function (i, v) {
                var keyT = v.xmid;
                if (obj.checked) {
                    //.增加已选中项
                    layui.sessionData('checkedData', {
                        key: keyT, value: v
                    });
                } else {
                    //.删除
                    layui.sessionData('checkedData', {
                        key: keyT, remove: true
                    });
                }
            });
        });

    });

    // 查询方法
    function search() {
        var obj = {};
        $(".search").each(function(i){
            var name = $(this).attr('name');
            var value = $(this).val();
            if(value){
                value = value.replace(/\s*/g,"");
            }
            obj[name] = value;
        });

        var sxfs = $("#sxfs").val();
        if(sxfs == 0){
            // 手动筛选
            addModel();
            // 重新请求
            table.reload("pageTable", {
                url: '/realestate-inquiry-ui/rest/v1.0/zjhc/zjxxcx',
                where: obj,
                page: {
                    curr: 1, //重新从第 1 页开始,
                    limits: [10, 50, 100, 200, 500]
                }
            });
        }else{
            // 随机筛选
            randomScreen(obj);
        }
    }

});

/**
 * 手动筛选
 * @param data 手动选择的数据
 */
function manualScreen(data){
    if(data.length == 0){
        ityzl_SHOW_INFO_LAYER("请先选择数据在进行创建。");
        return;
    }
    console.info(data);
    addModel();
    $.ajax({
        url: getContextPath() + "/rest/v1.0/zjhc/manual/cjzjxx",
        type: 'POST',
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (data) {
            removeModal();
            if(isNotBlank(data)){
                console.info(data);
                window.open("/realestate-inquiry-ui/view/zj/bdczj.html?zjdid="+ data +"&zjzt=0");
            }else{
                failMsg("随机筛选质检失败。");
            }
        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr)
        }
    });
}

/**
 * 随机筛选
 * @param obj 查询条件
 */
function randomScreen(obj){
    if(isNullOrEmpty(obj.kssj) || isNullOrEmpty(obj.jssj)){
        warnMsg("请选择时间范围后，在进行查询。");
        return;
    }
    addModel();
    $.ajax({
        url: getContextPath()+"/rest/v1.0/zjhc/random/cjzjxx",
        type: 'GET',
        data: obj,
        success: function (data) {
            removeModal();
            if(isNotBlank(data)){
                console.info(data);
                window.open("/realestate-inquiry-ui/view/zj/bdczj.html?zjdid="+ data +"&zjzt=0");
            }else{
                failMsg("随机筛选质检失败。");
            }
        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr)
        }
    });
}

function completeDate(date1, date2) {
    var oDate1 = new Date(date1);
    var oDate2 = new Date(date2);
    if (oDate1.getTime() > oDate2.getTime()) {
        return true;
    } else {
        return false;
    }
}
