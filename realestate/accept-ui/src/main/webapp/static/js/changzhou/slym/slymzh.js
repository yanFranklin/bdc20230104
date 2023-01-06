
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
var xmid_editElementForDjyy ={};
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

    // filter:moni-select用于监听select并修改对应的另一个select的值
    form.on('select(moni-select)', function(data){
        // 改变对应的select
        if (data.value == 1) {
            $(this).parents("tr.for-bind").find("select[name='qlrlx']").val(1);
            form.render();
        } else if (data.value == 7) {
            $(this).parents("tr.for-bind").find("select[name='qlrlx']").val(2);
            form.render();
        }

    });

    //查封机关
    form.on('select(cfjgselect)', function (data) {   //选择移交单位 赋值给input框
        $(this).parents('.layui-input-inline').find("input[name='cfjg']").val(data.value);
        $(this).parents('.layui-input-inline').find("dl").css({"display": "none"});
        form.render();
        resetSelectDisabledCss();
    });


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
    element.on('tab(tabFilter)', function (data) {
        if ($(this).hasClass('bdc-first-click')) {
            $(this).removeClass('bdc-first-click');

            var tabid = $(".layui-tab-title .layui-this").attr("id");
            $(this).removeAttr("onclick");

            if (tabid === "qlxx" && !sfchangeqljbxxTab) {
                addModel();
                setTimeout(function () {

                    refreshQlxx();
                }, 0);
            }else if(tabid === "ssxx"){
                addModel();
                setTimeout(function () {
                    loadSsxx();
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

            })
        } catch (e) {
            removeModal();
            delAjaxErrorMsg(e,"加载失败");
            return
        }
    }, 10);

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
        if (data.value === "2") {
            //定位贷款方式字段
            var $dkfs = $(data.elem).parents(".basic-info").find("select[name =dkfs]");
            if ($dkfs.length > 0) {
                $dkfs.val("商业贷款");
                form.render("select");
                resetSelectDisabledCss();
            }
        }
        //抵押方式为一般抵押，币种默认为人名币
        if (data.value === "1") {
            var $biz = $(data.elem).parents().find("select[name =biz]");
            if ($biz.length > 0) {
                $biz.val("1");
                form.render("select");
                resetSelectDisabledCss();
            }
        }
    });

    //监听小区名称,小区代码赋值
    form.on('select(xqmc)', function (data) {
        $("#xqdm").val(changeMcToDm(data.value,zdList.xqxx));
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

    //监听家庭成员 详细 单击
    $('.layui-tab-content').on('click','[name="jtcyxx"]',function(){
        var $qlrbasic =$(this).parents("tr");
        var sqrid=$qlrbasic.find("input[name='sqrid']").val();
        var sqrlb=$qlrbasic.find("input[name='sqrlb']").val();
        var hyzk =$qlrbasic.find("[name='hyzk']").val();
        var xmid  = $(this).data("xmid");
        if(hyzk == "已婚"){
            openJtcyYm(sqrid,sqrlb, xmid);
        }else{
            ityzl_SHOW_WARN_LAYER("无配偶子女信息，请检查婚姻状况！");
        }
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
var djlx;
//加载页面数据（入口）
function loadData() {
    //获取字典
    getCommonZd(function (data) {
        if (isNotBlank(data)) {
            zdList = data;
        }
    });
    loadQlxx();
}

//加载基本信息模块（入口）
function loadJbxx() {
    getReturnData("/slym/xm", {processInsId: processInsId}, 'GET', function (data) {
        if (isNotBlank(data)) {
            bdcXm = data;
            xmid = data.xmid;
            sply = data.sply;
            getReturnData("/slym/xm/fb",{xmid:xmid},"GET",function (result) {
                generateJbxxTab(data,result,true);
            },function (xhr) {
                delAjaxErrorMsg(xhr);
            });
        }
    }, function (err) {
        console.log(err);
    });
}

//加载基本信息TAB
//refresh:是否为刷新操作
function generateJbxxTab(xmxx,xmfb,refresh) {
    bdcXm = xmxx;
    xmid = xmxx.xmid;
    sply = xmxx.sply;
    //判断是否是虚拟单元号
    isXndyh = checkXndyh(xmxx.bdcdyh);
    generateJbxx(xmxx,xmfb,refresh);
    //加载收件材料(需要放在基本信息渲染之后,否则可能会导致收件材料加载报错)
    loadSjcl();
    
}

//组织基本信息到页面
function generateJbxx(bdcxmxx,xmfb,refresh) {
    //面积单位为空时默认为平方米
    if (bdcxmxx.mjdw === null || bdcxmxx.mjdw === '') {
        bdcxmxx.mjdw = '1'
    }
    //常州居住权获取设立方式，默认为0
    var jzq;
    var slfs = 0;
    for(var i = 0; i< qlList.length; i++){
        if (qlList[i].qllx === 92){
            jzq = getSlfs(qlList[i].bdcXm.xmid);
            slfs = jzq.slfs;
            break;
        }
    }

    //常州获取邮寄信息渲染到页面
    var yjxx = getYjxx();
    var cfbh =getCfbh(bdcxmxx.xmid,bdcxmxx.qllx);
    var json = {
        bdcxmxx: bdcxmxx,
        zd: zdList,
        yjxx: yjxx,
        slfs: slfs,
        xmfb:xmfb,
        cfbh:cfbh
    };
    var tpl = jbxxTpl.innerHTML, view = document.getElementById('sljbXx');
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });
    if(refresh) {
        form.render();
        renderDate(form, formIds);
        renderForm();
        getStateById(readOnly, formStateId, 'slymzh');
        form.render('select');
        disabledAddFa();
        renderSelect();
    }
}

//加载权利信息（入口）
function loadQlxx() {
    //权利信息在前,sfcxql为true,默认为false
    var sfcxql =false;
    if(sfchangeqljbxxTab){
        sfcxql =true;
    }
    //权利优先展示,权利信息无需查询两次
    getReturnData("/slym/ql/zhlc", {
        processInsId: processInsId,
        sfcxql: sfcxql,
        dsqlr: true,
        cxbdcdyxsql: true,
        cxCfxx: true,
        cxsdq:true,
        zxlc:zxlc,
        cxYchsCqxx:true
    }, 'GET', function (data) {
        if (isNotBlank(data)) {
            qlList = data;
            sply = data[0].bdcXm.sply;
            xmid = data[0].bdcXm.xmid;
            xmlx = qlList[0].xmlx;
            buildQlxx(data);
            if(sfchangeqljbxxTab){
                generateQlxx(qlList);
            }

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
        removeModal();
    }, function (err) {
        console.log(err);
    });
}




//更新权利信息
function refreshQlxx() {
    getReturnData("/slym/ql/zhlc", {
        processInsId: processInsId,
        sfcxql: true,
        dsqlr: true,
        cxbdcdyxsql: true,
        cxCfxx: true,
        cxsdq:true,
        zxlc:zxlc,
        cxYchsCqxx:true
    }, 'GET', function (data) {
        removeModal();
        if (isNotBlank(data)) {
            qlList = data;
            generateQlxx(qlList);
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
        sfchangeqljbxxTab:sfchangeqljbxxTab,
        sply:sply
    };
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

    generateJbxxTab(bdcSlQlxxymDTOList[0].bdcXm,bdcSlQlxxymDTOList[0].bdcXmFbDO,false);

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

            var xtjgpzList = [];
            if (qllx === 98) {
                xtjgpzList = listBdcXtJg(2);
            }
            var ghxx = new Map();
            // 房地产权增加过户信息模块
            if (bdcSlQlxxym.bdcXm.qllx == "4" || bdcSlQlxxym.bdcXm.qllx == "6" || bdcSlQlxxym.bdcXm.qllx == "8") {
                if(bdcSlQlxxym.bdcSdqghDOList){
                    $.each(bdcSlQlxxym.bdcSdqghDOList, function(index, value){
                        ghxx.set(value.ywlx, value.sfbl);
                    });
                }
                //获取签字信息
                getQzxx(bdcSlQlxxym.bdcXm.xmid);
            }
            var json = {
                bdcSlQlxxym: bdcSlQlxxym,
                index: i + 1,
                zd: zdList,
                showYqlBtn: bdcSlQlxxym.showYqlBtn,
                dkfs: dkfs,
                zxlc: zxlc,
                ghxx: ghxx,
                djxldjyy: bdcSlQlxxym.bdcDjxlDjyyGxDOList,
                xtjgpz: xtjgpzList
            };
            var bdcdyfwlx = "";
            if (bdcSlQlxxym.bdcQl && isNotBlank(bdcSlQlxxym.bdcQl.bdcdyfwlx) && bdcSlQlxxym.bdcQl.bdcdyfwlx === 1) {
                bdcdyfwlx = bdcSlQlxxym.bdcQl.bdcdyfwlx;
            }
            var qllxTpl = document.getElementById(bdcSlQlxxym.tableName + bdcdyfwlx);
            var tpl = qllxTpl.innerHTML, view = document.getElementById('qllx' + (i + 1));
            laytpl(tpl).render(json, function (html) {
                view.innerHTML = html;
            });
            form.render();
            //卡顿问题在获取权利类型后校验一次只读信息
            getStateById(readOnly, formStateId, 'slymzh');
            form.render('select');
            disabledAddFa();
            renderSelect();

            if (qllx === 98) {
                var bdcSlQlxxym = json.bdcSlQlxxym;
                if(bdcSlQlxxym.cqXmList &&bdcSlQlxxym.cqXmList.length >0) {
                    bdcSlQlxxym.cqXmList[0].zdzhyt = changeDmToMc(bdcSlQlxxym.cqXmList[0].zdzhyt, zdList.tdyt);
                    bdcSlQlxxym.cqXmList[0].dzwyt = changeDmToMc(bdcSlQlxxym.cqXmList[0].dzwyt, zdList.fwyt);
                    bdcSlQlxxym.cqXmList[0].qllx = changeDmToMc(bdcSlQlxxym.cqXmList[0].qllx, zdList.qllx);
                    bdcSlQlxxym.cqXmList[0].qlxz = changeDmToMc(bdcSlQlxxym.cqXmList[0].qlxz, zdList.qlxz);
                    bdcSlQlxxym.cqXmList[0].bdcdyhxszt = getBdcdyZt(bdcSlQlxxym.cqXmList[0].bdcdyh, "");
                }
                var cfbdcdyTpl = document.getElementById("cfBdcdyTpl");
                var cfdytpl = cfbdcdyTpl.innerHTML, cfdyview = $(".cf_bdcdyh")[0];
                if (cfdyview) {
                    laytpl(cfdytpl).render(json, function (html) {
                        cfdyview.innerHTML = html;
                    });
                }
                var cfqlzkTpl = document.getElementById("cfqlzkTpl");
                var cfqltpl = cfqlzkTpl.innerHTML, cfqlview = $(".cf_qlzk")[0];
                if (cfqlview) {
                    laytpl(cfqltpl).render(json, function (html) {
                        cfqlview.innerHTML = html;
                    });
                }

            }

            //截取权利名称_后部分内容，作为区分权利TAB页项目信息权限的标志
            var index = bdcSlQlxxym.tableName.lastIndexOf("\_");
            var qlTableId = bdcSlQlxxym.tableName.substring(index + 1, bdcSlQlxxym.tableName.length);
            if(isNotBlank(bdcSlQlxxym.bdcXm.jyhth)){
                //加载合同信息模块前先校验出只读信息
                getStateById(readOnly, formStateId, 'slymzh');
                form.render('select');
                disabledAddFa();
                //加载合同信息模块
                generateHtxxZh(bdcSlQlxxym.bdcXm, "bdchtxx" + (i + 1), i + 1, qlTableId);
            }
            var bdcdyh = bdcSlQlxxym.bdcXm.bdcdyh;
            if (bdcdyh.length > 20 && bdcdyh.substring(19, 20) === "F") {
                loadFwfsss(bdcSlQlxxym.bdcXm.xmid);

            } else {
                $("#fwfsss").remove();
            }
            loadQlrZh(bdcSlQlxxym.bdcXm.xmid, "sqr" + (i + 1), i + 1, true, bdcSlQlxxym.dydj);
            generateXmZh(bdcSlQlxxym, "xmzh" + (i + 1), i + 1, qlTableId, bdcSlQlxxym.bdcXmFbDO);
            loadZdjbxx(bdcSlQlxxym.bdcXm.bdcdyh);
            //加载第三权利人
            generateDsQlr(bdcSlQlxxym.bdcXm.xmid, $("#" +bdcSlQlxxym.tableName + (i + 1)), i + 1,bdcSlQlxxym.bdcDsQlrDOList);
            if (isNotBlank(bdcSlQlxxym.bdcQl.bdcdyfwlx) && bdcSlQlxxym.bdcQl.bdcdyfwlx === 1) {
                generateFdcqxm(bdcSlQlxxym.bdcQl.qlid);
            }
            if(bdcSlQlxxym.tableName ==="bdc_fdcq3") {
                //加载建筑物区分所有权共有信息模块
                getFdcq3Gyxx(bdcSlQlxxym.bdcQl.qlid);

            }
            if(qllx==parseInt(commonDyaq_qllx)){
                var djyy = 'djyy'+(i+1);
                //监听登记原因
                form.on('select('+djyy+')', function (data) {

                    getReturnData("/rest/v1.0/djyyDyfsGx/list", JSON.stringify({djyy:data.value}), 'POST', function (data) {
                        if (isNotBlank(data)) {
                            var dyfs =$("#dyaq-dyfs");
                            if(dyfs.length >0){
                                dyfs.val(data.dyfs);
                                form.render("select");
                                resetSelectDisabledCss();
                            }
                        }

                    }, function (err) {
                        delAjaxErrorMsg(err);
                    }, false);
                });
            }

        }
        form.render();
        renderSelect();
        //给下拉框添加删除图标
        renderSelectClose(form);
        renderForm();
        renderDate();
        getStateById(readOnly, formStateId, 'slymzh');
        form.render("select");
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
        // 房地产权中交易合同号字段位置处理
        if($('.layui-show #fdcq-jyhth') && $('.layui-show #fdcq-jyhth').length > 0){
            $('.layui-show #jyhth').parents('.layui-inline').remove();
        }
        removeModal();

        //点击表格中的更多
        $('.bdc-table-box').on('click', '.bdc-more-btn', function (event) {
            tableTop = $(this).offset().top;
            tableLeft = $(this).offset().left;
            scrollTop = $(document).scrollTop();
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

    }
}

//获取签字信息
function getQzxx(xmid) {
    if(isNullOrEmpty(xmid)) {
        return;
    }
    getReturnData("/pjq/qznrxx",{xmid:xmid},"GET",function (data) {
        if(data) {
            $("#qlrqztp").attr("src", data[1]);
            $("#ywrqztp").attr("src", data[3]);
        }

    },function (error) {
        delAjaxErrorMsg(error);

    });

}

//加载一窗税收信息
function loadSsxx() {
    getReturnData( "/ycsl", {gzlslid: processInsId, xmid: xmid}, 'GET', function (data) {
        removeModal();
        if(isNotBlank(data)){
            generateSsxxZh(data);
        }
    }, function (err) {
        console.log(err);
    });
}
// 组织一窗税收信息到组合页面
function generateSsxxZh(data) {
    // 常州只会有一个核税信息，此处合并所有税务信息，默认取第一个
    var hsxxList = [];
    if(null != data.bdcZrfSwxxList){
        hsxxList = hsxxList.concat(data.bdcZrfSwxxList);
    }
    if(null != data.bdcZrfSwxxList){
        hsxxList = hsxxList.concat(data.bdcZcfSwxxList);
    }
    var hsxx = {};
    if(hsxxList.length > 0) {
        hsxx = hsxxList[0].bdcSlHsxxDO;
    }
    if($.isEmptyObject(hsxx)){
        // 生成核税信息
        addHsxx(data.bdcSlJyxxDO.xmid);
    }
    var fwxx = {};
    if (data.bdcSlFwxxDO) {
        fwxx = data.bdcSlFwxxDO;
        fwxx.xqdm = changeMcToDm(fwxx.xqmc, zdList.xqxx);
        fwxx.jddm = changeMcToJddm(fwxx.xqmc, zdList.xqxx);
    }
    var jyxx = {};
    if(data.bdcSlJyxxDO){
        jyxx =data.bdcSlJyxxDO;
    }

    var json ={
        zd: zdList,
        hsxx: hsxx,
        fwxx: fwxx,
        jyxx: jyxx
    };
    var tpl = ssxxTpl.innerHTML;
    var view = document.getElementById('ycssxx');
    //渲染数据
    laytpl(tpl).render(json, function(html) {
        view.innerHTML = html;
    });

    //加载卖方信息
    generateCmfxx(data.bdcSlZcfDTOList, data.bdcSlJyxxDO.xmid);
    //加载买方信息
    generateMsfxx(data.bdcSlZrfDTOList, data.bdcSlJyxxDO.xmid);
    form.render();
    renderSelect();
    getStateById(readOnly, formStateId, 'slymzh');
    disabledAddFa();
}
// 组织卖方信息到组合页面
function generateCmfxx(sqrList, xmid) {
    // 获取房屋类型
    getReturnData( "/ycsl/checkSpfLc", {gzlslid: processInsId}, 'GET', function (data) {
        if(isNotBlank(data)){
            var json ={
                sqrList:sqrList,
                zd: zdList,
                sqrlb:2,
                xmid: xmid
            };
            if(data ==="clf"){
                //二手房卖方模板
                var tpl = zrfzcfxxTpl.innerHTML;

            }else {
                //商品房卖方模板
                var tpl = kfsxxTpl.innerHTML;
            }
            var view = document.getElementById('cmfxx');
            //渲染数据
            laytpl(tpl).render(json, function(html) {
                view.innerHTML = html;
            });
            form.render();
        }
    }, function (err) {
        console.log(err);
    });

}
// 组织买方信息到组合页面
function generateMsfxx(sqrList, xmid) {
    var json ={
        sqrList:sqrList,
        zd: zdList,
        sqrlb:1,
        xmid : xmid
    };
    var tpl = zrfzcfxxTpl.innerHTML;
    var view = document.getElementById('msfxx');
    //渲染数据
    laytpl(tpl).render(json, function(html) {
        view.innerHTML = html;
    });
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
    if (data.length === 0) {
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
function loadQlr(loadzwr) {
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
                reloadDyrAndCfjg(xmid, i + 1,loadzwr);
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
                    reloadDyrAndCfjg(xmid, i + 1,loadzwr);
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
function generateXmZh(bdcSlQlxxym, name, index, qlTableId,xmfb) {
    var xmTpl = document.getElementById("xmxxTpl");
    var form = layui.form;
    bdcSlQlxxym.bdcXm.wlzs = bdcSlQlxxym.wlzs;
    var json = {
        index: index,
        xmxx: bdcSlQlxxym.bdcXm,
        xmfb: xmfb,
        zd: zdList,
        qlTableId: qlTableId,
        zxlc: zxlc //20191025新增用于页面做注销流程判断
    };
    ydjyy = bdcSlQlxxym.bdcXm.djyy;
    ydjyyArr['djyy' + index] = bdcSlQlxxym.bdcXm.djyy;
    sfchange = false;
    var tpl = xmTpl.innerHTML, view = $("." + name)[0];
    //渲染数据
    if (isNotBlank(view) && isNotBlank(xmTpl)) {
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
    }
    //判断页面是否存在交易信息字段，显示即查询
    if ($(".jyxx" + index).length > 0 && isNotBlank(bdcSlQlxxym.bdcXm.xmid)) {
        getReturnData("/ycsl/jyxx", {xmid: bdcSlQlxxym.bdcXm.xmid}, "GET", function (data) {
            //数据库查询到交易信息数据以数据库为准
            if (data) {
                data.yhtbh = data.htbh;
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

//加载宗地基本信息
function loadZdjbxx(bdcdyh) {
    if($(".zdjbxx").length >0){
        getReturnData("/slym/ql/zdjbxx",{bdcdyh:bdcdyh},"GET",function (data) {
            $(".zdjbxx").each(function (index,item) {
                var name =this.name;
                $("[name="+name +"]").val(data[name]);
            });
            
        });
    }

}

//组织合同信息到组合页面
function generateHtxxZh(xmxx, name, index, qlTableId) {
    cxHtxx(xmxx).done(function(data){
        var bdchtxxTpl;
        if(isNotBlank(data)){
            if(data.htxx.fwlx == "clf"){
                bdchtxxTpl = document.getElementById("bdcClfHtxxTpl");
            }else {
                bdchtxxTpl = document.getElementById("bdcSpfHtxxTpl");
            }

        }else {
            bdchtxxTpl = document.getElementById("bdcClfHtxxTpl");
        }
        var json = {
            index: index,
            htxx: data,
            qlTableId: qlTableId,
            zd:zdList
        };
        var tpl = bdchtxxTpl.innerHTML, view = $("." + name)[0];

        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
        form.render();
        disabledAddFa();
    });
}

// 根据xmid和合同编号查询合同信息
function cxHtxx(xmxx) {
    var deferred = $.Deferred();
    addModel();
    $.ajax({
        url: getContextPath()+"/ycsl/jyxx/cz/queryFcjyxxByHtbh",
        type: 'POST',
        dataType: 'json',
        data: {
            xmid: xmxx.xmid,
            htbh: xmxx.jyhth,
            sfck:true
        },
        success: function (data) {
            removeModal();
            if(isNotBlank(data) &&isNotBlank(data.fwlx)){
                if(data.fwlx == "clf"){
                    var clfData = filterClfHtxx(data);
                    deferred.resolve(clfData);
                }else{
                    var spfData = filterSpfHtxx(data);
                    deferred.resolve(spfData);
                }
            } else{
                deferred.resolve(data);
            }
        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
            deferred.reject();
        }
    });
    return deferred;
}
// 过滤解析存量房合同信息数据
function filterClfHtxx(data){
    var msrxx = [];
    var cmrxx = [];
    if(data.qlrxx.length >0){
        $.each(data.qlrxx, function(index ,val){
            if(val.qlrlb == "1"){
                msrxx.push(val);
            }else{
                cmrxx.push(val);
            }
        });
    }
    return {
        htxx :  data,
        cmrxx : cmrxx,
        msrxx : msrxx
    };
}
// 过滤解析商品房合同信息数据
function filterSpfHtxx(data){
    var cmrxx = [];
    cmrxx.push({
        qlrmc: data.cmr,
        zjzl : data.cmrzjzl,
        zjh : data.cmrzjh
    });
    var msrxx = [];
    msrxx.push({
        qlrmc : data.msrmc,
        zjzl: data.msrzjzl,
        zjh : data.msrzjh
    });
    return {
        htxx : data,
        cmrxx : cmrxx,
        msrxx : msrxx,
        dlrxx : {
            dlrmc: data.wtdlr,
            dlrzjh: data.wtdlrzjh,
            dlrzjzl: data.wtdlrzjzl
        }
    }
}


function loadSjcl() {
    if (isNotBlank(xmlx) && xmlx === 2 && zhsjcl) {
        getReturnData("/slym/sjcl/zhsjcl", {gzlslid: processInsId, taskId: taskId}, 'GET', function (data) {
            generateZhsjcl(data);
        }, function (err) {
            console.log(err);
        });
    } else {
        getReturnData("/slym/sjcl/list/pl", {processInsId: processInsId, taskId: taskId}, 'GET', function (data) {
            generateSjcl(data, "");
        }, function (err) {
            console.log(err);
        });
    }
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
        gzlslid: processInsId,
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
    if(isLoad == null ||!isLoad){
        renderDate(form, formIds);
        form.render();
        renderForm();
        getStateById(readOnly, formStateId, "slymzh", 'qllx' + (index));
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

//组织组合收件材料内容
function generateZhsjcl(zhSjclList) {
    if (isNotBlank(zhSjclList)) {
        //加载收件材料前判断页面是否是展开状态，展开状态刷新后还继续展开
        var hideArea = $('.showhide');
        for (var i = 0; i < zhSjclList.length; i++) {
            sjclNumber += zhSjclList[i].length;
            for (var j = 0; j < zhSjclList[i].length; j++) {
                sjclidLists.push(zhSjclList[i][j].sjclid);
            }
        }
        sessionStorage.setItem("sjclidLists", sjclidLists);
        var json = {
            bdcZhSjclList: zhSjclList,
            zd: zdList
        };
        var tpl = zhsjclTpl.innerHTML, view = document.getElementById("sjcl");
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
        form.render();
        renderForm();
        getStateById(readOnly, formStateId, 'slymzh', 'sjcl');
        disabledAddFa();

        // 默认展开收件材料
        $('.bdc-show-more').html('收起');
        $('.bdc-show-more').parent().siblings('.bdc-more-item').removeClass('bdc-hide');
        $('.bdc-show-more').parent().siblings('.bdc-more-item').addClass('showhide');

        var $info = $('.basic-info');
        $info.off('click', '.bdc-show-more').on('click', '.bdc-show-more', function () {
            var $this = $(this);
            if ($this.html() === '展开') {
                $this.html('收起');
            } else {
                $this.html('展开');
            }
            $this.parent().siblings('.bdc-more-item').toggleClass('bdc-hide');
            $this.parent().siblings('.bdc-more-item').toggleClass('showhide');
            return false;
        });

    }
}

//加载项目内多幢数据（入口）
function generateFdcqxm(qlid) {
    getReturnData("/slym/ql/fdcqxm/list", {qlid: qlid}, 'GET', function (data) {
        if (isNotBlank(data)) {
            generateFdcqxmList(data);
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
        "bdc_tdsyq":"tdsyq"
    };
    if (qllxArr[qllx] != undefined) {
        qllxym = qllxArr[qllx];
    }
    var xmidInput = $(element).parents(".basic-info").find("input[name='xmid']")[0];
    var xmid = $(xmidInput).val();
    var url = "/realestate-register-ui/view/qlxx/" + qllxym + ".html?isCrossOri=false&xmid=" + xmid + "&formStateId=" + formStateId + "&readOnly=" + readOnly;
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
    // 获取当前元素所在的tab页面，通过tab页找到权利信息下的djxl信息
    var djxl = $(".layui-this").find("input[name='djxl']").val();
    var djlxArray = $(".layui-this").find("input[name='djlx']");
    var djlxInput = djlxArray[0];
    var djlx = $(djlxInput).val();
    var url = getContextPath() + "/view/slym/qlr.html?xmid=" + addXmid + "&lclx=" + lclx + "&processInsId=" + processInsId + "&formStateId=" + formStateId + "&qllx=" + qllx + "&zxlc=" + zxlc + "&djlx=" + djlx + "&djxl=" + djxl;
    if (dydj) {
        url = url + "&dydj=true";
    }
    layer.open({
        type: 2,
        area: ['960px', '610px'],
        fixed: false, //不固定
        title: "新增申请人",
        content: url,
        btnAlign: "c"
    });
    form.render();
    resetSelectDisabledCss();
}

//人脸识别
function rlsbcz(gzlslid) {
    $.ajax({
        url: getContextPath() + '/rest/v1.0/rlsb/changzhou/get/rlsb/ywlx?gzlslid='+gzlslid,
        type: 'GET',
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        success: function (data) {
            if (data){
                var url ='IdentityMatch://' + decodeURI(data.xmid) + '@' + decodeURI(data.ywlx);
                window.location.href =url;
            }else {
                layer.alert("<div style='text-align: center'>获取人脸识别业务类型失败</div>", {title: '提示'});
            }
        }
    });
}

//权利人详情展示 -----点击 详细 按钮
function showQlr(qlrid, xmid, qlrlb, elem) {
    addModel();
    var $this = $(".layui-this");
    var qllx = $($this.find("input[name='qllx']")[0]).val();
    var dydj = $($this.find("input[name='dydj']")[0]).val();
    // 获取当前元素所在的tab页面，通过tab页找到权利信息下的djxl信息
    var djxl = $(elem).parents(".layui-show").find("input[name='djxl']").val();
    //登记类型
    var djlx = $($this.find("input[name='djlx']")[0]).val();

    var title = '';
    var url = getContextPath() + "/changzhou/slym/qlr.html?processInsId=" + processInsId + "&qlrid=" + qlrid + "&lclx=" + lclx + "&xmid=" + xmid + "&formStateId=" + formStateId + "&qllx=" + qllx + "&readOnly=" + readOnly + "&zxlc=" + zxlc +"&djxl="+djxl+"&djlx="+djlx;
    if ((qllx === commonDyaq_qllx || dydj === "true")) {
        if (qlrlb === "1") {
            title = "抵押权人详细信息";
        } else {
            title = "抵押人详细信息";
        }
        layer.open({
            type: 2,
            area: ['960px', '610px'],
            fixed: false, //不固定
            title: title,
            content: url + "&dydj=true",
            btnAlign: "c"
        });
    } else {
        layer.open({
            type: 2,
            area: ['960px', '610px'],
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

//组织参数调用评价器
function evaluate() {
    pjq.evaluate();
}

//同步一窗信息
function syncYcsl(xmid){
    layer.confirm('是否同步税务信息', {
        btn: ['是', '否']
    }, function(index, layero){
        layer.close(index);
        addModel();
        getReturnData("/slxxcsh/syncYcslxx", {
            gzlslid : processInsId,
            xmid:xmid
        }, 'GET', function (data) {
            removeModal();
            ityzl_SHOW_SUCCESS_LAYER("操作成功，即将刷新页面");
            setTimeout(function () {
                location.reload();
            }, 500);
        }, function (err) {
            removeModal();
            delAjaxErrorMsg(err);
        });

    }, function(index){
        layer.close(index);
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
    //index 的取值应该看当前点击的是哪个tab页
    index = $('.layui-this').find("input[name=liIndex]").val();
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
            if (id === "dzwyt" && $("#qllx" + (index)).find("select[name=ghyt]").length !==0&&  $("#qllx" + (index)).find("select[name=ghyt]").parent().get(0).tagName != 'TD') {
                $("#qllx" + (index)).find("select[name=ghyt]").val(value);
            }

            //循环同步其他TAB权利页数据
            for (var i = 0; i < bdcSlQlxxymDTOList.length; i++) {
                var dyh = bdcSlQlxxymDTOList[i].bdcXm.bdcdyh;
                if (bdcdyh === dyh) {
                    $("#bdcdyxxForm" + (i + 1)).find("#" + id).val(value);
                    //定着物用途与规划用途同步
                    if (id === "dzwyt" && $("#qllx" + (i + 1)).find("select[name=ghyt]").length !==0&&  $("#qllx" + (i + 1)).find("select[name=ghyt]").parent().get(0).tagName != 'TD') {
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
            if (name === "ssxz") {
                //所属乡镇监听
                var $ssxz = $("#qllx" + index).find("select[name=ssxz]");
                if ($ssxz.length > 0) {
                    //循环同步其他TAB权利页数据
                    for (var i = 0; i < bdcSlQlxxymDTOList.length; i++) {
                        var dyh = bdcSlQlxxymDTOList[i].bdcXm.bdcdyh;
                        if ((i + 1) !== index && bdcdyh === dyh) {
                            $("#qllx" + (i + 1)).find("select[name=ssxz]").val(value);
                        }
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
 * 根据登记原因判断交易价格必填方法
 */
function initAndchangeCheckJyjgByDjyy(djyy,xmselect) {
    if(isNotBlank(xmselect)) {
        var liindex = xmselect.substring(4, 5);
        var $jyjg =$("#qlTab"+liindex).find("[name='jyjg']");
        //当登记原因为存量房买卖、已购公有住房买卖时，交易价格验证必填
        if ($jyjg.length > 0) {
            if (isNotBlank(djyy) && (djyy === "存量房买卖" || djyy === "已购公有住房买卖")) {
                addRequired($jyjg);
            } else {
                removeRequired($jyjg);
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
    getReturnData("/rest/v1.0/slym/fwbdcdy",{bdcdyh:bdcdyh,qjgldm:qjgldm},"GET",function (data) {
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
    if(sply ===3 || sply ===5){
        // 涉税流程按钮不可编辑
        $('.sslc').css({'color':'#666','pointer-events':'none'});
    }
}

//对表单的一系列渲染操作
function renderForm(){
    //表单权限控制
    setSplyBtnAttr();
}

//民政婚姻信息比对
function compareHyxx(elem){
    addModel();

    var slbh =$("#sjbh").val();
    var $qlrbasic =$(elem).parents("tr");
    var qlrmc =$qlrbasic.find("#zrzcf-sqrmc").val();
    var qlrzjh=$qlrbasic.find("#zrzcf-zjh").val();
    var sqrid=$qlrbasic.find("input[name='sqrid']").val();
    var hyzk=$qlrbasic.find("#zrzcf-hyzk").val();
    if(!isNotBlank(qlrmc) ||!isNotBlank(qlrzjh) ||!isNotBlank(sqrid) ||!isNotBlank(hyzk)){
        ityzl_SHOW_WARN_LAYER("申请人名称或证件号或婚姻状况不能为空！");
    }
    getReturnData("/slym/jtcy/compareHyxx",{slbh:slbh,xmid:xmid,sqrid:sqrid,qlrmc:qlrmc,qlrzjh:qlrzjh,hyzk:hyzk},"GET",function (data) {
        removeModal();
        if (data.code ==="0000") {
            ityzl_SHOW_SUCCESS_LAYER(data.msg);
            $(elem).removeClass("bdc-secondary-btn").addClass("bdc-major-btn");
            $qlrbasic.find("input[name='hyxxbdjg']").val("1");
        }else if (data.code ==="0002"){
            //提示
            var confirmIndex = layer.open({
                type: 1,
                skin: 'bdc-small-tips',
                title: '提示信息',
                area: ['320px'],
                content: data.msg,
                btn: ['通过', '取消'],
                btnAlign: 'c',
                yes: function () {
                    //确定操作
                    var sqr ={};
                    sqr.sqrid =sqrid;
                    sqr.hyxxbdjg =1;
                    getReturnData("/slym/sqr",JSON.stringify(sqr),"PATCH",function () {
                        $(elem).removeClass("bdc-secondary-btn").addClass("bdc-major-btn");
                        $qlrbasic.find("input[name='hyxxbdjg']").val("1");
                    },function (error) {
                        $qlrbasic.find("input[name='hyxxbdjg']").val("0");
                        delAjaxErrorMsg(error);
                    });
                    layer.close(confirmIndex);

                },
                btn2: function () {
                    //取消
                    layer.close(confirmIndex);
                    $qlrbasic.find("input[name='hyxxbdjg']").val("0")
                }
            });
        } else {
            //警告
            ityzl_SHOW_WARN_LAYER(data.msg);
            $qlrbasic.find("input[name='hyxxbdjg']").val("0")
        }

    },function (error) {
        delAjaxErrorMsg(error);

    });
}

//打开家庭成员页面
function openJtcyYm(sqrid, sqrlb, xmid) {
    var url = getContextPath() + "/changzhou/slym/jtcy.html?sqrid="+sqrid+"&formStateId="+ formStateId + "&sqrlb="+sqrlb + "&xmid=" + xmid;
    layer.open({
        title: '配偶子女信息',
        type: 2,
        area: ['1300px','600px'],
        content: url
        ,cancel: function(){
            //右上角关闭回调
            //return false 开启该代码可禁止点击该按钮关闭
        }
        ,success: function () {

        }
    });

}

function fmtHszt(hszt){
    if(isNotBlank(hszt)){
        if(hszt == "1"){
            return "已核税";
        }
        return "已退回";
    }
    return "";
}
function fmtWszt(ytsswzt, wszt){
    var zt = "";
    if(isNotBlank(ytsswzt)){
        if(ytsswzt == "1"){
            zt = "已提交税务";
        }
    }
    if(isNotBlank(wszt)){
        if(wszt == "1"){
            zt =  "已缴税";
        }else{
            zt = "未缴税";
        }
    }
    return zt;
}

// 小区名称onblur事件，
function changeXqdm(ele){
    var xqmc = $(ele).val();
    if (isNotBlank(zdList) && isNotBlank(zdList.xqxx)) {
        $.each(zdList.xqxx,function(index, value){
            var mc = value.MC;
            if(xqmc.indexOf(mc)>-1){
                $("#xqdm").val(value.DM);
                $("#jddm").val(value.JDDM);
                return false;
            }
        });
    }
}

function addHsxx(xmid){
    $.ajax({
        url: getContextPath() + "/slym/sw/hsxx",
        type: 'POST',
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify({xmid: xmid,sqrlb:1}),
        success: function (data) {
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

//字典项名称转街道代码
function changeMcToJddm(mc, zdList) {
    var dm = "";
    if (isNotBlank(zdList)) {
        for (var i = 0; i < zdList.length; i++) {
            var zd = zdList[i];
            if (mc == zd.MC) {
                dm = zd.JDDM;
                break;
            }
        }
    }
    return dm;
}

//不动产单元信息详情页面
function showBdcdy(xmid) {
    layer.open({
        type: 2,
        title: "不动产单元信息",
        area: ['960px', '390px'],
        fixed: false, //不固定
        content: getContextPath() + "/view/slym/bdcdy.html?xmid=" + xmid + "&formStateId=" + formStateId + "&readOnly=" + readOnly + "&zxlc=" + zxlc,
        btnAlign: "c",
        success: function () {
        }
    });
}

//加载按钮
function loadButtonArea(ymlx) {

    var json = {printBtn: slymPrintBtn};
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

    //一体化审批附件
    $("#ythspfj").click(function () {
        ythspfj();
    });

    //同步权籍数据
    $("#synchLpbData").click(function () {
        addModel();
        setTimeout(function () {
            synchLpbDataToLc();
        }, 0);

    });

    //评价器
    $("#evaluate").click(function () {
        evaluate();
    });

    // 不良记录
    $("#bljl").click(function () {
        bljl();
    });

    // 修正流程
    $("#xzlc").click(function () {
        xzlc();
    });

    // 生成权属证明
    $("#uploadQszm").click(function () {
        uploadQszm();
    });

    // 查看修正流程
    $("#ckxzlc").click(function () {
        showXzlc();
    });
}

function getSlfs(xmid){
    var jzq;
    getReturnData("/slym/xm/slfs", {xmid: xmid}, "GET", function (result) {
        jzq = result;
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    }, false);
    return jzq;
}
