var action = getQueryString("action");
var getDwmc = decodeURI(getQueryString("dwmc"));
var getId = getQueryString("id");
layui.use(['table','laytpl','laydate','layer', 'form'],function () {
    var $ = layui.jquery,
        layer = layui.layer,
        form = layui.form,
        laydate = layui.laydate;

    laydate.render({
        elem: '#basj'
        , type: 'datetime'
    });

    $(function () {
        if(action === "edit"){
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/dwgz/list",
                type: "GET",
                data: {'dwmc':getDwmc},
                contentType: 'application/json',
                dataType: "json",
                success: function (data) {
                    for(var i = 0; i < data.content.length; i++){
                        if(data.content[i].id == getId){
                            setData(data.content[i]);
                            break;
                        }
                    }
                },
                error:function(data){
                    fail(JSON.parse(data.responseText));
                }
            });
        }
    });
    /**
     * 表单数据提交
     */
    form.on('submit(submitBtn)', function(data) {
        if(action === "add"){
            saveData(data.field);
        }else {
            updateData(data.field);
        }
        // 禁止提交后刷新
        return false;
    });

    //点击取消
    $('.bdc-frame-close').on('click',function () {
        closeWin();
    });

    /**
     * 编辑时填充数据
     * @param data
     */
    window.setData = function (data) {
        // 这里直接用data赋值无效（原因未知），需要重新转JSON
        // var datas=JSON.parse(JSON.stringify(data));

        $('#dwmc').val(data.dwmc);
        $('#basj').val(formatDate(data.basj));
        $('#qksm').val(data.qksm);
        $('#id').val(data.id);
        $('#fjcl').val(data.fjcl);
        $('#xgry').val(data.xgry);
        $('#xgsj').val(formatDate(data.xgsj));
        // 编辑状态下不允许修改单位名称
        if(data&& data.dwmc){
            $("#dwmc").attr("disabled", "disabled");
        }

    };

    /**
     * 新增数据
     */
    window.saveData = function(data){
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/dwgz/saveDwgz",
            type: "POST",
            data: JSON.stringify(data),
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                layer.msg('<img src="../../static/lib/bdcui/images/success-small.png" alt="">提交成功',{
                        time: 1000},
                    function(){
                        //closeParentWindow();
                        closeWin();
                    }
                )
            },
            error:function(data){
                fail(JSON.parse(data.responseText));
            }
        });
    }

    /**
     * 更新数据
     */
    window.updateData = function(data){
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/dwgz/updateDwgz",
            type: "PUT",
            data: JSON.stringify(data),
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {

                layer.msg('<img src="../../static/lib/bdcui/images/success-small.png" alt="">提交成功',{
                        time: 1000},
                    function(){
                        //closeParentWindow();
                        closeWin();
                    }
                )
            },
            error:function(data){
                fail(JSON.parse(data.responseText));
            }
        });
    }

    /**
     * 关闭弹出页面
     */
    window.closeWin = function(){
        var index=parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    };

    /**
     * 提交成功提示
     */
    window.success = function(){
        saveSuccessMsg();
        setTimeout(function(){
            var index = parent.parent.parent.layer.getFrameIndex(window.name);
            parent.parent.parent.layer.close(index);
        },2000);
    }

    /**
     * 提交失败提示
     * @param data
     */
    window.fail = function(data){
        var msg = "提交失败，请检查填写内容!";
        alertMsg(msg);
    }

});