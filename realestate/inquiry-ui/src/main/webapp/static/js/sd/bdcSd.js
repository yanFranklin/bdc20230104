/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @description 不动产锁定
 */
var BASE_URL = '/realestate-inquiry-ui/rest/v1.0/';

function jsBdc() {
    if (!checkeddata || checkeddata.length == 0) {
        layer.alert("请选择需要编辑记录！", {title: '提示'});
        return;
    }

    for (var i = 0; i < checkeddata.length; i++){
        if(checkeddata[i]['SDZT']==='正常'){
            warnMsg("选中数据中存在正常状态数据，请重新选择！");
            return false;
        }
    }
    // if(checkeddata[0]['SDZT']==='正常'){
    //     warnMsg("该不动产权证状态为正常状态，无需解锁！");
    //     return false;
    // }
    // var url='';
    // if(checkeddata[0]['SDLX']=='bdcdy'){
    //     url='bdcdysd/js';
    // }else if(checkeddata[0]['SDLX']=='bdczs'){
    //     url='bdczssd/js';
    // }
    showDialog();
}

function loadComplete() {
    /**
     * 加载Table数据列表
     */
    var reverseList = ['BDCDYHANDCQZH', 'ZL'];

    reverseTableCell(reverseList);
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
        area: ['430px'],
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
    var dyjsxx = new Array();
    var zsjsxx = new Array();
    var jsyy = $('#jsyyNr').val();
    $.each(checkeddata, function (i, item) {
        if (item['SDLX'] == 'bdcdy') {
            var obj = new Object();
            obj.dysdid = item['SDID'];
            obj.bdcdyh = item['BDCDYHANDCQZH'];
            dyjsxx.push(obj);
        }
        if (item['SDLX'] == 'bdczs') {
            var obj = new Object();
            obj.zssdid = item['SDID'];
            obj.cqzh = item['BDCDYHANDCQZH'];
            obj.zsid = item['ZSID'];
            zsjsxx.push(obj);
        }
    });
    // if(sdlx==='bdcdy'){
    //     obj['dysdid']=checkeddata[0]['SDID'];
    //     obj['bdcdyh']=checkeddata[0]['BDCDYHANDCQZH'];
    // }else if(sdlx==='bdczs'){
    //     obj['zssdid']=checkeddata[0]['SDID'];
    //     obj['cqzh']=checkeddata[0]['BDCDYHANDCQZH'];
    //     obj['zsid'] = checkeddata[0]['ZSID'];
    // }
    // obj['sdzt']=0;
    if (dyjsxx.length != 0 && zsjsxx.length != 0) {
        $.ajax({
            url: BASE_URL + 'bdcdysd/js?jsyy=' + encodeURI(jsyy),
            type: "POST",
            data: JSON.stringify(dyjsxx),
            contentType: 'application/json',
            dataType: "json",
            async: false,
            success: function (data) {
            },
            error: function (e) {
                saveFailMsg();
            }, complete: function () {
            }
        });

        $.ajax({
            url: BASE_URL + 'bdczssd/js?jsyy=' + encodeURI(jsyy),
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
    } else if (dyjsxx.length != 0) {
        $.ajax({
            url: BASE_URL + 'bdcdysd/js?jsyy=' + encodeURI(jsyy),
            type: "POST",
            data: JSON.stringify(dyjsxx),
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
    } else if (zsjsxx.length != 0) {
        $.ajax({
            url: BASE_URL + 'bdczssd/js?jsyy=' + encodeURI(jsyy),
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

function sdBdcdy() {
    var layerIndex = layer.open({
        title: '不动产单元列表',
        type: 2,
        area: ['960px', '450px'],
        content: '../sd/bdcdyList.html'
        , cancel: function (index, layero) {
            layui.table.reload('pageTable', {page: {curr: 1}});
            parent.layer.close(index);
        }
        , success: function () {

        }
    });
    layer.full(layerIndex);
}

function sdBdczs() {
    var layerIndex = layer.open({
        title: '不动产证书列表',
        type: 2,
        area: ['960px', '450px'],
        content: '../sd/bdczsList.html'
        , cancel: function (index, layero) {
            layui.table.reload('pageTable', {page: {curr: 1}});
            parent.layer.close(index);
        }
        , success: function () {

        }
    });
    layer.full(layerIndex);
}

/**
 * 自定义查询不动产证书列表页面
 */
function sdBdczsNew(){
    var layerIndex = layer.open({
        title: '不动产证书列表',
        type: 2,
        area: ['1150px', '450px'],
        content: '../dtcx/commonCx.html?cxdh=zsxxcx',
        cancel: function (index, layero) {
            layui.table.reload('pageTable', {page: {curr: 1}});
            parent.layer.close(index);
        }
    });
    layer.full(layerIndex);
}

function showBz() {
    if (!checkeddata || checkeddata.length != 1) {
        layer.alert("请选择需要编辑的某一条记录！", {title: '提示'});
        return;
    }
    var div = " <div id=\"bdc-popup-textarea\"><form class=\"layui-form\" action=\"\">" +
        "            <div class=\"layui-form-item pf-form-item\">" +
        "                <div class=\"layui-inline\">" +
        "                    <label class=\"layui-form-label\"><span class=\"required-span\"><sub>*</sub></span>备注</label>" +
        "                    <div class=\"layui-input-inline bdc-end-time-box\">" +
        "                        <textarea name=\"bz\" id='bz' placeholder=\"请输入内容\" class=\"layui-textarea\"></textarea>" +
        "                    </div>" +
        "                </div>" +
        "            </div>" +
        "        </form>" +
        "    </div>";
    //小弹出层
    layer.open({
        title: '备注',
        type: 1,
        area: ['430px'],
        btn: ['提交', '取消'],
        content: div
        , yes: function (index, layero) {
            //提交 的回调
            saveBz(index);
        }
        , btn2: function (index, layero) {
            //取消 的回调
            layer.close(index)
        }
        , cancel: function () {
            //右上角关闭回调
            //return false 开启该代码可禁止点击该按钮关闭
            layer.close(index)
        }
        , success: function () {

        }
    });
}

function saveBz(index) {
    var obj = {};
    var url = '';
    obj['bz'] = $('#bz').val();
    var sdlx = checkeddata[0]['SDLX'];
    if (checkeddata[0]['SDLX'] === 'bdcdy') {
        obj['dysdid'] = checkeddata[0]['SDID'];
        url = 'bdcdysd/bz';
    } else if (checkeddata[0]['SDLX'] === 'bdczs') {
        obj['zssdid'] = checkeddata[0]['SDID'];
        url = 'bdczssd/bz';
    }
    $.ajax({
        url: BASE_URL + url,
        type: "POST",
        data: JSON.stringify(obj),
        contentType: 'application/json',
        dataType: "json",
        success: function (data) {
            saveSuccessMsg();
        },
        error: function (e) {
            saveFailMsg();
        }, complete: function () {
            $('#bz').val('');
            layer.close(index);
            parent.layui.table.reload('pageTable', {page: {curr: 1}});
            parent.layer.close(index);
        }
    });
}

function loadComplete() {
    var getTd = $('.layui-table-view .layui-table td');
    for (var i = 0; i < getTd.length; i++) {
        if ($(getTd[i]).attr('data-field') == "SDZT") {
            var getTdCell = $(getTd[i]).find('.layui-table-cell');
            if (getTdCell[0].innerText == '锁定') {
                $(getTd[i]).children('div').empty();
                $(getTd[i]).children('div').append('<span class="bdc-sd">锁定 </span>');
            }
        }
    }
    changeTrBackgroundExceptRight([
        {name: 'bdc-sd', color: '#333', background: '#a7afb6'}, true]);
}