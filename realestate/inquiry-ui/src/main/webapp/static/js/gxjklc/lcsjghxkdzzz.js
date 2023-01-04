/**
 *  获取规划许可电子证照列表
 */
layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});
var reverseList = ['zl'];
var gzlslid = $.getUrlParam("processInsId");
layui.use(['jquery', 'element', 'form', 'table', 'laydate', 'formSelects'], function () {
    //获取字典
    var $ = layui.jquery;
    var form = layui.form;
    var table = layui.table;
    var laytpl = layui.laytpl;
    var formSelects = layui.formSelects;

    form.render();

    // 当前页数据
    var currentPageData = new Array();
    $(function () {
        // 获取页面表单标识，用于权限控制
        var moduleCode = $.getUrlParam('moduleCode');
        // 加载Table
        table.render({
            elem: '#bdcdyTable',
            toolbar: '#toolbarDemo',
            title: '获取规划许可电子证照列表',
            defaultToolbar: ["filter"],
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            even: true,
            cols: [[
                {field: 'xh', type: 'numbers', width: 60, title: '序号'},
                {field: 'licenseNo', title: '证照标识', width: 200, hide: true},
                {field: 'licenseTypeCode', minWidth: 280, title: '证照类型编码', hide: true},
                {field: 'licenseTypeName', minWidth: 250, title: '证照类型名称'},
                {field: 'licenseName', minWidth: 280, title: '证照名称'},
                {field: 'licenseNumber', minWidth: 200, title: '证照编号'},
                {field: 'holderName', minWidth: 250, title: '持证者名称'},
                {field: 'deptName', minWidth: 200, title: '事项所属单位'},
                {field: 'deptOrganizeCode', title: '事项所属单位编码', width: 200, hide: true},
                {field: 'projectName', minWidth: 250, title: '业务名称'},
                {field: 'fileName', minWidth: 250, title: '文件名称'},
                {field: 'certificateDate', minWidth: 180, title: '发证日期'},
                {field: 'state', minWidth: 180, title: '是否签章'},
                {fixed: 'right', title:'操作', templet:"#barAction2", width: 150}
                ]],
            data: [],
            page: true  ,
            parseData: function (res) {
                return {
                    code: res.code, //解析接口状态
                    msg: res.msg, //解析提示文本
                    count: res.totalElements, //解析数据长度
                    data: res.content //解析数据列表
                }
            },
            done: function () {
                setHeight();
                reverseTableCell(reverseList);
                // 控制按钮权限
                if(moduleCode){
                    setElementAttrByModuleAuthority(moduleCode);
                }
            }
        });

        /**
         * 点击查询
         */
        $('#search').on('click', function () {
            search();
        });
        // 回车查询
        $('.required').on('keydown', function (event) {
            if (event.keyCode == 13) {
                search();
            }
        });

        //查询
        function search() {
            // 判断必填条件
            var count = 0;
            $(".required").each(function (i) {
                if (!isNullOrEmpty($(this).val())) {
                    count += 1;
                }
            });
            var key = formSelects.value('select07', 'val');

            if (0 == count) {
                layer.alert("<div style='text-align: center'>请输入必要查询条件！</div>", {title: '提示'});
                return false;
            }

            // 获取查询参数
            var obj = {};
            $(".search").each(function (i) {
                var name = $(this).attr('name');
                var value = $(this).val();
                if (value) {
                    value = value.replace(/\s*/g, "");
                }
                if(isNotBlank(value)){
                    obj[name] = value;
                }
            });

            addModel();
            // 重新请求
            table.reload("bdcdyTable", {
                url: "/realestate-inquiry-ui/rest/v1.0/gx/shucheng/queryGhxk/list"
                , where: {queryMap: JSON.stringify(obj)}
                , page: {
                    curr: 1, //重新从第 1 页开始
                    limits: [10, 50, 100, 200, 500]
                }
                , done: function (res, curr, count) {
                    currentPageData = res.data;
                    reverseTableCell(reverseList);
                    removeModal();
                    setHeight();
                }
            });
        }

        /**
         * 重置清空查询条件
         */
        $('#reset').on('click', function () {
        });

        //监听工具条
        table.on('tool(bdcdyTable)', function (obj) {
            var data = obj.data;

            if (obj.event === 'xxck') {
                xxck(data);
            }
            if (obj.event === 'fjxz') {
                fjxz(data);
            }
        });

    });


    function xxck(data){
        addModel();
        $.ajax({
            url:"/realestate-inquiry-ui/rest/v1.0/gx/shucheng/getDzzz/base64?licenseNo=" + data.licenseNo,
            type: "POST",
            contentType: 'application/json',
            success: function (data) {
                removeModal();
                if (isNotBlank(data)) {
                    showFile(data)
                    successMsg("下载成功");
                } else {
                    warnMsg(data.fail.message);
                }
            },
            error: function (xhr, status, error) {
                removeModal();
                delAjaxErrorMsg(xhr);
            }
        });
    }

    function fjxz(data){
        addModel();
        $.ajax({
            url:"/realestate-inquiry-ui/rest/v1.0/gx/shucheng/Savefjxx",
            type: "GET",
            data: {gzlslid: gzlslid, licenseNo: data.licenseNo},
            contentType: 'application/json',
            success: function (data) {
                removeModal();
                if (data.code == '1') {
                    successMsg("下载成功");
                } else {
                    warnMsg(data.fail.message);
                }
            },
            error: function (xhr, status, error) {
                removeModal();
                delAjaxErrorMsg(xhr);
            }
        });
    }

    // base64字符串下载
    function showFile(data) {
        var gzsnr = data.replace(/\s*/g,""),
            fileName = "证照信息.ofd",
            mime = "application/octet-stream";
        var byte = base64ToByte(gzsnr); // base64编码字符串转换二进制流
        if(isIE()){ //修复IE10无法使用Blob进行文件下载
            window.navigator.msSaveOrOpenBlob(new Blob([byte], { type: mime }),fileName);
        }else{
            var reader = new FileReader();
            var blob = new Blob([byte]);
            reader.readAsDataURL(blob);
            reader.onload = function (e) {
                // 转换完成，创建一个a标签用于下载
                var a = document.createElement('a');
                a.download = fileName;
                a.href = e.target.result;
                $("body").append(a);    // 修复firefox中无法触发click
                a.click();
                $(a).remove();
            }
        }
    }

    //判断是否IE浏览器
    function isIE() {
        if (!!window.ActiveXObject || "ActiveXObject" in window) {
            return true;
        } else {
            return false;
        }
    }

    // base64解码转为二进制流
    function base64ToByte(base64Str) {
        var decodeStr = atob(base64Str);
        var len = decodeStr.length;
        var byte = new Uint8Array(len);
        while(len--){
            byte[len] = decodeStr.charCodeAt(len);
        }
        return byte;
    }

});



