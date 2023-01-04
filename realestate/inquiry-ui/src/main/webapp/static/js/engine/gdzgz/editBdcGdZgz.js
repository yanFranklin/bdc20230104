/**
 * @author  <a href="mailto:zhuyong@gtmap.cn>zhuyong</a>
 * @version 1.0, 2019/03/25
 * @description 新增子规则页面相关数据处理JS
 */
// 子规则全局变量
var bdcGzZgzDTO = {"bdcGzSjlDTOList": [], "bdcGzBdsTsxxDOList": []};
var checkflag = true;
// 缓存初始子规则内容，为了后续保存日志操作
var oldBdcZgzDTO;

layui.use(['jquery', 'table', 'laytpl', 'layer', 'form',], function () {
    var form = layui.form;
    var table = layui.table;
    var $ = layui.jquery;
    var layer = layui.layer;

    var BASE_URL = '/realestate-inquiry-ui';
    var gzid = $.getUrlParam("gzid");

    // 用户IP地址
    var ipaddress;

    // 获取用户IP地址
    try {
        ipaddress = $("#ipaddress").val();
        if (isNullOrEmpty(ipaddress)) {
            getUserIP(function (ip) {
                if (ip != null && ip != undefined) {
                    ipaddress = ip;
                }
            });
        }
    } catch (err) {
        console.info("获取IP地址出错：" + err.toString());
    }

    // 查询子规则信息
    $.ajax({
        url: BASE_URL + "/gdzgz/queryBdcGzGdZgzDTOByGzid",
        type: 'GET',
        contentType: 'application/json',
        data: {gzid: gzid},
        dataType: "json",
        success: function (data) {
            if (data) {
                oldBdcZgzDTO = data;

                // 赋值基本信息
                form.val('jbxxFrom', data);
                // 格式化代码
                if (!isNullOrEmpty(data.jydm)) {
                    formatCode(data.jydm, false);
                }
                // 更新日期
                $("#pzrq").val(format(new Date()));

                // 缓存子规则数据
                bdcGzZgzDTO = {"bdcGzSjlDTOList": data.bdcGzSjlDTOList, "bdcGzBdsTsxxDOList": data.bdcGzBdsTsxxDOList};

                // 生成数据流数据
                creatGzSjl(data);
            } else {
                fail();
            }
        },
        error: function ($xhr, textStatus, errorThrown) {
            fail();
        }
    });

    /**
     * 切换到结果校验Tab页同步下数据
     */
    $(".jgjyTab").click(function () {
        //参数列表
        setCsArray();
        //数据流结果变量列表
        setJgblArray();
        // 规则表达式列表
        setGzbdsArray();
    })

    //参数列表
    function setCsArray() {
        var csArray = new Array();
        var order = 1;

        for (var i in bdcGzZgzDTO.bdcGzSjlDTOList) {
            var bdcGzSjlDTO = bdcGzZgzDTO.bdcGzSjlDTOList[i];
            for (var j in bdcGzSjlDTO.bdcGzSjlCsDOList) {
                var repeat = false;
                var bdcGzSjlCsDO = bdcGzSjlDTO.bdcGzSjlCsDOList[j];

                // 重复参数只显示一个
                for (var p in csArray) {
                    if (csArray[p].sjlcszl == getParamType(bdcGzSjlCsDO.sjlcszl) && csArray[p].sjlcsmc == bdcGzSjlCsDO.sjlcsmc) {
                        repeat = true;
                        break;
                    }
                }

                if (!repeat && ( 1 == bdcGzSjlCsDO.sjlcszl || 2 == bdcGzSjlCsDO.sjlcszl)) {
                    csArray.push({
                        "xh": order++,
                        "sjlcszl": getParamType(bdcGzSjlCsDO.sjlcszl),
                        "sjlcsmc": bdcGzSjlCsDO.sjlcsmc
                    });
                }
            }
        }

        table.render({
            elem: '#cslb',
            title: '参数信息表',
            cols: [[
                {field: 'xh', title: '序号', width: 100, align: 'center'},
                {field: 'sjlcszl', title: '类型', align: 'center'},
                {field: 'sjlcsmc', title: '名称', align: 'center'},
            ]],
            data: csArray,
            page: false,
            done: function () {
                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
            }
        });
    }

    function getParamType(typeKey) {
        var cstype = typeKey;
        if (typeKey == 1) {
            cstype = "常量";
        } else if (typeKey == 2) {
            cstype = "入参";
        } else if (typeKey == 3) {
            cstype = "动参";
        } else if (typeKey == 4) {
            cstype = "实体";
        }
        return cstype;
    }

    // 数据流结果变量列表
    function setJgblArray() {
        var jgblArray = new Array();
        var order = 1;
        for (var i in bdcGzZgzDTO.bdcGzSjlDTOList) {
            var bdcGzSjlDTO = bdcGzZgzDTO.bdcGzSjlDTOList[i];

            jgblArray.push({
                "xh": order++,
                "sjlmc": bdcGzSjlDTO.sjlmc,
                "jgblbs": bdcGzSjlDTO.jgblbs
            });
        }
        table.render({
            elem: '#jglb',
            title: '参数信息表',
            cols: [[
                {field: 'xh', title: '序号', width: 100, align: 'center'},
                {field: 'sjlmc', title: '数据流名称', align: 'center'},
                {field: 'jgblbs', title: '变量名称', align: 'center'},
            ]],
            data: jgblArray,
            page: false,
            done: function () {
                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
            }
        });
    }

    // 规则表达式列表
    function setGzbdsArray() {
        var gzbdsArray = new Array();
        var order = 1;
        if (bdcGzZgzDTO.bdcGzBdsTsxxDOList) {
            for (var i in bdcGzZgzDTO.bdcGzBdsTsxxDOList) {
                var bdsTsxxDO = bdcGzZgzDTO.bdcGzBdsTsxxDOList[i];
                gzbdsArray.push({
                    "xh": order++,
                    "gzbds": bdsTsxxDO.gzbds,
                    "tsxx": bdsTsxxDO.tsxx
                });
            }
        }


        table.render({
            elem: '#gzbds',
            toolbar: '#gzbdsBtn',
            defaultToolbar: [],
            title: '规则表达式',
            cols: [[
                {type: 'checkbox', fixed: 'left'},
                {field: 'xh', title: '序号', width: 60, align: 'center'},
                {field: 'gzbds', title: '规则表达式', align: 'center', edit: 'text'},
                {field: 'tsxx', title: '提示信息', align: 'center', edit: 'text'},
            ]],
            data: gzbdsArray,
            page: false,
            done: function () {
                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
            }
        });
    }

    //规则验证表头工具栏事件
    table.on('toolbar(gzbds)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'add':
                addGzbds();
                break;
            case 'del':
                deleteGzbds(checkStatus.data);
                break;
        }
        ;
    });

    // 新增规则表达式
    function addGzbds() {
        var bdcGzBdsTsxxDOList = bdcGzZgzDTO.bdcGzBdsTsxxDOList;
        if (bdcGzBdsTsxxDOList == null) {
            bdcGzBdsTsxxDOList = new Array();
        }

        var index = 1;
        for (var i in bdcGzBdsTsxxDOList) {
            bdcGzBdsTsxxDOList[i].xh = index++;
        }

        if (table.cache.gzbds && table.cache.gzbds.length > 0) {
            bdcGzBdsTsxxDOList.push({
                'xh': bdcGzBdsTsxxDOList.length + 1,
                'gzbds': '',
                'tsxx': ''
            });
        } else {
            bdcGzBdsTsxxDOList.push({'xh': 1, 'gzbds': '', 'tsxx': ''});
        }

        table.reload("gzbds", {data: bdcGzBdsTsxxDOList});
        bdcGzZgzDTO["bdcGzBdsTsxxDOList"] = bdcGzBdsTsxxDOList;
    }

    // 删除规则表达式
    function deleteGzbds(data) {
        if (!data || data.length == 0) {
            layer.alert("<div style='text-align: center'>请选择需要删除的记录！</div>", {title: '提示'});
            return;
        }

        var newTableData = [];
        var tableData = table.cache.gzbds;
        for (var index in tableData) {
            var isExist = false;
            for (var i in data) {
                if (tableData[index].xh == data[i].xh) {
                    isExist = true;
                    break;
                }
            }

            if (false == isExist) {
                newTableData.push(tableData[index]);
            }
        }

        // 重新索引序号
        var order = 1;
        for (var index in newTableData) {
            newTableData[index].xh = order++;
        }

        table.reload("gzbds", {data: newTableData});
        bdcGzZgzDTO["bdcGzBdsTsxxDOList"] = newTableData;
    }

    //编辑完成更新数据
    table.on('edit(gzbds)', function (obj) {
        bdcGzZgzDTO["bdcGzBdsTsxxDOList"] = table.cache.gzbds;
    });

    //子规则保存事件
    $("#submit-btn").click(function () {
        var gzmc = $("#gzmc").val();
        if (isNullOrEmpty(gzmc)) {
            layer.alert("<div style='text-align: center'>请输入子规则名称！</div>", {title: '提示'});
            return;
        }
        bdcGzZgzDTO["gzmc"] = gzmc;

        var yxj = $("#yxj").val();
        if (isNullOrEmpty(yxj) || !/^[0-9]*$/.test(yxj)) {
            layer.alert("<div style='text-align: center'>子规则优先级请输入数字！</div>", {title: '提示'});
            return;
        }

        // 验证规则表达式操作符配置是否正确
        if(!checkGzBds()) {
            return;
        }

        $("#checkflag").val(true);
        $('#childFrame').contents().find("#submitBtn").click();
        if ($("#checkflag").val() == "false"){
            return;
        }

        bdcGzZgzDTO["gzid"] = $("#gzid").val();
        bdcGzZgzDTO["yxj"] = yxj;
        bdcGzZgzDTO["pzr"] = $("#pzr").val();
        bdcGzZgzDTO["pzrq"] = $("#pzrq").val();
        bdcGzZgzDTO["ytsm"] = $("#ytsm").val();
        bdcGzZgzDTO["jydm"] = $("#jydm").val();
        bdcGzZgzDTO["pcqh"] = $("#pcqh").val();

        $.ajax({
            url: inquiryUiUrl + '/gdzgz/saveBdcGzGdZgz',
            type: "POST",
            data: JSON.stringify(bdcGzZgzDTO),
            contentType: 'application/json',
            dataType: "text",
            success: function (data) {
                if (!isNullOrEmpty(data)) {
                    layer.msg('<img src="../../../static/lib/bdcui/images/success-small.png" alt="">保存成功，请执行验证！');

                    $("#gzid").val(data);
                    saveEditLog();
                } else {
                    layer.msg('<img src="../../../static/lib/bdcui/images/error-small.png" alt="">保存失败，请检查配置内容');
                }
            },
            error: function (data) {
                layer.msg('<img src="../../../static/lib/bdcui/images/error-small.png" alt="">保存失败，请检查配置内容');
            }
        })
    });

    // 保存编辑日志
    function saveEditLog() {
        $.ajax({
            url: BASE_URL + "/zgz/queryBdcGzZgzDTOByGzid",
            type: 'GET',
            contentType: 'application/json',
            data: {"gzid": $("#gzid").val()},
            dataType: "json",
            success: function (data) {
                if (data) {
                    // 保存编辑前后子规则信息
                    var info = {"BJQZGZ": JSON.stringify(oldBdcZgzDTO), "BJHZGZ": JSON.stringify(data)};
                    saveDetailLog("ZGZBJ", "子规则配置-编辑", info);
                }
            }
        });
    }

    // 保存记录操作信息
    function saveDetailLog(logType, viewTypeName, data) {
        var json = JSON.parse(JSON.stringify(data));
        json.logType = logType;
        json.ipaddress = ipaddress;
        json.viewTypeName = viewTypeName;
        saveLogInfo(json);
    }

    //子规则校验事件
    $("#check-btn").click(function () {
        // 先验证配置是否全面
        checkConfig(false);

        // 验证规则表达式操作符配置是否正确
        if(!checkGzBds()) {
            return;
        }

        var zgz = bdcGzZgzDTO;
        zgz["gzmc"] = $("#gzmc").val();
        zgz["gzid"] = $("#gzid").val();
        zgz["yxj"] = $("#yxj").val();
        zgz["pzr"] = $("#pzr").val();
        zgz["pzrq"] = $("#pzrq").val();
        zgz["ytsm"] = $("#ytsm").val();
        zgz["jydm"] = $("#jydm").val();

        // 缓存规则数据
        layui.sessionData('zgzData', {
            key: 'data'
            , value: JSON.stringify(zgz)
        });

        // 再校验规则是否能正确运行
        layer.open({
            type: 2,
            title: '子规则验证',
            anim: -1,
            shadeClose: true,
            maxmin: true,
            shade: false,
            area: ['960px', '520px'],
            offset: 'auto',
            content: ["checkZgz.html", 'yes'],
            end: function () {
            }
        });
    });

    // 代码格式化
    $("#code-format").click(function () {
        var code = $("#jydm").val();
        formatCode(code, true);
    });

    function formatCode(code, isShowMsg) {
        if (isNullOrEmpty(code)) {
            warnMsg("未输入代码内容！");
            return;
        }

        $.ajax({
            url: inquiryUiUrl + '/zgz/code/format',
            data: JSON.stringify({"jydm": code}),
            type: "POST",
            contentType: 'application/json',
            dataType: "text",
            success: function (data) {
                $("#jydm").val(data);
                if (isShowMsg) {
                    successMsg("格式化成功！");
                }
            },
            error: function (e) {
                var text = JSON.parse(e.responseText);
                layer.alert("动态代码格式化失败，原因<br>" + text.detail);
            }
        });
    }

    /**
     * 提交失败提示
     * @param data
     */
    window.fail = function () {
        layer.alert("<div style='text-align: center'>未查询到子规则数据，请重试!</div>", {title: '提示'});
    }
});