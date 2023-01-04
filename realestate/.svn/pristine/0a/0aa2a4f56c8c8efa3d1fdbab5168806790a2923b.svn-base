var gzlslid = getQueryString("processInsId");
var type = getQueryString("type");
var processInsId = gzlslid;
var formStateId = getQueryString("formStateId");
var readOnly = getQueryString("readOnly");
var zdList = {a: []};
var $, form, layer, element, table, laydate, laytpl;
var sjclNumber = 0;
var sjclids = new Set();
//用于存放所有的收件材料id
var sjclidLists = [];
var zjjgidList =[];
//页面重新加载清空存储的收件材料id session
sessionStorage.removeItem("yxsjclids");
sessionStorage.removeItem("sjclidLists");
layui.use(['form', 'jquery', 'laydate', 'element', 'layer', 'table', 'laytpl'], function () {
    $ = layui.jquery;
    form = layui.form;
    layer = layui.layer;
    element = layui.element;
    table = layui.table;
    laydate = layui.laydate;
    laytpl = layui.laytpl;

    $(function () {
        //初始化字典表
        getCommonZd(function (data) {
            if (isNotBlank(data)) {
                zdList = data;
            }
        });

        //滚动时头部固定
        var $cnotentTitle = $('.bdc-content-fix');
        var $navContainer = $('.bdc-form-div');

        function defaultStyle() {
            if ($cnotentTitle.length == 1) {
                $('.bdc-form-div').css('padding-top', '68px');
            }
            if ($(window).scrollTop() > 68) {
                $cnotentTitle.css('top', '0');
                $navContainer.css('top', '63px');
            } else if ($(window).scrollTop() <= 68) {
                $cnotentTitle.css('top', '10px');
                $navContainer.css('top', '68px');
            }
        }

        $(window).resize(function () {
            defaultStyle();
        });
        $(window).on('scroll', function () {
            if ($(this).scrollTop() > 68) {
                $cnotentTitle.css('top', '0');
                $navContainer.css('top', '63px');
            } else if ($(this).scrollTop() <= 68) {
                $cnotentTitle.css('top', '10px');
                $navContainer.css('top', '68px');
            }
        });

        form.on('submit(saveZjjg)', function (data) {
            saveZjjg();
            return false;
        });

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

        $('.layui-input').on('focus', function () {
            if ($(this).hasClass('layui-form-danger')) {
                $(this).removeClass('layui-form-danger');
            }
            if ($(this).parents('.layui-form-select').siblings().hasClass('layui-form-danger')) {
                $(this).parents('.layui-form-select').siblings().removeClass('layui-form-danger');
            }
        });
        $('.xm-input').on('click', function () {
            if ($(this).parent().siblings('.xm-hide-input').hasClass('layui-form-danger')) {
                $(this).parent().siblings('.xm-hide-input').removeClass('layui-form-danger');
            }
        });

        //监听input修改
        $('.layui-input').on('change', function () {
            $(this).addClass('bdc-change-input');
        });
        //加载页面数据
        loadZjjg();
    });
});

