/**
 * Created by Ypp on 2019/9/6.
 * 南通收费信息
 */
var processInsId = getQueryString("processInsId");
var xmid = getQueryString("xmid");
var readOnly = getQueryString("readOnly");
var formStateId = getQueryString("formStateId");
var djxl = getQueryString("djxl");
//土地面积值
var tdmj = 0;
var wszt;
//收费项目列表数据
var getSfxmList;
//收费信息项目ID
var sfxxxmid;
//权利人义务人分别保存基本信息所需数据
var hasQlrJbxx = false,
    hasYwrJbxx = false,
    qlrSfxxId = '',
    getQlrlb = '',
    getQlrSfxwqy = false,
    getQlrSfayjs = false,
    ywrSfxxId = '',
    getYwrlb = '',
    getYwrSfxwqy = false,
    getYwrSfayjs = false,
    qlrjfrxm = '',
    ywrmc = '',
    ywrjsfrxm = '',
    qlrsfxmsxh = '',
    ywrsfxmsxh = '';
var zdList ={djlx: []};
var laytpl, form;
layui.use(['jquery', 'laydate', 'form', 'laytpl'], function () {
    var laydate = layui.laydate,
        $ = layui.jquery;
    laytpl = layui.laytpl;
    form = layui.form;

    $(function () {
        //程序计算数量
        var bdcSlSfxmSlDTO={};

        //查询字典
        getZd();

        //渲染收费信息
        window.renderSfxx = function() {
            var sfxxpz;
            $.ajax({
                url: getContextPath() + "/sf/xx/nantong/pz",
                type: 'GET',
                dataType: 'json',
                async: false,
                success: function (data) {
                    sfxxpz = data;
                }
            });

            // 查询收费信息(包含作废数据),以此判断权利人和义务人收费信息是否展示
            // $.ajax({url: getContextPath() + "/sf/xx/nantong/bhzf",
            //     type: 'GET',
            //     dataType: 'json',
            //     data: {xmid: xmid, processInsId: processInsId},
            //     success: function (data) {
            //         if(data){
            //             //单个流程和批量流程是一个收费单 如果这个收费单作废则权利人和义务人收费项目模块全部隐藏
            //             if(data.length == 1 && data[0].sfzf == 1){
            //                 $("#ywrSfxm").hide();
            //                 $("#qlrSfxm").hide();
            //             }
            //             //组合流程，循环判断权利人和义务人收费项目模块是否隐藏
            //             if(data.length >1){
            //                 $.each(data, function(index,item){
            //                     if(item.sfzf == 1 && item.qlrlb == 1){
            //                         $("#qlrSfxm").hide();
            //                     }
            //                     if(item.sfzf == 1 && item.qlrlb == 2){
            //                         $("#ywrSfxm").hide();
            //                     }
            //                 })
            //             }
            //         }
            //     },
            //     error: function (xhr, status, error) {
            //         delAjaxErrorMsg(xhr)
            //     }
            // });
            $.ajax({
                url: getContextPath() + "/sf/sfxx/nantong",
                type: 'GET',
                dataType: 'json',
                async: false,
                data: {gzlslid: processInsId, xmid: xmid},
                success: function (data) {
                    generateMsfSwxx(data, "1");
                    generateMsfSwxx(data, "2");
                }
            })
            $.ajax({
                url: getContextPath() + "/sf/xx/nantong",
                type: 'GET',
                dataType: 'json',
                data: {xmid: xmid, processInsId: processInsId},
                success: function (data) {
                    tdmj = data.tdsyqmj;
                    sfxxxmid=data.xmid;
                    renderSfxmList();

                    var qlrData = JSON.parse(JSON.stringify(data));
                    //渲染字典项
                    //登记类型
                    qlrData.djlx = changeDmToMc(parseInt(qlrData.djlx), zdList.djlx);
                    if (null != qlrData.bdcSlQlrSfxxDTO){
                        qlrData['renderList'] = qlrData.bdcSlQlrSfxxDTO;
                        qlrData['zd'] = zdList;
                        qlrData['sfxxpz'] = sfxxpz;
                        hasQlrJbxx = true;
                        qlrSfxxId = qlrData.renderList.sfxxid;
                        getQlrlb = qlrData.renderList.qlrlb;
                        getQlrSfxwqy = qlrData.renderList.sfxwqy;
                        getQlrSfayjs = qlrData.renderList.sfayjs;

                        //渲染基本信息
                        renderSfxxList(qlrData, '.bdc-jbxx-view', '#jbxxSfrq');
                        if (isNotBlank(qlrData.renderList.sfzt) && qlrData.renderList.sfzt == 2 && wszt == 1) {
                            $('#qlrSfxm .bdc-jfxzt').html("交易完成");
                        }else{
                            if(isNotBlank(qlrData.renderList.sfzt) && qlrData.renderList.sfzt == 2){
                                $('#qlrSfxm .bdc-jfxzt').html("已缴费");
                                $('#qlrSfxm .bdc-jfxzt').css("color", "#000000");
                            }else{
                                $('#qlrSfxm .bdc-jfxzt').html("未缴费");
                                $('#qlrSfxm .bdc-jfxzt').css("color", "#f24b43");
                            }
                        }
                        $('#qlrSfxm .bdc-jfxmc').html('缴费人姓名（名称）：'+ qlrData.renderList.mc);
                        qlrjfrxm =qlrData.renderList.mc;

                        //渲染权利人收费项目表格
                        renderSfxm(qlrData.renderList.sfxxid, '#qlrSfxm table','1',qlrData.renderList);
                        //月结的机构推送按钮不可点击
                        if(qlrData.renderList.sfayjs) {
                            $('#qlrSfxm .qlrtssfxx').removeAttr('onclick');
                            $('#qlrSfxm .qlrtssfxx')[0].classList.add('bdc-prohibit-btn');
                        }


                    }

                    var ywrData = JSON.parse(JSON.stringify(data));
                    if (ywrData.bdcSlYwrSfxxDTO != null) {
                        ywrData['renderList'] = ywrData.bdcSlYwrSfxxDTO;
                        ywrData['zd'] = zdList;

                        if(isNotBlank(ywrData.renderList.sfzt) && ywrData.renderList.sfzt == 2){
                            $('#ywrSfxm .bdc-jfxzt').html("已缴费");
                            $('#ywrSfxm .bdc-jfxzt').css("color", "#000000");
                        }else{
                            $('#ywrSfxm .bdc-jfxzt').html("未缴费");
                            $('#ywrSfxm .bdc-jfxzt').css("color", "#f24b43");
                        }
                        $('#ywrSfxm .bdc-jfxmc').html('缴费人姓名（名称）：'+ ywrData.renderList.mc);
                        ywrjsfrxm =ywrData.renderList.mc;
                        ywrmc = ywrData.renderList.mc;
                        hasYwrJbxx = true;
                        ywrSfxxId = ywrData.renderList.sfxxid;
                        getYwrlb = ywrData.renderList.qlrlb;
                        getYwrSfxwqy = ywrData.renderList.sfxwqy;
                        getYwrSfayjs = ywrData.renderList.sfayjs;

                        if(!hasQlrJbxx){
                            //渲染基本信息
                            renderSfxxList(ywrData, '.bdc-jbxx-view', '#jbxxSfrq');
                        }

                        //渲染义务人收费项目表格
                        renderSfxm(ywrData.renderList.sfxxid, '#ywrSfxm table','2',ywrData.renderList);
                        //月结的机构推送按钮不可点击
                        if(ywrData.renderList.sfayjs) {
                            $('#ywrSfxm .ywrtssfxx').removeAttr('onclick');
                            $('#ywrSfxm .ywrtssfxx')[0].classList.add("bdc-prohibit-btn");
                        }

                        // }
                    }else{
                        if(ywrData.bdcSlYwrSfxxDTO !== null && isNotBlank(ywrData.bdcSlYwrSfxxDTO.mc)) {
                            ywrmc = ywrData.bdcSlYwrSfxxDTO.mc;
                        }
                        //没有义务人收费信息,默认隐藏
                        $("#ywrSfxm").hide();
                    }

                    if(!hasQlrJbxx && !hasYwrJbxx){
                        $('#jbSfxx').hide();
                    }
                    //添加权限控制
                    getStateById(readOnly, formStateId, "sfxx");
                    setZfAndChActive();

                },
                error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr)
                }
            });
        }
        //渲染收费信息
        renderSfxx();

        //查询字典
        function getZd() {
            getReturnData("/bdczd", '', 'POST', function (data) {
                if (isNotBlank(data)) {
                    zdList = data;
                }
            }, function (err) {
                delAjaxErrorMsg(err);
            }, false);
        }



        /**
         * 根据获取的数据动态渲染收费信息
         * @param sfxxData 权利人、义务人渲染数据
         * @param view   渲染模板view
         * @param sfsj   收费时间id
         */
        function renderSfxxList(sfxxData, view, sfsj) {
            var getTpl = qlrxxTpl.innerHTML;
            laytpl(getTpl).render(sfxxData, function (html) {
                $(view).html(html);
                form.render();
                lay('.test-item').each(function () {
                    laydate.render({
                        elem: sfsj,
                        type: 'datetime',
                        trigger: 'click',
                        value: sfxxData.renderList.sfsj
                    });
                });
            });
        }

        //渲染收费项目列表
        function renderSfxm(sfxxid, view, qlrlb,sfxx) {
            $.ajax({
                url: getContextPath() + "/sf/xm/new",
                type: 'GET',
                dataType: 'json',
                data: {sfxxid: sfxxid},
                success: function (data) {
                    data.forEach(function (v) {
                        v['sfxmList'] = getSfxmList;
                        //登记费和工本费初始化加载需要程序计算数量
                        if ((isNotBlank(v.sfxmmc) && (v.sfxmmc.indexOf("登记费") > -1 || v.sfxmmc.indexOf("工本费") > -1 || v.sfxmmc.indexOf("土地使用权交易服务费") > -1)) && v.sl === null) {
                            //获取数量,如果已经获取过不再重新获取
                            if ($.isEmptyObject(bdcSlSfxmSlDTO)) {
                                getReturnData("/sf/sfxm/sl/nantong", {
                                    gzlslid: processInsId,
                                    djxl: djxl
                                }, "GET", function (data) {
                                    if (data) {
                                        bdcSlSfxmSlDTO = data;
                                    }
                                }, function (error) {
                                    delAjaxErrorMsg(error);
                                }, false);
                            }
                            if (v.sfxmbz.indexOf("工本费") > -1) {
                                if (v.jsff === "4") {
                                    v['sl'] = bdcSlSfxmSlDTO.gbfslAll;
                                } else {
                                    v['sl'] = bdcSlSfxmSlDTO.gbfsl;
                                }
                            } else if (isNotBlank(v.sfxmbz) && v.sfxmbz.indexOf("非住宅") > -1) {
                                v['sl'] = bdcSlSfxmSlDTO.fzzdjfsl;
                            } else if (isNotBlank(v.sfxmbz) && v.sfxmbz.indexOf("住宅") > -1) {
                                v['sl'] = bdcSlSfxmSlDTO.zzdjfsl;

                            } else if (v.sfxmmc.indexOf("土地使用权交易服务费") > -1) {
                                if (v.jsff === "1") {
                                    //取住宅面积和
                                    v['sl'] = bdcSlSfxmSlDTO.zzmjsl;
                                } else if (v.jsff === "2") {
                                    //取非住宅面积和
                                    v['sl'] = bdcSlSfxmSlDTO.fzzmjsl;

                                }else if(v.jsff ==="3"){
                                    //取总权利面积和
                                    v['sl'] = bdcSlSfxmSlDTO.tdsyjsl;

                                }else{
                                    v['sl'] = 0;
                                }
                            }

                        }
                        getSfxmList.forEach(function (value) {
                            if (value.sfxmdm == v.sfxmdm) {
                                value.bdcSlSfxmSfbzList.forEach(function (item) {
                                    if (item.sfxmbz == v.sfxmbz) {
                                        v['sfxmdj'] = item.sfxmdj;
                                    }
                                });
                            }
                        });
                        //计算应收金额,实收金额
                        var getShje = calculateFloat(parseFloat(v.sfxmdj) * v.sl);

                        //应收金额
                        v['ysje'] = getShje;
                        //实收金额
                        v['ssje'] = getShje - v.jmje;
                        if(qlrlb === '1') {
                            qlrsfxmsxh = v.xh;
                        }
                        if(qlrlb === '2') {
                            ywrsfxmsxh = v.xh;
                        }

                    });

                    var getTpl = sfxmTpl.innerHTML;
                    laytpl(getTpl).render(filterNotNull(data), function (html) {
                        $(view).html(html);
                        computeTotal($(view));
                        form.render();
                        if(sfxx &&isNotBlank(sfxx.jfsbm)){
                            //已推送状态下，收费信息不可编辑
                            setSfEditElement(qlrlb);
                        }
                    });

                },
                error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr)
                }
            });
        }

        // 页面展示时过滤收费项目数据，对数量为0的数据不进行展现。
        function filterNotNull(data){
            var notNullData = new Array();
            $.each(data,function(index,value){
                if(value.sl !== 0 && value.sl !== null){
                    notNullData.push(value);
                }
            });
            return notNullData;
        }

        //获取收费项目列表
        function renderSfxmList() {
            $.ajax({
                url: getContextPath() + "/sf/xm/pz/all",
                type: 'GET',
                dataType: 'json',
                data: {processInsId: processInsId, xmid: xmid},
                async: false,
                success: function (data) {
                    getSfxmList = data;
                },
                error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr)
                }
            });
        }

        //监听收费项目选择
        form.on('select(sfxmFilter)', function (data) {
            var sfxm = $(data.elem).find('option:selected').text();
            data.othis.parents('tr').find('.bdc-sfxmmc').val(sfxm);
            getSfxmList.forEach(function (v) {
                if (v.sfxmdm == data.value) {
                    var bzHtml = '';
                    var sfdj, sfbz;
                    v.bdcSlSfxmSfbzList.forEach(function (value, index) {
                        if (index == 0) {
                            sfdj = value.sfxmdj;
                            sfbz = value.sfxmbz;
                        }
                        bzHtml += '<option value="'+ value.sfxmdj +'" data-dj="'+ value.sfxmdj +'">'+ value.sfxmbz +'</option>';
                    });
                    data.othis.parents('tr').find('.bdc-sfxmbz').val(sfbz);
                    var $parentTr = data.othis.parents('tr');
                    var $parentTable = data.othis.parents('table');
                    var $sfdj = $parentTr.find('.bdc-sfdj');
                    $sfdj.html(bzHtml);
                    form.render('select');

                    //应收金额
                    //土地使用权交易服务费特殊计算,根据标准单价乘以土地面积
                    if (sfxm == "土地使用权交易服务费") {
                        var getShje = calculateFloat(parseFloat(sfdj) * tdmj);
                        $parentTr.find('.bdc-sfsl').val(tdmj);
                    } else {
                        if ($parentTr.find('.bdc-sfsl').val() == tdmj) {
                            $parentTr.find('.bdc-sfsl').val(0);
                        }
                        //数量
                        var sl = $parentTr.find('.bdc-sfsl').val();
                        var getShje = calculateFloat(parseFloat(sfdj) * parseFloat(sl));
                    }
                    //减免金额
                    var jmje = $parentTr.find('.bdc-jmje').val();
                    $parentTr.find('.bdc-ysje').val(getShje);
                    $parentTr.find('.bdc-ysje-input').val(getShje);
                    $parentTr.find('.bdc-ssje').val(getShje - jmje);
                    $parentTr.find('.bdc-ssje-input').val(getShje - jmje);
                    computeTotal($parentTable);
                }
            });
        });
        //监听收费标准选择
        form.on('select(sfdjFilter)', function (data) {
            var sfbz = $(data.elem).find('option:selected').text();
            var sfdj = $(data.elem).find('option:selected').data('dj');
            var $parentTr = data.othis.parents('tr');
            $parentTr.find('.bdc-sfxmbz').val(sfbz);
            var $parentTable = data.othis.parents('table');
            //数量
            var sl = $parentTr.find('.bdc-sfsl').val();
            //减免金额
            var jmje = $parentTr.find('.bdc-jmje').val();
            var getYsje = calculateFloat(parseFloat(sl) * sfdj);
            $parentTr.find('.bdc-ysje').val(getYsje);
            $parentTr.find('.bdc-ysje-input').val(getYsje);
            var getSsje = calculateFloat(getYsje - jmje);
            $parentTr.find('.bdc-ssje').val(getSsje);
            $parentTr.find('.bdc-ssje-input').val(getSsje);
            computeTotal($parentTable);
        });

        //监听保存
        var isSubmit = true;
        form.on("submit(saveData)", function (data) {
            if (!isSubmit) {
                layer.msg('必填项不能为空', {icon: 5, time: 3000});
                isSubmit = true;
                return false;
            } else {
                addModel();
                setTimeout(function () {
                    try {
                        $.when(saveSfxx(), saveSfxm()).done(function () {
                            removeModal();
                            ityzl_SHOW_SUCCESS_LAYER("保存成功");
                        })
                    } catch (e) {
                        removeModal();
                        ERROR_CONFIRM("保存失败", e.message);
                        return
                    }
                }, 10);
                return false;
            }

        });

        //保存收费项目
        function saveSfxm() {
            var sfxmList = [];
            $(".bdcsfxxForm").each(function () {
                //收费项目保存
                var tab = $(this).find(".bdc-table-box tr");
                $(this).find(".bdc-table-box tr").each(function () {
                    var sfxmArray = $(this).find(".sfxm").serializeArray();
                    if (sfxmArray !== null && sfxmArray.length > 0) {
                        var sfxm = {};
                        sfxmArray.forEach(function (item, index) {
                            sfxm[item.name] = item.value;
                        });
                        //金额单位页面不可编辑，全部都是1
                        sfxm.jedw = 1;
                        // 南通对收费金额不为0的项目进行保存与展示
                        if (sfxm.sfxmmc !== null && sfxm.sfxmmc !== "" && sfxm.sl != 0) {
                            sfxmList.push(sfxm);
                        }
                    }
                });
            });
            if (isNotBlank(sfxmList)) {
                $.ajax({
                    url: getContextPath() + "/sf/xm",
                    type: 'PATCH',
                    dataType: 'json',
                    async: false,
                    contentType: "application/json",
                    data: JSON.stringify(sfxmList),
                    success: function (data) {
                        renderSfxx();
                    },
                    error: function (xhr, status, error) {
                        delAjaxErrorMsg(xhr)
                    }
                });
            }
        }
    });
});

