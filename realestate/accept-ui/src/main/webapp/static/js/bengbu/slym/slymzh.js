
var $, form, layer, element, table, laytpl, laydate;
var sjclids = new Set();
//用于存放所有的收件材料id
var sjclidLists = [];
//页面重新加载清空存储的收件材料id session
sessionStorage.removeItem("yxsjclids");
sessionStorage.removeItem("sjclidLists");
// 审批来源
var sply;
//存放项目id与当前项目可编辑字段集合(主要用于根据登记原因控制字段可编辑)
var xmid_editElementForDjyy = {};
var sfdrjyxx = "0";
// 获取processInstanceType
var processInstanceType = $.getUrlParam('processInstanceType');
var processInsId = getQueryString("processInsId");
var lclx = "zhlc";
var formStateId = getQueryString("formStateId");
var zsmodel = getQueryString("zsmodel");
var readOnly = getQueryString("readOnly");
var isSubmit = true;
var zxlc = getQueryString("zxlc");
var printValue = getQueryString("print");
var taskId = getQueryString("taskId");


//页面入口
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl', 'laydate'], function () {
    form = layui.form;
    $ = layui.jquery;
    layer = layui.layer;
    element = layui.element;
    table = layui.table;
    laytpl = layui.laytpl;
    laydate = layui.laydate;
    //监听面积单位
    form.on('checkbox(mjdw)', function () {
        $("[name='mjdw']").prop("checked", "");
        $(this).prop("checked", "checked");
        form.render('checkbox');
    });
    form.render();
    element.init();
    //监听第一次单击tab，
    var qlIndex = 0;
    element.on('tab(tabFilter)', function (data) {
        var tabid = $(".layui-tab-title .layui-this").attr("id");
        $(this).removeAttr("onclick");
        if (tabid === "qlxx") {
            if (qlIndex === 0 && !sfchangeqljbxxTab) {
                qlIndex++;
                addModel();
                setTimeout(function () {
                    refreshQlxx();
                }, 0);
            }
        }
    });

    //监听回车事件
    $(document).keydown(function (event) {
        if($('.bdc-spf-layer').length > 0){
            var code = event.keyCode;
            if (code == 13) {
                $('.layui-layer-btn0').click();
                return false;
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
            delAjaxErrorMsg(e,"加载失败");
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
        if(sjclids.size >0) {
            var idList = [];
            sjclids.forEach(function (element, sameElement, set) {
                idList.push(sameElement);
            });
            sessionStorage.setItem("yxsjclids",idList);
        } else {
            sessionStorage.setItem("yxsjclids",[]);
        }
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
        if(sjclids.size >0) {
            var idList = [];
            sjclids.forEach(function (element, sameElement, set) {
                idList.push(sameElement);
            });
            sessionStorage.setItem("yxsjclids",idList);
        } else {
            sessionStorage.setItem("yxsjclids",[]);
        }
    });

    //监听抵押方式
    form.on('select(dyfs)', function (data) {
        //抵押方式为最高额抵押时，贷款方式为商业贷款
        if(data.value ==="2"){
            //定位贷款方式字段
            var $dkfs =$(data.elem).parents(".basic-info").find("select[name =dkfs]");
            if($dkfs.length >0){
                $dkfs.val("商业贷款");
                form.render("select");
                resetSelectDisabledCss();
            }
        }
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
                $this_ytmc_outer_dom.find('[name="ytmc"]').val('');
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

    //监听 权利信息 内 附记 单击
    $('.layui-tab-content').on('click','.bdc-pj-popup',function(){
        var $thisTextarea = $(this).siblings('textarea');
        var fjContent = $thisTextarea.val();
        var title = $(this).parents(".layui-form-item").find("label").text();
        layer.open({
            title: isNotBlank(title)? title : '附记',
            type: 1,
            area: ['960px'],
            btn: ['保存'],
            content: $('#fjPopup')
            ,yes: function(index, layero){
                //提交 的回调
                $thisTextarea.val($('#fjPopup textarea').val());
                $('#fjPopup textarea').val('');
                layer.close(index);
            }
            ,btn2: function(index, layero){
                //取消 的回调
                $('#fjPopup textarea').val('');
            }
            ,cancel: function(){
                //右上角关闭回调
                $('#fjPopup textarea').val('');
            }
            ,success: function () {
                $('#fjPopup textarea').val(fjContent);
            }
        });
    });

    //监听宗地面积修改
    $('.bdc-form-div').on('input propertychange','#zdzhmj',function(){
        var $this = $(this);
        var getMj = $this.val();
        $this.parents('.layui-show').find('#jsydsyq-syqmj').val(getMj);
    });
});

var zdList = {a:[]};
var xmid;
var qlList;
var bdcXm;
//是否虚拟号
var isXndyh = false;
var formIds = "";
var qllxforqlr;
var qlxxForPrint;


//加载页面数据（入口）
function loadData() {
    //获取字典
    getCommonZd(function (data) {
        if (isNotBlank(data)) {
            zdList = data;
        }
    });
    loadQlxx();
    // 记录表单第一次加载的业务数据至ES中
    addYwxxLog();
}

//加载基本信息模块（入口）
function loadJbxx() {
    getReturnData("/slym/xm", {processInsId: processInsId}, 'GET', function (data) {
        if (isNotBlank(data)) {
            bdcXm = data;
            xmid = data.xmid;
            sply = data.sply;
            getReturnData("/slym/xm/fb",{xmid:xmid},"GET",function (result) {
                //面积单位为空时默认为平方米
                if (data.mjdw === null || data.mjdw === '') {
                    data.mjdw = '1'
                }
                //判断是否是虚拟单元号
                isXndyh = checkXndyh(data.bdcdyh);
                generateJbxx(data, result);
                //加载收件材料(需要放在基本信息渲染之后,否则可能会导致收件材料加载报错)
                loadSjcl();
                //异步加载sqcltjfs
                querySjcltjfs();
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
        commonJedw:commonJedw,
        xmfb:xmfb
    };
    var tpl = jbxxTpl.innerHTML, view = document.getElementById('sljbXx');
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });
    form.render();
    renderDate(form, formIds);
    renderForm();
    getStateById(readOnly, formStateId, 'slymzh');
    loadHlnr();
    form.render('select');
    disabledAddFa();

    var bdcdyh = bdcxmxx.bdcdyh;
    if (bdcdyh.length > 20 && bdcdyh.substring(19, 20) === "F") {
        loadFwfsss(xmid);

    } else {
        $("#fwfsss").remove();
    }
    renderSelect();
}

//加载权利信息（入口）
function loadQlxx() {
    getReturnData("/slym/ql/zhlc", {processInsId: processInsId, sfcxql: false,zxlc:zxlc}, 'GET', function (data) {
        if (isNotBlank(data)) {
            qlList = data;
            qlxxForPrint = data;
            buildQlxx(data);
            //当此流程登记小类中存在需要高亮显示的登记小类时,查询修改日志
            var isQueryXgLog =false;
            if (isNotBlank(qlList)) {
                for (var i = 0; i < qlList.length; i++) {
                    var djxl = qlList[i].bdcXm.djxl;
                    if (isNotBlank(xgnrglxs) && xgnrglxs.indexOf(djxl) > -1) {
                        isQueryXgLog =true;
                        break;
                    }
                }
            }
            if (isQueryXgLog) {
                // 渲染后权利页面，渲染上一次修改的字段
                queryXgLog();
            }
        }
    }, function (err) {
        console.log(err);
    });
}

//更新权利信息
function refreshQlxx() {
    getReturnData("/slym/ql/zhlc", {processInsId: processInsId, sfcxql: true, qtsj: true,zxlc:zxlc}, 'GET', function (data) {
        removeModal();
        if (isNotBlank(data)) {
            qlList = data;
            generateQlxx(qlList);
            initFwxzmcYtmc();


            //当此流程登记小类中存在需要高亮显示的登记小类时,查询修改日志
            var isQueryXgLog = false;
            if (isNotBlank(qlList)) {
                for (var i = 0; i < qlList.length; i++) {
                    var djxl = qlList[i].bdcXm.djxl;
                    if (isNotBlank(xgnrglxs) && xgnrglxs.indexOf(djxl) > -1) {
                        isQueryXgLog =true;
                        break;
                    }
                }
            }
            if (isQueryXgLog) {
                // 渲染后权利页面，渲染上一次修改的字段
                queryXgLog();
            }
        }
    }, function (err) {
        delAjaxErrorMsg(err);
    });
}

//组织权利页面（主要是权利信息标题与框架，内容暂不加载）
function buildQlxx(bdcSlQlxxymDTOList) {

    var json = {
        bdcSlQlxxymDTOList: bdcSlQlxxymDTOList,
        ygdjhqspfhtxxDjxlList: ygdjhqspfhtxxDjxlList
    };
    json['sfchangeqljbxxTab'] = sfchangeqljbxxTab;
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
    $(".qlxx-btn").click(function () {
        openQlxx(this);
    });
    loadJbxx();
    form.render();
    renderDate(form, formIds);
    //给下拉框添加删除图标
    renderSelectClose(form);
    renderForm();
    getStateById(readOnly, formStateId, 'slymzh');
    form.render('select');
    disabledAddFa();
    //调整一窗iframe高度
    var h = document.body.clientHeight - 130;//可见区域高度
    var ycslym = document.getElementById('ycslbody');
    if (isNotBlank(ycslym)) {
        ycslym.setAttribute('style', 'height:' + h + 'px');
    }
    //是否需要监听修改字段
    for (var i = 0; i < bdcSlQlxxymDTOList.length; i++) {
        var bdcSlQlxxym = bdcSlQlxxymDTOList[i];
        if (isNotBlank(xgnrglxs) && xgnrglxs.indexOf(bdcSlQlxxym.bdcXm.djxl) > -1) {
            if (formIds.indexOf("sljbXx") < 0) {
                formIds += "sljbXx,";
            }
            renderChange("", form, formIds);
        }

    }
    if (sfchangeqljbxxTab) {
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

            var json = {
                bdcSlQlxxym: bdcSlQlxxym,
                index: i + 1,
                zd: zdList,
                showYqlBtn: bdcSlQlxxym.showYqlBtn,
                dkfs: dkfs,
                zxlc: zxlc
            };
            var bdcdyfwlx = "";
            if (isNotBlank(bdcSlQlxxym.bdcQl.bdcdyfwlx) && bdcSlQlxxym.bdcQl.bdcdyfwlx === 1) {
                bdcdyfwlx = bdcSlQlxxym.bdcQl.bdcdyfwlx;
            }
            var qllxTpl = document.getElementById(bdcSlQlxxym.tableName + bdcdyfwlx);
            var tpl = qllxTpl.innerHTML, view = document.getElementById('qllx' + (i + 1));
            laytpl(tpl).render(json, function (html) {
                view.innerHTML = html;
            });

            //截取权利名称_后部分内容，作为区分权利TAB页项目信息权限的标志
            var index = bdcSlQlxxym.tableName.lastIndexOf("\_");
            var qlTableId = bdcSlQlxxym.tableName.substring(index + 1, bdcSlQlxxym.tableName.length);

            // 加载更正信息模块
            if ($('#bdc_gzxx').length > 0) {
                generateGzxx(bdcSlQlxxym, "gzxx" + (i + 1), i + 1);
            }

            //加载单元信息模块
            generateBdcdyZh(bdcSlQlxxym, "bdcdyxx" + (i + 1), i + 1, qlTableId);
            loadQlrZh(bdcSlQlxxym.bdcXm.xmid, "sqr" + (i + 1), i + 1, true,bdcSlQlxxym.dydj);
            generateXmZh(bdcSlQlxxym, "xmxx" + (i + 1), i + 1, qlTableId, bdcSlQlxxym.dydj);
            if (isNotBlank(bdcSlQlxxym.bdcQl.bdcdyfwlx) && bdcSlQlxxym.bdcQl.bdcdyfwlx === 1) {
                generateFdcqxm(bdcSlQlxxym.bdcQl.qlid);
            }

            if(bdcSlQlxxym.tableName ==="bdc_fdcq3") {
                //加载建筑物区分所有权共有信息模块
                getFdcq3Gyxx(bdcSlQlxxym.bdcQl.qlid);

            }

            //加载完信息后，如果查封登记要根据查封类型修改djyy的默认值
            if (qllx === 98) {
                changeDjyyMrzByCflx(bdcSlQlxxym,i+1);
            }
        }
        form.render();
        renderSelect();
        //给下拉框添加删除图标
        renderSelectClose(form);
        renderForm();
        getStateById(readOnly, formStateId, 'slymzh');
        //合肥联系电话加密显示
        toEncryptClass('dhjm');
        //所有业务宗地用途,权利性质为空时可编辑
        changeZdState();
        disabledAddFa();

        //是否需要监听修改字段
        for (var j = 0; j < bdcSlQlxxymDTOList.length; j++) {
            var bdcSlQlxxym = bdcSlQlxxymDTOList[j];
            if (isNotBlank(xgnrglxs) && xgnrglxs.indexOf(bdcSlQlxxym.bdcXm.djxl) > -1) {
                if (formIds.indexOf('qlTab' + (j + 1)) < 0) {
                    formIds += 'qlTab' + (j + 1) + ",";
                }
                renderChange("", form, formIds);
                renderDate(form, formIds);
            }
            //监听实时同步不动产单元信息
            syncBdcdyxx(bdcSlQlxxym, (j + 1), bdcSlQlxxymDTOList);


        }
    }
}

//加载权利人（入口）
//isLoad:是否为页面加载时,默认false
function loadQlrZh(xmid, name, index,isLoad,sfdydj) {
    getReturnData("/slym/qlr/list/xm", {xmid: xmid, lclx: "zhlc"}, 'GET', function (data) {
        generateQlrxx(filterQlrZh(data), xmid, name, index,isLoad,sfdydj);
    }, function (err) {
        console.log(err);
    },false);
}
//1、义务人为企业时：相同名称视为同一企业只保留一个 2、义务人为个人时：按名称+证件号过滤，保留新证件号的
function filterQlrZh(data) {
    if (data.length == 0) {
        return data;
    }
    var filteQlrArray = [];
    //清理相同义务人缓存数据
    var cacheMap = {};
    $.each(data, function (index, val) {
        var bdcQlrDO = val;
        // 人员类别不是义务人时，不进行过滤
        if(bdcQlrDO.qlrlb != "2"){
            filteQlrArray.push(val);
            return true;
        }
        // 对义务人进行数据过滤
        if ("2" == bdcQlrDO.qlrlb) {
            // 义务人类型不为企业和个人时，不过滤
            if(2 != bdcQlrDO.qlrlx && 1 !=bdcQlrDO.qlrlx){
                filteQlrArray.push(val);
                return true;
            }
            // 义务人类型为企业
            if (bdcQlrDO.qlrlx == 2) {
                var isRepeat = bdcQlrDO.qlrmc in cacheMap;
                if(!isRepeat){
                    cacheMap[bdcQlrDO.qlrmc] = val;
                }
            }
            // 义务人类型为个人
            if (bdcQlrDO.qlrlx == 1 && !compareQlrRepeat(bdcQlrDO,cacheMap)) {
                var key = bdcQlrDO.qlrmc + convertIdCard18To15(bdcQlrDO.zjh);
                cacheMap[key] = val;
            }
        }
    });
    // 将去重的人员数据缓存数据添加到过滤数组中
    $.each(cacheMap, function (key, value) {
        filteQlrArray.push(value);
    });
    return filteQlrArray;
}
// 比较义务人是否重复, 重复返回true，不重复返回false
function compareQlrRepeat(bdcQlrDO,cacheMap) {
    // 将18位转15位进行重复校验,若存在则重复，不存在则不重复。
    if(isNotBlank(bdcQlrDO.zjh) &&bdcQlrDO.zjh.length == 18){
        var mapKey15 = bdcQlrDO.qlrmc + convertIdCard18To15(bdcQlrDO.zjh);
        if(!(mapKey15 in cacheMap)){
            return false;
        }
    }else{
        var mapKey = bdcQlrDO.qlrmc + bdcQlrDO.zjh;
        if(!(mapKey in cacheMap)){
            return false;
        }
    }
    return true;
}

//加载权利人（刷新权利人，用于打开新页面后的刷新）
function loadQlr() {
    var index = $("input[name='liIndex']");
    if (index !== null && index.length > 0) {
        for (var i = 0; i < index.length; i++) {
            var c = $(index[i]).val();
            var qlrArray = $(".qlr" + c);
            var sqrFormArray = qlrArray.serializeArray();
            var ywrArray = $(".ywr" + c);
            var ywrFormArray = ywrArray.serializeArray();
            if (sqrFormArray !== null && sqrFormArray.length > 0) {
                var name = "sqr" + (i + 1);
                var xmid = sqrFormArray[0].value;
                loadQlrZh(xmid, name, i + 1,'', null);
                //重新加载抵押人查封机关信息
                reloadDyrAndCfjg(xmid, i + 1);
            } else {
                var name = "sqr" + (i + 1);
                var xmid = $("input[name='xmid']");
                if (xmid !== null && xmid.length > 0) {
                    xmid = $(xmid[i]).val();
                }
                var sqrView =$("." + name);
                if(sqrView != null &&sqrView.length >0) {
                    loadQlrZh(xmid, name, i + 1,'', null);
                    //重新加载抵押人查封机关信息
                    reloadDyrAndCfjg(xmid, i + 1);
                }
            }
            if (ywrFormArray !== null && ywrFormArray.length > 0) {
                var ywrxmid = ywrFormArray[0].value;
                var name = "sqr" + (i + 1);
                var sqrView =$("." + name);
                //判断当前是否存在申请人模块
                if(sqrView != null&&sqrView.length >0) {
                    loadQlrZh(ywrxmid, name, i + 1,'', null);
                }
            }
        }
    }
}

//加载房屋附属设施信息
function loadFwfsss(xmid) {
    getReturnData("/slym/fwfsss/list/xm", {xmid: xmid}, 'GET', function (data) {
        if (data && data.length > 0) {
            generateFwfsss(data);
        } else {
            //没有附属设施，此模块不显示
            $("#fwfsss").remove();

        }
    }, function (err) {
        delAjaxErrorMsg(err);
    });
}

//组织房屋附属设施信息到页面
function generateFwfsss(data) {
    var json = {
        bdcfwfsssDOList: data,
        zd: zdList
    };
    var tpl = fwfsssTpl.innerHTML, view = document.getElementById("fwfsss");
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });

    form.render('select');
    renderForm();
    getStateById(readOnly, formStateId, 'slymzh');
    disabledAddFa();
}

//组织项目信息到组合页面
function generateXmZh(data, name, index, qlTableId,dydj) {
    var xmTpl = document.getElementById("xmxxTpl");
    var form = layui.form;
    data.bdcXm.wlzs = data.wlzs;
    var djlx = data.djlx;
    var json = {
        index: index,
        xmxx: data.bdcXm,
        xmfb: data.bdcXmFbDO,
        djxldjyy: data.bdcDjxlDjyyGxDOList,
        zd: zdList,
        qlTableId: qlTableId,
        zxlc: zxlc,//20191025新增用于页面做注销流程判断
        dydj: dydj
    };
    ydjyy = data.bdcXm.djyy;
    ydjyyArr['djyy' + index] = data.bdcXm.djyy;
    sfchange = false;
    var tpl = xmTpl.innerHTML, view = $("." + name)[0];
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });
    //判断页面是否存在交易信息字段，显示即查询
    if ($(".jyxx" + index).length > 0 && isNotBlank(data.bdcXm.xmid)) {
        getReturnData("/ycsl/jyxx", {xmid: data.bdcXm.xmid}, "GET", function (data) {
            //数据库查询到交易信息数据以数据库为准
            if (data) {
                //表单赋值
                form.val("xmForm" + index, data);
            }
            renderDate(form, formIds);
            resetSelectDisabledCss();
        }, function (error) {
            delAjaxErrorMsg(error);
        });
    }

}

