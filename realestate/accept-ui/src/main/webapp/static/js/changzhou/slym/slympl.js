var layer, laytpl, form;
//页面入口
var sjclids = new Set();
var qlrLength = 0;
//是否虚拟号
var isXndyh = false;
//页面重新加载清空存储的收件材料id session
sessionStorage.removeItem("yxsjclids");
sessionStorage.removeItem("sjclidLists");
//增量初始化需要的参数
var jbxxid ="";
var processDefKey="";
var warnLayer ="";
// 批量的字段在权利信息页面，这里记录全局变量
var qlxxChangeIds = "";
var authorityMapByDjyy = {};
//存放当前登记原因可编辑字段集合(主要用于根据登记原因控制字段可编辑)
var editElementForDjyy ="";
// 审批来源
var sply;
//用于打印判断产权还是抵押信息
var qlxxForPrint;
//用于判断权利tab页
var className;
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    form = layui.form;
    var $ = layui.jquery;
    layer = layui.layer;
    laytpl = layui.laytpl;
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
            delAjaxErrorMsg(e, "加载失败");
            return
        }
    }, 10);

    //获取登记原因与相应字段的的对应关系
    authorityMapByDjyyFun();

    $(function () {
        //监听第一次单击tab，
        var bdcdyhIndex = 0;
        element.on('tab(tabFilter)', function (data) {
            var tabid = $(".layui-tab-title .layui-this").attr("id");
            if (tabid === "bdcdyh" && !sfchangeqljbxxTab) {
                if (bdcdyhIndex === 0) {
                    bdcdyhIndex++;
                    addModel();
                    if(qllx ==37) {
                        //用css控制显示抵押模块，因动态表格无法用render来控制html是否渲染
                        $(".bdc_dyaq").show();
                    }
                    setTimeout(function () {
                        $.when(loadPlQlxx(), loadBdcdyh(),bdcdyaqxx()).done(function () {
                            var a = setInterval(function () {
                                if ($('.bdc-form-div .layui-show .bdc-table-box .layui-table-main>.layui-table').height() !== 0) {
                                    renderForm();
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
        $('.bdc-tab-box').on('click', '.bdc-pj-popup', function () {
            var $thisTextarea = $(this).siblings('textarea');
            var fjContent = $thisTextarea.val();
            var title = $(this).parents(".layui-form-item").eq(0).find("label").text();
            layer.open({
                title: isNotBlank(title)? title : '附记',
                type: 1,
                area: ['960px'],
                btn: ['保存'],
                content: $('#fjPopup')
                , yes: function (index, layero) {
                    //提交 的回调
                    $thisTextarea.val($('#fjPopup textarea').val());
                    $('#fjPopup textarea').val('');
                    layer.close(index);
                }
                , btn2: function (index, layero) {
                    //取消 的回调
                    $('#fjPopup textarea').val('');
                }
                , cancel: function () {
                    //右上角关闭回调
                    $('#fjPopup textarea').val('');
                }
                , success: function () {
                    $('#fjPopup textarea').val(fjContent);
                }
            });
        });

        //监听 权利信息 申请人信息表格内容的input focus
        $('.bdc-tab-box').on('focus', 'td input', function () {
            if ($(this).hasClass('layui-form-danger')) {
                $(this).removeClass('layui-form-danger');
            }
            if ($(this).parents('.layui-form-select').siblings().hasClass('layui-form-danger')) {
                $(this).parents('.layui-form-select').siblings().removeClass('layui-form-danger');
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
        if (sjclids.size > 0) {
            var idList = [];
            sjclids.forEach(function (element, sameElement, set) {
                idList.push(sameElement);
            });
            sessionStorage.setItem("yxsjclids", idList);
        } else {
            sessionStorage.setItem("yxsjclids", []);
        }
    });

    //查封机关
    form.on('select(cfjgselect)', function (data) {   //选择移交单位 赋值给input框
        $(this).parents('.layui-input-inline').find("input[name='cfjg']").val(data.value);
        $(this).parents('.layui-input-inline').find("dl").css({"display": "none"});
        form.render();
        resetSelectDisabledCss();
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
        if (sjclids.size > 0) {
            var idList = [];
            sjclids.forEach(function (element, sameElement, set) {
                idList.push(sameElement);
            });
            sessionStorage.setItem("yxsjclids", idList);
        } else {
            sessionStorage.setItem("yxsjclids", []);
        }
    });

    //监听权利tab修改证件类型
    form.on('select(zjzl)', function (data) {
        if (data.value == '1') {
            $(data.elem).parents('td').next().find('input').attr('lay-verify', 'identitynew');
        } else {
            $(data.elem).parents('td').next().find('input').attr('lay-verify', '');
        }
    });

    //监听抵押方式
    form.on('select(dyfs)', function (data) {
        //抵押方式为最高额抵押时，贷款方式为商业贷款
        if (data.value === "2") {
            //定位贷款方式字段
            var $dkfs = $("select[name =dkfs]");
            if ($dkfs.length > 0) {
                $dkfs.val("商业贷款");
                form.render("select");
                resetSelectDisabledCss();
            }
        }
    });


});
var zdList = {a: []};
var xmid;
var qlList = [];
// 登记原因
var djxldjyyList = {};

var formIds = "";
var qllx;
var qszt;
var sfdydj;
var xmfb;
//判断是否首次登记，从而允许楼盘表新增
var sfscdj =false;

function generateTabContent() {
    var xmxx;
    var qlxx;
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
                qszt =data.qszt;
                sply = data.sply;
                ydjyy =data.djyy;
                //面积单位为空时默认为平方米
                if (data.mjdw === null || data.mjdw === '') {
                    data.mjdw = '1'
                }
                if(data.djlx ===100 &&(data.qllx ===4 ||data.qllx ===6 ||data.qllx ===8)){
                    sfscdj =true;
                }
                if (isNotBlank(data.djxl)) {
                    getDjyyList(data.djxl,data.gzldyid);
                }
                xmxx = data;

                getReturnData("/slym/ql/pllc", {processInsId: processInsId, sfcxql: false, cxCqxx: true,cxsdq:true }, 'GET', function (data) {
                    if (isNotBlank(data)) {
                        qlxxForPrint = data;
                        qlxx = data[0];
                        sfdydj = data[0].dydj;
                        xmfb = data[0].bdcXmFbDO;
                    }
                }, function (err) {
                    console.log(err);
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
        json['sfchangeqljbxxTab'] = sfchangeqljbxxTab;
        var liTpl = liTableTpl.innerHTML, liView = document.getElementById('tableUi');
        //渲染数据
        laytpl(liTpl).render(json, function (html) {
            liView.innerHTML = liView.innerHTML + html;
        });
        tpl = tabContentTpL.innerHTML, view = document.getElementById("tabContent");
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
        renderDate(form, formIds);
        renderSelect();
        loadData(xmxx);

        if (sfchangeqljbxxTab) {
            if(qllx ==37) {
                //用css控制显示抵押模块
                $(".bdc_dyaq").show();
            }
            $.when(loadPlQlxx(), loadBdcdyh(),bdcdyaqxx()).done(function () {
                var a = setInterval(function () {
                    if ($('.bdc-form-div .layui-show .bdc-table-box .layui-table-main>.layui-table').height() !== 0) {
                        getStateById(readOnly, formStateId, "slympl", "qlxxTab");
                        clearInterval(a);
                    }
                }, 50);
            });
        }
    });

    // 记录表单第一次加载的业务数据至ES中
    addYwxxLog();
}

function loadData(xmxx) {
    getReturnData("/bdczd", '', 'POST', function (data) {
        removeModal();
        if (isNotBlank(data)) {
            zdList = data;
        }
    }, function (err) {
        delAjaxErrorMsg(err);
    }, false);
    //加载基本信息
    generateJbxx(xmxx);
    //加载申请人
    loadQlr();
    //加载收件材料
    loadSjcl();
}

function getDjyyList(djxl,gzldyid) {
    $.ajax({
        url: getContextPath() + "/slym/xm/queryDjxlDjyy",
        type: 'GET',
        dataType: 'json',
        data: {djxl: djxl,gzldyid:gzldyid},
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
                //面积单位为空时默认为平方米
                if (data.mjdw === null || data.mjdw === '') {
                    data.mjdw = '1'
                }
                if (isNotBlank(data.djxl)) {
                    getDjyyList(data.djxl,data.gzldyid);
                }
                generateJbxx(data);
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}


function loadQlr(loadzwr) {
    addModel();
    //权利人
    $.ajax({
        url: getContextPath() + "/slym/qlr/list",
        type: 'GET',
        dataType: 'json',
        async: false,
        data: {processInsId: processInsId, xmid: xmid, qlrlb: 1},
        success: function (data) {
            if (data) {
                qlrLength = data.length;
            }
            removeModal();
            generateQlrxx(data, "sqrxx");
            //重新加载抵押人查封机关信息
            reloadDyrAndCfjg(xmid, "");
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
    //义务人
    $.ajax({
        url: getContextPath() + "/slym/qlr/groupywr?gzlslid="+processInsId,
        type: 'POST',
        contentType: "application/json;charset=UTF-8",
        data: {},
        success: function (data) {
            removeModal();
            generateQlrxx(filterQlrZh(data), "qlrTable");
            //加载权利信息模块部分信息
            reloadQlxxForYwr(data);
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
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
        var bdcQlrDO = val.bdcQlrDO;
        // 人员类别不是义务人时，不进行过滤
        if (bdcQlrDO.qlrlb != "2") {
            filteQlrArray.push(val);
            return true;
        }
        // 对义务人进行数据过滤
        if ("2" == bdcQlrDO.qlrlb) {
            // 义务人类型不为企业和个人时，不过滤
            if (2 != bdcQlrDO.qlrlx && 1 != bdcQlrDO.qlrlx) {
                filteQlrArray.push(val);
                return true;
            }
            // 义务人类型为企业
            if (bdcQlrDO.qlrlx == 2) {
                var isRepeat = bdcQlrDO.qlrmc in cacheMap;
                if (!isRepeat) {
                    cacheMap[bdcQlrDO.qlrmc] = val;
                }
            }
            // 义务人类型为个人
            if (bdcQlrDO.qlrlx == 1 && !compareQlrRepeat(bdcQlrDO, cacheMap)) {
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
function compareQlrRepeat(bdcQlrDO, cacheMap) {
    // 将18位转15位进行重复校验,若存在则重复，不存在则不重复。
    if (isNotBlank(bdcQlrDO.zjh) &&bdcQlrDO.zjh.length == 18) {
        var mapKey15 = bdcQlrDO.qlrmc + convertIdCard18To15(bdcQlrDO.zjh);
        if (!(mapKey15 in cacheMap)) {
            return false;
        }
    } else {
        var mapKey = bdcQlrDO.qlrmc + bdcQlrDO.zjh;
        if (!(mapKey in cacheMap)) {
            return false;
        }
    }
    return true;
}

/**
 * 处理权利信息中义务人相关信息
 */
function reloadQlxxForYwr(ywrdata) {
    //抵押人
    var dyr = $("#dyaq-dyr");
    //供役地权利人
    var gydqlr = $("#dyiq-gydqlr");
    if ((dyr != null &&dyr.length >0 )|| (gydqlr != null &&gydqlr.length >0)) {
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

function loadSjcl() {
    addModel();
    $.ajax({
        url: getContextPath() + "/slym/sjcl/list/pl",
        type: 'GET',
        dataType: 'json',
        data: {processInsId: processInsId, taskId: taskId},
        success: function (data) {
            removeModal();
            generateSjcl(data);
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}


function generateJbxx(bdcxmxx) {
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var form = layui.form;
        var laytpl = layui.laytpl;
        var qllx = bdcxmxx.qllx;
        if (qllx === 97) {
            changeQlrlbMc();
        }
        //常州居住权获取设立方式
        var jzq;
        var slfs = 0;
        if (qllx === 92){
            jzq = getSlfs(bdcxmxx.xmid);
            slfs = jzq.slfs;
        }
        //申请分别持证为空默认为是
        if(bdcxmxx &&bdcxmxx.sqfbcz ==null){
            bdcxmxx.sqfbcz =1;
        }
        var yjxx = getYjxx();
        var cfbh =getCfbh(bdcxmxx.xmid,bdcxmxx.qllx);
        var json = {
            bdcxmxx: bdcxmxx,
            xmfb: xmfb,
            slfs: slfs,
            zd: zdList,
            djxldjyy: djxldjyyList,
            yjxx: yjxx,
            commonJedw: commonJedw,
            cfbh:cfbh
        };
        var tpl = jbxxTpl.innerHTML, view = document.getElementById('sljbXx');
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
        var xmxxtpl = xmxxTpl.innerHTML, xmxxview = document.getElementById('xmxx');
        //渲染数据
        laytpl(xmxxtpl).render(json, function (html) {
            xmxxview.innerHTML = html;
        });
        form.render();
        renderDate(form, formIds);
        //给下拉框添加删除图标
        renderSelectClose(form);
        renderForm();
        getStateById(readOnly, formStateId, 'slympl');
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
        renderForm();
        getStateById(readOnly, formStateId, 'slympl', id);
        //合肥联系电话加密显示
        toEncryptClass('dhjm');
        renderSelect();
        disabledAddFa(id);
    })
}

var sjclNumber = 0;
//用于存放所有的收件材料id
var sjclidLists = [];

function generateSjcl(data) {
    sjclidLists = [];
    if (data !== null && data.length > 0) {
        for (var i = 0; i < data.length; i++) {
            sjclidLists.push(data[i].sjclid);
        }
    }
    sessionStorage.setItem("sjclidLists", sjclidLists);
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
        renderForm();
        getStateById(readOnly, formStateId, "slympl");
        renderSelect();
        form.render('select');
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

        $('body').on('focus', 'textarea', function () {
            isSearch = false;
        }).on('blur', 'textarea', function () {
            isSearch = true;
        });

        //获取权利信息表头
        var unitTableTitle = getQlCols();
        var url = getContextPath() + '/slym/ql/listQlByPageJson';

        // 查询参数
        var data = {
            sortParameter: "xmid ASC"
        };
        if (zxlc === "true") {
            //注销流程
            data["sfyql"] = true;
        }
        data["qllx"] = qllx;

        // 根据当前查询参数，获取所有的单元信息，用于数据导出
        data["gzlslid"] = processInsId;
        //提交表单
        $("#searchBdcdy").click(function () {
            var bdcdyArray = $(".bdcdyForm").serializeArray();
            bdcdyArray.forEach(function (item) {
                data[item.name] = item.value;
            });
            $.when(tableReload('xmid', data, url,'0')).done(function () {
                var a = setInterval(function () {
                    if ($('.bdc-form-div .layui-show .bdc-table-box .layui-table-main>.layui-table').height() !== 0) {
                        renderForm();
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
            $('.bdc-form-search select').val('');
            form.render('select');
        });

        //获取流程对应的规则用途作为选择项
        getReturnData("/slym/xm/queryZdFwytByGzlslid",{gzlslid:processInsId},"GET",function (data) {
            //清空
            $("#ghyt").empty();
            $('#ghyt').append(new Option("请选择", ""));
            $.each(data, function (index, item) {
                //防止出现对比权籍后，规划用途字典项与登记不一致出现空的情况
                if(item !== null) {
                    $('#ghyt').append(new Option(item.mc, item.dm));// 下拉菜单里添加元素
                }
            });
        },function (error) {
            console.log("用途下拉框获取失败")
        },false);


        var tableConfig = {
            id: 'xmid',
            url: url,
            contentType: 'application/json',
            method: 'post',
            where: data,
            toolbar: "#toolbarBdcdyh",
            defaultToolbar: ['filter'],
            cols: [unitTableTitle]
            , parseData: function (res) {
                res.content.forEach(function (v) {
                    v.yxmid = queryYxmid(v.xmid);
                });
            }
            , done: function () {
                var reverseList = ['zl'];
                reverseTableCell(reverseList, 'xmid');

                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');

                if ($('.bdc-form-div .layui-show .bdc-table-box .layui-table-main>.layui-table').height() == 0) {
                    $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
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
                        renderForm();
                        getStateById(readOnly, formStateId, "slympl");
                        clearInterval(a);
                    }
                }, 50);
            }
        };

        //加载表格 设置默认显示分页详细信息 取消详细按钮
        function loadDataTablbeByUrl(_domId, _tableConfig) {
            layui.use(['table', 'laypage', 'jquery'], function () {
                if (_domId) {
                    var table = layui.table;
                    var $ = layui.jquery;
                    var tableDefaultConfig = {
                        elem: _domId,
                        toolbar: "#toolbarDemo",
                        even: true,
                        cellMinWidth: 80,
                        page: true,  //开启分页
                        limits: [10, 50, 100, 200, 500],
                        data: [],
                        request: {
                            limitName: 'size', //每页数据量的参数名，默认：limit
                            loadTotal:true,//详细分页属性
                        },
                        response: {
                            countName: 'totalElements',  //数据总数的字段名称，默认：count
                            dataName: 'content' //数据列表的字段名称，默认：data
                        }
                    };

                    if (!_tableConfig.cols || !_tableConfig.cols[0] || _tableConfig.cols[0].length < 1) {
                        _tableConfig.cols = [[]]
                    }
                    var tableRenderConfig = $.extend({}, tableDefaultConfig, _tableConfig);
                    table.render(tableRenderConfig);
                }
            });
        }
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
            }  else if (layEvent === "add") {
                var bdcGzYzQO ={};
                bdcGzYzQO.zhbs = "SLYM_ADDBDCDY";
                var gzyzParamMap={};
                gzyzParamMap.gzlslid =processInsId;
                bdcGzYzQO.paramMap = gzyzParamMap;
                gzyzCommon(2,bdcGzYzQO,function (data) {
                    console.log("验证通过");
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

                });


            } else if (layEvent === "delete") {
                var bdcGzYzQO ={};
                bdcGzYzQO.zhbs = "SLYM_SCBDCDY";
                var gzyzParamMap={};
                gzyzParamMap.gzlslid =processInsId;
                bdcGzYzQO.paramMap = gzyzParamMap;
                gzyzCommon(2,bdcGzYzQO,function (data) {
                    console.log("验证通过");
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
                                    parent.parent.location.href = newUlr;
                                } else {
                                    loadBdcdyh();
                                    loadQlr();
                                }
                            });
                        }, function (err) {
                            delAjaxErrorMsg(err);
                        });
                    }

                });


            } else if (layEvent === "pldjls") { //批量登记历史
                window.open("/realestate-register-ui/view/lsgx/pldjls.html?gzlslid=" + processInsId);
            } else if (layEvent === "lpb") {
                //楼盘表
                lpb();

            } else if(layEvent === "gwc") {
                openSlymGwc();
            }

            return false;
        });
        //监听单元格事件
        table.on('tool(bdcdyTable)', function(obj){
            var data={},
                $this = $(obj.tr[0]).find('td[data-field="fj"] .layui-table-cell');
            data.qlid = obj.data.qlid;
            if(obj.event === 'editFj'){
                layer.prompt({
                    formType: 2
                    ,title: '附记'
                    ,value: obj.data.fj
                    ,btn: ['保存', '取消']
                }, function(value, index){
                    layer.close(index);
                    data.fj = value;
                    //中文括号转为英文括号
                    if (isNotBlank(data.fj)) {
                        data.fj =  data.fj.replace('（', '(');
                        data.fj =  data.fj.replace('）', ')');
                    }
                    //这里一般是发送修改的Ajax请求
                    if (isNotBlank(className)&&isNotBlank(data.qlid)) {
                        getReturnData("/slym/ql?className=" + className, JSON.stringify(data), 'PATCH', function (data) {
                            ityzl_SHOW_SUCCESS_LAYER("附记保存成功！");
                        }, function (err) {
                            delAjaxErrorMsg(err);
                        }, false);
                    }else {
                        ityzl_SHOW_WARN_LAYER("未获取到权利！");
                    }
                    //同步更新表格和缓存对应的值
                    if(isNotBlank(obj.data.fj)){
                        obj.update({
                            fj: data.fj
                        });
                    }else {
                        $this.html('<span>'+ data.fj + '</span>');
                    }
                });
            }

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
        content: getContextPath() + "/view/slym/plxg.html?processInsId=" + processInsId + "&xmid=" + xmid + "&formStateId=" + formStateId + "&zxlc=" + zxlc + "&sfdydj=" + sfdydj,
        cancel: function () {
            $.when(loadBdcdyh()).done(function () {
                var a = setInterval(function () {
                    if ($('.bdc-form-div .layui-show .bdc-table-box .layui-table-main>.layui-table').height() !== 0) {
                        renderForm();
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

// 导出Excel
function exportExcel(cols, allData) {
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


    tempForm.on("submit", function () {});
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

//组织参数调用评价器
function evaluate() {
    pjq.evaluate();
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

function loadPlQlxx() {
    getReturnData("/slym/ql/pllc", {
        processInsId: processInsId,
        xmid: xmid,
        sfcxql: true,
        cxCfxx: true,
        cxCqxx: true,
        dsqlr: true,
        cxsdq: true,
        zdytJssjQcpj: true
    }, 'GET', function (data) {
        removeModal();
        if (isNotBlank(data)) {
            buildQlxx(data);
            className = data[0].className;

        }
    }, function (err) {
        delAjaxErrorMsg(err);
    });
}

function buildQlxx(bdcSlQlxxymDTOList) {
    //批量流程组织权利信息到页面，只需要展示其中一个权利即可
    if (isNotBlank(bdcSlQlxxymDTOList)) {
        var bdcSlQlxxym = bdcSlQlxxymDTOList[0];
        var qllx = bdcSlQlxxym.qllx;
        sfdydj = bdcSlQlxxym.dydj;
        ydjyy = bdcSlQlxxym.bdcXm.djyy;
        // bdc_qzxx根据bdc_xm中dzwmj最大的xmid来关联，获取dzwmj最大的xmid
        var xmList = bdcSlQlxxym.bdcXmDOList;
        // 签字xmid
        var qzxmid="";
        xmList.sort(function (a, b) {
            return b.dzwmj - a.dzwmj;
        })
        if (xmList && xmList.length > 0) {
            qzxmid = xmList[0].xmid;
        }
        //申请分别持证为空默认为是
        if(bdcSlQlxxym &&bdcSlQlxxym.bdcXm &&bdcSlQlxxym.bdcXm.sqfbcz ==null){
            bdcSlQlxxym.bdcXm.sqfbcz =1;
        }
        //加载登记原因抵押方式
        generateDjyyDyfs(bdcSlQlxxym);
        //抵押，预抵押，异议，查封，地役权展现在外面的权利字段 +++ 预告注销展现字段
        if (qllx === parseInt(commonDyaq_qllx) || sfdydj || qllx === 97 || qllx === 98 || qllx === 19 || (qllx === 96 && zxlc === "true")) {
            var xtjgpzList = [];
            if (qllx === 98) {
                xtjgpzList = listBdcXtJg(2);
            }
            var json = {
                bdcSlQlxxym: bdcSlQlxxym,
                zd: zdList,
                djxldjyy: djxldjyyList,
                dkfs: dkfs,
                zxlc: zxlc,
                xtjgpz: xtjgpzList,
                qzxmid: qzxmid
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


                if (qllx === 98) {
                    var bdcSlQlxxym = json.bdcSlQlxxym;
                    var bdcxmList = bdcSlQlxxym.cqXmList;
                    for (var i = 0; i < bdcxmList.length; i++) {
                        bdcSlQlxxym.cqXmList[i].zdzhyt = changeDmToMc(bdcSlQlxxym.cqXmList[i].zdzhyt, zdList.tdyt);
                        bdcSlQlxxym.cqXmList[i].dzwyt = changeDmToMc(bdcSlQlxxym.cqXmList[i].dzwyt, zdList.fwyt);
                        bdcSlQlxxym.cqXmList[i].qllx = changeDmToMc(bdcSlQlxxym.cqXmList[i].qllx, zdList.qllx);
                        bdcSlQlxxym.cqXmList[i].qlxz = changeDmToMc(bdcSlQlxxym.cqXmList[i].qlxz, zdList.qlxz);
                        bdcSlQlxxym.cqXmList[i].bdcdyhxszt = getBdcdyZt(bdcSlQlxxym.cqXmList[i].bdcdyh, "");
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

                if(qllx==parseInt(commonDyaq_qllx)){
                    var djyy = 'djyy';
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

                //加载第三权利人
                generateDsQlr(bdcSlQlxxym.bdcXm.xmid, $("#bdc_dyaq-pl"), "",bdcSlQlxxym.bdcDsQlrDOList);
                renderDate(form, formIds);
                form.render();
                renderSelect();
                //给下拉框添加删除图标
                renderSelectClose(form);
                renderForm();
                getStateById(readOnly, formStateId, 'slympl', 'qlxxTab');
                form.render();
                disabledAddFa('qlxxTab');

            }


            //监听修改字段
            if (isNotBlank(xgnrglxs) && xgnrglxs.indexOf(bdcSlQlxxym.bdcXm.djxl) > -1) {
                formIds = "tabContent";
                renderChange("", form, formIds);
            }
        } else if (qllx === 4 || qllx === 6 || qllx === 8) {
            // 渲染不动产信息模块
            //展现交易信息字段
            //增加水电气内容
            var ghxx = new Map();
            if(bdcSlQlxxym.bdcSdqghDOList){
                $.each(bdcSlQlxxym.bdcSdqghDOList, function(index, value){
                    ghxx.set(value.ywlx, value.sfbl);
                });
            }
            //获取签字信息
            getQzxx(bdcSlQlxxym.bdcXm.gzlslid);
            var json = {
                bdcSlQlxxym: bdcSlQlxxym,
                zd: zdList,
                djxldjyy: djxldjyyList,
                dkfs: dkfs,
                zxlc: zxlc,
                ghxx: ghxx,
                qzxmid: qzxmid
            };

            var bdcxxTpl = document.getElementById("bdcxxTpl");
            if (isNotBlank(bdcxxTpl)) {
                var tpl = bdcxxTpl.innerHTML, view = document.getElementById('bdcdyqlxx');
                laytpl(tpl).render(json, function (html) {
                    view.innerHTML = html;
                });
                //加载第三权利人
                generateDsQlr(bdcSlQlxxym.bdcXm.xmid, $(".sqr"), "", bdcSlQlxxym.bdcDsQlrDOList);
                if (isNotBlank(bdcSlQlxxym.bdcXm.jyhth)) {
                    $(".bdchtxx").show();
                    //加载合同信息模块
                    generateHtxxPl(bdcSlQlxxym.bdcXm, "bdchtxx", "", "");
                }
                renderDate(form, formIds);
                form.render();
                renderSelect();
                //给下拉框添加删除图标
                renderSelectClose(form);
                renderForm();
                getStateById(readOnly, formStateId, 'slympl', 'qlxxTab');
                form.render();
                disabledAddFa("bdcdyqlxx");
            }
            $(".bdc-table-zj").removeClass("bdc-hide");
            var height = $('.bdc-table-zj').height();
            $(".bdc-line-search-container").css("padding-top", height + 52);
            $(".layui-tab-item").find('.bdc-table-box').addClass('bdc-table-box-cz');
            //处理合计信息
            $("#sumjzmj").text(isNotBlank(bdcSlQlxxym.jzmjSum) ? bdcSlQlxxym.jzmjSum : 0 + "m²");
            $(".layui-show #bdcdyCount").text(bdcSlQlxxym.bdcdyCount);

            //判断页面是否存在交易信息字段，显示即查询
            if ($(".jyxx").length > 0 && isNotBlank(bdcSlQlxxym.bdcXm.xmid)) {
                getReturnData("/ycsl/jyxx", {xmid: bdcSlQlxxym.bdcXm.xmid}, "GET", function (data) {
                    //表单赋值
                    if (data) {
                        form.val("jyxx", data);
                    }
                    renderDate(form, formIds);
                }, function (error) {
                    delAjaxErrorMsg(error);
                });
            }


        } else {
            //展现交易信息字段
            var json = {
                zxlc: zxlc,
                zd: zdList,
                wlzs: bdcSlQlxxym.wlzs,
                xmfb: bdcSlQlxxym.bdcXmFbDO

            };
            var jyxxTpl = document.getElementById("jyxxTpl");
            if (isNotBlank(jyxxTpl)) {
                var tpl = jyxxTpl.innerHTML, view = document.getElementById('bdcdyqlxx');
                laytpl(tpl).render(json, function (html) {
                    view.innerHTML = html;
                });
                //加载第三权利人
                generateDsQlr(bdcSlQlxxym.bdcXm.xmid, $(".sqr"), "",bdcSlQlxxym.bdcDsQlrDOList);
                renderDate(form, formIds);
                form.render();
                renderSelect();
                //给下拉框添加删除图标
                renderSelectClose(form);
                renderForm();
                getStateById(readOnly, formStateId, 'slympl', 'qlxxTab');
                form.render();
                disabledAddFa("jyxxTpl");

            }

            //判断页面是否存在交易信息字段，显示即查询
            if ($(".jyxx").length > 0 && isNotBlank(bdcSlQlxxym.bdcXm.xmid)) {
                getReturnData("/ycsl/jyxx", {xmid: bdcSlQlxxym.bdcXm.xmid}, "GET", function (data) {
                    //表单赋值
                    if(data) {
                        form.val("jyxx", data);
                    }
                    renderDate(form, formIds);
                }, function (error) {
                    delAjaxErrorMsg(error);
                });
            }
        }
        //点击表格中的更多
        $('.bdc-table-box').on('click', '.bdc-more-btn', function (event) {
            tableTop = $(this).offset().top;
            tableLeft = $(this).offset().left;
            scrollTop = $(document).scrollTop();
            event.stopPropagation();
            $nowBtnMore = $(this).siblings('.bdc-table-btn-more');
            var $btnMore = $(this).siblings('.bdc-table-btn-more');
            if ($(this).offset().top + 26 + $btnMore.height() < document.body.offsetHeight) {
                $btnMore.css({top: tableTop + 26 - scrollTop, left: tableLeft - 40});
            } else {
                $btnMore.css({top: tableTop - scrollTop - $btnMore.height(), left: tableLeft - 40});
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
function getQzxx(gzlslid) {
    if(isNullOrEmpty(gzlslid)) {
        return;
    }
    getReturnData("/pjq/qznrxx",{gzlslid:gzlslid},"GET",function (data) {
        if(data) {
            $("#qlrqztp").attr("src", data[1]);
            $("#ywrqztp").attr("src", data[3]);
        }

    },function (error) {
        delAjaxErrorMsg(error);

    });

}

//加载登记原因与抵押方式
function generateDjyyDyfs(bdcSlQlxxym){
     var djyy = {
         bdcSlQlxxym: bdcSlQlxxym,
         djxldjyy: djxldjyyList
     };

     var tpl = djyyTpl.innerHTML, view = document.getElementById('djyyContainer');

    if (isNotBlank(view)) {
        //渲染数据
        laytpl(tpl).render(djyy, function (html) {
            view.innerHTML = html;
        });
    }

     var dyfs = {
         dyfs: bdcSlQlxxym.bdcQl.dyfs,
         zd: zdList
     };

     var dyfsTPL = dyfsTpl.innerHTML, dyfsView = document.getElementById('dyfsContainer');
     if (isNotBlank(dyfsView)) {
         //渲染数据
         laytpl(dyfsTPL).render(dyfs, function (html) {
             dyfsView.innerHTML = html;
         });
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
            {field: 'xh', title: '序号', width: 50, type: 'numbers'},
            {field: 'bdcdyh', title: '不动产单元号', minWidth: 270, templet: '#bdcdyhTpl'},
            {field: 'ycqzh', title: '原产权证号', minWidth: 280},
            {field: 'zl', title: '坐落', minWidth: 240},
            {field: 'zh', title: '幢号', minWidth: 100},
            {field: 'fjh', title: '房号', minWidth: 90},
            {field: 'zcs', title: '总层数', minWidth: 100},
            {field: 'szmyc', title: '所在层', minWidth: 100},
            {field: 'dzwmj', title: '面积(㎡)', minWidth: 240},
            {
                field: 'fwxz', title: '房屋性质', minWidth: 150,
                templet: function (d) {
                    return changeDmToMc(d.fwxz, zdList.fwxz);
                }
            },
            {
                field: 'ghyt', title: '房屋用途', minWidth: 150,
                templet: function (d) {
                    return changeDmToMc(d.ghyt, zdList.fwyt);
                }
            },
            {
                field: 'fwjg', title: '房屋结构', minWidth: 150,
                templet: function (d) {
                    return changeDmToMc(d.fwjg, zdList.fwjg);
                }
            },
            {
                field: 'jgsj', title: '竣工时间', minWidth: 150
            },
            {field: 'cz', title: '操作', minWidth: 220, templet: '#bdcdyczTpl', fixed: 'right'}
        ];
    } else if (qllx === parseInt(commonDyaq_qllx)) {
        /**
         * 抵押权数据列
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
            {
                field: 'dyfs', title: '抵押方式', minWidth: 100,
                templet: function (d) {
                    return changeDmToMc(d.dyfs, zdList.dyfs);
                }
            },
            {field: 'zh', title: '幢号', minWidth: 100},
            {field: 'fj', title: '附记', minWidth: 250, event: 'editFj', style:'cursor: pointer;'},
            {field: 'cz', title: '操作', minWidth: 220, templet: '#bdcdyczTpl', fixed: 'right'}
        ];
    } else if (qllx === 3 || qllx === 7) {

        /**
         * 建设用地使用权数据列
         */
        cols = [
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: 'xh', title: '序号', width: 50, type: 'numbers'},
            {field: 'bdcdyh', title: '不动产单元号', minWidth: 270, templet: '#bdcdyhTpl'},
            {field: 'ysfwbm', title: '预售房屋编码', minWidth: 140},
            {field: 'zl', title: '坐落', minWidth: 240},
            {field: 'ycqzh', title: '原产权证号', minWidth: 280},
            {field: 'dzwmj', title: '定着物面积(㎡)', minWidth: 240},
            {field: 'bdcdywybh', title: '不动产唯一编码', minWidth: 140},
            {field: 'qllx', title: '权利类型', minWidth: 100, templet: '#qllxTpl'},
            {
                field: 'qlxz', title: '权利性质', minWidth: 100,
                templet: function (d) {
                    return changeDmToMc(d.qlxz, zdList.qlxz);
                }
            }, {field: 'zdzhmj', title: '宗地宗海面积(㎡)', minWidth: 140},
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
            {field: 'fj', title: '附记', minWidth: 250, event: 'editFj', style:'cursor: pointer;'},
            {field: 'cz', title: '操作', minWidth: 220, templet: '#bdcdyczTpl', fixed: 'right'}
        ];
    } else if (qllx === 19) {
        /**
         * 地役权数据列
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
            }, {field: 'zdzhmj', title: '宗地宗海面积(㎡)', minWidth: 140},
            {field: 'zdzhyt', title: '宗地宗海用途', minWidth: 120, templet: '#zdzhytTpl'},
            {field: 'zh', title: '幢号', minWidth: 100},
            {field: 'cz', title: '操作', minWidth: 220, templet: '#bdcdyczTpl', fixed: 'right'}

        ];

    }
    else if (qllx === 97) {
        /**
         * 异议数据列
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
            }, {field: 'zdzhmj', title: '宗地宗海面积(㎡)', minWidth: 140},
            {field: 'zdzhyt', title: '宗地宗海用途', minWidth: 120, templet: '#zdzhytTpl'},
            {field: 'zh', title: '幢号', minWidth: 100},
            {field: 'cz', title: '操作', minWidth: 220, templet: '#bdcdyczTpl', fixed: 'right'}

        ];

    }
    else if (qllx === 98) {

        /**
         * 查封数据列
         */
        cols = [
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: 'xh', title: '序号', width: 50, type: 'numbers'},
            {
                field: 'cflx', title: '查封类型', minWidth: 100,
                templet: function (d) {
                    return changeDmToMc(d.cflx, zdList.cflx);
                }
            },
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
            }, {field: 'zdzhmj', title: '宗地宗海面积(㎡)', minWidth: 140},
            {field: 'zdzhyt', title: '宗地宗海用途', minWidth: 120, templet: '#zdzhytTpl'},
            {field: 'zh', title: '幢号', minWidth: 100},
            {field: 'cfwh', title: '查封文号', minWidth: 200},
            {field: 'cfjg', title: '查封机关', minWidth: 150},
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
            {field: 'jfwh', title: '解封文号', minWidth: 200},
            {field: 'jfjg', title: '解封机关', minWidth: 150},
            {field: 'jfwj', title: '解封文件', minWidth: 150},
            {field: 'fj', title: '附记', minWidth: 250, event: 'editFj', style:'cursor: pointer;'},
            {field: 'cz', title: '操作', minWidth: 220, templet: '#bdcdyczTpl', fixed: 'right'}
        ];
    } else if (qllx === 96) {
        if (sfdydj) {
            /**
             * 预抵押数据列
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
                }, {field: 'zdzhmj', title: '宗地宗海面积(㎡)', minWidth: 140},
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
                {field: 'qdjg', title: '被担保主债权数额', minWidth: 200},
                {field: 'fj', title: '附记', minWidth: 250, event: 'editFj', style:'cursor: pointer;'},
                {field: 'cz', title: '操作', minWidth: 220, templet: '#bdcdyczTpl', fixed: 'right'}
            ];

        } else {

            /**
             * 预告数据列
             */
            cols = [
                {type: 'checkbox', width: 50, fixed: 'left'},
                {field: 'xh', title: '序号', width: 50, type: 'numbers'},
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
                }, {field: 'zdzhmj', title: '宗地宗海面积(㎡)', minWidth: 140},
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
                {field: 'jyje', title: '交易金额', minWidth: 200},
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
                {field: 'fj', title: '附记', minWidth: 250, event: 'editFj', style:'cursor: pointer;'},
                {field: 'cz', title: '操作', minWidth: 220, templet: '#bdcdyczTpl', fixed: 'right'}
            ];
        }
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
    } else {
        cols = [
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: 'xh', title: '序号', width: 50, type: 'numbers'},
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
            }, {field: 'zdzhmj', title: '宗地宗海面积(㎡)', minWidth: 140},
            {field: 'zdzhyt', title: '宗地宗海用途', minWidth: 120, templet: '#zdzhytTpl'},
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

/**
 * 根据登记原因控制页面权限初始化方法
 */
function initAuthorityByDjyy(djyyVal,djyyId,eventlx,type) {
    changeAuthorityByDjyy(djyyVal,djyyId,eventlx,type);
}

/**
 * 根据登记原因控制页面权限
 */
function changeAuthorityByDjyy(djyyVal,djyyId,eventlx,type) {
    qlxxChangeIds = "";
    if (eventlx ==="change"&&isNotBlank(editElementForDjyy)) {
        var editElement =editElementForDjyy;
        //设置原有元素不可编辑
        $.each(editElement.split(","), function (index, elementName) {
            //设置不可编辑
            var $element = $("[name='" + elementName + "']");
            if ($element.length > 0) {
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
    if (isNotBlank(djyyVal)) {
        getReturnData("/slym/xm/getAuthorityByDjyy", {djyy: djyyVal}, "GET", function (data) {
            if (isNotBlank(data)) {
                editElementForDjyy=data;
                $.each(data.split(","), function (index, elementName) {
                    //设置可编辑
                    var $element = $("[name='" + elementName + "']");
                    if ($element.length > 0) {
                        var tagName = $element[0].tagName;
                        $element.parents(".layui-input-inline").removeClass("bdc-one-icon");
                        var img = $element.parents(".layui-input-inline").find("img");
                        if (img.length > 0) {
                            $(img).remove();
                        }
                        $element.removeAttr("disabled");
                        if (tagName === "SELECT") {
                            form.render("select");
                            resetSelectDisabledCss();
                        }
                    }else{
                        qlxxChangeIds = data;
                    }
                });
            }
        }, function (error) {
            delAjaxErrorMsg(error);
        });
    }
}

//对比权籍按钮
function openDbqj(xmid) {
    var url = getContextPath()+"/view/synlpb/contractLpb.html?xmid=" + xmid + "&formStateId=" + formStateId + "&readOnly=" + readOnly;
    layerOpenFullReload(url, "对比信息");
}

//查看权籍附件
function showLpbFj() {
    getReturnData("/rest/v1.0/slym/lpbFjUrlByLc", {processInsId: processInsId}, "GET", function (data) {
        var index = layer.open({
            type: 2,
            title: "权籍附件",
            area: ['1150px', '600px'],
            fixed: false, //不固定
            maxmin: true, //开启最大化最小化按钮
            content: data
        });
        layer.full(index);
    }, function (error) {
        delAjaxErrorMsg(error);

    });


}

//楼盘表
function lpb() {
    addModel();
    var qjgldm ="";
    if(qlxxForPrint &&qlxxForPrint.length >0 &&qlxxForPrint[0].bdcXmFbDO != null){
        qjgldm =qlxxForPrint[0].bdcXmFbDO.qjgldm;
    }
    getReturnData("/slym/lpb", {processInsId: processInsId,qjgldm:qjgldm}, "GET", function (data) {
        removeModal();
        if (data && data.length > 0) {
            var url;
            if (data.length === 1) {
                url = "/building-ui/lpb/view?code=getbdcdyh&fwDcbIndex=" + data[0].fwDcbIndex + "&gzlslid=" + processInsId+"&qjgldm="+qjgldm;
            }else{
                url = "/realestate-accept-ui/view/lpb/lpbList.html?gzlslid=" + processInsId+"&qjgldm="+qjgldm;
            }
            var index = layer.open({
                type: 2,
                title: "楼盘表",
                area: ['1150px', '600px'],
                fixed: false, //不固定
                maxmin: true, //开启最大化最小化按钮
                content: url
            });
            layer.full(index);
        } else {
            showAlertDialog("未找到楼盘表");
        }

    }, function (error) {
        delAjaxErrorMsg(error);

    })
}

//从受理页面打开购物车
function openSlymGwc() {
    var index = layer.open({
        type: 2,
        title: "购物车",
        area: ['960px', '600px'],
        fixed: false, //不固定
        maxmin: true, //开启最大化最小化按钮
        content: '/realestate-accept-ui/hefei/slym/slymgwc.html?gzlslid=' + processInsId
    });
}

//单击楼盘表，勾选弹窗内的复选框，点击 确定 按钮，调用当前方法
function xzbdcdyhCallBack(data) {
    if(!sfscdj){
        //非首次登记不允许新增
        ityzl_SHOW_WARN_LAYER("当前流程不允许选择楼盘表新增！");
        return false;

    }
    layer.closeAll();
    if (data && data.length > 0) {
        addModel();
        //获取创建所需参数
        getReturnData("/bdcdyh/queryParams", {gzlslid: processInsId}, "GET", function (paramdata) {
            processDefKey =paramdata.processDefKey;
            jbxxid =paramdata.jbxxid;
            //验证并增量初始化
            checkBdcdyh(data, paramdata.processDefKey, paramdata.jbxxid);

        }, function (error) {
            delAjaxErrorMsg(error);
        }, false);
    }
}

//规则验证
function checkBdcdyh(bdcdydata, processDefKey, jbxxid) {
    //组织规则验证参数
    var selectDataList = [];
    for (var i = 0; i < bdcdydata.length; i++) {
        var selectData = bdcdydata[i];
        var bdcGzYzsjDTO = {};
        bdcGzYzsjDTO.bdcdyh = selectData.bdcdyh;
        bdcGzYzsjDTO.processDefKey = processDefKey;
        selectDataList.push(bdcGzYzsjDTO);
    }
    var bdcGzYzQO = {};
    bdcGzYzQO.zhbs = processDefKey + "_XZBDCDY";
    bdcGzYzQO.paramList = selectDataList;
    $.ajax({
        url: getContextPath() + '/bdcGzyz',
        type: 'POST',
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(bdcGzYzQO),
        success: function (data) {
            if (data.length > 0) {
                //展示限制信息
                showXzxx(data);
                //将选择不动产单元data放入，方便后续获取
                sessionStorage.bdcdydata =  JSON.stringify(bdcdydata);
            } else {
                //添加不动产单元
                addBdcdyh(bdcdydata, processDefKey, jbxxid);
            }

        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
        }
    });


}

//添加不动产单元
function addBdcdyh(bdcdydata, processDefKey, jbxxid) {
    var selectDataList = [];
    for (var i = 0; i < bdcdydata.length; i++) {
        var selectData = bdcdydata[i];
        var bdcSlYwxxDTO = {};
        bdcSlYwxxDTO.bdcdyh = selectData.bdcdyh;
        bdcSlYwxxDTO.zl = selectData.zl;
        bdcSlYwxxDTO.qlr = selectData.qlr;
        bdcSlYwxxDTO.qjid = selectData.qjid;
        bdcSlYwxxDTO.dyhcxlx = "1";
        bdcSlYwxxDTO.lx = "HS";
        bdcSlYwxxDTO.sfzlcsh = 1;
        selectDataList.push(bdcSlYwxxDTO);
    }
    var bdcCshSlxmDTO = {};
    bdcCshSlxmDTO.bdcSlYwxxDTOList = selectDataList;
    bdcCshSlxmDTO.gzldyid = processDefKey;
    bdcCshSlxmDTO.jbxxid = jbxxid;
    $.ajax({
        url: getContextPath() + "/addbdcdyh",
        type: 'POST',
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(bdcCshSlxmDTO),
        success: function (data) {
            zlcshSelectedXxSingle(jbxxid, processDefKey);
        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
        }
    });
}

//增量初始化
function zlcshSelectedXxSingle(jbxxid, processDefKey) {
    $.ajax({
        url: getContextPath() + '/slxxcsh/zlcsh',
        type: 'GET',
        dataType: 'json',
        data: {jbxxid: jbxxid, gzldyid: processDefKey,gzlslid:processInsId},
        success: function (data) {
            removeModal();
            loadBdcdyh();
            loadQlr();
        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr)
        }
    });

}

//展现限制信息
function showXzxx(yzResult) {
    removeModal();
    if (yzResult.length > 0) {
        loadTsxx(yzResult);
        layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
            var $ = layui.jquery,
                layer = layui.layer;
            warnLayer = layer.open({
                type: 1,
                title: '提示信息',
                skin: 'bdc-tips-right',
                shade: [0],
                area: ['600px'],
                offset: 'r', //右下角弹出
                time: 5000000, //2秒后自动关闭
                anim: 2,
                content: $('#tsxx').html(),
                success: function () {
                    $('.layui-layer-ico .layui-layer-close .layui-layer-close1').on('click', function () {
                        layer.close(warnLayer);
                        generate();
                    })
                },
                cancel: function () {
                    layer.close(warnLayer);
                    generate();
                }
            });
        });
    }

}

//验证信息忽略回调函数
function removeTsxxCallBack(){
    if(sessionStorage.bdcdydata != undefined) {
        var bdcdydata = JSON.parse(sessionStorage.bdcdydata);
        addBdcdyh(bdcdydata,processDefKey, jbxxid);
    }
}

/**
 * 获取登记原因与相应字段的对应关系
 */
function authorityMapByDjyyFun(){
    var obj = {};
    $.ajax({
        url: getContextPath() + '/slym/xm/authorityMapByDjyy',
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            authorityMapByDjyy = data;
        }
    });
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

//xq.js整理
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
            cancel: function () {
                //页面关闭刷新单元信息列表和批量权利信息页面
                $.when(loadBdcdyh(),loadPlQlxx()).done(function () {
                    var a = setInterval(function () {
                        if ($('.bdc-form-div .layui-show .bdc-table-box .layui-table-main>.layui-table').height() !== 0) {
                            renderForm();
                            getStateById(readOnly, formStateId, "slympl", "qlxxTab");
                            clearInterval(a);
                        }
                    }, 50);
                });

            }
        });
        layer.full(index);
    } else {
        ityzl_SHOW_INFO_LAYER("无原权利信息")
    }
}

//新增权利人展示(受理批量页面)
function addQlr(qllx) {
    var djlxArray = $(".layui-this").find("input[name='djlx']");
    var djlxInput = djlxArray[0];
    var djlx = $(djlxInput).val();
    var dydj = false;
    // 获取当前元素所在的tab页面，通过tab页找到权利信息下的djxl信息
    var djxl = $(".layui-this").find("input[name='djxl']").val();
    //判断是否是抵押类登记
    getReturnData("/slym/ql/checkDydj", {xmid: xmid}, 'GET', function (data) {
        dydj = data;
    }, function (err) {
        delAjaxErrorMsg(err);
    }, false);
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var form = layui.form;
        var url = getContextPath() + "/view/slym/qlr.html?xmid=" + xmid + "&lclx=" + lclx + "&processInsId=" + processInsId + "&formStateId=" + formStateId + "&qllx=" + qllx + "&zxlc=" + zxlc + "&djlx=" + djlx + "&djxl=" + djxl;
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
        disabledAddFa();
    })


}

// 常州人脸识别（批量）
function rlsbcz() {
    $.ajax({
        url: getContextPath() + '/rest/v1.0/rlsb/changzhou/get/rlsb/ywlx?gzlslid='+ processInsId,
        type: 'GET',
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        success: function (data) {
            if (data) {
                var url ='IdentityMatch://' + decodeURI(data.xmid) + '@' + decodeURI(data.ywlx);
                window.location.href = url;
            } else {
                layer.alert("<div style='text-align: center'>获取人脸识别业务类型失败</div>", {title: '提示'});
            }
        }
    });
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
    var $this = $(".layui-this");
    var djlx = $($this.find("input[name='djlx']")[0]).val();

    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var form = layui.form;
        var url = getContextPath() + "/view/slym/qlr.html?processInsId=" + processInsId + "&qlrid=" + qlrid + "&lclx=" + lclx + "&xmid=" + xmid + "&formStateId=" + formStateId + "&qllx=" + qllx + "&readOnly=" + readOnly + "&zxlc=" + zxlc+"&djlx="+djlx;
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
            area: ['960px', '610px'],
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
            area: ['960px', '610px'],
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

//按钮显示隐藏
function titleShowUi() {
    $(".print-btn").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }
        $("#jyxx").hide();
        $("#sqs").hide();
        $("#xwbl").hide();
        $("#moreBtn").hide();
        $("#dzzzxx").hide();
        $("#print").show();
        setUiWidth($(this), $("#print"));
    });

    $(".jyxx-btn").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }
        $("#print").hide();
        $("#sqs").hide();
        $("#xwbl").hide();
        $("#moreBtn").hide();
        $("#dzzzxx").hide();
        $("#jyxx").show();
        setUiWidth($(this), $("#jyxx"));
    });
    $(".sqs-btn").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }
        $("#print").hide();
        $("#xwbl").hide();
        $("#jyxx").hide();
        $("#moreBtn").hide();
        $("#dzzzxx").hide();
        $("#sqs").show();
        setUiWidth($(this), $("#sqs"));
    });

    $(".xwbl-btn").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }
        $("#print").hide();
        $("#sqs").hide();
        $("#jyxx").hide();
        $("#moreBtn").hide();
        $("#dzzzxx").hide();
        $("#xwbl").show();
        setUiWidth($(this), $("#xwbl"));
    });

    $(".more-btn").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }
        $("#print").hide();
        $("#sqs").hide();
        $("#jyxx").hide();
        $("#xwbl").hide();
        $("#dzzzxx").hide();
        $("#moreBtn").show();
        setUiWidth($(this), $("#moreBtn"));
        var Y = $(this).offset().left;
        $("#moreBtn").offset({left: Y - 66});
    });

    $(".dzzz-button").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }
        $("#print").hide();
        $("#sqs").hide();
        $("#jyxx").hide();
        $("#xwbl").hide();
        $("#moreBtn").hide();
        $("#dzzzxx").show();
        setUiWidth($(this), $("#dzzzxx"));
    });

    $(".print-btn li").click(function () {
        $("#print").hide();
    });

    $(".jyxx-btn li").click(function () {
        $("#jyxx").hide();
    });
    $(".sqs-btn li").click(function () {
        $("#sqs").hide();
    });
    $(".xwbl-btn li").click(function () {
        $("#xwbl").hide();
    });
    $(".more-btn li").click(function () {
        $("#moreBtn").hide();
    });

    $(".dzzz-button li").click(function () {
        $("#dzzzxx").hide();
    });

    $(document).click(function (e) {
        var e = e || window.event; //浏览器兼容性
        var elem = e.target || e.srcElement;
        while (elem) { //循环判断至跟节点，防止点击的是div子元素
            if (elem.id && elem.id == 'print') {
                return;
            }
            elem = elem.parentNode;
        }
        $("#print").hide();
    });
    $(document).click(function (e) {
        var e = e || window.event; //浏览器兼容性
        var elem = e.target || e.srcElement;
        while (elem) { //循环判断至跟节点，防止点击的是div子元素
            if (elem.id && elem.id == 'jyxx') {
                return;
            }
            elem = elem.parentNode;
        }
        $("#jyxx").hide();
    });
    $(document).click(function (e) {
        var e = e || window.event; //浏览器兼容性
        var elem = e.target || e.srcElement;
        while (elem) { //循环判断至跟节点，防止点击的是div子元素
            if (elem.id && elem.id == 'sqs') {
                return;
            }
            elem = elem.parentNode;
        }
        $("#sqs").hide();
    });
    $(document).click(function (e) {
        var e = e || window.event; //浏览器兼容性
        var elem = e.target || e.srcElement;
        while (elem) { //循环判断至跟节点，防止点击的是div子元素
            if (elem.id && elem.id == 'sqs') {
                return;
            }
            elem = elem.parentNode;
        }
        $("#xwbl").hide();
    });
    $(document).click(function (e) {
        var e = e || window.event; //浏览器兼容性
        var elem = e.target || e.srcElement;
        while (elem) { //循环判断至跟节点，防止点击的是div子元素
            if (elem.id && elem.id == 'moreBtn') {
                return;
            }
            elem = elem.parentNode;
        }
        $("#moreBtn").hide();
    });

    $(document).click(function (e) {
        var e = e || window.event; //浏览器兼容性
        var elem = e.target || e.srcElement;
        while (elem) { //循环判断至跟节点，防止点击的是div子元素
            if (elem.id && elem.id == 'dzzzxx') {
                return;
            }
            elem = elem.parentNode;
        }
        $("#dzzzxx").hide();
    });
}
function setUiWidth(buttonElement, uiElement) {
    var X = buttonElement.offset().top;
    var Y = buttonElement.offset().left;
    uiElement.offset({top: X + 40, left: Y - 0});
    var uiWidth = 90;
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
    uiElement.width(uiWidth);
}

//不动产单元信息详情页面
function showBdcdy(xmid) {
    layer.open({
        type: 2,
        title: "不动产单元信息",
        area: ['1200px', '390px'],
        fixed: false, //不固定
        content: getContextPath() + "/view/slym/bdcdy.html?xmid=" + xmid + "&formStateId=" + formStateId + "&readOnly=" + readOnly + "&zxlc=" + zxlc,
        btnAlign: "c",
        success: function () {
        }
    });
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
//根据审批来源控制按钮隐藏
function setSplyBtnAttr(){
    //互联网
    if(sply ===3 || sply ===5){
        // 涉税流程按钮不可编辑
        $('.sslc').css({'color':'#666','pointer-events':'none'});
    }
    //互联网涉税
    if(sply ===5){
        //获取交易信息不可编辑
        $(".queryJyxx").removeAttr("onclick");
        $(".queryJyxx").addClass("bdc-prohibit-btn");
    }
}

//对表单的一系列渲染操作
function renderForm(){
    //表单权限控制
    setSplyBtnAttr();
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
        addModel();
        setTimeout(function () {
            synchLpbDataToLc();
        }, 0);

    });

    //查看权籍附件
    $("#showLpbFj").click(function () {
        showLpbFj("");
    });

    //评价器
    $("#evaluate").click(function () {
        evaluate();
    });

    //是否由EMS推送
    $("#emsPush").click(function () {
        markEmsPush();
    });

    //一窗办理
    $("#tsYcsl").click(function () {
        syncYcsl();
    });

    // 查看变更记录
    $("#ckbgjl").click(function () {
        showBgjl();
    });

    // 生成权属证明
    $("#uploadQszm").click(function () {
        uploadQszm();
    });

    // 生成权属证明
    $("#xzlc").click(function () {
        xzlc();
    });

    // 获取合同信息
    $("#queryHtxxClf").click(function () {
        queryFcjyClfHtxx("clf", xmid)
    });

    // 获取合同信息
    $("#queryHtxxSpf").click(function () {
        queryFcjyClfHtxx("spf", xmid)
    });


    // 查看修正流程
    $("#ckxzlc").click(function () {
        showXzlc();
    });

    //一体化审批附件
    $("#ythspfj").click(function () {
        ythspfj();
    });
}

/**
 * 创建修正流程
 */
var xzly = '';

function xzlc() {
    addModel();
    getReturnData("/slxxcsh/process/param", {processInsId: processInsId}, "GET", function (data) {
        var bdcXmList = data.bdcXmList;
        //验证结束添加修正信息受理购物车数据
        xzly = 2;
        addXzXmShoppingCar(bdcXmList, data.jbxxid, data.xzlcGzldyid);
        lcCsh(data.jbxxid, data.xzlcGzldyid, data.slbh, true, false, true);
    }, function () {
        ityzl_SHOW_WARN_LAYER("创建修正流程失败");
    });

}

/*
* 正常流程创建
* */
function lcCsh(jbxxid, processDefKey, slbh, isFrame, needYz, tslc) {
    $.ajax({
        url: getContextPath() + '/slxxcsh',
        type: 'GET',
        dataType: 'json',
        async: false,
        cache: false,
        data: {
            jbxxid: jbxxid,
            gzldyid: processDefKey,
            slbh: slbh,
            tslc: tslc,
            cdlb: '0',
            cdqlr: '',
            cdcqzh: '',
            cdzl: '',
            xzly: xzly
        },
        success: function (data) {
            removeModal();
            if (data.msg === "success") {
                if (isFrame) {
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                    var url = "/realestate-portal-ui/view/new-page.html?taskId=" + data.taskId;
                    setTimeout(function () {
                        parent.location.href = url;
                    }, 500);
                    parent.location.href = url + "&timestamp=" + new Date().getTime();
                } else {
                    var url = "/realestate-portal-ui/view/new-page.html?taskId=" + data.taskId;
                    window.location.href = url;
                }
                removeModal();
            } else {
                removeModal();
                ityzl_SHOW_WARN_LAYER("初始化失败");
            }
        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
        }
    });
}

//添加修正信息到受理购物车数据
function addXzXmShoppingCar(bdcXmList, jbxxid, gzldyid) {
    var selectDataList = [];
    for (var i = 0; i < bdcXmList.length; i++) {
        var selectData = bdcXmList[i];
        var bdcSlYwxxDTO = {};
        bdcSlYwxxDTO.xmid = selectData.xmid;
        bdcSlYwxxDTO.bdcdyh = selectData.bdcdyh;
        bdcSlYwxxDTO.ybdcqz = selectData.bdcqzh;
        bdcSlYwxxDTO.zl = selectData.zl;
        bdcSlYwxxDTO.qlr = selectData.qlr;
        bdcSlYwxxDTO.bdcWlSlXmLsgxDOList = selectData.bdcWlSlXmLsgxDOList;
        bdcSlYwxxDTO.bdcSlYgDTOS = selectData.bdcSlYgDTOS;
        // bdcSlYwxxDTO.dyhcxlx = bdcslxztzpz.dyhcxlx;
        bdcSlYwxxDTO.qllx = parseInt(selectData.qllx);
        bdcSlYwxxDTO.sfzlcsh = null;
        bdcSlYwxxDTO.bdclx = selectData.bdclx;
        bdcSlYwxxDTO.fcjyBaxxDTO = selectData.fcjyBaxxDTO;
        bdcSlYwxxDTO.qlrsjly = selectData.qlrsjly;
        selectDataList.push(bdcSlYwxxDTO);
    }
    var bdcCshSlxmDTO = {};
    bdcCshSlxmDTO.bdcSlYwxxDTOList = selectDataList;
    bdcCshSlxmDTO.gzldyid = gzldyid;
    bdcCshSlxmDTO.jbxxid = jbxxid;
    getReturnData("/addbdcdyh", JSON.stringify(bdcCshSlxmDTO), "POST", function (data) {

    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    }, false);
}

//组织合同信息到批量页面
function generateHtxxPl(xmxx, name, index, qlTableId) {
    cxHtxx(xmxx).done(function (data) {
        var bdchtxxTpl;
        if (isNotBlank(data)) {
            if (data.htxx.fwlx == "clf") {
                bdchtxxTpl = document.getElementById("bdcClfHtxxTpl");
            } else {
                bdchtxxTpl = document.getElementById("bdcSpfHtxxTpl");
            }

        } else {
            bdchtxxTpl = document.getElementById("bdcClfHtxxTpl");
        }
        var json = {
            htxx: data,
            qlTableId: qlTableId,
            zd: zdList
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
        url: getContextPath() + "/ycsl/jyxx/cz/queryFcjyxxByHtbh",
        type: 'POST',
        dataType: 'json',
        data: {
            xmid: xmxx.xmid,
            htbh: xmxx.jyhth,
            sfck: true
        },
        success: function (data) {
            removeModal();
            if (isNotBlank(data) && isNotBlank(data.fwlx)) {
                if (data.fwlx == "clf") {
                    var clfData = filterClfHtxx(data);
                    deferred.resolve(clfData);
                } else {
                    var spfData = filterSpfHtxx(data);
                    deferred.resolve(spfData);
                }
            } else {
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
function filterClfHtxx(data) {
    var msrxx = [];
    var cmrxx = [];
    if (data.qlrxx.length > 0) {
        $.each(data.qlrxx, function (index, val) {
            if (val.qlrlb == "1") {
                msrxx.push(val);
            } else {
                cmrxx.push(val);
            }
        });
    }
    return {
        htxx: data,
        cmrxx: cmrxx,
        msrxx: msrxx
    };
}

// 过滤解析商品房合同信息数据
function filterSpfHtxx(data) {
    var cmrxx = [];
    cmrxx.push({
        qlrmc: data.cmr,
        zjzl: data.cmrzjzl,
        zjh: data.cmrzjh
    });
    var msrxx = [];
    msrxx.push({
        qlrmc: data.msrmc,
        zjzl: data.msrzjzl,
        zjh: data.msrzjh
    });
    return {
        htxx: data,
        cmrxx: cmrxx,
        msrxx: msrxx,
        dlrxx: {
            dlrmc: data.wtdlr,
            dlrzjh: data.wtdlrzjh,
            dlrzjzl: data.wtdlrzjzl
        }
    }
}

//不动产抵押权表格显示
function bdcdyaqxx(){
    if(qllx !=37){
        return false;
    }
    //获取不动产单元信息
    var url=getContextPath() + '/slym/ql/pllc/dyacqxx';
        table.render({
            elem: '#bdcdyxx',
            method: 'get',
            title: '不动产单元信息',
            defaultToolbar: ['filter'],
            toolbar:"toolbarBdcdyxx",
            page:false,
            url:getContextPath() + '/slym/ql/pllc/dyacqxx',
            where: {
                gzlslid:processInsId
            },
            cols: [
                [{field: 'xh', title: '序号',rowspan:2, width: 50, type: 'numbers'},
                    {field: 'xmid', title: '项目id',rowspan:2, width: 260, hide: true},
                    {field: 'bdcdyh', title: '不动产单元号',rowspan:2, width: 260, hide: true},
                    {field: 'qllx', title: '权力类型',rowspan:2, width: 260, hide: true},
                    {field: 'bdcqzh', title: '证号',rowspan:2, width: 300},
                    {field: 'zl', title: '坐落',rowspan:2, width: 260},
                    {field: 'zh', title: '幢号',rowspan:2, width: 100},
                    {field: 'fjh', title: '房号',rowspan:2, width: 100},
                    {field: 'fwjgmc', title: '结构',rowspan:2, width:160},
                    {field: 'zcs', title: '总层数',rowspan:2, width: 100},
                    {field: 'szmyc', title: '所在层',rowspan:2, width: 100},
                    {field: 'jzmj', title: '面积(㎡)', width: 60,colspan: 4},
                    {title: '用途', width: 100, colspan: 2},
                    {title: '权利性质', colspan: 2,width: 140},
                    {field: 'clsjsyqx', title: '土地使用期限',rowspan:2, width: 120},
                    {field: 'tdsyqssj', title: '土地使用起始时间',rowspan:2,  width: 200 },
                    {field: 'tdsyjssj', title: '土地使用结束时间', rowspan:2, width: 200 },
                    {field: 'tdsyqssj2', title: '土地使用起始时间2',rowspan:2,  width: 200 },
                    {field: 'tdsyjssj2', title: '土地使用结束时间2',rowspan:2,  width: 200},
                    {field: 'tdsyqssj3', title: '土地使用起始时间3',rowspan:2,  width: 200},
                    {field: 'tdsyjssj3', title: '土地使用结束时间3',rowspan:2,  width: 200}],
                [{field: 'jzmj', title: '房屋', width: 120},
                    {field: 'zdzhmj', title: '宗地面积', width: 120},
                    {field: 'dytdmj', title: '独用土地面积', width: 120},
                    {field: 'fttdmj', title: '分摊土地面积', width: 120},
                    {field: 'fwytmc', title: '房屋', width: 120},
                    {field: 'tdytmc', title: '土地', width: 120, },
                    {field: 'fwxzmc', title: '房屋', width: 120},
                    {field: 'qlxzmc', title: '土地', width: 120},
                    {field: 'cz', title: '操作', minWidth: 220, templet: '#bdcdyxxtb', fixed: 'right'}
                ]
            ],
            autoSort: false,
            parseData: function (res) {
                return {
                    code: res.code, //解析接口状态
                    msg: res.msg, //解析提示文本
                    count: res.totalElements, //解析数据长度
                    data:res.content//解析数据列表
                }
            },
            done: function (res) {
                var zmj = 0;
                for(var i=0;i<res.data.length;i++){
                    if (res.data[i].jzmj) {
                        var mj = parseFloat(res.data[i].jzmj)
                        mj = mj.toFixed(2);
                        mj *= 100;
                        zmj += mj;
                    }
                }
                zmj /= 100;
                $("#zmj").html(zmj);
                $("#ts").html(res.data.length);
            }
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