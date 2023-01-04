/**
 * author: 前端组
 * date: 2020-02-26
 * describe: tab下双层表头表格
*/
var $;
var hzdata = [];
var ybbdata = [];
/**
 * 页面版本
 */
var version = '';
layui.config({
    base: '../../static/lib/form-select/'
}).extend({
    formSelects: 'formSelects-v4',
    custom_tree: '../../../../realestate-inquiry-ui/static/ztree/custom_tree'
});
layui.use(['form','jquery','element','layer','table','laydate','custom_tree', 'formSelects',],function () {
    $ = layui.jquery;
    var form = layui.form,
        layer = layui.layer,
        element = layui.element,
        table = layui.table,
        laydate = layui.laydate,
        formSelects = layui.formSelects;;
    custom_tree = layui.custom_tree;

    /**
     * 初始化组织机构下拉控件
     */
    $('.org_select_show').click(function () {
        if ($('.org_select_show').text() == '选择') {
            $('.org_select_tree').css('display', 'block');
            $('.org_select_show').text('隐藏')
        } else {
            $('.org_select_show').text('选择');
            $('.org_select_tree').css('display', 'none');
        }
    });
    //下拉面板宽高位置
    (function(){
        var width = $("input[name='bmmc']").width + 40;
        $('.org_select_tree').css({'width': width, 'height':'200px', 'left': '106px', 'top':35});
    })();
    //加载树
    custom_tree.load({
        treeId: "select_tree",
        commonUrl: '/realestate-inquiry-ui/rest/v1.0/ztree/orgs/gl',
        checkboxEnabled: false,
        onClick: function(event, treeId, treeNode) {
            if ("select_tree" == treeId) {
                $("input[name=bmid]").val(treeNode.id);
                $("input[name=bmmc]").val(treeNode.name);
            }
        }
    });

    /**
     * 搜索
     * @param selName 输入框输入值
     */
    $("#deptsel").bind("keyup blur",function(){
        if(this.value!=""){
            ztreeFuzzySearch(this.value)
        }else{
            $('.menuContent .xm-select-empty').css('display', 'none');
            //初始化树
            layui.custom_tree.load({
                treeId: "select_tree",
                commonUrl: '/realestate-inquiry-ui/rest/v1.0/ztree/orgs/gl',
                checkboxEnabled: false,
                onClick: function(event, treeId, treeNode) {
                    if ("select_tree" == treeId) {
                        $("input[name=bmid]").val(treeNode.id);
                        $("input[name=bmmc]").val(treeNode.name);
                        getDataByAjax('/rest/v1.0/process/users/'+treeNode.id,'','GET',function (param) {
                            console.info(param);
                            var ryData = [];
                            param.forEach(function (v) {
                                ryData.push({"name": v.alias, "value": v.username});
                            });
                            formSelects.data('selectBjry', 'local', {
                                arr: ryData
                            });
                        });
                    }

                }
            });

        }
    });

    /**
     * ztree新增搜索方法
     *  @param name 输入值
     */
    function ztreeFuzzySearch(name){
        var zTreeObj = layui.custom_tree.getZTreeObj();
        var nameKey = zTreeObj.setting.data.key.name;

        ztreeFilter(zTreeObj, name);

        // ztree 树节点过滤
        function ztreeFilter(zTreeObj,_keywords) {
            function filterFunc(node) {
                if(!node.zAsync){
                    zTreeObj.showNode(node);
                    zTreeObj.expandNode(node, true);
                    zTreeObj.reAsyncChildNodes(node, "refresh");
                }
                if (_keywords.length == 0) {
                    zTreeObj.showNode(node);
                    return true;
                }
                if (node[nameKey] && node[nameKey].toLowerCase().indexOf(_keywords.toLowerCase())!=-1) {
                    zTreeObj.showNode(node);
                    return true;
                }
                zTreeObj.hideNode(node);
                return false;
            }

            var nodesShow = zTreeObj.getNodesByFilter(filterFunc);
            processShowNodes(nodesShow, _keywords);
        }

        // 节点展开
        function processShowNodes(nodesShow,_keywords){
            if(nodesShow && nodesShow.length>0){
                $('.menuContent .xm-select-empty').css('display', 'none');
                if(_keywords.length>0){
                    $.each(nodesShow, function(n,obj){
                        var pathOfOne = obj.getPath();
                        if(pathOfOne && pathOfOne.length>0){
                            for(var i=0;i<pathOfOne.length-1;i++){
                                zTreeObj.showNode(pathOfOne[i]);
                                zTreeObj.expandNode(pathOfOne[i],true);
                            }
                        }
                    });
                }else{
                    $('.menuContent .xm-select-empty').css('display', 'block');
                    var rootNodes = zTreeObj.getNodesByParam('level','0');
                    $.each(rootNodes,function(n,obj){
                        zTreeObj.expandNode(obj, true);
                    });
                }
            }else{
                $('.menuContent .xm-select-empty').show();
            }
        }
    }

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
            toolbar: '#toolbarDemo',
            title: '汇总表',
            defaultToolbar: ['filter'],
            even: true,
        // limits: commonLimits,
            cols: [
                [
                    // {type: 'id', title: 'id', hide: true, rowspan:2},
                    // {type: 'checkbox', fixed: 'left', rowspan:2},
                    // {type: 'numbers', field: 'xh', title: '序号', width: 70, fixed: 'left', rowspan:2},
                {field:'qx', title:'市、县名称',rowspan:2},
                {field:'title1', title:'无地上建筑物的建设用地使用权(净地)抵押',colspan:4},
                {field:'title2', title:'建设用地使用权及在建建筑物抵押', colspan:4},
                {field:'title3', title:'已竣工建筑物及建设用地使用权抵押',colspan:4}
            ],[
                {field:'ctdcount', title:'宗数',width:70},
                {field:'ctdmj', title:'土地面积(亩)',width:130},
                {field:'ctddyje', title:'土地抵押金额(万元)',width:130},
                {field:'ctdavgjemj', title:'单位面积抵押金额(万元/亩)',width:130},

                {field:'zjgccount', title:'件数',width:70},
                {field:'zjgcmj', title:'在建建筑物、构筑物总面积(平方米)',width:130},
                {field:'zjgcdyje', title:'抵押金额(万元)',width:130},
                {field:'zjgcavg', title:'单位面积抵押金额(万元/平方米)',width:130},

                {field:'fdytcount', title:'件数',width:70},
                {field:'fdytmj', title:'建筑物、构筑物总面积(平方米)',width:130},
                {field:'fdytdyje', title:'抵押金额(万元)',width:130},
                {field:'fdytavg', title:'单位面积抵押金额(万元/平方米)',width:130}
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

        var zxbData = [];
        table.render({
            id: 'zxbTable',
            elem: '#zxbTable',
            toolbar: '#toolbarDemo',
            title: '专项表',
            defaultToolbar: ['filter'],

            even: true,
            cols: [[
                {field:'qx', title:'市、县名称', rowspan:2},
                {field:'title1', title:'工业类',colspan:5},
                {field:'title2', title:'住宅类', colspan:4}
            ],[
                {field:'gylcount', title:'宗数(件数)',width:70},
                {field:'gylmj', title:'土地抵押面积(平方米)',width:130},
                {field:'gylzj', title:'在建建筑物、构筑物抵押面积(平方米)',width:130},
                {field:'gyljg', title:'已竣工建筑物、构筑物抵押面积(平方米)',width:130},
                {field:'gyldyje', title:'抵押金额(万元)',width:130},
                {field:'zzlcount', title:'宗数(件数)',width:70},
                {field:'zzlmj', title:'在建建筑物抵押面积(平方米)',width:130},
                {field:'zzljg', title:'已竣工建筑物抵押面积(平方米)',width:130},
                {field:'zzldyje', title:'抵押金额(万元)',width:130}
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
        });

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

        //监听tab切换
        element.on('tab(tabFilter)', function(data){
            switch(data.index){
                case 1:
                    break;
                case 0:
                    table.resize('zxbTable');
                    $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');
                    break;
            }
        });

        // 监听表格操作栏按钮
        table.on('toolbar(zxbTable)', function (obj) {
            switch (obj.event) {
                case 'exportExcel':
                    exportExcel();
                    break;
            }
        });
        // 监听表格操作栏按钮
        table.on('toolbar(zbTable)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            switch (obj.event) {
                case 'exportExcel':
                    exportExcel();
                    break;
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
                table.reload("zbTable", {
                    url: '/realestate-inquiry-ui/hzdytj/hz/list'
                    , where: obj
                    , done: function (res, curr, count) {
                        removeModal();
                        hzdata = res.data;
                    }
                });

                table.reload("zxbTable", {
                    url: '/realestate-inquiry-ui/hzdytj/ybb/list'
                    , where: obj
                    , done: function (res, curr, count) {
                        removeModal();
                        setHeight();
                        //reverseTableCell(reverseList);
                        table.resize('zxbTable');
                        ybbdata = res.data;
                        console.log(ybbdata);
                    }
                });
            } else {
                layer.msg("请输入查询条件！");
            }
            return false;
        });

        // 设置部门名称下拉选择的值
        $.ajax({
            url: '/realestate-inquiry-ui/bdczd/org/list',
            type: "GET",
            dataType: "json",
            success: function (data) {
                if (data) {
                    $.each(data, function (index, item) {
                        $("#bmmc").append('<option value="' + item.name + '">' + item.name + '</option>');
                    });
                    form.render('select');
                }
            }
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
