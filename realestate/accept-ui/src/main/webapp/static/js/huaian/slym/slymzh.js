
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
var readOnly = getQueryString("readOnly");
var zxlc = getQueryString("zxlc");
var printValue = getQueryString("print");
var taskid = getQueryString("taskId");
var zdList = {a:[]};
var xmid;
var qlList;
var bdcXm;
//是否虚拟号
var isXndyh = false;
var formIds = "";
var qllxforqlr;
//共有方式有问题的，保存结束提示信息
var saveMsg = '';
var ydjyy = '';
var ydjyyArr = {};
var sfchange = false;
var sjclNumber = 0;
// 领证方式
var lzfs = {};
//权利类型
var qllxLz = {};
//档案归属地
var dagsd = '';
var xmfbList={};
var falg = true;
//key为index,value为规划用途的值,dzwyt和ghyt保存同步
var ghytArr ={};
//key为index,value为用途名称的值,dzwytmc和ytmc保存同步
var ghytmcArr ={};

//页面入口
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl', 'laydate'], function () {
    form = layui.form;
    $ = layui.jquery;
    layer = layui.layer;
    element = layui.element;
    table = layui.table;
    laytpl = layui.laytpl;
    laydate = layui.laydate;

    //滚动时头部固定
    var $cnotentTitle = $('.bdc-content-fix');
    var $navContainer = $('.bdc-form-div');
    function defaultStyle() {
        if($cnotentTitle.length == 1){
            $('.bdc-form-div').css('padding-top','68px');
        }
        if($(window).scrollTop() > 68){
            $cnotentTitle.css('top','0');
            $navContainer.css('top','63px');
        }else if($(window).scrollTop() <= 68){
            $cnotentTitle.css('top','10px');
            $navContainer.css('top','68px');
        }
    }

    form.on('select(selected)', function (data) {   //选择移交单位 赋值给input框
        $("#cf-jfwj").val(data.value);
        $(this).parents('.layui-input-inline').find("dl").css({"display": "none"});
        form.render();
        resetSelectDisabledCss();
    });

    form.on('select(selectedcf)', function (data) {   //选择移交单位 赋值给input框
        $("#cf-cfwj").val(data.value);
        $(this).parents('.layui-input-inline').find("dl").css({"display": "none"});
        form.render();
        resetSelectDisabledCss();
    });

    $(window).resize(function(){
        defaultStyle();
    });
    $(window).on('scroll',function () {
        if($(this).scrollTop() > 68){
            $cnotentTitle.css('top','0');
            $navContainer.css('top','63px');
        }else if($(this).scrollTop() <= 68){
            $cnotentTitle.css('top','10px');
            $navContainer.css('top','68px');
        }
    });
    $('.layui-input').on('focus',function () {
        if($(this).hasClass('layui-form-danger')){
            $(this).removeClass('layui-form-danger');
        }
        if($(this).parents('.layui-form-select').siblings().hasClass('layui-form-danger')){
            $(this).parents('.layui-form-select').siblings().removeClass('layui-form-danger');
        }
    });
    $('.xm-input').on('click',function () {
        if($(this).parent().siblings('.xm-hide-input').hasClass('layui-form-danger')){
            $(this).parent().siblings('.xm-hide-input').removeClass('layui-form-danger');
        }
    });

    //监听面积单位
    form.on('checkbox(mjdw)', function () {
        $("[name='mjdw']").prop("checked", "");
        $(this).prop("checked", "checked");
        form.render('checkbox');
    });
    form.render();
    element.init();

    //点击提交时不为空的全部标红
    var isSubmit = true;
    var isFirst = true;
    var verifyMsg = "必填项不能为空";
    form.verify({
        required: function (value, item) {
            if (value == '' || value == null || value == undefined) {//判断是否为空
                var bdcdyxx = $("div[name='bdcdyxx']").find($(item));
                if ((isXndyh &&bdcdyxx.length ===0 && zxlc !== "true") ||(!isXndyh)) {
                    //虚拟单元号如果需要验证的元素不在不动产单元信息模块，且非注销类流程,则进行校验 或者为非虚拟号
                    if (isFirst && falg) {
                        $('.layui-tab-item').removeClass('layui-show');
                        $(item).parents('.layui-tab-item').addClass('layui-show');
                        var liIndex = $(item).parents('.layui-tab-item').index() + 1;
                        $('.layui-tab-title li').removeClass('layui-this');
                        $('.layui-tab-title li:nth-child(' + liIndex + ')').addClass('layui-this');
                        isFirst = false;
                    }
                    $(item).addClass('layui-form-danger');
                    if(sfyzbt) {
                        isSubmit = false;
                    }
                }
            }
        }
        ,qlrmcrequired: function (value, item) {
            //权利人名称必填
            if (value == '') {
                $(item).addClass('layui-form-danger');
                isSubmit = false;
                verifyMsg = "权利人名称不能为空";
            }
        }
        , identitynew: function (value, item) {
            var msg = checkSfzZjh(value);
            if (isNotBlank(msg)) {
                $(item).addClass('layui-form-danger');
                isSubmit = false;
                verifyMsg = msg;
            }
        }
        ,number:function (value, item) {
            //为非负整数,允许为空
            if (isNotBlank(value) && !/^[1-9]+[0-9]*]*$/.test(value)) {
                $(item).addClass('layui-form-danger');
                isSubmit = false;
                verifyMsg = "请输入非负整数";
            }
        }
        //数字
        , number2: [/^-?\d*$/, '请输入数字']
    });
    form.on("submit(saveData)", function (data) {
        if (!isSubmit) {
            layer.msg(verifyMsg, {icon: 5, time: 3000});
            isSubmit = true;
            isFirst = true;
            return false;
        } else {
            addModel();
            setTimeout(function () {
                try {
                    $.when(saveXmxx(), saveQlxx(), saveSjcl(), saveQlr(), saveLzr(), saveDsQlr(), updateFwfsss(), insertXgLog()).done(function () {
                        refreshQlxx();
                        removeModal();
                        if (saveMsg == '') {
                            dagsd='';
                            ityzl_SHOW_SUCCESS_LAYER("保存成功");
                        } else {
                            ityzl_SHOW_WARN_LAYER(saveMsg);
                        }
                        saveMsg = '';
                    })
                } catch (e) {
                    removeModal();
                    if (e.message) {
                        showAlertDialog(e.message);
                    } else {
                        delAjaxErrorMsg(e);

                    }
                    return
                }
            }, 10);
            return false;
        }

    });

    form.on("submit(saveBtn)",function (data) {
        if (!isSubmit) {
            layer.msg(verifyMsg, {icon: 5, time: 3000});
            isSubmit = true;
            isFirst = true;
            falg = true;
            return false;
        }else{
            dagsd = $("#dagsd option:selected").val();
            saveDagsd(dagsd,processInsId);
            layer.closeAll();
            return false;
        }

    })
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
    setTimeout("loadButtonArea()", 10);
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

    //监听是否终身
    form.on('radio(zsjz)',function (data) {
        var sfzs = document.getElementById("jzq-jzqjssj");
        if (data.value==="1"){
            console.log(sfzs);
            sfzs.setAttribute("readOnly",true);
            sfzs.setAttribute("disabled",true);
            $("#jzq-jzqjssj").val("");
        }else {
            sfzs.removeAttribute("readOnly");
            sfzs.removeAttribute("disabled");
        }
    })

    //监听抵押方式
    form.on('select(dyfs)', function (data) {
        var bdbzzqse =$(data.elem).parents(".basic-info").find("input[name=bdbzzqse]");
        var zgzqe =$(data.elem).parents(".basic-info").find("input[name=zgzqe]");
        //抵押方式为最高额抵押时，贷款方式为商业贷款
        if(data.value ==="2"){
            addRequired(zgzqe);
            removeRequired(bdbzzqse);
            //定位贷款方式字段
            var $dkfs =$(data.elem).parents(".basic-info").find("select[name =dkfs]");
            if($dkfs.length >0){
                $dkfs.val("商业贷款");
                form.render("select");
                resetSelectDisabledCss();
            }
        }else {
            addRequired(bdbzzqse);
            removeRequired(zgzqe);
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

    // 点击房屋结构，房屋结构名称做出改变
    form.on('select(fwjg)', function (data) {
        if ($(data.elem).parent().get(0).tagName != 'TD') {
            var selected_value = data.value;
            var selected_text = $(data.elem).parents('div.layui-inline').find('dd[lay-value=' + selected_value + ']').text().trim();
            var $this_fwjg_outer_dom = $(data.elem).parents('div.layui-inline');
            var $this_fwjgmc_outer_dom = $this_fwjg_outer_dom.parent().find('[name="fwjgmc"]').parents('div.layui-inline');

            if (selected_value == 6) {
                $this_fwjgmc_outer_dom.css({
                    'display': 'block'
                });
                $this_fwjgmc_outer_dom.find('[name="fwjgmc"]').val('');
                $this_fwjgmc_outer_dom.find('[name="fwjgmc"]').attr('value', '')
            } else {
                $this_fwjgmc_outer_dom.css({
                    'display': 'none'
                });
                $this_fwjgmc_outer_dom.find('[name="fwjgmc"]').val(selected_text);
                $this_fwjgmc_outer_dom.find('[name="fwjgmc"]').attr('value', selected_text);
            }
        } else {
            // 项目内多幢
            var selected_value = data.value;
            var selected_text = $(data.elem).parents('td').find('dd[lay-value=' + selected_value + ']').text().trim();
            var $tr = $(data.elem).parents('tr');
            var $fwjgmctd = $tr.find('[name="fwjgmc"]').parents('td');

            if (selected_value == 6) {
                $tr.find('[name="fwjgmc"]').removeAttr('disabled');
                $fwjgmctd.find('img').css('display', 'none');
                $tr.find('[name="fwjgmc"]').val('');
                $tr.find('[name="fwjgmc"]').attr('value', '');
                $fwjgmctd.removeClass();
            } else {
                $tr.find('#fdcqxmdz-fwjgmc').removeAttr('disabled');
                $fwjgmctd.find('img').css('display', 'none');
                $tr.find('[name="fwjgmc"]').val('');
                $tr.find('[name="fwjgmc"]').attr('value', selected_text);
                $tr.find('#fdcqxmdz-fwjgmc').attr('disabled', 'off');
                $fwjgmctd.find('img').css({
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
                $tr.find('[name="ytmc"]').removeAttr('disabled');
                $ytmctd.find('img').css('display', 'none');
                $tr.find('[name="ytmc"]').val('');
                $tr.find('[name="ytmc"]').attr('value', '');
                $ytmctd.removeClass();
            } else {
                $tr.find('#fdcqxmdz-ytmc').removeAttr('disabled');
                $ytmctd.find('img').css('display', 'none');
                $tr.find('[name="ytmc"]').val('');
                $tr.find('[name="ytmc"]').attr('value', selected_text);
                $tr.find('#fdcqxmdz-ytmc').attr('disabled', 'off');
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

    // 点击定着物用途，定着物用途名称做出改变
    form.on('select(dzwyt)', function (data) {
        var selected_value = data.value;
        var selected_text = $(data.elem).parents('div.layui-inline').find('dd[lay-value=' + selected_value + ']').text().trim();
        var $this_dzwyt_outer_dom = $(data.elem).parents('div.layui-inline');
        var $this_dzwytmc_outer_dom = $this_dzwyt_outer_dom.parent().find('[name="dzwytmc"]').parents('div.layui-inline');

        if (selected_value == 80) {
            $this_dzwytmc_outer_dom.css({
                'display': 'block'
            });
            $this_dzwytmc_outer_dom.find('[name="dzwytmc"]').val('');
            $this_dzwytmc_outer_dom.find('[name="dzwytmc"]').attr('value', '')
        } else {
            $this_dzwytmc_outer_dom.css({
                'display': 'none'
            });
            $this_dzwytmc_outer_dom.find('[name="dzwytmc"]').val(selected_text);
            $this_dzwytmc_outer_dom.find('[name="dzwytmc"]').attr('value', selected_text);
        }
    });

    //弹窗显示
    $('.layui-tab-content').on('click','.bdc-popup-content',function(){
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

    //重新生成权利其他状况
    $(".bdc-form-div").on('click','.resetqlqtzk', function () {
        addModel();
        var xmid =$(this).data("xmid");
        reGenerateQlqtzk(xmid,"2",$(this));
    });

    /**
     * 重新生成权利其他状况
     */
    function reGenerateQlqtzk(xmid,mode,btn){
        var $nowTab =$(btn).parents(".layui-tab-item");
        var $qlqtzk = $nowTab.find("textarea[name='bfqlqtzk']");
        if($qlqtzk.length >0) {
            $qlqtzk.val("");
            //重新加载模板数据
            queryQlqtzkFjMb(xmid, $qlqtzk, mode,false);
        }
    }

    //重新生成附记
    $(".bdc-form-div").on('click','.resetfj', function () {
        var xmid =$(this).data("xmid");
        reGenerateFj(xmid,"3",$(this));
    });

    /**
     * 重新生成附记
     */
    function reGenerateFj(xmid,mode,btn){
        var $nowTab =$(btn).parents(".layui-tab-item");
        var $fj = $nowTab.find("textarea[name='fj']");
        if($fj.length >0) {
            $fj.val("");
            //重新加载模板数据
            queryQlqtzkFjMb(xmid, $fj, mode,false);
        }
    }

    //监听宗地面积修改
    $('.bdc-form-div').on('input propertychange','#zdzhmj',function(){
        var $this = $(this);
        var getMj = $this.val();
        $this.parents('.layui-show').find('#jsydsyq-syqmj').val(getMj);
    });
    //监听查询契税税票
    $('#queryQssp').click(function (e) {
        queryQssp();
    });


    //监听领证方式，领证方式为ems邮寄时，展示邮寄信息
    form.on('select(lzfs)', function(data){
        var id = data.elem.getAttribute("id");
        var index = id.substr(id.indexOf("lzfs")+4,id.length);
        var xmid = $("#qlTab" + index).find("[name=xmid]").val();
        var dydj = $(".layui-this").find("input[name='dydj']");
        if (data.value === "1"){
            // 生成邮寄信息
            $("#showYjxx"+index).attr("style", "display:off");
        }else{
            // 隐藏邮寄信息
            $("#showYjxx"+index).attr("style", "display:none");
        }
        // 更新领证方式
        saveLzfsZh(processInsId,xmid,data.value);
        var xmfb = {
            lzfs:data.value
        };
        // 如果选择电子证照，清空领证人信息
        if (data.value === "4") {
            deleteLzrPl(index,xmid,xmfb);

        } else {
            var qlrList = [];
            // 获取sxh为1的权利人
            getReturnData("/slym/qlr/list/xm", {xmid: xmid, lclx: "zhlc",qlrlb:1}, 'GET', function (data) {
                // 查询qlr有根据sxh排序,取第一个
                if (data && data.length > 0) {
                    qlrList.push(data[0]);
                }
                // 先删除，后插入领证人
                if (qlrList.length > 0) {
                    delAndSaveLzr(index,xmid,xmfb,qlrList[0]);
                }

            }, function (err) {
                console.log(err);
            }, false);

        }
    });


});

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
        if (id === "dzwytmc") {
            ghytmcArr[index] =value;
            //非项目内多幢的才定着物用途名称联动用途名称
            if ($("#qllx" + (index)).find("input[name=ytmc]").parent().get(0).tagName != 'TR'){
                $("#qllx" + (index)).find("input[name=ytmc]").val(value);
            }
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
                //定着物用途名称与用途名称同步(非项目内多幢)
                if (id === "dzwytmc") {
                    ghytmcArr[(i + 1)] =value;
                    if ($("#qllx" + (i + 1)).find("input[name=ytmc]").parent().get(0).tagName != 'TR'){
                        $("#qllx" + (i + 1)).find("input[name=ytmc]").val(value);
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
            var selected_text ="";
            //定着物用途与规划用途同步(非项目内多幢联动)
            if(id ==="dzwyt"){
                if(isNotBlank(value)) {
                    selected_text = $(data.elem).parents('div.layui-inline').find('dd[lay-value=' + value + ']').text().trim();
                }
                ghytArr[index] =value;
                ghytmcArr[index] =selected_text;
            }
            if (id === "dzwyt" && $("#qllx" + (index)).find("select[name=ghyt]").length !==0&&$("#qllx" + (index)).find("select[name=ghyt]").parent().get(0).tagName != 'TD') {
                $("#qllx" + (index)).find("select[name=ghyt]").val(value);

                //若定着物用途为其他时，用途名称显示，否则，用途名称隐藏
                if (value == 80) {
                    //用途名称显示
                    $("#qllx" + (index)).find('input[name="ytmc"]').parents('div.layui-inline').css({
                        'display': 'block'
                    });
                    $("#qllx" + (index)).find('[name="ytmc"]').val('');
                    $("#qllx" + (index)).find('[name="ytmc"]').attr('value', '');
                } else {
                    //用途名称隐藏
                    $("#qllx" + (index)).find('input[name="ytmc"]').parents('div.layui-inline').css({
                        'display': 'none'
                    });
                    $("#qllx" + (index)).find('[name="ytmc"]').val(selected_text);
                    $("#qllx" + (index)).find('[name="ytmc"]').attr('value', selected_text);

                }
            }

            //循环同步其他TAB权利页数据
            for (var i = 0; i < bdcSlQlxxymDTOList.length; i++) {
                var dyh = bdcSlQlxxymDTOList[i].bdcXm.bdcdyh;
                if (bdcdyh === dyh) {
                    ghytArr[(i + 1)] =value;
                    ghytmcArr[(i + 1)] =selected_text;
                    $("#bdcdyxxForm" + (i + 1)).find("#" + id).val(value);
                    //定着物用途与规划用途同步(非项目内多幢联动)
                    if (id === "dzwyt" && $("#qllx" + (i + 1)).find("select[name=ghyt]").length !==0&& $("#qllx" + (i + 1)).find("select[name=ghyt]").parent().get(0).tagName != 'TD') {
                        $("#qllx" + (i + 1)).find("select[name=ghyt]").val(value);
                        //若定着物用途为其他时，用途名称显示，否则，用途名称隐藏
                        if (value == 80) {
                            $("#qllx" + (index)).find('input[name="ytmc"]').parents('div.layui-inline').css({
                                'display': 'block'
                            });
                            $("#qllx" + (index)).find('[name="ytmc"]').val('');
                            $("#qllx" + (index)).find('[name="ytmc"]').attr('value', '');
                        } else {

                            $("#qllx" + (index)).find("input[name=ytmc]").parents('div.layui-inline').css({
                                'display': 'none'
                            });
                            $("#qllx" + (index)).find('[name="ytmc"]').val(selected_text);
                            $("#qllx" + (index)).find('[name="ytmc"]').attr('value', selected_text);
                        }
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
                ghytArr[index] =value;
                var selected_text ="";
                if(isNotBlank(value)) {
                    selected_text = $(data.elem).parents('div.layui-inline').find('dd[lay-value=' + value + ']').text().trim();
                }
                ghytmcArr[index] =selected_text;
                $("#bdcdyxxForm" + (index)).find("select[name=dzwyt]").val(value);
                if (value == 80) {
                    //定着物用途显示
                    $("#bdcdyxxForm" + (index)).find('[name="dzwytmc"]').parents('div.layui-inline').css({
                        'display': 'block'
                    });
                    $("#bdcdyxxForm" + (index)).find('[name="dzwytmc"]').val('');
                    $("#bdcdyxxForm" + (index)).find('[name="dzwytmc"]').attr('value', '');
                } else {
                    //定着物用途隐藏
                    $("#bdcdyxxForm" + (index)).find('[name="dzwytmc"]').parents('div.layui-inline').css({
                        'display': 'none'
                    });
                    $("#bdcdyxxForm" + (index)).find('[name="dzwytmc"]').val(selected_text);
                    $("#bdcdyxxForm" + (index)).find('[name="dzwytmc"]').attr('value', selected_text);
                }
                //循环同步其他TAB权利页数据
                for (var i = 0; i < bdcSlQlxxymDTOList.length; i++) {
                    var dyh = bdcSlQlxxymDTOList[i].bdcXm.bdcdyh;
                    if ((i + 1) !== index && bdcdyh === dyh) {
                        ghytArr[(i + 1)] =value;
                        ghytmcArr[(i + 1)] =selected_text;
                        $("#bdcdyxxForm" + (i + 1)).find("select[name=dzwyt]").val(value);
                        //定着物用途与规划用途同步
                        $("#qllx" + (i + 1)).find("select[name=ghyt]").val(value);
                        if (value == 80) {
                            //定着物用途名称显示
                            $("#bdcdyxxForm" + (i + 1)).find('[name="dzwytmc"]').parents('div.layui-inline').css({
                                'display': 'block'
                            });
                            $("#bdcdyxxForm" + (i + 1)).find('[name="dzwytmc"]').val('');
                            $("#bdcdyxxForm" + (i + 1)).find('[name="dzwytmc"]').attr('value', '');
                            //用途名称显示
                            $("#qllx" + (i + 1)).find('[name="ytmc"]').parents('div.layui-inline').css({
                                'display': 'block'
                            });
                            $("#qllx" + (i + 1)).find('[name="ytmc"]').val('');
                            $("#qllx" + (i + 1)).find('[name="ytmc"]').attr('value', '');
                        } else {
                            //定着物用途名称隐藏
                            $("#bdcdyxxForm" + (i + 1)).find('[name="dzwytmc"]').parents('div.layui-inline').css({
                                'display': 'none'
                            });
                            $("#bdcdyxxForm" + (i + 1)).find('[name="dzwytmc"]').val(selected_text);
                            $("#bdcdyxxForm" + (i + 1)).find('[name="dzwytmc"]').attr('value', selected_text);
                            //用途名称隐藏
                            $("#qllx" + (i + 1)).find('[name="ytmc"]').parents('div.layui-inline').css({
                                'display': 'none'
                            });
                            $("#qllx" + (i + 1)).find('[name="ytmc"]').val(selected_text);
                            $("#qllx" + (i + 1)).find('[name="ytmc"]').attr('value', selected_text);
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
    //用途名称监听
    var $ytmc = $("#qllx" + index).find("input[name=ytmc]");
    if ($ytmc.length > 0) {
        $ytmc.on('change', function () {
            var value = $(this).val();
            ghytmcArr[index] =value;
            $("#bdcdyxxForm" + (index)).find("input[name=dzwytmc]").val(value);
            //循环同步其他TAB权利页数据
            for (var i = 0; i < bdcSlQlxxymDTOList.length; i++) {
                var dyh = bdcSlQlxxymDTOList[i].bdcXm.bdcdyh;
                if ((i + 1) !== index && bdcdyh === dyh) {
                    $("#bdcdyxxForm" + (i + 1)).find("input[name=dzwytmc]").val(value);
                    //定着物面积与建筑面积同步
                    $("#qllx" + (i + 1)).find("input[name=ytmc]").val(value);
                    ghytmcArr[(i + 1)] =value;

                }
            }

        });
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

//对表单的一系列渲染操作
function renderForm(){
    //表单权限控制
    setSplyBtnAttr();
}

//根据审批来源控制按钮隐藏
function setSplyBtnAttr(){
    //互联网
    if(sply ===3 || sply ===17|| sply ===18 || sply ===19 || sply ===15 || sply ===22
        || sply ===5 || sply ===11){
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
 * 处理房屋性质名称、房屋结构名称和用途名称、定着物用途名称
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

    if ($('[name="fwjgmc"]').length > 0) {
        if($('[name="fwjgmc"]').parents("tr").length ===0) {
            $.each($('[name="fwjgmc"]'), function (index, item) {
                var fwjg_code = $(item).parents('div.layui-inline').parent().find('select[name="fwjg"]').val();
                if (fwjg_code == 6) {
                    $(item).parents('div.layui-inline').css({
                        'display': 'block'
                    });
                } else {
                    $(item).parents('div.layui-inline').css({
                        'display': 'none'
                    });
                }

            });
        } else {
            //项目内多幢
            $.each($('[name="fwjgmc"]'), function (index, item) {
                var fwjg_code = $(item).parents('tr').find('select[name="fwjg"]').val();
                var fwjg_name = $(item).parents('tr').find('select[name="fwjg"]').children('[selected="selected"]').text().trim();

                if (fwjg_code == 6) {
                    $(item).parents('tr').find('[name="fwjgmc"]').removeAttr('disabled');
                    $(item).parents('td').find('img').css('display', 'none');
                    $(item).parents('td').removeClass();
                } else {
                    $(item).val(fwjg_name);
                }

            });
        }
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
                var ghyt_name = $(item).parents('tr').find('select[name="ghyt"]').children('[selected="selected"]').text().trim();
                if (ghyt_code == 80) {
                    $(item).parents('tr').find('[name="ytmc"]').removeAttr('disabled');
                    $(item).parents('td').find('img').css('display', 'none');
                    $(item).parents('td').removeClass();

                } else {
                    $(item).val(ghyt_name);
                }

            });
        }

    }

    if ($('[name="dzwytmc"]').length > 0) {
        if($('[name="dzwytmc"]').parents("tr").length ===0) {
            $.each($('[name="dzwytmc"]'), function (index, item) {
                var ghyt_code = $(item).parents('div.layui-inline').parent().find('select[name="dzwyt"]').val();

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
            $.each($('[name="dzwytmc"]'), function (index, item) {
                var dzwyt_code = $(item).parents('tr').find('select[name="dzwyt"]').val();
                var dzwyt_name = $(item).parents('tr').find('select[name="dzwyt"]').children('[selected="selected"]').text().trim();
                if (dzwyt_code == 80) {
                    $(item).parents('tr').find('[name="dzwytmc"]').removeAttr('disabled');
                    $(item).parents('td').find('img').css('display', 'none');
                    $(item).parents('td').removeClass();

                } else {
                    $(item).val(dzwyt_name);
                }

            });
        }

    }
}

//关闭panel
function cancelEdit() {
    layer.closeAll();
}

//权利人顺序号修改
function changeQlrSxh(qlrid, czlx) {
    getReturnData("/slym/qlr/sxh", {
        qlrid: qlrid,
        czlx: czlx,
        lclx: lclx,
        processInsId: processInsId
    }, 'GET', function (data) {
        if (data > 0) {
            loadQlr();
        }
    }, function (err) {
        delAjaxErrorMsg(err);
    });
}

function changeQlrlbMc(qllx) {
    var qlrlbzd = zdList.qlrlb;
    if (qllx===97){
        for (var j = 0; j < qlrlbzd.length; j++) {
            if (qlrlbzd[j].DM === 1) {
                qlrlbzd[j].MC = "权利人(申请人)";
            }
        }
    }
    if (qllx===98){
        for (var j = 0; j < qlrlbzd.length; j++) {
            if (qlrlbzd[j].DM === 1) {
                qlrlbzd[j].MC = "执行人";
            }
            if (qlrlbzd[j].DM === 2){
                qlrlbzd[j].MC = "被执行人";
            }
        }
    }

    zdList.qlrlb = qlrlbzd;
}

// 查看产权信息
function showCqxx(qlrmc, qlrzjh){
    if(isNullOrEmpty(qlrmc) || isNullOrEmpty(qlrzjh)){
        warnMsg("姓名或证件号信息为空，无法查看产权信息。");
        return;
    }
    var qlrxx = [{
        qlrmc : qlrmc,
        zjh: qlrzjh
    }];
    window.open("/realestate-inquiry-ui/view/dtcx/commonCx.html?cxdh=qlrfc&qlrxx="+encodeURI(JSON.stringify(qlrxx)));
}

function showCbfJtcy(qlrid,qlrlb){
    var url = getContextPath() + "/view/slym/cbfjtcy.html?qlrid=" + qlrid + "&formStateId=" + formStateId+"&readOnly="+readOnly+"&processInsId="+processInsId+"&qlrlb="+qlrlb;
    layer.open({
        title: '家庭成员信息',
        type: 2,
        area: ['960px', '400px'],
        content: url
        , cancel: function () {
            //右上角关闭回调
            //return false 开启该代码可禁止点击该按钮关闭
        }
        , success: function () {

        }
    });

}

/**
 * 查询修改日志
 */
function queryXgLog() {
    getReturnData("/rest/v1.0/slym/queryXgLog", {gzlslid: processInsId}, "GET", function (data) {
        if (data && data.value) {
            var modifyData = JSON.parse(data.value).bdcXxXgZbDTOList;
            for (var i = 0; i < modifyData.length; i++) {
                var name = modifyData[i].name;
                if ($('input[name="' + name + '"]').length > 0) {
                    $('input[name="' + name + '"]').parents('.layui-input-inline').addClass('bdc-change-input');
                    $('input[name="' + name + '"]').css({"background-color": "#eaf4fe"})
                } else if ($('select[name="' + name + '"]').length > 0) {
                    $('select[name="' + name + '"]').parents('.layui-input-inline').addClass('bdc-change-input');
                    $('select[name="' + name + '"]').css({"background-color": "#eaf4fe"})
                } else if ($('textarea[name="' + name + '"]').length > 0) {
                    $('textarea[name="' + name + '"]').parents('.layui-input-inline').addClass('bdc-change-input');
                    $('textarea[name="' + name + '"]').css({"background-color": "#eaf4fe"})
                }

            }
            renderChangeTips(JSON.parse(data.value).tsxx);
        }
    }, function (error) {

    })
}

//---------------------页面加载---------------------------------------
//按钮加载
function loadButtonArea(ymlx) {
    var printDTO = queryPrintQlxx(processInsId, zxlc);
    var json = {
        lclx: printDTO[0].xmlx,
        qlxx: printDTO,
        printBtn: slymPrintBtn,
        fkdy: zhlcfkdy,
        sjdBtn: zhclSjdBtn,
        sqsBtn: zhlcSqsBtn,
        zhajlc: zhajlc
    };
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

    $("#dagsdBtn").click(function () {
        var datas = {
            zd: zdList,
            xmfb: xmfbList
        }
        if (xmfbList == null || isNullOrEmpty(xmfbList.dagsd)){
            if (dagsd != '' && dagsd != null){
                datas.xmfb.dagsd = dagsd;
            }
        }
        if (datas.xmfb.dagsd != dagsd && (dagsd != '' || dagsd != null)){
            datas.xmfb.dagsd = dagsd;
        }
        var gsdtpl = dagsdTpl.innerHTML,view1 = document.getElementById("dagsdDiv");
        laytpl(gsdtpl).render(datas,function (html) {
            view1.innerHTML = html;
        });
        form.render();
        getStateById(readOnly, formStateId, 'slymzh');
        falg = false;
        layer.open({
            title:"档案归属地",
            type: 1,
            area: ['360px','460px'],
            content:$("#bdc-dagsd")
        });
    });
    //同步权籍数据
    $("#synchLpbData").click(function () {
        addModel();
        setTimeout(function () {
            synchLpbDataToLc();
        }, 0);

    });
    //查看权籍附件
    $("#showLpbFj").click(function () {
        showLpbFj("");
    });

    // 查看变更记录
    $("#ckbgjl").click(function(){
        showBgjl();
    });

    //评价器
    $("#evaluate").click(function () {
        commonPj(processInsId,taskid);
    });

    //人证对比
    $("#rzdb").click(function () {
        commonRzdb("", "", processInsId);
    });
    

    //外联证书
    $("#glzs").click(function () {
        glzs();
    });
}

//获取权力信息打印按钮需要使用的参数
function queryPrintQlxx(gzlslid, zxlc) {
    var printDTO = {};
    getReturnData("/slym/ql/print", {gzlslid: gzlslid, zxlc: zxlc}, "GET", function (data) {
        if (data) {
            printDTO = data;
        }
    }, function (xhr) {
        delAjaxErrorMsg(xhr)
    }, false)
    return printDTO;
}

//按钮监听
function titleShowUi() {

    //打印点击
    $(".print-btn").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }
        $("#jyxx").hide();
        $("#query").hide();
        $("#print").show();
        setUiWidth($(this), $("#print"));
    });

    //获取交易信息点击
    $(".jyxx-btn").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }
        $("#print").hide();
        $("#query").hide();
        $("#jyxx").show();
        setUiWidth($(this), $("#jyxx"));
    });

    // 查询点击
    $(".query-btn").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }
        $("#print").hide();
        $("#jyxx").hide();
        $("#query").show();
        setUiWidth($(this), $("#query"));
    });

    $(".secondary-printbtn-sjd").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }
        $("#jyxx").hide();
        $("#moreBtn").hide();
        $("#dzzzxx").hide();
        // $("#print").hide();
        $("#third-prinbtn-sqs").hide();
        $("#third-prinbtn-sjd").show();
        setThirdUiWidth($(this), $("#third-prinbtn-sjd"));
    });

    $(".secondary-printbtn-sqs").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }
        $("#jyxx").hide();
        $("#moreBtn").hide();
        $("#dzzzxx").hide();
        // $("#print").hide();
        $("#third-prinbtn-sjd").hide();
        $("#third-prinbtn-sqs").show();
        setThirdUiWidth($(this), $("#third-prinbtn-sqs"));
    });


    $(".print-btn li").click(function () {
        $("#print").hide();
    });

    $(".jyxx-btn li").click(function () {
        $("#jyxx").hide();
    });

    $(".query-btn li").click(function () {
        $("#query").hide();
    });

    $(document).click(function (e) {
        var e = e || window.event; //浏览器兼容性
        var elem = e.target || e.srcElement;
        while (elem) { //循环判断至跟节点，防止点击的是div子元素
            if (elem.id && (elem.id === 'print' || elem.id === 'jyxx' || elem.id === 'query' || elem.id === 'third-prinbtn-sjd' || elem.id === 'third-prinbtn-sqs')) {
                return;
            }
            $("#dzzzxx").hide();
            $("#print").hide();
            $("#jyxx").hide();
            $("#query").hide();
            $("#third-prinbtn-sjd").hide();
            $("#third-prinbtn-sqs").hide();
            elem = elem.parentNode;
        }
    });
}

function showZhlcSjd(element) {
    $("#third-prinbtn-sjd").show();
    $("#third-prinbtn-sqs").hide();
    setThirdUiWidth($(element), $("#third-prinbtn-sjd"));
}

function showZhlcSqs(element) {
    $("#third-prinbtn-sjd").hide();
    $("#third-prinbtn-sqs").show();
    setThirdUiWidth($(element), $("#third-prinbtn-sqs"));
}

//设置按钮样式宽度
function setUiWidth(buttonElement, uiElement) {
    var X = buttonElement.offset().top;
    var Y = buttonElement.offset().left;
    var w = buttonElement.innerWidth();
    var scrollH = $(document).scrollTop();
    if (scrollH > 0) {
        uiElement.css({top: X + 42 - scrollH, right: $('body').width() - Y - w - 15});
    } else {
        uiElement.css({top: X + 32, right: $('body').width() - Y - w - 15});
    }
    var uiWidth = 0;
    var liArray = uiElement.find("li");
    if (liArray.length > 0) {
        var textNumber = 0;
        for (var i = 0; i < liArray.length; i++) {
            var liEl = liArray[i];
            var liText = $(liEl).text();
            if (textNumber < liText.length) {
                textNumber = liText.length;
            }
        }
        var countWidth = (textNumber + 3) * 13;
        if (uiWidth < countWidth) {
            uiWidth = countWidth;
        }
    }
    uiElement.css({"min-width": w, "width": uiWidth + 20});
}


//设置第三级ului标签位置
function setThirdUiWidth(buttonElement, uiElement) {
    var X = buttonElement.offset().top;
    var Y = buttonElement.offset().left;
    var w = buttonElement.innerWidth();
    var scrollH = $(document).scrollTop();
    var uiWidth = 0;
    var liArray = uiElement.find("li");
    if (liArray.length > 0) {
        var textNumber = 0;
        for (var i = 0; i < liArray.length; i++) {
            var liEl = liArray[i];
            var liText = $(liEl).text();
            if (textNumber < liText.length) {
                textNumber = liText.length;
            }
        }
        var countWidth = (textNumber + 3) * 13;
        if (uiWidth < countWidth) {
            uiWidth = countWidth;
        }
    }
    var top = X;
    if (scrollH > 0) {
        top = top - scrollH;
    }
    // 当右边空间不足时，向左展示
    var RightY = $('body').width() - Y - w;
    if (RightY > uiWidth) {
        uiElement.css({top: top, right: $('body').width() - Y - w - 15 - uiWidth});
    } else {
        uiElement.css({top: top, left: Y - uiWidth});
    }
    uiElement.css({"min-width": uiWidth, "width": uiWidth});
}

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

window.getCommonZd = function (callback) {
    var zdList = getZdList();
    if (zdList) {
        callback(zdList);
    }
};

//加载权利信息（入口）
function loadQlxx() {
    getReturnData("/slym/ql/zhlc", {processInsId: processInsId, sfcxql: false,zxlc:zxlc}, 'GET', function (data) {
        if (isNotBlank(data)) {
            qlList = data;
            dagsd = data[0].bdcXmFbDO.dagsd;
            xmfbList = data[0].bdcXmFbDO;
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

//记录流程第一次加载的业务数据
function addYwxxLog(){
    getReturnData("/rest/v1.0/bgxxdb/es",{gzlslid : processInsId},"GET",function (data) {
    },function (error) {});
}

//组织权利页面（主要是权利信息标题与框架，内容暂不加载）
function buildQlxx(bdcSlQlxxymDTOList) {

    var json = {
        bdcSlQlxxymDTOList: bdcSlQlxxymDTOList
    };
    json['sfchangeqljbxxTab'] = sfchangeqljbxxTab;
    var liTpl = liTableTpl.innerHTML, view = document.getElementById('liTable');
    //渲染数据
    laytpl(liTpl).render(json, function (html) {
        view.innerHTML = view.innerHTML + html;
    });

    var contentTpl = contentTableTpl.innerHTML, view = document.getElementById('contentTable');
    //渲染数据
    laytpl(contentTpl).render(json, function (html) {
        view.innerHTML = view.innerHTML + html;
    });
    generateJbxxTab(bdcSlQlxxymDTOList[0].bdcXm,bdcSlQlxxymDTOList[0].bdcXmFbDO);
    form.render();
    renderDate(form, formIds);
    //给下拉框添加删除图标
    renderSelectClose(form);
    renderForm();
    getStateById(readOnly, formStateId, 'slymzh');
    form.render('select');
    disabledAddFa();
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

//加载基本信息tab页
function generateJbxxTab(xmxx,xmfb){
    bdcXm = xmxx;
    xmid = xmxx.xmid;
    sply = xmxx.sply;
    //面积单位为空时默认为平方米
    if (xmxx.mjdw === null || xmxx.mjdw === '') {
        xmxx.mjdw = '1'
    }
    //判断是否是虚拟单元号
    isXndyh = checkXndyh(xmxx.bdcdyh);
    generateJbxx(xmxx,xmfb);
    //加载收件材料(需要放在基本信息渲染之后,否则可能会导致收件材料加载报错)
    loadSjcl();
}

//加载收件材料
function loadSjcl() {
    getReturnData("/slym/sjcl/list/pl", {processInsId: processInsId}, 'GET', function (data) {
        generateSjcl(data, "");
    }, function (err) {
        console.log(err);
    });
}

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

//更新权利信息
function refreshQlxx() {
    getReturnData("/slym/ql/zhlc", {processInsId: processInsId, sfcxql: true, qtsj: true, zxlc: zxlc}, 'GET', function (data) {
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
            dyfsbt(qlList);
            if (isQueryXgLog) {
                // 渲染后权利页面，渲染上一次修改的字段
                queryXgLog();
            }
        }
    }, function (err) {
        delAjaxErrorMsg(err);
    });
}
//抵押方式联动必填
function dyfsbt(data) {
    if (isNotBlank(data)) {
        for (var i = 0; i < data.length; i++) {
            var qllx = data[i].qllx;
            if (qllx === 37) {
                var bdbzzqse = $("#qllx" + (i+1)).find("input[name=bdbzzqse]");
                var zgzqe =$("#qllx" + (i+1)).find("input[name=zgzqe]");
                var dyfs = $("#qllx" + (i+1)).find("select[name=dyfs]").val();
                if (isNotBlank(dyfs)) {
                    if (data[i].bdcQl.dyfs === 2) {
                        addRequired(zgzqe);
                        removeRequired(bdbzzqse);
                    } else {
                        addRequired(bdbzzqse);
                        removeRequired(zgzqe);
                    }
                }else {
                    removeRequired(bdbzzqse);
                    removeRequired(zgzqe);
                }
            }
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
                changeQlrlbMc(qllx);
            }
            if (qllx === 98) {
                changeQlrlbMc(qllx);
            }
            if (bdcSlQlxxym.bdcQl.zsjz != '' && bdcSlQlxxym.bdcQl.zsjz != null){
                var zsjz = bdcSlQlxxym.bdcQl.zsjz;
            }

            var json = {
                bdcSlQlxxym: bdcSlQlxxym,
                index: i + 1,
                zd: zdList,
                showYqlBtn: bdcSlQlxxym.showYqlBtn,
                dkfs: dkfs,
                dysw: dysw,
                zxlc: zxlc
            };
            var bdcdyfwlx = "";
            if (isNotBlank(bdcSlQlxxym.bdcQl.bdcdyfwlx) && bdcSlQlxxym.bdcQl.bdcdyfwlx === 1) {
                bdcdyfwlx = bdcSlQlxxym.bdcQl.bdcdyfwlx;
                //项目内多幢,定着物用途与规划用途不一致,取定着物用途
                if(bdcSlQlxxym.bdcQl.ghyt !=bdcSlQlxxym.bdcXm.dzwyt){
                    ghytArr[(i + 1)] =bdcSlQlxxym.bdcXm.dzwyt;
                    if(bdcSlQlxxym.bdcXm.dzwyt ==80){
                        ghytmcArr[(i + 1)] =bdcSlQlxxym.bdcXm.dzwytmc;
                    }else {
                        ghytmcArr[(i + 1)] = changeDmToMc(bdcSlQlxxym.bdcXm.dzwyt, zdList.fwyt);
                    }
                }
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
            loadQlrZh(bdcSlQlxxym.bdcXm.xmid, "sqr" + (i + 1), i + 1, true, bdcSlQlxxym.dydj);
            //加载领证人
            //判断权利类型是预告，且预告等级种类为抵押
            if (qllx == 96 && isNotBlank(bdcSlQlxxym.bdcQl) && isNotBlank(bdcSlQlxxym.bdcQl.ygdjzl)) {
                var ygdjzl = bdcSlQlxxym.bdcQl.ygdjzl;
                generateLzrxx(bdcSlQlxxym.bdcLzrDOList, "lzrxx" + (i + 1), i + 1, bdcSlQlxxym.bdcXm.xmid, bdcSlQlxxym.bdcXmFbDO, true,ygdjzl,qllx);
            } else {
                generateLzrxx(bdcSlQlxxym.bdcLzrDOList, "lzrxx" + (i + 1), i + 1, bdcSlQlxxym.bdcXm.xmid, bdcSlQlxxym.bdcXmFbDO, true,null,qllx);
            }

            //加载第三权利人
            generateDsQlr(bdcSlQlxxym.bdcDsQlrDOList, i + 1, bdcSlQlxxym.bdcXm.xmid, $(".sqr" + (i + 1)));
            generateXmZh(bdcSlQlxxym, "xmxx" + (i + 1), i + 1, qlTableId, bdcSlQlxxym.dydj);
            if (isNotBlank(bdcSlQlxxym.bdcQl.bdcdyfwlx) && bdcSlQlxxym.bdcQl.bdcdyfwlx === 1) {
                generateFdcqxm(bdcSlQlxxym.bdcQl.qlid);
            }

            if (bdcSlQlxxym.tableName === "bdc_fdcq3") {
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
        var sfzs = document.getElementById("jzq-jzqjssj");
        if (sfzs != null){
            if (zsjz===1){
                sfzs.setAttribute("disabled",true);
            }else {
                sfzs.removeAttribute("disabled");
            }
        }

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

//组织权利人数据到页面
function generateQlrxx(data, xmid, name, index,isLoad,sfdydj) {
    if (sfdydj ===null) {
        sfdydj = false;
        getReturnData("/slym/ql/checkDydj", {xmid: xmid}, "GET", function (data) {
            sfdydj = data;
        }, function () {
        }, false);
    }
    var showJtcyBtn =false;
    if(showJtcyQllxList &&showJtcyQllxList.length>0 &&showJtcyQllxList.indexOf(qllxforqlr) >-1){
        showJtcyBtn =true;
    }
    var json = {
        index: index,
        name: name,
        xmid: xmid,
        bdcQlrDOList: data,
        zd: zdList,
        sfdydj: sfdydj,
        qllx: qllxforqlr,
        showJtcyBtn:showJtcyBtn

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
        //合肥联系电话加密显示
        toEncryptClass('dhjm');
        //重新渲染select框
        form.render('select');
        disabledAddFa("sqrForm" + index);
    }

}

//组织项目信息到组合页面
function generateXmZh(data, name, index, qlTableId,dydj) {
    var xmTpl = document.getElementById("xmxxTpl");
    var form = layui.form;
    data.bdcXm.wlzs = data.wlzs;
    var json = {
        index: index,
        xmxx: data.bdcXm,
        xmfb: data.bdcXmFbDO,
        djxldjyy: data.bdcDjxlDjyyGxDOList,
        zd: zdList,
        qlTableId: qlTableId,
        zxlc: zxlc,
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
        getReturnData("/ycsl/jyxx", {xmid: data.bdcXm.xmid}, "GET", function (jyResult) {
            //数据库查询到交易信息数据以数据库为准
            if (jyResult) {
                //表单赋值
                form.val("xmForm" + index, jyResult);
            }
            renderDate(form, formIds);
            resetSelectDisabledCss();
        }, function (error) {
            delAjaxErrorMsg(error);
        });
    }

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

//设置建筑物区分所有权业主共有部分登记信息_共有部分信息内容
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

//---------------------页面刷新---------------------------------------
//加载基本信息模块（入口）
function loadJbxx() {
    getReturnData("/slym/xm", {processInsId: processInsId}, 'GET', function (data) {
        if (isNotBlank(data)) {
            getReturnData("/slym/xm/fb",{xmid:xmid},"GET",function (result) {
                generateJbxxTab(data,result);
            },function (xhr) {
                delAjaxErrorMsg(xhr);
            });

        }
    }, function (err) {
        console.log(err);
    });
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

/**
 * 权利人，义务人发生改变后同步修改权利表权利人义务人信息
 * @param xmid
 * @param indexnum   渲染模板INDEX
 */
function reloadDyrAndCfjg(xmid, indexnum) {
    if (isNotBlank(xmid)) {
        var dyr = null;
        var gydqlr = null;
        var xydqlr = null;
        if (indexnum === "") {
            var index = $(".layui-this").find("input[name='liIndex']");
            indexnum = $(index[0]).val();
        }
        dyr = $("#qllx" + indexnum).find("#dyaq-dyr");
        gydqlr = $("#qllx" + indexnum).find("#dyiq-gydqlr");
        xydqlr = $("#qllx" + indexnum).find("#dyiq-xydqlr");
        if ( (dyr != null &&dyr.length>0) || (xydqlr != null &&xydqlr.length>0)) {
            var bdcxm;
            getReturnData("/slym/xm/xx", {xmid: xmid}, 'GET', function (data) {
                bdcxm = data
            }, function (err) {
                console.log(err);
            }, false);
            //抵押人
            if (dyr != null) {
                $(dyr).val(bdcxm.ywr);
                dyr.title = bdcxm.ywr;
            }
            if (gydqlr != null) {
                //供役地权利人
                $(gydqlr).val(bdcxm.ywr);
                gydqlr.title = bdcxm.ywr;
            }
            if (xydqlr) {
                //需役地权利人
                $(xydqlr).val(bdcxm.qlr);
                xydqlr.title = bdcxm.qlr;
            }
        }
        var cfjg = document.getElementById('cf-cfjg');
        var jfjg = document.getElementById('cf-jfjg');

        var qllx = $($(".layui-this").find("input[name='qllx']")[0]).val();
        if (qllx === "98") {
            var bdcCfjgQO = {};
            bdcCfjgQO.gzlslid = processInsId;
            if (zxlc === "true") {
                bdcCfjgQO.isjf = true;
            } else {
                bdcCfjgQO.iscf = true;
            }
            var bdccf;

            getReturnData("/slym/ql/updateCfjgOrJfjg?xmid=" + xmid, JSON.stringify(bdcCfjgQO), 'POST', function (data) {
                bdccf = data;
                if (zxlc === "true" && jfjg != null) {
                    $(jfjg).val(bdccf.jfjg);
                    jfjg.title = bdccf.jfjg;
                } else if (cfjg != null) {
                    $(cfjg).val(bdccf.cfjg);
                    cfjg.title = bdccf.cfjg;
                }
            }, function (err) {
                removeModal();
                delAjaxErrorMsg(err);
            });
        }
    }

}

//---------------------保存---------------------------------------
//项目信息保存（批量保存）
function saveXmxx() {
    var bdcXmData = {};
    var bdcXmArray = $(".bdcxm");
    bdcXmArray.serializeArray().forEach(function (item, index) {
        bdcXmData[item.name] = item.value;
    });
    bdcXmData.bz = $("#bz").val();
    //默认为平方米，防止不可编辑冲掉
    var mjdw = '1';
    $("input:checkbox[name='mjdw']:checked").each(function () {
        mjdw = $(this).val();
    });
    if (isNotBlank(mjdw)) {
        bdcXmData.mjdw = mjdw;
    }
    // 转移登记一并申请默认为否
    var zydjybsq = 0;
    $("input:radio[name='zydjybsq']:checked").each(function () {
        zydjybsq = $(this).val();
    });
    bdcXmData.zydjybsq = zydjybsq;

    getReturnData("/slym/xm/list?processInsId=" + processInsId, JSON.stringify(bdcXmData), 'PATCH', function (data) {
    }, function (err) {
        throw err;
    }, false);
}

//权利信息保存（入口方法-包括权利,项目附表,交易信息）
function saveQlxx() {
    saveXmfbPl(".xmfb");
    var index = $("input[name='liIndex']");
    if (index !== null && index.length > 0) {
        for (var i = 0; i < index.length; i++) {
            var c = $(index[i]).val();
            saveXmfb(".xmfb" + c);
            saveQl($(".tdsyq" + c), "cn.gtmap.realestate.common.core.domain.BdcTdsyqDO", c);
            saveQl($(".fdcq" + c), "cn.gtmap.realestate.common.core.domain.BdcFdcqDO", c);
            saveQl($(".dyaq" + c), "cn.gtmap.realestate.common.core.domain.BdcDyaqDO", c);
            saveQl($(".cf" + c), "cn.gtmap.realestate.common.core.domain.BdcCfDO", c);
            saveQl($(".yg" + c), "cn.gtmap.realestate.common.core.domain.BdcYgDO", c);
            saveQl($(".yy" + c), "cn.gtmap.realestate.common.core.domain.BdcYyDO", c);
            saveQl($(".lq" + c), "cn.gtmap.realestate.common.core.domain.BdcLqDO", c);
            saveQl($(".tdcbnydsyq" + c), "cn.gtmap.realestate.common.core.domain.BdcTdcbnydsyqDO", c);
            saveQl($(".dyiq" + c), "cn.gtmap.realestate.common.core.domain.BdcDyiqDO", c);
            saveQl($(".jsydsyq" + c), "cn.gtmap.realestate.common.core.domain.BdcJsydsyqDO", c);
            saveQl($(".gjzwsyq" + c), "cn.gtmap.realestate.common.core.domain.BdcGjzwsyqDO", c);
            saveQl($(".fdcq3" + c), "cn.gtmap.realestate.common.core.domain.BdcFdcq3DO", c);
            saveQl($(".hysyq" + c), "cn.gtmap.realestate.common.core.domain.BdcHysyqDO", c);
            saveQl($(".jzq" + c), "cn.gtmap.realestate.common.core.domain.BdcJzqDO", c);
            saveGzxx(".gzxx" + c, c);
            saveBdcXm(".xmxx" + c, c);
            //保存交易信息，存在即保存
            if ($(".jyxx" + c).length > 0) {
                saveJyxx(".jyxx" + c, c);
            }
        }

    }
}

// 项目附表信息保存(批量保存)
function saveXmfbPl(formClass,djxl) {
    var bdcXmfbData = {};
    var bdcXmfbArray = $(formClass);
    if (bdcXmfbArray !== null && bdcXmfbArray.length > 0) {
        bdcXmfbArray.serializeArray().forEach(function (item, index) {
            bdcXmfbData[item.name] = item.value;
        });
    }
    if (!$.isEmptyObject(bdcXmfbData)) {
        var bdcDjxxUpdateQO = {};
        var whereMap = {};
        whereMap.gzlslid = processInsId;
        if (isNotBlank(djxl)) {
            whereMap.djxl = djxl;
        }
        bdcDjxxUpdateQO.whereMap = whereMap;
        bdcDjxxUpdateQO.jsonStr = JSON.stringify(bdcXmfbData);
        getReturnData("/slym/xm/plxgxmfb", JSON.stringify(bdcDjxxUpdateQO), 'PATCH', function (data) {
            console.log("批量保存项目附表信息成功");
        }, function (err) {
            throw err;
        });
    }

}

//保存项目附表(按照tab页)
function saveXmfb(formClass, index) {
    var bdcXmfbData = {};
    var bdcXmfbArray = $(formClass);
    if (bdcXmfbArray !== null && bdcXmfbArray.length > 0) {
        bdcXmfbArray.serializeArray().forEach(function (item, index) {
            bdcXmfbData[item.name] = item.value;
        });
        if(Object.keys(bdcXmfbData).length >1) {
            getReturnData("/slym/xm/fb", JSON.stringify(bdcXmfbData), 'PATCH', function (data) {
                console.log("保存项目附表成功")
            }, function (err) {
                delAjaxErrorMsg(err);
            }, false);
        }
    }
}

//权利信息保存（按照tab页）
function saveQl(formClass, className, index) {
    if (formClass !== null && formClass.length > 0) {
        var qlData = {};
        formClass.serializeArray().forEach(function (item) {
            qlData[item.name] = item.value;
        });
        if (className === "cn.gtmap.realestate.common.core.domain.BdcCfDO") {
            //将中文括号转换为英文括号
            var bracketobj = replaceBracketArray();
            //中文括号转换为英文括号
            Object.keys(bracketobj).forEach(function (key) {
                qlData[key] = bracketobj[key];
            });
        }
        //同步登记原因
        var djyy = $("#djyy" + index).val();
        if (zxlc !== "true") {
            qlData.djyy = djyy;
        } else {
            //注销抵押原因/注销地役原因
            qlData.zxdyyy = djyy;
            //注销异议原因
            qlData.zxyyyy = djyy;
        }
        //坐落同步
        qlData.zl = $("#zl").val();

        //如果formClass包含房地产权，同步规划用途
        var getClassNameUse = formClass.attr('class');
        if(getClassNameUse.indexOf('fdcq') > -1){
            //规划用途同步
            if(hasProperty(ghytArr, index)) {
                qlData.ghyt = ghytArr[index];
            }else if(hasProperty(qlData, "ghyt")) {
                ghytArr[index] =qlData.ghyt;
            }
            if(hasProperty(ghytmcArr, index)) {
                qlData.ytmc = ghytmcArr[index];
            }else if(hasProperty(qlData, "ytmc")) {
                ghytmcArr[index] =qlData.ytmc;
            }
        }
        //如果formClass包含建设用地使用权，同步使用权面积
        if(getClassNameUse.indexOf('jsydsyq') > -1 &&$('.'+getClassNameUse+'[name=syqmj]').length >0){
            qlData['syqmj'] = $('.'+getClassNameUse+'[name=syqmj]').val();
        }

        //如果formClass包含林权，且xmxx有林种的属性
        if(getClassNameUse.indexOf('lq') > -1 &&$('#lz[class=xmxx'+index+']').length >0){
            // 取项目信息里面的lz 同步到权利信息里面
            qlData['lz'] = $('#lz[class=xmxx'+index+']').val();
        }
        if(getClassNameUse.indexOf('jzq') > -1) {
            var zsjz = "";
            $("input:radio[name='zsjz']:checked").each(function () {
                zsjz = $(this).val();
            });
            if (zsjz == "1"){
                qlData.jzqjssj = "";
            }
            qlData.zsjz = zsjz;
        }


        if (qlData !== {}) {
            savaFdcqxm(qlData.qlid);
            //保存共有部分
            saveFdcq3Gyxx(qlData.qlid);
            //修改土地性质为划拨时，权利的结束时间不可填写
            if ($('.jssj').length > 0) {
                checkJssjGz1($("#qlxz").val(), $('.jssj').val());
            }
            //验证所在层与总层数
            checkSzcAndZcs(qlData.szc, qlData.zcs);

            //验证面专有建筑面积和分摊建筑面积之和是否等于建筑面积
            var $fdcq = $("#bdc_fdcq" + index);
            if ($fdcq.length > 0) {
                var jzmj = $fdcq.find("#fdcq-jzmj").val();
                var zyjzmj = $fdcq.find("#fdcq-zyjzmj").val();
                var ftjzmj = $fdcq.find("#fdcq-ftjzmj").val();
                checkMj(ftjzmj, zyjzmj, jzmj);
                // 验证定着物面积和
                var $bdcdyxx = $("#bdcdyxxForm" + index);
                if($bdcdyxx.length > 0){
                    var dzwmj = $("#bdcdyxxForm" + index).find("#dzwmj").val();
                    checkDzwmjAndJzmj(dzwmj, qlData.jzmj);
                }
            }

            getReturnData("/slym/ql?className=" + className, JSON.stringify(qlData), 'PATCH', function (data) {

            }, function (err) {
                throw err;
            }, false);
        }

    }
}

//项目内多幢信息保存
function savaFdcqxm(qlid) {
    var fdcqxmArray = $(".fdcqxmdz").serializeArray();
    if (fdcqxmArray !== null && fdcqxmArray.length > 0) {
        var fdcqxmList = [];
        var fdcqxm = {};
        var bdcXmData = {};
        var bdcXmArray = $(".bdcxm");
        bdcXmArray.serializeArray().forEach(function (item) {
            bdcXmData[item.name] = item.value;
        });
        if (fdcqxmArray !== []) {
            for (var i = 0; i < fdcqxmArray.length; i++) {
                var name = fdcqxmArray[i].name;
                fdcqxm[name] = fdcqxmArray[i].value;
                //保存时同步保存
                if(hasProperty(bdcXmData, "dzwyt")) {
                    fdcqxm.ghyt = bdcXmData.dzwyt;
                }
                // 以fzid为每一组收件材料的起始数据，作为分割依据
                if ((i + 1 < fdcqxmArray.length && fdcqxmArray[i + 1].name === 'fzid') || i + 1 == fdcqxmArray.length) {
                    fdcqxmList.push(fdcqxm);
                    fdcqxm = {};
                }
            }
        }
        if (fdcqxmList !== []) {
            getReturnData("/slym/ql/fdcqxm/list", JSON.stringify(fdcqxmList), 'PATCH', function (data) {
                if (data > 0) {
                    generateFdcqxm(qlid)
                }
            }, function (err) {
                delAjaxErrorMsg(err)
            }, false);
        }
    }
}

//保存共有部分信息内容
function saveFdcq3Gyxx(qlid) {
    var gyxxArray = $(".gyxx").serializeArray();
    if (gyxxArray !== null && gyxxArray.length > 0) {
        var gyxxList = [];
        var gyxx = {};
        if (gyxxArray !== []) {
            for (var i = 0; i < gyxxArray.length; i++) {
                var name = gyxxArray[i].name;
                gyxx[name] = gyxxArray[i].value;
                // 以gyxxid为每一组权利人的起始数据，作为分割依据
                if ((i + 1 < gyxxArray.length && gyxxArray[i + 1].name === 'gyxxid') || i + 1 == gyxxArray.length) {
                    gyxxList.push(gyxx);
                    gyxx = {};
                }
            }
        }
        if (gyxxList !== []) {
            getReturnData("/slym/ql/fdcq3/gyxx", JSON.stringify(gyxxList), 'PATCH', function (data) {
                if (data > 0) {
                    getFdcq3Gyxx(qlid);
                }
            }, function (err) {
                delAjaxErrorMsg(err)
            }, false);
        }
    }

}

//项目信息保存（按照tab页）
function saveBdcXm(formClass, index) {
    var bdcXmData = {};
    var bdcXmArray = $(formClass);
    if (bdcXmArray !== null && bdcXmArray.length > 0) {
        bdcXmArray.serializeArray().forEach(function (item, index) {
            bdcXmData[item.name] = item.value;
        });
        //同步单元信息的时候会存在dzwmj不可编辑导致获取不到值的情况，这边单独取值保存
        var dzwmj = $("#bdcdyxxForm" + index).find("#dzwmj").val();
        if (dzwmj !== null && dzwmj !== undefined) {
            bdcXmData.dzwmj = dzwmj;
        }

        if(hasProperty(ghytArr, index)) {
            bdcXmData.dzwyt = ghytArr[index];
        }else if(hasProperty(bdcXmData, "dzwyt")) {
            ghytArr[index] =bdcXmData.dzwyt;
        }
        if(hasProperty(ghytmcArr, index)) {
            bdcXmData.dzwytmc = ghytmcArr[index];
        }else if(hasProperty(bdcXmData, "dzwytmc")) {
            ghytmcArr[index] =bdcXmData.dzwytmc;
        }

        var djyy = $("#djyy" + index).val();
        if(djyy !== ydjyyArr['djyy'+index]){
            sfchange =true;
            //防止页面未刷新多次保存
            ydjyyArr['djyy'+index] =djyy;
        }else{
            sfchange =false;
        }
        bdcXmData.djyy = djyy;
        var sqfbcz = $($(formClass).find("input[type='radio']:checked")).val();
        if (isNotBlank(sqfbcz)) {
            bdcXmData.sqfbcz = sqfbcz;
        }
        getReturnData("/slym/xm", JSON.stringify(bdcXmData), 'PATCH', function (data) {
            //保存项目后判断登记原因是否改变过，改变则重新创建收件材料
            if(sfchange) {
                reCreateSjcl();
            }
        }, function (err) {
            delAjaxErrorMsg(err);
        }, false);
    }

}

//交易信息保存(按照tab页）
function saveJyxx(formClass, index) {
    var jyxxData = {};
    var jyxxArray = $(formClass);
    if (jyxxArray !== null && jyxxArray.length > 0) {
        jyxxArray.serializeArray().forEach(function (item, index) {
            jyxxData[item.name] = item.value;
        });
        if(isNotBlank(jyxxData.jyjg)){
            //BDC_SL_JYXX 中jyje与fdcq 中jyjg同步
            jyxxData.jyje =jyxxData.jyjg;
        }
        var xmid = $(".xmxx" + index).find("input[name=xmid]").val();
        var jyxxid = $("#xmForm" + index).find("input[name=jyxxid]").val();
        if (isNotBlank(xmid)) {
            jyxxData["xmid"] = xmid;
            jyxxData["jyxxid"] = jyxxid;
            // 2020-3-11 新增用于在登记页面填写了交易合同号后，保存时同步合同编号至受理库中
            if($(".xmxx" + index).find("input[name=jyhth]").length != 0){
                jyxxData["htbh"] = $(".xmxx" + index).find("input[name=jyhth]").val();
            }
            getReturnData("/ycsl/htxx", JSON.stringify(jyxxData), 'PATCH', function (data) {
                if (data) {
                    $("#xmForm" + index).find("input[name=jyxxid]").val(data.jyxxid);
                }
            }, function (err) {
                delAjaxErrorMsg(err);
            }, false);
        }
    }

}

//更正信息保存(按照tab页)
function saveGzxx(classname, index) {
    var bdcGzxxData = {};
    var bdcGzxxArray = $(classname);
    if (bdcGzxxArray !== null && bdcGzxxArray.length > 0) {
        bdcGzxxArray.serializeArray().forEach(function (item, index) {
            bdcGzxxData[item.name] = item.value;
        });
        getReturnData("/rest/v1.0/gzdj", JSON.stringify(bdcGzxxData), 'PUT', function (data) {
        }, function (err) {
            delAjaxErrorMsg(err);
        }, false);
    }

}

//权利人保存
function saveQlr() {
    var index = $("input[name='liIndex']");
    if (index !== null && index.length > 0) {
        for (var i = 0; i < index.length; i++) {
            var c = $(index[i]).val();
            var qlrArray = $(".qlr" + c).serializeArray();
            saveAllQlr(qlrArray, c);
        }
    }
    loadQlr();
}

//权利人保存（具体）
function saveAllQlr(qlrArray, c) {
    var qlrList = [];
    var qlr = {};
    var qlrnum = 0;
    var ywrnum = 0;
    var gyfs = "";
    var ywrgyfs = "";

    for (var j = 0; j < qlrArray.length; j++) {
        var name = qlrArray[j].name;
        qlr[name] = qlrArray[j].value;
        // 以xmid为每一组权利人的起始数据，作为分割依据
        if ((j + 1 < qlrArray.length && qlrArray[j + 1].name === 'xmid') || j + 1 == qlrArray.length) {
            if (qlr.qlrlb === "1") {
                qlrnum++;
                gyfs += qlr.gyfs + ",";
            }
            if (qlr.qlrlb == '2') {
                ywrnum++;
                ywrgyfs += qlr.gyfs + ",";
            }
            //将证件号中小写字母改为大写
            toUpperClass(qlr, ["zjh", "dlrzjh", "frzjh", "lzrzjh"]);
            qlrList.push(qlr);
            qlr = {};
        }
    }
    //当前项目ID
    var currxmid = xmid;
    var qlmc = "";
    var tabs = $(".qlxx");
    currxmid = $(".xmxx" + c).find("input[name=xmid]").val();
    if (currxmid !== undefined) {
        qlmc = tabs[c - 1].innerText;
    } else {
        var id = $(tabs[c - 1]).find("input[name=xmid]").val();
        currxmid = id;
        qlmc = tabs[c - 1].innerText;
    }

    //判断是否存在权利人(当页面的权利人数据设置disabled属性后获取不到值，改为从数据库获取)
    var result = checkQlrsl(processInsId, currxmid);
    if (!result) {
        throw err = new Error(qlmc + '无权利人，请新增！');
    }
    //验证权利人共有方式
    if (!checkGyfs(gyfs, qlrnum)) {
        throw err = new Error('权利人共有方式不正确，请检查！');

    }

    //验证义务人共有方式
    if (!checkGyfs(ywrgyfs, ywrnum)) {
        saveMsg = "义务人共有方式不正确，请检查!";

    }

    // 处理权利比例数据，去除小数点之前多余的 0
    $.each(qlrList, function(index, value){
        value.qlbl = replaceBeforePointZero(value.qlbl);
    });

    if (!checkAddGybl(qlrList)) {
        throw err = new Error('共有比例不正确，请检查！');
    }
    var url = "/slym/qlr/list/zhlc?processInsId=" + processInsId;
    if (isNotBlank(qlrList)) {
        getReturnData(url, JSON.stringify(qlrList), 'PATCH', function (data) {
            if (data > 0) {
            }
        }, function (err) {
            throw err;
        }, false);
    }
}

//处理领证人信息
function initLzrxx(qlr) {
    var lzrList = [];
    var bdclzr = {};
    //合肥需求，领证人默认代理人
    if (isNotBlank(qlr.dlrmc)) {
        bdclzr.qlrid = qlr.qlrid;
        bdclzr.lzrmc = qlr.dlrmc;
        bdclzr.lzrzjzl = qlr.dlrzjzl;
        bdclzr.lzrzjh = qlr.dlrzjh;
        bdclzr.lzrdh = qlr.dlrdh;
        bdclzr.xmid = qlr.xmid;
    } else if (isNotBlank(qlr.qlrmc)) {
        bdclzr.qlrid = qlr.qlrid;
        bdclzr.lzrmc = qlr.qlrmc;
        bdclzr.lzrzjzl = qlr.zjzl;
        bdclzr.lzrzjh = qlr.zjh;
        bdclzr.lzrdh = qlr.dh;
        bdclzr.xmid = qlr.xmid;
    }else if(isNotBlank(qlr.xmid) && isNotBlank(qlr.qlrid)){
        //xmid和权利人id都不为空,可能受理页面设置了不可编辑,没有获取到名称,根据权利人id去后台查一遍
        getReturnData("/slym/qlr",{qlrid:qlr.qlrid},"GET",function (data) {
            if (data && isNotBlank(data.dlrmc)) {
                bdclzr.qlrid = data.qlrid;
                bdclzr.lzrmc = data.dlrmc;
                bdclzr.lzrzjzl = data.dlrzjzl;
                bdclzr.lzrzjh = data.dlrzjh;
                bdclzr.lzrdh = data.dlrdh;
                bdclzr.xmid = data.xmid;
            } else if (data && isNotBlank(data.qlrmc)) {
                bdclzr.qlrid = data.qlrid;
                bdclzr.lzrmc = data.qlrmc;
                bdclzr.lzrzjzl = data.zjzl;
                bdclzr.lzrzjh = data.zjh;
                bdclzr.lzrdh = data.dh;
                bdclzr.xmid = data.xmid;
            }
        },function (xhr) {
            delAjaxErrorMsg(xhr);
        },false)
    }


    lzrList.push(bdclzr);
    var url  = "/slym/lzr/hf/lzrxx?gzlslid=" + processInsId + "&qlrid=" + qlr.qlrid + "&xmid=" + qlr.xmid;
    getReturnData(url,JSON.stringify(lzrList),'PATCH',function (data) {

    },function (xhr) {
        removeModal();
        delAjaxErrorMsg(xhr);
    })
}

//更新房屋附属设施
function updateFwfsss() {
    var fwfsssList = [];
    var fwfsss = {};
    var fwfsssFormArray = $(".fwfsss").serializeArray();
    if (fwfsssFormArray.length > 0) {
        for (var i = 0; i < fwfsssFormArray.length; i++) {
            var dataName = fwfsssFormArray[i].name;
            fwfsss[dataName] = fwfsssFormArray[i].value;
            if ((i + 1) % 5 === 0) {
                fwfsssList.push(fwfsss);
                fwfsss = {};
            }
        }
        getReturnData("/slym/fwfsss/list", JSON.stringify(fwfsssList), 'PATCH', function (data) {
            removeModal();
            if (data > 0) {
                ityzl_SHOW_SUCCESS_LAYER("保存成功");
            }
        }, function (err) {
            throw err;
        }, false);
    }

}

//保存修改日志
function insertXgLog() {
    var tsxx = $("#updateTips p").text();
    if (isNotBlank(tsxx)) {
        var bdcXxXgDTO = {};
        bdcXxXgDTO.tsxx = tsxx;
        var bdcXxXgZbDTOList = [];
        $(".bdc-change-input").each(function (i) {
            var bdcXxXgZbDTO = new Object();
            var $change = $(this);
            if ($(this).hasClass("layui-input-inline")) {
                if ($(this).find("select").length > 0) {
                    $change = $(this).find("select");
                } else if ($(this).find("textarea").length > 0) {
                    $change = $(this).find("textarea");
                } else {
                    $change = $(this).find("input");
                }
            }
            var value = $change.val();
            var name = $change.attr('name');
            bdcXxXgZbDTO.value = value;
            bdcXxXgZbDTO.name = name;
            bdcXxXgZbDTOList.push(bdcXxXgZbDTO);
        });
        bdcXxXgDTO.bdcXxXgZbDTOList = bdcXxXgZbDTOList;
        getReturnData("/rest/v1.0/slym/addXgLog?gzlslid=" + processInsId, JSON.stringify(bdcXxXgDTO), "POST", function () {

        }, function (error) {
            delAjaxErrorMsg(error);
        })
    }
}

//登记原因改变重新生成收件材料
function reCreateSjcl(){
    getReturnData("/slym/sjcl/recreate",{gzlslid:processInsId},"GET",function (data) {
        if(data !== null && data !== '' && data !== undefined) {
            loadSjcl();
        }
    },function (xhr) {
        delAjaxErrorMsg(xhr,"重新生成收件材料失败");
    })
}

//---------------------按钮操作---------------------------------------
// 证书预览
function createZs() {
    var xmid ="";
    var bdcdyh ="";
    var url = "/realestate-register-ui/rest/v1.0/bdcZs?processInsId=" + processInsId + "&xmid=" + xmid + "&bdcdyh=" + bdcdyh + "&zsyl=true&time=" + new Date().getTime();

    var width = $(window).width() + "px";
    var height = $(window).height() + "px";
    var index = layer.open({
        title: "证书预览",
        type: 2,
        content: url,
        area: [width, height],
        maxmin: true
    });
    layer.full(index);
}

//同步权籍数据
function synchLpbDataToLc() {
    getReturnData("/rest/v1.0/slym/synchLpbDataToLc?processInsId=" + processInsId, '', 'PATCH', function (data) {
        removeModal();
        ityzl_SHOW_SUCCESS_LAYER("同步数据成功");
        loadJbxx();
        loadSjcl();
        refreshQlxx();
    }, function (err) {
        delAjaxErrorMsg(err);
    }, false);
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

//获取交易信息
function queryFcjyClfHtxx(fwlx,currxmid) {
    //小弹出层
    var htbhIndex = layer.open({
        title: '获取房产交易合同信息',
        type: 1,
        skin: 'bdc-spf-layer',
        area: ['430px'],
        btn: ['提交', '取消'],
        content: $('#bdc-popup-small')
        , yes: function (index, layero) {
            //提交 的回调
            var contractNo = $('#contractNo').val();
            $('#contractNo').val('');
            layer.confirm("确认导入合同号为"+contractNo+"的信息吗？", {title:'提示'}, function(index){
                addModel();
                generateYcslxx(contractNo, htbhIndex, fwlx, currxmid);
                layer.close(index);
            });
        }
        , btn2: function (index, layero) {
            //取消 的回调
        }
        , cancel: function () {
            //右上角关闭回调
            //return false 开启该代码可禁止点击该按钮关闭
        }
        , success: function () {
            //清空已有值
            $('#contractNo').val('');
            //自动获取焦点
            $('#contractNo').focus();
        }
    });
}

// 查询契税税票
function queryQssp() {
    var htbh = $('#htbh').val();
    var dzspNo = $('#dzspNo').val();
    var slfType = $('#slfType').val();
    if (!htbh) {
        layer.confirm("请输入合同编号", {title:'提示'}, function(index){
            layer.close(index);
        });
        return;
    }
    if ('' === slfType || !slfType) {
        layer.confirm("请选择是否存量房", {title:'提示'}, function(index) {
            layer.close(index);
        });
        return;
    }
    const wsxx = {gzlslid: processInsId, htbh: htbh, dzsphm: dzspNo, zlfclfbz: slfType};
    $('#htbh').val('');
    $('#dzspNo').val('');
    $('#slfType').val('');
    form.render("select");
    $('#htbh').focus();
    getReturnData("/slym/sjcl/list/swWsxx", JSON.stringify(wsxx),"POST",function (data) {
        if (data && data.length > 0) {
            ityzl_SHOW_SUCCESS_LAYER("查询成功");
        } else {
            ityzl_SHOW_WARN_LAYER("未查询到相关信息！");
        }
    },function (error) {
        delAjaxErrorMsg(error);
    })
}

// 获取契税税票信息
function queryXsspxx() {
    // 小弹出层
    layer.open({
        title: '契税税票查询',
        type: 1,
        area: ['430px', '300px'],
        skin: 'layui-layer-lan',
        content: $('#bdc-popup-qssp')
        , cancel: function () {
            $('#htbh').val('');
            $('#dzspNo').val('');
            $('#slfType').val('');
            form.render("select");
            //右上角关闭回调
            //return false 开启该代码可禁止点击该按钮关闭
        }
    });
}

// 获取互联网缴费状态
function queryHlwJfzt(){
    $.ajax({
        url: getContextPath() + "/sf/hlw/jfzt/" + processInsId,
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            ityzl_SHOW_SUCCESS_LAYER("查询互联网缴费状态成功");
        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
        }
    });
}


//导入交易信息
function generateYcslxx(contractNo, index,fwlx,currxmid) {
    if(!isNotBlank(currxmid)){
        currxmid =xmid;
    }
    layer.close(index);
    $.ajax({
        url: getContextPath() + "/ycsl/jyxx/queryHaFcjyxx",
        type: 'GET',
        data: {htbh: contractNo, xmid: currxmid, gzlslid: processInsId, fwlx: fwlx, lclx : lclx},
        dataType: 'json',
        success: function (data) {
            if (isNotBlank(data)) {
                refreshQlxx();
                loadQlr();
                setTimeout(function(){
                    ityzl_SHOW_SUCCESS_LAYER("查询成功");
                    removeModal();
                }, 150);
            } else {
                ityzl_SHOW_INFO_LAYER("未获取到合同信息");
                removeModal();
                layer.close(index);
            }
        },
        error: function (xhr, status, error) {
            removeModal();
            layer.close(index);
            delAjaxErrorMsg(xhr);
        }
    });
}

// 打开变更记录页面
function showBgjl(){
    getReturnData("/slym/xm/getlclx", {gzlslid: processInsId}, "GET", function (data) {
        if (data !== null) {
            // 1:普通  2：组合  3：批量  4:批量组合
            if(data == "1"){
                window.open(getContextPath() + "/view/bgdb/bgxxdb.html?processInsId=" + processInsId +"&lclx="+ data);
            }
            if(data == "2" || data == "3" || data == "4" ){
                window.open(getContextPath() + "/view/bgdb/bgxxdbPlzh.html?processInsId=" + processInsId +"&lclx="+ data);
            }
        }
    }, function (xhr) {
        delAjaxErrorMsg(xhr)
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

//新增权利人展示  -----------------点击新增申请人
function addQlr(qllx, dydj) {
    var xmidArray = $(".layui-this").find("input[name='xmid']");
    var xmidInput = xmidArray[0];
    var addXmid = $(xmidInput).val();
    if (!isNotBlank(addXmid)) {
        addXmid = xmid;
    }
    var djxl = $(".layui-this").find("input[name='djxl']").val();
    var index = $(".layui-this").find("input[name='liIndex']").val();
    var lzfs = $("#lzfs"+ index).val();
    var url = getContextPath() + "/view/slym/qlr.html?xmid=" + addXmid + "&lclx=" + lclx + "&processInsId=" + processInsId + "&formStateId=" + formStateId + "&qllx=" + qllx + "&zxlc=" + zxlc + "&sfdrjyxx=" + sfdrjyxx + "&djxl=" + djxl +"&lzfs=" + lzfs;
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
    resetSelectDisabledCss();
}

//权利人详情展示 -----点击 详细 按钮
function showQlr(qlrid, xmid, qlrlb, elem) {
    addModel();
    var $this = $(".layui-this");
    var qllx = $($this.find("input[name='qllx']")[0]).val();
    var dydj = $($this.find("input[name='dydj']")[0]).val();
    // 获取当前元素所在的tab页面，通过tab页找到权利信息下的djxl信息
    var djxl = $($this.find("input[name='djxl']")[0]).val();
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

//删除权利人
function deleteQlr(qlrid, sxh, qlrlb) {
    var xmid = "";
    var url = "";
    //获取当前权利人对应的项目ID
    xmid = $($(".layui-this").find("input[name='xmid']")[0]).val();
    url = "/slym/qlr/zhlc?xmid=" + xmid + "&qlrid=" + qlrid + "&sxh=" + sxh + "&qlrlb=" + qlrlb + "&processInsId=" + processInsId;
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
            var qlridList;
            if (qlrlb === "1") {
                qlridList = getDelQlridList(qlrid, 1);
            }
            getReturnData(url, {}, 'DELETE', function (data) {
                removeModal();
                loadQlr();
                reloadDsqlr();
                var qllx = $(".qlxx.layui-this").find("input[name='qllx']").val();
                if (isNotBlank(qllx) && ['4', '6', '8'].indexOf(qllx) > -1) {
                    refreshQlxx();
                }
                ityzl_SHOW_SUCCESS_LAYER("删除成功");
                //    删除权利人后删除权利人对应的领证人
                deleteLzr(qlrid, lclx);
                //删除对应的嘱托人
                deleteDlr(qlridList);
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

function getDelQlridList(qlrid, qlrlb) {
    var idList = [];
    getReturnData("/slym/qlr/allXmQlrid", {
        gzlslid: processInsId,
        qlrid: qlrid,
        djxl: $(".layui-this").find('input[name="djxl"]').val()
    }, "GET", function (data) {
        if (data) {
            idList = data;
        }
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    }, false);
    return idList;
}

//删除代理人（嘱托人） 数据
function deleteDlr(qlridList) {
    //批量根据qlrid 批量删除
    var url = "/slym/dlr/delqlr?gzlslid=" + processInsId + "&djxl=" + $(".layui-this").find('input[name="djxl"]').val();
    getReturnData(url, JSON.stringify(qlridList), "DELETE", function (data) {
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    })
}

//删除义务人列表
function deleteYwr(qlridlist, sxh, xmid, qlrid, otherXmidList) {
    var xmidList;
    if (isNotBlank(otherXmidList)) {
        xmidList = otherXmidList + ',' + xmid;
    } else {
        xmidList = xmid;
    }
    var url = "";
    var qlrList = [];
    if (isNotBlank(qlridlist)) {
        qlridlist += ',' + qlrid;
    } else {
        qlridlist = qlrid;
    }
    console.log(qlridlist);
    qlrList = qlridlist.split(",");
    var getXmid = xmidList.split(",");
    console.log(qlrList);
    var qlrData = {};
    qlrData.xmids = getXmid;
    qlrData.qlrids = qlrList;
    url = "/slym/qlr/deletegroupywr?gzlslid=" + processInsId + "&sxh=" + sxh + "&xmid=" + xmid;

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
            getReturnData(url, JSON.stringify(qlrData), 'DELETE', function (data) {
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

//查看房屋是否签售
function queryFcjyFwsfqs() {
    var json = {
        zd: zdList
    };
    //小弹出层
    var htbhIndex = layer.open({
        title: '获取签售信息',
        type: 1,
        skin: 'bdc-spf-layer',
        area: ['450px', '450px'],
        btn: ['提交', '取消'],
        content: $('#bdc-popup-fwsfqs')
        , yes: function (index, layero) {
            //提交 的回调
            var fwbh = $('#fwbh').val();
            var xmmc = $('#xmmc').val();
            var ysxkzh = $('#ysxkzh').val();
            var fh = $('#fh').val();
            var qsdm = $('#qsdm').val();
            // fwbh与xmmc、ysxkzh、qsdm不能同时存在
            if (fwbh && (xmmc || ysxkzh || fh || qsdm)) {
                ityzl_SHOW_WARN_LAYER("房屋编号与项目名称、预售许可证号、房号、区属代码不能同时输入");
                return;
            }
            var beanName;
            if (fwbh) {
                beanName = 'fcjyFwsfqs';
            } else {
                if (!((xmmc || ysxkzh) && fh && qsdm)) {
                    ityzl_SHOW_WARN_LAYER("项目名称或预售许可证号填写时，房号、区属代码必填");
                    return;
                }
                beanName = 'fcjyLpxx';
            }
            addModel();
            $.ajax({
                url: getContextPath() + "/ycsl/jyxx/queryHaFcjyFwsfqs",
                type: 'GET',
                data: {fwbh: fwbh, xmmc: xmmc, ysxkzh: ysxkzh, fh: fh, qsdm: qsdm, beanName: beanName},
                dataType: 'json',
                success: function (data) {
                    if (data != null) {
                        if(data) {
                            setTimeout(function(){
                                ityzl_SHOW_SUCCESS_LAYER("已签售");
                                removeModal();
                            }, 150);
                        } else {
                            setTimeout(function(){
                                ityzl_SHOW_SUCCESS_LAYER("未签售");
                                removeModal();
                            }, 150);
                        }
                    } else {
                        ityzl_SHOW_INFO_LAYER("未获取到签售信息");
                        removeModal();
                    }
                },
                error: function (xhr, status, error) {
                    removeModal();
                    delAjaxErrorMsg(xhr);
                }
            });
        }
        , btn2: function (index, layero) {
            //取消 的回调
        }
        , cancel: function () {
            //右上角关闭回调
            //return false 开启该代码可禁止点击该按钮关闭
        }
        , success: function () {
            //清空已有值
            $('#fwbh').val('');
            $('#xmmc').val('');
            $('#ysxkzh').val('');
            $('#fh').val('');
            $('#qsdm').val('');
            //自动获取焦点
            $('#fwbh').focus();

            // 加载区属代码下拉框
            $.each(zdList.qx, function (index, item) {
                $('#qsdm').append(new Option(item.MC, item.DM));
            });
            form.render('select');
        }
    });
}

//查看权利信息
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

//不动产单元新增
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

//删除领证人
function deleteLzr(qlrid,lclx) {
    var url = '/slym/lzr/hf/delete?qlrid='+ qlrid + '&gzlslid=' + processInsId + '&djxl=';
    getReturnData(url,'','DELETE',function (data) {
        console.log("删除领证人成功");
        removeModal();
        //删除完重新加载领证人
        var liIndex = $(".qlxx.layui-this").find('input[name="liIndex"]').val()
        var xmid = $($(".layui-this").find("input[name='xmid']")[0]).val();
        loadLzrxx("lzrxx" + liIndex, liIndex, xmid,null);
    },function (xhr) {
        delAjaxErrorMsg(xhr);
    })
}

//删除领证人,再加载
function deleteLzrPl(index,xmid,xmfb) {
    var djxl = $(".layui-this").find('input[name="djxl"]').val();
    var url = '/slym/lzr/yc/lzrdelete/pl?gzlslid='+ processInsId + '&djxl=' + djxl;
    getReturnData(url,'','DELETE',function (data) {
        console.log("批量删除领证人成功");
        loadLzrxx("lzrxx" + index, index, xmid, xmfb);
        removeModal();
    },function (xhr) {
        delAjaxErrorMsg(xhr);
    })
}

//删除领证人,再新增
function delAndSaveLzr(index,xmid,xmfb,qlrData) {
    var djxl = $(".layui-this").find('input[name="djxl"]').val();
    var dydj = $(".layui-this").find("input[name='dydj']");
    var url = '/slym/lzr/yc/lzrdelete/pl?gzlslid='+ processInsId + '&djxl=' + djxl;
    getReturnData(url,'','DELETE',function (data) {
        console.log("批量删除领证人成功");
        // 插入
        saveHfLzr(qlrData, "insert", "zhlc",dydj,index,xmfb);
        removeModal();
    },function (xhr) {
        delAjaxErrorMsg(xhr);
    })
}


//---------------------表单配置js---------------------------------------

//根据登记原因判断特定字段必填方法
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

//根据登记原因控制页面权限初始化方法
function initAuthorityByDjyy(djyyVal,djyyId,eventlx,type){
    changeAuthorityByDjyy(djyyVal,djyyId,eventlx,type);
}

//根据登记原因控制页面权限
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
            var $select = $("#qlTab" + liindex).find("select[name='djyy" + liindex + "']");
            if ($select.length > 0) {
                $select.parents(".layui-input-inline").addClass('bdc-change-input');
                var text = $select.parents(".layui-inline").find("label").text();
                renderChangeTips(text);
            }
        }
    }
}

/*
* 加载领证人信息
*/
function generateLzrxx(lzrdata, id, index, xmid, xmfb, isLoad,ygdjzl,qllxForLz) {
    var qllx = $('.layui-this').find("input[name=qllx]").val();
    // 传了副本才渲染领证方式
    if (xmfb) {
        lzfs[xmid] = xmfb.lzfs;
        // 当前领证方式为空时，设置领证方式默认值
        var lzfs0 = xmfb.lzfs;
        setLzfs(lzfs0,ygdjzl,qllxForLz,xmid);
    }

    //查解封和注销类不展示领证人信息
    if (qllx !== "98" && zxlc !== "true" ) {
        var json = {
            index: index,
            bdclzrList: lzrdata,
            zd: zdList,
            xmid: xmid,
            lzfs: lzfs[xmid]
        };
        var tpl = lzrxxTpl.innerHTML, view = document.getElementById(id);
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
        //页面加载此处不用处理权限,后续统一处理
        if (isLoad == null || !isLoad) {
            form.render(null, "lzrxxForm");
            getStateById(readOnly, formStateId, 'slymzh');
            form.render('select');
            resetSelectDisabledCss();
        }
    }
}

//当前领证方式为空时，设置领证方式默认值
function setLzfs(lzfs0,ygdjzl,qllx,xmid) {
    if (!lzfs0) {
        //如果预告权利，判断预告权利登记种类，预告等级种类是抵押权预告时，默认电子证照
        if (isNotBlank(ygdjzl) && (ygdjzl == 3 || ygdjzl == 4)) {
            lzfs[xmid] = 4;
            return;
        }

        var qllxStr = qllx + "";
        for (var lzfsKey in lzfsQldmMap) {
            var qldmArray = lzfsQldmMap[lzfsKey];
            for (var index = 0; index < qldmArray.length; index++) {
                if (qldmArray[index] == qllx) {
                    lzfs[xmid] = lzfsKey;
                    return;
                }
            }
        }
    }
}

//
//领证人保存
function saveLzr() {
    var index = $("input[name='liIndex']");
    if (index !== null && index.length > 0) {
        for (var i = 0; i <= $('.layui-tab-item').length; i++) {
            var c = $(index[i - 1]).val();
            var lzrArray = $(".layui-tab-item:nth-child(" + i + ") .lzxx").serializeArray();
            if (isNotBlank(lzrArray)) {
                saveAllLzr(lzrArray, c);
            }
        }
    }
}

//领证人保存
function saveAllLzr(lzrArray, c) {
    var lzrList = [];
    var lzr = {};
    for (var m = 0; m < lzrArray.length; m++) {
        var name = lzrArray[m].name;
        lzr[name] = lzrArray[m].value;
        if ((m + 1) % 7 === 0) {
            if (isNotBlank(lzr.lzrmc)) {
                lzrList.push(lzr);
            }
            lzr = {};
        }
    }
    var url = "";
    if (lclx === "zhlc") {
        url = "/slym/lzr/nt/lzrxx/jdlc?gzlslid=" + processInsId;
    }
    if (isNotBlank(lzrList)) {
        getReturnData(url, JSON.stringify(lzrList), "POST", function (data) {

        }, function (xhr) {
            delAjaxErrorMsg(xhr)
        }, false)
    }
}

/*
* 获取领证人信息
*/
function loadLzrxx(id, index, xmid, xmfb) {
    getReturnData("/slym/lzr/nt/lzrxx", {xmid: xmid}, 'GET', function (data) {
        generateLzrxx(data, id, index, xmid, xmfb, false);
    }, function (err) {
        console.log(err);
    });
}


//领证信息操作列 删除
function deleteLzxxTr(qlrid, elem, djxl) {
    if (isNotBlank(qlrid)) {
        showConfirmDialog("提示", "是否确认删除", "deleteLzr", "'" + qlrid + "','" + djxl + "'", "", "", true);
    } else {
        var $this = $(elem);
        $this.parents('.bdc-lzxx-tr').remove();
    }
}

//新增第三权利人
function showDsQlr(qlrid, xmid) {
    //qlrlb=5 借款人
    var url = getContextPath() + "/view/slym/dsQlr.html?xmid=" + xmid + "&processInsId=" + processInsId + "&formStateId=" + formStateId + "&qlrlb=5";
    layer.open({
        type: 2,
        area: ['960px', '360px'],
        fixed: false, //不固定
        title: "新增第三方权利人",
        content: url,
        btnAlign: "c"
    });
    form.render();
    resetSelectDisabledCss();
}


//第三权利人保存
function saveDsQlr() {
    var dsqlrList = [];
    $(".dsqlr-tr").each(function () {
        var $dsqlr = $(this);
        var dsqlrArray = $dsqlr.find(".dsQlr").serializeArray();
        if (dsqlrArray !== null && dsqlrArray.length > 0) {
            var dsqlr = {};
            dsqlrArray.forEach(function (item, index) {
                dsqlr[item.name] = item.value;
            });
            dsqlr.xmid = $dsqlr.find("[name=xmid]").val();
            dsqlr.qlrid = $dsqlr.find("[name=qlrid]").val();
            dsqlrList.push(dsqlr);
        }
    });
    var url = "/slym/qlr/list/updateDsQlr?processInsId=" + processInsId;

    if (dsqlrList != null && dsqlrList.length > 0) {
        getReturnData(url, JSON.stringify(dsqlrList), 'PATCH', function (data) {
        }, function (err) {
            throw err;
        }, false);
    }

}

function generateDsQlr(dsqlr, index, xmid, $sqr) {
    //渲染模板数据
    var dsqlrjson = {
        index: index,
        xmid: xmid,
        bdcDsQlrDOList: dsqlr,
        zd: zdList
    };
    var dsQlrxxTpl = dsQlrTpl.innerHTML;
    if (isNotBlank($sqr)) {
        //渲染数据
        if ($sqr.parents(".layui-tab-item").find(".dsqlr-basic").length > 0) {
            $sqr.parents(".layui-tab-item").find(".dsqlr-basic").remove();
        }
        laytpl(dsQlrxxTpl).render(dsqlrjson, function (html) {
            var $lzrTpl = $('.lzr-basic' + index);
            if ($lzrTpl.length > 0) {
                $lzrTpl.after(html);
            } else {
                $sqr.after(html);
            }
            form.render();
        });
    }
}

//删除第三权利人
function deleteDsQlr(qlrid, sxh, qlrlb, elem) {
    //获取当前权利人对应的项目ID
    var xmid = $($(".layui-this").find("input[name='xmid']")[0]).val();
    var url = "/slym/qlr/deleteDsQlr?xmid=" + xmid + "&qlrid=" + qlrid + "&sxh=" + sxh + "&qlrlb=" + qlrlb + "&processInsId=" + processInsId;

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
                ityzl_SHOW_SUCCESS_LAYER("删除成功");
                $(elem).parents(".dsqlr-tr").remove();
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

function reloadDsqlr() {
    for (var i = 1; i <= $('.layui-tab-item').length; i++) {
        var tabXmid = $('.layui-tab-item:nth-child(' + i + ')').data('xmid');
        var index = $('.layui-tab-item:nth-child(' + i + ')').data('index');
        if (isNotBlank(tabXmid)) {
            queryAndLoad(tabXmid, $('.sqr' + index), index);
        }
    }
}


function queryAndLoad(xmid, $sqr, index) {
    getReturnData("/slym/qlr/list/dsQlr", {xmid: xmid}, 'GET', function (data) {
        //渲染模板数据
        var json = {
            index: index,
            xmid: xmid,
            bdcDsQlrDOList: data,
            zd: zdList
        };
        var dsQlrxxTpl = dsQlrTpl.innerHTML;
        if (isNotBlank($sqr)) {
            //渲染数据
            if ($sqr.parents(".layui-tab-item").find(".dsqlr-basic").length > 0) {
                $sqr.parents(".layui-tab-item").find(".dsqlr-basic").remove();
            }
            laytpl(dsQlrxxTpl).render(json, function (html) {
                var $lzrTpl = $('.lzr-basic' + index);
                if ($lzrTpl.length > 0) {
                    $lzrTpl.after(html);
                } else {
                    $sqr.after(html);
                }
                form.render();
            });
        }
        getStateById(readOnly, formStateId, "slymzh");

    }, function (err) {
        console.log(err);
    });
}


function cancel(){
    layer.closeAll();
}


function loadYjxx(){
    console.log(processInsId);
    // 选中权利页面的xmid，djxl
    var xmid = $("#contentTable .layui-tab-item.layui-show").attr("data-xmid");
    var djxl = $("#contentTable .layui-tab-item.layui-show").attr("data-djxl");
    console.log(xmid);
    console.log(djxl);
    var slbh = $('#sjbh').val();
    var url = getContextPath() + "/huaian/slym/yjxx.html?processInsId=" + processInsId + "&djxl=" + djxl + "&slbh=" + slbh + "&xmid=" +xmid + "&lclx=zh";
    layer.open({
        type: 2,
        area: ['960px', '300px'],
        fixed: false, //不固定
        title: "邮寄信息",
        content: url,
        btnAlign: "c"
    });
}
function resetSelectDisabledCss() {
    //select框禁用修改下拉图标(动态渲染的要在渲染完成之后执行以下两行js代码)
    $('.layui-select-disabled i').addClass("bdc-hide");
    $('.layui-select-disabled .layui-disabled').attr('disabled', 'true');
    changeBtxbjs();
}

function count(){
    var jzmj = $("#fdcq-jzmj").val();
    var zyjzmj = $("#fdcq-zyjzmj").val();
    if (isNotBlank(jzmj) && isNotBlank(zyjzmj)){
        if (parseInt(jzmj) < parseInt(zyjzmj)){
            ityzl_SHOW_WARN_LAYER("专有建筑面积不能大于建筑面积");
        }else {
            $("#fdcq-ftjzmj").val(calculateFloat(jzmj-zyjzmj));
            form.render();
        }
    }
}


