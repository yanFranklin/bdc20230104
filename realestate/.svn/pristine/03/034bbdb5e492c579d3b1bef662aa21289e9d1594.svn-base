/**
 * 匹配台账js
 */
layui.use(['table', 'form', 'layer'], function () {
    var table = layui.table,
        form = layui.form,
        $ = layui.jquery,
        layer = layui.layer;
});

/**
 * 列表加载完成回调事件
 * @param res 当前查询结果
 */
function tableHasLoad(res) {
    //处理落宗状态，匹配状态，限制状态的值
    if (res && res.content) {
        res.content.forEach(function (v, index) {
            $(".layui-table-body tr[data-index='" + index + "']")
                .find("td[data-field='LZZT']")
                .find("div")
                .html(getLzZt(v.LZZT));

            $(".layui-table-body tr[data-index='" + index + "']")
                .find("td[data-field='PZZT']")
                .find("div")
                .html(getPpZt(v.PZZT,v.XMID));

            $(".layui-table-body tr[data-index='" + index + "']")
                .find("td[data-field='XZZT']")
                .find("div")
                .html(getCqzZt(v.BDCDYH,v.QLLX,v.XMID,v.QJGLDM));
        });
    }
}

/*
* 获取落宗状态
* */
function getLzZt(LZZT) {
    var bdcdyZt = '';
    if (!isNullOrEmpty(LZZT)) {
        if (LZZT === "已落宗") {
            bdcdyZt += '<span class="bdc-ylz">已落宗</span>'
        } else {
            bdcdyZt += '<span class="bdc-wlz">未落宗</span>';
        }
    } else {
        bdcdyZt += '';
    }
    return bdcdyZt
}

/*
* 获取匹配状态
* */
function getPpZt(PZZT, xmid) {
    var bdcdyZt = '';
    if (!isNullOrEmpty(PZZT)) {
        if (PZZT === "已匹配") {
            bdcdyZt += '<span class="bdc-ypp">已匹配</span>'
        } else {
            bdcdyZt += '<span class="bdc-wpp">未匹配</span>';
        }
    } else {
        bdcdyZt += '';
    }
    return bdcdyZt
}

/*
return getCqzZt(obj.bdcdyh,obj.qllx,obj.xmid,obj.qjgldm);
* 获取限制状态
* */
function getCqzZt(bdcdyh, qllx, zsxmid, qjgldm) {
    var bdcdyZt = '';
    $.ajax({
        url: "/realestate-accept-ui/bdcdyh/queryBdcqzZt?bdcdyh=" + bdcdyh + "&qllx=" + qllx + "&zsxmid=" + zsxmid + "&qjgldm=" + qjgldm,
        type: 'GET',
        dataType: 'json',
        async: false,
        success: function (data) {
            if (data !== null) {
                if (data.yg) {
                    bdcdyZt += '<span class="bdc-yg">已预告</span>';
                }
                if (data.ycf) {
                    bdcdyZt += '<span class="bdc-ycf">预查封</span>';
                }
                if (data.ydya) {
                    bdcdyZt += '<span class="bdc-ydy">预抵押</span>';
                }
                if (data.cf) {
                    bdcdyZt += '<span class="bdc-cf">查封</span>';
                }
                if (data.dya) {
                    bdcdyZt += '<span class="bdc-dya">抵押</span>';
                }
                if (data.yy) {
                    bdcdyZt += '<span class="bdc-yy">异议</span>';
                }
                if (data.sd) {
                    bdcdyZt += '<span class="bdc-sd">锁定</span>';
                }
                if (data.dyi) {
                    bdcdyZt += '<span class="bdc-dy">地役</span>';
                }
                if (data.zjgcdy) {
                    bdcdyZt += '<span class="bdc-zjgcdy">在建工程抵押</span>';
                }
                if (data.jzq) {
                    bdcdyZt += '<span class="bdc-jzq">居住</span>';
                }
                if (data.fwcq) {
                    bdcdyZt += '<span class="bdc-fwcq">房屋拆迁</span>';
                }
                if (data.ks) {
                    bdcdyZt += '<span class="bdc-ks">可售</span>';
                }
                if (data.ys) {
                    bdcdyZt += '<span class="bdc-ys">已售</span>';
                }
                if (data.xjspfks) {
                    bdcdyZt += '<span class="bdc-xjspfks">新建商品房可售</span>';
                }
                if (data.xjspfys) {
                    bdcdyZt += '<span class="bdc-xjspfys">新建商品房已售</span>';
                }
                if (data.clfks) {
                    bdcdyZt += '<span class="bdc-clfks">存量房可售</span>';
                }
                if (data.clfys) {
                    bdcdyZt += '<span class="bdc-clfys" >存量房已售</span>';
                }
                if (!data.yg && !data.ycf && !data.ydya && !data.cf && !data.dya && !data.yy
                    && !data.sd && !data.dyi && !data.zjgcdy && !data.ks && !data.ys && !data.xjspfks && !data.xjspfys && !data.clfks && !data.clfys && !data.jzq) {
                    bdcdyZt += '<span class="bdc-normal">正常</span>';
                }
            } else {
                bdcdyZt += '<span class="bdc-normal">正常</span>';
            }
        }, error: function (xhr, status, error) {
        }
    });
    return bdcdyZt
}

//匹配界面
function ppjm(obj, objdata) {
    var ppzt = 0;
    if(objdata.PZZT == "已匹配"){
        ppzt = 1;
    }
    var index = layer.open({
        type: 2,
        title: "数据匹配",
        area: ['1150px', '80%'],
        fixed: false, //不固定
        maxmin: true, //开启最大化最小化按钮
        content: '/realestate-accept-ui/view/query/matchData.html?lx=check&ppzt='+ ppzt,
        success: function (layero, index) {
            var list = [];
            list.push(objdata);
            sessionStorage.setItem('matchData', JSON.stringify(list));
        },
        cancel: function (layero, index) {

        }
    });
    layer.full(index);
}