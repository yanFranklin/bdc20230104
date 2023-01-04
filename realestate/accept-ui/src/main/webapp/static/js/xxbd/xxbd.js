/**
 * 对比表格js
 */
layui.use(['table','laytpl','layer','form'],function () {
    var table = layui.table,
        $ = layui.jquery,
        layer = layui.layer,
        form =layui.form;

    $(function () {

        var bdlx =getQueryString("bdlx");
        //获取所有参数
        var requestParam =getRequestParam();

        addModel();
        //对比表格数据
        var currentData =[];
        //获取比对数据
        $.ajax({
            url: getContextPath() + "/rest/v1.0/xxbd",
            type: 'GET',
            dataType: 'json',
            data: {bdlx:bdlx,paramMap:requestParam},
            success: function (data) {
                if (data) {
                    var cols=[
                        {field:'zdmc', title:'字段名'}
                    ];
                    //根据数据来源集合生成对应的列
                    var sjlyList =data.sjlyList;
                    for(var i=0;i<sjlyList.length;i++) {
                        cols.push({field:'zdz'+(i+1), title:sjlyList[i]});
                    };
                    currentData=data.bdcBdsjDTOList;
                    table.render({
                        id:'xxbdTable',
                        elem: '#pageTable',
                        height: 'full-196',
                        title: '对比表格',
                        even: true,
                        limit: 3000,
                        cols: [cols],
                        data: currentData,
                        done: function () {
                            removeModal();
                            changeColor(currentData);
                            $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');
                            if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
                                $('.layui-table-body .layui-none').html('<img src="../image/table-none.png" alt="">无数据');
                            }else if($('.layui-table-main.layui-table-body>table').height() < $('.layui-table-main.layui-table-body').height()) {
                                $('.layui-table-main.layui-table-body').height($('.layui-table-main.layui-table-body>table').height());
                                $('.layui-table-view').height($('.layui-table-main.layui-table-body>table').height() + 88);
                            }
                        }
                    });
                }
            },
            error: function (err) {
                delAjaxErrorMsg(err);
            }
        });


        //筛选相同项
        form.on('switch(switchXt)', function(data){
            var dealData=[];
            if(data.elem.checked){
                currentData.forEach(function (val,index) {
                    if(val.sfxd!=1){
                        dealData.push(val);
                    }
                })
            }else{
                dealData=currentData;
            }
            table.reload('xxbdTable', {
                data: dealData,
                done: function () {
                    changeColor(dealData);
                }
            });
        });



    });
});
/**
 * 根据返回值改变表格背景颜色方法
 * @param dataArr 返回数据
 */
function  changeColor(dataArr){
    var colorIndex=""; //需要改变背景色的下标
    if (dataArr) {
        for (var i = 0; i < dataArr.length; i++) {
            if (dataArr[i].sfxd == 0) {//判断值
                colorIndex = i;//获取下标
                if (colorIndex!=="") {
                    var changediv = ".layui-table-main tr:nth-child(" + (colorIndex + 1) + ")";
                    $(changediv).css("background-color", "#FF0000");
                }
            }
        }

    }
}

