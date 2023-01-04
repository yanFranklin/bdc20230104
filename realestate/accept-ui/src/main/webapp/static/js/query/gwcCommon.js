/**
 * 购物车通用处理js
 */
//页面状态
var state ="";
layui.use(['element', 'form', 'jquery', 'laydate', 'laypage', 'laytpl', 'layer'], function () {

    //外联证书
    $("#wlzs").on('click', function () {
        //获取选中数据
        var xmids = getSelectedData();
        if (xmids.length === 0) {
            showAlertDialog("未选择数据");
        } else {
            state = "wlzs";
            $(".bdc-yx-ybdcqz").show();
            $(".bdc-gwc-layer").css({"top": 0});
            var gwcindex = parent.layer.getFrameIndex(window.name);
            parent.layer.full(gwcindex);
            setTimeout(function () {
                //打开前先清除缓存数据
                sessionStorage.removeItem('wlzs_xmids' + jbxxid);
                //数据过多,存入缓存
                sessionStorage.setItem('wlzs_xmids' + jbxxid, xmids);
                //外联证书
                var url = '/realestate-accept-ui/view/query/selectBdcdyh.html?processDefKey=' + gzldyid + '&jbxxid=' + jbxxid + '&slbh=' + slbh + "&xztztype=wlzs";
                layer.open({
                    type: 2,
                    title: "外联证书",
                    area: ['960px', '550px'],
                    fixed: false, //不固定
                    maxmin: true, //开启最大化最小化按钮
                    content: url
                    , cancel: function () {
                        sessionStorage.removeItem('wlzs_xmids' + jbxxid);
                    }
                });
            }, 150);

        }

    });



});

//获取选中的xmid集合
function getSelectedData() {
    var xmids = [];
    //获取选中数据
    var yxlength = $(".bdc-yx-fx").find(".layui-form-checked").parents(".main-content-item").find("[name=xmid]").length;
    if (yxlength > 0) {
        $(".bdc-yx-fx").find(".layui-form-checked").parents(".main-content-item").find("[name=xmid]").each(function () {
            var xmid = $(this).val();
            if (isNotBlank(xmid)) {
                xmids.push(xmid);
            }
        });
    }
    return xmids;
}

//外联证书回调函数
function wlzsCallback(ymbz){
    //清空缓存
    sessionStorage.removeItem('wlzs_xmids'+jbxxid);
    //页面刷新
    $('#queryBdcdyh').click();
    parent.ityzl_SHOW_SUCCESS_LAYER("添加成功");

}