layui.use(['jquery', 'table', 'element', 'form', 'layer'], function () {
    var $ = layui.jquery,
        table = layui.table,
        element = layui.element,
        layer = layui.layer;

    $(function () {
        //鼠标移出购物车列表
        $('.bdc-gwc').on('mouseleave', function () {
            $(this).slideUp(300);
        });
        // 第一次鼠标覆盖圈圈，重置表格尺寸
        var enterIndex=  0;
        $('.bdc-car').on('click',function (event) {
            event.stopPropagation();
            $('.bdc-gwc').slideUp(300);
            var $gwclayer = $(document).find('.bdc-gwc-layer');
            if($gwclayer.length == 0){
                var url = getContextPath() + '/view/query/gwc.html?jbxxid=' + jbxxid + '&gzldyid=' + processDefKey+"&zlcsh="+zlcsh+"&slbh="+slbh;
                layer.open({
                    type: 2,
                    title: "已选信息",
                    skin: 'bdc-gwc-layer',
                    area: ['1080px', '600px'],
                    fixed: false, //不固定
                    content: url
                });
            }
        }).on('mouseenter',function () {
            enterIndex++;
            $('.bdc-gwc').slideDown(300);
            table.resize('gwcTable');
        });

        addGwc("isFirst");
        //判断是否增量初始化
        var zlcsh = getQueryString("zlcsh");
        //单击购物车表格中的去创建
        $('.bdc-go-gwc').on('click', function (event) {
            event.stopPropagation();
            $('.bdc-gwc').slideUp(300);
            var url = getContextPath() + '/view/query/gwc.html?jbxxid=' + jbxxid + '&gzldyid=' + processDefKey+"&zlcsh="+zlcsh +"&slbh="+slbh;
            layer.open({
                type: 2,
                title: "已选信息",
                skin: 'bdc-gwc-layer',
                area: ['1080px', '600px'],
                fixed: false, //不固定
                content: url
            });
        });

        //单击购物车表格中的初始化
        $('#csh').on('click', function (event) {
            var $this = $(this);
            $this.attr("disabled",true).css("pointer-events","none");
            var xmcount =$(".bdc-car").find("span")[0].textContent;
            if(xmcount ==="0"){
                layer.alert("未选择数据！");
                $this.removeAttr("disabled").css("pointer-events", "auto");
                return false;
            }
            addModel();
            var tab = $('.layui-this').val();
            cshSelectedXxSingle(jbxxid, processDefKey, false, true,tab);
            $this.removeAttr("disabled").css("pointer-events", "auto");
        });

        //单击购物车表格中的清空
        $('#clearGwc').on('click', function (event) {
            var xmcount =$(".bdc-car").find("span")[0].textContent;
            if(xmcount ==="0"){
                layer.alert("无数据！");
                return false;
            }
            addModel();
            var sfzlcsh =0;
            if(zlcsh ==="true") {
                sfzlcsh =1;
            }
            $.ajax({
                url: getContextPath() + '/gwc/deleteYxxm',
                data: {jbxxid: jbxxid,sfzlcsh:sfzlcsh,clearGwc:1},
                success: function (data) {
                    removeModal();
                    ityzl_SHOW_SUCCESS_LAYER('清空成功');
                    addGwc();
                },
                error: function (xhr, status, error) {
                    removeModal();
                    delAjaxErrorMsg(xhr);
                }
            });

        });

    });
});


/*
* 添加购物车
* @param sfcxjz 是否重新加载
* */
function addGwc(isFirst, isgzyz) {
    //判断是否增量初始化
    var zlcsh = getQueryString("zlcsh");
    if (zlcsh === "true" && (!isNotBlank(processDefKey) || !isNotBlank(jbxxid))) {
        var gzlslid = getQueryString("gzlslid");
        getReturnData("/bdcdyh/queryParams", {gzlslid: gzlslid}, "GET", function (data) {
            if (!isNotBlank(processDefKey)) {
                processDefKey = data.processDefKey;
            }
            if (!isNotBlank(jbxxid)) {
                jbxxid = data.jbxxid;
            }

        },function (error) {
            delAjaxErrorMsg(error);
        },false);
    }
    var url = getContextPath() + '/gwc/list/bdcslxm/removeduplicate?jbxxid=' + jbxxid;
    if(zlcsh ==="true") {
        url = url + "&sfzlcsh=1";
    }

    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            if (data != null) {
                generateGwc(data, isFirst);
                //刷新楼盘表页面
                var $iframe;
                var iframes = $("iframe");
                var child;
                if (iframes.length > 0) {
                    for (var j = 0; j < iframes.length; j++) {
                        if ($(iframes[j]).attr("src").indexOf("building-ui/lpb/view") > 0) {
                            $iframe = $(iframes[j]);
                        }
                    }
                }
                if ($iframe) {
                    child = $iframe[0].contentWindow;
                }
                if (data.length === 0 && !isgzyz) {
                    if (child && typeof (eval(child.refreshCartIcon)) == "function") {
                        child.location.reload();
                    }
                } else {
                    if (sfcxjzlpb) {
                        sfcxjzlpb = false;
                        child.location.reload();
                    }
                }
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

/*
* 组织购物车数据
* */
function generateGwc(gwcData,isFirst) {
    layui.use(['jquery', 'table', 'element', 'form', 'layer'], function () {
        var spanEl = $(".bdc-car").find("span")[0];
        $(spanEl).text(gwcData.length);
        if(gwcData.length >999) {
            $(".bdc-car>i").css("right","7px");
            $(".bdc-car span").css("width", "40px");
        }else{
            $(".bdc-car>i").css("right","15px");
            $(".bdc-car span").css("width", "32px");
        }
        var table = layui.table;
        table.render({
            elem: '#gwcTable',
            page: true,
            id: 'gwcTable',
            height: 380,
            limit: 10000,
            page: {
                layout: ['limit', 'count', 'prev', 'page', 'next'],
                limits: [100, 200, 300, 400, 500],
                limit: 100
            },
            cols: [[ //表头
                {type: 'numbers', title: '序号' ,width:70},
                {field: 'zl', title: '坐落'},
                {title: '', toolbar: '#gwccz', width: 60}
            ]],
            data: gwcData
        });
        if(isFirst !=="isFirst") {
            $('.bdc-car span').addClass('animateShow');
            setTimeout(function () {
                $('.bdc-car span').removeClass('animateShow');
            }, 5000);
        }

        //单击 购物车表格中 的 ×
        table.on('tool(gwcFilter)', function (obj) {
            var getId = obj.data.xmid;
            if (obj.event === 'delete-gwc') {
                deleteBdcslxm(getId);
                gwcData.forEach(function (v, i) {
                    if (v.xmid == getId) {
                        gwcData.splice(i, 1);
                        generateGwc(gwcData);
                        return;
                    }
                });
            }
        });
    });
}

/*
* 根据xmid删除购物车数据
* */
function deleteBdcslxm(xmid) {
    $.ajax({
        url: getContextPath() + '/gwc/deleteYxxm',
        data: {onexmid: xmid},
        success: function (data) {
            ityzl_SHOW_SUCCESS_LAYER('删除成功');
        },
        error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
        }
    });
}


/*
* 清空购物车
* clearGwc 页面关闭刷新或点击清空按钮时clearGwc为1
* */
function clearGwc(jbxxid,async,clearGwc){
    if (async === undefined) {
        async = true;
    }
    $.ajax({
        url: getContextPath() + '/gwc/deleteYxxm',
        data: {jbxxid: jbxxid,slbh:slbh,clearGwc:clearGwc},
        async: async,
        success: function (data) {
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });

}