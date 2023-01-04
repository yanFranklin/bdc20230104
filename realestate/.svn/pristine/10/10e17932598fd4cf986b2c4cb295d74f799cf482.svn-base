/**
 * 归档信息台账
 */
layui.use(['table', 'laytpl', 'laydate', 'layer', 'form'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer,
        form = layui.form;
    var BASE_URL = '/realestate-register-ui/rest/v1.0';
    var zdList = {a:[]};
    // 选中的数据集合
    var selectedDataArr = [];
    // 选中的数据对象的ID集合
    var selectedIdArr = [];
    // 选中的数据对象的ID临时集合
    var idArrNewTemp = [];
    // 选中的数据对象的ID临时集合
    var dataArrNewTemp = [];

    // 设置字符过多，省略样式
    var reverseList = ['zl'];
    /**
     * 加载Table数据列表
     */
    table.render({
        elem: '#gdxxTable',
        id: 'gdxxTable',
        toolbar: '#toolbar',
        title: '归档信息',
        defaultToolbar: [],
        // url: BASE_URL + "/gdxx/page",默认不展示数据
        data: [],
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        cellMinWidth: 80,
        even: true,
        limits: commonLimits,
        cols: [[
            {type: 'checkbox', fixed: 'left'},
            {field: 'xh', title: '序号', width: 50, type: 'numbers'},
            {field: 'xmid', title: 'xmid', hide: true},
            {field: 'gdxxid', title: 'gdxxid', hide: true},
            {field: 'gzlslid', title: 'gzlslid', hide: true},
            {field: 'slbh', title: '受理编号', minWidth: 100},
            {field: 'bdcdyh', title: '不动产单元号', minWidth: 120},
            {field: 'bdcqzh', title: '不动产权证号', minWidth: 120},
            {field: 'zl', title: '坐落', minWidth: 50},
            {field: 'gdrxm', title: '归档人', minWidth: 60},
            {
                field: 'gdsj', title: '归档时间',
                templet: function (d) {
                    return format(d.gdsj);
                },
                minWidth: 100
            },
            {
                field: 'gdzt', title: '归档状态',
                templet: function (d) {
                    if(isNullOrEmpty(d.gdxxid)){
                        return "未归档";
                    }
                    if(!isNullOrEmpty(d.gdxxid) && isNullOrEmpty(d.daid)){
                        return "归档失败";
                    }
                    if(!isNullOrEmpty(d.gdxxid) && !isNullOrEmpty(d.daid)){
                        return "归档成功";
                    }
                },
                minWidth: 100
            },
            {
                field: 'yjzt', title: '移交状态',
                templet: function (d) {
                    return isNullOrEmpty(d.yjdid) ? "未移交" : "已移交";
                }
            }
        ]],
        text: {
            none: '未查询到数据'
        },
        autoSort: false,
        page: true,
        parseData: function (res) {
            return {
                code: res.code, //解析接口状态
                msg: res.msg, //解析提示文本
                count: res.totalElements, //解析数据长度
                data: res.content //解析数据列表
            }
        },
        done: function () {
            var searchHeight = 131;
            setTableHeight(searchHeight);

            reverseTableCell(reverseList);
            if (!isEmptyObject($("#smqsr").val())) {
                $("#smqsr").focus();
                var smqsr = $("#smqsr").val();
                $("#smqsr").val("");
                $("#smqsr").val(smqsr);
            }
        }
    });
    $('.bdc-table-box').on('mouseenter', 'td', function () {
        if ($(this).text() && reverseList.indexOf($(this).attr("data-field")) != -1) {
            $(this).append('<div class="layui-table-grid-down"><i class="layui-icon layui-icon-down"></i></div>');
        }
        $('.layui-table-grid-down').on('click', function () {
            setTimeout(function () {
                $('.layui-table-tips-main .bdc-table-date').html(reverseString($('.layui-table-tips-main .bdc-table-date').html()));
            }, 20);
        });
    });
    if (!(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion.match(/9./i) == "9.")) {
        //监听input点击
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
    }

    //主页面头工具栏事件
    table.on('toolbar(gdxxTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'gd':
                gd(checkStatus.data);
                break;
            case 'addToDataArr':
                addToDataArr();
                break;
            case 'viewData':
                viewData();
                break;
            case 'resetData':
                resetData();
                break;
            case 'dagsdBtn':
                updateDagsd();
                break;
            case 'smsMsgBtn':
                sendSmsMsg();
        }
    });
    // 弹框页面头工具栏事件
    table.on('toolbar(viewFilter)', function (obj) {
        // var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'generateYjd':
                generateYjd(selectedIdArr);
                break;
            case 'printYjd':
                var yjdid = $("#yjdid").val();
                var yjdbh = $("#yjdbh").html();
                printYjd(yjdid, yjdbh);
                break;
        }
    });

    //监听弹框页面的行工具事件
    table.on('tool(viewFilter)', function (obj) {
        if (obj.event === 'del') {
            var submitIndex = layer.open({
                type: 1,
                skin: 'bdc-small-tips',
                title: '确认删除',
                area: ['320px'],
                content: '您确定要删除吗？',
                btn: ['确定', '取消'],
                btnAlign: 'c',
                yes: function () {
                    //确定操作
                    obj.del();
                    dataArrDel(obj.data);
                    layer.close(submitIndex);
                },
                btn2: function () {
                    //取消
                }
            });
        }
    });

    /**
     * 行双击编辑
     */
    table.on('rowDouble(gdxxTable)', function (obj) {
        var array = [];
        array.push(obj.data);
    });

    /**
     * 监听排序事件
     */
    table.on('sort(gdxxTable)', function (obj) {
        table.reload('gdxxTable', {
            initSort: obj
            , where: {
                field: obj.field //排序字段
                , order: obj.type //排序方式
            }
        });

    });

    document.onkeydown = function (event) {
        var code = event.keyCode;
        if (code == 13) {
            $('#search').click();
        }
    }
    /**
     * 点击查询
     */
    $('#search').on('click', function () {

        // 获取查询内容
        var obj = {};
        $(".search").each(function (i) {
            var value = $(this).val();
            if (!isNullOrEmpty(value)) {
                var name = $(this).attr('name');
                obj[name] = value;
            }
        });
        if ($("#smqsr").val() == "" && $("#slbh").val() == "") {
            layer.alert("请输入受理编号！", {
                title: '提示'
            });
            return false;
        }
        // if (isEmptyObject(obj)) {
        //     layer.alert("请输入查询条件！", {
        //         title: '提示'
        //     });
        //     return false;
        // }
        var smqsr = $("#smqsr").val();
        if (!isEmptyObject(smqsr) && smqsr.length !== (smqsr.lastIndexOf(",") + 1)) {
            smqsr += ",";
        }
        $("#smqsr").val(smqsr);
        addModel();
        // 重新请求
        table.reload("gdxxTable", {
            url: BASE_URL + "/gdxx/page"
            , where: obj
            , page: {
                curr: 1  //重新从第 1 页开始
            },
            done: function (res, curr, count) {
                removeModel();
                var searchHeight = 131;
                setTableHeight(searchHeight);

                if (selectedDataArr.length > 0) {
                    $("#dataNum").html('（' + selectedDataArr.length + '）');
                }

                reverseTableCell(reverseList);
                if (!isEmptyObject($("#smqsr").val())) {
                    $("#smqsr").focus();
                    var smqsr = $("#smqsr").val();
                    $("#smqsr").val("");
                    $("#smqsr").val(smqsr);
                }
            }
        });
        return false;
    });

    function pageInit(data){
        var xmid = data[0].xmid;
        var arr = {xmid:xmid};
        $.ajax({
            url: BASE_URL + '/gdxx/getDagsd',
            type:'POST',
            data:JSON.stringify(arr),
            contentType: 'application/json',
            success: function (res) {
                zdList = getZdList();
                $.each(zdList.qx,function (index,item) {
                    if (res==item.DM){
                        $("#dagsd").append("<option value='"+item.DM+"' selected>"+item.MC+"</option>");
                    }else {
                        $("#dagsd").append("<option value='"+item.DM+"'>"+item.MC+"</option>");
                    }

                })
                layui.form.render("select");
            }
        })

    }

    function updateDagsd(){
        var checkStatus = table.checkStatus('gdxxTable');
        var data = checkStatus.data;
        if (data.length == 0) {
            warnMsg("请选择数据！");
            return false;
        }
        var xmidArr = new Array();
        pageInit(data);
        form.render();
        layer.open({
            title:'档案归属地',
            type: 1,
            // skin: 'bdc-spf-layer',
            area: ['400px','460px'],
            btn: ['确定', '取消'],
            content:$("#bdc-popup-large"),
            yes:function (index, layero) {
                for (var i=0;i<data.length;i++){
                    var dagsd = $("#dagsd option:selected").val();
                    data[i].dagsd = dagsd;
                    xmidArr.push(data[i]);
                }
                $.ajax({
                    url:BASE_URL + '/gdxx/updateDagsd',
                    type:'POST',
                    data:JSON.stringify(xmidArr),
                    contentType: 'application/json',
                    success:function (res) {
                        successMsg("更新成功");
                        layer.closeAll();
                    },
                    error: function (res) {
                        warnMsg("更新失败");
                        xmidArr = new Array();
                    }
                })
            }
        })

    }

    /**
     * 发送短信
     * @author <a href="mailto:huanghui@gtmap.cn">huanghui</a>
     * @returns {boolean}
     */
    function sendSmsMsg(){
        var checkStatus = table.checkStatus('gdxxTable');
        var data = checkStatus.data;
        if (data.length == 0) {
            warnMsg("请选择数据！");
            return false;
        }
        var gzlslidArr = new Array();
        $.each(data, function (key, value) {
            gzlslidArr.push(value.gzlslid);
        })
        if (gzlslidArr.length > 0){
            addModel();
            //发送短信
            $.ajax({
                type: "POST",
                url: BASE_URL + "/gdxx/gdxxSendMsg",
                data: JSON.stringify(gzlslidArr),
                contentType: 'application/json',
                dataType: "json",
                success: function (data) {
                    removeModal();
                    successMsg("发送成功");
                }, error: function (e) {
                    removeModal();
                    successMsg("发送失败");
                    response.fail(e, '');
                }
            });
        }
    }

    /**
     * 重新加载数据
     */
    window.reload = function () {
        table.reload("gdxxTable", {});
    };

    /**
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 归档
     */
    function gd(data) {
        var errorSlbh = [];
        $.each(data,function(index, val){
            if(isNotBlank(val.gdxxid) && isNotBlank(val.daid)){
                errorSlbh.push(val.slbh);
            }
        });
        if(errorSlbh.length > 0){
            layer.confirm("请勿重复归档，已归档的受理编号：" + errorSlbh.join(","), {btn: '确认', title: '信息', area: '650px'}, function (index) {
                layer.close(index);
            });
            return false;
        }
        $.ajax({
            url: BASE_URL + '/gdxx/gd',
            type: "POST",
            dataType: "json",
            data: JSON.stringify(data),
            contentType: 'application/json',
            success: function (res) {
                var msg = "";
                if(isNotBlank(res.success)){
                    msg += '<img src="../../static/lib/bdcui/images/success-small.png" alt="">' + res.success + "<br/>";
                }
                if(isNotBlank(res.error)){
                    msg += '<img src="../../static/lib/bdcui/images/warn-small.png" alt="">' + res.error;
                }
                layer.confirm(msg, {btn: '确认', title: '信息', area: '650px'}, function (index) {
                    layer.close(index);
                    reload();
                });
            }, error: function (e) {
                var errorMsg = '<img src="../../static/lib/bdcui/images/warn-small.png" alt="">' + "归档失败:" + JSON.parse(e.responseText).msg;
                layer.confirm(errorMsg, {btn: '确认', title: '信息', area: '650px'}, function (index) {
                    layer.close(index);
                    reload();
                });
            }
        });
    }

    /**
     * 添加到已选数据
     * @param data
     */
    function addToDataArr() {
        var checkStatus = table.checkStatus('gdxxTable');
        var data = checkStatus.data;
        if (data.length == 0) {
            warnMsg("请选择数据！");
            return false;
        }

        var yjdid = $("#yjdid").val().trim();
        var yjdbh = $("#yjdbh").html().trim();
        if (yjdid && !isNullOrEmpty(yjdid)) {
            // 判断当前是否已有移交单编号生成，给出提示
            layer.confirm("已经生成移交单信息，是否继续添加？如果想要重新生成移交单，请选择重置。", {
                title: "提示",
                btn: ["继续", "重置"],
                btn1: function (index) {
                    // 继续执行程序
                    layer.close(index);
                    checkAndContinueAddData(data, yjdid, yjdbh);
                },
                btn2: function (index) {
                    layer.close(index);
                    // 重置移交单信息，生成新的移交清单
                    clearData();
                    clearYjdbh();
                    continueAddData(data);
                }
            });
        } else {
            continueAddData(data);
        }
    }

    /**
     *
     * @param data 新选择的数据
     */
    function checkAndContinueAddData(data, yjdid, yjdbh) {
        // 1.先和当前已有的数据比较去重
        continueAddData(data, yjdid);
        // 2.检查数据库中是否已移交，并做保存处理
        if (idArrNewTemp.length > 0) {
            var addNew = checkXmYjdExisted(idArrNewTemp, yjdid, yjdbh);
            if (addNew > 0) {
                selectedIdArr = selectedIdArr.concat(idArrNewTemp);
                selectedDataArr = selectedDataArr.concat(dataArrNewTemp);
                $("#dataNum").html('（' + selectedDataArr.length + '）');
            }
        }
        // 清空临时数组信息
        idArrNewTemp = [];
        dataArrNewTemp = [];
    }

    /**
     * 继续添加数据信息
     * @param data 选择的数据信息
     * @returns {boolean}
     */
    function continueAddData(data, yjdid) {
        // console.log(data);
        var flag = false;
        $.each(data, function (key, val) {
            if (!data[key].yjdid) {
                if (selectedIdArr.length == 0 || (selectedIdArr.length > 0 && $.inArray(val.xmid, selectedIdArr) == -1)) {
                    if (yjdid && !isNullOrEmpty(yjdid)) {
                        // 增加临时信息
                        idArrNewTemp.push(val.xmid);
                        dataArrNewTemp.push(val);
                    } else {
                        selectedIdArr.push(val.xmid);
                        selectedDataArr.push(val);
                    }
                }
            } else {
                flag = true;
            }
        });
        if (flag) {
            warnMsg("勾选数据包含已移交的数据，请检查！");
            return false;
        }
        $("#dataNum").html('（' + selectedDataArr.length + '）');
        // console.log(dataArr);
        // console.log(idArr);
    }

    /**
     * 清空已选信息，或者重置移交单编号信息
     */
    function resetData() {
        var yjdid = $("#yjdid").val().trim();
        if (yjdid && !isNullOrEmpty(yjdid)) {
            // 判断当前是否已有移交单编号生成，给出提示
            layer.confirm("已生成移交单编号，是否要生成一份新的移交单？", {
                title: "提示",
                btn: ["重新生成", "取消"],
                btn1: function (index) {
                    layer.close(index);
                    // 重置移交单信息，生成新的移交清单
                    clearData();
                    clearYjdbh();
                },
                btn2: function (index) {
                    layer.close(index);
                }
            });
        } else {
            clearData();
        }
    }

    /**
     * 清空数据信息
     */
    function clearData() {
        selectedDataArr = [];
        selectedIdArr = [];
        $("#dataNum").html('（' + selectedDataArr.length + '）');
    }

    /**
     * 清空移交单编号信息
     */
    function clearYjdbh() {
        resetYjdBtn();
        $("#yjdid").val("");
        $("#yjdbh").html("");
        $("#dataNum").html('');
    }


    /**
     * 已选数据查看
     */
    function viewData() {
        if (selectedDataArr.length == 0 && isNullOrEmpty($("#yjdid").val())) {
            warnMsg("请先添加数据！");
            return false;
        }
        layer.open({
            type: 1,
            title: "已添加信息",
            content: $('#popup'),
            area: ['960px', '500px'],
            cancel: function () {
                $("#popup").css('display', 'none');
            },
            success: function (layero, index) {
                viewTableRender();
                var yjdbh = $("#yjdbh").html();
                if (yjdbh != null && yjdbh != '') {
                    generateYjdBtnDisabled();
                }
            }
        });
    }

    /**
     * 已选择窗口数据渲染
     */
    function viewTableRender() {
        table.render({
            elem: '#viewTable',
            toolbar: '#toolbarDemo',
            id: 'test1',
            title: '档案移交清单',
            defaultToolbar: [],
            cols: [[
                {type: 'numbers', fixed: 'left'},
                {field: 'xmid', title: 'xmid', hide: true},
                {field: 'gdxxid', title: 'gdxxid', hide: true},
                {field: 'gzlslid', title: 'gzlslid', hide: true},
                {field: 'slbh', title: '受理编号', minWidth: 100},
                {field: 'bdcdyh', title: '不动产单元号', minWidth: 120},
                {field: 'bdcqzh', title: '不动产权证号', minWidth: 120},
                {field: 'zl', title: '坐落', minWidth: 50},
                {
                    field: 'zslx', title: '证书类型', minWidth: 110,
                    templet: function (d) {
                        return getXmZslx(d.xmid);
                    }
                },
                {
                    field: 'gdzt', title: '归档状态', minWidth: 100,
                    templet: function (d) {
                        return isNullOrEmpty(d.gdxxid) ? "未归档" : "已归档";
                    }
                },
                {fixed: 'right', title: '操作', toolbar: '#barDemo', width: 80}
            ]],
            data: selectedDataArr,
            page: true,
            done: function () {
                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
            }
        });
    }

    /**
     * 根据已知的值删除数组元素
     * @param data
     */
    function dataArrDel(data) {
        $.each(selectedIdArr, function (key, val) {
            if (selectedIdArr[key] == data.xmid) {
                selectedIdArr.splice(key, 1);
                selectedDataArr.splice(key, 1);
                $("#dataNum").html('（' + selectedDataArr.length + '）');
                // 删除成功，重新渲染表格
                viewTableRender();
                return false;
            }
        });
    }
});