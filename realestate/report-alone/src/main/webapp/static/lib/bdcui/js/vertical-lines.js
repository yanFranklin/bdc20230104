layui.use(['jquery','table'], function(){
    var $ = layui.$,
        table = layui.table ;
    
    $(function () {
        //点击详情页表格
        $('.bdc-table-box').on('click','.bdc-tr-box',function () {
            // console.log($(this).data('title'));
            $('.bdc-table-box').find('.bdc-table-click-show').remove();
            var $this = $(this);
            var getTitle = $this.data('title');
            console.log($this);
            if(getTitle){
                if(!$this.children().is('.bdc-table-click-show')){
                    $this.append('<div class="bdc-table-click-show"><img src="../images/delete.png" alt="">'+ getTitle +'</div>');
                }
            }
        });
        $('.bdc-table-box').on('click','.bdc-tr-content',function () {
            // console.log($(this).data('title'));
            $('.bdc-table-box').find('.bdc-table-click-show').remove();
            var $this = $(this);
            var getTitle = $this.data('title');
            console.log($this);
            if(getTitle){
                if(!$this.children().is('.bdc-table-click-show')){
                    $this.append('<div class="bdc-table-click-show"><img src="../images/delete.png" alt="">'+ getTitle +'</div>');
                }
            }
        });
        $('.bdc-table-box').on('click','.bdc-table-click-show',function (event) {
            event.stopPropagation();
        });

        //点击删除小圈圈
        $('.bdc-table-box').on('click','.bdc-table-click-show img',function (event) {
            event.stopPropagation();
            $(this).parent().remove();
        });

    });

});