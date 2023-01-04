var $, form, layer, element, table, laytpl, laydate;
var sjclids = new Set();

var includeDyaq = false;

//非受理节点优先展示权利所需
var isSljd = false,isQtjd = false;
var taskId = getQueryString("taskId");

var cfdqJflc = cfdqJflc();
//页面入口
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl', 'laydate'], function () {
    form = layui.form;
    $ = layui.jquery;
    layer = layui.layer;
    table = layui.table;
    laytpl = layui.laytpl;
    laydate = layui.laydate;

    //form验证
    form.on('checkbox(mjdw)', function () {
        $("[name='mjdw']").prop("checked", "");
        $(this).prop("checked", "checked");
        form.render('checkbox');
    });

    form.render();
    var element = layui.element;
    element.init();
    //监听第一次单击tab，
    var qlIndex = 0;
    element.on('tab(tabFilter)', function (data) {
        var tabid = $(".layui-tab-title .layui-this").attr("id");
        $(this).removeAttr("onclick");
        if (tabid === "qlxx") {
            if (qlIndex === 0 && isSljd) {
                qlIndex++;
                addModel();
                setTimeout(function () {
                    refreshQlxx();
                }, 0);
            }
        }

    });

    addModel();
    //加载页面上方按钮模块
    setTimeout("loadButtonArea('slymzh')", 10);
    setTimeout(function () {
        try {
            $.when(loadData()).done(function () {
                removeModal();
            })
        } catch (e) {
            removeModal();
            ERROR_CONFIRM("加载失败", e.message);
            return
        }
    }, 10);
    titleShowUi();

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
    form.on('select(zjhFilter)', function (data) {
        if (data.value == '1') {
            $(data.elem).parents('td').next().find('input').attr('lay-verify', 'identitynew');
        } else {
            $(data.elem).parents('td').next().find('input').attr('lay-verify', '');
        }
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
        reGenerateQlqtzk(xmid,"2",$(this));
    });

    //重新生成附记
    $(".bdc-form-div").on('click','.resetfj', function () {
        var xmid =$(this).data("xmid");
        reGenerateFj(xmid,"3",$(this));
    });

    //监听 权利信息 内 附记 单击
    $('.layui-tab-content').on('click','.bdc-qlqtzk-popup',function(){
        var $nowTab =$(this).parents(".layui-tab-item");
        $nowTab.find("#qlqtzksdtx").removeAttr('style');
    });

    //监听 权利信息 内 附记 单击
    $('.layui-tab-content').on('click','.bdc-pj-popup',function(){
        var $nowTab =$(this).parents(".layui-tab-item");
        $nowTab.find("#fjsdtx").removeAttr('style');
    });

    //监听查封类型 选择轮候查询的时候 清空开始结束时间
    form.on('select(cflx)', function (data) {
        if (data.value == '2') {
            $("#cf-cfqssj").val('');
            $("#cf-cfjssj").val('');
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
            getStateById(readOnly, formStateId, 'slymzh');
            fixBgm();
        }
        // form.render();
    });

    // 点击房屋性质，房屋性质名称做出改变
    form.on('select(fwxz)', function (data) {
        var selected_value = data.value;
        var selected_text = $(data.elem).parents('div.layui-inline').find('dd[lay-value=' + selected_value + ']').text().trim();
        var $this_fwxz_outer_dom = $(data.elem).parents('div.layui-inline');
        var $this_fwxzmc_outer_dom = $this_fwxz_outer_dom.parent().find('[name="fwxzmc"]').parents('div.layui-inline');

        if (selected_value == 99) {
            $this_fwxzmc_outer_dom.css({
                'display': 'block'
            });
            $this_fwxzmc_outer_dom.find('[name="fwxzmc"]').val('');
            $this_fwxzmc_outer_dom.find('[name="fwxzmc"]').attr('value', '')
        } else {
            $this_fwxzmc_outer_dom.css({
                'display': 'none'
            });
            $this_fwxzmc_outer_dom.find('[name="fwxzmc"]').val(selected_text);
            $this_fwxzmc_outer_dom.find('[name="fwxzmc"]').attr('value', selected_text);
        }

    });

    // 点击规划用途，用途名称做出改变
    form.on('select(ghyt)', function (data) {
        // 普通
        if ($(data.elem).parent().get(0).tagName != 'TD') {
            var selected_value = data.value;
            var selected_text = $(data.elem).parents('div.layui-inline').find('dd[lay-value=' + selected_value + ']').text().trim();
            var $this_ghyt_outer_dom = $(data.elem).parents('div.layui-inline');
            var $this_ytmc_outer_dom = $this_ghyt_outer_dom.parent().find('[name="ytmc"]').parents('div.layui-inline');

            if (selected_value == 80) {
                $this_ytmc_outer_dom.css({
                    'display': 'block'
                });
                $this_ytmc_outer_dom.find('[name="ytmc"]').val('')
                $this_ytmc_outer_dom.find('[name="ytmc"]').attr('value', '')
            } else {
                $this_ytmc_outer_dom.css({
                    'display': 'none'
                });
                $this_ytmc_outer_dom.find('[name="ytmc"]').val(selected_text);
                $this_ytmc_outer_dom.find('[name="ytmc"]').attr('value', selected_text);
            }
        } else {
            // 项目内多幢
            var selected_value = data.value;
            var selected_text = $(data.elem).parents('td').find('dd[lay-value=' + selected_value + ']').text().trim();
            var $tr = $(data.elem).parents('tr');
            var $ytmctd = $tr.find('[name="ytmc"]').parents('td');

            if (selected_value == 80) {
                $tr.find('#ytmc').removeAttr('disabled');
                $ytmctd.find('img').css('display', 'none');
                $tr.find('[name="ytmc"]').val('');
                $tr.find('[name="ytmc"]').attr('value', '');
                $ytmctd.removeClass();
            } else {
                $tr.find('#ytmc').removeAttr('disabled');
                $ytmctd.find('img').css('display', 'none');
                $tr.find('[name="ytmc"]').val('');
                $tr.find('[name="ytmc"]').attr('value', selected_text);
                $tr.find('#ytmc').attr('disabled', 'off');
                $ytmctd.find('img').css({
                    'display': 'inline-block',
                    'position': 'absolute',
                    'top': '50%',
                    'right': '6px',
                    'transform': 'translateY(-50%)',
                    'z-index': '991'
                });


            }


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
var qlList;
var showYqlBtn;
var bdcXm;
//是否虚拟号
var isXndyh = false;
var djxlMap = queryDjxlPz();
var formIds = "";
var qllxforqlr;
var qlxx;
var qlrCache;


//加载页面数据（入口）
function loadData() {
    getReturnData("/bdczd", '', 'POST', function (data) {
        if (isNotBlank(data)) {
            zdList = data;
            loadQlxx();

        }
    }, function (err) {
        console.log(err);
    });
}

//加载基本信息模块（入口）
function loadJbxx() {
    getReturnData("/slym/xm", {processInsId: processInsId}, 'GET', function (data) {
        if (isNotBlank(data)) {
            bdcXm = data;
            xmid = data.xmid;
            getReturnData("/slym/xm/fb",{xmid:xmid},"GET",function (result) {
                //面积单位为空时默认为平方米
                if (data.mjdw === null || data.mjdw === '') {
                    data.mjdw = '1'
                }
                //判断是否是虚拟单元号
                isXndyh = checkXndyh(data.bdcdyh);
                generateJbxx(data, result);
                //加载收件材料
                loadSjcl();
            },function (xhr) {
                delAjaxErrorMsg(xhr);
            });



        }
    }, function (err) {
        console.log(err);
    });
}

//组织基本信息到页面
function generateJbxx(bdcxmxx,xmfb) {

    var json = {
        bdcxmxx: bdcxmxx,
        zd: zdList,
        xmfb:xmfb
    };
    var tpl = jbxxTpl.innerHTML, view = document.getElementById('sljbXx');
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });
    form.render();
    renderDate(form, formIds);
    getStateById(readOnly, formStateId, 'slymzh','sljbXx');
    form.render('select');
    disabledAddFa();
    renderSelect();
}

//加载权利信息（入口）
function loadQlxx() {
    getReturnData("/slym/ql/zhlc", {processInsId: processInsId, sfcxql: false}, 'GET', function (data) {
        if (isNotBlank(data)) {
            qlxx = data;
            qlList = data;
            buildQlxx(data);
        }
    }, function (err) {
        console.log(err);
    });
}

//更新权利信息
function refreshQlxx() {
    getReturnData("/slym/ql/zhlc", {processInsId: processInsId, sfcxql: true,qtsj:true,cxexistDyaq:true,zxlc:zxlc}, 'GET', function (data) {
        removeModal();
        if (isNotBlank(data)) {
            qlList = data;
            generateQlxx(qlList);
            initFwxzmcYtmc();
        }
    }, function (err) {
        delAjaxErrorMsg(err);
    });
}

function commonReloadQlxx() {
    refreshQlxx();
}

//组织权利页面（主要是权利信息标题与框架，内容暂不加载）
function buildQlxx(bdcSlQlxxymDTOList) {
    var json = {
        bdcSlQlxxymDTOList: bdcSlQlxxymDTOList
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
    json['cfdqJflc'] = cfdqJflc;

    var liTpl = liTableTpl.innerHTML, view = document.getElementById('liTbale');
    //渲染数据
    laytpl(liTpl).render(json, function (html) {
        view.innerHTML = view.innerHTML + html;
    });

    var contentTpl = contentTableTpl.innerHTML, view = document.getElementById('contentTable');
    //渲染数据
    laytpl(contentTpl).render(json, function (html) {
        view.innerHTML = view.innerHTML + html;
    });

    loadJbxx();

    $(".qlxx-btn").click(function () {
        openQlxx(this);
    });
    form.render();
    renderDate(form, formIds);
    //给下拉框添加删除图标
    renderSelectClose(form);
    getStateById(readOnly, formStateId, 'slymzh');
    form.render('select');
    disabledAddFa();
    //是否需要监听修改字段
    for (var i = 0; i < bdcSlQlxxymDTOList.length; i++) {
        var bdcSlQlxxym = bdcSlQlxxymDTOList[i];
        if (bdcSlQlxxym.qllx == commonDyaq_qllx) {
            includeDyaq = true;
        }
        if (isNotBlank(xgnrglxs) && xgnrglxs.indexOf(bdcSlQlxxym.bdcXm.djxl) > -1) {
            if (formIds.indexOf("sljbXx") < 0) {
                formIds += "sljbXx,";
            }
            renderChange("", form, formIds);
        }

    }

    if(isQtjd){
        refreshQlxx();
    }
}

//组织权利信息到页面
function generateQlxx(bdcSlQlxxymDTOList) {
    if (isNotBlank(bdcSlQlxxymDTOList)) {
        for (var i = 0; i < bdcSlQlxxymDTOList.length; i++) {
            var bdcSlQlxxym = bdcSlQlxxymDTOList[i];
            var qllx = bdcSlQlxxym.qllx;
            qllxforqlr = bdcSlQlxxym.qllx;
            //异议登记修改权利人类型为  （权利人(申请人)）
            if (qllx === 97) {
                changeQlrlbMc();
            }
            showYqlBtn =bdcSlQlxxym.showYqlBtn;
            var json = {
                bdcSlQlxxym: bdcSlQlxxym,
                index: i + 1,
                zd: zdList,
                showYqlBtn: showYqlBtn,
                dkfs: dkfs,
                cfdqJflc : cfdqJflc
            };
            var bdcdyfwlx = "";
            if (isNotBlank(bdcSlQlxxym.bdcQl.bdcdyfwlx) && bdcSlQlxxym.bdcQl.bdcdyfwlx === 1) {
                bdcdyfwlx = bdcSlQlxxym.bdcQl.bdcdyfwlx;
            }
            var qllxTpl = document.getElementById(bdcSlQlxxym.tableName + bdcdyfwlx);
            var tpl = qllxTpl.innerHTML, view = document.getElementById('qllx' + (i + 1));
            laytpl(tpl).render(json, function (html) {
                view.innerHTML = html;
                laydate.render({
                    elem: '.test-item' //指定元素
                    ,type: 'date'

                });
            });
            //截取权利名称_后部分内容，作为区分权利TAB页项目信息权限的标志
            var index = bdcSlQlxxym.tableName.lastIndexOf("\_");
            var qlTableId = bdcSlQlxxym.tableName.substring(index + 1, bdcSlQlxxym.tableName.length);
            // 加载更正信息模块
            if ($('#bdc_gzxx').length > 0) {
                generateGzxx(bdcSlQlxxym, "gzxx" + (i + 1), i + 1);
            }
            //加载单元信息模块
            generateBdcdyZh(bdcSlQlxxym.bdcXm, bdcSlQlxxym.bdcXmFbDO, "bdcdyxx" + (i + 1), i + 1, qlTableId);
            loadQlrZh(bdcSlQlxxym.bdcXm.xmid, "sqr" + (i + 1), i + 1,true,bdcSlQlxxym.dydj);
            generateLzrxx(bdcSlQlxxym.bdcLzrDOList,"lzrxx" + (i+1),i+1,bdcSlQlxxym.bdcXm.xmid,true);
            generateXmZh(bdcSlQlxxym, "xmxx" + (i + 1), i + 1, qlTableId);
            if (isNotBlank(bdcSlQlxxym.bdcQl.bdcdyfwlx) && bdcSlQlxxym.bdcQl.bdcdyfwlx === 1) {
                generateFdcqxm(bdcSlQlxxym.bdcQl.qlid);
            }
            if (bdcSlQlxxym.tableName === "bdc_fdcq3") {
                //加载建筑物区分所有权共有信息模块
                getFdcq3Gyxx(bdcSlQlxxym.bdcQl.qlid);

            }
            //定位权利其他状况元素
            var $qlqtzk = $("#qlTab" + (i + 1)).find("textarea[name='bfqlqtzk']");
            //如果权利其他状况没有值,加载模板数据
            if ($qlqtzk.length > 0 &&!isNotBlank($qlqtzk.val())) {
                //加载权利其他状况
                queryQlqtzkFjMb(bdcSlQlxxym.bdcXm.xmid, $qlqtzk, "2",true);
            }
            //定位附记元素
            var $fj = $("#qlTab" + (i + 1)).find('.qlfj');
            //如果附记没有值,加载模板数据
            if ($fj.length > 0 && !isNotBlank($fj.val())) {
                //加载附记
                queryQlqtzkFjMb(bdcSlQlxxym.bdcXm.xmid, $fj, "3", true);
            }

            //added by cyc 2019-09-19,只有预查封和裁定过户才显示第三权利人
            if ((bdcSlQlxxym.bdcQl.cflx && bdcSlQlxxym.bdcQl.cflx == "3" && bdcSlQlxxym.qllx == "98") || djxlMap.cdghDjxl.indexOf(bdcSlQlxxym.bdcXm.djxl) != -1) {
                loadDsQlr(bdcSlQlxxym.bdcXm.xmid, "ygr" + (i + 1), i + 1, bdcSlQlxxym.bdcQl.cflx);
            }
            //是否展示电过户信息
            if (sdqghDjxlList.indexOf(bdcSlQlxxym.bdcXm.djxl) > -1) {
                loadSqdghxx(bdcSlQlxxym.bdcSdqghDOList);
            }
        }
        //是否需要监听修改字段
        for (var j = 0; j < bdcSlQlxxymDTOList.length; j++) {
            var bdcSlQlxxym = bdcSlQlxxymDTOList[j];
            if (isNotBlank(xgnrglxs) && xgnrglxs.indexOf(bdcSlQlxxym.bdcXm.djxl) > -1) {
                if (formIds.indexOf('qllx' + (j + 1)) < 0) {
                    formIds += 'qllx' + (j + 1) + ",";
                }
                renderChange("", form, formIds);
                renderDate(form, formIds);
            }
            //监听实时同步不动产单元信息
            syncBdcdyxx(bdcSlQlxxym, (j + 1), bdcSlQlxxymDTOList);
        }
        form.render();
        if($(".bdc-qlr").length >0) {
            //渲染背景色
            renderBackgroundColor('bdc-qlr');
        }
        renderSelect();
        //给下拉框添加删除图标
        renderSelectClose(form);
        getStateById(readOnly, formStateId, 'slymzh');
        fixBgm();//
        form.render('select');
        disabledAddFa();
    }
}

//加载权利人（入口）
//isLoad:是否为页面加载,页面加载不查询表单权限
function loadQlrZh(xmid, name, index,isLoad,dydj) {
    getReturnData("/slym/qlr/list/xm", {xmid: xmid, lclx: "zhlc"}, 'GET', function (data) {
        if(!dydj) {
            qlrCache = data;
        }
        generateQlrxx(data, xmid, name, index,isLoad,dydj);
    }, function (err) {
        console.log(err);
        //页面刷新问题同步查询，异步会有一个权利tab不刷新
    }, false);
}

//加载权利人（刷新权利人，用于打开新页面后的刷新）
function loadQlr() {
    var index = $("input[name='liIndex']");
    if (index !== null && index.length > 0) {
        for (var i = 0; i < index.length; i++) {
            var tempxmid = "";
            var c = $(index[i]).val();
            var qlrArray = $(".qlr" + c);
            var sqrFormArray = qlrArray.serializeArray();
            var ywrArray = $(".ywr" + c);
            var ywrFormArray = ywrArray.serializeArray();
            var dsQlrArray = $(".dsQlr" + c);
            if (sqrFormArray !== null && sqrFormArray.length > 0) {
                var name = "sqr" + (i + 1);
                var xmid = sqrFormArray[0].value;
                tempxmid = xmid;
                loadQlrZh(xmid, name, i + 1,false,null);
                //重新加载抵押人查封机关信息
                reloadDyrAndCfjg(xmid, i + 1);
            } else {
                var name = "sqr" + (i + 1);
                var xmid = $("input[name='xmid']");
                if (xmid !== null && xmid.length > 0) {
                    xmid = $(xmid[i]).val();
                }
                loadQlrZh(xmid, name, i + 1,false,null);
                //重新加载抵押人查封机关信息
                reloadDyrAndCfjg(xmid, i + 1);
            }
            if (ywrFormArray !== null && ywrFormArray.length > 0) {
                var name = "sqr" + (i + 1);
                var ywrxmid = ywrFormArray[0].value;

                loadQlrZh(ywrxmid, name, i + 1,false,null);
            }

            if ($('.dsQlrTitle:visible').length > 0) {
                var name = "ygr" + (i + 1);
                tempxmid = tempxmid == "" ? xmid : tempxmid;
                loadDsQlr(tempxmid, name, i + 1, 'refresh');
            }

        }
    }
    if(qllxforqlr === 94) {
        updateFdcq3Qlr(processInsId);
    }
}

//组织项目信息到组合页面
function generateXmZh(bdcSlQlxxym, name, index, qlTableId) {
    var xmTpl = document.getElementById("xmxxTpl");
    var json = {
        index: index,
        xmxx: bdcSlQlxxym.bdcXm,
        xmfb: bdcSlQlxxym.bdcXmFbDO,
        djxldjyy: bdcSlQlxxym.bdcDjxlDjyyGxDOList,
        zd: zdList,
        qlTableId: qlTableId
    };
    var tpl = xmTpl.innerHTML, view = $("." + name)[0];

    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });
    //判断页面是否存在交易信息字段，显示即查询
    if ($(".jyxx" + index).length > 0 && isNotBlank(bdcSlQlxxym.bdcXm.xmid)) {
        getReturnData("/ycsl/jyxx", {xmid: bdcSlQlxxym.bdcXm.xmid}, "GET", function (data) {
            //表单赋值
            form.val("xmForm" + index, data);
            renderDate(form, formIds);
        }, function (error) {
            delAjaxErrorMsg(error);
        });
    }
}

