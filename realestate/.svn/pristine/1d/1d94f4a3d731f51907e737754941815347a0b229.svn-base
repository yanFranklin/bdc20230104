/**
 * Created by ypp on 2020/3/3.
 * 详细配置js
 */
layui.use(['form','layer','table','laytpl'], function(){
    var form = layui.form,
        $ = layui.jquery,
        layer = layui.layer,
        table = layui.table,
        laytpl = layui.laytpl;

    $(function(){
        var getBgData;
        if(sessionStorage.tsywpzBgData != undefined){
            getBgData = JSON.parse(sessionStorage.tsywpzBgData);
        }
        $('.bdc-text-pzsm').html(getBgData.pzsm);
        $('.bdc-container').css('padding-top',$('.bdc-tips-words').outerHeight() + 'px');
        var tableData = [];
        console.log(getBgData.pzz);
        if(getBgData.pzz != undefined){
            var ysJson = JSON.parse(getBgData.pzz);
            for(var i in ysJson){
                tableData.push({key: i,value: ysJson[i]});
            }
        }

        var getTableTpl = tableTpl.innerHTML;
        laytpl(getTableTpl).render(tableData, function(html){
            $('.bdc-form-table tbody').html(html);
            form.render('select');
        });

        //单击 新增
        $('.bdc-add-tr-btn').on('click',function(){
            var getTrTpl = addTrTpl.innerHTML;
            laytpl(getTrTpl).render([], function(html){
                if($('.bdc-form-table tbody .bdc-table-none').length > 0){
                    $('.bdc-form-table tbody .bdc-table-none').remove();
                }
                $('.bdc-form-table tbody').append(html);
            });
        });
        //单击表格内删除
        $('.bdc-table-box').on('click','.bdc-delete-btn',function(){
            var $this = $(this);
            if($this.parents('tbody').find('tr').length == 1){
                $this.parents('tbody').append('<tr class="bdc-table-none">' +
                    ' <td colspan="4"> ' +
                    '<div class="layui-none">' +
                    '<img src="../../../static/lib/bdcui/images/table-none.png" alt="">无数据 ' +
                    '</div> ' +
                    '</td> ' +
                    '</tr>');
            }
            $this.parents('tr').remove();
            savePzzWithGzyz('delete');
        });

        //单击 保存
        $('.bdc-table-save').on('click',function(){
            savePzzWithGzyz('save');

        });

        //保存配置值(包括规则验证)
        function savePzzWithGzyz(type){
            //获取配置值
            addModel();
            var tableList = {};
            var tableArr = $(".bdc-table-data").serializeArray();
            tableArr.forEach(function (item,index) {
                if(item.name == "bgKey" &&isNotBlank(item.value) &&isNotBlank(tableArr[index + 1].value)){
                    tableList[item.value] = tableArr[index + 1].value;
                }
            });
            var pzz=JSON.stringify(tableList);
            if(isNotBlank(pzz)){
                if(isNotBlank(getBgData.pzyzzhbs)) {
                    //调用规则验证
                    var bdcGzYzQO = {};
                    bdcGzYzQO.zhbs = getBgData.pzyzzhbs;
                    var gzyzParamMap = {};
                    gzyzParamMap.pzz = pzz;
                    bdcGzYzQO.paramMap = gzyzParamMap;
                    gzyzCommon(bdcGzYzQO, function (data) {
                        console.log("验证通过");
                        //保存方法
                        savaBgPz(pzz,type);
                    });
                }else{
                    //保存方法
                    savaBgPz(pzz,type);
                }
            }
        }

        function savaBgPz(pzz,type){
            $.ajax({
                type: 'PUT',
                url: getContextPath() +"/rest/v1.0/tsywpz/update",
                contentType: 'application/json',
                dataType: "json",
                data: JSON.stringify({pzid: getBgData.pzid,pzz: pzz}),
                success: function (data) {
                    removeModal();
                    if(data == 1){
                        if(type ==="delete") {
                            delSuccessMsg();
                        }else{
                            saveSuccessMsg();
                        }
                        sessionStorage.tsywpzBgIsChange = true;
                    }
                }
            });
        }
    });
});