/**
 * 处理小数计算问题，四舍五入保留两位小数
 * 需要引用math.js
 * @param equation  计算公式
 */
function calculateFloat(equation) {
    var result = math.format(math.evaluate(equation), 14);
    return formatFloat(parseFloat(result));
}

//处理小数问题
function formatFloat(f) {
    if((f + '').indexOf('.') != -1){
        var d, carryD, i,
            ds = f.toString().split('.'),
            len = ds[1].length,
            b0 = '', k = 0;

        if (len > 2) {
            while(k < 2 && ds[1].substring(0, ++k) == '0') {
                b0 += '0';
            }
            d = Number(ds[1].substring(0, 2));
            // 判断保留位数后一位是否需要进1
            carryD = Math.round(Number('0.' + ds[1].substring(2, len)));
            len = (d + carryD).toString().length;
            if (len > 2) {
                return Number(ds[0]) + 1;
            } else if (len == 2) {
                return Number(ds[0] + '.' + (d + carryD));
            }
            return Number(ds[0] + '.' + b0 + (d + carryD));
        }
    }
    return f;
}
//修改数量
function changeSl(elem) {
    var sl = $(elem).val();
    if (sl === null || sl === "") {
        ityzl_SHOW_INFO_LAYER("请填写数量");
    } else {
        var $parentTr = $(elem).parents('tr');
        //先判断 收费项目 是不是土地使用权交易服务费
        var sfxm = $parentTr.find('.bdc-sfxm option:selected').text();

        var sfdjIndex = $parentTr.find(".bdc-sfdj").siblings('.layui-form-select').find('dd.layui-this').index() + 1;
        var sfdj = $parentTr.find(".bdc-sfdj option:nth-child("+ sfdjIndex +")").data('dj');
        var jmje = $parentTr.find('.bdc-jmje').val();
        if (sfxm == "土地使用权交易服务费") {
            var getShje = calculateFloat(parseFloat(sfdj)  * parseFloat(sl));
        } else {
            var getShje = calculateFloat(parseFloat(sfdj) * sl);
        }

        //应收金额
        var $ysjeEl = $parentTr.find(".bdc-ysje");
        $ysjeEl.val(getShje);
        $parentTr.find('.bdc-ysje-input').val(getShje);
        //实收金额
        var $ssjeEl = $parentTr.find(".bdc-ssje");
        $ssjeEl.val(calculateFloat(getShje - jmje));
        $parentTr.find('.bdc-ssje-input').val(calculateFloat(getShje - jmje));
        //合计
        computeTotal($(elem).parents('table'));
    }
}

