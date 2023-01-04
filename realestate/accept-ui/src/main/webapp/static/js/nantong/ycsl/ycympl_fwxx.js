var slbh;
var $,laytpl,form;
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function() {
    form = layui.form;
    $ = layui.jquery;
    var element = layui.element;
    laytpl = layui.laytpl;
    var layer = layui.layer;

    // 监听页面上申请人证件种类修改
    form.on('select(zjzl)', function (data) {
        var $zjh = $(data.elem).parents("td").next().find("input");
        addSfzYz(data.value, $zjh);
        form.render();
    });
});

//新增申请人
function addQlr(xmid) {
    var qllx = $('.layui-show').data('qllx');
    var djxl = $('.layui-show').data('djxl');
    var url = getContextPath() + "/view/ycsl/sqr.html?processInsId=" + processInsId + "&formStateId="
        + formStateId + "&xmid=" + xmid + "&qllx=" + qllx + "&djxl=" + djxl;
    layer.open({
        type: 2,
        area: ['960px', '575px'],
        fixed: false, //不固定
        title: "新增申请人",
        content: url,
        btnAlign: "c"
    });
    form.render();
    resetSelectDisabledCss();
}

//权利人详情展示
function showQlr(sqrid, xmid, qlrlb, elem) {
    addModel();
    var qllx = $('.layui-show').data('qllx');
    var djxl = $('.layui-show').data('djxl');
    var url = getContextPath() + "/nantong/ycsl/sqr.html?processInsId=" + processInsId + "&sqrid=" + sqrid + "&xmid=" + xmid + "&formStateId=" + formStateId + "&readOnly=" + readOnly + "&qllx=" + qllx + "&djxl=" + djxl;
    layer.open({
        type: 2,
        area: ['960px', '575px'],
        fixed: false, //不固定
        title: "申请人详细信息",
        content: url,
        btnAlign: "c"
    });
    removeModal();
    form.render();
    renderDate(form, "");
    disabledAddFa();
}

//删除权利人
function deleteSqr(sqrid, sxh, qlrlb) {
    var xmid = "";
    var tabXmid = $('.layui-show').data('xmid');
    var tabQllx = $('.layui-show').data('qllx');
    var tabDjxl = $('.layui-show').data('djxl');
    var url = "/slym/sqr/sqrxx/pl?gzlslid=" + processInsId + "&sqrid=" + sqrid + "&qllx=" + tabQllx + "&djxl=" + tabDjxl;
    if (!isNotBlank(tabXmid)) {
        tabXmid = xmid;
    }
    var deleteIndex = layer.open({
        type: 1,
        skin: 'bdc-small-tips',
        title: '确认删除',
        area: ['320px'],
        content: '是否确认删除？',
        btn: ['确定', '取消'],
        btnAlign: 'c',
        yes: function () {
            addModel();
            //确定操作
            getReturnData(url, {}, 'DELETE', function (data) {
                removeModal();
                loadYcslxx(tabXmid,$('.layui-show'));
                ityzl_SHOW_SUCCESS_LAYER("删除成功");
            }, function (err) {
                delAjaxErrorMsg(err)
            });
            layer.close(deleteIndex);
        },
        btn2: function () {
            //取消
            layer.close(deleteIndex);
        }
    });
}

//权利人顺序号修改
function changeSqrSxh(sqrid, czlx) {
    var tabXmid = $('.layui-show').data('xmid');
    if(!isNotBlank(tabXmid)){
        tabXmid =xmid;
    }
    getReturnData("/slym/sqr/sxh", {
        sqrid: sqrid,
        czlx: czlx,
        gzlslid: processInsId
    }, 'GET', function (data) {
        if (data > 0) {
            ityzl_SHOW_SUCCESS_LAYER("调整成功");
            loadYcslxx(tabXmid,$('.layui-show'));
        }
    }, function (err) {
        delAjaxErrorMsg(err);
    });
}

//关闭panel
function cancelEdit() {
    layer.closeAll();
}

