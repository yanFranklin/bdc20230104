layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
}).use(['form', 'jquery', 'laydate', 'element', 'table', 'formSelects', 'layer', 'laytpl'], function () {
    var $ = layui.jquery,
        form = layui.form,
        element = layui.element,
        table = layui.table,
        laydate = layui.laydate,
        layer = layui.layer,
        formSelects = layui.formSelects,
        laytpl = layui.laytpl;

    $(function () {

        var wwxmid = getQueryString("wwxmid");

        var gzldyid = getQueryString("gzldyid");

        var zdList =[];

        //滚动时头部固定
        var $cnotentTitle = $('.bdc-content-fix');
        var $navContainer = $('.bdc-form-div');

        function defaultStyle() {
            if ($cnotentTitle.length == 1) {
                $('.bdc-form-div').css('padding-top', '68px');
            }
            if ($(window).scrollTop() > 68) {
                $cnotentTitle.css('top', '0');
                $navContainer.css('top', '63px');
            } else if ($(window).scrollTop() <= 68) {
                $cnotentTitle.css('top', '10px');
                $navContainer.css('top', '68px');
            }
        }

        $(window).resize(function () {
            defaultStyle();
        });

        $(window).on('scroll', function () {
            if ($(this).scrollTop() > 68) {
                $cnotentTitle.css('top', '0');
                $navContainer.css('top', '63px');
            } else if ($(this).scrollTop() <= 68) {
                $cnotentTitle.css('top', '10px');
                $navContainer.css('top', '68px');
            }
        });

        //获取字典
        getZd();

        //获取外网申请信息
        getWwsqxx();

        function getZd(){
            $.ajax({
                url: "/realestate-etl/wwsq/zd",
                type: "GET",
                async: false,
                dataType: "json",
                success: function (data) {
                    if (data) {
                        zdList = data;
                    }
                }
            });

        }

        // 获取外网申请信息
        function getWwsqxx() {
            var sqxxList =[];
            sqxxList.push({});
            var json ={
                sqxxList:sqxxList
            };
            var view = document.getElementById('sqxxContent');

            var tpl = sqxxTpl.innerHTML;
            //渲染数据
            laytpl(tpl).render(json, function (html) {
                view.innerHTML = view.innerHTML + html;
            });
            $.ajax({
                url: "/realestate-etl/wwsq",
                type: 'GET',
                dataType: 'json',
                async: false,
                data: {wwxmid:wwxmid},
                success: function (data) {
                    if(data &&data.gxWwsqxxDTOList) {
                        var json ={
                            sqxxList:data.gxWwsqxxDTOList,
                            sqxx:data,
                            zd:zdList
                        };

                        //渲染数据
                        var liView =document.getElementById('liContent');
                        var litpl =liTpl.innerHTML;
                        laytpl(litpl).render(json, function (html) {
                            liView.innerHTML = html;
                        });
                        laytpl(tpl).render(json, function (html) {

                            view.innerHTML = html;
                        });
                        var jbxxContent =document.getElementById('jbxxContent');
                        var getjbxxTpl =jbxxTpl.innerHTML;
                        //渲染数据
                        laytpl(getjbxxTpl).render(json, function (html) {
                            jbxxContent.innerHTML = html;
                        });
                        //获取字典
                       renderZd(zdList);
                        //申请信息赋值
                        form.val("jbxxForm",data.gxWwSqxmDo);
                        form.val("jbxxForm",data.gxWwsqxxDTOList[0].gxWwsqBdcZdzDTO);
                        form.val("jbxxForm",data.gxWwsqxxDTOList[0].gxWwSqxxDo);
                        //权限控制,分tab页控制
                        $("[lay-filter='jbxxForm']").addClass("jbxx");
                        setElementAttrByFormState(gzldyid+"_sl","jbxx","jbxx");
                        for(var i = 0, len = data.gxWwsqxxDTOList.length; i < len; i++) {
                            setFormValue(data, (i+1));
                            //权限控制,分tab页控制
                            $("[lay-filter=sqxxForm"+(i+1)+"]").addClass("wwsqxx_"+data.gxWwsqxxDTOList[i].gxWwSqxxDo.sqlx);
                            setElementAttrByFormState(gzldyid+"_sl","wwsqxx_"+data.gxWwsqxxDTOList[i].gxWwSqxxDo.sqlx,"wwsqxx_"+data.gxWwsqxxDTOList[i].gxWwSqxxDo.sqlx);
                        }


                        //渲染日期
                        renderDate();

                        //设置不可编辑
                        setAllElementDisabled();

                        resetSelectDisabledCss();




                    }

                }, error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr)
                }
            });
        }

        //渲染字典项
        function renderZd(data){
            $.each(data.qlxz, function (index, item) {
                $('[name=zdzhqlxz]').append('<option value="' + item.DM + '">' + item.MC + '</option>');
            });
            $.each(data.cflx, function (index, item) {
                $('[name=cflx]').append('<option value="' + item.DM + '">' + item.MC + '</option>');
            });
            $.each(data.fwjg, function (index, item) {
                $('[name=fwjg]').append('<option value="' + item.DM + '">' + item.MC + '</option>');
            });
            $.each(data.dyfs, function (index, item) {
                $('[name=dyfs]').append('<option value="' + item.DM + '">' + item.MC + '</option>');
            });
            $.each(data.zjzl, function (index, item) {
                $('[name=cfzxrzjzl]').append('<option value="' + item.DM + '">' + item.MC + '</option>');
            });
            $.each(data.bdclx, function (index, item) {
                $('[name=bdclx]').append('<option value="' + item.DM + '">' + item.MC + '</option>');
            });
            $.each(data.fwyt, function (index, item) {
                $('[name=fwyt]').append('<option value="' + item.DM + '">' + item.MC + '</option>');
            });
            $.each(data.fwxz, function (index, item) {
                $('[name=fwxz]').append('<option value="' + item.DM + '">' + item.MC + '</option>');
            });
            $.each(data.tdyt, function (index, item) {
                $('[name=tdyt]').append('<option value="' + item.MC + '">' + item.MC + '</option>');
                $('[name=tdyt2]').append('<option value="' + item.MC + '">' + item.MC + '</option>');
                $('[name=tdyt3]').append('<option value="' + item.MC + '">' + item.MC + '</option>');
            });
            $.each(data.gjzwlx, function (index, item) {
                $('[name=gzwlx]').append('<option value="' + item.DM + '">' + item.MC + '</option>');
            });
            $.each(data.cflx, function (index, item) {
                $('[name=cflx]').append('<option value="' + item.DM + '">' + item.MC + '</option>');
            });
            $.each(data.fwlx, function (index, item) {
                $('[name=fwlx]').append('<option value="' + item.DM + '">' + item.MC + '</option>');
            });
        }

        //表单赋值
        function setFormValue(data,index){

            form.val("sqxxForm"+index,data);
            form.val("sqxxForm"+index,data.gxWwSqxmDo);
            form.val("sqxxForm"+index,data.gxWwsqxxDTOList[index-1].gxWwSqxxDo);
            form.val("sqxxForm"+index,data.gxWwsqxxDTOList[index-1].gxWwsqBdcZdzDTO);

        }

    });
});

//权利人详细
function showQlr(qlrid){
    var url ="/realestate-etl/view/wwsq/qlr.html?qlrid="+qlrid;
    layer.open({
        title: '申请人详细信息',
        type: 2,
        area: ['960px','400px'],
        content: url
        ,cancel: function(){
            //右上角关闭回调
            //return false 开启该代码可禁止点击该按钮关闭
        }
        ,success: function () {

        }
    });

}