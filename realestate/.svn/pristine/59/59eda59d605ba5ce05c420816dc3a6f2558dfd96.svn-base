// 查询参数
var gzlslid= $.getUrlParam("processInsId");
//目录
var menuList=[];
//未查询接口集合
var wcxjkArr =[];
//查询成功接口集合
var successjkArr =[];
//查询失败接口集合
var failjkArr =[];
layui.use(['form','jquery','laydate','element','layer','table'],function () {
    var $ = layui.jquery,
        laytpl = layui.laytpl,
        element = layui.element;

    $(function () {
        loadMenu();

        //监听 一级 第一次点击
        $(document).on('click', '.bdc-outer-li', function(){
            $(this).find('a').filter(':first').addClass('active');
            $(this).siblings('.submenu').find('.link').click;
        });

        //监听 二级菜单点击
        $(document).on('click', '.bdc-outer-li .bdc-inner-li', function(e){
            e.stopPropagation();
            $('.accordion .bdc-invented-li a').removeClass('active');
        });

        //1. 侧边栏点击效果
        //1.1  菜单点击展示其下内容
        $(document).on('click', '.link', function () {
            var $this = $(this);
            $this.parent().toggleClass('open');

            $(this).siblings('.submenu').slideToggle(function () {
            });
            if($this.data('url')){
                openGxlc($this.data('id'),$this.data('url'));
            }
        });
        //1.2  对于逻辑栋点击高亮, 点击查询接口并改变状态
        $(document).on('click', '.submenu a.bdc-last-child', function () {
            var $this = $(this);
            $('.submenu a').removeClass('active');
            $this.addClass('active');
            $('.accordion .bdc-invented-li').removeClass('bdc-this-li');
            $('.content-div iframe').attr('src',$(this).parent('.bdc-inner-li').data('src'));
            if($this.data('url')){
                openGxlc($this.data('id'),$this.data('url'));
            }
        });
        //1.3 点击箭头
        $('.accordion').on('click','.bdc-outer-li i',function (event) {
            event.stopPropagation();
            var $this = $(this).parent();
            $this.parent().toggleClass('open');
            $this.siblings('.submenu').slideToggle();
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
            $menuAside.animate({'left': -($menuAside.width() + 20) + 'px'},100);
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

    function loadMenu() {
        // addModel();
        $.ajax({
            url:"/realestate-inquiry-ui/rest/v1.0/gx/shij/ml/" + gzlslid,
            type: "POST",
            contentType: 'application/json',
            success: function (data) {
                console.log('data:');
                console.log(data);
                removeModal();
                if(!isNullOrEmpty(data) && data.length>0){
                    // addSecondMenu(data);
                    // generateMenu(menuList);
                }
            },
            error: function (xhr, status, error) {
                removeModal();
                delAjaxErrorMsg(xhr)
            }
        });

        var data = [
            {
                name: 'bs',
                mc: '民政部-婚姻登记信息查询',
                url: null,
                children: [
                    {
                        children: null,
                        mc: '婚姻登记信息查询',
                        name: 'hydjxx',
                        url: '/realestate-inquiry-ui/view/bjjk/hydjxx.html'
                    }
                ]
            },
            {
                name: 'bs',
                mc: '社会组织信息查询',
                url: null,
                children: [
                    {
                        children: null,
                        mc: '社会组织信息查询',
                        name: 'shzzxx',
                        url: '/realestate-inquiry-ui/view/bjjk/shzzxx.html'
                    }
                ]
            },
            {
                name: 'bs',
                mc: '银保监会-金融许可证查询',
                url: null,
                children: [
                    {
                        children: null,
                        mc: '金融许可证查询',
                        name: 'jrxkz',
                        url: '/realestate-inquiry-ui/view/bjjk/jrxkz.html'
                    }
                ]
            },
            {
                name: 'bs',
                mc: '中编办-中编办查询',
                url: null,
                children: [
                    {
                        children: null,
                        mc: '中编办查询',
                        name: 'zbb',
                        url: '/realestate-inquiry-ui/view/bjjk/zbb.html'
                    }
                ]
            },
            {
                name: 'bs',
                mc: '司法判决信息查询',
                url: null,
                children: [
                    {
                        children: null,
                        mc: '司法判决信息查询',
                        name: 'sfpj',
                        url: '/realestate-inquiry-ui/view/bjjk/sfpj.html'
                    }
                ]
            },
            {
                name: 'bs',
                mc: '法院回执信息',
                url: null,
                children: [
                    {
                        children: null,
                        mc: '法院回执信息查询',
                        name: 'jrxkzCx',
                        url: '/realestate-inquiry-ui/view/bjjk/fyhzxx.html'
                    }
                ]
            },
            {
                name: 'bs',
                mc: '公安身份核查',
                url: null,
                children: [
                    {
                        children: null,
                        mc: '公安身份核查',
                        name: 'gasfhc',
                        url: '/realestate-inquiry-ui/view/bjjk/gasfhc.html'
                    }
                ]
            },
            {
                name: 'bs',
                mc: '卫健委-出生医学证明',
                url: null,
                children: [
                    {
                        children: null,
                        mc: '出生证明查询',
                        name: 'cszm',
                        url: '/realestate-inquiry-ui/view/bjjk/cszm.html'
                    }
                ]
            },
            {
                name: 'bs',
                mc: '卫健委-死亡证明',
                url: null,
                children: [
                    {
                        children: null,
                        mc: '死亡证明',
                        name: 'swzm',
                        url: '/realestate-inquiry-ui/view/bjjk/swzm.html'
                    }
                ]
            },
            {
                name: 'bs',
                mc: '部级接口-地名地址查询',
                url: null,
                children: [
                    {
                        children: null,
                        mc: '地名地址查询',
                        name: 'dmxxcx',
                        url: '/realestate-inquiry-ui/view/bjjk/dmxxcx.html'
                    }
                ]
            },
            {
                name: 'bs',
                mc: '自定义查询-收养信息查询',
                url: null,
                children: [
                    {
                        children: null,
                        mc: '收养信息查询',
                        name: 'sydjxx',
                        url: '/realestate-inquiry-ui/view/dtcx/commonCx.html?cxdh=sydjxx'
                    }
                ]
            },
            {
                name: 'bs',
                mc: '自定义查询-火化信息查询',
                url: null,
                children: [
                    {
                        children: null,
                        mc: '火化信息查询',
                        name: 'hhxx',
                        url: '/realestate-inquiry-ui/view/dtcx/commonCx.html?cxdh=hhxx'
                    }
                ]
            },
        ];

        addSecondMenu(data);
        generateMenu(menuList);
    }

    // 添加二级目录
    function addSecondMenu(data) {
        //默认全部未查询
        for(var i=0; i<data.length; i++){
            if(isNotBlank(data[i])){
                for(var j=0; j<data[i].children.length; j++){
                    data[i].children[j].class = data[i].children[j].name;
                    wcxjkArr.push(data[i].children[j].class);
                }
            }
            // data[i].children = [];
            // addUrl(data[i]);
            menuList.push(data[i]);
        }
        $("#wcxNum").text(wcxjkArr.length);
    }

    // 根据名称对应二级名称和url
    // class和dealCxjgxx(结果，接口名)中接口名一致，wcxjkArr.push的值要和接口名一致
    function addUrl(list) {
        switch (list.name) {
            //金融许可证信息
            case "jrxkz":
                list.mc = "金融许可证查询";
                list.children.push(
                    {
                        "mc": "市级-金融许可证信息",
                        "url": "./lcshijJrxkzxx.html",
                        "class":"shijjrxkz"
                    });
                wcxjkArr.push("shijjrxkz");
                list.children.push(
                    {
                        "mc": "省级-金融许可证查询",
                        "url": "../../view/gxjklc/lcbjJrxkz.html",
                        "class":"bjjrxkz"
                    });
                wcxjkArr.push("bjjrxkz");
                return list;
                break;
            //综合法人库-司法判决信息-案件当事人信息
            case "sfpjajxx":
                list.mc = "司法判决信息查询";
                list.children.push(
                    {
                        "mc": "市级-司法判决信息",
                        "url": "./lcshijSfpjxx.html",
                        "class":"shijzhfrksfpjxxajxx"
                    });
                wcxjkArr.push("shijzhfrksfpjxxajxx");
                list.children.push(
                    {
                        "mc": "省级-司法判决信息",
                        "url": "../../view/gxjklc/lcbjSfpjxxsq.html",
                        "class":"bjsfpjxx"
                    });
                wcxjkArr.push("bjsfpjxx");
                return list;
                break;
            //综合法人库-事业单位基本信息
            case "sydwjbxx":
                list.mc = "事业单位基本信息查询";
                list.children.push(
                    {
                        "mc": "市级-事业单位基本信息查询",
                        "url": "./lcshijSydwjbxx.html",
                        "class":"shijzhfrksydwjbxx"
                    });
                wcxjkArr.push("shijzhfrksydwjbxx");
                return list;
                break;
            //委托权公证书信息
            case "wtqgzsxx":
                list.mc = "委托权公证书信息查询";
                list.children.push(
                    {
                        "mc": "市级-委托权公证书信息",
                        "url": "./lcshijWtqgzsxx.html",
                        "class":"shijwtqgzsxx"
                    });
                wcxjkArr.push("shijwtqgzsxx");
                return list;
                break;
            //继承权公证书信息
            case "jcqgzsxx":
                list.mc = "继承权公证书信息查询";
                list.children.push(
                    {
                        "mc": "市级-继承权公证书信息",
                        "url": "./lcshijJcqgzsxx.html",
                        "class":"shijjcqgzsxx"
                    });
                wcxjkArr.push("shijjcqgzsxx");
                return list;
                break;
            //民政部_涉外婚姻登记信息核验(单人)接口
            case "swhydjxxdrhy":
                list.mc = "涉外婚姻登记信息核验(单人)查询";
                list.children.push(
                    {
                        "mc": "市级-涉外婚姻登记信息核验(单人)",
                        "url": "./lcshijSwhydjxxdrhy.html",
                        "class":"shijswhydjxxdrhy"
                    });
                wcxjkArr.push("shijswhydjxxdrhy");
                list.children.push(
                    {
                        "mc": "省级-婚姻登记信息核验个人查询",
                        "url": "../../view/gxjklc/lcsjHyxxhygr.html",
                        "class":"sjhyxxhygr"
                    });
                wcxjkArr.push("sjhyxxhygr");
                return list;
                break;
            //民政部_社会团体名称变更登记记录查询接口
            case "shttmcbgdjjlcx":
                list.mc = "社会团体名称变更登记记录查询";
                list.children.push(
                    {
                        "mc": "市级-社会团体名称变更查询",
                        "url": "./lcshijShttmcbgdjjlcx.html",
                        "class":"shijshttmcbgdjjlcx"
                    });
                wcxjkArr.push("shijshttmcbgdjjlcx");
                return list;
                break;
            //民政部_收养登记证信息（国内）查询接口
            case "sydjzxxcx":
                list.mc = "收养登记证信息（国内）查询";
                list.children.push(
                    {
                        "mc": "市级-收养登记证查询",
                        "url": "./lcshijSydjzxxgncx.html",
                        "class":"shijsydjzxxcx"
                    });
                wcxjkArr.push("shijsydjzxxcx");
                return list;
                break;
            //民政部_民办非企业单位登记证书接口
            case "mbfqydwdjzscx":
                list.mc = "民办非企业单位登记证书查询";
                list.children.push(
                    {
                        "mc": "市级-民办非企业单位登记证书查询",
                        "url": "./lcshijMbfqydwdjzs.html",
                        "class":"shijmbfqydwdjzscx"
                    });
                wcxjkArr.push("shijmbfqydwdjzscx");
                list.children.push(
                    {
                        "mc": "省级-民办非企业单位登记证书查询",
                        "url": "../../view/gxjklc/lcsjMbfqydjzs.html",
                        "class":"sjmbfqydjzs"
                    });
                wcxjkArr.push("sjmbfqydjzs");
                return list;
                break;
            //民政部基金会法人登记证书接口
            case "jjhfrdjzscx":
                list.mc = "基金会法人登记证书查询";
                list.children.push(
                    {
                        "mc": "市级-基金会法人登记证书查询",
                        "url": "./lcshijJjhfrdjzs.html",
                        "class":"shijjjhfrdjzscx"
                    });
                wcxjkArr.push("shijjjhfrdjzscx");
                list.children.push(
                    {
                        "mc": "省级-基金会法人登记证书查询",
                        "url": "../../view/gxjklc/lcsjJjhfrdjzs.html",
                        "class":"sjjjhfrdjzs"
                    });
                wcxjkArr.push("sjjjhfrdjzs");
                return list;
                break;
            //综合人口库-户籍人口信息接口
            case "hjrkxx":
                list.mc = "户籍人口信息查询";
                list.children.push(
                    {
                        "mc": "市级-户籍人口信息查询",
                        "url": "./lcshijHjrkxx.html",
                        "class":"shijzhrkkhjrkxx"
                    });
                wcxjkArr.push("shijzhrkkhjrkxx");
                return list;
                break;
            //国家公安部—人口库基准信息查询接口
            case "rkkjzxxcx":
                list.mc = "人口库基准信息查询";
                list.children.push(
                    {
                        "mc": "市级-人口库基准信息查询",
                        "url": "./lcshijRkkjzxxcx.html",
                        "class":"shijrkkjzxxcx"
                    });
                wcxjkArr.push("shijrkkjzxxcx");
                return list;
                break;
            //综合人口库-户口迁移证信息
            case "hkqyzxx":
                list.mc = "户口迁移证信息查询";
                list.children.push(
                    {
                        "mc": "市级-户口迁移证信息查询",
                        "url": "./lcshijHkqyzxx.html",
                        "class":"shijzhrkkhkqyzxx"
                    });
                wcxjkArr.push("shijzhrkkhkqyzxx");
                return list;
                break;
            //综合法人库-科技小巨人企业认定信息
            case "kjxjrqyrdxx":
                list.mc = "科技小巨人企业认定信息查询";
                list.children.push(
                    {
                        "mc": "市级-科技小巨人企业认定信息查询",
                        "url": "./lcshijKjxjrqyrdxx.html",
                        "class":"shijzhfrkkjxjrqyrdxx"
                    });
                wcxjkArr.push("shijzhfrkkjxjrqyrdxx");
                return list;
                break;
            //民政部涉外婚姻登记信息查询接口
            case "swhydjxxcx":
                list.mc = "涉外婚姻登记信息查询";
                list.children.push(
                    {
                        "mc": "市级-涉外婚姻登记信息查询",
                        "url": "./lcshijSwhydjxxcx.html",
                        "class":"shijswhydjxxcx"
                    });
                wcxjkArr.push("shijswhydjxxcx");
                list.children.push(
                    {
                        "mc": "省级-婚姻登记信息查询",
                        "url": "../../view/gxjklc/lcbjHydjxx.html",
                        "class":"bjhydjxx"
                    });
                wcxjkArr.push("bjhydjxx");
                return list;
                break;
            //民政部_涉外婚姻登记信息核验（双方）接口
            case "swhydjxxsfhy":
                list.mc = "涉外婚姻登记信息核验（双方）查询";
                list.children.push(
                    {
                        "mc": "市级-涉外婚姻登记核验（双方）查询",
                        "url": "./lcshijSwhydjxxhysf.html",
                        "class":"shijswhydjxxsfhy"
                    });
                wcxjkArr.push("shijswhydjxxsfhy");
                list.children.push(
                    {
                        "mc": "省级-婚姻登记信息核验双方查询",
                        "url": "../../view/gxjklc/lcsjHyxxhysf.html",
                        "class":"sjhyxxhysf"
                    });
                wcxjkArr.push("sjhyxxhysf");
                return list;
                break;
            //市自然规划局—建设工程规划许可证接口
            case "yztgcxk":
                list.mc = "建设工程规划许可证查询";
                list.children.push(
                    {
                        "mc": "市级-建设工程规划许可证查询",
                        "url": "./lcshijJsgcghxkz.html",
                        "class":"shijyztgcxk"
                    });
                wcxjkArr.push("shijyztgcxk");
                return list;
                break;
            //综合法人库-全市社会组织登记信息
            case "qsshzzdjxx":
                list.mc = "全市社会组织登记信息查询";
                list.children.push(
                    {
                        "mc": "市级-全市社会组织登记信息查询",
                        "url": "./lcshijQsshzzdjxx.html",
                        "class":"shijzhfrkqsshzzdjxx"
                    });
                wcxjkArr.push("shijzhfrkqsshzzdjxx");
                return list;
                break;
            //综合法人库-社会组织基本信息
            case "shzzjbxx":
                list.mc = "社会组织基本信息查询";
                list.children.push(
                    {
                        "mc": "市级-社会组织基本信息",
                        "url": "./lcshijShzzjbxx.html",
                        "class":"shijzhfrkshzzjbxx"
                    });
                wcxjkArr.push("shijzhfrkshzzjbxx");
                list.children.push(
                    {
                        "mc": "省级-社会组织统一社会信用代码信息查询",
                        "url": "../../view/gxjklc/lcbjShzztyshxydmxx.html",
                        "class":"bjshzztyshxydmxx"
                    });
                wcxjkArr.push("bjshzztyshxydmxx");
                return list;
                break;
            //综合人口库-人口基本信息
            case "rkjbxx":
                list.mc = "人口基本信息查询";
                list.children.push(
                    {
                        "mc": "市级-人口基本信息查询",
                        "url": "./lcshijRkjbxx.html",
                        "class":"shijzhrkkrkjbxx"
                    });
                wcxjkArr.push("shijzhrkkrkjbxx");
                return list;
            //综合人口库-家庭成员关系
            case "jtcygx":
                list.mc = "家庭成员关系查询";
                list.children.push(
                    {
                        "mc": "市级-家庭成员关系查询",
                        "url": "./lcshijJtcygx.html",
                        "class":"shijzhrkkjtcygx"
                    });
                wcxjkArr.push("shijzhrkkjtcygx");
                list.children.push(
                    {
                        "mc": "省级-居民户成员信息查询",
                        "url": "../../view/gxjklc/lcsjJmhxx.html",
                        "class":"lcsjJmhxx"
                    });
                wcxjkArr.push("lcsjJmhxx");
                return list;
            //市公安局—户籍信息
            case "gzhjxx":
                list.mc = "市公安局-户籍信息";
                list.children.push(
                    {
                        "mc": "市级-户籍信息",
                        "url": "./lcshijHjxx.html",
                        "class":"shijgzhjxx"
                    });
                wcxjkArr.push("shijgzhjxx");
                return list;
                break;
            //市编办—事业单位法人信息
            case "sydwfrxx":
                list.mc = "市编办-事业单位法人信息";
                list.children.push(
                    {
                        "mc": "市级-事业单位法人信息",
                        "url": "./lcshijSydwfrxx.html",
                        "class":"shijsydwfrxx"
                    });
                wcxjkArr.push("shijsydwfrxx");
                return list;
                break;
            //市编办—行政机关法人信息
            case "xzjgfrxx":
                list.mc = "行政机关法人信息";
                list.children.push(
                    {
                        "mc": "市级-行政机关法人信息",
                        "url": "./lcshijXzjgfrxx.html",
                        "class":"shijxzjgfrxx"
                    });
                wcxjkArr.push("shijxzjgfrxx");
                return list;
                break;
            //中央编办—党群机关信息查询接口
            case "dqjgcx":
                list.mc = "党群机关信息查询";
                list.children.push(
                    {
                        "mc": "市级-党群机关信息查询",
                        "url": "./lcshijDqjgxxcx.html",
                        "class":"shijdqjgcx"
                    });
                wcxjkArr.push("shijdqjgcx");
                list.children.push(
                    {
                        "mc": "省级-党群机关信息查询",
                        "url": "../../view/gxjklc/lcsjDqjg.html",
                        "class":"sjdqjg"
                    });
                wcxjkArr.push("sjdqjg");
                return list;
                break;
            //中央编办—事业单位信息查询
            case "sydwcx":
                list.mc = "事业单位信息查询";
                list.children.push(
                    {
                        "mc": "市级-事业单位信息查询",
                        "url": "./lcshijSydwxxcx.html",
                        "class":"shijsydwcx"
                    });
                wcxjkArr.push("shijsydwcx");
                list.children.push(
                    {
                        "mc": "省级-事业单位信息查询",
                        "url": "../../view/gxjklc/lcbjSydwdjxx.html",
                        "class":"bjsydwdjxx"
                    });
                wcxjkArr.push("bjsydwdjxx");
                return list;
                break;
            //民政厅—婚姻资源服务查询接口(整体授权)
            case "hyzyfw":
                list.mc = "婚姻资源服务查询";
                list.children.push(
                    {
                        "mc": "市级-婚姻资源服务查询",
                        "url": "./lcshijHyzyfwcx.html",
                        "class":"shijhyzyfw"
                    });
                wcxjkArr.push("shijhyzyfw");
                return list;
                break;
            //企业注册登记信息
            case "qyzcdjxx":
                list.mc = "企业注册登记信息查询";
                list.children.push(
                    {
                        "mc": "市级-企业注册登记信息查询",
                        "url": "./lcshijQyzcdjxx.html",
                        "class":"shijycsjgjqyzcdjxx"
                    });
                wcxjkArr.push("shijycsjgjqyzcdjxx");
                list.children.push(
                    {
                        "mc": "省级-企业基本信息查询",
                        "url": "../../view/gxjklc/lcsjQyjbxx.html",
                        "class":"sjqyjbxx"
                    });
                wcxjkArr.push("sjqyjbxx");
                return list;
                break;
            //国家公安部—人口库身份核查接口
            case "rkksfhc":
                list.mc = "人口库身份核查";
                list.children.push(
                    {
                        "mc": "市级-人口库身份核查",
                        "url": "./lcshijRkksfhc.html",
                        "class":"shijrkksfhc"
                    });
                wcxjkArr.push("shijrkksfhc");
                list.children.push(
                    {
                        "mc": "省级-人口库身份核查",
                        "url": "../../view/gxjklc/lcbjSfhc.html",
                        "class":"bjsfhc"
                    });
                wcxjkArr.push("bjsfhc");
                return list;
                break;
            //综合法人库-企业纳税明细信息
            case "qynsmxxx":
                list.mc = "企业纳税明细信息查询";
                list.children.push(
                    {
                        "mc": "市级-企业纳税明细信息查询",
                        "url": "./lcshijQynsmxxx.html",
                        "class":"shijzhfrkqynsmxxx"
                    });
                wcxjkArr.push("shijzhfrkqynsmxxx");
                return list;
                break;
            //综合人口库-农村留守儿童信息
            case "nclset":
                list.mc = "农村留守儿童信息查询";
                list.children.push(
                    {
                        "mc": "市级-农村留守儿童信息查询",
                        "url": "./lcshijNclsrtxx.html",
                        "class":"shijzhrkknclset"
                    });
                wcxjkArr.push("shijzhrkknclset");
                return list;
                break;
            //审批局-投资项目基本信息表
            case "spjtzxmjbxx":
                list.mc = "投资项目基本信息查询";
                list.children.push(
                    {
                        "mc": "市级-投资项目基本信息查询",
                        "url": "./lcshijTzxmjbxxb.html",
                        "class":"shijspjtzxmjbxx"
                    });
                wcxjkArr.push("shijspjtzxmjbxx");
                return list;
                break;
            //综合人口库-救助信息
            case "jzxx":
                list.mc = "救助信息查询";
                list.children.push(
                    {
                        "mc": "市级-救助信息查询",
                        "url": "./lcshijJzxx.html",
                        "class":"shijzhrkkjzxx"
                    });
                wcxjkArr.push("shijzhrkkjzxx");
                return list;
                break;
            //综合人口库-死亡医学证明
            case "swyxzm":
                list.mc = "死亡医学证明查询";
                list.children.push(
                    {
                        "mc": "市级-死亡医学证明查询",
                        "url": "./lcshijSwyxzm.html",
                        "class":"shijzhrkkswyxzm"
                    });
                wcxjkArr.push("shijzhrkkswyxzm");
                list.children.push(
                    {
                        "mc": "省级-死亡医学证明查询",
                        "url": "../../view/gxjklc/lcsjSwyxzm.html",
                        "class":"sjswyxzm"
                    });
                wcxjkArr.push("sjswyxzm");
                return list;
                break;
            //综合人口库-低收入人口信息
            case "dsrrkxx":
                list.mc = "低收入人口信息查询";
                list.children.push(
                    {
                        "mc": "市级-低收入人口信息查询",
                        "url": "./lcshijDsrrkxx.html",
                        "class":"shijzhrkkdsrrkxx"
                    });
                wcxjkArr.push("shijzhrkkdsrrkxx");
                return list;
                break;
            //地名信息查询
            case "dmxx":
                list.mc = "地名信息查询";
                list.children.push(
                    {
                        "mc": "省级-地名信息查询",
                        "url": "../../view/gxjklc/lcbjDmxx.html",
                        "class":"bjdmxx"
                    });
                wcxjkArr.push("bjdmxx");
                return list;
                break;
            //出生医学证明查询
            case "csyxzm":
                list.mc = "出生医学证明查询";
                list.children.push(
                    {
                        "mc": "省级-出生医学证明查询",
                        "url": "../../view/gxjklc/lcsjCsyxzm.html",
                        "class":"sjcsyxzm"
                    });
                wcxjkArr.push("sjcsyxzm");
                return list;
                break;
            //公民基本信息比对
            case "gmjbxxbd":
                list.mc = "公民基本信息比对";
                list.children.push(
                    {
                        "mc": "省级-公民基本信息比对",
                        "url": "../../view/gxjklc/lcsjGmjbxx.html",
                        "class":"sjgmjbxxbd"
                    });
                wcxjkArr.push("sjgmjbxxbd");
                return list;
                break;
            //企业基本信息验证
            case "qyjbxxyz":
                list.mc = "企业基本信息验证";
                list.children.push(
                    {
                        "mc": "省级-企业基本信息验证",
                        "url": "../../view/gxjklc/lcsjQyjbxxyz.html",
                        "class":"sjqyjbxxyz"
                    });
                wcxjkArr.push("sjqyjbxxyz");
                return list;
                break;
            //社会团体法人登记证书查询
            case "shttfrdjzs":
                list.mc = "社会团体法人登记证书查询";
                list.children.push(
                    {
                        "mc": "省级-社会团体法人登记证书查询",
                        "url": "../../view/gxjklc/lcsjShttfrdjzs.html",
                        "class":"sjshttfrdjzs"
                    });
                wcxjkArr.push("sjshttfrdjzs");
                return list;
                break;
            /**************************************以下为省厅接口************************************************************/
//用双斜杠注释的表示已经合并到市级接口中，用/**/注释的表示没有合并到市级接口，
//省市级接口暂时先按照开发想法合并，后续根据现场要求统一修改调整好后再删除省级接口


            // 2.1 公安部-身份核查
            // case "bjsfhc":
            //     list.mc = "身份核查";
            //     list.children.push(
            //         {
            //             "mc": "自然资源部-公安部",
            //             "url": "./lcbjSfhc.html",
            //             "class":"lcbjSfhc"
            //         });
            //     return list;
            //     break;

            // 2.2 民政部-地名信息
            /*            case "bjdmxx":
                            list.mc = "地名信息查询";
                            list.children.push(
                                {
                                    "mc": "自然资源部-民政部",
                                    "url": "./lcbjDmxx.html",
                                    "class":"lcbjDmxx"
                                });
                            return list;
                            break;
            */
            // 2.3 民政部-婚姻登记信息 合并在涉外婚姻登记
            // case "bjhydjxx":
            //     list.mc = "婚姻登记信息查询";
            //     list.children.push(
            //         {
            //             "mc": "自然资源部-民政部",
            //             "url": "./lcbjHydjxx.html",
            //             "class":"lcbjHydjxx"
            //         });
            //     return list;
            //     break;

            // 2.4 民政部-社会组织统一社会信用代码查询  合并在
            // case "bjshzztyshxydmxx":
            //     list.mc = "社会组织统一社会信用代码查询";
            //     list.children.push(
            //         {
            //             "mc": "自然资源部-民政部",
            //             "url": "./lcbjShzztyshxydmxx.html",
            //             "class":"lcbjShzztyshxydmxx"
            //         });
            //     return list;
            //     break;

            // 2.5 银保监会-金融许可证查询 已合并
            // case "bjjrxkz":
            //     list.mc = "金融许可证查询";
            //     list.children.push(
            //         {
            //             "mc": "自然资源部-银保监会",
            //             "url": "./lcbjJrxkz.html",
            //             "class":"lcbjJrxkz"
            //         });
            //     return list;
            //     break;

            // 2.6 中编办-事业单位登记信息（含机关、群团信息）查询
            /*           case "bjsydwdjxx":
                           list.mc = "事业单位（含机关、群团信息）登记信息查询";
                           list.children.push(
                               {
                                   "mc": "自然资源部-中编办",
                                   "url": "./lcbjSydwdjxx.html",
                                   "class":"lcbjSydwdjxx"
                               });
                           return list;
                           break;

                           // 2.7、2.8合并 最高法-司法判决信息查询
                       case "bjsfpjxx":
                           list.mc = "司法判决信息查询";
                           list.children.push(
                               {
                                   "mc": "自然资源部-最高法",
                                   "url": "./lcbjSfpjxxsq.html",
                                   "class":"lcbjSfpjxxsq"
                               });
                           return list;
                           break;

                           // 3.1 卫健委-出生医学证明
                       case "sjcsyxzm":
                           list.mc = "出生医学证明查询";
                           list.children.push(
                               {
                                   "mc": "省级数据共享交换平台-卫健委",
                                   "url": "./lcsjCsyxzm.html",
                                   "class":"lcsjCsyxzm"
                               });
                           return list;
                           break;
           */
            // 3.2 卫健委-死亡医学证明查询
            // case "sjswyxzm":
            //     list.mc = "死亡医学证明查询";
            //     list.children.push(
            //         {
            //             "mc": "省级数据共享交换平台-卫健委",
            //             "url": "./lcsjSwyxzm.html",
            //             "class":"lcsjSwyxzm"
            //         });
            //     return list;
            //     break;

            // 3.3 民政部-基金会法人登记证书查询
            // case "sjjjhfrdjzs":
            //     list.mc = "基金会法人登记证书查询";
            //     list.children.push(
            //         {
            //             "mc": "省级数据共享交换平台-民政部",
            //             "url": "./lcsjJjhfrdjzs.html",
            //             "class":"lcsjJjhfrdjzs"
            //         });
            //     return list;
            //     break;

            // 3.4 民政部-民办非企业单位登记证书查询
            // case "sjmbfqydjzs":
            //     list.mc = "民办非企业单位登记证书查询";
            //     list.children.push(
            //         {
            //             "mc": "省级数据共享交换平台-民政部",
            //             "url": "./lcsjMbfqydjzs.html",
            //             "class":"lcsjMbfqydjzs"
            //         });
            //     return list;
            //     break;

            // 3.5 民政部-社会团体法人登记证书查询
            /*            case "sjshttfrdjzs":
                            list.mc = "社会团体法人登记证书查询";
                            list.children.push(
                                {
                                    "mc": "省级数据共享交换平台-民政部",
                                    "url": "./lcsjShttfrdjzs.html",
                                    "class":"lcsjShttfrdjzs"
                                });
                            return list;
                            break;
            */
            // 3.6 民政部-婚姻登记信息核验（个人）
            // case "sjhyxxhygr":
            //     list.mc = "婚姻登记信息核验个人查询";
            //     list.children.push(
            //         {
            //             "mc": "省级数据共享交换平台-民政部",
            //             "url": "./lcsjHyxxhygr.html",
            //             "class":"lcsjHyxxhygr"
            //         });
            //     return list;
            //     break;

            // 3.7 民政部-婚姻登记信息核验（双方）
            // case "sjhyxxhysf":
            //     list.mc = "婚姻登记信息核验双方查询";
            //     list.children.push(
            //         {
            //             "mc": "省级数据共享交换平台-民政部",
            //             "url": "./lcsjHyxxhysf.html",
            //             "class":"lcsjHyxxhysf"
            //         });
            //     return list;
            //     break;

            // 3.8 市场监管总局-企业基本信息
            // case "sjqyjbxx":
            //     list.mc = "企业基本信息查询";
            //     list.children.push(
            //         {
            //             "mc": "省级数据共享交换平台-市场监管总局",
            //             "url": "./lcsjQyjbxx.html",
            //             "class":"lcsjQyjbxx"
            //         });
            //     return list;
            //     break;

            // 3.9 市场监管总局-企业基本信息验证
            /*            case "sjqyjbxxyz":
                            list.mc = "企业基本信息验证";
                            list.children.push(
                                {
                                    "mc": "省级数据共享交换平台-市场监管总局",
                                    "url": "./lcsjQyjbxxyz.html",
                                    "class":"lcsjQyjbxxyz",
                                });
                            return list;
                            break;
            */
            // 3.10 中编办-党群机关信息查询
            // case "sjdqjg":
            //     list.mc = "党群机关信息查询";
            //     list.children.push(
            //         {
            //             "mc": "省级数据共享交换平台-中编办",
            //             "url": "./lcsjDqjg.html",
            //             "class":"lcsjDqjg",
            //         });
            //     return list;
            //     break;

            // 3.11 中编办-事业单位信息查询
            // case "sjsydwxx":
            //     list.mc = "事业单位信息查询";
            //     list.children.push(
            //         {
            //             "mc": "省级数据共享交换平台-中编办",
            //             "url": "./lcsjSydwxx.html",
            //             "class":"lcsjSydwxx",
            //         });
            //     return list;
            //     break;

            // 4.1 省公安厅-公民基本信息在线比对
            /*           case "sjgmjbxxbd":
                           list.mc = "公民基本信息在线比对";
                           list.children.push(
                               {
                                   "mc": "省公安厅",
                                   "url": "./lcsjGmjbxx.html",
                                   "class":"lcsjGmjbxx",
                               });
                           return list;
                           break;

                       // 4.3 省公安厅-居民户成员信息在线查询
                       case "sjjmhxx":
                           list.mc = "居民户成员信息在线查询";
                           list.children.push(
                               {
                                   "mc": "省公安厅",
                                   "url": "./lcsjJmhxx.html",
                                   "class":"lcsjJmhxx",
                               });
                           return list;
                           break;
                           */
        }
    }

    // 组织目录数据到页面
    function generateMenu(menuList) {
        if(menuList.length > 0){
            //渲染菜单
            var getAsideTpl = menuTpl.innerHTML
                ,asideView = document.getElementById('accordion');
            laytpl(getAsideTpl).render(menuList, function(html){
                asideView.innerHTML = html;
            });
            // 只执行了下拉 未触发a标签点击事件
            eval($('#accordion a').filter(':first').attr("href"));
            //默认全部展开
            $('.submenu').css('display','block');
            $('.accordion li').toggleClass('open');

            // 自动查询
            // openGxlc(menuList[0].children[0].class,menuList[0].children[0].url);

            // 点击目录标题，让其处于选中状态
            $('a.bdc-last-child').on('click', function() {
                $('a.bdc-last-child').parent().css({
                    'background': 'transparent'
                });

                $(this).parent().css({
                    'background': '#E0F2F7'
                });
            });


        }else {
            warnMsg(" 未查询到流程数据！");
        }
    }

    // 打开查询页面
    function openGxlc(id,url) {
        $('#contentView .childFrame').hide();
        if($('#contentView').find('#' + id).length>0){
            $('#contentView').find('#' + id).show();
        }else {
            var iframe = '<iframe class="childFrame" id="' + id + '" src="'
                + url + '?gzlslid=' + gzlslid + '"> </iframe>'
            $('#contentView').append(iframe);
        }
        // $('#childFrame').attr("src",  url + '?gzlslid=' + gzlslid);
    }


});

//处理查询结果
function dealCxjg(result,jkname){
    if(result ==="success"){
        if(successjkArr.indexOf(jkname) <0) {
            //查询成功数量加1
            var successNum = parseInt($("#successNum").text());
            $("#successNum").text(successNum + 1);

            if (wcxjkArr.indexOf(jkname) > -1) {
                var wcxNum =parseInt($("#wcxNum").text());
                //未查询数量减1
                $("#wcxNum").text(wcxNum -1);
                wcxjkArr.forEach(function(item, index, arr) {
                    if(item == jkname) {
                        arr.splice(index, 1);
                    }
                });
            }else{
                var failNum =parseInt($("#failNum").text());
                //查询失败数量减1
                $("#failNum").text(failNum -1);
                failjkArr.forEach(function(item, index, arr) {
                    if(item == jkname) {
                        arr.splice(index, 1);
                    }
                });
            }
            successjkArr.push(jkname);
            //图标处理
            var $child =$("#img_"+jkname).parent(".bdc-last-child");
            $("#img_"+jkname).remove();
            if($child.length >0) {
                $child.append("<img src=\"../../static/image/cx-success.png\" alt=\"图标\" class=\"cxzt cx-success\" id=img_'" + jkname + "</img>");
            }
        }
    }else if(result ==="fail"){
        if(failjkArr.indexOf(jkname) <0) {
            //查询失败数量加1
            var failNum = parseInt($("#failNum").text());
            $("#failNum").text(failNum + 1);

            if (wcxjkArr.indexOf(jkname) > -1) {
                var wcxNum =parseInt($("#wcxNum").text());
                //未查询数量减1
                $("#wcxNum").text(wcxNum -1);
                wcxjkArr.forEach(function(item, index, arr) {
                    if(item == jkname) {
                        arr.splice(index, 1);
                    }
                });
            }else{
                var successNum =parseInt($("#successNum").text());
                //查询失败数量减1
                $("#successNum").text(successNum -1);
                successjkArr.forEach(function(item, index, arr) {
                    if(item == jkname) {
                        arr.splice(index, 1);
                    }
                });
            }
            failjkArr.push(jkname);
            //图标处理
            var $child =$("#img_"+jkname).parent(".bdc-last-child");
            $("#img_"+jkname).remove();
            if($child.length >0) {
                $child.append("<img src=\"../../static/image/cx-fail.png\" alt=\"图标\" class=\"cxzt cx-fail\" id=img_'" + jkname + "</img>");
            }
        }

    }

}
