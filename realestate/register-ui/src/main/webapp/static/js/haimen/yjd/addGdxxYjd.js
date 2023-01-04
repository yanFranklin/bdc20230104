/**
 * 海门档案移交单 新增移交单页面 处理JS
 */
var yjdid = "";
layui.use(['table', 'laytpl', 'laydate', 'layer', 'form'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer,
        form = layui.form;

    // 设置字符过多，省略样式
    var reverseList = ['zl'];
    // 选中的数据集合
    var selectedData = [];


    /**
     * 加载Table数据列表
     */
    table.render({
        elem: '#yjdTable',
        id: 'yjdTable',
        toolbar: '#toolbar',
        defaultToolbar: [],
        data: [],
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        cellMinWidth: 80,
        even: true,
        limits: commonLimits,
        cols: [[
            {type: 'checkbox', fixed: 'left'},
            {field: 'xh',       title: '序号',        width: 50, type: 'numbers'},
            {field: 'slbh',     title: '受理编号',     width: 130},
            {field: 'gzldymc',  title: '流程名称',     width: 200},
            {field: 'qlr',      title: '权利人',       width: 150},
            {field: 'bdcdyh',   title: '不动产单元号',  width: 250},
            {field: 'bdcqzh',   title: '不动产权证号',  width: 220},
            {field: 'zl',       title: '坐落',         miWidth: 300},
            {field: 'djsj',     title: '登记时间',      width: 160,
                templet: function (d) {
                    return format(d.djsj);
                }
            },
            {templet: '#barAction', title: '操作', fixed: 'right', width: 120}
        ]],
        text: {
            none: '未查询到数据'
        },
        autoSort: false,
        page: true,
        limit: 100,
        limits: [100, 200, 300, 400, 500],
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

    /**
     * 表格头按钮事件
     */
    table.on('toolbar(yjdTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'newYjd':
                newYjd();
                break;
            case 'printYjd':
                printYjd(yjdid);
                break;
            case 'batchAdd':
                batchAdd(checkStatus);
                break;
            case 'batchRemove':
                batchRemove(checkStatus);
                break;
        }
    });


    /**
     * 添加已勾选
     */
    function batchAdd(data) {
        if(!data || !data.data || data.data.length == 0){
            warnMsg("请先勾选数据！");
            return false;
        }
        var result = 0;

        for(var i = 0;i<data.data.length;i++){
            var flag = addYjd(data.data[i],false);
            if(!flag){
                result++;
            }
        }
        if(result == 0){
            successMsg("添加成功！");
        }

    }

    /**
     * 移除已勾选
     */
    function batchRemove(data) {
        if(!data || !data.data || data.data.length == 0){
            warnMsg("请先勾选数据！");
            return false;
        }
        var result = 0;
        for(var i = 0;i<data.data.length;i++){
            var flag = delYjd(data.data[i],false);
            if(!flag){
                result++;
            }
        }
        if(result == 0){
            successMsg("移除成功！");
        }

    }
    /**
     * 打印移交单
     */
    function printYjd(yjdid) {
        if (yjdid == null || yjdid == '') {
            warnMsg("没有生成移交单编号！");
            return false;
        }
        var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/jjd/" + yjdid + "/xml";
        print(jjdModelUrl, dataUrl, false);

    }

    /**
     * 打开yjd打印弹窗
     * @param yjdid
     */
    function openPrintYjdDialog(yjdid){
        var submitIndex = layer.open({
            type: 1,
            title: '确认提示',
            skin: 'bdc-small-tips',
            area: ['400px'],
            content: "移交单编号已生成，是否打印移交单",
            btn: ['确定','取消'],
            btnAlign: 'c',
            yes: function(){
                printYjd(yjdid);
                //确定操作
                layer.close(submitIndex);
            },
            btn2: function(){
                layer.close(submitIndex);
            }
        });
    }

    /**
     * 生成移交单
     */
    function newYjd() {
        if(!selectedData || selectedData.length == 0) {
            warnMsg("当前无项目添加，无法生成移交单！");
            return;
        }

        var info = "以下受理编号对应项目将生成移交单，是否确认？\n"
        selectedData.forEach(function (selection) {
            info += selection + "、";
        });
        info = info.substr(0, info.length - 1);

        var submitIndex = layer.open({
            type: 1,
            title: '确认提示',
            skin: 'bdc-small-tips',
            area: ['400px'],
            content: info,
            btn: ['确定','取消'],
            btnAlign: 'c',
            yes: function(){
                generateYjd();

                //确定操作
                layer.close(submitIndex);
            },
            btn2: function(){
                //取消
            }
        });
    }

    /**
     * 正式请求后台进行生成移交单操作
     */
    function generateYjd() {
        $.ajax({
            url: "/realestate-register-ui/rest/v1.0/yjd/new",
            type: "POST",
            dataType: "json",
            data: JSON.stringify(selectedData),
            contentType: 'application/json',
            success: function (res) {
                successMsg("新增移交单成功！");
                yjdid = res.yjdid;
                openPrintYjdDialog(yjdid);
                $("#dataNum").parent().attr("disabled","disabled");
                $("#batchAdd").parent().attr("disabled","disabled");
                $("#batchRemove").parent().attr("disabled","disabled");
                selectedData = [];
                /*setTimeout(function () {
                    var index=parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                }, 1500);*/
            },
            error: function (e) {
                failMsg("新增移交单失败，请重试！");
            }
        });
    }

    /**
     * 表格行操作
     */
    table.on('tool(yjdTable)', function (obj) {
        var data = obj.data;
        if (obj.event === 'add') {
            addYjd(data,true);
        } else if (obj.event === 'del') {
            delYjd(data,true);
        }
    });

    /**
     * 添加项目到移交单
     */
    function addYjd(data,showMsg) {
        if(selectedData && selectedData.length > 0) {
            var hasSelection = false;
            selectedData.forEach(function (selection) {
               if(data.slbh == selection) {
                    hasSelection = true;
               }
            });

            if(hasSelection == true) {
                warnMsg("存在项目已添加！");
                return false;
            }
        }
        var flag = true;
        $.ajax({
            url: "/realestate-register-ui/rest/v1.0/yjd/check",
            type: "POST",
            dataType: "text",
            data: JSON.stringify(data),
            async: false,
            contentType: 'application/json',
            success: function (res) {
                if(res && "yyj" == res) {
                    warnMsg("存在项目已经移交过，不可再次移交！");
                    flag = false;
                } else {
                    selectedData.push(data.slbh);
                    $("#dataNum").html('（' + selectedData.length + '）');
                    if(showMsg){
                        successMsg("添加项目成功！");
                    }
                }
            },
            error: function (e) {
                flag = false;
                failMsg("添加失败，请重试！");
            }
        });
        return flag;
    }

    /**
     * 移除移交单
     */
    function delYjd(data,showMsg) {
        if(!selectedData || selectedData.length == 0) {
            warnMsg("存在项目未添加，无法移除！");
            return false;
        }

        if(selectedData && selectedData.length > 0) {
            var hasSelection = false;
            for(var index = 0; index < selectedData.length; index++) {
                if(selectedData[index] == data.slbh) {
                    hasSelection = true;
                    selectedData.splice(index, 1);
                    if(showMsg){
                        successMsg("移除已添加项目成功！");
                    }
                    $("#dataNum").html('（' + selectedData.length + '）');
                }
            }

            if(hasSelection == false) {
                warnMsg("存在项目未添加，无法移除！");
                return false;
            }
        }
        return true;
    }

    document.onkeydown = function (event) {
        var code = event.keyCode;
        if (code == 13) {
            $('#search').click();
        }
    }

    /**
     * 点击查询
     */
    $('#search').on('click', function () {

        $("#dataNum").html("");
        yjdid = "";
        // 获取查询内容
        var count = 0;
        var obj = {};
        $(".search").each(function (i) {
            var value = $(this).val();
            if (!isNullOrEmpty(value)) {
                var name = $(this).attr('name');
                obj[name] = value;
                count += 1;
            }
        });

        if (0 == count) {
            warnMsg("请输入查询条件！");
            return false;
        }

        var sfcz = $('input[name="sfcz"]:checked').val();
        if(isNotBlank(sfcz)){
            obj.sfcz = sfcz;
        }

        addModel();
        // 重新请求

        table.reload("yjdTable", {
            url:  "/realestate-register-ui/rest/v1.0/yjd/xmxx"
            , where: obj
            , page: {
                curr: 1  //重新从第 1 页开始
            },
            done: function (res, curr, count) {
                removeModel();
                setTableHeight(131);
                reverseTableCell(reverseList);

                $("#dataNum").html('（' + selectedData.length + '）');

                var curSlbh = $("#slbh").val();
                if(curSlbh != "" && curSlbh.substring(curSlbh.length-1,curSlbh.length) != ","){
                    $("#slbh").val(curSlbh+",");
                }
                $("#slbh").focus();
            }
        });
        return false;
    });
});