var $, form, layer, element, table, laytpl, laydate;
var sjclids = new Set();
//用于存放所有的收件材料id
var sjclidLists = [];
//页面重新加载清空存储的收件材料id session
sessionStorage.removeItem("yxsjclids");
sessionStorage.removeItem("sjclidLists");
var processInsId = getQueryString("processInsId");
var lclx = "zhlc";
var formStateId = getQueryString("formStateId");
var zsmodel = getQueryString("zsmodel");
var readOnly = getQueryString("readOnly");
var isSubmit = true;
var zxlc = getQueryString("zxlc");
var printValue = getQueryString("print");
var taskId = getQueryString("taskId");
var jgyyZd = [{DM: 0, MC: '按常政发(1998)43号文件予以接轨'}, {DM: 1, MC: '按常房改办(1999)4号文接轨完全产权'}];
var ydjyyArr = {};
var ygzlslid ="";
var zdList = {a: []};
layui.use(['form', 'jquery', 'laydate', 'element', 'layer', 'laytpl', 'table'], function () {
    $ = layui.jquery;
    form = layui.form;
    layer = layui.layer;
    laydate = layui.laydate;
    laytpl = layui.laytpl;
    element = layui.element;
    table = layui.table;

    form.verify({
        required: function (value, item) {
            if (value == '' || value == null || value == undefined) {//判断是否为空
                $(item).addClass('layui-form-danger');
                if (sfyzbt) {
                    isSubmit = false;
                }
            }
        }
        , number: function (value, item) {
            //为非负整数,允许为空
            if (isNotBlank(value) && !/^[1-9]+[0-9]*]*$/.test(value)) {
                $(item).addClass('layui-form-danger');
                isSubmit = false;
                verifyMsg = "请输入非负整数";
            }
        }
        //数字
        , number2: [/^-?\d*$/, '请输入数字']
    });


    $(function () {
        // filter:moni-select用于监听select并修改对应的另一个select的值
        form.on('select(moni-select)', function (data) {
            // 改变对应的select
            if (data.value == 1) {
                $(this).parents("tr.for-bind").find("select[name='qlrlx']").val(1);
                form.render();
            } else if (data.value == 7) {
                $(this).parents("tr.for-bind").find("select[name='qlrlx']").val(2);
                form.render();
            }

        });


        //form验证
        form.on('checkbox(mjdw)', function () {
            $("[name='mjdw']").prop("checked", "");
            $(this).prop("checked", "checked");
            form.render('checkbox');
        });
        form.render();
        var element = layui.element;
        element.init();


        //监听回车事件
        $(document).keydown(function (event) {
            if ($('.bdc-spf-layer').length > 0) {
                var code = event.keyCode;
                if (code == 13) {
                    $('.layui-layer-btn0').click();
                    return false;
                }
            }

        });

        addModel();
        //加载页面上方按钮模块
        setTimeout(function () {
            try {
                $.when(loadData()).done(function () {
                    removeModal();

                })
            } catch (e) {
                removeModal();
                delAjaxErrorMsg(e, "加载失败");
                return
            }
        }, 10);

        $($('select[disabled="disabled"]')[1]).after('<img src="../images/lock.png" alt="">');

        //3. 滚动时头部固定
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
        var scrollIndex = 0;
        var slickLength;
        $(window).on('scroll', function () {
            if ($(this).scrollTop() > 85) {
                $cnotentTitle.css('top', '0');
                $navContainer.css('top', '58px');
            } else if ($(this).scrollTop() <= 85) {
                $cnotentTitle.css('top', '10px');
                $navContainer.css('top', '68px');
            }

            //为页面添加页面滚动监听事件
            var wst = $(window).scrollTop();
            scrollIndex++;
            if (scrollIndex == 1) {
                slickLength = $('.slick-slide').length - $('.slick-cloned').length;
            }
            //超过8个，需要左右切换的情况
            // slickIndex的值是一屏展示的slick的个数8，
            var slickIndex = 8;
            var $slickNow = $($('.slick .slick-slide')[8]);
            for (i = 1; i <= slickLength; i++) {
                if ($("#slick" + i).offset().top - 108 <= wst) {
                    slickIndex = 8 + i;
                    $('.slick .slick-slide').removeClass('bdc-this');
                    $slickNow = $($('.slick .slick-slide')[slickIndex]);
                    $slickNow.addClass('bdc-this');
                }
                if (wst == 0) {
                    $('.slick .slick-slide').removeClass('bdc-this');
                    $slickNow = $($('.slick .slick-slide')[8]);
                    $slickNow.addClass('bdc-this');
                }
            }
            if (!$slickNow.hasClass('slick-active')) {
                $('.slick-next').click();
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
                        $.when(
                            saveFwjgxx()
                        ).done(function () {

                            removeModal();
                            ityzl_SHOW_SUCCESS_LAYER("保存成功");
                        });
                    } catch (e) {
                        removeModal();
                        if (e.message) {
                            showAlertDialog(e.message);
                        } else {
                            delAjaxErrorMsg(e);

                        }
                        return
                    }
                }, 10);
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

        var $titleBtnBox = $('.title-btn-area');
        var thirdIn = false;
        $titleBtnBox.on('mouseenter', '.bdc-test-btn', function () {
            $('.bdc-test').show();
            $('.bdc-test1').hide();
        });

        //二级按钮鼠标覆盖
        $titleBtnBox.on('mouseenter', '.bdc-title-secondary-btn', function () {
            $('.bdc-third-btn-box').show();
        }).on('mouseleave', '.bdc-title-secondary-btn', function () {
            setTimeout(function () {
                if (!thirdIn) {
                    $('.bdc-third-btn-box').hide();
                }
            }, 100);
        });
        $titleBtnBox.on('mouseenter', '.bdc-third-btn-box', function () {
            thirdIn = true;
        }).on('mouseleave', '.bdc-third-btn-box', function () {
            thirdIn = false;
        });
        $titleBtnBox.on('mouseleave', function () {
            $('.bdc-table-btn-more').hide();
        });
        $('.bdc-table-btn-more a').on('click', function () {
            $(this).parent().hide();
        });

    });
});

