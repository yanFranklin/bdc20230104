/**
 * @author "<a href="mailto:lixin1@gtmap.cn>lixin</a>"
 * @version 1.0, 2019/6/19
 * @description 信息补录修改项目基本信息 JS
 */
layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});
var zdList;
var xmid;
var processInsId;
var ygzlslid;
// 编辑类型
var type;
// 是否生成证书
var sfsczs;
var djyyList = {};
var xgnrglxs;
var moduleCode = 'xxblUpdateinfo';
var isDeleteBlxxLc = false;
var readOnly = false;
var formStateId = "";
var gzldyid = $.getUrlParam('processDefKey');
var lclx;
layui.use(['jquery', 'form', 'laytpl', 'laydate', 'layer', 'formSelects'], function () {
    var $ = layui.jquery,
        laytpl = layui.laytpl,
        layer = layui.layer,
        formSelects = layui.formSelects,
        form = layui.form;
    $(function () {
        addModel();
        processInsId = $.getUrlParam('processInsId');
        var taskId = $.getUrlParam('taskId');
        ygzlslid = $.getUrlParam('ygzlslid');
        isDeleteBlxxLc = $.getUrlParam("isDeleteBlxxLc");
        formStateId = $.getUrlParam("formStateId");
        readOnly = $.getUrlParam("readOnly");
        // 获取必要参数
        $.ajax({
            url: "/realestate-register-ui/rest/v1.0/blxx/blxz/param",
            type: 'GET',
            data: {
                gzlslid: processInsId,
                taskid: taskId,
            },
            dataType: 'json',
            async: false,
            success: function (data) {
                // 赋值前保留 ygzlslid
                if (isNullOrEmpty(ygzlslid)) {
                    ygzlslid = processInsId;
                }
                processInsId = data.processInsId;
                xmid = data.xmid;
                type = data.type;
                xgnrglxs = data.xgnrglxs;
                if(isNullOrEmpty(data.lclx)){
                    lclx= "jdlc"
                }else{
                    lclx = data.lclx;
                }
                removeModel();


                /**
                 * 设置权限控制样式
                 */
                if (!isNullOrEmpty(type) && type === "check") {
                    $('#addQlr').addClass('bdc-hide');
                    $('#saveData').addClass('bdc-hide');
                }

                $('.bdc-content').css('min-height', $('body').height() - 56);

                zdList = getZdList();

                /**
                 * 加载页面信息
                 */
                if (!isNullOrEmpty(processInsId)) {
                    loadXmxx(processInsId);
                }

                if(!isNullOrEmpty(isDeleteBlxxLc)){
                    $("#dbxx").hide();
                }

            }
        });

        /**
         * 监听下一页按钮
         */
        form.on("submit(next)", function (data) {
            var bdcxm = data.field;
            var qllx = parseInt(bdcxm.qllx);
            var bdcdyfwlx = parseInt(bdcxm.bdcdyfwlx);
            if (type !== 'check') {
                var btx = btxCheck(bdcxm.dbr, bdcxm.djsj);
                if (!btx) {
                    warnMsg("登簿人, 登记时间必须填写！");
                    return;
                }
            }
            $.ajax({
                url: "/realestate-register-ui/rest/v1.0/blxx/qlr",
                type: 'GET',
                dataType: 'json',
                data: {xmid: xmid,qlrlb:1},
                success: function (data) {
                    if (!isNullOrEmpty(data)) {
                        window.location.href = getContextPath() + "/view/xxbl/lc_qlxx.html?qllx=" + qllx + "&bdcdyfwlx=" + bdcdyfwlx + "&xmid=" + xmid
                            + "&processInsId=" + processInsId + "&type=" + type + "&sfsczs=" + sfsczs + "&taskId=" + taskId
                            + "&ygzlslid=" + ygzlslid + "&isDeleteBlxxLc=" + isDeleteBlxxLc + "&readOnly=" + readOnly + "&lclx=" + lclx;
                    } else {
                        removeModel();
                        warnMsg("请先添加权利人信息！")
                    }
                },
                error: function (xhr, status, error) {
                    removeModel();
                    delAjaxErrorMsg(xhr)
                }
            });

        });

        /**
         * 监听对比信息按钮
         */
        form.on("submit(dbxx)", function (data) {
            var url = getContextPath() + "/view/xxbl/dbym.html?processInsId="+ processInsId+"&type=xxbl"+"&xmid="+xmid;
            if(lclx == "pllc"){
             url = getContextPath() + "/view/xxbl/dbxxList.html?processInsId="+ processInsId;
            }
            window.open(url);
        });

        /**
         * 监听保存数据按钮
         */
        form.on("submit(saveData)", function (data) {
            addModel();
            try {
                $.when(saveQlr(), saveXmxx(data), updateRyzd(data),insertXgLog()).done(function () {
                    removeModel();
                    successMsg("保存成功");
                });
            } catch (e) {
                removeModel();
                ERROR_CONFIRM("保存失败", e.message);
            }
        });

        /**
         * 监听添加权利人按钮
         */
        form.on("submit(addQlr)", function (data) {
            addQlr(data.field.qllx, data.field.xmid);
        });

        /**
         * 监听房产档案按钮
         */
        form.on("submit(fcda)", function (data) {
            var url = "/realestate-register-ui/view/daxx/scan.html?slbh=" + data.field.slbh + "&bdcqzh=";
            if (!isNullOrEmpty(data.field.bdcqzh)) {
                var strs = [];
                strs = data.field.bdcqzh.split(","); //字符分割
                url += encodeURI(strs[0]);
            }
            window.open(url);
        });

        /**
         * 加载项目信息（发起请求）
         * @param processInsId
         */
        function loadXmxx(processInsId) {
            addModel();
            $.ajax({
                url: "/realestate-register-ui/rest/v1.0/plblxx/xmxx",
                type: 'GET',
                data: {
                    processInstanceId: processInsId,
                    xmid: xmid,
                    lclx: lclx
                },
                dataType: 'json',
                success: function (datas) {
                        generateBdcdyxx(datas);
                        var data = datas[0];
                        bdcXm = data;
                        xmid = data.xmid;
                        //面积单位为空时默认为平方米
                        if (data.mjdw === null || data.mjdw === '') {
                            data.mjdw = '1'
                        }
                        if (!isNullOrEmpty(data.djxl)) {
                            getDjyyList(data.djxl);
                        }
                        form.on('checkbox(mjdw)', function () {
                            $("[name='mjdw']").prop("checked", "");
                            $(this).prop("checked", "checked");
                            form.render('checkbox');
                        });
                        generateXmxx(data);
                        //如果有原产权证号并且是信息补录修改的流程，传ycqzh
                        $.ajax({
                            url: "/realestate-register-ui/rest/v1.0/blxx/bllc/blxglc",
                            type: 'GET',
                            data: {
                                gzldyid: gzldyid
                            },
                            dataType: 'json',
                            success: function (result) {
                                if (result && data.bdcqzh) {
                                    sessionStorage.setItem("xxbl" + processInsId, data.bdcqzh);
                                }
                            }
                        });
                        loadQlr();
                        queryXgLog();
                        renderChange("", form, "all");
                        // 判断是否生成证书
                        checkSfsczs(data.djxl);
                        setElementAttrByModuleAuthority(moduleCode);
                        if (readOnly === "true") {
                            setAllElementDisabled();
                        }

                    removeModel();
                }
            });
        }

        /**
         * 根据 djxl 获取登记原因
         * @param djxl 登记小类
         */
        function getDjyyList(djxl) {
            $.ajax({
                url: "/realestate-register-ui/rest/v1.0/blxx/djyy",
                type: 'GET',
                dataType: 'json',
                data: {djxl: djxl},
                async: false,
                success: function (data) {
                    if (!isNullOrEmpty(data)) {
                        djyyList = data;
                    }
                }
            });
        }

        /**
         * 渲染项目信息
         * @param bdcxmxx
         */
        function generateXmxx(bdcxmxx) {
            var json = {
                bdcxmxx: bdcxmxx,
                zd: zdList,
                djxldjyy: djyyList
            };
            var tpl = xmxxTpl.innerHTML, view = document.getElementById('xmxx');
            laytpl(tpl).render(json, function (html) {
                view.innerHTML = html;
            });
            form.render();
            if (!isNullOrEmpty(type) && type === "check") {
                // setAllElementDisabled();
            }
            form.render('select');
            if (isNullOrEmpty(bdcxmxx.djyy)) {
                formSelects.render('djyy', {
                    create: function (id, name) {
                        return name;  //返回该标签对应的val
                    }
                });
            } else {
                formSelects.render('djyy', {
                    init: [bdcxmxx.djyy, bdcxmxx.djyy],
                    create: function (id, name) {
                        return name;  //返回该标签对应的val
                    }
                });
            }
            form.render();
            // 日期的渲染放在表单渲染后
            renderDate();
            // ygzlslid 和 processInsId 说明此流程是修改流程
            if (ygzlslid !== processInsId) {
                queryDbGl(ygzlslid);
            }

            form.on('select', function (data) {
                //if (isNotBlank(xgnrglxs) && xgnrglxs.indexOf(djxl) > -1) {
                $(data.elem.parentElement).addClass('bdc-change-input');
                var text = $(data.elem).parents(".layui-inline").find("label").text();
                console.log($(data.elem.parentElement));
                renderChangeTips(text);
                //}
            });
        }

        function generateBdcdyxx(data) {
            var json = {
                bdcxmxx: data,
                zd: zdList,
                djxldjyy: djyyList
            };
            var tpl = bdcdyxxTpl.innerHTML, view = document.getElementById('bdcdyxx');
            laytpl(tpl).render(json, function (html) {
                view.innerHTML = html;
            });
            form.render();

        }

            /**
         * 新增权利人展示
         * @param qllx 权力类型
         * @param xmid 项目 ID
         * @param dydj 抵押登记，判断抵押增加的权利人类型
         */
        function addQlr(qllx, xmid, dydj) {
            var url = getIP() + "/realestate-accept-ui/view/slym/qlr.html?xmid=" + xmid + "&processInsId=" + processInsId + "&qllx=" + qllx + "&type=XXBL" + "&lclx=" + lclx;
            if (qllx === commonDyaq_qllx || dydj) {
                layer.open({
                    type: 2,
                    area: ['960px', '500px'],
                    fixed: false, //不固定
                    title: "新增抵押权人",
                    content: url + "&dydj=true",
                    btnAlign: "c",
                    cancel: function(index, layero){
                    }
                });
            } else {
                layer.open({
                    type: 2,
                    area: ['960px', '500px'],
                    fixed: false, //不固定
                    title: "新增申请人",
                    content: url,
                    btnAlign: "c",
                    cancel: function(index, layero){
                    }
                });
            }
            form.render();
        }

        /**
         * 保存项目信息
         * @param data 需要保存的项目信息
         */
        function saveXmxx(data) {
            var bdcxm = data.field;

            bdcxm.djyy = formSelects.value('djyy', 'valStr');
            if (!isNullOrEmpty(bdcxm.djsj)) {
                bdcxm.djsj = bdcxm.djsj + " 00:00:00";
            }
            bdcxm.xmid = xmid;
            // 受理时间不保存
            delete bdcxm.slsj;

            var bdcXmArray = $(".bdcxm-pl").serializeArray();
            var xmxx = {};
            var xmxxList = [];
            if(bdcXmArray.length != 0){
                for (var j = 0; j < bdcXmArray.length; j++) {
                    var name = bdcXmArray[j].name;
                    xmxx[name] = bdcXmArray[j].value;
                    if ((j + 1 < bdcXmArray.length && bdcXmArray[j + 1].name === 'xmid') || j + 1 == bdcXmArray.length) {
                        xmxx.slbh = bdcxm.slbh;
                        xmxx.djyy = bdcxm.djyy;
                        xmxx.djsj = bdcxm.djsj;
                        xmxx.sqfbcz = bdcxm.sqfbcz;
                        xmxx.slr = bdcxm.slr;
                        xmxx.mjdw = bdcxm.mjdw;
                        xmxx.bz = bdcxm.bz;
                        xmxx.dbr = bdcxm.dbr;
                        xmxxList.push(xmxx);
                        xmxx = {};
                    }
                }
            }else{
                xmxxList.push(bdcxm);
            }
            //console.info(bdcXmArray);
            //console.info(xmxxList);
            //console.info(data);
            if (bdcxm.xmid !== "" && bdcxm.xmid !== null) {
                $.ajax({
                    url: "/realestate-register-ui/rest/v1.0/plblxx/plxmxx",
                    type: 'POST',
                    contentType: "application/json",
                    dataType: "json",
                    async: false,
                    data: JSON.stringify(xmxxList),
                    success: function () {
                        loadXmxx(processInsId);
                    },
                    error: function (err) {
                        throw err;
                    }
                });
            } else {
                throw err;
            }
        }

        /**
         * 保存项目表内容时同步更新冗余字段
         * @param data
         */
        function updateRyzd(data) {
            var qlDataList = [];
            var bdcXmArray = $(".bdcxm-pl").serializeArray();
            var xmxxList = [];
            var xmxx = {};
            var qlData = {};
            // 同步登记原因 (补录暂时没有注销流程)
            var djyy = formSelects.value('djyy', 'valStr');
            var qllx = data.field.qllx;
            if (qllx == "98") {
                qlData.cfyy = djyy;
            } else if (qllx !== "97") {
                qlData.djyy = djyy;
            }
            if (qllx == "3") {
                // 土地权利的修改需要同步以下两个字段内容
                qlData.qlxz = data.field.qlxz;
                qlData.syqmj = data.field.zdzhmj;
            }
            qlData.zl = data.field.zl;
            // 定着物面积与建筑面积同步
            qlData.jzmj = data.field.dzwmj;
            // 定着物用途与规划用途同步
            qlData.ghyt = data.field.dzwyt;
            // 更新登簿人，登簿时间的冗余字段
            qlData.djsj = data.field.djsj;
            qlData.dbr = data.field.dbr;
            qlData.dbjg = data.field.dbjg;
            qlData.xmid = xmid;

            if (bdcXmArray.length != 0) {

                for (var j = 0; j < bdcXmArray.length; j++) {
                    var bdcxm = data.field;

                    bdcxm.djyy = formSelects.value('djyy', 'valStr');
                    var name = bdcXmArray[j].name;
                    xmxx[name] = bdcXmArray[j].value;
                    if ((j + 1 < bdcXmArray.length && bdcXmArray[j + 1].name === 'xmid') || j + 1 == bdcXmArray.length) {
                        xmxx.djyy = bdcxm.djyy;
                        xmxx.dbr = bdcxm.dbr;
                        xmxxList.push(xmxx);
                        xmxx = {};
                    }
                }
                if (xmxxList.length != 0) {
                    for (var i = 0; i < xmxxList.length; i++) {
                        var bdcxm = xmxxList[i];
                        var qlData = {};
                        // 同步登记原因 (补录暂时没有注销流程)
                        var djyy = bdcxm.djyy;
                        var qllx = bdcxm.qllx;
                        if (qllx == "98") {
                            qlData.cfyy = djyy;
                        } else if (qllx !== "97") {
                            qlData.djyy = djyy;
                        }
                        if (qllx == "3") {
                            // 土地权利的修改需要同步以下两个字段内容
                            qlData.qlxz = bdcxm.qlxz;
                            qlData.syqmj = bdcxm.zdzhmj;
                        }
                        qlData.zl = bdcxm.zl;
                        // 定着物面积与建筑面积同步
                        qlData.jzmj = bdcxm.dzwmj;
                        // 定着物用途与规划用途同步
                        qlData.ghyt = bdcxm.dzwyt;
                        // 更新登簿人，登簿时间的冗余字段
                        qlData.djsj = bdcxm.djsj;
                        qlData.dbr = bdcxm.dbr;
                        qlData.dbjg = bdcxm.dbjg;
                        qlData.xmid = bdcxm.xmid;
                        qlDataList.push(qlData);

                    }

                } else {
                    qlDataList.push(qlData);
                }


                if (!$.isEmptyObject(qlData) && !isNullOrEmpty(xmid) && isNullOrEmpty(isDeleteBlxxLc)) {
                    $.ajax({
                        url: "/realestate-register-ui/rest/v1.0/plblxx/updateQlRyzd?processInsId=&xmid=" + xmid,
                        type: 'PATCH',
                        contentType: "application/json",
                        dataType: "json",
                        async: false,
                        data: JSON.stringify(qlDataList),
                        success: function () {
                        }, error: function (err) {
                            throw err;
                        }
                    });
                } else {
                    if (isNullOrEmpty(isDeleteBlxxLc)) {
                        throw err;
                    }
                }

            }
        }
        /**
         * 判断是否生成证书
         * 不生成证书修改页面样式，隐藏第 4 步内容
         * @param djxl 登记小类
         */
        function checkSfsczs(djxl) {
            var url = "/realestate-register-ui/rest/v1.0/blxx/sfsczs?xmid=" + xmid + "&djxl=" + djxl;
            if(!isNullOrEmpty(isDeleteBlxxLc)){
                $.ajax({
                    url: "/realestate-register-ui/rest/v1.0/blxx/blxz/getYqlxm",
                    type: 'GET',
                    async: false,
                    data: {gzlslid: processInsId},
                    success: function (data) {
                        console.info(data);
                        removeModel();
                        if (!isNullOrEmpty(data) ) {
                         url = "/realestate-register-ui/rest/v1.0/blxx/sfsczs?xmid=" + data.xmid + "&djxl=" + data.djxl;
                        }
                    }, error: function (e) {
                        console.error(e);
                    }
                });
            }
            $.ajax({
                url: url,
                type: "GET",
                async: false,
                success: function (data) {
                    sfsczs = data;
                    if (sfsczs === "0" || sfsczs === 0) {
                        // 进度栏修改为 3 等分
                        $('.bdc-header > p ').css("width", "33.3%");
                        // 隐藏 4. 证书/明信息
                        $('#zsm').addClass('bdc-hide');
                        // 修改 2/4 为 2/3
                        $('#sczs').html("2/3：核验基本信息");
                    }
                    if (sfsczs === "-1" || sfsczs === -1) {
                        console.log("未获取到生否生成证书信息，默认为生成证书");
                    }
                },
                error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr);
                }
            });
        }
    });

    //renderDate(form,"all");
});

