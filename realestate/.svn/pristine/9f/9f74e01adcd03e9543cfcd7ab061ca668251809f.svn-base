/**
 * Created by Administrator on 2018/12/21.
 */
layui.use(['jquery', 'form', 'laytpl','element','table'], function () {
    var $ = layui.jquery,
        laytpl = layui.laytpl,
        element = layui.element,
        table = layui.table;
    $(function () {
        //获取最外层元素的padding-left的值
        var containerLeft = 300;

        //侧边栏渲染完成获取总共的个数
        var liIndex = 0;
        var liLength = $('.accordion .bdc-invented-li').length;
        // console.log(liLength);

        //新增，根据地址栏参数，默认展示指定内容
        function GetQueryString(name) {
            var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if(r!=null)return  unescape(r[2]); return null;
        }
        var getNum = GetQueryString('num');
        if(getNum != null && getNum.toString().length > 1){
            liIndex = 0;
            for(var i = 0; i < $('.accordion .bdc-outer-li').length; i++){
                var getUl = $('.accordion>.bdc-invented-li:nth-child('+i+') .submenu');
                // console.log(getUl);
                if(getUl.length > 0){
                    // console.log(getUl.find('li').length);
                    liIndex += getUl.find('.bdc-inner-li').length+1;
                }else {
                    liIndex++;
                }

                if($($('.accordion .bdc-outer-li')[i]).data('num') == getNum){
                    var nowI = i + 1;
                    $('.accordion .bdc-outer-li:nth-child('+ nowI +')').addClass('open bdc-this-li').find('.submenu').show();
                    $('.content-div iframe').attr('src',$('.accordion .bdc-outer-li:nth-child('+ nowI +') .link').data('src'));
                    break;
                }
            }
            // console.log(liIndex);
        }

        // 动态渲染侧边栏
        var firstData = [
            {
                "lsgxModelDTO": {
                    "id": "f1695bf7472d402880af695b45370040",
                    "yid": null,
                    "qllx": 4,
                    "cjsj": "2019-03-08 14:15:18",
                    "qlxx": null,
                    "djlx": 100,
                    "bdcqzh": "苏(2019)海门市不动产权第000000015号",
                    "qszt": 2,
                    "dbr": "admin",
                    "djsj": "2019-03-08 15:03:53"
                },
                "lsgxXzqlModelDTO": {
                    "dyLsgxModel": [{
                        "id": "f1696d8007ee402880af696d7ac5000d",
                        "yid": "f1695bf7472d402880af695b45370040",
                        "qllx": 37,
                        "cjsj": "2019-03-11 16:11:40",
                        "qlxx": null,
                        "djlx": 900,
                        "bdcqzh": null,
                        "qszt": 1,
                        "dbr": "admin",
                        "djsj": "2019-03-12 00:28:29"
                    }, {
                        "id": "f1696e99f1c0402880af696d7ac500ce",
                        "yid": "f1695bf7472d402880af695b45370040",
                        "qllx": 37,
                        "cjsj": "2019-03-12 05:16:16",
                        "qlxx": null,
                        "djlx": 900,
                        "bdcqzh": "苏(2019)海门市不动产证明第020号",
                        "qszt": 2,
                        "dbr": "admin",
                        "djsj": "2019-03-12 05:34:53"
                    }, {
                        "id": "f16978f4867b402880af6976a17b026c",
                        "yid": "f1696e99f1c0402880af696d7ac500ce",
                        "qllx": 37,
                        "cjsj": "2019-03-13 21:35:23",
                        "qlxx": null,
                        "djlx": 900,
                        "bdcqzh": null,
                        "qszt": 0,
                        "dbr": null,
                        "djsj": null
                    }, {
                        "id": "f16981907e6c4028833c6981652c0051",
                        "yid": "f1696e99f1c0402880af696d7ac500ce",
                        "qllx": 37,
                        "cjsj": "2019-03-15 21:35:31",
                        "qlxx": null,
                        "djlx": 900,
                        "bdcqzh": "苏(2019)海门市不动产证明第030号",
                        "qszt": 1,
                        "dbr": "admin",
                        "djsj": "2019-03-15 21:43:51"
                    }],
                    "cfLsgxModel": [],
                    "yyLsgxModel": [],
                    "ygLsgxModel": [],
                    "dyiLsgxModel": []
                }
            },
            {
                "lsgxModelDTO": {
                    "id": "f16950d8dfc5402880af694dcf720123",
                    "yid": null,
                    "qllx": 4,
                    "cjsj": "2019-03-06 18:06:02",
                    "qlxx": null,
                    "djlx": 200,
                    "bdcqzh": "苏(2019)海门市不动产权0290号",
                    "qszt": 0,
                    "dbr": null,
                    "djsj": null
                },
                "lsgxXzqlModelDTO": {
                    "dyLsgxModel": [{
                        "id": "f16950d8dfd5402880af694dcf720125",
                        "yid": "f16950d8dfc5402880af694dcf720123",
                        "qllx": 37,
                        "cjsj": "2019-03-06 18:06:02",
                        "qlxx": null,
                        "djlx": 900,
                        "bdcqzh": null,
                        "qszt": 0,
                        "dbr": null,
                        "djsj": null
                    }],
                    "cfLsgxModel": [],
                    "yyLsgxModel": [],
                    "ygLsgxModel": [],
                    "dyiLsgxModel": []
                }
            },
            {
                "lsgxModelDTO": {
                    "id": "f1697082a7ee402883f6697082380003",
                    "yid": null,
                    "qllx": 4,
                    "cjsj": "2019-03-12 14:06:55",
                    "qlxx": null,
                    "djlx": 100,
                    "bdcqzh": "苏(2019)海门市不动产权0299号",
                    "qszt": 0,
                    "dbr": null,
                    "djsj": null
                },
                "lsgxXzqlModelDTO": null
            }];
        //渲染一级
        var getAsideTpl = asideTpl.innerHTML
            ,asideView = document.getElementById('accordion');
        laytpl(getAsideTpl).render(firstData, function(html){
            asideView.innerHTML = html;
        });
        //监听 一级 第一次点击
        $('#accordion .bdc-outer-li').on('click',function(){
            if(!$(this).hasClass('bdc-render-child')){
                $(this).addClass('bdc-render-child');
                //获取二三级数据
                var secondData = {
                    "dyLsgxModel": [{
                        "id": "f1696d8007ee402880af696d7ac5000d",
                        "yid": "f1695bf7472d402880af695b45370040",
                        "qllx": 37,
                        "cjsj": "2019-03-11 16:11:40",
                        "qlxx": null,
                        "djlx": 900,
                        "bdcqzh": null,
                        "qszt": 1,
                        "dbr": "admin",
                        "djsj": "2019-03-12 00:28:29"
                    }, {
                        "id": "f1696e99f1c0402880af696d7ac500ce",
                        "yid": "f1695bf7472d402880af695b45370040",
                        "qllx": 37,
                        "cjsj": "2019-03-12 05:16:16",
                        "qlxx": null,
                        "djlx": 900,
                        "bdcqzh": "苏(2019)海门市不动产证明第0000020号",
                        "qszt": 2,
                        "dbr": "admin",
                        "djsj": "2019-03-12 05:34:53"
                    }, {
                        "id": "f16978f4867b402880af6976a17b026c",
                        "yid": "f1696e99f1c0402880af696d7ac500ce",
                        "qllx": 37,
                        "cjsj": "2019-03-13 21:35:23",
                        "qlxx": null,
                        "djlx": 900,
                        "bdcqzh": null,
                        "qszt": 0,
                        "dbr": null,
                        "djsj": null
                    }, {
                        "id": "f16981907e6c4028833c6981652c0051",
                        "yid": "f1696e99f1c0402880af696d7ac500ce",
                        "qllx": 37,
                        "cjsj": "2019-03-15 21:35:31",
                        "qlxx": null,
                        "djlx": 900,
                        "bdcqzh": "苏(2019)海门市不动产证明第0000030号",
                        "qszt": 1,
                        "dbr": "admin",
                        "djsj": "2019-03-15 21:43:51"
                    }],
                    "cfLsgxModel": [],
                    "yyLsgxModel": [],
                    "ygLsgxModel": [],
                    "dyiLsgxModel": []
                };
                //渲染
                var newSecondData = [];
                for(var k in secondData){
                    newSecondData.push({
                        name: k,
                        child: secondData[k]
                    });
                }
                var getAsideSecondTpl = asideSecondTpl.innerHTML
                    ,asideSecondView = $(this).children('.submenu')[0];
                laytpl(getAsideSecondTpl).render(newSecondData, function(html){
                    asideSecondView.innerHTML = html;
                });

            }
        })

        //1. 侧边栏点击效果
        //1.1  手风琴点击效果
        $(document).on('click', '.link', function () {
            var $this = $(this);
            $this.parent().toggleClass('open');
            $this.parent().siblings().removeClass('open');
            $this.parent().siblings().find('.bdc-inner-li').removeClass('open');
            $this.parent().siblings().find('.submenu').slideUp();
            $(this).siblings('.submenu').slideToggle();

            $('.content-div iframe').attr('src',$this.data('src'));
        });
        //1.2  对于逻辑栋点击高亮
        $(document).on('click', '.submenu a.bdc-last-child', function () {
            var $this = $(this);
            $('.submenu a').removeClass('active');
            $this.addClass('active');
            $('.accordion .bdc-invented-li').removeClass('bdc-this-li');
            $('.content-div iframe').attr('src',$(this).parent('.bdc-inner-li').data('src'));
        });

        //2. 点击侧边栏收缩按钮
        var $asideClose = $('.bdc-aside-close'),
            $asideOpen = $('.bdc-aside-open'),
            $zoom = $('.bdc-aside-zoom'),
            $menuAside = $('.bdc-menu-aside'),
            $container = $('.bdc-container'),
            $zoomLine = $('#asideLine');
        $asideClose.on('click',function () {
            containerLeft = 20;
            $(this).toggleClass('bdc-hide');
            $asideOpen.toggleClass('bdc-hide');
            $zoom.animate({'left':'-5px'},100);
            $menuAside.animate({'left': -($menuAside.width() + 40) + 'px'},100);
            $zoomLine.animate({'left': '-24px'},100);
            $container.animate({'padding-left':'20px'},100);
        });
        $asideOpen.on('click',function () {
            containerLeft = 300;
            $(this).toggleClass('bdc-hide');
            $asideClose.toggleClass('bdc-hide');
            $zoom.animate({'left': $menuAside.width() - 1 + 'px'},100);
            $menuAside.animate({'left':'0'},100);
            $zoomLine.animate({'left': $menuAside.width() - 4 + 'px'},100);
            $container.animate({'padding-left':'300px'},100);
        });
        //3. 查询条件获取焦点
        $('.bdc-search').on('focus',function () {
            $(this).addClass('bdc-search-click');
            $('#aside .bdc-search-box .layui-icon-search').css('font-size','14px');
            $('.bdc-search-box .layui-icon-close').css('font-size','14px');
        }).on('blur',function () {
            $(this).removeClass('bdc-search-click');
            $('#aside .bdc-search-box .layui-icon-search').css('font-size','12px');
            $('.bdc-search-box .layui-icon-close').css('font-size','12px');
        });
        //3.1 点击删除
        $('.bdc-search-box .layui-icon-close').on('click',function () {
            console.log('删除操作');
        });
        //4.监听tab切换
        element.on('tab(menuChangeFilter)', function(data){
            $('.bdc-menu-tab .border-up-empty').addClass('bdc-hide');
            $(this).children('.border-up-empty').removeClass('bdc-hide');
        });

        //---------------------查询条件开始-------------------------------------------------------
        //按enter键
        $('.bdc-search').bind('keyup', function(event) {
            if (event.keyCode == "13") {
                $('#accordion').addClass('bdc-hide');
                $('#searchMenu').removeClass('bdc-hide');
                //回车执行查询
                var asideData = [
                    {
                        "lsgxModelDTO": {
                            "id": "f16978d18fc8402880af6976a17b020c",
                            "yid": null,
                            "qllx": 4,
                            "cjsj": "2019-03-13 20:56:53",
                            "qlxx": null,
                            "djlx": 200,
                            "bdcqzh": "null",
                            "qszt": 2,
                            "dbr": "admin",
                            "djsj": "2019-03-14 05:31:22"
                        },
                        "lsgxXzqlModelDTO": {
                            "dyLsgxModel": [{
                                "id": "f169806540f64028833c697bf90901f9",
                                "yid": "f16978d18fc8402880af6976a17b020c",
                                "qllx": 37,
                                "cjsj": "2019-03-15 16:09:31",
                                "qlxx": null,
                                "djlx": 900,
                                "bdcqzh": "不动产权201900000",
                                "qszt": 1,
                                "dbr": "admin",
                                "djsj": "2019-03-15 16:35:16"
                            }, {
                                "id": "f169846c740c4028833c698433f6005e",
                                "yid": "f16978d18fc8402880af6976a17b020c",
                                "qllx": 37,
                                "cjsj": "2019-03-16 10:55:14",
                                "qlxx": null,
                                "djlx": 900,
                                "bdcqzh": "苏(2019)海门市不动产证明第0000037号",
                                "qszt": 1,
                                "dbr": "admin",
                                "djsj": "2019-03-16 11:04:21"
                            }],
                            "cfLsgxModel": [{
                                "id": "f169a019779b402c8093699f02550413",
                                "yid": "f169846c740c4028833c698433f6005e",
                                "qllx": 98,
                                "cjsj": "2019-03-21 19:53:39",
                                "qlxx": null,
                                "djlx": 800,
                                "bdcqzh": "不动产权201900001",
                                "qszt": 0,
                                "dbr": null,
                                "djsj": null
                            }],
                            "yyLsgxModel": [],
                            "ygLsgxModel": [],
                            "dyiLsgxModel": []
                        }
                    }, {
                        "lsgxModelDTO": {
                            "id": "f16938b5d2e0402880af69382cff0029",
                            "yid": null,
                            "qllx": 4,
                            "cjsj": "2019-03-02 01:59:33",
                            "qlxx": null,
                            "djlx": 200,
                            "bdcqzh": "苏(2019)海门市不动产权第0000029号",
                            "qszt": 2,
                            "dbr": null,
                            "djsj": null
                        },
                        "lsgxXzqlModelDTO": {
                            "dyLsgxModel": [{
                                "id": "f1695197d3b0402880af695197d30001",
                                "yid": "f16938b5d2e0402880af69382cff0029",
                                "qllx": 37,
                                "cjsj": "2019-03-06 21:57:19",
                                "qlxx": null,
                                "djlx": 900,
                                "bdcqzh": "苏(2019)海门市不动产证明第0000002号",
                                "qszt": 1,
                                "dbr": null,
                                "djsj": null
                            }, {
                                "id": "f16952196458402880af695197d3003f",
                                "yid": "f16938b5d2e0402880af69382cff0029",
                                "qllx": 37,
                                "cjsj": "2019-03-07 00:19:18",
                                "qlxx": null,
                                "djlx": 900,
                                "bdcqzh": "苏(2019)海门市不动产证明第0000003号",
                                "qszt": 2,
                                "dbr": "admin",
                                "djsj": "2019-03-06 16:32:45"
                            }, {
                                "id": "f169847f5de94028833c698433f6008c",
                                "yid": "f16952196458402880af695197d3003f",
                                "qllx": 37,
                                "cjsj": "2019-03-16 11:15:52",
                                "qlxx": null,
                                "djlx": 900,
                                "bdcqzh": "苏(2019)海门市不动产证明第0000039号",
                                "qszt": 1,
                                "dbr": "admin",
                                "djsj": "2019-03-16 11:23:07"
                            }],
                            "cfLsgxModel": [],
                            "yyLsgxModel": [],
                            "ygLsgxModel": [],
                            "dyiLsgxModel": []
                        }
                    }, {
                        "lsgxModelDTO": {
                            "id": "f169382cffd7402880af69382cff0001",
                            "yid": null,
                            "qllx": 4,
                            "cjsj": "2019-03-01 23:30:10",
                            "qlxx": null,
                            "djlx": 900,
                            "bdcqzh": "苏(2019)海门市不动产权第0000030号",
                            "qszt": 1,
                            "dbr": null,
                            "djsj": null
                        },
                        "lsgxXzqlModelDTO": null
                    }, {
                        "lsgxModelDTO": {
                            "id": "f1692b7bffdb402880af692a1d820026",
                            "yid": null,
                            "qllx": 4,
                            "cjsj": "2019-02-27 04:23:57",
                            "qlxx": null,
                            "djlx": 100,
                            "bdcqzh": "苏(2019)海门市不动产权第0000002号",
                            "qszt": 1,
                            "dbr": null,
                            "djsj": null
                        },
                        "lsgxXzqlModelDTO": {
                            "dyLsgxModel": [{
                                "id": "f169529c2139402883f0695196000074",
                                "yid": "f1692b7bffdb402880af692a1d820026",
                                "qllx": 37,
                                "cjsj": "2019-02-28 18:37:24",
                                "qlxx": null,
                                "djlx": 900,
                                "bdcqzh": "不动产权201900003",
                                "qszt": 0,
                                "dbr": null,
                                "djsj": null
                            }, {
                                "id": "f1694752e21a402883f0694752e20001",
                                "yid": "f1692b7bffdb402880af692a1d820026",
                                "qllx": 37,
                                "cjsj": "2019-03-04 14:08:17",
                                "qlxx": null,
                                "djlx": 900,
                                "bdcqzh": "不动产权201900004",
                                "qszt": 0,
                                "dbr": null,
                                "djsj": null
                            }, {
                                "id": "f16947a10fc6402880af694736ed0029",
                                "yid": "f1692b7bffdb402880af692a1d820026",
                                "qllx": 37,
                                "cjsj": "2019-03-04 23:29:47",
                                "qlxx": null,
                                "djlx": 900,
                                "bdcqzh": null,
                                "qszt": 1,
                                "dbr": null,
                                "djsj": null
                            }, {
                                "id": "f16947e51792402880af694736ed0036",
                                "yid": "f1692b7bffdb402880af692a1d820026",
                                "qllx": 37,
                                "cjsj": "2019-03-05 00:44:40",
                                "qlxx": null,
                                "djlx": 900,
                                "bdcqzh": "不动产权201900005",
                                "qszt": 1,
                                "dbr": null,
                                "djsj": null
                            }, {
                                "id": "f169480d26e2402880af694736ed004a",
                                "yid": "f1692b7bffdb402880af692a1d820026",
                                "qllx": 37,
                                "cjsj": "2019-03-05 01:29:43",
                                "qlxx": null,
                                "djlx": 900,
                                "bdcqzh": "不动产权201900006",
                                "qszt": 1,
                                "dbr": null,
                                "djsj": null
                            }, {
                                "id": "f1694d721d5e402883f0694d2a700006",
                                "yid": "f1692b7bffdb402880af692a1d820026",
                                "qllx": 37,
                                "cjsj": "2019-03-05 13:48:56",
                                "qlxx": null,
                                "djlx": 900,
                                "bdcqzh": "不动产权201900007",
                                "qszt": 0,
                                "dbr": null,
                                "djsj": null
                            }, {
                                "id": "f1694c674943402883f0694c67490001",
                                "yid": "f1692b7bffdb402880af692a1d820026",
                                "qllx": 37,
                                "cjsj": "2019-03-05 13:50:58",
                                "qlxx": null,
                                "djlx": 900,
                                "bdcqzh": "不动产权201900010",
                                "qszt": 0,
                                "dbr": null,
                                "djsj": null
                            }, {
                                "id": "f169561b4596402880af695197d301b5",
                                "yid": "f1692b7bffdb402880af692a1d820026",
                                "qllx": 37,
                                "cjsj": "2019-03-07 11:03:00",
                                "qlxx": null,
                                "djlx": 900,
                                "bdcqzh": "不动产权201900009",
                                "qszt": 0,
                                "dbr": null,
                                "djsj": null
                            }, {
                                "id": "f16956277c3c402880af695197d301ba",
                                "yid": "f1692b7bffdb402880af692a1d820026",
                                "qllx": 37,
                                "cjsj": "2019-03-07 11:15:21",
                                "qlxx": null,
                                "djlx": 900,
                                "bdcqzh": "不动产权201901000",
                                "qszt": 0,
                                "dbr": null,
                                "djsj": null
                            }, {
                                "id": "f1695d44b079402880af695b45370064",
                                "yid": "f1692b7bffdb402880af692a1d820026",
                                "qllx": 37,
                                "cjsj": "2019-03-08 20:20:42",
                                "qlxx": null,
                                "djlx": 900,
                                "bdcqzh": "不动产权2019008954",
                                "qszt": 0,
                                "dbr": null,
                                "djsj": null
                            }, {
                                "id": "f16978ee5be4402880af6976a17b025c",
                                "yid": "f1692b7bffdb402880af692a1d820026",
                                "qllx": 37,
                                "cjsj": "2019-03-13 21:27:13",
                                "qlxx": null,
                                "djlx": 900,
                                "bdcqzh": "不动产权2019008850",
                                "qszt": 0,
                                "dbr": null,
                                "djsj": null
                            }, {
                                "id": "f1697f4886084028833c697bf90900eb",
                                "yid": "f1692b7bffdb402880af692a1d820026",
                                "qllx": 37,
                                "cjsj": "2019-03-15 10:57:36",
                                "qlxx": null,
                                "djlx": 900,
                                "bdcqzh": "不动产权2019850000",
                                "qszt": 0,
                                "dbr": null,
                                "djsj": null
                            }, {
                                "id": "f1698020ac694028833c697bf9090184",
                                "yid": "f1692b7bffdb402880af692a1d820026",
                                "qllx": 37,
                                "cjsj": "2019-03-15 14:53:45",
                                "qlxx": null,
                                "djlx": 900,
                                "bdcqzh": "不动产权201900008854",
                                "qszt": 0,
                                "dbr": null,
                                "djsj": null
                            }, {
                                "id": "f169809c4ecb4028833c697bf909021f",
                                "yid": "f1692b7bffdb402880af692a1d820026",
                                "qllx": 37,
                                "cjsj": "2019-03-15 16:36:58",
                                "qlxx": null,
                                "djlx": 900,
                                "bdcqzh": "不动产权2019000484",
                                "qszt": 0,
                                "dbr": null,
                                "djsj": null
                            }, {
                                "id": "f169d81815fc4028b22569d8136d000f",
                                "yid": "f1692b7bffdb402880af692a1d820026",
                                "qllx": 37,
                                "cjsj": "2019-04-01 16:50:49",
                                "qlxx": null,
                                "djlx": 900,
                                "bdcqzh": "不动产权201900048",
                                "qszt": 0,
                                "dbr": null,
                                "djsj": null
                            }, {
                                "id": "f1696cc47a89402883f6696cc4780003",
                                "yid": "f1696cc478dd402883f6696cc4780001",
                                "qllx": 37,
                                "cjsj": "2019-03-11 20:40:04",
                                "qlxx": null,
                                "djlx": 900,
                                "bdcqzh": "不动产权2019058500",
                                "qszt": 0,
                                "dbr": null,
                                "djsj": null
                            }, {
                                "id": "f169806540f64028833c697bf90901f9",
                                "yid": "f16978d18fc8402880af6976a17b020c",
                                "qllx": 37,
                                "cjsj": "2019-03-15 16:09:31",
                                "qlxx": null,
                                "djlx": 900,
                                "bdcqzh": "不动产权201900088",
                                "qszt": 1,
                                "dbr": "admin",
                                "djsj": "2019-03-15 16:35:16"
                            }, {
                                "id": "f169846c740c4028833c698433f6005e",
                                "yid": "f16978d18fc8402880af6976a17b020c",
                                "qllx": 37,
                                "cjsj": "2019-03-16 10:55:14",
                                "qlxx": null,
                                "djlx": 900,
                                "bdcqzh": "苏(2019)海门市不动产证明第0000037号",
                                "qszt": 1,
                                "dbr": "admin",
                                "djsj": "2019-03-16 11:04:21"
                            }, {
                                "id": "f169843a4e5a4028833c698433f60013",
                                "yid": "f169843a4e2b4028833c698433f60011",
                                "qllx": 37,
                                "cjsj": "2019-03-16 10:00:07",
                                "qlxx": null,
                                "djlx": 900,
                                "bdcqzh": "不动产权201900058",
                                "qszt": 0,
                                "dbr": null,
                                "djsj": null
                            }, {
                                "id": "f169a350128b402c809369a31ce20113",
                                "yid": "f169a350127b402c809369a31ce20111",
                                "qllx": 37,
                                "cjsj": "2019-03-22 10:52:09",
                                "qlxx": null,
                                "djlx": 900,
                                "bdcqzh": "不动产权2019000000",
                                "qszt": 0,
                                "dbr": null,
                                "djsj": null
                            }],
                            "cfLsgxModel": [{
                                "id": "f169469d5b1a402880af693d4fe10046",
                                "yid": "f1692b7bffdb402880af692a1d820026",
                                "qllx": 98,
                                "cjsj": "2019-03-04 18:48:11",
                                "qlxx": null,
                                "djlx": 800,
                                "bdcqzh": "不动产权2019000011",
                                "qszt": 0,
                                "dbr": null,
                                "djsj": null
                            }, {
                                "id": "f16946b126fa402880af693d4fe10052",
                                "yid": "f1692b7bffdb402880af692a1d820026",
                                "qllx": 98,
                                "cjsj": "2019-03-04 19:09:55",
                                "qlxx": null,
                                "djlx": 800,
                                "bdcqzh": "不动产权201900005",
                                "qszt": 0,
                                "dbr": null,
                                "djsj": null
                            }, {
                                "id": "f1697fdf5e3a402883f6697f4f8c0009",
                                "yid": "f1692b7bffdb402880af692a1d820026",
                                "qllx": 98,
                                "cjsj": "2019-03-15 13:46:09",
                                "qlxx": null,
                                "djlx": 800,
                                "bdcqzh": "不动产权2019000015",
                                "qszt": 0,
                                "dbr": null,
                                "djsj": null
                            }, {
                                "id": "f169e12300f64028b23569e101740c35",
                                "yid": "f1692b7bffdb402880af692a1d820026",
                                "qllx": 98,
                                "cjsj": "2019-04-03 10:59:21",
                                "qlxx": null,
                                "djlx": 800,
                                "bdcqzh": "不动产权2019000158",
                                "qszt": 0,
                                "dbr": null,
                                "djsj": null
                            }, {
                                "id": "f169a019779b402c8093699f02550413",
                                "yid": "f169846c740c4028833c698433f6005e",
                                "qllx": 98,
                                "cjsj": "2019-03-21 19:53:39",
                                "qlxx": null,
                                "djlx": 800,
                                "bdcqzh": "不动产权20190000045",
                                "qszt": 0,
                                "dbr": null,
                                "djsj": null
                            }],
                            "yyLsgxModel": [],
                            "ygLsgxModel": [{
                                "id": "f1698110e0f44028833c698110e00001",
                                "yid": "f1692b7bffdb402880af692a1d820026",
                                "qllx": 96,
                                "cjsj": "2019-03-15 19:16:04",
                                "qlxx": null,
                                "djlx": 700,
                                "bdcqzh": "不动产权20190000055",
                                "qszt": 0,
                                "dbr": null,
                                "djsj": null
                            }, {
                                "id": "f1698110e1234028833c698110e00003",
                                "yid": "f1698110e0f44028833c698110e00001",
                                "qllx": 96,
                                "cjsj": "2019-03-15 19:16:04",
                                "qlxx": null,
                                "djlx": 700,
                                "bdcqzh": "不动产权2019000580",
                                "qszt": 0,
                                "dbr": null,
                                "djsj": null
                            }],
                            "dyiLsgxModel": []
                        }
                    }, {
                        "lsgxModelDTO": {
                            "id": "f16935051fb2402880af69344fe6005d",
                            "yid": null,
                            "qllx": 4,
                            "cjsj": "2019-03-01 00:53:00",
                            "qlxx": null,
                            "djlx": 200,
                            "bdcqzh": "苏(2019)海门市不动产权第0000129号",
                            "qszt": 0,
                            "dbr": null,
                            "djsj": null
                        },
                        "lsgxXzqlModelDTO": null
                    }];
                asideData.forEach(function (v) {
                    v.childList = [];
                    if(v.lsgxXzqlModelDTO != null){
                        if(v.lsgxXzqlModelDTO.dyLsgxModel.length != 0){
                            v.childList.push({
                                name: '抵押信息',
                                childTree: v.lsgxXzqlModelDTO.dyLsgxModel
                            });
                        }
                        if(v.lsgxXzqlModelDTO.cfLsgxModel.length != 0){
                            v.childList.push({
                                name: '查封信息',
                                childTree: v.lsgxXzqlModelDTO.cfLsgxModel
                            });
                        }
                        if(v.lsgxXzqlModelDTO.yyLsgxModel.length != 0){
                            v.childList.push({
                                name: '异议信息',
                                childTree: v.lsgxXzqlModelDTO.yyLsgxModel
                            });
                        }
                        if(v.lsgxXzqlModelDTO.ygLsgxModel.length != 0){
                            v.childList.push({
                                name: '预告信息',
                                childTree: v.lsgxXzqlModelDTO.ygLsgxModel
                            });
                        }
                        if(v.lsgxXzqlModelDTO.dyiLsgxModel.length != 0){
                            v.childList.push({
                                name: '地役信息',
                                childTree: v.lsgxXzqlModelDTO.dyiLsgxModel
                            });
                        }
                    }
                });
                var getAsideData = JSON.parse(JSON.stringify(asideData));
                console.log(asideData);
                var getSearchValue = $('.bdc-search').val();
                for(var i = getAsideData.length-1; i >= 0; i--){
                    //一级没有
                    if(getAsideData[i].lsgxModelDTO.bdcqzh != null){
                        if(getAsideData[i].lsgxModelDTO.bdcqzh.indexOf(getSearchValue) == -1){
                            //二级存在
                            if(getAsideData[i].childList.length != 0){
                                for(var index = getAsideData[i].childList.length-1; index >= 0; index--){
                                    //二级没有
                                    if(getAsideData[i].childList[index].name.indexOf(getSearchValue) == -1){
                                        //三级存在
                                        if(getAsideData[i].childList[index].childTree.length != 0){
                                            for(var thirdIndex = getAsideData[i].childList[index].childTree.length-1; thirdIndex >= 0; thirdIndex--){
                                                //三级没有
                                                if(getAsideData[i].childList[index].childTree[thirdIndex].bdcqzh != null){
                                                    if(getAsideData[i].childList[index].childTree[thirdIndex].bdcqzh.indexOf(getSearchValue) == -1){
                                                        getAsideData[i].childList[index].childTree.splice(thirdIndex, 1);
                                                    }
                                                }else {
                                                    getAsideData[i].childList[index].childTree.splice(thirdIndex, 1);
                                                }
                                            }
                                            if(getAsideData[i].childList[index].childTree.length == 0){
                                                getAsideData[i].childList.splice(index,1);
                                            }
                                        }else {
                                            //三级不存在
                                            getAsideData[i].childList.splice(index, 1);
                                        }
                                    }
                                }
                                if(getAsideData[i].childList == 0){
                                    getAsideData.splice(i,1);
                                }
                            }else {
                                //二级不存在
                                getAsideData.splice(i,1);
                            }
                        }
                    }else {
                        getAsideData.splice(i,1);
                    }

                }
                var getSearchAsideTpl = searchMenuTpl.innerHTML
                    ,asideSearchView = document.getElementById('searchMenu');
                laytpl(getSearchAsideTpl).render(getAsideData, function(html){
                    asideSearchView.innerHTML = html;
                });
                //默认展开第一个
                $('#searchMenu .bdc-outer-li:first-child').addClass('open').find('.submenu').css('display','block');
                $('#searchMenu .bdc-outer-li:first-child .submenu .bdc-inner-li:first-child').addClass('open');
            }
        });

        //---------------------查询条件结束-------------------------------------------------------

        //绑定需要拖拽改变大小的元素对象
        var oBox = document.getElementById('asideBox');
        var oLine = document.getElementById('asideLine');
        oLine.onmousedown = function(ev){
            document.onmousemove=function(ev){
                var iEvent = ev||event;
                oBox.style.width =  iEvent.clientX + 'px';
                oLine.style.left = iEvent.clientX - 4 + 'px';
                $zoom.css('left',iEvent.clientX - 1);
                if(oBox.offsetWidth <= 280){
                    oBox.style.width = '280px';
                    oLine.style.left = oBox.offsetWidth - 4 + 'px';
                    $zoom.css('left','280px');
                }

            };
            document.onmouseup=function(){
                document.onmousedown=null;
                document.onmousemove=null;
            };
            return false;
        };
    });
});