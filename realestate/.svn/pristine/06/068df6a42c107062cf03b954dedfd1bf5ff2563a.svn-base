/**
 * @author <a href ="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
 * @description 更新静态文件导航页面js
 */
layui.use(['form'], function () {
    var $ = layui.jquery,
        form = layui.form;
    var BASE_URL = '/' + getProjectName();

    //加载ui子系统下拉框数据
    getServices();

    // 获取当前项目名称
    function getProjectName() {
        var path = window.document.location.pathname;
        path = path.substring(1);
        var projectName = path.substring(0, path.indexOf("/"));
        return projectName;
    }

    function getServices() {
        $.ajax({
            url: BASE_URL + '/uploadstaticfile/getServices',
            type: 'GET',
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                if (data == null) {
                    layer.msg('<img src="../../../static/lib/bdcui/images/warn-small.png" alt="">获取UI子系统列表失败！');
                } else if (data.length == 0) {
                    layer.msg('<img src="../../../static/lib/bdcui/images/warn-small.png" alt="">注册中心当前没有UI子系统');
                } else {
                    var option = '<option value="">请选择</option>';
                    for (var i = 0; i < data.length; i++) {
                        option += "<option value='" + data[i] + "'>" + data[i] + "</option>";
                    }

                    $("#uiSystemName").append(option);
                    form.render('select');
                }
            },
            error: function (e) {
                layer.msg('<img src="../../../static/lib/bdcui/images/warn-small.png" alt="">获取UI子系统列表失败！');
            }
        })
    }


    form.on('select(uiSystemName)', function (data) {
        $("#instanceName").empty();
        form.render('select');
        $.ajax({
            url: BASE_URL + '/uploadstaticfile/getInstances',
            type: 'GET',
            contentType: 'application/json',
            data: {"uiSystemName": data.value},
            dataType: "json",
            success: function (data) {
                if (data == null) {
                    layer.msg('<img src="../../../static/lib/bdcui/images/warn-small.png" alt="">获取实例失败！');
                } else if (data.length == 0) {
                    layer.msg('<img src="../../../static/lib/bdcui/images/warn-small.png" alt="">该子系统没有实例');
                } else {
                    var option = '<option value="">请选择</option>';
                    for (var i = 0; i < data.length; i++) {
                        option += "<option value='" + data[i].instanceInfo.homePageUrl + "'>" + data[i].host + "</option>";
                    }

                    $("#instanceName").append(option);
                    form.render('select');
                }
            },
            error: function (e) {
                layer.msg('<img src="../../../static/lib/bdcui/images/warn-small.png" alt="">获取实例失败！');
            }
        });
    });

    form.on('select(instanceName)', function (data) {
        var url = data.value + "/static/lib/updatestaticfile/updateStaticFile.html" ;
        window.open(url);
        // $("#childFrame").attr('src',url);
        // document.getElementById('childFrame').contentWindow.location.reload(true);
        }
    )
});