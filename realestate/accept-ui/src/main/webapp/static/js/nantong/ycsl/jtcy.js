layui.use(['jquery', 'layer', 'element', 'form', 'laytpl'],function () {
    form = layui.form;
    $ = layui.jquery;
    var laytpl = layui.laytpl;

    //获取申请人ID
    var sqrid = getQueryString('sqrid');
    //获取表单权限参数
    var readOnly = getQueryString('readOnly');
    var formStateId = getQueryString('formStateId');
    var sqrlb = getQueryString('sqrlb');

    // 渲染字典项
    $.each(parent.zdList.zjzl, function (index, item) {
        $('#po_zjzl').append('<option value="' + item.DM + '">' + item.MC + '</option>');
    });

    //获取家庭成员信息
    getReturnData("/slym/jtcy/listBdcSlJtcy",{sqrid:sqrid},"GET",function (data) {
        //获取配偶信息和子女信息
        var po ={};
        var znList =[];
        for (var i = 0, len = data.length; i < len; i++) {
            if(data[i].ysqrgx ==="配偶"){
                po =data[i];
            }else{
                znList.push(data[i]);
            }
        }
        //赋值配偶信息
        form.val('form', po);
        var json = {
            zd: parent.zdList,
            znList:znList
        };

        //渲染子女信息
        if(znList.length ===0){
            var tpl = addZnxxTpl.innerHTML;
        }else{
            var tpl = znxxTpl.innerHTML
        }
        var view = document.getElementById('znxx');
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            $("#tbody2").append(html);
        });
        form.render();

        //区分权利人与义务人
        var resourceName ="qlrjtcy";
        if(sqrlb =="2"){
            resourceName ="ywrjtcy";
        }
        getStateById(readOnly, formStateId, resourceName);

        //在设置权限后设置判断身份证验证，防止必填冲掉身份证验证
        $("[lay-filter=zjzl]").each(function () {
            addSfzYz($(this).val(), $(this));
        });



    });



    // 子女信息表格行增删
    $(document).on('click','.znxx-add', function() {
        var json = {
            zd: parent.zdList
        };
        var tpl = addZnxxTpl.innerHTML;
        laytpl(tpl).render(json, function (html) {
            $("#tbody2").append(html);
        });
        form.render();
        resetSelectDisabledCss();
    });

    $(document).on('click','.bdc-delete-btn', function(){
        var $tr =$(this).parents('tr');
        var jtcyid =$tr.find("input[name=jtcyid]").val();
        var url = getContextPath() + "/slym/jtcy?jtcyid=" + jtcyid;
        var deleteIndex = layer.open({
            type: 1,
            skin: 'bdc-small-tips',
            title: '确认删除',
            area: ['320px'],
            content: '是否确认删除？',
            btn: ['确定', '取消'],
            btnAlign: 'c',
            yes: function () {
                addModel();
                //确定操作
                if (isNotBlank(jtcyid)) {
                    $.ajax({
                        url: url,
                        type: 'DELETE',
                        success: function (data) {
                            $tr.remove();
                            removeModal();

                        }, error: function (xhr, status, error) {
                            delAjaxErrorMsg(xhr)
                        }
                    });
                } else {
                    $tr.remove();
                    removeModal();
                }


                layer.close(deleteIndex);
            },
            btn2: function () {
                //取消
                layer.close(deleteIndex);
            }
        });
    });

    //表单校验
    //点击提交时不为空的全部标红
    var isSubmit = true;
    var verifyMsg = "必填项不能为空";
    form.verify({
        required: function (value, item) {
            if (value == '') {
                $(item).addClass('layui-form-danger');
                isSubmit = false;
            }
        },
        identitynew: function (value, item) {
            var yzxx = verifyIdNumber(value, item);
            if (!isEmptyObject(yzxx) && !yzxx.isSubmit) {
                $(item).addClass('layui-form-danger');
                isSubmit = yzxx.isSubmit;
                verifyMsg = yzxx.verifyMsg;
            }
        }
    });

    //表单保存
    form.on("submit(saveJtcyData)", function (data) {
        if (!isSubmit) {
            layer.msg(verifyMsg, {icon: 5, time: 3000});
            isSubmit = true;
            verifyMsg = "必填项不能为空!";
            return false;
        }

        var jtcyxxList =[];
        $("table tbody tr").each(function () {
            var $tr =$(this);
            var jtcyArr = $tr.find(":input").serializeArray();
            if (jtcyArr !== null && jtcyArr.length > 0) {
                var jtcy ={};
                jtcyArr.forEach(function (item, index) {
                    jtcy[item.name] = item.value;
                });
                if(isNotBlank(jtcy.jtcymc)) {
                    jtcy.sqrid = sqrid;
                    jtcyxxList.push(jtcy);
                }
            }
        });
        if (jtcyxxList.length > 0) {
            getReturnData("/slym/jtcy",JSON.stringify(jtcyxxList),"PATCH",function () {
                ityzl_SHOW_SUCCESS_LAYER("保存成功");
                window.location.reload();
                
            },function (error) {
                delAjaxErrorMsg(error);

            })

        }

    });

    //监听证件类型
    form.on('select(zjzl)', function (data) {
        addSfzYz(data.value,data.elem);
    });


    // 身份证格式验证
    function addSfzYz(zjzl, elem) {
        var zjzlid = $(elem).parent().parent().find("select")[0].id;
        var zjhid = zjzlid.replace("zjzl", "zjh");
        var attrVal = $("#" + zjhid).attr("lay-verify");
        if (zjzl === "1" || zjzl === 1) {
            //增加身份证验证
            if (isNotBlank(attrVal)) {
                if (attrVal.indexOf("identitynew") < 0) {
                    $("#" + zjhid).attr("lay-verify", attrVal + "|identitynew");
                }
            } else {
                $("#" + zjhid).attr("lay-verify", "identitynew");
            }
        } else {
            //移除身份证验证
            //增加长度大于五位验证
            if (isNotBlank(attrVal) && attrVal.indexOf("identitynew") > -1) {
                $("#" + zjhid).attr("lay-verify", attrVal.replace("identitynew", ""));
            }
            var newattr = $("#" + zjhid).attr("lay-verify");
            if (isNotBlank(newattr) && newattr.indexOf("zjhlength") < 0) {
                $("#" + zjhid).attr("lay-verify", newattr + "|zjhlength");
            } else if (zjzl !== null && zjzl !== "") {
                $("#" + zjhid).attr("lay-verify", "zjhlength");
            } else if ((zjzl === null || zjzl === "") && isNotBlank(newattr) && newattr.indexOf("zjhlength") > -1) {
                $("#" + zjhid).attr("lay-verify", attrVal.replace("zjhlength", ""));
            }
        }
    }
});