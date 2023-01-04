/**
 * Created by Administrator on 2019/1/21.
 */
var laydate, laytpl, layer, element, form;
var week, lunarDateString;
var weekIndex = 0, monthIndex = 0, yearIndex = 0;
var $paramArr;
var timer;
var zxywbll =false;
layui.use(['jquery', 'laydate', 'element', 'form', 'laytpl', 'layer', 'moduleAuthority', 'response'], function () {
    var $ = layui.jquery,
        response = layui.response,
        moduleAuthority = layui.moduleAuthority;

    laydate = layui.laydate;
    laytpl = layui.laytpl;
    layer = layui.layer;
    element = layui.element;
    form = layui.form;
    $(function () {
        $paramArr = getIpHz();
        var clientId = $paramArr['clientId'];
        // 模块编码
        var moduleCode = $paramArr['moduleCode'];
        // 加载登记首页
        var loadHome = $paramArr['loadHome'];
        var authorityCode = '';
        //初始化定时（判断是否打开）
        initTimer();
        // 获取子系统列表
        getReturnData("/rest/v1.0/user/modules/portaluiclient/subsystem", {}, "GET", function (data) {
            //路径不完整
            var pathNotAll = isNullOrEmpty(clientId);
            var path = document.location.pathname + document.location.search;
            if (data) {
                data.forEach(function (v) {
                    // if(pathNotAll){
                    //     window.location.href=v.url;
                    //     window.reload();
                    // }
                    if (v.url.indexOf(clientId) > -1) {
                        $('.bdc_subsystem').html(v.name);
                    }
                });
            }
            var getSystemTpl = systemTyl.innerHTML
                , getSystemView = document.getElementById('systemView');
            laytpl(getSystemTpl).render(data, function (html) {
                getSystemView.innerHTML = html;
            });
        });

        //获取登记访问首页的权限
        getReturnData("/rest/v1.0/user/loadHome", {}, "GET", function (data) {
            if(data){
                $("#homeDiv").addClass("layui-show");
            }else{
                loadHome=false;
            }
        },true);

        //待办量页面跳转
        getReturnData("/rest/v1.0/config/pz", {}, "GET", function (data) {
            var url = "/realestate-portal-ui/view/listTasks.html?clientId=initialClientId&moduleCode=100&loadHome=true&viewType=";
            if(data && data.dbltzym){
                $("#dblurl").attr("lay-href",url+ data.dbltzym);
            }else {
                $("#dblurl").attr("lay-href",url+ "todo");
            }
            if(data && data.zxywbll){
                zxywbll =data.zxywbll;
            }
        },true);

        getReturnData("/rest/v1.0/workflow/process-instances/module/authority", {moduleCode: moduleCode}, "GET", function (data) {
            console.log(data);
            authorityCode = data;
            moduleAuthority.load({
                authorityCode: data
            });
        });

        // 获取系统控件 url
        $.ajax({
            type: "GET",
            url: getContextPath() + "/rest/v1.0/user/xtkj",
            success: function (data) {
                if (!isNullOrEmpty(data)){
                    $("#bdc-xtkj-download").attr("href", data);
                }
            }, error: function (e) {
                response.fail(e, '');
            }
        });

        //点击退出
        $('.bdc-logout').on('click', function () {
            var logoutIndex = layer.confirm('您确定要退出吗？', {
                title: '提示',
                btn: ['确定', '取消'] //按钮
            }, function () {
                layer.close(logoutIndex);
                //退出评价器
                try {
                    LogoutPjq();
                } catch (e) {
                    console.info(e);
                }
                getReturnData("/rest/v1.0/user/logout", {}, "GET", function (data) {
                    var t = (new Date()).getTime();
                    var path = document.location.protocol + "//" + location.host + location.pathname;
                    var search = "?t=" + (new Date()).getTime();
                    for (let i in $paramArr) {
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

        //获取基本信息
        getReturnData("/rest/v1.0/user/info", {}, "GET", function (data) {
            //消息中心推送
            webSocket(data.username);
            $('.bdc-person-nav .bdc-user-name').html(data.alias);
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
        getReturnData("/rest/v1.0/user/menu/" + clientId, {}, "GET", function (data) {
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
            var getAsideTpl = asideTpl.innerHTML
                , getAsideView = document.getElementById('LAY-system-side-menu');
            laytpl(getAsideTpl).render(data, function (html) {
                getAsideView.innerHTML = html;
            });
            element.render('nav', 'layadmin-system-side-menu');
            if (loadHome != 'true') {
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
                }/*
                $('.layadmin-pagetabs').css('padding-left','40px');
                $('#LAY_app_tabsheader li').removeClass('layui-this');
                $('#LAY_app_tabsheader').append('<li lay-id="'+ firstUrl +'" class="layui-this">'+ firstName +'</li>');
                $('#LAY_app_body .layadmin-tabsbody-item').removeClass('layui-show');
                $('#LAY_app_body').append('<div class="layadmin-tabsbody-item layui-show">'+
                    '<iframe src="'+ firstUrl +'" frameborder="0" class="layadmin-iframe"></iframe>'+
                    '</div>');*/
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
        //1.1 监听三级菜单点击
        $('.layui-side-menu .layui-nav').on('click', '.layui-nav-child .layui-nav-child a', function (e) {
            // console.log($(this).offset().top);
            // e.stopPropagation();
            $('.bdc-four-list').hide();
            $(this).siblings().css('top', $(this).offset().top).toggle();
        });
        //1.2 点击四级菜单点击
        $('.layui-side').on('click', '.bdc-four-list p', function () {
            $(this).parent().css('display', 'none');
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

        reloadMsg();

        //3. 消息列表相关
        //3.1 消息列表高度
        $('.bdc-msg-child').height($('.layui-body').height() + 50);
        $('.bdc-msg-child .layui-tab-item').height($('.layui-body').height() - 116);
        //3.2 点击消息列表中的‘×’，关闭
        $('.bdc-msg-title .layui-icon-close').on('click', function () {
            $('.bdc-msg-child').removeClass('layui-show');
        });

        //加载首页的话处理
        if (loadHome == 'true') {
            // 渲染查封到期
            renderCfdq();
            //查询模块显示顺序列表
            getReturnData("/rest/v1.0/collect/module", {}, "GET", function (data) {
                // console.log(data);
                if (data != '') {
                    var getData = data.split(',');
                    console.log(getData);
                    getData.forEach(function (v) {
                        var getId = v - 1;
                        $('#m' + getId).addClass('bdc-hide');
                        $('.bdc-custom-div div p:nth-child(' + v + ') input').removeAttr('checked');

                        if (v == 1) {
                            $('#m1').removeClass('bdc-merge');

                        } else if (v == 2) {
                            $('#m0').removeClass('bdc-merge');
                        }
                    });
                    form.render('checkbox');
                }
                // 根据首页自定义区块隐藏配置，对区块进行隐藏
                zdyqkHide();
            });

            form.on('checkbox(showModuleFilter)', function (data) {
                if ($(data.elem).attr('id') == 'restoreDefault') {
                    //恢复默认
                    $.ajax({
                        url: getContextPath() + "/rest/v1.0/collect/module",
                        type: "POST",
                        data: {
                            _method: "DELETE"
                        },
                        success: function () {
                            //重新渲染
                            window.location.reload();
                        },
                        error: function (err) {
                            response.fail(err, '');
                        }
                    });
                    return false;
                }

                var getIndex = $(data.elem).attr("data-index");
                var getId = getIndex - 1;
                if (data.elem.checked) {
                    $.ajax({
                        url: getContextPath() + "/rest/v1.0/collect/module/back",
                        type: "POST",
                        data: {code: getIndex},
                        success: function (data) {
                            //重新渲染
                            // console.log(data);
                            if (getIndex == 1 || getIndex == 2) {
                                $('#m' + getId).siblings().addClass('bdc-merge');
                            }
                            $('#m' + getId).removeClass('bdc-hide');
                            layer.msg('新增成功');
                        },
                        error: function (err) {
                            response.fail(err, '');
                        }
                    });
                } else {
                    checkboxDeleteModule(getIndex);
                }
            });

            //1.渲染办理情况
            renderBlqk();

            //2.渲染工作提醒
            var getToday = $('.calendar-today').attr('abbr');
            var lunar = {
                tg: '甲乙丙丁戊己庚辛壬癸',
                dz: '子丑寅卯辰巳午未申酉戌亥',
                number: '一二三四五六七八九十',
                year: '鼠牛虎兔龙蛇马羊猴鸡狗猪',
                month: '正二三四五六七八九十冬腊',
                monthadd: [0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334],
                calendar: [0xA4B, 0x5164B, 0x6A5, 0x6D4, 0x415B5, 0x2B6, 0x957, 0x2092F, 0x497, 0x60C96, 0xD4A, 0xEA5, 0x50DA9, 0x5AD, 0x2B6, 0x3126E, 0x92E, 0x7192D, 0xC95, 0xD4A, 0x61B4A, 0xB55, 0x56A, 0x4155B, 0x25D, 0x92D, 0x2192B, 0xA95, 0x71695, 0x6CA, 0xB55, 0x50AB5, 0x4DA, 0xA5B, 0x30A57, 0x52B, 0x8152A, 0xE95, 0x6AA, 0x615AA, 0xAB5, 0x4B6, 0x414AE, 0xA57, 0x526, 0x31D26, 0xD95, 0x70B55, 0x56A, 0x96D, 0x5095D, 0x4AD, 0xA4D, 0x41A4D, 0xD25, 0x81AA5, 0xB54, 0xB6A, 0x612DA, 0x95B, 0x49B, 0x41497, 0xA4B, 0xA164B, 0x6A5, 0x6D4, 0x615B4, 0xAB6, 0x957, 0x5092F, 0x497, 0x64B, 0x30D4A, 0xEA5, 0x80D65, 0x5AC, 0xAB6, 0x5126D, 0x92E, 0xC96, 0x41A95, 0xD4A, 0xDA5, 0x20B55, 0x56A, 0x7155B, 0x25D, 0x92D, 0x5192B, 0xA95, 0xB4A, 0x416AA, 0xAD5, 0x90AB5, 0x4BA, 0xA5B, 0x60A57, 0x52B, 0xA93, 0x40E95]
            };
            //农历
            var lunarDate = getLunarDate(getToday);
            lunarDateString = getLunarDateString(lunarDate);
            //console.log(lunarDateString);
            //星期
            // var day = new Date(Date.parse(getToday));
            var day = new Date();
            var today = ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'];
            week = today[day.getDay()];
            workRemind(clientId);

            //监听工作提醒的一行
            $('.bdc-work-msg').on('mouseenter', '.bdc-work-msg-content', function () {
                $(this).css('border-left-color', '#1d87d1');
            }).on('mouseleave', '.bdc-work-msg-content', function () {
                $(this).css('border-left-color', 'transparent');
            });
            // 待办工作项点击事件
            $('.bdc-work-msg').on('click', '.bdc-work-msg-content', function () {
                var $this = $(this);
                $('.bdc-work-msg .bdc-work-msg-content').removeClass('bdc-work-msg-this');
                $this.addClass('bdc-work-msg-this');
                var id = $this.attr('data-id');
                $.ajax({
                    url: getContextPath() + '/rest/v1.0/user/workmsg/' + id,
                    type: 'GET',
                    success: function (data) {
                        $('#id').val(data.id);
                        $('#msgTitle').val(data.msgTitle);
                        $('#msgContent').val(data.msgContent);
                        $('#optStartTime').val(data.optStartTime);
                        $('#optEndTime').val(data.optEndTime);
                        // 是否已经办理默认是待办不会被修改
                        layer.open({
                            title: '待办工作项',
                            type: 1,
                            area: ['960px'],
                            btn: ['保存', '取消'],
                            content: $('#bdc-popup-large')
                            , yes: function (index, layero) {
                                var read = $("[name=read]:checked").val();
                                if (read === "1") {
                                    $.ajax({
                                        url: getContextPath() + "/rest/v1.0/user/workmsg/read/" + data.id,
                                        type: 'GET',
                                        contentType: "application/json;charset=utf-8",
                                        success: function () {
                                            layer.close(index);
                                            workRemind(clientId);
                                        }
                                    });
                                }
                            }
                            , btn2: function (index, layero) {
                                //取消 的回调
                            }
                            , cancel: function () {
                                //右上角关闭回调
                                //return false 开启该代码可禁止点击该按钮关闭
                            }
                            , success: function () {
                                laydate.render({
                                    elem: '#optStartTime',
                                    type: 'datetime'
                                });
                                laydate.render({
                                    elem: '#optEndTime',
                                    type: 'datetime'
                                });

                                //监听是否选择全天
                                var timeData = [
                                    '00:00',
                                    '00:30',
                                    '01:00',
                                    '01:30',
                                    '02:00',
                                    '02:30',
                                    '03:00',
                                    '03:30',
                                    '04:00',
                                    '04:30',
                                    '05:00',
                                    '05:30',
                                    '06:00',
                                    '06:30',
                                    '07:00',
                                    '07:30',
                                    '08:00',
                                    '08:30',
                                    '09:00',
                                    '09:30',
                                    '10:00',
                                    '10:30',
                                    '11:00',
                                    '11:30',
                                    '12:00',
                                    '12:30',
                                    '13:00',
                                    '13:30',
                                    '14:00',
                                    '14:30',
                                    '15:00',
                                    '15:30',
                                    '16:00',
                                    '16:30',
                                    '17:00',
                                    '17:30',
                                    '18:00',
                                    '18:30',
                                    '19:00',
                                    '19:30',
                                    '10:30',
                                    '21:00',
                                    '21:30',
                                    '22:00',
                                    '22:30',
                                    '23:00',
                                    '23:30'
                                ];
                                var nameHtml = ' ';
                                for (var i = 0; i < timeData.length; i++) {
                                    if (timeData[i] == '12:00') {
                                        nameHtml += '<option value=' + timeData[i] + ' selected>' + timeData[i] + '</option>'
                                    } else {
                                        nameHtml += '<option value=' + timeData[i] + '>' + timeData[i] + '</option>'
                                    }
                                }
                                $('#bdc-popup-large .bdc-time-select select').html(nameHtml);
                                form.render('select');

                                form.on('checkbox(checkAllDay)', function (data) {
                                    if (data.elem.checked) {
                                        $('#bdc-popup-large .bdc-time-select select').attr('disabled', 'off');
                                        $('#bdc-popup-large .bdc-time-select select').html(nameHtml);
                                        form.render('select');
                                        //1.2 select框禁用修改下拉图标(动态渲染的要在渲染完成之后执行以下两行js代码)
                                        $('.layui-select-disabled i').addClass('bdc-hide');
                                        $('.layui-select-disabled').append('<img src="../static/lib/bdcui/images/lock.png" alt="">');
                                        $('.layui-select-disabled .layui-disabled').attr('disabled', 'true');
                                    } else {
                                        $('#bdc-popup-large .bdc-time-select select').removeAttr('disabled');
                                        $('#bdc-popup-large .bdc-time-select select').html(nameHtml);
                                        form.render('select');
                                        //1.2 select框禁用修改下拉图标(动态渲染的要在渲染完成之后执行以下两行js代码)
                                        $('.layui-select-disabled i').removeClass('bdc-hide');
                                        $('.layui-select-disabled img').remove();
                                        $('.layui-select-disabled .layui-disabled').removeAttr('disabled');
                                    }
                                });
                            }
                        });
                    }, error: function (e) {
                        response.fail(e, '');
                    }, complete: function () {
                        result = false;
                    }
                })
            });

            function showHangReson(checkData) {
                var result = true;
                if (checkData.state == 2 || checkData.procStatus == 3) {
                    $.ajax({
                        url: getContextPath() + '/rest/v1.0/workflow/process-instances/opinion',
                        type: 'POST',
                        data: {processInstanceId: checkData.processInstanceId, type: 'suspend_opinion'},
                        async: false,
                        success: function (data) {
                            var name = data.userAlisa;
                            var opinion = data.opinion;
                            var msg = '流程已挂起';
                            if (name != '' && name != undefined) {
                                msg = msg + '，挂起人：' + name;
                            }
                            if (opinion != '' && opinion != undefined) {
                                msg = msg + '，挂起原因：' + opinion;
                            }
                            layer.msg(msg, {area: ['auto', 'auto']});
                        }, error: function (e) {
                            response.fail(e, '');
                        }, complete: function () {
                            result = false;
                        }
                    });
                }
                return result;
            }


            //3.渲染操作日志
            renderCzrz();

            //4.渲染数据分析
            //默认渲染  日
            getReturnData("/rest/v1.0/user/workdata", {"dateType": 'day'}, "GET", function (data) {
                // console.log(data);
                var xDataDay = [];
                var yBllDataDay = [];
                var yRwlDataDay = [];
                data.all.forEach(function (v) {
                    xDataDay.push(v.dateStr);
                    yRwlDataDay.push(v.count);
                });
                data.complete.forEach(function (v) {
                    yBllDataDay.push(v.count);
                });
                renderChar('analysisCharDay', xDataDay, yBllDataDay, yRwlDataDay);
            });

            //监听选项卡切换
            element.on('tab(dateChangeFilter)', function (data) {
                switch (data.index) {
                    case 1:
                        //周
                        weekIndex++;
                        if (weekIndex == 1) {
                            getReturnData("/rest/v1.0/user/workdata", {"dateType": 'week'}, "GET", function (data) {
                                //console.log(data);
                                var xDataWeek = [];
                                var yBllDataWeek = [];
                                var yRwlDataWeek = [];
                                data.all.forEach(function (v) {
                                    xDataWeek.push(v.dateStr);
                                    yRwlDataWeek.push(v.count);
                                });
                                data.complete.forEach(function (v) {
                                    yBllDataWeek.push(v.count);
                                });
                                renderChar('analysisCharWeek', xDataWeek, yBllDataWeek, yRwlDataWeek);
                            });
                        }
                        break;
                    case 2:
                        //月
                        monthIndex++;
                        if (monthIndex == 1) {
                            getReturnData("/rest/v1.0/user/workdata", {"dateType": 'month'}, "GET", function (data) {
                                //console.log(data);
                                var xDataMonth = [];
                                var yBllDataMonth = [];
                                var yRwlDataMonth = [];
                                data.all.forEach(function (v) {
                                    xDataMonth.push(v.dateStr);
                                    yRwlDataMonth.push(v.count);
                                });
                                data.complete.forEach(function (v) {
                                    yBllDataMonth.push(v.count);
                                });
                                renderChar('analysisCharMonth', xDataMonth, yBllDataMonth, yRwlDataMonth);
                            });
                        }
                        break;
                    case 3:
                        //年
                        yearIndex++;
                        if (yearIndex == 1) {
                            getReturnData("/rest/v1.0/user/workdata", {"dateType": 'year'}, "GET", function (data) {
                                //console.log(data);
                                var xDataYear = [];
                                var yBllDataYear = [];
                                var yRwlDataYear = [];
                                data.all.forEach(function (v) {
                                    xDataYear.push(v.dateStr);
                                    yRwlDataYear.push(v.count);
                                });
                                data.complete.forEach(function (v) {
                                    yBllDataYear.push(v.count);
                                });
                                renderChar('analysisCharYear', xDataYear, yBllDataYear, yRwlDataYear);
                            });
                        }
                        break;
                }
            });

            // 5. 渲染版本更新
            renderBbgx();

            // 修改星期文字
            //updateWeekWords();
            $('.calendar-body .calendar-day').on('click', function () {
                var date = $(this).attr('abbr');
                // workRemind(date);
            });

            //鼠标覆盖...  显示更多内容
            $('.bdc-operate-default').on('mouseenter', function () {
                $(this).siblings('.bdc-operate-checked').toggleClass('bdc-hide');
                $(this).siblings('.bdc-common-more').removeClass('bdc-hide');
                $(this).toggleClass('bdc-hide');
            });
            $('.bdc-others-more').on('mouseleave', function () {
                $(this).find('img').toggleClass('bdc-hide');
                $(this).find('.bdc-common-more').addClass('bdc-hide');
            });
            $('.bdc-operate-more').on('mouseleave', function () {
                $(this).find('.bdc-common-more').addClass('bdc-hide');
                $(this).find('img').toggleClass('bdc-hide');
            });
            $('.bdc-common-more>span').on('click', function () {
                $(this).parent().addClass('bdc-hide');
            });

            //点击新增工作项
            $('.bdc-add-tips-btn').on('click', function () {
                $('#msgTitle').val("");
                $('#msgContent').val("");
                $('#optStartTime').val("");
                $('#optEndTime').val("");
                layer.open({
                    title: '新增工作项',
                    type: 1,
                    area: ['960px'],
                    btn: ['保存', '取消'],
                    content: $('#bdc-popup-large')
                    , yes: function (index, layero) {
                        workform = $('#workMsg');
                        var postObj = {};
                        postObj.msgTitle = workform.find('#msgTitle').val();
                        postObj.msgContent = workform.find('#msgContent').val();
                        postObj.msgTypeName = workform.find('#msgTitle').val();
                        postObj.optStartTime = workform.find('#optStartTime').val();
                        postObj.optEndTime = workform.find('#optEndTime').val();
                        postObj.read = $("[name=read]:checked").val();
                        postObj.options = "save";
                        // 判断开始时间和结束时间
                        if (completeDate(postObj.optStartTime, postObj.optEndTime)) {
                            warnMsg('开始时间不能大于结束时间');
                            return false;
                        }
                        $.ajax({
                            url: getContextPath() + "/rest/v1.0/user/workmsg",
                            type: 'post',
                            data: JSON.stringify(postObj),
                            contentType: "application/json;charset=utf-8",
                            dataType: "json",
                            success: function () {
                                layer.close(index);
                                workRemind(clientId);
                            }
                        });
                    }
                    , btn2: function (index, layero) {
                        //取消 的回调
                    }
                    , cancel: function () {
                        //右上角关闭回调
                        //return false 开启该代码可禁止点击该按钮关闭
                    }
                    , success: function () {
                        laydate.render({
                            elem: '#optStartTime',
                            type: 'datetime'
                        });
                        laydate.render({
                            elem: '#optEndTime',
                            type: 'datetime'
                        });

                        //监听是否选择全天
                        var timeData = [
                            '00:00',
                            '00:30',
                            '01:00',
                            '01:30',
                            '02:00',
                            '02:30',
                            '03:00',
                            '03:30',
                            '04:00',
                            '04:30',
                            '05:00',
                            '05:30',
                            '06:00',
                            '06:30',
                            '07:00',
                            '07:30',
                            '08:00',
                            '08:30',
                            '09:00',
                            '09:30',
                            '10:00',
                            '10:30',
                            '11:00',
                            '11:30',
                            '12:00',
                            '12:30',
                            '13:00',
                            '13:30',
                            '14:00',
                            '14:30',
                            '15:00',
                            '15:30',
                            '16:00',
                            '16:30',
                            '17:00',
                            '17:30',
                            '18:00',
                            '18:30',
                            '19:00',
                            '19:30',
                            '10:30',
                            '21:00',
                            '21:30',
                            '22:00',
                            '22:30',
                            '23:00',
                            '23:30'
                        ];
                        var nameHtml = ' ';
                        for (var i = 0; i < timeData.length; i++) {
                            if (timeData[i] == '12:00') {
                                nameHtml += '<option value=' + timeData[i] + ' selected>' + timeData[i] + '</option>'
                            } else {
                                nameHtml += '<option value=' + timeData[i] + '>' + timeData[i] + '</option>'
                            }
                        }
                        $('#bdc-popup-large .bdc-time-select select').html(nameHtml);
                        form.render('select');

                        form.on('checkbox(checkAllDay)', function (data) {
                            if (data.elem.checked) {
                                $('#bdc-popup-large .bdc-time-select select').attr('disabled', 'off');
                                $('#bdc-popup-large .bdc-time-select select').html(nameHtml);
                                form.render('select');
                                //1.2 select框禁用修改下拉图标(动态渲染的要在渲染完成之后执行以下两行js代码)
                                $('.layui-select-disabled i').addClass('bdc-hide');
                                $('.layui-select-disabled').append('<img src="../static/lib/bdcui/images/lock.png" alt="">');
                                $('.layui-select-disabled .layui-disabled').attr('disabled', 'true');
                            } else {
                                $('#bdc-popup-large .bdc-time-select select').removeAttr('disabled');
                                $('#bdc-popup-large .bdc-time-select select').html(nameHtml);
                                form.render('select');
                                //1.2 select框禁用修改下拉图标(动态渲染的要在渲染完成之后执行以下两行js代码)
                                $('.layui-select-disabled i').removeClass('bdc-hide');
                                $('.layui-select-disabled img').remove();
                                $('.layui-select-disabled .layui-disabled').removeAttr('disabled');
                            }
                        });
                    }
                });
            });

            // 点击 查封更多 按钮
            $('.bdc-inner-content').on('click', '.bdc-cf-more', function () {
                var ip = document.location.protocol + "//" + window.location.host;
                // 传递参数 sfcq 为 1 默认查询到期的项目
                var cf = "/realestate-inquiry-ui/view/dtcx/commonCx.html?sfcq=1&cxdh=cfxx";
                var path = ip + cf;

                // 如果不存在该tab则新打开，如果已存在则直接打开tab页
                if ($(document).find('iframe[src=' + '"' + path + '"' + ']').length === 0) {
                    addTab(ip + cf, "查封到期");
                } else {
                    $('li[lay-id="test.html"]').trigger('click');
                }
            });

            // 自定义标签的悬浮和移出事件
            $(".bdc-custom").hover(function () {
                $(".bdc-custom-div").removeClass("bdc-hide");
            }, function () {
                $(".bdc-custom-div").addClass("bdc-hide");
            });

            //监听滚动
            $('.layadmin-tabsbody-item').scroll(function () {
                var scrollH = $('.layadmin-tabsbody-item.layui-show').scrollTop();
                $('.bdc-other-tips-box').css('bottom', '-' + scrollH + 'px');
            });

        } else {
            $("#LAY_app_tabsheader_home").attr("style", "display:none!important");
            LAY_app_body.innerHTML='<div class="layadmin-tabsbody-item"></div><div class="layadmin-tabsbody-item"></div>';
            $('.layadmin-pagetabs').css('padding-left','40px');
        }

        //解决跨域问题
        $('.layui-side').on('click', '.bdc-show-msg', function () {
            setTimeout(function () {
                $('.layadmin-tabsbody-item:last-child').append('<iframe id="test2" src="" frameborder="0" width="100%" height="0"></iframe>');
            }, 1000);
        });

        //点击自己出的 删除
        $('.bdc-outer-tab').on('click', '.bdc-tab-close', function () {
            var _this = $(this).parent();
            var prevIndex = _this.index();
            var nowIndex = _this.index() + 1;
            if (_this.hasClass('layui-this')) {
                // var nextLiIndex = nowIndex + 1;
                _this.parent().find('li').removeClass('layui-this');
                _this.parent().find('li:nth-child(' + prevIndex + ')').addClass('layui-this');
                _this.remove();
                $('.layui-body .layadmin-tabsbody-item:nth-child(' + nowIndex + ')').remove();
                $('.layui-body .layadmin-tabsbody-item').removeClass('layui-show');
                $('.layui-body .layadmin-tabsbody-item:nth-child(' + prevIndex + ')').addClass('layui-show');
            } else {
                _this.remove();
                $('.layui-body .layadmin-tabsbody-item:nth-child(' + nowIndex + ')').remove();
            }

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
        //监听消息列表中的点击
        $('.bdc-msg-nav').on('click', '.layui-tab-item a', function () {
            // 跳转
            window.open('../view/msg-detail.html?id='+$(this).data('id'));
        })

        //监听消息列表中的 ×
        $('.bdc-msg-nav').on('click', '.layui-tab-item .layui-icon-close', function () {
            // 删除操作
            var className = this.parentElement.parentElement.className;
            var tpl = className == 'bdc-read' ? readMsgTpl : '';
            var ids = [];
            ids.push(this.id);
            //console.log('ids==='+ids);
            $.ajax({
                type: "DELETE",
                url: getContextPath() + "/rest/v1.0/msg/deleteMessages",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: JSON.stringify(ids),
                async: false,
                success: function () {
                    layer.msg('删除消息成功！');
                    reloadMsg(tpl);
                }, error: function (e) {
                    response.fail(e, '');
                }
            });
        });
        //监听消息中的 更多
        $('.bdc-msg-nav').on('click', '.bdc-more-msg', function () {
            window.open('../view/news-msg.html?moduleCode=msgs');
        });

        //获取农历
        function getLunarDate(date) {
            var year, month, day;
            if (!date) {
                date = new Date(), year = date.getFullYear(), month = date.getMonth(), day = date.getDate();
            } else {
                date = date.split('-'), year = parseInt(date[0]), month = date[1] - 1, day = parseInt(date[2]);
            }

            if (year < 1921 || year > 2020) {
                return {}
            }

            var total, m, n, k, bit, lunarYear, lunarMonth, lunarDay;
            var isEnd = false;
            var tmp = year;
            if (tmp < 1900) {
                tmp += 1900;
            }
            total = (tmp - 1921) * 365 + Math.floor((tmp - 1921) / 4) + lunar.monthadd[month] + day - 38;
            if (year % 4 == 0 && month > 1) {
                total++;
            }
            for (m = 0; ; m++) {
                k = (lunar.calendar[m] < 0xfff) ? 11 : 12;
                for (n = k; n >= 0; n--) {
                    bit = (lunar.calendar[m] >> n) & 1;
                    if (total <= 29 + bit) {
                        isEnd = true;
                        break;
                    }
                    total = total - 29 - bit;
                }
                if (isEnd) break;
            }
            lunarYear = 1921 + m;
            lunarMonth = k - n + 1;
            lunarDay = total;
            if (k == 12) {
                if (lunarMonth == Math.floor(lunar.calendar[m] / 0x10000) + 1) {
                    lunarMonth = 1 - lunarMonth;
                }
                if (lunarMonth > Math.floor(lunar.calendar[m] / 0x10000) + 1) {
                    lunarMonth--;
                }
            }

            return {
                lunarYear: lunarYear,
                lunarMonth: lunarMonth,
                lunarDay: lunarDay,
            };
        }

        function getLunarDateString(lunarDate) {
            if (!lunarDate.lunarDay) return;
            var data = {},
                lunarYear = lunarDate.lunarYear,
                lunarMonth = lunarDate.lunarMonth,
                lunarDay = lunarDate.lunarDay;

            data.tg = lunar.tg.charAt((lunarYear - 4) % 10);
            data.dz = lunar.dz.charAt((lunarYear - 4) % 12);
            data.year = lunar.year.charAt((lunarYear - 4) % 12);
            data.month = lunarMonth < 1 ? '(闰)' + lunar.month.charAt(-lunarMonth - 1) : lunar.month.charAt(lunarMonth - 1);

            data.day = (lunarDay < 11) ? "初" : ((lunarDay < 20) ? "十" : ((lunarDay < 30) ? "廿" : "三十"));
            if (lunarDay % 10 != 0 || lunarDay == 10) {
                data.day += lunar.number.charAt((lunarDay - 1) % 10);
            }

            return data;
        }
    });

    function webSocket(username) {
        var websocket;
        // 首先判断是否 支持 WebSocket
        if ('WebSocket' in window) {
            websocket = new WebSocket("ws://" + window.location.host + getContextPath() + "/wsInfo?username=" + username+"&type=HOME");
        } else if ('MozWebSocket' in window) {
            websocket = new MozWebSocket("ws://" + window.location.host + getContextPath() + "/wsInfo?username=" + username+"&type=HOME");
        } else {
            websocket = new SockJS("ws://" + window.location.host + getContextPath() + "/wsInfo?username=" + username+"&type=HOME");
        }
        // 打开连接时
        websocket.onopen = function (event) {
            console.log(" websocket.onopen");
        };
        // 收到消息时
        websocket.onmessage = function (event) {
            var msg = $.parseJSON(event.data);
            if(msg.msgType=='YHQSPXX'){
                bottomShowTips(msg.msgContent);
                return ;
            }
            if(msg.msgType!='CUSTOM'){
                bottomShowTips(msg.msgTypeName+"("+msg.msgTitle+")");
            }

            if (msg.msgType === "USER_DISCONNECT") {
                var childWindow = $(".layadmin-tabsbody-item:nth-child(3) iframe")[0].contentWindow;
                childWindow.refreshOpen(msg);
                // 接收到登出消息，刷新页面即可
                layer.open({
                    title: msg.msgTypeName
                    , content: '您的账户已在别处登陆，您被迫下线，请核实！'
                    , yes: function (index, layero) {
                        window.location.reload();
                    }, cancel: function () {
                        window.location.reload();
                    }
                });
            } else {
                // 登出系统不需要去渲染消息列表
                reloadMsg();
            }
        };
        websocket.onerror = function (event) {
            console.log("websocket.onerror");
        };
        websocket.onclose = function (event) {
            console.log("websocket.onclose");
        };
    }

    /**
     * 加载消息中心
     */
    function reloadMsg(tpl) {
        // 获取消息中心列表
        $.ajax({
            type: "POST",
            url: getContextPath() + "/rest/v1.0/msg/home/list",
            success: function (data) {
                var getMsgTitleTpl = msgTitleTpl.innerHTML
                    , getMsgTpl = tpl == undefined || tpl == '' ? msgTpl.innerHTML : tpl.innerHTML,
                    getMsgView = document.getElementById('msgView'),
                    getMsgTitleView = document.getElementById('msgTitleView');
                laytpl(getMsgTitleTpl).render(data, function (html) {
                    getMsgTitleView.innerHTML = html;
                });
                laytpl(getMsgTpl).render(data, function (html) {
                    getMsgView.innerHTML = html;
                });
                if(data.qrList && data.qrList.length>0){
                    var dataMsg=[];
                    var msgId=[];
                    data.qrList.forEach(function (v) {
                        var user = {};
                        user.msgContent = v.msgContent;
                        user.name = v.producerName;
                        user.msgCode = v.msgCode;
                        user.fjbs = v.fjbs;
                        dataMsg.push(user);
                        msgId.push(v.id);
                    });
                    strongTips(dataMsg,function () {
                        $.ajax({
                            type:"PUT",
                            url: getContextPath() + "/rest/v1.0/msg/readMessages",
                            contentType: "application/json;charset=utf-8",
                            data: JSON.stringify(msgId),
                            success: function () {
                                reloadMsg(tpl);
                            }, error: function (e) {
                            }
                        });
                    });
                }
            }, error: function (e) {
                response.fail(e, '');
            }
        });
    }


    //删除模块
    window.deleteModule = function (index) {
        var id = index - 1;
        $('#m' + id).addClass('bdc-hide');
        if (index == 1) {
            $('#m1').removeClass('bdc-merge');
        } else if (index == 2) {
            $('#m0').removeClass('bdc-merge');
        }
        $('.bdc-custom-div div p:nth-child(' + index + ') input').removeAttr('checked');
        form.render('checkbox');
        $.ajax({
            url: getContextPath() + "/rest/v1.0/collect/module",
            type: "POST",
            data: {code: index},
            success: function (data) {
                //重新渲染
                console.log(data);
                layer.msg('移除成功');
            },
            error: function (err) {
                response.fail(err, '');
            }
        });
    };

    function checkboxDeleteModule(index) {
        var id = index - 1;
        $('#m' + id).addClass('bdc-hide');
        if (index == 1) {
            $('#m1').removeClass('bdc-merge');
        } else if (index == 2) {
            $('#m0').removeClass('bdc-merge');
        }
        $.ajax({
            url: getContextPath() + "/rest/v1.0/collect/module",
            type: "POST",
            data: {code: index},
            success: function (data) {
                //重新渲染
                console.log(data);
                layer.msg('移除成功');
            },
            error: function (err) {
                response.fail(err, '');
            }
        });
    }

    // 监控关闭其他标签页的点击事件，并触发leftPage方法
    $('#closeOtherTabs').on('click', function() {
        setTimeout(function() {
            $('div[layadmin-event="leftPage"]').click();
        },50);
    });

});

//渲染办理情况
function renderBlqk() {
    getReturnData("/rest/v1.0/user/backlog", {}, "GET", function (data) {
        $('.bdc-wait-handle-num').html(data.todo);
        $('.bdc-done-handle-num').html(data.complete);
        $('.bdc-warn-handle-num').html(data.timeout);
        //连云港新增专项业务办理量
        $('.bdc-done-zxhandle-num').html(data.special);
    });
    if( !isNullOrEmpty(zxywbll)&&zxywbll==true){
        $("#zxyw").show();
        $(".bdc-handle-msg-box").css('width','25%')
    }
}

//渲染查封到期
function renderCfdq() {
    getReturnData("/rest/v1.0/user/cfdq", {}, "GET", function (data) {
        var getCfdqTpl = cfdqTpl.innerHTML
            , getCfdqView = $('#CfdqView');
        getCfdqView.html('<button class="layui-btn layui-btn-sm bdc-cf-more">更多</button>');
        laytpl(getCfdqTpl).render(data, function (html) {
            getCfdqView.append(html);
        });
    });
}

//渲染版本更新
function renderBbgx() {
    getReturnData("/rest/v1.0/xtbb/bbgk", {}, "GET", function (data) {
        if (!isNullOrEmpty(data)) {
            var getSystemVerTpl = systemVerTpl.innerHTML
                , getSystemVerView = document.getElementById('systemVerView');
            laytpl(getSystemVerTpl).render(data, function (html) {
                getSystemVerView.innerHTML = html;
            });
        }
    });
}

//工作提醒
function workRemind(clientId) {
    getReturnData("/rest/v1.0/user/workmsg", {clientId: clientId}, "GET", function (data) {
        var getWorkTpl = workTpl.innerHTML
            , getWorkView = document.getElementById('workView');
        laytpl(getWorkTpl).render(data.content, function (html) {
            getWorkView.innerHTML = '<h5 class="bdc-work-msg-title">待办工作项</h5>';
            getWorkView.innerHTML += html;
        });
    });
}

//操作日志
function renderCzrz() {
    getReturnData("/rest/v1.0/user/loginfo", {}, "GET", function (data) {
        data.forEach(function (v) {
            v.time = v.time.split(" ")[0];
            v.details.forEach(function (d) {
                times = d.time.split(" ");
                if (times.length > 1) {
                    d.time = times[1];
                }
            })
        });
        var getOperateLogTpl = operateLogTpl.innerHTML
            , getOperateLogView = document.getElementById('operateLogView');
        laytpl(getOperateLogTpl).render(data, function (html) {
            getOperateLogView.innerHTML = html;
        });
    });
}

//数据分析
function renderSjfx() {
    weekIndex = 0;
    monthIndex = 0;
    yearIndex = 0;

    $('.bdc-sjfx-tab').html('<ul class="layui-tab-title">' +
        '<li class="layui-this">日</li>' +
        '<li>周</li>' +
        '<li>月</li>' +
        '</ul>' +
        '<div class="layui-tab-content">' +
        '<div class="layui-tab-item layui-show">' +
        '<div id="analysisCharDay" class="bdc-analysis-char"></div>' +
        '</div>' +
        '<div class="layui-tab-item">' +
        '<div id="analysisCharWeek" class="bdc-analysis-char"></div>' +
        '</div>' +
        '<div class="layui-tab-item">' +
        '<div id="analysisCharMonth" class="bdc-analysis-char"></div>' +
        '</div>' +
        '</div>');

    //默认渲染  日
    getReturnData("/rest/v1.0/user/workdata", {"dateType": 'day'}, "GET", function (data) {
        // console.log(data);
        var xDataDay = [];
        var yBllDataDay = [];
        var yRwlDataDay = [];
        data.all.forEach(function (v) {
            xDataDay.push(v.dateStr);
            yRwlDataDay.push(v.count);
        });
        data.complete.forEach(function (v) {
            yBllDataDay.push(v.count);
        });
        renderChar('analysisCharDay', xDataDay, yBllDataDay, yRwlDataDay);
    });
}

//折线图
function renderChar(id, xData, yBllData, yRwlData) {
    var myChart = echarts.init(document.getElementById(id));
    var option = {
        title: {
            text: '变化对比',
            textStyle: {
                color: '#333',
                fontSize: '14px'
            },
            x: 'center',
            top: '15px'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['办理量', '新增任务量'],
            top: '32px',
            right: '70px'
        },
        grid: {
            top: '60px',
            left: '20px',
            right: '20px',
            bottom: '20px',
            containLabel: true
        },
        xAxis: {
            type: 'category',
            splitLine: {
                show: true
            },
            data: xData
        },
        yAxis: {
            type: 'value',
            splitLine: {
                show: true
            }
        },
        series: [
            {
                name: '办理量',
                type: 'line',
                data: yBllData,
                itemStyle: {
                    normal: {
                        color: '#1d87d1',
                        lineStyle: {
                            color: '#1d87d1'
                        }
                    }
                }
            },
            {
                name: '新增任务量',
                type: 'line',
                data: yRwlData,
                itemStyle: {
                    normal: {
                        color: '#88e5d0',
                        lineStyle: {
                            color: '#88e5d0'
                        }
                    }
                }
            }
        ]
    };
    myChart.setOption(option);
}

function formatDate(time) {
    var newStartTime = new Date(time);
    return newStartTime.toLocaleString();
}

// 自定义区块隐藏
function zdyqkHide(){
    getReturnData("/rest/v1.0/collect/symk/pz", {}, "GET", function (data) {
        if(isNotBlank(data)){
            console.info(data);
            $.each(data, function(index, value){
                var v = parseInt(value);
                $('.bdc-custom-div div p:nth-child(' + v + ') input').parent("p").hide();
            });
        }
    });
}
function initTimer() {
    $.ajax({
        url: "/realestate-portal-ui/rest/v1.0/config/getTimerConfig",
        type: "GET",
        data:{},
        success: function (res) {
            if (res.switch){
                timer = window.setInterval("isOffline(1);",res.time);
            }
        }
    })
}