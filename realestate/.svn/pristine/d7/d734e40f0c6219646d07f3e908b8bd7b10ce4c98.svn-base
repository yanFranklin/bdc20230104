var cxtjData = [];
var cxjgData = [];
var cxxx = {};
var cxsql = '';
var pzjs = '';
var cxdh = '';
/*标识查询代号是否已通过检查*/
var dhPassCheck = false;
/*标识是不是新增查询*/
var newCx = true;
/*标识是不是复制查询*/
var fuzhiCx = false;

layui.use(['layer', 'table', 'jquery', 'form', 'element', 'upload'], function () {
    addModel();

    var layer = layui.layer;
    var $ = layui.jquery;
    var table = layui.table;
    var form = layui.form;
    var element = layui.element;
    var upload = layui.upload;

    //基础设置模糊查询，若不支持，则查询条件中可切换模糊查询为否，默认模糊类型为精确，并且设置不可编辑
    var canMhcx = $("#canMhcx").is(':checked');
    if (!canMhcx) {
        $("select[name=canmhcx]").val(0);
        $("select[name=mrmhlx]").val(1);
        $("select[name=canmhcx]").attr('disabled', 'off');
        $("select[name=mrmhlx]").attr('disabled', 'off');
    }

    //基础设置 模糊查询监听
    form.on('switch(canMhcxSwitch)', function(data){
        if (!data.elem.checked) {
            //若不支持，则查询条件中可切换模糊查询为否，默认模糊类型为精确，并且设置不可编辑
            $("select[name=canmhcx]").val(0);
            $("select[name=mrmhlx]").val(1);
            $("select[name=canmhcx]").attr('disabled', 'off');
            $("select[name=mrmhlx]").attr('disabled', 'off');
        } else {
            $("select[name=canmhcx]").removeAttr("disabled", "off");
            $("select[name=mrmhlx]").removeAttr("disabled", "off");
        }
        form.render('select');
    });


    $(function () {
        cxdh = $.getUrlParam('cxdh');
        fuzhiCx = $.getUrlParam('fuzhiCx');
        if (!isNullOrEmpty(cxdh)) {
            /*cxdh不为空，表明不是新增操作*/
            newCx = false;
            dhPassCheck = true;
            $.ajax({
                url: "/realestate-inquiry-ui/dtcx/get/" + cxdh,
                type: "get",
                async: true,
                success: function (data) {
                    cxtjList = data.cxtjDOList;
                    cxjgList = data.cxjgDOList;

                    /*赋值查询基本信息*/

                    var options = {jsonValue: data, exclude: [], isDebug: false};

                    // 复制时候进行值处理 cxdh和cxmc置空
                    if (fuzhiCx) {
                        newCx = true;
                        dhPassCheck = false;
                        options.jsonValue.cxmc = "";
                        options.jsonValue.cxdh = "";
                    }
                    $("#cxxx").initForm(options);
                    $("#zdyymgj").val(data.zdyymgj);
                    $("#zdyjs").val(data.js);
                    $("#fxkys").val(data.fxkys);
                    pzjs = data.pzjs;
                    /*填充sql框并解密*/
                    if(!isNullOrEmpty(data.cxsql)){
                        $("#sql").val(Base64.decode(data.cxsql));
                    }
                    if (!isEmptyObject(cxtjList)) {
                        $.each(cxtjList, function (i, cxtj) {
                            addCxtjRow(cxtj.tjid);
                            $("#" + cxtj.tjid).initForm({jsonValue: cxtj, exclude: [], isDebug: false});
                        });
                    } else {
                        addCxtjRow();
                    }

                    /*处理复选框*/
                    var ymgj = data.ymgj;
                    var mhcx = data.canmhcx;
                    var ymgjCheckBox = $("#ymgj-item").find("input");
                    $.each(ymgjCheckBox, function (i, checkBox) {
                        if (ymgj && ymgj.indexOf(checkBox.name) != -1) {
                            checkBox.checked = true;
                        } else {
                            checkBox.checked = false;
                        }
                    });

                    var mhcxCheckBox = $("#mhcx-item").find("input");
                    $.each(mhcxCheckBox, function (i, checkBox) {
                        if (mhcx && mhcx === 1) {
                            checkBox.checked = true;
                        } else {
                            checkBox.checked = false;
                        }
                    });

                    if (!isEmptyObject(cxjgList)) {
                        $.each(cxjgList, function (i, cxjg) {
                            addCxjgRow(cxjg.jgid);
                            $("#" + cxjg.jgid).initForm({jsonValue: cxjg, exclude: [], isDebug: false});
                        });
                        form.render();
                    } else {
                        addCxjgRow();
                    }
                    setTimeout(removeModal(), 100);
                },
                error: function (e) {
                    showErrorInfo(e);
                }
            })
        } else {
            $("#checkCxdh-btn").show();
            addCxtjRow();
            addCxjgRow();
            setTimeout(removeModal(), 100);
        }

        //js上传
        upload.render({
            elem: '#jsUpload'
            , url: '/realestate-inquiry-ui/dtcx/config/upload/js' //上传接口
            , accept: 'file' //普通文件
            , done: function (res) {
                console.log(res);
                if (res && res.downUrl) {
                    var downUrl = res.downUrl;
                    if(downUrl.toLowerCase().indexOf("/storage") == 0) {
                        // 以/storage开头的加上缺省的IP、端口
                        downUrl = document.location.protocol + "//" + window.location.host + downUrl;
                    }
                    if($("#js").val()==""){
                        $("#js").val("[\""+downUrl+"\"]");
                        successMsg('上传成功');
                    }else{
                        if($("#js").val().indexOf("]") >= 0){
                            var js=$("#js").val().replace("]", ",\""+downUrl+"\"]");
                            $("#js").val(js);
                            successMsg('上传成功');
                        }else{
                            warnMsg("自定义格式错误！正确格式[\"../../static/js/bengbu/zdycx/djls.js\",\"../../static/js/dtcx/drExcelCx.js\"]");
                        }
                    }

                } else {
                    errorsMsg("上传失败！");
                }
            }
        });

        /*代号输入框改变时，将dhPassCheck置为false*/
        $("#cxdh").change(function () {
            dhPassCheck = false;
        });


        $(".cxjgType").change(function () {
            renderCxjg();
        });
    });

});

