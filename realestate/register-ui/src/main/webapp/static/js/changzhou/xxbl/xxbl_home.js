var selectDataList = [];
var xmids = [];
var moduleCode = $.getUrlParam('moduleCode');
var reverseList = [];
reverseList.push('zl');
layui.use(['jquery', 'table', 'layer','form'], function () {
    var $ = layui.jquery,
        table = layui.table,
        form = layui.form,
        layer = layui.layer;

    $(function () {
        /**
         * 监听台账查询 input 点击 显示 x 清空 input 中的内容
         */
        $('.layui-form-item .layui-input-inline').on('click', '.layui-icon-close', function () {
            $(this).siblings('.layui-input').val('');
        });
        $('.layui-form-item .layui-input-inline .layui-input').on('focus', function () {
            $(this).siblings('.layui-icon-close').removeClass('bdc-hide');
        }).on('blur', function () {
            var $this = $(this);
            setTimeout(function () {
                $this.siblings('.layui-icon-close').addClass('bdc-hide');
            }, 150)
        });
        $('.bdc-content').css('min-height', $('body').height() - 56);

        //监听回车事件
        var isSearch = true;
        //判断回车操作
        $('.bdc-table-box').on('focus', '.layui-laypage-skip .layui-input', function () {
            isSearch = false;
        }).on('blur', '.layui-laypage-skip .layui-input', function () {
            isSearch = true;
        });
        $(document).keydown(function (event) {
            if (event.keyCode === 13 &&isSearch) { //绑定回车
                $("#search").click();
            }
        });

        /**
         * 渲染补录信息表格
         * @type {string}
         */
        var url = "/realestate-register-ui/rest/v1.0/blxx/page";
        var tableId = '#blTable';
        var toolbar = '#toolbar';
        renderblTable(url, tableId, toolbar);

        /**
         * 查询点击事件
         */
        $('#search').on('click', function () {
            cxfs=$('input:radio[name="cxfs"]:checked').val();
            // 选择方式 全模糊
            if(4 == cxfs){
                mhlx = {
                    "bdcdyhmh": 4, "bdcqzhmh": 4, "qlrmcmh": 4,"zlmh": 4
                };
            }

            // 选择方式 精确
            if(1 == cxfs){
                mhlx = {
                    "bdcdyhmh": 1, "bdcqzhmh": 1, "qlrmcmh": 1,"zlmh": 1
                };
            }


            form.val('searchform', JSON.parse(JSON.stringify(mhlx)));

            // 获取查询内容
            var obj = {};
            $(".search").each(function (i) {
                var value = $(this).val();
                var name = $(this).attr('name');
                // 此处去除查询条件中的空格
                obj[name] = deleteWhitespace(value);
            });
            // 查询时间长，添加遮罩
            addModel();
            // 重新请求
            table.reload("blTable", {
                url: url,
                where: obj,
                page: {
                    curr: 1  //重新从第 1 页开始
                },
                done: function (res) {
                    obj['response'] = res.data;
                    saveDetailLog("XXBL_CX", "信息补录-查询", obj);
                    // 判断当前登录用户角色
                    var user = queryCurrentUser();
                    if (user && 1 != user.admin) {
                        console.log(user);
                        $.each($(".bdc-delete-btn"), function (index, item) {
                            $(item).attr("style", "display:none");
                        });
                    }
                    setButtonAttrByModuleAuthority(moduleCode);
                    removeModel();
                    setTableHeight();
                    reverseTableCell(reverseList);
                    setElementAttrByModuleAuthority(moduleCode);
                }
            });
        });
        // 根据权限控制表格内部操作按钮展示
        function setButtonAttrByModuleAuthority(moduleCode){
            $.ajax({
                url: getContextPath() + '/rest/v1.0/form/moduleAuthority/' + moduleCode,
                type: 'GET',
                async: false,
                contentType: "application/json;charset=UTF-8",
                success: function (data) {
                    if (data && !isEmptyObject(data)) {
                        for(var i in data) {
                            if(data[i] === "hidden") {
                                $.each($("." + i), function (index, item) {
                                    $(item).attr("style", "display:none");
                                });
                            }
                        }
                    }
                }, error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr);
                }
            });
        }
        /**
         * 根据所选信息判断是否是产权
         * @param data
         */
        function isCq(data) {
            var qllx = data.qllx;
            // 产权的权利类型如下
            var cq = ["3", "4"];
            // 先判断是否是产权可以进行复制
            if (cq.indexOf(qllx) !== -1) {
                layer.msg("仅支持产权的复制！");

            } else if (!isNullOrEmpty(data)) {
                var yxmid = data[0].xmid;
                var ybdcdyh = data[0].bdcdyh;
                var qllx = data[0].qllx;
                var gzldymc = isNullOrEmpty(data[0].gzldymc) ? "": encodeURI(data[0].gzldymc);
                $.ajax({
                    url: getContextPath() + '/rest/v1.0/blxx/blxz/gzldyid?gzldymc=' + gzldymc,
                    type: 'GET',
                    success: function (gzldyid) {
                        // 打开不动产单元号的选择页面，通过 URL 参数和选择页面区分
                        var index = layer.open({
                            type: 2,
                            title: '选择不动产单元',
                            maxmin: true,
                            area: ['1150px', '600px'],
                            content: '../xxbl/xxbl_selectbdcdyh.html?yxmid=' + yxmid + '&gzldyid=' + gzldyid + '&ybdcdyh=' + ybdcdyh + '&qllx=' + qllx
                        });
                        layer.full(index);
                    }, error: function (xhr, status, error) {
                        removeModel();
                        delAjaxErrorMsg(xhr);
                    }
                });

            }
        }

        /**
         * 根据所选信息判断是否能够关联数据
         * 首次产权登记流程 和 预购商品房预告登记流程 不能关联数据
         */
        function isOpenGl(selectData) {
            $.ajax({
                url: "/realestate-register-ui/rest/v1.0/blxx/blxz/isgl?xmid=" + selectData[0].xmid,
                type: "GET",
                dataType: "json",
                success: function (data) {
                    if (data === true) {
                        var index = layer.open({
                            type: 2,
                            title: '关联产权',
                            maxmin: true,
                            area: ['1150px', '600px'],
                            content: '../xxbl/xxbl_glcq.html?gzlslid=' + selectData[0].gzlslid + "&xmid=" + selectData[0].xmid
                        });
                        layer.full(index);
                    } else {
                        warnMsg("不能关联");
                    }
                },
                error: function (xhr, status, error) {
                    removeModel();
                    delAjaxErrorMsg(xhr)
                }
            });
        }

        /**
         * 监听头工具栏事件
         */
        table.on('toolbar(blTable)', function (obj) {
            var layEvent = obj.event; //获得 lay-event 对应的值
            var checkStatus = table.checkStatus(obj.config.id);
            var selectData = checkStatus.data;
            var selectedNum = checkStatus.data.length;
            if (layEvent === "addBlxx") {
                window.open('./xxblSelectbdcdy.html');
            } else if (layEvent === "zxQl") {
                // 清空全局变量中的值
                selectDataList = [];
                // 判断是否选择了对应数据
                if (selectedNum != 1) {
                    layer.msg('请选择一条数据进行操作！');
                    return false;
                } else {
                    // 重新赋值
                    xmids = [];
                    var gzldymc;
                    // 遍历数据判断是否选择了相同的流程办理批量注销
                    selectData.forEach(function (v) {
                        if (isNullOrEmpty(gzldymc)) {
                            gzldymc = v.gzldymc;
                        } else {
                            if (gzldymc !== v.gzldymc) {
                                warnMsg("请选择相同的流程办理批量注销");
                                return;
                            }
                        }
                        xmids.push(v.xmid);
                    });
                    for (var i = 0; i < selectData.length; i++) {
                        var data = selectData[i];
                        var bdcGzYzsjDTO = {};
                        bdcGzYzsjDTO.bdcdyh = data.bdcdyh;
                        selectDataList.push(bdcGzYzsjDTO);
                    }
                    zxQl(checkQlxx);
                }
            } else if (layEvent === "copy") {
                // 判断是否选择了对应数据
                if (selectedNum == 1) {
                    isCq(checkStatus.data);
                } else {
                    layer.msg('请选择一条产权进行复制操作！');
                    return false;
                }
            } else if (layEvent === "connect"){
                if (selectedNum == 1) {
                    isOpenGl(selectData);
                } else {
                    layer.msg('请选择一条产权进行关联操作！');
                    return false;
                }

            } else if (layEvent === "hyQl"){
                // 清空全局变量中的值
                selectDataList = [];
                if (selectedNum != 1) {
                    layer.msg('请选择一条数据进行操作！');
                    return false;
                } else {
                    // 重新赋值
                    xmids = [];
                    var gzldymc;
                    // 遍历数据判断是否选择了相同的流程办理批量注销
                    selectData.forEach(function (v) {
                        if (isNullOrEmpty(gzldymc)) {
                            gzldymc = v.gzldymc;
                        } else {
                            if (gzldymc !== v.gzldymc) {
                                warnMsg("请选择相同的流程办理批量注销");
                                return;
                            }
                        }
                        xmids.push(v.xmid);
                    });
                    for (var i = 0; i < selectData.length; i++) {
                        var data = selectData[i];
                        var bdcGzYzsjDTO = {};
                        bdcGzYzsjDTO.bdcdyh = data.bdcdyh;
                        selectDataList.push(bdcGzYzsjDTO);
                    }
                    hyQl(hyCheckQlxx);
                }
            }
        });

        // 根据配置内容进行权限控制
        setElementAttrByModuleAuthority(moduleCode);
        //页面默认加载提示信息
        generate();

        function generate() {
            alertSize = 0;
            confirmSize = 0;
            layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
                var form = layui.form;
                var laytpl = layui.laytpl;
                var json = {};
                var tpl = tsxxTpl.innerHTML, view = document.getElementById("tsxx");
                //渲染数据
                laytpl(tpl).render(json, function (html) {
                    view.innerHTML = html;
                });
                form.render();
            })
        }

        /**
         * 判断是否存现限制权利
         * 调用规则验证
         */
        function checkQlxx(index) {
            layer.close(index);
            addModel();
            var bdcGzYzQO = {};
            bdcGzYzQO.zhbs = "ZXQLYZ";
            bdcGzYzQO.paramList = selectDataList;

            $.ajax({
                url: getContextPath() + '/rest/v1.0/blxx/bdcGzyz',
                type: 'POST',
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(bdcGzYzQO),
                success: function (data) {
                    if (data.length > 0) {
                        // 未通过验证，页面提示信息
                        removeModel();
                        loadTsxx(data);
                        gzyztsxx();
                    } else {
                        // 通过验证，组织数据注销权利
                        var postObj = {};
                        postObj.xmidList = xmids;
                        postObj.zxyy = $('#zxyy').val();
                        $.ajax({
                            type: 'POST',
                            url: "/realestate-register-ui/rest/v1.0/blxx/zxql",
                            contentType: "application/json;charset=utf-8",
                            data: JSON.stringify(postObj),
                            dataType: "text",
                            success: function (data) {
                                removeModel();
                                if (data === "history") {
                                    warnMsg("当前项目已经被注销，不可以重复注销！");
                                } else {
                                    successMsg("注销成功");
                                }
                            },
                            error: function (xhr, status, error) {
                                delAjaxErrorMsg(xhr)
                            }
                        });
                    }
                }, error: function (xhr, status, error) {
                    removeModel();
                    delAjaxErrorMsg(xhr);
                }
            });
        }

        /**
         * 添加补录审核信息
         * @param data 选中的数据
         */
        function addBlShxx(data) {
            layer.open({
                title: '审核类型',
                type: 1,
                area: ['430px'],
                btn: ['确认', '取消'],
                content: $('#bdc-popup-radio')
                , yes: function (index, layero) {
                    addModel();
                    layer.close(index);
                    var blshlx = $('input[name="blshlx"]:checked').val();
                    //提交 的回调
                    var blShDO = {};
                    // 修改数据补录类型为 1 修改
                    blShDO.bllx = 1;
                    blShDO.blshlx = blshlx;
                    blShDO.gzlslid= data.gzlslid;
                    blShDO.xmid = data.xmid;
                    $.ajax({
                        url: getContextPath() + '/rest/v1.0/blxx/blsh',
                        type: 'POST',
                        dataType: 'json',
                        contentType: "application/json;charset=UTF-8",
                        data: JSON.stringify(blShDO),
                        success: function () {
                            removeModel();
                            saveDetailLog("XXBL_CK", "信息补录-查看", data);
                            window.open("./xxblUpdateinfo.html?processInsId=" + data.gzlslid + "&type=update" + "&xmid=" + data.xmid);
                        }, error: function (xhr, status, error) {
                            removeModel();
                            delAjaxErrorMsg(xhr);
                        }
                    });
                }
            });
        }

        /**
         * 判断是否能够修改
         *
         * @param data
         */
        function checkEdit(data) {
            //提交 的回调
            var blShDO = {};
            blShDO.gzlslid= data.gzlslid;
            blShDO.xmid = data.xmid;
            addModel();
            $.ajax({
                url: getContextPath() + '/rest/v1.0/blxx/blsh/issh',
                type: 'POST',
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(blShDO),
                success: function (issh) {
                    removeModel();
                    if (issh == '0') {
                        warnMsg("此数据正在审核中请勿修改！")
                    } else if (issh == '1') {
                        addBlShxx(data);
                    } else if (issh == '2') {
                        saveDetailLog("XXBL_CK", "信息补录-查看", data);
                        window.open("/realestate-register-ui/view/xxbl/xxblUpdateinfo.html?processInsId=" + data.gzlslid +"&xmid="+ data.xmid + "&type=update");
                    } else if (issh == '3') {
                        warnMsg("此数据正在被其他人员修改！")
                    }
                }, error: function (xhr, status, error) {
                    removeModel();
                    delAjaxErrorMsg(xhr);
                }
            });
        }

        /**
         * 监听工具栏事件，工具栏进入到编辑页面可以对于数据进行操作
         */
        table.on('tool(blTable)', function (obj) {
            var layEvent = obj.event; //获得 lay-event 对应的值
            var data = obj.data; //获得当前行数据
            // 确认 gzlslid 是有值的
            if (isNullOrEmpty(obj.data.gzlslid)) {
                layer.msg("数据异常，未获取到工作流实例 id");
            } else {
                if (layEvent === 'del') { //删除
                    var deleteIndex = layer.open({
                        type: 1,
                        skin: 'bdc-small-tips',
                        title: '确认删除',
                        area: ['320px'],
                        content: '是否确认删除？',
                        btn: ['确定', '取消'],
                        btnAlign: 'c',
                        yes: function () {
                            layer.close(deleteIndex);
                            //确定操作
                            addModel();
                            saveDetailLog("XXBL_SC", "信息补录-删除", data);
                            $.ajax({
                                url: "/realestate-register-ui/rest/v1.0/blxx?xmid=" + data.xmid + "&all=false",
                                type: 'DELETE',
                                datetype: 'json',
                                success: function (result) {
                                    removeModel();
                                    if (result === false) {
                                        var reSure = layer.open({
                                            type: 1,
                                            skin: 'bdc-small-tips',
                                            title: '确认',
                                            area: ['320px'],
                                            content: '此项目属于批量流程，删除将会删除全部信息，是否确认删除？',
                                            btn: ['确定', '取消'],
                                            yes: function () {
                                                layer.close(reSure);
                                                addModel();
                                                $.ajax({
                                                    url: "/realestate-register-ui/rest/v1.0/blxx?xmid=" + data.xmid + "&all=true",
                                                    type: 'DELETE',
                                                    datetype: 'json',
                                                    success: function (deleteRsult) {
                                                        removeModel();
                                                        if (deleteRsult === true) {
                                                            obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                                                            layer.msg("删除成功");
                                                        }
                                                    },
                                                    error: function (xhr, status, error) {
                                                        delAjaxErrorMsg(xhr);
                                                    }
                                                });
                                            },
                                            btn2: function () {
                                                //取消
                                                layer.close(deleteIndex);
                                            }
                                        });
                                    } else {
                                        obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                                        layer.msg("删除成功");
                                    }
                                },
                                error: function (xhr, status, error) {
                                    delAjaxErrorMsg(xhr);
                                }
                            });
                        },
                        btn2: function () {
                            //取消
                            layer.close(deleteIndex);
                        }
                    });
                } else if (layEvent === 'edit') { //编辑
                    checkEdit(obj.data);
                } else if (layEvent === 'back') { //退回
                    window.open("./xxblBack.html?xmid=" + obj.data.xmid);
                } else if (layEvent === 'check') {
                    saveDetailLog("XXBL_CK", "信息补录-查看", data);
                    window.open("./xxblUpdateinfo.html?processInsId=" + obj.data.gzlslid + "&xmid=" + obj.data.xmid + "&type=check");
                } else if(layEvent === 'clbl'){
                    clbl(obj.data);
                }
            }
        });
    });

    /**
     * 渲染补录数据
     * @param url 地址
     * @param tableId 表格 id
     * @param toolbar 表格工具栏
     */
    function renderblTable(url, tableId, toolbar) {
        addModel();
        table.render({
            elem: tableId,
            // url: url,
            data: [],
            toolbar: toolbar,
            title: '补录任务表格',
            method: 'GET',
            even: true,
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            defaultToolbar: ['filter'],
            limits: commonLimits,
            cols: [[
                {type: 'checkbox', width: 50, fixed: 'left'},
                {field: 'bdcqzh', templet: "#bdcqzhTpl", minWidth: 280, title: '不动产权证号', event: 'check'},
                {field: 'ycqzh', minWidth: 100, title: '原产权证号', event: 'check'},
                {field: 'qlrmc', minWidth: 100, title: '权利人'},
                {field: 'gzldymc', minWidth: 100, title: '流程名称'},
                {field: 'zl', minWidth: 180, title: '坐落'},
                {field: 'bdcdyh', templet: '#bdcdyhTpl', minWidth: 270, title: '不动产单元号'},
                {field: 'blsj', title: '补录时间', width: 200, sort: true},
                {field: 'qszt', title: '权属状态', templet: '#qsztTemplate', minWidth: 90, hide: true},
                {field: 'gzlslid', title: '流程ID', width: 90, hide: true},
                {field: 'qszt', title: '限制状态', templet: '#xzqlZtTemplate', minWidth: 90},
                {fixed: 'right', title: '操作', toolbar: '#editbar', width: 240}
            ]],
            parseData: function (res) { //res 即为原始返回的数据
                res.content.forEach(function (v) {
                    if (v.slsj) {
                        var blsj = new Date(v.slsj);
                        v.blsj = format(blsj);
                    }
                });
                return {
                    code: res.code, //解析接口状态
                    msg: res.msg, //解析提示文本
                    count: res.totalElements, //解析数据长度
                    data: res.content //解析数据列表
                }
            },
            page: true,
            done: function () {
                removeModel();
                setTableHeight();
                reverseTableCell(reverseList);
            }
        });
    }


    /**
     * 还原权利验证
     * 调用规则验证，HYQLYZ
     */
    function hyCheckQlxx(index) {
        layer.close(index);
        addModel();
        var bdcGzYzQO = {};
        bdcGzYzQO.zhbs = "HYQLYZ";
        bdcGzYzQO.paramMap = selectDataList[0];
        $.ajax({
            url: getContextPath() + '/rest/v1.0/blxx/qtyz',
            type: 'POST',
            dataType: 'json',
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(bdcGzYzQO),
            success: function (data) {
                if (data.length > 0) {
                    // 未通过验证，页面提示信息
                    removeModel();
                    loadTsxx(data);
                    gzyztsxx();
                } else {
                    // 通过验证，组织数据注销权利
                    var postObj = {};
                    postObj.xmidList = xmids;
                    postObj.hfyy = $('#hfyy').val();
                    $.ajax({
                        type: 'POST',
                        url: "/realestate-register-ui/rest/v1.0/blxx/hyql",
                        contentType: "application/json;charset=utf-8",
                        data: JSON.stringify(postObj),
                        dataType: "text",
                        success: function (data) {
                            removeModel();
                            if (data === "valid") {
                                warnMsg("当前项目已经被还原，不可以重复还原！");
                            } else {
                                successMsg("还原成功");
                            }
                        },
                        error: function (xhr, status, error) {
                            delAjaxErrorMsg(xhr)
                        }
                    });
                }
            }, error: function (xhr, status, error) {
                removeModel();
                delAjaxErrorMsg(xhr);
            }
        });
    }



    /**
     * 还原权利
     */
    function hfQl(xmid) {
        addModel();
        $.ajax({
            url: getContextPath() + '/rest/v1.0/blxx/blxz/qszt/hfyz?xmid=' + xmid,
            type: 'GET',
            success: function (data) {
                if (data === "success") {
                    // 注销原因清空
                    $('#hfyy').val("");
                    layer.open({
                        title: '恢复权利',
                        type: 1,
                        area: ['430px'],
                        btn: ['恢复', '取消'],
                        content: $('#hfql-reason'),
                        yes: function (index, layero) {
                            var postObj = {};
                            postObj.xmid = xmid;
                            postObj.hfyy = $('#hfyy').val();
                            $.ajax({
                                type: 'POST',
                                url: getContextPath() + "/rest/v1.0/blxx/blxz/qszt/hfql",
                                contentType: "application/json;charset=utf-8",
                                data: JSON.stringify(postObj),
                                dataType: "text",
                                success: function (msg) {
                                    layer.close(index);
                                    removeModel();
                                    if (msg === "success") {
                                        successMsg("产权恢复成功！");
                                    } else {
                                        warnMsg(msg);
                                    }
                                },
                                error: function (xhr, status, error) {
                                    layer.close(index);
                                    removeModel();
                                    delAjaxErrorMsg(xhr)
                                }
                            });
                        },
                        btn2: function (index, layero) {
                            //取消 的回调
                            removeModel();
                        },
                        cancel: function () {
                            removeModel();
                            //右上角关闭回调
                            //return false 开启该代码可禁止点击该按钮关闭
                        }
                    });
                } else {
                    removeModel();
                    warnMsg(data);
                }
            }, error: function (xhr, status, error) {
                removeModel();
                delAjaxErrorMsg(xhr);
            }
        });
    }

});

//关闭 panel
function cancelEdit() {
    layui.use(['layer'], function () {
        var layer = layui.layer;
        layer.closeAll();
    });
}

function clbl(data){
    scwjj(data.gzlslid,"补录材料");
}


