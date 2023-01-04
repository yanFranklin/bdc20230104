/**
 * @author <a href ="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
 * @description 公告台账
 */
var isSubmit = true;
var form;
var zdList = getSlMulZdList("slgglx");
layui.use(['form', 'jquery', 'element', 'table', 'laydate', 'upload'], function () {
    form = layui.form;
    var layer = layui.layer;
    var verifyMsg = "必填项不能为空";
    var laydate = layui.laydate;
    form.verify({});
    laydate.render({
        elem: '#ggkssj',
        type: 'datetime'
    });

    laydate.render({
        elem: '#ggjssj' ,
        type: 'datetime'
    });
    /**
     * 表单数据提交
     */
    form.on('submit(saveGgxx)', function(data) {
        if (!isSubmit) {
            layer.msg(verifyMsg, {icon: 5, time: 3000});
            isSubmit = true;
            return false;
        } else {

            addModel();
            saveZctb(data.field);
            return false;
        }
        // 禁止提交后刷新
        return false;
    });
})

/**
 * 收件材料列表
 */
function sjcl(data) {
    window.open('/realestate-portal-ui/view/new-page.html?type=list&name=' +  data.data.GZLSLID);
}

/**
 * 公告信息录入
 */
function edit(data) {
    var ggid = isNotBlank(data.data.GGID) ? data.data.GGID: "";
    layer.open({
        type: 2,
        shade: false,
        shadeClose: true,
        isOutAnim: false,
        content: "../../changzhou/ggtzlr/edit.html?ggid="+ggid,
        area: ['960px', '500px'],
        title: '公告信息录入',
    });

}

function zszf(obj,data){
    state = "";
    var ggids = [];
        if(checkeddata.length == 0){
            warnMsg('请勾选需要证书作废的数据！');
            return;
        }
        for(var i = 0 ; i < checkeddata.length ; i++){
            if(ggids.indexOf(checkeddata[i].GGID) == -1){
                ggids.push(checkeddata[i].GGID);
            }
        }
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/ggxx/updateggzt?ggzt=1",
        type: "POST",
        data: JSON.stringify(ggids),
        contentType: 'application/json',
        async: false,
        dataType: "json",
        success: function (data) {
            removeModal();
            if(data){
                successMsg("操作成功！");
                layui.table.reload('pageTable', {page: {curr: 1}});
            }
        },
        error: function (err) {
            removeModal();
            delAjaxErrorMsg(err)
        }
    });
}

/*
* 新增公告，先弹出选择公告类型，在弹出对应的页面
* */
function addGg() {
    var gglxHtml = gglxSelectOpt(zdList);
    var index = layer.open({
        title: '请选择公告类型',
        type: 1,
        area: ['450px', '450px'],
        btn: ['确定', '取消'],
        content: gglxHtml,
        yes: function (index, layero) {
            //根据选择的数判断打开的页面
            var gglxValue = $('#gglx').find('option:selected')[0].dataset.name;
            var gglxVal = $('#gglx').val();
            if (isNullOrEmpty(gglxValue)) {
                ityzl_SHOW_INFO_LAYER("请先选择公告类型!");
                return false;
            }
            var url = "/realestate-inquiry-ui/changzhou/ggtzlr/" + gglxValue + ".html?gglx=" + gglxVal + "";
            var ggindex = layer.open({
                type: 2,
                title: "公告",
                area: ['1300px', '600px'],
                fixed: false, //不固定
                maxmin: true, //开启最大化最小化按钮
                content: url
            })
            layer.full(ggindex);
            //关闭当前弹框
            layer.close(index);
        },
        btn2: function () {
            layer.close(index);
        },
        end: function () {
            form.render('select');
        }
    });
    form.render('select');

}

/**
 * @param ggxx 选中的公告数据
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @description 公告查看
 * @date : 2022/3/1 10:37
 */
function ggck(ggxx) {
    //如果当前数据没有选公告类型，查看时先选则公告类型
    var gglx = ggxx.data.GGLXDM;
    if (isNullOrEmpty(gglx)) {
        //提示先选择公告类型
        var gglxHtml = gglxSelectOpt(zdList);
        var index = layer.open({
            title: '请选择公告类型',
            type: 1,
            area: ['450px', '450px'],
            btn: ['确定', '取消'],
            content: gglxHtml,
            yes: function (index, layero) {
                //保存当前点击的值到公告信息数据中
                var gglxVal = $('#gglx').val();
                //根据选择的数判断打开的页面
                var gglxValue = $('#gglx').find('option:selected')[0].dataset.name;
                if (isNullOrEmpty(gglxValue)) {
                    ityzl_SHOW_INFO_LAYER("请先选择公告类型!");
                    return false;
                }

                // 更新公告类型
                saveGgxx({ ggid: ggxx.data.GGID, gglx : gglxVal});
                // 关闭公告类型选择页面
                layer.close(index);

                // 展现公告页面
                var url = "/realestate-inquiry-ui/changzhou/ggtzlr/" + gglxValue + ".html?gglx=" + gglxVal + "" + "&ggid=" + ggxx.data.GGID;
                var ggindex = layer.open({
                    type: 2,
                    title: "公告查看",
                    area: ['1300px', '600px'],
                    fixed: false, //不固定
                    maxmin: true, //开启最大化最小化按钮
                    content: url,
                    end: function () {
                        layui.table.reload('pageTable', {page: {curr: 1}});
                    }
                });
                layer.full(ggindex);
            },
            btn2: function () {
                layer.close(index);
            },
        });
        form.render('select');
    } else {
        var ggjc = getGgjc(zdList.slgglx, gglx);
        var url = "/realestate-inquiry-ui/changzhou/ggtzlr/" + ggjc + ".html?gglx=" + gglx + "&ggid=" + ggxx.data.GGID;
        var ggindex = layer.open({
            type: 2,
            title: "公告查看",
            area: ['1300px', '600px'],
            fixed: false, //不固定
            maxmin: true, //开启最大化最小化按钮
            content: url,
            end: function () {
                layui.table.reload('pageTable', {page: {curr: 1}});
            }
        })
        layer.full(ggindex);
    }
}


