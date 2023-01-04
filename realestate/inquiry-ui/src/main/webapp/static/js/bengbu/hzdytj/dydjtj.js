/**
 * author: 前端组
 * date: 2020-02-26
 * describe: tab下双层表头表格
 */
var $;
var hzdata = [];
var ybbdata = [];
layui.use(['form','jquery','element','laytpl','laydate'],function () {
    $ = layui.jquery;
    var form = layui.form,
        layer = layui.layer,
        laydate = layui.laydate,
        laytpl = layui.laytpl;

    $(function () {
        var zbData = [
            // {nc: "测试",zs: "1",tdmj: "2",tddyje: "3",dwmjdyje: "4",js: "5",zjjzzmj: "6",dyje: "7",dwmjdyje1: "8",js2: "9",jzwzmj: "10",dyje1: "11",dwmjdyje2: "12"},
            // {nc: "测试",zs: "2",tdmj: "11",tddyje: "11",dwmjdyje: "11",js: "11",zjjzzmj: "11",dyje: "11",dwmjdyje1: "11",js2: "1",jzwzmj: "1",dyje1: "1",dwmjdyje2: "1"},
            // {nc: "测试",zs: "3",tdmj: "11",tddyje: "11",dwmjdyje: "11",js: "11",zjjzzmj: "11",dyje: "11",dwmjdyje1: "11",js2: "1",jzwzmj: "1",dyje1: "1",dwmjdyje2: "1"},
            // {nc: "测试",zs: "4",tdmj: "11",tddyje: "11",dwmjdyje: "11",js: "11",zjjzzmj: "11",dyje: "11",dwmjdyje1: "11",js2: "1",jzwzmj: "1",dyje1: "1",dwmjdyje2: "1"},
            // {nc: "测试",zs: "5",tdmj: "11",tddyje: "11",dwmjdyje: "11",js: "11",zjjzzmj: "11",dyje: "11",dwmjdyje1: "11",js2: "1",jzwzmj: "1",dyje1: "1",dwmjdyje2: "1"},
            // {nc: "测试",zs: "6",tdmj: "11",tddyje: "11",dwmjdyje: "11",js: "11",zjjzzmj: "11",dyje: "11",dwmjdyje1: "11",js2: "1",jzwzmj: "1",dyje1: "1",dwmjdyje2: "1"},
        ];
        //判断查询起始时间与结束时间关系
        laydate.render({
            elem: '#startTime',
            format: 'yyyy-MM-dd',
            done: function (value, date, endDate) {
                var startDate = new Date(value).getTime();
                var endTime = new Date($('#endTime').val()).getTime();
                if (endTime < startDate) {
                    layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
                }
            }
        });
        laydate.render({ //结束时间
            elem: '#endTime',
            format: 'yyyy-MM-dd',
            done: function (value, date, endDate) {
                var startDate = new Date($('#startTime').val()).getTime();
                var endTime = new Date(value).getTime();
                if (endTime < startDate) {
                    layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
                }
            }
        });

        // 监听表单提交事件
        form.on('submit(search)', function (data) {
            if (data.field) {
                // if(data.field.startTime == "" || data.field.endTime == ""){
                //     layer.msg("请输入查询条件！");
                //     return false;
                // }
                addModel();
                // 重新请求
                // 获取查询参数
                var obj = data.field;

                console.log(obj);

                $.ajax({
                    url: "/realestate-inquiry-ui/hzdytj/hz/bb/list",
                    type: "GET",
                    data: obj,
                    contentType: 'application/json',
                    success: function (data) {
                        removeModal();
                        // hzdata = JSON.stringify(data.content[0]);
                        console.log(data);
                        if(isNotBlank(data)){
                            generateHztj(data);
                        }else {
                            showErrorInfo(e, "无数据");
                        }

                    },
                    error: function (e) {
                        showErrorInfo(e, "获取信息失败");
                    }
                });
                /*table.reload("zbTable", {
                    url: '/realestate-inquiry-ui/hzdytj/hz/bb/list'
                    , where: obj
                    , done: function (res, curr, count) {
                        removeModal();
                        hzdata = res.data;
                    }
                });*/

            } else {
                layer.msg("请输入查询条件！");
            }
            return false;
        });
    });

    function generateHztj(data) {
        var tpl = hztjTpl.innerHTML, view = $('.bdc-table-box tbody');
        //渲染数据
        laytpl(tpl).render(data, function (html) {
            view.find('tr').remove();
            view.append(html);
        });
    }
});


//数据交互
function getContextPath(){
    var pathName = document.location.pathname;
    var index = pathName.substr(1).indexOf("/");
    var result = pathName.substr(0, index + 1);
    return result;
}

//设置IP
function getIp() {
    return document.location.protocol + "//" + window.location.host;
}
