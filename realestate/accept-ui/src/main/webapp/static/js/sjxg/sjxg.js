/**
 * 数据修改JS代码
 */
var zdList = {a:[]};
var $, form, layer, element, table, laytpl, laydate;
// 查询参数
var processInsId = $.getUrlParam("processInsId");
// 表单ID
var formStateId = $.getUrlParam('formStateId');
// 任务id
var taskId = $.getUrlParam('taskId');
// 获取表单权限参数readOnly
var readOnly = $.getUrlParam('readOnly');
var sjclids = new Set();
layui.use(['element', 'form', 'jquery', 'laydate', 'laytpl', 'layer'], function () {
     element = layui.element;
     form = layui.form;
     $ = layui.jquery;
     laytpl = layui.laytpl;
     layer = layui.layer;
     laydate = layui.laydate;


    // 加载遮罩
    addModel();

    var screenH = document.documentElement.clientHeight;
    $(".content-main").css({'min-height': screenH - 100});

    getReturnData("/bdczd",{},"POST",function (data) {
        zdList=data;
    },function () {
    },false);


    //获取数据
    $.ajax({
        url: "/realestate-accept-ui/slsjxg/query?processInsId=" + processInsId,
        contentType: "application/json;charset=utf-8",
        type: "GET",
        async: false,
        dataType: "json",
        success: function (data) {
            if (data) {

                // 为基本信息赋值
                // form.val('form', data);
                generateJbxx(data);

                loadSjcl();



                // 关闭遮罩
                removeModal();
            }
        },
        error: function (err) {
            removeModal();
            delAjaxErrorMsg(err);
        }

    });


    //点击提交时不为空的全部标红
    var isSubmit = true;
    //验证提示信息
    var verifyMsg = "";
    form.verify({
        required: function (value, item) {
            if (value == '') {
                $(item).addClass('layui-form-danger');
                isSubmit = false;
                verifyMsg = "必填项不能为空";
            }
        }
    });


    //监听提交
    form.on('submit(saveData)', function (data) {
        if (!isSubmit) {
            layer.msg(verifyMsg, {icon: 5, time: 3000});
            isSubmit = true;
            removeModal();
            return false;
        } else {
            addModel();
            saveSjxg();
            saveSjcl();

            removeModal();
            // 禁止提交后刷新
            return false;

        }
    });

    //监听复选框选择
    //全选，收件材料
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
        if(sjclids.size >0) {
            var idList = [];
            sjclids.forEach(function (element, sameElement, set) {
                idList.push(sameElement);
            });
            sessionStorage.setItem("yxsjclids",idList);
        } else {
            sessionStorage.setItem("yxsjclids",[]);
        }
    });
    //收件材料单个的
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
        if(sjclids.size >0) {
            var idList = [];
            sjclids.forEach(function (element, sameElement, set) {
                idList.push(sameElement);
            });
            sessionStorage.setItem("yxsjclids",idList);
        } else {
            sessionStorage.setItem("yxsjclids",[]);
        }
    });

    // 处理时间，不展示时分秒
    lay('.test-item').each(function () {
        if (isNotBlank(this.value)) {
            this.value = Format(this.value, 'yyyy-MM-dd');
        }
    });





});

function saveSjxg() {
    var bdcSlSjxgDO = {};
    bdcSlSjxgDO["sjxgid"] = $("#sjxgid").val();
    bdcSlSjxgDO["wtlx"] = $("#wtlx").val();
    bdcSlSjxgDO["wtms"] = $("#wtms").val();
    $.ajax({
        url: "/realestate-accept-ui/slsjxg/save",
        type: "PUT",
        data: JSON.stringify(bdcSlSjxgDO),
        contentType: 'application/json',
        dataType: "json",
        success: function (data) {
            ityzl_SHOW_SUCCESS_LAYER("保存成功");
        },
        error: function (e) {
            ityzl_SHOW_WARN_LAYER("保存失败");
        }
    });
}
//组织基本信息到页面
function generateJbxx(bdcslsjxg) {
    var json = {
        bdcslsjxg: bdcslsjxg
    };
    var tpl = jbxxTpl.innerHTML, view = document.getElementById('sljbxx');
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });
    form.render();
    getStateById(readOnly, formStateId, 'sjxg');
    disabledAddFa();


}

//加载收件材料
function loadSjcl() {
    getReturnData("/slym/sjcl/list/pl", {processInsId: processInsId}, 'GET', function (data) {
        generateSjcl(data, "");
    }, function (err) {
        console.log(err);
    });
}

//组织收件材料到页面
function generateSjcl(data, xmid) {
    sjclNumber = data.length;
    sjclidLists = [];
    if(data !== null && data.length >0) {
        for(var i=0;i<data.length;i++) {
            sjclidLists.push(data[i].sjclid);
        }
    }
    sessionStorage.setItem("sjclidLists",sjclidLists);
    var json = {
        xmid: xmid,
        bdcSlSjclDOList: data,
        zd: zdList
    };
    var tpl = sjclTpl.innerHTML, view = document.getElementById("sjcl");
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });
    form.render();
    getStateById(readOnly, formStateId, 'sjxg');
}