// 组织更正信息到组合页面
function generateGzxx(bdcSlQlxxym, name, index) {
    var gzxxTpl = bdc_gzxx.innerHTML;
    var gzxxView = document.getElementById(name);

    var json = {
        index: index,
        zd: zdList,
        bdcSlQlxxym: bdcSlQlxxym
    };
    laytpl(gzxxTpl).render(json, function(html) {
        gzxxView.innerHTML = html;
    });
}

//组织单元信息到组合页面
function generateBdcdyZh(data,bdcxmFb, name, index, qlTableId) {
    var bdcdyxxTpl = document.getElementById("bdcdyxxTpl");
    var xmid = data.xmid;
    //处理是否换证,是否主房
    var sfzf ="";
    getReturnData("/slym/xm/getBdcCshFwkgSlDO?xmid="+xmid,"","GET",function (result) {
        if(result){
            sfzf =result.sfzf;
            $("input[name='sfjcybdcqzh" + index + "'][value=" + result.sfhz + "]").prop("checked", "checked");
            form.render();
            resetSelectDisabledCss();
        }
    },function (error) {
        delAjaxErrorMsg(error);

    },false);
    var yhfs = "";
    var hydb = "";
    var bdcdyh = data.bdcdyh;
    if(hyllc){
        getReturnData("/rest/v1.0/slym/queryYhfs?bdcdyh="+bdcdyh,"","GET",function (result) {
            if(result){
                yhfs =result;
            }
        },function (error) {
            delAjaxErrorMsg(error);
        },false);

        getReturnData("/rest/v1.0/slym/queryDb?bdcdyh="+bdcdyh,"","GET",function (result) {
            if(result){
                hydb =result;
            }
        },function (error) {
            delAjaxErrorMsg(error);
        },false);
    }
    var json = {
        index: index,
        xmxx: data,
        zd: zdList,
        xmfb: bdcxmFb,
        qlTableId: qlTableId,
        sfzf:sfzf,
        hyllc:hyllc,
        hydb:hydb,
        yhfs:yhfs
    };
    var tpl = bdcdyxxTpl.innerHTML, view = $("." + name)[0];

    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });

    //监听用海方式修改
    form.on('select(yhfs)', function (data) {

        var yhfs = $("#yhfs").val();
        $.ajax({
            url: getContextPath() + "/rest/v1.0/slym/updateYhfs?bdcdyh=" + bdcdyh + "&yhfs=" + yhfs,
            type: 'POST',
            async: false,
            contentType: "application/json",
            success: function (data) {
                ityzl_SHOW_SUCCESS_LAYER("修改成功");
            }, error: function (xhr, status, error) {
                removeModal();
                delAjaxErrorMsg(xhr)
            }
        });
    });

    //监听海域等级修改
    form.on('select(hydb)', function (data) {
        var hydb = $("#hydb").val();
        $.ajax({
            url: getContextPath() + "/rest/v1.0/slym/updateDb?bdcdyh=" + bdcdyh + "&hydb=" + hydb,
            type: 'POST',
            async: false,
            contentType: "application/json",
            success: function (data) {
                ityzl_SHOW_SUCCESS_LAYER("修改成功");
            }, error: function (xhr, status, error) {
                removeModal();
                delAjaxErrorMsg(xhr)
            }
        });
    });


}

