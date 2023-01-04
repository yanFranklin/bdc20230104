
/**
 * author: 前端组
 * date: 2019-03-06
 * version 3.0.0
 * describe: 新分页控制
 */


layui.use(['table','laytpl','layer'],function () {
    var table = layui.table,
        $ = layui.jquery,
        layer = layui.layer;
    // //此为每页显示条数，默认为10条
    // var size=10;
    // //默认是第一页
    // var page=1;

    $(document).on("change"," .pageSelect",function () {
        var $this = $(this);
        //获取需要操作的表格
        var id = $this.parents(".newpage").siblings(".layui-table-view").attr("lay-id");
        var size = $(this).val();

        //重新从第一页加载
        table.reload(id, {
            where: {
                page: 1,
                size: size
            }
        });
        $this.siblings('.pageInput').val('1');
        $this.siblings('.prev-page').addClass('is-first-page');
        $this.siblings('.next-page').removeClass('is-last-page');
    });

    $(document).on("click"," .prev-page",function () {
        var $this = $(this);
        //获取需要操作的表格
        var id = $this.parents(".newpage").siblings(".layui-table-view").attr("lay-id");
        // console.log(id);
        var page = $this.siblings('.pageInput').val();
        var size = $this.siblings('.pageSelect').val();

        if(page == 1){
            //倘若page为1表示为第一页，则点击上一页提示不可点击
            // layer.msg("当前为第一页");
        }else{
            if(page == 2){
                $(".prev-page").addClass("is-first-page");
            }
            page--;
            table.reload(id, {
                where: {
                    page: page,
                    size: size
                }
            });
            $this.siblings('.pageInput').val(page);
            if($this.siblings('.next-page').hasClass('is-last-page')){
                $this.siblings('.next-page').removeClass('is-last-page');
            }
        }
    });

    $(document).on("click"," .next-page",function () {
        var $this = $(this);
        //获取需要操作的表格
        var id = $this.parents(".newpage").siblings(".layui-table-view").attr("lay-id");
        // console.log(id);
        var page = $this.siblings('.pageInput').val();
        var size = $this.siblings('.pageSelect').val();

        if($(this).hasClass("is-last-page")){
            // layer.msg("当前是最后一页");
            return
        }else{
            page++;
            table.reload(id, {
                where: {
                    page: page,
                    size: size
                }
            });
            $this.siblings('.pageInput').val(page);
            if($this.siblings(".prev-page").hasClass("is-first-page")){
                $this.siblings(".prev-page").removeClass("is-first-page");
            }

            var $tableView = $this.parents(".newpage").siblings(".layui-table-view");
            // console.log($tableView);

            var isEndRender = setInterval(function () {
                if($tableView.find('.layui-table-main tbody tr').length != 0){
                    var trLength = $tableView.find('.layui-table-main tbody tr').length;
                    if(trLength < size){
                        $this.addClass("is-last-page");
                    }
                    clearInterval(isEndRender);
                }
                if($tableView.find('.layui-table-main .layui-none').length != 0){
                    clearInterval(isEndRender);
                    $this.addClass("is-last-page");
                }
            },100);
        }

    });

    $(document).on("keypress"," .pageInput",function(event){
        var $this = $(this);
        //获取需要操作的表格
        var id = $this.parents(".newpage").siblings(".layui-table-view").attr("lay-id");
        console.log(id);
        var size = $this.siblings('.pageSelect').val();
        if(event.keyCode == 13 || event.keyCode ==108) {
            var page = $(this).val();
            table.reload(id, {
                where: {
                    page: page,
                    size: size
                }
            });

            if(page != 1 && $this.siblings('.prev-page').hasClass('is-first-page')){
                $this.siblings('.prev-page').removeClass('is-first-page');
            }
            if(page == 1){
                $this.siblings('.prev-page').addClass('is-first-page');
            }

            var $tableView = $this.parents(".newpage").siblings(".layui-table-view");
            var isEndRender = setInterval(function () {
                if($tableView.find('.layui-table-main tbody tr').length != 0){
                    var trLength = $tableView.find('.layui-table-main tbody tr').length;
                    if(trLength < size){
                        $this.siblings('.next-page').addClass("is-last-page");
                    }else {
                        $this.siblings('.next-page').removeClass("is-last-page");
                    }
                    clearInterval(isEndRender);
                }
                if($tableView.find('.layui-table-main .layui-none').length != 0){
                    clearInterval(isEndRender);
                    $this.siblings('.next-page').addClass("is-last-page");
                }
            },100);
        }

    });

    $(document).on("click"," .pageTotalButton",function () {
        var $this = $(this);
        //获取需要操作的表格
        var id = $this.parents(".newpage").siblings(".layui-table-view").attr("lay-id");
        var page = $this.siblings('.pageInput').val();
        var size = $this.siblings('.pageSelect').val();

        var $table = $this.parents(".newpage").siblings(".layui-table-view");
        // console.log($table);

        // $this.parents(".newpage").hide();
        $this.parents(".newpage").remove();
        table.reload(id, {
            limit: size,
            where: {
                page: page
                ,isShowCount: true
            },
            page: {
                curr: page
            },
            data: [111]
        });
    });
});