var $, form, layer, element, table, laytpl, laydate;
var processInsId = getQueryString("processInsId");
var formStateId = getQueryString("formStateId");
var readOnly = getQueryString("readOnly");
var taskId = getQueryString("taskId");
var zxlc = getQueryString('zxlc');
var sjclids = new Set();
//用于存放所有的收件材料id
var sjclidLists = [];
var zdList;
var djxl;

layui.use(['form', 'jquery', 'laydate', 'element', 'layer', 'laytpl', 'table'], function () {
    form = layui.form;
    $ = layui.jquery;
    layer = layui.layer;
    element = layui.element;
    table = layui.table;
    laytpl = layui.laytpl;
    laydate = layui.laydate;

    $(function () {
        //初始化日期控件
        laydate.render({
            elem: '#sjsj'
        });
        laydate.render({
            elem: '#zlsj'
        });

        // 添加遮罩
        addModel();
        // 加载页面信息
        setTimeout(function () {
            try {
                $.when(loadJbxx(), loadGzdxx(),loadZdData(),loadSjcl()).done(function () {
                    removeModal();
                })
            } catch (e) {
                removeModal();
                delAjaxErrorMsg(e, "加载失败");
                return
            }
        }, 50);

        // 保存
        form.on("submit(saveData)", function (data) {
            addModel();
            saveGzdxx();
            saveSjcl();
            return false;
        });

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

        //滚动时头部固定
        var $cnotentTitle = $('.bdc-content-fix');
        var $navContainer = $('.bdc-nav-container');

        function defaultStyle() {
            if ($cnotentTitle.length == 1) {
                $('.bdc-form-div').css('padding-top', '68px');
            }
            if ($(window).scrollTop() > 85) {
                $cnotentTitle.css('top', '0');
                $navContainer.css('top', '58px');
            } else if ($(window).scrollTop() <= 85) {
                $cnotentTitle.css('top', '10px');
                $navContainer.css('top', '68px');
            }
        }

        $(window).resize(function () {
            defaultStyle();
        });
        $(window).on('scroll', function () {
            if ($(this).scrollTop() > 85) {
                $cnotentTitle.css('top', '0');
                $navContainer.css('top', '58px');
            } else if ($(this).scrollTop() <= 85) {
                $cnotentTitle.css('top', '10px');
                $navContainer.css('top', '68px');
            }
        });
        //点击提交时不为空的全部标红
        var isSubmit = true;
        form.verify({
            required: function (value, item) {
                if (value == '') {
                    $(item).addClass('layui-form-danger');
                    isSubmit = false;
                }
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

    });
});

// 获取字典
function loadZdData(){
    $.ajax({
        url: getContextPath()+"/bdczd",
        type: 'POST',
        dataType: 'json',
        async: false,
        success: function (data) {
            if (isNotBlank(data)) {
                zdList = data;
            }
        },error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

// 加载收件材料
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
    //renderForm();
    getStateById(readOnly, formStateId, 'gzd', 'sjcl');
    form.render('select');
    disabledAddFa("sjclForm");
}

//加载基本信息
function loadJbxx() {
    getReturnData("/slym/xm/queryBdcSlJbxxByGzlslid", {"processInsId": processInsId}, "GET", function (data) {
        jbxxid = data.jbxxid;
        console.log(data);
        //时间只保留年月日
        if (data.slsj != null) {
            data.slsj = data.slsj.substring(0, 10);
        }
        //获取xmid、djxl
        getReturnData("/gwc/list/bdcslxm", {jbxxid: jbxxid}, 'GET', function (data) {
            if (isNotBlank(data) && data.length > 0) {
                xmid = data[0].xmid;
                djxl = data[0].djxl;
            }
        }, function (err) {
            console.log(err);
        }, false);
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
        laytpl(getTpl).render(data, function (html) {
            $('.bdc-jbxx').html(html);
        });
        getStateById(readOnly, formStateId, "gzd", "", "");
    }, function (error) {
        delAjaxErrorMsg(error);

    });
}

//加载告知单信息
function loadGzdxx() {
    $.ajax({
        url: getContextPath() + "/cdlc/getCdxx/" + processInsId,
        type: "GET",
        success: function (data) {
            console.log("加载查档信息成功");
            if (isNotBlank(data)) {
                generateGzdxx(data);
            }
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}
//组织基本信息到页面
function generateGzdxx(data) {
    var json = {
        zd: zdList,
        gzdxx: data
    };
    var tpl = gzdxxTpl.innerHTML;
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        $('.bdc-gzdxx')[0].innerHTML = html;
    });
    getStateById(readOnly, formStateId, "gzd", "", "");
    form.render();
    form.render('select');
    disabledAddFa();
    renderSelect();
}

// 保存告知单信息
function saveGzdxx(){
    saveBz();
    var cdxxData = $('.gzd').serializeArray();
    var cdxxObj = {};
    if (cdxxData.length > 0) {
        cdxxData.forEach(function (item, index) {
            cdxxObj[item.name] = item.value;
        });
    }
    console.log(cdxxObj);
    $.ajax({
        url: getContextPath() + "/cdlc/saveBdcCdxx?gzlslid=" + processInsId,
        type: "PATCH",
        data: JSON.stringify(cdxxObj),
        contentType: 'application/json',
        dataType: "json",
        success: function (data) {
            console.log("保存查档信息成功");
            removeModal();
            loadGzdxx();
            ityzl_SHOW_SUCCESS_LAYER("保存成功");
        },
        error: function (e) {
            removeModal();
            ityzl_SHOW_WARN_LAYER("保存失败");
        }
    });
}

// 保存查档信息
function saveBz(){
    var bz = $('#bz').val();
    var sqrxm = $('#xcxr').val();
    var jbxxid = $("#jbxxid").val();
    var bzObj = {};
    bzObj.jbxxid =jbxxid;
    bzObj.bz = bz;
    bzObj.sqrxm = sqrxm;
    $.ajax({
        url: getContextPath() + "/cdlc/saveBz",
        type: "PATCH",
        data: JSON.stringify(bzObj),
        contentType: 'application/json',
        dataType: "json",
        async: false,
        success: function (data) {
            console.log("备注保存成功")
        },
        error: function (e) {
            ityzl_SHOW_WARN_LAYER("备注保存失败");
        }
    });
}
