var slbh;
var $, laytpl, form;
var jbxxid;
var sjclids = new Set();
//用于存放所有的收件材料id
var sjclidLists = [];
//页面重新加载清空存储的收件材料id session
sessionStorage.removeItem("yxsjclids");
sessionStorage.removeItem("sjclidLists");
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    form = layui.form;
    $ = layui.jquery;
    var element = layui.element;
    laytpl = layui.laytpl;
    var layer = layui.layer;
    loadJbxxTabContent();
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

//加载基本信息tab内容
function loadJbxxTabContent() {
    getReturnData("/slym/xm/queryBdcSlJbxxByGzlslid", {processInsId: processInsId}, 'GET', function (data) {
        if (isNotBlank(data)) {
            jbxxid = data.jbxxid;
            var tpl1 = jbxxTpl.innerHTML,
                view1 = document.getElementById('sljbXx');
            //渲染数据
            var json = {
                jbxx: data
            };
            laytpl(tpl1).render(json, function (html) {
                view1.innerHTML = html;
            });
            //加载收件材料
            getReturnData("/slym/sjcl/list/pl", {processInsId: processInsId}, 'GET', function (data) {
                generateSjcl(data, false);
            }, function (err) {
                console.log(err);
            }, false);
            form.render();
            renderDate(form, ""); // 格式化一窗基本信息页面收件时间
            getStateById(readOnly, formStateId, 'ycym');
            form.render('select');
            disabledAddFa();
            renderSelect();
        }
    }, function (err) {
        console.log(err);
    }, false);

}

// 加载基本信息tab
function loadJbxx() {
    getReturnData("/slym/xm/queryBdcSlJbxxByGzlslid", {processInsId: processInsId}, 'GET', function (data) {
        if (isNotBlank(data)) {
            jbxxid = data.jbxxid;
            var tpl1 = jbxxTpl.innerHTML,
                view1 = document.getElementById('sljbXx');
            //渲染数据
            var json = {
                jbxx: data
            };
            laytpl(tpl1).render(json, function (html) {
                view1.innerHTML = html;
            });
            form.render();
            renderDate(form, ""); // 格式化一窗基本信息页面收件时间
            getStateById(readOnly, formStateId, 'ycym');
            form.render('select');
            disabledAddFa();
            renderSelect();
        }
    }, function (err) {
        console.log(err);
    }, false);
}

//加载收件材料
function loadSjcl() {
    getReturnData("/slym/sjcl/list/pl", {processInsId: processInsId}, 'GET', function (data) {
        generateSjcl(data, true);
    }, function (err) {
        console.log(err);
    });
}

//组织收件材料到页面
//isLoad:是否加载表单权限
function generateSjcl(data, isLoad) {
    sjclidLists = [];
    if (data !== null && data.length > 0) {
        sjclNumber = data.length;
        for (var i = 0; i < data.length; i++) {
            sjclidLists.push(data[i].sjclid);
        }
    }
    sessionStorage.setItem("sjclidLists", sjclidLists);
    var json = {
        bdcSlSjclDOList: data,
        zd: zdList
    };
    var tpl = sjclTpl.innerHTML, view = document.getElementById("sjcl");
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });
    if (isLoad) {
        form.render();
        getStateById(readOnly, formStateId, 'ycym');
        form.render('select');
        disabledAddFa("sjclForm");
    }
}

