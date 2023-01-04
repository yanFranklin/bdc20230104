/**
 * @author <a href ="mailto:hanyi@gtmap.cn">hanyi</a>
 * @description 规则例外原因填写页面
 */
var gzlslid = $.getUrlParam('gzlslid');
layui.use(['jquery', 'table', 'element', 'form', 'laytpl'], function () {
    var $ = layui.jquery,
        table = layui.table,
        laytpl = layui.laytpl,
        response = layui.response,
        form = layui.form;

    $(function () {

        function renderZfTable(data, col) {
            table.render({
                elem: '#checkboxTable',
                id: 'checkBoxTableId',
                cols: col,
                data: data
            });
        }

        //点击退回
        $('.bdc-save-btn').on('click', function () {
            var success = '保存成功！';
            var lwyy = $('#lwyy').val();

            if (isNullOrEmpty(lwyy)) {
                layer.msg('请输入例外原因');
                return;
            }
            addModel('保存中');
            $.ajax({
                type: "POST",
                dataType: 'json',
                url: getContextPath() + "/bdcGzyz/lwyy",
                data: {
                    lwyy: lwyy,
                    gzlslid: gzlslid
                },
                success: function () {
                    layer.msg(success);
                    setTimeout(function () {
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.location.reload();
                        parent.layer.close(index);
                    }, 1000);
                }, error: function (e) {
                    delAjaxErrorMsg(e);
                }, complete: function () {
                    removeModal();
                }
            });
            /// }
        });

        /**
         * 关闭弹出页面
         */
        $('.bdc-save-cancel').on('click', function () {
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        })

    });
});

