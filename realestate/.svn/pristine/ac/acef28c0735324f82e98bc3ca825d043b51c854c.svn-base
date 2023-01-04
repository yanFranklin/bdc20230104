layui.config({
    base: '../../' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'static/lib/form-select/formSelects-v4',
    selectN: 'static/js/xxbl/selectN'
});

var edition = 'hf';
var $,selectN;
// 可以选择不动产单元的流程
var choosebdcdyh = ["转移登记","更正登记"];
layui.use(['jquery', 'selectN','form'], function () {
    $ = layui.jquery;
    selectN = layui.selectN;
    form = layui.form
});


/**
 * 0. 获取登记流程列表，渲染级联下拉框（二级分类）
 */
function listCategoryProcess() {
    $.ajax({
        type: "GET",
        url: getContextPath() + "/rest/v1.0/blxx/blxz/process",
        async: false,
        success: function (data) {
            var result = [];
            var flag = 0;
            data.forEach(function (v) {
                if (v.name != "组合登记") {
                    result[flag] = v;
                    flag++;
                }
            });
            // 如果未成功过滤则默认展示所有的信息
            if (isNullOrEmpty(result)) {
                result = data;
            }
            // processs = selectN({
            //     elem: '#process',
            //     search: [true, true],
            //     showNumber: 2, //几级，默认3级
            //     data: result
            // })
            // $("#process .layui-select-title:eq(0)").css({"width":"100px!important"})
            // 渲染一级下拉框
            data.forEach(function (item) {
                if(item.name != "组合登记"){
                    $("#lcdl_select").append("<option  value='" + item.id + "'>" + item.name + "</option>");
                }
            });

            // 监听一级下拉框 渲染二级下拉框
            form.on('select(lcdl_select)', function () {
                hideTable();

                $("#lcxl_select").val("")
                $("#lcxl_select").empty()
                $("#lcxl_select").append("<option value=''>请选择</option>");
                var id = $("#lcdl_select").val();
                data.forEach(function (item) {
                    if(id==item.id) {
                        var list = item.processList;
                        list.forEach(function (item) {
                            $("#lcxl_select").append("<option  value='" + item.processDefKey + "'>" + item.name + "</option>");
                        });
                    }
                });
                form.render('select');
            });


            // 监听二级下拉框
            form.on('select(lcxl_select)', function () {
                showXnbdcdyh($("#lcxl_select").val());
                hideTable();
            });

        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

/**
 * 判断是否可以打开选择不动产单元台账
 */
function checkIsOpen(tzlx, content) {
    if (choosebdcdyh.indexOf(processs.firstName) != -1 || xztzlx.indexOf(tzlx) != -1) {
        var index = layer.open({
            type: 2,
            title: '选择不动产单元',
            maxmin: true,
            area: ['1150px', '600px'],
            content: content+'&choosebdcdyh=cq'
        });
        layer.full(index);
    } else if (tzlx === "1") {
        warnMsg("此流程不可以选择不动产单元！");
    } else if (tzlx === "2") {
        warnMsg("此流程不可以选择不动产权证");
    } else if (tzlx === "3") {
        warnMsg("此流程不可以选择查封");
    } else {
        warnMsg("选择台账未配置，请手动输入信息！");
    }
}

//隐藏表格 显示提示图标 清空已选单元号数据
function hideTable(){
    if($(".xzcf") && $(".xzcf").length > 1){
        $(".xzcf").hide();
    }
    if($(".xzbdcdyh") && $(".xzbdcdyh").length > 1){
        $(".xzbdcdyh").hide();

    }
    if($(".xzcq") && $(".xzcq").length > 1){
        $(".xzcq").hide();
    }
    $(".bdc-tip").show();
    $("#bdcqzh").val("");
    $("#ycqzh").val("");
    $("#bdcdyh").val("");

}

function showXnbdcdyh(gzldyid) {
    if (isNullOrEmpty(gzldyid)) {
        return;
    }
    if (scxnbdcdyh == false && (xnbdcdyhlckeys != null || xnbdcdyhlckeys != undefined)) {
        if (xnbdcdyhlckeys.indexOf(gzldyid) > -1) {
            $('#xnbdcdyh').removeClass('bdc-hide');
        } else {
            $('#xnbdcdyh').addClass('bdc-hide');
        }
    }
}