// 加载权力信息与不动产单元信息统一入口
function generateQlxxAndBdcdyxx(data,$nowTab,bdcSlXm,bdcqllxSlXm) {
    // 加载权利信息
    generateQlxx(data, bdcSlXm, $nowTab);
    // 渲染领证人信息
    generateLzrxx({
        zd: zdList
    }, $nowTab);

    // 加载不动产单元信息
    var djxl = bdcSlXm.djxl;
    var xmxx = bdcqllxSlXm[djxl];
    if (xmxx && xmxx.length < 2) {
        generateBdcdyxx({
            bdcSlXm: bdcSlXm,
            zd: zdList
        }, $nowTab);
    } else {
        generateBdcdyxx({
            bdcSlXm: xmxx,
            zd: zdList
        }, $nowTab);
    }

}

// 加载不动产单元信息
function generateBdcdyxx(data,$nowTab){
    if($nowTab.find('#bdcdyxx').length >0) {
        if(data.bdcSlXm.length > 1){
            var tpl = bdcdyxxPlTpl.innerHTML;
        }else {
            tpl = bdcdyxxTpl.innerHTML;
        }
        var view = $nowTab.find('#bdcdyxx')[0];
        laytpl(tpl).render(data, function (html) {
            view.innerHTML = html;
        });
        form.render();
    }
}

// 渲染领证人信息
function generateLzrxx(data,$nowTab){
    if($nowTab.find('#lzrxx').length >0) {
        var tabXmid = $nowTab.data('xmid');
        getReturnData("/slym/lzr/lzrxx", {xmid: tabXmid}, 'GET', function (lzrList) {
            var renderData = {
                lzrList: lzrList,
                zd: zdList
            }
            var tpl = lzxxTpl.innerHTML, view = $nowTab.find('#lzrxx')[0];
            laytpl(tpl).render(renderData, function (html) {
                view.innerHTML = html;
            });
            form.render();
        }, function (err) {
            console.log(err);
        }, false);
    }
}

/**
 * 初始化加载权利信息模块数据信息
 * @param ycslYmxxDTO 一窗受理页面信息
 * @param bdcSlXm 不动产受理项目信息
 * @param $nowTab 当前点击tab页
 */
function generateQlxx(ycslYmxxDTOList, bdcSlXm, $nowTab){
    if(isNotBlank(ycslYmxxDTOList)){
        // 根据登记小类获取登记原因
        var djyyList = tabModel.loadDjyy(bdcSlXm.djxl);
        if(isNotBlank(djyyList)){
            var tpl = {};
            var dataList = [];
            // 从zrf中解析qlr
            $.each(ycslYmxxDTOList, function(index, ycslYmxxDTO){
                var zrflist = ycslYmxxDTO.bdcSlZrfDTOList;
                var tdsyqr = "";
                if(zrflist){
                    for(var i=0;i<zrflist.length;i++){
                        var item = zrflist[i];
                        var sqr = item.bdcSlSqrDO.sqrmc;
                        tdsyqr += sqr+",";
                    }
                    if(tdsyqr.length > 0){
                        tdsyqr = tdsyqr.substr(0,tdsyqr.length-1);
                    }
                }
                var data = {
                    zd : zdList,
                    bdcSlJyxx : ycslYmxxDTO.bdcSlJyxxDO,
                    bdcSlFwxx : ycslYmxxDTO.bdcSlFwxxDO,
                    bdcSlXm : ycslYmxxDTO.bdcSlXmDO,
                    djyyList :  djyyList,
                    tdsyqr : tdsyqr
                };
                dataList.push(data);
            });
            tpl = qlxxTpl.innerHTML;
        }
        if($nowTab.find('#qlxx').length >0) {
            var view = $nowTab.find('#qlxx')[0];
            laytpl(tpl).render(dataList, function (html) {
                view.innerHTML = html;
            });
            // 添加权利信息页面表单监听事件，同步权利信息至交易与税收页面
            qlxxModel.syncFwxxData();
        }
    }
}

