/**
 * 常州： 抵押查询、查封查询、异议查询  通用查询条件方法
 */
// 高级查询控制参数
var searchData, needsearch;

layui.use(['form', 'jquery', 'element', 'table'], function () {

    var $ = layui.jquery, form = layui.form, table = layui.table;

    $(function () {

        var moduleCode = $.getUrlParam('moduleCode');

        // 重置清空查询条件
        $('#reset').on('click', function () {
            $("#bdcdyh").val(null);
            $("#ycqzh").val(null);
            $("#zh").val(null);
            $("#fjh").val(null);
            $("#slbh").val(null);
            $("#fwbh").val(null);
            $("#zhlsh").val(null);
            $("#zsyzh").val(null);
            $("#ghyt").val(null);
        });

        // 高级查询
        $('#moreSearch').on('click', function () {
            needsearch = true;

            layer.open({
                title: '高级查询',
                type: 2,
                area: ['960px', '500px'],
                content: 'moreSearch.html?moduleCode='+ moduleCode,
                success: function (layero, index) {
                    var obj = {};
                    $(".search").each(function (i) {
                        var name = $(this).attr('name');
                        var value = $(this).val();
                        if (value) {
                            value = value.replace(/\s*/g, "");
                        }
                        obj[name] = value;
                    });

                    if(isNotBlank(obj.ywrmc)){
                        obj.ywr = obj.ywrmc;
                    }
                    // 传递查询参数
                    var iframe = window['layui-layer-iframe' + index];
                    iframe.setSearchData(obj);
                },
                end: function () {
                    if (needsearch && searchData) {
                        addModel();
                        // 高级查询需要清空勾选缓存
                        layui.sessionData("checkedData", null);
                        // IP地址
                        searchData.ipaddress = ipaddress;
                        // 版本标识
                        searchData.version = 'changzhou';

                        reloadPageTable(searchData);
                    }
                }
            });
        });

    });

    // 高级查询传值
    window.setSearchData = function (data, flag) {
        if(isNotBlank(data)){
            if(isNotBlank(data.ywrmc)){
                data.ywr = data.ywrmc;
            }
            if(isNotBlank(data.fwbh)){
                data.bdcdywybh = data.fwbh;
            }
        }
        searchData = data;
        searchParam = data;
        needsearch = flag;
        if (searchData) {
            form.val('searchform', JSON.parse(JSON.stringify(searchData)));
        }
    }

});


