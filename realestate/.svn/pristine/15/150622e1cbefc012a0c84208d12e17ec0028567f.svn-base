var zdData;
var $,laytpl,form;
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    form = layui.form;
    $ = layui.jquery;
    var element = layui.element;
    laytpl = layui.laytpl;
    var isSubmit = true;
    var verifyMsg = "必填项不能为空";
    //保存
    form.on("submit(saveData)", function (data) {
        if (!isSubmit) {
            layer.msg(verifyMsg, {icon: 5, time: 3000});
            isSubmit = true;
            return false;
        } else {
            addModel();
            setTimeout(function () {
                try {
                    $.when(saveYcslxxVo()).done(function () {
                        removeModal();
                        ityzl_SHOW_SUCCESS_LAYER("保存成功");
                    })
                } catch (e) {
                    removeModal();
                    if (e.message) {
                        showAlertDialog(e.message);
                    } else {
                        delAjaxErrorMsg(e);

                    }
                    return
                }
            }, 10);


            return false;

        }

    });

    $(function () {
        addModel();
        //获取字典列表、加载基本信息
        setTimeout(function () {
            try {
                $.when(loadButtonArea(),loadZdData(),loadYcymList()).done(function () {
                    removeModal();
                })
            } catch (e) {
                removeModal();
                delAjaxErrorMsg(e,"加载失败");
                return
            }
        }, 50);
    });

    //按钮加载
    function loadButtonArea() {
        var tpl = buttonAreaTpl.innerHTML, view = document.getElementById('buttonArea');
        //渲染数据
        laytpl(tpl).render({}, function (html) {
            view.innerHTML = html;
        });
        form.render();

    }

    function loadZdData() {
        $.ajax({
            url: getContextPath() + "/bdczd",
            type: 'POST',
            dataType: 'json',
            async: false,
            success: function (data) {
                if (isNotBlank(data)) {
                    zdList = data;
                }
            }
        });
    }

    window.loadYcymList = function (){
        $.ajax({
            url: getContextPath() + "/ycsl/listYcslxxByPage",
            type: 'GET',
            dataType: 'json',
            async: false,
            data: {gzlslid: processInsId,loadpage:false},
            success: function (data) {
                console.log(data);
                generateYcymList(data);
                form.render();

                renderSelect();
                form.render("select");
                renderDate(form);
                removeModal();
            }
        });
    };

    window.generateYcymList =  function(bdcYcslxxVOList) {
        var json = {
            bdcYcslxxVOList: bdcYcslxxVOList,
            zd: zdList
        };
        var tpl = ycListTpl.innerHTML, view = document.getElementById('ycslList');
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
        getStateById(readOnly, formStateId, 'ycymList');
        form.render("select");

    };

});

//查询详情
function showYcym(xmid){
    var url = "/realestate-accept-ui/view/ycsl/ycym.html?xmid=" + xmid +"&processInsId="+processInsId+"&formStateId="+formStateId+"&readOnly="+readOnly;
    var index = layer.open({
        type: 2,
        title: "采集税务信息",
        area: ['1150px', '600px'],
        fixed: false, //不固定
        maxmin: true, //开启最大化最小化按钮
        content: url,
        cancel: function () {
            //刷新页面
            loadYcymList();
        }
    });
    layer.full(index);

}

//列表保存
function saveYcslxxVo(){
    var bdcSlJyxxList =[];
    //循环表格行
    $("#ycslListTable tbody tr").each(function () {
        var bdcSlJyxx={};
        var $tr =$(this);
        var jyxxArray = $tr.find(".htxx");
        if (jyxxArray !== null && jyxxArray.length > 0) {
            jyxxArray.serializeArray().forEach(function (item, index) {
                bdcSlJyxx[item.name] = item.value;
            });
            bdcSlJyxxList.push(bdcSlJyxx);
        }

    });
    if(bdcSlJyxxList != null &&bdcSlJyxxList.length >0) {
        getReturnData("/ycsl/updateJyxxList", JSON.stringify(bdcSlJyxxList), "PATCH", function (data) {


        }, function (error) {
            delAjaxErrorMsg(error);
        })
    }

}

//监听增值税金额等字段修改：增值税金额=含增值税金额-不含增值税金额
function changeZzsJe(elem) {
    var $tr =$(elem).parents("tr");
    var hzzsje =$tr.find("#hzzsje").val();
    var bhzzsje =$tr.find("#bhzzsje").val();
    if(isNotBlank(hzzsje) &&isNotBlank(bhzzsje)){
        $tr.find("#zzsje").val(calculateFloat(hzzsje -bhzzsje));
    }

}

