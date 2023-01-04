/**
 * Created by ypp on 2020/1/10.
 * 一窗首页js
 */
var laydate, laytpl, layer, element, form;
var $paramArr;
layui.use(['jquery', 'laydate', 'element', 'form', 'laytpl', 'layer', 'response'], function () {
    var $ = layui.jquery,
        response = layui.response;

    laydate = layui.laydate;
    laytpl = layui.laytpl;
    layer = layui.layer;
    element = layui.element;
    form = layui.form;

    $(function () {
        $paramArr = getIpHz();
        var clientId = $paramArr['clientId'];

        //点击退出
        $('.bdc-logout').on('click', function () {
            var logoutIndex = layer.confirm('您确定要退出吗？', {
                title: '提示',
                btn: ['确定', '取消'] //按钮
            }, function () {
                layer.close(logoutIndex);
                getReturnData("/rest/v1.0/user/logout", {}, "GET", function (data) {
                    var t = (new Date()).getTime();
                    var path = document.location.protocol + "//" + location.host + location.pathname;
                    var search = "?t=" + (new Date()).getTime();
                    for (var i in $paramArr) {
                        //加的时间戳不处理
                        if (i == "t") {
                            continue;
                        }
                        search += "&" + i + "=" + $paramArr[i]
                    }
                    window.location.href = data + path + encodeURIComponent(search);
                });
            });
        });


        //点击基本信息
        $('.bdc-message').on('click', function () {
            $(this).parents('.bdc-person-child').removeClass('layui-show');
            var $parentLi = $('.layui-side-menu .layui-nav-tree li');
            var liLength = $parentLi.length;
            $parentLi.removeClass('layui-nav-itemed');
            var $nowLi = $($parentLi[liLength - 1]);
            $nowLi.addClass('layui-nav-itemed');
            $nowLi.find('dd:first-child a').click();
        });
        //点击修改密码
        $('.bdc-update').on('click', function () {
            $(this).parents('.bdc-person-child').removeClass('layui-show');
            var $parentLi = $('.layui-side-menu .layui-nav-tree li');
            var liLength = $parentLi.length;
            $parentLi.removeClass('layui-nav-itemed');
            var $nowLi = $($parentLi[liLength - 1]);
            $nowLi.addClass('layui-nav-itemed');
            $nowLi.find('dd:last-child a').click();
        });

        // 侧边栏动态渲染
        var asideData = [{
            name: "任务中心",
            childTree: [{
                childTree: [],
                name: '业务办理',
                mark: 'ywbl',
                url: getContextPath() + '/view/listTasks.html?clientId=initialClientId&moduleCode=1'
            }]
        },{
            name: "查询统计",
            childTree: [{
                childTree: [],
                name: '日志查询',
                mark: 'rzcx',
                url: "http://192.168.2.98:8030/realestate-inquiry-ui/view/log/logList.html"
            },{
                childTree: [],
                name: '综合查询',
                mark: 'zhcx',
                url: "http://192.168.2.98:8030/realestate-inquiry-ui/view/zszm/bdcZszm.html?moduleCode=zhcx"
            },{
                childTree: [],
                name: '工作量统计',
                mark: 'gzltj',
                url: "http://192.168.2.98:8030/realestate-inquiry-ui/view/count/gzlTj.html"
            }]
        }];
        renderAside(asideData);
        function renderAside(data){
            if (data) {
                data.push({
                    name: '设置',
                    childTree: [
                        {
                            childTree: [],
                            name: '基本资料',
                            mark: 'basic',
                            url: getContextPath() + '/view/basic-info.html'
                        },
                        {
                            childTree: [],
                            name: '修改密码',
                            mark: 'update',
                            url: getContextPath() + '/view/update-pwd.html'
                        }
                    ]
                });
            }
            if (data && data.length > 0) {
                $('.layui-body .layadmin-tabsbody-item.layui-show iframe').attr('src', data[0].childTree[0].url);
                $('.bdc-outer-tab .layui-tab-title li.layui-this').attr('lay-id', data[0].childTree[0].url).attr('lay-attr', data[0].childTree[0].url);
                $('.bdc-outer-tab .layui-tab-title li.layui-this>span').html(data[0].childTree[0].name);
            }
            var getAsideTpl = asideTpl.innerHTML
                , getAsideView = document.getElementById('LAY-system-side-menu');
            laytpl(getAsideTpl).render(data, function (html) {
                getAsideView.innerHTML = html;
            });
            element.render('nav', 'layadmin-system-side-menu');
            //单击第一个
            var data = $('.layui-nav-tree .layui-nav-item:first-child a');
            var firstName,firstUrl;
            if (data && data.length > 0) {
                for (var i = 0, len = data.length; i < len; i++) {
                    if ($(data[i]).attr("lay-href")) {
                        $(data[i]).click();
                        firstName = $(data[i]).html();
                        firstUrl = $(data[i]).attr('lay-href');
                        break;
                    } else {
                        $(data[i]).click();
                    }
                }
            }
        }
        //默认显示的第一个tab 删除
        $('.bdc-outer-tab li .bdc-tab-close').on('click', function () {
            var _this = $(this).parent();
            if (_this.hasClass('layui-this')) {
                if($('.bdc-outer-tab .layui-tab-title li').length > 3){
                    _this.parent().find('li:nth-child(4)').addClass('layui-this');
                    $('.layui-body .layadmin-tabsbody-item:nth-child(4)').addClass('layui-show');
                    var layId = $('.bdc-outer-tab .layui-tab-title li:nth-child(4)').attr('lay-id');
                    var $sideA = $('.layui-side-scroll .layui-nav-tree li .bdc-show-msg');
                    for(var i = 1; i< $sideA.length; i++){
                        if($($sideA[i]).attr('lay-href') == layId){
                            $($sideA[i]).parent().addClass('layui-this');
                        }
                    }

                    _this.remove();
                    $('.layui-body .layadmin-tabsbody-item:nth-child(3)').remove();
                    $('.layui-side-scroll .layui-nav-tree li:first-child').removeClass('layui-this');
                }else {
                    _this.remove();
                    $('.layui-body .layadmin-tabsbody-item:nth-child(3)').remove();
                }
            } else {
                _this.remove();
                $('.layui-body .layadmin-tabsbody-item:nth-child(3)').remove();
            }

        });

        //1. 监听侧边栏点击
        $('.layui-side-menu .layui-nav-item .layui-nav-child').on('click', function () {
            $('.bdc-four-list').hide();
            // $('.breadcrumb-div cite').html($(this).siblings().find('cite').html());
        });
        $('.layui-side-menu .bdc-mime').on('click', function () {
            $('.bdc-four-list').hide();
            // $('.breadcrumb-div cite').html($(this).find('cite').html());
        });

        //2. 调整左侧滑动条的位置
        $('.layui-side-menu .layui-nav-item>a').on('click', function () {
            $('.bdc-four-list').hide();
            $('.layui-nav-bar').css({'height': '50px', 'opacity': 1, 'top': $(this).parent().index() * 50});
        });

        $('#flexible').click(function () {
            setTimeout(function () {
                renderCurrentTable();
            }, 300);
        });


        //加载首页的话处理
        //LAY_app_body.innerHTML='<div class="layadmin-tabsbody-item"></div><div class="layadmin-tabsbody-item"></div>';

        //获取基本信息
        getReturnData("/rest/v1.0/user/info", {}, "GET", function (data) {
            //消息中心推送
            $('.bdc-person-nav .bdc-user-name').html(data.alias);
        });

        //点击刷新图标
        $('.bdc-refresh-frame').on('click', function () {
            var nowTabIndex = $('.layui-body .layadmin-tabsbody-item.layui-show').index();
            var $frame = $('.layui-body .layadmin-tabsbody-item.layui-show iframe');
            if (nowTabIndex != 0) {
                $frame.attr('src', $frame.attr('src'));
            }
        });

        // 监听tab切换
        $('.bdc-outer-tab').on('click', '.layui-tab-title li', function () {
            if ($(this).attr('data-refresh') == 1) {
                setTimeout(function () {
                    $('.bdc-refresh-frame').click();
                }, 500);
                $(this).removeAttr('data-refresh');
            }
        });
    });

});