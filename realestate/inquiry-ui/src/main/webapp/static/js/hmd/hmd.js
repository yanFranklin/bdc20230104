/**
 * 黑名单自定义查询 js
 */

/**
 * 列表加载完成回调事件
 * @param res 当前查询结果
 */

// 表格加载完成后，处理黑名单状态字段颜色
function tableHasLoad(res) {
    var getTd = $('.layui-table-view .layui-table td');
    for (var i = 0; i < getTd.length; i++) {
        if ($(getTd[i]).attr('data-field') == "HMDZTMC") {
            var getTdCell = $(getTd[i]).find('.layui-table-cell');
            if (getTdCell[0].innerText == '锁定') {
                $(getTd[i]).children('div').empty();
                $(getTd[i]).children('div').append('<span class="bdc-sd">锁定</span>');
            }else if(getTdCell[0].innerText == '正常'){
                $(getTd[i]).children('div').empty();
                $(getTd[i]).children('div').append('<span class="bdc-normal">正常</span>');
            }
        }
    }
}

// 证书黑名单操作
function blockZs(){
    var index = layer.open({
        title: '不动产证书列表',
        type: 2,
        area: ['1150px', '450px'],
        content: '../../view/hmd/hmdBdcZsList.html',
        cancel: function (index, layero) {
            layui.table.reload('pageTable', {page: {curr: 1}});
            parent.layer.close(index);
        },
    });
    layer.full(index);
}

// 人员黑名单操作
function blockRy(){
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/zszm/userinfo",
        type: 'GET',
        dataType: 'json',
        success: function (result) {
            var obj = {
                hmdztlb : 1,
                hmdzt: 1,
                cjr : result.alias,
                cjsj : Format(formatDate(new Date()), "yyyy-MM-dd hh:mm:ss")
            };
            layer.open({
                title: '黑名单',
                type: 2,
                content: "/realestate-inquiry-ui/view/hmd/hmdEdit.html",
                area: ['960px', '450px'],
                success: function(layero,index){
                    var iframeWin = window[layero.find('iframe')[0]['name']];
                    iframeWin.renderForm(obj);
                },
                end: function(){
                    layui.table.reload('pageTable', {page: {curr: 1}});
                }
            });
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr);
        }
    });
}

// 不动产单元号黑名单操作
function blockBdcdyh(){
    var index = layer.open({
        title: '不动产单元列表',
        type: 2,
        area: ['1150px', '450px'],
        content: '../../view/hmd/hmdBdcdyList.html',
        cancel: function (index, layero) {
            layui.table.reload('pageTable', {page: {curr: 1}});
            parent.layer.close(index);
        },
    });
    layer.full(index);
}

// 解除黑名单
function relieve(){
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
    showRelieveDialog();
}

// 展示黑名单解除原因
function showRelieveDialog() {
    var div = "<div id='bdc-popup-textarea'><form class='layui-form' action=''>" +
        "            <div class='layui-form-item pf-form-item'>" +
        "                <div class='layui-inline'>" +
        "                    <label class='layui-form-label'><span class='required-span'><sub>*</sub></span>解除原因</label>" +
        "                    <div class='layui-input-inline bdc-end-time-box'>" +
        "                        <textarea name='jsyy' id='jsyy' placeholder='请输入内容' class='layui-textarea'></textarea>" +
        "                    </div>" +
        "                </div>" +
        "            </div>" +
        "        </form>" +
        "    </div>";
    //小弹出层
    layer.open({
        title: '解除原因',
        type: 1,
        area: ['450px','200px'],
        btn: ['提交', '取消'],
        content: div,
        yes: function (index, layero) {
            jc(index);
        },
        btn2: function (index, layero) {
            layer.close(index)
        },
        cancel: function (index) {
            layer.close(index)
        },
    });
}

// 解除
function jc(index) {
    var hmdjcxx = new Array();
    var jsyy = $('#jsyy').val();
    $.each(checkeddata, function (i, item) {
        var obj = new Object();
        obj.hmdid = item['HMDID'];
        obj.jsyy = jsyy;
        hmdjcxx.push(obj);
    });
    if(hmdjcxx.length > 0){
        addModel();
        $.ajax({
            url: getContextPath() + '/rest/v1.0/hmd/js',
            type: "POST",
            data: JSON.stringify(hmdjcxx),
            contentType: 'application/json',
            dataType: "json",
            async: false,
            success: function (data) {
                removeModal();
                successMsg("解除成功");
            },
            error: function (e) {
                removeModal();
                saveFailMsg();
            },
            complete: function () {
                $('#jsyy').val("");
                layer.close(index);
                parent.layui.table.reload('pageTable', {page: {curr: 1}});
                parent.layer.close(index);
            }
        });
    }
}


/**
 * 修改
 */
function xg(obj, data) {
    var result = {
        hmdid : data.HMDID,
        hmdztlb : data.HMDZTLB,
        cjr : data.CJR,
        cjsj: data.CJSJ,
        cjyy: data.CJYY,
        ztmc: data.ZTMC,
        ztzjh: data.ZTZJH,
        bz: data.BZ,
        hmdzt: data.HMDZT
    };
    layer.open({
        title: '修改黑名单',
        type: 2,
        content: "/realestate-inquiry-ui/view/hmd/hmdEdit.html",
        area: ['960px', '450px'],
        success: function(layero,index){
            var iframeWin = window[layero.find('iframe')[0]['name']];
            iframeWin.renderForm(result);
        },
        end: function(){
            layui.table.reload('pageTable', {page: {curr: 1}});
        }
    });
}
/**
 * 删除
 */
function sc(obj, data) {
    layer.confirm("是否确认移除当前记录？", {
        btn: ["确认", "取消"],
    }, function (index, layero) {
        var hmdidList = [];
        hmdidList.push(data.HMDID);
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
    }, function (index) {
        return;
    });
}