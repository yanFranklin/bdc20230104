/**
 * 任务台账查询批量件查询条件处理
 * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
 */
layui.config({
    base: '../static' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    selectInput: "/lib/selectInput/selectInput"
});

var selectInput, inputSelectIns = {};
layui.use(['selectInput'], function(){
    selectInput = layui.selectInput;
});

function renderPlcx(rwlblx) {
    ["bdcdyh", "zl"].forEach(function (itemxx, index) {
        var itemid = rwlblx + "-" + itemxx;
        inputSelectIns[itemid] = selectInput.render({
            // 容器id，必传参数
            elem: '#' + itemid,
            name: itemxx, // 渲染的input的name值
            layReqText: '请输入再选择', //同layui form参数lay-ReqText
            initValue: '', // 渲染初始化默认值
            hasSelectIcon: true,
            placeholder: '请输入再选择', // 渲染的inputplaceholder值
            remoteSearch: true, // 是否启用远程搜索 默认是false，和远程搜索回调保存同步
            parseData: function (res) {  // 此参数仅在异步加载data数据下或者远程搜索模式下有效，解析回调，如果你的异步返回的data不是上述的data格式，请在此回调格式成对应的数据格式，回调参数是异步加载的数据
                var data = [];
                res.forEach(function (item, index) {
                    data.push({name: item, value: item})
                });
                return data;
            },
            error: function (error) { // 异步加载出错的回调 回调参数是错误msg

            },
            done: function (data) { // 异步加载成功后的回调 回调参数加载返回数据

            },
            remoteMethod: function (value, cb) { // 远程搜索的回调函数
                if (isNullOrEmpty(value)) {
                    return cb([]);
                }

                var data = {};
                data[itemxx] = value;
                $.ajax({
                    url: '/realestate-portal-ui/rest/v1.0/task/search/matcheddata',
                    type: 'post',
                    dataType: 'json',
                    contentType: "application/json",
                    data: JSON.stringify(data),
                    success: function (data) {
                        return cb(data)
                    }
                })
            }
        });
    });
}

function resetFun(rwlblx) {
    $("#" + rwlblx + "-reset").click(function () {
        ["bdcdyh", "zl"].forEach(function (itemxx, index) {
            var itemid = rwlblx + "-" + itemxx;
            inputSelectIns[itemid].emptyValue();
        });
    });
}