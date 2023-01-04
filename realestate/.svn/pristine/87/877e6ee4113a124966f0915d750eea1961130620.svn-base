// 发票模板地址
var fphUrl = "C:/GTIS/sfddj.fr3";

//收费项目列表数据
var getSfxmList;
var BASE_URL = '/realestate-inquiry-ui/rest/v1.0/fph';
var sfxxid;
var qlrlb;
var sfzt;

layui.use(['table', 'laytpl', 'laydate', 'layer', 'form', 'laytpl'], function () {
    var $ = layui.jquery,
        table = layui.table,
        form = layui.form,
        laytpl = layui.laytpl,
        laydate = layui.laydate,
        layer = layui.layer;
    //绑定回车键
    $(document).keydown(function (event) {
        if (event.keyCode == "13") {
            $("#query").click();
            return false;
        }
    });
    var data;
    var allData = [],
        effectiveData = [],
        toolbarId = '#toolbarShowDemo';
    //程序计算数量
    var bdcSlSfxmSlDTO = {};

    // 渲染时间
    laydate.render({
        elem: '#sfsj',
        format: 'yyyy-MM-dd HH:mm:ss',
        type: 'datetime'
    });


    form.render();

    var cols = [ //表头
        {type: 'radio', width: 50, fixed: 'left'},
        {field: 'sfxxid', title: 'ID', width: 50, fixed: 'left', hide: true},
        {field: 'qlrlb', title: '权利人类别', width: 50, fixed: 'left', hide: true}
        , {field: 'fph', title: '发票号'}
        , {field: 'slbh', title: '受理编号'}
        // , {field: 'fpzl', title: '发票种类'}
        , {field: 'hj', title: '金额合计小写'}
        , {field: 'jfrxm', title: '付款方名称'}
        , {field: 'sfrxm', title: '收款人名称'}
        , {field: 'sfrzh', title: '收款人账号'}
        , {field: 'sfrkhyh', title: '收款人银行'}
        , {title: '收费状态', templet: '#ztTpl'}
        , {title: '操作', width: 260, fixed: 'right', templet: '#barDemo'}
    ];
    table.render({
        elem: '#fphTable'
        , id: "fphTableId"
        , even: true
        , cols: [cols]
        , toolbar: toolbarId
        , defaultToolbar: ['filter']
        , data: [],
        done: function () {
            $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
            if ($('.bdc-table-box .layui-table-main>.layui-table').height() == 0) {
                $('.layui-table-body .layui-none').html('<img src="../../static/lib/registerui/image/table-none.png" alt="">无数据');
            }
        }
    });
    $('#query').click(function () {
        var slbh = $('#slbh').val();
        if (isNullOrEmpty(slbh)) {
            warnMsg("受理编号不能为空！")
            return false;
        }
        var checkData = checkState(slbh);
        if (checkData.state == true) {
            warnMsg("项目已办结，不能再次收费!");
            return false;
        } else if (checkData.sfzt == true) {
            layer.confirm('已收费，是否需要重新收费？', function (index) {
                querySfxx(slbh);
                layer.close(index);
                return false;
            });
        } else if (checkData.isSz == true) {
            layer.confirm('今天缮证，是否继续生成收费信息？', function (index) {
                querySfxx(slbh);
                layer.close(index);
                return false;
            });
        } else {
            querySfxx(slbh);
        }
    });
    $('#search').click(function () {
        var slbh = $('#slbh').val();
        if (isNullOrEmpty(slbh)) {
            warnMsg("受理编号不能为空！")
            return false;
        }
        $.ajax({
            url: BASE_URL + "/query?slbh=" + slbh,
            type: "GET",
            success: function (data) {
                effectiveData = [];
                $.each(data, function (key, value) {
                    value['slbh'] = slbh;
                    if (value.hj != 0 && value.hj != '') {
                        effectiveData.push(value);
                    }
                });
                setQbdyAuth(data);
                checkJfsewmurl(data);
                sfxxCache = data;
                allData = data;
                toolbarId = "#toolbarShowDemo";
                table.reload('fphTableId', {
                    data: effectiveData,
                    toolbar: toolbarId
                });
                $("#secondTableView").html('');
            }, error: function (e) {
                showErrorInfo(e, "获取信息失败");
            }, complete: function (XMLHttpRequest, textStatus) {
                // 关闭loading
                removeModal();
            }
        });
    });

    //监听滚动事件
    var scrollTop = 0, tableTop = 0, tableLeft = 0;
    var $nowBtnMore = '';
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
        $('.bdc-table-top-more-show').hide();
    });

    var sfxxCache = data;

    // 设置全部打印权限，若其中一个收费项是二维码缴费的，则不允许全部打印
    function setQbdyAuth(data){
        // 先将按钮禁止事件解除
        $("#dyqb").removeAttr("disabled");
        $("#dyqb").css({'pointer-events':'auto'});
        if(isNotBlank(data)){
            var hasEwmjf = false;
            $.each(data, function(index, value){
                if(isNotBlank(value.jfsbm)){
                    hasEwmjf = true;
                    return false;
                }
            });
            if(hasEwmjf){
                $("#dyqb").attr("disabled","off");
                $("#dyqb").css({'pointer-events':'none'});
            }
        }
    }

    function checkJfsewmurl(data){
        // 先将按钮隐藏解除
        $("#dyqb").css('display','');
        $("#query").css('display','');
        if(isNotBlank(data)){
            var hasJfsewmurl = false;
            $.each(data, function (index,item){
                if(isNotBlank(item.jfsewmurl)){
                    hasJfsewmurl = true;
                    return false;
                }
            })

            if(hasJfsewmurl){
                $("#dyqb").css('display','none');
                $("#query").css('display','none');
            }
        }
    }
    function querySfxx(slbh) {
        addModel();
        $.ajax({
            url: BASE_URL + "?slbh=" + slbh,
            type: "GET",
            success: function (data) {
                effectiveData = [];
                $.each(data, function (key, value) {
                    value['slbh'] = slbh;
                    if (value.hj != 0 && value.hj != '') {
                        effectiveData.push(value);
                    }
                });
                sfxxCache = data;
                allData = data;
                toolbarId = "#toolbarShowDemo";
                table.reload('fphTableId', {
                    data: effectiveData,
                    toolbar: toolbarId
                });
                $("#secondTableView").html('');
            },
            error: function (e) {
                showErrorInfo(e, "生成发票号失败");
            },
            complete: function (XMLHttpRequest, textStatus) {
                // 关闭loading
                removeModal();
            }
        });
    }

    function checkState(slbh) {
        var result = {};
        result.state = false;
        $.ajax({
            url: BASE_URL + "/check?slbh=" + slbh,
            type: "GET",
            async: false,
            success: function (data) {
                result.state = data.state;
                result.taskData = data.taskData;
                result.sfzt = data.sfzt;
            }, complete: function (XMLHttpRequest, textStatus) {

            }
        });
        return result;
    }

    //一级 表格内部 操作按钮监听事件
    table.on('tool(fphTableFilter)', function (obj) {
        var layEvent = obj.event; //获得 lay-event 对应的值
        var data = obj.data;
        console.log(data);
        if (layEvent === "hqfph") { // 重新获取发票号
            var fph = cxjqfph(data.sfxxid);
            obj.update({
                fph: fph
            });
        }
        if (layEvent === "editFph") { // 修改发票号
            // 验证发票号是否可用
            $.ajax({
                url: '/realestate-inquiry-ui/rest/v1.0/fph/edit/check?sfxxid=' + data.sfxxid + "&fph=" + data.fph,
                type: "GET",
                async: false,
                success: function (data) {
                    if (data.isKy == false || data.syzt == '2') {
                        warnMsg("只可对当前账户名下的发票号做更改！")
                        return false;
                    } else {
                        var index = layer.open({
                            type: 2,
                            title: "修改",
                            area: ['600px', '240px'],
                            fixed: false, //不固定bdcFzjl
                            maxmin: true, //开启最大化最小化按钮
                            content: "editFph.html?sfxxid=" + obj.data.sfxxid,
                        });
                    }
                },
                error: function (e) {
                    showErrorInfo(e, "验证服务异常");
                    removeModal();
                },complete: function (XMLHttpRequest, textStatus) {
                    removeModal();
                }
            });
        }
        if (layEvent === "dy") { //打印
            if (isNotBlank(data.fph)) {
                dy(data.sfxxid);
            } else {
                warnMsg("该收费信息无发票号！")
            }
        }
        if (layEvent === "relink"){
            relink(data);
        }
    });
    //一级 表格上面 操作列操作事件
    table.on('toolbar(fphTableFilter)', function (obj) {
        switch (obj.event) {
            case 'showIsNull':
                if (effectiveData.length == allData.length) {
                    layer.msg('<img src="../../static/lib/bdcui/images/info-small.png" alt="">没有金额为0项');
                } else {
                    toolbarId = "#toolbarHideDemo";
                    table.reload('fphTableId', {
                        data: allData
                        , toolbar: toolbarId
                    });
                }
                break;
            case 'hideIsNull':
                toolbarId = "#toolbarShowDemo";
                table.reload('fphTableId', {
                    data: effectiveData
                    , toolbar: toolbarId
                });
                break;
        }
        $('.bdc-update-padding').html('');
    });

    //监听表格1单选框选择
    table.on('radio(fphTableFilter)', function (obj) {
        console.log(obj.data); //选中行的相关数据
        sfxxid = obj.data.sfxxid;
        qlrlb = obj.data.qlrlb;
        sfzt = obj.data.sfzt;
        renderSecondTable(obj.data.sfxxid, obj.data.xmid, obj.data.gzlslid);
    });

    //获取收费项目列表
    function renderSfxmList(xmid, gzlslid) {
        $.ajax({
            url: BASE_URL + "/sf/xm/pz/all",
            type: 'GET',
            dataType: 'json',
            data: {processInsId: gzlslid, xmid: xmid},
            async: false,
            success: function (data) {
                getSfxmList = data;
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        });
    }

    function renderSecondTable(sfxxid, xmid, gzlslid) {

        $.ajax({
            url: BASE_URL + "/sf/xm/new",
            type: 'GET',
            dataType: 'json',
            data: {sfxxid: sfxxid},
            success: function (data) {
                renderSfxmList(xmid, gzlslid);
                data.forEach(function (v) {
                    v['sfxmList'] = getSfxmList;
                    //登记费和工本费初始化加载需要程序计算数量
                    if ((isNotBlank(v.sfxmmc) && v.sfxmmc.indexOf("登记费") > -1 || v.sfxmmc.indexOf("工本费") > -1 || v.sfxmmc.indexOf("土地使用权交易服务费") > -1) && v.sl === null) {
                        if (v.sfxmmc.indexOf("工本费") > -1) {
                            v['sl'] = bdcSlSfxmSlDTO.gbfsl;
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

                            } else if (v.jsff === "3") {
                                //取总权利面积和
                                v['sl'] = bdcSlSfxmSlDTO.tdsyjsl;

                            } else {
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
                    v['ssje'] = calculateFloat(getShje - v.jmje);

                });

                var getTpl = secondTableTpl.innerHTML;
                laytpl(getTpl).render(filterNotNull(data), function (html) {
                    var $secondTableView = $("#secondTableView");
                    $secondTableView.html(html);
                    computeTotal($secondTableView);
                    if (sfzt == '2') {
                        var $secondSelect = $secondTableView.find("select");
                        $secondSelect.attr("disabled", true);
                        $secondSelect.parents('.bdc-td-box').addClass('bdc-one-icon').append('<img src="../../static/lib/bdcui/images/lock.png" alt="">');

                        var $secondInput = $secondTableView.find("input");
                        $secondInput.attr("disabled", true);
                        $secondInput.parents('.bdc-td-box').addClass('bdc-one-icon').append('<img src="../../static/lib/bdcui/images/lock.png" alt="">');

                        var $secondA = $secondTableView.find("a");
                        $secondA.css("pointer-events", "none").addClass('bdc-prohibit-btn');
                    }
                    form.render();
                });

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
                    bzHtml += '<option value="' + value.sfxmbz + '" data-dj="' + value.sfxmdj + '">' + value.sfxmbz + '</option>';
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
                $parentTr.find('.bdc-ssje').val(calculateFloat(getShje - jmje));
                $parentTr.find('.bdc-ssje-input').val(calculateFloat(getShje - jmje));
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

    //打印
    function dy(sfxxid) {
        if (isNullOrEmpty(sfxxid)) {
            warnMsg("收费信息ID不能为空！")
            return false;
        }
        var sfxxidList = new Array();
        sfxxidList.push(sfxxid);
        var dataUrl = getIP() + BASE_URL + "/dy/xml?sfxxidList=" + sfxxidList;
        print(fphUrl, dataUrl, false);
        modifyKpzt(sfxxidList);
    }

    //打印全部
    $('#dyqb').click(function () {
        if(!isNotBlank(sfxxCache)|| sfxxCache.length == 0 ){
            warnMsg("打印的收费信息不能为空！")
            return false;
        }
        var sfxxidList = new Array();
        $.each(sfxxCache, function (key, val) {
            if (!isNullOrEmpty(val.fph)) {
                sfxxidList.push(val.sfxxid)
            }
        });
        if (sfxxidList.length == 0) {
            layer.msg("没有可以打印的发票号！");
            return;
        }
        var dataUrl = getIP() + BASE_URL + "/dy/xml?sfxxidList=" + sfxxidList;
        print(fphUrl, dataUrl, true);
        modifyKpzt(sfxxidList);
    });

    //重新获取 发票号
    function cxjqfph(sfxxid) {
        addModel();
        var slbh = $('#slbh').val();
        if (isNullOrEmpty(slbh)) {
            removeModal();
            warnMsg("受理编号不能为空！")
            return false;
        }
        if (isNullOrEmpty(sfxxid)) {
            removeModal()
            warnMsg("收费信息ID不能为空！")
            return false;
        }
        var fph = '';
        $.ajax({
            url: BASE_URL + "/cxhqfph?sfxxid=" + sfxxid + "&slbh=" + slbh,
            type: "GET",
            async: false,
            success: function (data) {
                fph = data[0].fph;
            },
            complete: function (XMLHttpRequest, textStatus) {
                removeModal();
            }
        });
        return fph;
    }

    //收费
    $('#sf').click(function () {
        var slbh = $('#slbh').val();
        if (isNullOrEmpty(slbh)) {
            warnMsg("受理编号不能为空！")
            return false;
        }
        var checkData = checkState(slbh);

        if (checkData.sfzt == true) {
            layer.confirm('已收费，是否重新收费？', {
                    btn: ["是", "否"]
                },
                function (index) {
                    sf(slbh, 0);
                    layer.close(index);
                    return false;
                }, function (index) {
                    layer.close(index);
                    return false;
                }
            );
        } else {
            sf(slbh, 1);
        }
    });

    // 取消收费
    $('#qxsf').click(function () {
        var slbh = $('#slbh').val();
        if (isNullOrEmpty(slbh)) {
            warnMsg("受理编号不能为空！")
            return false;
        }
        $.ajax({
            url: BASE_URL + "/qxsf?slbh=" + slbh,
            type: "GET",
            async: false,
            success: function () {
                layer.msg('<img src="../../static/lib/bdcui/images/success-small.png" alt="">取消收费成功');
                $.each(sfxxCache, function (key, value) {
                    value.sfzt = 1;
                });
                table.render({
                    elem: '#fphTable'
                    , id: "fphTableId"
                    , even: true
                    , cols: [cols]
                    , toolbar: toolbarId
                    , defaultToolbar: ['filter']
                    , data: effectiveData,
                    done: function () {
                        $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
                        if ($('.bdc-table-box .layui-table-main>.layui-table').height() == 0) {
                            $('.layui-table-body .layui-none').html('<img src="../../static/lib/registerui/image/table-none.png" alt="">无数据');
                        }
                    }
                });
            }, error: function (e) {
                layer.msg('<img src="../../static/lib/bdcui/images/success-small.png" alt="">取消收费失败');
            },
            complete: function (XMLHttpRequest, textStatus) {

            }
        });
    });

    function sf(slbh, gxbz) {
        $.ajax({
            url: BASE_URL + "/sf?slbh=" + slbh + "&gxbz=" + gxbz,
            type: "GET",
            async: false,
            success: function () {
                layer.msg('<img src="../../static/lib/bdcui/images/success-small.png" alt="">收费成功');
                $.each(sfxxCache, function (key, value) {
                    value.sfzt = 2;
                });
                table.render({
                    elem: '#fphTable'
                    , id: "fphTableId"
                    , even: true
                    , cols: [cols]
                    , toolbar: toolbarId
                    , defaultToolbar: ['filter']
                    , data: effectiveData,
                    done: function () {
                        $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
                        if ($('.bdc-table-box .layui-table-main>.layui-table').height() == 0) {
                            $('.layui-table-body .layui-none').html('<img src="../../static/lib/registerui/image/table-none.png" alt="">无数据');
                        }
                    }
                });
            }, error: function (e) {
                layer.msg('<img src="../../static/lib/bdcui/images/success-small.png" alt="">收费失败');
            },
            complete: function (XMLHttpRequest, textStatus) {

            }
        });
    }

    //二级表格中的新增
    $('.bdc-container').on('click', '.bdc-add-tr', function () {
        var $lastTr = $("#qlrSfxm").find('table tbody .bdc-count');
        var $tbody = $("#qlrSfxm").find('table tbody');
        var getTpl = addSfxmTpl.innerHTML;
        var addsfxmid;
        $.ajax({
            url: BASE_URL + "/uuid",
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
        if ($lastTr.length > 0) {
            laytpl(getTpl).render(addSfxmList, function (html) {
                $lastTr.before(html);
                form.render();
            });
        } else {
            $("#qlrSfxm").find('.bdc-table-none').remove();
            var getCountTpl = hjTpl.innerHTML;
            laytpl(getTpl).render(addSfxmList, function (html) {
                $tbody.append(html);
                laytpl(getCountTpl).render([], function (countHtml) {
                    $tbody.append(countHtml);
                });
                form.render();
            });
        }
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

});

// 重新关联
function relink(data){
    console.info(data);
    var sfxxid = data.sfxxid;

    //小弹出层
    layer.open({
        title: '重新关联',
        type: 1,
        area: ['450px', '250px'],
        btn: ['确定','取消'],
        content: $('#bdc-popup-small'),
        yes: function (index, layero) {
            var fph = $("#relinkForm").find("input[name='fph']").val();
            var sfsj =  $("#relinkForm").find("input[name='sfsj']").val();
            if(!isNotBlank(fph)){
                warnMsg("请输入需要关联的发票号。");
            }
            checkFph(fph).done(function(result){
                if(!result.isKy || result.syzt == null){
                    warnMsg("未获取到发票号：" + fph);
                    return;
                }
                if(result.syzt == '2'){
                    warnMsg("发票号：" + fph + " 已作废，请关联已使用的发票号。" );
                    return;
                }else if(result.syzt != '3'){
                    warnMsg("发票号：" + fph + " 未使用，请关联已使用的发票号。" );
                    return;
                }else{
                    addModel();
                    // 重新关联
                    $.ajax({
                        url: BASE_URL + "/edit/save",
                        type: 'GET',
                        dataType: 'json',
                        data: {sfxxid: sfxxid, fph : fph, sfsj: sfsj, slbh: data.slbh},
                        success: function (data) {
                            removeModal();
                            successMsg("关联成功");
                            layer.close(index);
                        },
                        error: function (xhr, status, error) {
                            removeModal();
                            delAjaxErrorMsg(xhr);
                            layer.close(index);
                        }
                    });
                }
            });

        },
        cancel: function () {
            $("#relinkForm")[0].reset();
        },
        success: function () {
        }
    });
}

function checkFph(fph){
    var deferred = $.Deferred();
    addModel();
    $.ajax({
        url: '/realestate-inquiry-ui/rest/v1.0/fph/edit/check?fph=' + fph,
        type: "GET",
        async: false,
        success: function (data) {
            removeModal();
            deferred.resolve(data);
        },
        error: function (e) {
            removeModal();
            showErrorInfo(e, "验证发票号服务异常");
            deferred.reject();
        }
    });
    return deferred;
}


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
        var sfdj = $parentTr.find(".bdc-sfdj option:nth-child(" + sfdjIndex + ")").data('dj');
        var jmje = $parentTr.find('.bdc-jmje').val();
        if (sfxm == "土地使用权交易服务费") {
            var getShje = calculateFloat(parseFloat(sfdj) * parseFloat(sl));
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
        hj += formatFloat(parseFloat($($ssjeList[i]).val()));
    });
    $table.find('.bdc-hj').val(formatFloat(hj));
}

// 页面展示时过滤收费项目数据，对数量为0的数据不进行展现。
function filterNotNull(data) {
    var notNullData = new Array();
    $.each(data, function (index, value) {
        if (value.sl != 0) {
            notNullData.push(value);
        }
    });
    return notNullData;
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
    $parentTr.find('.bdc-ssje-input').val(calculateFloat(getYsje - jmje));
    //合计
    computeTotal($parentTable);
}

//保存收费信息
function saveSfxx() {
    if (isNotBlank(sfxxid)) {
        var qlrSfxx = {};
        if (isNotBlank($('#qlrSfxm .bdc-hj').val())) {
            qlrSfxx.hj = $('#qlrSfxm .bdc-hj').val();
        } else {
            qlrSfxx.hj = 0;
        }
        qlrSfxx.sfxxid = sfxxid;
        //保存收费信息
        $.ajax({
            url: BASE_URL + "/sf/xx",
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
}

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
                // 南通对收费金额不为0的项目进行保存与展示
                if (sfxm.sfxmmc !== null && sfxm.sfxmmc !== "" && sfxm.sl != 0) {
                    sfxmList.push(sfxm);
                }
            }
        });
    });
    if (isNotBlank(sfxmList)) {
        $.ajax({
            url: BASE_URL + "/sf/xm",
            type: 'PATCH',
            dataType: 'json',
            async: false,
            contentType: "application/json",
            data: JSON.stringify(sfxmList),
            success: function (data) {
                $("#query").click();
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        });
    } else {
        $("#query").click();
    }
}

//单击删除收费项目
function deleteSfxmTr(item, sfxmid) {
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
                        } else {
                            $this.parents('tr').remove();
                            computeTotal($table);
                        }
                        saveSfxx();
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
        url: BASE_URL + "/sf/xm?sfxmid=" + sfxmid,
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

// 更新收费信息是否开票信息
function modifyKpzt(sfxxidList){
    $.ajax({
        url: BASE_URL + "/sf/kpzt",
        type: "POST",
        data: JSON.stringify(sfxxidList),
        contentType: 'application/json',
        dataType: "json",
        success: function (data) {
        },
        error: function (e) {
            delAjaxErrorMsg(xhr);
        }
    });
}