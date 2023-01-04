var layer, laytpl, form,table,$;
//页面入口
var sjclids = new Set();
var qlrLength = 0;
//是否虚拟号
var isXndyh = false;
//不动产单元信息表格配置
var tableConfig = {};
var includeDyaq=false;
var qlxxdata;

//非受理节点优先展示权利所需
var isSljd = false,isQtjd = false;
var taskId = getQueryString("taskId");
var processInsId = getQueryString("processInsId");

var djxl;
var cfdqJflc = cfdqJflc();
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    form = layui.form;
    $ = layui.jquery;
    layer = layui.layer;
    laytpl = layui.laytpl;
    table = layui.table;
    //form验证
    form.on('checkbox(mjdw)', function () {
        $("[name='mjdw']").prop("checked", "");
        $(this).prop("checked", "checked");
        form.render('checkbox');
    });
    form.render();
    var element = layui.element;
    element.init();
    titleShowUi();
    addModel();
    setTimeout("loadButtonArea('slympl')", 10);
    setTimeout(function () {
        try {
            $.when(generateTabContent()).done(function () {

            })
        } catch (e) {
            removeModal();
            ERROR_CONFIRM("加载失败", e.message);
            return
        }
    }, 10);
    $(function () {
        //监听第一次单击tab，
        var bdcdyhIndex = 0;
        element.on('tab(tabFilter)', function (data) {
            var tabid = $(".layui-tab-title .layui-this").attr("id");
            if (tabid === "bdcdyh") {
                if (bdcdyhIndex === 0 && isSljd) {
                    bdcdyhIndex++;
                    addModel();
                    setTimeout(function () {
                        $.when(loadPlQlxx(), loadBdcdyh()).done(function () {
                            var a = setInterval(function () {
                                if ($('.bdc-form-div .layui-show .bdc-table-box .layui-table-main>.layui-table').height() !== 0) {
                                    getStateById(readOnly, formStateId, "slympl", "qlxxTab");
                                    clearInterval(a);
                                }
                            }, 50);
                        });
                    }, 0);

                }
            }
        });
        //监听 权利信息 内 附记 单击
        $('.layui-tab-content').on('click','.bdc-qlqtzk-popup',function(){
            $('#qlqtzksdtx').removeAttr('style');
        });

        //监听 权利信息 内 附记 单击
        $('.layui-tab-content').on('click','.bdc-pj-popup',function(){
            $('#fjsdtx').removeAttr('style');
        });
    });

    //监听复选框选择
    //全选
    form.on('checkbox(qxCheckbox)', function (data) {

        $('td input[name=yxTable]').each(function (index, item) {
            item.checked = data.elem.checked;
            var qxData = item.value;
            //如果是选中的则添加，否则全部删除
            if (item.checked) {
                sjclids.add(qxData)
            } else {
                sjclids.delete(qxData);
            }
        });
        form.render('checkbox');
    });
    //单个的
    form.on('checkbox(yxCheckbox)', function (data) {
        var checkedLength = $('td .yx+.layui-form-checked[lay-skin=primary]').length;
        var checkBoxLength = $('td .yx+.layui-form-checkbox[lay-skin=primary]').length;
        var qxCheckBox = $('.gray-tr input[name=qxTable]')[0];
        var sjclid = data.value;
        if (sjclids.has(sjclid)) {
            sjclids.delete(sjclid);
        } else {
            sjclids.add(sjclid);
        }
        //判断是否全选，全选的时候勾选最上面的复选框
        if (checkedLength == checkBoxLength) {
            qxCheckBox.checked = true;
        } else {
            qxCheckBox.checked = false;
        }
        form.render('checkbox');
    });

    //监听权利tab修改证件类型
    form.on('select(zjzl)', function (data) {
        if (data.value == '1') {
            $(data.elem).parents('td').next().find('input').attr('lay-verify', 'identitynew');
        } else {
            $(data.elem).parents('td').next().find('input').attr('lay-verify', '');
        }
    });

    //监听抵押类型修改 zgzqe 和 bdbzzqse 的lay-verify属性值;
    form.on('select(dyfs)', function (data) {
        //获取当前tab页的zgzqe和bdbzzqse对象；
        var currentTab = document.getElementsByClassName("layui-show");
        $currentTab = $(currentTab);
        var $currentZgzqe = $currentTab.find("#dyaq-zgzqe");
        var $currentBdbzzqse = $currentTab.find("#dyaq-bdbzzqse");

        //一般抵押时，zgzqe不必填，bdbzzqse必填
        if (data.value == '1' && $currentZgzqe.length > 0 && $currentBdbzzqse.length > 0) {
            $currentZgzqe[0].setAttribute("lay-verify","");
            //移除dyaq-zgzqe必填背景色和标志*
            $currentZgzqe[0].setAttribute("style","");
            $currentZgzqe.parents(".layui-inline").removeClass("bdc-not-null");
            $currentZgzqe.parents(".layui-inline").find(".required-span").remove();

            $currentBdbzzqse[0].setAttribute("lay-verify","required");
            //添加dyaq-bdbzzqse必填背景色和标志*
            $currentBdbzzqse.parents(".layui-inline").addClass("bdc-not-null");
            var requiredArr = $currentBdbzzqse.parents(".layui-inline").find(".required-span");
            if(requiredArr.length ===0) {
                $currentBdbzzqse.parents(".layui-inline").find("label").prepend("<span class=\"required-span\"><sub>*</sub></span>");
            }
        } else if ($currentZgzqe.length > 0 && $currentBdbzzqse.length > 0){
            $currentZgzqe[0].setAttribute("lay-verify","");
            $currentZgzqe.parents(".layui-inline").removeClass("bdc-not-null");
            $currentZgzqe.parents(".layui-inline").find(".required-span").remove();

            $currentBdbzzqse[0].setAttribute("lay-verify","");
            $currentBdbzzqse.parents(".layui-inline").removeClass("bdc-not-null");
            $currentBdbzzqse.parents(".layui-inline").find(".required-span").remove();
            getStateById(readOnly, formStateId, 'slympl');
        }
        // form.render();
    });

    //监听领证人修改证件类型
    form.on('select(lzrzjhFilter)', function (data) {
        console.log(data.elem); //得到select原始DOM对象
        console.log(data.value); //得到被选中的值
        console.log(data.othis); //得到美化后的DOM对象
        var forms = data.elem.parentNode.parentNode.parentNode;
        var attrVal = $(forms).find("input[name=lzrzjh]").attr("lay-verify");
        if (data.value == '1') {
            if (isNotBlank(attrVal)) {
                if (attrVal.indexOf("identitynew") < 0) {
                    $(forms).find("input[name=lzrzjh]").attr("lay-verify", attrVal + "|identitynew");
                }
            } else {
                $(forms).find("input[name=lzrzjh]").attr("lay-verify", "identitynew");
            }
        } else {
            //移除身份证验证
            if (isNotBlank(attrVal) && attrVal.indexOf("identitynew") > -1) {
                $(forms).find("input[name=lzrzjh]").attr("lay-verify", attrVal.replace("identitynew", ""));
            }
        }
    });

    //重新生成权利其他状况
    $(".bdc-form-div").on('click','.resetqlqtzk', function () {
        var xmid =$(this).data("xmid");
        reGenerateQlqtzk(xmid,"2");
    });

    //重新生成附记
    $(".bdc-form-div").on('click','.resetfj', function () {
        var xmid =$(this).data("xmid");
        reGenerateFj(xmid,"3");
    });

    //监听查封类型 选择轮候查询的时候 清空开始结束时间
    form.on('select(cflx)', function (data) {
        if (data.value == '2') {
            $("#cf-cfqssj").val('');
            $("#cf-cfjssj").val('');
        }
    });

    //点击单位名称下拉框，动态展示新户主名称，核验按钮
    form.on('select(gs-dw)', function(data){
        var ywlx = 1;
        loadSdqXhzAndHy(ywlx,data.value);
    });

    //点击单位名称下拉框，动态展示新户主名称，核验按钮
    form.on('select(gq-dw)', function(data){
        var ywlx = 3;
        loadSdqXhzAndHy(ywlx,data.value);
    });
});
var zdList;
var xmid;
// 用于南通更新合同信息的xmid
var xmidNtHtxx;
// 登记原因
var djxldjyyList = {};
var djxlMap = queryDjxlPz();
var formIds = "";
var qllx;
var sfdydj;
var qlrCache;
var bdcXm;


