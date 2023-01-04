/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @author <a href ="mailto:yaoyi@gtmap.cn">yaoyi</a> 2019年10月24日更新
 * @description 不动产异议
 */
layui.config({
    base: '../static/' //静态资源所在路径
}).extend({response:'lib/bdcui/js/response'});
layui.use(['table', 'laytpl', 'laydate', 'layer', 'form','jquery','laytpl','response'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer,
        formSelects = layui.formSelects,
        laydate = layui.laydate,
        laytpl = layui.laytpl,
        response = layui.response,
        form = layui.form;
    var UserCache = {};
    var RoleCache = {};
    // 当前页数据
    var currentPageData = [];
    // 保存当前选中的taskId
    var jsonArray = [];

    var BASE_URL = '/realestate-portal-ui/rest/v1.0/task/bdcYjd';

    var user = queryCurrentUser();

    formSelects.config('selectGzldymc', {
        keyName: 'name',            //自定义返回数据中name的key, 默认 name
        keyVal: 'key'            //自定义返回数据中value的key, 默认 value
    }, true);

    //绑定回车键
    $(document).keydown(function (event) {
        if (event.keyCode == 13) {
            $("#search").click();
        }
    });
    var kssjdy = laydate.render({
        elem: '#kssjdy',
        type: 'datetime',
        trigger: 'click',
        done: function (value, date) {
            //选择的结束时间大
            if ($('#kssjxy').val() != '' && !completeDate($('#kssjxy').val(), value)) {
                $('#kssjxy').val('');
                $('.laydate-disabled.layui-this').removeClass('layui-this');
            }
            kssjxy.config.min = {
                year: date.year,
                month: date.month - 1,
                date: date.date,
                hours: date.hours,
                minutes: date.minutes,
                seconds: date.seconds
            }
        }
    });
    var kssjxy = laydate.render({
        elem: '#kssjxy',
        type: 'datetime',
        trigger: 'click'
    });
    /**
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取工作流定义名称
     */
    $.ajax({
        url: BASE_URL + '/gzldymcs',
        type: "GET",
        dataType: "json",
        async:false,
        success: function (data) {
            if(data){
                formSelects.data('selectGzldymc','local',{arr:data});
            }
        },error:function(e){
            response.fail(e);
        }
    });
    /**
     * 加载Table数据列表
     */
    var reverseList = ['bdcdyh', 'zl'];
    table.render({
        elem: '#bdcYjdTable',
        toolbar: '#toolbar',
        title: '不动产移交单列表',
        defaultToolbar: ['filter'],
        url: BASE_URL+'/page',
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        cellMinWidth: 80,
        where: {},
        even: true,
        cols: [[
            {type: 'checkbox', fixed: 'left', width: 60},
            {field: 'id', title: 'id', hide: true},
            {field: 'processInstanceId', title: '工作流实例ID', hide: true},
            {field: 'slbh', title: '受理编号', minWidth: 120},
            {field: 'processDefName', title: '流程名称', minWidth: 200},
            {field: 'qlr', title: '权利人', minWidth: 100},
            {field: 'ywr', title: '义务人'},
            {
                field: 'bdcdyh', title: '不动产单元号', minWidth: 160,
                templet: function (d) {
                    return formatBdcdyh(d.bdcdyh);
                }
            },
            {field: 'zl', title: '坐落', minWidth: 150},
            {
                field: 'startTime', title: '收件时间',
                /*templet: function (d) {
                    return formatDate(d.startTime);
                }*/
                templet: function (d) {
                    return formatDate(new Date(d.startTime.replace(/-/g, "/")));
                }, sort: true,
                minWidth: 150
            },
            {
                field: 'endTime', title: '操作时间',
                templet: function (d) {
                    return formatDate(new Date(d.endTime.replace(/-/g, "/")));
                }, sort: true,
                minWidth: 150
            },
            {field: 'taskAssName', title: '操作者'},
            {field: 'bz', title: '备注'},
            {field: 'dyzt', title: '打印状态', hide: true},
            {field: 'dyzt', title: '打印状态', templet: '#dyztTpl'}

        ]],
        text: {
            none: '未查询到数据'
        },
        autoSort: false,
        page: true,
        parseData: function (res) {
            // 设置选中行
            if (res.content && res.content.length > 0 && jsonArray && jsonArray.length > 0) {
                res.content.forEach(function (v) {
                    $.each(jsonArray, function (key, value) {
                        if (value.taskId == v.taskId) {
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
        done: function (res, curr, count) {
            currentPageData = res.data;

            $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
            reverseTableCell(reverseList);
            if ($('.bdc-table-box .layui-table-main>.layui-table').height() == 0) {
                $('.layui-table-body .layui-none').html('<img src="../../static/lib/registerui/image/table-none.png" alt="">无数据');
            } else {
                $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
            }
        }
    });

    // 记录选中事件
    table.on('checkbox(bdcYjdTable)', function (obj) {
        // 获取选中或者取消的数据
        var data = obj.type == 'one' ? [obj.data] : currentPageData;

        if (obj.checked) {
            //.增加已选中项
            for (var index in data) {
                jsonArray.push(data[index]);
            }
        } else {
            //.删除
            for (var index in jsonArray) {
                data.forEach(function (v) {
                    if (jsonArray[index].taskId == v.taskId) {
                        jsonArray.splice(index, 1);
                    }
                });
            }
        }

        jsonArray = $.unique(jsonArray);
    });

    $('.bdc-table-box').on('mouseenter','td',function () {
        if($(this).text() && reverseList.indexOf($(this).attr("data-field")) != -1){
            $(this).append('<div class="layui-table-grid-down"><i class="layui-icon layui-icon-down"></i></div>');
        }
        $('.layui-table-grid-down').on('click',function () {
            setTimeout(function () {
                $('.layui-table-tips-main .bdc-table-date').html(reverseString($('.layui-table-tips-main .bdc-table-date').html()));
            },20);
        });
    });

    //头工具栏事件
    table.on('toolbar(bdcYjdTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'dy':dy(checkStatus.data);
                break;
            case 'bz':bz(checkStatus.data);
                break;
        }
    });

    /**
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 修改备注
     */
    function bz(data){
        if(!data || data.length != 1){
            layer.alert("请选择需要编辑的某一条记录！");
            return;
        }
        //小弹出层
        layer.open({
            title: '备注',
            type: 1,
            area: ['430px'],
            btn: ['保存', '取消'],
            content: $('#bdc-popup-small')
            ,yes: function(index, layero){
                //提交 的回调
                saveBz(data[0],index);

            }
            ,btn2: function(index, layero){
                //取消 的回调

            }
            ,cancel: function(index){
                //右上角关闭回调
                //return false 开启该代码可禁止点击该按钮关闭
                layer.close(index);

            }
            ,success: function () {

            }
        });
    }

    function saveBz(data,index){
        var map ={};
        //工作流实例ID
        map["PROC_INS_ID"] =data.processInstanceId;
        var bz=$('#bz').val();
        map["BZ"] = bz;

        $.ajax({
            url: BASE_URL + '/updateYjdBz',
            type: "POST",
            contentType: "application/json;charset=utf-8",
            dataType: "json",
            data: JSON.stringify(map),
            // async:false,
            success: function (data) {
                layer.close(index);
            }, error: function (e) {
                response.fail(e)
            },complete:function () {
                $('#bz').val('');
                reload()
            }
        });
    }

    /**
     * 监听排序事件
     */
    table.on('sort(bdcYjdTable)', function (obj) {
        table.reload('bdcYjdTable', {
            initSort: obj
            , where: {
                field: obj.field //排序字段
                , order: obj.type //排序方式
            }
        });
    });

    /**
     * 点击查询新方法
     */
    $('#search').on('click', function () {
        // 情况下行选择记录
        jsonArray = [];

        // 获取查询内容
        var gzldymcArr=formSelects.value('selectGzldymc');
        var orgArr = formSelects.value('selectOrg');
        var processDefName = "",orgId="";
        if(gzldymcArr.length!=0){
            processDefName =gzldymcArr[0].name;
        }
        if(orgArr.length!=0){
            orgId = orgArr[0].value;
        }
        var obj = {
            //taskName : "受理",
            processDefNames : getMultiSelectVal('selectGzldymc','name'),
            taskOrgId : orgId,
            roleIds : getMultiSelectVal('selectRole','id'),
            userNames : getMultiSelectVal('selectUser','id'),
        };
        $(".search").each(function (i) {
            var value = $(this).val();
            var name = $(this).attr('name');
            obj[name] = value;
        });
        console.info(obj);
        // 重新请求
        table.reload("bdcYjdTable", {
            where: obj
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
    });


    /**
     * 获取多选select框的值
     * @param selectorName 多选框选择器
     * @param type 指定多选框的值类型（name：名称，value：id）
     * @returns {string}
     */
    var getMultiSelectVal = function(selectorName,type){
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
    };

    /**
     * 重新加载数据
     */
    window.reload = function () {
        var obj = {};
        //obj["taskName"]="受理";
        $(".search").each(function (i) {
            var value = $(this).val();
            var name = $(this).attr('name');
            obj[name] = value;
        });
        table.reload("bdcYjdTable", {
            where: obj
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
    };

    //打印移交单
    function dy(data) {
        // 不用当前页选择数据，用每一页选择的数据
        if(!jsonArray || jsonArray.length == 0){
            layer.alert("请选择需要打印的记录！");
            return;
        }
        var dyList = [];
        $.each(jsonArray, function (key, val) {
            if (val.dyzt == 1) {
                dyList.push(val.slbh);
            }
        });
        filterPrintData(jsonArray).done(function(data){
            console.info(data);
            if(dyList.length>0){
                layer.confirm('受理编号为  '+dyList.join(",").toString()+'  已打印，确定要再次打印吗？', function (index) {
                    sessionStorage.setItem("yjddydata:"+user.id,JSON.stringify(data));
                    window.open("bdcYjdDy.html");
                    return false;
                });
            }else{
                sessionStorage.setItem("yjddydata:"+user.id,JSON.stringify(data));
                window.open("bdcYjdDy.html");
            }
        }).fail(function(){
            layer.alert("获取打印数据出错。");
        });

    }

    //获取抵押类业务的抵押权方式
    function filterPrintData(data){
        var deferred = $.Deferred();
        var dyqdj = [];
        $.each(data,function(index,value){
            if(value.processDefName.indexOf("抵押权")>-1){
                dyqdj.push(value.slbh);
            }
        });
        if(dyqdj.length == 0){
            $.each(data,function(index,value){
                data[index]['businessType'] = value.processDefName + (value.djyy === undefined ? '' : value.djyy);
            });
            return deferred.resolve(data);
        }
        $.ajax({
            url: BASE_URL+"/qllx/list",
            type: "POST",
            dataType: "json",
            contentType: 'application/json',
            data : JSON.stringify(dyqdj),
            success: function (result) {
                if(result){
                    $.each(data,function(index,value) {
                        data[index]['businessType'] = value.processDefName + (value.djyy === undefined ? '' : value.djyy);
                        var qllx = result[value.slbh];
                        if (qllx) {
                            $.each(qllx, function (i, qlxx) {
                                if (((value.bdcdyh).indexOf(qlxx.bdcdyh) > -1) && qlxx.dyfs) {
                                    data[index]['businessType'] = value.processDefName + result[qlxx.dyfs];
                                }
                            });
                        }
                    });
                    deferred.resolve(data);
                }else{
                    deferred.resolve([]);
                }
            },error:function(e){
                response.fail(e);
                deferred.reject(e);
            }
        });
        return deferred;
    }

    $('#seniorSearch').on('click',function () {
        $('.pf-senior-show').toggleClass('bdc-hide');
        $(this).parent().toggleClass('bdc-button-box-four');

        if($(this).html() == '高级查询'){
            $(this).html('收起');
        }else {
            $(this).html('高级查询');
        }
    });

    /**
     * 选择框数据初始化方法
     * @type {{appendUserCache: (function(*=): (*|*[])), appendRoleCache: (function(*=): (*|*[])), getData: (function(*=): (*|jQuery|{})), uniqueArray: uniqueArray}}
     */
    var SelectInit = {
        getData : function(url){
            var deferred =$.Deferred();
            $.ajax({
                url: url,
                type: "GET",
                dataType: "json",
                success: function (data) {
                    if(data){
                        deferred.resolve(data);
                    }else{
                        deferred.resolve([]);
                    }
                },error:function(e){
                    response.fail(e);
                    deferred.reject(e);
                }
            });
            return deferred;
        },
        appendUserCache : function(data){
            if(!$.isEmptyObject(UserCache)){
                for(var item in UserCache){
                    data.push.apply(data,UserCache[item]);
                }
            }
            return SelectInit.uniqueArray(data);
        },
        appendRoleCache : function(data){
            if(!$.isEmptyObject(RoleCache)){
                for(var item in RoleCache){
                    data.push.apply(data,RoleCache[item]);
                }
            }
            return SelectInit.uniqueArray(data);
        },
        uniqueArray : function (data) {
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
        },
    };

    formSelects.config('selectOrg',{keyName: 'name',keyVal: 'id'});
    formSelects.data('selectOrg','server',{
        url: BASE_URL + '/rootOrgs',
        tree:{
            nextClick: function(id,item,callback){
                var url = BASE_URL + '/childrenOrgs/'+item.value;
                SelectInit.getData(url).done(function(data){
                    var newData = [];
                    $.each(data, function (index, val) {
                        newData.push({
                            name: val.name,
                            value: val.id
                        });
                    });
                    callback(newData);
                });
            }
        },
    });
    formSelects.config('selectRole',{keyName: 'alias',keyVal: 'id'});
    formSelects.config('selectUser',{keyName: 'alias',keyVal: 'username'});
    //组织单选
    formSelects.on('selectOrg',function(id, vals, val, isAdd, isDisabled){
        if(isAdd){ //判断当前操作是添加还是删除，true为增加，false为删除
            formSelects.data('selectRole','server',{
                url: BASE_URL + '/roles/'+ val.value
            });
        }else {//置空角色与用户的值
            formSelects.data('selectRole', 'local', {arr: []});
            formSelects.data('selectUser', 'local', {arr: []});
            UserCache = {};
            RoleCache = {};
        }
    });
    formSelects.on('selectRole',function(id, vals, val, isAdd, isDisabled){
        var roleId = val.value;
        if(isAdd){//判断当前操作是添加还是删除，true为增加，false为删除
            SelectInit.getData(BASE_URL + '/users/'+ roleId).done(
                function(data){
                    //获取人员缓存信息，人员缓存不为空时，将缓存数据和已有数据同步返回给页面。
                    UserCache[roleId] = data;
                    formSelects.data('selectUser','local',{
                        arr : SelectInit.appendUserCache(data)
                    });
                }
            );
        }else{//置空用户的值
            delete UserCache[roleId];
            formSelects.data('selectUser','local',{
                arr: SelectInit.appendUserCache([])
            });
        }
    });

});