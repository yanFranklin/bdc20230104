/**
 * @author <a href ="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @description 历史遗留问题台账js
 */
var BASE_URL = '/realestate-inquiry-ui/rest/v1.0/wtsj';

//解决按钮
function jjBdc() {
    if (!checkeddata || checkeddata.length === 0) {
        layer.alert("请选择需要编辑记录！", {title: '提示'});
        return;
    }

    for (var i = 0; i < checkeddata.length; i++){
        if(checkeddata[i]['WTSJZT']=='2'){
            warnMsg("选中数据中存在已解决数据，请重新选择！");
            return false;
        }
    }
    showJjfa();
}

//弹出解决方案
function showJjfa() {

    var div = " <div id=\"bdc-popup-textarea\"><form class=\"layui-form\" action=\"\">" +
        "            <div class=\"layui-form-item pf-form-item\">" +
        "                <div class=\"layui-inline\">" +
        "                    <label class=\"layui-form-label\"><span class=\"required-span\"><sub>*</sub></span>解决方案</label>" +
        "                    <div class=\"layui-input-inline bdc-end-time-box\">" +
        "                        <textarea name=\"jjfaNr\" id='jjfaNr' placeholder=\"请输入内容\" class=\"layui-textarea\"></textarea>" +
        "                    </div>" +
        "                </div>" +
        "            </div>" +
        "        </form>" +
        "    </div>";
    //小弹出层
    layer.open({
        title: '解决方案',
        type: 1,
        area: ['430px'],
        btn: ['提交', '取消'],
        content: div
        , yes: function (index, layero) {
            //提交 的回调
            jj(index);
        }
        , btn2: function (index, layero) {
            //取消 的回调
            layer.close(index)
        }
        , cancel: function (index) {
            //右上角关闭回调
            //return false 开启该代码可禁止点击该按钮关闭
            layer.close(index)
        }
        , success: function () {

        }
    });
}

function jj(index) {
    var wtsjxx = [];
    var jjfa = $('#jjfaNr').val();
    if(isNullOrEmpty(jjfa)){
        warnMsg("解决方案不能为空！");
        return false;
    }
    $.each(checkeddata, function (i, item) {
        var wtsj= {};
        wtsj.wtsjid = item['WTSJID'];
        wtsj.wtsj = item['WTSJ'];
        wtsjxx.push(wtsj);

    });

    if (wtsjxx.length != 0) {
        $.ajax({
            url: BASE_URL + '/jj?jjfa=' + encodeURI(jjfa),
            type: "POST",
            data: JSON.stringify(wtsjxx),
            contentType: 'application/json',
            dataType: "json",
            async: false,
            success: function (data) {
                saveSuccessMsg();
                layui.table.reload('pageTable', {page: {curr: 1}});
                layer.close(index);
            },
            error: function (e) {
                saveFailMsg();
            }, complete: function () {
            }
        });
    }
}

//新增不动产单元
function addBdcdy() {
    layer.open({
        title: '不动产单元列表',
        type: 2,
        area: ['1300px', '450px'],
        content: '../wtsj/bdcdyList.html'
        , cancel: function (index, layero) {
            layui.table.reload('pageTable', {page: {curr: 1}});
            parent.layer.close(index);
        }
        , success: function () {

        }
    });
}

//新增不动产证书
function addBdczs() {
    layer.open({
        title: '不动产证书列表',
        type: 2,
        area: ['960px', '450px'],
        content: '../wtsj/bdczsList.html'
        , cancel: function (index, layero) {
            layui.table.reload('pageTable', {page: {curr: 1}});
            parent.layer.close(index);
        }
        , success: function () {

        }
    });
}

//详细信息
function wtxx(obj,data){
    layer.open({
        title: '修改问题数据',
        type: 2,
        content: "/realestate-inquiry-ui/view/wtsj/wtsjEdit.html",
        area: ['960px', '360px'],
        success: function(layero,index){
            var iframeWin = window[layero.find('iframe')[0]['name']];
            iframeWin.renderForm(data);
        },
        cancel: function (){
            layui.table.reload('pageTable', {page: {curr: 1}});
        }
    });


}


