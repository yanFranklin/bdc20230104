/**
 * 复制收件材料
 */
var gzlslid = getQueryString("gzlslid");
var clfzlx = getQueryString("clfzlx");
var djxl = getQueryString("djxl");
var testData = [
    {
        clmc: '材料名称1'
    },
    {
        clmc: '材料名称2'
    },
    {
        clmc: '材料名称3'
    }, {
        clmc: '材料名称4'
    },
    {
        clmc: '材料名称5'
    },
    {
        clmc: '材料名称6'
    }
];
layui.use(['form', 'jquery', 'laydate', 'element', 'table', 'laytpl'], function () {
    var $ = layui.jquery,  form = layui.form,  table = layui.table, laytpl = layui.laytpl;

    // 收件材料信息
    var sjclData = [];
    $(function () {
        // 初始化
        init();

        // 点击搜索
        $('#search').on('click', function () {
            search();
        });

        // 点击确定
        $('#confirm').on('click', function () {
            confirm();
        });

        // 监听全选checkbox
        form.on('checkbox(qxCheckbox)', function (data) {
            var qxCheckboxStatus = data.elem.checked;

            if (qxCheckboxStatus) {
                $('#sjclTable tbody').find('tr').each(function(index, element) {
                    var $thisElem = $(element).find('input[type="checkbox"]');
                    $thisElem.each(function(index, element) {
                        $(element).attr('checked', 'checked');
                    });
                });
            } else {
                $('#sjclTable tbody').find('tr').each(function(index, element) {
                    var $thisElem = $(element).find('input[type="checkbox"]');
                    $thisElem.each(function(index, element) {
                        $(element).removeAttr('checked');
                    });
                });
            }


            form.render();
        });

        function init() {
            generateSjcl([]);
        }

        // 查询事件
        function search() {
            var slbh = $("#slbh").val();
            if (isNullOrEmpty(slbh)) {
                warnMsg("请输入受理编号！");
                return;
            }

            addModel();
            // 查询收件材料信息
            $.ajax({
                url: getContextPath() + "/slym/sjcl/slbh",
                type: 'GET',
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                data: {slbh: slbh},
                success: function (data) {
                    generateSjcl(data);
                    removeModal();
                }, error: function (xhr) {
                    delAjaxErrorMsg(xhr);
                    removeModal();
                }
            });
        }

        // 点击确认
        function confirm() {
            addModel("正在复制");
            var $trList = $('#sjclTable').find('tbody').find('tr');
            var checkedList = [];

            $trList.each(function (index, element) {
                if($(element).find('input[type="checkbox"]').length == 0){
                    removeModal();
                    ityzl_SHOW_WARN_LAYER("请至少勾选一条数据");
                    return false;
                }
                if ($(element).find('input[type="checkbox"]')[0].checked) {
                    var value = $(element).find('[name="sjclid"]').val();
                    checkedList.push(value);
                }
            });
            if (checkedList.length === 0) {
                removeModal();
                ityzl_SHOW_WARN_LAYER("请至少勾选一条数据");
                return false;
            }
            var sjcl = {};
            sjcl.gzlslid = gzlslid;
            sjcl.sjclIdList = checkedList;
            sjcl.djxl = djxl;
            var url= "/slym/sjcl/copy";
            if(clfzlx == "slymzh"){
                url= "/slym/sjcl/copyzh";
            }
            getReturnData(url, JSON.stringify(sjcl), "POST", function (data) {
                removeModal();
                ityzl_SHOW_SUCCESS_LAYER("复制成功");
                // 更新份数页数
                $.ajax({
                    url: getContextPath() + "/slym/sjcl/ys",
                    type: 'PATCH',
                    dataType: 'json',
                    data: {gzlslid: gzlslid},
                    success: function (data) {
                        if (data > 0) {
                            window.parent.loadSjcl();
                        }
                        removeModal();
                    }, error: function (xhr, status, error) {
                        delAjaxErrorMsg(xhr)
                    }
                });
            }, function (xhr) {
                removeModal();
                delAjaxErrorMsg(xhr);
            })
        }



        // 组织收件材料
        function generateSjcl(data) {
            var getTpl = sjclTpl.innerHTML
                ,view = document.getElementById('sjclView');
            laytpl(getTpl).render(data, function(html){
                view.innerHTML = html;
            });
            form.render();
            table.render();
        }


    });

    // 关闭弹出页面
    window.closeWin = function () {
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    };
});
