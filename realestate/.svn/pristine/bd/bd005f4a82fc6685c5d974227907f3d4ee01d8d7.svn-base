layui.use(['form','jquery','laydate','element','layer','table'],function () {
    var $ = layui.jquery,
        form = layui.form,
        layer = layui.layer,
        element = layui.element,
        table = layui.table,
        laydate = layui.laydate;
    form.render();
    var isfirst=true;


    //点击页面任一空白位置，消失
    // $('.main-content-item-content-tanchu').on('mouseleave',function () {
    //     $(".main-content-item-content-tanchu").hide();
    // });
    //点击弹出下拉框
    $(".main-content-item").on("click",".main-content-item-content>div span",function () {
        $(".main-content-item-content-tanchu").hide();
        $(this).parent().find(".main-content-item-content-tanchu").show();
    });
    //点击弹出框关闭
    $(".main-content-item-content-tanchu").on("click","form  input[type='button']:nth-child(2)",function () {
        $(".main-content-item-content-tanchu").hide();
    });
    //点击弹出框确认按钮
    $(".main-content-item-content-tanchu").on("click","form  input[type='button']:nth-child(1)",function () {
        var value=$(this).parent().siblings().find("select").val();
        $(this).parents(".main-content-item-content-tanchu").siblings(".confirm-content").text(value);
        $(".main-content-item-content-tanchu").hide();
    });
    //展开
    $(".main-content-open").click(function () {
        if($(this).parents(".main-content-item-title").siblings(".main-content-item-content").css("display")=="none"){
            $(this).parents(".main-content-item-title").siblings(".main-content-item-content").show();
            $(this).val("收起");
        }else{
            $(this).parents(".main-content-item-title").siblings(".main-content-item-content").hide();
            $(".main-content-item-content-tanchu").hide();
            $(this).val("展开");
        }
    });
    //全部展开消息
    $("#expandAll").click(function () {
        if($(this).val()=="展开全部信息"){
            $(".main-content-container>div>.main-content-item-content").show();
            $(".main-content-open").val("收起");
            $(this).val("收起全部信息");
        }else{
            $(".main-content-container>div>.main-content-item-content").hide();
            $(".main-content-item-content-tanchu").hide();
            $(".main-content-open").val("展开");
            $(this).val("展开全部信息");
        }
    });
    //删除
    $(".main-content-delete").click(function () {
        if($(this).parents(".main-content-item").hasClass("drag-disabled")==true){
            return
        }else{
            $(this).parents(".main-content-item").remove();
        }
    });
    var that;
    //打开弹出框
    form.on('select(aaa)', function(data){
        if(data.value==3){
            //标题显示
            $(".layui-colla-title").show()
            if(isfirst==true){
                $('.todrag').draggable({
                    revert:true,
                    deltaX:10,
                    deltaY:5,
                    disabled:false,
                    proxy:function(source){
                        var n = $('<div class="proxy"></div>');
                        n.html($(source).html()).appendTo('body');
                        return n;
                    },
                    onStartDrag: function (e) {
                        //延迟proxy，使有机会执行dblclick
                        // var proxy = $(this).draggable('proxy');
                        // proxy.css('z-index', 20);

                        // proxy.hide();
                        // setTimeout(function(){
                        //     proxy.show();
                        // }, 500);
                    },
                    onDrag:function (e) {
                        // var offset=$(e.data.parent).offset();
                        // e.data.top = (e.pageY-offset.top-e.data.offsetHeight+400);
                        // e.data.left = (e.pageX-offset.left-e.data.offsetWidth);
                        // var proxy = $(this).draggable('proxy');
                        // if (proxy){
                        //     proxy.show();
                        // }
                    }
                });
                $(".main-content-container-right-item").droppable({
                    accept: '.todrag',
                    onDrop: function(e,source){
                        var dataIndex=$(source).parent().parent().attr("data-index");
                        var n=$("<div class='reverse-drag' data-index='"+dataIndex+"'></div>");
                        var html=$(source).find("div:nth-child(2)").html();
                        n.html(html);
                        if($(source).parent().attr("data-certificate-index")!=$(this).attr("data-certificate-index")){
                            $(this).append(n);
                        }

                        n.draggable({
                            revert:true,
                            deltaX:10,
                            deltaY:10,
                            proxy:function(source){
                                var n = $('<div class="proxy"></div>');
                                n.html($(source).html()).appendTo('body');
                                return n;
                            }
                        })
                        //     .droppable({
                        //     accept:".reverse-drag",
                        //     onDrop:function(e,source){
                        //         $(source).insertAfter($(this));
                        //         that=$(this);
                        //     }
                        // });

                        //容器不变  则为改变顺序
                        // if($(source).parent().attr("data-certificate-index")==$(this).attr("data-certificate-index")){
                        //     //左侧容器进行顺序修改
                        //     var CertificateIndex=$(this).attr("data-certificate-index");
                        //
                        //     var sIndex=$(source).attr("data-index");
                        //     var $s=$(".drag-disabled-container[data-certificate-index="+CertificateIndex+"] .drag-disabled[data-index="+sIndex+"]");
                        //     var zIndex=that.attr("data-index");
                        //     var $z=$(".drag-disabled-container[data-certificate-index="+CertificateIndex+"] .drag-disabled[data-index="+zIndex+"]");
                        //     $s.insertAfter($z);
                        //     return
                        // }
                        // else{
                            $(source).parent().parent().hide();
                            var CertificateIndex=$(this).attr("data-certificate-index");
                            var $n=$(".drag-disabled[data-index="+dataIndex+"]");
                            $(".drag-disabled-container[data-certificate-index="+CertificateIndex+"]").append($n.clone());
                            $(".drag-disabled-container[data-certificate-index="+CertificateIndex+"] .drag-disabled[data-index="+dataIndex+"]").show();
                            $(".drag-disabled-container[data-certificate-index="+CertificateIndex+"] .drag-disabled[data-index="+dataIndex+"] .main-content-item-content").hide();
                        // }
                    }
                });

                isfirst=false;
            }
            //任意组合  开始变形！！！！！！
            // $(".modal-tanchu").show();

            //头部标题消失
            $(".main-content-container-toptitle").hide();
            //主体内容变窄
            $(".main-content-container").addClass("new-main-content-container");
            $(".main-content-container").removeClass("old-main-content-container");
            //主体内容宽度调整
            $(".main-content-item .main-content-item-title>div:nth-child(2)").addClass("new-main-content-item-title-div");
            $(".main-content-item .main-content-item-title .todrag>div").addClass("new-main-content-item-title-div");
            //下方弹出框内容排版修改
            $(".main-content-item-content").addClass("new-main-content-item-content");
            $(".main-content-item-content-tanchu").addClass("new-main-content-item-content-tanchu");
            $(".qlrsjly").addClass("new-qlrsjly");
            $(".qllx").addClass("new-qllx");
            $(".ywrsjly").addClass("new-ywrsjly");
            $(".sffz").addClass("new-sffz");


            // var html=`<div class="main-content-container-right-item-container">
            //               <div class="main-content-container-right-item" data-options="" data-certificate-index="1">
            //                   <div class="main-content-container-right-item-title">
            //                       <!--标题-->
            //                       <span>证书1</span>
            //                       <!--删除按钮-->
            //                       <input type="button" class="deleteZhengshu" value="删除">
            //                   </div>
            //               </div>
            //           </div>
            //           <div>
            //               <input type="button" id="addZhengshu" value="添加证书">
            //           </div>`;
            //清空容器
            // $(".main-content-container-right").html(html);
            // $(".main-content-container .drag-disabled-container").remove();
            // $(".main-content-container-container").append('<div class="drag-disabled-container" data-certificate-index="1"></div>');
            //左侧还原
            // $('.main-content-item').show();
            // $('.drag-disabled').hide();
            //显示右侧栏目
            $(".main-content-container-right").show();


        }
        else{
            //隐藏标题
            $(".layui-colla-content").addClass("layui-show");
            $(".layui-colla-title").hide();
            //头部标题消失
            $(".main-content-container-toptitle").show();
            //主体内容变窄
            $(".main-content-container").removeClass("new-main-content-container");
            $(".main-content-container").addClass("old-main-content-container");
            //主体内容宽度调整
            $(".main-content-item .main-content-item-title>div:nth-child(2)").removeClass("new-main-content-item-title-div");
            $(".main-content-item .main-content-item-title .todrag>div").removeClass("new-main-content-item-title-div");
            //下方弹出框内容排版修改
            $(".main-content-item-content").removeClass("new-main-content-item-content");
            $(".main-content-item-content-tanchu").removeClass("new-main-content-item-content-tanchu");
            $(".qlrsjly").removeClass("new-qlrsjly");
            $(".qllx").removeClass("new-qllx");
            $(".ywrsjly").removeClass("new-ywrsjly");
            $(".sffz").removeClass("new-sffz");

            //隐藏右侧栏目
            $(".main-content-container-right").hide();

            $(".main-content-item").draggable({
                disabled:true
            });
        }
    });
    //关闭弹出框
    $(".modal-tanchu-title img").on("click",function () {
        $(".modal-tanchu").hide();
    });

    //搜索框隐藏与显示
    $("#openSearch").on("click",function () {
        if($(this).val()=="展开搜索框"){
            $(".search").show();
            $(this).val("收起搜索框");
        }else{
            $(".search").hide();
            $(this).val("展开搜索框");
        }

    });

    $('.drag-disabled ').draggable({
        disabled:true
    });

    //添加证书
    $('body').on("click","#addZhengshu",function () {
        //获取当前有多少证书
        var num=$(".main-content-container-right-item-container .main-content-container-right-item").length;
        //加一操作
        var newnum=parseFloat(num)+1;
        var html=' <div class="main-content-container-right-item" data-options="" data-certificate-index="'+newnum+'">'+
                              '<div class="main-content-container-right-item-title">'+
                                  '<span>证书'+newnum+'</span>'+
                                  '<input type="button" class="deleteZhengshu" value="删除">'+
                              '</div>'+
                          '</div>';
        $(".main-content-container-right-item-container").append(html);

        //左侧添加
        var htmlLeft='<div class="drag-disabled-container" data-certificate-index="'+newnum+'"></div>';
        $(".main-content-container-container").append(htmlLeft);

        $(".main-content-container-right-item").droppable({
            accept: '.todrag,.reverse-drag',
            onDrop: function(e,source){

                if($(source).hasClass('reverse-drag')==false){
                    var dataIndex=$(source).parent().parent().attr("data-index");
                    var n=$("<div class='reverse-drag' data-index='"+dataIndex+"'></div>");
                    var html=$(source).find("div:nth-child(2)").html();
                    n.html(html);
                    n.draggable({
                        revert:true,
                        deltaX:10,
                        deltaY:10,
                        proxy:function(source){
                            var n = $('<div class="proxy"></div>');
                            n.html($(source).html()).appendTo('body');
                            return n;
                        }
                    })
                    //     .droppable({
                    //     accept:".reverse-drag",
                    //     onDrop:function(e,source){
                    //         $(source).insertAfter($(this));
                    //
                    //         var CertificateIndex=$(this).parent().attr("data-certificate-index");
                    //
                    //         var sIndex=$(source).attr("data-index");
                    //         var $s=$(".drag-disabled-container[data-certificate-index="+CertificateIndex+"] .drag-disabled[data-index="+sIndex+"]");
                    //         var zIndex=$(this).attr("data-index");
                    //         var $z=$(".drag-disabled-container[data-certificate-index="+CertificateIndex+"] .drag-disabled[data-index="+zIndex+"]");
                    //         $s.insertAfter($z);
                    //     }
                    // })
                    $(this).append(n);
                    $(source).parent().parent().hide();
                    var CertificateIndex=$(this).attr("data-certificate-index");
                    var $n=$(".drag-disabled[data-index="+dataIndex+"]");
                    $(".drag-disabled-container[data-certificate-index="+CertificateIndex+"]").append($n.clone());
                    $(".drag-disabled-container[data-certificate-index="+CertificateIndex+"] .drag-disabled[data-index="+dataIndex+"]").show();
                    $(".drag-disabled-container[data-certificate-index="+CertificateIndex+"] .drag-disabled[data-index="+dataIndex+"] .main-content-item-content").hide();
                }
                else{

                        // $(source).draggable({
                        //     revert:true,
                        //     deltaX:10,
                        //     deltaY:10,
                        //     proxy:function(source){
                        //         var n = $('<div class="proxy"></div>');
                        //         n.html($(source).html()).appendTo('body');
                        //         return n;
                        //     }
                        // }).droppable({
                        //     accept:".reverse-drag",
                        //     onDrop:function(e,source){
                        //
                        //     }
                        // });
                        var CertificateIndex=$(this).attr("data-certificate-index");
                        var dataIndex=$(source).attr("data-index");
                        //判断是否来自于内部
                        // if($(source).parent().attr("data-certificate-index")==$(this).attr("data-certificate-index")){
                        //     $(source).insertAfter($(".reverse-drag:hover"));
                        //
                        //
                        //     var $s=$(".drag-disabled-container[data-certificate-index="+CertificateIndex+"] .drag-disabled[data-index="+dataIndex+"]");
                        //     var zIndex=$(".reverse-drag:hover").attr("data-index");
                        //     var $z=$(".drag-disabled-container[data-certificate-index="+CertificateIndex+"] .drag-disabled[data-index="+zIndex+"]");
                        //     $s.insertAfter($z);
                        // }
                        // else{
                            $(".drag-disabled-container .drag-disabled[data-index="+dataIndex+"]").remove();
                            var $n=$(".drag-disabled[data-index="+dataIndex+"]");
                            $(".drag-disabled-container[data-certificate-index="+CertificateIndex+"]").append($n.clone());
                            $(".drag-disabled-container[data-certificate-index="+CertificateIndex+"] .drag-disabled[data-index="+dataIndex+"]").show();
                            $(".drag-disabled-container[data-certificate-index="+CertificateIndex+"] .drag-disabled[data-index="+dataIndex+"] .main-content-item-content").hide();
                            $(this).append($(source));
                        // }
                }
            }
        })

        $(".reverse-drag").droppable({
            accept:".reverse-drag",
            onDrop:function (e,source) {
            }
        })
    });

    //删除证书
    $("body").on("click",".deleteZhengshu",function () {
        //判断内部是否有值  有值的话则对内部值进行退回
        if($(this).parent().parent().find(".reverse-drag").length>0){
            $(this).parent().parent().find(".reverse-drag").each(function () {
                var dataIndex=$(this).attr("data-index");
                $('.main-content-item[data-index='+dataIndex+']').show();
                $(".main-content-container>.drag-disabled[data-index="+dataIndex+"]").hide();
            });
        }
        var certificateIndex= $(this).parent().parent().attr("data-certificate-index");
        $(this).parent().parent().remove();
        $(".main-content-container-right-item-container .main-content-container-right-item").each(function () {
            var index=parseFloat($(this).index())+1;
            $(this).attr("data-certificate-index",index);
            $(this).find(".main-content-container-right-item-title span").html("证书"+index);
        });
        $(".main-content-container-container .drag-disabled-container[data-certificate-index="+certificateIndex+"]").remove();
        $(".main-content-container .main-content-container-container .drag-disabled-container").each(function () {
            var index=parseFloat($(this).index())+1;
            $(this).attr("data-certificate-index",index);
        });
    });



    $(".main-content-container").droppable({
        accept:".reverse-drag",
        onDrop:function (e,source) {
            var CertificateIndex=$(source).parent().attr("data-certificate-index");
            var dataIndex=$(source).attr("data-index");
            $('.main-content-item[data-index='+dataIndex+']').show();
            // $('.drag-disabled[data-index='+dataIndex+']').hide();
            $(".drag-disabled-container[data-certificate-index="+CertificateIndex+"]  .drag-disabled[data-index="+dataIndex+"]").remove();
            $(".main-content-container>.drag-disabled[data-index="+dataIndex+"]").hide();
            $(source).remove();
        }
    })

});