/**
 * 验证SQL输入是否合理
 */
function checkSql(sql) {
    var checksql = sql.replace(/\s+/g, "");
    if (isNullOrEmpty(checksql)) {
        layui.layer.msg("请配置sql语句");
        return false;
    }
    // hy 2018-09-28 修改check条件
    if (sql.indexOf("update") > 0 || sql.indexOf("delete") > 0 || sql.indexOf("insert") > 0 || sql.indexOf("truncate") > 0 || sql.indexOf("drop") > 0) {
        // hy 2018-09-28 修改check条件
        return false;
    } else {
        return true;
    }
}

/**
 * 点击查询条件中的保存查询条件按钮事件
 */
function checkCxtj() {
    var result = false;
    var check = true;
    $.each(cxtjData, function (i, cxtj) {
        if (cxtj.zddyfs === '') {
            layui.layer.msg("字段对应方式不应为空");
            check = false;
        }

        if (cxtj.tjzdid === '') {
            layui.layer.msg("查询条件代码不应为空");
            check = false;
        }

        if (cxtj.tjzdname === '') {
            layui.layer.msg("查询条件名称不应为空");
            check = false;
        }

        if (cxtj.canmhcx == '1' && (cxtj.tjtype !== 'text')) {
            layui.layer.msg("支持模糊查询切换时，控件类型应该为text");
            check = false;
        }

        if (cxtj.canmhcx == '1' && (cxtj.zddyfs.indexOf(' #{=} ') < 0 || cxtj.zddyfs.indexOf('%') >= 0)) {
            layui.layer.msg("支持模糊查询切换时，字段对应方式格式为：and 字段 #{=} " + "#" + "{字段}");
            check = false;
        }
        // 当查询条件类型为身份证号 或 批量查询 或区县代码过滤时 查询语句必须为in
        if ((cxtj.tjtype === 'id' || cxtj.tjtype === 'plcx' ||cxtj.tjtype ==="qxdmgl") && (cxtj.zddyfs.indexOf(' in ') < 0)) {
            layui.layer.msg("对于身份证号 和 批量查询 和 区县代码过滤 查询语句必须为in");
            check = false;
        }
        //区县代码过滤默认不展示
        if (cxtj.tjtype ==="qxdmgl"&& (cxtj.mrxs ==='1')) {
            layui.layer.msg("对于区县代码过滤 是否显示 必须为否");
            check = false;
        }

    });
    if (check === true) {
        $.ajax({
            url: '/realestate-inquiry-ui' + "/dtcx/check/config/cxtj",
            type: "post",
            async: false,
            data: {
                cxsql: Base64.encode(cxsql),
                cxtj: JSON.stringify(cxtjData)
            },
            success: function (data) {
                if (data.result !== '成功') {
                    var msg = data.result;
                    var errorTjidList = data.errorId;
                    if (!isEmptyObject(errorTjidList)) {
                        for (var i = 0; i < errorTjidList.length; i++) {
                            msg = msg + errorTjidList[i] + " ";
                        }
                    }
                    layui.layer.msg(data.result);
                    result = false;
                } else {
                    result = true;
                }
            }
        });

        return result;
    } else {
        return check;
    }
}

