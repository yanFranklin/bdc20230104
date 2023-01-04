var $, form, layer, element, table, laytpl, laydate;
var processInsId = getQueryString("processInsId");
var formStateId = getQueryString("formStateId");
var readOnly = getQueryString("readOnly");
var taskId = getQueryString("taskId");
var zxlc = getQueryString('zxlc');
var xmid = getQueryString("xmid");
var sjclids = new Set();
var zdList;
var commonJedw = "";
var djxl;
var bysllx = "1";

layui.use(['form', 'jquery', 'laydate', 'element', 'layer', 'laytpl', 'table'], function () {
    form = layui.form;
    $ = layui.jquery;
    layer = layui.layer;
    element = layui.element;
    table = layui.table;
    laytpl = layui.laytpl;
    laydate = layui.laydate;


    form.on('select(selected)', function (data) {   //选择移交单位 赋值给input框
        $(this).parents('.layui-input-inline').find("input[name='blsx']").val(data.value);
        $(this).parents('.layui-input-inline').find("dl").css({"display": "none"});
        form.render();
        resetSelectDisabledCss();
    });


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
                $.when(loadJbxx(), loadZdData(), loadYcslxx()).done(function () {
                    removeModal();
                })
            } catch (e) {
                removeModal();
                delAjaxErrorMsg(e, "加载失败");
                return
            }
        }, 50);

        // 保存备注信息
        form.on('submit(saveBtn)', function () {
            addModel();
            saveBz();
            removeModal();
            return false;
        });

        // 保存
        form.on("submit(saveData)", function (data) {
            addModel();
            saveBysldxx();
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

// 加载权力信息与不动产单元信息统一入口
function generateQlxxAndBdcdyxx(data){

    // 加载不动产单元信息
    generateBdcdyxx({
        bdcSlXm : data,
        zd : zdList
    });

}

// 加载不动产单元信息
function generateBdcdyxx(data){
    var tpl = bdcdyxxTpl.innerHTML, view = document.getElementById('bdcdyxx');
    laytpl(tpl).render(data, function (html) {
        view.innerHTML = html;
    });
    form.render();
}


//加载基本信息
function loadJbxx() {
    getReturnData("/slym/xm/queryBdcSlJbxxByGzlslid", {"processInsId": processInsId}, "GET", function (data) {
        var json = {
            zd: zdList,
            jbxx: data
        };
        jbxxid = data.jbxxid;
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
        laytpl(getTpl).render(json, function (html) {
            $('.bdc-jbxx').html(html);
        });
        // getStateById(readOnly, formStateId, "bysld", "", "");
    }, function (error) {
        delAjaxErrorMsg(error);

    });
}

function resetSelectDisabledCss() {
    //select框禁用修改下拉图标(动态渲染的要在渲染完成之后执行以下两行js代码)
    $('.layui-select-disabled i').addClass("bdc-hide");
    $('.layui-select-disabled .layui-disabled').attr('disabled', 'true');
    changeBtxbjs();
}


function sqrReadIdCard(element) {
    readxxByIdCard(element, "cdrzjzl", "cdrzjh");
}

function loadYcslxx (){
    $.ajax({
        url: getContextPath() + "/ycsl/bygzlid",
        type: 'GET',
        dataType: 'json',
        async: false,
        data: {gzlslid: processInsId},
        success: function (data) {
            // 初始化加载权利信息和不动产单元信息
            generateQlxxAndBdcdyxx(data);
        }
    });
}

function showYlcxx() {
    $.ajax({
        url: getContextPath() + "/slym/xm/ylc",
        type: 'GET',
        dataType: 'json',
        async: false,
        data: {gzlslid: processInsId},
        success: function (data) {
            if (data && data.gzlslid) {
                var url = '/realestate-register-ui/view/xxbl/lc_home.html?processInsId=' + data.gzlslid + "&readOnly=true";
                layerCommonOpenFull(url, "原流程信息");
            } else {
                ityzl_SHOW_WARN_LAYER("原流程数据已被删除");
            }
        }
    });
    return false;
}

// 保存备注信息
function saveBz(){
    var bz = $('#bz').val();
    var jbxxid = $("#jbxxid").val();
    var bzObj = {};
    bzObj.jbxxid =jbxxid;
    bzObj.bz =bz;
    $.ajax({
        url: getContextPath() + "/cdlc/saveBz",
        type: "PATCH",
        data: JSON.stringify(bzObj),
        contentType: 'application/json',
        dataType: "json",
        async:false,
        success: function (data) {
            ityzl_SHOW_SUCCESS_LAYER("保存成功");
        },
        error: function (e) {
            ityzl_SHOW_WARN_LAYER("备注保存失败");
        }
    });
}