/*
* 获取领证人信息
*/
function loadLzrxx(id,index,xmid) {
    getReturnData("/slym/lzr/nt/lzrxx", {xmid: xmid}, 'GET', function (data) {
        generateLzrxx(data,id,index,xmid,false);
    }, function (err) {
        console.log(err);
    });
}


/*
* 加载领证人信息
*/
function generateLzrxx(lzrdata,id,index,xmid,isLoad) {
    var json = {
        index:index,
        bdclzrList: lzrdata,
        zd: zdList,
        xmid:xmid
    };
    var tpl = lzrxxTpl.innerHTML, view = document.getElementById(id);
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });
    //页面加载此处不用处理权限,后续统一处理
    if(isLoad == null ||!isLoad) {
        form.render(null, "lzrxxForm");
        getStateById(readOnly, formStateId, 'slymzh');
        form.render('select');
        resetSelectDisabledCss();
    }
}

function loadSjcl() {
    getReturnData("/slym/sjcl/list/pl", {processInsId: processInsId}, 'GET', function (data) {
        generateSjcl(data, "");
    }, function (err) {
        console.log(err);
    });
}


//组织权利人数据到页面
function generateQlrxx(data, xmid, name, index,isLoad,sfdydj) {
    if (sfdydj ==null) {
        sfdydj = false;
        getReturnData("/slym/ql/checkDydj", {xmid: xmid}, "GET", function (data) {
            sfdydj = data;
        }, function () {
        }, false);
    }
    var json = {
        index: index,
        name: name,
        xmid: xmid,
        bdcQlrDOList: data,
        zd: zdList,
        sfdydj: sfdydj,
        qllx: qllxforqlr
    };
    var tpl;
    var view;
    if (qlList.length > 1) {
        tpl = sqrTpl.innerHTML;
        view = $("." + name)[0];
    } else {
        tpl = sqrDgTpl.innerHTML;
        view = $("." + name)[0];
    }
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });
    //页面加载此处不用处理权限,后续统一处理
    if(isLoad == null ||!isLoad) {
        renderDate(form, formIds);
        form.render(null, "sqrForm" + index);
        getStateById(readOnly, formStateId, "slymzh", 'qllx' + (index));
        //重新渲染select框
        form.render('select');
        if($(".bdc-qlr").length >0) {
            //渲染背景色
            renderBackgroundColor('bdc-qlr');
        }
        disabledAddFa("sqrForm" + index);
    }
}

