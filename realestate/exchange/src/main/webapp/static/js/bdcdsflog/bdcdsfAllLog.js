var type = GetQueryString("type");
var interfaceId = GetQueryString("interfaceId");

layui.use(['jquery', 'layer', 'element'], function () {
    var element = layui.element;
    var $ = layui.jquery;
    var layer = layui.layer;
    //触发事件
    $(function () {
        // 加载字典
        var url = '../bdcDsfLog/getBdcDsfLogPagesJson?type=' + type + '&jkid=' + interfaceId;
        initLogData(element, url);
    });

    function initLogData(element, url) {
        $.ajax({
            url: url,
            type: "POST",
            async: false,
            dataType: "json",
            success: function (data) {
                if (data) {
                    if (data.esLog && data.esLog.totalElements > 0) {
                        tabAdd(element, 'esLog', 'bdcdsflog.html?type=logines&interfaceId=' + interfaceId, 'es日志', 'demo');
                        element.tabChange('demo', 'esLog');
                    }
                    if (data.dbLog && data.dbLog.totalElements > 0) {
                        tabAdd(element, 'dbLog', 'bdcdsflog.html?type=logindb&interfaceId=' + interfaceId, 'db日志', 'demo');
                        element.tabChange('demo', 'dbLog');
                    }
                } else {
                    layer.confirm("当前接口没有相关日志数据", {
                        icon: 5,
                        btn: '确认',
                        title: '提示'
                    }, function (index) {
                        window.open("about:blank", "_self").close();
                    });
                }
            },
            error: function (e) {
            }
        });
    }

    function tabAdd(element, id, url, name, tabName) {
        element.tabAdd(tabName, {
            title: name,
            content: '<iframe data-frameid="' + id + '" scrolling="no" frameborder="0" src="' + url + '" name="container" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes" style="min-height:660px;height: 100%;width: 100%"></iframe>',
            id: id //规定好的id
        });
        element.render('tab');
    }
});