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

var processInsId = $.getUrlParam('processInsId');
var zl = "";

layui.use(['jquery', 'selectN'], function () {
    // 当包含该参数是 说明是从修正流程中打开的 需要带入修正流程的 参数（bdcqzh zl）
    if(processInsId){
        getXzlcparam();
    }

    $ = layui.jquery;
    selectN = layui.selectN;
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
            // 如果未成功过滤则默认展示所有的信息
            if (isNullOrEmpty(result)) {
                result = data;
                result.forEach(function (value) {
                    if (value.children != null && value.children.length > 0) {
                        value.children.forEach(function (v) {
                            v.children = v.processList;
                            if (v.children != null && v.children.length > 0) {
                                v.children.forEach(function (m) {
                                    m.id = m.processDefKey;
                                })
                            }
                        })
                    }
                })
            }
            processs = selectN({
                elem: '#process',
                search: [true, true, true],
                field: {idName: 'id', titleName: 'name', childName: 'children'},
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


// 带入修正流程的参数
function getXzlcparam(){
    $.ajax({
        url: getContextPath() + '/rest/v1.0/blxx/xz?processInsId=' + processInsId,
        type: 'GET',
        async: false,
        success: function (data) {
            if(data){
                zl = data.zl;
                $("#bdcqzh").val(data.bdcqzh);
            }
        }, error: function (xhr, status, error) {
            removeModel();
            delAjaxErrorMsg(xhr);
        }
    });
}