/**
 * 点击查询条件中的保存查询条件按钮事件
 */
function checkCxjg() {
    var result = true;
    // 存在翻页后无法找到数据的情况
    if (cxjgData.length === 0) {
        layui.layer.msg("还未设置查询结果！");
        return false;
    }

    $.each(cxjgData, function (i, cxjg) {

        if (cxjg.jgzdid === '') {
            layui.layer.msg("查询结果代码不应为空");
            result = false;
        }

        if (cxjg.jgzdname === '') {
            layui.layer.msg("查询结果名称不应为空");
            result = false;
        }
    });

    if (result === true) {
        $.ajax({
            url: '/realestate-inquiry-ui' + "/dtcx/check/config/cxjg",
            type: 'post',
            dataType: 'json',
            async: false,
            data: {cxsql: Base64.encode(cxsql), cxjg: JSON.stringify(cxjgData)},
            success: function (data) {
                if (data.result !== '成功') {
                    var msg = data.result;
                    var errorJgidList = data.errorId;
                    if (!isEmptyObject(errorJgidList)) {
                        for (var i = 0; i < errorJgidList.length; i++) {
                            msg = msg + errorJgidList[i] + " ";
                            /*$('#' + errorJgidList[i]).find("td").css("background-color", "pink");*/
                        }
                    }
                    layui.layer.msg(data.result);
                    result = false;
                } else {
                    return true;
                }
            }
        });
    }
    return result;
}

/**
 * 保存按钮点击事件
 * @date 2019.03.26 16:43
 * @author hanyaning
 * @param null
 * @return
 */
function saveAction() {
    addModel();
    // 获取查询条件数据
    cxtjData = getFormRows("cxtjForm");
    cxjgData = getFormRows("cxjgForm");
    cxsql = $("#sql").val();
    var cxmc = $("#cxmc").val();
    // 添加查询名称为空判断
    if (cxmc === '') {
        layui.layer.msg("查询名称不应为空");
        removeModal();
        return false;
    }
    if (checkSql(cxsql) && checkCxtj() && checkCxjg()) {
        if (dhPassCheck) {
            saveAll();
        } else {
            layer.msg("请先检查查询代号是否可用");
            removeModal();
        }
    } else {
        setTimeout(removeModal(), 2000)
    }
    return false;
}

/*
 * 取消操作*/
function cancelAndClose() {
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    parent.layer.close(index);
}

/**
 * 保存所有配置项
 * @date 2019.03.20 17:28
 * @author hanyaning
 * @param
 * @return
 */
