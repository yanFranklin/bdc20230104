layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    var element = layui.element;
    var layer = layui.layer;
    var form = layui.form;

    form.on('select(hyzk)', function (data) {
        var $qlrbasic = $(data.elem).parents("tr");
        var sqrid = $qlrbasic.find("input[name='sqrid']").val();
        var sqrlb = $qlrbasic.find("input[name='sqrlb']").val();
        if (data.value == "已婚") {
            openJtcyYm(sqrid, sqrlb);
        }else if(data.value =="未婚"){
            //清除家庭成员信息
            deleteAllJtcy(sqrid);
        }
    });

    //监听行政区划选择
    form.on('select(xzqh)', function (data) {
        jddmDz(data.value, data.elem);
        form.render('select');
        resetSelectDisabledCss();
    });
});

//加载交易与税收tab内容
function generateJyss(data) {

    //加载卖方信息
    generateCmfxx(data[0].bdcSlZcfDTOList);

    //加载买方信息
    generateMsfxx(data[0].bdcSlZrfDTOList);

    //加载房屋交易信息
    generateFwjyxx(data);

    //加载房屋信息模块
    generateFwxx(data);

    //加载承受方税款信息模块
    generateMsfSwxx(data[0], "1");
    //二手房展示卖方信息
    if (fwfclx === "clf") {
        generateMsfSwxx(data[0], "2");
    }
}

function reloadFwsJyxx(){
    $.ajax({
        url: getContextPath() + "/ycsl/pl",
        type: 'GET',
        dataType: 'json',
        async: false,
        data: {gzlslid: processInsId},
        success: function (data) {
            //获取当前受理项目
            var tabSlxm ={};
            var tabSlxmList = [];
            bdcSlXmList.forEach(function (v) {
                if (v.xmid == tabxmid) {
                    tabSlxm =v;
                    tabSlxmList.push(tabSlxm);
                }
            });
            // 初始化加载权利信息和不动产单元信息
            generateQlxxAndBdcdyxx(data, $nowTab, tabSlxm, bdcqllxSlXm);
            // 加载交易与税收tab标签页
            generateJyss(data);

            form.render();
            renderSelect();
            getStateById(readOnly, formStateId, 'ycym');
            form.render("select");
            disabledAddFa();
            renderDate(form);
            removeModal();
            reloadZjhYz();
        }
    });
}

//加载卖方信息模块
function generateCmfxx(sqrList) {
    if (fwfclx === "clf") {
        //二手房卖方模板
        var tpl = zrfzcfxxTpl.innerHTML;

    } else {
        //商品房卖方模板
        var tpl = kfsxxTpl.innerHTML;
    }
    var view = document.getElementById('zlfzcfxx');
    var json = {
        msfcmfxxList: sqrList,
        zd: zdList,
        sqrlb: 2
    };
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });

}

//加载买方信息模块
function generateMsfxx(sqrList) {
    var tpl = zrfzcfxxTpl.innerHTML,
        view = document.getElementById('zlfzrfxx');
    var json = {
        msfcmfxxList: sqrList,
        zd: zdList,
        sqrlb: 1
    };
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });

}

//加载房屋交易模块
//function generateFwjyxx(jyxx, fwxx, xmxx) {
function generateFwjyxx(data){
    var tpl = fwjyxxTpl.innerHTML,
        view = document.getElementById('fwjyxx');
    var json = {
        data: data,
        zd: zdList
    };
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });

}

//加载房屋信息模块
function generateFwxx(data) {
    var tpl = fwxxTpl.innerHTML,
        view = document.getElementById('fwxx');
    var json = {
        data: data,
        zd: zdList
    };
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });

    // 同步加载房屋类型，房屋性质
    var fwlx = $('#qlxxTab').find("select[name='fwlx']").val(),
        fwxz = $('#qlxxTab').find("select[name='fwxz']").val();
    $(".swxx #fwxx").find("select[name='fwlx']").val(fwlx);
    $(".swxx #fwxx").find("select[name='fwxz']").val(fwxz);
    form.render("select");

}

//加载发票信息模块
function generateFpxx(swxxList, jyxx) {
    var tpl = fpxxTpl.innerHTML,
        view = document.getElementById('fpxx');
    var hsxx = {};
    if (swxxList != null && swxxList.length > 0) {
        var swxxDTO = swxxList[0];
        if (swxxDTO) {
            hsxx = swxxDTO.bdcSlHsxxDO
        }

    }
    var json = {
        zd: zdList,
        hsxx: hsxx,
        jyxx: jyxx
    };
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });

}

