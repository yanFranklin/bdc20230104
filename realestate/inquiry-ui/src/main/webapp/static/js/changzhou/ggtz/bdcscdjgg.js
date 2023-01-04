var gzlslid = getQueryString("gzlslid");
var processInsId = getQueryString("gzlslid");
var zxlc = "false";
var sfsc = false;//是否是删除操作判断
var gglx = getQueryString("gglx");
var ggid = getQueryString("ggid");
var xmid = "";
var fieldsStr = "";
var zdList = {};
var ggindex;
var zdList = getMulZdList("fwyt");
var zdQllxList = getMulZdList("qllx");
var bdcGgywsjDOList;
var $, form, layer, element, table, laydate, laytpl, formselects;
layui.use(['form', 'jquery', 'laydate', 'element', 'layer', 'table', 'laytpl'], function () {
    $ = layui.jquery;
    form = layui.form;
    layer = layui.layer;
    element = layui.element;
    table = layui.table;
    laydate = layui.laydate;
    laytpl = layui.laytpl;
    $(function () {
        addModel("加载中");
        //获取字典
        getCommonZd(function (data) {
            if (isNotBlank(data)) {
                zdList = data;
            }
        });
        fieldsStr =  getfields(gglx);
        //获取数据
        loadGg(ggid);
    });
})

function loadGg(ggid) {
    getReturnData("/rest/v1.0/ggxx/czgg", { gzlslid: gzlslid, ggid: ggid}, "GET", function (data) {
        removeModal();
        if (data) {
            var json = {
                ggxx: data,
                zdList: zdList
            };
            var tpl = ggxxTpl.innerHTML;
            var view = document.getElementById('ggxx');
            //渲染数据
            laytpl(tpl).render(json, function (html) {
                view.innerHTML = html;
            });
            // 公告业务数据
            setGgywsj(data);
        }
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    })
}

// 关联项目数据
function setGgywsj(data) {
    var gzbdsArray = new Array();
    var order = 1;
    if (data.bdcGgywsjDOList) {
        bdcGgywsjDOList = data.bdcGgywsjDOList
        for (var i in bdcGgywsjDOList) {
            var bdcGgywsjDO = bdcGgywsjDOList[i];
            gzbdsArray.push({
                "xh": order++,
                "qlr": bdcGgywsjDO.qlr,
                "qllx": bdcGgywsjDO.qllx,
                "zl": bdcGgywsjDO.zl,
                "bdcdyh": bdcGgywsjDO.bdcdyh,
                "qxdm": bdcGgywsjDO.qxdm,
                "zdzhmj": bdcGgywsjDO.zdzhmj,
                "dzwyt": bdcGgywsjDO.dzwyt,
                "bz": bdcGgywsjDO.bz,
                "xmid":bdcGgywsjDO.xmid,
                "bdcqzh":bdcGgywsjDO.bdcqzh,
                "gznr":bdcGgywsjDO.gznr,
                "zxyy":bdcGgywsjDO.zxyy,
                "cxyy":bdcGgywsjDO.cxyy

            });
        }
    }
    var col = [
        {type: 'checkbox', fixed: 'left'},
        {field: 'xh', title: '序号', width: 80, align: 'center',hide:true},
        {field: 'qlr', title: '权利人', width: 180, align: 'center',hide:true},
        {
            field: 'qllx', title: '权利类型', width: 220, align: 'center',hide:true,
            templet: function (d) {
                return convertZdDmToMc("qllx", d.qllx, "zdQllxList");
            }
        },
        {field: 'bdcqzh', title: '不动产权证号', width: 250, align: 'center',hide:true},
        {field: 'zl', title: '不动产坐落', width: 300, align: 'center',hide:true},
        {field: 'bdcdyh', title: '不动产单元号', width: 270, align: 'center',hide:true},
        {field: 'qxdm', title: '区县代码', width: 100, align: 'center',hide:true},
        {field: 'zdzhmj', title: '面积', width: 140, align: 'center',hide:true},
        {
            field: 'dzwyt', title: '用途', width: 180, align: 'center',hide:true,
            templet: function (d) {
                return convertZdDmToMc("fwyt", d.dzwyt, "zdList");
            }
        },
        {field: 'bz', title: '备注', width: 400,align: 'center',hide:true},
        {field: 'gznr', title: '更正内容', width: 400,align: 'center',hide:true},
        {field: 'zxyy', title: '注销原因', width: 400,align: 'center',hide:true},
        {field: 'cxyy', title: '撤销原因', width: 400,align: 'center',hide:true},
        {field: 'xmid', title: '项目id', align: 'center',hide:true}

    ];
    fieldsStr =  getfields(gglx);
    var fields = fieldsStr.split(",");

    for(var i = 0 ; i<fields.length;i++){
        for(var j = 0 ; j<col.length;j++){
            if(fields[i] == col[j].field){
                col[j].hide = false;
            }
        }
    }
    table.render({
        elem: '#ggywsj',
        toolbar: '#ggywsjBtn',
        defaultToolbar: [],
        title: '关联项目',
        cols: [col],
        data: gzbdsArray,
        page: false,
        done: function () {
            $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
        }
    });
    //表头工具栏事件
    table.on('toolbar(ggywsj)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'add':
                addGlxm();
                break;
            case 'del':
                deleteGlxm(checkStatus.data);
                break;
            case 'edit':
                editGlxm(checkStatus.data);
                break;
            case 'glyw':
                linkProcess();
                break;
        }
        ;
    });
}
function editGlxm(data){
    if (!data || data.length == 0) {
        layer.alert("<div style='text-align: center'>请选择需要编辑的记录！</div>", {title: '提示'});
        return;
    }
    if (!data || data.length > 1) {
        layer.alert("<div style='text-align: center'>请选择一条记录编辑！</div>", {title: '提示'});
        return;
    }
    var objStr = encodeURIComponent(JSON.stringify(data[0]));
    var url = getIP() + "/realestate-inquiry-ui/changzhou/ggtzlr/addYwsj.html?fieldsStr="+ fieldsStr+"&objStr="+objStr;
    layer.open({
        type: 2,
        area: ['960px', '500px'],
        fixed: false, //不固定
        title: "新增业务数据",
        content: url,
        btnAlign: "c",
        btn: ["确定","取消"],
        yes: function(index, layero){
            var res = window["layui-layer-iframe" + index].callbackdata();
            console.log(res);
            if(fieldsStr.indexOf('bdcdyh') > -1 && res.bdcdyh.length != 28){
                ityzl_SHOW_WARN_LAYER("不动产单元号长度应该为28位！");
                return false;
            }else{
                layer.close(index);
                for(var i = 0;i<table.cache.ggywsj.length;i++){
                    if(table.cache.ggywsj[i].xh == res.xh){
                        table.cache.ggywsj[i] = res;
                    }
                }
                bdcGgywsjDOList = table.cache.ggywsj;
                table.reload("ggywsj", {data: table.cache.ggywsj});
                saveGg(sfsc);
            }

        },
        cancel: function(index, layero){
            layer.close(index);
        }
    });
}

