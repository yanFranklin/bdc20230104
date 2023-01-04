layui.use(['jquery', 'layer', 'element', 'form', 'laytpl'],function () {
    form = layui.form;
    $ = layui.jquery;
    var laytpl = layui.laytpl;

    //获取申请人ID
    var qlrid = getQueryString('qlrid');
    var processInsId = getQueryString('processInsId');
    //获取表单权限参数
    var readOnly = getQueryString('readOnly');
    var formStateId = getQueryString('formStateId');
    var qlrlb = getQueryString('qlrlb');
    var zdList =[];

    addModel();
    //获取字典
    getReturnData("/bdczd",{},"POST",function (data) {
        zdList=data;
    },function (error) {

    },false);

    //获取家庭成员信息
    getReturnData("/rest/v1.0/jtcy/qlrid/"+qlrid,{},"GET",function (jtcyData) {
        //渲染家庭成员信息
        if(jtcyData.length ===0){
            var tpl = addJtcyTpl.innerHTML;
        }else{
            var tpl = jtcyTpl.innerHTML
        }
        var json ={};
        json.jtcyData =jtcyData;
        json.zd =zdList;
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            $("#tbody").append(html);
        });
        form.render();

        //区分权利人与义务人
        var resourceName ="qlrjtcy";
        if(qlrlb =="2"){
            resourceName ="ywrjtcy";
        }
        getStateById(readOnly, formStateId, resourceName);

        //在设置权限后设置判断身份证验证，防止必填冲掉身份证验证
        $("[lay-filter=zjzl]").each(function () {
            addSfzYz($(this).val(), $(this));
        });

        removeModal();



    },function (error){
        delAjaxErrorMsg(error);
    });



    // 家庭成员删除
    $(document).on('click','.jtcy-add', function() {
        var json = {
            zd: zdList
        };
        var tpl = addJtcyTpl.innerHTML;
        laytpl(tpl).render(json, function (html) {
            $("#tbody").append(html);
        });
        form.render();
        resetSelectDisabledCss();
    });

    $(document).on('click','.bdc-delete-btn', function(){
        var $tr =$(this).parents('tr');
        var jtcyid =$tr.find("input[name=jtcyid]").val();
        var url = getContextPath() + "/rest/v1.0/jtcy?jtcyid=" + jtcyid+"&gzlslid="+processInsId;
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
                            removeModal();
                            ityzl_SHOW_SUCCESS_LAYER("删除成功");
                            $tr.remove();

                        }, error: function (xhr, status, error) {
                            delAjaxErrorMsg(xhr)
                        }
                    });
                } else {
                    removeModal();
                    ityzl_SHOW_SUCCESS_LAYER("删除成功");
                    $tr.remove();
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
        , lxdh: function (value, item) {
            if (!validatePhone($.trim(value))) {
                $(item).addClass('layui-form-danger');
                isSubmit = false;
                verifyMsg = "联系电话格式不正确";
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
                    jtcy.qlrid = qlrid;
                    jtcyxxList.push(jtcy);
                }
            }
        });
        if (jtcyxxList.length > 0) {
            var qlrAndJtcyList =[];
            var qlrIds=[];
            qlrIds.push(qlrid);
            var qlrAndJtcy={};
            qlrAndJtcy.qlrid = qlrIds;
            qlrAndJtcy.jtcyList = jtcyxxList;
            qlrAndJtcyList.push(qlrAndJtcy);
            getReturnData("/rest/v1.0/jtcy/updateJtcyxx/"+processInsId,JSON.stringify(qlrAndJtcyList),"POST",function (data) {
                ityzl_SHOW_SUCCESS_LAYER("保存成功！");
                setTimeout(function () {
                    location.reload();
                }, 500);
            },function (error) {
                delAjaxErrorMsg(error);
            },false)

        }

    });

    //监听证件类型
    form.on('select(zjzl)', function (data) {
        addSfzYz(data.value,data.elem);
    });


    // 身份证格式验证
    function addSfzYz(zjzl, elem) {
        var $tr =$(elem).parents('tr');
        var $jtcyzjh =$tr.find("input[name=jtcyzjh]");
        var attrVal = $jtcyzjh.attr("lay-verify");
        if (zjzl === "1" || zjzl === 1) {
            //增加身份证验证
            if (isNotBlank(attrVal)) {
                if (attrVal.indexOf("identitynew") < 0) {
                    $jtcyzjh.attr("lay-verify", attrVal + "|identitynew");
                }
            } else {
                $jtcyzjh.attr("lay-verify", "identitynew");
            }
        } else {
            //移除身份证验证
            //增加长度大于五位验证
            if (isNotBlank(attrVal) && attrVal.indexOf("identitynew") > -1) {
                $jtcyzjh.attr("lay-verify", attrVal.replace("identitynew", ""));
            }
            var newattr = $jtcyzjh.attr("lay-verify");
            if (isNotBlank(newattr) && newattr.indexOf("zjhlength") < 0) {
                $jtcyzjh.attr("lay-verify", newattr + "|zjhlength");
            } else if (zjzl !== null && zjzl !== "") {
                $jtcyzjh.attr("lay-verify", "zjhlength");
            } else if ((zjzl === null || zjzl === "") && isNotBlank(newattr) && newattr.indexOf("zjhlength") > -1) {
                $jtcyzjh.attr("lay-verify", attrVal.replace("zjhlength", ""));
            }
        }
    }


});

//改变证件号
function changeZjh($this){
    var jtcyzjh =$this.val();
    var jtcyzjzl =$this.parents("tr").find("[name=jtcyzjzl]").val();
    if(jtcyzjzl ===1 ||jtcyzjzl ==="1" &&!isNullOrEmpty(jtcyzjh) &&(jtcyzjh.length ===15 ||jtcyzjh.length ===18)){
        $this.parents("tr").find("[name=xb]").val(getSex(jtcyzjh));
        $this.parents("tr").find("[name=nl]").val(getAge(jtcyzjh));
        form.render();
        resetSelectDisabledCss();
    }
}

/**
 * 身份证读卡器读取信息到页面
 */
function readJtcy(element,zjzlName,zjhName) {
    try {
        var objTest = readIdCard();
        if (objTest.ReadCard()) {
        layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
            var pName = objTest.Name;
            var pCardNo = objTest.ID;
            var pAddress = objTest.Address;
            var forms = element.parentNode.parentNode.parentNode;
            var elementName =$(element)[0].name;
            $(forms).find("input[name="+elementName+"]").val(trimStr(pName));
            if(isNotBlank(zjzlName)){
                $(forms).find("select[name="+zjzlName+"]").val("1");
            }
            if(isNotBlank(zjhName)){
                $(forms).find("input[name="+zjhName+"]").val($.trim(pCardNo));
                changeZjh($(forms).find("input[name="+zjhName+"]"));
            }
            var form = layui.form;
            form.render('select');
        });
        } else {
            layer.alert("请检查是否安装成功并重新放置！");
        }
    } catch (objError) {
        layer.alert("Fail to create object. error:" + objError.description);
    }

}
