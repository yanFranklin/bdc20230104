/**
 * describe: 统计监管人员部门JS
 */
var reverseList = ['zl'];
/**
 * 页面版本
 */
var version = '';
// 组织树多选值
var OrgTreeCheck = new Map();
layui.config({
    base: '../../static/lib/form-select/'
}).extend({
    formSelects: 'formSelects-v4',
    custom_tree: '../../../../realestate-inquiry-ui/static/ztree/custom_tree'
});
layui.use(['jquery','form','table','laydate','formSelects','custom_tree'], function () {
    //获取字典
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        laydate = layui.laydate,
        formSelects = layui.formSelects;
    custom_tree = layui.custom_tree;

    // 当前页数据
    var currentPageData = [];

    layui.sessionData('checkedDataLc', null);

    version=$('#version').val();
    if(version=='nantong') {
        // 获取部门下拉框数据
        getTreeData('dept_treetjjd', 'selectedtjjdDeptNameShow', 'selectedtjjdDeptName', "/rest/v1.0/process/dept/allnew");
    }

    /**
     * 初始化组织机构下拉控件
     */
    $('.org_select_showtjjd').click(function () {
        if($('.org_select_showtjjd').text() == '选择'){
            $('.org_select_tree').css('display','block');
            $('.org_select_showtjjd').text('隐藏')
        }else{
            $('.org_select_showtjjd').text('选择');
            $('.org_select_tree').css('display','none');
        }
    });
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
        var width = $('#selectedtjjdDeptNameShow').width + 40;
        $('.org_select_tree').css({'width': width, 'height':'200px', 'left':-66, 'top':35});
    })();
    //下拉面板宽高位置
    (function () {
        $('.org_select_tree').css({'width': '260px', 'height': '200px', 'left': 60, 'top': 40});
    })();

    //加载树
    custom_tree.load({
        treeId: "select_tree",
        commonUrl: '/realestate-inquiry-ui/rest/v1.0/ztree/orgs',
        checkboxEnabled: true,
        onCheck: onCheck
    });
    /**
     * ztree新增方法
     * 全选
     */
    $('#all').click(function () {
        var zTreeObj = $.fn.zTree.getZTreeObj("select_tree");//得到tree对象
        var node = zTreeObj.getNodes();//得到全部节点
        var nodes = zTreeObj.transformToArray(node);//全部节点转换成数组array
        var checkNode = zTreeObj.getCheckedNodes().length;//得到被选中的节点数量
        if (checkNode < nodes.length) {//已选中的节点数小于总数 - 全选
            $('#all').prop('checked', true)
            zTreeObj.checkAllNodes(true);//给全部节点设置为true
        } else {
            zTreeObj.checkAllNodes(false);//给全部节点设置为false
            OrgTreeCheck=new Map();
        }
        var checked = zTreeObj.getCheckedNodes(true);//得到选中的节点
        var checkeds = zTreeObj.transformToArray(checked); //转变为数组
        //判断点击前是否已有选项
        if(checkeds.length>0){
            for( var i=0;i<checkeds.length;i++){
                OrgTreeCheck.set(checkeds[i].id, {id: checkeds[i].id,name: checkeds[i].name});
            }

        }
        concat(OrgTreeCheck, "select_tree")
    });
    /**
     * ztree新增方法
     * 反选
     */
    $("#unall").click(function (e) {
        var zTreeObj = $.fn.zTree.getZTreeObj("select_tree"); //ztree对象

        var checked = zTreeObj.getCheckedNodes(true);//得到选中的节点
        var checkeds = zTreeObj.transformToArray(checked); //转变为数组
        var checkNode = zTreeObj.getCheckedNodes(true).length;//选中的节点数量

        var node = zTreeObj.getNodes();//全部节点
        var nodes = zTreeObj.transformToArray(node);//全部节点数量

        if (checkNode < nodes.length) {//已选中的节点数小于总数 - 全选
            //判断点击前是否已有选项
            if(checkeds.length>0){
                for( var i=0;i<checkeds.length;i++){
                    OrgTreeCheck.delete(checkeds[i].id, {id: checkeds[i].id, code: checkeds[i].code, name: checkeds[i].name});
                }

            }
            zTreeObj.checkAllNodes(true);//给所有的都设置为true
            $.each(checkeds, function (index, node) {//之前选中的节点为false
                zTreeObj.checkNode(node, false, false);
            });

        } else {
            zTreeObj.checkAllNodes(false);//否则所有的都设置为false
            OrgTreeCheck=new Map();
        }
        var changechecked = zTreeObj.getCheckedNodes(true);//得到选中的节点
        var newcheckeds = zTreeObj.transformToArray(changechecked); //转变为数组
        //判断点击后
        if(newcheckeds.length>0){
            for( var i=0;i<newcheckeds.length;i++){
                OrgTreeCheck.set(newcheckeds[i].id, {id: newcheckeds[i].id, code: newcheckeds[i].code, name: newcheckeds[i].name});
            }

        }
        concat(OrgTreeCheck, "select_tree")
    });

    /**
     * 清空
     */
    $('#empty').on('click', function (e) {
        var zTreeObj = $.fn.zTree.getZTreeObj("select_tree");//ztree对象
        zTreeObj.checkAllNodes(false);//设置全部为false,不选中节点
        OrgTreeCheck=new Map();
        formSelects.data('selectBjry', 'local', {
            arr: []
        });
        $("input[name='bmmc']").val("");//清空input值
        $("input[name='djbmdm']").val("");//清空input值
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
            //加载树
            custom_tree.load({
                treeId: "select_tree",
                commonUrl: '/realestate-inquiry-ui/rest/v1.0/ztree/orgs',
                checkboxEnabled: true,
                onCheck: onCheck
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


    /**
     * onCheck 用于捕获 checkbox 被勾选 或 取消勾选的事件回调函数
     * @param e 标准的js event对象
     * @param treeId 对应 zTree 的 treeId，便于用户操控
     * @param treeNode 被勾选 或 取消勾选的节点 JSON 数据对象
     */
    function onCheck(e, treeId, treeNode) {

        if(treeNode.checked){
            OrgTreeCheck.set(treeNode.id, {id: treeNode.id, code: treeNode.code, name: treeNode.name});
        }else{
            OrgTreeCheck.delete(treeNode.id);
        }
        concat(OrgTreeCheck, treeId)
    }
    /**
     * concat用于拼接获取的id和name值获参
     * @param OrgTreeCheck 为选中的节点
     *  @param treeId 对应 zTree 的 treeId
     */
    function concat(OrgTreeCheck, treeId) {
        var treeDiv = $("#" + treeId).parents(".layui-inline");
        var checkedOrgName = [], checkedOrgid = [];
        if (OrgTreeCheck.size > 0) {
            OrgTreeCheck.forEach(function (value, key) {
                checkedOrgName.push(value.name);
                checkedOrgid.push(value.id);
            });
            $(treeDiv).find("input[name=bmmc]").val(checkedOrgName.join(","));
            $(treeDiv).find("input[name=djbmdm]").val(checkedOrgid.join(","));
            //获取当前部门下的人员
            var ryData = []
            $.ajax({
                type: "POST",
                url:  getContextPath() +"/rest/v1.0/process/users",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: JSON.stringify(checkedOrgid),
                success: function (data) {
                    data.forEach(function (v) {
                        ryData.push({"name": v.alias, "value": v.username});
                    });
                    formSelects.data('selectBjry', 'local', {
                        arr: ryData
                    });
                }
            });

        } else {
            $(treeDiv).find("input[name=bmmc]").val("");
            $(treeDiv).find("input[name=djbmdm]").val("");
        }

    }

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
        //1.默认渲染退回节点名称
        getDataByAjax('/rest/v1.0/process/node/config','','GET',function (data) {
            console.info(data);
            var jdmcData = [];
            data.forEach(function (v) {
                jdmcData.push({"name": v, "value": v});
            });
            formSelects.data('selectJdmc', 'local', {
                arr: jdmcData
            });
        });
        //2.默认渲染部门名称，选中名称后，根据名称渲染办理人员 采用树结构
        // getDataByAjax('/rest/v1.0/process/listOrgs','','GET',function (data) {
        //     var bmData = [];
        //     data.forEach(function (v) {
        //         bmData.push({"name": v.name, "value": v.id});
        //     });
        //     formSelects.data('selectBmmc', 'local', {
        //         arr: bmData
        //     })
        // });
        // formSelects.on('selectBmmc', function(id, vals, val){
        //     getDataByAjax('/rest/v1.0/process/users/'+val.value,'','GET',function (param) {
        //         console.info(param);
        //         var ryData = [];
        //         param.forEach(function (v) {
        //             ryData.push({"name": v.alias, "value": v.username});
        //         });
        //         formSelects.data('selectBjry', 'local', {
        //             arr: ryData
        //         });
        //     });
        // });
        var rymcSelList=[]
        $.ajax({
            url: '/realestate-inquiry-ui/bdczd/user/list',
            type: "GET",
            dataType: "json",
            success: function (data) {
                if (data) {
                    $.each(data, function (index, item) {
                        $('#rymc').append('<option value="' + item.alias + '">' + item.alias + '</option>');
                        ;
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
        //3.渲染起止时间，限制截止时间大于开始时间
        lay('.test-item').each(function(){
            //初始化日期控件
            var startDate = laydate.render({
                elem: '#kssj',
                type: 'datetime',
                trigger: 'click',
                done: function(value,date){
                    //选择的结束时间大
                    if($('#jzsj').val() != '' && !completeDate($('#jzsj').val(),value)){
                        $('#jzsj').val('');
                        $('.laydate-disabled.layui-this').removeClass('layui-this');
                        layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
                    }
                    endDate.config.min ={
                        year:date.year,
                        month:date.month-1,
                        date: date.date
                    }
                }
            });
            var endDate = laydate.render({
                elem: '#jzsj',
                type: 'datetime',
                trigger: 'click'
            });

        });

        //4.定义table的cols，显示默认表格
        renderTable();

        //5.查询 按钮
        $('#search').on('click', function () {
            search();
        });
        //6.回车查询
        $('.bdc-pjtj').on('keydown', function (event) {
            if (event.keyCode == 13) {
                search();
            }
        });

        //7. 输入框删除图标
        if(!(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion.match(/9./i)=="9."))
        {
            //监听input点击
            $('.layui-form-item .layui-input-inline').on('click','.layui-icon-close',function () {
                $(this).siblings('.layui-input').val('');
                $(this).siblings().find('.layui-input').val('');
            });

            $('.layui-form-item .layui-input-inline .layui-input').on('focus',function () {
                $(this).siblings('.layui-icon-close').removeClass('bdc-hide');
                $(this).parents('.layui-input-inline').find('.layui-icon-close').removeClass('bdc-hide');
                $(this).siblings('.layui-edge').addClass('bdc-hide');
            }).on('blur',function () {
                var $this = $(this);
                setTimeout(function () {
                    $this.siblings('.layui-icon-close').addClass('bdc-hide');
                    $this.parents('.layui-input-inline').find('.layui-icon-close').addClass('bdc-hide');
                    $this.siblings('.layui-edge').removeClass('bdc-hide');
                },150)
            });
        }

        // 监听表格操作栏按钮
        table.on('toolbar(jgryFilter)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            switch (obj.event) {
                case 'exportExcel':
                    exportExcel(checkStatus.data, obj.config.cols[0], "checked");
                    break;
            }
        });
        // 监听复选框事件，缓存选中的行数据
        table.on('checkbox(jgryFilter)', function(obj){
            // 获取选中或者取消的数据
            var data = obj.type == 'one' ? [obj.data] : currentPageData;

            $.each(data, function(i, v) {
                var keyT = v.taskKey;
                if (obj.checked) {
                    //.增加已选中项
                    layui.sessionData('checkedDataRy', {
                        key: keyT, value: v
                    });
                } else {
                    //.删除
                    layui.sessionData('checkedDataRy', {
                        key: keyT, remove: true
                    });
                }
            });
        });
        // 监听行事件
        table.on('tool(jgryFilter)', function (obj) { //bdcdyhList为table的lay-filter对应的值
            if (obj.event === 'openpage') {
                //得到当前行数据
                var listArray = {
                    processInstanceId: obj.data.processInstanceId,
                    processDefName: obj.data.processDefName,
                    claimStatus: obj.data.claimStatus,
                    claimStatusName: obj.data.claimStatusName,
                    taskName: obj.data.taskName,
                    processInstanceType: 'list',
                    taskId : obj.data.taskId
                };
                sessionStorage.setItem('listArray' + obj.data.processInstanceId, JSON.stringify(listArray));
                var index = window.open("/realestate-portal-ui/view/new-page.html?name=" + obj.data.taskId, obj.data.slbh);
                // newWin.push(index);
            }
        });
    });



    //比较起止时间
    function completeDate(date1,date2){
        var oDate1 = new Date(date1);
        var oDate2 = new Date(date2);
        if(oDate1.getTime() > oDate2.getTime()){
            return true;
        } else {
            return false;
        }
    }

    // 加载表格
    function renderTable(){
        var cols = [
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: 'jdzt', minWidth: 110, title: '节点状态', templet: '#stateTpl'},
            {field: 'taskName', minWidth: 110, title: '节点名称'},
            {field: 'slbh', minWidth: 110, title: '受理编号', templet: '#slbhTpl', event: 'openpage'},
            {field: 'qlr', minWidth: 100, title: '权利人'},
            {field: 'ywr', minWidth: 100, title: '义务人'},
            {field: 'zl', minWidth: 200, title: '坐落'},
            {field: 'taskAssName', minWidth: 100, title: '任务办理人'},
            {field: 'processDefName',minWidth: 160, title: '流程名称'},
        ];

        table.render({
            elem: '#jgryTable',
            title: '统计监管人员',
            url: '/realestate-inquiry-ui/rest/v1.0/tjjg/rybm/page',
            toolbar: '#toolbarDemo',
            defaultToolbar: ['filter'],
            method: 'GET',
            request: {
                limitName: 'size', //每页数据量的参数名，默认：limit
                loadTotal: true,
                loadTotalBtn: false
            },
            even: true,
            cols: [cols],
            page: true,
            parseData: function (res) {
                // 设置选中行
                if(res.content && res.content.length > 0){
                    var checkedData = layui.sessionData('checkedDataRy');
                    res.content.forEach(function (v) {
                        $.each(checkedData, function(key, value){
                            if(key == v.id){
                                v.LAY_CHECKED = true;
                            }
                        })
                    });
                }
                return {
                    "code": res.code, //解析接口状态
                    "msg": res.msg, //解析提示文本
                    "count": res.totalElements, //解析数据长度
                    "data": res.content //解析数据列表
                };
            },
            done: function () {
                setHeight();
                reverseTableCell(reverseList);
                table.resize('jgryTable');
            }
        });
    }

    // 重新加载表格数据
    function reloadTable(whereObj) {
        addModel();
        layui.use(['table'], function () {
            var table = layui.table;
            table.reload('jgryTable', {
                url: '/realestate-inquiry-ui/rest/v1.0/tjjg/rybm/page',
                where: whereObj,
                page: {
                    curr: 1,  //重新从第 1 页开始
                    limits: [10, 50, 100]
                },
                done: function (res, curr, count) {
                    removeModal();
                    setHeight();
                    reverseTableCell(reverseList);
                    table.resize('jgryTable');
                }
            });
        });
    }

    // 查询
    function search() {
        var requestConditions = getSearchParam();
        console.log(requestConditions);
        reloadTable({
            paramJson: JSON.stringify(requestConditions)
        });
    }

    function getSearchParam(){
        var jdmc = formSelects.value('selectJdmc', 'val')[0];
        // var bmid = formSelects.value('selectBmmc', 'val')[0];
        var bmid = $("input[name=djbmdm]").val();
        var bjry = getMultiSelectVal("selectBjry", "name");
        var requestConditions = [];
        requestConditions.push(condition("taskName", "eq", jdmc));
        requestConditions.push(condition("taskOrg", "in", bmid));
        requestConditions.push(condition("taskAssiginName", "in", bjry));
        requestConditions.push(condition("startTime_complete", "egt", $("#kssj").val()));
        requestConditions.push(condition("startTime_complete", "elt", $("#jzsj").val()));
        console.log(requestConditions);
        return requestConditions;
    }

    function condition(key,judge,value){
        return {
            requestKey : key,
            requestJudge : judge,
            requestValue : value == undefined? "" : value,
        }
    }

    function exportExcel(d, cols, type){
        if($.isEmptyObject(d) && type == "checked"){
            // 未选择数据时，询问是否导出全部
            layer.confirm('未勾选数据,将导出全部数据？', {
                title: '提示',
                btn: ['确定', '取消'] //按钮
            }, function (index) {
                addModel();
                var requestConditions = getSearchParam();
                $.ajax({
                    url: '/realestate-inquiry-ui/rest/v1.0/tjjg/rybm/page?loadTotal=true&page=1&size=9999',
                    type: "GET",
                    async: true,
                    contentType: 'application/json;charset=utf-8',
                    data: {paramJson:JSON.stringify(requestConditions)},
                    success: function (data) {
                        removeModal();
                        layer.close(index);
                        if(data.content.length>0){
                            doExport(data.content, cols, type);
                        }else{
                            warnMsg("未获取到数据");
                        }
                    },
                    error: function (err) {
                        removeModal();
                        delAjaxErrorMsg(err);
                    }
                });
            });
        }else{
            doExport(d, cols, type);
        }

    }

    function doExport(d, cols, type){
        // 标题
        var showColsTitle = [];
        // 列内容
        var showColsField = [];
        // 列宽
        var showColsWidth = [];
        for(var index in cols){
            if(cols[index].hide == false && cols[index].type != 'checkbox' && !cols[index].toolbar){
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

        var data = d;
        for(var i = 0; i < data.length; i++){
            data[i].xh   = i + 1;
            data[i].jdzt = "退回";
        }
        // 设置Excel基本信息
        $("#fileName").val('统计节点退回监管');
        $("#sheetName").val('统计表');
        $("#cellTitle").val(showColsTitle);
        $("#cellWidth").val(showColsWidth);
        $("#cellKey").val(showColsField);
        $("#data").val(JSON.stringify(data));
        $("#form").submit();
    }

    //数据交互
    function getContextPath(){
        var pathName = document.location.pathname;
        var index = pathName.substr(1).indexOf("/");
        var result = pathName.substr(0, index + 1);
        return result;
    }

    function getDataByAjax(_path, _param,_type, _fn, async) {
        var _url = getContextPath() + _path;
        var getAsync = false;
        if(async){
            getAsync = async
        }
        $.ajax({
            url: _url,
            type: _type,
            async: getAsync,
            contentType: 'application/json;charset=utf-8',
            data: _param,
            success: function (data) {
                _fn.call(this, data, data);
            },
            error: function (err) {
                console.log(err);
            }
        });
    }

    /**
     * 获取多选select框的值
     * @param selectorName 多选框选择器
     * @param type 指定多选框的值类型（name：名称，value：id）
     * @returns {string}
     */
    function getMultiSelectVal(selectorName, type) {
        var selectArr = formSelects.value(selectorName);
        var selects = "";
        if (selectArr.length != 0) {
            var selectList = [];
            $.each(selectArr, function (index, select) {
                if (type == "name") {
                    selectList.push(select.name);
                } else {
                    selectList.push(select.value);
                }
            });
            // return selectList;
            selects = selectList.join(",");
        }
        return selects;
    }

});

//树结构获取值重置
$('#reset').on('click', function () {
    $("input[name=djbmdm]").val("");
});