function saveAll() {
    cxdh = $("#cxdh").val();

    /*获取工具配置*/
    var ymgjList = $("#ymgj-item").find("input");
    var ymgjCheckedList = [];
    for (var i = 0; i < ymgjList.length; i++) {
        if (ymgjList[i].checked == true) {
            ymgjCheckedList.push(ymgjList[i]);
        }
    }

    var mhcxCheckBox = $("#mhcx-item").find("input");
    var mhcx = 0;
    $.each(mhcxCheckBox, function (i, checkBox) {
        if (checkBox.checked === true) {
            mhcx = 1;
        }
    });
    var ymgj = [];
    var tempObject = {title: "", name: ""};

    if (!isEmptyObject(ymgjCheckedList)) {
        $.each(ymgjCheckedList, function (i, gj) {
            tempObject = {title: "", name: ""};
            tempObject["title"] = gj.title;
            tempObject["name"] = gj.name;
            ymgj.push(tempObject);
        });
    }

    $.ajax({
        url: '/realestate-inquiry-ui' + "/dtcx/save/all?" + $("#cxxx").serialize(),
        type: "post",
        async: true,
        data: {
            cxsql: Base64.encode(cxsql),
            pzjs: pzjs,
            cxtj: JSON.stringify(cxtjData),
            cxjg: JSON.stringify(cxjgData),
            ymgj: JSON.stringify(ymgj),
            mhcx: mhcx,
            fuzhiCx: fuzhiCx != null ? fuzhiCx : false
        },
        success: function (data) {
            var index = parent.layer.getFrameIndex(window.name);
            setTimeout(removeModal(), 100);
            parent.layui.table.reload('pageTable', {page: {curr: 1}});
            parent.layer.msg("保存成功");
            parent.layer.close(index)
        },
        error: function (e) {
            showErrorInfo(e);
        }
    })
}

/**
 * 新增一行
 * @param ele
 */
function addCxtjRow(tjid) {
    layui.use(['laytpl', 'form'], function () {
        var laytpl = layui.laytpl;
        var form = layui.form;
        laytpl(cxtjTemplet.innerHTML).render({
            tjid: tjid
        }, function (html) {
            $('#cxtjTable').append($(html));
        });
        //基础设置模糊查询，若不支持，则查询条件中可切换模糊查询为否，默认模糊类型为精确，并且设置不可编辑
        var canMhcx = $("#canMhcx").is(':checked');
        if (!canMhcx) {
            $("select[name=canmhcx]").val(0);
            $("select[name=mrmhlx]").val(1);
            $("select[name=canmhcx]").attr('disabled', 'off');
            $("select[name=mrmhlx]").attr('disabled', 'off');
        } else {
            $("select[name=canmhcx]").removeAttr('disabled', 'off');
            $("select[name=mrmhlx]").removeAttr('disabled', 'off');

        }
        form.render();
    });
}

function addCxjgRow(jgid) {
    layui.use(['laytpl', 'form'], function () {
        var laytpl = layui.laytpl;
        var form = layui.form;
        laytpl(cxjgRowTemplet.innerHTML).render({
            jgid: jgid
        }, function (html) {
            $('#cxjgTable').append($(html));
        });
        form.render();
    });
}

/**
 * 删除一行
 * @param ele
 */
function removeConditionRow(ele) {
    if ($('#cxtjTable').find("tr").length > 2) {
        $(ele).closest("tr").remove();
    }

}

function removeCxjgRow(ele) {
    if ($('#cxjgTable').find("tr").length > 1) {
        $(ele).closest("tr").remove();
    }

}

/**
 * 将form中table的多个tr转换为数组
 * tr中的form组件需要有一致的name属性
 * @date 2019.03.20 17:23
 * @author hanyaning
 * @param formId 对应form的id
 * @return 对象数组
 */
function getFormRows(formId) {
    var resultData = [];
    var formData = $('#' + formId).serializeArray();
    var tempObj = {};
    var pri = 1;
    $.each(formData, function (index, item) {
        if (!tempObj[item.name]) {
            tempObj[item.name] = item.value;
        } else {
            if (isEmptyObject(tempObj["priority"])) {
                tempObj["priority"] = pri;
            }
            resultData.push(tempObj);
            pri += 1;
            tempObj = {};
            tempObj[item.name] = item.value;
        }
    });
    if (isEmptyObject(tempObj["priority"])) {
        tempObj["priority"] = pri;
    }
    resultData.push(tempObj);
    return resultData;

}

