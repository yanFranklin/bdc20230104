/**
 * author: <a href="mailto:yaoyi@gtmap.cn>yaoyi</a>
 * version 1.0.0  2022/04/25
 * describe: 日志查询-综合查询日志模块
 */
layui.config({
    base: '/realestate-inquiry-ui/static/ztree/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v3',
    custom_tree: 'custom_tree'
});
var zdList = [];
// 分页重置查询参数
var paramD ={};
var cols = [];
// 列表需要展示的列名称
var showElement = {
    ZHCXLOG : ['alias','ipaddress','viewTypeName','cxtj','cxjg','time'],
    ZHCXLSGX : ['alias','remoteAddr','viewTypeName','bdcdyh','djyy','ghyt','ajzt','qllx',"djxl","zdzhyt","zdzhmj","qjgldm","fwbh",
        "qszt","slbh","qlrzjh","qlrmc","zl","bdcqzh","gzlslid","xmly","zslx","bdclx","zhlsh",'time'],
    ZHCXLPB : ['alias','remoteAddr','viewTypeName','bdcdyh','djyy','ghyt','ajzt','qllx',"djxl","zdzhyt","zdzhmj","qjgldm","fwbh",
        "qszt","slbh","qlrzjh","qlrmc","ywrmc","ywrzjh","zl","bdcqzh","gzlslid","xmly","zslx","bdclx","zhlsh",'time'],
    ZHCXDJB: ['alias','remoteAddr','viewTypeName','bdcdyh','djyy','ghyt','ajzt','qllx',"djxl","zdzhyt","zdzhmj","qjgldm","fwbh",
        "qszt","slbh","qlrzjh","qlrmc","ywrmc","ywrzjh","zl","bdcqzh","gzlslid","xmly","zslx","bdclx","zhlsh",'time'],
    ZHCXDADY: ['alias','remoteAddr','viewTypeName','bdcdyh','djyy','ghyt','ajzt','qllx',"djxl","zdzhyt","zdzhmj","qjgldm","fwbh",
        "qszt","slbh","qlrzjh","qlrmc","ywrmc","ywrzjh","zl","bdcqzh","gzlslid","xmly","zslx","bdclx","zhlsh",'time'],
    ZHCXTXDW: ['alias','remoteAddr','viewTypeName','bdcdyh','djyy','ghyt','ajzt','qllx',"djxl","zdzhyt","zdzhmj","qjgldm","fwbh",
        "qszt","slbh","qlrzjh","qlrmc","ywrmc","ywrzjh","zl","bdcqzh","gzlslid","xmly","zslx","bdclx","zhlsh",'time'],
    ZHCXDJDCB: ['alias','remoteAddr','viewTypeName','bdcdyh','djyy','ghyt','ajzt','qllx',"djxl","zdzhyt","zdzhmj","qjgldm","fwbh",
        "qszt","slbh","qlrzjh","qlrmc","ywrmc","ywrzjh","zl","bdcqzh","gzlslid","xmly","zslx","bdclx","zhlsh",'time'],
};

