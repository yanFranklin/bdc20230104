/**
 * author: 前端组
 * date: 2020-02-26
 * describe: tab下双层表头表格
*/
var $;
var hzdata = [];
var ybbdata = [];
layui.use(['form','jquery','element','layer','table','laydate'],function () {
    $ = layui.jquery;
    var form = layui.form,
        layer = layui.layer,
        element = layui.element,
        table = layui.table,
        laydate = layui.laydate;

    $(function () {
        var zbData = [
            // {nc: "测试",zs: "1",tdmj: "2",tddyje: "3",dwmjdyje: "4",js: "5",zjjzzmj: "6",dyje: "7",dwmjdyje1: "8",js2: "9",jzwzmj: "10",dyje1: "11",dwmjdyje2: "12"},
            // {nc: "测试",zs: "2",tdmj: "11",tddyje: "11",dwmjdyje: "11",js: "11",zjjzzmj: "11",dyje: "11",dwmjdyje1: "11",js2: "1",jzwzmj: "1",dyje1: "1",dwmjdyje2: "1"},
            // {nc: "测试",zs: "3",tdmj: "11",tddyje: "11",dwmjdyje: "11",js: "11",zjjzzmj: "11",dyje: "11",dwmjdyje1: "11",js2: "1",jzwzmj: "1",dyje1: "1",dwmjdyje2: "1"},
            // {nc: "测试",zs: "4",tdmj: "11",tddyje: "11",dwmjdyje: "11",js: "11",zjjzzmj: "11",dyje: "11",dwmjdyje1: "11",js2: "1",jzwzmj: "1",dyje1: "1",dwmjdyje2: "1"},
            // {nc: "测试",zs: "5",tdmj: "11",tddyje: "11",dwmjdyje: "11",js: "11",zjjzzmj: "11",dyje: "11",dwmjdyje1: "11",js2: "1",jzwzmj: "1",dyje1: "1",dwmjdyje2: "1"},
            // {nc: "测试",zs: "6",tdmj: "11",tddyje: "11",dwmjdyje: "11",js: "11",zjjzzmj: "11",dyje: "11",dwmjdyje1: "11",js2: "1",jzwzmj: "1",dyje1: "1",dwmjdyje2: "1"},
        ];
        table.render({
            id: 'zbTable',
            elem: '#zbTable',
            //toolbar: '#toolbarDemo',
            title: '汇总表',
            defaultToolbar: ['filter'],
            even: true,
        // limits: commonLimits,
            cols: [
                [
                    // {type: 'id', title: 'id', hide: true, rowspan:2},
                    // {type: 'checkbox', fixed: 'left', rowspan:2},
                    // {type: 'numbers', field: 'xh', title: '序号', width: 70, fixed: 'left', rowspan:2},
                //{field:'qx', title:'市、县名称',rowspan:2},
                {field:'title1', title:'土地',colspan:5},
                {field:'title2', title:'土地房屋', colspan:5},
                {field:'title3', title:'在建工程',colspan:5},
                {field:'title3', title:'林权',colspan:5},
            ],[
                {field:'ctdcount', title:'在押宗数',width:70},
                {field:'ctdcount_dy', title:'抵押宗数',width:70},
                {field:'ctdmj', title:'土地面积(亩)',width:120},
                {field:'ctddyje', title:'土地抵押金额(万元)',width:130},
                {field:'ctdavgjemj', title:'单位面积抵押金额(万元/亩)',width:130},

                {field:'zjgccount', title:'在押件数',width:70},
                {field:'zjgccount_dy', title:'抵押件数',width:70},
                {field:'zjgcmj', title:'土地房屋总面积(平方米)',width:130},
                {field:'zjgcdyje', title:'抵押金额(万元)',width:130},
                {field:'zjgcavg', title:'单位面积抵押金额(万元/平方米)',width:130},

                {field:'fdytcount', title:'在押件数',width:70},
                {field:'fdytcount_dy', title:'抵押件数',width:70},
                {field:'fdytmj', title:'在建工程总面积(平方米)',width:130},
                {field:'fdytdyje', title:'抵押金额(万元)',width:130},
                {field:'fdytavg', title:'单位面积抵押金额(万元/平方米)',width:130},

                {field:'lqcount', title:'在押件数',width:70},
                {field:'lqcount_dy', title:'抵押件数',width:70},
                {field:'lqmj', title:'林权总面积(平方米)',width:130},
                {field:'lqdyje', title:'抵押金额(万元)',width:130},
                {field:'lqavg', title:'单位面积抵押金额(万元/平方米)',width:130}
            ]],
             data: [],
            // page: false,
            parseData: function (res) {
                // 设置选中行
                if(res.content && res.content.length > 0){
                    var checkedData = layui.sessionData('checkedData');
                    res.content.forEach(function (v) {
                        $.each(checkedData, function(key, value){
                            if(key == v.id){
                                v.LAY_CHECKED = true;
                            }
                        })
                    });
                }
                return {
                    code: res.code, //解析接口状态
                    msg: res.msg, //解析提示文本
                    count: res.totalElements, //解析数据长度
                    data: res.content //解析数据列表
                }
            },
            done: function (data) {
                currentPageData = data.data;
            }
        });

        /*var zxbData = [];
        table.render({
            id: 'zxbTable',
            elem: '#zxbTable',
            //toolbar: '#toolbarDemo',
            title: '专项表',
            defaultToolbar: ['filter'],

            even: true,
            cols: [[
               // {field:'qx', title:'市、县名称', rowspan:2},
                {field:'title1', title:'工业类',colspan:5},
                {field:'title2', title:'住宅类', colspan:4}
            ],[
                {field:'gylcount', title:'宗数(件数)',width:140},
                {field:'gylmj', title:'土地抵押面积(平方米)',width:180},
                {field:'gylzj', title:'在建建筑物、构筑物抵押面积(平方米)',width:130},
                {field:'gyljg', title:'已竣工建筑物、构筑物抵押面积(平方米)',width:130},
                {field:'gyldyje', title:'抵押金额(万元)',width:130},
                {field:'zzlcount', title:'宗数(件数)',width:140},
                {field:'zzlmj', title:'在建建筑物抵押面积(平方米)',width:180},
                {field:'zzljg', title:'已竣工建筑物抵押面积(平方米)',width:130},
                {field:'zzldyje', title:'抵押金额(万元)'}
            ]],
            data: zxbData,
            //page: false,
            parseData: function (res) {
                // 设置选中行
                if(res.content && res.content.length > 0){
                    var checkedData = layui.sessionData('checkedData');
                    res.content.forEach(function (v) {
                        $.each(checkedData, function(key, value){
                            if(key == v.id){
                                v.LAY_CHECKED = true;
                            }
                        })
                    });
                }
                return {
                    code: res.code, //解析接口状态
                    msg: res.msg, //解析提示文本
                    count: res.totalElements, //解析数据长度
                    data: res.content //解析数据列表
                }
            },
            done: function (data) {
                currentPageData = data.data;
            }
        });*/

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

        /*//监听tab切换
        element.on('tab(tabFilter)', function(data){
            switch(data.index){
                case 1:
                    break;
                case 0:
                    table.resize('zxbTable');
                    $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');
                    break;
            }
        });*/

        // 监听表格操作栏按钮
        table.on('toolbar(zxbTable)', function (obj) {
            switch (obj.event) {
                case 'exportExcel':
                    exportExcel();
                    break;
            }
        });
        /*// 监听表格操作栏按钮
        table.on('toolbar(zbTable)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            switch (obj.event) {
                case 'exportExcel':
                    exportExcel();
                    break;
            }
        });*/

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
                table.reload("zbTable", {
                    url: '/realestate-inquiry-ui/hzdytj/hz/bb/list'
                    , where: obj
                    , done: function (res, curr, count) {
                        removeModal();
                        hzdata = res.data;
                    }
                });

               /* table.reload("zxbTable", {
                    url: '/realestate-inquiry-ui/hzdytj/ybb/bb/list'
                    , where: obj
                    , done: function (res, curr, count) {
                        removeModal();
                        //setHeight();
                        //reverseTableCell(reverseList);
                        table.resize('zxbTable');
                        ybbdata = res.data;
                        console.log(ybbdata);
                    }
                });*/
            } else {
                layer.msg("请输入查询条件！");
            }
            return false;
        });
    });
});

/**
 * 汇总导出
 */
function exportExcel(){
    var url = '/realestate-inquiry-ui/hzdytj/export?hz='+JSON.stringify(hzdata)+
        '&ybb='+JSON.stringify(ybbdata);
    window.open(encodeURI(url));
    // $.ajax({
    //     url: '/realestate-inquiry-ui/hzdytj/export',
    //     type: "GET",
    //     data: {hz:JSON.stringify(hzdata),ybb:JSON.stringify(ybbdata)},
    //     success: function (data) {
    //         //window.location.href = data+"model.xlsx";
    //         //window.location.href = getContextPath()+"/static/printModel/model.xlsx";
    //     }
    // });
}

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
