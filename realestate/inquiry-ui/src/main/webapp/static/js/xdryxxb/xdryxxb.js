var id = getQueryString('id');
var laydate;
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl', 'laydate'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        element = layui.element,
        form = layui.form,
        table = layui.table,
        laytpl = layui.laytpl;
    laydate = layui.laydate;
    $(function () {
        loadXdryxxb();

        form.render();

        function loadXdryxxb() {
            // 添加遮罩
            addModel();

            // 请查询数据
            if (isNotBlank(id)) {
                $.ajax({
                    url: "/realestate-inquiry-ui/rest/v1.0/xdryxx",
                    type: 'GET',
                    dataType: 'json',
                    async: false,
                    data: {id: id},
                    success: function (data) {
                        if (isNotBlank(data)) {
                            renderXdryxxb(data);
                            removeModal();
                        }
                    },
                    complete: function () {
                        removeModal();
                    }
                });
            } else {
                renderXdryxxb({});
                removeModal();
            }


        }

        form.on("submit(updateData)", function (data) {
            // 获取弹窗数据
            var updatedData = data.field;

            // 保存到接口
            saveData(updatedData);
            return false;
        });

        // 渲染数据
        function renderXdryxxb(json) {
            var tpl = xdryxxbTpl.innerHTML, view = document.getElementById('bdc-popup-large');
            laytpl(tpl).render(json, function (html) {
                view.innerHTML = html;
            });
            renderDate("yyyy-MM-dd");
            form.render();
        }

        function saveData(data) {
            addModel("正在保存");
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/xdryxx",
                type: 'PATCH',
                dataType: 'json',
                contentType: "application/json",
                data: JSON.stringify(data),
                success: function (data) {
                    ityzl_SHOW_SUCCESS_LAYER("保存成功");
                    setTimeout(function () {
                        window.parent.$("#search").click();
                        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                        parent.layer.close(index); //再执行关闭
                    }, 2000)
                    removeModal();
                },
                error: function (e) {
                    removeModal();
                    delAjaxErrorMsg(e);
                }
            });
            return false;
        }

    });
});

function cancel() {
    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
    parent.layer.close(index); //再执行关闭
}

