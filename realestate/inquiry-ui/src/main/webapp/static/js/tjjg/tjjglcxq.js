/**
 * author: <a href="mailto:yaoyi@gtmap.cn>yaoyi</a>
 * version 1.0.0  2022/09/14
 * describe: 流程超期统计监管详情查看
 */
var gzlslid = $.getUrlParam('gzlslid');
layui.use(['form', 'jquery', 'element', 'table', 'layer','laydate', 'laytpl'], function () {
    var layer = layui.layer;
    var table = layui.table;
    addModel();
    $.ajax({
        url: '/realestate-inquiry-ui/rest/v1.0/tjjg/lc/csxq',
        type: "GET",
        async: true,
        contentType: 'application/json;charset=utf-8',
        data: {gzlslid: gzlslid},
        success: function (data) {
            removeModal();
            if(data.length>0){
                table.render({
                    elem: '#xq',
                    limit: 999,
                    data: data,
                    id: 'xq',
                    even: true,
                    cols: [[
                        {field: 'taskName', title: '节点名称', width:180},
                        {field: 'taskAssName', title: '办理人员名称', width:180},
                        {field: 'statisticsTime', title: '办理时长(小时)', width:180, templet: function (d) {
                            if(d.statisticsTime == 0){
                                return d.statisticsTime;
                            }else{
                                return (d.statisticsTime/60).toFixed(2);
                            }
                            }},
                    ]]
                });
            }else{
                warnMsg("未获取到数据");
            }
        },
        error: function (err) {
            removeModal();
            delAjaxErrorMsg(err);
        }
    });

});