function generateTabContent() {
    var xmxx;
    var qlxx;
    var xmfb={};
    $.ajax({
        url: getContextPath() + "/slym/xm",
        type: 'GET',
        dataType: 'json',
        async: false,
        data: {processInsId: processInsId},
        success: function (data) {
            if (isNotBlank(data)) {
                qllx = data.qllx;
                xmid = data.xmid;

                getReturnData("/slym/xm/fb", {xmid: xmid}, "GET", function (result) {
                    xmfb =result;

                },function (xhr) {
                    delAjaxErrorMsg(xhr);
                },false);
                bdcXm = data;
                //面积单位为空时默认为平方米
                if (data.mjdw === null || data.mjdw === '') {
                    data.mjdw = '1'
                }
                if (isNotBlank(data.djxl)) {
                    djxl = data.djxl;
                    getDjyyList(data.djxl);
                }
                xmxx = data;

                getReturnData("/slym/ql/pllc", {processInsId: processInsId, sfcxql: false}, 'GET', function (data) {
                    if (isNotBlank(data)) {
                        qlxx = data[0];
                        qlxxdata = data[0];
                        sfdydj = data[0].dydj;
                        //加载页面的时候就判断是否抵押权，该参数用于打印抵押类型
                        if(sfdydj) {
                            includeDyaq = true;
                        }
                    }
                }, function (err) {
                    delAjaxErrorMsg(err);
                }, false);
                //判断是否是虚拟单元号
                checkXndyhPl();
            }

        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var form = layui.form;
        var laytpl = layui.laytpl;
        var json = {
            qllx: qllx,
            qlxx: qlxx
        };
        getReturnData("/rest/v1.0/slym/jdmc", {taskId: taskId}, 'GET', function (data) {
            if (isNotBlank(data)) {
                if(data.taskName == '受理'){
                    isSljd = true;
                }else {
                    isQtjd = true;
                }
            }
        }, function (err) {
            console.log(err);
        }, false);
        json['sljd'] = isSljd;
        json['qtjd'] = isQtjd;
        var liTpl = liTableTpl.innerHTML, liView = document.getElementById('tableUi');
        //渲染数据
        laytpl(liTpl).render(json, function (html) {
            liView.innerHTML = liView.innerHTML + html;
        });
        var tpl = tabContentTpL.innerHTML, view = document.getElementById("tabContent");
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
        renderDate(form, formIds);
        renderSelect();
        loadData(xmxx,xmfb);

        if (isQtjd) {
            $.when(loadPlQlxx(), loadBdcdyh()).done(function () {
                var a = setInterval(function () {
                    if ($('.bdc-form-div .layui-show .bdc-table-box .layui-table-main>.layui-table').height() !== 0) {
                        getStateById(readOnly, formStateId, "slympl", "qlxxTab");
                        clearInterval(a);
                    }
                }, 50);
            });
        }
    })
}

function loadData(xmxx,xmfb) {
    getReturnData("/bdczd", '', 'POST', function (data) {
        removeModal();
        if (isNotBlank(data)) {
            zdList = data;
            //加载基本信息
            generateJbxx(xmxx,xmfb);
            //加载申请人
            loadQlr();
            //加载收件材料
            loadSjcl();
        }
    }, function (err) {
        delAjaxErrorMsg(err);
    });
}

function getDjyyList(djxl) {
    $.ajax({
        url: getContextPath() + "/slym/xm/queryDjxlDjyy",
        type: 'GET',
        dataType: 'json',
        data: {djxl: djxl},
        async: false,
        success: function (data) {
            if (isNotBlank(data)) {
                djxldjyyList = data;
            }
        }
    });
}

function loadJbxx() {
    $.ajax({
        url: getContextPath() + "/slym/xm",
        type: 'GET',
        dataType: 'json',
        data: {processInsId: processInsId},
        success: function (data) {
            removeModal();
            if (isNotBlank(data)) {
                xmid = data.xmid;
                slbh = data.slbh;
                getReturnData("/slym/xm/fb", {xmid: xmid}, "GET", function (result) {
                    //面积单位为空时默认为平方米
                    if (data.mjdw === null || data.mjdw === '') {
                        data.mjdw = '1'
                    }
                    if (isNotBlank(data.djxl)) {
                        getDjyyList(data.djxl);
                    }
                    generateJbxx(data,result);
                }, function (xhr) {
                    delAjaxErrorMsg(xhr);
                });
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}


function loadQlr() {
    addModel();
    var param = {
        xmidList:""
    };
    //权利人
    $.ajax({
        url: getContextPath() + "/slym/qlr/groupQlr?gzlslid="+processInsId+"&qlrlb=1",
        type: 'POST',
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(param),
        async: false,
        success: function (data) {
            var qlrxx = [];
            if (data) {
                $.each(data, function(index, value){
                    qlrxx.push(value.bdcQlrDO);
                });
                qlrLength = qlrxx.length;
                qlrCache = qlrxx;
            }
            removeModal();
            generateQlrxx(qlrxx, "sqrxx");
            //重新加载抵押人查封机关信息
            reloadDyrAndCfjg(xmid, "");
            if (qllx === 94) {
                updateFdcq3Qlr(processInsId);
            }
            loadDsQlr('');
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
    //义务人
    $.ajax({
        url: getContextPath() + "/slym/qlr/groupywr?gzlslid="+processInsId,
        type: 'POST',
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(param),
        success: function (data) {
            removeModal();
            generateQlrxx(data, "qlrTable");
            //加载权利信息模块部分信息
            reloadQlxxForYwr(data);
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

/**
 * 处理权利信息中义务人相关信息
 */
function reloadQlxxForYwr(ywrdata) {
    //抵押人
    var dyr = $("#dyaq-dyr");
    //供役地权利人
    var gydqlr = $("#dyiq-gydqlr");
    if ((dyr != null &&dyr.length >0) || (gydqlr != null &&gydqlr.length >0)) {
        //权利表需要展现义务人的字段，需要拼接所有义务人
        var ywr = dealYwr(ywrdata);
        if (dyr !== undefined && dyr != null) {
            $(dyr).val(ywr);
            dyr.title = ywr;
        }
        if (gydqlr !== undefined && gydqlr != null) {
            $(gydqlr).val(ywr);
            gydqlr.title = ywr;
        }
    }
}

/*
* 获取领证人信息
*/
function loadLzrxx() {
    getReturnData("/slym/lzr/nt/lzrxx", {xmid: xmid}, 'GET', function (data) {
        generateLzrxx(data);
    }, function (err) {
        delAjaxErrorMsg(err);
    });
}

//加载bdc_Csh_fwkg_sl 表的sfhz（是否换证）字段
<!--bdc_Csh_fwkg_sl 表的sfhz（是否换证）字段  sfhz  0: 否（不换证 继承） 1：是 （换证 不继承）-->
function getSfhz(xmxx){
    if(!document.getElementById("sfjcybdcqzhDiv")){
        //如果页面不存在该元素，则不加载收费状态
        return ;
    }
    if ($("#sfjcybdcqzhDiv").css("display") =="none"){
        return;
    }
    $.ajax({
        url: '/realestate-accept-ui/slym/xm/getBdcCshFwkgSlDO?xmid=' + xmxx.xmid,
        type: 'GET',
        dataType: "json",
        success: function (data) {
            if (data && data.sfhz !== null && data.sfhz !== undefined) {
                $("input[name='sfjcybdcqzh'][value=" + data.sfhz + "]").prop("checked", "checked");
                form.render();
                resetSelectDisabledCss();
            }
        },
        error: function (error) {
            delAjaxErrorMsg(error);

        }
    });
}


/*
* 加载领证人信息
*/
function generateLzrxx(lzrdata) {
    var json = {
        bdclzrList: lzrdata,
        xmid: xmid,
        zd: zdList
    };
    var tpl = lzrxxTpl.innerHTML, view = document.getElementById("lzrxx");
    //渲染数据
    if (view !== null) {
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
        form.render();
        getStateById(readOnly, formStateId, 'slympl', "lzrxx");
        form.render('select');
        resetSelectDisabledCss();
    }
}

function loadSjcl() {
    addModel();
    $.ajax({
        url: getContextPath() + "/slym/sjcl/list/pl",
        type: 'GET',
        dataType: 'json',
        data: {processInsId: processInsId},
        success: function (data) {
            removeModal();
            generateSjcl(data);
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}


function generateJbxx(bdcxmxx,xmfb) {
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var form = layui.form;
        var laytpl = layui.laytpl;
        var qllx = bdcxmxx.qllx;
        if (qllx === 97) {
            changeQlrlbMc();
        }
        //申请分别持证为空默认为是
        if(bdcxmxx &&bdcxmxx.sqfbcz ==null){
            bdcxmxx.sqfbcz =1;
        }
        var json = {
            bdcxmxx: bdcxmxx,
            zd: zdList,
            djxldjyy: djxldjyyList,
            xmfb:xmfb
        };
        var tpl = jbxxTpl.innerHTML, view = document.getElementById('sljbXx');
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
        //抵押,查封,预抵押,异议,地役的登记原因和是否分别持证在权利页面不需要再次加载
        //zsmodel有值的话说明是商品房首次登记
        if (qllx !== parseInt(commonDyaq_qllx) && !sfdydj && qllx !== 97 && qllx !== 98 && qllx !== 19) {
            var xmxxtpl = xmxxTpl.innerHTML, xmxxview = document.getElementById('xmxx');
            //渲染数据
            laytpl(xmxxtpl).render(json, function (html) {
                xmxxview.innerHTML = html;
            });

        }
        form.render();
        renderDate(form, formIds);
        //给下拉框添加删除图标
        renderSelectClose(form);
        getStateById(readOnly, formStateId, 'slympl', "xmxx");
        //监听修改字段
        if (isNotBlank(xgnrglxs) && xgnrglxs.indexOf(bdcxmxx.djxl) > -1) {
            formIds = "tabContent";
            renderChange("", form, formIds);
        }
        renderSelect();
        disabledAddFa();
    })
}


function generateQlrxx(data, id) {
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var form = layui.form;
        var laytpl = layui.laytpl;
        var json = {
            bdcQlrDOList: data,
            zd: zdList,
            qlrLength: qlrLength
        };
        if (id == 'qlrTable') {
            //无数据隐藏
            if (data && data.length > 0) {
                $("#qlrTable .bdc-table-none").hide();
            }
            //渲染义务人
            var tpl = ywrTpl.innerHTML, view = $('#' + id).find('tbody');
            //渲染数据
            laytpl(tpl).render(json, function (html) {
                view.append(html);
            });
        } else {
            var tpl = sqrTpl.innerHTML, view = document.getElementById(id);
            //渲染数据
            laytpl(tpl).render(json, function (html) {
                view.innerHTML = html;
            });
        }

        form.render();
        if($(".bdc-qlr").length >0) {
            renderBackgroundColor('bdc-qlr');
        }
        getStateById(readOnly, formStateId, 'slympl');
        renderSelect();
        disabledAddFa("sqrForm");
    })
}

var sjclNumber = 0;
//用于存放所有的收件材料id
var sjclidLists = [];

function generateSjcl(data) {
    if (data !== null && data.length > 0) {
        for (var i = 0; i < data.length; i++) {
            sjclidLists.push(data[i].sjclid);
        }
    }
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var form = layui.form;
        var laytpl = layui.laytpl;
        var json = {
            bdcSlSjclDOList: data,
            zd: zdList
        };
        sjclNumber = data.length;
        var tpl = sjclTpl.innerHTML, view = document.getElementById('sjclxx');
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
            form.render('checkbox');

        });
        form.render('select');
        getStateById(readOnly, formStateId, "slympl");
        form.render('select');
        renderSelect();
        disabledAddFa("sjclForm");
    })
}

function loadBdcdyh() {
    layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
        var $ = layui.jquery;
        var table = layui.table;

        var isSearch = true;
        $(document).keydown(function (event) {
            if (event.keyCode == 13 && isSearch) { //绑定回车
                $("#searchBdcdy").click();
            }
        });
        // //判断回车操作
        $('.bdc-tab-box').on('focus', '.layui-laypage-skip .layui-input', function () {
            isSearch = false;
        }).on('blur', '.layui-laypage-skip .layui-input', function () {
            isSearch = true;
        });

        //获取权利信息表头
        var unitTableTitle = getQlCols();
        var url = getContextPath() + '/slym/ql/listQlByPageJson';

        // 查询参数
        var data = {};
        if (zxlc === "true") {
            //注销流程
            data["sfyql"] = true;
        }
        data["qllx"] = qllx;

        // 根据当前查询参数，获取所有的单元信息，用于数据导出
        data["gzlslid"] = processInsId;
        if (qllx === 4 || qllx === 6 || qllx === 8) {
            $(".bdc-table-zj").removeClass("bdc-hide");
            var height = $('.bdc-table-zj').height();
            $(".bdc-line-search-container").css("padding-top", height + 52);
            //处理合计信息
            dealHjxx();
            $(".layui-show #bdcdyCount").text(qlxxdata.bdcdyCount);
        }

        //提交表单
        $("#searchBdcdy").click(function () {
            var bdcdyArray = $(".bdcdyForm").serializeArray();
            bdcdyArray.forEach(function (item) {
                data[item.name] = item.value;
            });
            //刷新
            if (qllx === 4 || qllx === 6 || qllx === 8) {
                $(".bdc-table-zj").removeClass("bdc-hide");
                var height = $('.bdc-table-zj').height();
                $(".bdc-line-search-container").css("padding-top", height + 52);
                //处理合计信息
                dealHjxx();
                $(".layui-show #bdcdyCount").text(qlxxdata.bdcdyCount);
            }
            $.when(tableReload('xmid', data, url, 'not')).done(function () {
                var a = setInterval(function () {
                    if ($('.bdc-form-div .layui-show .bdc-table-box .layui-table-main>.layui-table').height() !== 0) {
                        getStateById(readOnly, formStateId, "slympl");
                        clearInterval(a);
                    }
                }, 50);
            });
            return false;
        });

        //重置操作
        $("#reset").on('click', function () {
            $('.bdc-form-search input').val('');
        });


        tableConfig = {
            id: 'xmid',
            url: url,
            contentType: 'application/json',
            method: 'post',
            where: data,
            toolbar: "#toolbarBdcdyh",
            defaultToolbar: ['filter'],
            limit: 50,
            cols: [unitTableTitle]
            , parseData: function (res) {
                res.content.forEach(function (v) {
                    v.yxmid = queryYxmid(v.xmid);
                });
            }
            , done: function () {
                changeCheckboxBackground1([{name: 'bdc-change-blue', color: '#1D87D1'}]);
                var reverseList = ['zl'];
                reverseTableCell(reverseList, 'xmid');

                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');

                if ($('.bdc-form-div .layui-show .bdc-table-box .layui-table-main>.layui-table').height() == 0) {
                    $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
                } else {
                }
                $('.bdc-table-box').on('mouseenter', 'td', function () {
                    if ($(this).text() && ($(this).attr("data-field") === "zl" || $(this).attr("data-field") === "zldz")) {
                        $(this).append('<div class="layui-table-grid-down"><i class="layui-icon layui-icon-down"></i></div>')
                    }
                    $('.layui-table-grid-down').on('click', function () {
                        setTimeout(function () {
                            $('.layui-table-tips-main .bdc-table-date').html(reverseString($('.layui-table-tips-main .bdc-table-date').html()));
                        }, 20);
                    });
                });
                var a = setInterval(function () {
                    if ($('.bdc-form-div .layui-show .bdc-table-box .layui-table-main>.layui-table').height() !== 0) {
                        getStateById(readOnly, formStateId, "slympl");
                        clearInterval(a);
                    }
                }, 50);
            }
        };

        //加载表格
        loadDataTablbeByUrl('#bdcdyTable', tableConfig);
        renderSearchInput();
        //头工具栏事件
        table.on('toolbar(bdcdyTable)', function (obj) {
            var layEvent = obj.event; //获得 lay-event 对应的值
            if (layEvent === "plxg") { //批量修改
                var checkStatus = table.checkStatus(obj.config.id);
                var checkData = checkStatus.data;
                var xmids = [];
                if (checkData.length === 0) {
                    showConfirmDialog("提示", "未选择数据，默认修改全部数据?", "openPlxg", "''", "", "");
                } else {
                    for (var i = 0; i < checkData.length; i++) {
                        var xmid = checkData[i].xmid;
                        xmids.push(xmid);
                    }
                    openPlxg(xmids);
                }

            } else if (layEvent === 'export') {
                exportExcel(obj.config.cols[0]);
            } else if (layEvent === "add") {
                //新增不动产单元
                var url = getContextPath() + "/view/query/selectBdcdyh.html?gzlslid=" + processInsId + "&zlcsh=true";
                var index = layer.open({
                    type: 2,
                    title: "选择不动产单元",
                    area: ['1300px', '600px'],
                    fixed: false, //不固定
                    maxmin: true, //开启最大化最小化按钮
                    content: url
                });
                layer.full(index);
            } else if (layEvent === "delete") {
                //删除不动产单元
                var checkStatus = table.checkStatus(obj.config.id);
                var checkData = checkStatus.data;
                var xmids = [];
                if (checkData.length === 0) {
                    showAlertDialog("未选择数据")
                } else {
                    for (var i = 0; i < checkData.length; i++) {
                        var xmid = checkData[i].xmid;
                        xmids.push(xmid);
                    }
                    getReturnData("/slxxcsh/deleteBdcdy?xmids=" + xmids+"&gzlslid="+processInsId, {}, 'DELETE', function (data) {
                        ityzl_SHOW_SUCCESS_LAYER("删除成功");
                        addModel();
                        // 先查询不动产单元数量
                        var sfyql = false;
                        if (zxlc === "true") {
                            sfyql = true;
                        }
                        var obj = {};
                        obj.sfyql = sfyql;
                        obj.qllx = qllx;
                        obj.loadTotal = false;
                        obj.page = 1;
                        obj.size = 10;
                        obj.gzlslid = processInsId;
                        getReturnData("/slym/ql/listQlByPageJson", JSON.stringify(obj), 'POST', function (data) {
                            removeModal();
                            // 如果删除后只剩下单个的不动产单元页面，刷新 portal 页面
                            if (data.totalElements === 1) {
                                var getUrl = parent.window.location.href;
                                setTimeout(function () {
                                    parent.window.location.href = getUrl;
                                }, 100);

                                var newUlr = changeURLPar(getUrl, 'timestamp', new Date().getTime());
                                window.location.href = newUlr
                            } else {
                                loadBdcdyh();
                            }
                        });
                    }, function (err) {
                        delAjaxErrorMsg(err);
                    });


                }


            }


            return false;


        });

        // 复选框事件
        table.on('checkbox(bdcdyTable)', function (obj) {
            // 保存选中的xmid
            if (obj.checked) {
                xmidNtHtxx = obj.data.xmid;
            } else {
                xmidNtHtxx = "";
            }
            console.log("xmidNtHtxx:", xmidNtHtxx);
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
                    $nowBtnMore.css({top: tableTop + 26 - scrollTop, left: tableLeft - 30});
                } else {
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
                $btnMore.css({top: tableTop + 26 - scrollTop, left: tableLeft - 30});
            } else {
                $btnMore.css({top: tableTop - scrollTop - $btnMore.height(), left: tableLeft - 30});
            }
            $('.bdc-table-btn-more').hide();
            $btnMore.show();
        });
        //点击更多内的任一内容，隐藏
        $('.bdc-table-btn-more a').on('click', function () {
            $(this).parent().hide();
        });
        //点击页面任一空白位置，消失
        $('body').on('click', function () {
            $('.bdc-table-btn-more').hide();
        });
    })
}


function queryQllx(qllx) {
    if (isNotBlank(zdList.qllx)) {
        for (var i = 0; i < zdList.qllx.length; i++) {
            var qllxZd = zdList.qllx[i];
            if (qllx == qllxZd.DM) {
                qllx = qllxZd.MC;
                break;
            }
        }
    }
    return qllx;
}

function queryZdzhyt(zdzhyt) {
    if (isNotBlank(zdList)) {
        for (var i = 0; i < zdList.tdyt.length; i++) {
            var tdytZd = zdList.tdyt[i];
            if (zdzhyt == tdytZd.DM) {
                zdzhyt = tdytZd.MC;
                break;
            }
        }
    }
    return zdzhyt;
}

function queryDzwyt(dzwyt) {
    if (isNotBlank(zdList.fwyt)) {
        for (var i = 0; i < zdList.fwyt.length; i++) {
            var dzwytZd = zdList.fwyt[i];
            if (dzwyt == dzwytZd.DM) {
                dzwyt = dzwytZd.MC;
                break;
            }
        }
    }
    return dzwyt;
}

function queryGhyt(ghyt) {
    if (isNotBlank(zdList.fwyt)) {
        for (var i = 0; i < zdList.fwyt.length; i++) {
            var ghytZd = zdList.fwyt[i];
            if (ghyt == ghytZd.DM) {
                ghyt = ghytZd.MC;
                break;
            }
        }
    }
    return ghyt;
}

//打开批量修改页面
function openPlxg(xmids) {
    //打开前先清除缓存数据
    sessionStorage.removeItem('plxg_xmids');
    //数据过多,存入缓存
    sessionStorage.setItem('plxg_xmids', xmids);
    var index = layer.open({
        type: 2,
        title: "批量修改",
        area: ['1300px', '600px'],
        fixed: false, //不固定
        maxmin: true, //开启最大化最小化按钮
        content: getContextPath() + "/view/slym/plxg.html?processInsId=" + processInsId + "&xmid=" + xmid + "&formStateId=" + formStateId + "&zxlc=" + zxlc,
        cancel: function () {
            $.when(loadBdcdyh()).done(function () {
                loadPlQlxx();
                var a = setInterval(function () {
                    if ($('.bdc-form-div .layui-show .bdc-table-box .layui-table-main>.layui-table').height() !== 0) {
                        getStateById(readOnly, formStateId, "slympl", "qlxxTab");
                        clearInterval(a);
                    }
                }, 50);
            });
        }
    });
    layer.full(index);
}

//查看原项目ID
function queryYxmid(xmid) {
    var yxmid = "";
    $.ajax({
        url: getContextPath() + "/slym/ql/queryYxmid",
        type: 'GET',
        data: {xmid: xmid},
        async: false,
        success: function (data) {
            yxmid = data;
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
    return yxmid;
}


function getAllBdcdy(param) {
    var allData = {};
    $.ajax({
        url: getContextPath() + '/slym/ql/listQl/all',
        type: "POST",
        dataType: "json",
        data: JSON.stringify(param),
        timeout: 10000,
        contentType: "application/json",
        async: false,
        success: function (data) {
            if (data) {
                $.each(data, function (index, item) {
                    item.ghyt = changeDmToMc(item.ghyt, zdList.fwyt);
                    item.dzwyt = changeDmToMc(item.dzwyt, zdList.dzwyt);
                    item.fwlx = changeDmToMc(item.fwlx, zdList.fwlx);
                    item.fwxz = changeDmToMc(item.fwxz, zdList.fwxz);
                    item.fwjg = changeDmToMc(item.fwjg, zdList.fwjg);
                    item.dyfs = changeDmToMc(item.dyfs, zdList.dyfs);
                    item.cflx = changeDmToMc(item.cflx, zdList.cflx);
                    item.ygdjzl = changeDmToMc(item.ygdjzl, zdList.ygdjzl);
                    item.dyfs = changeDmToMc(item.dyfs, zdList.dyfs);
                    item.qllx = changeDmToMc(item.qllx, zdList.qllx);
                    item.zdzhyt = changeDmToMc(item.zdzhyt, zdList.tdyt);
                });
                allData = data;
            }
        }
    });
    return allData;
}

// 导出Excel
// 通过隐藏form提交方式，避免ajax方式不支持下载文件
function exportExcel(cols) {
    var exportCol = {};
    for (var index in cols) {
        if (cols[index].hide == false && cols[index].type != 'checkbox' && !cols[index].toolbar && cols[index].field != 'cz') {
            exportCol[cols[index].title] = cols[index].field
        }
    }
    var tempForm = $("<form>");
    tempForm.attr("id", "exportFrom");
    tempForm.attr("style", "display:none");
    tempForm.attr("target", "export");
    tempForm.attr("method", "post");
    tempForm.attr("action", '/realestate-accept-ui/slym/ql/exportBdcdyXxByGzlslid');
    var dataInput = $("<input>");
    dataInput.attr("type", "hidden");
    dataInput.attr("name", "exportCol");
    dataInput.attr("value", encodeURIComponent(JSON.stringify(exportCol)));
    tempForm.append(dataInput);
    var dataInputGzlslid = $("<input>");
    dataInputGzlslid.attr("type", "hidden");
    dataInputGzlslid.attr("name", "gzlslid");
    dataInputGzlslid.attr("value", processInsId);
    tempForm.append(dataInputGzlslid);
    tempForm.on("submit", function () {
    });
    tempForm.trigger("submit");
    $("body").append(tempForm);
    tempForm.submit();
    $("exportFrom").remove();
    return false;
}

/**
 * 打印不动产单元列表
 * @returns {boolean}
 */
function printBdcdyList() {
    var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/bdcdy/" + processInsId + "/bdcdyList/xml";
    var modelUrl = bdcdyListModelUrl;
    print(modelUrl, dataUrl, false);

    // 禁止提交后刷新
    return false;
}

// 组织参数调用评价器, groupByQxdm: true 控制评价器的 URL 地址是否根据 qxdm 来分。
function evaluate() {
    getReturnData("/pjq", {gzlslid: processInsId, groupByQxdm: true}, "GET", function (data) {
        if (data !== null) {
            pj(data);
        }
    }, function (err) {
        removeModal();
        delAjaxErrorMsg(err);
    })
}

function pj(pjdata) {
    if (pjdata.url === "exchange") {
        addModel();
        getReturnData("/pjq/xmoke", {
            ywbh: pjdata.bdcXmDO.slbh,
            jdmc: "受理",
            blry: pjdata.bdcXmDO.slr,
            sqrxm: pjdata.qlrmc,
            sqrlxfs: pjdata.qlrlxfs
        }, "GET", function (data) {
            if (data && data.code == "1") {
                ityzl_SHOW_SUCCESS_LAYER("评价成功");
            } else {
                ityzl_SHOW_WARN_LAYER(data.message);
            }
            removeModal();
        }, function (xhr) {
            delAjaxErrorMsg(xhr);
        }, false)
    } else {
        var url = pjdata.url + "?ywbh=" + pjdata.bdcXmDO.slbh + "&jdmc=受理" + "&blry=" + pjdata.bdcXmDO.slr + "&sqrxm=" + pjdata.qlrmc + "&sqrlxfs=" + pjdata.qlrlxfs;
        getReturnData("/pjq/insertPjq", {
            ywbh: pjdata.bdcXmDO.slbh,
            jdmc: "受理",
            blry: pjdata.bdcXmDO.slr,
            sqrxm: pjdata.qlrmc,
            sqrlxfs: pjdata.qlrlxfs,
            ywmc: pjdata.bdcXmDO.gzldymc
        }, "GET", function (data) {
            removeModal();
        }, function (xhr) {
            delAjaxErrorMsg(xhr);
        }, false)
        window.open(encodeURI(url), "评价页面");
    }
}

function queryDjxlPz() {
    var djxlMap = "";
    getReturnData("/slym/qlr/getDjxl", "", "GET", function (data) {
        djxlMap = data;
    }, function (error) {
    }, false);
    return djxlMap;
}

function loadPlQlxx() {
    getReturnData("/slym/ql/pllc", {processInsId: processInsId, xmid: xmid, sfcxql: true, cxexistDyaq: true}, 'GET', function (data) {
        removeModal();
        if (isNotBlank(data)) {
            buildQlxx(data);
        }
    }, function (err) {
        delAjaxErrorMsg(err);
    });
}

//重新加载权利信息
function commonReloadQlxx() {
    loadPlQlxx();
}

function buildQlxx(bdcSlQlxxymDTOList) {
    //批量流程组织权利信息到页面，只需要展示其中一个权利即可
    if (isNotBlank(bdcSlQlxxymDTOList)) {
        var bdcSlQlxxym = bdcSlQlxxymDTOList[0];
        var qllx = bdcSlQlxxym.qllx;
        //抵押，预抵押，异议，查封，地役权展现在外面的权利字段
        if (qllx === parseInt(commonDyaq_qllx)) {
            includeDyaq = true;
        }
        //申请分别持证为空默认为是
        if(bdcSlQlxxym &&bdcSlQlxxym.bdcXm &&bdcSlQlxxym.bdcXm.sqfbcz ==null){
            bdcSlQlxxym.bdcXm.sqfbcz =1;
        }
        // 处理存在多次抵押提示
        if (bdcSlQlxxym) {
            var dyaBdcdyh = bdcSlQlxxym.dyaBdcdyh;
            if (dyaBdcdyh) {
                // 用于展示
                var dyaBdcdyhZs = dyaBdcdyh;
                if (dyaBdcdyh.indexOf(',') !== -1) {
                    dyaBdcdyhZs = dyaBdcdyh.split(',')[0] + '等';
                }
                bdcSlQlxxym.dyaBdcdyhZs = dyaBdcdyhZs;
            }
        }
        if (qllx === parseInt(commonDyaq_qllx) || sfdydj || qllx === 97 || qllx === 98 || qllx === 19) {
            var json = {
                bdcSlQlxxym: bdcSlQlxxym,
                zd: zdList,
                djxldjyy: djxldjyyList,
                dkfs: dkfs,
                cfdqJflc: cfdqJflc
            };
            var bdcdyfwlx = "";
            if (isNotBlank(bdcSlQlxxym.bdcQl.bdcdyfwlx) && bdcSlQlxxym.bdcQl.bdcdyfwlx === 1) {
                bdcdyfwlx = bdcSlQlxxym.bdcQl.bdcdyfwlx;
            }
            var qllxTpl = document.getElementById(bdcSlQlxxym.tableName + bdcdyfwlx);
            if (isNotBlank(qllxTpl)) {
                var tpl = qllxTpl.innerHTML, view = document.getElementById('bdcdyqlxx');
                laytpl(tpl).render(json, function (html) {
                    view.innerHTML = html;
                });
                //定位权利其他状况元素
                var $qlqtzk = $("textarea[name='bfqlqtzk']");
                //定位附记
                var $fj = $('.qlfj');
                //处理权利其他状况和附记
                dealQlqtzkFj('', $qlqtzk, $fj, bdcSlQlxxym.bdcXm.xmid, djxl);
                renderDate(form, formIds);
                form.render();
                renderSelect();
                //给下拉框添加删除图标
                renderSelectClose(form);
                getStateById(readOnly, formStateId, 'slympl', 'qlxxTab');
                fixBgm();
                form.render();
                disabledAddFa('qlxxTab');
            }


            //监听修改字段
            if (isNotBlank(xgnrglxs) && xgnrglxs.indexOf(bdcSlQlxxym.bdcXm.djxl) > -1) {
                formIds = "tabContent";
                renderChange("", form, formIds);
            }
        } else if (qllx === 96 || qllx === 4 || qllx === 6 || qllx === 8) {
            //展现交易信息字段
            var json = {
                zd: zdList,
                bdcSlQlxxym: bdcSlQlxxym,
                xmfb: bdcSlQlxxym.bdcXmFbDO
            };
            var jyxxTpl = document.getElementById("jyxxTpl");
            if (isNotBlank(jyxxTpl)) {
                var tpl = jyxxTpl.innerHTML, view = document.getElementById('bdcdyqlxx');
                laytpl(tpl).render(json, function (html) {
                    view.innerHTML = html;
                });
                //定位权利其他状况元素
                var $qlqtzk = $("textarea[name='bfqlqtzk']");
                //定位附记
                var $fj = $(".qlfj");
                dealQlqtzkFj('', $qlqtzk, $fj, bdcSlQlxxym.bdcXm.xmid, djxl);

                renderDate(form, formIds);
                form.render();
                renderSelect();
                //给下拉框添加删除图标
                renderSelectClose(form);
                getStateById(readOnly, formStateId, 'slympl', 'qlxxTab');
                form.render();
                disabledAddFa("jyxxTpl");

            }

            //判断页面是否存在交易信息字段，显示即查询
            if ($(".jyxx").length > 0 && isNotBlank(bdcSlQlxxym.bdcXm.xmid)) {
                getReturnData("/ycsl/jyxx", {xmid: bdcSlQlxxym.bdcXm.xmid}, "GET", function (data) {
                    if (data && isNotBlank(data.htbh)) {
                        data.htbh = data.htbh + "等";
                    }
                    //表单赋值
                    form.val("jyxx", data);
                    renderDate(form, formIds);
                }, function (error) {
                    delAjaxErrorMsg(error);
                });
            }
        } else {
            //其余产权不展现权利信息详细
            $("#qlxxdiv").remove();
        }
        if (sdqghDjxlList.indexOf(bdcSlQlxxym.bdcXm.djxl) > -1) {
            loadSqdghxx(bdcSlQlxxym.bdcSdqghDOList);
        }

        //加载第三权利人
        if (bdcSlQlxxym.qllx == "98" && bdcSlQlxxym.bdcQl.cflx == "3") {
            $("#ygrMsrTitle").parent().show();
            loadDsQlr("ygr");
        } else if (djxlMap.cdghDjxl.indexOf(bdcSlQlxxym.bdcXm.djxl) != -1) {
            $("#ygrMsrTitle").parent().show();
            loadDsQlr("msr");
        }
        //加载领证人
        loadLzrxx();
        getSfhz(bdcSlQlxxym.bdcXm);
    }


}

function getQlCols() {
    var cols = [];
    //根据权利类型与数据列对应关系获取对应数据列
    if (qllx === 4 || qllx === 6 || qllx === 8) {

        /**
         * 房地产权数据列
         */
        cols = [
            {type: 'checkbox', width: 50, fixed: 'left'},
            {
                field: 'sfzf', title: '是否主房', minWidth: 100, templet: function (d) {
                    if (d.sfzf == 1) {
                        return '是';
                    } else {
                        return '<span class="bdc-change-blue">否</span>';

                    }
                }
            },
            {
                field: 'zdsylx', title: '宗地使用类型', minWidth: 120, templet: function (d) {
                    if (d.zdsylx === 1) {
                        return '独用';
                    } else if (d.zdsylx === 2) {
                        return '共有';
                    } else {
                        return '';
                    }
                }
            },
            {field: 'ycqzh', title: '原产权证号', minWidth: 270, hide: true},
            {field: 'bdcdyh', title: '不动产单元号', minWidth: 270, templet: '#bdcdyhTpl'},
            {field: 'zl', title: '坐落', minWidth: 240},
            {
                field: 'ghyt', title: '房屋用途', minWidth: 150,
                templet: function (d) {
                    return changeDmToMc(d.ghyt, zdList.fwyt);
                }
            },
            {field: 'jzmj', title: '建筑面积', minWidth: 100},
            {field: 'tdsyqmj', title: '土地权利面积', minWidth: 140},
            {field: 'bdcdywybh', title: '不动产唯一编码', minWidth: 140},
            {field: 'qllx', title: '权利类型', minWidth: 100, templet: '#qllxTpl'},
            {field: 'zdzhmj', title: '宗地宗海面积(㎡)', minWidth: 140},
            {field: 'zdzhyt', title: '宗地宗海用途', minWidth: 120, templet: '#zdzhytTpl'},
            {field: 'zh', title: '幢号', minWidth: 100},
            {
                field: 'tdsyqssj', title: '土地使用开始期限', minWidth: 150,
                templet: function (d) {
                    if (d.tdsyqssj) {
                        return Format(format(d.tdsyqssj), 'yyyy年MM月dd日');
                    } else {
                        return '';
                    }
                }
            },
            {
                field: 'tdsyjssj', title: '土地使用结束期限', minWidth: 150,
                templet: function (d) {
                    if (d.tdsyjssj) {
                        return Format(format(d.tdsyjssj), 'yyyy年MM月dd日');
                    } else {
                        return '';
                    }
                }
            },
            {
                field: 'fwlx', title: '房屋类型', minWidth: 100,
                templet: function (d) {
                    return changeDmToMc(d.fwlx, zdList.fwlx);
                }
            },
            {
                field: 'fwxz', title: '房屋性质', minWidth: 150,
                templet: function (d) {
                    return changeDmToMc(d.fwxz, zdList.fwxz);
                }
            },
            {field: 'zcs', title: '总层数', minWidth: 100},
            {field: 'szc', title: '所在物理层', minWidth: 100},
            {
                field: 'fwjg', title: '房屋结构', minWidth: 150,
                templet: function (d) {
                    return changeDmToMc(d.fwjg, zdList.fwjg);
                }
            },
            {field: 'jyjg', title: '房地产交易价格(万元)', minWidth: 180},
            {
                field: 'jgsj', title: '竣工时间', minWidth: 150
            },
            {field: 'zyjzmj', title: '专有建筑面积', minWidth: 120},
            {field: 'ftjzmj', title: '分摊建筑面积', minWidth: 120},
            {field: 'fj', title: '附记', minWidth: 250},
            {field: 'cz', title: '操作', minWidth: 220, templet: '#bdcdyczTpl', fixed: 'right'}
        ];
    } else if (qllx === parseInt(commonDyaq_qllx)) {
        if (zxlc === "true") {
            /**
             * 抵押权注销数据列
             */
            cols = [
                {type: 'checkbox', width: 50, fixed: 'left'},
                {
                    field: 'sfzf', title: '是否主房', minWidth: 100, templet: function (d) {
                        if (d.sfzf == 1) {
                            return '是';
                        } else {
                            return '<span class="bdc-change-blue">否</span>';
                        }
                    }
                },
                {
                    field: 'zdsylx', title: '宗地使用类型', minWidth: 120, templet: function (d) {
                        if (d.zdsylx === 1) {
                            return '独用';
                        } else if (d.zdsylx === 2) {
                            return '共有';
                        } else {
                            return '';
                        }
                    }
                },
                {field: 'ycqzh', title: '原产权证号', minWidth: 270, hide: true},
                {field: 'bdcdyh', title: '不动产单元号', minWidth: 270, templet: '#bdcdyhTpl'},
                {field: 'zl', title: '坐落', minWidth: 240},
                {field: 'ycqzh', title: '抵押证明号', minWidth: 250},
                {field: 'zwr', title: '抵押人', minWidth: 100},
                {
                    field: 'dyfs', title: '抵押方式', minWidth: 100,
                    templet: function (d) {
                        return changeDmToMc(d.dyfs, zdList.dyfs);
                    }
                },
                {
                    field: 'zwlxqssj', title: '债务履行起始时间', minWidth: 150,
                    templet: function (d) {
                        if (d.zwlxqssj) {
                            return Format(format(d.zwlxqssj), 'yyyy年MM月dd日');
                        } else {
                            return '';
                        }
                    }
                },
                {
                    field: 'zwlxjssj', title: '债务履行结束时间', minWidth: 150,
                    templet: function (d) {
                        if (d.zwlxjssj) {
                            return Format(format(d.zwlxjssj), 'yyyy年MM月dd日');
                        } else {
                            return '';
                        }
                    }
                },
                {field: 'tddymj', title: '抵押土地面积', minWidth: 150},
                {field: 'fwdymj', title: '抵押房产面积', minWidth: 150},
                {field: 'bdcdywybh', title: '不动产唯一编码', minWidth: 140},
                {field: 'qllx', title: '权利类型', minWidth: 100, templet: '#qllxTpl'},
                {field: 'zdzhmj', title: '宗地宗海面积(㎡)', minWidth: 140},
                {field: 'zdzhyt', title: '宗地宗海用途', minWidth: 120, templet: '#zdzhytTpl'},
                {field: 'zh', title: '幢号', minWidth: 100},
                {field: 'zjjzwdyfw', title: '在建建筑物抵押范围', minWidth: 180},
                {field: 'zjjzwzl', title: '在建建筑物坐落', minWidth: 150},
                {field: 'fj', title: '附记', minWidth: 250},
                {field: 'cz', title: '操作', minWidth: 220, templet: '#bdcdyczTpl', fixed: 'right'}
            ];
        } else {
            /**
             * 抵押权数据列
             */
            cols = [
                {type: 'checkbox', width: 50, fixed: 'left'},
                {
                    field: 'sfzf', title: '是否主房', minWidth: 100, templet: function (d) {
                        if (d.sfzf == 1) {
                            return '是';
                        } else {
                            return '<span class="bdc-change-blue">否</span>';
                        }
                    }
                },
                {
                    field: 'zdsylx', title: '宗地使用类型', minWidth: 120, templet: function (d) {
                        if (d.zdsylx === 1) {
                            return '独用';
                        } else if (d.zdsylx === 2) {
                            return '共有';
                        } else {
                            return '';
                        }
                    }
                },
                {field: 'ycqzh', title: '原产权证号', minWidth: 270, hide: true},
                {field: 'bdcdyh', title: '不动产单元号', minWidth: 270, templet: '#bdcdyhTpl'},
                {field: 'zl', title: '坐落', minWidth: 240},
                {field: 'bdcdywybh', title: '不动产唯一编码', minWidth: 140},
                {field: 'qllx', title: '权利类型', minWidth: 100, templet: '#qllxTpl'},
                {field: 'zdzhmj', title: '宗地宗海面积(㎡)', minWidth: 140},
                {field: 'zdzhyt', title: '宗地宗海用途', minWidth: 120, templet: '#zdzhytTpl'},
                {field: 'zh', title: '幢号', minWidth: 100},
                {
                    field: 'zwlxqssj', title: '债务履行起始时间', minWidth: 150,
                    templet: function (d) {
                        if (d.zwlxqssj) {
                            return Format(format(d.zwlxqssj), 'yyyy年MM月dd日');
                        } else {
                            return '';
                        }
                    }
                },
                {
                    field: 'zwlxjssj', title: '债务履行结束时间', minWidth: 150,
                    templet: function (d) {
                        if (d.zwlxjssj) {
                            return Format(format(d.zwlxjssj), 'yyyy年MM月dd日');
                        } else {
                            return '';
                        }
                    }
                },
                {
                    field: 'dyfs', title: '抵押方式', minWidth: 100,
                    templet: function (d) {
                        return changeDmToMc(d.dyfs, zdList.dyfs);
                    }
                },
                {field: 'zjjzwdyfw', title: '在建建筑物抵押范围', minWidth: 180},
                {field: 'zjjzwzl', title: '在建建筑物坐落', minWidth: 150},
                {field: 'tddymj', title: '抵押土地面积', minWidth: 150},
                {field: 'fwdymj', title: '抵押房产面积', minWidth: 150},
                {field: 'fj', title: '附记', minWidth: 250},
                {field: 'cz', title: '操作', minWidth: 220, templet: '#bdcdyczTpl', fixed: 'right'}
            ];
        }
    } else if (qllx === 3 || qllx === 7) {

        /**
         * 建设用地使用权数据列
         */
        cols = [
            {type: 'checkbox', width: 50, fixed: 'left'},
            {
                field: 'sfzf', title: '是否主房', minWidth: 100, templet: function (d) {
                    if (d.sfzf == 1) {
                        return '是';
                    } else {
                        return '<span class="bdc-change-blue">否</span>';

                    }
                }
            },
            {
                field: 'zdsylx', title: '宗地使用类型', minWidth: 120, templet: function (d) {
                    if (d.zdsylx === 1) {
                        return '独用';
                    } else if (d.zdsylx === 2) {
                        return '共有';
                    } else {
                        return '';
                    }
                }
            },
            {field: 'ycqzh', title: '原产权证号', minWidth: 270, hide: true},
            {field: 'bdcdyh', title: '不动产单元号', minWidth: 270, templet: '#bdcdyhTpl'},
            {field: 'zl', title: '坐落', minWidth: 240},
            {field: 'bdcdywybh', title: '不动产唯一编码', minWidth: 140},
            {field: 'qllx', title: '权利类型', minWidth: 100, templet: '#qllxTpl'},
            {field: 'zdzhmj', title: '宗地宗海面积(㎡)', minWidth: 140},
            {field: 'zdzhyt', title: '宗地宗海用途', minWidth: 120, templet: '#zdzhytTpl'},
            {field: 'zh', title: '幢号', minWidth: 100},
            {
                field: 'syqqssj', title: '使用权起始时间', minWidth: 150,
                templet: function (d) {
                    if (d.syqqssj) {
                        return Format(format(d.syqqssj), 'yyyy年MM月dd日');
                    } else {
                        return '';
                    }
                }
            },
            {
                field: 'syqjssj', title: '使用权结束时间', minWidth: 150,
                templet: function (d) {
                    if (d.syqjssj) {
                        return Format(format(d.syqjssj), 'yyyy年MM月dd日');
                    } else {
                        return '';
                    }
                }
            },
            {field: 'fj', title: '附记', minWidth: 250},
            {field: 'cz', title: '操作', minWidth: 220, templet: '#bdcdyczTpl', fixed: 'right'}
        ];
    } else if (qllx === 98) {
        if (zxlc === "true") {
            /**
             * 解封数据列
             */
            cols = [
                {type: 'checkbox', width: 50, fixed: 'left'},
                {
                    field: 'sfzf', title: '是否主房', minWidth: 100, templet: function (d) {
                        if (d.sfzf == 1) {
                            return '是';
                        } else {
                            return '<span class="bdc-change-blue">否</span>';

                        }
                    }
                },
                {
                    field: 'zdsylx', title: '宗地使用类型', minWidth: 120, templet: function (d) {
                        if (d.zdsylx === 1) {
                            return '独用';
                        } else if (d.zdsylx === 2) {
                            return '共有';
                        } else {
                            return '';
                        }
                    }
                },
                {field: 'ycqzh', title: '原产权证号', minWidth: 270, hide: true},
                {field: 'bdcdyh', title: '不动产单元号', minWidth: 270, templet: '#bdcdyhTpl'},
                {field: 'zl', title: '坐落', minWidth: 240},
                {field: 'ycqzh', title: '原产权证号', minWidth: 200},
                {field: 'cfwh', title: '查封文号', minWidth: 200},
                {field: 'cfjg', title: '查封机关', minWidth: 150},
                {
                    field: 'cflx', title: '查封类型', minWidth: 100,
                    templet: function (d) {
                        return changeDmToMc(d.cflx, zdList.cflx);
                    }
                },
                {field: 'cfwj', title: '查封文件', minWidth: 150},

                {
                    field: 'cfqssj', title: '查封起始时间', minWidth: 150,
                    templet: function (d) {
                        if (d.cfqssj) {
                            return Format(format(d.cfqssj), 'yyyy年MM月dd日');
                        } else {
                            return '';
                        }
                    }
                },
                {
                    field: 'cfjssj', title: '查封结束时间', minWidth: 150,
                    templet: function (d) {
                        if (d.cfjssj) {
                            return Format(format(d.cfjssj), 'yyyy年MM月dd日');
                        } else {
                            return '';
                        }
                    }
                },
                {field: 'cffw', title: '查封范围', minWidth: 200},
                {field: 'bdcdywybh', title: '不动产唯一编码', minWidth: 140},
                {field: 'qllx', title: '权利类型', minWidth: 100, templet: '#qllxTpl'},
                {field: 'zdzhmj', title: '宗地宗海面积(㎡)', minWidth: 140},
                {field: 'zdzhyt', title: '宗地宗海用途', minWidth: 120, templet: '#zdzhytTpl'},
                {field: 'zh', title: '幢号', minWidth: 100},
                {field: 'jfwh', title: '解封文号', minWidth: 200},
                {field: 'jfjg', title: '解封机关', minWidth: 150},
                {field: 'jfwj', title: '解封文件', minWidth: 150},
                {field: 'fj', title: '附记', minWidth: 250},
                {field: 'cz', title: '操作', minWidth: 220, templet: '#bdcdyczTpl', fixed: 'right'}
            ];
        } else {
            /**
             * 查封数据列
             */
            cols = [
                {type: 'checkbox', width: 50, fixed: 'left'},
                {
                    field: 'sfzf', title: '是否主房', minWidth: 100, templet: function (d) {
                        if (d.sfzf == 1) {
                            return '是';
                        } else {
                            return '<span class="bdc-change-blue">否</span>';

                        }
                    }
                },
                {
                    field: 'zdsylx', title: '宗地使用类型', minWidth: 120, templet: function (d) {
                        if (d.zdsylx === 1) {
                            return '独用';
                        } else if (d.zdsylx === 2) {
                            return '共有';
                        } else {
                            return '';
                        }
                    }
                },
                {field: 'ycqzh', title: '原产权证号', minWidth: 270, hide: true},
                {field: 'bdcdyh', title: '不动产单元号', minWidth: 270, templet: '#bdcdyhTpl'},
                {field: 'zl', title: '坐落', minWidth: 240},
                {field: 'bdcdywybh', title: '不动产唯一编码', minWidth: 140},
                {field: 'qllx', title: '权利类型', minWidth: 100, templet: '#qllxTpl'},
                {field: 'zdzhmj', title: '宗地宗海面积(㎡)', minWidth: 140},
                {field: 'zdzhyt', title: '宗地宗海用途', minWidth: 120, templet: '#zdzhytTpl'},
                {field: 'zh', title: '幢号', minWidth: 100},
                {field: 'cfwh', title: '查封文号', minWidth: 200},
                {field: 'cfjg', title: '查封机关', minWidth: 150},
                {field: 'cfwj', title: '查封文件', minWidth: 150},
                {
                    field: 'cflx', title: '查封类型', minWidth: 100,
                    templet: function (d) {
                        return changeDmToMc(d.cflx, zdList.cflx);
                    }
                },
                {
                    field: 'cfqssj', title: '查封起始时间', minWidth: 150,
                    templet: function (d) {
                        if (d.cfqssj) {
                            return Format(format(d.cfqssj), 'yyyy年MM月dd日');
                        } else {
                            return '';
                        }
                    }
                },
                {
                    field: 'cfjssj', title: '查封结束时间', minWidth: 150,
                    templet: function (d) {
                        if (d.cfjssj) {
                            return Format(format(d.cfjssj), 'yyyy年MM月dd日');
                        } else {
                            return '';
                        }
                    }
                },
                {field: 'cffw', title: '查封范围', minWidth: 200},
                {field: 'jfwh', title: '解封文号', minWidth: 200},
                {field: 'jfjg', title: '解封机关', minWidth: 150},
                {field: 'jfwj', title: '解封文件', minWidth: 150},
                {field: 'fj', title: '附记', minWidth: 250},
                {field: 'cz', title: '操作', minWidth: 220, templet: '#bdcdyczTpl', fixed: 'right'}
            ];
        }
    } else if (qllx === 96) {
        if (sfdydj) {
            /**
             * 预抵押数据列
             */
            cols = [
                {type: 'checkbox', width: 50, fixed: 'left'},
                {
                    field: 'sfzf', title: '是否主房', minWidth: 100, templet: function (d) {
                        if (d.sfzf == 1) {
                            return '是';
                        } else {
                            return '<span class="bdc-change-blue">否</span>';

                        }
                    }
                },
                {
                    field: 'zdsylx', title: '宗地使用类型', minWidth: 120, templet: function (d) {
                        if (d.zdsylx === 1) {
                            return '独用';
                        } else if (d.zdsylx === 2) {
                            return '共有';
                        } else {
                            return '';
                        }
                    }
                },
                {field: 'ycqzh', title: '原产权证号', minWidth: 270, hide: true},
                {field: 'bdcdyh', title: '不动产单元号', minWidth: 270, templet: '#bdcdyhTpl'},
                {field: 'zl', title: '坐落', minWidth: 240},
                {field: 'bdcdywybh', title: '不动产唯一编码', minWidth: 140},
                {field: 'qllx', title: '权利类型', minWidth: 100, templet: '#qllxTpl'},
                {field: 'zdzhmj', title: '宗地宗海面积(㎡)', minWidth: 140},
                {field: 'zdzhyt', title: '宗地宗海用途', minWidth: 120, templet: '#zdzhytTpl'},
                {field: 'zh', title: '幢号', minWidth: 100},
                {
                    field: 'ygdjzl', title: '预告登记种类', minWidth: 200,
                    templet: function (d) {
                        return changeDmToMc(d.ygdjzl, zdList.ygdjzl);
                    }
                },
                {
                    field: 'dyfs', title: '抵押方式', minWidth: 100,
                    templet: function (d) {
                        return changeDmToMc(d.dyfs, zdList.dyfs);
                    }
                },
                {field: 'qdjg', title: '被担保主债权数额(万元)', minWidth: 200},
                {
                    field: 'zwlxqssj', title: '债务履行起始时间', minWidth: 150,
                    templet: function (d) {
                        if (d.zwlxqssj) {
                            return Format(format(d.zwlxqssj), 'yyyy年MM月dd日');
                        } else {
                            return '';
                        }
                    }
                },
                {
                    field: 'zwlxjssj', title: '债务履行结束时间', minWidth: 150,
                    templet: function (d) {
                        if (d.zwlxjssj) {
                            return Format(format(d.zwlxjssj), 'yyyy年MM月dd日');
                        } else {
                            return '';
                        }
                    }
                },
                {field: 'fj', title: '附记', minWidth: 250},
                {field: 'cz', title: '操作', minWidth: 220, templet: '#bdcdyczTpl', fixed: 'right'}
            ];

        } else {

            /**
             * 预告数据列
             */
            cols = [
                {type: 'checkbox', width: 50, fixed: 'left'},
                {
                    field: 'sfzf', title: '是否主房', minWidth: 100, templet: function (d) {
                        if (d.sfzf == 1) {
                            return '是';
                        } else {
                            return '<span class="bdc-change-blue">否</span>';

                        }
                    }
                },
                {
                    field: 'zdsylx', title: '宗地使用类型', minWidth: 120, templet: function (d) {
                        if (d.zdsylx === 1) {
                            return '独用';
                        } else if (d.zdsylx === 2) {
                            return '共有';
                        } else {
                            return '';
                        }
                    }
                },
                {field: 'ycqzh', title: '原产权证号', minWidth: 270, hide: true},
                {field: 'bdcdyh', title: '不动产单元号', minWidth: 270, templet: '#bdcdyhTpl'},
                {field: 'zl', title: '坐落', minWidth: 240},
                {field: 'bdcdywybh', title: '不动产唯一编码', minWidth: 140},
                {field: 'qllx', title: '权利类型', minWidth: 100, templet: '#qllxTpl'},
                {field: 'zdzhmj', title: '宗地宗海面积(㎡)', minWidth: 140},
                {field: 'zdzhyt', title: '宗地宗海用途', minWidth: 120, templet: '#zdzhytTpl'},
                {field: 'zh', title: '幢号', minWidth: 100},
                {
                    field: 'ygdjzl', title: '预告登记种类', minWidth: 200,
                    templet: function (d) {
                        return changeDmToMc(d.ygdjzl, zdList.ygdjzl);
                    }
                },
                {field: 'zcs', title: '总层数', minWidth: 100},
                {field: 'szc', title: '所在物理层', minWidth: 100},
                {field: 'szmyc', title: '所在名义层', minWidth: 100},
                {field: 'jzmj', title: '建筑面积', minWidth: 100},
                {
                    field: 'fwxz', title: '房屋性质', minWidth: 150,
                    templet: function (d) {
                        return changeDmToMc(d.fwxz, zdList.fwxz);
                    }
                },
                {
                    field: 'ghyt', title: '规划用途', minWidth: 150,
                    templet: function (d) {
                        return changeDmToMc(d.ghyt, zdList.fwyt);
                    }
                },
                {field: 'tdsyqr', title: '土地使用权人', minWidth: 200},
                {field: 'jyje', title: '交易金额(万元)', minWidth: 200},
                {
                    field: 'tdsyqssj', title: '土地使用起始时间', minWidth: 150,
                    templet: function (d) {
                        if (d.tdsyqssj) {
                            return Format(format(d.tdsyqssj), 'yyyy年MM月dd日');
                        } else {
                            return '';
                        }
                    }
                },
                {
                    field: 'tdsyjssj', title: '土地使用结束时间', minWidth: 150,
                    templet: function (d) {
                        if (d.tdsyjssj) {
                            return Format(format(d.tdsyjssj), 'yyyy年MM月dd日');
                        } else {
                            return '';
                        }
                    }
                },
                {field: 'fj', title: '附记', minWidth: 250},
                {field: 'cz', title: '操作', minWidth: 220, templet: '#bdcdyczTpl', fixed: 'right'}
            ];
        }
    } else if (qllx === 94) {
        cols = [
            {type: 'checkbox', width: 50, fixed: 'left'},
            {
                field: 'zdsylx', title: '宗地使用类型', minWidth: 120, templet: function (d) {
                    if (d.zdsylx === 1) {
                        return '独用';
                    } else if (d.zdsylx === 2) {
                        return '共有';
                    } else {
                        return '';
                    }
                }
            },
            {field: 'slbh', title: '业务号', minWidth: 140},
            {field: 'jgzwbh', title: '建（构）筑物编号', minWidth: 150},
            {field: 'jgzwmc', title: '建（构）筑物名称', minWidth: 150},
            {field: 'jgzwmj', title: '建（构）筑物数量或者面积', minWidth: 200},
            {field: 'fttdmj', title: '分摊土地面积', minWidth: 140},
            {field: 'dbr', title: '登簿人', minWidth: 120},
            {
                field: 'djsj', title: '登记时间', minWidth: 100,
                templet: function (d) {
                    if (d.djsj) {
                        return Format(format(d.djsj), 'yyyy年MM月dd日');
                    } else {
                        return '';
                    }
                }
            },
            {field: 'fj', title: '附记', minWidth: 100},
            {field: 'cz', title: '操作', minWidth: 220, templet: '#bdcdyczTpl', fixed: 'right'}
        ];
    } else if (qllx === 92) {
        /**
         * 居住权数据列
         */
        cols = [
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: 'xh', title: '序号', width: 50, type: 'numbers'},
            {field: 'ycqzh', title: '原产权证号', minWidth: 250},
            {field: 'bdcdyh', title: '不动产单元号', minWidth: 270, templet: '#bdcdyhTpl'},
            {field: 'ysfwbm', title: '预售房屋编码', minWidth: 140},
            {field: 'zl', title: '坐落', minWidth: 240},
            {field: 'dzwmj', title: '定着物面积(㎡)', minWidth: 240},
            {field: 'bdcdywybh', title: '不动产唯一编码', minWidth: 140},
            {field: 'qllx', title: '权利类型', minWidth: 100, templet: '#qllxTpl'},
            {
                field: 'qlxz', title: '权利性质', minWidth: 100,
                templet: function (d) {
                    return changeDmToMc(d.qlxz, zdList.qlxz);
                }
            },
            {field: 'zdzhmj', title: '宗地宗海面积(㎡)', minWidth: 140},
            {field: 'zdzhyt', title: '宗地宗海用途', minWidth: 120, templet: '#zdzhytTpl'},
            {field: 'jztjhyq', title: '居住条件和要求', minWidth: 140},
            {
                field: 'jzqkssj', title: '居住权起始时间', minWidth: 150,
                templet: function (d) {
                    if (d.jzqkssj) {
                        return Format(format(d.jzqkssj), 'yyyy年MM月dd日');
                    } else {
                        return '';
                    }
                }
            },
            {
                field: 'jzqjssj', title: '居住权结束时间', minWidth: 150,
                templet: function (d) {
                    if (d.jzqjssj) {
                        return Format(format(d.jzqjssj), 'yyyy年MM月dd日');
                    } else {
                        return '';
                    }
                }
            },
            {field: 'fj', title: '附记', minWidth: 250, event: 'editFj', style:'cursor: pointer;'},
            {field: 'cz', title: '操作', minWidth: 220, templet: '#bdcdyczTpl', fixed: 'right'}
        ];
    }
    else {
        cols = [
            {type: 'checkbox', width: 50, fixed: 'left'},
            {
                field: 'sfzf', title: '是否主房', minWidth: 100, templet: function (d) {
                    if (d.sfzf == 1) {
                        return '是';
                    } else {
                        return '<span class="bdc-change-blue">否</span>';

                    }
                }
            },
            {
                field: 'zdsylx', title: '宗地使用类型', minWidth: 120, templet: function (d) {
                    if (d.zdsylx === 1) {
                        return '独用';
                    } else if (d.zdsylx === 2) {
                        return '共有';
                    } else {
                        return '';
                    }
                }
            },
            {field: 'ycqzh', title: '原产权证号', minWidth: 270, hide: true},
            {field: 'bdcdyh', title: '不动产单元号', minWidth: 270, templet: '#bdcdyhTpl'},
            {field: 'zl', title: '坐落', minWidth: 240},
            {field: 'bdcdywybh', title: '不动产唯一编码', minWidth: 140},
            {field: 'qllx', title: '权利类型', minWidth: 100, templet: '#qllxTpl'},
            {field: 'zdzhmj', title: '宗地宗海面积(㎡)', minWidth: 140},
            {field: 'zdzhyt', title: '宗地宗海用途', minWidth: 120, templet: '#zdzhytTpl'},
            {field: 'zh', title: '幢号', minWidth: 100},
            {field: 'cz', title: '操作', minWidth: 220, templet: '#bdcdyczTpl', fixed: 'right'}
        ];
    }
    return cols;
}

/**
 * ywrgroup 义务人分组对象
 * 处理义务人
 */
function dealYwr(ywrgroup) {
    var ywr = "";
    if (ywrgroup != null && ywrgroup.length > 0) {
        for (var i = 0; i < ywrgroup.length; i++) {
            var ywrObj = ywrgroup[i];
            if (ywrObj && isNotBlank(ywrObj.bdcQlrDO.qlrmc)) {
                ywr += ywrObj.bdcQlrDO.qlrmc + " ";
            }
        }

    }
    return ywr;
}

function checkXndyhPl() {
    getReturnData("/slym/xm/checkXndyh", {gzlslid: processInsId}, "GET", function (data) {
        isXndyh = data;
    }, function (error) {
        delAjaxErrorMsg(error);

    });
}

//处理合计信息
function dealHjxx() {
    getReturnData("/slym/ql/calculatedQlMj", {gzlslid: processInsId, zxlc: zxlc}, "GET", function (data) {
        if (data) {
            //总建筑面积
            if (isNotBlank(data.jzmj)) {
                $("#sumjzmj").text(data.jzmj + "m²");
            } else {
                $("#sumjzmj").text("0m²");
            }
            //总土地权利面积
            if (isNotBlank(data.tdqlmj)) {
                $("#sumtdqlmj").text(data.tdqlmj + "m²");
            } else {
                $("#sumtdqlmj").text("0m²");

            }
        }
    }, function (error) {
        delAjaxErrorMsg(error);
    });
    var bdcBdcdyQO = {};
    bdcBdcdyQO.gzlslid = processInsId;
    bdcBdcdyQO.sfyql = zxlc;
    if ($(".calculate-yt").length === 0) {
        getReturnData("/slym/ql/calculateBdcdyMjByYt", JSON.stringify(bdcBdcdyQO), "POST", function (data) {
            if (data && data.length > 0) {
                console.log(data);
                if (data.length > 4) {
                    //用途大于4 个时改变div高度
                    $('.bdc-table-zj').height($('.bdc-table-zj').height() + 40);
                    var height = $('.bdc-table-zj').height();
                    $(".bdc-line-search-container").css("padding-top", height + 52);
                }
                for (var i = 0; i < data.length; i++) {
                    if (data[i] && isNotBlank(data[i].YTMC)) {
                        if (isNotBlank(data[i].JZMJ)) {
                            $('.bdc-table-zj').append("<div><span class='calculate-yt'>" + data[i].YTMC + "： <i>" + data[i].JZMJ + "m²</i></span></div>");
                        } else {
                            $('.bdc-table-zj').append("<div><span class='calculate-yt'>" + data[i].YTMC + "： <i>0m²</i></span></div>");
                        }
                    }

                }
            }


        }, function (error) {
            delAjaxErrorMsg(error);

        })
    }
}

//计算使用期限（单位：月）
function countSyqxByMonth() {
    var $layuishow = $('.layui-show');
    var ydyqssj = $layuishow.find("#yg-zwlxqssj").val();
    var dyaqssj = $layuishow.find("#dyaq-zwlxqssj").val();
    var syqx = parseInt($layuishow.find(".zwlxqx").val());
    if (syqx > 0) {
        //计算结束时间后的日期 日 要减一天
        if (isNotBlank(ydyqssj)) {
            var qssj = new Date(ydyqssj);
            qssj.setMonth(qssj.getMonth() + syqx);
            var time = qssj.getTime() - 1000 * 60 * 60 * 24;
            console.log(Format(formatChinaTime(new Date(time)), "yyyy-MM-dd"));
            $layuishow.find("#yg-zwlxqssj").val(Format(formatChinaTime(new Date(time)), "yyyy-MM-dd"));
        } else if (isNotBlank(dyaqssj)) {
            var qssj = new Date(dyaqssj);
            qssj.setMonth(qssj.getMonth() + syqx);
            var time = qssj.getTime() - 1000 * 60 * 60 * 24;
            console.log(Format(formatChinaTime(qssj), "yyyy-MM-dd"));
            $layuishow.find("#dyaq-zwlxjssj").val(Format(formatChinaTime(new Date(time)), "yyyy-MM-dd"));
        }
    }
}

/**
 * 重新生成权利其他状况
 */
function reGenerateQlqtzk(xmid, mode) {
    var $qlqtzk = $("textarea[name='bfqlqtzk']");
    if ($qlqtzk.length > 0) {
        $qlqtzk.val("");
        //清空原有数据并保存受理页面
        $('#saveData').click();
        //重新加载模板数据--保存后页面会刷新不用重新加载

    }
}

/**
 * 重新生成附记
 */
function reGenerateFj(xmid, mode) {
    var $fj = $("textarea[name='fj']");
    if ($fj.length > 0) {
        $fj.val("");
        //清空原有数据并保存受理页面
        $('#saveData').click();
        //重新加载模板数据--保存后页面会刷新不用重新加载
    }
}

function loadDsQlr(type) {
    addModel();
    //权利人
    $.ajax({
        url: getContextPath() + "/slym/qlr/list/dsQlr",
        type: 'GET',
        dataType: 'json',
        async: false,
        data: {xmid: xmid},
        success: function (data) {
            removeModal();
            generateDsQlrxx(data, "dsQlrxx", type);
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}


function generateDsQlrxx(data, id, type) {
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var form = layui.form;
        var laytpl = layui.laytpl;
        var json = {
            bdcDsQlrDOList: data,
            zd: zdList
        };
        //无数据隐藏
        if (data && data.length > 0) {
            $("#dsQlrTable .bdc-table-none").hide();
        }
        var tpl = ygrTpl.innerHTML, view = document.getElementById(id);
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });

        form.render();
        getStateById(readOnly, formStateId, 'slympl');
        renderSelect();
        disabledAddFa("ygrForm");
        if (type == "ygr") {
            $('.dsQlrTitle').find('p').text("预购人")
        } else if (type == "msr") {
            $('.dsQlrTitle').find('p').text("买受人")
        }
    })
}


//新增权利人展示  -----------------点击新增申请人
function addYgrOrMsr(qllx) {
    var addXmid = xmid;
    var url = getContextPath() + "/view/slym/dsQlr.html?xmid=" + addXmid + "&lclx=" + lclx + "&processInsId=" + processInsId + "&formStateId=" + formStateId + "&qllx=" + qllx + "&zxlc=" + zxlc;
    layer.open({
        type: 2,
        area: ['960px', '250px'],
        fixed: false, //不固定
        title: "新增" + $('.dsQlrTitle').find('p').text(),
        content: url,
        btnAlign: "c"
    });
    form.render();
    resetSelectDisabledCss();
}

//删除第三权利人
function deleteYgr(qlrid, sxh, qlrlb) {
    var xmid = "";
    var url = "";
    //获取当前权利人对应的项目ID
    xmid = $($(".layui-this").find("input[name='xmid']")[0]).val();
    url = "/slym/qlr/deleteDsQlr?xmid=" + xmid + "&qlrid=" + qlrid + "&sxh=" + sxh + "&qlrlb=" + qlrlb + "&processInsId=" + processInsId;

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
                loadQlr();
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

/**
 * 处理交易信息中登记业务表相关信息
 */
function dealDjxx(data) {
    var bdcSlJyxx = {
        htbh: data.htbh,
        jyje: data.jyje,
        htdjsj: format(new Date(data.sqsj)),
        xmid: xmid
    };
    var fcjyxxQO={
        version:"nantong"
    };
    var paremJson = {
        bdcSlJyxx: bdcSlJyxx,
        fw: data.fw,
        fcjyxxQO:fcjyxxQO
    };
    getReturnData("/ycsl/jyxx/dealDjxx?processInsId=" + processInsId, JSON.stringify(paremJson), "POST", function () {
        loadPlQlxx();
        loadBdcdyh();
        removeModal();
        ityzl_SHOW_SUCCESS_LAYER("导入成功。");
    }, function (error) {
        removeModal();
        console.info(error);
        ityzl_SHOW_WARN_LAYER("导入失败。");
    })
}

/**
 * 判断流程是否生成一本证,生成多本证隐藏附记和权利其他状况(初始化方法)
 */
function initHideQlqtzkFjByZssl($qlqtzk, $fj) {
    getReturnData("/slym/ql/checkPlZhOne", {gzlslid: processInsId}, "GET", function (data) {
        if (!data) {
            //生成多本证移除附记和权利其他状况
            if ($qlqtzk.length > 0) {
                $qlqtzk.parent().parent().remove();
            }
            if ($fj.length > 0) {
                $fj.parent().parent().remove();
            }
        }
    }, function (error) {
        delAjaxErrorMsg(error);
    });
}

//表格内详细信息
function showDetails(xmid, qllx, bdcdyfwlx, sfyql) {
    var index = layer.open({
        type: 2,
        area: ['1150px', '600px'],
        fixed: false, //不固定
        title: "详细信息",
        maxmin: true,
        content: getContextPath() + '/view/slym/qldyxx.html?xmid=' + xmid + "&qllx=" + qllx + "&bdcdyfwlx=" + bdcdyfwlx + "&sfyql=" + sfyql + "&formStateId=" + formStateId + "&readOnly=" + readOnly + "&zxlc=" + zxlc+"&processInsId="+processInsId,
        btnAlign: "c",
        zIndex: 2147483647,
        success: function () {

        },
        cancel: function () {
            //获取子页面的权利其他状态信息。
            if (lclx !== "zhlc") {
                //有分页的情况下关闭权利页面后点击当前页刷新
                if (isNotBlank($('.layui-laypage-btn')[0])) {
                    $('.layui-laypage-btn')[0].click();
                } else {
                    $('#searchBdcdy').click();
                }
                loadPlQlxx();
            }

        }
    });
    layer.full(index);
}

//xq.js合并
//展现权利信息(受理批量页面）
function showQl(xmid, qllx, bdcdyfwlx, sfyql) {
    if (xmid !== "" && xmid !== null) {
        qllx = parseInt(qllx);
        bdcdyfwlx = parseInt(bdcdyfwlx);
        var qllxym = getQlxxYm(qllx, bdcdyfwlx);
        var url;
        //展示原权利，不可编辑
        if (sfyql) {
            url = "/realestate-register-ui/view/qlxx/" + qllxym + ".html?xmid=" + xmid + "&formStateId=" + formStateId + "&readOnly=true" + "&isCrossOri=false";
        } else {
            url = "/realestate-register-ui/view/qlxx/" + qllxym + ".html?xmid=" + xmid + "&formStateId=" + formStateId + "&readOnly=" + readOnly + "&isCrossOri=false";
        }
        var index = layer.open({
            type: 2,
            area: ['1150px', '600px'],
            fixed: false, //不固定
            title: "权利信息",
            maxmin: true,
            content: url,
            btnAlign: "c",
            zIndex: 2147483647,
            success: function () {
            },
            cancel: function (index, layero) {
                //获取子页面的权利其他状态信息。
                if (lclx !== "zhlc") {
                    //有分页的情况下关闭权利页面后点击当前页刷新
                    if (isNotBlank($('.layui-laypage-btn')[0])) {
                        $('.layui-laypage-btn')[0].click();
                    } else {
                        $('#searchBdcdy').click();
                    }
                    // 更新一本证受理界面中，权利其他状况信息。
                    var $bfqlqtzk = layer.getChildFrame('#bfqlqtzk', index);
                    if ($("#bfqlqtzk").length != 0) {
                        $("#bfqlqtzk").val($bfqlqtzk.val());
                    }
                }
            }
        });
        layer.full(index);
    } else {
        ityzl_SHOW_INFO_LAYER("无原权利信息")
    }
}

//新增权利人展示(受理批量页面)
function addQlr(qllx) {
    var dydj = false;
    //判断是否是抵押类登记
    getReturnData("/slym/ql/checkDydj", {xmid: xmid}, 'GET', function (data) {
        dydj = data;
    }, function (err) {
        delAjaxErrorMsg(err);
    }, false);
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var form = layui.form;
        var url = getContextPath() + "/nantong/slym/qlr.html?xmid=" + xmid +"&cfdqJflc="+cfdqJflc+ "&lclx=" + lclx + "&processInsId=" + processInsId + "&formStateId=" + formStateId + "&qllx=" + qllx + "&zxlc=" + zxlc;
        if (dydj) {
            url = url + "&dydj=true";
        }
        layer.open({
            type: 2,
            area: ['960px', '575px'],
            fixed: false, //不固定
            title: "新增申请人",
            content: url,
            btnAlign: "c"
        });
        form.render();
        disabledAddFa();
    })


}

//权利人详情展示
function showQlr(qlrid, xmid, qlrlb) {
    sessionStorage.removeItem('qlridList');
    sessionStorage.removeItem('xmidList');
    var dydj = false;
    //判断是否是抵押类登记
    getReturnData("/slym/ql/checkDydj", {xmid: xmid}, 'GET', function (data) {
        dydj = data;
    }, function (err) {
        delAjaxErrorMsg(err);
    }, false);
    addModel();
    var qllx = $($("input[name='qllx']")[0]).val();
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var form = layui.form;
        var url = getContextPath() + "/view/slym/qlr.html?processInsId=" + processInsId + "&qlrid=" + qlrid + "&lclx=" + lclx + "&xmid=" + xmid + "&formStateId=" + formStateId + "&qllx=" + qllx + "&readOnly=" + readOnly + "&zxlc=" + zxlc;
        //标题
        var title;
        if (dydj) {
            url = url + "&dydj=true";
            if (qlrlb === "1") {
                title = "抵押权人详细信息";
            } else {
                title = "抵押人详细信息";
            }

        } else {
            title = "申请人详细信息";
        }
        layer.open({
            type: 2,
            area: ['960px', '575px'],
            fixed: false, //不固定
            title: title,
            content: url,
            btnAlign: "c"
        });
        removeModal();
        form.render();
        renderDate(form);
        disabledAddFa();
    })
}

//义务人详情展示
function showYwr(qlrid, xmid, qlrlb, otherqlridList, otherXmidList) {
    if (otherqlridList != undefined) {
        if (isNotBlank(otherqlridList)) {
            sessionStorage.qlridList = otherqlridList + ',' + qlrid;
        } else {
            sessionStorage.qlridList = qlrid;
        }
        if (isNotBlank(otherXmidList)) {
            sessionStorage.xmidList = otherXmidList + ',' + xmid;
        } else {
            sessionStorage.xmidList = xmid;
        }
    } else {
        sessionStorage.removeItem('qlridList');
        sessionStorage.removeItem('xmidList');
    }
    var dydj = false;
    //判断是否是抵押类登记
    getReturnData("/slym/ql/checkDydj", {xmid: xmid}, 'GET', function (data) {
        dydj = data;
    }, function (err) {
        delAjaxErrorMsg(err);
    }, false);
    addModel();
    var qllx = $($("input[name='qllx']")[0]).val();
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var form = layui.form;
        var url = getContextPath() + "/view/slym/qlr.html?processInsId=" + processInsId + "&qlrid=" + qlrid + "&lclx=" + lclx + "&xmid=" + xmid + "&formStateId=" + formStateId + "&qllx=" + qllx + "&readOnly=" + readOnly + "&zxlc=" + zxlc;
        //标题
        var title;
        if (dydj) {
            url = url + "&dydj=true";
            if (qlrlb === "1") {
                title = "抵押权人详细信息";
            } else {
                title = "抵押人详细信息";
            }

        } else {
            title = "申请人详细信息";
        }
        layer.open({
            type: 2,
            area: ['960px', '575px'],
            fixed: false, //不固定
            title: title,
            content: url,
            btnAlign: "c"
        });
        removeModal();
        form.render();
        renderDate(form);
        disabledAddFa();
    })
}

