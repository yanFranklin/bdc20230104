layui.config({
    base: '../../static/lib/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    selectN: 'select-extend/selectN'
});
//页面入口

var processInsId = getQueryString("processInsId");
var djxl = getQueryString("djxl");
var xmid = getQueryString("xmid");
var slbh = getQueryString("slbh");
var xmidListStr = getQueryString("xmidList");
// 流程类型，组合，批量，批量组合
var lclx = getQueryString("lclx");
var $,laytpl,form;
var qlrList;
var jjrSelect,sjrSelect;
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl','selectN'], function () {
    form = layui.form;
    laytpl = layui.laytpl;
    selectN = layui.selectN;
    $ = layui.jquery;
    addModel();
    loadYjxx();
    removeModal();
    //监听邮寄信息里的选择权利人，加载证件号和电话
    form.on('select(loadSjr)', function(data){
        if (qlrList && qlrList.length>0){
            $.each(qlrList, function (index, val) {
                var bdcQlrDO = val;
                // 权利人
                if (bdcQlrDO.qlrmc === data.value) {
                    $("#sjrzjh").val(bdcQlrDO.zjh);
                    $("#sjrlxdh").val(bdcQlrDO.dh);
                }
            });
        }

    });
    form.render('select');

    var result = verifyform1(form);
    //提交表单
    form.on("submit(updateYjxx)", function (data) {
        if (isNotBlank(result) && !result.isSubmit) {
            layer.msg(result.verifyMsg, {icon: 5, time: 3000});
            result.isSubmit = true;
            return false;
        } else {
            addModel();
            // 收件人有值

            var yjxx = {};
            yjxx.gzlslid = processInsId;
            yjxx.slbh = slbh;
            yjxx.djxl = djxl;
            yjxx.yjxxid = $('#yjxxid').val();
            yjxx.sjrmc = $('#sjrmc').val();
            yjxx.sjrzjh = $('#sjrzjh').val();
            yjxx.sjrlxdh = $('#sjrlxdh').val();
            yjxx.sjrxxdz = $('#sjrxxdz').val();
            var sjr =getSelectData(sjrSelect.names);
            yjxx.sjrszp = sjr.province;
            yjxx.sjrszc = sjr.city;
            yjxx.sjrszx = sjr.area;
            console.log(yjxx);
            // 保存
            getReturnData("/slym/yjxx/yj",JSON.stringify(yjxx),'POST',function (data) {
                removeModal();
                successCz();
                // 保存领证方式
                saveLzfs();

            },function (xhr) {
                delAjaxErrorMsg(xhr);
            })

            removeModal();

        }
        return false;
    });
});


function loadYjxx(){
    var yjxx = queryYjxx();
    qlrList = queryQlr();

    var json = {
        bdcQlrDOList: qlrList,
        bdcYjxxDO: yjxx
    };
    var tpl;
    var view;
    tpl = yjTpl.innerHTML;

    view = document.getElementById("bdc-popup-large");
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });
    form.render();
    // 邮寄信息为空且权利人无数据
    if(isNullOrEmpty(yjxx.yjxxid) && qlrList.length==0){
        layer.msg("请先添加权利人信息", {icon: 5, time: 3000});
    }
    removeModal();

    // 获取数据字典省市区县数据
    var ssqData = loadSelectData();
    //寄件人三级渲染
    sjrSelect = selectN({
        elem: '#sjrAddress',
        data: ssqData,
        last:true,
        tips: ['请选择省','请选择市','请选择区'],
        delimiter: ',',
        field:{idName:'name',titleName:'name',childName:'children'},
        formFilter: null,
        selected : [yjxx.sjrszp, yjxx.sjrszc, yjxx.sjrszx]
    });

}


function queryYjxx(){
    var yjxx ={};
    // 请求同步，不然加载有问题
    $.ajax({
        url: getContextPath() + "/slym/yjxx/xc?gzlslid=" + processInsId + "&djxl=" + djxl,
        type: 'GET',
        dataType: 'json',
        async: false,
        success: function (data) {
            // 邮寄信息只有一条
            yjxx=data;
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr);
        }
    });
    return yjxx;
}