//组织单元信息到组合页面
function generateBdcdyZh(bdcSlQlxxym, name, index, qlTableId) {
    var bdcdyxxTpl = document.getElementById("bdcdyxxTpl");
    var json = {
        index: index,
        xmxx: bdcSlQlxxym.bdcXm,
        xmfb: bdcSlQlxxym.bdcXmFbDO,
        zd: zdList,
        qlTableId: qlTableId
    };
    var tpl = bdcdyxxTpl.innerHTML, view = $("." + name)[0];

    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });
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

function loadSjcl() {
    getReturnData("/slym/sjcl/list/pl", {processInsId: processInsId}, 'GET', function (data) {
        generateSjcl(data, "");
    }, function (err) {
        console.log(err);
    });
}

//组织权利人数据到页面
function generateQlrxx(data, xmid, name, index,isLoad,sfdydj) {
    if (sfdydj ===null) {
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
    form.on('select(zjzl)', function (data) {
        changeqlrlxByZjzl(data);
    })
    form.on('select(qlrlx)', function (data) {
        changeZjzlByQlrlx(data);
    })
    addzjhYz();
    //页面加载此处不用处理权限,后续统一处理
    if(isLoad == null ||!isLoad){
        renderDate(form, formIds);
        form.render();
        renderForm();
        getStateById(readOnly, formStateId, "slymzh", 'qllx' + (index));
        //合肥联系电话加密显示
        toEncryptClass('dhjm');
        //重新渲染select框
        form.render('select');
        disabledAddFa("sqrForm" + index);
    }

}

var sjclNumber = 0;

//组织收件材料到页面
function generateSjcl(data, xmid) {
    sjclNumber = data.length;
    sjclidLists = [];
    if(data !== null && data.length >0) {
        for(var i=0;i<data.length;i++) {
            sjclidLists.push(data[i].sjclid);
        }
    }
    sessionStorage.setItem("sjclidLists",sjclidLists);
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
    renderForm();
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
    renderForm();
    getStateById(readOnly, formStateId, 'slymzh');
    form.render('select');
    disabledAddFa();
}

/**
 *  设置建筑物区分所有权业主共有部分登记信息_共有部分信息内容
 */
function getFdcq3Gyxx(qlid){
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
        renderForm();
        getStateById(readOnly, formStateId, 'slymzh');
        disabledAddFa();

    }, function (err) {
        console.log(err);
    });

}

