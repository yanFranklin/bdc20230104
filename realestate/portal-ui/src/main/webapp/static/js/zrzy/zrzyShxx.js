/**
 * Created by Ypp on 2019/7/31.
 * 注意：当前js需要跟register-ui下面的shxx/bdcShxx.js保持一致
 */
// 当前审核信息ID
var currentShxxid;
// 当前节点名称
var currentJdmc;
// 当前节点id
var currentJdid;
// 工作流实例ID
var gzlslid;

layui.use(['jquery','table','element','form','laytpl','response'], function() {
    var $ = layui.jquery,
        table = layui.table,
        laytpl = layui.laytpl,
        response = layui.response,
        form = layui.form;

    $(function () {
        //获取地址栏参数
        var $paramArr =getIpHz();
        var taskId;
        var isIndex;
        if ($paramArr['name']) {
            taskId = $paramArr['name'];
        } else if ($paramArr['taskId']) {
            taskId = $paramArr['taskId'];
        }
        var isEnd = $paramArr['isEnd'];
        isIndex= $paramArr['isIndex'];
        var currentId= $paramArr['currentId'];
        gzlslid= $paramArr['processInstanceId'];

        //新增签名
        // 获取默认意见，因为要返回当流程的节点信息,所以先执行该方法
        getMryj();
        function getMryj() {
            //获取数据
            const url = "/realestate-natural-ui/rest/v1.0/shxx/mryj/" + taskId + "?time=" + new Date().getTime();
            console.log(url);
            $.ajax({
                url: url,
                contentType: "application/json;charset=utf-8",
                type: "GET",
                async: false,
                success: function (data) {
                    if (data && !isNullOrEmpty(data.shxxid)) {
                        currentShxxid = taskId;
                        currentJdmc = data.jdmc;
                        currentJdid = data.jdid;

                        // 页面获取审核信息,并设置默认意见
                        getShxxList(data.shyj);
                    }
                },
                error: function () {
                    console.log(url);
                    warnMsg("未获取到签名节点信息！");
                }
            });
            //return false;
        }
        function getShxxList(mryj) {
            // 加载遮罩
            addModel();
            //获取数据
            $.ajax({
                url: "/realestate-natural-ui/rest/v1.0/shxx/list/signal?" + new Date().getTime(),
                contentType: "application/json;charset=utf-8",
                type: "GET",
                async: false,
                data: {gzlslid: gzlslid, taskId: taskId, currentJdmc: currentJdmc,onlyCurrentJd:true},
                success: function (data) {
                    shxxData = data;
                    // 模板渲染
                    processData(data);

                    // 后台做控制，如果当前没有签名意见，则获取默认意见
                    if (!isNullOrEmpty(mryj)) {
                        $("#" + currentJdid + "_pre").html(mryj);
                        $("#" + currentJdid + "_shyj").val(mryj);
                    }

                    // 控制签名权限
                    resetDisabled();

                    // 设置意见行高样式（超过一行左对齐，首行空两格）
                    var $text = $('.bdc-text');
                    for (var i = 0; i < $text.length; i++) {
                        if ($($text[i]).height() > 32) {
                            $($text[i]).addClass('bdc-text-lines');
                        }
                    }

                    // 获取表单控制权限
                    //if (readOnly || !isNullOrEmpty(formStateId)) {
                    //    getStateById(readOnly, formStateId, "bdcShxx");
                    //}
                },
                error: function () {
                    console.log(url);
                    warnMsg("获取签名数据失败！");
                }
            });
            removeModal();
            // 禁止页面刷新
            return false;
        }
        //模板引擎加载数据
        function processData(data) {
            var getTpl = qmyjDivTpl.innerHTML; //获取自己定义的模板元素
            laytpl(getTpl).render(data, function (html) {
                $("#templateFatherDiv").html(html); //加载到主元素中
                form.render();
            });
            $.ajax({
                url: getContextPath() + "/rest/v1.0/workflow/zrzy/process-instances/getZfqmxs",
                dataType: "json",
                async: false,
                success: function (data) {
                    if (data === 0){
                        $("#templateFatherDiv").hide();
                    }
                }
            });
        }
        function resetDisabled() {
            $.each(shxxData, function (index, item) {
                if (isNullOrEmpty(currentJdid) || item.jdid != currentJdid) {
                    // 根据当前节点设置按钮权限
                    $("#" + item.jdid + "_sign").removeAttr("onclick");
                    $("#" + item.jdid + "_sign").addClass("prohibit_sign");
                    $("#" + item.jdid + "_deleteSign").removeAttr("onclick");
                    $("#" + item.jdid + "_deleteSign").addClass("prohibit_deleteSign");
                    // 设置当前节点文本框权限
                    $("#" + item.jdid + "_shyj").attr('disabled', 'disabled');
                    $("#" + item.jdid + "_shyj").addClass("bdc-prohibit-text");
                }
            });
        }

    });
});

/**
 * 获取签名
 * @param jdmc
 * @param shxxid
 * @returns {boolean}
 */
function sign(jdmc, shxxid) {
    if (isNullOrEmpty(jdmc) || isNullOrEmpty(shxxid)) {
        warnMsg("未生成该节点的信息，不允许签名!");
        return false;
    }
    if (shxxid != currentShxxid) {
        warnMsg("不是" + currentJdmc + "节点，不允许签名!");
        return false;
    }
    var shxxDO = {};
    shxxDO.jdmc = jdmc;
    // shxxid为当前的taskId
    shxxDO.shxxid = shxxid;
    shxxDO.shyj = $("#" + currentJdid + "_shyj").val();
    shxxDO.gzlslid = gzlslid;
    //获取数据
    $.ajax({
        url: "/realestate-natural-ui/rest/v1.0/shxx/sign",
        contentType: "application/json;charset=utf-8",
        type: "PUT",
        data: JSON.stringify(shxxDO),
        success: function (data) {
            $("#" + currentJdid + "_img").attr('src', data.qmtpdz);
            $("#" + currentJdid + "_time").html(data.qmsj);
        },
        error: function () {
            warnMsg("签名失败！");
        }
    });
}

/**
 * 删除签名
 * @param jdmc
 * @param shxxid
 * @returns {boolean}
 */
function deleteSign(jdmc, shxxid) {
    if (isNullOrEmpty(jdmc) || isNullOrEmpty(shxxid)) {
        warnMsg("审核信息为空!");
        return false;
    }
    if (shxxid != currentShxxid) {
        warnMsg("不是" + currentJdmc + "节点，不允许删除!");
        return false;
    }
    // 清除审核意见和签名信息
    $.ajax({
        url: "/realestate-natural-ui/rest/v1.0/shxx/sign/" + shxxid,
        contentType: "application/json;charset=utf-8",
        type: "DELETE",
        // data: JSON.stringify(shxxDO),
        success: function (data) {
            $("#" + currentJdid + "_shyj").val(null);
            $("#" + currentJdid + "_pre").html(null);
            $("#" + currentJdid + "_img").attr('src', null);
            $("#" + currentJdid + "_time").html(null);
        },
        error: function () {
            warnMsg("删除签名信息失败！");
        }
    });
}


/**
 * 调节页面输入框的高度
 * @param event
 */
function changeContent(event) {
    var event = event || window.event;
    var target = event.target || event.srcElement;
    var $textarea = $(target);
    var $pre = $textarea.siblings('.pre');
    $pre.html($textarea.val());
}

/**
 * 警告提示
 * @param msg
 */
function warnMsg(msg) {
    layer.msg('<img src="../static/lib/bdcui/images/warn-small.png" alt="">' + msg);
}