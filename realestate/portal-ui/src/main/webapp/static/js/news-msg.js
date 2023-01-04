/**
 * author: 前端组
 * date: 2018-12-14
 * version 3.0.0
 * describe: 查询条件所需的日期初始化、复选框初始化及高级查询
 */
layui.extend({
    custom_tree: '../../../../realestate-portal-ui/static/ztree/custom_tree'
});
// 组织树多选值
var OrgTreeCheck = new Map();
var custom_tree;
var roleids,deptids;
layui.use(['form', 'upload', 'jquery', 'element', 'table', 'response', 'formSelects','custom_tree'], function () {
    var $ = layui.jquery,
        table = layui.table,
        formSelects = layui.formSelects,
        upload = layui.upload,
        response = layui.response;
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

        //ztree控件加载
        $('.org_select_tree').css({'width': '278px', 'height': '200px', 'left': 106, 'top': 40});
//加载树
        custom_tree.load({
            treeId: "select_tree",
            commonUrl: '/realestate-portal-ui/rest/v1.0/ztree/orgs',
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
                    commonUrl: '/realestate-portal-ui/rest/v1.0/ztree/orgs',
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
                deptids=$("input[name=djbmdm]").val();
                //获取当前部门下的人员
                var ryData = []
                $.ajax({
                    type: "GET",
                    url: getContextPath() + "/rest/v1.0/user/queryUsersByRoles",
                    contentType: "application/json;charset=utf-8",
                    dataType: "json",
                    data: {roles: roleids,deptids:deptids},
                    success: function (data) {
                        data.forEach(function (v) {
                            ryData.push({"name": v.alias, "value": v.username});
                        });
                        formSelects.data('selectRy', 'local', {
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
        // 获取页面表单标识，用于权限控制
        $paramArr = getIpHz();
        var moduleCode = $paramArr['moduleCode'];
        var msgType=$paramArr['msgType'];
        var clientId=$paramArr['clientId'];
        table.render({
            elem: '#pageTable',
            toolbar: '#toolbarDemo',
            title: '用户数据表',
            defaultToolbar: ['filter'],
            url: getContextPath() + "/rest/v1.0/msg/list",
            where: {msgType:msgType,clientId:clientId},
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            even: true,
            cols: [[
                {type: 'checkbox', width: 50, fixed: 'left'},
                {field: 'msgTypeName',  width: 200,title: '名称'},
                {field: 'producerName', width: 100, title: '发送者'},
                {field: 'msgTitle',title: '发送标题', width: 200},
                {title: '流程名称', width: 200,templet: '#processDefNameTpl'},
                {title: '类型', width: 100,templet: '#producerTypeTpl'},
                { field: 'msgContent', title: '操作原因/发送内容', templet: '#opinionTpl'},
                {width: 120, title: '状态', fixed: 'right', templet: '#stateTpl'},
                {title: '操作', width: "10%", templet: '#editBar', align: "center"}
            ]],
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
                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');

                if ($('.bdc-table-box .layui-table-main>.layui-table').height() == 0) {
                    $('.layui-table-body .layui-none').html('<img src="../static/lib/bdcui/images/table-none.png" alt="">无数据');
                } else {
                    $('.layui-table-main.layui-table-body').height($('.bdc-table-box').height() - 131);
                }
                // 控制按钮权限
                setElementAttrByModuleAuthority(moduleCode);
            }
        });
        //监听行工具事件
        table.on('tool(msgFilter)', function(obj){
            var data = obj.data;
            //console.log(obj)
            if(obj.event === 'download'){
                addModel();
                $.ajax({
                    type: "POST",
                    url: getContextPath() + "/rest/v1.0/msg/downloadMsgFile",
                    traditional: true,
                    data: {msgCode: data.msgCode},
                    success: function (data) {
                        removeModal();
                        if (!isNullOrEmpty(data)){
                            data.forEach(function (value) {
                                window.open(encodeURI(value.downUrl));
                            });
                        }else{
                            response.success("无附件信息");
                        }
                    }, error: function (e) {
                        removeModal();
                        response.fail(e, '');
                    }
                });
            }
        });

        //监听表格头部工具栏事件
        table.on('toolbar(msgFilter)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            if (obj.event != "all-read" && obj.event != "send-msg") {
                if (checkStatus.data.length < 1) {
                    layer.msg('请选择一条数据进行操作！');
                    return false;
                }
            }
            var ids = new Array();
            $.each(checkStatus.data, function (key, val) {
                ids.push(val.id);
            });

            switch (obj.event) {
                case 'read':
                    // 已读
                    console.log('已读');
                    readMsg(ids);
                    break;
                case 'delete':
                    // 删除
                    console.log('删除');
                    deleteMsg(ids);
                    break;
                case 'all-read':
                    // 全部已读
                    // 不需要选中数据，传一个参数到后台全部已读，重新刷新表格
                    readAllMsg();
                    console.log('全部已读');
                    break;
                case 'send-msg':
                    //弹出发送消息弹出层
                    sendMsg();
                    break;
            }
        });
        $('#search').on('click', function () {
            var obj = new Object();
            obj['read'] = $('#msgState').val();
            // 重新请求
            table.reload('pageTable', {
                where: obj
                , page: {
                    curr: 1  //重新从第 1 页开始
                }
            });
        });

        //监听角色
        var iTime;
        layui.formSelects.on('selectJs', function (id, vals, val, isAdd, isDisabled) {
            clearTimeout(iTime);
            iTime = setTimeout(function () {
                roleids = formSelects.value('selectJs', 'val');
                if (roleids.length > 0){
                    //获取用户列表
                    $.ajax({
                        type: "GET",
                        url: getContextPath() + "/rest/v1.0/user/queryUsersByRoles",
                        traditional: true,
                        data: {roles: roleids,deptids:deptids},
                        success: function (data) {
                            if (data) {
                                var userdata = [];
                                data.forEach(function (val, index) {
                                    var user = {};
                                    user.name = val.alias;
                                    user.value = val.username;
                                    userdata.push(user);
                                })
                                formSelects.data('selectRy', 'local', {
                                    arr: userdata
                                });
                            }
                        }, error: function (e) {
                            response.fail(e, '');
                        }
                    });
                }
            }, 100);

        }, true);
        //监听组织
        layui.formSelects.on('selectZz', function (id, vals, val, isAdd, isDisabled) {
            clearTimeout(iTime);
            iTime = setTimeout(function () {
                deptid = formSelects.value('selectZz', 'val');

                if (deptid.length > 0){
                    //获取用户列表
                    $.ajax({
                        type: "GET",
                        url: getContextPath() + "/rest/v1.0/user/queryUsersByRoles",
                        traditional: true,

                        data: {roles: roleids,deptids: deptids},
                        success: function (data) {
                            if (data) {
                                var userdata = [];
                                data.forEach(function (val, index) {
                                    var user = {};
                                    user.name = val.alias;
                                    user.value = val.username;
                                    userdata.push(user);
                                })
                                formSelects.data('selectRy', 'local', {
                                    arr: userdata
                                });
                            }
                        }, error: function (e) {
                            response.fail(e, '');
                        }
                    });
                }
            }, 100);

        }, true);

        if( isNullOrEmpty(deptids) && isNullOrEmpty(roleids)){
            formSelects.data('selectRy', 'local', {
                arr: []
            });

        }
        //标记为已读
        function readMsg(ids) {
            processMsg("/rest/v1.0/msg/readMessages", "PUT", "标记消息为已读", ids)
        }

        //标记为未读
        function unreadMsg(ids) {
            processMsg("/rest/v1.0/msg", "DELETE", "删除消息", ids)
        }

        //删除
        function deleteMsg(ids) {
            processMsg("/rest/v1.0/msg/deleteMessages", "DELETE", "删除消息", ids)
        }

        //全部消息标记为已读
        function readAllMsg() {
            var allids = []
            $.each(layui.table.cache["pageTable"], function (key, val) {
                allids.push(val.id);
            });
            processMsg("/rest/v1.0/msg/readMessages", "PUT", "标记消息为已读", allids)
        }

        //给指定用户发送消息
        function sendMsg() {
            //先清空
            formSelects.data('selectJs', 'local', {
                arr: []
            });
            formSelects.data('selectRy', 'local', {
                arr: []
            });
            $("#msgContent").val("");
            layer.open({
                title: '自定义消息',
                type: 1,
                area: ['430px', '420px'],
                btn: ['提交', '取消'],
                content: $('#bdc-popup-small')
                , success: function () {
                    //获取角色列表
                    $.ajax({
                        type: "GET",
                        url: getContextPath() + "/rest/v1.0/user/queryEnabledRoles",
                        success: function (data) {
                            if (data) {
                                var roledata = [];
                                data.forEach(function (val, index) {
                                    var role = {};
                                    role.name = val.alias;
                                    role.value = val.id;
                                    roledata.push(role);
                                })
                                formSelects.data('selectJs', 'local', {
                                    arr: roledata
                                });
                            }
                        }, error: function (e) {
                            response.fail(e, '');
                        }
                    });
                }
                , yes: function (index, layero) {
                    var content = $("#msgContent").val();
                    if (isNullOrEmpty(content)) {
                        layer.msg("请填写发送内容！");
                        return;
                    }
                    var usernames = formSelects.value('selectRy', 'val');
                    uploadFile();
                    if (usernames.length > 0) {
                        $.ajax({
                            type: "POST",
                            url: getContextPath() + "/rest/v1.0/msg",
                            traditional: true,
                            data: {usernames: usernames, msgContent: $("#msgContent").val(), msgCode: $("#msgCode").val(),msgTitle:$("#msgTitle").val()},
                            success: function (data) {
                                layer.msg("发送成功！");
                            }, error: function (e) {
                                response.fail(e, '');
                            }
                        });
                    } else {
                        layer.msg("请选择发送人员！");
                    }
                    layer.close(index);
                },
                btn2: function(index, layero){
                    var zTreeObj = $.fn.zTree.getZTreeObj("select_tree");//ztree对象
                    zTreeObj.checkAllNodes(false);//设置全部为false,不选中节点
                    formSelects.data('selectry', 'local', {
                        arr: []
                    });
                    $("input[name='bmmc']").val("");//清空input值
                    $("input[name='djbmdm']").val("");//清空input值
                    layer.close(index);
                },
            });
        }

        //请求
        function processMsg(url, type, processType, ids) {
            $.ajax({
                type: type,
                url: getContextPath() + url,
                contentType: "application/json;charset=utf-8",
                data: JSON.stringify(ids),
                success: function () {
                    layer.msg(processType + '成功！');
                    renderTable(getContextPath() + "/rest/v1.0/msg/list", '', "pageTable")
                }, error: function (e) {
                    response.fail(e, '');
                }
            });
        }
    });
});

function uploadFile(){

    var formData = new FormData();
    var fileobjs = $("#file")[0].files;  // $("#file")[0]将jquery对象转换为dom对象，使用jquery的方法.get(0)也可以
    for (var i = 0; i < fileobjs.length; i++) {
        formData.append("files", fileobjs[i]);  // append方法使用相同键追加元素，最后会被输出为MultipartFile数组
    }
    $.ajax({
        url: getContextPath() + "/rest/v1.0/msg/uploadMsgFile",
        type: 'POST',
        data: formData,
        processData: false,
        contentType: false,
        async: false,
        cache: false,
        success: function(data) {
            console.log(data);
            $("#msgCode").val(data.msgCode);
        }
    });
}