//组织公告类型下拉框
function gglxSelectOpt(gglxZdList) {
    var gglxSelOption = "<option value=\"\" data-name=\"\">请选择</option>";
    if (gglxZdList.slgglx !== null && gglxZdList.slgglx.length > 0) {
        for (var i = 0; i < gglxZdList.slgglx.length; i++) {
            var option = gglxZdList.slgglx[i];
            gglxSelOption += '<option value="' + option.DM + '" data-name="' + option.JC + '">' + option.MC + '</option>';
        }
    }
    return "<div id=\"bdc-popup-small\" class=\"\">\n" +
        "    <form class=\"layui-form\" action=\"\">\n" +
        "        <div class=\"layui-form-item pf-form-item\">\n" +
        "            <div class=\"layui-inline\">\n" +
        "                <label class=\"layui-form-label\">公告类型：</label>\n" +
        "                <div class=\"layui-input-inline\">\n" +
        "                        <select name=\"gglx\" lay-filter='gglx' id='gglx' lay-search=''>" +
        gglxSelOption +
        "                        </select>" +
        "                </div>\n" +
        "            </div>\n" +
        "        </div>\n" +
        "    </form>\n" +
        "</div>";
}


//公告推送
function ts(ggxx) {
    addModel();
    var qzbs = ggxx.data.QZBS;
    var ggid = ggxx.data.GGID;
    if (isNotBlank(qzbs)) {
        removeModal();
        ityzl_SHOW_WARN_LAYER("已推送签章");
    } else {
        getReturnData("/rest/v1.0/ggxx/tsqz", {ggid: ggid}, "GET", function (data) {
            removeModal();
            ityzl_SHOW_SUCCESS_LAYER("推送成功")
        }, function (xhr) {
            delAjaxErrorMsg(xhr);
        })
    }
}

//公告下载
function xz(ggxx) {
    //根据返回的证照标识下载证照
    var qzbs = ggxx.data.QZBS;
    if (isNullOrEmpty(qzbs)) {
        removeModal();
        ityzl_SHOW_WARN_LAYER("未生成签章信息无法下载");
    } else {
        getReturnData("/rest/v1.0/ggxx/qzxz", {qzbs: qzbs}, "GET", function (data) {
            if (data) {
                window.open(data.downUrl);
            }
        }, function (xhr) {
            delAjaxErrorMsg(xhr);
        })
    }
}

//获取公告简称
function getGgjc(gglxZd, gglx) {
    var gglxjc = "";
    for (var i = 0; i < gglxZd.length; i++) {
        if (gglxZd[i].DM == gglx) {
            gglxjc = gglxZd[i].JC;
            break;
        }
    }
    return gglxjc;
}

/**
 * 保存公告信息
 * @param data
 */
function saveGgxx(data) {
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/ggxx",
        type: "PUT",
        data: JSON.stringify(data),
        contentType: 'application/json',
        async: false,
        dataType: "json",
        success: function (data) {
            removeModal();
            console.log("更新公告类型成功")
        },
        error: function (err) {
            removeModal();
            delAjaxErrorMsg(err)
        }
    });
}
/**
 * 批量删除
 */
function deleteGg(){
    if(checkeddata.length == 0){
        layer.msg('<img src="../../static/lib/bdcui/images/warn-small.png" alt="">请选择需要删除的数据行!');
        return;
    }
    layer.confirm('确定删除？', function (index) {
        var ggidList = [];
        for(var i=0;i<checkeddata.length;i++){
            ggidList.push(checkeddata[i].GGID);
        }
        if(ggidList.length > 0){
            $.ajax({
                url: '/realestate-inquiry-ui/rest/v1.0/ggxx/deleteGg',
                data:JSON.stringify(ggidList),
                contentType: 'application/json',
                dataType: "json",
                type: "delete",
                success: function (data) {
                    if(data){
                        successMsg("删除成功，即将刷新页面！");
                        setTimeout(function () {
                            $("#search").click();
                        },2000)
                    }
                },
                error: function (e) {
                }
            });
        }
        return false;
    });
}
/**
 * 批量删除
 */
function batchDelete(){
    if(checkeddata.length == 0){
        layer.msg('<img src="../../static/lib/bdcui/images/warn-small.png" alt="">请选择需要删除的数据行!');
        return;
    }
    layer.confirm('确定删除？', function (index) {
        var tbcxList = [];
        for(var i=0;i<checkeddata.length;i++){
            var tbcx = {};
            tbcx["id"] = checkeddata[i].ID;
            tbcx["wjzxid"] = checkeddata[i].WJZXID;
            tbcxList.push(tbcx);
        }
        if(tbcxList.length > 0){
            $.ajax({
                url: '/realestate-inquiry-ui/rest/v1.0/tbcx/batchDelete',
                data:JSON.stringify(tbcxList),
                contentType: 'application/json',
                dataType: "json",
                type: "delete",
                success: function (data) {
                    if(data){
                        successMsg("删除成功，即将刷新页面！");
                        setTimeout(function () {
                            $("#search").click();
                        },2000)
                    }
                },
                error: function (e) {
                }
            });
        }
        return false;
    });
}

function cancel(){
    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
    parent.layer.close(index); //再执行关闭
}