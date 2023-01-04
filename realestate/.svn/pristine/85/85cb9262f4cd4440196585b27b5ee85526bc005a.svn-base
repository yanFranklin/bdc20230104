/**
 * 机构配置修改 js
 */
var jgid =  $.getUrlParam('jgid');
var jglbZd = [];
var zjzlZd = [];
var form;
var moduleCode = $.getUrlParam('moduleCode');
var isSubmit = true;
layui.use(['layer', 'table', 'jquery', 'form'], function () {
    var layer = layui.layer;
    var $ = layui.jquery;
    var table = layui.table;
    form = layui.form;

    $(function () {
        var verifyMsg = "必填项不能为空";
        form.verify({
            identitynew: function (value, item) {
                var msg = checkSfzZjh(value);
                if (isNotBlank(msg)) {
                    $(item).addClass('layui-form-danger');
                    isSubmit = false;
                    verifyMsg = msg;
                }
            }
        });

        addModel();
        setTimeout(function () {
            try {
                $.when(loadZdx(),generateJgxx()).done(function () {
                    removeModal();
                })
            } catch (e) {
                removeModal();
                delAjaxErrorMsg(e,"加载失败");
                return
            }
        }, 10);

        form.on("submit(saveJgpz)", function (data) {
            if (!isSubmit) {
                layer.msg(verifyMsg, {icon: 5, time: 3000});
                isSubmit = true;
                return false;
            } else {
                saveJgpz();
                return false;
            }
        });

        form.on('select(jgzjzl)', function (data) {
            addSfzYz(data.value, data.elem);
            form.render('select');
        });
        form.on('select(frzjzl)', function (data) {
            addSfzYz(data.value, data.elem);
            form.render('select');
        });
        form.on('select(dlrzjzl)', function (data) {
            addSfzYz(data.value, data.elem);
            form.render('select');
        });
    });
});

function loadZdx(){
    var zdxMap = getMulZdList("jglb,zjzl,qlrlx");
    // 渲染字典项
    if(zdxMap.jglb && zdxMap.jglb.length >0) {
        jglbZd = zdxMap.jglb;
        $.each(zdxMap.jglb, function (index, item) {
            $('#jglb').append('<option value="' + item.DM + '">' + item.MC + '</option>');
        });
        form.render("select");
    }
    if(zdxMap.zjzl && zdxMap.zjzl.length > 0){
        zjzlZd = zdxMap.zjzl;
        $.each(zdxMap.zjzl, function (index, item) {
            $('#jgzjzl').append('<option value="' + item.DM + '">' + item.MC + '</option>');
            $('#frzjzl').append('<option value="' + item.DM + '">' + item.MC + '</option>');
            $('#dlrzjzl').append('<option value="' + item.DM + '">' + item.MC + '</option>');
        });
        form.render("select");
    }
    if(zdxMap.qlrlx && zdxMap.qlrlx.length >0) {
        var qlrlx = zdxMap.qlrlx;
        $.each(zdxMap.qlrlx, function (index, item) {
            $('#qlrlx').append('<option value="' + item.DM + '">' + item.MC + '</option>');
        });
        form.render("select");
    }
}

function generateJgxx(){
    if (isNotBlank(jgid)){
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/jgpz/query",
            type: 'GET',
            dataType: 'json',
            async: false,
            data: {jgid: jgid},
            success: function (data) {
                if (moduleCode){
                    setElementAttrByModuleAuthority(moduleCode);
                }
                form.val("searchform", data);
                form.render();
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        })
    }else{
        var nf = (new Date).getFullYear();
        $("input[name='nf']").val(nf);
    }
}