//加载页面数据（入口）
function loadData() {
    //获取字典
    getCommonZd(function (data) {
        if (isNotBlank(data)) {
            zdList = data;
        }
    });
    loadFgfxx();
    $(".basic-info").css("border-bottom"," 1px dotted #eaedf1");
}

//加载房改房信息
function loadFgfxx() {

    getReturnData("/slym/fgyhsf/getBdcSlFghysfDTO",{"gzlslid":processInsId}, "GET", function (data) {
        removeModal();
        ygzlslid =data.yxmxx.gzlslid;
        data.jgyyZd =jgyyZd;
        data.zd =zdList;
        if(!isNotBlank(data.bdcSlFgyhsfDO.jgyy) ){
            data.bdcSlFgyhsfDO.jgyy = '0';
        }


        var tpl = fgfTpl.innerHTML, view = document.getElementById('fgfView');
        laytpl(tpl).render(data, function (html) {
            view.innerHTML = html;
        });
        if(!isNotBlank(data.bdcSlFgyhsfDO.jgrq)){
            laydate.render({
                elem: '#jgrq',
                trigger: 'click',
                format: 'yyyy-MM-dd',
                value: new Date()
            });
        }
        //申请人和单元信息模块不可编辑
        $("#sqrForm").find('select').attr('disabled', 'off');
        $("#sqrForm").find('input').attr('disabled', 'off');
        $("#fdcqForm").find('select').attr('disabled', 'off');
        $("#fdcqForm").find('input').attr('disabled', 'off');
        form.render();
        renderDate(form, "");
        getStateById(readOnly, formStateId, "fgfjgsl", "", "");
        //监听 权利信息 内 附记 单击
        $('#fdcqForm').on('click', '.bdc-pj-popup', function () {
            var $thisTextarea = $(this).siblings('textarea');
            var fjContent = $thisTextarea.val();
            var title = $(this).parents(".layui-form-item").find("label").text();
            layer.open({
                title: isNotBlank(title)? title : '附记',
                type: 1,
                area: ['960px'],
                btn: ['确定'],
                content: $('#fjPopup')
                , yes: function (index, layero) {
                    // //提交 的回调
                    // $thisTextarea.val($('#fjPopup textarea').val());
                    // $('#fjPopup textarea').val('');
                    layer.close(index);
                }
                , btn2: function (index, layero) {
                    //取消 的回调
                    $('#fjPopup textarea').val('');
                }
                , cancel: function () {
                    //右上角关闭回调
                    $('#fjPopup textarea').val('');
                }
                , success: function () {
                    $('#fjPopup textarea').val(fjContent);
                    $('#fjPopup textarea').attr("disabled","disabled");
                }
            });
        });
        disabledAddFa();
        loadSjcl();
    }, function(xhr) {
        delAjaxErrorMsg(xhr);
    }, false);

}

