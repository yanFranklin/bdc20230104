/**
 * author: <a href="mailto:wangwei5@gtmap.cn>wangwei</a>
 * version 1.0.0  2021/02/22
 * describe: 不动产登记中心需求流转单
 */
var $, form, layer, element, table, laytpl, laydate;
var processInsId = getQueryString("processInsId");
var formStateId = getQueryString("formStateId");
var readOnly = getQueryString("readOnly");
var taskId = getQueryString("taskId");
var xgztOpt = ['未修改', '已修改', '修改中'];
var qrztOpt = ['未确认','已确认','确认中'];
var sjclidLists = [];
var zdList;
var sjclids = new Set();

layui.use(['laydate', 'form', 'jquery', 'laytpl', 'layer', 'table'], function () {
        $ = layui.jquery;
        laydate = layui.laydate;
        form = layui.form;
        layer = layui.layer;
        table = layui.table;
        laytpl = layui.laytpl;

    $(function () {
        // 根据工作流实例id获取需求流转信息实体
        function loadXqxx() {
            $.ajax({
                url: getContextPath() + '/xqxx/getXqxx/' + processInsId,
                async: false,
                dataType: 'json',
                type: 'GET',
                success: function (data) {
                    if (isNotBlank(data)) {
                        var bdcSlXqxxDO = data.bdcSlXqxxDO;

                        // 渲染模板
                        var tpl = xqxxTpl.innerHTML
                            ,view = document.getElementById('xqxxView');
                        laytpl(tpl).render(data, function(html){
                            view.innerHTML = html;
                        });

                        // 渲染三个日期
                        laydate.render({
                            elem: '#raiseTime',
                            trigger: 'click',
                            format: 'yyyy-MM-dd HH:mm:ss',
                            type: 'datetime'
                        });
                        laydate.render({
                            elem: '#modifiedTime',
                            trigger: 'click',
                            value: bdcSlXqxxDO.xgwcsj,
                            format: 'yyyy-MM-dd HH:mm:ss',
                            type: 'datetime'
                        });
                        laydate.render({
                            elem: '#confirmTime',
                            trigger: 'click',
                            value: bdcSlXqxxDO.qrwcsj,
                            format: 'yyyy-MM-dd HH:mm:ss',
                            type: 'datetime'
                        });

                        renderDate(form, formStateId);
                        form.render();
                        loadZdData();
                        loadSjcl();
                        getStateById(readOnly, formStateId, 'xqxx');
                    }
                },
                error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr)
                }
            });
        }
        loadXqxx();

        // 页面验证
        var isSubmit = true;
        var verifyMsg = "";
        form.verify({
            required: function (value, item) {
                if (value == '' || value == null || value == undefined) {//判断是否为空
                    $(item).addClass('layui-form-danger');
                    isSubmit = false;
                    verifyMsg = "必填项不能为空";
                }
            }
        });

        // 保存
        form.on('submit(saveData)', function (data) {
            if (!isSubmit) {
                layer.msg(verifyMsg, {icon: 5, time: 3000});
                isSubmit = true;
                isFirst = true;
                verifyMsg = "";
                removeModal();
                return false;
            } else {
                addModel();
                setTimeout(function () {
                    try{
                        $.when(saveXqxx(data),saveSjcl()).done(function () {
                            ityzl_SHOW_SUCCESS_LAYER("保存成功");
                            removeModal();
                            loadXqxx();
                            loadSjcl();
                            form.render();
                        })
                    }catch (e) {
                        removeModal();
                        ityzl_SHOW_WARN_LAYER(e.message);
                    }
                } , 50);
            }

        });


        // 保存需求信息
        function saveXqxx(data) {
            var xqxxData = $('.xqxx').serializeArray();
            console.log(xqxxData);
            var xqxxObj = {};
            if (xqxxData.length > 0) {
                xqxxData.forEach(function (item, index) {
                    xqxxObj[item.name] = item.value;
                });
            }
            xqxxObj.gzlslid = processInsId;
            console.log(xqxxObj);

            $.ajax({
                url: getContextPath() + '/xqxx/saveBdcSlxqxx?gzlslid='+processInsId,
                type: "PATCH",
                data: JSON.stringify(xqxxObj),
                async: false,
                contentType: 'application/json',
                dataType: "json",
                success: function (data) {
                    console.log("需求信息保存成功");
                    form.render();
                    table.render();
                    laydate.render();
                },
                error: function (e) {
                    ityzl_SHOW_WARN_LAYER("保存失败");
                }
            });
        }

        //监听复选框选择
        //全选
        layui.form.on('checkbox(qxCheckbox)', function (data) {
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
        //单个的
        layui.form.on('checkbox(yxCheckbox)', function (data) {
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

    });

});

function loadSjcl() {
    getReturnData("/slym/sjcl/list/pl",{processInsId:processInsId},'GET',function (data) {
        generateSjcl(data,"");
    },function (error) {
        console.log(error);
    });
}

function loadZdData() {
    $.ajax({
        url: getContextPath() + "/bdczd",
        type: 'POST',
        dataType: 'json',
        async: false,
        success: function (data) {
            if (isNotBlank(data)) {
                zdList = data;
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

function generateSjcl(data,xmid) {
    sjclNumber = data.length;
    sjclidLists = [];
    if (data !== null && data.length>0){
        for (var i=0;i<data.length;i++){
            sjclidLists.push(data[i].sjclid);
        }
    }
    sessionStorage.setItem("sjclidLists",sjclidLists);
    var json = {
        xmid: xmid,
        bdcSlSjclDOList: data,
        zd: zdList
    };
    var tpl = sjclTpl.innerHTML,
        view = document.getElementById("sjcl");
    layui.laytpl(tpl).render(json, function(html){
        view.innerHTML = html;
    });
    layui.form.render();
    getStateById(readOnly,formStateId,'xqxx','sjcl');
    layui.form.render('select');
    disabledAddFa("sjclForm");
}

function printXqxx(dylx){
    var xmid  = $("input[name='xmid']").val();
    var param = {
        dylx: dylx,
        url : getIP() + "/realestate-accept-ui",
        zxlc: false,
        zdyXmlUrl: "shxx/" + dylx + "/xml/datas?gzlslid=" + processInsId+"&xmid=" + xmid,
        gzlslid: processInsId,
        xmid : xmid
    };
    getReturnData("/slPrint/newmode", param,"GET",function (data) {
        console.info(data);
        if(isNotBlank(data)){
            var dylxArr = [data.dylx];
            //2. 把当前打印类型放到session
            var sessionKey = data.dylx;
            setDypzSession(dylxArr,sessionKey);
            //3.调用打印方法
            printChoice(data.dylx, data.appName, data.dataurl, data.modelurl, data.hidemodel, sessionKey);
        }else{
            ityzl_SHOW_WARN_LAYER("未获取到打印参数。");
        }
    },function (xhr) {
        delAjaxErrorMsg(xhr);
    },false);
}