var sjclNumber = 0;
//用于存放所有的收件材料id
var sjclidLists = [];

//组织收件材料到页面
function generateSjcl(data, xmid) {
    sjclNumber = data.length;
    if (data !== null && data.length > 0) {
        for (var i = 0; i < data.length; i++) {
            sjclidLists.push(data[i].sjclid);
        }
    }
    var json = {
        xmid: xmid,
        bdcSlSjclDOList: data,
        zd: zdList
    };
    var tpl = sjclTpl.innerHTML, view = document.getElementById("sjcl");
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });
    form.render();
    getStateById(readOnly, formStateId, 'slymzh', 'sjcl');
    form.render('select');
    disabledAddFa("sjclForm");
}

//加载项目内多幢数据（入口）
function generateFdcqxm(qlid) {
    getReturnData("/slym/ql/fdcqxm/list", {qlid: qlid}, 'GET', function (data) {
        if (isNotBlank(data)) {
            generateFdcqxmList(data);
            initFwxzmcYtmc();
        } else {
            $("#fdcqxmTable").hide();
        }
    }, function (err) {
        console.log(err);
    });
}

//组织项目内多幢信息到页面
function generateFdcqxmList(bdcFdcqFdcqxmDOList) {
    var json = {
        bdcFdcqFdcqxmDOList: bdcFdcqFdcqxmDOList,
        zd: zdList
    };
    var tpl = fdcqxmTpl.innerHTML, view = document.getElementById('fdcqxm');
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });
    form.render();
    renderDate(form, formIds);
    renderSelect();
    getStateById(readOnly, formStateId, 'slymzh');
    disabledAddFa();
}

