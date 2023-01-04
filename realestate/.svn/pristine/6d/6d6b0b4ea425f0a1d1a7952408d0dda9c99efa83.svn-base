layui.config({
    base: '../../../static/lib/form-select/'
}).extend({
    formSelects: 'formSelects-v4'
});
layui.use(['table', 'laytpl', 'laydate', 'layer', 'form','formSelects'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer,
        form = layui.form,
        formSelects = layui.formSelects;

    var BASE_URL = '/realestate-inquiry-ui/rest/v1.0';

    var consumer = getConsumer("gxbmbz");
    formSelects.data('consumer', 'local', {
        arr: consumer
    });

    var releaseStatusOption = "<option value=''>请选择</option><option value='0'>未发布</option><option value='1'>已发布</option>";
    $("#releaseStatus").append(releaseStatusOption);
    form.render('select');

    /**
     * 加载Table数据列表
     */
    table.render({
        elem: '#apiTable',
        toolbar: "#toolbar",
        defaultToolbar: ['filter'],
        url: BASE_URL+'/apiManagement/page/search',
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        cellMinWidth: 80,
        where: {},
        even: true,
        cols: [[
            {type: 'checkbox', fixed: 'left',width:50},
            {fixed: 'left',field: '', title: '序号',width:60,templet: function(d){
                    return (d.LAY_INDEX);
                }},
            { field: 'releaseStatus', title: '发布状态', width: 100,
                templet: function (d) {
                    if(d.releaseStatus === 1){
                        return "已发布";
                    }else if(d.releaseStatus === 0){
                        return "未发布";
                    }else{
                        return "";
                    }
                }
            },
            { field: 'id', title: '接口id', width: 100, hide:true},
            { field: 'clientId', title: '应用id', width: 100,hide:true},
            { field: 'name', title: '接口名称', width: 200 },
            { field: 'url', title: '接口地址', width: 300 },
            { field: 'requestMethod', title: '请求方式', width: 100 },
            { field: 'consumer', title: '接口消费方', width: 100,
                templet: function (d) {
                    if(d.consumer && consumer && consumer.length>0){
                        if (d.consumer.indexOf(',')){
                            var consumerArray = d.consumer.split(',');
                            var consumerStr = '';
                            for (let j = 0; j < consumerArray.length; j++) {
                                var flag = false;
                                for(var i = 0 ; i < consumer.length ; i++){
                                    if(consumer[i].value === consumerArray[j]){
                                        consumerStr = consumerStr + ',' + consumer[i].name;
                                        flag = true;
                                        break;
                                    }
                                }
                                if (!flag){
                                    consumerStr = consumerStr + ',' + consumerArray[j];
                                }
                            }
                            if (consumerStr){
                                return consumerStr.substr(1,consumerStr.length);
                            }
                        }else {
                            for(var i = 0 ; i < consumer.length ; i++){
                                if(consumer[i].value === d.consumer){
                                    return consumer[i].name;
                                }
                            }
                        }
                        return d.consumer;
                    }else{
                        return '';
                    }
                }
            },
            { field: 'requestBody', title: '请求参数', width: 300,
                templet: function (d) {
                    if(d.type === 0){
                        var requestBody = d.requestBodyParamJson && JSON.parse(d.requestBodyParamJson) || {};
                        var queryParam = d.queryParamJson && JSON.parse(d.queryParamJson) || {};
                        var restParam = d.restParamJson && JSON.parse(d.restParamJson) || {};
                        var param = Object.assign(requestBody, queryParam, restParam);
                        return JSON.stringify(param) === "{}" ? "" : JSON.stringify(param);
                    }else{
                        return d.paramJson === "" ? "" : d.paramJson.replace(/\n/g, "");
                    }
                }
            },
            { field: 'description', title: '接口描述', width: 200 },
            { field: 'type', title: '接口类型', width: 100,
                templet: function (d) {
                    if(d.type === 0){
                        return "自定义接口";
                    }else{
                        return "程序定义接口";
                    }
                }
                },
            { field: 'logFlag', title: '是否记录日志', width: 100,
                templet: function (d) {
                    if(d.logFlag === 1){
                        return "否";
                    }else if(d.logFlag === 0){
                        return "是";
                    }else{
                        return "";
                    }
                }
            },
            { field: 'sql', title: '配置sql', width: 300,hide: true},
            { field: 'createdTime', title: '创建时间', width: 100,hide: true,
                templet: function (d) {
                    return format(d.createdTime);
                }
            },
            { field: 'createdBy', title: '创建人', width: 100,hide: true},
            { field: 'updatedTime', title: '修改时间', width: 100,
                templet: function (d) {
                    return format(d.updatedTime);
                }    },
            { field: 'updatedBy', title: '修改人', width: 100 },
            { fixed: 'right', title: '操作', templet: '#api_tool_bar', width: 230, align: "center" }
        ]],
        text: {
            none: '未查询到数据'
        },
        autoSort: false,
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
                $('.layui-table-body .layui-none').html('<img src="../../../static/lib/registerui/image/table-none.png" alt="">无数据');
            } else {
                $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
            }
        }
    });

    //监听滚动事件
    var scrollTop = 0,
        scrollLeft = 0;
    var tableTop = 0, tableLeft = 0;
    var $nowBtnMore = '';
    $(window).on('scroll', function () {
        scrollTop = $(this).scrollTop();
        scrollLeft = $(this).scrollLeft();

        if ($nowBtnMore != '') {
            if (tableTop + 26 + $nowBtnMore.height() < document.body.offsetHeight) {
                // $nowBtnMore.css({top: tableTop + 26 -scrollTop,right: 20});
                $nowBtnMore.css({top: tableTop + 26 - scrollTop, left: tableLeft - 30});
            } else {
                // $nowBtnMore.css({top: tableTop -scrollTop - $nowBtnMore.height(),right: 20});
                $nowBtnMore.css({top: tableTop - scrollTop - $nowBtnMore.height(), left: tableLeft - 30});
            }
        }
    });
    //点击表格中的更多
    $('.bdc-table-box').on('click', '.bdc-more-btn', function (event) {
        tableTop = $(this).offset().top;
        tableLeft = $(this).offset().left;
        event.stopPropagation();
        $nowBtnMore = $(this).siblings('.bdc-table-btn-more');
        var $btnMore = $(this).siblings('.bdc-table-btn-more');
        if ($(this).offset().top + 26 + $btnMore.height() < document.body.offsetHeight) {
            // $btnMore.css({top: $(this).offset().top + 26 -scrollTop,right: 20});
            $btnMore.css({top: tableTop + 26 - scrollTop, left: tableLeft - 30});
        } else {
            // $btnMore.css({top: $(this).offset().top -scrollTop - $btnMore.height(),right: 20});
            $btnMore.css({top: tableTop - scrollTop - $btnMore.height(), left: tableLeft - 30});
        }
        $('.bdc-table-btn-more').hide();
        $btnMore.show();
    });
    // 点击更多内的任一内容，隐藏
    $('.bdc-table-btn-more a').on('click', function () {
        $(this).parent().hide();
    });
    //点击页面任一空白位置，消失
    $('body').on('click', function () {
        $('.bdc-table-btn-more').hide();
    });
    //头工具栏事件
    table.on('toolbar(apiTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'add':
                editApi(0, '');
                break;
            case 'batchDelete':
                batchDeleteApi(checkStatus.data);
                break;
            case 'allrztj':
                allrztj(checkStatus.data);
                break;
            case 'additionalLearning':
                additionalLearning();
                break;
            case 'importInterface':
                importInterface();
                break;
            case 'exportInterface':
                exportInterface(checkStatus.data);
                break;
        }
    });

    //监听工具条
    table.on('tool(apiTable)', function(obj) {
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event;
        if(layEvent === 'test'){
            if(data.type == 0){
                window.open("/realestate-inquiry-ui/view/config/jkgl/apiTest.html?apiId=" + data.id)
            }else{
                window.open("/realestate-inquiry-ui/view/config/jkgl/complexApiText.html?apiId=" + data.id + "&type=" + data.type)
            }
        }
        if(layEvent === 'release'){
            updateReleaseStatus(data.id,1);
        }
        if(layEvent === 'edit'){
            editApi(1,data.id);
        }
        if(layEvent === 'delete'){
            var param = [];
            param.push(data.id);
            deleteApi(param);
        }
        if(layEvent === 'detail'){
            editApi(2,data.id);
        }
        if(layEvent === 'unPublish'){
            updateReleaseStatus(data.id, 0);
        }
        if (layEvent === 'export') {
            var param = {
                apiId: data.id
            };
            $("#filedata").val(JSON.stringify(param));
            console.log($('input[name="filedata"]').val());
            $("#apidoc").submit();
        }
        if (layEvent === 'queryLog') {
            queryLog(data.jklx, data.logType, data.id);
        }
        if (layEvent === 'copy') {
            copyApi(1,data.id);
        }
    });

    function queryLog(jklx, logType, interfaceId) {
        if (jklx && jklx == 0 && logType && logType == 1) {
            window.open("/realestate-exchange/view/bdcdsflog.html?type=logines&interfaceId=" + interfaceId);
        } else if (jklx && jklx == 0 && logType && logType == 0) {
            window.open("/realestate-exchange/view/bdcdsflog.html?type=logindb&interfaceId=" + interfaceId);
        } else {
            window.open("/realestate-exchange/view/bdcdsfalllog.html?type=loginall&interfaceId=" + interfaceId);
        }
    }

    /**
     * 更新发布状态
     * @param apiId
     * @param releaseStatus
     */
    function updateReleaseStatus(apiId, releaseStatus) {
        var param = {
            id: apiId,
            releaseStatus: releaseStatus
        };
        $.ajax({
            url: getContextPath() + "/rest/v1.0/apiManagement/releaseStatus/update",
            dataType: "json",
            type: 'POST',
            data: JSON.stringify(param),
            contentType: "application/json;charset=UTF-8",
            async: false,
            success: function (data) {
                if(releaseStatus === 0){
                    layer.msg('取消发布成功！');
                }else{
                    layer.msg('发布成功！');
                }
                var obj = {
                    "consumer" : formSelects.value('consumer', 'valStr'),
                    "name" :  $('#name').val(),
                    'url' : $('#url').val(),
                    "description" :  $('#description').val(),
                    "releaseStatus" : $("#releaseStatus").val()
                };
                // 重新请求
                table.reload("apiTable", {
                    where: obj
                    , page: {
                        curr: 1  //重新从第 1 页开始
                    }
                });
            },
            error: function (e) {
                layer.msg(e);
            }
        });
    }

    /**
     * 批量删除
     * @param param
     */
    function batchDeleteApi(param){
        console.log(param);
        if(!param || param.length === 0){
            layer.alert("请选择需要删除的记录！", {title: '提示'});
            return;
        }
        var apiIdList = [];
        for(var i = 0 ; i < param.length ; i++){
            if(param[i].releaseStatus === 1){
                layer.alert("接口名称为:【" + param[i].name + "】的接口已发布,不能删除!", {title: '提示'});
                return;
            }
            apiIdList.push(param[i].id);
        }
        deleteApi(apiIdList);
    }

    /**
     * 删除api接口
     * @param apiIdList
     */
    function deleteApi(apiIdList) {
        layer.confirm("该操作不可恢复，确认删除？", {
            title: "提示",
            btn: ["确认", "取消"],
            btn1: function (index2) {
                layer.close(index2);
                var param = {
                    ids: apiIdList,
                };
                $.ajax({
                    url: getContextPath() + "/rest/v1.0/apiManagement/delete",
                    dataType: "json",
                    type: 'POST',
                    data: JSON.stringify(param),
                    async: false,
                    contentType: "application/json;charset=UTF-8",
                    success: function (data) {
                        layer.msg('删除成功！');
                        var obj = {
                            "consumer" : formSelects.value('consumer', 'valStr'),
                            "name" :  $('#name').val(),
                            'url' : $('#url').val(),
                            "description" :  $('#description').val(),
                            "releaseStatus" : $("#releaseStatus").val()
                        };
                        // 重新请求
                        table.reload("apiTable", {
                            where: obj
                            , page: {
                                curr: 1  //重新从第 1 页开始
                            }
                        });
                    },
                    error: function (e) {
                        layer.msg(e);
                    }
                });
            },
            btn2: function (index2) {
                layer.close(index2);
            }
        });
    }

    /**
     * 复制接口
     * @param editType
     * @param apiId
     */
    function copyApi(editType,apiId) {
        addModel();
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/apiManagement/copy/interface?interfaceId="+apiId,
            type: "GET",
            contentType: "application/json;charset=UTF-8",
            success: function (data) {
                removeModal();
               if (data && data.success){
                   editApi(editType,data.data);
               }else {
                   if (data && !data.success){
                       layer.msg('复制接口失败:'+data.fail.message);
                   }else {
                       layer.msg('复制接口失败');
                   }
               }
            },
            error: function (xhr, status, error) {
                layer.msg(e);
            }
        });
    }


    /**
     * 页面跳转 0新增 1编辑 2查看
     * @param editType
     * @param apiId
     */
    function editApi(editType,apiId) {
        var url = "/realestate-inquiry-ui/view/config/jkgl/editApi.html";
        var title = "";
        // 新增
        if(editType === 0){
            url += "?editType=0";
            title = "新增接口";
        }
        // 编辑
        if(editType === 1){
            url += "?editType=1&apiId="+apiId;
            title = "编辑接口";
        }
        // 查看
        if(editType === 2){
            url += "?editType=2&apiId="+apiId;
            title = "查看接口";
        }
        var index = layer.open({
            type: 2,
            title: title,
            area: ['1150px','80%'],
            fixed: false, //不固定
            maxmin: true, //开启最大化最小化按钮
            content: [url,'yes'],
            success: function (layero, index) {
            },
            end: function (layero, index) {
                if(editType === 0 || editType === 1){
                    table.reload('apiTable');
                }
            }
        });
        layer.full(index);

    }

    /**
     * 导入接口配置
     */
    function importInterface() {
        $("#importFile").val("");
        $("#importFile").click();
    }

    /**
     * 导出接口配置
     */
    function exportInterface(param) {
        console.log(param);
        if(!param || param.length === 0){
            layer.alert("请选择需要导出的记录！", {title: '提示'});
            return;
        }
        var apiIdList = [];
        for(var i = 0 ; i < param.length ; i++){
            if(param[i].type !== 0){
                layer.alert("存在程序定义接口不支持导出，请核查！", {title: '提示'});
                return;
            }
            apiIdList.push(param[i].id);
        }
        $("#ids").val(apiIdList);
        $("#apiConfiguration").submit();
    }

    /**
     * 页面跳转 0新增 1编辑 2查看
     * @param editType
     * @param apiId
     */
    function additionalLearning() {
        var url = "/realestate-inquiry-ui/view/config/jkgl/editOldApi.html";
        var title = "补录接口";
        var index = layer.open({
            type: 2,
            title: title,
            area: ['1150px','80%'],
            fixed: false, //不固定
            maxmin: true, //开启最大化最小化按钮
            content: [url,'yes'],
            success: function (layero, index) {
            },
            end: function (layero, index) {
                if(editType === 0 || editType === 1){
                    table.reload('apiTable');
                }
            }
        });
        layer.full(index);

    }

    // 查看数据库日志
    function cksjkrz() {
        window.open("/realestate-exchange/view/bdcdsflog.html?type=logindb");
    }
    // 查看es日志
    function ckesrz() {
        window.open("/realestate-exchange/view/bdcdsflog.html?type=logines");
    }

    // 数据库日志统计
    function sjkrztj() {
        window.open("/realestate-exchange/view/dsfrz.html?type=logindb");
    }

    // es日志统计
    function esrztj() {
        window.open("/realestate-exchange/view/dsfrz.html?type=logines");
    }

    // 所有日志统计
    function allrztj(data) {
        if (!data) {
            window.open("/realestate-exchange/view/dsfrz.html?type=loginall");
        } else {
            var ids = [];
            data.forEach(function (item, index) {
                ids.push(item.id);
            });
            window.open("/realestate-exchange/view/dsfrz.html?type=loginall&interfaceIds=" + ids.join(","));
        }
    }

    /**
     * 点击查询
     */
    $('#query').on('click', function () {
        // 获取查询内容
        var consumer = formSelects.value('consumer', 'valStr');
        var name = $('#name').val();
        var url = $('#url').val();
        var description = $('#description').val();
        var releaseStatus = $("#releaseStatus").val();
        var obj = {
            "consumer" : consumer,
            "name" : name,
            'url' : url,
            "description" : description,
            "releaseStatus" : releaseStatus
        };
        // 重新请求
        table.reload("apiTable", {
            where: obj
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
        return false;
    });

    /**
     * 重新加载数据
     */
    window.reload = function () {
        table.reload("apiTable", {
            where: []
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
    };

});

/**
 * 获取消费方字典项
 * @param dictionary
 */
function getConsumer(dictionary) {
    var consumer = [];
    $.ajax({
        url: getContextPath() + "/rest/v1.0/apiManagement/zdMul",
        dataType: "json",
        data: {
            zdNames: dictionary
        },
        async: false,
        success: function (data) {
            var gxbmbz = data.gxbmbz;
            if(typeof gxbmbz == "undefined" || gxbmbz == null){
                return consumer;
            }
            for(var i = 0 ; i < gxbmbz.length ; i++){
                var data = {
                    "name":gxbmbz[i].MC,
                    "value":gxbmbz[i].DM
                };
                consumer.push(data);
            }
        },
        error: function (e) {}
    });
    return consumer;
}
/**
 * 转换字典值
 * @param key
 * @param dictionaryList
 * @returns {string|string}
 */
function convertConsumerDictionary(key,dictionaryList) {
    if (key) {
        for(var i = 0; i < dictionaryList.length; i++){
            var dictionary = dictionaryList[i];
            if (key === dictionary.value) {
                return dictionary.name;
            }
        }
    }
    return key;
}

/**
 * 导入接口配置
 */
function importTXT(fileObj) {
    addModel();
    $("#uploadFile").ajaxSubmit({
        type: 'post',
        url: '/realestate-inquiry-ui/rest/v1.0/apiManagement/import/interface?updateFlag=false',
        success: function (data) {
            removeModal();
            if (data && !data.success){
                if (data.data && data.data.updateIds){
                    // var msg = "存在" + data.data.updateIds + ",这些jkid已存在数据是否继续导入?"
                    var msg = "接口id【"+data.data.updateIds+"】已存在，是否继续导入";
                    layer.confirm(msg, {
                        title: "提示",
                        btn: ["确认", "取消"],
                        btn1: function (index2) {
                            layer.close(index2);
                            addModel();
                            $("#uploadFile").ajaxSubmit({
                                type: 'post',
                                url: '/realestate-inquiry-ui/rest/v1.0/apiManagement/import/interface?updateFlag=true',
                                success: function (data) {
                                    removeModal();
                                    var msg = "导入成功";
                                    if (data && !data.success && data.data && (data.data.addErrorIds || data.data.error)){
                                        if (data.data.addErrorIds){
                                            // msg = "存在" + Object.keys(data.data.addErrorIds) + ",这些jkid新增导入失败请核查";
                                            msg = "接口id【"+Object.keys(data.data.addErrorIds)+"】新增导入失败请核查";
                                            // console.log(JSON.stringify(data.data.addErrorIds));
                                        }
                                        if (data.data.error){
                                            if (msg && msg !== "导入成功"){
                                                msg = msg + '且' + data.data.error;
                                            }else {
                                                msg = data.data.error;
                                            }
                                        }
                                    }
                                    alertMsg(msg);
                                    // 重新请求
                                    if (msg === "导入成功"){
                                        window.location.reload();
                                    }
                                }, error: function (XmlHttpRequest, textStatus, errorThrown) {
                                    removeModal();
                                    alertMsg("导入失败！");
                                }
                            });
                        },
                        btn2: function (index2) {
                            layer.close(index2);
                            if (data && !data.success && data.data && (data.data.addErrorIds || data.data.error)){
                                if (data.data.addErrorIds){
                                    msg = "接口id【"+Object.keys(data.data.addErrorIds)+"】新增导入失败请核查";
                                    // console.log(JSON.stringify(data.data.addErrorIds));
                                }
                                if (data.data.error){
                                    if (msg && msg !== "导入成功"){
                                        msg = msg + '且' + data.data.error;
                                    }else {
                                        msg = data.data.error;
                                    }
                                }
                                alertMsg(msg);
                                // 重新请求
                                // window.location.reload();
                            }
                        }
                    });
                }else if (data && !data.success && data.data && (data.data.addErrorIds || data.data.error)){
                    if (data.data.addErrorIds){
                        msg = "接口id【"+Object.keys(data.data.addErrorIds)+"】新增导入失败请核查";
                        // console.log(JSON.stringify(data.data.addErrorIds));
                    }
                    if (data.data.error){
                        if (msg && msg !== "导入成功"){
                            msg = msg + '且' + data.data.error;
                        }else {
                            msg = data.data.error;
                        }
                    }
                    alertMsg(msg);
                    // 重新请求
                    // window.location.reload();
                }
            }else if (data && data.success){
                window.location.reload();
                alert("导入成功！");
            }
        },
        error: function (XmlHttpRequest, textStatus, errorThrown) {
            removeModal();
            alertMsg("导入失败！");
        }
    });
    return false;
}