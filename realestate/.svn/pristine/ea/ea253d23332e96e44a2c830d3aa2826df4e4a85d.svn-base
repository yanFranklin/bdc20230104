// 查询参数
var gzlslid= $.getUrlParam("processInsId");
var formStateId= $.getUrlParam("formStateId");
//目录
var menuList=[];
//未查询接口集合
var wcxjkArr =[];
//查询成功接口集合
var successjkArr =[];
//查询成功接口集合
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
        //1.2  对于逻辑栋点击高亮
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
        addModel();
        $.ajax({
            url:"/realestate-inquiry-ui/rest/v1.0/gx/ml/" + gzlslid,
            type: "POST",
            contentType: 'application/json',
            success: function (data) {
                removeModal();
                if(data && data.length>0){
                    addSecondMenu(data);
                    generateMenu(menuList);
                }
            },
            error: function (xhr, status, error) {
                removeModal();
                delAjaxErrorMsg(xhr)
            }
        });
    }

    // 添加二级目录
    function addSecondMenu(data) {
        //默认全部未查询
        $("#wcxNum").text(data.length);
        wcxjkArr =data;
        for(var i=0; i<data.length; i++){
            if(isNotBlank(data[i])){
                var list={
                    "name": data[i],
                    "children": []
                };
                addUrl(list);
                menuList.push(list);
            }
        }

    }

    // 根据名称对应二级名称和url
    function addUrl(list) {
        switch (list.name) {

            // 2.1 公安部-身份核查
            case "bjsfhc":
                list.mc = "身份核查";
                list.children.push(
                    {
                        "mc": "自然资源部-公安部",
                        "url": "./lcbjSfhc.html?jkname="+list.name,
                        "class":"lcbjSfhc"
                    });
                return list;
                break;

                // 2.2 民政部-地名信息
            case "bjdmxx":
                list.mc = "地名信息查询";
                list.children.push(
                    {
                        "mc": "自然资源部-民政部",
                        "url": "./lcbjDmxx.html?jkname="+list.name,
                        "class":"lcbjDmxx"
                    });
                return list;
                break;

                // 2.3 民政部-婚姻登记信息
            case "bjhydjxx":
                list.mc = "婚姻登记信息查询";
                list.children.push(
                    {
                        "mc": "自然资源部-民政部",
                        "url": "./lcbjHydjxx.html?jkname="+list.name,
                        "class":"lcbjHydjxx"
                    });
                return list;
                break;

                // 2.4 民政部-社会组织统一社会信用代码查询
            case "bjshzztyshxydmxx":
                list.mc = "社会组织统一社会信用代码查询";
                list.children.push(
                    {
                        "mc": "自然资源部-民政部",
                        "url": "./lcbjShzztyshxydmxx.html?jkname="+list.name,
                        "class":"lcbjShzztyshxydmxx"
                    });
                return list;
                break;

                // 2.5 银保监会-金融许可证查询
            case "bjjrxkz":
                list.mc = "金融许可证查询";
                list.children.push(
                    {
                        "mc": "自然资源部-银保监会",
                        "url": "./lcbjJrxkz.html?jkname="+list.name,
                        "class":"lcbjJrxkz"
                    });
                return list;
                break;

                // 2.6 中编办-事业单位登记信息（含机关、群团信息）查询
            case "bjsydwdjxx":
                list.mc = "事业单位（含机关、群团信息）登记信息查询";
                list.children.push(
                    {
                        "mc": "自然资源部-中编办",
                        "url": "./lcbjSydwdjxx.html?jkname="+list.name,
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
                        "url": "./lcbjSfpjxxsq.html?jkname="+list.name,
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
                        "url": "./lcsjCsyxzm.html?jkname="+list.name,
                        "class":"lcsjCsyxzm"
                    });
                return list;
                break;

                // 3.2 卫健委-死亡医学证明查询
            case "sjswyxzm":
                list.mc = "死亡医学证明查询";
                list.children.push(
                    {
                        "mc": "省级数据共享交换平台-卫健委",
                        "url": "./lcsjSwyxzm.html?jkname="+list.name,
                        "class":"lcsjSwyxzm"
                    });
                return list;
                break;

            // 3.3 民政部-基金会法人登记证书查询
            case "sjjjhfrdjzs":
                list.mc = "基金会法人登记证书查询";
                list.children.push(
                    {
                        "mc": "省级数据共享交换平台-民政部",
                        "url": "./lcsjJjhfrdjzs.html?jkname="+list.name,
                        "class":"lcsjJjhfrdjzs"
                    });
                return list;
                break;

            // 3.4 民政部-民办非企业单位登记证书查询
            case "sjmbfqydjzs":
                list.mc = "民办非企业单位登记证书查询";
                list.children.push(
                    {
                        "mc": "省级数据共享交换平台-民政部",
                        "url": "./lcsjMbfqydjzs.html?jkname="+list.name,
                        "class":"lcsjMbfqydjzs"
                    });
                return list;
                break;

            // 3.5 民政部-社会团体法人登记证书查询
            case "sjshttfrdjzs":
                list.mc = "社会团体法人登记证书查询";
                list.children.push(
                    {
                        "mc": "省级数据共享交换平台-民政部",
                        "url": "./lcsjShttfrdjzs.html?jkname="+list.name,
                        "class":"lcsjShttfrdjzs"
                    });
                return list;
                break;

            // 3.6 民政部-婚姻登记信息核验（个人）
            case "sjhyxxhygr":
                list.mc = "婚姻登记信息核验个人查询";
                list.children.push(
                    {
                        "mc": "省级数据共享交换平台-民政部",
                        "url": "./lcsjHyxxhygr.html?jkname="+list.name,
                        "class":"lcsjHyxxhygr"
                    });
                return list;
                break;

            // 3.7 民政部-婚姻登记信息核验（双方）
            case "sjhyxxhysf":
                list.mc = "婚姻登记信息核验双方查询";
                list.children.push(
                    {
                        "mc": "省级数据共享交换平台-民政部",
                        "url": "./lcsjHyxxhysf.html?jkname="+list.name,
                        "class":"lcsjHyxxhysf"
                    });
                return list;
                break;

            // 3.8 市场监管总局-企业基本信息
            case "sjqyjbxx":
                list.mc = "企业基本信息查询";
                list.children.push(
                    {
                        "mc": "省级数据共享交换平台-市场监管总局",
                        "url": "./lcsjQyjbxx.html?jkname="+list.name,
                        "class":"lcsjQyjbxx"
                    });
                return list;
                break;

            // 3.9 市场监管总局-企业基本信息验证
            case "sjqyjbxxyz":
                list.mc = "企业基本信息验证";
                list.children.push(
                    {
                        "mc": "省级数据共享交换平台-市场监管总局",
                        "url": "./lcsjQyjbxxyz.html?jkname="+list.name,
                        "class":"lcsjQyjbxxyz",
                    });
                return list;
                break;

            // 3.10 中编办-党群机关信息查询
            case "sjdqjg":
                list.mc = "党群机关信息查询";
                list.children.push(
                    {
                        "mc": "省级数据共享交换平台-中编办",
                        "url": "./lcsjDqjg.html?jkname="+list.name,
                        "class":"lcsjDqjg",
                    });
                return list;
                break;

            // 3.11 中编办-事业单位信息查询
            case "sjsydwxx":
                list.mc = "事业单位信息查询";
                list.children.push(
                    {
                        "mc": "省级数据共享交换平台-中编办",
                        "url": "./lcsjSydwxx.html?jkname="+list.name,
                        "class":"lcsjSydwxx",
                    });
                return list;
                break;

            // 4.1 省公安厅-公民基本信息在线比对
            case "sjgmjbxxbd":
                list.mc = "公民基本信息在线比对";
                list.children.push(
                    {
                        "mc": "省公安厅",
                        "url": "./lcsjGmjbxx.html?jkname="+list.name,
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
                        "url": "./lcsjJmhxx.html?jkname="+list.name,
                        "class":"lcsjJmhxx",
                    });
                return list;
                break;
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

            openGxlc(menuList[0].children[0].class,menuList[0].children[0].url);

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
                + url + '&gzlslid=' + gzlslid + '&formStateId='+formStateId+'"> </iframe>';
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
            //查询成功数量加1
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
