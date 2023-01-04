var bdcdyh= $("#bdcdyh").val();
var fwHsIndex= $("#fwHsIndex").val();
window.onload = function () {
    var json = [];
    addModel()
    $.ajax({
        url: "../fwhs/hsbghistorynew",
        dataType: "json",
        data:{
            bdcdyh:bdcdyh
        },
        async: false,
        success: function (data) {
            removeModal();
            json = data;
        }
    });

    var flowChart = new Flow('mygraph', {
        options: {
            layout: {
                hierarchical: {
                    direction: 'DU' // UD、DU、RL
                }
            }
        },
        nodeClick: function (param) {
            if(param && param.id){
                toView(getIP() + '/fwhs/historyhs?fwHsIndex=' + param.id + "&last=" + param.last,{tabname:"户室基本信息"});
            }
        },
        data: json,
        nodesImage: {
            color: {
                unselectedColor: {
                    nowColor: {
                        fillColor: '#abcdf7',
                        backgroundColor: '#eaf4fe'
                    },
                    preColor: {
                        fillColor: '#d0d5da',
                        backgroundColor: '#eaedf1'
                    }
                },
                selectedColor: {
                    fillColor: '#1d87d1',
                    backgroundColor: '#eaf4fe'
                }
            },
            width: 300,  // 节点宽度
            height: 86   // 节点高度
        }

    });
};

/**
 * 关闭
 */
window.closeWin = function () {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

$("#revokeBg").click(function () {

    if (fwHsIndex) {
        layer.open({
            content: '是否确定撤销本房屋最后一次变更？'
            , btn: ['确定', '取消']
            , yes: function (index, layero) {
                addModel();
                $.ajax({
                    url: "../fwhsbg/revokebg",
                    dataType: "json",
                    traditional: true,
                    data: {
                        fwHsIndex:fwHsIndex
                    },
                    success: function (data) {
                        removeModal();
                        parent.layer.closeAll();
                        parent.layer.msg("撤销成功")
                    },
                    error: function (xhr, status, error) {
                        delAjaxErrorMsg(xhr,this)
                    }
                });
            }
        });
    }
})