// 添加身份证号验证
function addSfzYz(zjzl, elem) {
    var zjhdom = $(elem).parents(".layui-inline").next(".layui-inline").find("input");
    var attrVal = $(zjhdom).attr("lay-verify");
    if (zjzl === "1" || zjzl === 1) {
        //增加身份证验证
        if (isNotBlank(attrVal)) {
            if (attrVal.indexOf("identitynew") < 0) {
                $(zjhdom).attr("lay-verify", attrVal + "|identitynew");
            }
        } else {
            $(zjhdom).attr("lay-verify", "identitynew");
        }
    } else {
        //移除身份证验证
        //增加长度大于五位验证
        if (isNotBlank(attrVal) && attrVal.indexOf("identitynew") > -1) {
            $(zjhdom).attr("lay-verify", attrVal.replace("identitynew", ""));
        }
        var newattr = $(zjhdom).attr("lay-verify");
        if (isNotBlank(newattr) && newattr.indexOf("zjhlength") < 0) {
            $(zjhdom).attr("lay-verify", newattr + "|zjhlength");
        } else if (zjzl !== null && zjzl !== "") {
            $(zjhdom).attr("lay-verify", "zjhlength");
        } else if ((zjzl === null || zjzl === "") && isNotBlank(newattr) && newattr.indexOf("zjhlength") > -1) {
            $(zjhdom).attr("lay-verify", attrVal.replace("zjhlength", ""));
        }
    }
}

function saveJgpz(){
    var obj = {};
    $(".search").each(function (i) {
        var name = $(this).attr('name');
        var value = $(this).val();
        if (value) {
            value = value.replace(/\s*/g, "");
        }
        obj[name] = value;
    });

    if (isNotBlank(jgid)){
        obj["jgid"] = jgid;
    }
    addModel();
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/jgpz/save",
        type: 'POST',
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(obj),
        success: function (data) {
            removeModal();
            var index = parent.layer.getFrameIndex(window.name);
            setTimeout(removeModal(), 100);
            parent.layui.table.reload('pageTable',{page:{curr:1}});
            parent.initUploadInst();

            layer.confirm('保存成功，是否添加机构默认领证人?', {
                btn: ['是', '否']
            }, function(index, layero){
                window.open("/realestate-inquiry-ui/view/jglzr/jglzr.html")
                cancel();
            }, function(index){
                cancel();
            });

        }, error: function (xhr, status, error) {
            removeModal();
        }
    });
}

function cancel(){
    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
    parent.layer.close(index); //再执行关闭
}

function clsc(){
    if(!isNotBlank(jgid)){
        // 不存在机构id时，先保存机构基本信息，生成jgid信息
        var obj = {};
        $(".search").each(function (i) {
            var name = $(this).attr('name');
            var value = $(this).val();
            if (value) {
                value = value.replace(/\s*/g, "");
            }
            obj[name] = value;
        });
        if(isNullOrEmpty(obj.jglb) || isNullOrEmpty(obj.jgmc) || isNullOrEmpty(obj.jgzjzl) || isNullOrEmpty(obj.jgzjbh)){
            ityzl_SHOW_INFO_LAYER("请输入机构类别、机构名称、机构证件种类、机构证件编号等信息。");
            return false;
        }
        addModel();
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/jgpz/save",
            type: 'POST',
            contentType: "application/json;charset=UTF-8",
            async: false,
            data: JSON.stringify(obj),
            success: function (data) {
                removeModal();
                jgid = data.jgid;
            }, error: function (xhr, status, error) {
                removeModal();
            }
        });
    }
    openWjsc(jgid,"机构材料");
}

//打开附件上传页面
function openWjsc(id, wjjmc){
    getReturnData("/rest/v1.0/fjcl/folder", {gzlslid: id, wjjmc: wjjmc}, 'GET', function (data) {
        if (isNotBlank(data)) {
            // 保存文件中心id至机构配置表中
            saveWjzxid(id, data.id);
            var wjglCs = getWjglCs(id,"clientId", data.id, 2);
            if(isNullOrEmpty(wjglCs.token) ||isNullOrEmpty(wjglCs.spaceId)){
                layer.alert("缺失附件参数");
                return false;
            }
            var url = "/realestate-accept-ui/view/slym/sjd.html?paramJson=" + encodeURI(JSON.stringify(wjglCs));
            var index = parent.layer.open({
                title: "上传附件",
                type: 2,
                area: ['1150px', '600px'],
                content: url
            });
            parent.layer.full(index);
        }else{
            layer.msg(clmc+ "文件夹生成失败");
        }
    }, function (err) {
        delAjaxErrorMsg(err)
    }, false);
}

// 更新文件中心ID
function saveWjzxid(jgid, wjzxid){
    var obj = {
        jgid : jgid,
        wjzxid : wjzxid
    }
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/jgpz/save",
        type: 'POST',
        contentType: "application/json;charset=UTF-8",
        async: false,
        data: JSON.stringify(obj),
        success: function (data) {
        },
        error: function (xhr, status, error) {
        }
    });
}