//---------------------xq.js整理--------------------------------------
//权利信息详情（受理组合页面）---点击详细信息按钮
function openQlxx(element) {
    var qllxym;
    var tableName = $(".layui-this").find("input[name='tableName']")[0];
    var qllx = $(tableName).val();
    if (qllx == "bdc_fdcq") {
        var bdcdyfwlxInput = $(element).parents(".basic-info").find("input[name='bdcdyfwlx']")[0];
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
        "bdc_fdcq3":"jzwqfsyqyzgybf",
        "bdc_jzq":"jzq",
        "bdc_tdsyq":"tdsyq",
        "bdc_hysyq":"hysyq"
    };
    if (qllxArr[qllx] != undefined) {
        qllxym = qllxArr[qllx];
    }
    var xmidInput = $(element).parents(".basic-info").find("input[name='xmid']")[0];
    var xmid = $(xmidInput).val();
    var url = "/realestate-register-ui/view/qlxx/" + qllxym + ".html?isCrossOri=false&xmid=" + xmid + "&formStateId=" + formStateId + "&readOnly=" + readOnly +"&processInstanceType="+processInstanceType;
    layerOpenFull(url, "权利信息");
}

// 点击家庭成员按钮打开家庭成员
function showJtcy() {
    var djh = $('#bdcdyh').val().substring(0, 19);
    var url = "/building-ui/zdjtcy/view?djh=" + djh;
    layerCommonOpenFull(url, "家庭成员");
}

