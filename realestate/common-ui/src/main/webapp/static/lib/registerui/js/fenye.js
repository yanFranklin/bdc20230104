
/**
 * author: 前端组
 * date: 2019-03-06
 * version 3.0.0
 * describe: 新分页控制
 */


layui.use(['table','laytpl','layer'],function () {
    var table = layui.table,
        $ = layui.jquery,
        layer = layui.layer,
        laypage=layui.laypage;
    // //此为每页显示条数，默认为10条
    // var size=10;
    // //默认是第一页
    // var page=1;

    var tableSetting=[];
    //是否是最后一页
    var isLast=false;
    //是否是第一页
    var isFirst=false;


    var changeSize = function(limit,id) {
        for(var i=0;i<tableSetting.length;i++){
            var obj=tableSetting[i];
            if(obj.tableID==id){
                obj.size=limit;
            }
        }
    };

    var getPage = function (id) {
        var page;
        for(var i=0;i<tableSetting.length;i++){
            var obj=tableSetting[i];
            if(obj.tableID==id){
                page=obj.page;
            }
        }
        return parseFloat(page);
    };

    var getSize=function (id) {
        var size;
        for(var i=0;i<tableSetting.length;i++){
            var obj=tableSetting[i];
            if(obj.tableID==id){
                size=obj.size;
            }
        }
        return parseFloat(size);
    };

    //重置page
    var setPage = function(newPage,id) {
        for(var i=0;i<tableSetting.length;i++){
            var obj=tableSetting[i];
            if(obj.tableID==id){
                obj.page=newPage;
                $(obj.tableID).siblings(".newpage ").find(".pageInput").val(newPage);
            }
        }

    };



    //切换回来（暂时不用）
    $("body").on("click","#pageTotalButtonX",function () {
        $("#page").show();
        var page=$(".layui-laypage-skip").find("input").val();
        var size=$(".layui-laypage-limits select option:selected").val();
        $("#pageSelect option").each(function(){
            $(this).attr("selected",false);
            if($(this).val()==size){
                $(this).attr("selected",true);
            }
        });
        changeSize(size);
        $(".layui-table-page").remove();
        pageLoaddata(page,size,url,tableID,toolbar,defaultToolbar,tablecol,ajaxmethod);
    });





    $(document).on("change"," .pageSelect",function () {
        //获取需要操作的表格
        var id="#"+$(this).parents(".newpage").siblings("table").attr("id");
        //获取URL
        var url,tablecol,tableID,toolbar,defaultToolbar,ajaxmethod,laypageID;
        for(var i=0;i<tableSetting.length;i++){
            var obj=tableSetting[i];
            if(obj.tableID==id){
                url=obj.url;
                tablecol=obj.tablecol;
                tableID=obj.tableID;
                toolbar=obj.toolbar;
                defaultToolbar=obj.defaultToolbar;
                ajaxmethod=obj.ajaxmethod;
                laypageID=obj.laypageID.substring(1);
            }
        }
        var size=$(this).val();
        changeSize(size,id);
        //重新从第一页加载
        pageLoaddata(1,size,url,tableID,toolbar,defaultToolbar,tablecol,ajaxmethod);
    });

    $(document).on("click"," .prev-page",function () {
        //获取需要操作的表格
        var id="#"+$(this).parents(".newpage").siblings("table").attr("id");
        //获取URL
        var url,tablecol,tableID,toolbar,defaultToolbar,ajaxmethod,laypageID;
        for(var i=0;i<tableSetting.length;i++){
            var obj=tableSetting[i];
            if(obj.tableID==id){
                url=obj.url;
                tablecol=obj.tablecol;
                tableID=obj.tableID;
                toolbar=obj.toolbar;
                defaultToolbar=obj.defaultToolbar;
                ajaxmethod=obj.ajaxmethod;
                laypageID=obj.laypageID.substring(1);
            }
        }
        var page=getPage(id);
        var size=getSize(id);
        if(page==1){
            //倘若page为1表示为第一页，则点击上一页提示不可点击
            // layer.msg("当前为第一页");
        }else{
            if(page==2){
                $(".prev-page").addClass("is-first-page");
            }
            page=page-1;
            pageLoaddata(page,size,url,tableID,toolbar,defaultToolbar,tablecol,ajaxmethod);
        }
    });

    $(document).on("click"," .next-page",function () {
        //获取需要操作的表格
        var id="#"+$(this).parents(".newpage").siblings("table").attr("id");
        //获取URL
        var url,tablecol,tableID,toolbar,defaultToolbar,ajaxmethod,laypageID;
        for(var i=0;i<tableSetting.length;i++){
            var obj=tableSetting[i];
            if(obj.tableID==id){
                url=obj.url;
                tablecol=obj.tablecol;
                tableID=obj.tableID;
                toolbar=obj.toolbar;
                defaultToolbar=obj.defaultToolbar;
                ajaxmethod=obj.ajaxmethod;
                laypageID=obj.laypageID.substring(1);
            }
        }
        var page=getPage(id);
        var size=getSize(id);
        if($(this).hasClass("is-last-page")){
            // layer.msg("当前是最后一页");
            return
        }else{
            page=page+1;
            pageLoaddata(page,size,url,tableID,toolbar,defaultToolbar,tablecol,ajaxmethod);
            $(".prev-page").removeClass("is-first-page");
        }
    });

    $(document).on("keypress"," .pageInput",function(event){
        //获取需要操作的表格
        var id="#"+$(this).parents(".newpage").siblings("table").attr("id");
        //获取URL
        var url,tablecol,tableID,toolbar,defaultToolbar,ajaxmethod,laypageID;
        for(var i=0;i<tableSetting.length;i++){
            var obj=tableSetting[i];
            if(obj.tableID==id){
                url=obj.url;
                tablecol=obj.tablecol;
                tableID=obj.tableID;
                toolbar=obj.toolbar;
                defaultToolbar=obj.defaultToolbar;
                ajaxmethod=obj.ajaxmethod;
                laypageID=obj.laypageID.substring(1);
            }
        }
        if(event.keyCode == 13 || event.keyCode ==108) {
            var size=getSize(id);
            var page=$(this).val();
            pageLoaddata(page,size,url,tableID,toolbar,defaultToolbar,tablecol,ajaxmethod);
        }

    });

    $(document).on("click"," .pageTotalButton",function () {
        //获取需要操作的表格
        var id="#"+$(this).parents(".newpage").siblings("table").attr("id");
        //获取URL
        var url,tablecol,tableID,toolbar,defaultToolbar,ajaxmethod,laypageID;
        for(var i=0;i<tableSetting.length;i++){
            var obj=tableSetting[i];
            if(obj.tableID==id){
                url=obj.url;
                tablecol=obj.tablecol;
                tableID=obj.tableID;
                toolbar=obj.toolbar;
                defaultToolbar=obj.defaultToolbar;
                ajaxmethod=obj.ajaxmethod;
                laypageID=obj.laypageID.substring(1);
            }
        }
        var aa=tableID.substring(1);
        $(this).parents(".newpage").hide();
        var size=getSize(id);
        var page=getPage(id);
        loadTotalData(page,size,url,tablecol,tableID,toolbar,defaultToolbar,ajaxmethod,laypageID);
    });

    Window.pageStart=function(url,tablecol,tableID,ajaxmethod,toolbar,defaultToolbar,laypageID){
        var page=1;
        var size=10;

        var obj={
            tableID:tableID,
            url:url,
            tablecol:tablecol,
            ajaxmethod:ajaxmethod,
            toolbar:toolbar,
            defaultToolbar:defaultToolbar,
            page:page,
            size:size,
            laypageID:laypageID
        };

        tableSetting.push(obj);
        var aaa=tableID.substring(1);

        $(tableID).after('' +
            // '    <div id="'+tableID+'laypage"></div>' +
            '    <div id="'+aaa+'page" class="newpage">' +
            '        <i class="layui-icon layui-icon-left prev-page is-first-page"></i>' +
            '        第<input type="text" class="pageInput" value="1">页' +
            '        <i class="layui-icon layui-icon-right next-page"></i>' +
            '        <select name="" class="pageSelect">' +
            '            <option value="10">10条/页</option>' +
            '            <option value="20">20条/页</option>' +
            '            <option value="30">30条/页</option>' +
            '            <option value="40">40条/页</option>' +
            '            <option value="50">50条/页</option>' +
            '            <option value="60">60条/页</option>' +
            '            <option value="70">70条/页</option>' +
            '            <option value="80">80条/页</option>' +
            '            <option value="90">90条/页</option>' +
            '        </select>\n' +
            '        <input type="button" value="详细" class="pageTotalButton">' +
            '    </div>');
        var firstSize=10;
        var firstPage=1;
        pageLoaddata(firstPage,firstSize,url,tableID,toolbar,defaultToolbar,tablecol,ajaxmethod);

    };

    //此为不带总数的加载方式
    function loadTable(data,tableID,toolbar,defaultToolbar,col,ajaxmethod){
        table.render({
            elem:tableID,
            toolbar: toolbar,
            title: '',
            defaultToolbar: defaultToolbar,
            cols: col,
            data: data,
            page:false,
            even: true,
            limit:Number.MAX_VALUE,
            done: function () {
                $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');
            }
        });
    }
    //此为不带总数的分页器
    var pageLoaddata = function(page,size,url,tableID,toolbar,defaultToolbar,tablecol,ajaxmethod){
        $.ajax({
            type: ajaxmethod,
            url:  url,
            data: {
                page: page,
                size: size,
                loadTotal:false
            },
            success: function (data) {
                if(data.content.length==0){
                    layer.msg("目标页面没有数据！");
                }else{
                    console.log(data);
                    loadTable(data.content,tableID,toolbar,defaultToolbar,tablecol,ajaxmethod);
                    setPage(page,tableID);
                    if(parseFloat(data.content.length)<parseFloat(size)){
                        $(".next-page").addClass("is-last-page");
                    }else{
                        $(".next-page").removeClass("is-last-page");
                    }
                }

            },
            complete:function (msg) {
                // console.log(msg)
            },
            error:function (msg) {
                console.log(msg)
            }
        });
    };


    //切换成layui默认显示方式
    var changePageStyle = function(page,size,url,col,tableID,toolbar,defaultToolbar,ajaxmethod,laypageID,data,total) {
        table.render({
            elem:tableID,
            toolbar: toolbar,
            title: '',
            defaultToolbar: defaultToolbar,
            cols: col,
            data: data,
            page:false,
            even: true,
            limit:size,
            done: function (res, curr, count) {
                $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');
                laypage.render({
                    elem:laypageID
                    ,count:total
                    ,curr:page
                    ,limit:size
                    ,limits:[10,20,30,40,50,60,70,80,90]
                    ,layout: ['prev', 'page', 'next', 'skip', 'count', 'limit']
                    ,prev: '<i class="layui-icon">&#xe603;</i>'
                    ,next: '<i class="layui-icon">&#xe602;</i>'
                    ,jump:function (obj,first) {
                        if(!first){
                            var curnum = obj.curr;
                            var limitcount = obj.limit;
                            loadTotalData(curnum,limitcount,url,col,tableID,toolbar,defaultToolbar,ajaxmethod);
                        }
                    }
                });
                // $(".layui-laypage").append('<input type="button" value="切换" id="pageTotalButtonX">');
            }
        });
    };

    //带总数的数据查询
    var loadTotalData=function(page,size,url,col,tableID,toolbar,defaultToolbar,ajaxmethod,laypageID){
        $.ajax({
            type: ajaxmethod,
            url:  url,
            data: {
                page: page,
                size: size
            },
            success: function (data) {
                if(data.content.length==0){
                    layer.msg("目标页面没有数据！");
                }else{
                    changePageStyle(page,size,url,col,tableID,toolbar,defaultToolbar,ajaxmethod,laypageID,data.content,data.totalElements);
                }

            },
            complete:function (msg) {
                // console.log(msg)
            },
            error:function (msg) {
                console.log(msg)
            }
        });
    }

});