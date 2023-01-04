/**
 * @author <a href ="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
 * @description 在线更新静态文件js
 */
layui.use(['form', 'upload'], function () {
    var $ = layui.jquery,
        upload = layui.upload,
        form = layui.form;
    var BASE_URL = '/' + getProjectName();

    // 加载上传静态文件事件绑定
    importFile();

    // 获取当前项目名称
    function getProjectName(){
        var path = window.document.location.pathname;
        path = path.substring(1);
        var projectName = path.substring(0, path.indexOf("/"));
        return projectName;
    }

    // 上传静态文件
    function importFile(){
        upload.render({
            elem: '#import' //绑定元素
            ,url: BASE_URL + '/uploadstaticfile/import' //上传接口
            ,accept: 'file'
            ,acceptMime: 'file/html,file/js,file/css'
            ,exts: 'html|js|css'
            ,data: {
                path: function(){
                    return $('#path').val();
                }
            }
            ,done: function(res){
                //上传完毕回调
                if(res.code == 0){
                    layer.msg('<img src="../../../static/lib/bdcui/images/success-small.png" alt="">更新成功，即将刷新页面');
                    setTimeout(function () {
                        location.reload();
                    }, 500);

                } else {
                    layer.msg('<img src="../../../static/lib/bdcui/images/warn-small.png" alt="">更新失败，请选择正确文件，填写正确路径！');
                }
            }
            ,error: function(){
                layer.msg('<img src="../../../static/lib/bdcui/images/warn-small.png" alt="">更新失败，请选择正确文件，填写正确路径！');
            }
        });
    }
});