function loadZjjg() {
    addModel("加载中");

    // 存放基本信息、资金监管信息、收件信息接口返回的数据
    var json = {};
    json.zd = zdList;

    var zjjgUrl = "";
    if(type == "cx"){
        zjjgUrl = "/slym/zjjg/cx";
    }else{
        zjjgUrl = "/slym/zjjg";
    }

    $.when(// 获取资金监管信息接口数据
        getReturnData(zjjgUrl, {gzlslid: gzlslid}, "GET", function (data) {
            removeModal();
            json.zjjg = {};
            if(data &&data.bdcSlZjjgDOS &&data.bdcSlZjjgDOS.length >0){
                zjjgidList =data.zjjgidList;
                json.zjjg =data.bdcSlZjjgDOS[0];
            }
        }, function (xhr) {
            delAjaxErrorMsg(xhr);
        }, false), // 获取基本信息接口数据
        getReturnData("/slym/xm/queryBdcSlJbxxByGzlslid",{"processInsId":gzlslid}, "GET", function (data) {
            removeModal();
            json.jbxx = data;
        }, function(xhr) {
            delAjaxErrorMsg(xhr);
        }, false), // 获取收件信息接口数据
        getReturnData("/slym/sjcl/list/pl",{"processInsId":gzlslid}, "GET", function (data) {
            removeModal();
            sjclNumber = data.length;
            json.sjcl = data;
        }, function(xhr) {
            delAjaxErrorMsg(xhr);
        }, false))
        .done(function(){
            // 渲染页面
            var tpl = zjjgTpl.innerHTML, view = document.getElementById('zjjgView');
            laytpl(tpl).render(json, function (html) {
                view.innerHTML = html;
            });
            form.render();
            renderDate(form, "");
            getStateById(readOnly, formStateId, "zjjg", "", "");
            disabledAddFa();
            renderSelect();
            renderSelectClose(form);
        });
}

function saveZjjg() {
    addModel("保存中");
    saveJbxx();
    saveSjcl();
    var zjjgArray = $('.zjjg');
    var zjjg = {};
    zjjgArray.serializeArray().forEach(function (item, index) {
        zjjg[item.name] = item.value;
    });

    if(Object.keys(zjjg).length >1 &&isNotBlank(zjjg.zjjgid)) {
        zjjg.zjjgidList =zjjgidList;
        getReturnData("/slym/zjjg", JSON.stringify(zjjg), "POST", function (data) {
            loadZjjg();
            removeModal();
            ityzl_SHOW_SUCCESS_LAYER("保存成功");
        }, function (xhr) {
            delAjaxErrorMsg(xhr);
        })
    }
}

function saveJbxx(){
    var jbxxArray = $(".jbxx").serializeArray();
    var jbxx = {};
    if (jbxxArray !== null && jbxxArray.length > 0) {
        jbxxArray.forEach(function (item, index) {
            jbxx[item.name] = item.value;
        });
    }
    if(Object.keys(jbxx).length >1 &&isNotBlank(jbxx.jbxxid)) {
        getReturnData("/slym/xm/jbxx", JSON.stringify(jbxx), 'PATCH', function (data) {
        }, function (err) {
            throw err;
        }, false);
    }

}

//删除收件材料批量
function deleteSjclPl() {
    //防止删除之前新增了很多数据没保存，删除后会刷新table丢失
    var sjclidsArray = [];
    sjclids.forEach(function (element, sameElement, set) {
        sjclidsArray.push(sameElement)
    });

    if (null != sjclidsArray && sjclidsArray.length > 0) {
        sjclNumber = sjclNumber - 1;
        var deleteIndex = layer.open({
            type: 1,
            skin: 'bdc-small-tips',
            title: '确认删除',
            area: ['320px'],
            content: '是否确认删除？',
            btn: ['确定', '取消'],
            btnAlign: 'c',
            yes: function () {
                addModel("正在删除");
                //确定操作
                getReturnData("/slym/sjcl/deleteBdcsjclPl?sjclids=" + sjclidsArray, {
                    processInsId: processInsId,
                    sfcxql: true
                }, 'DELETE', function (data) {
                    removeModal();
                    sjclids.clear();
                    loadZjjg();
                }, function (err) {
                    delAjaxErrorMsg(err);
                });

                layer.close(deleteIndex);
            },
            btn2: function () {
                //取消
                layer.close(deleteIndex);
            }
        });
    } else {
        layer.alert("请选择最少一条数据进行删除！", {title: '提示'});
    }

}

function loadSjcl() {
    getReturnData("/slym/sjcl/list/pl", {processInsId: processInsId}, 'GET', function (data) {
        loadZjjg();
    }, function (err) {
        console.log(err);
    });
}