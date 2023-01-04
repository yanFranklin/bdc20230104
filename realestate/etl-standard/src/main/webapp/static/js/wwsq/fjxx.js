/**
 * Created by Administrator on 2019/1/21.
 */
layui.use(['jquery','table','element','carousel','form','laytpl','response'], function() {
    var $ = layui.jquery,
        table = layui.table,
        element = layui.element,
        laytpl = layui.laytpl,
        response = layui.response,
        form = layui.form;

    $(function () {

        var $paramArr = getIpHz();
        var hlwxmid,wwslbh;
        if ($paramArr['hlwxmid']) {
            hlwxmid = $paramArr['hlwxmid'];
        }
        if ($paramArr['wwslbh']) {
            wwslbh = $paramArr['wwslbh'];
        }
        var imgList,
            imgListLength,
            imgIndex;

        listFiles();
        function listFiles() {
            $.ajax({
                type: "POST",
                url: getContextPath() + "/wwsq/files/all",
                data: {
                    wwslbh: wwslbh,
                    hlwxmid: hlwxmid
                },
                dataType: "json",
                success: function (data) {
                    if(isNotBlank(data)){
                        data.forEach(function (value) {
                            value.picType = value.clmc.substring(value.clmc.length - 3);
                            //格式一律转换为小写
                            value.picType = value.picType.toLowerCase();
                            if (value.fjxx) {
                                value.fjxx.forEach(function (v) {
                                    v.picType = v.fjmc.substring(v.fjmc.length - 3);
                                    //格式一律转换为小写
                                    v.picType = v.picType.toLowerCase();
                                    if(v.fjlj.indexOf("http") < 0){
                                        v.fjlj = document.location.protocol + "//" + document.location.host + "/realestate-etl/wwsq/fjxx/download?fjid=" + v.fjid;
                                    }
                                });
                            }
                        });
                        console.info(data);
                        var getFjAsideTpl = fjAsideTpl.innerHTML
                            , getFjAsideView = document.getElementById('fjLeftTree');
                        laytpl(getFjAsideTpl).render(data, function (html) {
                            getFjAsideView.innerHTML = html;
                        });
                        element.render('nav', 'fjTreeFilter');
                    }else{
                        warnMsg("未获取到附件信息");
                    }
                }, error: function (e) {
                    delAjaxErrorMsg(e,"");
                    var getFjAsideTpl = fjAsideTpl.innerHTML
                        , getFjAsideView = document.getElementById('fjLeftTree');
                    laytpl(getFjAsideTpl).render(data, function (html) {
                        getFjAsideView.innerHTML = html;
                    });
                    element.render('nav', 'fjTreeFilter');
                }
            });
        }

        //点击附件一级、二级按钮
        $('#fjLeftTree').on('click', '.bdc-fj-file', function () {
            var src = $(this).data('src');
            var type = $(this).data('type');
            var typedm = $(this).data('typedm');
            if(typedm != 0 &&$(this).parent().hasClass("layui-nav-item")){
                //根目录为文件
                imgIndex = $(this).index() + 1;
                imgListLength = 1;
                if($('#seeImgView .viewer-container').length > 0){
                    viewer.destroy();
                }else {
                    $('#seeImgView').html('');
                }
                renderImg(src,type);
            }else {
                imgIndex = 0;
                imgList = $(this).siblings('.layui-nav-child').children();
                if (imgList.length != 0) {
                    imgListLength = imgList.length;
                }
                $(this).parent().siblings().removeClass('layui-nav-itemed');
                $(this).parent().siblings().find('dd').removeClass('layui-nav-itemed');
                $('.fl-list-box').find('dd').removeClass('layui-this');
                if ($('#seeImgView .viewer-container').length > 0) {
                    viewer.destroy();
                } else {
                    $('#seeImgView').html('');
                }
                var src = '';
                // console.log(src);
                renderImg(src, '');
            }
        });

        //默认展示图片
        var image = new Image();
        var viewer;
        renderImg('','');
        // $($(imgList)[imgIndex]).parent().addClass('layui-this');
        // renderImg($($(imgList)[imgIndex]).data('src'));

        //1. 复选框相关动效
        preventBubbling();
        //1.1 监听 文件夹 复选框选择
        form.on('checkbox(folderFilter)', function (data) {
            if (!data.elem.checked) {
                $(this).parent().siblings().find('.layui-form-checkbox').removeClass('layui-form-checked')
            }
        });
        //1.2 监听 文件 复选框选择
        form.on('checkbox(fileFilter)', function (data) {
            if (data.elem.checked) {
                //选中，父元素选中
                $(this).parents('.layui-nav-child').siblings().find('.layui-form-checkbox').addClass('layui-form-checked');
            } else {
                // 没选中，看其他兄弟元素是否选中
                if ($(this).parent().parent().siblings().find('.layui-form-checkbox').hasClass('layui-form-checked')) {
                    $(this).parents('.layui-nav-child').siblings().find('.layui-form-checkbox').addClass('layui-form-checked');
                } else {
                    $(this).parents('.layui-nav-child').siblings().find('.layui-form-checkbox').removeClass('layui-form-checked');
                }
            }
        });
        //阻止点击复选框向上冒泡
        var $checkBox = $('.layui-form-checkbox');

        function preventBubbling() {
            $checkBox = $('.layui-form-checkbox');
            //阻止复选框向上冒泡
            $checkBox.on('click', function (event) {
                event.stopPropagation();
            });
        }

        //2. 监听图片侧边栏单击
        $('.fl-list-box').on('click', '.layui-nav-child dd>a', function () {
            $(this).parent().addClass('layui-this');
            imgIndex = $(this).index() + 1;
            imgList = $(this).parent().parent().children();
            imgListLength = imgList.length;
            if($('#seeImgView .viewer-container').length > 0){
                viewer.destroy();
            }else {
                $('#seeImgView').html('');
            }
            var src = $(this).data('src');
            var type = $(this).data('type');
            // console.log(src);
            renderImg(src,type);
        });
        //2.1 监听上一个 下一个
        $('.seeLeftBtn').on('click', function () {
            imgIndex--;
            if (imgIndex >= 0) {
                if ($(imgList[imgIndex]).children().length == 1) {
                    $('.fl-list-box').find('dd').removeClass('layui-this');
                    $(imgList[imgIndex]).addClass('layui-this');
                    if($('#seeImgView .viewer-container').length > 0){
                        viewer.destroy();
                    }else {
                        $('#seeImgView').html('');
                    }
                    var src = $(imgList[imgIndex]).children('a').data('src');
                    var type = $(imgList[imgIndex]).children('a').data('type');
                    renderImg(src,type);
                } else {
                    $(imgList[imgIndex]).siblings().removeClass('layui-nav-itemed');
                    $(imgList[imgIndex]).siblings().find('dd').removeClass('layui-nav-itemed');
                    $(imgList[imgIndex]).addClass('layui-nav-itemed');

                    // console.log($(imgList[imgIndex]));
                    imgList = $(imgList[imgIndex]).children('dl').children();
                    if (imgList.length != 0) {
                        imgListLength = imgList.length;
                    }
                    if($('#seeImgView .viewer-container').length > 0){
                        viewer.destroy();
                    }else {
                        $('#seeImgView').html('');
                    }
                    var src = '';
                    renderImg(src,'');
                    imgIndex = 0;
                }
            } else {
                imgIndex = 1;
                layer.msg('已经是第一张了');
            }
        });
        $('.seeRightBtn').on('click', function () {
            // console.log(imgIndex);
            if (imgIndex <= imgListLength - 1) {
                if ($(imgList[imgIndex]).children().length == 1) {
                    $('.fl-list-box').find('dd').removeClass('layui-this');
                    $(imgList[imgIndex]).addClass('layui-this');
                    if($('#seeImgView .viewer-container').length > 0){
                        viewer.destroy();
                    }else {
                        $('#seeImgView').html('');
                    }
                    var src = $(imgList[imgIndex]).children('a').data('src');
                    var type = $(imgList[imgIndex]).children('a').data('type');
                    renderImg(src,type);

                    imgIndex++;
                } else {
                    $(imgList[imgIndex]).siblings().removeClass('layui-nav-itemed');
                    $(imgList[imgIndex]).siblings().find('dd').removeClass('layui-nav-itemed');
                    $(imgList[imgIndex]).addClass('layui-nav-itemed');

                    // console.log($(imgList[imgIndex]));
                    imgList = $(imgList[imgIndex]).children('dl').children();
                    if (imgList.length != 0) {
                        imgListLength = imgList.length;
                    }
                    if($('#seeImgView .viewer-container').length > 0){
                        viewer.destroy();
                    }else {
                        $('#seeImgView').html('');
                    }
                    var src = '';
                    renderImg(src,'');
                    imgIndex = 0;
                }
            } else {
                imgIndex = imgListLength - 1;
                layer.msg('已经是最后一张了');
            }
        });

        //渲染指定图片
        function renderImg(src,type) {
            if(type == 'pdf' || type == 'lsx' || type == 'xls' || type == 'ofd' || type == 'doc' || type == 'docx'|| type == 'ofd'){
                $('#seeImgView').html('<iframe src="'+ src +'"></iframe>');

            } else if(type == 'mp4' || type == 'webm' || type == 'ogg'){
                var loadvideo = '<video width="100%" height="100%"  controls="controls" autobuffer="autobuffer"  controls="controls" loop="loop"><source src="'+ src + '"></source></video>'
                $('#seeImgView').html(loadvideo);

            }else {
                image.src = src;
                viewer = new Viewer(image, {
                    hidden: function () {
                        viewer.destroy();
                    }
                });
                viewer.show();
            }
        }

        //3. 点击左侧操作按钮
        //3.1 全选
        $('.bdc-select-all').on('click', function () {
            $checkBox.addClass('layui-form-checked');
        });
        //3.2 新增
        $('.bdc-add-btn').on('click', function (event) {
            event.stopPropagation();
            $('.bdc-add-details').toggleClass('bdc-hide');
        });
        $('body').on('click', function () {
            $('.bdc-add-details').addClass('bdc-hide');
            $('.layui-nav .bdc-prepend-li a .layui-icon-close').click();
        });
        // 3.2.1 新增文件夹
        $('.bdc-add-wjj').on('click', function (event) {
            event.stopPropagation();
            $('.fl-list-box>.layui-nav').prepend('<li class="layui-nav-item bdc-prepend-li">' +
                '<a href="javascript:;">' +
                '<input type="checkbox" name="bdcFolder" lay-filter="folderFilter" lay-skin="primary">' +
                '<img src="../image/file.png" alt="">' +
                '<input type="text" autocomplete="off" class="layui-input bdc-new-folder-name" value="新建文件夹">' +
                '<i class="layui-icon bdc-icon-add layui-icon-ok"></i>' +
                '<i class="layui-icon bdc-icon-add layui-icon-close"></i>' +
                '</a>' +
                '</li>');
            form.render('checkbox');
            $('.bdc-new-folder-name').focusEnd();
            preventBubbling();
        });
        // 3.2.1.1 点击√
        $('.pf-fl-list').on('click', '.layui-nav .bdc-prepend-li a .layui-icon-ok', function (event) {
            event.stopPropagation();
            var folderName = $('.bdc-new-folder-name').val();
            $(this).parent().append('<span>' + folderName + '</span>');
            $('.bdc-new-folder-name').remove();
            $('.bdc-icon-add').remove();
        });
        // 3.2.1.2 点击×
        $('.pf-fl-list').on('click', '.layui-nav .bdc-prepend-li a .layui-icon-close', function (event) {
            event.stopPropagation();
            $(this).parents('.bdc-prepend-li').remove();
            // $('.bdc-prepend-li').remove();
        });
        //3.2.1.3 点击输入框
        $('.pf-fl-list').on('click', '.bdc-prepend-li .bdc-new-folder-name', function (event) {
            event.stopPropagation();
        });

        // 3.3 删除
        $('.bdc-fj-delete').on('click', function () {
            layer.msg('您确认要删除吗？', {
                skin: 'bdc-del-bg',
                time: 0 //不自动关闭
                , shade: [0.4, '#000']
                , btn: ['确定', '取消']
                , yes: function (index) {
                    layer.close(index);
                    layer.msg('删除成功');
                }
            });
        });


        //焦点在input文字最后
        $.fn.setCursorPosition = function (position) {
            if (this.lengh == 0) return this;
            return $(this).setSelection(position, position);
        };

        $.fn.setSelection = function (selectionStart, selectionEnd) {
            if (this.lengh == 0) return this;
            input = this[0];

            if (input.createTextRange) {
                var range = input.createTextRange();
                range.collapse(true);
                range.moveEnd('character', selectionEnd);
                range.moveStart('character', selectionStart);
                range.select();
            } else if (input.setSelectionRange) {
                input.focus();
                input.setSelectionRange(selectionStart, selectionEnd);
            }

            return this;
        };

        $.fn.focusEnd = function () {
            if (this.val() != undefined) {
                this.setCursorPosition(this.val().length);
            }
        };
    });
});