var form,laytpl;
var processInsId = getQueryString("processInsId");
//收件材料类型列表,默认值
var zdList = {"sjlx":[]};
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    var table = layui.table;
    laytpl = layui.laytpl;
    form = layui.form;

    $(function () {
        //获取收件材料类型list
        getReturnData("/bdczd", '', 'POST', function (data) {
            if (isNotBlank(data)) {
                zdList = data;
            }
        }, function (err) {
            console.log(err);
        },false);
        //监听复选框选择
        //全选
        form.on('checkbox(qxCheckbox)', function (data) {
            $('td input[name=yxTable]').each(function (index, item) {
                item.checked = data.elem.checked;
                var qxData = item.value;
                //如果是选中的则添加，否则全部删除
                if (item.checked) {
                    sjclids.add(qxData)
                } else {
                    sjclids.delete(qxData);
                }
            });
            form.render('checkbox');
        });
        //单个的
        form.on('checkbox(yxCheckbox)', function (data) {
            var checkedLength = $('td .yx+.layui-form-checked[lay-skin=primary]').length;
            var checkBoxLength = $('td .yx+.layui-form-checkbox[lay-skin=primary]').length;
            var qxCheckBox = $('.gray-tr input[name=qxTable]')[0];
            var sjclid = data.value;
            if (sjclids.has(sjclid)) {
                sjclids.delete(sjclid);
            } else {
                sjclids.add(sjclid);
            }
            //判断是否全选，全选的时候勾选最上面的复选框
            if (checkedLength == checkBoxLength) {
                qxCheckBox.checked = true;
            } else {
                qxCheckBox.checked = false;
            }
            form.render('checkbox');
        });

        //打印收件单
        $("#printSjd").click(function () {
            printData('qjdcsjd');
        });


    });
});

//获取表单基本信息并渲染到页面
function loadSmxx(){
    getReturnData("/slym/xm/queryBdcSlJbxxByGzlslid",{"processInsId":processInsId}, "GET", function (data) {
        jbxxid =data.jbxxid;
        //console.log(data);
        //时间只保留年月日
        if(data.slsj != null) {
            data.slsj = data.slsj.substring(0, 10);
        }
        //获取xmid、djxl
        getReturnData("/gwc/list/bdcslxm", {jbxxid: jbxxid}, 'GET', function (data) {
            if (isNotBlank(data) &&data.length >0) {
                xmid = data[0].xmid;
                djxl = data[0].djxl;
            }
        }, function (err) {
            console.log(err);
        },false);
        data.djyyList = [];
        //登记原因列表
        if (isNotBlank(djxl)) {
            //查看登记原因
            getReturnData("/slym/xm/queryDjxlDjyy", {djxl: djxl}, 'GET', function (item) {
                if (isNotBlank(item)) {
                    data.djyyList = item;
                }
            }, function (err) {
                console.log(err);
            }, false);
        }
        var getTpl = jbxxTpl.innerHTML;
        laytpl(getTpl).render(data, function(html){
            $('.bdc-jbxx').html(html);
        });
    },function (error) {
        delAjaxErrorMsg(error);

    });
}
//获取收件材料信息
function loadSjcl() {
    getReturnData("/slym/sjcl/list/pl", {processInsId: processInsId}, 'GET', function (data) {
        generateSjcl(data,xmid);
    }, function (err) {
        console.log(err);
    });
}
//组织收件材料到页面
function generateSjcl(data,xmid) {
    sjclNumber = data.length;
    var json = {
        xmid: xmid,
        bdcSlSjclDOList: data,
        zd: zdList
    };
    var tpl = sjclTpl.innerHTML;
    laytpl(tpl).render(json, function (html) {
        $('.bdc-sjcl-view').html(html);
    });
    form.render();
    getStateById(readOnly, formStateId, 'slymqjdc');
    form.render('select');
    disabledAddFa("sjclForm");
}

//附件上传（批量上传)
function scfjqb() {
    getReturnData("/sjcl/folder", {gzlslid: processInsId, wjjmc: "人像采集"}, 'GET', function (data) {
        if (isNullOrEmpty(data)) {
            layer.msg("人像采集文件夹生成失败");
        }
    }, function (err) {
        delAjaxErrorMsg(err)
    }, false);
    uploadSjcl("");
}