/**
 * 加载权利人信息（向后台发起请求获取数据）
 * @param xmid 项目ID
 */
function loadQlr() {
    $.ajax({
        url: "/realestate-register-ui/rest/v1.0/blxx/qlr",
        type: 'GET',
        dataType: 'json',
        data: {xmid: xmid, gzlslid: processInsId},
        success: function (data) {
            //if (!isNullOrEmpty(data)) {
                generateQlrxx(data, xmid);
            //}
        }
    });
}

/**
 * 渲染权利人信息
 * @param data 从后台获取的数据
 * @param xmid 项目 ID
 */
function generateQlrxx(data, xmid) {
    var json = {
        xmid: xmid,
        bdcQlrDOList: data,
        zd: zdList
    };
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var $ = layui.jquery,
            laytpl = layui.laytpl,
            layer = layui.layer,
            laydate = layui.laydate,
            form = layui.form;
        var tpl = sqrTpl.innerHTML, view = document.getElementById('qlrxx');
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
        // renderDate(form);
        form.render();
        if (!isNullOrEmpty(type) && type === "check") {
            setAllElementDisabled();
        }
        if (readOnly === "true") {
            setAllElementDisabled();
        }
        form.render('select');
        disabledAddFa();
        // ygzlslid 和 processInsId 说明此流程是修改流程
        if (ygzlslid !== processInsId) {
            queryDbGl(ygzlslid);
        }
    });
}

