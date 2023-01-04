var ryzhDataList = [];
layui.use(['element', 'form', 'jquery', 'laydate', 'laypage', 'laytpl', 'layer'], function () {
    var form = layui.form;
    var $ = layui.jquery;
    var layer = layui.layer;
    var laytpl = layui.laytpl;

    form.render();
    //无数据的情况
    if ($(".info-content-details").length < 1) {
        $(".no-info").show();
    }

    //展开内容
    $(document).on("click", ".main-content-open", function () {
        if ($(this).parents(".main-content-item").hasClass("drag-disabled") == true) {

        } else if ($(this).parents(".main-content-item-title").siblings(".main-content-item-content").css("display") == "none") {
            $(this).parents(".main-content-item-title").siblings(".main-content-item-content").show();
            $(this).val("收起");
        } else {
            $(this).parents(".main-content-item-title").siblings(".main-content-item-content").hide();
            $(this).val("展开");
        }
    });

    //全部展开信息
    $(".expand-all").click(function () {
        if ($(this).attr('title') == "展开全部信息") {
            $(".main-content-open").val("收起");
            $(".main-content-item-content").show();
        } else {
            $(".main-content-open").val("展开");
            $(".main-content-item-content").hide();
        }
        $(".expand-all").toggleClass('bdc-hide');
    });

    //打开任意组合弹出框
    form.on('select(zsxh)', function (data) {
        var thisElem = this;
        if (data.value == 3) {
            showRyzh(thisElem);
        } else {
            goBackDefaultPage(thisElem);
        }
    });

    //点击返回按钮
    $('#goBack').on('click', function () {
        goBackDefaultPage();
        $(this).addClass('bdc-hide');
    });

    //点击任意其他处关闭弹出框
    $(document).click(function (e) {
        var e = e || window.event; //浏览器兼容性
        var elem = e.target || e.srcElement;
        while (elem) { //循环判断至跟节点，防止点击的是div子元素
            if (elem.id && elem.id == 'alertFather') {
                return;
            }
            elem = elem.parentNode;
        }
        $("#alertFather").hide();

    });

    //搜索框隐藏与显示
    $("#openSearch").on("click", function () {
        if ($(this).attr('title') == "展开搜索框") {
            $(this).attr('title', '收起搜索框');
            $('.main-content-container').height('399px');
        } else {
            $(this).attr('title', '展开搜索框');
            $('.main-content-container').height('467px');
        }
        $(".search").toggle();
    });

    $('.drag-disabled ').draggable({
        disabled: true
    });

    //添加证书
    $('body').on("click", "#addZhengshu", function () {
        var $classifyParent = $(this).parents('.bdc-classify-container');
        //获取当前有多少证书
        var num = $classifyParent.find(".main-content-container-right-item-container .main-content-container-right-item").length;
        //加一操作
        var newnum = parseFloat(num) + 1;
        var html = ' <div class="main-content-container-right-item bdc-ryzhzs" data-options="" data-certificate-index="' + newnum + '" name="zsxh">' +
            '<div class="main-content-container-right-item-title">' +
            '<span>证书' + newnum + '</span>' +
            '<input type="button" class="deleteZhengshu" value="删除">' +
            '</div>' +
            '</div>';
        $classifyParent.find(".main-content-container-right-item-container").append(html);

        //左侧添加
        var htmlLeft = '<div class="drag-disabled-container" data-certificate-index="' + newnum + '"></div>';
        $classifyParent.find(".main-content-container-container").append(htmlLeft);

        $classifyParent.find(".main-content-container-right-item").droppable({
            accept: '.todrag,.reverse-drag',
            onDrop: function (e, source) {
                var $classifyCur = $(source).parents('.bdc-classify-container');
                if ($(source).hasClass('reverse-drag') == false) {
                    var dataIndex = $(source).parent().parent().attr("data-index");
                    var n = $("<div class='reverse-drag' data-index='" + dataIndex + "'></div>");
                    var html = $(source).find("div:nth-child(2)").html();
                    var xmid = $(source).parent().parent().find("input[name='xmid']").val();
                    n.val(xmid);
                    n.html(html);
                    n.draggable({
                        revert: true,
                        deltaX: 10,
                        deltaY: 10,
                        proxy: function (source) {
                            var n = $('<div class="proxy"></div>');
                            n.html($(source).html()).appendTo('body');
                            return n;
                        }
                    });
                    $(this).append(n);
                    $(source).parent().parent().hide();
                    var CertificateIndex = $(this).attr("data-certificate-index");
                    var $n = $classifyCur.find(".main-content-item[data-index=" + dataIndex + "]");
                    $classifyCur.find(".drag-disabled-container[data-certificate-index=" + CertificateIndex + "]").append($n.clone());
                    //变成灰色显示
                    $classifyCur.find(".drag-disabled-container[data-certificate-index=" + CertificateIndex + "] .main-content-item[data-index=" + dataIndex + "]").addClass("drag-disabled").show();
                    $classifyCur.find(".drag-disabled-container[data-certificate-index=" + CertificateIndex + "] .drag-disabled[data-index=" + dataIndex + "] .main-content-item-content").hide();
                }
                else {

                    var CertificateIndex = $(this).attr("data-certificate-index");
                    var dataIndex = $(source).attr("data-index");
                    $classifyCur.find(".drag-disabled-container .drag-disabled[data-index=" + dataIndex + "]").remove();
                    var $n = $classifyCur.find(".main-content-item[data-index=" + dataIndex + "]");
                    $classifyCur.find(".drag-disabled-container[data-certificate-index=" + CertificateIndex + "]").append($n.clone());
                    //变成灰色显示
                    $classifyCur.find(".drag-disabled-container[data-certificate-index=" + CertificateIndex + "] .main-content-item[data-index=" + dataIndex + "]").addClass("drag-disabled").show();
                    $classifyCur.find(".drag-disabled-container[data-certificate-index=" + CertificateIndex + "] .drag-disabled[data-index=" + dataIndex + "] .main-content-item-content").hide();
                    $(this).append($(source));
                }
            }
        });

        $(".reverse-drag").droppable({
            accept: ".reverse-drag",
            onDrop: function (e, source) {
            }
        })
    });

    //删除证书
    $("body").on("click", ".deleteZhengshu", function () {
        var $classifyParent = $(this).parents('.bdc-classify-container');
        //判断内部是否有值  有值的话则对内部值进行退回
        if ($(this).parent().parent().find(".reverse-drag").length > 0) {
            $(this).parent().parent().find(".reverse-drag").each(function () {
                var dataIndex = $(this).attr("data-index");
                $classifyParent.find('.main-content-item[data-index=' + dataIndex + ']').show();
                $classifyParent.find(">.drag-disabled[data-index=" + dataIndex + "]").hide();
            });
        }
        var certificateIndex = $(this).parent().parent().attr("data-certificate-index");
        $(this).parent().parent().remove();
        $classifyParent.find(".main-content-container-right-item-container .main-content-container-right-item").each(function () {
            var index = parseFloat($(this).index()) + 1;
            $(this).attr("data-certificate-index", index);
            $(this).find(".main-content-container-right-item-title span").html("证书" + index);
        });
        $classifyParent.find(".main-content-container-container .drag-disabled-container[data-certificate-index=" + certificateIndex + "]").remove();
        $classifyParent.find(".main-content-container-container .drag-disabled-container").each(function () {
            var index = parseFloat($(this).index()) + 1;
            $(this).attr("data-certificate-index", index);
        });
    });

    $(".main-content-container").droppable({
        accept: ".reverse-drag",
        onDrop: function (e, source) {
            var $classifyParent = $(source).parents('.bdc-classify-container');
            var CertificateIndex = $(source).parent().attr("data-certificate-index");
            var dataIndex = $(source).attr("data-index");
            $classifyParent.find('.main-content-item[data-index=' + dataIndex + ']').show();

            $classifyParent.find(".drag-disabled-container[data-certificate-index=" + CertificateIndex + "]  .drag-disabled[data-index=" + dataIndex + "]").remove();
            $(".main-content-container>.bdc-classify-container>.drag-disabled[data-index=" + dataIndex + "]").hide();
            if ($(source).hasClass('reverse-drag')) {
                $(source).remove();
            }
        }
    });


    form.on("submit(queryBdcdyh)", function (data) {
        loadSelectBdcdyh(1, 5, data.field);
        return false;
    });

    //监听复选框选择
    //全选
    form.on('checkbox(qsCheckbox)', function (data) {
        $(this).parents('.bdc-classify-container').find('.info-content-details .main-content-item input[name=yxTable]').each(function (index, item) {
            item.checked = data.elem.checked;
        });
        form.render('checkbox');
    });
    //单个的
    form.on('checkbox(yxCheckbox)', function (data) {
        var $classify = $(this).parents('.bdc-classify-container');
        var checkedLength = $classify.find('.info-content-details .main-content-item .layui-form-checked').length;
        var checkBoxLength = $classify.find('.info-content-details .main-content-item .layui-form-checkbox').length;
        var qxCheckBox = $classify.find('.main-content-container-toptitle>.bdc-yx-fx input[name=qxTable]')[0];
        var xmid = $(this).parents(".main-content-item").find("[name=xmid]");
        if (checkedLength == checkBoxLength) {
            qxCheckBox.checked = true;
        } else {
            qxCheckBox.checked = false;
        }
        form.render('checkbox');
    });

    // 查询条件获取焦点
    $('.main-content-container').on('focus', '.ryzh-search .bdc-search', function () {
        $(this).addClass('bdc-search-click');
        $('.ryzh-search .layui-icon-search').css('font-size', '14px');
        $('.ryzh-search .layui-icon-close').css('font-size', '14px');
    }).on('blur', '.ryzh-search .bdc-search', function () {
        $(this).removeClass('bdc-search-click');
        $('.ryzh-search .layui-icon-search').css('font-size', '12px');
        $('.ryzh-search .layui-icon-close').css('font-size', '12px');
    })

    //按enter键
    $('.main-content-container').bind('keyup', '.ryzh-search .layui-icon-search' ,function (event) {
        if (event.keyCode == "13") {
            //回车执行查询
            var getAsideData = JSON.parse(JSON.stringify(ryzhDataList));
            var getSearchValue = $('.ryzh-search .bdc-search').val();
            for (var i = getAsideData.bdcXmDOList.length - 1; i >= 0; i--) {
                if ((getAsideData.bdcXmDOList[i].bdcdyh.indexOf(getSearchValue) == -1) && (getAsideData.bdcXmDOList[i].zl.indexOf(getSearchValue) == -1)) {
                    getAsideData.bdcXmDOList.splice(i, 1);
                }
            }

            var isfirst = true;
            goBackDefaultPage();
            $('#bdcdyList').remove();
            var json = {
                data: getAsideData,
            };
            //获取模板
            var tpl = $("#ryzhTpl").html();
            console.log(json);
            //渲染数据
            laytpl(tpl).render(json, function (html) {
                $('.main-content-container-container').before(html);
                $bdcdyList.removeClass('no-info');
                $bdcdyList.addClass('info-content-details');

            });
            showRyzh();
            dragRyzh();
            showOrHideItem();
        }
    });
});
var gzlslid, zsxh = 0, zdDjxlList = {};
$(function () {
    gzlslid = getQueryString("gzlslid");
    loadSelectBdcdyh(1, 5, {});
    loadZdbData();
    renderSearchInput();
});
function loadZdbData() {
    $.ajax({
        url: getContextPath() + "/bdczd",
        type: 'POST',
        dataType: 'json',
        async: false,
        success: function (data) {
            zdDjxlList = data.djxl;
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

var $bdcdyList = $("#bdcdyList");

//从项目表查询当前已经创建的项目
function loadSelectBdcdyh(page, size, queryData) {
    $.ajax({
        url: getContextPath() + "/gwc/slym",
        type: 'GET',
        dataType: 'json',
        data: {
            gzlslid:gzlslid,
            zl: queryData.zl,
            bdcdyh: deleteWhitespace(queryData.bdcdyh),
        },
        success: function (data) {
            if (data && data.length >0) {
                var $tableNone =$(".third-all-content-container .bdc-table-none");
                if($tableNone.length >0) {
                    $tableNone.remove();
                }
                // 重新组织数据
                for(var res =0;res <data.length;res++) {
                    for (var i = 0; i < data[res].bdcCshFwkgSlDOList.length; i++) {
                        for (var j = 0; j < data[res].bdcXmDOList.length; j++) {
                            if (data[res].bdcCshFwkgSlDOList[i].id === data[res].bdcXmDOList[j].xmid) {
                                data[res].bdcXmDOList[j].zsxh = data[res].bdcCshFwkgSlDOList[i].zsxh;
                            }
                        }
                    }
                    // 已保存数据根据证书序号分类
                    data[res].ryzhZsList = [];
                    for(var i = 0; i < data[res].bdcXmDOList.length; i++) {
                        if(data[res].bdcXmDOList[i].zsxh != null){
                            if(!data[res].ryzhZsList[data[res].bdcXmDOList[i].zsxh]) {
                                var ryzhZs = [];
                                ryzhZs.push(data[res].bdcXmDOList[i]);
                                data[res].ryzhZsList[data[res].bdcXmDOList[i].zsxh] = ryzhZs;
                            }else {
                                data[res].ryzhZsList[data[res].bdcXmDOList[i].zsxh].push(data[res].bdcXmDOList[i]);
                            }
                        }
                    }
                }
                ryzhDataList = data;
                initBdcdyh(data);
            } else {
                //没有数据,先清空主体内容部分
                $('#contentView').html("");
                if ($('.bdc-table-none').length == 0) {
                    $('.third-all-content-container-condition').after('' +
                        '<div class="bdc-table-none">' +
                        '<img src="../../static/lib/registerui/image/table-none.png" alt="">无数据</div>');
                }
            }
        },
        error: function (data) {
            delAjaxErrorMsg(data);
        }
    });
}


function initBdcdyh(slymGwcDTO) {
    layui.use(['form', 'jquery', 'laytpl', 'element'], function () {
        var form = layui.form;
        var $ = layui.jquery;
        var laytpl = layui.laytpl;
        var element = layui.element;

        var json = {
            data: slymGwcDTO,
            zdDjxl: zdDjxlList
        };
        //获取模板
        var tpl = $("#classifyTpl").html();
        console.log(json);
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            $('#contentView').html(html);
            $bdcdyList.removeClass('no-info');
            $bdcdyList.addClass('info-content-details');

        });
        //if (!bdcdyKg.zssl) {
        //    $("[name ='zsxhpz']").hide();
        //}
        form.render();
        element.render();
        showOrHideItem();
        if(slymGwcDTO.zssl =='3'){
            showRyzh();
        }
    })
}

/**
 * @param
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @description 保存证书序号
 * @date : 2020/8/5 9:58
 */
function saveZssl() {
    $('.bdc-classify-container').each(function (i) {
        var $classifyTab = $($('.bdc-classify-container')[i]);

        // 生成证书方式
        var sczsfs = $classifyTab.find(".bdc-sczsfs").val();
        if (sczsfs == 1) {
            zsxh++;
        }

        if (sczsfs === "1" || sczsfs === "2") {
            var $xmid =$classifyTab.find("input[name=xmid]");
            if($xmid.length>0) {
                var cshfwkgslList = [];
                for (var i = 0; i < $xmid.length; i++) {
                    var cshfwkgsl = {};
                    cshfwkgsl.id = ($xmid[i].value);
                    cshfwkgsl.zsxh = zsxh;
                    if(sczsfs === "2") {
                        cshfwkgsl.zsxh = null;
                    }
                    cshfwkgsl.sfsczs = 1;
                    if(sczsfs === "0") {
                        cshfwkgsl.zsxh = null;
                        cshfwkgsl.sfsczs = 0;
                    }
                    cshfwkgslList.push(cshfwkgsl);
                    if($('.bdc-classify-container').length ===1){
                        //选择一本证或者多本证针对所有项目，不传xmid，初始化开关实例表全部更新
                        cshfwkgsl.id =null;
                        break;
                    }


                }

                $.ajax({
                    url: getContextPath() + "/gwc/zsxh/" + gzlslid,
                    type: 'PATCH',
                    dataType: 'json',
                    contentType: "application/json",
                    data: JSON.stringify(cshfwkgslList),
                    success: function (data) {
                        if (data > 0) {
                            ityzl_SHOW_SUCCESS_LAYER("保存成功");
                        }
                    }, error: function (xhr, status, error) {
                        removeModal();
                        delAjaxErrorMsg(xhr)
                    }
                });
            }
        } else {
            saveZsxh($classifyTab);
        }
    });
}

// 任意组合证书序号保存
function saveZsxh(currentClassify) {
    var zsxhSpanArray = currentClassify.find('.bdc-ryzhzs');
    if (zsxhSpanArray != null && zsxhSpanArray.length > 0) {
        var cshfwkgslList = [];
        for (var i = 0; i < zsxhSpanArray.length; i++) {
            zsxh++;
            var zsxhSpan = zsxhSpanArray[i];
            $(zsxhSpan).find(".reverse-drag").each(function (index, elem) {
                var cshfwkgsl = {};
                cshfwkgsl.sfsczs = 1;
                cshfwkgsl.zsxh = zsxh;
                cshfwkgsl.id = $(this).attr('data-index');
                cshfwkgslList.push(cshfwkgsl);
            });

        }
        $.ajax({
            url: getContextPath() + "/gwc/zsxh/" + gzlslid,
            type: 'PATCH',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(cshfwkgslList),
            success: function (data) {
                if(data>0) {
                    ityzl_SHOW_SUCCESS_LAYER("保存成功")
                }
            }, error: function (xhr, status, error) {
                removeModal();
                delAjaxErrorMsg(xhr);
            }
        });
    }
}
var isfirst = true;
function showRyzh(thisElem) {
    //复选框按钮隐藏
    // $(".bdc-yx-fx").hide();
    //搜索按钮隐藏
    // $("#openSearch").hide();
    //收起展开内容
    $(".main-content-item-content").hide();
    $(".main-content-open").val("展开");
    if (isfirst == true) {
        dragRyzh();
        isfirst = false;
    }
    //头部标题消失
    $(".main-content-container-toptitle").hide();
    //权利类型标题 右侧隐藏
    $('.bdc-right-content').hide();
    //主体内容变窄
    $(thisElem).parents('.bdc-classify-container').siblings().addClass('bdc-hide');
    $(".main-content-container").addClass("new-main-content-container");
    $(".main-content-container").removeClass("old-main-content-container");
    //下方弹出框内容排版修改
    $(".main-content-item-content").addClass("new-main-content-item-content");
    $(".clickP").addClass("newclickP");
    //调整收起内容高度
    var height = $(".main-content-item[data-index =1]").find(".newclickP").length * 30;
    if (height > 70) {
        $(".new-main-content-item-content").css("height", height + "px");

    }
    //显示右侧栏目
    $(".main-content-container-right").show();
    //显示返回按钮
    $('#goBack').removeClass('bdc-hide');
    // 显示左侧栏搜索框
    $('.ryzh-search').removeClass('bdc-hide');
}

function dragRyzh() {
    $('.todrag').draggable({
        revert: true,
        deltaX: 10,
        deltaY: 5,
        disabled: false,
        proxy: function (source) {
            var n = $('<div class="proxy"></div>');
            n.html($(source).html()).appendTo('body');
            return n;
        },
        onStartDrag: function (e) {

        },
        onDrag: function (e) {

        }
    });
    $(".main-content-container-right-item").droppable({
        accept: '.todrag,.reverse-drag',
        onDrop: function (e, source) {
            var dataIndex = $(source).parent().parent().attr("data-index");
            var n = $("<div class='reverse-drag' data-index='" + dataIndex + "'></div>");
            var html = $(source).find("div:nth-child(2)").html();
            var xmid = $(source).parent().parent().find("input[name='xmid']").val();
            n.val(xmid);
            n.html(html);
            if ($(source).parent().attr("data-certificate-index") != $(this).attr("data-certificate-index")) {
                $(this).append(n);
            }

            n.draggable({
                revert: true,
                deltaX: 10,
                deltaY: 10,
                proxy: function (source) {
                    var n = $('<div class="proxy"></div>');
                    n.html($(source).html()).appendTo('body');
                    return n;
                }
            });

            var $dragClassify = $(source).parents('.bdc-classify-container');
            $(source).parent().parent().hide();
            var CertificateIndex = $(this).attr("data-certificate-index");
            var $n = $dragClassify.find(".main-content-item[data-index=" + dataIndex + "]");
            $dragClassify.find(".drag-disabled-container[data-certificate-index=" + CertificateIndex + "]").append($n.clone());
            //变成灰色显示
            $dragClassify.find(".drag-disabled-container[data-certificate-index=" + CertificateIndex + "] .main-content-item[data-index=" + dataIndex + "]").addClass("drag-disabled").show();
            $dragClassify.find(".drag-disabled-container[data-certificate-index=" + CertificateIndex + "] .drag-disabled[data-index=" + dataIndex + "] .main-content-item-content").hide();
            // $(this).remove();
        }
    });

    $('.reverse-drag').draggable({
        revert: true,
        deltaX: 10,
        deltaY: 5,
        disabled: false,
        proxy: function (source) {
            var n = $('<div class="proxy"></div>');
            n.html($(source).html()).appendTo('body');
            return n;
        },
        onStartDrag: function (e) {

        },
        onDrag: function (e) {

        }
    });

    $('.drag-disabled .todrag').draggable({
        disabled: true
    });
}

function goBackDefaultPage(thisElem) {
    //复选框按钮显示
    $(".bdc-yx-fx").show();
    //搜索按钮隐藏
    $("#openSearch").show();
    //头部标题消失
    $(".main-content-container-toptitle").show();
    //权利类型标题 右侧显示
    $('.bdc-right-content').show();
    //主体内容变窄
    $('.bdc-classify-container').removeClass('bdc-hide');
    $(".main-content-container").removeClass("new-main-content-container");
    $(".main-content-container").addClass("old-main-content-container");
    //下方弹出框内容排版修改
    $(".main-content-item-content").removeClass("new-main-content-item-content");
    $(".clickP").removeClass("newclickP");
    //调整收起内容高度
    $(".new-main-content-item-content").css("height", "70px");

    //隐藏右侧栏目
    $(".main-content-container-right").hide();
    // 隐藏左侧栏搜索框
    $('.ryzh-search').addClass('bdc-hide');

    $(".main-content-item").draggable({
        disabled: true
    });
    // 删除任意组合证书
    $(thisElem).parents('.bdc-classify-title').siblings('.main-content-container-right').find('.deleteZhengshu').trigger('click');
    // 添加空证书
    $(thisElem).parents('.bdc-classify-title').siblings('.main-content-container-right').find('#addZhengshu').trigger('click');
}

// 左侧栏中有已添加证书项则隐藏
function showOrHideItem() {
    $('.info-content-details .main-content-item').each(function () {
        var xmid = $(this).attr('data-index');
        var isSame = false;
        $('.drag-disabled-container  .main-content-item').each(function (index) {
            if(xmid === $(this).attr('data-index') ) {
                isSame = true;
            }
        });
        if (isSame){
            $(this).hide();
        }else {
            $(this).show();
        }
    });
}
