layui.use(['jquery','table','laypage','laytpl'], function(){
    var $ = layui.$,
        table = layui.table,
        laypage = layui.laypage,
        laytpl = layui.laytpl;

    $(function () {
        //获取动态添加的表格有几列
        var $trJs = $('.bdc-tr-table-js');
        for(var i = 0, len = $trJs.length; i < len; i++){
            var getWidth = Math.floor(100 / $($trJs[i]).find('.bdc-tr-td').length * 100)/100 + '%';
            var getI = i + 2;
            $('.bdc-change-tr:nth-child('+ getI +') .bdc-tr-table-js .bdc-tr-td').css('width',getWidth);
        }
        // console.log(getWidth);
        //点击详情页表格
        $('.bdc-table-box').on('click','.bdc-tr-box',function () {
            var $this = $(this);
            showTips($this);
        });

        var showMoreTop = 0;
        var showMoreLeft = 0;
        var $showMoreDiv = '';
        var scrollTop = 0;
        var scrollLeft = 0;
        var showDivWidth = 0;
        $(window).on('scroll',function (e) {
            scrollLeft = $(this).scrollLeft();
            scrollTop = $(this).scrollTop();

            if($showMoreDiv != ''){
                $showMoreDiv.css({top: showMoreTop - scrollTop,left: showMoreLeft - scrollLeft});
            }
        });
        var halfWidth = ($('body').width())/2 + 250;

        $('.bdc-table-box').on('click','.bdc-tr-content',function () {
            var $this = $(this);
            showTips($this);
        });
        function showTips($this) {
            $('.bdc-table-box').find('.bdc-table-click-show').remove();
            var getTitle = $this.data('title');
            showMoreLeft = $this.offset().left;
            showMoreTop = $this.offset().top;

            if(getTitle){
                if(!$this.children().is('.bdc-table-click-show')){
                    $this.append('<div class="bdc-table-click-show"><img src="../images/delete.png" alt="">'+ getTitle +'</div>');
                    $('.bdc-table-click-show').css('minWidth',$this.width());
                    $showMoreDiv = $('.bdc-table-click-show');
                    showDivWidth = $showMoreDiv.width();
                    // console.log('halfWidth:'+halfWidth);
                    // console.log('left:'+(showMoreLeft + showDivWidth - 80));
                    if(halfWidth > (showMoreLeft + showDivWidth - 80)){
                        showMoreTop = $this.offset().top + 5;
                        showMoreLeft = $this.offset().left + 20 ;
                    }else {
                        showMoreTop = $this.offset().top+ 5 ;
                        showMoreLeft = $this.offset().left - showDivWidth;
                    }
                    $showMoreDiv.css({
                        top: showMoreTop - scrollTop,
                        left: showMoreLeft-scrollLeft
                    });
                }
            }
        }
        $('.bdc-table-box').on('click','.bdc-table-click-show',function (event) {
            event.stopPropagation();
        });

        //点击删除小圈圈
        $('.bdc-table-box').on('click','.bdc-table-click-show img',function (event) {
            event.stopPropagation();
            $(this).parent().remove();
        });

        // 渲染分页
        laypage.render({
            elem: 'tablePage',
            count: 50,
            layout: ['prev', 'page', 'next','skip','limit'],
            jump: function(obj, first){
                //在这里点击分页后，页面渲染的逻辑
                console.log(obj.curr); //得到当前页，以便向服务端请求对应页的数据。
                console.log(obj.limit); //得到每页显示的条数

                //首次不执行
                if(!first){
                    //do something
                }
            }
        });

        var maxHeight = 38;
        $('.bdc-fj .bdc-tr-box-content').each(function () {
            if (maxHeight < $(this).height()) {
                maxHeight = $(this).height()
            }
        });
        $('.bdc-tr-box.bdc-fj').height(maxHeight);
    });

});