/**
 * 权利人详情展示
 * @param qlrid 权利人 ID
 * @param xmid 项目 ID
 * @param qlrlb 权利人类别
 */
function showQlr(qlrid, xmid, qlrlb) {
    addModel();
    var qllx = $(".qllx").val();
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var $ = layui.jquery,
            laytpl = layui.laytpl,
            layer = layui.layer,
            laydate = layui.laydate,
            form = layui.form;
        var readOnly = false;
        if (!isNullOrEmpty(type) && type === "check") {
            readOnly = true;
        }
        var djxl = $("input[name='djxl']").val();
        //登记类型
        var djlx = $("input[name='djlx']").val();

        var url = getIP() + "/realestate-accept-ui/view/slym/qlr.html?readOnly=" + readOnly + "&processInsId=" + processInsId + "&qlrid=" + qlrid + "&xmid=" + xmid + "&qllx=" + qllx + "&djlx=" + djlx + "&djxl=" + djxl + "&type=XXBL" + "&lclx=" + lclx;
        // var url = "http://127.0.0.1:8687/realestate-accept-ui/view/slym/qlr.html?readOnly=" + readOnly + "&processInsId=" + processInsId + "&qlrid=" + qlrid + "&lclx=jdlc" + "&xmid=" + xmid + "&qllx=" + qllx;
        layer.open({
            type: 2,
            area: ['960px', '475px'],
            fixed: false, //不固定
            title: "申请人详细信息",
            content: url,
            btnAlign: "c",
            cancel: function(index, layero){
                location.reload();
            }
        });

        removeModel();
        form.render();
        // renderDate(form);
        disabledAddFa();
    })
}

