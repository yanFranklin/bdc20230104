layui.use(['jquery','table','laypage','laytpl'], function(){
    var $ = layui.$,
        table = layui.table,
        laypage = layui.laypage,
        laytpl = layui.laytpl;
    
    $(function () {
        //点击表格中的一行，展示当前行全部信息
        $('.bdc-table-box').on('click','.bdc-tr-box',function () {
            // console.log($(this).data('title'));
            $('.bdc-table-box').find('.bdc-table-click-show').remove();
            var $this = $(this);
            var getTitle = $this.data('title');
            // console.log($this);
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

        //渲染表格数据
        var data = [{
            ywh: '33251485951',
            syq: '啦啦啦啦',
            zjzl: '身份证',
            zjh: '320152485495125621',
            gyqk: '按份共有',
            qlrlx: '个人',
            djlx: '首次登记',
            djyy: '新建房屋',
            tdsyqr: '啦啦啦啦',
            dymj: '27.00',
            ftmj: '27.00',
            syksnx: '2018-08-02',
            syjsnx: '2018-12-13',
            zsh: '1561651651651561561',
            djsj: '测试室内容测试测试室内容测试测试室内容测试测试室内容测试测试室内容测试测试室内容测试测试室内容测试测试室内容测试测试室内容测试测试室内容测试测试室内容测试'
        }];
        console.log(data.length);
        if(data.length <= 2){
            for(var i = data.length; i < 2; i++){
                data.push({});
            }
            var getTpl = tableTpl.innerHTML
                ,view = document.getElementById('trView');
            laytpl(getTpl).render(data, function(html){
                view.innerHTML = html;
            });
            $('.bdc-tr-width').css('width','50%');
        }else {
            for(var j = data.length; j < 4; j++){
                data.push({});
            }
            var getTpl = tableTpl.innerHTML
                ,view = document.getElementById('trView');
            laytpl(getTpl).render(data, function(html){
                view.innerHTML = html;
            });
        }

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