//获取总计
function computeTotal($table) {
    var hj = 0;
    var $ssjeList = $table.find('.bdc-ssje');
    $ssjeList.each(function (i) {
        hj += parseFloat($($ssjeList[i]).val());
    });
    $table.find('.bdc-hj').val(calculateFloat(hj));
}

//修改减免金额
function changeJmje(elem) {
    var $parentTr = $(elem).parents('tr');
    var $parentTable = $(elem).parents('table');
    var getYsje = $parentTr.find('.bdc-ysje').val();
    $parentTr.find('.bdc-ysje-input').val(getYsje);
    var jmje = $(elem).val();
    //实收金额
    var $ssjeEl = $parentTr.find(".bdc-ssje");
    $ssjeEl.val(calculateFloat(parseFloat(getYsje) - jmje));
    $parentTr.find('.bdc-ssje-input').val(calculateFloat(parseFloat(getYsje) - jmje));
    //合计
    computeTotal($parentTable);
}

//设置作废冲红等按钮可编辑
function setZfAndChActive(){
    $(".bdc-zf-sfxx").removeAttr("disabled");
    $(".bdc-ch-sfxx").removeAttr("disabled");
    $(".bdc-tssf-sfxx").removeAttr("disabled");
    $(".bdc-zf-sfxx").removeClass("layui-btn-disabled");
    $(".bdc-ch-sfxx").removeClass("layui-btn-disabled");
    $(".bdc-tssf-sfxx").removeClass("layui-btn-disabled");
    $(".bdc-zf-sfxx").removeClass("bdc-prohibit-btn");
    $(".bdc-ch-sfxx").removeClass("bdc-prohibit-btn");
    $(".bdc-tssf-sfxx").removeClass("bdc-prohibit-btn");
    $("#tfyy").removeAttr("disabled");
    $("#qlr-zfsfxx").attr("onclick", "zfsfxx('1')");
    $("#ywr-zfsfxx").attr("onclick", "zfsfxx('2')");
    $("#ywr-chsfxx").attr("onclick", "chsfxx('2')");
    $("#qlr-chsfxx").attr("onclick", "chsfxx('1')");
    $("#qlr-tssf").attr("onclick", "tssf('1')");
    $("#ywr-tssf").attr("onclick", "tssf('2')");
}

