/**
 * author: 前端组
 * date: 2019-03-07
 * version 3.0.0
 * describe: 统一遮罩样式
 */
layui.use(['jquery','layer'], function() {
    var $ = layui.jquery,
        layer = layui.layer;

    //统一遮罩
    //在需要加遮罩的位置执行addModel();需要关闭时执行removeModal()
    $('#mask').on('click',function () {
        addModel();
        // setTimeout(removeModal,3000);
    });

    //统一删除确认
    $('#deleteSure').on('click',function () {
        var deleteIndex = layer.open({
            type: 1,
            skin: 'bdc-small-tips',
            title: '确认删除',
            area: ['320px'],
            content: '您确定要删除吗？您确定要删除吗？您确定要删除吗？您确定要删除吗？',
            btn: ['确定','取消'],
            btnAlign: 'c',
            yes: function(){
                //确定操作
                console.log('确定');
                layer.close(deleteIndex);
            },
            btn2: function(){
                //取消
            }
        });
    });

    //统一提交确认
    $('#submitSure').on('click',function () {
        var submitIndex = layer.open({
            type: 1,
            title: '确认提交',
            skin: 'bdc-small-tips',
            area: ['320px'],
            content: '您确定要提交吗？',
            btn: ['确定','取消'],
            btnAlign: 'c',
            yes: function(){
                //确定操作
                console.log('确定');
                layer.close(submitIndex);
            },
            btn2: function(){
                //取消
            }
        });
    });

    //强提示消息
    var msg = [
        '强提示信息',
        '强提示信息强提示信息强提示信息强提示信息强提示信息强提示信息'
    ];
    var strongTipsHtml = '';
    msg.forEach(function (v) {
        strongTipsHtml += '<p class="bdc-zf-tips-p"><img src="../images/info-small.png" alt="">'+ v +'</p>';
    });

    $('#strongTips').on('click',function () {
        var submitIndex = layer.open({
            type: 1,
            title: '提示',
            skin: 'bdc-strong-tips',
            area: ['320px'],
            content: strongTipsHtml,
            btn: ['确定'],
            btnAlign: 'c',
            closeBtn: 0,
            yes: function(){
                //确定操作
                console.log('确定');
                layer.close(submitIndex);
            }
        });
    });

    //把添加遮罩及移除遮罩方法添加在自己的通用js中，或者引入当前js，注释上面两行，调用即可使用
    function addModel() {
        var modalHtml = '<div id="waitModalLayer">'+
            '<p class="bdc-wait-content">'+
            '<i class="layui-icon layui-anim layui-anim-rotate layui-anim-loop">&#xe63d;</i>'+
            // '<i class="layui-icon layui-icon-loading"></i>'+
            '<span>加载中</span>'+
            '</p>'+
            '</div>';
        $('body').append(modalHtml);
    }

    function removeModal() {
        $("#waitModalLayer").remove();
    }

    //提示信息
    //右中弹出警告内容
    $('#warnTips').on('click',function () {
        rightShowWarn();
    });
    function rightShowWarn() {
        var warnLayer = layer.open({
            type: 1,
            title: '提示信息',
            anim: -1,
            skin: 'bdc-tips-right bdc-error-layer',
            shade: [0],
            area: ['390px'],
            offset: 'r',
            content:
            '<a class="layui-btn layui-btn-sm bdc-ignore-btn">忽略全部</a>' +
            '<div class="bdc-right-tips-box" id="bottomTips">' +
            '<p>' +
            '<img src="../images/warn.png" alt="">'+
            '该不动产信单元已抵押，是否继续？<a href="javascrit:;">查看</a></p>'+
            '<p>' +
            '<img src="../images/error.png" alt="">'+
            '该不动产信单元正在办理其他信息<a href="javascrit:;">查看</a><a href="javascrit:;">忽略</a></p>'+
            '<p>' +
            '<img src="../images/error.png" alt="">'+
            '该不动产信单元正在办理其他信息<a href="javascrit:;">查看</a><a href="javascrit:;">忽略</a></p>'+
            '<p>' +
            '<img src="../images/error.png" alt="">'+
            '该不动产信单元正在办理其他信息<a href="javascrit:;">查看</a><a href="javascrit:;">忽略</a></p>'+
            '<p>' +
            '<img src="../images/error.png" alt="">'+
            '该不动产信单元正在办理其他信息<a href="javascrit:;">查看</a><a href="javascrit:;">忽略</a></p>'+
            '<p>' +
            '<img src="../images/error.png" alt="">'+
            '该不动产信单元正在办理其他信息<a href="javascrit:;">查看</a><a href="javascrit:;">忽略</a></p>'+
            '<p>' +
            '<img src="../images/error.png" alt="">'+
            '该不动产信单元正在办理其他信息<a href="javascrit:;">查看</a><a href="javascrit:;">忽略</a></p>'+
            '<p>' +
            '<img src="../images/error.png" alt="">'+
            '该不动产信单元正在办理其他信息<a href="javascrit:;">查看</a><a href="javascrit:;">忽略</a></p>'+
            '<p>' +
            '<img src="../images/error.png" alt="">'+
            '该不动产信单元正在办理其他信息<a href="javascrit:;">查看</a><a href="javascrit:;">忽略</a></p>'+
            '<p>' +
            '<img src="../images/error.png" alt="">'+
            '该不动产信单元正在办理其他信息<a href="javascrit:;">查看</a><a href="javascrit:;">忽略</a></p>'+
            '<p>' +
            '<img src="../images/error.png" alt="">'+
            '该不动产信单元正在办理其他信息<a href="javascrit:;">查看</a><a href="javascrit:;">忽略</a></p>'+
            '<p>' +
            '<img src="../images/error.png" alt="">'+
            '该不动产信单元正在办理其他信息<a href="javascrit:;">查看</a><a href="javascrit:;">忽略</a></p>'+
            '<p>' +
            '<img src="../images/error.png" alt="">'+
            '该不动产信单元正在办理其他信息<a href="javascrit:;">查看</a><a href="javascrit:;">忽略</a></p>'+
            '<p>' +
            '<img src="../images/error.png" alt="">'+
            '该不动产信单元正在办理其他信息<a href="javascrit:;">查看</a><a href="javascrit:;">忽略</a></p>'+
            '<p>' +
            '<img src="../images/error.png" alt="">'+
            '该不动产信单元正在办理其他信息<a href="javascrit:;">查看</a><a href="javascrit:;">忽略</a></p>'+
            '<p>' +
            '<img src="../images/error.png" alt="">'+
            '该不动产信单元正在办理其他信息<a href="javascrit:;">查看</a><a href="javascrit:;">忽略</a></p>'+
            '<p>' +
            '<img src="../images/error.png" alt="">'+
            '该不动产信单元正在办理其他信息<a href="javascrit:;">查看</a><a href="javascrit:;">忽略</a></p>'+
            '<p>' +
            '<img src="../images/error.png" alt="">'+
            '该不动产信单元正在办理其他信息<a href="javascrit:;">查看</a><a href="javascrit:;">忽略</a></p>'+
            '<p>' +
            '<img src="../images/error.png" alt="">'+
            '该不动产信单元正在办理其他信息<a href="javascrit:;">查看</a><a href="javascrit:;">忽略</a></p>'+
            '<p>' +
            '<img src="../images/error.png" alt="">'+
            '该不动产信单元正在办理其他信息<a href="javascrit:;">查看</a><a href="javascrit:;">忽略</a></p>'+
            '<p>' +
            '<img src="../images/error.png" alt="">'+
            '该不动产信单元正在办理其他信息<a href="javascrit:;">查看</a><a href="javascrit:;">忽略</a></p>'+
            '<p>' +
            '<img src="../images/error.png" alt="">'+
            '该不动产信单元正在办理其他信息<a href="javascrit:;">查看</a><a href="javascrit:;">忽略</a></p>'+
            '<p>' +
            '<img src="../images/error.png" alt="">'+
            '该不动产信单元正在办理其他信息<a href="javascrit:;">查看</a><a href="javascrit:;">忽略</a></p>'+
            '<p>' +
            '<img src="../images/error.png" alt="">'+
            '该不动产信单元正在办理其他信息<a href="javascrit:;">查看</a><a href="javascrit:;">忽略</a></p>'+
            '<p>' +
            '<img src="../images/error.png" alt="">'+
            '该不动产信单元正在办理其他信息<a href="javascrit:;">查看</a><a class="bdc-hl" href="javascrit:;">忽略</a></p>'+
           '</div>',
            success: function () {
                $('.bdc-tips-right .bdc-right-tips-box .layui-icon-close').on('click',function () {
                    layer.close(warnLayer);
                });
				$('.bdc-hl').on('click',function(){
                    $(this).parent().remove();
				})
                setTimeout(function () {
                    $('.bdc-error-layer').css('opacity',1)
                },500);
            }
        });
    }

    //右中弹出警告内容---分类
    $('#warnTipsFl').on('click',function () {
        rightShowWarnFl();
    });
    function rightShowWarnFl() {
        var warnLayer = layer.open({
            type: 1,
            title: '提示信息',
            anim: -1,
            skin: 'bdc-tips-right bdc-error-layer',
            shade: [0],
            area: ['390px'],
            offset: 'r',
            content:
            '<a class="layui-btn layui-btn-sm bdc-ignore-btn">忽略全部</a>' +
            '<div class="bdc-right-tips-box" id="bottomTips">' +
            '<div class="bdc-fl-box">'+
            '<h2 class="bdc-warn-title">确认信息<span class="bdc-warn-title-num">（3）</span></h2>' +
            '<h4 class="bdc-sure-tip">温馨提示：以下为需要确认的信息。</h4>' +
            '<p>' +
            '<img src="../images/warn.png" alt="">'+
            '该不动产信单元已抵押，是否继续？<a href="javascrit:;">确认</a></p>'+
            '<p>' +
            '<img src="../images/warn.png" alt="">'+
            '该不动产信单元正在办理其他信息<a href="javascrit:;">确认</a><a href="javascrit:;" class="bdc-ignore-msg">忽略</a></p>'+
            '<p>' +
            '<img src="../images/warn.png" alt="">'+
            '该不动产信单元正在办理其他信息<a href="javascrit:;">确认</a><a href="javascrit:;" class="bdc-ignore-msg">忽略</a></p>'+
            '</div>' +
            '<div class="bdc-fl-box">'+
            '<h2 class="bdc-warn-title">警告信息<span class="bdc-warn-title-num">（3）</span></h2>' +
            '<p>' +
            '<img src="../images/error.png" alt="">'+
            '该不动产信单元正在办理其他信息<a href="javascrit:;">查看</a></p>'+
            '<p>' +
            '<img src="../images/error.png" alt="">'+
            '该不动产信单元正在办理其他信息<a href="javascrit:;">查看</a></p>'+
            '<p>' +
            '<img src="../images/error.png" alt="">'+
            '该不动产信单元正在办理其他信息<a href="javascrit:;">查看</a></p>'+
            '</div>' +
            '<div class="bdc-fl-box">'+
            '<h2 class="bdc-warn-title">提示信息<span class="bdc-warn-title-num">（12）</span></h2>' +
            '<p>' +
            '<img src="../images/warn.png" alt="">'+
            '该不动产信单元正在办理其他信息<a href="javascrit:;">查看</a><a href="javascrit:;" class="bdc-ignore-msg">忽略</a></p>'+
            '<p>' +
            '<img src="../images/warn.png" alt="">'+
            '该不动产信单元正在办理其他信息<a href="javascrit:;" class="bdc-ignore-msg">忽略</a></p>'+
            '<p>' +
            '<img src="../images/warn.png" alt="">'+
            '该不动产信单元正在办理其他信息<a href="javascrit:;" class="bdc-ignore-msg">忽略</a></p>'+
            '<p>' +
            '<img src="../images/warn.png" alt="">'+
            '该不动产信单元正在办理其他信息<a href="javascrit:;">查看</a><a href="javascrit:;" class="bdc-ignore-msg">忽略</a></p>'+
            '<p>' +
            '<img src="../images/warn.png" alt="">'+
            '该不动产信单元正在办理其他信息<a href="javascrit:;">查看</a><a href="javascrit:;" class="bdc-ignore-msg">忽略</a></p>'+
            '<p>' +
            '<img src="../images/warn.png" alt="">'+
            '该不动产信单元正在办理其他信息<a href="javascrit:;">查看</a><a href="javascrit:;" class="bdc-ignore-msg">忽略</a></p>'+
            '<p>' +
            '<img src="../images/warn.png" alt="">'+
            '该不动产信单元正在办理其他信息<a href="javascrit:;">查看</a><a href="javascrit:;" class="bdc-ignore-msg">忽略</a></p>'+
            '<p>' +
            '<img src="../images/warn.png" alt="">'+
            '该不动产信单元正在办理其他信息<a href="javascrit:;">查看</a><a href="javascrit:;" class="bdc-ignore-msg">忽略</a></p>'+
            '<p>' +
            '<img src="../images/warn.png" alt="">'+
            '该不动产信单元正在办理其他信息<a href="javascrit:;">查看</a><a href="javascrit:;" class="bdc-ignore-msg">忽略</a></p>'+
            '<p>' +
            '<img src="../images/warn.png" alt="">'+
            '该不动产信单元正在办理其他信息<a href="javascrit:;">查看</a><a href="javascrit:;" class="bdc-ignore-msg">忽略</a></p>'+
            '<p>' +
            '<img src="../images/warn.png" alt="">'+
            '该不动产信单元正在办理其他信息<a href="javascrit:;">查看</a><a href="javascrit:;" class="bdc-ignore-msg">忽略</a></p>'+
            '<p>' +
            '<img src="../images/warn.png" alt="">'+
            '该不动产信单元正在办理其他信息<a href="javascrit:;">查看</a><a href="javascrit:;" class="bdc-ignore-msg">忽略</a></p>'+
            '</div>' +
            '</div>',
            success: function () {
                setTimeout(function () {
                    $('.bdc-error-layer').css('opacity',1)
                },500);
                $('.bdc-tips-right .bdc-right-tips-box .layui-icon-close').on('click',function () {
                    layer.close(warnLayer);
                });
                $('.bdc-ignore-msg').on('click',function(){
                    $(this).parent().remove();
                });
                //单击忽略全部
                $('.bdc-ignore-btn').on('click',function () {
                    $('.bdc-ignore-msg').parent().remove();
                    for(var i = 0; i < 3; i++){
                        if($($('.bdc-fl-box')[i]).find('p').length == 0){
                            $($('.bdc-fl-box')[i]).remove();
                        }
                    }
                });
            }
        });
    }

    //提示信息
    //右中弹出提示内容
    $('#hintTips').on('click', function() {
        hintShowWarn();
    });

    function hintShowWarn() {
        var warnLayer = layer.open({
            type: 1,
            title: '提示信息',
            anim: -1,
            skin: 'bdc-tips-right bdc-error-layer',
            shade: [0],
            area: ['390px'],
            offset: 'r',
            content: '<a class="layui-btn layui-btn-sm bdc-ignore-btn">忽略全部</a>' +
                '<div class="bdc-right-tips-box" >' +

                '<div class="title-area">' +
                '<p class="tsxx-title">受理编号</p>' +
                '<span>47383333</span>' +
                '<p>' +
                '<img src="../images/error.png" alt=""> 该不动产信单元正在办理其他信息' +
                '<a href="javascrit:;">查看</a><a href="javascrit:;">忽略</a></p>' +
                '<p>' +
                '<img src="../images/error.png" alt=""> 该不动产信单元正在办理其他信息' +
                '<a href="javascrit:;">查看</a><a href="javascrit:;">忽略</a></p>' +
                '<p>' +
                '<img src="../images/error.png" alt=""> 该不动产信单元正在办理其他信息' +
                '<a href="javascrit:;">查看</a><a href="javascrit:;">忽略</a></p>' +
                '<p>' +
                '<img src="../images/error.png" alt=""> 该不动产信单元正在办理其他信息' +
                '<a href="javascrit:;">查看</a><a href="javascrit:;">忽略</a></p>' +
                '<p>' +
                '<img src="../images/error.png" alt=""> 该不动产信单元正在办理其他信息' +
                '<a href="javascrit:;">查看</a><a href="javascrit:;">忽略</a></p>' +
                '</div>' +

                '<div class="title-area">' +
                '<p class="tsxx-title">受理编号</p>' +
                '<span>47383333</span>' +
                '<p>' +
                '<img src="../images/error.png" alt=""> 该不动产信单元正在办理其他信息' +
                '<a href="javascrit:;">查看</a><a href="javascrit:;">忽略</a></p>' +
                '<p>' +
                '<img src="../images/error.png" alt=""> 该不动产信单元正在办理其他信息' +
                '<a href="javascrit:;">查看</a><a href="javascrit:;">忽略</a></p>' +
                '<p>' +
                '<img src="../images/error.png" alt=""> 该不动产信单元正在办理其他信息' +
                '<a href="javascrit:;">查看</a><a href="javascrit:;">忽略</a></p>' +
                '<p>' +
                '<img src="../images/error.png" alt=""> 该不动产信单元正在办理其他信息' +
                '<a href="javascrit:;">查看</a><a href="javascrit:;">忽略</a></p>' +
                '<p>' +
                '<img src="../images/error.png" alt=""> 该不动产信单元正在办理其他信息' +
                '<a href="javascrit:;">查看</a><a href="javascrit:;">忽略</a></p>' +
                '</div>' +
                '</div>',
            success: function() {
                setTimeout(function() {
                    $('.bdc-error-layer').css('opacity', 1)
                }, 500);
                $('.bdc-tips-right .bdc-right-tips-box .layui-icon-close').on('click', function() {
                    layer.close(warnLayer);
                });
                $('.bdc-hl').on('click', function() {
                    $(this).parent().remove();
                });
            }
        });
    }

    //成功小提示
    $('#msgSuccessTips').on('click',function () {
        layer.msg('<img src="../images/success-small.png" alt="">请求成功，即将跳转');
    });
    //失败小提示
    $('#msgErrorTips').on('click',function () {
        layer.msg('<img src="../images/error-small.png" alt="">服务器内部异常 <a class="bdc-show-more-tips" href="javascript:;">更多</a>', {
            time: 4000,
            success: function(){
                //点击更多
                $('.bdc-show-more-tips').on('click',function () {
                    $('.bdc-other-tips-box').removeClass('bdc-hide');
                });
                //点击 不再提示 ，关闭提示框
                $('.bdc-other-tips-box .bdc-close').on('click',function () {
                    $('.bdc-other-tips-box').addClass('bdc-hide');
                });
            }
        });
    });
    //信息小提示
    $('#msgInfoTips').on('click',function () {
        layer.msg('<img src="../images/info-small.png" alt="">提示信息');
    });
    //警告小提示
    $('#msgWarnTips').on('click',function () {
        layer.msg('<img src="../images/warn-small.png" alt="">警告内容');
    });

    //验证信息小提示
    $('#yzInfoTips').on('click',function () {
        layer.msg('<p class="bdc-zf-tips-p"><img src="../images/success-small.png" alt="">转发成功5条</p>' +
            '<p class="bdc-zf-tips-p"><img src="../images/error-small.png" alt="">转发失败4条</p>',{time: 400000});
    });
    //转发小提示
    $('#zfTips').on('click',function () {
        layer.msg('<p class="bdc-zf-tips-p"><img src="../images/info-small.png" alt="">20190802签名失败</p>' +
            '<p class="bdc-zf-tips-p"><img src="../images/info-small.png" alt="">20190101必填项验证不通过</p>',{time: 400000});
    });

});