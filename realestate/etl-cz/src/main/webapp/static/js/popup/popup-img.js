/**
 * Created by Administrator on 2019/1/21.
 */
// 存储已读附记id
var fjIdList = [];
// checked checkbox计数
var checkedCount = 0;
var downUrlArray = [];
var imgArray = [];
//设置打印信息
var dylxArry = ["fjclhx", "fjclzx"]
var sessionKey = "fjcl";
setDypzSessionEtl(dylxArry, sessionKey);
var layer;
layui.use(['jquery', 'table', 'element', 'carousel', 'form', 'laytpl', 'response'], function () {
    var $ = layui.jquery,
        table = layui.table,
        element = layui.element,
        laytpl = layui.laytpl,
        response = layui.response,
        layer = layui.layer,
        form = layui.form;

    $(function () {

        var $paramArr = getIpHz();
        var processInstanceId;
        var ymlx;
        var url;
        if ($paramArr['processinsid']||$paramArr['processInsId']) {
            processInstanceId = $paramArr['processinsid']||$paramArr['processInsId'];
        }
        if($paramArr['ymlx']){
            ymlx = $paramArr['ymlx'];
        }
        if(isNotBlank(ymlx)){
            if(ymlx=="ylcfjxx"){
                url = getContextPath() + "/rest/v1.0/old/system/fgf/lssjcl?gzlslid="+processInstanceId+"&sflsfj="+ymlx;
            }else{
                url = getContextPath() + "/rest/v1.0/old/system/fgf/lssjcl?gzlslid="+processInstanceId+"&sflsfj=";
            }
        }else{
            url = getContextPath() + "/rest/v1.0/old/system/fetch/catalog?gzlslid="+processInstanceId;
        }

        var imgList,
            imgListLength,
            imgIndex;
        listFiles();
        function listFiles() {
            $.ajax({
                type: "POST",
                url: url,
                success: function (data) {
                    if (data && data.success){
                        data.data.forEach(function (value) {
                            // 新增是否已读字段
                            addSfyd(value);
                            value.picType = getPicType(value.suffix)
                            if (value.children) {
                                value.children.forEach(function (v) {
                                    // 新增是否已读字段
                                    addSfyd(v);
                                    //格式一律转换为小写
                                    v.picType = getPicType(v.suffix);
                                    if (v.children) {
                                        v.children.forEach(function (childV) {
                                            // 新增是否已读字段
                                            addSfyd(v);
                                            childV.picType = getPicType(childV.suffix);
                                        });
                                    }
                                });
                            }
                        });
                        var getFjAsideTpl = fjAsideTpl.innerHTML, getFjAsideView = document.getElementById('fjLeftTree');
                        laytpl(getFjAsideTpl).render(data.data, function (html) {
                            getFjAsideView.innerHTML = html;
                        });
                        form.render();
                        element.render('nav', 'fjTreeFilter');
                    }
                }, error: function (e) {
                    response.fail(e,'');
                    var getFjAsideTpl = fjAsideTpl.innerHTML
                        , getFjAsideView = document.getElementById('fjLeftTree');
                    laytpl(getFjAsideTpl).render(data, function (html) {
                        getFjAsideView.innerHTML = html;
                    });
                    element.render('nav', 'fjTreeFilter');
                }
            });
        }

        function getPicType(type){
            if(isNotBlank(type)){
                return type.toLowerCase();
            }else{
                return type;
            }
        }

        /**
         * 新增sfyd字段并根据fjIdList判断是否已读
         */
        function addSfyd(v) {
            var idList = JSON.parse(sessionStorage.getItem("fjIdList"));
            var isSame = false;
            if(isNotBlank(idList)){
                for(var i = 0;i< idList.length; i++){
                    if(v.id == idList[i].id){
                        isSame = true;
                    }
                }
            }
            if(isSame){
                v.sfyd = "1";
            }else {
                v.sfyd = "0";
            }
            return v;
        }

        //点击附件一级、二级按钮
        $('#fjLeftTree').on('click', '.bdc-fj-file', function () {
            var src = $(this).data('src');
            var type = $(this).data('type');
            var typedm = $(this).data('typedm');
            if(typedm != 0 &&$(this).parent().hasClass("layui-nav-item")){
                //根目录为文件
                if ($(this).hasClass('checkbox-select-a')) {
                    imgIndex = $(this).index();
                } else {
                    imgIndex = $(this).index() + 1;
                }
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

        // 监听checkbox，计数
        form.on('checkbox(qxCheckbox)', function(data) {
            if ($(this).is(':checked')) {
                var downUrl = $(this).nextAll('a').attr('data-src');
                downUrlArray.push(downUrl);
                checkedCount++;
            } else if (!$(this).is(':checked')) {
                checkedCount--;
            }
        });

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
            // 常州版本未查看附件名称默认显示蓝色，已查看显示灰色，正在查看显示红色
            if($(this).parent().hasClass('default-span')){
                $(this).parent().removeClass('default-span');
                var thisId = $(this).data('id')
                if(isNotBlank(JSON.parse(sessionStorage.getItem("fjIdList")))){
                    fjIdList = JSON.parse(sessionStorage.getItem("fjIdList"));
                }
                fjIdList.push({id:thisId});
                sessionStorage.setItem("fjIdList",JSON.stringify(fjIdList));
            }
            imgIndex = $(this).index() - 1;
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
                if ($(imgList[imgIndex]).children().length-2 == 1) {
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
                if ($(imgList[imgIndex]).children().length-2 == 1) {
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
            //if(type == 'pdf'){
            //    $('#seeImgView').html('<iframe src="' + getContextPath() + "/static/lib/pdf-viewer/web/viewer.html?file=" + src +'"></iframe>');
            //}
            if(type == 'pdf' || type == 'lsx' || type == 'xls' || type == 'ofd' || type == 'doc' || type == 'docx'|| type == 'ofd'){
                $('#seeImgView').html('<iframe src="'+ src +'"></iframe>');
            } else {
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


function plPrint(dylx) {
    // 如果有checkbox被选中
    if (checkedCount >= 1) {
        // 打印方法
        var downUrlList = [];
        $("input[name='qxTable']").each(function (index, item) {
            var downUrlObj = {};
            if (item.checked) {
                downUrlObj["downUrl"] = $(item).parents('dd').find("a").attr('data-src');
                if(downUrlObj["downUrl"].indexOf(document.location.protocol + "//") === -1) {
                    downUrlObj["downUrl"] = getIP() + downUrlObj["downUrl"];
                }
                downUrlList.push(downUrlObj);
            }
        })
        printSjcl(downUrlList, dylx);
    } else if (checkedCount < 1) {
        // 如果没有checkbox被选中
        warnMsg('请先勾选需要打印的数据!');
    }
}

function imgPrint(data) {
    if (checkedCount >= 1){
        var imgSrc = {};
        $("input[name='qxTable']").each(function (index,item) {
            if (item.checked) {
                var type = $(item).parents('dd').find("a").attr('data-type');
                if (type == 'jpg' || type == 'png'){
                    imgSrc.push($(item).parents('dd').find("a").attr('data-src'));
                }
            }
        });
        PrintImage(imgSrc);
    }else if (checkedCount < 1){
        // 如果没有checkbox被选中
        warnMsg('请先勾选需要打印的数据!');
    }
}

function printSjcl(downUrlList, dylx) {
    //每次打印之前把勾选的数据存到redis，返回key，时长 20s
    var key = "";
    $.ajax({
        type: "POST",
        url: getContextPath() + "/rest/v1.0/old/system/sjcl/redis",
        data: JSON.stringify(downUrlList),
        contentType: 'application/json',
        success: function (data) {
            if (isNotBlank(data)) {
                key = data;
                //根据redis的key值获取xml数据
                var modelUrl = getIP() + "/realestate-etl/static/printmodel/fjcl.fr3";
                var dataUrl = getIP() + "/realestate-etl/rest/v1.0/old/system/print?key=" + key;
                console.log("打印老系统附件dataUrl=" + dataUrl);
                printChoice(dylx, "etl-app", dataUrl, modelUrl, false, sessionKey);
            }
        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
        }
    })
}