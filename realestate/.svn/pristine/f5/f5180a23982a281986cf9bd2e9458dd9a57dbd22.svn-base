/**
 * 退件量统计查询
 */
var reverseList = ['zl'];
layui.config({
    base: '../../static/lib/form-select/'
}).extend({
    formSelects: 'formSelects-v4'
});
layui.use(['jquery','form','table','laydate','formSelects'], function () {
    //获取字典
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        laydate = layui.laydate,
        formSelects = layui.formSelects;

    // 当前页数据
    var currentPageData = [];

    layui.sessionData('checkedDataLc', null);

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
        //2.默认渲染部门名称，选中名称后，根据名称渲染办理人员
        getDataByAjax('/rest/v1.0/process/listOrgs','','GET',function (data) {
            var bmData = [];
            data.forEach(function (v) {
                bmData.push({"name": v.name, "value": v.id});
            });
            formSelects.data('selectBmmc', 'local', {
                arr: bmData
            });
        });
        formSelects.on('selectBmmc', function(id, vals, val){
            getDataByAjax('/rest/v1.0/process/users/'+val.value,'','GET',function (param) {
                console.info(param);
                var ryData = [];
                param.forEach(function (v) {
                    ryData.push({"name": v.alias, "value": v.username});
                });
                formSelects.data('selectBjry', 'local', {
                    arr: ryData
                });
            });
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
            {field: 'slbh', minWidth: 110, title: '受理编号', templet: '#slbhTpl', event: 'openpage'},
            {field: 'qlr', minWidth: 100, title: '权利人'},
            {field: 'zl', minWidth: 200, title: '坐落'},
            {field: 'procStartUserName', minWidth: 100, title: '受理人员'},
            {field: 'processDefName', minWidth: 160, title: '流程名称'},
            {field: 'processDefName', minWidth: 160, title: '退件原因'},
            // 退件人员
            // {field: 'ywr', minWidth: 100, title: '义务人'},
            {field: 'taskName', minWidth: 50, title: '被退回节点名称', minWidth: 140},
            {field: 'backTime', minWidth: 200, title: '退回时间'},
            {field: 'backReason', minWidth: 200, title: '被退回原因'},
            {field: 'backPerson', minWidth: 100, title: '退回人'},
            {field: 'jdzt', minWidth: 100, title: '节点状态', templet: '#stateTpl'},
            {field: 'backUserAlias', minWidth: 100, title: '受理人'},
            {field: 'next_back_opinion', minWidth: 200, title: '退件原因'},
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
            limits: [10, 50, 100, 200, 500],
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
            done: function (res) {
                layuiRowspan(['slbh','processDefName','zl','qlr'],1);//支持数组

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
                    layuiRowspan(['slbh','processDefName','zl','qlr'],1);//支持数组
                    //merge(res);
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
        reloadTable({
            paramJson: JSON.stringify(requestConditions)
        });
    }

    //获取查询参数
    function getSearchParam(){
        var requestConditions = [];
        var jdmc = formSelects.value('selectJdmc', 'val')[0];
        var bmid = formSelects.value('selectBmmc', 'val')[0];
        var bjry = getMultiSelectVal("selectBjry", "name");
        var requestConditions = [];
        requestConditions.push(condition("taskName", "eq", jdmc));
        requestConditions.push(condition("taskOrg", "like", bmid));
        // requestConditions.push(condition("taskAssiginName", "eq", bjry));
        requestConditions.push(condition("taskAssiginName", "in", bjry));
        requestConditions.push(condition("startTime_complete", "egt", $("#kssj").val()));
        requestConditions.push(condition("startTime_complete", "elt", $("#jzsj").val()));
        requestConditions.push(condition("startUserName", "like", $("#startUserName").val()));
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
            if(!data[i].processDefName){
                data[i].processDefName = "";
            }
            if(!data[i].zl){
                data[i].zl = "";
            }
            if(!data[i].qlr){
                data[i].qlr = "";
            }
        }
        // 设置Excel基本信息
        $("#fileName").val('统计节点退回监管');
        $("#sheetName").val('统计表');
        $("#cellTitle").val(showColsTitle);
        $("#cellWidth").val(showColsWidth);
        $("#cellKey").val(showColsField);
        $("#data").val(JSON.stringify(data));
        $("#mergeSameCell").val(true);
        $("#mergeCellIdKey").val("slbh");
        $("#mergeCellKey").val("slbh,processDefName,zl,qlr");
        $("#mergeColumnCellKeyList").val(JSON.stringify(new Array("slbh,processDefName,zl,qlr")));
        $("#addBorder").val(true);
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

    var execRowspan = function(fieldName,index,flag){
        // 1为不冻结的情况，左侧列为冻结的情况
        var fixedNode = index=="1"?$(".layui-table-body")[index - 1]:(index=="3"?$(".layui-table-fixed-r"):$(".layui-table-fixed-l"));
        // 左侧导航栏不冻结的情况
        var child = $(fixedNode).find("td");
        var childFilterArr = [];
        // 获取data-field属性为fieldName的td
        for(var i = 0; i < child.length; i++){
            if(child[i].getAttribute("data-field") == fieldName){
                childFilterArr.push(child[i]);
            }
        }
        // 获取td的个数和种类
        var childFilterTextObj = {};
        for(var i = 0; i < childFilterArr.length; i++){

            var childText = flag?childFilterArr[i].innerHTML:childFilterArr[i].textContent;
            if(childFilterTextObj[childText] == undefined){
                childFilterTextObj[childText] = 1;
            }else{
                var num = childFilterTextObj[childText];
                childFilterTextObj[childText] = num*1 + 1;
            }
        }
        var canRowspan = true;
        var maxNum;//以前列单元格为基础获取的最大合并数
        var finalNextIndex;//获取其下第一个不合并单元格的index
        var finalNextKey;//获取其下第一个不合并单元格的值
        for(var i = 0; i < childFilterArr.length; i++){

            (maxNum>9000||!maxNum)&&(maxNum = $(childFilterArr[i]).prev().attr("rowspan")&&fieldName!="8"?$(childFilterArr[i]).prev().attr("rowspan"):9999);
            var key = flag?childFilterArr[i].innerHTML:childFilterArr[i].textContent;//获取下一个单元格的值
            var nextIndex = i+1;
            var tdNum = childFilterTextObj[key];
            var curNum = maxNum<tdNum?maxNum:tdNum;
            if(canRowspan){
                for(var j =1;j<=curNum&&(i+j<childFilterArr.length);){//循环获取最终合并数及finalNext的index和key
                    finalNextKey = flag?childFilterArr[i+j].innerHTML:childFilterArr[i+j].textContent;

                    // slbh不一样的不能合并
                    var slbh2 = $(childFilterArr[i]).parent().find('td:eq(1)').text();
                    var slbh1 = $(childFilterArr[i]).parent().next().find('td:eq(1)').text();
                    finalNextIndex = i+j;
                    if((key != finalNextKey && curNum>1)||maxNum == j  ){
                        canRowspan = true;
                        curNum = j;
                        break;
                    }

                    j++;
                    if((i+j)==childFilterArr.length){
                        finalNextKey=undefined;
                        finalNextIndex=i+j;
                        break;
                    }
                }

                // slbh不一样的不能合并
                var slbh2 = $(childFilterArr[i]).parent().find('td:eq(1)').text();
                var slbh1 = $(childFilterArr[i]).parent().next().find('td:eq(1)').text();
                if(slbh1 != slbh2){
                    //continue;
                }

                // $(childFilterArr[i]).parent().css("background-color","#f3f4f6");
                childFilterArr[i].setAttribute("rowspan",curNum);
                if($(childFilterArr[i]).find("div.rowspan").length>0){//设置td内的div.rowspan高度适应合并后的高度
                    $(childFilterArr[i]).find("div.rowspan").parent("div.layui-table-cell").addClass("rowspanParent");
                    $(childFilterArr[i]).find("div.layui-table-cell")[0].style.height= curNum*38-10 +"px";
                }
                canRowspan = false;
            }else{
                // $(childFilterArr[i]).parent().css("background-color","#f3f4f6");
                childFilterArr[i].style.display = "none";
            }
            if(--childFilterTextObj[key]==0|--maxNum==0|--curNum==0|(finalNextKey!=undefined&&nextIndex==finalNextIndex)){
                canRowspan = true;
            }
        }
    }
//合并数据表格行
    var layuiRowspan = function(fieldNameTmp,index,flag){
        var fieldName = [];
        if(typeof fieldNameTmp == "string"){
            fieldName.push(fieldNameTmp);
        }else{
            fieldName = fieldName.concat(fieldNameTmp);
        }
        for(var i = 0;i<fieldName.length;i++){
            execRowspan(fieldName[i],index,flag);
        }
    }
});