function checkCxdhIsUsed(cxdh) {
    var url = "/realestate-inquiry-ui/dtcx/check/cxdh?cxdh=" + cxdh;
    addModel();
    $.ajax({
        url: url,
        type: 'get',
        success: function (data) {
            setTimeout(removeModal(), 100);
            if (data == "1") {
                layer.msg("代号检测通过，可正常使用");
                dhPassCheck = true;
            } else if (data == "0") {
                layer.msg("代号已被使用，请查看是否已有类似查询功能");
                dhPassCheck = false;
            } else {
                layer.msg("检查失败");
                dhPassCheck = false;
            }
        },
        error: function (e) {
            dhPassCheck = false;
            showErrorInfo(e);

        }
    })
}

/**
 * 分新增和更新不同情况都用此方法检查
 * @date 2019.03.26 19:43
 * @author hanyaning
 * @return 检查代号是否可用
 */
function checkCxdh() {
    var cxdh = $("#cxdh").val();
    if (isNullOrEmpty(cxdh)) {
        layer.msg("请先输入..");
        return false;
    }
    var rep = new RegExp("^[a-z]+[a-z0-9]*$", "g")
    if (!rep.test(cxdh)) {
        layer.alert("代号只能包含英文小写字母和数字，且以字母开头，以字母或数字结尾。", {title: "提示"});
        return false;
    }
    if (newCx) {
        checkCxdhIsUsed(cxdh);
    } else {
        oldDh = $.getUrlParam("cxdh");
        if (oldDh == cxdh) {
            dhPassCheck = true;
            layer.msg("代号检测通过，可正常使用");
        } else {
            layui.layer.confirm("查询代号已改变，确认要重新设置？", {
                title: "提示",
                btn: ["确认", "取消"],
                btn1: function (index) {
                    checkCxdhIsUsed(cxdh);
                    layer.close(index);
                },
                btn2: function (index, window) {
                    $("#cxdh").val(oldDh);
                    dhPassCheck = true;
                    layer.close(index);
                }
            })
        }
    }
}

/**
 * 生成url事件
 * @date 2019.03.26 19:52
 * @author hanyaning
 * @return
 */
function getUrlFromDh() {
    var dh = $("#cxdh").val();
    if (isNullOrEmpty(dh)) {
        layer.msg("请先设置查询代号");
        return false;
    }
    if (dhPassCheck) {
        var url = "http://{ip}:{port}/realestate-inquiry-ui/view/dtcx/commonCx.html?cxdh=" + dh;
        $("#url").val(url);
        layer.msg("获取成功");
    } else {
        layer.msg("请先检查查询代号是否可用");
        return false;
    }

}
/**
 * 点击配置js按钮的弹出页面
 * @date 2022.08.10
 * @author hongqin
 * @return
 */
function configJs() {
    layui.use(['form', 'layedit', 'laydate', 'jquery','table','layer'], function () {
        var layer = layui.layer;
        var $=layui.jquery;
        if(pzjs==null){
            pzjs='';
        }
        parent.layer.open({
            type: 1,
            title: '配置js',
            area: ['950px', '405px'],
            btn: ['确认', '取消'],
            btnAlign: 'c',
            maxmin: true,
            content: '<textarea id="pzjs" name="pzjs" class="layui-textarea bdc-layer-text" style="height: 95%;"> '+pzjs +'</textarea>',
            btn1: function (index,layero) {
                var zj = layero.find('#pzjs');
                pzjs = $(zj).val();
                parent.layer.close(index);
            },
            btn2: function (index) {
                layer.close(index);
            }
        });
    })
}
/**
 * 按钮获取字段触发方法，获取并解析sql,获得sql语句的返回字段，并显示
 * @date 2022.06.20
 * @author hongqin
 * @return
 */