// 批量流程保存不动产受理房屋信息
function saveBdcSlFwxxPl(tabType){
    var fwxxDataList = [];
    if(tabType === "jyssTab"){
        if($(".inner-fwjyxx").length >0){
            $(".inner-fwjyxx").each(function (index, ele){
                var fwxxData = $(ele).find(".fwxx").serializeObject();
                var xmid = fwxxData.xmid;
                if($("#"+xmid).length > 0 && $("#"+xmid).find(".fwxx").length > 0){
                    var fwjyxxData = $("#"+xmid).find(".fwxx").serializeObject();
                    $.extend(fwxxData, fwjyxxData);
                }
                fwxxDataList.push(fwxxData);
            });
        }
    }else{
        if($(".inner-qlxx").length >0){
            $(".inner-qlxx").each(function (index, ele){
                var fwssData = $(ele).find(".fwxx").serializeObject();
                var fwxxData = {};
                for (var key in fwssData) {
                    fwxxData[key] = fwssData[key];
                }
                fwxxDataList.push(fwxxData);
            });
        }
    }
    //修改土地性质为划拨或租赁时，权利的结束时间不可填写
    if ($('.jssj').length > 0) {
        checkJssjGz2($("#qlxz").val(), $('.jssj').val(),false);
        checkJssjGz4($("#qlxz").val(), $('.jssj').val());
    }
    if ($('.jssj').length > 0 && $('.qssj').length > 0) {
        checkJssjGz3($("#qlxz").val(), $('.jssj').val(), $('.qssj').val())
    }
    console.info(fwxxDataList);
    $.each(fwxxDataList, function(index, value){
        if(qlxxModel.checkSzcAndZcs(value.szc, value.zcs)) {
            $.ajax({
                url: getContextPath() + "/ycsl/fwxx",
                type: 'PATCH',
                dataType: 'json',
                async: false,
                contentType: "application/json",
                data: JSON.stringify(value),
                success: function (data) {
                }, error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr)
                }
            });
        }
    });
}

// 保存不动产受理项目
function saveBdcSlXm(){
    for (var i = 1; i <= $('.layui-tab-item').length; i++) {
        var $nowTab = $('.layui-tab-item:nth-child(' + i + ')');
        var tabXmid = $nowTab.data('xmid');
        if (!isNotBlank(tabXmid)) {
            continue;
        }
        saveBdcSlXmTabPl($nowTab);
    }
}

function saveBdcSlXmTabPl($nowTab){
    var bdcSlXmxxList = [];
    if($nowTab.find(".slxm").length != 0){
        $(".inner-qlxx").each(function (index, ele){
            var slxmData = $(ele).find(".slxm").serializeObject();
            var slxmxx = {};
            for (var key in slxmData) {
                slxmxx[key] = slxmData[key];
            }
            slxmxx.sqfbcz = $(ele).find("input[type='radio']:checked").val();
            bdcSlXmxxList.push(slxmxx);
        });

        var bdcSlXmArray = [];
        var $bdcdy_xmxx =$nowTab.find("#bdcdyxx .slxm");
        if($bdcdy_xmxx.length >0){
            bdcSlXmArray = $bdcdy_xmxx.serializeArray();
        }

        if(bdcSlXmArray.length > 10){
            var bdcSlXmData = {};
            for (var j = 0; j < bdcSlXmArray.length; j++) {
                var name = bdcSlXmArray[j].name;
                bdcSlXmData[name] = bdcSlXmArray[j].value;
                if ((j + 1 < bdcSlXmArray.length && bdcSlXmArray[j + 1].name === 'xmid') || j + 1 == bdcSlXmArray.length) {
                    $.each(bdcSlXmxxList, function(index, value){
                        if(value.xmid == bdcSlXmData.xmid){
                           $.extend(value, bdcSlXmData);
                        }
                    });
                }
            }
        }
        $.each(bdcSlXmxxList, function(index, xmxx){
            modifyBdcSlxm(xmxx);
        });
    }
}