//新增权利人展示  -----------------点击新增申请人
function addQlr(qllx, dydj, elem) {
    var xmidArray = $(".layui-this").find("input[name='xmid']");
    var xmidInput = xmidArray[0];
    var addXmid = $(xmidInput).val();
    if (!isNotBlank(addXmid)) {
        addXmid = xmid;
    }
    var djxl = $(elem).parents(".layui-show").find("input[name='djxl']").val();
    var url = getContextPath() + "/view/slym/qlr.html?xmid=" + addXmid + "&lclx=" + lclx + "&processInsId=" + processInsId + "&djxl=" + djxl + "&formStateId=" + formStateId + "&qllx=" + qllx + "&zxlc=" + zxlc + "&sfdrjyxx=" + sfdrjyxx;
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

//权利人详情展示 -----点击 详细 按钮
function showQlr(qlrid, xmid, qlrlb, elem) {
    addModel();
    var $this = $(".layui-this");
    var qllx = $($this.find("input[name='qllx']")[0]).val();
    var dydj = $($this.find("input[name='dydj']")[0]).val();
    // 获取当前元素所在的tab页面，通过tab页找到权利信息下的djxl信息
    var djxl = $(elem).parents(".layui-show").find("input[name='djxl']").val();
    var title = '';
    var url = getContextPath() + "/view/slym/qlr.html?processInsId=" + processInsId + "&qlrid=" + qlrid + "&lclx=" + lclx + "&xmid=" + xmid + "&formStateId=" + formStateId + "&qllx=" + qllx + "&readOnly=" + readOnly + "&zxlc=" + zxlc +"&djxl="+djxl + "&sfdrjyxx="+sfdrjyxx;
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
            if(bdcXm != null) {
                showQl(bdcXm.xmid, bdcXm.qllx + "", bdcXm.bdcdyfwlx, true);
            }else{
                ityzl_SHOW_INFO_LAYER("无原权利信息");
            }
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
            url = "/realestate-register-ui/view/qlxx/" + qllxym + ".html?xmid=" + xmid + "&formStateId=" + formStateId + "&readOnly=" + readOnly + "&isCrossOri=false" + "&processInstanceType=" + processInstanceType;
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
                if(!sfyql){
                    refreshQlxx();

                }

            }
        });
        layer.full(index);
    } else {
        ityzl_SHOW_INFO_LAYER("无原权利信息")
    }
}

