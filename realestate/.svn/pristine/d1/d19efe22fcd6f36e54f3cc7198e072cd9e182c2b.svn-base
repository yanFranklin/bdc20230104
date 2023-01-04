/**
 * Created by Administrator on 2019/1/21.
 */
layui.use(['jquery','element','carousel','form','laytpl'], function() {
    var $ = layui.jquery,
        element = layui.element,
        laytpl = layui.laytpl,
        form = layui.form;

    $(function () {

        var imgList,
            imgListLength,
            imgIndex;

        $('#search').on('click', function () {
            // 获取查询条件
            var slbh = $('#slbh').val();
            // 判空
            if (!isNotBlank(slbh)) {
                warnMsg('请输入必要查询条件');
                return false;
            }
            listFiles(slbh);
        });

        function listFiles(slbh) {
            $.ajax({
                type: "GET",
                url: getContextPath() + "/rest/v1.0/fjcx/allfiles/" + slbh,
                data: {
                    clientId: "clientId",
                    name : "人脸识别",
                },
                success: function (data) {
                    data.forEach(function (value) {
                        var  index = value.name.lastIndexOf( "." );
                        value.picType= value.name.substring(index + 1, value.name.length);
                        //格式一律转换为小写
                        value.picType = value.picType.toLowerCase();
                        if (value.children) {
                            value.children.forEach(function (v) {
                                var  index = v.name.lastIndexOf( "." );
                                v.picType= v.name.substring(index + 1, v.name.length);
                                //格式一律转换为小写
                                v.picType = v.picType.toLowerCase();
                                if (v.children) {
                                    v.children.forEach(function (childV) {
                                        var  index = childV.name.lastIndexOf( "." );
                                        childV.picType= childV.name.substring(index + 1, childV.name.length);
                                        childV.picType = childV.picType.toLowerCase();
                                    });
                                }
                            });
                        }
                    });
                    var getFjAsideTpl = fjAsideTpl.innerHTML
                        , getFjAsideView = document.getElementById('fjLeftTree');
                    laytpl(getFjAsideTpl).render(data, function (html) {
                        getFjAsideView.innerHTML = html;
                    });
                    element.render('nav', 'fjTreeFilter');
                }, error: function (e) {
                    showErrorInfo(e, "");
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
                renderImg(src, '');
            }
        });

        //默认展示图片
        var image = new Image();
        var viewer;
        renderImg('','');

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
            renderImg(src,type);
        });
        //2.1 监听上一个 下一个
        $('.seeLeftBtn').on('click', function () {
            imgIndex--;
            if (imgIndex >= 0) {
                if ($(imgList[imgIndex]).children().length == 1) {
                    $('.fl-list-box').find('dd').removeClass('layui-this');
                    $(imgList[imgIndex]).addClass('layui-this');
                    if ($('#seeImgView .viewer-container').length > 0) {
                        viewer.destroy();
                    }else {
                        $('#seeImgView').html('');
                    }
                    var src = $(imgList[imgIndex]).children('a').data('src');
                    var type = $(imgList[imgIndex]).children('a').data('type');
                    renderImg(src, type);
                } else {
                    $(imgList[imgIndex]).siblings().removeClass('layui-nav-itemed');
                    $(imgList[imgIndex]).siblings().find('dd').removeClass('layui-nav-itemed');
                    $(imgList[imgIndex]).addClass('layui-nav-itemed');

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
            if (imgIndex <= imgListLength - 1) {
                if ($(imgList[imgIndex]).children().length == 1) {
                    $('.fl-list-box').find('dd').removeClass('layui-this');
                    $(imgList[imgIndex]).addClass('layui-this');
                    if ($('#seeImgView .viewer-container').length > 0) {
                        viewer.destroy();
                    } else {
                        $('#seeImgView').html('');
                    }
                    var src = $(imgList[imgIndex]).children('a').data('src');
                    var type = $(imgList[imgIndex]).children('a').data('type');
                    renderImg(src, type);

                    imgIndex++;
                } else {
                    $(imgList[imgIndex]).siblings().removeClass('layui-nav-itemed');
                    $(imgList[imgIndex]).siblings().find('dd').removeClass('layui-nav-itemed');
                    $(imgList[imgIndex]).addClass('layui-nav-itemed');

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
            if(type =='docx'){
                //WORD 转换成PDF查看
                src =getContextPath() + "/rest/v1.0/files/wordToPdf/download?downUrl="+encodeURIComponent(src)+"&type="+type;
                type ="pdf";
            }
            if(type == 'pdf'){
                //PDF引用pdf.js 预览,不用借助插件
                $('#seeImgView').html('<iframe src="' + getContextPath() + "/static/lib/pdf/web/viewer.html?file=" + encodeURIComponent(src) +'"></iframe>');
            }else if(type == 'lsx' || type == 'xls' || type == 'ofd' || type == 'doc' || type == 'ofd'){
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