/**
 * 保存权利人
 */
function saveQlr() {
    var qlrArray = $(".qlr").serializeArray();
    var qlrList = [];
    var qlr = {};
    var qlrnum = 0;
    var gyfs = "";

    for (var j = 0; j < qlrArray.length; j++) {
        var name = qlrArray[j].name;
        qlr[name] = qlrArray[j].value;
        if ((j + 1 < qlrArray.length && qlrArray[j + 1].name === 'xmid') || j + 1 == qlrArray.length) {
            if (qlr.qlrlb === "1") {
                qlrnum++;
                gyfs += qlr.gyfs + ",";
            }
            qlrList.push(qlr);
            qlr = {};
        }
    }
    //验证权利人共有方式
    if (!checkGyfs(gyfs, qlrnum)) {
        layer.msg('共有方式不正确，请检查！');
        throw err = new Error('共有方式不正确，请检查！');
    }

    if (!checkAddGybl(qlrList)) {
        layer.msg('共有比例不正确，请检查！');
        throw err = new Error('共有比例不正确，请检查！');
    }
    var url = getContextPath() + "/rest/v1.0/blxx/qlr?processInsId=" + processInsId;

    if (!isNullOrEmpty(qlrList)) {
        $.ajax({
            url: url,
            type: 'PATCH',
            dataType: 'json',
            async: false,
            contentType: "application/json",
            data: JSON.stringify(qlrList),
            success: function (data) {
                if (data > 0) {
                    loadQlr();
                }
            }, error: function (err) {
                throw err;
            }
        });
    }
}