/**
 *  设置建筑物区分所有权业主共有部分登记信息_共有部分信息内容
 */
function getFdcq3Gyxx(qlid) {
    getReturnData("/slym/ql/fdcq3/gyxx", {qlid: qlid}, 'GET', function (data) {
        var json = {
            gyxxList: data,
            zd: zdList
        };
        var tpl = fdcq3gyxxTpl.innerHTML, view = document.getElementById('fdcq3gyxx');
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
        form.render();
        renderDate(form, formIds);
        renderSelect();
        getStateById(readOnly, formStateId, 'slymzh',"fdcq3gyxx");
        disabledAddFa();

    }, function (err) {
        console.log(err);
    });

}

/**
 * 重新生成权利其他状况
 */
function reGenerateQlqtzk(xmid,mode,$btn){
    var $qlqtzk =$btn.parents(".layui-input-inline").find("textarea[name='bfqlqtzk']");
    if($qlqtzk.length >0) {
        $qlqtzk.val("");
        //清空原有数据并保存受理页面
        $('#saveData').click();
        //重新加载模板数据--保存后页面会刷新不用重新加载

    }
}

/**
 * 重新生成附记
 */
function reGenerateFj(xmid,mode,$btn){
    var $fj =$btn.parents(".layui-input-inline").find("textarea[name='fj']");
    if($fj.length >0) {
        $fj.val("");
        //清空原有数据并保存受理页面
        $('#saveData').click();
        //重新加载模板数据--保存后页面会刷新不用重新加载
    }
}

//---------------------xq.js整理--------------------------------------
//权利信息详情（受理组合页面）---点击详细信息按钮
function openQlxx(element) {
    var qllxym;
    var tableName = $(".layui-this").find("input[name='tableName']")[0];
    var qllx = $(tableName).val();
    if (qllx == "bdc_fdcq") {
        var bdcdyfwlxInput = $(element).parent().parent().find("input[name='bdcdyfwlx']")[0];
        var bdcdyfwlx = $(bdcdyfwlxInput).val();
        if (bdcdyfwlx != "1") {
            qllxym = "fdcq";
        } else {
            qllxym = "fdcqXmndzfw";
        }
    }
    var qllxArr = {
        "bdc_dyaq": "dyaq",
        "bdc_cf": "cfdj",
        "bdc_yg": "ygdj",
        "bdc_yy": "yydj",
        "bdc_lq": "lq",
        "bdc_tdcbnydsyq": "tdcbjyqNyddqtsyq",
        "bdc_dyiq": "dyiq",
        "bdc_jsydsyq": "jsydsyq",
        "bdc_gjzwsyq": "gzwsyq",
        "bdc_fdcq3": "jzwqfsyqyzgybf",
        "bdc_hysyq":"hysyq",
        "bdc_jzq":"jzq",
        "bdc_tdsyq":"tdsyq"
    };
    if (qllxArr[qllx] != undefined) {
        qllxym = qllxArr[qllx];
    }
    var xmidInput = $(element).parent().find("input[name='xmid']")[0];
    var xmid = $(xmidInput).val();
    var url = "/realestate-register-ui/view/qlxx/" + qllxym + ".html?isCrossOri=false&xmid=" + xmid + "&processInsId="+processInsId+"&zxlc="+zxlc+"&formStateId=" + formStateId + "&readOnly=true";
    layerOpenFull(url, "权利信息");
}