var defaultCols = [
    {type: 'checkbox', fixed: 'left'},
    {field: 'xh',  title: '序号',width: 40, type: 'numbers'},
    {field: 'alias', title: '姓名',width: 100, hide:true, templet: function(d){ return fmtData(d.alias);}},
    {field: 'ipaddress', title: '操作IP',width: 150, hide:true},
    {field: 'remoteAddr', title: '操作IP',width: 150, hide:true},
    {field: 'viewTypeName', title: '日志类型',width: 150, hide:true},
    {field: 'cxtj', title: '查询条件', width: 350, hide:true},
    {field: 'cxjg', title: '查询结果', width: 350, hide:true},
    {field: 'bdcdyh', title: '不动产单元号', width: 250, hide:true},
    {field: 'djyy', title: '登记原因', width: 120, hide:true},
    {field: 'ghyt', title: '规划用途', width: 100, hide:true, templet: function (d) {return convertZdDmToMc("fwyt", d.ghyt, "zdList");}},
    {field: 'ajzt', title: '案件状态', width: 100, hide:true, templet: function (d) {return convertZdDmToMc("ajzt", d.ajzt, "zdList");}},
    {field: 'qllx', title: '权利类型', width: 220, hide:true, templet: function (d) {return convertZdDmToMc("qllx", d.qllx, "zdList");}},
    {field: 'djxl', title: '登记小类', width: 240, hide:true, templet: function (d) {return convertZdDmToMc("djxl", d.djxl, "zdList");}},
    {field: 'zdzhyt', title: '宗地宗海用途', width: 120, hide:true, templet: function (d) {return convertZdDmToMc("tdyt", d.zdzhyt, "zdList");}},
    {field: 'zdzhmj', title: '宗地宗海面积', width: 120, hide:true},
    {field: 'qjgldm', title: '权籍管理代码', width: 120, hide:true},
    {field: 'fwbh', title: '房屋编号', width: 100, hide:true},
    {field: 'qszt', title: '权属状态', width: 100, hide:true, templet: function (d) {return convertZdDmToMc("qszt", d.qszt, "zdList");}},
    {field: 'slbh', title: '受理编号', width: 150, hide:true},
    {field: 'qlrzjh', title: '权利人证件号', width: 120, hide:true},
    {field: 'qlrmc', title: '权利人名称', width: 120, hide:true},
    {field: 'ywrzjh', title: '义务人证件号', width: 120, hide:true},
    {field: 'ywrmc', title: '义务人名称', width: 120, hide:true},
    {field: 'zl', title: '坐落', width: 280, hide:true},
    {field: 'bdcqzh', title: '不动产权证号', width: 250, hide:true},
    {field: 'gzlslid', title: '工作流实例ID', width: 130, hide:true},
    {field: 'xmly', title: '项目来源', width: 120, hide:true, templet: function (d) {return convertZdDmToMc("xmly", d.xmly, "zdList");}},
    {field: 'zslx', title: '证书类型', width: 100, hide:true, templet: function (d) {return convertZdDmToMc("zslx", d.ghyt, "zdList");}},
    {field: 'bdclx', title: '不动产类型', width: 100, hide:true, templet: function (d) {return convertZdDmToMc("bdclx", d.ghyt, "zdList");}},
    {field: 'zhlsh', title: '证号流水号', width: 120, hide:true},
    {field: 'fj', title: '附记', width: 180, hide:true},
    {field: 'time', title: '操作时间', width: 170, fixed: 'right', hide:true,templet:function(d){return formatTime(d.time);}},
    {field: 'cz', title: '操作', width: 70, templet: '#barDemo', align: "center", fixed:"right"},
];

