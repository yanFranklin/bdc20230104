/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @description 不动产锁定
 */
var BASE_URL = '/realestate-inquiry-ui/rest/v1.0/';

/**
 * 新增
 */
function xg(obj, data) {
    var result = {
        bdcqzh : data.BDCQZH,
        hmdid : data.HMDID,
        hmdztlb : data.HMDZTLB,
        gzldymc : data.GZLDYMC,
        cjr : data.CJR,
        cjsj: data.CJSJ,
        cjyy: data.CJYY,
        ztmc: data.ZTMC,
        ztzjh: data.ZTZJH,
        bz: data.BZ
    };
    layer.open({
        title: '修改警示记录',
        type: 2,
        content: "/realestate-inquiry-ui/changzhou/sd/bljlEdit.html",
        area: ['960px', '360px'],
        success: function(layero,index){
            var iframeWin = window[layero.find('iframe')[0]['name']];
            iframeWin.renderForm(result);
        },
        cancel: function (){
            layui.table.reload('pageTable', {page: {curr: 1}});
        }
    });
}
/**
 * 删除
 */
function sc(obj, data) {
    var hmdidList = [];
    hmdidList.push(data.HMDID);
    console.info(hmdidList);
    addModel();
    $.ajax({
        url: getContextPath() + '/rest/v1.0/hmd/sc',
        type: "DELETE",
        data: JSON.stringify(hmdidList),
        contentType: 'application/json',
        dataType: "json",
        success: function (result) {
            removeModal();
            delSuccessMsg();
            layui.table.reload('pageTable',{page:{curr:1}});
        },
        error: function (e) {
            removeModal();
            delFailMsg();
        }
    });
}

function jsBdc() {
    if (!checkeddata || checkeddata.length == 0) {
        layer.alert("请选择需要编辑记录！", {title: '提示'});
        return;
    }

    for (var i = 0; i < checkeddata.length; i++){
        if(checkeddata[i]['HMDZT']==='正常'){
            warnMsg("选中数据中存在正常状态数据，请重新选择！");
            return false;
        }
    }
    showDialog();
}

function showDialog() {

    var div = " <div id=\"bdc-popup-textarea\"><form class=\"layui-form\" action=\"\">" +
        "            <div class=\"layui-form-item pf-form-item\">" +
        "                <div class=\"layui-inline\">" +
        "                    <label class=\"layui-form-label\"><span class=\"required-span\"><sub>*</sub></span>解锁原因</label>" +
        "                    <div class=\"layui-input-inline bdc-end-time-box\">" +
        "                        <textarea name=\"jsyy\" id='jsyyNr' placeholder=\"请输入内容\" class=\"layui-textarea\"></textarea>" +
        "                    </div>" +
        "                </div>" +
        "            </div>" +
        "        </form>" +
        "    </div>";
    //小弹出层
    layer.open({
        title: '解锁原因',
        type: 1,
        area: ['450px','200px'],
        btn: ['提交', '取消'],
        content: div
        , yes: function (index, layero) {
            //提交 的回调
            js(index);
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

function js(index) {
    var zsjsxx = new Array();
    var jsyy = $('#jsyyNr').val();
    $.each(checkeddata, function (i, item) {
        var obj = new Object();
        obj.hmdid = item['HMDID'];
        obj.bdcqzh = item['BDCQZH'];
        obj.jsyy = jsyy;
        zsjsxx.push(obj);
    });
    if(zsjsxx.length > 0){
        $.ajax({
            url: getContextPath() + '/rest/v1.0/hmd/js',
            type: "POST",
            data: JSON.stringify(zsjsxx),
            contentType: 'application/json',
            dataType: "json",
            async: false,
            success: function (data) {
                saveSuccessMsg();
            },
            error: function (e) {
                saveFailMsg();
            }, complete: function () {
                $('#jsyy').val('');
                layer.close(index);
                parent.layui.table.reload('pageTable', {page: {curr: 1}});
                parent.layer.close(index);
            }
        });
    }
}

function sdBdczs() {
    var index = layer.open({
        title: '不动产证书列表',
        type: 2,
        area: ['1150px', '450px'],
        content: '../../changzhou/sd/bdczsList.html'
        , cancel: function (index, layero) {
            layui.table.reload('pageTable', {page: {curr: 1}});
            parent.layer.close(index);
        }
        , success: function () {
        }
    });
    layer.full(index);
}

function loadComplete() {
    /**
     * 加载Table数据列表
     */
    var reverseList = ['BDCQZH'];

    reverseTableCell(reverseList);
    var getTd = $('.layui-table-view .layui-table td');
    for (var i = 0; i < getTd.length; i++) {
        if ($(getTd[i]).attr('data-field') == "SDZT") {
            var getTdCell = $(getTd[i]).find('.layui-table-cell');
            if (getTdCell[0].innerText == '锁定') {
                $(getTd[i]).children('div').empty();
                $(getTd[i]).children('div').append('<span class="bdc-sd">锁定</span>');
            }
        }
    }
    changeTrBackgroundExceptRight([
        {name: 'bdc-sd', color: '#333', background: '#a7afb6'}, true]);
}