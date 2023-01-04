/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @description 证书、证明统计 js
 */
layui.extend({
    formSelects: '../../static/lib/form-select/formSelects-v4'
});
// 部门下拉数组
var myChart, tabName = "dyltj";
var BASE_URL = "/realestate-inquiry-ui/rest/v1.0";
var zdList = {bm: ""};// 定义字典信息全局变量
var bmZdList = [];
var formSelects;
// 部门下拉数组
var bmmcSelList = [];
// 人员下拉数组
var rymcSelList = [];
var zszmlxs = [];
// var UserCache= new Map(); 在zszmTj_selectTree中先定义
// 组织树多选值
var OrgTreeCheck = new Map();
layui.use(['form', 'jquery', 'laydate', 'element', 'table','formSelects'], function () {
    var $ = layui.jquery,
        table = layui.table,
        form = layui.form,
        laydate = layui.laydate,
        element = layui.element;
    formSelects = layui.formSelects;

    // 获取页面表单标识，用于权限控制
    var moduleCode = $.getUrlParam('moduleCode');
    if(moduleCode){
        setElementAttrByModuleAuthority(moduleCode);
    }
    // 加载所有组织
    loadAllOrgs();

    formSelects.config('selectBjry',{keyName: 'alias',keyVal: 'username'});
    // 加载多选目录树
    // loadMultiOrgTree("select_tree", function(event, treeId, treeNode){
    //     // 多选选中、取消事件
    //     if(treeNode.checked){
    //         OrgTreeCheck.set(treeNode.id, {id: treeNode.id, code: treeNode.code, name: treeNode.name});
    //         getAjaxData('/realestate-inquiry-ui/rest/v1.0/process/users/'+ treeNode.id).done(
    //             function(data){
    //                 //获取人员缓存信息，人员缓存不为空时，将缓存数据和已有数据同步返回给页面。
    //                 UserCache.set(treeNode.id, data);
    //                 formSelects.data('selectBjry','local',{
    //                     arr : getUserData()
    //                 });
    //             }
    //         );
    //     }else{
    //         OrgTreeCheck.delete(treeNode.id);
    //         UserCache.delete(treeNode.id);
    //         formSelects.data('selectBjry','local',{
    //             arr: getUserData()
    //         });
    //     }
    //
    //     var treeDiv = $("#" + treeId).parents(".layui-inline");
    //     if(OrgTreeCheck.size > 0){
    //         var checkedOrgName = [], checkedOrgCode = [];
    //         OrgTreeCheck.forEach(function(value, key){
    //             checkedOrgName.push(value.name);
    //             checkedOrgCode.push(value.code);
    //         });
    //         $(treeDiv).find("input[name=bmmc]").val(checkedOrgName.join(","));
    //         $(treeDiv).find("input[name=djbmdm]").val(checkedOrgCode.join(","));
    //     }else{
    //         $(treeDiv).find("input[name=bmmc]").val("");
    //         $(treeDiv).find("input[name=djbmdm]").val("");
    //     }
    // });

    // 日期控件
    laydate.render({
        elem: '#kssj',
        format: 'yyyy-MM-dd',
        value: new Date(),
        done: function (value, date, endDate) {
            var startDate = new Date(value).getTime();
            var endTime = new Date($('#jzsj').val()).getTime();
            if (endTime < startDate) {
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
            }
        }
    });
    laydate.render({
        elem: '#jzsj',
        format: 'yyyy-MM-dd',
        value: new Date(),
        done: function (value, date, endDate) {
            var startDate = new Date(value).getTime();
            var endTime = new Date($('#kssj').val()).getTime();
            if (endTime > startDate) {
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
            }
        }
    });

    $('#search').click(function () {
        if (tabName == "szltj") {
            szltj();
        } else if(tabName == "fzltj"){
            fzltj();
        } else if(tabName == "dyltj"){
            dyltj();
        } else if (tabName == 'yzhsyltj') {
            yzhsyltj();
        }
    });

    // 设置人员不可编辑
    $("#rymc").attr("disabled",true).css("pointer-events","none");
    $("#bjryDiv").attr("disabled",true).css("pointer-events","none");
    dyltj();
    // 打印量统计
    function dyltj() {
        zszmlxs = [];
        var searchData = getSearchData();

        for(var i=0;i<formSelects.value('selectZslx', 'value').length;i++){
            var item = formSelects.value('selectZslx', 'value')[i];
            var obj = {"zmlx":item.value, "zmmc":item.name};
            zszmlxs.push(obj);
        }
        if(zszmlxs.length == 0){
            zszmlxs = [{"zmlx":"1", "zmmc":"证书"},{"zmlx":"2", "zmmc":"证明"}];
        }

        initDytjChart(searchData);
    }

    // 印制号使用量统计
    function yzhsyltj() {
        var searchData= getSearchData();
        console.log(searchData);
        initYzhsyltjChart(searchData);
    }

    // 打印量统计
    function initDytjChart(searchData){
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/zszm/print/count",
            type: 'POST',
            data: JSON.stringify(searchData),
            contentType: 'application/json',
            dataType: "json",
            success: function (res) {
                console.log(res);
                if(res && res.keySet && res.valueMap) {
                    var orgNameSet = getOrgName(res.keySet);
                    // 设置要展示的数据
                    var xData = [];
                    $.each(zszmlxs, function (index, value) {
                        var data = [];
                        $.each(res.valueMap[value.zmlx], function (index, value) {
                            if (value == 0) {
                                data.push("");
                            } else {
                                data.push(value);
                            }
                        });

                        xData.push({
                            'name': value.zmmc, 'type': 'bar', stack: '总量', barWidth : 40,
                            label: {
                                normal: {
                                    show: true,
                                    position: 'insideRight'
                                }
                            }, data: data
                        })
                    });

                    var array = new Array();
                    $.each(zszmlxs, function (index, item) {
                        array.push(item.zmmc);
                    })

                    var option = {
                        title: {
                            text: "不动产证书、证明打印数量统计",
                            x: 'center',
                            textStyle: {
                                fontSize: 16,
                                fontWeight: "400",
                                fontWeight: 'bold',
                                fontFamily: "Microsoft YaHei",
                                color: "#333"
                            },
                            subtextStyle: {
                                fontSize: 12
                            }
                        },
                        tooltip: {
                            trigger: 'axis',
                            axisPointer: {
                                type: 'shadow'
                            }
                        },
                        legend: {
                            data: ['证书', '证明'],
                            top: "10",
                            right: "50"
                        },
                        grid: {
                            left: '3%',
                            right: '4%',
                            bottom: '3%',
                            containLabel: true
                        },
                        xAxis: {
                            type: 'value'
                        },
                        yAxis: {
                            type: 'category',
                            boundaryGap: [0, 0.2],
                            data: orgNameSet
                        },
                        series: xData
                    };

                    var myChart = echarts.init(document.getElementById('dysltjChart'), "light");
                    myChart.setOption(option, true);
                    removeModal();
                    // 设置缓存，用于导出Excel
                    setZsSessionData(orgNameSet, res);
                } else {
                    sessionStorage.setItem("dyltj", null);
                    warnMsg("未查询到证书、证明打印数据！");
                }
            },
            error: function (e) {
                removeModal();
            }
        });
    }

    // 印制号使用量统计
    function initYzhsyltjChart(searchData){
        searchData.order = "ysy";
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/yzh/fjltj",
            type: 'POST',
            data: JSON.stringify(searchData),
            contentType: 'application/json',
            dataType: "json",
            success: function (res) {
                if(isNotBlank(res)) {
                    // 设置要展示的数据
                    var ryData = new Array();
                    var ysyData = new Array();
                    $.each(res, function(index, value){
                        ryData.push(value.syr);
                        ysyData.push(isNotBlank(value.ysy)? value.ysy:0);
                    });

                    // 动态设置柱状图y轴的长度
                    var setHeight = 40;
                    if(res.length > 2){
                        var autoHeight = res.length * setHeight;
                        $('#yzhsyltjChart').height(autoHeight);
                    }

                    var option = {
                        title: {
                            text: "印制号使用量统计",
                        },
                        tooltip: {
                            trigger: 'axis',
                            axisPointer: {
                                type: 'shadow'
                            }
                        },
                        legend: {
                            data: [],
                        },
                        grid: {
                            bottom: '30',
                            top: '50px',
                            containLabel: true,
                            left: 30,
                            right: 110
                        },
                        xAxis: {
                            type: 'value'
                        },
                        yAxis: {
                            type: 'category',
                            boundaryGap: [0, 0.2],
                            data: ryData,
                        },
                        series: [{
                            'type':'bar',stack: 'sum',
                            label: {
                                normal: {
                                    show: true,
                                    position: 'right'
                                }
                            },
                            data: ysyData,
                            itemStyle:{
                                normal:{color: '#2e7ce9'}
                            },
                            barMaxWidth: 50,
                            barMinWidth:25
                        }]
                    };

                    var myChart = echarts.init(document.getElementById('yzhsyltjChart'), "light");
                    myChart.setOption(option, true);
                    removeModal();
                    // 设置缓存，用于导出Excel
                    setYzhSessionData(res);
                } else {
                    sessionStorage.setItem("yzhsyltj", null);
                    warnMsg("未查询到印制号使用数据！");
                }
            },
            error: function (e) {
                removeModal();
            }
        });
    }

    // 设置打印量缓存数据
    function setZsSessionData(orgNameSet, res){
        var data = new Array();
        $.each(orgNameSet, function (index, value) {
            $.each(zszmlxs, function (idx, val) {
                data.push({"djjg": value, "zslxmc": val.zmmc, "sl": getCount(res.valueMap[val.zmlx], index)});
            });
        });
        sessionStorage.setItem("dyltj", JSON.stringify(data));
    }
    // 设置印制号使用量缓存数据
    function setYzhSessionData(res){
        var data = new Array();
        $.each(res, function (index, value) {
            data.push({"ry": value.syr, "sl": value.ysy});
        });
        sessionStorage.setItem("yzhsyltj", JSON.stringify(data));
    }

    function getCount(zsValueMap, index){
        var zscount = 0;
        $.each(zsValueMap, function (idx, val) {
            if(index == idx){
                zscount = val;
            }
        });
        return zscount;
    }

    $('#export').click(function () {
        if(tabName == "yzhsyltj"){
            exportYzhExcel(sessionStorage.getItem(tabName));
        }else{
            exportExcel(sessionStorage.getItem(tabName));
        }
    });

    $("#exportSz").click(function(){
        exportSz();
    });
    function exportExcel(data){
        // 标题
        var showColsTitle = ["部门","证书类型","数量"];
        // 列内容
        var showColsField = ["djjg","zslxmc","sl"];
        // 列宽
        var showColsWidth = ["50","30","30"];

        var title = '不动产证书、证明、证明单缮证量统计';
        if(tabName == "fzltj"){
            title = '不动产证书、证明、证明单发证量统计';
        } else if(tabName == "dyltj"){
            title = '不动产证书、证明、证明单打印量统计';
        }

        // 设置Excel基本信息
        $("#fileName").val(title);
        $("#sheetName").val(title);
        $("#cellTitle").val(showColsTitle);
        $("#cellWidth").val(showColsWidth);
        $("#cellKey").val(showColsField);
        $("#data").val(data);
        $("#form").submit();
    }
    function exportYzhExcel(data){
        // 标题
        var showColsTitle = ["人员","数量"];
        // 列内容
        var showColsField = ["ry","sl"];
        // 列宽
        var showColsWidth = ["50","30"];

        var title = '印制号使用量统计';

        // 设置Excel基本信息
        $("#fileName").val(title);
        $("#sheetName").val(title);
        $("#cellTitle").val(showColsTitle);
        $("#cellWidth").val(showColsWidth);
        $("#cellKey").val(showColsField);
        $("#data").val(data);
        $("#form").submit();
    }

    function exportSz(){
        var searchData = {};
        var rymc = getMultiSelectVal('selectBjry','name');
        if(isNotBlank(rymc)){
            searchData["rymc"] = getMultiSelectVal('selectBjry','name');
        }
        var zslx = getMultiSelectVal('selectZslx','name');
        if(isNotBlank(zslx)){
            searchData["zslx"] = getMultiSelectVal('selectZslx','value');
        }
        var djbmmc = $("input[name='bmmc']").val();
        searchData["djbmmc"] = djbmmc;
        $(".search").each(function (i) {
            var value = $(this).val();
            var name = $(this).attr('name');

            if("kssj" == name && !isNullOrEmpty(value)){
                value += " 00:00:00";
            }
            if("jzsj" == name && !isNullOrEmpty(value)){
                value += " 23:59:59";
            }
            if(isNotBlank(value)){
                searchData[name] = value;
            }
        });

        console.log("searchData:"+searchData);
        var tempForm = $("<form>");
        tempForm.attr("id", "exportFrom");
        tempForm.attr("style", "display:none");
        tempForm.attr("target", "export");
        tempForm.attr("method", "post");
        tempForm.attr("action", '/realestate-inquiry-ui/rest/v1.0/zszm/getSzgzlExcel');

        var dataInput = $("<input>");
        dataInput.attr("type", "hidden");
        dataInput.attr("name", "paramJson");
        dataInput.attr("value", encodeURIComponent(JSON.stringify(searchData)));
        tempForm.append(dataInput);
        var exportCol = {
            姓名:"name",
            业务办理总量:"ywblzl",
            证书打印业务量:"zsdyywl",
            证书印制号领用量:"zsyzhlyl",
            证书印制号使用量:"zsyzhsyl",
            证书印制号作废量:"zsyzhzfl",
            证明打印业务量:"zmdyywl",
            证明印制号领用量:"zmyzhlyl",
            证明印制号使用量:"zmyzhsyl",
            证明印制号作废量:"zmyzhzfl"
        };
        var dataInput = $("<input>");
        dataInput.attr("type", "hidden");
        dataInput.attr("name", "exportCol");
        dataInput.attr("value", encodeURIComponent(JSON.stringify(exportCol)));
        tempForm.append(dataInput);


        tempForm.on("submit", function () {
        });
        tempForm.trigger("submit");
        $("body").append(tempForm);
        tempForm.submit();
        $("exportFrom").remove();
        return false;
    }
    // tab页切换事件
    element.on('tab(tabFilter)', function () {
        tabName = this.getAttribute('lay-id');
        tabChangeEvent(tabName,table);
    });

    /**
     * Tab页切换
     */
    function tabChangeEvent(tab, table) {
        $("#rymc").removeAttr("disabled").css("pointer-events","auto");
        $("#bjryDiv").removeAttr("disabled").css("pointer-events","auto");
        $("#zslx").removeAttr("disabled").css("pointer-events","auto");
        $("#zslxDiv").removeAttr("disabled").css("pointer-events","auto");
       if (tab == "szltj") {
           szltj();
       } else if(tab == "fzltj"){
            fzltj();
       } else if(tab == "dyltj"){
           $("#rymc").attr("disabled",true).css("pointer-events","none");
           $("#bjryDiv").attr("disabled",true).css("pointer-events","none");
           dyltj();
       } else if (tab == 'yzhsyltj') {
           $("#zslx").attr("disabled",true).css("pointer-events","none");
           $("#zslxDiv").attr("disabled",true).css("pointer-events","none");
           yzhsyltj();
       }
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

function getAjaxData(url){
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
            console.info(e);
            deferred.reject(e);
        }
    });
    return deferred;
}
function getSearchData(){
    var startDate = new Date($('#kssj').val()).getTime();
    var endTime = new Date($('#jzsj').val()).getTime();
    if (endTime < startDate) {
        warnMsg('结束时间不能小于开始时间');
        return;
    }
    var searchData = {};
    var rymc = getMultiSelectVal('selectBjry','name');
    if(isNotBlank(rymc)){
        searchData["rymc"] = getMultiSelectVal('selectBjry','name');
    }
    var zslx = getMultiSelectVal('selectZslx','name');
    if(isNotBlank(zslx)){
        searchData["zslx"] = getMultiSelectVal('selectZslx','value');
    }
    $(".search").each(function (i) {
        var value = $(this).val();
        var name = $(this).attr('name');

        if("kssj" == name && !isNullOrEmpty(value)){
            value += " 00:00:00";
        }
        if("jzsj" == name && !isNullOrEmpty(value)){
            value += " 23:59:59";
        }
        if(isNotBlank(value)){
            searchData[name] = value;
        }
    });
    if(!isNotBlank(searchData)){
        warnMsg("请输入查询条件!");
        return;
    }
    return searchData;
}

function getUserData(){
    var data = new Array();
    if(UserCache.size !=0 ){
        UserCache.forEach(function(key){
            data.push.apply(data, key);
        });
    }
    var list = uniqueArray(data);
    console.info(list);
    return list;
}

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


// 渲染部门
function loadAllOrgs(){
    var deferred = $.Deferred();
    $.ajax({
        url: '/realestate-inquiry-ui/bdczd/org/list',
        type: "GET",
        async: false,
        dataType: "json",
        timeout: 10000,
        success: function (data) {
            if (data) {
                $.each(data, function (index, value) {
                    bmZdList[value.code] = value.name;
                });
                deferred.resolve();
            }
        }
    });
    return deferred;
}

function getOrgName(key){
    var orgNameSet = new Array();
    if(bmZdList.length == 0){
        loadAllOrgs().done(function(){
            $.each(res.keySet, function (index, value) {
                orgNameSet.push(bmZdList[value]);
            });
        });
    }else{
        $.each(key, function (index, value) {
            orgNameSet.push(bmZdList[value]);
        });
    }
    return orgNameSet;
}