// 新增关联项目
function addGlxm() {
    var strings = fieldsStr.split(",");
    bdcGgywsjDOList = table.cache.ggywsj;
    if (bdcGgywsjDOList == null) {
        bdcGgywsjDOList = new Array();
    }
    var index = 1;
    for (var i in bdcGgywsjDOList) {
        bdcGgywsjDOList[i].xh = index++;
    }
    var obj = {};
    obj["xmid"] = ""
    for(var i = 0;i<strings.length;i++){
        var field = strings[i];
        obj[field] = "";
    }
    if (table.cache.ggywsj && table.cache.ggywsj.length > 0) {
        var xh =  bdcGgywsjDOList.length + 1;
        obj.xh = xh;
    }else{
        obj.xh = 1;
    }
    var objStr = encodeURIComponent(JSON.stringify(obj));
    var url = getIP() + "/realestate-inquiry-ui/changzhou/ggtzlr/addYwsj.html?fieldsStr="+ fieldsStr+"&objStr="+objStr;
        layer.open({
            type: 2,
            area: ['960px', '500px'],
            fixed: false, //不固定
            title: "新增业务数据",
            content: url,
            btnAlign: "c",
            btn: ["确定","取消"],
            yes: function(index, layero){
                var res = window["layui-layer-iframe" + index].callbackdata();

                if(fieldsStr.indexOf('bdcdyh') > -1 && res.bdcdyh.length != 28){
                    ityzl_SHOW_WARN_LAYER("不动产单元号长度应该为28位！");
                    return false;
                }else{
                    layer.close(index);
                    bdcGgywsjDOList.push(res);
                    table.reload("ggywsj", {data: bdcGgywsjDOList});
                    saveGg(sfsc);
                }
            },
            cancel: function(index, layero){
                layer.close(index);
            }
        });
}

// 删除关联项目
function deleteGlxm(data) {
    if (!data || data.length == 0) {
        layer.alert("<div style='text-align: center'>请选择需要删除的记录！</div>", {title: '提示'});
        return;
    }
    layer.confirm("是否删除关联的业务？",function(index){
        var newTableData = [];
        var tableData = table.cache.ggywsj;
        for (var index in tableData) {
            var isExist = false;
            for (var i in data) {
                if (tableData[index].xh == data[i].xh) {
                    isExist = true;
                    break;
                }
            }
            if (false == isExist) {
                newTableData.push(tableData[index]);
            }
        }
        // 重新索引序号
        var order = 1;
        for (var index in newTableData) {
            newTableData[index].xh = order++;
        }
        bdcGgywsjDOList = newTableData;
        table.reload("ggywsj", {data: newTableData});
        sfsc = true;
        saveGg(sfsc);
    });
}

