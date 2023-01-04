var bgbh= $("#bgbh").val();
var layerIndex = "";
layui.use(['jquery', 'layer', 'laytpl', 'table'], function () {
    var laytpl = layui.laytpl;
    var table = layui.table;
    var form = layui.form;
    layerIndex = parent.layer.getFrameIndex(window.name);

    //第一个实例
    var tableConfig = {
        url: '../fwhsbg/zhslist?fwHsIndexList=' + encodeURI(fwhsIndexList)
        , page: false //开启分页
        , cols: [[ //表头
            {type: 'checkbox', fixed: 'left', align: 'center',LAY_CHECKED:true, width: '10%'}
            , {field: 'dyh', title: '单元号', width: '15%'}
            , {field: 'wlcs', title: '物理层数', width: '20%'}
            , {field: 'fjh', title: '房间号', width: '20%'}
            , {field: 'fjlx', title: '户室类型', width: '20%'}
            , {field: 'zhsfjh', title: '主户室房间号', width: '15%'}
        ]]
    }

    //加载表格
    loadDataTablbeByUrl("#hbList", tableConfig);

    form.on("submit(hsbg)", function (data) {
        var checkStatus = table.checkStatus('hbList');
        var hbYfwhs = [];
        for (var i = 0; i < checkStatus.data.length; i++) {
            var fjlx=checkStatus.data[i].fjlx
            if(fjlx == '主户室'){
                fjlx ="FW_HS"
            }else {
                fjlx="FW_ZHS"
            }
            hbYfwhs.push(checkStatus.data[i].hsindex+fjlx);
        }
        parent.layer.close(layerIndex);
        parent.$.hsbg._fwhsHbMain(hbYfwhs);
    })
})