//加载承受方税款信息模块
function generateMsfSwxx(data, qlrlb) {
    var swxxList;
    if (qlrlb === "2") {
        swxxList = data.bdcZcfSwxxList;
    } else {
        swxxList = data.bdcZrfSwxxList
    }
    var tpl = csfskxxTpl.innerHTML,
        view = document.getElementById('csfskxx');
    if (qlrlb === "2") {
        view = document.getElementById('msfskxx');
    }
    var swxxDTO = {};
    var jexj = {
        ynsexj: 0,
        jmskxj: 0,
        jzexj: 0,
        sjnse: 0,
        allynsexj: 0,
        alljmskxj: 0,
        alljzexj: 0,
        allsjnse: 0
    };
    if (swxxList != null && swxxList.length > 0) {
        swxxDTO = swxxList[0];

        var swxx = swxxDTO.bdcSlHsxxMxDOList;
        if (swxx) {
            for (var i = 0; i < swxx.length; i++) {
                jexj.ynsexj += swxx[i].ynse;
                jexj.jmskxj += swxx[i].cqbczsjmsk;
                jexj.jzexj += swxx[i].zzsxgmnsrjze;
                jexj.sjnse += swxx[i].sjnse;
                jexj.allynsexj += swxx[i].ynse;
                jexj.alljmskxj += swxx[i].cqbczsjmsk;
                jexj.alljzexj += swxx[i].zzsxgmnsrjze;
                jexj.allsjnse += swxx[i].sjnse;
            }
        }
    }
    //如果是转出方，要计算出转出方和转入方  双方的金额小计
    if (qlrlb === "2") {
        //转入方的税务信息
        if (data.bdcZrfSwxxList != null && data.bdcZrfSwxxList.length > 0) {
            var zrfSwxx = data.bdcZrfSwxxList[0].bdcSlHsxxMxDOList;
            if (zrfSwxx) {
                for (var j = 0; j < zrfSwxx.length; j++) {
                    jexj.allynsexj += zrfSwxx[j].ynse;
                    jexj.alljmskxj += zrfSwxx[j].cqbczsjmsk;
                    jexj.alljzexj += zrfSwxx[j].zzsxgmnsrjze;
                    jexj.allsjnse += zrfSwxx[j].sjnse;
                }
            }
        }
    }
    var json = {
        zd: zdList,
        swxxDTO: swxxDTO,
        jexj: jexj,
        qlrlb: qlrlb,
        fwfclx:fwfclx
    };
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });

}

function reloadFwJyxx(){
    $.ajax({
        url: getContextPath() + "/ycsl/pl",
        type: 'GET',
        dataType: 'json',
        async: false,
        data: {gzlslid: processInsId},
        success: function (data) {
            //加载房屋交易信息
            generateFwjyxx(data);
            //加载房屋信息模块
            generateFwxx(data);

            form.render();
            renderSelect();
            getStateById(readOnly, formStateId, 'ycym');
            form.render("select");
            disabledAddFa();
            renderDate(form);
            removeModal();
            reloadZjhYz();
        }
    });
}

/**
 * 保存交易税收页签的申报房屋套次字段
 */
function saveBdcSlZrfZcf(className) {
    var bdcSlSqrData = [];
    var sqrlist = [];
    if ($(className).length != 0) {
        bdcSlSqrData = $(className).serializeArray();
        var bdcSlSqrSingleData = {};
        for (var j = 0; j < bdcSlSqrData.length; j++) {
            var name = bdcSlSqrData[j].name;
            bdcSlSqrSingleData[name] = bdcSlSqrData[j].value;
            // 以qlrid为每一组权利人的起始数据，作为分割依据
            if ((j + 1 < bdcSlSqrData.length && bdcSlSqrData[j + 1].name === 'sqrid') || j + 1 == bdcSlSqrData.length) {
                sqrlist.push(bdcSlSqrSingleData);
                bdcSlSqrSingleData = {};
            }
        }
    } else {
        // 没有申请人 认为是成功的
        return true;
    }
    console.log(sqrlist);
    var count = saveAllSqrs(sqrlist);
    // 更新的数量 == 页面的数量 则说明更新成功
    if (bdcSlSqrData.length == count) {
        return true;
    } else {
        return false;
    }

}

//民政婚姻信息比对
function compareHyxx(elem) {
    // addModel();

    var slbh = $("#slbh").val();
    var $qlrbasic = $(elem).parents("tr");
    var qlrmc = $qlrbasic.find("#zrzcf-sqrmc").val();
    var qlrzjh = $qlrbasic.find("#zrzcf-zjh").val();
    var sqrid = $qlrbasic.find("input[name='sqrid']").val();
    var hyzk = $qlrbasic.find("#zrzcf-hyzk").val();
    if (!isNotBlank(qlrmc) || !isNotBlank(qlrzjh) || !isNotBlank(sqrid) || !isNotBlank(hyzk)) {
        ityzl_SHOW_WARN_LAYER("申请人名称或证件号或婚姻状况不能为空！");
        return false;
    }
    if(hyzk ==="已婚"){
        //验证是否填写配偶信息
        if(!checkPoxx(sqrid)){
            ityzl_SHOW_WARN_LAYER("请补充配偶及家庭成员信息！");
            return false;
        }
    }
    layer.open({
        type: 2,
        title: "婚姻比对信息",
        area: ['960px', '550px'],
        content: "/realestate-accept-ui/nantong/ycsl/hybdjg.html?sqrid=" + sqrid + "&qlrmc=" + encodeURI(qlrmc) + "&qlrzjh=" + qlrzjh
            + "&hyzt=" + encodeURI(hyzk) + "&gzlslid=" + processInsId
        , end: function (index, layero) {
            return false;
        }
        ,cancel: function(index,layero){
            var resultcode =layer.getChildFrame('body', index).find("input[name='resultcode']").val();
            var hyxxbdjg =$qlrbasic.find("input[name='hyxxbdjg']").val();
            //比对通过
            if(resultcode ==="0000" &&hyxxbdjg != "1") {
                loadYcslxx($('.layui-tab-item:nth-child(' + 2 + ')').data("xmid"),$('.layui-tab-item:nth-child(' + 2 + ')'));

            }

        }
    });
}