//权利人详情展示 -----点击 详细 按钮
function showQlr(qlrid, xmid, qlrlb, elem) {
    addModel();
    var $this = $(".layui-this");
    var qllx = $($this.find("input[name='qllx']")[0]).val();
    // 获取当前元素所在的tab页面，通过tab页找到权利信息下的djxl信息
    var djxl = $(elem).parents(".layui-show").find("input[name='djxl']").val();
    var url = getContextPath() + "/changzhou/slym/qlr.html?processInsId=" + processInsId + "&qlrid=" + qlrid + "&lclx=" + lclx + "&xmid=" + xmid + "&formStateId=" + formStateId + "&qllx=" + qllx + "&readOnly=true" + "&zxlc=" + zxlc + "&djxl=" + djxl;
    layer.open({
        type: 2,
        area: ['960px', '610px'],
        fixed: false, //不固定
        title: "申请人详细信息",
        content: url,
        btnAlign: "c"
    });

    removeModal();
    form.render();
    resetSelectDisabledCss();
    renderDate(form, formIds);
    disabledAddFa();
}

function saveFwjgxx() {
    var fwjgxxData = {};
    fwjgxxData.jgyy = $('#jgyy').val();
    fwjgxxData.jgrq = $('#jgrq').val();
    fwjgxxData.bz = $('#bz').val();
    fwjgxxData.fgyhsfid = $('[name="fgyhsfid"]').val();

    getReturnData("/slym/fgyhsf/saveOrUpdate", JSON.stringify(fwjgxxData), 'POST', function (data) {
    }, function (err) {
        throw err;
    }, false);
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
    var tpl = sjclTpl.innerHTML, view = document.getElementById("sjclView");
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });
    form.render();
    getStateById(readOnly, formStateId, 'slymzh', 'sjcl');
    form.render('select');
    disabledAddFa("sjclForm");
    $('td').next('img').remove();
}

//加载收件材料
function loadSjcl() {
    getReturnData("/slym/sjcl/list/pl", {processInsId: ygzlslid}, 'GET', function (data) {
        generateSjcl(data, "");
    }, function (err) {
        removeModal();
        console.log(err);
    });
}

/**
 * 补充上一手材料
 */
function bcYclxx() {
    //获取上一手
    scwjj(ygzlslid, "房改优惠售房补充材料");
}

//仅仅用于查看文件管理器并且设置权限
function checksjcl() {
    var bdcSlWjscDTO = queryBdcSlWjscDTO('', true);
    bdcSlWjscDTO.spaceId = ygzlslid;
    bdcSlWjscDTO.storageUrl = document.location.protocol + "//" + document.location.host + "/storage";
    var url = getContextPath() + "/view/slym/sjd.html?paramJson=" + encodeURI(JSON.stringify(bdcSlWjscDTO));
    openSjcl(url, false, "附件上传");
}


//查询收件材料所需参数（打开附件上传使用）
function queryBdcSlWjscDTO(clmc, sfhqqx) {
    var bdcSlWjscDTO = {};
    $.ajax({
        url: '/realestate-accept-ui/slym/sjcl/bdcSlWjscDTO',
        type: 'GET',
        async: false,
        data: {processInsId: processInsId, clmc: clmc, sfhqqx: sfhqqx},
        success: function (data) {
            if (isNotBlank(data)) {
                bdcSlWjscDTO = data;
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
    return bdcSlWjscDTO;
}

//打开附件上传页面
function openSjcl(url, sfsx, title) {
    var width = $(window).width() + "px";
    var height = $(window).height() + "px";
    var index = layer.open({
        title: title,
        type: 2,
        content: url,
        area: [width, height],
        maxmin: true,
        end: function () {
            if (sfsx) {
                addModel();
                $.ajax({
                    url: getContextPath() + "/slym/sjcl/ys",
                    type: 'PATCH',
                    dataType: 'json',
                    data: {gzlslid: processInsId},
                    success: function (data) {
                        if (data > 0) {
                            loadSjcl();
                        }
                        removeModal();
                    }, error: function (xhr, status, error) {
                        delAjaxErrorMsg(xhr)
                    }
                });
            }
        }
    });
    layer.full(index);
}