function modifyBdcSlxm(bdcSlXmData){
    $.ajax({
        url: getContextPath() + "/ycsl/xmxx",
        type: 'PATCH',
        dataType: 'json',
        async: false,
        contentType: "application/json",
        data: JSON.stringify(bdcSlXmData),
        success: function (data) {
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

function saveBdcSlJyxxPl(tabType){
    var isFirst =true;
    for (var i = 1; i <= $('.layui-tab-item').length; i++) {
        var $nowTab = $('.layui-tab-item:nth-child(' + i + ')');
        var tabXmid = $nowTab.data('xmid');
        if (!isNotBlank(tabXmid)) {
            continue;
        }
        saveBdcSlJyxxTabPl($nowTab,isFirst,tabType);
        if(tabType ==="jyssTab" &&isFirst){
            break;
        }
        isFirst = false;
    }
}

// 保存不动产受理交易信息
function saveBdcSlJyxxTabPl($nowTab, isFirst, tabType) {
    if($nowTab.find(".inner-qlxx").length != 0 ||(isFirst && $(".inner-fwjyxx").length!=0 ) ){
        var jyxxList = [];
        if(tabType ==="slTab") {
            $(".inner-qlxx").each(function (index, ele){
                var jyssData = $(ele).find(".jyxx").serializeObject();
                var jyxxData = {};
                for (var key in jyssData) {
                    jyxxData[key] = jyssData[key];
                }
                jyxxList.push(jyxxData);
            });
        }

        if (tabType ==="jyssTab" && isFirst && $(".inner-fwjyxx").length > 0) {
            $(".inner-fwjyxx").each(function (index, ele){
                var jyssData = $(ele).find(".jyxx").serializeObject();
                var jyxxData = {};
                for (var key in jyssData) {
                    jyxxData[key] = jyssData[key];
                }
                var xmid = jyxxData.xmid;
                if($("#"+xmid).length > 0 && $("#"+xmid).find(".jyxx").length > 0){
                    var fwjyxxData = $("#"+xmid).find(".jyxx").serializeObject();
                    $.extend(jyxxData, fwjyxxData);
                }
                jyxxList.push(jyxxData);
            });
        }
        console.info(jyxxList);
        $.each(jyxxList, function(index, value){
            $.ajax({
                url: getContextPath() + "/ycsl/htxx",
                type: 'PATCH',
                dataType: 'json',
                async: false,
                contentType: "application/json",
                data: JSON.stringify(value),
                success: function (data) {
                }, error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr)
                }
            });
        });
    }

}

// 保存不动产受理抵押权信息
function saveBdcSlDyaq(className) {
    if ($(className).length !== 0) {
        for (var i = 1; i <= $('.layui-tab-item').length; i++) {
            var dyaqData = $('.layui-tab-item:nth-child(' + i + ') ' + className).serializeObject();
            if (isNotBlank(dyaqData)) {
                $.ajax({
                    url: getContextPath() + "/rest/v1.0/ycym/qlxx/dyaq",
                    type: 'PUT',
                    dataType: 'json',
                    async: false,
                    contentType: "application/json",
                    data: JSON.stringify(dyaqData),
                    success: function (data) {
                    }, error: function (xhr, status, error) {
                        delAjaxErrorMsg(xhr)
                    }
                });
            }
        }
    }
}

// 保存不动产领证人信息
function saveBdcSlLzrxx(className){
    var lzrxxList = [];
    for (var i = 1; i <= $('.layui-tab-item').length; i++) {
        var tabXmid = $('.layui-tab-item:nth-child(' + i + ')').data('xmid');
        var tabQllx = $('.layui-tab-item:nth-child(' + i + ')').data('qllx');
        var tabDjxl = $('.layui-tab-item:nth-child(' + i + ')').data('djxl');
        var tabXmids = [];
        if (!isNotBlank(tabXmid)) {
            continue;
        }
        tabXmids.push(tabXmid);
        if (isNotBlank(tabDjxl) && bdcqllxSlXm != null && bdcqllxSlXm[tabDjxl] && bdcqllxSlXm[tabDjxl].length > 0) {
            for (var m = 0; m < bdcqllxSlXm[tabDjxl].length; m++) {
                if (isNotBlank(bdcqllxSlXm[tabDjxl][m].xmid) && bdcqllxSlXm[tabDjxl][m].xmid != tabXmid) {
                    tabXmids.push(bdcqllxSlXm[tabDjxl][m].xmid);
                }
            }
        }
        var $lzrxx = $('.layui-tab-item:nth-child(' + i + ') ' + className);
        if ($lzrxx.length > 0) {
            for (var k = 0; k < tabXmids.length; k++) {
                var lzrxxArray = $lzrxx.serializeArray();
                var bdcSlLzrSingleData = {};
                for (var j = 0; j < lzrxxArray.length; j++) {
                    var name = lzrxxArray[j].name;
                    bdcSlLzrSingleData[name] = lzrxxArray[j].value;
                    if ((j + 1 < lzrxxArray.length && lzrxxArray[j + 1].name === 'lzrid') || j + 1 == lzrxxArray.length) {
                        bdcSlLzrSingleData["xmid"] = tabXmids[k];
                        bdcSlLzrSingleData["gzlslid"] = processInsId;
                        // 踢掉没有填写名称的领证人
                        if(isNotBlank(bdcSlLzrSingleData.lzrmc)) {
                            lzrxxList.push(bdcSlLzrSingleData);
                        }
                        bdcSlLzrSingleData = {};
                    }
                }

            }

        }

    }
    if(lzrxxList.length > 0){
        $.ajax({
            url: getContextPath() + "/slym/lzr/lzrxx",
            type: 'PATCH',
            dataType: 'json',
            async: false,
            contentType: "application/json",
            data: JSON.stringify(lzrxxList),
            success: function (data) {
                loadLzr();
            }, error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        });
    }

}

//刷新领证人信息
function loadLzr() {
    for (var i = 1; i <= $('.layui-tab-item').length; i++) {
        var tabXmid = $('.layui-tab-item:nth-child(' + i + ')').data('xmid');
        var $nowTab = $('.layui-tab-item:nth-child(' + i + ')');
        if(isNotBlank(tabXmid)) {
            generateLzrxx({ zd : zdList },$nowTab);
        }
    }
}

var qlxxModel ={
    // 初始化附记和权利其他状况按钮事件
    initEvent : (function(){
        return function(){
            // 领证人证件类型添加初始化事件
            form.on('select(lzrzjhFilter)', function (data) {
                var $zjh = $(data.elem).parents(".layui-inline").next().find("input");
                addSfzYz(data.value, $zjh);
            });
        }
    })(),
    // 所在层与总层数验证
    checkSzcAndZcs : function(szc, zcs){
        if (szc === "0") {
            throw error = new Error("所在层不能为0");
            return false;
        }
        if (isNotBlank(szc) && isNotBlank(zcs) && parseInt(szc) > parseInt(zcs)) {
            throw error = new Error("所在层不能大于总层数");
            return false;
        }
        return true;
    },
    // 同步权利信息模块相关数据至交易与税收模块中
    syncFwxxData : (function(){
        // 计算土地权利面积
        var sumTdqlMj = function($fttdmj, $dytdmj){
            var fttdmj = $fttdmj.val();
            var dytdmj = $dytdmj.val();
            var fttdmjX = 0;
            var dytdmjX = 0;
            if (fttdmj != "") {
                fttdmjX = parseFloat(fttdmj) * 100;
            }
            if (dytdmj != "") {
                dytdmjX = parseFloat(dytdmj) * 100;
            }
            var $syqmj =$fttdmj.parents(".inner-qlxx").find("input[name='tdsyqmj']");
            if($syqmj.length >0) {
                $syqmj.val((fttdmjX + dytdmjX) / 100);
            }
        }
        return function(){
            // 监听房屋类型，同步更新交易与税收页面的房屋类型
            form.on('select(fwlx)',function (data) {
                var xmid = $(this).parents(".inner-qlxx").find("input[name='xmid']").val();
                if($("#"+xmid).length > 0){
                    $("#"+xmid).find("select[name='fwlx']").val(data.value);
                    form.render("select");
                    disabledAddFa();
                    resetSelectDisabledCss();
                }
            });

            // 监听房屋性质，同步更新交易与税收页面的房屋性质
            form.on('select(fwxz)',function (data) {
                var xmid = $(this).parents(".inner-qlxx").find("input[name='xmid']").val();
                if($("#"+xmid).length > 0){
                    $("#"+xmid).find("select[name='fwxz']").val(data.value);
                    form.render("select");
                    disabledAddFa();
                    resetSelectDisabledCss();
                }
            });


            // 分摊土地面积和独用土地面积监听
            $(".inner-qlxx").each(function(index,item){
                var $fttdmj = $(item).find("input[name='fttdmj']");
                var $dytdmj = $(item).find("input[name='dytdmj']");
                if ($fttdmj.length > 0 && $dytdmj.length > 0) {
                    $fttdmj.on('change', function () {
                        sumTdqlMj($fttdmj, $dytdmj);
                    });
                    $dytdmj.on('change', function () {
                        sumTdqlMj($fttdmj, $dytdmj);
                    });
                }
            });
        }
    })()
}

// Tab模块，通用方法信息
var tabModel = {
    // 通过不动产登记小类获取当前登记小类相关的登记原因信息
    loadDjyy : function(djxl){
        if(isNotBlank(djxl)){
            var djxlList;
            getReturnData("/slym/xm/queryDjxlDjyy", {djxl: djxl}, 'GET', function (data) {
                if (isNotBlank(data)) {
                    djxlList = data;
                }
            }, function (err) {
                console.log(err);
            },false);
            return djxlList;
        }
    }
}

function saveSqr(){
    for (var i = 1; i <= $('.layui-tab-item').length; i++) {
        var tabXmid = $('.layui-tab-item:nth-child(' + i + ')').data('xmid');
        var tabDjxl = $('.layui-tab-item:nth-child(' + i + ')').data('djxl');
        if (!isNotBlank(tabXmid)) {
            continue;
        }
        var $nowTab = $('.layui-tab-item:nth-child(' + i + ')');
        saveSqrTab($nowTab, tabXmid, tabDjxl);
    }

}

function saveZrzcfxx() {

    if (isNotBlank(xmid)) {
        var jyss_qllx="";
        var jyss_djxl="";
        if(bdcSlXm != null){
            jyss_qllx =bdcSlXm.qllx;
            jyss_djxl=bdcSlXm.djxl;
        }
        var sqrlist = [];
        var bdcSlSqrSingleData ={};
        if($(".zrzcf").length != 0){
            var zrfData = $(".zrzcf").serializeArray();
            var zrf= {};
            for (var zrfnum = 0; zrfnum < zrfData.length; zrfnum++) {
                var name = zrfData[zrfnum].name;
                zrf[name] = zrfData[zrfnum].value;
                // 以qlrid为每一组权利人的起始数据，作为分割依据
                if ((zrfnum + 1 < zrfData.length && zrfData[zrfnum + 1].name === 'sqrid') || zrfnum + 1 == zrfData.length) {
                    bdcSlSqrSingleData.sqrid=zrf.sqrid;
                    bdcSlSqrSingleData.sbfwtc=zrf.sbfwtc;
                    bdcSlSqrSingleData.hyzk=zrf.hyzk;
                    bdcSlSqrSingleData.hyxxbdjg=zrf.hyxxbdjg;
                    bdcSlSqrSingleData.xmid=xmid;
                    bdcSlSqrSingleData.sqrlb=zrf.sqrlb;
                    sqrlist.push(bdcSlSqrSingleData);
                    bdcSlSqrSingleData = {};

                }
            }
        }
        if($(".zcf").length != 0){
            var zcfData = $(".zcf").serializeArray();
            var zcf= {};
            for (var zcfnum = 0; zcfnum < zcfData.length; zcfnum++) {
                var name = zcfData[zcfnum].name;
                zcf[name] = zcfData[zcfnum].value;
                // 以qlrid为每一组权利人的起始数据，作为分割依据
                if ((zcfnum + 1 < zcfData.length && zcfData[zcfnum + 1].name === 'sqrid') || zcfnum + 1 == zcfData.length) {
                    bdcSlSqrSingleData.sqrid=zcf.sqrid;
                    bdcSlSqrSingleData.bz=zcf.bz;
                    bdcSlSqrSingleData.xmid=xmid;
                    bdcSlSqrSingleData.sqrlb="2";
                    sqrlist.push(bdcSlSqrSingleData);
                    bdcSlSqrSingleData = {};
                }
            }
        }
        if(sqrlist != null &&sqrlist.length >0) {
            batchsaveAllSqrs(sqrlist, jyss_qllx, jyss_djxl);
        }
    }
}


// 页面上方保存按钮 保存权利人
function saveSqrTab($nowTab, tabXmid, tabdjxl) {
    var bdcSlSqrData = [];
    var sqrlist = [];
    var qlrnum = 0;
    var ywrnum = 0;
    var gyfs = "";
    var ywrgyfs = "";
    var tabQllx = $nowTab.data('qllx');
    if ($nowTab.find(".sqr").length != 0) {
        bdcSlSqrData = $nowTab.find(".sqr").serializeArray();
        var bdcSlSqrSingleData = {};
        for (var j = 0; j < bdcSlSqrData.length; j++) {
            var name = bdcSlSqrData[j].name;
            bdcSlSqrSingleData[name] = bdcSlSqrData[j].value;
            // 以qlrid为每一组权利人的起始数据，作为分割依据
            if ((j + 1 < bdcSlSqrData.length && bdcSlSqrData[j + 1].name === 'sqrid') || j + 1 == bdcSlSqrData.length) {
                if (Object.keys(bdcSlSqrSingleData).length ===1) {
                    continue;

                }
                if (bdcSlSqrSingleData.sqrlb === "1") {
                    qlrnum++;
                    gyfs += bdcSlSqrSingleData.gyfs + ",";
                }
                if (bdcSlSqrSingleData.sqrlb == '2') {
                    ywrnum++;
                    ywrgyfs += bdcSlSqrSingleData.gyfs + ",";
                }
                bdcSlSqrSingleData.xmid =tabXmid;
                toUpperClass(bdcSlSqrSingleData, ["zjh", "dlrzjh", "frzjh", "lzrzjh"]);
                sqrlist.push(bdcSlSqrSingleData);

                bdcSlSqrSingleData = {};
            }
        }
    }else{
        // 没有申请人 认为是成功的
        return true;
    }
    //验证权利人共有方式
    if (!checkGyfs(gyfs, qlrnum)) {
        throw err = new Error('权利人共有方式不正确，请检查！');
        return false;
    }

    //验证义务人共有方式
    if (!checkGyfs(ywrgyfs, ywrnum)) {
        throw err = new Error('义务人共有方式不正确，请检查！');
        return false;
    }

    // 验证共有比例
    if (!checkAddGybl(sqrlist)) {
        throw err = new Error('共有比例不正确，请检查！');
    }

    //验证婚姻信息比对结果
    if (!checkHyxxBdjg(sqrlist)) {
        throw err = new Error('婚姻信息比对失败，不允许保存');
    }

    console.log(sqrlist);
    var count = batchsaveAllSqrs(sqrlist, tabQllx, tabdjxl);
    // 更新的数量 == 页面的数量 则说明更新成功
    if(bdcSlSqrData.length == count){
        return true;
    }else{
        return false;
    }

}

/**
 * 上方保存按钮 需要保存所有申请人
 * @param sqrList
 */
function saveAllSqrs(sqrList){
    var count = 0;
    for(var i = 0;i<sqrList.length;i++){
        var bdcQlrData = sqrList[i];
        //申请人名称单独处理
        var BdcSlSqrDTO = {};
        var bdcSlSqrDTOList =[];
        BdcSlSqrDTO['BdcSlSqrDO'] =bdcQlrData;
        BdcSlSqrDTO['bdcSlJtcyDOList'] =[];
        bdcSlSqrDTOList.push(BdcSlSqrDTO);
        var url =  getContextPath() + "/slym/sqr/sqrxx?gzlslid=" + processInsId;
        $.ajax({
            url: url,
            type: 'PATCH',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(bdcSlSqrDTOList),
            async: false,
            success: function (data) {
                if (isNotBlank(data)) {
                    count++;
                }
            },
            error: function (e) {
                delAjaxErrorMsg(e);
            }
        });
    }
    return count;
}

function batchsaveAllSqrs(sqrList, tabqllx, tabdjxl) {
    var count = 0;
    var url = getContextPath() + "/slym/sqr/sqrxx/save?gzlslid=" + processInsId + "&qllx=" + tabqllx + "&djxl=" + tabdjxl;
    $.ajax({
        url: url,
        type: 'PATCH',
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(sqrList),
        async: false,
        success: function (data) {
            if (isNotBlank(data)) {
                count++;
            }
        },
        error: function (e) {
            delAjaxErrorMsg(e);
        }
    });
    return count;
}

/**
 * gyfs 共有方式集合，多个用英文逗号隔开
 * qlrnum 权利人个数
 * xmid 用于判断当前登记小类是否默认共同共有
 * 验证共有方式是否正确：1.一个权利人为单独所有 2.两个权利人共有方式不为单独所有且保持一致 3.三个及三个以上共有方式不为单独所有
 * 返回结果：是否验证通过
 */
function checkGyfs(gyfs, qlrnum,xmid) {
    if (qlrnum === 1) {
        // 如果 传递过XMID 则判断当前项目 的登记小类 是否为 默认共有方式为 GTGY
        if(xmid){
            var checkflag = false;
            getReturnData("/slym/qlr/checkdefaultgyfs", {gyfs: gyfs, xmid: xmid}, "GET", function (data) {
                checkflag = data;
            }, function () {
            }, false);
            if(checkflag){
                return true;
            }
        }
        //一个权利人时共有方式必须为单独所有
        if(!dgGyfsYz){
            return true;
        }
        if (gyfs.indexOf("1") < 0 && gyfs.indexOf("2") < 0 && gyfs.indexOf("3") < 0) {
            return true;
        }
    } else if (qlrnum === 2) {
        //两个权利人时共有方式不能存在单独所有，且两个共有方式保持一致
        var gyfsArr = gyfs.split(",");
        if (gyfsArr.length > 1 && gyfsArr[0] === gyfsArr[1] && gyfsArr[0] !== "0") {
            return true;
        }
    } else {
        //多个权利人时共有方式不能存在单独所有
        if (gyfs.indexOf("0") < 0) {
            return true;
        }
    }
    return false;
}

/**
 * qlrList 权利人集合
 * 验证共有比例之和为1：支持百分数，小数，分数（多个共有比例为统一类型）
 * 返回结果：是否验证通过
 */
function checkAddGybl(qlrList) {
    var sumgybl = 0;
    var gyblList = [];
    var qlrs = [];
    //分母相乘
    var fenmumultiply = 1;
    //分子之和
    var fenzicount = 0;
    for (var k in qlrList) {
        if (qlrList[k].qlrlb === "1" ||(qlrList[k].sqrlb === "1")) {
            qlrs.push(qlrList[k]);
        }
    }
    if (isNotBlank(qlrs)) {
        for (var k in qlrs) {
            if (qlrs[k].gyfs === "2") {
                if (isNotBlank(qlrs[k].qlbl)) {
                    var qlbl = qlrs[k].qlbl;
                    if (isNaN(qlbl)) {
                        if (qlbl.indexOf("%") > -1) {
                            gyblList.push(parseFloat(qlbl.substr(0, qlbl.length - 1)));
                        } else if (qlbl.indexOf("/") > -1) {
                            var fenmu = parseInt(qlbl.split("/")[1]);
                            fenmumultiply = fenmumultiply * fenmu;
                        }
                    } else {
                        gyblList.push(parseFloat(qlrs[k].qlbl) * 100);

                    }
                } else {
                    //按份共有共有比例必须有值
                    return false;
                }
            } else if (isNotBlank(qlrs[k].qlbl)) {
                throw err = new Error('非按份共有请勿填写共有比例！');
            }

        }
    } else {
        //无权利人直接通过
        return true;
    }
    if (gyblList.length > 0) {
        for (var i in gyblList) {
            sumgybl += formatFloat(gyblList[i]);
        }
        if (formatFloat(sumgybl) === 100) {
            return true;

        }
    } else if (fenmumultiply > 1) {
        for (var k in qlrList) {
            if (qlrList[k].qlrlb === "1" && isNotBlank(qlrList[k].qlbl)) {
                var qlbl = qlrList[k].qlbl;
                if (qlbl.indexOf("/") > -1) {
                    var fenmu = parseInt(qlbl.split("/")[1]);
                    var fenzi = parseInt(qlbl.split("/")[0]);
                    fenzicount += fenmumultiply / fenmu * fenzi;
                }
            }
        }
        if (fenmumultiply === fenzicount) {
            return true;

        }
    } else {
        return true;

    }
    return false;
}

function checkHyxxBdjg(qlrList){
    for (var k in qlrList) {
        if (qlrList[k].sqrlb === "1" &&hasProperty(qlrList[k], "hyxxbdjg") &&qlrList[k].hyxxbdjg !=="1" &&qlrList[k].sbfwtc !=="9") {
            return false;
        }
    }
    return true;

}

//计算使用期限（单位：月）
function countSyqxByMonth(){
    var $layuishow = $('.layui-show');
    var dyaqssj = $layuishow.find("#dyaq-zwlxqssj").val();
    var syqx = parseInt($layuishow.find(".zwlxqx").val());
    if (syqx > 0) {
        //计算结束时间后的日期 日 要减一天
       if (isNotBlank(dyaqssj)) {
            var qssj = new Date(dyaqssj);
            qssj.setMonth(qssj.getMonth() + syqx);
            var time = qssj.getTime() - 1000 * 60 * 60 * 24;
            console.log(Format(formatChinaTime(qssj), "yyyy-MM-dd"));
            $layuishow.find("#dyaq-zwlxjssj").val(Format(formatChinaTime(new Date(time)), "yyyy-MM-dd"));
        }
    }
}