var laydate,form,laytpl;
layui.use(['form', 'jquery', 'element', 'table', 'layer','laydate', 'laytpl', 'custom_tree'], function () {
    var layer = layui.layer;
    var table = layui.table;
    laytpl = layui.laytpl;
    laydate = layui.laydate;
    form = layui.form;
    custom_tree = layui.custom_tree;
    $('.org_select_show').click(function () {
        if($('.org_select_show').text() == '选择'){
            $('.org_select_tree').css('display','block');
            $('.org_select_show').text('隐藏')
        }else{
            $('.org_select_show').text('选择');
            $('.org_select_tree').css('display','none');
        }
    });
    var width = $("input[name='lqbm']").width + 40;
    $('.org_select_tree').css({'width': width, 'height':'200px', 'left':60, 'top':40});
    //加载树
    custom_tree.load({
        treeId: "select_tree",
        commonUrl: '/realestate-inquiry-ui/rest/v1.0/ztree/orgs',
        checkboxEnabled: false,
        onClick: function(event, treeId, treeNode) {
            if ("select_tree" == treeId) {
                $("input[name=organization]").val(treeNode.name);
                $("input[name=orgId]").val(treeNode.id);
            }
        }
    });
    $("#deptsel").bind("keyup blur",function(){
        if(this.value!=""){
            ztreeFuzzySearch(this.value)
        }else{
            $('.menuContent .xm-select-empty').css('display', 'none');
            //初始化树
            layui.custom_tree.load({
                treeId: "select_tree",
                commonUrl: '/realestate-inquiry-ui/rest/v1.0/ztree/orgs',
                checkboxEnabled: false,
                onClick: function(event, treeId, treeNode) {
                    if ("select_tree" == treeId) {
                        $("input[name=organization]").val(treeNode.name);
                        $("input[name=orgId]").val(treeNode.id);
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
        var zTreeObj =$.fn.zTree.getZTreeObj("select_tree");
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

    layui.sessionData('checkedData',null);

    // 页面下拉等元素初始化
    pageInit();

    // 加载Table
    table.render({
        elem: '#logTable',
        toolbar: '#toolbarDemo',
        title: '综合查询日志查询列表',
        defaultToolbar: ['filter'],
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        even: true,
        cols: [defaultCols],
        data: [],
        page: true,
        limits: [10, 50, 100, 200, 500],
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
        done: function () {
            setHeight();
        }
    });

    // 监听表单提交事件
    form.on('submit(search)', function (data) {
        if($('#eventName').val() == ""){
            layer.msg('<img src="../../static/lib/bdcui/images/warn-small.png" alt="">' + "请选择日志类型");
            return;
        }
        if (data.field) {
            addModel();
            paramD = {};
            $(".search").each(function (i) {
                var value = $(this).val();
                var name = $(this).attr('name');
                if(!isNullOrEmpty(value)){
                    paramD[name] = value;
                }
            });

            if(paramD["eventName"] == "ZHCXLOG"){
                paramD["organization"] = $("input[name='organization']").val();
            }else{
                paramD["orgId"] = $("input[name='orgId']").val();
            }

            paramD = LogListCommon.handlerQueryParam(paramD);
            // 重新请求
            table.reload("logTable", {
                url: '/realestate-inquiry-ui/log/v2/list',
                where: paramD,
                page: {
                    curr: 1  //重新从第 1 页开始
                },
                cols: [cols],
                done: function (res, curr, count) {
                    currentPageData = res.data;
                    removeModal();
                    setHeight();
                    table.resize('logTable');
                }
            });
            // 查询完成后，清除缓存选择数据
            layui.sessionData("checkedData", null);
        } else {
            layer.msg("请输入查询条件！");
        }
        return false;
    });

    // 日志类型下拉选择事件
    form.on('select(eventName)', function(data){
        var className = $(data.elem).find("option:selected").attr("class");
        if(isNotBlank(className)){
            var getTpl = null;
            cols = LogListCommon.showColsByConfig(defaultCols, showElement[data.value]);
            if(data.value == "ZHCXLOG"){
                getTpl = zhcxSearchTpl.innerHTML;
                $("#seniorSearch").hide();
            }else{
                getTpl = zhcxDetailsSearchTpl.innerHTML;
                $("#seniorSearch").show();
            }

            if(null != getTpl){
                var data = {
                    zdList : zdList,
                    currentTime : Format(formatDate(new Date()), "yyyy-MM-dd"),
                    event: data.value,
                    cols: showElement[data.value],
                }
                laytpl(getTpl).render(data, function (html) {
                    $("#zhcx-search-content").html(html);
                });

                form.render();

                LogListCommon.initSearchElement();

                // 设置搜索框区域的高度
                console.info($('.bdc-search-content').height());
                $('.bdc-percentage-container').css('padding-top',$('.bdc-search-content').height());
                setHeight();
            }
        }
    });

    // 监听表格操作栏按钮
    table.on('toolbar(logTable)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id);
        switch(obj.event){
            case 'exportExcel':
                exportExcel(checkStatus.data, obj.config.cols[0], "checked");
                break;
            case 'exportAllExcel':
                exportAllExcel(checkStatus.data, obj.config);
                break;
        }
    });

    //监听工具条
    table.on('tool(logTable)', function (obj) {
        var data = obj.data;
        if (obj.event === 'xq') {
            console.info(data);
            layer.open({
                type: 2,
                title: '详情',
                area: ["640px","600px"],
                shadeClose: true,
                content: './logxq.html',
                success: function(layero, index){
                    var iframeWin = window[layero.find('iframe')[0]['name']];
                    iframeWin.loadXqData(data, zdList);
                },
                yes: function(index, layero){
                    layer.close(index);
                }
            });
        }
    });

    // 监听复选框事件，缓存选中的行数据
    table.on('checkbox(logTable)', function(obj){
        // 获取选中或者取消的数据
        var data = obj.type == 'one' ? [obj.data] : currentPageData;

        $.each(data, function(i, v) {
            var keyT = v.id;
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

    //点击高级查询
    $('#seniorSearch').on('click', function () {
        $('.pf-senior-show').toggleClass('bdc-hide');
        $(this).parent().toggleClass('bdc-button-box-four');
        $('.bdc-percentage-container').css('padding-top',$('.bdc-search-content').height());
        setHeight();
    });

});

// 页面下拉参数初始化
function pageInit(){
    // 获取字典项内容
    zdList = getMulZdList('fwyt,ajzt,qllx,djxl,qszt,xmly,zslx,bdclx,tdyt,eszdmc');

    cols = LogListCommon.showColsByConfig(defaultCols, showElement["ZHCXLOG"]);
    var getTpl = zhcxSearchTpl.innerHTML
    var data = {
        zdList : zdList,
        currentTime : Format(formatDate(new Date()), "yyyy-MM-dd"),
        event: "ZHCXLOG",
        cols: showElement["ZHCXLOG"],
    }
    laytpl(getTpl).render(data, function (html) {
        $("#zhcx-search-content").html(html);
    });

    form.render();

    LogListCommon.initElement();
    LogListCommon.initSearchElement();
}

/**
 * 导出Excel通过隐藏form提交方式，避免ajax方式不支持下载文件
 * @date 2019.05.24 14:45
 * @author zhuyong
 * @return
 */
function exportExcel(d, cols, type){
    var checkedData = layui.sessionData('checkedData');
    if($.isEmptyObject(checkedData) && type == "checked"){
        layer.alert("请选择需要导出Excel的数据行！", {title: '提示'});
        return;
    }
    // 标题
    var showColsTitle = [];
    // 列内容
    var showColsField = [];
    // 列宽
    var showColsWidth = [];
    for(var index in cols){
        if(cols[index].hide == false && cols[index].type != 'checkbox' && !cols[index].toolbar && cols[index].field != 'cz'){
            showColsTitle.push(cols[index].title);
            showColsField.push(cols[index].field);
            if(cols[index].width > 0){
                showColsWidth.push(parseInt(cols[index].width / 100 * 15));
            }else if(cols[index].minWidth > 0){
                showColsWidth.push(parseInt(cols[index].minWidth / 100 * 15));
            }else{
                showColsWidth.push(200 / 100 * 15);
            }
        }
    }

    var data = new Array();
    if(type == "checked"){
        $.each(checkedData, function(key, value){
            data.push(value);
        })
    }else{
        data = d;
    }

    for(var i = 0; i < data.length; i++){
        data[i].xh   = i + 1;
        data[i].time = formatTime(data[i].time),
        data[i].event = tToD[data[i].event];
        if(!data[i].viewTypeName){
            var str = $('select[name="eventName"]').find('option:selected').text();
            if(str!= "登录" && str!= "登出"){
                str+="流程"
            }else{
                str+="系统"
            }
            data[i].viewTypeName = str;
        }
    }
    // 设置Excel基本信息
    $("#fileName").val('日志查询');
    $("#sheetName").val('统计表');
    $("#cellTitle").val(showColsTitle);
    $("#cellWidth").val(showColsWidth);
    $("#cellKey").val(showColsField);
    $("#data").val(JSON.stringify(data));
    $("#form").submit();
}

/**
 * 导出Excel通过隐藏form提交方式，避免ajax方式不支持下载文件
 * @date 2019.05.25 14:45
 * @author chenyucheng
 * @return
 */
function exportAllExcel(data, obj){
    var cols = obj.cols[0]
    var url = obj.url;
    var paramData = obj.where;
    paramData["dctspz"] = true;
    $.ajax({
        url: url,
        dataType: "json",
        data: paramData,
        success: function (data) {
            if(data.code == 0){//查询成功
                exportExcel(data.content, cols, "all");
            }
        }
    });
    paramD["dctspz"]="";
}