//新增权利人展示  -----------------点击新增申请人
function addQlr(qllx, dydj) {
    var xmidArray = $(".layui-this").find("input[name='xmid']");
    var xmidInput = xmidArray[0];
    var addXmid = $(xmidInput).val();
    if (!isNotBlank(addXmid)) {
        addXmid = xmid;
    }
    var url = getContextPath() + "/view/slym/qlr.html?xmid=" + addXmid +"&cfdqJflc="+cfdqJflc+ "&lclx=" + lclx + "&processInsId=" + processInsId + "&formStateId=" + formStateId + "&qllx=" + qllx + "&zxlc=" + zxlc;
    if (dydj) {
        layer.open({
            type: 2,
            area: ['960px', '575px'],
            fixed: false, //不固定
            title: "新增申请人",
            content: url + "&dydj=true",
            btnAlign: "c"
        });
    } else {
        layer.open({
            type: 2,
            area: ['960px', '575px'],
            fixed: false, //不固定
            title: "新增申请人",
            content: url,
            btnAlign: "c"
        });
    }
    form.render();
    resetSelectDisabledCss();
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
        }
    });
    return cfdqJflc;
}


//权利人详情展示 -----点击 详细 按钮
function showQlr(qlrid, xmid, qlrlb, elem) {
    addModel();
    var $this = $(".layui-this");
    var qllx = $($this.find("input[name='qllx']")[0]).val();
    var dydj = $($this.find("input[name='dydj']")[0]).val();
    var title = '';
    var url = getContextPath() + "/view/slym/qlr.html?processInsId=" + processInsId + "&qlrid=" + qlrid + "&lclx=" + lclx + "&xmid=" + xmid + "&formStateId=" + formStateId + "&qllx=" + qllx + "&readOnly=" + readOnly + "&zxlc=" + zxlc;
    if ((qllx === commonDyaq_qllx || dydj === "true")) {
        if (qlrlb === "1") {
            title = "抵押权人详细信息";
        } else {
            title = "抵押人详细信息";
        }
        layer.open({
            type: 2,
            area: ['960px', '575px'],
            fixed: false, //不固定
            title: title,
            content: url + "&dydj=true",
            btnAlign: "c"
        });
    } else {
        layer.open({
            type: 2,
            area: ['960px', '575px'],
            fixed: false, //不固定
            title: "申请人详细信息",
            content: url,
            btnAlign: "c"
        });
    }

    removeModal();
    form.render();
    renderDate(form, formIds);
    disabledAddFa();
}


//原权利信息详情
//根据当前项目的xmid找到上一手原权利并展示
function openYqlxx(xmid) {
    getReturnData("/slym/ql/yql", {xmid: xmid}, 'GET', function (data) {
        if (data !== null) {
            var bdcXm = data.bdcXm;
            showQl(bdcXm.xmid, bdcXm.qllx + "", bdcXm.bdcdyfwlx, true);
        } else {
            ityzl_SHOW_INFO_LAYER("无原权利信息");
        }
    }, function (err) {
        delAjaxErrorMsg(err);
    });

}

function showQl(xmid, qllx, bdcdyfwlx, sfyql) {
    if (xmid !== "" && xmid !== null) {
        var qllxym = getQlxxYm(parseInt(qllx), parseInt(bdcdyfwlx));
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
            cancel: function () {

            }
        });
        layer.full(index);
    } else {
        ityzl_SHOW_INFO_LAYER("无原权利信息")
    }
}

//组织参数调用评价器, groupByQxdm: true 控制评价器的 URL 地址是否根据 qxdm 来分。
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
        console.log(encodeURI(url));
        getReturnData("/pjq/insertPjq", {
            ywbh: pjdata.bdcXmDO.slbh,
            jdmc: "受理",
            blry: pjdata.bdcXmDO.slr,
            sqrxm: pjdata.qlrmc,
            sqrlxfs: pjdata.qlrlxfs,
            ywmc: pjdata.bdcXmDO.gzldymc
        }, "GET", function (data) {
            // ityzl_SHOW_SUCCESS_LAYER("评价成功");
            removeModal();
        }, function (xhr) {
            delAjaxErrorMsg(xhr);
        }, false)
        window.open(encodeURI(url));
    }
}



function addBdcdy() {
    var bdcGzYzQO ={};
    bdcGzYzQO.zhbs = "SLYM_ADDBDCDY";
    var gzyzParamMap={};
    gzyzParamMap.gzlslid =processInsId;
    bdcGzYzQO.paramMap = gzyzParamMap;
    gzyzCommon(2,bdcGzYzQO,function (data) {
        //新增不动产单元
        var url = getContextPath() + "/view/query/selectBdcdyh.html?gzlslid=" + processInsId + "&zlcsh=true&zllx=single";
        var index = layer.open({
            type: 2,
            title: "选择不动产单元",
            area: ['1300px', '600px'],
            fixed: false, //不固定
            maxmin: true, //开启最大化最小化按钮
            content: url
        });
        layer.full(index);
    });
}

/**
 * bdcSlQlxxym
 * index 当前数据索引
 * bdcSlQlxxymDTOList
 * 实时监听同步不动产单元信息
 */