function queryQlr(){
    var qlrArr = [];

    //权利人+领证人
    $.ajax({
        url: getContextPath() + "/slym/qlr/list/yjxx/sjr",
        type: 'GET',
        dataType: 'json',
        async: false,
        data: { xmid: xmid },
        success: function (data) {
            if (data) {
                qlrArr = data
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
    return qlrArr;
}


function cancel() {
    window.parent.cancelEdit();
}



/**
 * 提交成功操作
 */
function successCz(fun) {
    layer.msg('<img src="../../static/lib/bdcui/images/success-small.png" alt="">保存成功', {
        time: 1000
    }, function () {
        // 保存后加载，防止多次新增数据
        loadYjxx();
    });
    removeModal();
}


function verifyform1(form) {
    var result = {};
    //点击提交时不为空的全部标红
    var isSubmit = true;
    //验证提示信息
    var verifyMsg = "必填项不能为空";
    form.verify({
        required: function (value, item) {
            if (value == '') {
                $(item).addClass('layui-form-danger');
                isSubmit = false;
                verifyMsg = "必填项不能为空";
                result["isSubmit"] = isSubmit;
                result["verifyMsg"] = verifyMsg;

            }
        }
        , lxdh: function (value, item) {
            if (!validatePhone($.trim(value)) || value == '') {
                $(item).addClass('layui-form-danger');
                isSubmit = false;
                verifyMsg = "联系电话格式不正确";
                result["isSubmit"] = isSubmit;
                result["verifyMsg"] = verifyMsg;
            }
        }
    });
    return result;

}

function saveLzfs(){
    if (lclx === "zh"){
        if (isNullOrEmpty(xmid) || isNullOrEmpty(processInsId)){
            return;
        }
        saveLzfsZh();
    }else if(lclx === "pl"){
        if (isNullOrEmpty(processInsId)){
            return;
        }
        saveLzfsPl();
    }else if(lclx === "plzh"){
        if (isNullOrEmpty(processInsId) || isNullOrEmpty(xmidListStr)){
            return;
        }
        saveLzfsPlzh();
    }
}

function saveLzfsZh(){
    var bdcXmfbData = {
        gzlslid: processInsId,
        lzfs: "1",
        xmid: xmid
    };
    console.log(JSON.stringify(bdcXmfbData));
    getReturnData("/slym/xm/fb", JSON.stringify(bdcXmfbData), 'PATCH', function (data) {
        console.log("保存项目附表成功");
    }, function (err) {
        delAjaxErrorMsg(err);
    }, false);
}

function saveLzfsPl(){
    var bdcXmfbData = {
        lzfs: 1
    };
    var bdcDjxxUpdateQO = {};
    var whereMap = {};
    whereMap.gzlslid = processInsId;
    bdcDjxxUpdateQO.whereMap = whereMap;
    bdcDjxxUpdateQO.jsonStr = JSON.stringify(bdcXmfbData);
    console.log(JSON.stringify(bdcDjxxUpdateQO));
    getReturnData("/slym/xm/plxgxmfb", JSON.stringify(bdcDjxxUpdateQO), 'PATCH', function (data) {
        console.log("批量保存项目附表信息成功");
    }, function (err) {
        throw err;
    });
}

function saveLzfsPlzh(){
    var xmidList = xmidListStr.split(",");
    var bdcXmfbData = {
        lzfs: 1
    };
    var bdcDjxxUpdateQO = {};
    var whereMap = {};
    whereMap.gzlslid = processInsId;
    whereMap.xmids = xmidList;
    bdcDjxxUpdateQO.whereMap = whereMap;
    bdcDjxxUpdateQO.jsonStr = JSON.stringify(bdcXmfbData);
    console.log(JSON.stringify(bdcDjxxUpdateQO));
    getReturnData("/slym/xm/plxgxmfb", JSON.stringify(bdcDjxxUpdateQO), 'PATCH', function (data) {
        console.log("批量保存项目附表信息成功");
    }, function (err) {
        throw err;
    });
}

/**
 * 初始化加载选择框内容
 */
function loadSelectData() {
    var selectData = "";
    getDataByAjax('/slym/yjxx/init/zd','','GET', function(data){
        selectData = data;
    },false);
    return JSON.parse(selectData);
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
 * 获取select框方法
 */
function getSelectData(array){
    var data = {province : "",city : "",area : ""};
    switch(array.length){
        case 0:
            data;
            break;
        case 1:
            data.province = array[0];
            break;
        case 2:
            data.province = array[0];
            data.city = array[1];
            break;
        case 3:
            data.province = array[0];
            data.city = array[1];
            data.area = array[2];
            break;
    }
    return data;
}