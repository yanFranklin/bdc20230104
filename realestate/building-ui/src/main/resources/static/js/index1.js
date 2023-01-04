/**
 * Created by Administrator on 2018/12/21.
 */
layui.use(['jquery', 'laytpl','element','table','form'], function () {
    var $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table,
        form = layui.form;
    $(function () {
        // 手风琴点击效果
        $(document).on('click', '.link', function () {
            var $this = $(this);
            $this.parents('.accordion').find('.submenu').not($this.next()).slideUp().parent().removeClass('open');
            $this.next().slideToggle();
            $this.parent().toggleClass('open');
        });
        // 默认第一个逻辑栋高亮
        $('.accordion>li').first().addClass('open').find('.submenu').show().find('a').first().addClass('active');

        // 对于逻辑栋点击高亮
        $(document).on('click', '.submenu a', function () {
            $('.submenu a').removeClass('active');
            $(this).addClass('active');
        });

        //点击侧边栏收缩按钮
        var $asideClose = $('.bdc-aside-close'),
            $asideOpen = $('.bdc-aside-open'),
            $zoom = $('.bdc-aside-zoom'),
            $menuAside = $('.bdc-menu-aside'),
            $container = $('.bdc-container');
        $asideClose.on('click',function () {
            $(this).toggleClass('bdc-hide');
            $asideOpen.toggleClass('bdc-hide');
            $zoom.animate({'left':'-10px'},100);
            $menuAside.animate({'left':'-300px'},100);
            $container.animate({'padding-left':'15px'},100,function () {
                table.resize('resizeId');
            });
            $('.bdc-btn-list').animate({"width": $('.bdc-table-box').width() + 280},100);
        });
        $asideOpen.on('click',function () {
            $(this).toggleClass('bdc-hide');
            $asideClose.toggleClass('bdc-hide');
            $zoom.animate({'left':'259px'},100);
            $menuAside.animate({'left':'0'},100);
            $container.animate({'padding-left':'275px'},100,function () {
                $('.bdc-btn-list').width($('.layui-table').width() -31);
                table.resize('resizeId');
            });
        });

        //状态展示收缩按钮
        var stateIndex = 0;
        $('.bdc-state-zoom').on('click',function () {
            stateIndex++;
            if(stateIndex % 2 == 0){
                //展示
                $('.bdc-state-show').removeClass('bdc-state-hide').animate({"height": '106px'},100);
                $(this).animate({"top": '125px'},100);
                // $('.layui-form.layui-border-box').height($('.layui-form.layui-border-box').height() - 125);

                table.reload('resizeId', {
                    height: 'full-232'
                });
            }else {
                //隐藏
                $('.bdc-state-show').addClass('bdc-state-hide').animate({"height": 0},100);
                $(this).animate({"top": '0'},100);
                // $('.layui-form.layui-border-box').height($('.layui-form.layui-border-box').height() + 125);
                table.reload('resizeId', {
                    height: 'full-107'
                })
            }
            $(this).find('.layui-icon-down').toggleClass('bdc-hide');
            $(this).find('.layui-icon-up').toggleClass('bdc-hide');
        });

        //测试
        var getData = {
            "dyList": [
                {
                    "colspan": 1,
                    "dyh": "2"
                },
                {
                    "colspan": 2,
                    "dyh": "3"
                },
                {
                    "colspan": 9,
                    "dyh": "1"
                }
            ],
            "cList": [],
            "cFwList": [
                {
                    "dyFwList": [
                        {
                            "dyh": "2",
                            "maxHs": 1,
                            "fwhsResourceDTOList": [

                            ]
                        },
                        {
                            "dyh": "3",
                            "maxHs": 2,
                            "fwhsResourceDTOList": [
                                {
                                    "info": {
                                        "fwHsIndex": {
                                            "value": "f167f9ffda77402c80d467f9ffd80009",
                                            "desc": null
                                        },
                                        "wlcs": {
                                            "value": 2,
                                            "desc": null
                                        },
                                        "hbhs": {
                                            "desc": null
                                        },
                                        "hbfx": {
                                            "desc": null
                                        },
                                        "dyh": {
                                            "value": "3-1-1",
                                            "desc": "单元号"
                                        },
                                        "zl": {
                                            "desc": "坐落"
                                        },
                                        "fjh": {
                                            "value": "301",
                                            "desc": null
                                        },
                                        "bdcdyh": {
                                            "value": "320684114304GB00008F00020050",
                                            "desc": null
                                        },
                                        "dycs": {
                                            "value": "2",
                                            "desc": null
                                        },
                                        "fwbm": {
                                            "value": "32068411430401000080020505",
                                            "desc": null
                                        },
                                        "scjzmj": {
                                            "desc": "实测建筑面积"
                                        }
                                    },
                                    "status": {
                                        "yy": 0,
                                        "sd": 0,
                                        "cf": 0,
                                        "zjgcdy": 0,
                                        "dyi": 0,
                                        "dj": 0,
                                        "ks": 0,
                                        "ys": 0,
                                        "ydya": 0,
                                        "dya": 0,
                                        "ycf": 0,
                                        "yg": 0
                                    },
                                    "links": [
                                        {
                                            "rel": "fwhsinfo",
                                            "href": "http://fwhsinfoUrl"
                                        }
                                    ]
                                },
                                {
                                    "info": {
                                        "fwHsIndex": {
                                            "value": "f167f9ffda77402c80d467f9ffd80009",
                                            "desc": null
                                        },
                                        "wlcs": {
                                            "value": 2,
                                            "desc": null
                                        },
                                        "hbhs": {
                                            "desc": null
                                        },
                                        "hbfx": {
                                            "desc": null
                                        },
                                        "dyh": {
                                            "value": "3-1-2",
                                            "desc": "单元号"
                                        },
                                        "zl": {
                                            "desc": "坐落"
                                        },
                                        "fjh": {
                                            "value": "302",
                                            "desc": null
                                        },
                                        "bdcdyh": {
                                            "value": "320684114304GB00008F00020050",
                                            "desc": null
                                        },
                                        "dycs": {
                                            "value": "2",
                                            "desc": null
                                        },
                                        "fwbm": {
                                            "value": "32068411430401000080020505",
                                            "desc": null
                                        },
                                        "scjzmj": {
                                            "desc": "实测建筑面积"
                                        }
                                    },
                                    "status": {
                                        "yy": 0,
                                        "sd": 0,
                                        "cf": 0,
                                        "zjgcdy": 0,
                                        "dyi": 0,
                                        "dj": 0,
                                        "ks": 0,
                                        "ys": 0,
                                        "ydya": 0,
                                        "dya": 0,
                                        "ycf": 0,
                                        "yg": 0
                                    },
                                    "links": [
                                        {
                                            "rel": "fwhsinfo",
                                            "href": "http://fwhsinfoUrl"
                                        }
                                    ]
                                }
                            ]
                        },
                        {
                            "dyh": "1",
                            "maxHs": 9,
                            "fwhsResourceDTOList": [
                                {
                                    "info": {
                                        "fwHsIndex": {
                                            "value": "1086ab76-def9-48e2-b358-88b9c8e9f1df",
                                            "desc": null
                                        },
                                        "wlcs": {
                                            "value": 8,
                                            "desc": null
                                        },
                                        "dyh": {
                                            "value": "1",
                                            "desc": "单元号"
                                        },
                                        "zl": {
                                            "value": "世纪花园1号44",
                                            "desc": "坐落"
                                        },
                                        "hbhs": {
                                            "desc": null
                                        },
                                        "fjh": {
                                            "value": "801",
                                            "desc": null
                                        },
                                        "bdcdyh": {
                                            "value": "320684106208GB00104F00010044",
                                            "desc": null
                                        },
                                        "dycs": {
                                            "value": "8",
                                            "desc": null
                                        },
                                        "hbfx": {
                                            "desc": null
                                        },
                                        "fwbm": {
                                            "value": "3206841062080100104000100441",
                                            "desc": null
                                        },
                                        "scjzmj": {
                                            "desc": "实测建筑面积"
                                        }
                                    },
                                    "status": {
                                        "yy": 0,
                                        "sd": 0,
                                        "cf": 0,
                                        "zjgcdy": 0,
                                        "dyi": 0,
                                        "dj": 0,
                                        "ks": 0,
                                        "ys": 0,
                                        "ydya": 0,
                                        "dya": 0,
                                        "ycf": 0,
                                        "yg": 0
                                    },
                                    "links": [
                                        {
                                            "rel": "fwhsinfo",
                                            "href": "http://fwhsinfoUrl"
                                        }
                                    ]
                                },
                                {
                                    "info": {
                                        "fwHsIndex": {
                                            "value": "f94d5dbd-d877-4bb4-8952-efe7f6938fd7",
                                            "desc": null
                                        },
                                        "wlcs": {
                                            "value": 8,
                                            "desc": null
                                        },
                                        "dyh": {
                                            "value": "1",
                                            "desc": "单元号"
                                        },
                                        "zl": {
                                            "desc": "坐落"
                                        },
                                        "hbhs": {
                                            "desc": null
                                        },
                                        "fjh": {
                                            "value": "802",
                                            "desc": null
                                        },
                                        "bdcdyh": {
                                            "value": "320684106208GB00104F00010036",
                                            "desc": null
                                        },
                                        "dycs": {
                                            "value": "8",
                                            "desc": null
                                        },
                                        "hbfx": {
                                            "desc": null
                                        },
                                        "fwbm": {
                                            "value": "3206841062080100104000100360",
                                            "desc": null
                                        },
                                        "scjzmj": {
                                            "value": 94.25,
                                            "desc": "实测建筑面积"
                                        }
                                    },
                                    "status": {
                                        "yy": 0,
                                        "sd": 0,
                                        "cf": 0,
                                        "zjgcdy": 0,
                                        "dyi": 0,
                                        "dj": 0,
                                        "ks": 0,
                                        "ys": 0,
                                        "ydya": 0,
                                        "dya": 0,
                                        "ycf": 0,
                                        "yg": 0
                                    },
                                    "links": [
                                        {
                                            "rel": "fwhsinfo",
                                            "href": "http://fwhsinfoUrl"
                                        }
                                    ]
                                },
                                {
                                    "info": {
                                        "fwHsIndex": {
                                            "value": "8f71fb19-8f26-4666-8dcd-235f54978744",
                                            "desc": null
                                        },
                                        "wlcs": {
                                            "value": 8,
                                            "desc": null
                                        },
                                        "dyh": {
                                            "value": "1",
                                            "desc": "单元号"
                                        },
                                        "zl": {
                                            "value": "世纪花园1号37",
                                            "desc": "坐落"
                                        },
                                        "hbhs": {
                                            "desc": null
                                        },
                                        "fjh": {
                                            "value": "803",
                                            "desc": null
                                        },
                                        "bdcdyh": {
                                            "value": "320684106208GB00104F00010037",
                                            "desc": null
                                        },
                                        "dycs": {
                                            "value": "8",
                                            "desc": null
                                        },
                                        "hbfx": {
                                            "desc": null
                                        },
                                        "fwbm": {
                                            "value": "3206841062080100104000100378",
                                            "desc": null
                                        },
                                        "scjzmj": {
                                            "value": 77.0,
                                            "desc": "实测建筑面积"
                                        }
                                    },
                                    "status": {
                                        "yy": 0,
                                        "sd": 0,
                                        "cf": 0,
                                        "zjgcdy": 0,
                                        "dyi": 0,
                                        "dj": 0,
                                        "ks": 0,
                                        "ys": 0,
                                        "ydya": 0,
                                        "dya": 0,
                                        "ycf": 0,
                                        "yg": 0
                                    },
                                    "links": [
                                        {
                                            "rel": "fwhsinfo",
                                            "href": "http://fwhsinfoUrl"
                                        }
                                    ]
                                },
                                {
                                    "info": {
                                        "fwHsIndex": {
                                            "value": "66d25e36-37b4-4f16-bcbd-3ebbf4d9ab03",
                                            "desc": null
                                        },
                                        "wlcs": {
                                            "value": 8,
                                            "desc": null
                                        },
                                        "dyh": {
                                            "value": "1",
                                            "desc": "单元号"
                                        },
                                        "zl": {
                                            "value": "世纪花园1号38",
                                            "desc": "坐落"
                                        },
                                        "hbhs": {
                                            "desc": null
                                        },
                                        "fjh": {
                                            "value": "804",
                                            "desc": null
                                        },
                                        "bdcdyh": {
                                            "value": "320684106208GB00104F00010038",
                                            "desc": null
                                        },
                                        "dycs": {
                                            "value": "8",
                                            "desc": null
                                        },
                                        "hbfx": {
                                            "desc": null
                                        },
                                        "fwbm": {
                                            "value": "3206841062080100104000100386",
                                            "desc": null
                                        },
                                        "scjzmj": {
                                            "desc": "实测建筑面积"
                                        }
                                    },
                                    "status": {
                                        "yy": 0,
                                        "sd": 0,
                                        "cf": 0,
                                        "zjgcdy": 0,
                                        "dyi": 0,
                                        "dj": 0,
                                        "ks": 0,
                                        "ys": 0,
                                        "ydya": 0,
                                        "dya": 0,
                                        "ycf": 0,
                                        "yg": 0
                                    },
                                    "links": [
                                        {
                                            "rel": "fwhsinfo",
                                            "href": "http://fwhsinfoUrl"
                                        }
                                    ]
                                },
                                {
                                    "info": {
                                        "fwHsIndex": {
                                            "value": "a247b080-3851-408c-bf5e-6c611a2b664c",
                                            "desc": null
                                        },
                                        "wlcs": {
                                            "value": 8,
                                            "desc": null
                                        },
                                        "dyh": {
                                            "value": "1",
                                            "desc": "单元号"
                                        },
                                        "zl": {
                                            "value": "世纪花园1号41",
                                            "desc": "坐落"
                                        },
                                        "hbhs": {
                                            "desc": null
                                        },
                                        "fjh": {
                                            "value": "805",
                                            "desc": null
                                        },
                                        "bdcdyh": {
                                            "value": "320684106208GB00104F00010041",
                                            "desc": null
                                        },
                                        "dycs": {
                                            "value": "8",
                                            "desc": null
                                        },
                                        "hbfx": {
                                            "desc": null
                                        },
                                        "fwbm": {
                                            "value": "3206841062080100104000100417",
                                            "desc": null
                                        },
                                        "scjzmj": {
                                            "desc": "实测建筑面积"
                                        }
                                    },
                                    "status": {
                                        "yy": 0,
                                        "sd": 0,
                                        "cf": 0,
                                        "zjgcdy": 0,
                                        "dyi": 0,
                                        "dj": 0,
                                        "ks": 0,
                                        "ys": 0,
                                        "ydya": 0,
                                        "dya": 0,
                                        "ycf": 0,
                                        "yg": 0
                                    },
                                    "links": [
                                        {
                                            "rel": "fwhsinfo",
                                            "href": "http://fwhsinfoUrl"
                                        }
                                    ]
                                },
                                {
                                    "info": {
                                        "fwHsIndex": {
                                            "value": "f63443e0-3ac0-491b-938c-972c26bdb54a",
                                            "desc": null
                                        },
                                        "wlcs": {
                                            "value": 8,
                                            "desc": null
                                        },
                                        "dyh": {
                                            "value": "1",
                                            "desc": "单元号"
                                        },
                                        "zl": {
                                            "value": "世纪花园1号42",
                                            "desc": "坐落"
                                        },
                                        "hbhs": {
                                            "desc": null
                                        },
                                        "fjh": {
                                            "value": "806",
                                            "desc": null
                                        },
                                        "bdcdyh": {
                                            "value": "320684106208GB00104F00010042",
                                            "desc": null
                                        },
                                        "dycs": {
                                            "value": "8",
                                            "desc": null
                                        },
                                        "hbfx": {
                                            "desc": null
                                        },
                                        "fwbm": {
                                            "value": "3206841062080100104000100425",
                                            "desc": null
                                        },
                                        "scjzmj": {
                                            "desc": "实测建筑面积"
                                        }
                                    },
                                    "status": {
                                        "yy": 0,
                                        "sd": 0,
                                        "cf": 0,
                                        "zjgcdy": 0,
                                        "dyi": 0,
                                        "dj": 0,
                                        "ks": 0,
                                        "ys": 0,
                                        "ydya": 0,
                                        "dya": 0,
                                        "ycf": 0,
                                        "yg": 0
                                    },
                                    "links": [
                                        {
                                            "rel": "fwhsinfo",
                                            "href": "http://fwhsinfoUrl"
                                        }
                                    ]
                                },
                                {
                                    "info": {
                                        "fwHsIndex": {
                                            "value": "1b46f891-56ea-43f1-b147-36f0d7aaa839",
                                            "desc": null
                                        },
                                        "wlcs": {
                                            "value": 8,
                                            "desc": null
                                        },
                                        "dyh": {
                                            "value": "1",
                                            "desc": "单元号"
                                        },
                                        "zl": {
                                            "desc": "坐落"
                                        },
                                        "hbhs": {
                                            "desc": null
                                        },
                                        "fjh": {
                                            "value": "807",
                                            "desc": null
                                        },
                                        "bdcdyh": {
                                            "value": "320684106208GB00104F00010043",
                                            "desc": null
                                        },
                                        "dycs": {
                                            "value": "8",
                                            "desc": null
                                        },
                                        "hbfx": {
                                            "desc": null
                                        },
                                        "fwbm": {
                                            "value": "3206841062080100104000100433",
                                            "desc": null
                                        },
                                        "scjzmj": {
                                            "value": 94.25,
                                            "desc": "实测建筑面积"
                                        }
                                    },
                                    "status": {
                                        "yy": 0,
                                        "sd": 0,
                                        "cf": 0,
                                        "zjgcdy": 0,
                                        "dyi": 0,
                                        "dj": 0,
                                        "ks": 0,
                                        "ys": 0,
                                        "ydya": 0,
                                        "dya": 0,
                                        "ycf": 0,
                                        "yg": 0
                                    },
                                    "links": [
                                        {
                                            "rel": "fwhsinfo",
                                            "href": "http://fwhsinfoUrl"
                                        }
                                    ]
                                },
                                {
                                    "info": {
                                        "fwHsIndex": {
                                            "value": "fa5bb060-2f1c-4628-8929-4571c3cf841d",
                                            "desc": null
                                        },
                                        "wlcs": {
                                            "value": 8,
                                            "desc": null
                                        },
                                        "dyh": {
                                            "value": "1",
                                            "desc": "单元号"
                                        },
                                        "zl": {
                                            "desc": "坐落"
                                        },
                                        "hbhs": {
                                            "desc": null
                                        },
                                        "fjh": {
                                            "value": "808",
                                            "desc": null
                                        },
                                        "bdcdyh": {
                                            "value": "320684106208GB00104F00010040",
                                            "desc": null
                                        },
                                        "dycs": {
                                            "value": "8",
                                            "desc": null
                                        },
                                        "hbfx": {
                                            "desc": null
                                        },
                                        "fwbm": {
                                            "value": "3206841062080100104000100409",
                                            "desc": null
                                        },
                                        "scjzmj": {
                                            "value": 94.25,
                                            "desc": "实测建筑面积"
                                        }
                                    },
                                    "status": {
                                        "yy": 0,
                                        "sd": 0,
                                        "cf": 0,
                                        "zjgcdy": 0,
                                        "dyi": 0,
                                        "dj": 0,
                                        "ks": 0,
                                        "ys": 0,
                                        "ydya": 0,
                                        "dya": 0,
                                        "ycf": 0,
                                        "yg": 0
                                    },
                                    "links": [
                                        {
                                            "rel": "fwhsinfo",
                                            "href": "http://fwhsinfoUrl"
                                        }
                                    ]
                                },
                                {
                                    "info": {
                                        "fwHsIndex": {
                                            "value": "c35f0584-640b-4c4c-9e15-cb930e6a2513",
                                            "desc": null
                                        },
                                        "wlcs": {
                                            "value": 8,
                                            "desc": null
                                        },
                                        "dyh": {
                                            "value": "1",
                                            "desc": "单元号"
                                        },
                                        "zl": {
                                            "value": "世纪花园1号39",
                                            "desc": "坐落"
                                        },
                                        "hbhs": {
                                            "value": 1,
                                            "desc": null
                                        },
                                        "fjh": {
                                            "value": "809",
                                            "desc": null
                                        },
                                        "bdcdyh": {
                                            "value": "320684106208GB00104F00010039",
                                            "desc": null
                                        },
                                        "dycs": {
                                            "value": "8",
                                            "desc": null
                                        },
                                        "hbfx": {
                                            "value": "4",
                                            "desc": null
                                        },
                                        "fwbm": {
                                            "value": "3206841062080100104000100394",
                                            "desc": null
                                        },
                                        "scjzmj": {
                                            "desc": "实测建筑面积"
                                        }
                                    },
                                    "status": {
                                        "yy": 0,
                                        "sd": 0,
                                        "cf": 0,
                                        "zjgcdy": 0,
                                        "dyi": 0,
                                        "dj": 0,
                                        "ks": 0,
                                        "ys": 0,
                                        "ydya": 0,
                                        "dya": 0,
                                        "ycf": 0,
                                        "yg": 0
                                    },
                                    "links": [
                                        {
                                            "rel": "fwhsinfo",
                                            "href": "http://fwhsinfoUrl"
                                        }
                                    ]
                                }
                            ]
                        }
                    ],
                    "wlcs": "8",
                    "dycs": "8"
                },
                {
                    "dyFwList": [
                        {
                            "dyh": "2",
                            "maxHs": 1,
                            "fwhsResourceDTOList": [
                                {
                                    "info": {
                                        "fwHsIndex": {
                                            "value": "f167f9ffda77402c80d467f9ffd80009",
                                            "desc": null
                                        },
                                        "wlcs": {
                                            "value": 2,
                                            "desc": null
                                        },
                                        "hbhs": {
                                            "value": 1,
                                            "desc": null
                                        },
                                        "hbfx": {
                                            "value": '3',
                                            "desc": null
                                        },
                                        "dyh": {
                                            "value": "2-2",
                                            "desc": "单元号"
                                        },
                                        "zl": {
                                            "desc": "坐落"
                                        },
                                        "fjh": {
                                            "value": "201",
                                            "desc": null
                                        },
                                        "bdcdyh": {
                                            "value": "320684114304GB00008F00020050",
                                            "desc": null
                                        },
                                        "dycs": {
                                            "value": "2",
                                            "desc": null
                                        },
                                        "fwbm": {
                                            "value": "32068411430401000080020505",
                                            "desc": null
                                        },
                                        "scjzmj": {
                                            "desc": "实测建筑面积"
                                        }
                                    },
                                    "status": {
                                        "yy": 0,
                                        "sd": 0,
                                        "cf": 0,
                                        "zjgcdy": 0,
                                        "dyi": 0,
                                        "dj": 0,
                                        "ks": 0,
                                        "ys": 0,
                                        "ydya": 0,
                                        "dya": 0,
                                        "ycf": 0,
                                        "yg": 0
                                    },
                                    "links": [
                                        {
                                            "rel": "fwhsinfo",
                                            "href": "http://fwhsinfoUrl"
                                        }
                                    ]
                                }
                            ]
                        },
                        {
                            "dyh": "3",
                            "maxHs": 2,
                            "fwhsResourceDTOList": [
                                {
                                    "info": {
                                        "fwHsIndex": {
                                            "value": "f167f9ffda77402c80d467f9ffd80009",
                                            "desc": null
                                        },
                                        "wlcs": {
                                            "value": 2,
                                            "desc": null
                                        },
                                        "hbhs": {
                                            "desc": null
                                        },
                                        "hbfx": {
                                            "desc": null
                                        },
                                        "dyh": {
                                            "value": "3-2-1",
                                            "desc": "单元号"
                                        },
                                        "zl": {
                                            "desc": "坐落"
                                        },
                                        "fjh": {
                                            "value": "301",
                                            "desc": null
                                        },
                                        "bdcdyh": {
                                            "value": "320684114304GB00008F00020050",
                                            "desc": null
                                        },
                                        "dycs": {
                                            "value": "2",
                                            "desc": null
                                        },
                                        "fwbm": {
                                            "value": "32068411430401000080020505",
                                            "desc": null
                                        },
                                        "scjzmj": {
                                            "desc": "实测建筑面积"
                                        }
                                    },
                                    "status": {
                                        "yy": 0,
                                        "sd": 0,
                                        "cf": 0,
                                        "zjgcdy": 0,
                                        "dyi": 0,
                                        "dj": 0,
                                        "ks": 0,
                                        "ys": 0,
                                        "ydya": 0,
                                        "dya": 0,
                                        "ycf": 0,
                                        "yg": 0
                                    },
                                    "links": [
                                        {
                                            "rel": "fwhsinfo",
                                            "href": "http://fwhsinfoUrl"
                                        }
                                    ]
                                },
                                {
                                    "info": {
                                        "fwHsIndex": {
                                            "value": "f167f9ffda77402c80d467f9ffd80009",
                                            "desc": null
                                        },
                                        "wlcs": {
                                            "value": 2,
                                            "desc": null
                                        },
                                        "hbhs": {
                                            "desc": null
                                        },
                                        "hbfx": {
                                            "desc": null
                                        },
                                        "dyh": {
                                            "value": "3-2-2",
                                            "desc": "单元号"
                                        },
                                        "zl": {
                                            "desc": "坐落"
                                        },
                                        "fjh": {
                                            "value": "302",
                                            "desc": null
                                        },
                                        "bdcdyh": {
                                            "value": "320684114304GB00008F00020050",
                                            "desc": null
                                        },
                                        "dycs": {
                                            "value": "2",
                                            "desc": null
                                        },
                                        "fwbm": {
                                            "value": "32068411430401000080020505",
                                            "desc": null
                                        },
                                        "scjzmj": {
                                            "desc": "实测建筑面积"
                                        }
                                    },
                                    "status": {
                                        "yy": 0,
                                        "sd": 0,
                                        "cf": 0,
                                        "zjgcdy": 0,
                                        "dyi": 0,
                                        "dj": 0,
                                        "ks": 0,
                                        "ys": 0,
                                        "ydya": 0,
                                        "dya": 0,
                                        "ycf": 0,
                                        "yg": 0
                                    },
                                    "links": [
                                        {
                                            "rel": "fwhsinfo",
                                            "href": "http://fwhsinfoUrl"
                                        }
                                    ]
                                }
                            ]
                        },
                        {
                            "dyh": "1",
                            "maxHs": 9,
                            "fwhsResourceDTOList": [
                                {
                                    "info": {
                                        "fwHsIndex": {
                                            "value": "e0cef3cb-2f04-4a16-aed2-a8229ae5d607",
                                            "desc": null
                                        },
                                        "wlcs": {
                                            "value": 7,
                                            "desc": null
                                        },
                                        "dyh": {
                                            "value": "1",
                                            "desc": "单元号"
                                        },
                                        "zl": {
                                            "value": "世纪花园1号28",
                                            "desc": "坐落"
                                        },
                                        "hbhs": {
                                            "desc": null
                                        },
                                        "fjh": {
                                            "value": "701",
                                            "desc": null
                                        },
                                        "bdcdyh": {
                                            "value": "320684106208GB00104F00010028",
                                            "desc": null
                                        },
                                        "dycs": {
                                            "value": "7",
                                            "desc": null
                                        },
                                        "hbfx": {
                                            "desc": null
                                        },
                                        "fwbm": {
                                            "value": "3206841062080100104000100280",
                                            "desc": null
                                        },
                                        "scjzmj": {
                                            "desc": "实测建筑面积"
                                        }
                                    },
                                    "status": {
                                        "yy": 0,
                                        "sd": 0,
                                        "cf": 0,
                                        "zjgcdy": 0,
                                        "dyi": 0,
                                        "dj": 0,
                                        "ks": 0,
                                        "ys": 0,
                                        "ydya": 0,
                                        "dya": 0,
                                        "ycf": 0,
                                        "yg": 0
                                    },
                                    "links": [
                                        {
                                            "rel": "fwhsinfo",
                                            "href": "http://fwhsinfoUrl"
                                        }
                                    ]
                                },
                                {
                                    "info": {
                                        "fwHsIndex": {
                                            "value": "4e87de17-2990-497b-b0e8-7214baf4eca6",
                                            "desc": null
                                        },
                                        "wlcs": {
                                            "value": 7,
                                            "desc": null
                                        },
                                        "dyh": {
                                            "value": "1",
                                            "desc": "单元号"
                                        },
                                        "zl": {
                                            "value": "世纪花园1号29",
                                            "desc": "坐落"
                                        },
                                        "hbhs": {
                                            "desc": null
                                        },
                                        "fjh": {
                                            "value": "702",
                                            "desc": null
                                        },
                                        "bdcdyh": {
                                            "value": "320684106208GB00104F00010029",
                                            "desc": null
                                        },
                                        "dycs": {
                                            "value": "7",
                                            "desc": null
                                        },
                                        "hbfx": {
                                            "desc": null
                                        },
                                        "fwbm": {
                                            "value": "3206841062080100104000100298",
                                            "desc": null
                                        },
                                        "scjzmj": {
                                            "desc": "实测建筑面积"
                                        }
                                    },
                                    "status": {
                                        "yy": 0,
                                        "sd": 0,
                                        "cf": 0,
                                        "zjgcdy": 0,
                                        "dyi": 0,
                                        "dj": 0,
                                        "ks": 0,
                                        "ys": 0,
                                        "ydya": 0,
                                        "dya": 0,
                                        "ycf": 0,
                                        "yg": 0
                                    },
                                    "links": [
                                        {
                                            "rel": "fwhsinfo",
                                            "href": "http://fwhsinfoUrl"
                                        }
                                    ]
                                },
                                {
                                    "info": {
                                        "fwHsIndex": {
                                            "value": "5a144a76-ae06-417b-ae07-a75f632eb719",
                                            "desc": null
                                        },
                                        "wlcs": {
                                            "value": 7,
                                            "desc": null
                                        },
                                        "dyh": {
                                            "value": "1",
                                            "desc": "单元号"
                                        },
                                        "zl": {
                                            "value": "世纪花园1号30",
                                            "desc": "坐落"
                                        },
                                        "hbhs": {
                                            "desc": null
                                        },
                                        "fjh": {
                                            "value": "703",
                                            "desc": null
                                        },
                                        "bdcdyh": {
                                            "value": "320684106208GB00104F00010030",
                                            "desc": null
                                        },
                                        "dycs": {
                                            "value": "7",
                                            "desc": null
                                        },
                                        "hbfx": {
                                            "desc": null
                                        },
                                        "fwbm": {
                                            "value": "3206841062080100104000100301",
                                            "desc": null
                                        },
                                        "scjzmj": {
                                            "desc": "实测建筑面积"
                                        }
                                    },
                                    "status": {
                                        "yy": 0,
                                        "sd": 0,
                                        "cf": 0,
                                        "zjgcdy": 0,
                                        "dyi": 0,
                                        "dj": 0,
                                        "ks": 0,
                                        "ys": 0,
                                        "ydya": 0,
                                        "dya": 0,
                                        "ycf": 0,
                                        "yg": 0
                                    },
                                    "links": [
                                        {
                                            "rel": "fwhsinfo",
                                            "href": "http://fwhsinfoUrl"
                                        }
                                    ]
                                },
                                {
                                    "info": {
                                        "fwHsIndex": {
                                            "value": "a2de6a93-0182-4a16-9a98-946a9ffb43af",
                                            "desc": null
                                        },
                                        "wlcs": {
                                            "value": 7,
                                            "desc": null
                                        },
                                        "dyh": {
                                            "value": "1",
                                            "desc": "单元号"
                                        },
                                        "zl": {
                                            "value": "世纪花园1号31",
                                            "desc": "坐落"
                                        },
                                        "hbhs": {
                                            "desc": null
                                        },
                                        "fjh": {
                                            "value": "704",
                                            "desc": null
                                        },
                                        "bdcdyh": {
                                            "value": "320684106208GB00104F00010031",
                                            "desc": null
                                        },
                                        "dycs": {
                                            "value": "7",
                                            "desc": null
                                        },
                                        "hbfx": {
                                            "desc": null
                                        },
                                        "fwbm": {
                                            "value": "3206841062080100104000100319",
                                            "desc": null
                                        },
                                        "scjzmj": {
                                            "desc": "实测建筑面积"
                                        }
                                    },
                                    "status": {
                                        "yy": 0,
                                        "sd": 0,
                                        "cf": 0,
                                        "zjgcdy": 0,
                                        "dyi": 0,
                                        "dj": 0,
                                        "ks": 0,
                                        "ys": 0,
                                        "ydya": 0,
                                        "dya": 0,
                                        "ycf": 0,
                                        "yg": 0
                                    },
                                    "links": [
                                        {
                                            "rel": "fwhsinfo",
                                            "href": "http://fwhsinfoUrl"
                                        }
                                    ]
                                },
                                {
                                    "info": {
                                        "fwHsIndex": {
                                            "value": "d8022b69-66f1-4abc-8052-c3feacfa6c3e",
                                            "desc": null
                                        },
                                        "wlcs": {
                                            "value": 7,
                                            "desc": null
                                        },
                                        "dyh": {
                                            "value": "1",
                                            "desc": "单元号"
                                        },
                                        "zl": {
                                            "value": "世纪花园1号34",
                                            "desc": "坐落"
                                        },
                                        "hbhs": {
                                            "desc": null
                                        },
                                        "fjh": {
                                            "value": "705",
                                            "desc": null
                                        },
                                        "bdcdyh": {
                                            "value": "320684106208GB00104F00010034",
                                            "desc": null
                                        },
                                        "dycs": {
                                            "value": "7",
                                            "desc": null
                                        },
                                        "hbfx": {
                                            "desc": null
                                        },
                                        "fwbm": {
                                            "value": "3206841062080100104000100343",
                                            "desc": null
                                        },
                                        "scjzmj": {
                                            "desc": "实测建筑面积"
                                        }
                                    },
                                    "status": {
                                        "yy": 0,
                                        "sd": 0,
                                        "cf": 0,
                                        "zjgcdy": 0,
                                        "dyi": 0,
                                        "dj": 0,
                                        "ks": 0,
                                        "ys": 0,
                                        "ydya": 0,
                                        "dya": 0,
                                        "ycf": 0,
                                        "yg": 0
                                    },
                                    "links": [
                                        {
                                            "rel": "fwhsinfo",
                                            "href": "http://fwhsinfoUrl"
                                        }
                                    ]
                                },
                                {
                                    "info": {
                                        "fwHsIndex": {
                                            "value": "66d3dfbc-0ddf-47e7-922d-12ac9d4ffa11",
                                            "desc": null
                                        },
                                        "wlcs": {
                                            "value": 7,
                                            "desc": null
                                        },
                                        "dyh": {
                                            "value": "1",
                                            "desc": "单元号"
                                        },
                                        "zl": {
                                            "desc": "坐落"
                                        },
                                        "hbhs": {
                                            "desc": null
                                        },
                                        "fjh": {
                                            "value": "706",
                                            "desc": null
                                        },
                                        "bdcdyh": {
                                            "value": "320684106208GB00104F00010035",
                                            "desc": null
                                        },
                                        "dycs": {
                                            "value": "7",
                                            "desc": null
                                        },
                                        "hbfx": {
                                            "desc": null
                                        },
                                        "fwbm": {
                                            "value": "3206841062080100104000100351",
                                            "desc": null
                                        },
                                        "scjzmj": {
                                            "value": 94.25,
                                            "desc": "实测建筑面积"
                                        }
                                    },
                                    "status": {
                                        "yy": 0,
                                        "sd": 0,
                                        "cf": 0,
                                        "zjgcdy": 0,
                                        "dyi": 0,
                                        "dj": 0,
                                        "ks": 0,
                                        "ys": 0,
                                        "ydya": 0,
                                        "dya": 0,
                                        "ycf": 0,
                                        "yg": 0
                                    },
                                    "links": [
                                        {
                                            "rel": "fwhsinfo",
                                            "href": "http://fwhsinfoUrl"
                                        }
                                    ]
                                },
                                {
                                    "info": {
                                        "fwHsIndex": {
                                            "value": "4337fcac-d110-4df4-b9d8-f156d641181c",
                                            "desc": null
                                        },
                                        "wlcs": {
                                            "value": 7,
                                            "desc": null
                                        },
                                        "dyh": {
                                            "value": "1",
                                            "desc": "单元号"
                                        },
                                        "zl": {
                                            "value": "世纪花园1号33",
                                            "desc": "坐落"
                                        },
                                        "hbhs": {
                                            "desc": null
                                        },
                                        "fjh": {
                                            "value": "707",
                                            "desc": null
                                        },
                                        "bdcdyh": {
                                            "value": "320684106208GB00104F00010033",
                                            "desc": null
                                        },
                                        "dycs": {
                                            "value": "7",
                                            "desc": null
                                        },
                                        "hbfx": {
                                            "desc": null
                                        },
                                        "fwbm": {
                                            "value": "3206841062080100104000100335",
                                            "desc": null
                                        },
                                        "scjzmj": {
                                            "desc": "实测建筑面积"
                                        }
                                    },
                                    "status": {
                                        "yy": 0,
                                        "sd": 0,
                                        "cf": 0,
                                        "zjgcdy": 0,
                                        "dyi": 0,
                                        "dj": 0,
                                        "ks": 0,
                                        "ys": 0,
                                        "ydya": 0,
                                        "dya": 0,
                                        "ycf": 0,
                                        "yg": 0
                                    },
                                    "links": [
                                        {
                                            "rel": "fwhsinfo",
                                            "href": "http://fwhsinfoUrl"
                                        }
                                    ]
                                },
                                {
                                    "info": {
                                        "fwHsIndex": {
                                            "value": "fc2ee369-f097-4c4c-aec4-5c59b030724b",
                                            "desc": null
                                        },
                                        "wlcs": {
                                            "value": 7,
                                            "desc": null
                                        },
                                        "dyh": {
                                            "value": "1",
                                            "desc": "单元号"
                                        },
                                        "zl": {
                                            "desc": "坐落"
                                        },
                                        "hbhs": {
                                            "desc": null
                                        },
                                        "fjh": {
                                            "value": "708",
                                            "desc": null
                                        },
                                        "bdcdyh": {
                                            "value": "320684106208GB00104F00010032",
                                            "desc": null
                                        },
                                        "dycs": {
                                            "value": "7",
                                            "desc": null
                                        },
                                        "hbfx": {
                                            "desc": null
                                        },
                                        "fwbm": {
                                            "value": "3206841062080100104000100327",
                                            "desc": null
                                        },
                                        "scjzmj": {
                                            "value": 94.25,
                                            "desc": "实测建筑面积"
                                        }
                                    },
                                    "status": {
                                        "yy": 0,
                                        "sd": 0,
                                        "cf": 0,
                                        "zjgcdy": 0,
                                        "dyi": 0,
                                        "dj": 0,
                                        "ks": 0,
                                        "ys": 0,
                                        "ydya": 0,
                                        "dya": 0,
                                        "ycf": 0,
                                        "yg": 0
                                    },
                                    "links": [
                                        {
                                            "rel": "fwhsinfo",
                                            "href": "http://fwhsinfoUrl"
                                        }
                                    ]
                                }
                            ]
                        }
                    ],
                    "wlcs": "7",
                    "dycs": "7"
                },
                {
                    "dyFwList": [
                        {
                            "dyh": "2",
                            "maxHs": 1,
                            "fwhsResourceDTOList": [
                                {
                                    "info": {
                                        "fwHsIndex": {
                                            "value": "f167f9ffda77402c80d467f9ffd80009",
                                            "desc": null
                                        },
                                        "wlcs": {
                                            "value": 2,
                                            "desc": null
                                        },
                                        "hbhs": {
                                            "desc": null
                                        },
                                        "hbfx": {
                                            "desc": null
                                        },
                                        "dyh": {
                                            "value": "2-3",
                                            "desc": "单元号"
                                        },
                                        "zl": {
                                            "desc": "坐落"
                                        },
                                        "fjh": {
                                            "value": "201",
                                            "desc": null
                                        },
                                        "bdcdyh": {
                                            "value": "320684114304GB00008F00020050",
                                            "desc": null
                                        },
                                        "dycs": {
                                            "value": "2",
                                            "desc": null
                                        },
                                        "fwbm": {
                                            "value": "32068411430401000080020505",
                                            "desc": null
                                        },
                                        "scjzmj": {
                                            "desc": "实测建筑面积"
                                        }
                                    },
                                    "status": {
                                        "yy": 0,
                                        "sd": 0,
                                        "cf": 0,
                                        "zjgcdy": 0,
                                        "dyi": 0,
                                        "dj": 0,
                                        "ks": 0,
                                        "ys": 0,
                                        "ydya": 0,
                                        "dya": 0,
                                        "ycf": 0,
                                        "yg": 0
                                    },
                                    "links": [
                                        {
                                            "rel": "fwhsinfo",
                                            "href": "http://fwhsinfoUrl"
                                        }
                                    ]
                                }
                            ]
                        },
                        {
                            "dyh": "3",
                            "maxHs": 2,
                            "fwhsResourceDTOList": [
                                {
                                    "info": {
                                        "fwHsIndex": {
                                            "value": "f167f9ffda77402c80d467f9ffd80009",
                                            "desc": null
                                        },
                                        "wlcs": {
                                            "value": 2,
                                            "desc": null
                                        },
                                        "hbhs": {
                                            "desc": null
                                        },
                                        "hbfx": {
                                            "desc": null
                                        },
                                        "dyh": {
                                            "value": "3-3-1",
                                            "desc": "单元号"
                                        },
                                        "zl": {
                                            "desc": "坐落"
                                        },
                                        "fjh": {
                                            "value": "301",
                                            "desc": null
                                        },
                                        "bdcdyh": {
                                            "value": "320684114304GB00008F00020050",
                                            "desc": null
                                        },
                                        "dycs": {
                                            "value": "2",
                                            "desc": null
                                        },
                                        "fwbm": {
                                            "value": "32068411430401000080020505",
                                            "desc": null
                                        },
                                        "scjzmj": {
                                            "desc": "实测建筑面积"
                                        }
                                    },
                                    "status": {
                                        "yy": 0,
                                        "sd": 0,
                                        "cf": 0,
                                        "zjgcdy": 0,
                                        "dyi": 0,
                                        "dj": 0,
                                        "ks": 0,
                                        "ys": 0,
                                        "ydya": 0,
                                        "dya": 0,
                                        "ycf": 0,
                                        "yg": 0
                                    },
                                    "links": [
                                        {
                                            "rel": "fwhsinfo",
                                            "href": "http://fwhsinfoUrl"
                                        }
                                    ]
                                },
                                {
                                    "info": {
                                        "fwHsIndex": {
                                            "value": "f167f9ffda77402c80d467f9ffd80009",
                                            "desc": null
                                        },
                                        "wlcs": {
                                            "value": 2,
                                            "desc": null
                                        },
                                        "hbhs": {
                                            "desc": null
                                        },
                                        "hbfx": {
                                            "desc": null
                                        },
                                        "dyh": {
                                            "value": "3-3-2",
                                            "desc": "单元号"
                                        },
                                        "zl": {
                                            "desc": "坐落"
                                        },
                                        "fjh": {
                                            "value": "302",
                                            "desc": null
                                        },
                                        "bdcdyh": {
                                            "value": "320684114304GB00008F00020050",
                                            "desc": null
                                        },
                                        "dycs": {
                                            "value": "2",
                                            "desc": null
                                        },
                                        "fwbm": {
                                            "value": "32068411430401000080020505",
                                            "desc": null
                                        },
                                        "scjzmj": {
                                            "desc": "实测建筑面积"
                                        }
                                    },
                                    "status": {
                                        "yy": 0,
                                        "sd": 0,
                                        "cf": 0,
                                        "zjgcdy": 0,
                                        "dyi": 0,
                                        "dj": 0,
                                        "ks": 0,
                                        "ys": 0,
                                        "ydya": 0,
                                        "dya": 0,
                                        "ycf": 0,
                                        "yg": 0
                                    },
                                    "links": [
                                        {
                                            "rel": "fwhsinfo",
                                            "href": "http://fwhsinfoUrl"
                                        }
                                    ]
                                }
                            ]
                        },
                        {
                            "dyh": "1",
                            "maxHs": 9,
                            "fwhsResourceDTOList": [
                                {
                                    "info": {
                                        "fwHsIndex": {
                                            "value": "15a52d78-32ee-4585-b33f-7ad09b369a20",
                                            "desc": null
                                        },
                                        "wlcs": {
                                            "value": 6,
                                            "desc": null
                                        },
                                        "dyh": {
                                            "value": "1",
                                            "desc": "单元号"
                                        },
                                        "zl": {
                                            "value": "世纪花园1号19",
                                            "desc": "坐落"
                                        },
                                        "hbhs": {
                                            "desc": null
                                        },
                                        "fjh": {
                                            "value": "601",
                                            "desc": null
                                        },
                                        "bdcdyh": {
                                            "value": "320684106208GB00104F00010019",
                                            "desc": null
                                        },
                                        "dycs": {
                                            "value": "6",
                                            "desc": null
                                        },
                                        "hbfx": {
                                            "desc": null
                                        },
                                        "fwbm": {
                                            "value": "3206841062080100104000100191",
                                            "desc": null
                                        },
                                        "scjzmj": {
                                            "value": 0.0,
                                            "desc": "实测建筑面积"
                                        }
                                    },
                                    "status": {
                                        "yy": 0,
                                        "sd": 0,
                                        "cf": 0,
                                        "zjgcdy": 0,
                                        "dyi": 0,
                                        "dj": 0,
                                        "ks": 0,
                                        "ys": 0,
                                        "ydya": 0,
                                        "dya": 0,
                                        "ycf": 0,
                                        "yg": 0
                                    },
                                    "links": [
                                        {
                                            "rel": "fwhsinfo",
                                            "href": "http://fwhsinfoUrl"
                                        }
                                    ]
                                },
                                {
                                    "info": {
                                        "fwHsIndex": {
                                            "value": "538fce04-3710-47fa-8f52-0ea94e8636e2",
                                            "desc": null
                                        },
                                        "wlcs": {
                                            "value": 6,
                                            "desc": null
                                        },
                                        "dyh": {
                                            "value": "1",
                                            "desc": "单元号"
                                        },
                                        "zl": {
                                            "value": "世纪花园1号21",
                                            "desc": "坐落"
                                        },
                                        "hbhs": {
                                            "desc": null
                                        },
                                        "fjh": {
                                            "value": "602",
                                            "desc": null
                                        },
                                        "bdcdyh": {
                                            "value": "320684106208GB00104F00010021",
                                            "desc": null
                                        },
                                        "dycs": {
                                            "value": "6",
                                            "desc": null
                                        },
                                        "hbfx": {
                                            "desc": null
                                        },
                                        "fwbm": {
                                            "value": "3206841062080100104000100214",
                                            "desc": null
                                        },
                                        "scjzmj": {
                                            "desc": "实测建筑面积"
                                        }
                                    },
                                    "status": {
                                        "yy": 0,
                                        "sd": 0,
                                        "cf": 0,
                                        "zjgcdy": 0,
                                        "dyi": 0,
                                        "dj": 0,
                                        "ks": 0,
                                        "ys": 0,
                                        "ydya": 0,
                                        "dya": 0,
                                        "ycf": 0,
                                        "yg": 0
                                    },
                                    "links": [
                                        {
                                            "rel": "fwhsinfo",
                                            "href": "http://fwhsinfoUrl"
                                        }
                                    ]
                                },
                                {
                                    "info": {
                                        "fwHsIndex": {
                                            "value": "149dc04e-e82a-4c2c-98e7-9a6cd77b1d68",
                                            "desc": null
                                        },
                                        "wlcs": {
                                            "value": 6,
                                            "desc": null
                                        },
                                        "dyh": {
                                            "value": "1",
                                            "desc": "单元号"
                                        },
                                        "zl": {
                                            "desc": "坐落"
                                        },
                                        "hbhs": {
                                            "desc": null
                                        },
                                        "fjh": {
                                            "value": "603",
                                            "desc": null
                                        },
                                        "bdcdyh": {
                                            "value": "320684106208GB00104F00010022",
                                            "desc": null
                                        },
                                        "dycs": {
                                            "value": "6",
                                            "desc": null
                                        },
                                        "hbfx": {
                                            "desc": null
                                        },
                                        "fwbm": {
                                            "value": "3206841062080100104000100221",
                                            "desc": null
                                        },
                                        "scjzmj": {
                                            "value": 0.0,
                                            "desc": "实测建筑面积"
                                        }
                                    },
                                    "status": {
                                        "yy": 0,
                                        "sd": 0,
                                        "cf": 0,
                                        "zjgcdy": 0,
                                        "dyi": 0,
                                        "dj": 0,
                                        "ks": 0,
                                        "ys": 0,
                                        "ydya": 0,
                                        "dya": 0,
                                        "ycf": 0,
                                        "yg": 0
                                    },
                                    "links": [
                                        {
                                            "rel": "fwhsinfo",
                                            "href": "http://fwhsinfoUrl"
                                        }
                                    ]
                                },
                                {
                                    "info": {
                                        "fwHsIndex": {
                                            "value": "3705231c-9610-42d3-b1f3-454e7256429b",
                                            "desc": null
                                        },
                                        "wlcs": {
                                            "value": 6,
                                            "desc": null
                                        },
                                        "dyh": {
                                            "value": "1",
                                            "desc": "单元号"
                                        },
                                        "zl": {
                                            "desc": "坐落"
                                        },
                                        "hbhs": {
                                            "desc": null
                                        },
                                        "fjh": {
                                            "value": "604",
                                            "desc": null
                                        },
                                        "bdcdyh": {
                                            "value": "320684106208GB00104F00010023",
                                            "desc": null
                                        },
                                        "dycs": {
                                            "value": "6",
                                            "desc": null
                                        },
                                        "hbfx": {
                                            "desc": null
                                        },
                                        "fwbm": {
                                            "value": "3206841062080100104000100239",
                                            "desc": null
                                        },
                                        "scjzmj": {
                                            "value": 0.0,
                                            "desc": "实测建筑面积"
                                        }
                                    },
                                    "status": {
                                        "yy": 0,
                                        "sd": 0,
                                        "cf": 0,
                                        "zjgcdy": 0,
                                        "dyi": 0,
                                        "dj": 0,
                                        "ks": 0,
                                        "ys": 0,
                                        "ydya": 0,
                                        "dya": 0,
                                        "ycf": 0,
                                        "yg": 0
                                    },
                                    "links": [
                                        {
                                            "rel": "fwhsinfo",
                                            "href": "http://fwhsinfoUrl"
                                        }
                                    ]
                                },
                                {
                                    "info": {
                                        "fwHsIndex": {
                                            "value": "e697ad64-eddf-414f-a88c-0f187b4c702a",
                                            "desc": null
                                        },
                                        "wlcs": {
                                            "value": 6,
                                            "desc": null
                                        },
                                        "dyh": {
                                            "value": "1",
                                            "desc": "单元号"
                                        },
                                        "zl": {
                                            "desc": "坐落"
                                        },
                                        "hbhs": {
                                            "desc": null
                                        },
                                        "fjh": {
                                            "value": "605",
                                            "desc": null
                                        },
                                        "bdcdyh": {
                                            "value": "320684106208GB00104F00010024",
                                            "desc": null
                                        },
                                        "dycs": {
                                            "value": "6",
                                            "desc": null
                                        },
                                        "hbfx": {
                                            "desc": null
                                        },
                                        "fwbm": {
                                            "value": "3206841062080100104000100247",
                                            "desc": null
                                        },
                                        "scjzmj": {
                                            "value": 0.0,
                                            "desc": "实测建筑面积"
                                        }
                                    },
                                    "status": {
                                        "yy": 0,
                                        "sd": 0,
                                        "cf": 0,
                                        "zjgcdy": 0,
                                        "dyi": 0,
                                        "dj": 0,
                                        "ks": 0,
                                        "ys": 0,
                                        "ydya": 0,
                                        "dya": 0,
                                        "ycf": 0,
                                        "yg": 0
                                    },
                                    "links": [
                                        {
                                            "rel": "fwhsinfo",
                                            "href": "http://fwhsinfoUrl"
                                        }
                                    ]
                                },
                                {
                                    "info": {
                                        "fwHsIndex": {
                                            "value": "14654ee6-0ef5-42d4-b45f-dc5e23feab6b",
                                            "desc": null
                                        },
                                        "wlcs": {
                                            "value": 6,
                                            "desc": null
                                        },
                                        "dyh": {
                                            "value": "1",
                                            "desc": "单元号"
                                        },
                                        "zl": {
                                            "value": "世纪花园1号25",
                                            "desc": "坐落"
                                        },
                                        "hbhs": {
                                            "desc": null
                                        },
                                        "fjh": {
                                            "value": "606",
                                            "desc": null
                                        },
                                        "bdcdyh": {
                                            "value": "320684106208GB00104F00010025",
                                            "desc": null
                                        },
                                        "dycs": {
                                            "value": "6",
                                            "desc": null
                                        },
                                        "hbfx": {
                                            "desc": null
                                        },
                                        "fwbm": {
                                            "value": "3206841062080100104000100255",
                                            "desc": null
                                        },
                                        "scjzmj": {
                                            "desc": "实测建筑面积"
                                        }
                                    },
                                    "status": {
                                        "yy": 0,
                                        "sd": 0,
                                        "cf": 0,
                                        "zjgcdy": 0,
                                        "dyi": 0,
                                        "dj": 0,
                                        "ks": 0,
                                        "ys": 0,
                                        "ydya": 0,
                                        "dya": 0,
                                        "ycf": 0,
                                        "yg": 0
                                    },
                                    "links": [
                                        {
                                            "rel": "fwhsinfo",
                                            "href": "http://fwhsinfoUrl"
                                        }
                                    ]
                                },
                                {
                                    "info": {
                                        "fwHsIndex": {
                                            "value": "8dcd80a7-e7a1-47be-aa29-ac3776673b3d",
                                            "desc": null
                                        },
                                        "wlcs": {
                                            "value": 6,
                                            "desc": null
                                        },
                                        "dyh": {
                                            "value": "1",
                                            "desc": "单元号"
                                        },
                                        "zl": {
                                            "value": "世纪花园1号27",
                                            "desc": "坐落"
                                        },
                                        "hbhs": {
                                            "desc": null
                                        },
                                        "fjh": {
                                            "value": "607",
                                            "desc": null
                                        },
                                        "bdcdyh": {
                                            "value": "320684106208GB00104F00010027",
                                            "desc": null
                                        },
                                        "dycs": {
                                            "value": "6",
                                            "desc": null
                                        },
                                        "hbfx": {
                                            "desc": null
                                        },
                                        "fwbm": {
                                            "value": "3206841062080100104000100271",
                                            "desc": null
                                        },
                                        "scjzmj": {
                                            "desc": "实测建筑面积"
                                        }
                                    },
                                    "status": {
                                        "yy": 0,
                                        "sd": 0,
                                        "cf": 0,
                                        "zjgcdy": 0,
                                        "dyi": 0,
                                        "dj": 0,
                                        "ks": 0,
                                        "ys": 0,
                                        "ydya": 0,
                                        "dya": 0,
                                        "ycf": 0,
                                        "yg": 0
                                    },
                                    "links": [
                                        {
                                            "rel": "fwhsinfo",
                                            "href": "http://fwhsinfoUrl"
                                        }
                                    ]
                                },
                                {
                                    "info": {
                                        "fwHsIndex": {
                                            "value": "f5ef87fe-bb8e-4a20-be97-4461ef825d0c",
                                            "desc": null
                                        },
                                        "wlcs": {
                                            "value": 6,
                                            "desc": null
                                        },
                                        "dyh": {
                                            "value": "1",
                                            "desc": "单元号"
                                        },
                                        "zl": {
                                            "value": "世纪花园1号20",
                                            "desc": "坐落"
                                        },
                                        "hbhs": {
                                            "desc": null
                                        },
                                        "fjh": {
                                            "value": "608",
                                            "desc": null
                                        },
                                        "bdcdyh": {
                                            "value": "320684106208GB00104F00010020",
                                            "desc": null
                                        },
                                        "dycs": {
                                            "value": "6",
                                            "desc": null
                                        },
                                        "hbfx": {
                                            "desc": null
                                        },
                                        "fwbm": {
                                            "value": "3206841062080100104000100206",
                                            "desc": null
                                        },
                                        "scjzmj": {
                                            "desc": "实测建筑面积"
                                        }
                                    },
                                    "status": {
                                        "yy": 0,
                                        "sd": 0,
                                        "cf": 0,
                                        "zjgcdy": 0,
                                        "dyi": 0,
                                        "dj": 0,
                                        "ks": 0,
                                        "ys": 0,
                                        "ydya": 0,
                                        "dya": 0,
                                        "ycf": 0,
                                        "yg": 0
                                    },
                                    "links": [
                                        {
                                            "rel": "fwhsinfo",
                                            "href": "http://fwhsinfoUrl"
                                        }
                                    ]
                                },
                                {
                                    "info": {
                                        "fwHsIndex": {
                                            "value": "43fc3519-dcdf-46ec-9c85-1599dabeabee",
                                            "desc": null
                                        },
                                        "wlcs": {
                                            "value": 6,
                                            "desc": null
                                        },
                                        "dyh": {
                                            "value": "1",
                                            "desc": "单元号"
                                        },
                                        "zl": {
                                            "value": "世纪花园1号26",
                                            "desc": "坐落"
                                        },
                                        "hbhs": {
                                            "desc": null
                                        },
                                        "fjh": {
                                            "value": "609",
                                            "desc": null
                                        },
                                        "bdcdyh": {
                                            "value": "320684106208GB00104F00010026",
                                            "desc": null
                                        },
                                        "dycs": {
                                            "value": "6",
                                            "desc": null
                                        },
                                        "hbfx": {
                                            "desc": null
                                        },
                                        "fwbm": {
                                            "value": "3206841062080100104000100263",
                                            "desc": null
                                        },
                                        "scjzmj": {
                                            "desc": "实测建筑面积"
                                        }
                                    },
                                    "status": {
                                        "yy": 0,
                                        "sd": 0,
                                        "cf": 0,
                                        "zjgcdy": 0,
                                        "dyi": 0,
                                        "dj": 0,
                                        "ks": 0,
                                        "ys": 0,
                                        "ydya": 0,
                                        "dya": 0,
                                        "ycf": 0,
                                        "yg": 0
                                    },
                                    "links": [
                                        {
                                            "rel": "fwhsinfo",
                                            "href": "http://fwhsinfoUrl"
                                        }
                                    ]
                                }
                            ]
                        }
                    ],
                    "wlcs": "6",
                    "dycs": "6"
                },
                {
                    "dyFwList": [
                        {
                            "dyh": "2",
                            "maxHs": 1,
                            "fwhsResourceDTOList": [
                                {
                                    "info": {
                                        "fwHsIndex": {
                                            "value": "f167f9ffda77402c80d467f9ffd80009",
                                            "desc": null
                                        },
                                        "wlcs": {
                                            "value": 2,
                                            "desc": null
                                        },
                                        "hbhs": {
                                            "desc": null
                                        },
                                        "hbfx": {
                                            "desc": null
                                        },
                                        "dyh": {
                                            "value": "2-4",
                                            "desc": "单元号"
                                        },
                                        "zl": {
                                            "desc": "坐落"
                                        },
                                        "fjh": {
                                            "value": "201",
                                            "desc": null
                                        },
                                        "bdcdyh": {
                                            "value": "320684114304GB00008F00020050",
                                            "desc": null
                                        },
                                        "dycs": {
                                            "value": "2",
                                            "desc": null
                                        },
                                        "fwbm": {
                                            "value": "32068411430401000080020505",
                                            "desc": null
                                        },
                                        "scjzmj": {
                                            "desc": "实测建筑面积"
                                        }
                                    },
                                    "status": {
                                        "yy": 0,
                                        "sd": 0,
                                        "cf": 0,
                                        "zjgcdy": 0,
                                        "dyi": 0,
                                        "dj": 0,
                                        "ks": 0,
                                        "ys": 0,
                                        "ydya": 0,
                                        "dya": 0,
                                        "ycf": 0,
                                        "yg": 0
                                    },
                                    "links": [
                                        {
                                            "rel": "fwhsinfo",
                                            "href": "http://fwhsinfoUrl"
                                        }
                                    ]
                                }
                            ]
                        },
                        {
                            "dyh": "3",
                            "maxHs": 2,
                            "fwhsResourceDTOList": [
                                {
                                    "info": {
                                        "fwHsIndex": {
                                            "value": "f167f9ffda77402c80d467f9ffd80009",
                                            "desc": null
                                        },
                                        "wlcs": {
                                            "value": 2,
                                            "desc": null
                                        },
                                        "hbhs": {
                                            "desc": null
                                        },
                                        "hbfx": {
                                            "desc": null
                                        },
                                        "dyh": {
                                            "value": "3-4-1",
                                            "desc": "单元号"
                                        },
                                        "zl": {
                                            "desc": "坐落"
                                        },
                                        "fjh": {
                                            "value": "301",
                                            "desc": null
                                        },
                                        "bdcdyh": {
                                            "value": "320684114304GB00008F00020050",
                                            "desc": null
                                        },
                                        "dycs": {
                                            "value": "2",
                                            "desc": null
                                        },
                                        "fwbm": {
                                            "value": "32068411430401000080020505",
                                            "desc": null
                                        },
                                        "scjzmj": {
                                            "desc": "实测建筑面积"
                                        }
                                    },
                                    "status": {
                                        "yy": 0,
                                        "sd": 0,
                                        "cf": 0,
                                        "zjgcdy": 0,
                                        "dyi": 0,
                                        "dj": 0,
                                        "ks": 0,
                                        "ys": 0,
                                        "ydya": 0,
                                        "dya": 0,
                                        "ycf": 0,
                                        "yg": 0
                                    },
                                    "links": [
                                        {
                                            "rel": "fwhsinfo",
                                            "href": "http://fwhsinfoUrl"
                                        }
                                    ]
                                },
                                {
                                    "info": {
                                        "fwHsIndex": {
                                            "value": "f167f9ffda77402c80d467f9ffd80009",
                                            "desc": null
                                        },
                                        "wlcs": {
                                            "value": 2,
                                            "desc": null
                                        },
                                        "hbhs": {
                                            "desc": null
                                        },
                                        "hbfx": {
                                            "desc": null
                                        },
                                        "dyh": {
                                            "value": "3-4-2",
                                            "desc": "单元号"
                                        },
                                        "zl": {
                                            "desc": "坐落"
                                        },
                                        "fjh": {
                                            "value": "302",
                                            "desc": null
                                        },
                                        "bdcdyh": {
                                            "value": "320684114304GB00008F00020050",
                                            "desc": null
                                        },
                                        "dycs": {
                                            "value": "2",
                                            "desc": null
                                        },
                                        "fwbm": {
                                            "value": "32068411430401000080020505",
                                            "desc": null
                                        },
                                        "scjzmj": {
                                            "desc": "实测建筑面积"
                                        }
                                    },
                                    "status": {
                                        "yy": 0,
                                        "sd": 0,
                                        "cf": 0,
                                        "zjgcdy": 0,
                                        "dyi": 0,
                                        "dj": 0,
                                        "ks": 0,
                                        "ys": 0,
                                        "ydya": 0,
                                        "dya": 0,
                                        "ycf": 0,
                                        "yg": 0
                                    },
                                    "links": [
                                        {
                                            "rel": "fwhsinfo",
                                            "href": "http://fwhsinfoUrl"
                                        }
                                    ]
                                }
                            ]
                        },
                        {
                            "dyh": "1",
                            "maxHs": 9,
                            "fwhsResourceDTOList": [
                                {
                                    "info": {
                                        "fwHsIndex": {
                                            "value": "2ec7b98f-d79d-4f0d-87fe-e115b4d58dfb",
                                            "desc": null
                                        },
                                        "wlcs": {
                                            "value": 5,
                                            "desc": null
                                        },
                                        "dyh": {
                                            "value": "1",
                                            "desc": "单元号"
                                        },
                                        "zl": {
                                            "value": "世纪花园1号16",
                                            "desc": "坐落"
                                        },
                                        "hbhs": {
                                            "desc": null
                                        },
                                        "fjh": {
                                            "value": "501",
                                            "desc": null
                                        },
                                        "bdcdyh": {
                                            "value": "320684106208GB00104F00010016",
                                            "desc": null
                                        },
                                        "dycs": {
                                            "value": "5",
                                            "desc": null
                                        },
                                        "hbfx": {
                                            "desc": null
                                        },
                                        "fwbm": {
                                            "value": "3206841062080100104000100167",
                                            "desc": null
                                        },
                                        "scjzmj": {
                                            "value": 0.0,
                                            "desc": "实测建筑面积"
                                        }
                                    },
                                    "status": {
                                        "yy": 0,
                                        "sd": 0,
                                        "cf": 0,
                                        "zjgcdy": 0,
                                        "dyi": 0,
                                        "dj": 0,
                                        "ks": 0,
                                        "ys": 0,
                                        "ydya": 0,
                                        "dya": 0,
                                        "ycf": 0,
                                        "yg": 0
                                    },
                                    "links": [
                                        {
                                            "rel": "fwhsinfo",
                                            "href": "http://fwhsinfoUrl"
                                        }
                                    ]
                                },
                                {
                                    "info": {
                                        "fwHsIndex": {
                                            "value": "9051c1a1-fb57-43a1-93a4-a20805fab3e1",
                                            "desc": null
                                        },
                                        "wlcs": {
                                            "value": 5,
                                            "desc": null
                                        },
                                        "dyh": {
                                            "value": "1",
                                            "desc": "单元号"
                                        },
                                        "zl": {
                                            "value": "世纪花园1号17",
                                            "desc": "坐落"
                                        },
                                        "hbhs": {
                                            "desc": null
                                        },
                                        "fjh": {
                                            "value": "502",
                                            "desc": null
                                        },
                                        "bdcdyh": {
                                            "value": "320684106208GB00104F00010017",
                                            "desc": null
                                        },
                                        "dycs": {
                                            "value": "5",
                                            "desc": null
                                        },
                                        "hbfx": {
                                            "desc": null
                                        },
                                        "fwbm": {
                                            "value": "3206841062080100104000100175",
                                            "desc": null
                                        },
                                        "scjzmj": {
                                            "value": 0.0,
                                            "desc": "实测建筑面积"
                                        }
                                    },
                                    "status": {
                                        "yy": 0,
                                        "sd": 0,
                                        "cf": 0,
                                        "zjgcdy": 0,
                                        "dyi": 0,
                                        "dj": 0,
                                        "ks": 0,
                                        "ys": 0,
                                        "ydya": 0,
                                        "dya": 0,
                                        "ycf": 0,
                                        "yg": 0
                                    },
                                    "links": [
                                        {
                                            "rel": "fwhsinfo",
                                            "href": "http://fwhsinfoUrl"
                                        }
                                    ]
                                },
                                {
                                    "info": {
                                        "fwHsIndex": {
                                            "value": "465cc471-77b5-41a1-a646-815819af3037",
                                            "desc": null
                                        },
                                        "wlcs": {
                                            "value": 5,
                                            "desc": null
                                        },
                                        "dyh": {
                                            "value": "1",
                                            "desc": "单元号"
                                        },
                                        "zl": {
                                            "value": "世纪花园1号18",
                                            "desc": "坐落"
                                        },
                                        "hbhs": {
                                            "desc": null
                                        },
                                        "fjh": {
                                            "value": "503",
                                            "desc": null
                                        },
                                        "bdcdyh": {
                                            "value": "320684106208GB00104F00010018",
                                            "desc": null
                                        },
                                        "dycs": {
                                            "value": "5",
                                            "desc": null
                                        },
                                        "hbfx": {
                                            "desc": null
                                        },
                                        "fwbm": {
                                            "value": "3206841062080100104000100183",
                                            "desc": null
                                        },
                                        "scjzmj": {
                                            "value": 0.0,
                                            "desc": "实测建筑面积"
                                        }
                                    },
                                    "status": {
                                        "yy": 0,
                                        "sd": 0,
                                        "cf": 0,
                                        "zjgcdy": 0,
                                        "dyi": 0,
                                        "dj": 0,
                                        "ks": 0,
                                        "ys": 0,
                                        "ydya": 0,
                                        "dya": 0,
                                        "ycf": 0,
                                        "yg": 0
                                    },
                                    "links": [
                                        {
                                            "rel": "fwhsinfo",
                                            "href": "http://fwhsinfoUrl"
                                        }
                                    ]
                                },
                                {
                                    "info": {
                                        "fwHsIndex": {
                                            "value": "4120d733-e62e-4615-821f-ec994c96bf82",
                                            "desc": null
                                        },
                                        "wlcs": {
                                            "value": 5,
                                            "desc": null
                                        },
                                        "dyh": {
                                            "value": "1",
                                            "desc": "单元号"
                                        },
                                        "zl": {
                                            "value": "世纪花园1号15",
                                            "desc": "坐落"
                                        },
                                        "hbhs": {
                                            "desc": null
                                        },
                                        "fjh": {
                                            "value": "504",
                                            "desc": null
                                        },
                                        "bdcdyh": {
                                            "value": "320684106208GB00104F00010015",
                                            "desc": null
                                        },
                                        "dycs": {
                                            "value": "5",
                                            "desc": null
                                        },
                                        "hbfx": {
                                            "desc": null
                                        },
                                        "fwbm": {
                                            "value": "3206841062080100104000100159",
                                            "desc": null
                                        },
                                        "scjzmj": {
                                            "value": 0.0,
                                            "desc": "实测建筑面积"
                                        }
                                    },
                                    "status": {
                                        "yy": 0,
                                        "sd": 0,
                                        "cf": 0,
                                        "zjgcdy": 0,
                                        "dyi": 0,
                                        "dj": 0,
                                        "ks": 0,
                                        "ys": 0,
                                        "ydya": 0,
                                        "dya": 0,
                                        "ycf": 0,
                                        "yg": 0
                                    },
                                    "links": [
                                        {
                                            "rel": "fwhsinfo",
                                            "href": "http://fwhsinfoUrl"
                                        }
                                    ]
                                },
                                {
                                    "info": {
                                        "fwHsIndex": {
                                            "value": "46d62984-a4da-408f-b10f-9cbc8eb192c2",
                                            "desc": null
                                        },
                                        "wlcs": {
                                            "value": 5,
                                            "desc": null
                                        },
                                        "dyh": {
                                            "value": "1",
                                            "desc": "单元号"
                                        },
                                        "zl": {
                                            "value": "世纪花园1号13",
                                            "desc": "坐落"
                                        },
                                        "hbhs": {
                                            "value": 1,
                                            "desc": null
                                        },
                                        "fjh": {
                                            "value": "505",
                                            "desc": null
                                        },
                                        "bdcdyh": {
                                            "value": "320684106208GB00104F00010046",
                                            "desc": null
                                        },
                                        "dycs": {
                                            "value": "5",
                                            "desc": null
                                        },
                                        "hbfx": {
                                            "value": "2",
                                            "desc": null
                                        },
                                        "fwbm": {
                                            "value": "3206841062080100104000100468",
                                            "desc": null
                                        },
                                        "scjzmj": {
                                            "value": 0.0,
                                            "desc": "实测建筑面积"
                                        }
                                    },
                                    "status": {
                                        "yy": 0,
                                        "sd": 0,
                                        "cf": 0,
                                        "zjgcdy": 0,
                                        "dyi": 0,
                                        "dj": 0,
                                        "ks": 0,
                                        "ys": 0,
                                        "ydya": 0,
                                        "dya": 0,
                                        "ycf": 0,
                                        "yg": 0
                                    },
                                    "links": [
                                        {
                                            "rel": "fwhsinfo",
                                            "href": "http://fwhsinfoUrl"
                                        }
                                    ]
                                },
                                {
                                    "info": {
                                        "fwHsIndex": {
                                            "value": "c63dfd98-87f7-4126-b3db-3ec6127d41e5",
                                            "desc": null
                                        },
                                        "wlcs": {
                                            "value": 5,
                                            "desc": null
                                        },
                                        "dyh": {
                                            "value": "1",
                                            "desc": "单元号"
                                        },
                                        "zl": {
                                            "value": "世纪花园1号10",
                                            "desc": "坐落"
                                        },
                                        "hbhs": {
                                            "value": 1,
                                            "desc": null
                                        },
                                        "fjh": {
                                            "value": "506",
                                            "desc": null
                                        },
                                        "bdcdyh": {
                                            "value": "320684106208GB00104F00010047",
                                            "desc": null
                                        },
                                        "dycs": {
                                            "value": "5",
                                            "desc": null
                                        },
                                        "hbfx": {
                                            "value": "1",
                                            "desc": null
                                        },
                                        "fwbm": {
                                            "value": "3206841062080100104000100476",
                                            "desc": null
                                        },
                                        "scjzmj": {
                                            "value": 77.0,
                                            "desc": "实测建筑面积"
                                        }
                                    },
                                    "status": {
                                        "yy": 0,
                                        "sd": 0,
                                        "cf": 0,
                                        "zjgcdy": 0,
                                        "dyi": 0,
                                        "dj": 0,
                                        "ks": 0,
                                        "ys": 0,
                                        "ydya": 0,
                                        "dya": 0,
                                        "ycf": 0,
                                        "yg": 0
                                    },
                                    "links": [
                                        {
                                            "rel": "fwhsinfo",
                                            "href": "http://fwhsinfoUrl"
                                        }
                                    ]
                                }
                            ]
                        }
                    ],
                    "wlcs": "5",
                    "dycs": "5"
                },
                {
                    "dyFwList": [
                        {
                            "dyh": "2",
                            "maxHs": 1,
                            "fwhsResourceDTOList": [
                                {
                                    "info": {
                                        "fwHsIndex": {
                                            "value": "f167f9ffda77402c80d467f9ffd80009",
                                            "desc": null
                                        },
                                        "wlcs": {
                                            "value": 2,
                                            "desc": null
                                        },
                                        "hbhs": {
                                            "desc": null
                                        },
                                        "hbfx": {
                                            "desc": null
                                        },
                                        "dyh": {
                                            "value": "2-5",
                                            "desc": "单元号"
                                        },
                                        "zl": {
                                            "desc": "坐落"
                                        },
                                        "fjh": {
                                            "value": "201",
                                            "desc": null
                                        },
                                        "bdcdyh": {
                                            "value": "320684114304GB00008F00020050",
                                            "desc": null
                                        },
                                        "dycs": {
                                            "value": "2",
                                            "desc": null
                                        },
                                        "fwbm": {
                                            "value": "32068411430401000080020505",
                                            "desc": null
                                        },
                                        "scjzmj": {
                                            "desc": "实测建筑面积"
                                        }
                                    },
                                    "status": {
                                        "yy": 0,
                                        "sd": 0,
                                        "cf": 0,
                                        "zjgcdy": 0,
                                        "dyi": 0,
                                        "dj": 0,
                                        "ks": 0,
                                        "ys": 0,
                                        "ydya": 0,
                                        "dya": 0,
                                        "ycf": 0,
                                        "yg": 0
                                    },
                                    "links": [
                                        {
                                            "rel": "fwhsinfo",
                                            "href": "http://fwhsinfoUrl"
                                        }
                                    ]
                                }
                            ]
                        },
                        {
                            "dyh": "3",
                            "maxHs": 2,
                            "fwhsResourceDTOList": [
                                {
                                    "info": {
                                        "fwHsIndex": {
                                            "value": "f167f9ffda77402c80d467f9ffd80009",
                                            "desc": null
                                        },
                                        "wlcs": {
                                            "value": 2,
                                            "desc": null
                                        },
                                        "hbhs": {
                                            "value": 1,
                                            "desc": null
                                        },
                                        "hbfx": {
                                            "value": '1',
                                            "desc": null
                                        },
                                        "dyh": {
                                            "value": "3-5-1",
                                            "desc": "单元号"
                                        },
                                        "zl": {
                                            "desc": "坐落"
                                        },
                                        "fjh": {
                                            "value": "301",
                                            "desc": null
                                        },
                                        "bdcdyh": {
                                            "value": "320684114304GB00008F00020050",
                                            "desc": null
                                        },
                                        "dycs": {
                                            "value": "2",
                                            "desc": null
                                        },
                                        "fwbm": {
                                            "value": "32068411430401000080020505",
                                            "desc": null
                                        },
                                        "scjzmj": {
                                            "desc": "实测建筑面积"
                                        }
                                    },
                                    "status": {
                                        "yy": 0,
                                        "sd": 0,
                                        "cf": 0,
                                        "zjgcdy": 0,
                                        "dyi": 0,
                                        "dj": 0,
                                        "ks": 0,
                                        "ys": 0,
                                        "ydya": 0,
                                        "dya": 0,
                                        "ycf": 0,
                                        "yg": 0
                                    },
                                    "links": [
                                        {
                                            "rel": "fwhsinfo",
                                            "href": "http://fwhsinfoUrl"
                                        }
                                    ]
                                }
                            ]
                        },
                        {
                            "dyh": "1",
                            "maxHs": 9,
                            "fwhsResourceDTOList": [
                                {
                                    "info": {
                                        "fwHsIndex": {
                                            "value": "5a9f57a1-f1f5-45e7-ad4d-6730479ffae1",
                                            "desc": null
                                        },
                                        "wlcs": {
                                            "value": 4,
                                            "desc": null
                                        },
                                        "dyh": {
                                            "value": "1",
                                            "desc": "单元号"
                                        },
                                        "zl": {
                                            "value": "世纪花园1号6",
                                            "desc": "坐落"
                                        },
                                        "hbhs": {
                                            "desc": null
                                        },
                                        "fjh": {
                                            "value": "401",
                                            "desc": null
                                        },
                                        "bdcdyh": {
                                            "value": "320684106208GB00104F00010006",
                                            "desc": null
                                        },
                                        "dycs": {
                                            "value": "4",
                                            "desc": null
                                        },
                                        "hbfx": {
                                            "desc": null
                                        },
                                        "fwbm": {
                                            "value": "3206841062080100104000100061",
                                            "desc": null
                                        },
                                        "scjzmj": {
                                            "value": 0.0,
                                            "desc": "实测建筑面积"
                                        }
                                    },
                                    "status": {
                                        "yy": 0,
                                        "sd": 0,
                                        "cf": 0,
                                        "zjgcdy": 0,
                                        "dyi": 0,
                                        "dj": 0,
                                        "ks": 0,
                                        "ys": 0,
                                        "ydya": 0,
                                        "dya": 0,
                                        "ycf": 0,
                                        "yg": 0
                                    },
                                    "links": [
                                        {
                                            "rel": "fwhsinfo",
                                            "href": "http://fwhsinfoUrl"
                                        }
                                    ]
                                },
                                {
                                    "info": {
                                        "fwHsIndex": {
                                            "value": "717e6cf6-9d24-4ae1-aed5-1438d9bf7c67",
                                            "desc": null
                                        },
                                        "wlcs": {
                                            "value": 4,
                                            "desc": null
                                        },
                                        "dyh": {
                                            "value": "1",
                                            "desc": "单元号"
                                        },
                                        "zl": {
                                            "value": "世纪花园1号7",
                                            "desc": "坐落"
                                        },
                                        "hbhs": {
                                            "desc": null
                                        },
                                        "fjh": {
                                            "value": "402",
                                            "desc": null
                                        },
                                        "bdcdyh": {
                                            "value": "320684106208GB00104F00010007",
                                            "desc": null
                                        },
                                        "dycs": {
                                            "value": "4",
                                            "desc": null
                                        },
                                        "hbfx": {
                                            "desc": null
                                        },
                                        "fwbm": {
                                            "value": "3206841062080100104000100079",
                                            "desc": null
                                        },
                                        "scjzmj": {
                                            "value": 0.0,
                                            "desc": "实测建筑面积"
                                        }
                                    },
                                    "status": {
                                        "yy": 0,
                                        "sd": 0,
                                        "cf": 0,
                                        "zjgcdy": 0,
                                        "dyi": 0,
                                        "dj": 0,
                                        "ks": 0,
                                        "ys": 0,
                                        "ydya": 0,
                                        "dya": 0,
                                        "ycf": 0,
                                        "yg": 0
                                    },
                                    "links": [
                                        {
                                            "rel": "fwhsinfo",
                                            "href": "http://fwhsinfoUrl"
                                        }
                                    ]
                                },
                                {
                                    "info": {
                                        "fwHsIndex": {
                                            "value": "d12e5d7f-fb98-48a4-837d-2992aef6c01b",
                                            "desc": null
                                        },
                                        "wlcs": {
                                            "value": 4,
                                            "desc": null
                                        },
                                        "dyh": {
                                            "value": "1",
                                            "desc": "单元号"
                                        },
                                        "zl": {
                                            "value": "世纪花园1号8",
                                            "desc": "坐落"
                                        },
                                        "hbhs": {
                                            "desc": null
                                        },
                                        "fjh": {
                                            "value": "403",
                                            "desc": null
                                        },
                                        "bdcdyh": {
                                            "value": "320684106208GB00104F00010008",
                                            "desc": null
                                        },
                                        "dycs": {
                                            "value": "4",
                                            "desc": null
                                        },
                                        "hbfx": {
                                            "desc": null
                                        },
                                        "fwbm": {
                                            "value": "3206841062080100104000100087",
                                            "desc": null
                                        },
                                        "scjzmj": {
                                            "value": 0.0,
                                            "desc": "实测建筑面积"
                                        }
                                    },
                                    "status": {
                                        "yy": 0,
                                        "sd": 0,
                                        "cf": 0,
                                        "zjgcdy": 0,
                                        "dyi": 0,
                                        "dj": 0,
                                        "ks": 0,
                                        "ys": 0,
                                        "ydya": 0,
                                        "dya": 0,
                                        "ycf": 0,
                                        "yg": 0
                                    },
                                    "links": [
                                        {
                                            "rel": "fwhsinfo",
                                            "href": "http://fwhsinfoUrl"
                                        }
                                    ]
                                },
                                {
                                    "info": {
                                        "fwHsIndex": {
                                            "value": "3c3381b3-f762-49a5-a2c9-5888afb56eda",
                                            "desc": null
                                        },
                                        "wlcs": {
                                            "value": 4,
                                            "desc": null
                                        },
                                        "dyh": {
                                            "value": "1",
                                            "desc": "单元号"
                                        },
                                        "zl": {
                                            "value": "世纪花园1号9",
                                            "desc": "坐落"
                                        },
                                        "hbhs": {
                                            "desc": null
                                        },
                                        "fjh": {
                                            "value": "404",
                                            "desc": null
                                        },
                                        "bdcdyh": {
                                            "value": "320684106208GB00104F00010009",
                                            "desc": null
                                        },
                                        "dycs": {
                                            "value": "4",
                                            "desc": null
                                        },
                                        "hbfx": {
                                            "desc": null
                                        },
                                        "fwbm": {
                                            "value": "3206841062080100104000100095",
                                            "desc": null
                                        },
                                        "scjzmj": {
                                            "value": 0.0,
                                            "desc": "实测建筑面积"
                                        }
                                    },
                                    "status": {
                                        "yy": 0,
                                        "sd": 0,
                                        "cf": 0,
                                        "zjgcdy": 0,
                                        "dyi": 0,
                                        "dj": 0,
                                        "ks": 0,
                                        "ys": 0,
                                        "ydya": 0,
                                        "dya": 0,
                                        "ycf": 0,
                                        "yg": 0
                                    },
                                    "links": [
                                        {
                                            "rel": "fwhsinfo",
                                            "href": "http://fwhsinfoUrl"
                                        }
                                    ]
                                },
                                {
                                    "info": {
                                        "fwHsIndex": {
                                            "value": "49345ae0-46d3-4a77-a264-b1ad3b48ac0a",
                                            "desc": null
                                        },
                                        "wlcs": {
                                            "value": 4,
                                            "desc": null
                                        },
                                        "dyh": {
                                            "value": "1",
                                            "desc": "单元号"
                                        },
                                        "zl": {
                                            "value": "世纪花园1号1",
                                            "desc": "坐落"
                                        },
                                        "hbhs": {
                                            "desc": null
                                        },
                                        "fjh": {
                                            "value": "405",
                                            "desc": null
                                        },
                                        "bdcdyh": {
                                            "value": "320684106208GB00104F00010001",
                                            "desc": null
                                        },
                                        "dycs": {
                                            "value": "4",
                                            "desc": null
                                        },
                                        "hbfx": {
                                            "desc": null
                                        },
                                        "fwbm": {
                                            "value": "3206841062080100104000100011",
                                            "desc": null
                                        },
                                        "scjzmj": {
                                            "desc": "实测建筑面积"
                                        }
                                    },
                                    "status": {
                                        "yy": 0,
                                        "sd": 0,
                                        "cf": 0,
                                        "zjgcdy": 0,
                                        "dyi": 0,
                                        "dj": 0,
                                        "ks": 0,
                                        "ys": 0,
                                        "ydya": 0,
                                        "dya": 0,
                                        "ycf": 0,
                                        "yg": 0
                                    },
                                    "links": [
                                        {
                                            "rel": "fwhsinfo",
                                            "href": "http://fwhsinfoUrl"
                                        }
                                    ]
                                },
                                {
                                    "info": {
                                        "fwHsIndex": {
                                            "value": "2a21b0c2-74ec-4a79-8941-397ea4b63a93",
                                            "desc": null
                                        },
                                        "wlcs": {
                                            "value": 4,
                                            "desc": null
                                        },
                                        "dyh": {
                                            "value": "1",
                                            "desc": "单元号"
                                        },
                                        "zl": {
                                            "value": "世纪花园1号3",
                                            "desc": "坐落"
                                        },
                                        "hbhs": {
                                            "value": 2,
                                            "desc": null
                                        },
                                        "fjh": {
                                            "value": "406",
                                            "desc": null
                                        },
                                        "bdcdyh": {
                                            "value": "320684106208GB00104F00010045",
                                            "desc": null
                                        },
                                        "dycs": {
                                            "value": "4",
                                            "desc": null
                                        },
                                        "hbfx": {
                                            "value": "2",
                                            "desc": null
                                        },
                                        "fwbm": {
                                            "value": "3206841062080100104000100450",
                                            "desc": null
                                        },
                                        "scjzmj": {
                                            "value": 233.0,
                                            "desc": "实测建筑面积"
                                        }
                                    },
                                    "status": {
                                        "yy": 0,
                                        "sd": 0,
                                        "cf": 0,
                                        "zjgcdy": 0,
                                        "dyi": 0,
                                        "dj": 0,
                                        "ks": 0,
                                        "ys": 0,
                                        "ydya": 0,
                                        "dya": 0,
                                        "ycf": 0,
                                        "yg": 0
                                    },
                                    "links": [
                                        {
                                            "rel": "fwhsinfo",
                                            "href": "http://fwhsinfoUrl"
                                        }
                                    ]
                                },
                                {
                                    "info": {
                                        "fwHsIndex": {
                                            "value": "3ab41935-13af-4d04-8f3a-b9fb85aa61ca",
                                            "desc": null
                                        },
                                        "wlcs": {
                                            "value": 4,
                                            "desc": null
                                        },
                                        "dyh": {
                                            "value": "1",
                                            "desc": "单元号"
                                        },
                                        "zl": {
                                            "value": "世纪花园1号2",
                                            "desc": "坐落"
                                        },
                                        "hbhs": {
                                            "value": 1,
                                            "desc": null
                                        },
                                        "fjh": {
                                            "value": "407",
                                            "desc": null
                                        },
                                        "bdcdyh": {
                                            "value": "320684106208GB00104F00010002",
                                            "desc": null
                                        },
                                        "dycs": {
                                            "value": "4",
                                            "desc": null
                                        },
                                        "hbfx": {
                                            "value": "3",
                                            "desc": null
                                        },
                                        "fwbm": {
                                            "value": "3206841062080100104000100020",
                                            "desc": null
                                        },
                                        "scjzmj": {
                                            "value": 444.0,
                                            "desc": "实测建筑面积"
                                        }
                                    },
                                    "status": {
                                        "yy": 0,
                                        "sd": 0,
                                        "cf": 0,
                                        "zjgcdy": 0,
                                        "dyi": 0,
                                        "dj": 0,
                                        "ks": 0,
                                        "ys": 0,
                                        "ydya": 0,
                                        "dya": 0,
                                        "ycf": 0,
                                        "yg": 0
                                    },
                                    "links": [
                                        {
                                            "rel": "fwhsinfo",
                                            "href": "http://fwhsinfoUrl"
                                        }
                                    ]
                                }
                            ]
                        }
                    ],
                    "wlcs": "4",
                    "dycs": "4"
                }
            ],
            "resource": {
                "info": {
                    "lszd": {
                        "value": "320684106208GB00104",
                        "desc": null
                    },
                    "fwDcbIndex": {
                        "value": "c32f6a6b-e35b-4c39-98fb-1209d26c8f30",
                        "desc": null
                    },
                    "zrzh": {
                        "value": "1",
                        "desc": null
                    }
                },
                "status": {},
                "links": [
                    {
                        "rel": "zdtreelistljz",
                        "href": "http://127.0.0.1:8999/building-ui/lpb/listljz"
                    },
                    {
                        "rel": "zdtreejson",
                        "href": "http://127.0.0.1:8999/building-ui/lpb/initzdtree?fwDcbIndex=c32f6a6b-e35b-4c39-98fb-1209d26c8f30"
                    },
                    {
                        "rel": "zdjbxx",
                        "href": "http://zdjbxxurl"
                    },
                    {
                        "rel": "zrzjbxx",
                        "href": "http://zrzjbxxUrl"
                    },
                    {
                        "rel": "ljzjbxx",
                        "href": "http://ljzjbxxUrl"
                    }
                ]
            },
            "maxHs": 9
        };
        var dynamicCol = [
            [{field: 'wlcs',unresize: true,align: 'center', title: '物理层数',fixed: 'left', width: 90,rowspan: 2}
                ,{field: 'dycs',unresize: true,align: 'center', title: '定义层数',fixed: 'left', width: 90,rowspan: 2}
            ],
            []
        ];
        var mainData = [];
        var mergeData = [];
        getData.cFwList.forEach(function (value,index) {
            // console.log(value);
            mainData.push({
                wlcs: value.wlcs,
                dycs: value.dycs,
                dyFwList: [[],[]]//第一个放置colspan为1的，第二个放置colspan超过1的
            });
            value.dyFwList.forEach(function (val,ind) {
                // console.log(val);
                // console.log(val.fwhsResourceDTOList);
                for(var n = 0; n < val.maxHs; n++){
                    // console.log(val.fwhsResourceDTOList[n]);
                    if(!$.isEmptyObject(val.fwhsResourceDTOList[n])){
                        if(val.fwhsResourceDTOList[n].info.hbhs.value){
                            // console.log(va.info.hbfx.value);
                            var num = 0;
                            for(var m = 0; m < ind; m++){
                                num += getData.cFwList[index].dyFwList[m].maxHs;
                            }
                            switch(val.fwhsResourceDTOList[n].info.hbfx.value)
                            {
                                case '1':case '2':
                                mergeData.push([index+1,n+num+3,{colspan:val.fwhsResourceDTOList[n].info.hbhs.value + 1}]);
                                for(var v = 1; v <= val.fwhsResourceDTOList[n].info.hbhs.value; v++){
                                    val.fwhsResourceDTOList.splice(n+v,0,{});
                                }
                                break;
                                case '3':
                                    for(var v = 1; v <= val.fwhsResourceDTOList[n].info.hbhs.value; v++){
                                        getData.cFwList[index-v].dyFwList[ind].fwhsResourceDTOList.splice(n,0,{});
                                    }
                                    mergeData.push([index +1,n+num+3,{up:val.fwhsResourceDTOList[n].info.hbhs.value + 1}]);
                                    break;
                                case '4':
                                    for(var v = 1; v <= val.fwhsResourceDTOList[n].info.hbhs.value; v++){
                                        getData.cFwList[index+v].dyFwList[ind].fwhsResourceDTOList.splice(n,0,{});
                                    }
                                    mergeData.push([index+1,n+num+3,{rowspan:val.fwhsResourceDTOList[n].info.hbhs.value + 1}]);
                                    break;
                            }
                        }
                    }
                }
            });
            // console.log(value);
            value.dyFwList.forEach(function (v) {
                // console.log(v);
                if(v.maxHs == 1){
                    v.fwhsResourceDTOList.forEach(function (va) {
                        mainData[index].dyFwList[0].push(va);
                    });
                }else {
                    var resourseList = v.fwhsResourceDTOList;
                    for(var k = 0; k < v.maxHs; k++){
                        if(resourseList[k]){
                            mainData[index].dyFwList[1].push(resourseList[k]);
                        }else {
                            mainData[index].dyFwList[1].push({});
                        }
                    }
                }
            });
        });
        // console.log(mainData);
        var dynamicIndex = 0;
        var colspan1Num = 0;
        // console.log(getData);
        getData.dyList.forEach(function (v) {
            var nowNum = colspan1Num;
            if(v.colspan == 1){
                dynamicCol[0].push({field: '',unresize: true, title: v.dyh, rowspan: 2,width: 110,templet: function(d){
                        // console.log(d.dyFwList[0]);
                        var colList = d.dyFwList[0][nowNum];
                        // console.log(colList);
                        if(colList){
                            if(colList.status){
                                return '<div class="layui-form">'+
                                    '<input type="checkbox" name="house" lay-skin="primary">'+
                                    '</div>'+
                                    '<p class="bdc-fh">房号：<span class="bdc-wdj-word">'+ colList.info.fjh.value +'</span></p>'+
                                    '<p class="bdc-describe bdc-hide">'+ colList.info.dyh.value +'</p>'+
                                    '<p class="bdc-table-state bdc-jy-state bdc-hide">'+
                                    '<span class="bdc-ks"></span>'+
                                    '</p>'+
                                    '<p class="bdc-table-state bdc-ql-state bdc-hide">'+
                                    '<span class="bdc-zj"></span>'+
                                    '<span class="bdc-ydy"></span>'+
                                    '</p>';
                            }else {
                                return '<div class="layui-form">'+
                                    '<input type="checkbox" name="house" lay-skin="primary">'+
                                    '</div>'+
                                    '<p class="bdc-fh">房号：<span class="bdc-wdj-word">'+ colList.info.fjh.value +'</span></p>'+
                                    '<p class="bdc-describe bdc-hide">'+ colList.info.dyh.value +'</p>'+
                                    '<p class="bdc-table-state bdc-jy-state bdc-hide">无交易状态</p>'+
                                    '<p class="bdc-table-state bdc-ql-state bdc-hide">无权利状态</p>';
                            }

                        }else {
                            return '';
                        }

                    }});

                colspan1Num++;

            }else {
                dynamicIndex += v.colspan;
                dynamicCol[0].push({align: "center", title: v.dyh, colspan: v.colspan});
            }
        });
        for(var i = 0; i < dynamicIndex; i++){
            (function(n){
                dynamicCol[1].push({field: '', title: n,width: 110,templet: function(d){
                        var setList = d.dyFwList[1][n];
                        // console.log(setList);
                        if($.isEmptyObject(setList)){
                            return '';
                        }else {
                            return '<div class="layui-form">'+
                                '<input type="checkbox" name="house" lay-skin="primary">'+
                                '</div>'+
                                '<p class="bdc-fh">房号：<span class="bdc-wdj-word">'+ setList.info.fjh.value +'</span></p>'+
                                '<p class="bdc-describe bdc-hide">'+ setList.info.dyh.value +'</p>'+
                                '<p class="bdc-table-state bdc-jy-state bdc-hide">'+
                                '<span class="bdc-ks"></span>'+
                                '</p>'+
                                '<p class="bdc-table-state bdc-ql-state bdc-hide">'+
                                '<span class="bdc-zj"></span>'+
                                '<span class="bdc-ydy"></span>'+
                                '</p>';
                        }

                    }});
            })(i);
        }
        // console.log(dynamicCol);
        table.render({
            elem: '#tableView',
            height: 'full-237',
            id: 'resizeId',
            cols:  dynamicCol,
            toolbar: '#toolbarDemo',
            defaultToolbar: [],
            limit: 20,
            data: mainData,
            done: function () {
                //内容扩展高度调整
                if($('.layui-table-main .layui-table td').height() != 52){
                    $('.layui-table-fixed .layui-table td').height($('.layui-table-main .layui-table td').height()+1);
                    $('.layui-table-fixed .layui-table tr:first-child td').height($('.layui-table-main .layui-table td').height());
                }

                //rowspan为1的，调整位置
                var $tableCol = $('.layui-table-header thead .layui-table-col-special');
                for(var i = 0; i < $tableCol.length; i++){
                    if($($tableCol[i]).attr('rowspan') == 2){
                        $($tableCol[i]).find('.layui-table-cell').css({'top':'-16px','text-align': 'center'});
                    }
                }

                // console.log(mergeData);
                mergeData.forEach(function (value) {
                    if(value[2].rowspan){
                        $('.layui-table-main tbody tr:nth-child('+value[0]+') td:nth-child('+ value[1] +')').attr('rowspan',value[2].rowspan);
                        for(var i = 1; i < value[2].rowspan; i++){
                            var getInd = value[0] + i;
                            $('.layui-table-main tbody tr:nth-child('+getInd +') td:nth-child('+ value[1] +')').css('display','none');
                        }
                    }else if(value[2].colspan){
                        $('.layui-table-main tbody tr:nth-child('+value[0]+') td:nth-child('+ value[1] +')').attr('colspan',value[2].colspan);
                        for(var n = 1; n < value[2].colspan; n++){
                            var getIndex = value[1] + n;
                            $('.layui-table-main tbody tr:nth-child('+value[0] +') td:nth-child('+ getIndex +')').css('display','none');
                        }
                    }else if(value[2].up){
                        var getHtml = $('.layui-table-main tbody tr:nth-child(' + value[0] + ') td:nth-child(' + value[1] + ')').html();
                        for(var n = 0; n < value[2].up - 1; n++){
                            var getIndex1 = value[0] - n;
                            $('.layui-table-main tbody tr:nth-child('+getIndex1 +') td:nth-child('+ value[1] +')').css('display','none');
                        }
                        var getNum = value[0] - value[2].up +1;
                        $('.layui-table-main tbody tr:nth-child('+getNum+') td:nth-child('+ value[1] +')').html(getHtml).attr('rowspan',value[2].up);
                    }
                });

                form.render('checkbox');
                $('.layui-table-box .layui-form-checkbox').addClass('bdc-hide');
            }
        });

        //监听滚动事件
        var scrollTop = 0,
            scrollLeft = 0;
        $(window).on('scroll',function () {
            scrollTop = $(this).scrollTop();
            scrollLeft = $(this).scrollLeft();
        });

        //点击详细
        var $details = $('.bdc-details');
        $('.bdc-right-content').on('click','.bdc-details-btn',function (event) {
            event.stopPropagation();
            var top = $(this).offset().top-215-scrollTop;
            var left = $(this).offset().left-scrollLeft-354;
            $details.css({top: top,left: left}).removeClass('bdc-hide');
        });

        //监听事件
        table.on('toolbar(test)', function(obj){
            switch(obj.event){
                case 'bdc-select':
                    $('.layui-table-box .layui-form-checkbox').toggleClass('bdc-hide');
                    break;
            }
        });
        //点击更多
        $('.bdc-container').on('click','.bdc-more-box',function (event) {
            event.stopPropagation();
            $(this).find('.bdc-table-btn-more').toggle();
        });
        $('.bdc-table-btn-more a').on('click',function (event) {
            event.stopPropagation();
            $(this).parent().hide();
        });
        $('.bdc-export-tools .bdc-table-btn-more>span').on('click',function (event) {
            event.stopPropagation();
        });
        //点击页面任一空白位置，详情弹窗及更多操作按钮弹窗消失
        $('body').on('click',function () {
            $('.bdc-container').find('.bdc-table-btn-more').hide();
            $details.addClass('bdc-hide');
        });

        //新增
        // 监听点击更多中的复选框
        form.on('checkbox(showOthers)', function(data){
            switch($(data.elem).data('mark')){
                case 0:
                    //房屋信息
                    if(data.elem.checked){
                        $('.bdc-describe').removeClass('bdc-hide');
                    }else {
                        $('.bdc-describe').addClass('bdc-hide');
                    }
                    break;
                case 1:
                    //交易状态
                    if(data.elem.checked){
                        $('.bdc-jy-state').removeClass('bdc-hide');
                    }else {
                        $('.bdc-jy-state').addClass('bdc-hide');
                    }
                    break;
                case 2:
                    //权利信息
                    if(data.elem.checked){
                        $('.bdc-ql-state').removeClass('bdc-hide');
                    }else {
                        $('.bdc-ql-state').addClass('bdc-hide');
                    }
                    break;
            }
            $('.layui-table-fixed .layui-table-body').height($('.layui-table-body.layui-table-main').height()-17);
            $('.layui-table-fixed .layui-table td').height($('.layui-table-main .layui-table td').height()+1);
            $('.layui-table-fixed .layui-table tr:first-child td').height($('.layui-table-main .layui-table td').height());
        });
    });
});