/**
 * Created by Ypp on 2020/4/8.
 * 附件管理js
 */
var $, form, table, laytpl;
var zdList = {a:[]};
var processInsId = getQueryString("processInsId");
var sjclids = new Set();
layui.use(['table', 'form', 'laytpl'], function(){
    $ = layui.jquery;
    table = layui.table;
    form = layui.form;
    laytpl = layui.laytpl;

    getReturnData("/bdczd", '', 'POST', function (data) {
        if (isNotBlank(data)) {
            zdList = data;
        }
    }, function (err) {
        console.log(err);
    },false);
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

    form.on("submit(saveData)", function () {
        addModel();
        setTimeout(function () {
            saveSjcl();
        }, 10);
        return false;
    });
});

function loadSjcl() {
    getReturnData("/slym/sjcl/list/pl", {processInsId: processInsId}, 'GET', function (data) {
        generateSjcl(data, "");
    }, function (err) {
        console.log(err);
    });
}

var sjclNumber = 0;

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
    var tpl = sjclTpl.innerHTML, view = document.getElementById("sjclView");
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });
    form.render();
    //renderForm();
    //getStateById(readOnly, formStateId, 'slymzh', 'sjcl');
    //form.render('select');
    disabledAddFa("sjclForm");
}