/*
* 挂接收费信息
* */
function gjSfxx(qlrlb) {
    //判断是否线上缴费
    var jkfs = $("#jkfs").val();
    if (jkfs !== "线上缴费") {
        ityzl_SHOW_WARN_LAYER("非线上缴费不允许挂接，请先修改缴款方式");
        return false;
    }
    //保存收费信息
    saveSfxx();
    //弹框输入受理编号
    layer.open({
        title: '挂接选择受理编号',
        type: 1,
        area: ['430px'],
        btn: ['确定', '取消'],
        content: $('#gjsfxx'),
        yes: function (index, layero) {
            addModel("");
            var slbh = $("#gjslbh").val();
            if (isNullOrEmpty(slbh)) {
                ityzl_SHOW_WARN_LAYER("请输入受理编号");
                return false;
            }
            var sfxxid = "";
            if (qlrlb === "1") {
                sfxxid = qlrSfxxId;
            } else {
                sfxxid = ywrSfxxId;
            }
            getReturnData("/sf/gjsfxx", {slbh: slbh, qlrlb: qlrlb, sfxxid: sfxxid}, "GET", function (data) {
                removeModal();
                layer.close(index);
                ityzl_SHOW_SUCCESS_LAYER("挂接成功");
                //重新加载收费信息页面
                renderSfxx();
            }, function (xhr) {
                delAjaxErrorMsg(xhr);
            })

        },
        btns: function (index, layero) {
            layer.close(index);
        }
        , success: function () {
            //清空已有值
            $('#gjslbh').val('');
            //自动获取焦点
            $('#gjslbh').focus();
        }
    });


}

