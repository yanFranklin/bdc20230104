/**
 * 非税配置修改页面 js
 */

var fspzid = $.getUrlParam('fspzid');
var form;
var formSelects;
var moduleCode = $.getUrlParam('moduleCode');
var isSubmit = true;

//人员下拉框
var ryselecList = "/realestate-inquiry-ui/slqktj/ry/select";
// 人员下拉数组
var bjrySelList = [];

layui.use(['layer', 'table', 'jquery', 'form'], function () {
    var layer = layui.layer;
    var $ = layui.jquery;
    var table = layui.table;
    form = layui.form;
    formSelects = layui.formSelects;
    custom_tree = layui.custom_tree;

    var verifyMsg = "";
    var isSubmit = true;
    $(function () {
        form.verify({
            required: function (value, item) {
                if (value == '' || value == null || value == undefined) {//判断是否为空
                    verifyMsg = "必填项不能为空";
                    $(item).addClass('layui-form-danger');
                    isSubmit = false;
                }
                // 判断部门树形结构和人员多选框是否为空
                var bmid = $("#bmid").attr("value");
                var rydata = formSelects.value("selectBjry");
                if (rydata && rydata.length == 0) {
                    verifyMsg = "人员不能为空";
                    isSubmit = false;
                }
                if (bmid == '' || bmid == null || bmid == undefined) {
                    //判断是否为空
                    verifyMsg = "部门不能为空";
                    isSubmit = false;
                }

            }
        });

        addModel();
        setTimeout(function () {
            try {
                $.when(generateFspzxx()).done(function () {
                    removeModal();
                })
            } catch (e) {
                removeModal();
                delAjaxErrorMsg(e, "加载失败");
                return
            }
        }, 10);

        form.on("submit(saveFspz)", function (data) {
            if (!isSubmit) {
                layer.msg(verifyMsg, {icon: 5, time: 3000});
                isSubmit = true;
                return false;
            } else {
                saveFspz();
                return false;
            }
        });


    });


});


/**
 * 加载非税配置编辑页面
 */
function generateFspzxx() {
    if (isNotBlank(fspzid)) {
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/fspz/query",
            type: 'GET',
            dataType: 'json',
            async: false,
            data: {fspzid: fspzid},
            success: function (data) {
                if (moduleCode) {
                    setElementAttrByModuleAuthority(moduleCode);
                }
                // 表单赋值
                form.val("searchform", data);
                // 多选框赋值,先加载下拉框，再选中
                var ryData = [];
                ryData.push({"name": data.alias, "value": data.userid + "," + data.username, "selected": "selected"})
                formSelects.data('selectBjry', 'local', {
                    arr: ryData
                });
                form.render();
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        })
    } else {
        var nf = (new Date).getFullYear();
        $("input[name='nf']").val(nf);
    }
}


/**
 * 非税的保存或修改
 */
function saveFspz() {

    // 获取人员信息userid，username，alias
    var rydata = [];
    // 请求参数
    var fspzDO = [];
    // 多选框选择参数
    rydata = formSelects.value("selectBjry");

    if (rydata && rydata.length > 0) {
        for (var i = 0; i < rydata.length; i++) {
            var obj = {};
            $(".search").each(function (i) {
                var name = $(this).attr('name');
                var value = $(this).val();
                if (value) {
                    value = value.replace(/\s*/g, "");
                }
                obj[name] = value;
            });
            obj.alias = rydata[i].name;
            // value是userid和username的拼接
            var value = rydata[i].value.split(",");
            obj.userid = value[0];
            obj.username = value[1];
            fspzDO.push(obj);
        }
    }


    addModel();
    console.log(fspzDO);


    // 如果fspzid不为空，是修改页面
    if (isNotBlank(fspzid)) {
        var updateFsDO = fspzDO[0];
        updateFsDO.fspzid = fspzid;
        //修改
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/fspz/update",
            type: 'POST',
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(updateFsDO),
            success: function (data) {
                removeModal();
                setTimeout(removeModal(), 100);
                parent.layui.table.reload('pageTable', {page: {curr: 1}});
                if (data && data > 0) {
                    layer.msg("修改成功");
                }
                //关闭当前页
                var index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);


            }, error: function (xhr, status, error) {
                removeModal();
            }
        });


    } else {
        //新增
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/fspz/save/pl",
            type: 'POST',
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(fspzDO),
            success: function (data) {
                removeModal();
                setTimeout(removeModal(), 100);
                parent.layui.table.reload('pageTable', {page: {curr: 1}});
                layer.msg("新增" + data + "条数据");
                var index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);

            }, error: function (xhr, status, error) {
                removeModal();
            }
        });

    }


}

function cancel() {
    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
    parent.layer.close(index); //再执行关闭
}