function syncBdcdyxx(bdcSlQlxxym, index, bdcSlQlxxymDTOList) {
    var bdcdyh = bdcSlQlxxym.bdcXm.bdcdyh;
    //不动产单元信息同步
    var $bdcdyxx = $("#bdcdyxxForm" + index).find(".xmxx" + index);
    $bdcdyxx.on('change', function () {
        var id = $(this)[0].id;
        var value = $(this).val();
        //定着物面积与建筑面积同步
        var jzmjid = "";
        var jzmjelem = $("#qllx" + (index)).find("input[name=jzmj]");
        if (jzmjelem && jzmjelem.length > 1) {
            for (var i = 0; i < jzmjelem.length; i++) {
                if ("fdcqxmdz-jzmj" !== jzmjelem[i].id) {
                    jzmjid = jzmjelem[i].id;
                    break;
                }
            }

        }
        if (id === "dzwmj") {
            if (jzmjelem && jzmjelem.length > 1) {
                $("#qllx" + (index)).find("#" + jzmjid).val(value);
            } else {
                $("#qllx" + (index)).find("input[name=jzmj]").val(value);
            }
        }
        //循环同步其他TAB权利页数据
        for (var i = 0; i < bdcSlQlxxymDTOList.length; i++) {
            var dyh = bdcSlQlxxymDTOList[i].bdcXm.bdcdyh;
            if ((i + 1) !== index && bdcdyh === dyh) {
                $("#bdcdyxxForm" + (i + 1)).find("#" + id).val(value);
                //定着物面积与建筑面积同步
                if (id === "dzwmj") {
                    if (jzmjelem && jzmjelem.length > 1) {
                        $("#qllx" + (index)).find("#" + jzmjid).val(value);
                    } else {
                        $("#qllx" + (i + 1)).find("input[name=jzmj]").val(value);
                    }
                }
            }
        }

    });
    form.on('select', function (data) {
        var $select = $("div[name=bdcdyxx]").find(data.othis).find("input");
        if ($select.length > 0) {
            var id = data.elem.id;
            var value = data.value;
            //定着物用途与规划用途同步
            if (id === "dzwyt" && $("#qllx" + (index)).find("select[name=ghyt]").length !==0&&  $("#qllx" + (index)).find("select[name=ghyt]").parent().get(0).tagName != 'TD') {
                $("#qllx" + (index)).find(".ghyt").val(value);
            }
            //循环同步其他TAB权利页数据
            for (var i = 0; i < bdcSlQlxxymDTOList.length; i++) {
                var dyh = bdcSlQlxxymDTOList[i].bdcXm.bdcdyh;
                if (bdcdyh === dyh) {
                    $("#bdcdyxxForm" + (i + 1)).find("#" + id).val(value);
                    //定着物用途与规划用途同步
                    if (id === "dzwyt" && $("#qllx" + (i + 1)).find("select[name=ghyt]").length !==0&&  $("#qllx" + (i + 1)).find("select[name=ghyt]").parent().get(0).tagName != 'TD') {
                        $("#qllx" + (i + 1)).find(".ghyt").val(value);
                    }

                }
            }

            form.render("select");
            resetSelectDisabledCss();
        } else {
            var name = data.elem.name;
            var value = data.value;
            var classList = data.elem.classList;
            //定着物用途与规划用途同步
            if (name === "ghyt" && !classList.contains('fdcqxmdz')) {
                $("#bdcdyxxForm" + (index)).find("select[name=dzwyt]").val(value);
                //循环同步其他TAB权利页数据
                for (var i = 0; i < bdcSlQlxxymDTOList.length; i++) {
                    var dyh = bdcSlQlxxymDTOList[i].bdcXm.bdcdyh;
                    if ((i + 1) !== index && bdcdyh === dyh) {
                        $("#bdcdyxxForm" + (i + 1)).find("select[name=dzwyt]").val(value);
                        //定着物用途与规划用途同步
                        $("#qllx" + (i + 1)).find(".ghyt").val(value);
                    }
                }
            }
            form.render("select");
            resetSelectDisabledCss();


        }
        //监听修改内容
        if (isNotBlank(formIds)) {
            $.each(formIds.split(","), function (index, formID) {
                var $select = $("#" + formID).find(data.othis).find("input");
                if ($select.length > 0) {
                    $(data.elem).parents(".layui-input-inline").addClass('bdc-change-input');
                    var text = $(data.elem).parents(".layui-inline").find("label").text();
                    renderChangeTips(text);
                }
            });
        }


    });
    //建筑面积监听
    var $jzmj = $("#qllx" + index).find("input[name=jzmj]");
    if ($jzmj.length > 0) {
        $jzmj.on('change', function () {
            var value = $(this).val();
            $("#bdcdyxxForm" + (index)).find("input[name=dzwmj]").val(value);
            //循环同步其他TAB权利页数据
            for (var i = 0; i < bdcSlQlxxymDTOList.length; i++) {
                var dyh = bdcSlQlxxymDTOList[i].bdcXm.bdcdyh;
                if ((i + 1) !== index && bdcdyh === dyh) {
                    $("#bdcdyxxForm" + (i + 1)).find("input[name=dzwmj]").val(value);
                    //定着物面积与建筑面积同步
                    $("#qllx" + (i + 1)).find("input[name=jzmj]").val(value);

                }
            }

        });
    }
    //分摊土地面积和独用土地面积监听
    var $fttdmj = $("#qllx" + index).find("input[name=fttdmj]");
    var $dytdmj = $("#qllx" + index).find("input[name=dytdmj]");
    if ($fttdmj.length > 0 && $dytdmj.length > 0) {
        $fttdmj.on('change', function () {
            sumTdqlMj(index, $fttdmj, $dytdmj);
        });
        $dytdmj.on('change', function () {
            sumTdqlMj(index, $fttdmj, $dytdmj);
        });

    }


}

/**
 * 计算土地权利面积（分摊土地面积和独用土地面积之和)
 */
