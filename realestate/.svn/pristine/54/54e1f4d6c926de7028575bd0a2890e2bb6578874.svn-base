var alertSize = 0;
var confirmSize = 0;
//验证提示弹出层
var yztsxxLayer;
var gxbdcdyfwlx =false;
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    generate();
});

//匹配与取消匹配验证
function checkMatchData(bdcdyh, tdxmid, ppbs,qszt) {
    addModel();
    var selectDataList = [];
    var bdcGzYzsjDTO = {};
    var paramMap = {};
    paramMap.xnxmid = xnxmid;
    paramMap.bdcdyh = bdcdyh;
    paramMap.tdxmid = tdxmid;
    selectDataList.push(bdcGzYzsjDTO);
    var bdcGzYzQO = {};
    bdcGzYzQO.zhbs = ppbs;
    bdcGzYzQO.paramMap = paramMap;
    $.ajax({
        url: getContextPath() + '/bdcGzyz/qtyz',
        type: 'POST',
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(bdcGzYzQO),
        success: function (tsdata) {
            removeModal();
            if (tsdata.length > 0) {
                showTsxx(tsdata, ppbs, bdcdyh, tdxmid);
            } else {
                if (ppbs === "YZPPDYH") {
                    matchDyh(bdcdyh);

                } else if (ppbs === "YZQXPPDYH") {
                    dismatchDyh(bdcdyh);

                } else if (ppbs === "YZPPTDZ") {
                    matchTdz(tdxmid);

                }else if (ppbs === "YZPPTDZCZ") {
                    //常州土地证匹配,验证权属状态是否一致
                    matchCzTdz(tdxmid,qszt);

                } else if (ppbs === "YZQXPPTDZ") {
                    dismatchTdz(tdxmid);

                } else if (ppbs === "YZJCGLTDZ") {
                    disassociation(bdcdyh, tdxmid, xnxmid)
                }
            }

        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr);
            removeModal();
        }
    });
}

function showTsxx(tsdata, ppbs, bdcdyh, tdxmid) {
    gxbdcdyfwlx =false;
    $.each(tsdata, function (index, item) {
        if (item.yzlx === "confirm") {
            confirmSize++;
            if (isNotBlank(item.msg) && item.msg.indexOf("TDYPP") > -1) {
                var msg = item.msg.replace("TDYPP", "");
                $("#confirmInfo").append('<p><img src="../../static/lib/bdcui/images/warn.png" alt="">' + msg + '<a href="javascrit:;" onclick="checkTdz(this,\'' + tdxmid + '\')">是</a><a href="javascrit:;" onclick="remove(this);return false">否</a></p>');
            }else if(isNotBlank(item.msg) && item.msg.indexOf("GXBDCDYFWLX") > -1){
                var msg = item.msg.replace("GXBDCDYFWLX", "");
                $("#confirmInfo").append('<p><img src="../../static/lib/bdcui/images/warn.png" alt="">' + msg + '<a href="javascrit:;" onclick="updatebdcdyfwlx(this,\'' + ppbs + '\',\'' + bdcdyh + '\',\'' + tdxmid + '\')">是</a><a href="javascrit:;" onclick="remove(this,\'' + ppbs + '\',\'' + bdcdyh + '\',\'' + tdxmid + '\')">否</a></p>');
            } else {
                $("#confirmInfo").append('<p><img src="../../static/lib/bdcui/images/warn.png" alt="">' + item.msg + '<a href="javascrit:;" onclick="remove(this,\'' + ppbs + '\',\'' + bdcdyh + '\',\'' + tdxmid + '\')">忽略</a></p>');
            }

        } else if (item.yzlx === "alert") {
            alertSize++;
            $("#alertInfo").append('<p><img src="../../static/lib/bdcui/images/error.png" alt="">' + item.msg + '</p>');
        }
    });
    layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
        var $ = layui.jquery,
            layer = layui.layer;
        yztsxxLayer = layer.open({
            type: 1,
            title: '提示信息',
            skin: 'bdc-tips-right',
            // closeBtn: 0, //不显示关闭按钮
            shade: [0],
            area: ['600px'],
            offset: 'r', //右下角弹出
            time: 5000000, //2秒后自动关闭
            anim: 2,
            content: $('#tsxx').html(),
            success: function () {
                generate();
                $('.bdc-tips-right .bdc-right-tips-box .layui-icon-close').on('click', function () {
                    layer.close(yztsxxLayer);
                    generate();
                })
            },
            yse: function () {
                generate();
            },
            cancel: function () {
                layer.close(yztsxxLayer);
                generate();
            }
        });
    });
}

function generate() {
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var form = layui.form;
        var laytpl = layui.laytpl;
        var json = {};
        var tpl = tsxxTpl.innerHTML, view = document.getElementById("tsxx");
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
        form.render();
    })
}

function updatebdcdyfwlx(elem, ppbs, bdcdyh, tdxmid){
    gxbdcdyfwlx =true;
    remove(elem,ppbs,bdcdyh,tdxmid)
}


function remove(elem, ppbs, bdcdyh, tdxmid) {
    $(elem).parent().remove();
    confirmSize--;
    //在没有警告提示下创建
    if (alertSize === 0 && confirmSize === 0) {
        //不存在提示信息时，关闭提示弹出层
        layer.close(yztsxxLayer);
        if (ppbs === "YZPPDYH") {
            matchDyh(bdcdyh,gxbdcdyfwlx);
        } else if (ppbs === "YZQXPPDYH") {
            dismatchDyh(bdcdyh);
        } else if (ppbs === "YZPPTDZ") {
            matchTdz(tdxmid);
        } else if (ppbs === "YZQXPPTDZ") {
            dismatchTdz(tdxmid);
        } else if (ppbs === "YZJCGLTDZ") {
            disassociation(bdcdyh, tdxmid, xnxmid);
        }


    }

}

//解除关联关系
/**
 * @param bdcdyh
 * @param gltdxmid 土地的项目id
 * @param fwXmid 房屋的项目id
 * @param 规则验证标识
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @description 主要用于存在相同单元号的房屋和土地数据，
 * @date : 2021/12/30 14:15
 */
function disassociation(bdcdyh, tdXmid, fwXmid) {
    getReturnData("/matchData/qxgl", {gltdXmid: tdXmid, fwXmid: fwXmid, bdcdyh: bdcdyh}, "GET", function (data) {
        ityzl_SHOW_SUCCESS_LAYER("解除成功");
        //刷新table
        gltdxmid = "";
        initXmTable();
    }, function (xhr) {
        delAjaxErrorMsg(xhr)
    })
}