function saveGg(sfsc) {
    var qlrswsj = [];
    var swsjArray = $(".swsj").serializeArray();
    var swsj = {};
    for (var j = 0; j < swsjArray.length; j++) {
        var name = swsjArray[j].name;

        swsj[name] = trim(swsjArray[j].value);
        if ((j + 1 < swsjArray.length && swsjArray[j + 1].name === 'qlr') || j + 1 == swsjArray.length) {
            qlrswsj.push(swsj);
            swsj = {};
        }
    }
    console.info(qlrswsj);
    addModel("正在保存");
    var dataObj = {};
    var $elems = $('.bdcgg');
    $elems.each(function (index, item) {
        var id = $(item).attr('id');
        var val = $(item).text();
        dataObj[id] = val;
    });
    dataObj.gglx = gglx;
    dataObj.ggid = $("#ggid").val();
    dataObj.qlrswsjList = qlrswsj;
    var bdcGgDTO = {};
    bdcGgDTO.bdcGgywsjDOList = table.cache.ggywsj;
    if (sfsc == undefined){
        bdcGgDTO.sfsc = false;
    }else {
        bdcGgDTO.sfsc = sfsc;
    }
    bdcGgDTO.bdcGgVO = dataObj;
    bdcGgDTO.ggid = $("#ggid").val();
    getReturnData("/rest/v1.0/ggxx", JSON.stringify(bdcGgDTO), "PUT", function (data) {
        removeModal();
        ityzl_SHOW_SUCCESS_LAYER("保存成功");
        $("#ggid").val(data.ggid);
        ggid = data.ggid;
        loadGg(ggid);
    }, function (xhr) {
        ityzl_SHOW_WARN_LAYER("保存失败")
        delAjaxErrorMsg(xhr);
    });
}

function linkProcess() {
    var url = "/realestate-inquiry-ui/changzhou/ggtzlr/linkProcess.html"+"?gglx="+ gglx +"&ggid=";
    if(isNotBlank($("#ggid").val())){
        var ggid = $("#ggid").val();
        url = url+ ggid;
        ggindex = layer.open({
            type: 2,
            title: "关联业务",
            area: ['1300px', '600px'],
            fixed: false, //不固定
            maxmin: true, //开启最大化最小化按钮
            content: url
        })
        layer.full(ggindex);
    }else{
        // 初始化ggid
        getReturnData("/rest/v1.0/ggxx/initGgxx", {gglx: gglx}, "GET", function (data) {
            if (data) {
                ggid = data.ggid;
                url = url+ ggid;
                // 跳转到关联页面
                console.log(url);
                ggindex = layer.open({
                    type: 2,
                    title: "关联业务",
                    area: ['1300px', '600px'],
                    fixed: false, //不固定
                    maxmin: true, //开启最大化最小化按钮
                    content: url
                })
                layer.full(ggindex);
            } else {
                ityzl_SHOW_WARN_LAYER("初始化公告信息失败");
            }
        }, function (xhr) {
            delAjaxErrorMsg(xhr);
        })
    }


}

var xmlx = "";
function addSwsj() {
    var appendEl = $("#addSwsjTable");
    var trELArray = $(appendEl).find("tr");
    var djxl = "";
    if (trELArray.length > 1) {
        $(appendEl).find("tr[class='bdc-table-none']").remove();
        //移除后长度减一
        trELArray = $(appendEl).find("tr");
    }
    addSwsjxx = {};
    var getTpl = addSwsjTpl.innerHTML;
    laytpl(getTpl).render(addSwsjxx, function (html) {
        appendEl.append(html);
        form.render();
    });
    $(appendEl.find('tr:last-child td input')[0]).focus();
    form.render();
}




function getfields(gglx){
    var obj = {
        1:"xh,qlr,qllx,zl,bdcdyh,qxdm,zdzhmj,dzwyt,bz",
        2:"xh,qlr,qllx,zl,bdcdyh,qxdm,zdzhmj,dzwyt,bz",
        3:"xh,qlr,qllx,zl,bdcdyh,qxdm,zdzhmj,dzwyt,bz",
        4:"xh,qlr,qllx,zl,bdcdyh,qxdm,zdzhmj,dzwyt,bz",
        5:"xh,bdcqzh,zl,gznr,bz",
        6:"xh,bdcqzh,qlr,zl,zxyy,bz",
        7:"xh,bdcqzh,qlr,zl,cxyy,bz",
        8:"xh,bdcqzh,qlr,qllx,bdcdyh,zl,bz",
        9:"xh,qlr,qllx,zl,bdcdyh,qxdm,zdzhmj,dzwyt,bz",
        10:"xh,qlr,qllx,zl,bdcdyh,qxdm,zdzhmj,dzwyt,bz",
        11:"xh,qlr,qllx,zl,bdcdyh,qxdm,zdzhmj,dzwyt,bz"
    }
    for (var key in obj) {
        if(key == gglx){
            return obj[key];
        }
    }
}

// 去除空格
function trim(val){
    return val.replace(/\s*/g,"");
}