//查看权籍附件
function showLpbFj(bdcdyh) {
    if (!isNotBlank(bdcdyh)) {
        if(bdcXm != null &&isNotBlank(bdcXm.bdcdyh)){
            bdcdyh =bdcXm.bdcdyh;

        }else {
            bdcdyh = $("#bdcdyh").val();
        }
    }
    var qjgldm ="";
    if(qlList.length >0 &&qlList[0].bdcXmFbDO != null){
        qjgldm =qlList[0].bdcXmFbDO.qjgldm;
    }
    getReturnData("/rest/v1.0/slym/lpbFjUrl",{bdcdyh:bdcdyh,qjgldm:qjgldm},"GET",function (data) {
        var index = layer.open({
            type: 2,
            title: "权籍附件",
            area: ['1150px', '600px'],
            fixed: false, //不固定
            maxmin: true, //开启最大化最小化按钮
            content: data
        });
        layer.full(index);
    },function (error) {
        delAjaxErrorMsg(error);

    });
}

//组织参数调用评价器
function evaluate() {
    getReturnData("/pjq", {gzlslid: processInsId}, "GET", function (data) {
        if (data !== null) {
            pj(data);
        }
    }, function (err) {
        removeModal();
        delAjaxErrorMsg(err);
    })
}

//人证对比
function rzdb() {
    var slbh = $("#sjbh").val();
    Face(slbh);
}
//记录由EMS推送
function markEmsPush(){
    getReturnData("/slym/yjxx/mark", {
        gzlslid : processInsId,
        slbh : $("#sjbh").val()
    }, 'GET', function (data) {
        console.info(data);
        ityzl_SHOW_SUCCESS_LAYER(data);
    }, function (err) {
        throw err;
    });
}