//查询房屋附属设施
function fwfsss(xmid) {
    $.ajax({
        url: getContextPath() + "/slym/fwfsss/list/xm",
        type: 'GET',
        dataType: 'json',
        data: {xmid: xmid},
        success: function (data) {
            if (data.length > 0) {
                showFwfsss(xmid);
            } else {
                ityzl_SHOW_INFO_LAYER("无房屋附属设施");
            }


        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });


}

//打开房屋附属设施页面
function showFwfsss(xmid) {
    layer.open({
        type: 2,
        title: "房屋附属设施信息",
        area: ['960px', '600px'],
        fixed: false, //不固定
        content: getContextPath() + "/view/slym/fwfsss.html?xmid=" + xmid,
        btnAlign: "c",
        success: function () {
        }
    });

}


//原权利信息详情
//根据当前项目的xmid找到上一手原权利并展示
function openYqlxx(xmid) {
    $.ajax({
        url: getContextPath() + "/slym/ql/yql",
        type: 'GET',
        dataType: 'json',
        data: {xmid: xmid},
        success: function (data) {
            if (data !== null) {
                var bdcXm = data.bdcXm;
                showQl(bdcXm.xmid, bdcXm.qllx, bdcXm.bdcdyfwlx, true);
            } else {
                ityzl_SHOW_INFO_LAYER("无原权利信息");
            }
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });

}

/**
 * 判断当前流程是否是查封到期解封流程
 */
function cfdqJflc(){
    $.ajax({
        url: '/realestate-accept-ui/slym/xm/cfdqJflc',
        data: {gzlslid:processInsId},
        type: 'GET',
        async: false,
        success: function (data) {
            cfdqJflc = data;
        },
        error: function () {
            cfdqJflc = false;
        }
    });
    return cfdqJflc;
}

//按钮加载
function loadButtonArea(ymlx) {
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var gzlslid = getQueryString("processInsId");
        var zxlc = getQueryString("zxlc");
        var lclx;
        getReturnData("/slym/xm/getlclx", {gzlslid: gzlslid}, "GET", function (data) {
            if (data !== null) {
                lclx = data;
            }
        }, function (xhr) {
            delAjaxErrorMsg(xhr)
        }, false);
        var qlxx =null;
        getReturnData("/slym/ql/qlmc", {gzlslid: gzlslid, zxlc: zxlc}, 'GET', function (data) {
            if (isNotBlank(data)) {
                qlxx = data;
            }
        }, function (err) {
            console.log(err);
        }, false);


        // 不是6的情况要隐藏审核不通过的按钮
        if(qlxx &&qlxx.length >0 &&qlxx[0].bdcXm != null && qlxx[0].bdcXm.sply!= 6){
            setTimeout(function(){$("#shbtg").hide()},200)
        }

        var form = layui.form;
        var laytpl = layui.laytpl;
        var json = {qlxx: qlxx, printBtn: slymPrintBtn};
        var tpl = buttonAreaTpl.innerHTML, view = document.getElementById('buttonArea');
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
        form.render();
        getStateById(readOnly, formStateId, ymlx);
        disabledAddFa();
        titleShowUi();

        //证书预览
        $("#createZs").click(function () {
            createZs();
        });

        // 地籍调查表/海籍调查表
        $("#djdcb").click(function () {
            djdcb(processInsId);
        });
        //档案调用
        $("#dady").click(function () {
            dady(processInsId);
        });

        //查看权籍附件
        $("#showLpbFj").click(function () {
            showLpbFj("");
        });

        //评价器
        $("#evaluate").click(function () {
            evaluate();
        });

        //更新证书信息
        $("#updateZs").click(function () {
            updateZs();
        });
        // 打印电子发票
        $("#printDzfp").click(function(){
            printDzfp();
        });

        // 审核不通过
        $("#shbtg").click(function () {
            shbtg();
        });
        //获取扣款信息
        $("#getKkxx").click(function () {
            console.log("获取扣款信息");
            getKkxx();
        });

        // 按钮宽度
        $(".title-btn-area .layui-btn").css("padding","0 10px");
        if($(".title-btn-area").height()>38){
            $(".bdc-tab-box").css("padding-top","76px")
        }
    });
}

//获取扣款信息
function getKkxx(){
    getReturnData("/slym/sw/wsxx", {gzlslid: processInsId, htbh: getHtbhParam()}, "GET", function (data) {
        ityzl_SHOW_SUCCESS_LAYER("获取成功");
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    })
}

//获取合同编号
function getHtbhParam() {
    var htbh = "";
    getReturnData("/ycsl/getJyxxByGzlslid", {gzlslid: processInsId}, "GET", function (data) {
        if (isNotBlank(data)) {
            htbh = data.htbh;
        }
    }, function (error) {
        console.info(error);
    }, false);
    return htbh;
}