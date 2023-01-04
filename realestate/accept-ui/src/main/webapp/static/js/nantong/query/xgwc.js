var gwcData;
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
            var tab =$('.layui-this').val();
            var area = ['960px', '600px'];
            if (tab !== 1 || tab !== 5) {
                // modified by lixin 南通购物车需要额外显示一列内容，扩展宽度（排除 选择 bdcdyh 和 ljz 的）
                area = ['1180px', '600px'];
            }
            var $gwclayer = $(document).find('.bdc-gwc-layer');
            if($gwclayer.length == 0){
                var url = getContextPath() + '/view/query/gwc.html?jbxxid=' + jbxxid + '&gzldyid=' + processDefKey+"&zlcsh="+zlcsh+"&slbh="+slbh + '&processDefKey=' + processDefKey;
                layer.open({
                    type: 2,
                    title: "已选信息",
                    skin: 'bdc-gwc-layer',
                    area: area,
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
            var tab =$('.layui-this').val();
            var area = ['960px', '600px'];
            if (tab !== 1 || tab !== 5) {
                // modified by lixin 南通购物车需要额外显示一列内容，扩展宽度（排除 选择 bdcdyh 和 ljz 的）
                area = ['1180px', '600px'];
            }
            var url = getContextPath() + '/view/query/gwc.html?jbxxid=' + jbxxid + '&gzldyid=' + processDefKey+"&zlcsh="+zlcsh +"&slbh="+slbh + '&processDefKey=' + processDefKey;
            layer.open({
                type: 2,
                title: "已选信息",
                skin: 'bdc-gwc-layer',
                area: area,
                fixed: false, //不固定
                content: url
            });
        });

        //单击购物车表格中的初始化
        $('#csh').on('click', function (event) {
            var $this = $(this);
            createCsh($this);
        });

        //单击购物车表格中的清空
        $('#clearGwc').on('click', function (event) {
            var xmcount =$(".bdc-car").find("span")[0].textContent;
            if(xmcount ==="0"){
                layer.alert("无数据！");
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

function addGwc(isFirst) {
    //判断是否增量初始化
    var zlcsh = getQueryString("zlcsh");
    if(zlcsh ==="true"){
        var gzlslid = getQueryString("gzlslid");
        getReturnData("/bdcdyh/queryParams",{gzlslid:gzlslid},"GET",function (data) {
            processDefKey =data.processDefKey;
            jbxxid =data.jbxxid;

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
                gwcData = data;
                generateGwc(data,isFirst);
                if(data.length ===0){
                    //刷新楼盘表页面
                    var $iframe;
                    var iframes = $("iframe");
                    if(iframes.length > 0){
                        for(var j = 0 ;j<iframes.length ;j++){
                            if($(iframes[j]).attr("src").indexOf("building-ui/lpb/view") > 0){
                                $iframe = $(iframes[j]);
                            }
                        }
                    }
                    if($iframe){
                        var child = $iframe[0].contentWindow;
                        if (typeof (eval(child.refreshCartIcon)) == "function") {
                            child.refreshCartIcon();
                        }
                    }
                }

            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

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
            page: {
                layout: ['limit', 'count', 'prev', 'page', 'next'],
                limits: [100, 200, 300, 400, 500],
                limit: 100
            },
            limit: 10000,
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
            console.log(obj);
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

function deleteBdcslxm(xmid) {
    $.ajax({
        url: getContextPath() + '/gwc/deleteYxxm',
        data: {onexmid: xmid},
        success: function (data) {
            ityzl_SHOW_SUCCESS_LAYER('删除成功');
            removeModal();
        },
        error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
        }
    });
}

//clearGwc 页面关闭刷新或点击清空按钮时clearGwc为1
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

//单击创建按钮
function createCsh($this){
    sfcj =true;
    $this.attr("disabled",true).css("pointer-events","none");
    var xmcount =$(".bdc-car").find("span")[0].textContent;
    if(xmcount ==="0"){
        layer.alert("未选择数据！");
        return false;
    }
    addModel();
    //验证选中数据而不是购物车中的数据
    $this.removeAttr("disabled").css("pointer-events","auto");
    var tab = $('.layui-this').val();
    //只有选择产权证时才回去验证匹配落宗状态
    if(tab=== 2) {
        getReturnData("/gwc/listGwcData", {jbxxid: jbxxid}, "GET", function (data) {
            checkPplzzt(data,true,tab);
        }, function (xhr) {
            removeModal();
            addGwc();
            delAjaxErrorMsg(xhr);
        })
    } else if(tab === 3) {
        getReturnData("/gwc/listGwcData", {jbxxid: jbxxid}, "GET", function (data) {
            sfcj = true;
            yzCfXnBdcdyh(data,'',true);
        }, function (xhr) {
            removeModal();
            addGwc();
            delAjaxErrorMsg(xhr);
        })
    } else {
        cshWithCjyz();
    }
}


function cshWithCjyz() {
    var bdcGzYzQO = {};
    var bdcGzYzsjDTO = {};
    var paramList = [];
    bdcGzYzsjDTO.jbxxid = jbxxid;
    paramList.push(bdcGzYzsjDTO);
    //创建前验证
    bdcGzYzQO.zhbs = processDefKey + "_CJYZ";
    bdcGzYzQO.paramList = paramList;
    getReturnData("/bdcGzyz",JSON.stringify(bdcGzYzQO),"POST",function (data) {
        if(data.length === 0) {
            if (plcjlc) {
                lcPlCsh(jbxxid);
            } else {
                cshSelectedXxSingle(jbxxid, processDefKey);
            }
        }else{
            removeModal();
        }
        //展现限制信息
        showXzxx(data,'cjyz');
    },function (xhr, status, error) {
        delAjaxErrorMsg(xhr)
    })
}