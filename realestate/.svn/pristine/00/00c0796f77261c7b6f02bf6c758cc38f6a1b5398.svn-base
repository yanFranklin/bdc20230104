var form,laytpl;
//收件材料类型列表,默认值
var zdList = {"sjlx":[]};
//收件材料个数
var sjclNumber = 0;
//附件相关上传按钮需要
var xmid = '';
//登记原因需要
var djxl;
//收件材料全选存储数据
var sjclids = new Set();

var formStateId = getQueryString("formStateId");
var readOnly = getQueryString("readOnly");
var processInsId = getQueryString("processInsId");
var zxlc ="";

layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    var table = layui.table;
    laytpl = layui.laytpl;
    form = layui.form;

    $(function () {
        addModel()
        var jbxxid ="";

        //加载表单基本信息
        loadSmxx();

        //获取收件材料类型list
        getReturnData("/bdczd", '', 'POST', function (data) {
            if (isNotBlank(data)) {
                zdList = data;
            }
        }, function (err) {
            console.log(err);
        }, false);
        //加载权籍调查模块
        loadDcxx();
        //加载收件材料
        loadSjcl();

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
        });

        //点击提交时不为空的全部标红
        var isSubmit = true;
        var isFirst = true;
        //验证提示信息
        var verifyMsg = "必填项不能为空";
        form.verify({
            required: function (value, item) {
                if (value == '' && !isXndyh) {
                    if (isFirst) {
                        $('.layui-tab-item').removeClass('layui-show');
                        $(item).parents('.layui-tab-item').addClass('layui-show');
                        var liIndex = $(item).parents('.layui-tab-item').index() + 1;
                        $('.layui-tab-title li').removeClass('layui-this');
                        $('.layui-tab-title li:nth-child(' + liIndex + ')').addClass('layui-this');
                        isFirst = false;
                    }
                    $(item).addClass('layui-form-danger');
                    isSubmit = false;
                }
            }
        });
        //监听保存
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
                        $.when(saveXmxx(), saveSjcl(), saveQjdcxx()).done(function () {
                            removeModal();
                            ityzl_SHOW_SUCCESS_LAYER("保存成功");
                        })
                    } catch (e) {
                        removeModal();
                        if (e.message) {
                            showAlertDialog(e.message);
                        } else {
                            delAjaxErrorMsg(e);

                        }
                        return;
                    }
                }, 10);
                return false;
            }

        });

        //打印收件单
        $("#printSjd").click(function () {
            printData('qjdcsjd');
        });


    });
});

//获取表单基本信息并渲染到页面
function loadSmxx(){
    getReturnData("/slym/xm/queryBdcSlJbxxByGzlslid",{"processInsId":processInsId}, "GET", function (data) {
        jbxxid =data.jbxxid;
        //console.log(data);
        //时间只保留年月日
        if(data.slsj != null) {
            data.slsj = data.slsj.substring(0, 10);
        }
        //获取xmid、djxl
        getReturnData("/gwc/list/bdcslxm", {jbxxid: jbxxid}, 'GET', function (data) {
            if (isNotBlank(data) &&data.length >0) {
                xmid = data[0].xmid;
                djxl = data[0].djxl;
            }
        }, function (err) {
            console.log(err);
        },false);
        data.djyyList = [];
        //登记原因列表
        if (isNotBlank(djxl)) {
            //查看登记原因
            getReturnData("/slym/xm/queryDjxlDjyy", {djxl: djxl}, 'GET', function (item) {
                if (isNotBlank(item)) {
                    data.djyyList = item;
                }
            }, function (err) {
                console.log(err);
            }, false);
        }
        var getTpl = jbxxTpl.innerHTML;
        laytpl(getTpl).render(data, function(html){
            $('.bdc-jbxx').html(html);
        });
    },function (error) {
        delAjaxErrorMsg(error);

    });
}

//获取收件材料信息
function loadSjcl() {
    getReturnData("/slym/sjcl/list/pl", {processInsId: processInsId}, 'GET', function (data) {
        removeModal();
        generateSjcl(data, xmid);
    }, function (err) {
        console.log(err);
    });
}

//加载调查信息
function loadDcxx() {
    getReturnData("/qjdc", {processInsId: processInsId}, 'GET', function (data) {
        var getTpl = dcxxTpl.innerHTML;
        laytpl(getTpl).render(data, function (html) {
            $('.bdc-dcxx').html(html);
        });
        getStateById(readOnly, formStateId, "slymqjdc", "", "")
        form.render();
    }, function (err) {
        console.log(err);
    });
}

//组织收件材料到页面
function generateSjcl(data, xmid) {
    sjclNumber = data.length;
    var json = {
        xmid: xmid,
        bdcSlSjclDOList: data,
        zd: zdList
    };
    var tpl = sjclTpl.innerHTML;
    laytpl(tpl).render(json, function (html) {
        $('.bdc-sjcl-view').html(html);
    });
    form.render();
    getStateById(readOnly, formStateId, 'slymqjdc');
    form.render('select');
    disabledAddFa("sjclForm");
}

//附件上传（批量上传)
function scfjqb() {
    uploadSjcl("");
}

//保存表单信息
function saveXmxx(){
    var xmxxArray = $(".bdc-smxx").serializeArray();
    var bdcxmxx = {};
    if (xmxxArray !== null && xmxxArray.length > 0) {
        xmxxArray.forEach(function (item, index) {
            bdcxmxx[item.name] = item.value;
        });
        bdcxmxx.gzlslid = processInsId;
    }
    getReturnData("/slym/xm/jbxx", JSON.stringify(bdcxmxx), 'PATCH', function (data) {
        if (data > 0) {
            loadSmxx();
        }
    }, function (err) {
        throw err;
    }, false);
}

function saveQjdcxx() {
    var dcxxArray = $(".qjdcxx").serializeArray();
    var qjdcxx = {};
    if (dcxxArray !== null && dcxxArray.length > 0) {
        dcxxArray.forEach(function (item, index) {
            qjdcxx[item.name] = item.value;
        });
        qjdcxx.gzlslid = processInsId;
        qjdcxx.xmid = xmid;
    }
    getReturnData("/qjdc", JSON.stringify(qjdcxx), 'POST', function (data) {
        if (data) {
            loadDcxx();
        }
    }, function (err) {
        throw err;
    }, false);
}





