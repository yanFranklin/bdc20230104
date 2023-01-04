/**
 * Created by Administrator on 2019/1/21.
 * 合肥js
 */
//转发需要根据lcPageEdition判断显示哪个send.html
var lcPageEdition = 'standard';
var iSabandon = 'hide';
//  仅在受理节点撤销申请
var showAandonTaskNames = ["受理"];

/**
 * 判断当前流程是否显示撤销
 */
function isAbandon(gzlslid) {
    $.ajax({
        type: "GET",
        url: getContextPath() + "/rest/v1.0/workflow/process-instances/abandon?gzlslid=" + gzlslid,
        async: false,
        success: function (data) {
            if (data === 'success') {
                iSabandon = "show";
            }
        }, error: function (e) {
            response.fail(e, '');
        }
    });
}

/**
 * 退回件显示退回意见
 */
function showThyj(processInstanceId, taskId) {
    $.ajax({
        type: "POST",
        url: getContextPath() + "/rest/v1.0/workflow/process-instances/opinion",
        data: {
            processInstanceId: processInstanceId,
            taskId: taskId,
            type: "back_opinion"
        },
        async: false,
        success: function (data) {
            if (isNotBlank(data)) {
                var getTpl = thyjTpl.innerHTML;
                laytpl(getTpl).render(data, function (html) {
                    $('#fjzq').append(html);
                    if (!$('#fjzq').hasClass('bdc-tips-left-box')) {
                        $('#fjzq').addClass("bdc-tips-left-box");
                    }
                });
            }
        }, error: function (e) {
            response.fail(e, '');
        }
    });
}

/**
 * 本节点曾经退回显示退回意见
 */
function showBjdThyj(processInstanceId, taskId) {
    $.ajax({
        type: "POST",
        url: getContextPath() + "/rest/v1.0/workflow/process-instances/range/opinions",
        data: {
            processInsId: processInstanceId,
            taskId: taskId,
            types: "back_opinion,next_back_opinion",
            rangeType: 1,
        },
        success: function (data) {
            data.forEach(function (data){
                if (isNotBlank(data)){
                    if(!isNullOrEmpty(data.opinion)){
                        if(isNullOrEmpty(data.userAlisa)){
                            data.userAlisa = "未知人员";
                        }
                        var getTpl = bjdthyjTpl.innerHTML;
                        laytpl(getTpl).render(data, function (html) {
                            $('#fjzq').append(html);
                            if (!$('#fjzq').hasClass('bdc-tips-left-box')) {
                                $('#fjzq').addClass("bdc-tips-left-box");
                            }
                        });
                    }
                }
            })
        }, error: function (e) {
            response.fail(e, '');
        }
    });
}


// 有例外显示例外原因
function showLwsh(processInstanceId) {
    $.ajax({
        type: "GET",
        url: getContextPath() + "/rest/v1.0/yw/queryBdcGzlwShByGzlslid?gzlslid=" + processInstanceId,
        async: false,
        success: function (data) {
            var bdcGzlwShDOList = data;
            var lwyy = {};
            if (bdcGzlwShDOList && bdcGzlwShDOList.length > 0) {
                var lwyyArr = [];
                var shyjArr = [];
                for (var j = 0; j < bdcGzlwShDOList.length; j++) {
                    var lwsh = bdcGzlwShDOList[j];
                    lwyyArr.push(lwsh.lwyy);
                    shyjArr.push(lwsh.shyj);
                }
                lwyy['cjlwyy'] = '创建例外原因：' + lwyyArr.join(";");
                lwyy['shtgyy'] = '例外审核通过原因：' + shyjArr.join(";");
            }
            if (isNotBlank(lwyy)) {
                var getTpl = lwshTpl.innerHTML;
                laytpl(getTpl).render(lwyy, function (html) {
                    $('#fjzq').append(html);
                    if (!$('#fjzq').hasClass('bdc-tips-left-box')) {
                        $('#fjzq').addClass("bdc-tips-left-box");
                    }
                });
            }
        }, error: function (e) {
            response.fail(e, '');
        }
    });
}
//显示退回互联网原因
function showThhlwyy(processInstanceId){
    $.ajax({
        type: "GET",
        url: getContextPath() + "/rest/v1.0/yw/queryThhlwyyByGzlslid?gzlslid=" + processInstanceId,
        async: false,
        success: function (data) {
            var bdcCzrzDOList = data;
            var thyy = {};
            if (bdcCzrzDOList && bdcCzrzDOList.length > 0) {
                var thhlwyyArr = [];
                for (var j = 0; j < bdcCzrzDOList.length; j++) {
                    var bdcCzrzDO = bdcCzrzDOList[j];
                    thhlwyyArr.push(bdcCzrzDO.czyy);
                }
                thyy['thhlwyy'] = '退回互联网原因：' + thhlwyyArr.join(";");
            }
            if (isNotBlank(thyy)) {
                var getTpl = thhlwTpl.innerHTML;
                laytpl(getTpl).render(thyy, function (html) {
                    $('#fjzq').append(html);
                    if (!$('#fjzq').hasClass('bdc-tips-left-box')) {
                        $('#fjzq').addClass("bdc-tips-left-box");
                    }
                });
            }
        }, error: function (e) {
            response.fail(e, '');
        }
    });

}