//查询数据库判断是否存在配偶
function checkPoxx(sqrid) {
    var hasPoxx =false;
    getReturnData("/slym/jtcy/listBdcSlJtcy",{sqrid:sqrid,ysqrgx:"配偶"},"GET",function (data) {
        if(data &&data.length >0){
            hasPoxx =true;
        }

    },function (error) {
        delAjaxErrorMsg(error);

    },false);
    return hasPoxx;

}

//清除家庭成员
function deleteAllJtcy(sqrid) {
    getReturnData("/slym/jtcy/all?sqrid="+sqrid,{},"DELETE",function (data) {
    },function (error) {
        delAjaxErrorMsg(error);

    });

}

//打开家庭成员页面
function openJtcyYm(sqrid, sqrlb) {
    var url = getContextPath() + "/view/ycsl/jtcy.html?sqrid=" + sqrid + "&formStateId=" + formStateId + "&sqrlb=" + sqrlb;
    layer.open({
        title: '配偶子女信息',
        type: 2,
        area: ['960px', '400px'],
        content: url
        , cancel: function () {
            //右上角关闭回调
            //return false 开启该代码可禁止点击该按钮关闭
        }
        , success: function () {

        }
    });

}

// 行政区划与街道代码对照
function jddmDz(xzqh, ele) {
    var jddmList = zdList.jddm;
    // 获取行政区划对应街道代码
    if (isNotBlank(jddmList)) {
        $(ele).parents(".inner-fwjyxx").find('#jyss-jddm option').remove();
        var $jddm =  $(ele).parents(".inner-fwjyxx").find('#jyss-jddm');
        $jddm.append("<option value=''>请选择</option>");
        for (var i = 0; i < jddmList.length; i++) {
            var zdjddm = jddmList[i];
            if (zdjddm.DM.length >= 6) {
                var jddmDz = zdjddm.DM.substring(0, 6);
                if (jddmDz === xzqh) {
                    $jddm.append("<option value='" + zdjddm.DM + "' title='" + zdjddm.MC + "'>" + zdjddm.MC + "</option>");
                }
            }
        }
    }
}

/**
 * 合同备案共那个
 * @param sczt 审查状态  1 通过 0 不通过
 */
function htba(sczt) {
    $("#btg-reason").val('');
    $("#jyba-hth").val('');
    var hegiht = "250px";
    if (sczt === 1) {
        hegiht = "150px";
    }
    layer.open({
        title: '合同备案',
        type: 1,
        area: ['430px', hegiht],
        btn: ['确定', '取消'],
        content: $('#htba-layer'),
        success: function (layero, index) {
            $('#jyba-hth').focus(); //自动获取焦点
            if (sczt === 1) {
                $("#btg-reason").parents(".layui-inline").hide();
            } else {
                $("#btg-reason").parents(".layui-inline").show();
            }
            var htbh = $("#jyss-htbh").val();
            if (isNotBlank(htbh)) {
                $("#jyba-hth").val(htbh);
            }
        },
        yes: function (index, layero) {
            var reason = $("#btg-reason").val();
            if (sczt === 0 && isNullOrEmpty(reason)) {
                layer.msg('请输入原因!');
                return false;
            }
            addModel();
            $.ajax({
                url: getContextPath() + "/ycsl/jyxx/ts/htba",
                type: 'POST',
                dataType: 'json',
                data: {htbh: $("#jyba-hth").val(), reason: reason, sczt: sczt, xmid: xmid},
                success: function (data) {
                    removeModal();
                    layer.close(index);
                    if (data === 0) {
                        ityzl_SHOW_SUCCESS_LAYER("备案成功");
                    } else if (data === 1) {
                        ityzl_SHOW_WARN_LAYER("备案失败");
                    } else {
                        ityzl_SHOW_WARN_LAYER("无网签合同信息，请确认是否网签");
                    }
                },
                error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr);
                }
            });
        },
        btn2: function (index, layero) {
            layer.close(index);
        }
    });


}

//电子签字
function getQzxx(qzrlb, bdlx) {
    layer.open({
        title: '合同签字',
        type: 2,
        area: ['700px', '430px'],
        content: "/realestate-accept-ui/view/ycsl/qzb.html?xmid=" + xmid + "&bdlx=" + bdlx + "&qzrlb=" + qzrlb
        , cancel: function () {
        }
        , success: function () {
        }
    });
}



