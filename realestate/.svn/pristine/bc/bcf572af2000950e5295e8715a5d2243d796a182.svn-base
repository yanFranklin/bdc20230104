var zlcsh;
var slbh;
var processDefKey = parent.processDefKey;
layui.use(['element', 'form', 'jquery', 'laydate', 'laypage', 'laytpl', 'layer'], function () {
    var form = layui.form;
    var $ = layui.jquery;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var element = layui.element;

    form.render();

    var isfirst = true;

    //无数据的情况
    if ($(".info-content-details").length < 1) {
        $(".no-info").show();
    }
    //初始化已选择信息
    $("#cshSelectedXx").click(function () {
        var $this = $(this);
        $this.attr("disabled", true).css("pointer-events", "none");
        addModel();
        setTimeout(function () {
            try {
                $.when(cshSelectedXx()).done(function () {
                    $this.removeAttr("disabled").css("pointer-events", "auto");
                })
            } catch (e) {
                $this.removeAttr("disabled").css("pointer-events", "auto");
                removeModal();
                ERROR_CONFIRM("加载失败", e.message);

            }
        }, 1);
    });

    //点击弹出修改下拉框
    $(document).on("click", ".main-content-item >.main-content-item-content>div", function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }
        var getThisTop = $(this).offset().top + 50;
        var getThisLeft = $(this).offset().left - 30;
        $("#alertFather").css({
            top: getThisTop,
            left: getThisLeft
        });
        var getData = {};
        if (this.id === "qllx") {
            getData = {
                zdQllx: zdQllxList
            };
        }
        editxmid = $(this).parents(".main-content-item").find("input[name=xmid]").val();
        editindex = $(this).parents(".main-content-item").attr("data-index");
        var template = this.id + "Template";
        var getTpl = $("#" + template + "").html(); //获取自己定义的模板元素
        laytpl(getTpl).render(getData, function (html) {
            $("#alertFather").html(html).show(); //加载到主元素中
            form.render('select', template); //重新渲染下拉框
        });
    });

    //修改取消
    $(document).on("click", ".cancle-btn", function () {
        $("#alertFather").hide()
    });

    //修改确定
    $(document).on("click", ".sure-btn", function () {
        var name = this.id.replace("sure", "");
        var editname = name + "edit";
        var value = $("[name=" + editname + "]").val();
        //获取选中数据
        var xmids = [];
        xmids.push(editxmid);
        if (isNotBlank(value)) {
            //是否注销特殊处理，将对应项目历史关系转换为其他项目受理历史关系，用于部分注销情况
            if (name === "sfzx") {
                if (value === "1") {
                    $.ajax({
                        url: getContextPath() + "/gwc/dealYxzxxm",
                        type: 'PATCH',
                        data: {jbxxid: jbxxid, xmids: (xmids.join(","))},
                        success: function (data) {
                            ityzl_SHOW_SUCCESS_LAYER('操作成功');
                            //隐藏弹出层
                            $("#alertFather").hide();
                            $('#queryBdcdyh').click();
                            parent.addGwc();

                        }, error: function (xhr, status, error) {
                            delAjaxErrorMsg(xhr)
                        }
                    });

                }

            } else {
                var bdcSlXmData = {};
                bdcSlXmData[name] = value;
                bdcSlXmData.xmid = editxmid;
                $.ajax({
                    url: getContextPath() + "/gwc/updateBdcSlXm",
                    type: 'PATCH',
                    dataType: 'json',
                    async: false,
                    contentType: "application/json",
                    data: JSON.stringify(bdcSlXmData),
                    success: function (data) {
                        //前台展现
                        var select = $("#alertFather").find("select")[0];
                        var index = select.selectedIndex;
                        var options = select.options;
                        var value = options[index].innerText;
                        var dataIndex = name + editindex;
                        $(".clickP[data-index=" + dataIndex + "]").find(".confirm-content").text(value);
                        $("#alertFather").hide();
                    }, error: function (xhr, status, error) {
                        delAjaxErrorMsg(xhr)
                    }
                });
            }
        }
    });

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

    //显示隐藏个当前登记小类下内容
    $('.main-content-container').on('click', '.bdc-operate-classify', function () {
        var $this = $(this);
        $this.find('.layui-icon').toggleClass('bdc-hide');
        $this.parents('.bdc-classify-title').siblings().toggleClass('bdc-hide');
    });

    //删除
    $(document).on("click", ".main-content-delete", function () {
        if ($(this).parents(".main-content-item").hasClass("drag-disabled") == true) {

        } else {
            var xmid = $(this).parents(".main-content-item").find("input[name=xmid]").val();
            var deleteIndex = layer.open({
                type: 1,
                skin: 'bdc-small-tips',
                title: '确认删除',
                area: ['320px'],
                content: '是否确认删除？',
                btn: ['确定', '取消'],
                btnAlign: 'c',
                yes: function () {
                    //确定操作
                    deleteAll(xmid);
                    layer.close(deleteIndex);
                },
                btn2: function () {
                    //取消
                    layer.close(deleteIndex);
                }
            });
        }
    });

    //打开任意组合弹出框
    form.on('select(zsxh)', function (data) {
        if (data.value == 3) {
            //显示楼栋
            $(".layui-colla-title").show();
            //复选框按钮隐藏
            $(".bdc-yx-fx").hide();
            //搜索按钮隐藏
            $("#openSearch").hide();
            //收起展开内容
            $(".main-content-item-content").hide();
            $(".main-content-open").val("展开");
            //收起住宅
            $(".layui-colla-title").click()
            if (isfirst == true) {
                //拖动楼栋
                $('.layui-colla-title').draggable({
                    revert: true,
                    deltaX: 10,
                    deltaY: 5,
                    disabled: false,
                    proxy: function (source) {
                        var m = $('<div class="proxy"></div>');
                        m.html($(source).html()).appendTo('body');
                        return m;
                    },
                    onStartDrag: function (e) {
                    },
                    onDrag: function (e) {

                    },
                });
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

                //右侧div接收到拖拽元素事件
                $(".main-content-container-right-item").droppable({
                    accept: '.todrag,.layui-colla-title,.reverse-drag',
                    onDrop: function (e, source) {
                        //拖动楼栋接收html的处理逻辑
                        if ($(source).hasClass('layui-colla-title') == true) {
                            //父元素下标标识
                            var fdataIndex = $(source).parent().attr("data-findex");
                            //获取数组的长度
                            var listLength = $(source).parent().find("input[name='yxLength']").val();

                            //循环遍历layui-colla-item
                            for (var i = 0; i < listLength; i++) {
                                //layui-colla-item
                                var $colldiv = $(source).parent().find(".layui-colla-content[data-index ="+ (i +1)+ "]")
                                //坐落住宅
                                var fjhtml = $colldiv.children().children().find("div:nth-child(3)").html();
                                var choose = $colldiv.attr("data-choose");
                                var m = $("<div class='reverse-drag' data-findex='"+fdataIndex+"' data-index='"  +( i + 1)+"'  data-choose='"  +choose+"'></div>")
                                var xmid = $colldiv.children().find("input[name='xmid']").val();
                                //设置id
                                m.val(xmid);
                                //显示文字
                                m.html(fjhtml);
                                //置灰
                                $colldiv.addClass("drag-disabled");
                                //设置已选值
                                $colldiv.attr("data-choose","true");
                                //复选框按钮隐藏
                                $(".bdc-yx-fx").hide();
                                //搜索按钮隐藏
                                $("#openSearch").hide();
                                //收起展开内容
                                $(".main-content-item-content").hide();
                                //放置增加的子元素 排除已选的
                                if ($(source).parent().attr("data-findex")!= $(this).parent().attr("data-findex") && m.attr("data-choose")=="false") {
                                    $(this).append(m);
                                }
                                //楼层标题置灰
                                $(source).addClass("drag-disabled")
                                //开启楼栋拖拽
                                m.draggable({
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

                                    },
                                });

                            }

                        };//拖动单个住宅接收html的处理逻辑
                        if ($(source).hasClass('todrag') == true) {
                            //住宅标识
                            var dataIndex = $(source).parent().parent().attr("data-index");

                            //    main-content-item
                            var $fdiv = $(source).parent().parent().parent()
                            //楼栋标识
                            var fdataIndex = $fdiv.parent().attr("data-findex");


                            var dataChoose = $(source).parent().parent().attr("data-choose");
                            var n = $("<div class='reverse-drag' data-findex='"+fdataIndex+"' data-index='"+dataIndex+"'  data-choose='"+dataChoose+"'></div>");
                            var html = $(source).find("div:nth-child(3)").html();
                            var xmid = $(source).parent().parent().find("input[name='xmid']").val();
                            //给定标识选择为已选
                            $fdiv.attr("data-choose","true");
                            n.val(xmid);
                            n.html(html);
                            var h2 = $(source).parents(".layui-colla-item").find(".layui-colla-title");
                            // 先默认给楼栋置灰
                            h2.addClass("drag-disabled");
                            //获取数组的长度
                            var listLength = $(source).parents(".layui-colla-item").find("input[name='yxLength']").val();
                            //循环遍历layui-colla-item 判断存在一个未选中的住宅那么就去除置灰
                            for (var i = 0; i < listLength; i++) {
                                //layui-colla-item
                                var $colldiv =  $(source).parents(".layui-colla-item").find(".layui-colla-content[data-index =" + (i + 1) + "]")
                                //坐落住宅
                                var choose = $colldiv.attr("data-choose");
                                if(choose=="false"){
                                    h2.removeClass("drag-disabled");
                                }
                            }
                            if ($(source).parent().attr("data-certificate-index") != $(this).attr("data-certificate-index")) {

                                $(this).append(n);
                            }

                            //开启住宅拖回
                            n.draggable({
                                revert: true,
                                deltaX: 10,
                                deltaY: 5,
                                disabled: false,
                                proxy: function (source) {
                                    var n = $('<div class="proxy"></div>');
                                    n.html($(source).html()).appendTo('body');
                                    index = $(source).attr("data-index");
                                    findex =  $(source).attr("data-findex")
                                    return n;
                                },
                                onStartDrag: function (e) {
                                },
                                onDrag: function (e) {

                                },
                            });


                            var $dragClassify = $(source).parents('.layui-collapse');
                            var CertificateIndex = $(this).attr("data-certificate-index");
                            var $fn = $dragClassify.find(".layui-colla-item[data-findex=" + fdataIndex + "]");
                            var $n = $fn.find(".layui-colla-content[data-index=" + dataIndex + "]");
                            $n.addClass("drag-disabled").show();

                        }
                    }
                });
                isfirst = false;
            }

            //头部标题消失
            $(".main-content-container-toptitle").hide();
            //权利类型标题 右侧隐藏
            $('.bdc-right-content').hide();
            //主体内容变窄
            $(this).parents('.bdc-classify-container').siblings().addClass('bdc-hide');
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
        } else {
            goBackDefaultPage();
        }
    });

    function goBackDefaultPage() {
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

        $(".main-content-item").draggable({
            disabled: true
        });
        //移除楼栋显示
        $(".layui-colla-title").hide();
        //展开住宅
        $(".layui-colla-content").addClass("layui-show");
    }

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
        $classifyParent.find(".main-content-container-right-item").droppable(
            {
                accept: '.todrag,.layui-colla-title,.reverse-drag',
                onDrop: function (e, source) {

                    //拖动楼栋接收html的处理逻辑
                    if ($(source).hasClass('layui-colla-title') == true) {
                        //父元素下标标识
                        var fdataIndex = $(source).parent().attr("data-findex");
                        //获取数组的长度
                        var listLength = $(source).parent().find("input[name='yxLength']").val();

                        //循环遍历layui-colla-item
                        for (var i = 0; i < listLength; i++) {
                            //layui-colla-item
                            var $colldiv = $(source).parent().find(".layui-colla-content[data-index ="+ (i +1)+ "]")
                            //坐落住宅
                            var fjhtml = $colldiv.children().children().find("div:nth-child(3)").html();
                            var choose = $colldiv.attr("data-choose");
                            var m = $("<div class='reverse-drag' data-findex='"+fdataIndex+"' data-index='"  +( i + 1)+"'  data-choose='"  +choose+"'></div>")
                            var xmid = $colldiv.children().find("input[name='xmid']").val();
                            //设置id
                            m.val(xmid);
                            //显示文字
                            m.html(fjhtml);
                            //置灰
                            $colldiv.addClass("drag-disabled");
                            //设置已选值
                            $colldiv.attr("data-choose","true");
                            //复选框按钮隐藏
                            $(".bdc-yx-fx").hide();
                            //搜索按钮隐藏
                            $("#openSearch").hide();
                            //收起展开内容
                            $(".main-content-item-content").hide();
                            //放置增加的子元素 排除已选的
                            if ($(source).parent().attr("data-findex")!= $(this).parent().attr("data-findex") && m.attr("data-choose")=="false") {
                                $(this).append(m);
                            }
                            //楼层标题置灰
                            $(source).addClass("drag-disabled")
                            //开启楼栋拖拽
                            m.draggable({
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

                                },
                            });

                        }

                    };//拖动单个住宅接收html的处理逻辑
                    if ($(source).hasClass('todrag') == true) {
                        //住宅标识
                        var dataIndex = $(source).parent().parent().attr("data-index");

                        //    main-content-item
                        var $fdiv = $(source).parent().parent().parent()
                        //楼栋标识
                        var fdataIndex = $fdiv.parent().attr("data-findex");


                        var dataChoose = $(source).parent().parent().attr("data-choose");
                        var n = $("<div class='reverse-drag' data-findex='"+fdataIndex+"' data-index='"+dataIndex+"'  data-choose='"+dataChoose+"'></div>");
                        var html = $(source).find("div:nth-child(3)").html();
                        var xmid = $(source).parent().parent().find("input[name='xmid']").val();
                        //给定标识选择为已选
                        $fdiv.attr("data-choose","true");
                        n.val(xmid);
                        n.html(html);
                        var h2 = $(source).parents(".layui-colla-item").find(".layui-colla-title");
                        // 先默认给楼栋置灰
                        h2.addClass("drag-disabled");
                        //获取数组的长度
                        var listLength = $(source).parents(".layui-colla-item").find("input[name='yxLength']").val();
                        //循环遍历layui-colla-item 判断存在一个未选中的住宅那么就去除置灰
                        for (var i = 0; i < listLength; i++) {
                            //layui-colla-item
                            var $colldiv =  $(source).parents(".layui-colla-item").find(".layui-colla-content[data-index =" + (i + 1) + "]")
                            //坐落住宅
                            var choose = $colldiv.attr("data-choose");
                            if(choose=="false"){
                                h2.removeClass("drag-disabled");
                            }
                        }
                        if ($(source).parent().attr("data-certificate-index") != $(this).attr("data-certificate-index")) {

                            $(this).append(n);
                        }

                        //开启住宅拖回
                        n.draggable({
                            revert: true,
                            deltaX: 10,
                            deltaY: 5,
                            disabled: false,
                            proxy: function (source) {
                                var n = $('<div class="proxy"></div>');
                                n.html($(source).html()).appendTo('body');
                                index = $(source).attr("data-index");
                                findex =  $(source).attr("data-findex")
                                return n;
                            },
                            onStartDrag: function (e) {
                            },
                            onDrag: function (e) {

                            },
                        });


                        var $dragClassify = $(source).parents('.layui-collapse');
                        var CertificateIndex = $(this).attr("data-certificate-index");
                        var $fn = $dragClassify.find(".layui-colla-item[data-findex=" + fdataIndex + "]");
                        var $n = $fn.find(".layui-colla-content[data-index=" + dataIndex + "]");
                        $n.addClass("drag-disabled").show();

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
                var datafindex = $(this).attr("data-findex");
                var $floorParent=  $classifyParent.find('.layui-colla-item[data-findex='+datafindex+']');
                //去除置灰
                $floorParent.find('.layui-colla-title').removeClass("drag-disabled")
                $floorParent.find('.layui-colla-title').css("color","#333");
                $floorParent.find('.layui-colla-content[data-index=' + dataIndex + ']').removeClass("drag-disabled")
                //设置未选状态
                $floorParent.find('.layui-colla-content[data-index=' + dataIndex + ']').attr("data-choose","false");

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

    //左边拖拽回元素
    $(".main-content-container").droppable({
        accept: ".reverse-drag",
        onDrop: function (e, source) {
            var $classifyParent = $(source).parents('.bdc-classify-container');
            var CertificateIndex = $(source).parent().attr("data-certificate-index");
            var dataIndex = $(source).attr("data-index");
            var datafindex = $(source).attr("data-findex");
            $classifyParent.find('.main-content-item[data-index=' + dataIndex + ']').show();
            var $floorParent=  $classifyParent.find('.layui-colla-item[data-findex='+datafindex+']');
            //去除置灰
            $floorParent.find('.layui-colla-title').removeClass("drag-disabled")
            $floorParent.find('.layui-colla-title').css("color","#333");
            $floorParent.find('.layui-colla-content[data-index=' + dataIndex + ']').removeClass("drag-disabled");
            $floorParent.find('.layui-colla-content[data-index=' + dataIndex + ']').attr("data-choose","false");
            if ($(source).hasClass('reverse-drag')) {
                $(source).remove();
            }
        }
    });

    //购物车清空
    $('#clearGwc').on('click', function (event) {
        if ($('.bdc-classify-container').length === 0) {
            removeModal();
            showAlertDialog("请选择数据");
            return false;
        }
        var sfzlcsh =0;
        if(zlcsh ==="true") {
            sfzlcsh =1;
        }
        addModel();
        $.ajax({
            url: getContextPath() + '/gwc/deleteYxxm',
            data: {jbxxid: jbxxid,sfzlcsh:sfzlcsh,clearGwc:1},
            success: function (data) {
                removeModal();
                ityzl_SHOW_SUCCESS_LAYER('清空成功');
                $("#queryBdcdyh").click();
                parent.addGwc();
            },
            error: function (xhr, status, error) {
                removeModal();
                delAjaxErrorMsg(xhr);
            }
        });
    });

    // 批量删除
    $('#deletePl').on('click', function (event) {
        addModel();
        //获取选中数据
        var xmids = getSelectedData();
        if (xmids.length === 0) {
            showAlertDialog("未选择数据");
            removeModal();
        } else if (xmids.length > 0) {
            var xmidstr = '';
            for (var i = 0; i < xmids.length; i++) {
                xmidstr += xmids[i] + ',';
            }
            // 判断 xmidstr 是否为空
            if (isNotBlank(xmidstr)) {
                xmidstr = xmidstr.substring(0, xmidstr.length - 1);
            }
            $.ajax({
                url: getContextPath() + '/gwc/deleteYxxm',
                data: {xmids: xmidstr},
                success: function (data) {
                    removeModal();
                    ityzl_SHOW_SUCCESS_LAYER('清空成功');
                    $("#queryBdcdyh").click();
                    parent.addGwc();
                },
                error: function (xhr, status, error) {
                    removeModal();
                    delAjaxErrorMsg(xhr);
                }
            });
        }
    });

    //批量注销(用于部分注销)
    $("#zxSelectedXx").on('click', function () {
        //获取选中数据
        var xmids = getSelectedData();
        if (xmids.length === 0) {
            showAlertDialog("未选择数据");
        } else {
            $.ajax({
                url: getContextPath() + "/gwc/dealYxzxxm",
                type: 'PATCH',
                data: {jbxxid: jbxxid, xmids: (xmids.join(","))},
                success: function (data) {
                    ityzl_SHOW_SUCCESS_LAYER('操作成功');
                    $('#queryBdcdyh').click();
                    parent.addGwc();

                }, error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr)
                }
            });
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
});

$(function () {
    zlcsh = getQueryString("zlcsh");
    slbh = getQueryString("slbh");
    loadSelectBdcdyh(1, 5, {});
    loadZdbData();
    renderSearchInput();

});

var zsxh = 0;

function cshSelectedXx() {
    if ($('.bdc-classify-container').length === 0) {
        removeModal();
        showAlertDialog("请选择数据");
        return false;
    }
    var ycslDjywDzb ={};
    //判断是否为一窗创建,对照表存在数据,即为一窗流程
    getReturnData("/slxxcsh/queryYcslDjywDzb",{gzldyid:gzldyid},"GET",function (data) {
        ycslDjywDzb =data;
    },function (error) {
        delAjaxErrorMsg(error);
    },false);
    if(!$.isEmptyObject(ycslDjywDzb)){
        //一窗创建
        parent.ycslCsh(jbxxid, gzldyid);

    }else {
        /**
         * 点击创建后,调用初始化方法前处理证书序号值
         */
        $('.bdc-classify-container').each(function (i) {
            var $classifyTab = $($('.bdc-classify-container')[i]);

            var sczsfs = $classifyTab.find(".bdc-sczsfs").val();
            if (sczsfs == 1) {
                zsxh++;
            }

            if (sczsfs === "1" || sczsfs === "2") {
                var $xmid =$classifyTab.find("input[name=xmid]");
                if($xmid.length>0) {
                    var xmids = [];
                    for (var i = 0; i < $xmid.length; i++) {
                        xmids.push($xmid[i].value);
                    }
                    var batchZsxhData = {
                        jbxxid: jbxxid,
                        xmids: xmids.join(","),
                        zsxh: zsxh
                    };
                    if (sczsfs == 2) {
                        batchZsxhData.zsxh = null;
                    }
                    $.ajax({
                        url: getContextPath() + "/gwc/batchUpdateBdcSlXmZsxh",
                        type: 'PATCH',
                        dataType: 'json',
                        async: false,
                        data: batchZsxhData,
                        success: function (data) {
                            if (data > 0) {
                                // flag = data;
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
        removeModal();
        parent.addModel();
        var tab = window.parent.$('.layui-this').val();
        parent.cshSelectedXxSingle(jbxxid, processDefKey,true, true,tab);
    }

}

var zdDjxlList = {};
var zdQllxList = {};
var editxmid = "";
var editindex;

function loadZdbData() {
    $.ajax({
        url: getContextPath() + "/bdczd",
        type: 'POST',
        dataType: 'json',
        async: false,
        success: function (data) {
            zdDjxlList = data.djxl;
            zdQllxList = data.qllx;
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

var $bdcdyList = $("#bdcdyList");

function loadSelectBdcdyh(page, size, queryData) {
    $.ajax({
        url: getContextPath() + "/gwc/listYxBdcdyDTOGroupByDjxl",
        type: 'GET',
        dataType: 'json',
        data: {
            jbxxid: jbxxid,
            gzldyid: gzldyid,
            zl: queryData.zl,
            bdcdyh: deleteWhitespace(queryData.bdcdyh),
            sfzlcsh: zlcsh === "true" ? 1 : null,
            sfazfz:"true"

        },
        success: function (data) {
            if (data.length > 0) {
                var $tableNone =$(".third-all-content-container .bdc-table-none");
                if($tableNone.length >0) {
                    $tableNone.remove();
                }
                initBdcdyh(data);
                if (data[0].bdcdyKgDTO.sfzx) {
                    $("#zxSelectedXx").removeClass('bdc-hide');
                }
                if(state ==="wlzs"){
                    $(".bdc-yx-ybdcqz").show();
                }
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


function initBdcdyh(bdcYxYwxxDTOList) {
    layui.use(['form', 'jquery', 'laytpl', 'element'], function () {
        var form = layui.form;
        var $ = layui.jquery;
        var laytpl = layui.laytpl;
        var element = layui.element;
        //计算住宅数量
        layui.each(bdcYxYwxxDTOList, function(i, bdcList){
            var num = 0;
            layui.each(bdcList.yxBdcdyAzfzDTOList, function(i, List){
                num += List.bdcYxYwxxDTOList.length
            });
            bdcList.zzhNum = num;
        });
        var json = {
            data: bdcYxYwxxDTOList,
            zdDjxl: zdDjxlList,
            zdQllx: zdQllxList
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
        form.render();
        element.render();

    })
}

function queryQlrsjly(qlrsjly) {
    var qlrsjlyVal = "";
    if (isNotBlank(qlrsjly)) {
        if (qlrsjly == 1) {
            qlrsjlyVal = "权籍"
        }
        if (qlrsjly == 2) {
            qlrsjlyVal = "上一手权利人"
        }
        if (qlrsjly == 3) {
            qlrsjlyVal = "上一手义务人"
        }
    }
    return qlrsjlyVal;
}

function queryQlsjly(qlsjly) {
    var qlrsjlyVal = "";
    if (isNotBlank(qlsjly)) {
        if (qlsjly == "1") {
            qlrsjlyVal = "权籍"
        }
        if (qlsjly == "2") {
            qlrsjlyVal = "上一手"
        }
        if (qlsjly == "1,2") {
            qlrsjlyVal = "权籍+上一手"
        }
    }
    return qlrsjlyVal;
}


function querySf(dm, csz) {
    var value = csz;
    if (typeof dm === "number") {
        if (dm == 0) {
            value = "否"
        }
        if (dm == 1) {
            value = "是"
        }
    }
    return value;
}

function queryZszl(zslx) {
    var zslxva = "";
    if (isNotBlank(zslx)) {
        if (zslx == 1) {
            zslxva = "证书"
        }
        if (zslx == 2) {
            zslxva = "证明"
        }
    }
    return zslxva;
}


function deleteAll(xmid) {
    addModel();
    $.ajax({
        url: getContextPath() + '/gwc/deleteYxxm',
        data: {onexmid: xmid},
        success: function (data) {
            removeModal();
            ityzl_SHOW_SUCCESS_LAYER('删除成功');
            $('#queryBdcdyh').click();
            parent.addGwc();
        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
        }
    });

}

function queryDjxl(djxl) {
    var djxlDm = parseInt(djxl);
    for (var i = 0; i < zdDjxlList.length; i++) {
        if (djxlDm === zdDjxlList[i].DM) {
            djxl = zdDjxlList[i].MC;
        }
    }
    return djxl;
}

function queryQllx(qllx) {
    var qllxDm = parseInt(qllx);
    for (var i = 0; i < zdQllxList.length; i++) {
        if (qllxDm === zdQllxList[i].DM) {
            qllx = zdQllxList[i].MC;
        }
    }
    return qllx;
}

function saveZsxh(currentClassify) {
    var flag = 0;
    var zsxhSpanArray = currentClassify.find('.bdc-ryzhzs');
    if (zsxhSpanArray != null && zsxhSpanArray.length > 0) {
        var slxmList = [];
        for (var i = 0; i < zsxhSpanArray.length; i++) {
            zsxh++;
            var zsxhSpan = zsxhSpanArray[i];
            //var zsxh = $(zsxhSpan).attr("data-certificate-index");
            $(zsxhSpan).find(".reverse-drag").each(function () {
                var slxm = {};
                slxm.zsxh = zsxh;
                slxm.xmid = this.value;
                slxmList.push(slxm);
            });

        }
        //console.log(slxmList);
        $.ajax({
            url: getContextPath() + "/gwc/slxm/list",
            type: 'PATCH',
            dataType: 'json',
            async: false,
            contentType: "application/json",
            data: JSON.stringify(slxmList),
            success: function (data) {
                flag = data;
            }, error: function (xhr, status, error) {
                removeModal();
                delAjaxErrorMsg(xhr);
            }
        });
    }
    return flag;
}



function showXzxx(yzResult) {
    // removeModal();
    if (yzResult.length > 0) {
        loadTsxx(yzResult);
        layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
            var $ = layui.jquery,
                layer = layui.layer;
            warnLayer = layer.open({
                type: 1,
                title: '提示信息',
                skin: 'bdc-tips-right',
                shade: [0],
                area: ['600px'],
                offset: 'r', //右下角弹出
                time: 5000000, //2秒后自动关闭
                anim: 2,
                content: $('#tsxx').html(),
                success: function () {
                    $('.layui-layer-ico .layui-layer-close .layui-layer-close1').on('click', function () {
                        layer.close(warnLayer);
                        generate();
                    })
                },
                cancel: function () {
                    layer.close(warnLayer);
                    generate();
                }
            });
        });
    }

}

