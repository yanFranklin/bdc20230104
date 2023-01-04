/**
 * author: <a href="mailto:caolu@gtmap.cn>caolu</a>
 * version 1.0.0  2022/7/7
 * describe: 舒城共享系统
 */
var reverseList = ['zl'];
var processInsId = getQueryString("processInsId");
layui.use(['table', 'laytpl', 'laydate', 'layer', 'form'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer,
        formSelects = layui.formSelects,
        form = layui.form;


    $(function () {
        //绑定回车键
        $(document).keydown(function (event) {
            if (event.keyCode == 13) {
                $("#search").click();
            }
        });


        /**
         * 加载Table数据列表
         */
        table.render({
            elem: '#gxxtTable',
            title: '查询共享系统',
            method: 'post',
            contentType: "application/json;charset=utf-8",
            defaultToolbar: ["filter"],
            // request: {
            //     limitName: 'size' //每页数据量的参数名，默认：limit
            // },
            even: true,
            cols: [[
                {width: 50, sort: false, type: 'numbers', title: '序号', unresize: true},
                {width: 400, sort: false, field: 'slbh', title: '共享业务号'},
                {width: 400, sort: false, field: 'qlrmc', title: '权利人名称'},
                {width: 400, sort: false, field: 'bdcdyh', title: '不动产单元号'},
                {width: 400, sort: false, field: 'zl', title: '坐落'},
                {width: 225, sort: false, toolbar: '#barAction' , title: '操作', fixed: 'right'}

            ]],
            data: [],
            // page: true,
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

    });



    // 附件下载
    table.on('tool(gxxtTable)', function (obj) {
        console.log(obj);
        var data = obj.data;
        var layEvent = obj.event;
        if (layEvent === 'fjxz'){
           downloadFj(data.slbh,processInsId);
        }
    });


    /**
     * 点击查询
     */
    $('#search').on('click', function () {
        var gxywh = $("#gxywh").val();
        var qlrmc = $("#qlrmc").val();
        var bdcdyh = $("#bdcdyh").val();

        if (isNullOrEmpty(gxywh) && isNullOrEmpty(qlrmc) && isNullOrEmpty(bdcdyh)) {
            warnMsg("请输入查询条件")
            return false;
        }

        // 重新请求
        table.reload("gxxtTable", {
            method: 'post',
            contentType: "application/json;charset=utf-8",
            url: "/realestate-inquiry-ui/rest/v1.0/gx/shucheng/querygxyw",
            where: {"gxywh": gxywh, "qlrxm": qlrmc, "bdcdyh": bdcdyh}
            , parseData: function (res) {
                console.log(res);
                return {
                    "code": 0
                    , "msg": ""
                    , "data": res
                }
            }
        });
    });

    /**
     * 重置
     */
    $('#reset').on('click', function () {
    });

});

//附件下载
function downloadFj(gxywh,gzlslid){
    addModel();
    var param={
        gxywh: gxywh,
        gzlslid: gzlslid
    }
    getReturnData("/rest/v1.0/gx/shucheng/gxfjxz", {gxywh: gxywh,gzlslid: gzlslid}, "GET", function (data) {
        removeModal();
        console.log(data);
        if (data) {
            layer.msg("下载成功，请在附件查看内部共享资料文件夹");
        } else {
            warnMsg("下载失败，未获取到对应材料信息");
        }
    }, function (xhr) {
        removeModal();
        delAjaxErrorMsg(xhr);
    })
}








