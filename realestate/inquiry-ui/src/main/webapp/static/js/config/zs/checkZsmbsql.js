/**
 * @author  <a href="mailto:hongqin@gtmap.cn>hongqin</a>
 * @version 1.0, 2022/06/22
 * @description 证书模板sql校验处理JS
 */

layui.use(['jquery', 'table', 'laytpl', 'layer', 'form',], function () {
    var table = layui.table;
    var $ = layui.jquery;

    var sqlData = layui.sessionData('sqlData');
    var sqlstrData = JSON.parse(sqlData.data);
    var sqlarr=[];//存储sql以#分割后的数组
    var csmc=[];//存储参数名称
    sqlarr=sqlstrData.split("#");
    var row=0;
    for(var i=1;i<sqlarr.length;i++){
        if( csmc.indexOf(sqlarr[i].slice(1,sqlarr[i].indexOf("}")))===-1){
            csmc[row]=sqlarr[i].slice(1,sqlarr[i].indexOf("}"));
            row++;
        }
    }
    var csid=1;
    for(i=0;i<csmc.length;i++){
        layui.use(['laytpl', 'form'], function () {
            var laytpl = layui.laytpl;
            var form = layui.form;
            laytpl(mbsqlcsRowTemplet.innerHTML).render({
                csid: csid,
                csmc: csmc[i]
            }, function (html) {
                $('#mbsqlcsTable').append($(html));
            });
            form.render();
        });
        csid++;
    }

    //编辑完成更新数据
    table.on('edit(mbsqlcsTable)', function (obj) {
        var mbsqlcsTable = table.cache.mbsqlcsTable;
    });

    /**
     * 执行验证
     */
    $("#check").click(function () {
        addModel();
        var sqlcsmc=[];
        var sqlcsz=[];
        for(row=1;row<csmc.length+1;row++){
            sqlcsmc[row-1]=mbsqlcsTable.rows[row].cells[0].children[0].value;
            if(mbsqlcsTable.rows[row].cells[1].children[0].value===""){
                sqlcsz[row-1]="-1";
            }else {
                sqlcsz[row-1]=mbsqlcsTable.rows[row].cells[1].children[0].value;
            }
        }
        var jsonT = {"sql":Base64.encode(sqlstrData),"csmc":sqlcsmc.toString(),"csz":sqlcsz.toString()};
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/zsmbpz/check",
            type: "POST",
            data:JSON.stringify(jsonT),
            contentType: 'application/json;charset=utf-8',
            dataType: "json",
            success: function (data) {
                var flag=0;
                for(flag;flag<data.length;flag++){
                    if(data[flag]!=="[]"){
                        break;
                    }
                }
                if(flag===data.length){
                    $("#jyjg").val('[ 当前证书模板sql验证结果为空! ]');
                }else {
                    $("#jyjg").val(formatJson(JSON.stringify(data)));
                }
            },
            error:function($xhr,textStatus,errorThrown){
                $("#jyjg").val(formatJson($xhr.responseText));
            },
            complete: function () {
                removeModal();
            }
        });
    });
});

/**
 * 关闭弹出页面
 */
window.closeWin = function(){
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

window.closeParentWindow = function(){
    var index = parent.parent.layer.getFrameIndex(window.name);
    parent.parent.layer.close(index);
};