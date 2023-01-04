
layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});

var formSelects;
var yhList;
var yharr;
layui.use(['form', 'jquery', 'element', 'table', 'laydate', 'upload','formSelects'], function () {
    var laydate = layui.laydate;
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    formSelects = layui.formSelects;

    yhList = listBdcXtJg();
    yharr = [];
    if (isNotBlank(yhList)) {
        $.each(yhList, function (index, item) {
            var yhobject = new Object();
            yhobject.name = item.jgmc;
            yhobject.value = item.jgid;
            yharr.push(yhobject);
        });
    }

    formSelects.data('jgid', 'local', {
        arr: yharr
    });
    // 获取页面表单标识，用于权限控制
    var moduleCode = $.getUrlParam('moduleCode');


    //x 的事件
    form.on('select', function (data) {
        if($(this).text() == "请选择"){
            $(this).parents('.layui-input-inline').find('.reseticon').hide();
        }else{
            $(this).parents('.layui-input-inline').find('.reseticon').show();
        }
    });
    $('.reseticon').on('click',function(item){
        $(this).parent().find("option:contains('请选择')").attr("selected", "selected");
        $(this).hide();
        layui.form.render("select");
    })
    $('#reset').on('click',function(item){
        $('.bdc-percentage-container').find('.layui-form')
            .find('select').find("option:eq(0)")
            .attr("selected","selected");
        setTimeout($('.bdc-percentage-container').find('.layui-form')
            .find('select').parent().find('input').val(''),100);
        $('.reseticon').hide();

    })

   /* formSelects.render('jgmc', {
        create: function (id, name) {
            console.log(id);    //多选id
            console.log(name);  //创建的标签名称
            return name;  //返回该标签对应的val
        }
    });*/
    cols = [
        {type: 'gxid', title: 'gxid', hide: true},
        {type: 'numbers', field: 'xh', title: '序号', width: 70, fixed: 'left'},
        {field: 'jgmc',title: '机构名称', width: 300},
        {field: 'lzrmc', title: '领证人名称', width: 180},
        {field: 'lzrzjzlmc', title: '领证人证件种类', width: 180},
        {field: 'lzrzjh', title: '领证人证件号', width: 300},
        {field: 'lzrdh', title: '领证人电话', width: 180},
        {title: '删除', fixed: 'right',  toolbar: '#barDemo'}

    ];
// 加载Table
    table.render({
        elem: '#bdcJglzrTable',
        toolbar: '#toolbar',
        title: '机构领证人',
        defaultToolbar: ['filter'],
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        even: true,
        cols: [cols],
        data: [],
        page: true,
        limits: [10, 20, 50, 100, 200, 500],
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
            if(moduleCode){
                setElementAttrByModuleAuthority(moduleCode);
            }
        }
    });

    // 监听表单提交事件
    form.on('submit(search)', function (data) {
        if (data.field) {

            addModel();
            var obj = {"jgid":formSelects.value('jgid', 'val').join(","),"lzrmc":data.field.lzrmc};
            console.log(obj);
            table.reload("bdcJglzrTable", {
                url: '/realestate-inquiry-ui/rest/v1.0/jgpz/queryLzrByPage'
                , where: obj
                , page: {
                    curr: 1  //重新从第 1 页开始
                }
                , done: function (res, curr, count) {
                    currentPageData = res.data;
                    removeModal();
                    setHeight();
                    table.resize('bdcJglzrTable');
                    if(moduleCode){
                        setElementAttrByModuleAuthority(moduleCode);
                    }
                }
            });
        } else {
            layer.msg("请输入查询条件！");
        }
        return false;
    });

    //监听工具条
    table.on('tool(bdcJglzrTable)', function (obj) {
        var data = obj.data;
        if (obj.event === 'delete') {
            var deleteIndex = layer.open({
                type: 1,
                skin: 'bdc-small-tips',
                title: '确认删除',
                area: ['320px'],
                content: '是否确认删除？',
                btn: ['确定', '取消'],
                btnAlign: 'c',
                yes: function () {
                    $.ajax({
                        url: getContextPath() + "/rest/v1.0/jgpz/delete/"+data.gxid,
                        type: 'DELETE',
                        success: function (data) {
                            layer.close(deleteIndex);
                            layer.msg("删除成功,即将刷新页面");
                            window.setTimeout(function(){$("#search").click();},1000)
                        }
                    });

                },
                btn2: function () {
                    //取消
                    layer.close(deleteIndex);
                }
            });
        }
    })
    // 监听表格操作栏按钮
    table.on('toolbar(bdcJglzrTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'add':
                var zdList = getMulZdList("zjzl");
                $.each(zdList.zjzl, function (key, value) {
                    var selectItem = '<option value="'+value.DM+'">'+value.MC+'</option>'
                   $("#addlzrzjzl").append(selectItem)
                });

               /* $.each(yharr, function (key, value) {
                    var selectItem = '<option value="'+value.value+'">'+value.name+'</option>'
                    $("#addlzrjgid").append(selectItem)
                });*/


                /*if (isNotBlank(yhList)) {
                    $.each(yhList, function (index, item) {
                        var yhobject = new Object();
                        yhobject.name = item.jgmc;
                        yhobject.value = item.jgid;
                        yharr.push(yhobject);
                    });
                }*/

                formSelects.data('addlzrjgid', 'local', {
                    arr: yharr
                });

                form.render();

                layer.open({
                    title: '新增领证人',
                    type: 1,
                    area: ['980px','450px'],
                    btn: ['提交', '取消'],
                    content: $('#bdc-popup-large')
                    ,yes: function(index, layero){

                        var addJgid = formSelects.value('addlzrjgid', 'val').join(",");

                        if(!addJgid){
                            warnMsg("请选择机构！");
                            return
                        }

                        if(addJgid.indexOf(",") >-1){
                            warnMsg("只能选择一个机构！");
                            return
                        }
                        if(!$("#addlzr").val()){
                            warnMsg("请输入领证人名称！");
                            return
                        }
                        if(!$("#addlzrzjzl").val()){
                            warnMsg("请选择领证人证件种类！");
                            return
                        }
                        if(!$("#addlzrzjh").val()){
                            warnMsg("请输入领证人证件号！");
                            return
                        }

                        if($("#addlzrzjzl").val() == "1"){
                            var flag = checkSfzZjh($("#addlzrzjh").val())
                            if(flag != "") {
                                warnMsg(flag);
                                return
                            }
                        }

                        if($("#addlzrdh").val()){
                           var flag = validatePhone($("#addlzrdh").val())
                            if(!flag){
                                warnMsg("电话格式错误！");
                                return
                            }
                        }


                        var obj = {};
                        obj["jgid"] = addJgid;
                        obj["lzrzjzl"] = $("#addlzrzjzl").val();
                        obj["lzrzjh"] = $("#addlzrzjh").val();
                        obj["lzrdh"] = $("#addlzrdh").val();
                        obj["lzrmc"] = $("#addlzr").val();
                        console.log(obj);
                        addModel();
                        insertJglzr(obj);
                        clearAllInput();

                    }
                    ,btn2: function(index, layero){
                        //取消 的回调
                        clearAllInput()

                    }
                    ,cancel: function(){
                        //右上角关闭回调
                        //return false 开启该代码可禁止点击该按钮关闭
                        clearAllInput()
                    }
                    ,success: function () {
                        clearAllInput()
                    }
                });
                break;
        }
    });

    function clearAllInput(){
        $("#addlzrjgid").find("option:eq(0)").attr("selected","selected");
        $("#addlzrzjzl").find("option:eq(0)").attr("selected","selected");
        $("#addlzrzjh").val('');
        $("#addlzrdh").val('');
        $("#addlzr").val('');
        form.render();
    }


})

function listBdcXtJg() {
    var yhList;
    $.ajax({
        url: getContextPath() + "/rest/v1.0/jgpz/query/allYgJg",
        type: 'GET',
        dataType: 'json',
        async: false,
        success: function (data) {
            yhList = data
        }
    });
    return yhList;
}

/**
 * 新增lzr
 * @param obj
 */
function insertJglzr(obj){
    $.ajax({
        url: getContextPath() + "/rest/v1.0/jgpz/lzr/save",
        type: 'POST',
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(obj),
        success: function (data) {
            removeModal();
            layer.closeAll();
            successMsg("保存成功,请重新查询");
        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr)
        }
    });
}




