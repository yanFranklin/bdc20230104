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
// 编辑类型
var type;
// 是否生成证书
var sfsczs;
var ygzlslid;
var yxmid;
// 是否注销流程
var zxxx = {};
var xgyyList = {};
var moduleCode = 'xxblUpdateinfo';
var hasQlrFlag = true;
layui.use(['jquery', 'form', 'laytpl', 'laydate', 'layer', 'formSelects'], function () {
    var $ = layui.jquery,
        laytpl = layui.laytpl,
        layer = layui.layer,
        laydate = layui.laydate,
        formSelects = layui.formSelects,
        form = layui.form;
    $(function () {
        // 获取参数
        processInsId = $.getUrlParam('processInsId');
        ygzlslid = $.getUrlParam('ygzlslid');
        xmid = $.getUrlParam('xmid');
        // 获取操作类型（check、update 和 new）
        type = $.getUrlParam('type');
        var diyq = false;
        // 获取必要参数
        $.ajax({
            url: "/realestate-register-ui/rest/v1.0/blxx/blxz/sfscql",
            type: 'GET',
            data: {
                gzlslid: processInsId,
                xmid:xmid
            },
            dataType: 'json',
            async: false,
            success: function (data) {
                // 赋值前保留 ygzlslid
                if (isNullOrEmpty(ygzlslid)) {
                    ygzlslid = processInsId;
                }
                if(isNullOrEmpty(xmid)){
                    xmid = data.xmid;
                }
                processInsId = data.gzlslid;
                if (isNullOrEmpty(processInsId)) {
                    warnMsg("原项目数据缺少 gzlslid，联系管理员处理历史数据！");
                }
                if (ygzlslid !== processInsId) {
                    $.ajax({
                        url: "/realestate-register-ui/rest/v1.0/blxx/xmxx",
                        type: 'GET',
                        data: {
                            processInstanceId: ygzlslid,
                            xmid: ""
                        },
                        dataType: 'json',
                        success: function (data) {
                            zxxx = data;
                            yxmid = zxxx.xmid;
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

                            /**
                             * 加载注销信息
                             */
                            if (!isEmptyObject(zxxx)) {
                                loadZxxx(zxxx);
                            }
                        }
                    });
                } else {
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

                    /**
                     * 加载注销信息
                     */
                    if (!isEmptyObject(zxxx)) {
                        loadZxxx(zxxx);
                    }
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
                var btx = btxCheck();
                if (!btx) {
                    if (isEmptyObject(zxxx)) {
                        warnMsg("登簿人, 登记时间必须填写！");
                    } else {
                        if (zxxx.qllx === 98) {
                            warnMsg("解封登簿人, 解封登记时间必须填写！");
                        } else {
                            warnMsg("注销登簿人, 注销登记时间必须填写！");
                        }
                    }
                    return;
                }
            }
            if (!hasQlrFlag) {
                warnMsg("请新增权利人！");
                return;
            }

            window.location.href = getContextPath() + "/view/xxbl/xxblUpdateQlxx.html?qllx=" + qllx + "&bdcdyfwlx=" + bdcdyfwlx + "&xmid=" + xmid + "&processInsId=" + processInsId + "&type=" + type + "&sfsczs=" + sfsczs + "&ygzlslid=" + ygzlslid;
        });

        /**
         * 监听保存数据按钮
         */
        form.on("submit(saveData)", function (data) {
            addModel();
            try {
                $.when(saveQlr(), saveXmxx(data), updateRyzd(data)).done(function () {
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

        // 监听修改原因选项
        form.on("select(xgyySelect)", function (data){
            if(data.value === 'others'){
                $("#qtyy").css('visibility','visible');
            }else{
                $("#qtyy").css('visibility','hidden');
                $("#xgyyInput").attr("value", "");
            }
        })

        /**
         * 加载项目信息（发起请求）
         * @param processInsId
         */
        function loadXmxx(processInsId) {
            addModel();
            $.ajax({
                url: "/realestate-register-ui/rest/v1.0/blxx/xmxx",
                type: 'GET',
                data: {
                    processInstanceId: processInsId,
                    xmid: xmid
                },
                dataType: 'json',
                success: function (data) {
                    bdcXm = data;
                    xmid = data.xmid;
                    //面积单位为空时默认为平方米
                    if (data.mjdw === null || data.mjdw === '') {
                        data.mjdw = '1'
                    }
                    generateXmxx(data);
                    // 设置修改时间和修改人
                    setXgsjAndXgr();
                    // 获取不动产权证号
                    getBdcqzh(xmid);

                    loadQlr();
                    // 地役权需要额外显示权利信息
                    if (data.qllx === 19) {
                        diyq = true;
                        loadQlxx();
                    }
                    // 判断是否生成证书
                    checkSfsczs(data.djxl);
                    if (type != "check") {
                        setElementAttrByModuleAuthority(moduleCode);
                    }
                    removeModel();
                }
            });
        }

        /**
         * 渲染注销信息
         */
        function loadZxxx() {
            if (!isEmptyObject(zxxx)) {
                var json = {zxxx: zxxx};
                var tpl = zxxxTpl.innerHTML, view = document.getElementById('zxxx');
                laytpl(tpl).render(json, function (html) {
                    view.innerHTML = html;
                });
                form.render();
                if (!isNullOrEmpty(type) && type === "check") {
                    setAllElementDisabled();
                }
                renderDate();
            }
        }

        /**
         * 渲染项目信息
         * @param bdcxmxx
         */
        function generateXmxx(bdcxmxx) {
            var json = {
                bdcxmxx: bdcxmxx,
                zd: zdList,
            };
            var tpl = xmxxTpl.innerHTML, view = document.getElementById('xmxx');
            laytpl(tpl).render(json, function (html) {
                view.innerHTML = html;
            });
            form.render();
            if (!isNullOrEmpty(type) && type === "check") {
                setAllElementDisabled();
            }
            form.render('select');
            form.render();
            // 日期的渲染放在表单渲染后
            renderDate();
        }

        /**
         * 设置修改时间和修改人
         */
        function setXgsjAndXgr(){
            var current = new Date();
            var year=current.getFullYear();        //获取当前年
            var month=current.getMonth()+1;   //获取当前月
            var date=current.getDate();            //获取当前日

            var now=year+'-'+month+"-"+date;
            $("#xgsj").val(now);

            $.ajax({
                url: "/realestate-register-ui/rest/v1.0/blxx/getCurrentUserName",
                type: 'GET',
                success: function (data) {
                    if (!isNullOrEmpty(data)) {
                        $("#xgr").val(data);
                    }

                }
            });
        }

        function getBdcqzh(xmid){
            $.ajax({
                url: "/realestate-register-ui/rest/v1.0/blxx/zs?xmid=" + xmid +"&zsid=",
                type: 'GET',
                dataType: 'json',
                success: function (data) {
                    if (isNullOrEmpty(data)) {
                        warnMsg("未获取到不动产权证号！");
                    } else {
                        $("#bdcqzh").val(data.bdcqzh);
                        $("#zsid").val(data.zsid);
                    }
                },
                error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr)
                }
            });
        }

        /**
         * 加载权利信息（地役权）
         * @param xmid 项目ID
         */
        function loadQlxx() {
            $.ajax({
                url: "/realestate-register-ui/rest/v1.0/blxx/qlxx",
                type: 'GET',
                data: {
                    xmid: xmid
                },
                dataType: 'json',
                success: function (data) {
                    var tpl = qlxxTpl.innerHTML, view = document.getElementById('qlxx');
                    laytpl(tpl).render(data, function (html) {
                        view.innerHTML = html;
                    });
                    form.render();
                }
            });
        }

        function saveQlxx() {
            var xydbdcdyh = $("#xydbdcdyh").val();
            if (diyq == true && !isNullOrEmpty(xydbdcdyh)) {
                // 1. 组织 JSON 格式
                var result = "{";
                var qlid = $("#qlid").val();
                result = result + "\"qlid\":\"" + qlid + "\"" + ",\"xydbdcdyh\":\"" + xydbdcdyh + "\"}";
                console.info(result);
            }
            // 发起 ajax 请求，修改数据并保存相关日志
            $.ajax({
                url: "/realestate-register-ui/rest/v1.0/blxx/qlxx?xmid=" + xmid + "&className=BdcDyiqDO",
                type: "POST",
                contentType: "application/json;charset=utf-8",
                data: result,
                dataType: "json",
                success: function (data) {
                },
                error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr)
                }
            });
        }

        /**
         * 新增权利人展示
         * @param qllx 权力类型
         * @param xmid 项目 ID
         * @param dydj 抵押登记，判断抵押增加的权利人类型
         */
        function addQlr(qllx, xmid, dydj) {
            var url = getIP() + "/realestate-accept-ui/view/slym/qlr.html?xmid=" + xmid + "&lclx=jdlc&processInsId=" + processInsId + "&qllx=" + qllx +"&type=XXBL";
            if (qllx === commonDyaq_qllx || dydj) {
                layer.open({
                    type: 2,
                    area: ['960px', '500px'],
                    fixed: false, //不固定
                    title: "新增抵押权人",
                    content: url + "&dydj=true",
                    btnAlign: "c",
                    cancel: function(index, layero){
                        location.reload();
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
                        location.reload();
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
            // 修改原因
            var xgyy;
            if($("#xgyySelect").val() === 'others'){
                xgyy = $("#xgyyInput").val();
            }else{
                xgyy = $("#xgyySelect").val();
            }

            // 受理时间不保存
            delete bdcxm.slsj;
            // 保存注销信息
            if (!isEmptyObject(zxxx)) {
                var zxxm = {};
                zxxm.xmid = $("#zxxmid").val();
                zxxm.djsj = $("#zxsj").val() + " 00:00:00";
                zxxm.dbr = $("#zxdbr").val();
                $.ajax({
                    url: "/realestate-register-ui/rest/v1.0/blxx/xmxx",
                    type: 'POST',
                    contentType: "application/json",
                    dataType: "json",
                    data: JSON.stringify(zxxm),
                    success: function () {
                    },
                    error: function (err) {
                        throw err;
                    }
                });
            }

            if (bdcxm.xmid !== "" && bdcxm.xmid !== null) {
                $.ajax({
                    url: "/realestate-register-ui/rest/v1.0/blxx/xmxx?xgyy=" + xgyy,
                    type: 'POST',
                    contentType: "application/json",
                    dataType: "json",
                    data: JSON.stringify(bdcxm),
                    success: function () {
                    },
                    error: function (err) {
                        throw err;
                    }
                });

                // 保存产权证号
                saveBdcqzh(bdcxm.xmid);
            } else {
                throw err;
            }


        }

        function saveBdcqzh(xmid) {
            var zsid = $("#zsid").val();
            var bdcqzh = $("#bdcqzh").val();
            $.ajax({
                url: "/realestate-register-ui/rest/v1.0/blxx/zs/zsxx/bdcqzh",
                type: "GET",
                data: {
                    zsid: zsid,
                    xmid: xmid,
                    bdcqzh: bdcqzh
                },
                contentType: 'application/json',
                dataType: "json",
                async: false,
                success: function (data) {
                    if (data == -1) {
                        warnMsg("当前产权证号证号已经被使用！")
                    }
                }, error: function (xhr, status, error) {
                    removeModel();
                    delAjaxErrorMsg(xhr)
                }

            });
        }

        /**
         * 保存项目表内容时同步更新冗余字段
         * @param data
         */
        function updateRyzd(data) {
            var qlData = {};
            // 同步登记原因 (补录暂时没有注销流程)
            // var djyy = formSelects.value('djyy', 'valStr');
            var qllx = data.field.qllx;
            if (qllx == "98") {
                // qlData.cfyy = djyy;
                if (isEmptyObject(zxxx)) {
                    qlData.jfdjsj = $("#zxsj").val() + " 00:00:00";
                    qlData.jfdbr = $("#zxdbr").val();
                }
            } else if (qllx !== "97") {
                // qlData.djyy = djyy;
            }
            if (qllx == "3") {
                // 土地权利的修改需要同步以下两个字段内容
                qlData.qlxz = data.field.qlxz;
                qlData.syqmj = data.field.zdzhmj;
            }
            if (qllx == "19") {
                qlData.xydbdcdyh = $("#xydbdcdyh").val();
            }
            qlData.zl = data.field.zl;
            // 定着物面积与建筑面积同步
            qlData.jzmj = data.field.dzwmj;
            // 定着物用途与规划用途同步
            qlData.ghyt = data.field.dzwyt;
            // 更新登簿人，登簿时间的冗余字段
            // qlData.djsj = data.field.djsj;
            // qlData.dbr = data.field.dbr;
            qlData.dbjg = data.field.dbjg;

            if (!$.isEmptyObject(qlData) && !isNullOrEmpty(xmid)) {
                $.ajax({
                    url: "/realestate-register-ui/rest/v1.0/blxx/updateQlRyzd?processInsId=&xmid=" + xmid,
                    type: 'PATCH',
                    contentType: "application/json",
                    dataType: "json",
                    data: JSON.stringify(qlData),
                    success: function () {
                    }, error: function (err) {
                        throw err;
                    }
                });
            } else {
                throw err;
            }

        }

        /**
         * 判断是否生成证书
         * 不生成证书修改页面样式，隐藏第 4 步内容
         * @param djxl 登记小类
         */
        function checkSfsczs(djxl) {
            var checkXmid;
            if (isNullOrEmpty(yxmid)) {
                checkXmid = xmid;
            } else {
                checkXmid = yxmid;
            }
            $.ajax({
                url: "/realestate-register-ui/rest/v1.0/blxx/sfsczs?xmid=" + checkXmid + "&djxl=" + djxl,
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
        data: {xmid: xmid},
        success: function (data) {
            if(!isNullOrEmpty(data) && data.length > 0){
                hasQlrFlag = true;
            }else{
                hasQlrFlag = false;
            }
            if (!isNullOrEmpty(data)) {
                generateQlrxx(data, xmid);
            }
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
        form.render('select');
        disabledAddFa();
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
        var url = getIP() + "/realestate-accept-ui/view/slym/qlr.html?readOnly=" + readOnly + "&processInsId=" + processInsId + "&qlrid=" + qlrid + "&lclx=jdlc" + "&xmid=" + xmid + "&qllx=" + qllx +"&type=XXBL";
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
function btxCheck() {
    var check = false;
    if (isEmptyObject(zxxx)) {
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
                // if (!isNullOrEmpty(data.dbr) && !isNullOrEmpty(data.djsj)) {
                //     check = true;
                // }
                if(data){
                    check = true;
                }
            }
        });
    } else {
        if (!isNullOrEmpty($("#zxdbr").val()) && !isNullOrEmpty($("#zxsj").val())) {
            addModel();
            // 页面上均填写了，检查数据库中是否包含值，确实是否填写了数据。
            $.ajax({
                url: "/realestate-register-ui/rest/v1.0/blxx/xmxx",
                type: 'GET',
                data: {
                    processInstanceId: zxxx.gzlslid,
                    xmid: zxxx.xmid
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

    }
    return check;
}