function sumTdqlMj(index, $fttdmj, $dytdmj) {
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
    var $syqmj =$("#qllx" + (index)).find("input[name=tdsyqmj]");
    if($syqmj.length ===0){
        $syqmj =$("#qllx" + (index)).find("input[name=syqmj]");
    }
    if($syqmj.length >0) {
        $syqmj.val((fttdmjX + dytdmjX) / 100);
    }
}
//计算使用期限（单位：月）
function countSyqxByMonth(){
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

function countSyqxByYear() {
    var syqjssj = $("#jsydsyq-syqjssj").val();
    var syqx = parseInt($(".tdsyqx1").val());
    if (syqx > 0) {
        //计算开始日期后的 日 要加一天
        var jssj = new Date(syqjssj);
        console.log(jssj.getFullYear());
        jssj.setFullYear(jssj.getFullYear() - syqx);
        var time = jssj.getTime() + 1000 * 60 * 60 * 24;
        console.log(new Date(time));
        console.log(Format(formatChinaTime(jssj), "yyyy-MM-dd"));
        $("#jsydsyq-syqqssj").val(Format(formatChinaTime(new Date(time)), "yyyy-MM-dd"));
    }
}

function countSyqxByYear2() {
    var syqjssj = $("#jsydsyq-syqjssj2").val();
    var syqx = parseInt($(".tdsyqx2").val());
    if (syqx > 0) {
        var jssj = new Date(syqjssj);
        console.log(jssj.getFullYear());
        jssj.setFullYear(jssj.getFullYear() - syqx);
        var time = jssj.getTime() + 1000 * 60 * 60 * 24;
        console.log(Format(formatChinaTime(jssj), "yyyy-MM-dd"));
        $("#jsydsyq-syqqssj2").val(Format(formatChinaTime(new Date(time)), "yyyy-MM-dd"));
    }
}

function countSyqxByYear3() {
    var syqjssj = $("#jsydsyq-syqjssj3").val();
    var syqx = parseInt($(".tdsyqx3").val());
    if (syqx > 0) {
        var jssj = new Date(syqjssj);
        console.log(jssj.getFullYear());
        jssj.setFullYear(jssj.getFullYear() - syqx);
        var time = jssj.getTime() + 1000 * 60 * 60 * 24;
        console.log(Format(formatChinaTime(jssj), "yyyy-MM-dd"));
        $("#jsydsyq-syqqssj3").val(Format(formatChinaTime(new Date(time)), "yyyy-MM-dd"));
    }
}

//加载预购人和买受人（入口）
function loadDsQlr(xmid, name, index, cflx) {
    getReturnData("/slym/qlr/list/dsQlr", {xmid: xmid}, 'GET', function (data) {
        generateDsQlrxx(data, xmid, name, index, cflx);
    }, function (err) {
        console.log(err);
    }, false);
}

//组织权利人数据到页面
function generateDsQlrxx(data, xmid, name, index, cflx) {
    var json = {
        index: index,
        name: name,
        xmid: xmid,
        bdcDsQlrDOList: data,
        zd: zdList,
        qllx: qllxforqlr
    };
    var tpl;
    var view;
    tpl = ygrTpl.innerHTML;
    view = $("." + name)[0];
    var titleStr = $('.dsQlrTitle').find('p').text();
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });
    renderDate(form, formIds);
    form.render();
    getStateById(readOnly, formStateId, "slymzh", 'qllx' + (index));
    //重新渲染select框
    form.render('select');
    disabledAddFa("ygrForm" + index);

    if (cflx != "refresh") {
        if (data && data.length > 0) {
            if (data[0].qlrlb == "1") {
                $('.dsQlrTitle').find('p').text("预购人")
            } else {
                $('.dsQlrTitle').find('p').text("买受人")
            }
        } else {
            if (cflx == "3") {
                $('.dsQlrTitle').find('p').text("预购人");
            } else {
                $('.dsQlrTitle').find('p').text("买受人");
            }
        }
    } else {
        $('.dsQlrTitle').find('p').text(titleStr);
    }

}

//新增权利人展示  -----------------点击新增申请人
function addYgrOrMsr(qllx) {
    var addXmid = "";
    var xmidArray = $(".layui-this").find("input[name='xmid']");
    var xmidInput = xmidArray[0];
    addXmid = $(xmidInput).val();
    if (!isNotBlank(addXmid)) {
        addXmid = xmid;
    }
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

function queryDjxlPz() {
    var djxlMap = "";
    getReturnData("/slym/qlr/getDjxl", "", "GET", function (data) {
        djxlMap = data;
    }, function (error) {
    }, false);
    return djxlMap;
}

/**
 * 处理交易信息中登记业务表相关信息
 */
function dealDjxx(data) {
    var bdcSlJyxx = {
        htbh : data.htbh,
        jyje : data.jyje,
        htdjsj : data.sqsj,
        xmid : xmid
    };
    var fcjyxxQO={
        version:"nantong"
    };
    var paremJson = {
        bdcSlJyxx :  bdcSlJyxx,
        fw : data.fw,
        fcjyxxQO:fcjyxxQO
    };
    getReturnData("/ycsl/jyxx/dealDjxx?xmid=" + xmid+"&processInsId="+processInsId, JSON.stringify(paremJson), "POST", function () {
        removeModal();
        refreshQlxx();
        ityzl_SHOW_SUCCESS_LAYER("导入成功。");
    }, function (error) {
        console.info(error);
        removeModal();
        ityzl_SHOW_WARN_LAYER("导入失败。");
    }, false)
}

/**
 * 处理房屋性质名称和用途名称
 */
function initFwxzmcYtmc() {
    if ($('[name="fwxzmc"]').length > 0) {
        $.each($('[name="fwxzmc"]'), function (index, item) {
            var fwxz_code = $(item).parents('div.layui-inline').parent().find('select[name="fwxz"]').val();

            if (fwxz_code == 99) {
                $(item).parents('div.layui-inline').css({
                    'display': 'block'
                });
            } else {
                $(item).parents('div.layui-inline').css({
                    'display': 'none'
                });
            }

        });

    }

    if ($('[name="ytmc"]').length > 0) {
        if($('[name="ytmc"]').parents("tr").length ===0) {
            $.each($('[name="ytmc"]'), function (index, item) {
                var ghyt_code = $(item).parents('div.layui-inline').parent().find('select[name="ghyt"]').val();

                if (ghyt_code == 80) {
                    $(item).parents('div.layui-inline').css({
                        'display': 'block'
                    });
                } else {
                    $(item).parents('div.layui-inline').css({
                        'display': 'none'
                    });
                }

            });
        }else{
            //项目内多幢
            $.each($('[name="ytmc"]'), function (index, item) {
                var ghyt_code = $(item).parents('tr').find('select[name="ghyt"]').val();
                if (ghyt_code == 80) {
                    $(item).parents('tr').find('#ytmc').removeAttr('disabled');
                    $(item).parents('td').find('img').css('display', 'none');
                    $(item).parents('td').removeClass();

                }

            });
        }

    }
}

//加载按钮
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
        // var qlxx =null;
        // getReturnData("/slym/ql/qlmc", {gzlslid: gzlslid, zxlc: zxlc}, 'GET', function (data) {
        //     if (isNotBlank(data)) {
        //         qlxx = data;
        //     }
        // }, function (err) {
        //     console.log(err);
        // }, false);


        // // 不是6的情况要隐藏审核不通过的按钮
        // if(qlxx &&qlxx.length >0 &&qlxx[0].bdcXm != null && qlxx[0].bdcXm.sply!= 6){
        //     setTimeout(function(){$("#shbtg").hide()},200);
        // }

        var form = layui.form;
        var laytpl = layui.laytpl;
        var json = {lclx: lclx, printBtn: slymPrintBtn};
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

        //审核不通过
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
            $(".bdc-tab-box").css("padding-top","76px");
            // 海门
            $(".layui-tab.fixed-content").css("padding-top","76px");
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
    getReturnData("/ycsl/jyxx", {xmid: xmid}, "GET", function (data) {
        if (isNotBlank(data)) {
            htbh = data.htbh;
        }
    }, function (error) {
        console.info(error);
    }, false);
    return htbh;
}