/**
 * 删除权利人
 * @param qlrid 权利人 id
 * @param sxh 顺序号
 * @param qlrlb 权利人类别
 */
function deleteQlr(qlrid, sxh, qlrlb) {
    var url = "/realestate-register-ui/rest/v1.0/blxx/qlr?xmid=" + xmid + "&qlrid=" + qlrid + "&sxh=" + sxh + "&qlrlb=" + qlrlb + "&processInsId=" + processInsId;
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
            $.ajax({
                url: url,
                type: 'DELETE',
                success: function (data) {
                    removeModel();
                    loadQlr();
                    layer.msg("删除成功");
                    location.reload();
                }
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
 * 改变权利人的顺序号
 * @param qlrid 权利人 id
 * @param czlx 操作类型 UP DOWN
 */
function changeQlrSxh(qlrid, czlx) {
    $.ajax({
        url: "/realestate-register-ui/rest/v1.0/blxx/qlr/sxh",
        type: 'get',
        dataType: 'json',
        data: {qlrid: qlrid, czlx: czlx},
        success: function (data) {
            if (data > 0) {
                loadQlr();
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

//验证共有比例之和
function checkAddGybl(qlrList) {
    var sumgybl = 0;
    var gyblList = [];
    var qlrs = [];
    //分母相乘
    var fenmumultiply = 1;
    //分子之和
    var fenzicount = 0;
    for (var k in qlrList) {
        if (qlrList[k].qlrlb === "1") {
            qlrs.push(qlrList[k]);
        }
    }
    if (!isNullOrEmpty(qlrs)) {
        for (var k in qlrs) {
            if (qlrs[k].gyfs === "2") {
                if (!isNullOrEmpty(qlrs[k].qlbl)) {
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
            } else if (!isNullOrEmpty(qlrs[k].qlbl)) {
                layer.msg("非按份共有请勿填写共有比例！");
                throw err = new Error('非按份共有请勿填写共有比例!');
            }
        }
    } else {
        //无权利人直接通过
        return true;
    }
    if (gyblList.length > 0) {
        for (var i in gyblList) {
            sumgybl += gyblList[i];
        }
        if (sumgybl === 100) {
            return true;

        }
    } else if (fenmumultiply > 1) {
        for (var k in qlrList) {
            if (qlrList[k].qlrlb === "1" && !isNullOrEmpty(qlrList[k].qlbl)) {
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

function checkGyfs(gyfs, qlrnum) {
    if (qlrnum === 1) {
        //一个权利人时共有方式必须为单独所有
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

//关闭panel (新增申请人弹窗关闭方法)
function cancelEdit() {
    layui.use(['layer'], function () {
        var layer = layui.layer;
        location.reload();
        layer.closeAll();
    });
}

/**
 * 必填项验证
 * @param
 * @returns {boolean}
 */
function btxCheck(dbr, djsj) {
    var check = false;
    if (!isNullOrEmpty(dbr) && !isNullOrEmpty(djsj)) {
        addModel();
        // 页面上均填写了，检查数据库中是否包含值，确实是否填写了数据。
        $.ajax({
            url: "/realestate-register-ui/rest/v1.0/blxx/xmxx",
            type: 'GET',
            data: {
                processInstanceId: processInsId,
                xmid: xmid
            },
            async: false,
            dataType: 'json',
            success: function (data) {
                removeModel();
                if (!isNullOrEmpty(data.dbr) && !isNullOrEmpty(data.djsj)) {
                    check = true;
                }
            }
        });
    }
    return check;
}


/**
 * 渲染日期
 */
function renderDate(form, formIds) {
    var laydate = layui.laydate;
    var format = 'yyyy-MM-dd';
    lay('.date').each(function () {
        if (isNotBlank(this.value)) {
            this.value = Format(this.value, format);
        }

        laydate.render({
            elem: this,
            format: format,
            trigger: 'click'
            , done: function (value, date, endDate) {
                //监听修改
                var $date = this.elem;
                $date.addClass('bdc-change-input');
                var text = $($date).parents(".layui-inline").find("label").text();
                renderChangeTips(text);
            }
        });
    });

}

function insertXgLog() {
    var tsxx = $("#updateTips p").text();
    if (isNotBlank(tsxx)) {
        var bdcXxXgDTO = {};
        bdcXxXgDTO.tsxx = tsxx;
        var bdcXxXgZbDTOList = [];
        $(".bdc-change-input").each(function (i) {
            var bdcXxXgZbDTO = {};
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
        getReturnData("/rest/v1.0/blxx/addXgLog?gzlslid=" + processInsId, JSON.stringify(bdcXxXgDTO), "POST", function () {

        }, function (error) {
            delAjaxErrorMsg(error);


        })
    }
}

/**
 * 查询修改日志
 */
function queryXgLog() {
    getReturnData("/rest/v1.0/blxx/queryXgLog", {gzlslid: processInsId}, "GET", function (data) {
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

function showBdcdyxx(xmid){
    var url = getIP() + "/realestate-accept-ui/view/slym/bdcdy.html?readOnly=" + readOnly + "&xmid=" + xmid+ "&type=xxbl";
    layer.open({
        type: 2,
        area: ['1200px', '800px'],
        fixed: false, //不固定
        title: "不动产单元详细信息",
        content: url,
        btnAlign: "c",
        cancel: function(index, layero){
            location.reload();
        }
    });
}