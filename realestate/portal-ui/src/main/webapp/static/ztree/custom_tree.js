/**
 * @author zenglihuan
 * @create 2018/08/02 14:57
 * @discription ztree 自定义树型结构模块 全加载或分级加载都支持
 * 1、分级加载时，后端需要返回isParent参数
 * 2、分级加载查找下级节点时，会传当前节点的id作为pid至后端
 * 3、全加载或分级加载时，都需要返回id和parentId参数
 */
layui.define(['layer'], function(exports){
    var that = {
        // 默认异步加载
        async:{ enable: true}
    };

    that.checkboxEnabled = true;
    that.chkboxType_Y = "";
    that.chkboxType_N = "";
    that.params = {};
    that.chkStyle = "checkbox";
    that.zNodes = [];

    var custom_tree = {
        /**
         * 初始化加载数型结构
         * config
         * @param treeId ztree的ID。 必填
         * @param commonUrl 查询树型结构列表URL。 必填
         * @param onClick 点击节点时触发的方法。 选填
         * @param checkboxEnabled 是否有checkbox多选框。 选填
         * @param onCheck 勾选多选框触发的方法。 选填
         * @param onClickEnabled 判断是否可以点击事件。 选填
         * @param fillNodeContent 加载节点明细。 选填
         * @param updateParentUrl 若想支持拖拽，则需设置更新父节点的URL。 选填
         * @param chkboxType_Y checkbox 被勾选后的情况。 选填
         * @param chkboxType_N checkbox 取消勾选后的情况。 选填
         * @param chkStyle 勾选框类型(checkbox 或 radio）,默认checkbox。 选填
         * @param params 参数,JSON格式或数组格式，如{"id": "1"}或 ["id", "1"]，具体参见ztree中otherParam的用法。 选填
         */
        load: function (config){
            that.treeId = config.treeId;
            that.commonUrl = config.commonUrl;
            that.updateParentUrl = config.updateParentUrl;
            that.onClick = config.onClick;
            that.onCheck = config.onCheck;
            that.fillNodeContent = config.fillNodeContent;
            that.onClickEnabled = config.onClickEnabled;
            that.async.enable = config.asyncEnable != undefined ? config.asyncEnable : that.async.enable;
            that.zNodes = config.zNodes;

            if(!isEmpty(config.chkStyle)){
                that.chkStyle = config.chkStyle;
            }
            if(!isEmpty(config.chkboxType_Y)){
                that.chkboxType_Y = config.chkboxType_Y;
            }
            if(!isEmpty(config.chkboxType_N)){
                that.chkboxType_N = config.chkboxType_N;
            }
            if(!isEmpty(config.checkboxEnabled)){
                that.checkboxEnabled = config.checkboxEnabled;
            }
            if(!isEmpty(config.params)){
                that.params = config.params;
            }

            init();
        },
        /**
         * 重新加载树
         * @param params 参数
         */
        reload: function (params){
            if(!isEmpty(params)){
                that.params = params;
            }else{
                that.params = {};
            }

            init();
        },
        /**
         * 获取被点击节点
         */
        getClickNode: function (){
            return that.clickNode;
        },
        /**
         * 获取第一个根节点
         * @returns {*}
         */
        getFirstNode: function (){
            return that.firstNode;
        },
        /**
         * 获取被同步的节点
         */
        getAsyncNode: function (){
            return that.asyncNode;
        },
        /**
         * 获取多选框被勾选的节点
         */
        getCheckedNodes: function () {
            var zTree = $.fn.zTree.getZTreeObj(that.treeId);
            return zTree.getCheckedNodes(true);
        },
        /**
         * 获取多选框被勾选的节点Id
         */
        getCheckedNodeIds: function () {
            var zTree = $.fn.zTree.getZTreeObj(that.treeId),
                checkedNodes = zTree.getCheckedNodes(true);
            return object2Field(checkedNodes, "id");
        },
        /**
         * 刷新编辑内容
         * @param treeNode
         */
        refreshRightContent: function(treeNode) {
            that.fillNodeContent(treeNode);
        },
        /**
         * 重新同步treeNode子节点
         */
        reAsyncChildNodes: function (treeNode, callback) {
            var treeObj = $.fn.zTree.getZTreeObj(that.treeId);
            treeObj.reAsyncChildNodes(treeNode, "refresh", null, callback);
        },
        /**
         * 获取Ztree对象
         */
        getZTreeObj: function () {
            var treeObj = $.fn.zTree.getZTreeObj(that.treeId);
            return treeObj;
        },
        /**
         * 选中节点
         * @param treeNode
         */
        selectNode: function (treeNode) {
            var treeObj = $.fn.zTree.getZTreeObj(that.treeId);
            treeObj.selectNode(treeNode);
            that.clickNode = treeNode;
        },
        /**
         * 清空树
         */
        emptyTree: function(){
            empty();
        }
    }

    function empty(){
        var setting = {
            data: {
                simpleData: {
                    enable: true
                }
            }
        };
        $(document).ready(function(){
            $.fn.zTree.init($("#" + that.treeId), setting, []);
        });
    }

    function isEmpty(obj){
        return (obj == null || obj == undefined);
    }

    function isArrayEmpty(arr){
        return (arr == null || arr == undefined || arr.length == 0);
    }

    function isEmpty(obj){
        return (obj == null || obj == undefined);
    }

    function isArray(temp){
        return (typeof temp=="object")&&temp.constructor==Array;
    }

    function isString(temp){
        return (typeof temp=="string")&&temp.constructor==String;
    }

    function object2Field(objs, field) {
        var fields = [];
        if(!isArrayEmpty(objs)){
            objs.forEach(function(obj){
                fields.push(obj[field]);
            });
        }
        return fields;
    }

    function field2Object(vals, field) {
        var objs = [];
        if(!isArrayEmpty(vals)){
            vals.forEach(function(val){
                var obj = {};
                obj[field] = val;
                objs.push(obj);
            });
        }
        return objs;
    }

    function object2Map(objs, key) {
        var _map = {};
        if(!isArrayEmpty(objs)){
            objs.forEach(function(obj){
                _map[obj[key]] = obj;
            });
        }
        return _map;
    }

    function init(){
        var setting = {
            view: {
                selectedMulti: false
            },
            edit: {
                enable: true,
                showRemoveBtn: false,
                showRenameBtn: false
            },
            check: {
                enable: that.checkboxEnabled,
                chkboxType: { "Y": that.chkboxType_Y, "N": that.chkboxType_N },
                chkStyle: that.chkStyle
            },
            callback: {
                beforeAsync: beforeAsync,
                onAsyncSuccess: onAsyncSuccess,
                beforeClick: beforeClick,
                onClick: onClick,
                onCheck: onCheck,
                beforeDrag: beforeDrag,
                beforeDrop: beforeDrop,
                onDrop: onDrop
            },
            async: {
                enable: that.async.enable,
                url: that.commonUrl,
                autoParam:["id=pid"],
                otherParam: that.params,
                contentType: "application/x-www-form-urlencoded",
                dataFilter: filter
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "parentId",
                    rootPId: ""
                }
            }
        };

        $(document).ready(function(){
            if(that.async.enable){
                $.fn.zTree.init($("#" + that.treeId), setting);
            }else{
                $.fn.zTree.init($("#" + that.treeId), setting, that.zNodes);
            }

        });
    }

    /**
     * 点击事件
     * @param event
     * @param treeId
     * @param treeNode
     */
    function onClick(event, treeId, treeNode){
        if(isEmpty(that.onClick)){
            return;
        }
        that.onClick(event, treeId, treeNode);
    }

    function onCheck(event, treeId, treeNode){
        if(isEmpty(that.onCheck)){
            return;
        }
        that.onCheck(event, treeId, treeNode);
    }

    /**
     * 同步前
     * @param treeId
     * @param treeNode
     * @returns {boolean}
     */
    function beforeAsync(treeId, treeNode) {
        return true;
    }

    /**
     * 同步成功后
     * 1、初始化clickNode、firstNode的值，并默认选中第一个根节点
     * 2、更新asyncNode，禁用时字体替换
     * @param event
     * @param treeId
     * @param treeNode
     * @param msg
     */
    function onAsyncSuccess(event, treeId, treeNode, msg) {
        that.asyncNode = treeNode;
        if(isEmpty(treeNode)){
            loadRootNodes();
        }
        //禁用时，设置节点为灰色
        setDisabledColor(treeNode, "#A9A9A9");
    };

    /**
     * 设置禁用节点颜色，只是变色，多选框可勾选，也可点击
     * 支持分级加载和全加载
     * @param color
     */
    function setDisabledColor(treeNode, color){
        var treeObj = $.fn.zTree.getZTreeObj(that.treeId);
        var nodes = [];
        if(isEmpty(treeNode)){
            nodes = treeObj.getNodes();
        }else{
            nodes = treeNode.children;
        }
        disableColor(treeObj, nodes, color);
    }

    function disableColor(treeObj, nodes, color){
        nodes.forEach(function(node) {
            if(node.enabled == 0){
                //禁用时，设置节点为灰色
                treeObj.setting.view.fontCss["color"] = color;
            }else{
                treeObj.setting.view.fontCss["color"] = "#333333";
            }
            treeObj.updateNode(node);

            var children = node.children;
            if(!isArrayEmpty(children)){
                disableColor(treeObj, children, color);
            }
        });
    }

    /**
     * 加载根节点
     * 设置clickNode、firstNode
     * 第一个节点被选中
     * 初始化编辑内容
     */
    function loadRootNodes(){
        var treeObj = $.fn.zTree.getZTreeObj(that.treeId);
        var nodes = treeObj.getNodes();
        if(!isArrayEmpty(nodes)){
            treeObj.selectNode(nodes[0]);
            that.clickNode = nodes[0];
            that.firstNode = nodes[0];
            if(isEmpty(that.fillNodeContent)){
                return;
            }
            that.fillNodeContent(nodes[0]);
        }else{
            that.clickNode = null;
            if(isEmpty(that.fillNodeContent)){
                return;
            }
            that.fillNodeContent(null);
        }
    }

    /**
     * 是否可以点击，默认都可以点击
     * 可根据初始化参数config.onClickEnabled方法来确定是否可以点击
     * @param treeId
     * @param treeNode
     * @param clickFlag
     * @returns {boolean}
     */
    function beforeClick(treeId, treeNode, clickFlag) {
        var clickEnabled = true;
        if(!isEmpty(that.onClickEnabled)){
            clickEnabled = that.onClickEnabled(treeId, treeNode, clickFlag);
        }
        if(clickEnabled){
            that.clickNode = treeNode;
        }
        return clickEnabled;
    };

    /**
     * 根据是否传updateParentUrl参数，来判断是否允许拖拽
     */
    function beforeDrag(treeId, treeNodes) {
        if(isEmpty(that.updateParentUrl)){
            return false;
        }else{
            return true;
        }
    };

    function filter(treeId, parentNode, childNodes) {
        if (!childNodes) return null;
        for (var i=0, l=childNodes.length; i<l; i++) {
            childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
        }
        return childNodes;
    }

    /**
     * 拖拽前判断是否能够拖拽到该父节点
     * 1、若没有目标节点，则拖拽失败
     * 2、若目标节点的子节点与当前结点重名，则拖拽失败
     */
    function beforeDrop (treeId, treeNodes, targetNode, moveType, isCopy) {
        if (targetNode) {
            if(!isArrayEmpty(targetNode.children)){
                var name = treeNodes[0].name;
                for (var i = 0; i < targetNode.children.length; i++) {
                    if (targetNode.children[i].name == name) {
                        alert("Error: This name already exists.");
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    //拖拽成功时，修改父节点
    function onDrop(event, treeId, treeNodes, targetNode, moveType, isCopy) {
        //拖拽成功时，修改被拖拽节点的pid
        if (treeNodes && targetNode) {
            $.post(that.updateParentUrl,{
                    id: treeNodes[0].id,
                    pid: targetNode.id
                },
                function(data, status){
                    if(status == 'success'){
                        console.log(treeNodes[0].name + "—拖拽到—" + targetNode.name + "下");
                    }
                });
        }
    }

    exports("custom_tree", custom_tree);

});