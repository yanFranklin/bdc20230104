// 存量房买卖转移与按揭流程创建项目时，需要先查询合同备案信息，在创建项目时验证当前项目的合同监管状态。
// 规则:【南通】验证合同备案状态 用于获取不动产权证号内容。 实际的验证合同状态判断在yztsxx.js中进行比对处理
function initHtjgxxTable() {
    layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
        var $ = layui.jquery;
        var form = layui.form;
        var table = layui.table;

        var cxObj = {
            beanName : "nt_clfhtxx"
        };
        //不动产单元号的表头
        var unitTableTitle = [
            {field: 'htbh', title: '合同编号', width: "15%"},
            {field: 'msr', title: '买方', width: "15%", templet: function(d){
                    var msrArray = [];
                    $.each(d.msr,function(index, value){
                        msrArray.push(value.msrmc);
                    });
                    return msrArray.join(",");
                }},
            {field: 'cmr', title: '卖方', width: "15%", templet: function(d){
                    var cmrArray = [];
                    $.each(d.cmr,function(index, value){
                        cmrArray.push(value.cmrmc);
                    });
                    return cmrArray.join(",");
                }},
            {field: 'fw', title: '坐落', width: "35%",sort: true, templet: function(d){
                    var fwzl = [];
                    $.each(d.fw,function(index, value){
                        fwzl.push(value.zl);
                    });
                    return fwzl.join(",");
                }},
            {field: 'htzt', title: '合同状态', templet: function(d){
                    return getHtztmc(d.htzt);
                }},
            {field: 'sfdk', title: '买房是否有贷款', templet: function(d){
                    return getSfdk(d.sfdk);
                }}
        ];

        //提交表单
        form.on("submit(queryHtjgxx)", function (data) {
            if(!isNotBlank(data.field.htbh)){
                ityzl_SHOW_WARN_LAYER("请输入合同编号");
                return;
            }
            addModel();
            var url = getContextPath() + '/ycsl/jyxx/listFcjyxxByPage';
            cxObj.htbh = data.field.htbh;
            table.reload("htjgxx", {
                url: url,
                where: cxObj,
                page: {
                    curr: 1, //重新从第 1 页开始
                    limits: [10, 50, 100, 200, 500]
                },
                done: function (res) {
                    console.info(res.content);
                    htjgxx = []; // 先将全局变量合同监管信息置空
                    if(res.content.length >0){
                        var obj = [];
                        $.each(res.content, function(index, val){
                            if(val.fw.length >0){
                                $.each(val.fw, function(index, fwxx){
                                    if(isNotBlank(fwxx.bdcqzh)){
                                        obj.push({
                                            bdcqzh: fwxx.bdcqzh,
                                            htzt: val.htzt,
                                            htbh: val.htbh
                                        });
                                    }
                                });
                            }
                        });
                        htjgxx = obj;
                    }
                    removeModal();
                    $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
                    reverseTableCell(reverseList, "htjgxx");
                    if ($('.bdc-form-div .layui-show .bdc-table-box .layui-table-main>.layui-table').height() === 0) {
                        $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
                    }
                }
            });
            return false;
        });

        var tableConfig = {
            id: 'htjgxx',
            toolbar: "#toolbarHtjgxx",
            defaultToolbar: ['filter'],
            cols: [unitTableTitle],
            done: function () {
                var reverseList = ['zl'];
                reverseTableCell(reverseList,'htjgxx');
                //无数据时显示内容调整
                if($('.layui-show .layui-table-body>.layui-table').height() == 0){
                    $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
                }
            }
        };

        //加载表格
        loadDataTablbeByUrl('#htjgxxList', tableConfig);
        //表格初始化
        table.init('htjgxxList', tableConfig);
        //监听排序事件
        setColorAfterZl('htjgxxList');

        function getHtztmc(htzt){
            var mc = "";
            switch(htzt){
                case "01":
                    mc =  "已备案资金全部到账";
                    break;
                case "02":
                    mc =  "已备案免监管";
                    break;
                case "03":
                    mc =  "未备案";
                    break;
            }
            return mc;
        }
        function getSfdk(sfdk){
            var mc = "";
            switch(sfdk){
                case "0":
                    mc =  "否";
                    break;
                case "1":
                    mc =  "是";
                    break;
            }
            return mc;
        }
    });
}