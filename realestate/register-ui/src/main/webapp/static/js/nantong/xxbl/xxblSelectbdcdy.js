layui.config({
    base: '../../' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'static/lib/form-select/formSelects-v4',
    selectN: 'static/js/xxbl/selectN'
});

var edition = 'nt';
var $,selectN;
var choosebdcdyh = [];
layui.use(['jquery', 'selectN'], function () {
    $ = layui.jquery;
    selectN = layui.selectN;
});

/**
 * 0. 获取登记流程列表，渲染级联下拉框（三级分类）
 */
function listCategoryProcess() {
    $.ajax({
        type: "GET",
        url: getContextPath() + "/rest/v1.0/blxx/blxz/process",
        async: false,
        success: function (data) {
            var result = [];
            
            // 如果未成功过滤则默认展示所有的信息
            if (isNullOrEmpty(result)) {
                result = data;
                result.forEach(function (value) {
                    if(value.children != null && value.children.length > 0){
                        value.children.forEach(function (v) {
                            v.children = v.processList;
                            if(v.children != null && v.children.length > 0){
                                v.children.forEach(function (m) {
                                    m.id=m.processDefKey;
                                })
                            }
                        })
                    }
                })
            }
            processs = selectN({
                elem: '#process',
                search: [true, true, true],
                field:{idName:'id',titleName:'name',childName:'children'},
                data: result
            })
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
    if (xztzlx.indexOf(tzlx) != -1) {
        var index = layer.open({
            type: 2,
            title: '选择数据',
            maxmin: true,
            area: ['1150px', '600px'],
            content: content
        });
        layer.full(index);
    } else if (tzlx === "1") {
        warnMsg("此流程不可以选择不动产单元！");
    } else if (tzlx === "2") {
        warnMsg("此流程不可以选择不动产权证")
    } else if (tzlx === "3") {
        warnMsg("此流程不可以选择查封");
    } else {
        warnMsg("选择台账未配置，请手动输入信息！")
    }
}