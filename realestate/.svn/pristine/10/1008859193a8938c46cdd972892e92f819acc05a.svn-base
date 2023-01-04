/**
 * @author <a href ="mailto:wutao2@gtmap.cn">wutao</a>
 * @description 证书、证明统计 js
 */
layui.extend({
    formSelects: '../../static/lib/form-select/formSelects-v4'
});
var BASE_URL = "/realestate-inquiry-ui/rest/v1.0";
var zdList = getMulZdList("fwyt");
var zdQllxList = getMulZdList("qllx");
var bdcGgDTO = {"bdcGgywsjDOList": [], "ggid": ""};
var ggid = getQueryString("ggid");
var gglx = getQueryString("gglx");
layui.use(['form', 'jquery', 'laydate', 'element', 'table','formSelects'], function () {
    var $ = layui.jquery,
        table = layui.table,
        form = layui.form,
        laydate = layui.laydate,
        element = layui.element;
    formSelects = layui.formSelects;

    bdcGgDTO["ggid"] = ggid;
    // 获取页面表单标识，用于权限控制
    var moduleCode = $.getUrlParam('moduleCode');
    if(moduleCode){
        setElementAttrByModuleAuthority(moduleCode);
    }

    // 当前页数据
    var currentPageData = new Array();
    var wpage,     //当前页码
        currcount; //每页行数

    //4.定义table的cols，显示默认表格
    renderTable();

    function renderTable(){
        var cols = [
            {type: 'checkbox', width: 80, fixed: 'left'},
            {field: 'qlr', minWidth: 110, title: '权利人'},

            {
                field: 'qllx', minWidth: 110, title: '权利类型',
                templet: function (d) {
                    return convertZdDmToMc("qllx", d.qllx, "zdQllxList");
                }
            },
            {field: 'ywr', minWidth: 110, title: '义务人'},
            {field: 'ycqzh', minWidth: 100, title: '原产权证号'},

            {field: 'zl', minWidth: 400, title: '不动产坐落'},
            {field: 'bdcdyh', minWidth: 100, title: '不动产单元号'},
            {field: 'bdcqzh', minWidth: 100, title: '不动产权证号'},
            {field: 'qxdm', minWidth: 100, title: '区县代码'},
            {field: 'zdzhmj', minWidth: 160, title: '面积'},
            {
                field: 'dzwyt', minWidth: 300, title: '用途',
                templet: function (d) {
                    return convertZdDmToMc("fwyt", d.dzwyt, "zdList");
                }
            },
            {field: 'bz', minWidth: 170, title: '备注'}
        ];

        table.render({
            elem: '#linkProcessTable',
            title: '关联项目统计',
            toolbar: '#toolbarDemo',
            defaultToolbar: ['filter'],
            request: {
                limitName: 'size'//每页数据量的参数名，默认：limit

            },
            even: true,
            cols: [cols],
            data: [],
            page: true,
            limits: [10, 50, 100, 200, 500],
            parseData: function (res) {
                return {
                    "code": res.code, //解析接口状态
                    "msg": res.msg, //解析提示文本
                    "count": res.totalElements, //解析数据长度
                    "data": res.content //解析数据列表
                };
            },
            done: function () {
                setHeight();
                if(moduleCode){
                    setElementAttrByModuleAuthority(moduleCode);
                }
            }
        });

        // 监听表格操作栏按钮
        table.on('toolbar(linkProcessFilter)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            switch (obj.event) {
                case 'relate':
                    relate(checkStatus.data, "checked");
                    break;
            }
        });
    }

    // 点击查询
    $('#search').on('click', function () {
        search();
    });


    function relate(d, type){
        var selectedNum = d.length;
        if (selectedNum == 0) {
            layer.msg('请选择数据进行操作！');
            return false;
        }
        bdcGgDTO["bdcGgywsjDOList"] = d;
        console.log(d);
        //作废公告带入选择数据的上一手信息
        if(gglx == 8){
            bdcGgDTO["bdcGgywsjDOList"] = "";
            var xmids = [];
            for (var i = 0; i < d.length; i++) {
                var xmid = d[i].xmid;
                xmids.push(xmid);
            }

            $.ajax({
                url: '/realestate-inquiry-ui/rest/v1.0/ggxx/getZfggYwsj?xmids='+xmids,
                type: "GET",
                data: {},
                contentType: 'application/json',
                dataType: "text",
                async:false,
                success: function (data) {
                    if(!isNullOrEmpty(data)){
                        var jsonObj = JSON.parse(data);
                        bdcGgDTO["bdcGgywsjDOList"] = jsonObj;
                    }else{
                        layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">获取上一手信息为空');
                    }
                },
                error: function (data) {
                    delAjaxErrorMsg(data);
                }
            })
        }


        bdcGgDTO["gglx"] = gglx;
        bdcGgDTO["sfsc"] = false;
        $.ajax({
            url: '/realestate-inquiry-ui/rest/v1.0/ggxx/saveYwsj',
            type: "POST",
            data: JSON.stringify(bdcGgDTO),
            contentType: 'application/json',
            dataType: "text",
            success: function (data) {
                if (!isNullOrEmpty(data)) {
                    layer.msg('<img src="../../static/lib/bdcui/images/success-small.png" alt="">关联成功');
                } else {
                    layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">关联失败');
                }
                var currentindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                parent.layer.close(currentindex); //再执行关闭
                window.parent.loadGg(ggid)
            },
            error: function (data) {
                delAjaxErrorMsg(data);
            }
        })

    }

    // 查询处理逻辑
    function search() {
        // 判断必填条件
        var count = 0;
        $(".required").each(function (i) {
            if (!isNullOrEmpty($(this).val())) {
                count += 1;
            }
        });
        if (0 == count) {
            warnMsg(" 请输入必要查询条件，例如权利人名称");
            return false;
        }
        // 获取查询参数
        var obj = {};
        $(".search").each(function (i) {
            var name = $(this).attr('name');
            var value = $(this).val();
            obj[name] = value;
        });
        // 重新请求
        table.reload("linkProcessTable", {
            url: "/realestate-inquiry-ui/rest/v1.0/ggxx/listLinkProcess"
            , where: obj
            , page: {
                curr: 1, //重新从第 1 页开始
                limits: [10, 50, 100, 200, 500]
            }
            , done: function (res, curr, count) {
                currentPageData = res.data;
                wpage = curr;//当前页码
                currcount = res.data.length;//每页行数
                removeModal();
                setHeight();
            }
        });
    }

});



$(function () {
    //绑定回车键
    $(document).keydown(function (event) {
        if (event.keyCode == 13) {
            $("#search").click();
        }
    });
});

function uniqueArray(data){
    if (data.length == 0) {
        return [];
    }
    var temp = {};
    var newArray = [];
    for (var i = 0; i < data.length; i++) {
        if (!temp[data[i].id]) {
            newArray.push(data[i]);
            temp[data[i].id] = true;
        }
    }
    return newArray;
}

function getMultiSelectVal(selectorName,type){
    var selectArr = formSelects.value(selectorName);
    var selects = "";
    if(selectArr.length!=0){
        var selectList = [];
        $.each(selectArr, function (index, select) {
            if (type == "name") {
                selectList.push(select.name);
            } else {
                selectList.push(select.value);
            }
        });
        selects = selectList.join(",");
    }
    return selects;
}

//获取URL参数
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (null !== r) {
        if (decodeURI(r[2]) !== "null") {
            return decodeURI(r[2]);
        } else {
            return null
        }
    }
    return null;
}