function getZdxxList() {
    try{
        var sql = $("#sql").val();
        if(sql.replace(/(^\s*)|(\s*$)/g, "") === ""){
            warnMsg("请输入sql！");
        } else{
            sql = sql.replace(/(^\s*)|(\s*$)/g, "").replace(/\s+/g, ' ').toLowerCase();
            var sqlstr = [];//将sql以','分割并存放
            var len = sql.indexOf("from");
            sql = sql.slice(7,len);
            sqlstr = sql.split(',');
            var arr1 = [];
            var zddm1 = [];//初步获得类似x.name的字段代码
            for(i = 0;sqlstr[i] != null;i++){
                arr1 = sqlstr[i].replace(/(^\s*)|(\s*$)/g, "").split(' ');
                var len = arr1.length;
                zddm1[i] = arr1[len-1];
            }
            var index = 0;
            var arr = [];//存放x.name的分解后字段，做中间处理
            var zddm = [];//存放分解后的字段代码
            for(i=0;zddm1[i] != null;i++){
                arr = zddm1[i].split('.');
                var len = arr.length;
                if(zddm.indexOf(arr[len-1]) === -1) {
                    zddm[index] = arr[len-1];
                    index++;
                }
            }
            var tb = document.getElementById('cxjgTable');
            var rows = cxjgTable.rows.length;
            var yh = [];//存放用户已经手动添加的字段
            var row = 1;
            for(i = 1;i < rows;i++){
                if(cxjgTable.rows[row].cells[0].children[0].value !== "" ){
                    yh[row-1] = cxjgTable.rows[row].cells[0].children[0].value;
                    row++;
                }else{
                    tb.deleteRow(row);
                }
            }
            $.each(zddm, function(i, result) {
                if(result != null && yh.indexOf(result.toUpperCase()) === -1){
                    layui.use(['laytpl', 'form'], function () {
                        var laytpl = layui.laytpl;
                        var form = layui.form;
                        laytpl(cxjgRowTemplet1.innerHTML).render({
                            zddm: result.toUpperCase()
                        }, function (html) {
                            $('#cxjgTable').append($(html));
                        });
                        form.render();
                    });
                }
            });
        }
    }catch(e){
        failMsg("获取字段异常，请手动配置");
    }
}

/*
 * jquery 初始化form插件，传入一个json对象，为form赋值
 * version: 1.0.0-2013.06.24
 * @requires jQuery v1.5 or later
 * Copyright (c) 2013
 * note:  1、此方法能赋值一般所有表单，但考虑到checkbox的赋值难度，以及表单中很少用checkbox，这里不对checkbox赋值
 *		  2、此插件现在只接收json赋值，不考虑到其他的来源数据
 *		  3、对于特殊的textarea，比如CKEditor,kindeditor...，他们的赋值有提供不同的自带方法，这里不做统一，如果项目中有用到，不能正确赋值，请单独赋值
 */
(function ($) {
    $.fn.extend({
        initForm: function (options) {
            //默认参数
            var defaults = {
                jsonValue: "",
                exclude: [],     //不需要进行初始化的name,将name字符串数组传入
                isDebug: false	//是否需要调试，这个用于开发阶段，发布阶段请将设置为false，默认为false,true将会把name value打印出来
            }
            //设置参数
            var setting = $.extend({}, defaults, options);
            var form = this;
            jsonValue = setting.jsonValue;
            //如果传入的json字符串，将转为json对象
            if ($.type(setting.jsonValue) === "string") {
                jsonValue = $.parseJSON(jsonValue);
            }
            //如果传入的json对象为空，则不做任何操作
            if (!$.isEmptyObject(jsonValue)) {
                var debugInfo = "";
                $.each(jsonValue, function (key, value) {
                    //是否开启调试，开启将会把name value打印出来
                    if (setting.isDebug) {
                        //alert("name:"+key+"; value:"+value);
                        debugInfo += "name:" + key + "; value:" + value + " || ";
                    }
                    if (setting.exclude.indexOf(key) == -1) {

                        var formField = form.find("[name='" + key + "']");
                        if ($.type(formField[0]) === "undefined") {
                            if (setting.isDebug) {
                                alert("can not find name:[" + key + "] in form!!!");	//没找到指定name的表单
                            }
                        } else {
                            var fieldTagName = formField[0].tagName.toLowerCase();
                            if (fieldTagName == "input") {
                                if (formField.attr("type") == "radio") {
                                    $("input:radio[name='" + key + "'][value='" + value + "']").attr("checked", "checked");
                                } else {
                                    formField.val(value);
                                }
                            } else if (fieldTagName == "select") {
                                //do something special
                                formField.val(value);
                            } else if (fieldTagName == "textarea") {
                                //do something special
                                formField.val(value);
                            } else {
                                formField.val(value);
                            }
                        }
                    }
                })
                if (setting.isDebug) {
                    alert(debugInfo);
                }
            }
            return form;	//返回对象，提供链式操作
        }
    });
})(jQuery)
