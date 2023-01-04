var zkxxid = getQueryString('zkxxid');
var zrzklx = getQueryString('zrzklx');
var zdList = {};
layui.use(['jquery', 'form', 'laydate'], function () {
    var $ = layui.jquery,
        form = layui.form,
        laydate = layui.laydate;

    $(function () {
        //动态设置title
        var $title = $('.bdc-title');
        for (var i = 0; i < $title.length; i++) {
            $($title[i]).attr('title', $($title[i]).val());
        }
        // lay('.test-item').each(function () {
        //     laydate.render({
        //         elem: '.test-item',
        //         trigger: 'click',
        //         type: 'date'
        //     });
        //     //日期不可使用
        //     laydate.render({
        //         elem: '#dateDisable',
        //         value: '2019-01-16'
        //         , trigger: 'click'
        //     });
        // });

        //滚动时头部固定
        var $contentTitle = $('.bdc-content-fix:visible');
        console.log($contentTitle);
        if ($contentTitle.length != 0) {
            defaultStyle();
        }

        function defaultStyle() {
            if ($(window).scrollTop() > 10) {
                $contentTitle.css('top', '0');
            } else if ($(window).scrollTop() <= 10) {
                $contentTitle.css('top', '10px');
            }
        }

        $(window).resize(function () {
            if ($contentTitle.length != 0) {
                defaultStyle();
            }
        });
        $(window).on('scroll', function () {
            if ($contentTitle.length != 0) {
                if ($(this).scrollTop() > 10) {
                    $contentTitle.css('top', '0');
                } else if ($(this).scrollTop() <= 10) {
                    $contentTitle.css('top', '10px');
                }
            }
        });

        //测试复选框使用
        // console.log($('#djLine').length);
        if ($('#djLine').length == 1) {
            var combotreeData = [
                {id: "100", text: "首次登记"},
                {id: "200", text: "转移登记"},
                {id: "300", text: "变更登记"},
                {id: "400", text: "注销登记"},
                {id: "500", text: "更正登记"},
                {id: "600", text: "异议登记"},
                {id: "700", text: "预告登记"},
                {id: "800", text: "查封登记"}
            ];
            $('#djLine').combotree({
                valueField: "id", //Value字段
                textField: "text", //Text字段
                multiple: true,
                data: combotreeData,
                onCheck: function (record) {
                    console.log(record);
                    console.log(record.checkState);
                }
            });
        }

        $('td input').on('focus', function () {
            if ($(this).hasClass('layui-form-danger')) {
                $(this).removeClass('layui-form-danger');
            }
            if ($(this).parents('.layui-form-select').siblings().hasClass('layui-form-danger')) {
                $(this).parents('.layui-form-select').siblings().removeClass('layui-form-danger');
            }
        });
        $('.xm-input').on('click', function () {
            if ($(this).parent().siblings('.xm-hide-input').hasClass('layui-form-danger')) {
                $(this).parent().siblings('.xm-hide-input').removeClass('layui-form-danger');
            }
        });

        $.ajax({
            url: '/realestate-natural-ui/rest/v1.0/ywxx/zrzkxx/' + zkxxid + '/' + zrzklx,
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                console.log('详情：');
                console.log(data);
                form.val('form', data);

                laydate.render({
                    elem: '#clgsjzr',
                    trigger: 'click',
                    type: 'date',
                    value: Format(data.clgsjzr, 'yyyy-MM-dd')
                });

            },
            error: function (xhr, status, error) {

            }
        });

        // 加载字典
        loadZdList();

        generateSelect(['kczhfs', 'cljldw']);

        function evalFormElem(containerId, elemId, elemVal) {
            $('#' + containerId).find('#' + elemId).val(elemVal);
            // $('#' + containerId).find('#' + elemId).attr('disabled', 'off');
            // 移除下拉框图标
            // $('#' + containerId).find('#' + elemId).next().find('.layui-edge').remove();
            form.render();
        }

        $('#saveTable').on('click', function () {
            var tableData = {};
            $('.tm').each(function (index, element) {
                var elemId = $(element).attr('id');
                var elemVal = $(element).val();
                tableData[elemId] = elemVal;
            });
            tableData.zkxxid = zkxxid;

            console.log('保存的数据：');
            console.log(tableData);
            addModel();
            $.ajax({
                url: '/realestate-natural-ui/rest/v1.0/ywxx/zrzkxx/' + zrzklx,
                type: 'PATCH',
                dataType: 'json',
                contentType: 'application/json;charset=utf-8',
                data: JSON.stringify(tableData),
                success: function (data) {
                    console.log('保存成功，返回：');
                    console.log(data);
                    ityzl_SHOW_SUCCESS_LAYER("保存成功");
                    if (!isNullOrEmpty(data)) {

                    }
                    removeModal();
                },
                error: function (xhr, status, error) {
                    ityzl_SHOW_WARN_LAYER("保存失败");
                    console.log(error);
                    removeModal();
                },
                complete: function (xhr, status) {
                    // 刷新父页面
                    setTimeout(function () {
                        parent.refreshSlym();
                    }, 1000);
                }
            });

            return false;
        });

        function loadZdList() {
            $.ajax({
                url: '/realestate-natural-ui/rest/v1.0/ywxx/zd',
                type: 'GET',
                dataType: 'json',
                async: false,
                success: function (data) {
                    console.log('zdList:');
                    console.log(data);
                    if (data) {
                        zdList = data;
                    }
                },
                error: function (xhr, status, error) {
                }
            });
        }

        function generateSelect(idArr) {
            idArr.forEach(function (id, index) {
                var zd = zdList[id];
                var $dom = $('#' + id);

                $dom.append('<option value="">' + '请选择' + '</option>');
                zd.forEach(function (elem, index) {
                    $dom.append('<option value="' + elem.DM + '">' + elem.MC + '</option>');
                });
            });

            form.render();
        }

    });

});

