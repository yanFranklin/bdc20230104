/**
 * Created by yousiyi on 2021/3/22.
 */
//设置打印信息
var dylxArry = ["fjcl"]
var sessionKey = "fjcl";
setDypzSession(dylxArry, sessionKey);
layui.use(['jquery','table','element','carousel','form','laytpl','response','layer'], function() {
    var $ = layui.jquery,
        table = layui.table,
        element = layui.element,
        laytpl = layui.laytpl,
        response = layui.response,
        form = layui.form;
    var layer = layui.layer;
    var currentMl;
    var currentFile;
    var current$fjmlElem;
    $(function () {

        var $paramArr = getIpHz();
        var processInstanceId;
        var slbh;
        if ($paramArr['processinsid']) {
            processInstanceId = $paramArr['processinsid'];
        }
        if ($paramArr['slbh']) {
            slbh = $paramArr['slbh'];
        }
        //需要隐藏的文件夹名
        var hideWjjList =[];
        //需要隐藏的文件名
        var hideFileNameList =[];
        var imgList,
            imgListLength,
            imgIndex=0;

        // 记录操作顺序
        var $backList=[];
        //展示view 关闭按钮
        var viewHide =false;
        listFiles();

        var hidewjjArr = [];
        $.ajax({
            type: "GET",
            url: getContextPath() + "/rest/v1.0/files/hidewjj",
            async:false,
            success: function (data) {
                // data = ['涉税材料'];
                hidewjjArr = data;
                // console.log('隐藏的文件：');
                // console.log(data);

                hideWjjList = [];
            }, error: function (e) {
                console.log(e);
            }
        });

        function listFiles() {
            var spaceId;
            if (!isNullOrEmpty(processInstanceId)) {
                spaceId = processInstanceId;
            } else {
                spaceId = slbh;
            }
            $.ajax({
                type: "GET",
                url: getContextPath() + "/rest/v1.0/files/allinone",
                data: {
                    clientId: "clientId",
                    spaceId: spaceId
                },
                success: function (data) {
                    if(data.children) {
                        data.children.forEach(function (value) {
                            // console.log(value);
                            // console.log(hideWjjList);
                            // 显示一级文件夹
                            if(hideWjjList.length >0 &&hideWjjList.indexOf(value.name) >-1){
                                hideFileNameList.push(value.name);
                            }
                            // 增加文件计数属性
                            if(value.type != 0){
                                var  index = value.name.lastIndexOf( "." );
                                value.picType= value.name.substring(index + 1, value.name.length);
                                //value.picType = value.name.substring(value.name.length - 3);
                                //格式一律转换为小写
                                value.picType = value.picType.toLowerCase();
                                value.imgIndex = imgIndex;
                                imgIndex++;
                            }
                            if (value.children) {
                                value.children.forEach(function (v) {
                                    if(hideWjjList.length >0 &&(hideWjjList.indexOf(value.name) >-1 ||hideWjjList.indexOf(v.name) >-1)){
                                        hideFileNameList.push(v.name);
                                    }
                                    // 增加文件计数属性
                                    if(v.type != 0){
                                        var  index = v.name.lastIndexOf( "." );
                                        v.picType= v.name.substring(index + 1, v.name.length);
                                        // v.picType = v.name.substring(v.name.length - 3);
                                        //格式一律转换为小写
                                        v.picType = v.picType.toLowerCase();
                                        v.imgIndex = imgIndex;
                                        imgIndex++;
                                    }
                                    if (v.children) {
                                        v.children.forEach(function (childV) {
                                            if(hideWjjList.length >0 &&(hideWjjList.indexOf(value.name) >-1 ||hideWjjList.indexOf(v.name) >-1 ||hideWjjList.indexOf(childV.name) >-1)){
                                                hideFileNameList.push(childV.name);
                                            }
                                            // 增加文件计数属性
                                            if(childV.type != 0){
                                                var  index = childV.name.lastIndexOf( "." );
                                                childV.picType= childV.name.substring(index + 1, childV.name.length);
                                                //childV.picType = childV.name.substring(childV.name.length - 3);
                                                childV.picType = childV.picType.toLowerCase();
                                                childV.imgIndex = imgIndex;
                                                imgIndex++;
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                    // console.log('hideFileNameList');
                    // console.log(hideFileNameList);
                    var getFjAsideTpl = fjAsideTpl.innerHTML
                        , getFjAsideView = document.getElementById('fjLeftTree');
                    laytpl(getFjAsideTpl).render(data , function (html) {
                        getFjAsideView.innerHTML = html;
                    });
                    element.render('nav', 'fjTreeFilter');
                    // 默认展开目录
                    $('.fj-ml').click();
                    $('.bdc-show-all').click();

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

        //默认展示图片
        var image = new Image();
        var viewer;
        renderImg('','');
        // $($(imgList)[imgIndex]).parent().addClass('layui-this');
        // renderImg($($(imgList)[imgIndex]).data('src'));

        // 目录全部展开
        $('.bdc-operate-btn').on('click','.bdc-show-all', function () {
            // 根目录展开
            if(!$('.fj-ml').parent().hasClass('layui-nav-itemed')){
                $('.fj-ml').parent().addClass('layui-nav-itemed');
            }
            $('.fl-list-box').find('dd').addClass('layui-nav-itemed');
        });

        // 目录全部收起
        $('.bdc-operate-btn').on('click','.bdc-hide-all', function () {
            // 根目录展开
            if($('.fj-ml').parent().hasClass('layui-nav-itemed')){
                $('.fj-ml').parent().removeClass('layui-nav-itemed');
            }
            $('.fl-list-box').find('dd').removeClass('layui-nav-itemed');
        })

        //点击附件一级、二级按钮
        $('#fjLeftTree').on('click', '.bdc-fj-file', function () {
            // 记录操作
            // if($('.fl-list-box').find('a.layui-this').length>0){
            //     $backList.push($('.fl-list-box').find('a.layui-this'));
            // }

            $('.fl-list-box').find('dd').removeClass('layui-this');
            $('.fl-list-box').find('a').removeClass('layui-this');
            $(this).addClass('layui-this');
            if($('#seeImgView .viewer-container').length > 0){
                viewer.destroy();
            }else {
                $('#seeImgView').html('');
            }
            renderImgslt($(this));

        });

        //2. 监听图片侧边栏单击
        $('.fl-list-box').on('click', '.layui-nav-child dd>a', function () {
            var src = $(this).data('src');
            var type = $(this).data('type');
            var index = $(this).data('index');
            var id = $(this).data('id');

            // 记录操作
            // if($('.fl-list-box').find('a.layui-this').length>0){
            //     $backList.push($('.fl-list-box').find('a.layui-this'));
            // }

            $('.fl-list-box').find('dd').removeClass('layui-this');
            $('.fl-list-box').find('a').removeClass('layui-this');
            $(this).addClass('layui-this');

            if(!isNullOrEmpty(type)) {
                viewHide=false;
                if($('#seeImgView .viewer-container').length > 0){
                    viewer.destroy();
                }else {
                    $('#seeImgView').html('');
                }
                console.log(src);
                if(index != undefined){
                    currentFile = id;
                    renderImg(src, type);
                }else {
                    renderImgslt($(this));
                }
            }
        });

        //监听缩略图
        $('#seeImgView').on('click', '.slt-img', function (){
            if($('#seeImgView .viewer-container').length > 0){
                viewer.destroy();
            }
            var src = $(this).data('src');
            var type = $(this).data('type');
            var index = $(this).data("index");
            var id = $(this).data("id");

            viewHide=true;

            $('.fl-list-box').find('dd').removeClass('layui-this');
            $('.fl-list-box').find('a').removeClass('layui-this');
            // 根目录展开
            if(!$('.fj-ml').parent().hasClass('layui-nav-itemed')){
                $('.fj-ml').parent().addClass('layui-nav-itemed');
            }

            if(index!=undefined){
                // 对应目录展开
                var $thisImg = $('.fl-list-box').find('a[data-index='+ index +']');
                $thisImg.parents('dd').addClass('layui-nav-itemed');
                $thisImg.addClass('layui-this');
                // 点击文件
                currentFile = id;
                renderImg(src, type);
            }else {
                // 点击文件夹
                $('.layui-nav-tree .bdc-fj-file').each(function (index,item) {
                    if($(item).data('src')== src){
                        // 对应目录展开
                        $(item).parents('dd').addClass('layui-nav-itemed');
                        $(item).addClass('layui-this');
                        renderImgslt($(item));
                        return false;
                    }
                })
            }

        });

        // 监听 后退 按钮
        $('.bdc-operate-btn').on('click', '.bdc-back', function () {

        });

        //2.1 监听上一个 下一个
        $('.seeLeftBtn').on('click', function () {
            // 获取当前展示文件或文件夹
            var $now = $('.fl-list-box').find('a.layui-this');
            var index = $now.data('index');

            if(index != undefined){
                if(index==0){
                    layer.msg('已经是第一张了');
                }else {
                    $('.fl-list-box').find('dd').removeClass('layui-this');
                    $('.fl-list-box').find('a').removeClass('layui-this');
                    if($('#seeImgView .viewer-container').length > 0){
                        viewer.destroy();
                    }else {
                        $('#seeImgView').html('');
                    }

                    $('.layui-nav-tree a').each(function (i,item) {

                        if($(item).data('index')== index-1){
                            $(item).addClass('layui-this');
                            var src = $(item).data('src');
                            var type = $(item).data('type');
                            var id = $(item).data('id');
                            if(!isNullOrEmpty(type)) {
                                $(item).parents('dd').addClass('layui-nav-itemed');
                                currentFile = id;
                                renderImg(src, type);
                            }
                            return false;
                        }
                    });
                }
            }
        });

        $('.seeRightBtn').on('click', function () {
            // 获取当前展示文件或文件夹
            var $now = $('.fl-list-box').find('a.layui-this');
            var index = $now.data('index');

            if(index != undefined){
                if(index == imgIndex-1){
                    layer.msg('已经是最后一张了');
                }else {
                    $('.fl-list-box').find('dd').removeClass('layui-this');
                    $('.fl-list-box').find('a').removeClass('layui-this');
                    if($('#seeImgView .viewer-container').length > 0){
                        viewer.destroy();
                    }else {
                        $('#seeImgView').html('');
                    }

                    $('.layui-nav-tree a').each(function (i,item) {

                        if($(item).data('index')== index+1){
                            $(item).addClass('layui-this');
                            var src = $(item).data('src');
                            var type = $(item).data('type');
                            var id = $(item).data('id');
                            if(!isNullOrEmpty(type)) {
                                $(item).parents('dd').addClass('layui-nav-itemed');
                                currentFile = id;
                                renderImg(src, type);
                            }
                            return false;
                        }
                    })
                }
            }
        });

        //渲染指定图片,hide:是否展示右上角关闭按钮
        function renderImg(src,type,hide) {
            if(type =='docx'){
                //WORD 转换成PDF查看
                src =getContextPath() + "/rest/v1.0/files/wordToPdf/download?downUrl="+encodeURIComponent(src)+"&type="+type;
                type ="pdf";
            }
            if(type == 'pdf'){
                //PDF引用pdf.js 预览,不用借助插件
                $('#seeImgView').html('<iframe src="' + getContextPath() + "/static/lib/pdf/web/viewer.html?file=" + encodeURIComponent(src) +'"></iframe>');
            } else if(type == 'lsx' || type == 'xls' || type == 'doc'){
                $('#seeImgView').html('<iframe src="'+ src +'"></iframe>');
            } else if(type == 'mp4' || type == 'webm' || type == 'ogg'){
                var loadvideo = '<video width="100%" height="100%"  controls="controls" autobuffer="autobuffer"  controls="controls" loop="loop"><source src="'+ src + '"></source></video>';
                $('#seeImgView').html(loadvideo);

            } else if(type == 'ofd'){
                // var url = getContextPath() + "/static/ofdshow/viewer.html?" + src;
                // $('#seeImgView').html('<iframe src="'+ url +'"></iframe>');
                getReturnData("/rest/v1.0/config/pz", {}, "GET", function (data) {
                    if(data && data.ofdSfzxyl == false){
                        $('#seeImgView').html('<iframe src="'+ src +'"></iframe>');
                    }else {
                        if(isNotBlank(src)){
                            var tmp = src.split('/');
                            var storageid = tmp[tmp.length-1];
                            var url = getContextPath() + "/view/popup-ofd.html?storageid="+storageid+"&url="+src;
                            $('#seeImgView').html('<iframe src="'+ url +'"></iframe>');
                        }
                    }
                },true);
            } else {
                image.src = src;
                viewer = new Viewer(image, {
                    hidden: function () {
                        viewer.destroy();
                        if(viewHide){
                            renderImgslt($(document).find(".layui-nav-itemed").find(".fileslt-show"));
                        }

                    }
                });
                viewer.show();
                // $('.fl-list-box').find('dd').removeClass('layui-this');
                // $('.fl-list-box').find('a').removeClass('layui-this');

                // if(viewHide){
                // $(".bdc-operate-btn .bdc-back").show();
                // }
            }
        }

        //渲染缩略图
        function renderImgslt($fjmlElem){
            qxys();
            currentFile = null;
            // console.log('函数内hideFileNameList');
            // console.log(hideFileNameList);
            $(".fileslt-show").removeClass("fileslt-show");
            $($fjmlElem).addClass("fileslt-show");
            //当前展示的目录
            currentMl = $($fjmlElem).attr('data-id');
            current$fjmlElem = $fjmlElem;
            var $childCollection = $fjmlElem.siblings('.layui-nav-child').find("a");
            // 初始化文件信息数组
            var fileInfoArr = [];
            // 遍历所有的dd
            $childCollection.each(function (index, element) {
                var fileInfo = {};
                // 遍历目录下所有的a标签并将a标签的data-src值和span中的text存入数组中作为一个对象
                fileInfo.fileName = $(element).find('span').text();
                fileInfo.fileUrl = $(element).attr('data-src');
                fileInfo.type = $(element).attr('data-type');
                fileInfo.imgIndex = $(element).attr('data-index');
                fileInfo.id = $(element).attr('data-id');
                // console.log('fileInfo');
                // console.log(fileInfo);
                // console.log('hideFileNameList');
                // console.log(hideFileNameList);
                if (!isNullOrEmpty(fileInfo.fileUrl)) {
                    if ((isNullOrEmpty(fileInfo.type) && $(element).siblings(".layui-nav-child").length === 0) || (hideFileNameList.length > 0 && hideFileNameList.indexOf(fileInfo.fileName) > -1 )) {
                        //空文件夹以及配置隐藏的文件夹及其附件过滤
                    } else {
                        // console.log(fileInfo);
                        fileInfoArr.push(fileInfo);
                    }
                }
            });
            // console.log('显示的文件');
            // console.log(fileInfoArr);
            var previewTpl = filePreviewTpl.innerHTML;
            var view = document.getElementById('seeImgView');
            laytpl(previewTpl).render(fileInfoArr, function (html) {
                view.innerHTML = html;
            });

            // 根据hidewjjArr隐藏目录和其文件
            hidewjjArr.forEach(function(value, index) {
                var wjjName = value;
                $('#seeImgView').find('p').each(function(index, item) {
                    if ($(item).text() === wjjName) {
                        // 隐藏当前目录
                        var $cur = $(this).parents('div.file-slt');
                        $cur.hide();
                        // 基于目录和目录下的文件是平铺的，隐藏该目录下的文件
                        while ($cur.next().hasClass('file-slt') && $cur.next().find('img').attr('data-type') != 'undefined') {
                            $cur.next().hide();
                            $cur = $cur.next('div.file-slt');
                        }
                    }
                });
            });

        }
        //上传
        $(document).on('click','#sc',function () {
            // gzlslid: processInstanceId,
            //     parentId: currentMl
            if(isNullOrEmpty(currentMl)){
                currentMl = "";
            }
            layer.open({
                type: 2,
                title: '文件上传',
                anim: -1,
                shadeClose: true,
                maxmin: true,
                shade: false,
                area: ['960px', '600px'],
                offset: 'auto',
                content: [ "popup-upload.html?processinsid="+processInstanceId+"&parentId="+currentMl, 'yes'],
                end:function(){
                    location.reload();
                }
            });
        });
        //压缩-- 将勾选框放出
        $(document).on('click','#ys',function () {
            $(".xzCheckbox").show();
            $(".bdc-operate-btn").find("button").hide();
            $(".yscz").show();
            $(".qxwj").show();
        });
        $(document).on('click','#qxys',function () {
            qxys();
        });
        function qxys(){
            $(".xzCheckbox").hide();
            $(".bdc-operate-btn").find("button").show();
            $(".czan").hide();
        }
        //确认压缩
        $(document).on('click','#qrys',function () {
            qrys();
        });
        function qrys(){
            var chk_value =[];//定义一个数组
            $('input[name="xzCheckbox"]:checked').each(function(){
                if ($(this).next().text().indexOf(".jpg") != -1 || $(this).next().text().indexOf(".png") != -1 ||
                    $(this).next().text().indexOf(".jpeg") != -1) {
                    chk_value.push($(this).val().substring($(this).val().lastIndexOf("/")+1));
                }else {
                    chk_value.push($(this).val());
                }
            });
            console.log(chk_value);
            console.log(currentMl);
            //如过当前目录的ID为空则任务是在根目录下
            addModel("压缩文件中");
            $.ajax({
                type: "POST",
                url: getContextPath() + "/rest/v1.0/files/ys",
                async: true,
                dataType: "json",
                contentType: "application/json;charset=UTF-8",
                data:JSON.stringify( {
                    zipfile: chk_value,
                    gzlslid: processInstanceId,
                    parentId: currentMl
                }),
                success: function (data) {
                    removeModal();
                    layer.alert("文件压缩成功");
                    $(".xzCheckbox").hide();
                    $(".bdc-operate-btn").find("button").show();
                    $(".czan").hide();
                    listFiles();
                    //removeModal();
                }, error: function (e) {
                    removeModal();
                    layer.alert("文件压缩失败");
                    $(".xzCheckbox").hide();
                    $(".bdc-operate-btn").find("button").show();
                    $(".czan").hide();
                    console.log(e);
                }
            });
        }


        //解压缩-- 将勾选框放出
        $(document).on('click','#jys',function () {
            $(".xzCheckbox").show();
            $(".bdc-operate-btn").find("button").hide();
            $(".jyscz").show();
            $(".qxwj").show();
        });
        $(document).on('click','#qxjys',function () {
            qxjys();
        });
        //取消解压缩
        function qxjys(){
            $(".xzCheckbox").hide();
            $(".bdc-operate-btn").find("button").show();
            $(".czan").hide();
        }
        //确认解压缩
        $(document).on('click','#qrjys',function () {
            qrjys();
        });
        function qrjys(){
            var chk_value =[];//定义一个数组
            $('input[name="xzCheckbox"]:checked').each(function(){
                chk_value.push($(this).val());
            });
            console.log(chk_value);
            console.log(currentMl);
            //如过当前目录的ID为空则任务是在根目录下
            if(chk_value.length < 1){
                layer.alert("请选择一个操作数据");
                return;
            }
            if(chk_value.length > 1){
                layer.alert("只能选择一个操作数据");
                return;
            }
            var layerMsg = layer.alert("解压中", {
                title: false,
                icon: 16 // 加载图标
            });
            addModel("解压文件中");
            $.ajax({
                type: "POST",
                url: getContextPath() + "/rest/v1.0/files/jys",
                async: true,
                dataType: "json",
                contentType: "application/json;charset=UTF-8",
                data:JSON.stringify( {
                    zipfile: chk_value,
                    gzlslid: processInstanceId,
                    parentId: currentMl
                }),
                success: function (data) {
                    removeModal();
                    layer.alert("文件解压成功");
                    $(".xzCheckbox").hide();
                    $(".bdc-operate-btn").find("button").show();
                    $(".czan").hide();
                    renderImgslt(current$fjmlElem);
                    listFiles();
                }, error: function (e) {
                    removeModal();
                    layer.alert("文件解压失败");
                    $(".xzCheckbox").hide();
                    $(".bdc-operate-btn").find("button").show();
                    $(".czan").hide();
                    console.log(e);
                }
            });
        }

        //文件下载
        $(document).on('click','#xz',function () {
            //如果当前是打开的单文件的方式则直接下载当前的文件
            if(!isNullOrEmpty(currentFile)){
                var pdfUrl = getContextPath() + "/rest/v1.0/files/xz?zipfile=" + currentFile;
                window.open(pdfUrl,"PDFDOWNLOAD");
                return;
            }
            $(".xzCheckbox").show();
            $(".bdc-operate-btn").find("button").hide();
            $(".xzcz").show();
            $(".qxwj").show();
        });
        $(document).on('click','#qxxz',function () {
            qxxz();
        });
        //取消下载
        function qxxz(){
            $(".xzCheckbox").hide();
            $(".bdc-operate-btn").find("button").show();
            $(".czan").hide();
        }
        //确认下载
        $(document).on('click','#qrxz',function () {
            qrxz();
        });
        function qrxz(){
            var chk_value =[];//定义一个数组
            $('input[name="xzCheckbox"]:checked').each(function(){
                if ($(this).next().text().indexOf(".jpg") != -1 || $(this).next().text().indexOf(".png") != -1 ||
                    $(this).next().text().indexOf(".jpeg") != -1) {
                    chk_value.push($(this).val().substring($(this).val().lastIndexOf("/")+1));
                }else {
                    chk_value.push($(this).val());
                }
            });
            console.log(chk_value);
            console.log(currentMl);
            //如过当前目录的ID为空则任务是在根目录下
            if(chk_value.length < 1){
                layer.alert("请选择一个操作数据");
                return;
            }
            if(chk_value.length >= 1){
                addModel();
                $("#idJsonStr").val(chk_value);
                $("#gzlslid").val(processInstanceId);
                $("#form").submit();
                $(".xzCheckbox").hide();
                $(".bdc-operate-btn").find("button").show();
                $(".czan").hide();
                removeModal();
            }
        }



        //文件全选
        $(document).on('click','#wjqx',function () {
            $(".xzCheckbox").prop("checked","checked");
        });
        //取消文件全选
        $(document).on('click','#wjqxqx',function () {
            $(".xzCheckbox").removeAttr("checked");
        });

        //文件打印
        $(document).on('click','#dy',function () {
            //如果当前是打开的单文件的方式则直接打印当前的文件
            if(!isNullOrEmpty(currentFile)){
                var imgSrc = new Array();
                if($('#seeImgView .viewer-container').length > 0){
                    // 打印图片
                    console.log($('#seeImgView').find("iframe").length)
                    imgSrc.push($('#seeImgView').find("img").attr('src'));
                    PrintImageDjls(imgSrc);
                } else if ($('#seeImgView').find("iframe").length > 0){
                    // 打印pdf方法
                    var dylx = 'fjcl'
                    var downUrlList = [];
                    var downUrlObj = {};
                    downUrlObj["downUrl"] = $('#seeImgView').find("iframe").attr('src');
                    if (downUrlObj["downUrl"].indexOf(document.location.protocol + "//") === -1) {
                        downUrlObj["downUrl"] = getIP() + downUrlObj["downUrl"];
                    }
                    downUrlList.push(downUrlObj);
                    printSjcl(downUrlList, dylx);
                }
            } else if (isNullOrEmpty(currentFile)){
                $(".xzCheckbox").show();
                $(".bdc-operate-btn").find("button").hide();
                $(".dycz").show();
                $(".xztp").show();
            }
        });
        //取消打印
        $(document).on('click',"#qxdy",function () {
            qxdy();
        });
        function qxdy() {
            $(".xzCheckbox").hide();
            $(".bdc-operate-btn").find("button").show();
            $(".czan").hide();
            $(".xzCheckbox").removeAttr("checked");
        }
        $(document).on('click',"#xzqx",function () {
            $(".xzCheckbox").removeAttr("checked");
        })
        $(document).on('click',"#xztp",function () {
            xztp();
        });
        //选中图片
        function xztp(){
            $(".xzCheckbox").prop("checked","checked");
            $('input[name="xzCheckbox"]:checked').each(function () {
                console.log($(this).next().text());
                if ($(this).next().text().indexOf(".jpg") === -1 && $(this).next().text().indexOf(".png") === -1 &&
                    $(this).next().text().indexOf(".jpeg") === -1) {
                    $(this).removeAttr("checked");
                }
            });
        }
        //确认打印
        $(document).on('click','#qrdy',function () {
            qrdy();
        });
        function qrdy() {
            var checkImag = [];
            var imagName = [];
            $('input[name="xzCheckbox"]:checked').each(function () {
                console.log(getIP());
                if ($(this).val().indexOf("http") === -1) {
                    checkImag.push(getIP()+$(this).val());
                }else {
                    checkImag.push($(this).val());
                }
                imagName.push($(this).next().text());
            });
            console.log(checkImag);
            if (checkImag.length < 1){
                layer.alert("请选择一个操作数据");
                return;
            }
            for (var i = 0; i < imagName.length; i++){
                if (imagName[i].indexOf(".jpg") === -1 && imagName[i].indexOf(".png") === -1 && imagName[i].indexOf(".jpeg") === -1 ) {
                    layer.alert("请勿选择非图片格式打印");
                    return;
                }
            }
            //转换图片 将横向图片转为纵向
            $.ajax({
                url: getContextPath()+'/rest/v1.0/files/getImage',
                type: 'POST',
                data: JSON.stringify(checkImag),
                contentType: "application/json;charset=UTF-8",
                success: function (res) {
                    if (!isNullOrEmpty(res.data)){
                        for (var i = 0;i < res.data.length;i++) {
                            if (res.data[i].indexOf("http:") === -1 && res.data[i].indexOf("https:") === -1) {
                                res.data[i] = "data:image/png;base64," + res.data[i];
                            }
                        }
                        PrintImageDjls(res.data);
                    }
                }
            })
        }
        function printSjcl(downUrlList, dylx) {
            var key = "";
            $.ajax({
                type: "POST",
                url: getContextPath() + "/rest/v1.0/files/sjcl/redis",
                data: JSON.stringify(downUrlList),
                contentType: 'application/json',
                success: function (data) {
                    if (isNotBlank(data)) {
                        key = data;
                        //根据redis的key值获取xml数据
                        var modelUrl = getIP() + "/realestate-portal-ui/static/printmodel/fjcl.fr3";
                        var dataUrl = getIP() + "/realestate-portal-ui/rest/v1.0/files/print?key=" + key;
                        console.log("打印附件dataUrl=" + dataUrl);
                        printChoice(dylx, "portal-ui", dataUrl, modelUrl, false, sessionKey);
                    }
                },
                error: function (xhr, status, error) {
                    removeModal();
                    delAjaxErrorMsg(xhr);
                }
            })
        }
    });
});