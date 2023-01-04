var gzlslid = getQueryString("processInsId");
var formStateId = getQueryString("formStateId");
var readOnly = getQueryString("readOnly");
var zxlc = getQueryString("zxlc");
var taskid = getQueryString("taskId");
var processInsId = gzlslid;
var zdList = {a: []};
var $, form, layer, element, table, laydate, laytpl, formselects;
var btZdItem = [];
var sjclNumber = 0;
var sjclidLists = [];
layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});
layui.use(['form', 'jquery', 'laydate', 'element', 'layer', 'table', 'laytpl'], function () {
    $ = layui.jquery;
    form = layui.form;
    layer = layui.layer;
    element = layui.element;
    table = layui.table;
    laydate = layui.laydate;
    laytpl = layui.laytpl;
    // formselects = layui.formSelects;

    $(function () {
        addModel();
        //初始化字典表
        getCommonZd(function (data) {
            if (isNotBlank(data)) {
                zdList = data;
            }
        });

        //加载页面数据
        loadYwlzd();

        //初始化日期控件
        lay('.render-date').each(function () {
            laydate.render({
                elem: '#wydcrq',
                trigger: 'click',
                format: 'yyyy-MM-dd',
                value: new Date()
            });
            laydate.render({
                elem: '#qjshrq',
                trigger: 'click',
                value: new Date()
            });
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

        //点击提交时不为空的全部标红
        var isSubmit = verifyform(form);
        var isFirst = true;
        form.verify({
            required: function (value, item) {
                if (value == '') {
                    if (isFirst) {
                        console.log($(item).parents('.layui-tab-item'));
                        $('.layui-tab-item').removeClass('layui-show');
                        $(item).parents('.layui-tab-item').addClass('layui-show');
                        var liIndex = $(item).parents('.layui-tab-item').index() + 1;
                        console.log(liIndex);
                        console.log($('.layui-tab-title li:nth-child(' + liIndex + ')'));
                        $('.layui-tab-title li').removeClass('layui-this');
                        $('.layui-tab-title li:nth-child(' + liIndex + ')').addClass('layui-this');
                        isFirst = false;
                    }
                    $(item).addClass('layui-form-danger');
                    btZdItem.push($(item).attr("id"));
                    // isSubmit = false;
                }
            }
        });
        form.on('submit(saveYwlzd)', function (data) {
            if (!isSubmit) {
                layer.msg('必填项不能为空', {icon: 5, time: 3000});
                isSubmit = true;
                isFirst = true;
                return false;
            } else {
                saveSlxmxx();
                saveYwlzd();
                saveSjcl();
                return false;
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

    });
});

//表单信息加载
function loadYwlzd() {

    getReturnData("/slym/ywlz", {gzlslid: processInsId}, 'GET', function (data) {
        if (isNotBlank(data)) {
            // 这里获取ywlzd接口的数据ywlzd

            var json = {
                bdcxmxx: data.bdcSlXmDO,
                jbxx: data.bdcSlJbxxDO,
                slShxx: data.slShxxVO,
                gzShxx: data.gzShxxVO,
                ywlzd: data.bdcSlYwlzDO
            }
            var tpl = ywlzdTpl.innerHTML, view = document.getElementById('ywlzdView');
            //渲染数据
            laytpl(tpl).render(json, function (html) {
                view.innerHTML = html;
            });
            loadSjcl();
            form.render();
            renderDate(form, "");
            getStateById(readOnly, formStateId, "ywlzd", "", "");
            disabledAddFa();
            renderSelect();
            renderSelectClose(form)
        }
        removeModal();
    }, function (xhr) {
        delAjaxErrorMsg(xhr)
    });
}


//表单保存
function saveYwlzd() {
    addModel("正在保存");
    var ywlzdArray = $('.ywlzd');
    var ywlzd = {};
    ywlzdArray.serializeArray().forEach(function (item, index) {
        ywlzd[item.name] = item.value;
    });
    ywlzd["bdcqzh"] = $("#cqzh").val();
    ywlzd.processInsId = processInsId;
    getReturnData("/slym/ywlz", JSON.stringify(ywlzd), "POST", function (data) {
        //自动签名
        autoSign();
        loadYwlzd();
        removeModal();
        ityzl_SHOW_SUCCESS_LAYER("保存成功");
        if (btZdItem && btZdItem.length > 0) {
            window.setTimeout(function () {
                for (var i = 0; i < btZdItem.length; i++) {
                    if ($("#" + btZdItem[i]) && $("#" + btZdItem[i]).val() == "") {
                        $("#" + btZdItem[i]).addClass('layui-form-danger');
                    }
                }
                btZdItem = [];
            }, 1500)
        }
        console.log("保存成功");
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    }, false);
}

function saveSlxmxx() {
    addModel("正在保存");
    var slxmArray = $('.slxm');
    var slxm = {};
    slxmArray.serializeArray().forEach(function (item, index) {
        slxm[item.name] = item.value;
    });
    getReturnData("/gwc/updateBdcSlXm", JSON.stringify(slxm), "PATCH", function (data) {
        console.log("保存受理项目成功")
    }, function (xhr) {

    })

}

//签名
function sign(id, jdmc) {
    // addModel();
    var shxxDO = {};
    // shxxid为当前的taskId
    shxxDO.gzlslid = processInsId;
    shxxDO.jdmc = jdmc;
    //获取数据
    $.ajax({
        url: "/realestate-accept-ui/slshxx/sign",
        contentType: "application/json;charset=utf-8",
        type: "PUT",
        data: JSON.stringify(shxxDO),
        success: function (data) {
            $("#" + id + "qmtp").attr('src', data.qmtpdz);
            $("#" + id + "qmsj").html("");
            $("#" + id + "qmsj").html("日期：" + data.qmsj);
            removeModal();
        },
        error: function () {
            removeModal();
            warnMsg("签名失败！");
        }
    });
}

function autoSign() {
    getReturnData("/slym/ywlz/jdmc", {taskid: taskid}, "GET", function (data) {
        if (data) {
            if (data.taskName === "受理") {
                sign("slr", data.taskName);
            } else {
                sign("gzr", data.taskName);
            }
        }
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    });
}

function uploadFj() {
    getReturnData("/sjcl/folder", {gzlslid: processInsId, wjjmc: "公证处出具法律调查意见书"}, "GET", function (data) {
        var bdcSlWjscDTO = queryBdcSlWjscDTO("", false);
        var spaceId = processInsId;
        bdcSlWjscDTO.spaceId = spaceId;
        bdcSlWjscDTO.storageUrl = document.location.protocol + "//" + document.location.host + "/storage";
        // bdcSlWjscDTO.storageUrl = "http://192.168.2.99:8030/storage";
        var url = getContextPath() + "/view/slym/sjd.html?paramJson=" + encodeURI(JSON.stringify(bdcSlWjscDTO));
        openSjcl(url, false, "附件上传");
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    })
}

function querySwzm() {
    var width = $(window).width() + "px";
    var height = $(window).height() + "px";
    var index = layer.open({
        title: "死亡证明查询",
        type: 2,
        content: '/realestate-inquiry-ui/view/gxjkjs/sjSwyxzm.html',
        area: [width, height],
        maxmin: true
    });
    layer.full(index);
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
    if (data !== null && data.length > 0) {
        for (var i = 0; i < data.length; i++) {
            sjclidLists.push(data[i].sjclid);
        }
    }
    sessionStorage.setItem("sjclidLists", sjclidLists);
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
    form.render('select');
}