//推送收费信息
function tssfxx(qlrlb) {
    var processInsId = getQueryString("processInsId");
    var xmid = getQueryString("xmid");

    $.ajax({
        url: getContextPath() + "/sf/xx/nantong",
        type: 'GET',
        dataType: 'json',
        data: {xmid: xmid, processInsId: processInsId},
        success: function (data) {
            var sfxxid = "";

            if (qlrlb === '1') {
                var qlrData = JSON.parse(JSON.stringify(data));
                //渲染字典项
                //登记类型
                if (qlrData.bdcSlQlrSfxxDTO != null) {
                    sfxxid = qlrData.bdcSlQlrSfxxDTO.sfxxid;
                }
            }
            if (qlrlb === '2') {
                var ywrData = JSON.parse(JSON.stringify(data));
                if (ywrData.bdcSlYwrSfxxDTO != null) {
                    sfxxid = ywrData.bdcSlYwrSfxxDTO.sfxxid;
                }
            }

            if (sfxxid === "" || sfxxid === null) {
                warnMsg("没有可推送数据 请添加收费信息后保存")
            } else {
                $.ajax({
                    url: getContextPath() + "/sf/xx/ts",
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        sfxxid: sfxxid,
                        qlrlb: qlrlb,
                        gzlslid: processInsId
                    },
                    success: function (data) {
                        if (data &&data.success === true){
                            ityzl_SHOW_SUCCESS_LAYER("推送成功");
                            setSfEditElement(qlrlb);
                        } else {
                            warnMsg("推送失败");
                        }
                    }
                });
            }
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}


function showTfyyModal() {
    var deferred = $.Deferred();
    $("#tfyy").val("");
    layer.open({
        title: '退付原因',
        type: 1,
        area: ['430px'],
        btn: ['确定', '取消'],
        content: $('#tfyy-div'),
        yes: function(index, layero){
            var tfyy = $("#tfyy").val();
            layer.close(index);
            deferred.resolve(tfyy);
        },
        btn2: function(index, layero){
            layer.close(index);
            deferred.reject();
        }
    });
    return deferred;
}

//作废
function zfsfxx(qlrlb){
    var processInsId = getQueryString("processInsId");
    var xmid = getQueryString("xmid");

    showTfyyModal().done(function (tfyy) {
        addModel('作废中');
        var sfxx = { xmid: xmid, qlrlb: qlrlb, gzlslid: processInsId, tfyy: tfyy};

        // 判断接口调用模式
        var sftsfsxt = "";
        getReturnData("/sf/nantong/sfmode/name",{gzlslid: processInsId, configName: "fs.config.zf.beanName"},"GET",function (data){
            sftsfsxt = data;
        },function (error){
            delAjaxErrorMsg(error);
            return false;
        },false);

        /**
         * 南通区县作废接口的fs.config.zf.beanName=fs_srpt_zfdjfxx
         * 南通市区作废接口的fs.config.zf.beanName=fs_jfpt_zfdjfxx，当sfxx.zf.sfdyjk=false时不调用作废接口，直接作废
         */
        if(isNotBlank(sftsfsxt) && sftsfsxt.indexOf("fs_srpt_zfdjfxx") > -1){
            // 通过beanName调用方式（南通区县作废）
            zfByBeanName(sfxx);
        }else{
            // 原有接口模式（南通市区作废）
            zfByBeanNameOld(sfxx);
        }
    });
}
// 新接口模式，beanName=fs_srpt_zfdjfxx调用exchange进行作废
function zfByBeanName(sfxx){
    $.ajax({
        url: getContextPath() + "/sf/xx/zfdjfxx",
        type: 'POST',
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(sfxx),
        success: function (data) {
            removeModal();
            if (data && data.success === true){
                ityzl_SHOW_SUCCESS_LAYER("作废成功");
            } else {
                warnMsg("作废失败");
            }
        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
        }
    });
}

// 原有接口模式，修改为beanName=fs_jfpt_zfdjfxx调用exchange进行作废，sfxx.zf.sfdyjk=false时不调用作废接口，直接作废
function zfByBeanNameOld(sfxx){
    // 原有接口模式
    $.ajax({
        url: getContextPath() + "/sf/xx/zf?sfch=false",
        type: 'POST',
        dataType: 'json',
        contentType: "application/json;charset=utf-8",
        data: JSON.stringify(sfxx),
        success: function (data) {
            removeModal();
            if (isNotBlank(data)){
                renderSfxx();
                if(qlrlb ==="1"){
                    $("#qlr-addSfxm").removeAttr("disabled");
                    $("#qlr-addSfxm").removeClass("bdc-prohibit-btn");
                    $("#qlr-addSfxm").attr("onclick","addSfxm(this,'1')");

                    $("#qlr-tssfxx").removeAttr("disabled");
                    $("#qlr-tssfxx").removeClass("bdc-prohibit-btn");
                    $("#qlr-tssfxx").attr("onclick","tssfxx('1')");

                }else{
                    $("#ywr-addSfxm").removeAttr("disabled");
                    $("#ywr-addSfxm").removeClass("bdc-prohibit-btn");
                    $("#ywr-addSfxm").attr("onclick","addSfxm(this,'2')");

                    $("#ywr-tssfxx").removeAttr("disabled");
                    $("#ywr-tssfxx").removeClass("bdc-prohibit-btn");
                    $("#ywr-tssfxx").attr("onclick","tssfxx('1')");
                }
                ityzl_SHOW_SUCCESS_LAYER("作废成功");
                printTfsqs(data.gzlslid,data.xmid, data.sfxxid, "tfsqs", qlrlb);
            } else {
                ityzl_SHOW_WARN_LAYER("作废失败");
            }
        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
        }
    });
}

//冲红
function chsfxx(qlrlb){
    var processInsId = getQueryString("processInsId");
    var xmid = getQueryString("xmid");

    showTfyyModal().done(function (tfyy) {
        var sfxx = { xmid: xmid, qlrlb: qlrlb, gzlslid: processInsId, tfyy: tfyy };
        addModel('作废中');
        $.ajax({
            url: getContextPath() + "/sf/xx/zf?sfch=true",
            type: 'POST',
            dataType: 'json',
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(sfxx),
            success: function (data) {
                removeModal();
                if (isNotBlank(data)){
                    renderSfxx();
                    if(qlrlb ==="1"){
                        $("#qlr-addSfxm").removeAttr("disabled");
                        $("#qlr-addSfxm").removeClass("bdc-prohibit-btn");
                        $("#qlr-addSfxm").attr("onclick","addSfxm(this,'1')");

                        $("#qlr-tssfxx").removeAttr("disabled");
                        $("#qlr-tssfxx").removeClass("bdc-prohibit-btn");
                        $("#qlr-tssfxx").attr("onclick","tssfxx('1')");

                    }else{
                        $("#ywr-addSfxm").removeAttr("disabled");
                        $("#ywr-addSfxm").removeClass("bdc-prohibit-btn");
                        $("#ywr-addSfxm").attr("onclick","addSfxm(this,'2')");

                        $("#ywr-tssfxx").removeAttr("disabled");
                        $("#ywr-tssfxx").removeClass("bdc-prohibit-btn");
                        $("#ywr-tssfxx").attr("onclick","tssfxx('1')");
                    }
                    ityzl_SHOW_SUCCESS_LAYER("作废成功");
                    addModel("冲红中");
                    data.qlrlb = qlrlb;
                    var billFormElectInvoiceTool = BillFormElectInvoiceTool.getInstance({
                        url: '127.0.0.1:31018', svrRandom: '', sessionId: processInsId
                    });
                    // 判断当前websocket连接状态
                    if(billFormElectInvoiceTool.isConnect()){
                        getCA(billFormElectInvoiceTool, data);
                    }else{
                        billFormElectInvoiceTool.onopen = function () {
                            getCA(billFormElectInvoiceTool, data);
                        };
                        billFormElectInvoiceTool.connect();
                    }
                } else {
                    ityzl_SHOW_WARN_LAYER("作废失败");
                }
            },
            error: function (xhr, status, error) {
                removeModal();
                delAjaxErrorMsg(xhr);
            }
        });
    });
}

var serialNum = "";
function getCA(billFormElectInvoiceTool, param){
    if(isNotBlank(serialNum)){
        doAfterGetCA(billFormElectInvoiceTool, serialNum, param);
    }else{
        // 请求获取CA序列号
        billFormElectInvoiceTool.takeCert(function(data){
            console.info("CA序列号："+ JSON.stringify(data));
            var cert = JSON.parse(data);
            if(cert["result"] == "failed"){
                removeModal();
                ityzl_SHOW_WARN_LAYER("请插入CA后，在进行冲红操作。");
                return;
            }
            if(cert["IdeaBank cKey 0"].length == 0){
                removeModal();
                ityzl_SHOW_WARN_LAYER("未获取到CA序列号内容。");
                return;
            }
            serialNum =  cert["IdeaBank cKey 0"][0].Items.serialNumber;
            var ckeyUser = cert["IdeaBank cKey 0"][0].Items.CN;
            console.info(param);
            console.info(ckeyUser);
            if(param.tfrxm == ckeyUser){
                if(isNotBlank(serialNum)){
                    doAfterGetCA(billFormElectInvoiceTool, serialNum, param);
                }else{
                    removeModal();
                    ityzl_SHOW_WARN_LAYER("未获取到CA序列号内容。");
                }
            }else{
                removeModal();
                ityzl_SHOW_WARN_LAYER("当前ca账户与登录账户不一致。");
            }
        });
    }
}

function doAfterGetCA(billFormElectInvoiceTool, serialNum, param){
    getBeforeRevoke(param.jfsbm, serialNum).done(function (value) {
        // 对返回的票据信息进行签名加密
        billFormElectInvoiceTool.signData(value.invoiceData, function(signData){
            console.info("CA签名加密的invoiceData："+ JSON.stringify(signData));
            var signJson = JSON.parse(signData);
            if(isNotBlank(signJson.SignData)){
                value.signData = signJson.SignData;
                value.billno = value.billNo;
                value.serialNumber = serialNum;
                getHandleRevoke(value).done(function(){
                    removeModal();
                    printTfsqs(param.gzlslid, param.xmid, param.sfxxid, "tfsqs", param.qlrlb);
                });
            }else{
                removeModal();
                ityzl_SHOW_WARN_LAYER("未获取到签名信息。");
            }
        });
    });
}
// 获取冲红票据信息
function getBeforeRevoke(jfsbm, caNo){
    var deferred = $.Deferred();
    $.ajax({
        url: getContextPath() + "/sf/getBeforeRevoke",
        type: "POST",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify({billno : jfsbm, serialNumber : caNo}),
        success: function (data) {
            if(isNotBlank(data) && isNotBlank(data.result.invoiceData)){
                deferred.resolve(data.result);
            }else{
                ityzl_SHOW_WARN_LAYER("未获取到票据信息。");
                deferred.reject();
            }
        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
            deferred.reject();
        }
    });
    return deferred;
}
// 生成冲红票据
function getHandleRevoke(param){
    var deferred = $.Deferred();
    $.ajax({
        url: getContextPath() + "/sf/getHandleRevoke",
        type: "POST",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(param),
        success: function (data) {
            if(isNotBlank(data) && "0" == data.errcode){
                deferred.resolve(data);
            }else{
                ityzl_SHOW_WARN_LAYER("未获取到冲红票据信息。");
                deferred.reject();
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr);
            deferred.reject();
        }
    });
    return deferred;
}

function printTfsqs(processInsId, xmid, sfxxid, dylx, qlrlb){
    var url = getIP() + "/realestate-accept-ui";
    $.ajax({
        url: getContextPath() + "/slPrint",
        type: "GET",
        // dataType: 'json',
        data: {gzlslid: processInsId, xmid: xmid, dylx: dylx, url: url, zxlc:false, qlrlb:qlrlb, sfxxid:sfxxid, sjclids:""},
        success: function (data) {
            if (data !== null && data !== '') {
                window.location.href = "eprt://" + data;
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}
//新增收费信息
function addSfxm(elem, qlrlb) {
    var $this = $(elem);
    if(qlrlb == 1){
        var sfxxid = qlrSfxxId;
    }else {
        var sfxxid = ywrSfxxId;
    }
    if(!isNotBlank(sfxxid)) {
        //判断收费信息是否为空,为空需新生成
        $.ajax({
            url: getContextPath() + "/uuid",
            async: false,
            success: function (data) {
                sfxxid = data;
                if(qlrlb == 1){
                    qlrSfxxId = data;
                    getQlrlb =qlrlb;
                }else {
                    ywrSfxxId = data;
                    getYwrlb =qlrlb;
                }
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        });
    }

    var $lastTr = $this.parents('.basic-info').find('table tbody .bdc-count');
    var $tbody = $this.parents('.basic-info').find('table tbody');
    var getTpl = addSfxmTpl.innerHTML;
    var addsfxmid;
    $.ajax({
        url: getContextPath() + "/uuid",
        async: false,
        success: function (data) {
            addsfxmid = data;
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
    var addSfxmList = {};
    addSfxmList.sfxmList = getSfxmList;
    addSfxmList.qlrlb = qlrlb;
    addSfxmList.sfxxid = sfxxid;
    addSfxmList.sfxmid = addsfxmid;
    if(qlrlb === "1") {
        if(qlrsfxmsxh < 0 || qlrsfxmsxh === "" || qlrsfxmsxh === null) {
            qlrsfxmsxh = 0;
        }
        qlrsfxmsxh ++;
        addSfxmList.xh = qlrsfxmsxh;
    } else {
        if(ywrsfxmsxh < 0 || ywrsfxmsxh === "" || ywrsfxmsxh === null) {
            ywrsfxmsxh = 0;
        }
        ywrsfxmsxh ++;
        addSfxmList.xh = ywrsfxmsxh;
    }
    if ($lastTr.length > 0) {
        laytpl(getTpl).render(addSfxmList, function (html) {
            $lastTr.before(html);
            form.render();
        });
    } else {
        $this.parents('.basic-info').find('.bdc-table-none').remove();
        var getCountTpl = hjTpl.innerHTML;
        laytpl(getTpl).render(addSfxmList, function (html) {
            $tbody.append(html);
            laytpl(getCountTpl).render([], function (countHtml) {
                $tbody.append(countHtml);
            });
            form.render();
        });
    }
}

//保存收费信息
function saveSfxx() {
    var processInsId = getQueryString("processInsId");
    var $jbxx = $('#jbSfxx');
    if ($jbxx.length > 0) {
        var ywrSfxx = {};
        var qlrSfxx = {};
        var bdcSfxxArray = $jbxx.find(".sfxx").serializeArray();
        if (bdcSfxxArray !== null && bdcSfxxArray.length > 0) {
            bdcSfxxArray.forEach(function (item, index) {
                qlrSfxx[item.name] = item.value;
                ywrSfxx[item.name] = item.value;
            })
        }

        if(hasQlrJbxx &&isNotBlank(qlrSfxxId)){
            qlrSfxx.hj = $('#qlrSfxm .bdc-hj').val();
            qlrSfxx.sfxxid = qlrSfxxId;
            qlrSfxx.qlrlb = getQlrlb;
            qlrSfxx.jfrxm =qlrjfrxm;
            qlrSfxx.ywr = ywrmc;
            qlrSfxx.gzlslid =processInsId;
            if(isNotBlank(sfxxxmid)){
                qlrSfxx.xmid =sfxxxmid;
            }
            //点击了保存按钮
            qlrSfxx.bczt = 1;
            //保存的时候，小微企业和月结银行收费状态改为已缴费
            if (getQlrSfxwqy || getQlrSfayjs) {
                qlrSfxx.sfzt = 2;
            }
            //保存收费信息
            $.ajax({
                url: getContextPath() + "/sf/xx",
                type: 'PATCH',
                dataType: 'json',
                async: false,
                contentType: "application/json",
                data: JSON.stringify(qlrSfxx),
                success: function (data) {
                },
                error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr)
                }
            });
        }

        if(hasYwrJbxx &&isNotBlank(ywrSfxxId)){
            ywrSfxx.hj = $('#ywrSfxm .bdc-hj').val();
            ywrSfxx.sfxxid = ywrSfxxId;
            ywrSfxx.qlrlb = getYwrlb;
            ywrSfxx.jfrxm =ywrjsfrxm;
            ywrSfxx.ywr = ywrmc;
            ywrSfxx.bczt = 1;
            ywrSfxx.gzlslid =processInsId;
            //保存的时候，小微企业和月结银行收费状态改为已缴费
            if (getYwrSfxwqy || getYwrSfayjs) {
                ywrSfxx.sfzt = 2;
            }
            if(isNotBlank(sfxxxmid)){
                ywrSfxx.xmid =sfxxxmid;
            }
            //保存收费信息
            $.ajax({
                url: getContextPath() + "/sf/xx",
                type: 'PATCH',
                dataType: 'json',
                async: false,
                contentType: "application/json",
                data: JSON.stringify(ywrSfxx),
                success: function (data) {
                },
                error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr)
                }
            });
        }
    }
}

//单击删除收费项目
function deleteSfxmTr(item, sfxmid,qlrlb) {
    var $this = $(item);
    var trLength = $this.parents('tbody').find('tr').length;
    var $table = $this.parents('table');
    var deleteIndex = layer.open({
        type: 1,
        skin: 'bdc-small-tips',
        title: '确认删除',
        area: ['320px'],
        content: '是否确认删除？',
        btn: ['确定', '取消'],
        btnAlign: 'c',
        yes: function () {
            //确定操作
            addModel();
            setTimeout(function () {
                try {
                    $.when(deletesfxm(sfxmid)).done(function () {
                        if (trLength == 2) {
                            $this.parents('tbody').html('<tr class="bdc-table-none">' +
                                '<td colspan="9">' +
                                '<div class="layui-none"><img src="../../static/lib/bdcui/images/table-none.png" alt="">无相关收费信息' +
                                '</div>' +
                                '</td>' +
                                '</tr>');
                            if(qlrlb === '1') {
                                qlrsfxmsxh = 0;
                                deleteSfxx(qlrSfxxId);
                            } else {
                                ywrsfxmsxh = 0;
                                deleteSfxx(ywrSfxxId);
                            }
                        } else {
                            $this.parents('tr').remove();
                            computeTotal($table);
                        }
                        renderSfxx();
                        removeModal();
                        ityzl_SHOW_SUCCESS_LAYER("删除成功");
                        layer.close(deleteIndex);
                    })
                } catch (e) {
                    removeModal();
                    ERROR_CONFIRM("删除失败", e.message);
                    layer.close(deleteIndex);
                    return;
                }
            }, 10);
        },
        btn2: function () {
            //取消
            layer.close(deleteIndex);
        }
    });
}

//从库里面删除收费项目
function deletesfxm(sfxmid) {
    $.ajax({
        url: getContextPath() + "/sf/xm?sfxmid=" + sfxmid,
        async: false,
        type: 'DELETE',
        success: function (data) {

        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr);
            return;
        }
    });
}

//删除收费信息
function deleteSfxx(sfxxid){
    $.ajax({
        url: getContextPath() + "/sf/sfxx?sfxxid=" + sfxxid,
        async: false,
        type: 'DELETE',
        success: function (data) {
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr);
            return;
        }
    });
}

// 通过websocket调用，获取CA序列号
var BillFormElectInvoiceTool = (function(){
    function Singleton(options) {
        options = options || {};
        this.name = "BillFormElectInvoiceTool";
        this.url = options.url || '';
        this.sessionId = options.sessionId || '';
        this.socket = options.socket;
        this.isReconnect = false;
        this.messageSession = {};
    }

    Singleton.prototype.connect = function() {
        if (this.socket !== undefined) {
            return this;
        }
        var protocol = (window.location.protocol == 'http:') ? 'ws://' : 'ws://';
        this.host = protocol + this.url;

        window.WebSocket = window.WebSocket || window.MozWebSocket;
        if(!window.WebSocket) { // 检测浏览器支持
            this.error('浏览器不支持WebSocket，请使用谷歌4.0以上的浏览器');
            return;
        }
        var that = this;
        try {
            this.socket = new WebSocket(this.host); // 创建连接并注册响应函数
            this.socket.onopen = function(e) {
                that.onopen(e);
            };
            this.socket.onmessage = function(e) {
                that.onmessage(e);
            };
            this.socket.onclose = function(e) {
                that.onclose(e);
                that.socket = null; // 清理
            };
            this.socket.onerror = function(e) {
                that.onerror(e);
            };
            return this;
        } catch (e) {
            this.error('连接WebSocket失败!')
        }
    };

    Singleton.prototype.onopen = function (e) {};

    Singleton.prototype.onmessage = function (message) {
        var callback = this.messageSession[this.sessionId];
        if (callback) {
            callback(message.data);
        }
    };

    Singleton.prototype.onclose = function (e) {
        if(this.socket !== undefined && this.socket!= null) {
            this.socket.close();
        } else {
            this.error("WebSocket链接已失效");
        }
    };

    Singleton.prototype.onerror = function (e) {
        removeModal();
        console.log(e);
        ityzl_SHOW_WARN_LAYER("连接Websocket服务器异常");
    };

    Singleton.prototype.error = function (errorMsg) {
        this.onerror(errorMsg);
    };

    Singleton.prototype.isConnect = function () {
        return this.socket && this.socket.readyState === 1;
    };

    Singleton.prototype.reconnect = function () {
        if (this.isReconnect) {
            return;
        }
        this.isReconnect = true;
        var that = this;
        setTimeout(function() {
            that.connect();
            that.isReconnect = false;
        }, 2000)
    };

    Singleton.prototype.send = function (message) {
        if (this.socket.readyState !== 1) {
            this.reconnect();
        }
        if(this.socket.readyState === 1) {
            this.socket.send(message);
            return true;
        }
        this.error('请先开启Websocket连接服务器');
        return false;
    };

    Singleton.prototype.signData = function (message, callback) {
        var data = '{"FuncName":"SignData_P7","Parames" :{"SignAlgType":"", "OriginalData":"'+message+'","OriginalDataType":""}}';
        this.send(data);
        this.messageSession[this.sessionId] = callback;
    };

    Singleton.prototype.takeCert = function (callback) {
        this.send('{"FuncName":"GetKeyCert"}');
        this.messageSession[this.sessionId] = callback;
    };

    var instance;
    return {
        name: "BillFormElectInvoiceTool",
        getInstance: function (options) {
            if (instance === undefined) {
                instance = new Singleton(options);
            } else {
                instance.connect(options);
            }
            return instance;
        }
    };
})();

//设置收费修改元素不可编辑权限
function setSfEditElement(qlrlb){
    var $parent =$("#qlrSfxm");
    if(qlrlb ==="2"){
        $parent =$("#ywrSfxm");
    }
    //收费修改
    var $sfEdit =$parent.find(".edit-sfxx");
    var $sfEditBtn =$parent.find(".edit-sfxx-btn");
    $sfEdit.attr("disabled", "disabled");
    $sfEditBtn.attr("disabled", "disabled");
    $sfEdit.parent().append("<img src=\"../../static/lib/bdcui/images/lock.png\" alt=\"\">");
    $sfEditBtn.addClass("bdc-prohibit-btn");
    $sfEditBtn.removeClass('bdc-major-btn');
    $sfEditBtn.removeClass('bdc-delete-btn');
    $sfEditBtn.removeAttr("onclick");
    form.render("select");
    resetSelectDisabledCss();
}

//加载承受方税款信息模块
function generateMsfSwxx(data, qlrlb) {
    var swxxList;
    if (qlrlb === "2") {
        swxxList = data.bdcZcfSwxxList;
    } else {
        swxxList = data.bdcZrfSwxxList
    }
    var tpl = csfskxxTpl.innerHTML,
        view = document.getElementById('csfskxx');
    if (qlrlb === "2") {
        view = document.getElementById('msfskxx');
    }
    var swxxDTO = {};
    var jexj = {
        ynsexj: 0,
    };
    if (swxxList != null && swxxList.length > 0) {
        for (var i = 0; i < swxxList.length; i++) {
            jexj.ynsexj += swxxList[i].ynsehj;
        }
    }

    if (swxxList != null && swxxList.length > 0) {
        swxxDTO = swxxList[0];
        wszt = swxxDTO.wszt;
        if (qlrlb === "2") {
            //给页面税费二维码图片赋值
            if (isNotBlank(swxxDTO.hyzfurl)) {
                var url = "/realestate-accept-ui/sf/ewm?ewmnr=" + encodeURIComponent(swxxDTO.hyzfurl);
                $(".ywrewm").attr('src', url);
            }
            $(".ywrewm").click(function () {
                var _this = $(this);//将当前的pimg元素作为_this传入函数
                imgShow("#outerdiv", "#innerdiv", "#bigimg", _this);
            });
        } else {
            //给页面税费二维码图片赋值
            if (isNotBlank(swxxDTO.hyzfurl)) {
                var url = "/realestate-accept-ui/sf/ewm?ewmnr=" + encodeURIComponent(swxxDTO.hyzfurl);
                $(".qlrewm").attr('src', url);
            }
            $(".qlrewm").click(function () {
                var _this = $(this);//将当前的pimg元素作为_this传入函数
                imgShow("#outerdiv", "#innerdiv", "#bigimg", _this);
            });
        }
    }
    var json = {
        zd: zdList,
        swxxDTO: swxxDTO,
        swxxList: swxxList,
        jexj: jexj,
    };
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });

}

//推送税费信息
function tssf(qlrlb){
    var sfxxid = "";
    if (qlrlb === '1' ) {
        sfxxid = qlrSfxxId;
    }else {
        sfxxid = ywrSfxxId;
    }
    if (isNullOrEmpty(sfxxid)) {
        ityzl_SHOW_WARN_LAYER("未获取到sfxxid");
        return false;
    }
    addModel("");
    getReturnData("/slym/sw/sftj?qlrlb=" + qlrlb, {gzlslid: processInsId, xmid: xmid, sfxxid:sfxxid}, "GET", function (data) {
        removeModal();
        if (data.success) {
            //渲染收费信息
            renderSfxx();
            ityzl_SHOW_SUCCESS_LAYER("推送成功");
        } else {
            ityzl_SHOW_WARN_LAYER("推送失败");
        }
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    });
}
function imgShow(outerdiv, innerdiv, bigimg, _this) {
    var src = _this.attr("src");//获取当前点击的pimg元素中的src属性
    $(bigimg).attr("src", src);//设置#bigimg元素的src属性

    /*获取当前点击图片的真实大小，并显示弹出层及大图*/
    $("<img/>").attr("src", src).load(function () {
        var windowW = $(window).width();//获取当前窗口宽度
        var windowH = $(window).height();//获取当前窗口高度
        var realWidth = this.width;//获取图片真实宽度
        var realHeight = this.height;//获取图片真实高度
        var imgWidth, imgHeight;
        var scale = 0.8;//缩放尺寸，当图片真实宽度和高度大于窗口宽度和高度时进行缩放

        if (realHeight > windowH * scale) {//判断图片高度
            imgHeight = windowH * scale;//如大于窗口高度，图片高度进行缩放
            imgWidth = imgHeight / realHeight * realWidth;//等比例缩放宽度
            if (imgWidth > windowW * scale) {//如宽度扔大于窗口宽度
                imgWidth = windowW * scale;//再对宽度进行缩放
            }
        } else if (realWidth > windowW * scale) {//如图片高度合适，判断图片宽度
            imgWidth = windowW * scale;//如大于窗口宽度，图片宽度进行缩放
            imgHeight = imgWidth / realWidth * realHeight;//等比例缩放高度
        } else {//如果图片真实高度和宽度都符合要求，高宽不变
            imgWidth = realWidth;
            imgHeight = realHeight;
        }
        $(bigimg).css("width", imgWidth);//以最终的宽度对图片缩放

        var w = (windowW - imgWidth) / 2;//计算图片与窗口左边距
        var h = (windowH - imgHeight) / 2;//计算图片与窗口上边距
        $(innerdiv).css({"top": h, "left": w});//设置#innerdiv的top和left属性
        $(outerdiv).fadeIn("fast");//淡入显示#outerdiv及.pimg
    });

    $(outerdiv).click(function () {//再次点击淡出消失弹出层
        $(this).fadeOut("fast");
    });
}