//同步一窗信息
function syncYcsl(xmid){
    addModel();
    getReturnData("/slxxcsh/syncYcslxx", {
        gzlslid : processInsId,
        xmid:xmid
    }, 'GET', function (data) {
        removeModal();
        ityzl_SHOW_SUCCESS_LAYER("操作成功");
    }, function (err) {
        delAjaxErrorMsg(err);
    });
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
    var djxl = bdcSlQlxxym.bdcXm.djxl;
    //不动产单元信息同步
    var $bdcdyxx = $("#bdcdyxxForm" + index).find(".xmxx" + index);
    $bdcdyxx.on('change', function () {
        var id = $(this)[0].id;
        var value = $(this).val();
        //定着物面积与建筑面积同步
        if (id === "dzwmj") {
            $("#qllx" + (index)).find("input[name=jzmj]").val(value);
        }
        //循环同步其他TAB权利页数据
        for (var i = 0; i < bdcSlQlxxymDTOList.length; i++) {
            var dyh = bdcSlQlxxymDTOList[i].bdcXm.bdcdyh;
            if ((i + 1) !== index && bdcdyh === dyh) {
                $("#bdcdyxxForm" + (i + 1)).find("#" + id).val(value);
                //定着物面积与建筑面积同步
                if (id === "dzwmj") {
                    $("#qllx" + (i + 1)).find("input[name=jzmj]").val(value);
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
            if (id === "dzwyt" && $("#qllx" + (index)).find("select[name=ghyt]").length !==0&& $("#qllx" + (index)).find("select[name=ghyt]").parent().get(0).tagName != 'TD') {
                $("#qllx" + (index)).find("select[name=ghyt]").val(value);
            }

            //循环同步其他TAB权利页数据
            for (var i = 0; i < bdcSlQlxxymDTOList.length; i++) {
                var dyh = bdcSlQlxxymDTOList[i].bdcXm.bdcdyh;
                if (bdcdyh === dyh) {
                    $("#bdcdyxxForm" + (i + 1)).find("#" + id).val(value);
                    //定着物用途与规划用途同步
                    if (id === "dzwyt" && $("#qllx" + (i + 1)).find("select[name=ghyt]").length !==0&& $("#qllx" + (i + 1)).find("select[name=ghyt]").parent().get(0).tagName != 'TD') {
                        $("#qllx" + (i + 1)).find("select[name=ghyt]").val(value);
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
                        $("#qllx" + (i + 1)).find("select[name=ghyt]").val(value);
                    }
                }
            }
            form.render("select");
            resetSelectDisabledCss();


        }
        //监听修改内容
        if (isNotBlank(xgnrglxs) && xgnrglxs.indexOf(djxl) > -1) {
            $(data.elem.parentElement).addClass('bdc-change-input');
            var text = $(data.elem).parents(".layui-inline").find("label").text();
            renderChangeTips(text);
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


}

function countSyqxByMonth() {
    var ydyqssj = $("#yg-zwlxqssj").val();
    var dyaqssj = $("#dyaq-zwlxqssj").val();
    var syqx = parseInt($(".zwlxqx").val());
    if (syqx > 0) {
        //计算结束时间后的日期 日 要减一天
        if (isNotBlank(ydyqssj)) {
            var qssj = new Date(ydyqssj);
            qssj.setMonth(qssj.getMonth() + syqx);
            var time = qssj.getTime()-1000*60*60*24;
            console.log(Format(formatChinaTime(new Date(time)), "yyyy-MM-dd"));
            $("#yg-zwlxjssj").val(Format(formatChinaTime(new Date(time)), "yyyy-MM-dd"));
        } else if (isNotBlank(dyaqssj)) {
            var qssj = new Date(dyaqssj);
            qssj.setMonth(qssj.getMonth() + syqx);
            var time = qssj.getTime()-1000*60*60*24;
            console.log(Format(formatChinaTime(qssj), "yyyy-MM-dd"));
            $("#dyaq-zwlxjssj").val(Format(formatChinaTime(new Date(time)), "yyyy-MM-dd"));
        }
    }
}

/**
 * 根据登记原因控制页面权限初始化方法
 */
function initAuthorityByDjyy(djyyVal,djyyId,eventlx,type){
    changeAuthorityByDjyy(djyyVal,djyyId,eventlx,type);
}
/**
 * 根据登记原因控制页面权限
 */
function changeAuthorityByDjyy(djyyVal,djyyId,eventlx,type){
    var liindex = djyyId.substring(4,5);
    //获取当前项目ID
    var xmid =$("#qlTab"+liindex).find("[name=xmid]").val();
    if(eventlx ==="change"&&xmid_editElementForDjyy &&isNotBlank(xmid_editElementForDjyy[xmid])){
        var editElement =xmid_editElementForDjyy[xmid];
        //设置原有元素不可编辑
        $.each(editElement.split(","), function (index, elementName) {
            //设置不可编辑
            var $element =$("#qlTab"+liindex).find("[name='"+elementName+"']");
            if($element.length >0) {
                $element.attr("disabled", "off");
                var tagName = $element[0].tagName;
                if (tagName === "SELECT") {
                    form.render("select");
                    resetSelectDisabledCss();
                }
                //加锁
                var imgArray = $element.parent().find("img");
                if (imgArray.length === 0) {
                    $element.parent().append('<img src="../../static/lib/bdcui/images/lock.png" alt="">');
                }
            }
        });
    }
    if(isNotBlank(djyyVal)){
        getReturnData("/slym/xm/getAuthorityByDjyy",{djyy:djyyVal},"GET",function (data) {
            if(isNotBlank(data)){
                xmid_editElementForDjyy[xmid]=data;
                $.each(data.split(","), function (index, elementName) {
                    //设置可编辑
                    var $element =$("#qlTab"+liindex).find("[name='"+elementName+"']");
                    if($element.length >0){
                        var tagName =$element[0].tagName;
                        $element.parents(".layui-input-inline").removeClass("bdc-one-icon");
                        $element.siblings('img').remove();
                        $element.removeAttr("disabled");
                        if(tagName ==="SELECT"){
                            form.render("select");
                            resetSelectDisabledCss();
                        }
                    }
                });
            }
        },function (error) {
            delAjaxErrorMsg(error);
        });
    }
    //页面需要监听登记原因修改时,两边监听会冲突，故在此加上监听改变
    //获取登记小类值
    var $djxlelement =$("#qlTab"+liindex).find("[name='djxl']");
    if($djxlelement.length >0 &&eventlx ==="change"){
        var djxl =$djxlelement.val();
        if (isNotBlank(xgnrglxs) && xgnrglxs.indexOf(djxl) > -1) {
            var $select = $("#qlTab" + liindex).find("select[name='djyy"+liindex+"']");
            if ($select.length > 0) {
                $select.parents(".layui-input-inline").addClass('bdc-change-input');
                var text = $select.parents(".layui-inline").find("label").text();
                renderChangeTips(text);
            }
        }
    }
}

/**
 * 根据登记原因判断特定字段必填方法
 */
function initAndchangeCheckJyjgByDjyy(djyy,xmselect) {
    if(isNotBlank(xmselect)) {
        var liindex = xmselect.substring(4, 5);
        if(colIdAndDjyyValMap){
            for(var key in colIdAndDjyyValMap){
                var $item =$("#qlTab"+liindex).find("[name=" + key + "]");
                //当登记原因为xxx，xxx字段验证必填
                if ($item.length > 0) {
                    var djyys = colIdAndDjyyValMap[key];
                    if (isNotBlank(djyy) && (djyys.indexOf(djyy) > -1)) {
                        addRequired($item);
                    } else {
                        removeRequired($item);
                    }
                }
            }
        }
    }
}

//宗地用途、权利性质这两个字段为空，则允许编辑。
function changeZdState(){
    var $zdyt = $('.bdc-zdzhyt');
    var $qlxz = $('.bdc-qlxz');
    $zdyt.each(function(i){
        if($($zdyt[i]).val() == ''){
            $($zdyt[i]).removeAttr('disabled');
            $($zdyt[i]).parent().removeClass('bdc-date-disabled');
            $($zdyt[i]).siblings('img').remove();
        }
        form.render();
    });
    $qlxz.each(function(i){
        if($($qlxz[i]).val() == ''){
            $($qlxz[i]).removeAttr('disabled');
            $($qlxz[i]).parent().removeClass('bdc-date-disabled');
            $($qlxz[i]).siblings('img').remove();
        }
        form.render();
    });
}

//楼盘表
function lpb(bdcdyh,qjgldm) {
    addModel();
    getReturnData("/rest/v1.0/slym/fwbdcdy",{bdcdyh:bdcdyh,bdcdyfwlx:4,qjgldm:qjgldm},"GET",function (data) {
        if(data &&isNotBlank(data.fwDcbIndex)) {
            var index = layer.open({
                type: 2,
                title: "楼盘表",
                area: ['1150px', '600px'],
                fixed: false, //不固定
                maxmin: true, //开启最大化最小化按钮
                content: "/building-ui/lpb/view?code=getbdcdyh&fwDcbIndex="+data.fwDcbIndex+ "&gzlslid=" + processInsId+"&qjgldm="+qjgldm
            });
            layer.full(index);
        }else{
            showAlertDialog("未找到楼盘表");
        }
        removeModal();
    },function (error) {
        delAjaxErrorMsg(error)
    })
}

//根据审批来源控制按钮隐藏
function setSplyBtnAttr(){
    //互联网
    if(sply ===3 || sply ===5 || sply ===11){
        // 涉税流程按钮不可编辑
        $('.sslc').css({'color':'#666','pointer-events':'none'});
    }
    //互联网涉税
    if(sply ===5 || sply ===11){
        //获取交易信息不可编辑
        $(".queryJyxx").removeAttr("onclick");
        $('.queryJyxx').css({'color':'#666','pointer-events':'none'});
        $('.queryJyxx').attr("disabled", "off");
    }
}

//对表单的一系列渲染操作
function renderForm(){
    //表单权限控制
    setSplyBtnAttr();
}

/**
 * 根据登记原因控制页面权限初始化方法
 */
function initSfdrjyxx(val,id,eventlx,type){
    console.log("当前事件类型为"+eventlx);
    if(sfdrjyxx == "1" && id ){
        if($("#"+id)[0].type == "button"){
            $("#"+id).hide();
        }else{
            $("#"+id).attr('disabled', 'disabled');
        }
        disabledAddFa();
    }
}

/**
 * 根据cflx修改djyy的默认值
 */
function changeDjyyMrzByCflx(data,index){
    // 查封类型是轮候查封
    if(data.bdcQl.cflx === 2){
        if($("#djyy"+index) &&  $("#djyy"+index).find('option[value="轮候查封"]').length > 0 ){
            $("#djyy"+index).find('option[value="轮候查封"]').attr('selected', 'selected');
        }
    }
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

// 加载忽略内容
function loadHlnr() {
    if($("#hlnr").length > 0) {
        getReturnData("/slym/xm/hlxx", {
            slbh: $("#sjbh").val()
        }, 'GET', function (data) {
            var hlnr = "";
            for (var i = 0; i < data.length; i++) {
                if (data[i].indexOf('"') == -1) {
                    var array = data[i].split(':');
                    if(isNotBlank(array[array.length - 1])){
                        var cznr = array[array.length - 1].split(" ");
                        hlnr = hlnr + cznr[cznr.length - 1] || "" + "\r\n";
                    }
                } else {
                    var array = data[i].split('"');
                    if (isNotBlank(array[1])) {
                        hlnr = hlnr + array[1].split(" ")[0] || "" + "\r\n";
                    }
                }
            }
            $("#hlnr").val(hlnr);
        }, function (err) {
            throw err;
        });
    }
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

        var form = layui.form;
        var laytpl = layui.laytpl;
        var json = {lclx: lclx, qlxx: qlxx, printBtn: slymPrintBtn};
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
        //推送省直房改办
        $("#tsSzfgb").click(function () {
            tsSzfgb();
        });
        // 通知外网缴费
        $("#tswwjf").click(function () {
            tswwjf();
        });

        //同步权籍数据
        $("#synchLpbData").click(function () {
            isConfirm("synchLpbData","是否确认同步权籍数据？","synchLpbDataToLc")
        });
        //查看权籍附件
        $("#showLpbFj").click(function () {
            showLpbFj("");
        });

        //评价器
        $("#evaluate").click(function () {
            evaluate();
        });

        //人证对比
        $("#rzdb").click(function () {
            rzdb();
        });

        //是否由EMS推送
        $("#emsPush").click(function () {
            markEmsPush();
        });


        //蚌埠好差评
        $("#bbpjqhcp").click(function () {
            getBbPjqHcp();
        });



    });
}