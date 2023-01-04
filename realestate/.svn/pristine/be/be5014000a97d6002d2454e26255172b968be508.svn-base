var zkxxid = getQueryString('zkxxid');
var zrzklx = getQueryString('zrzklx');
var zdList = {};
layui.use(['jquery', 'form', 'laydate'], function () {
    var $ = layui.jquery,
        form = layui.form,
        laydate = layui.laydate;

    $(function () {

        /**
         * 字典
         */
        getZd(function (data) {
            // 渲染字典项
            $.each(data.cdlx, function (index, item) {
                $('#cdlx').append('<option value="' + item.DM + '">' + item.MC + '</option>');
            });

            // $.each(data.cylx, function (index, item) {
            //     $('#cylx').append('<option value="' + item.DM + '">' + item.MC + '</option>');
            // });


            form.render('select');
        });

        /**
         * 获取字典
         * @param callback 回调函数
         */
        function getZd(callback) {
            var zdList = getZdList();
            if (zdList && zdList.djlx) {
                callback(zdList);
            }
        }



        //动态设置title
        var $title = $('.bdc-title');
        for (var i = 0; i < $title.length; i++) {
            $($title[i]).attr('title', $($title[i]).val());
        }

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
                form.val('form', data);
            },
            error: function (xhr, status, error) {

            }
        });


        $('#saveTable').on('click', function () {
            var tableData = {};
            $('.cy').each(function (index, element) {
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


    });

});

