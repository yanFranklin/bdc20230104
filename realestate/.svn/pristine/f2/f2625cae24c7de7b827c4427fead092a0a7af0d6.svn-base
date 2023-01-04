/**
 * 附件台账js
 */
var gzlslid = getQueryString("gzlslid");
layui.use(['table', 'form', 'layer'], function () {
    var table = layui.table,
        form = layui.form,
        $ = layui.jquery,
        layer = layui.layer;
    if(!isNullOrEmpty(gzlslid)) {
        //查询不动产单元号
        getXm(gzlslid);
        //form.render();
    }
});

function getXm(gzlslid) {
    $.ajax({
        url: "/realestate-inquiry-ui/fjtz/current/xm/" + gzlslid,
        type: 'GET',
        dataType: 'json',
        async: true,
        success: function (data) {
            if (data !== null) {
                $("#bdcdyh").val(data.bdcdyh);
            }
        }, error: function (xhr, status, error) {
        }
    });
}
/**
 * 列表加载完成回调事件
 * @param res 当前查询结果
 */
function tableHasLoad(res) {
    //展示挂接按钮
    $("#fjgj").removeClass("layui-btn-disabled");
}


//附件挂接
function fjgj(obj, objdata) {
    // 参数没有值，说明是勾选的批量解封
    if(checkeddata.length == 0){
        warnMsg('请勾选需要挂接的数据！');
        return;
    }
    var index = layer.open({
        type: 2,
        title: "附件挂接",
        area: ['1150px', '80%'],
        fixed: false, //不固定
        maxmin: true, //开启最大化最小化按钮
        content: '/realestate-accept-ui/view/fjtz/fjgj.html?processInsId=' + gzlslid +'&wjzxid=' + checkeddata[0].WJZXID,
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

//新增附件
function xzfj() {
    var index = layer.open({
        type: 2,
        title: "附件新增",
        area: ['850px', '60%'],
        fixed: false, //不固定
        maxmin: true, //开启最大化最小化按钮
        content: '/realestate-accept-ui/view/fjtz/addFjtz.html',
        success: function (layero, index) {

        },
        cancel: function (layero, index) {

        }
    });
    // layer.full(index);
}

//详情页面
function xq(obj, objdata) {
    viewWjglFj(objdata.WJZXID,"clientId",null,"附件详情",2,true);
}