//字典列表
var zdList;
//地址栏获取数据
var processInsId = getQueryString("processInsId");
var formStateId = getQueryString("formStateId");
var readOnly = getQueryString("readOnly");
var xmid = getQueryString("xmid");
var $,laytpl,form;

layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    form = layui.form;
    $ = layui.jquery;
    var element = layui.element;
    laytpl = layui.laytpl;
    var layer = layui.layer;

    $(function () {
        addModel();
        //获取字典列表、加载基本信息、加载收件材料
        setTimeout(function () {
            try {
                $.when(loadButtonArea(),loadZdData(),loadSwxx()).done(function () {
                    removeModal();
                })
            } catch (e) {
                removeModal();
                delAjaxErrorMsg(e,"加载失败");
                return
            }
        }, 50);


        $('.bdc-form-div').on('focus','.layui-input',function () {
            if($(this).hasClass('layui-form-danger')){
                $(this).removeClass('layui-form-danger');
            }
            if($(this).parents('.layui-form-select').siblings().hasClass('layui-form-danger')){
                $(this).parents('.layui-form-select').siblings().removeClass('layui-form-danger');
            }
        });
        $('.bdc-form-div').on('click','.xm-input',function () {
            if($(this).parent().siblings('.xm-hide-input').hasClass('layui-form-danger')){
                $(this).parent().siblings('.xm-hide-input').removeClass('layui-form-danger');
            }
        });
    });

    //按钮加载
    function loadButtonArea() {
        var tpl = buttonAreaTpl.innerHTML, view = document.getElementById('buttonArea');
        //渲染数据
        laytpl(tpl).render({}, function (html) {
            view.innerHTML = html;
        });
        form.render();
        getStateById(readOnly, formStateId, "ycym");
        disabledAddFa();
    }

    //字典加载
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

    //加载税务信息
    window.loadSwxx = function (){
        // 涉税项目
        getReturnData("/slym/xm/listBdcXm", {gzlslid: processInsId,xmid:xmid}, 'GET', function (xmxxList) {
            var ssxm ={};
            if(xmxxList &&xmxxList.length >0) {
                if(xmxxList.length >1){
                    //查询出多条,优先取涉税的项目,没有则任取一条
                    for(var i=0;i<xmxxList.length;i++){
                        if(xmxxList[i].sply != null &&sply_ss.indexOf(xmxxList[i].sply) >-1){
                            xmid =xmxxList[i].xmid;
                            ssxm =xmxxList[i];
                            break;
                        }
                    }
                    if(!isNotBlank(xmid)){
                        xmid = xmxxList[0].xmid;
                        ssxm =xmxxList[0];
                    }
                }else {
                    xmid = xmxxList[0].xmid;
                    ssxm =xmxxList[0];
                }
                $.ajax({
                    url: getContextPath() + "/slym/sw/swxx",
                    type: 'GET',
                    dataType: 'json',
                    async: false,
                    data: {gzlslid: processInsId, xmid: xmid},
                    success: function (data) {
                        //加载转入方税务信息
                        generateSwxx(data.bdcZrfSwxxList, 1);
                        //加载转出方税务信息
                        generateSwxx(data.bdcZcfSwxxList, 2);
                        $("#swfybh").val(ssxm.swfybh);
                        getStateById(readOnly, formStateId, "ycym");
                        disabledAddFa();

                        form.render("select");
                        resetSelectDisabledCss();
                        removeModal();
                    }
                });
            }else{
                layer.alert("当前未生成项目数据，请确认！");
            }
        }, function (err) {
            console.log(err);
        },false);

    };

    //加载转入方税务信息
    window.generateSwxx = function(bdcSlSwxxList, sqrlb) {
        var json;
        if (isNotBlank(bdcSlSwxxList) && bdcSlSwxxList.length > 0) {
            json = {
                bdcSlSwxxList: bdcSlSwxxList[0],
                zd: zdList,
                sqrlb: sqrlb
            };
        } else {
            json = {
                bdcSlSwxxList: bdcSlSwxxList,
                zd: zdList,
                sqrlb: sqrlb
            };
        }
        var tpl, view;
        if(bdcSlSwxxList !== null && bdcSlSwxxList.length>0) {
            tpl = swxxTpl.innerHTML;
        } else {
            tpl = nullSwxxTpl.innerHTML;
        }
        if (sqrlb === 1) {
            view = document.getElementById('ycslzrfswxx');
        } else if (sqrlb === 2) {
            view = document.getElementById('ycslzcfswxx');
        }
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });

    }
});

//加载基本信息模块（入口）
function loadJbxx() {
    getReturnData("/slym/xm/listYcslXm", {gzlslid: processInsId}, 'GET', function (data) {
        if(data &&data.length >0) {
            xmid = data[0].xmid;
        }else{
            layer.alert("当前未生成涉税数据，请确认！");
        }
    }, function (err) {
        console.log(err);
    },false);
}

// 税务信息页面，上传完税审核通知单
function scfjqb(){
    var clmc = "完税审核通知单";
    getReturnData("/sjcl/folder", {gzlslid: processInsId, wjjmc: clmc}, 'GET', function (data) {
        if (isNotBlank(data)) {
            var bdcSlWjscDTO = queryBdcSlWjscDTO(clmc);
            bdcSlWjscDTO.spaceId = encodeURI(clmc);
            bdcSlWjscDTO.storageUrl = document.location.protocol + "//" + document.location.host + "/storage";
            bdcSlWjscDTO.nodeId = data.id;// 用于绑定当前流程与文件中心附件的id
            console.log(JSON.stringify(bdcSlWjscDTO));
            var url = getContextPath() + "/view/slym/sjd.html?paramJson=" + encodeURI(JSON.stringify(bdcSlWjscDTO));
            openUpload( url, "附件上传");
        }else{
            layer.msg(clmc+ "文件夹生成失败");
        }
    }, function (err) {
        delAjaxErrorMsg(err)
    }, false);

}
// 打开附件上传页面
function openUpload(url, title) {
    var width = $(window).width() + "px";
    var height = $(window).height() + "px";
    var index = layer.open({
        title: title,
        type: 2,
        content: url,
        area: [width, height],
        maxmin: true,
        end: function () {
        }
    });
    layer.full(index);
}
//查询收件材料所需参数（打开附件上传使用）
function queryBdcSlWjscDTO(clmc) {
    var bdcSlWjscDTO = {};
    $.ajax({
        url: '/realestate-accept-ui/slym/sjcl/bdcSlWjscDTO',
        type: 'GET',
        async: false,
        data: {processInsId: processInsId,clmc: clmc},
        success: function (data) {
            if (isNotBlank(data)) {
                bdcSlWjscDTO = data;
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
    return bdcSlWjscDTO;
}

//验证商品房完税状态
function checkSpfwszt() {
    addModel();
    var fybh =$("#swfybh").val();
    if(!isNotBlank(fybh)){
        removeModal();
        ityzl_SHOW_WARN_LAYER('请输入房源编号');
        return false;
    }
    //调用规则验证
    var bdcGzYzQO ={};
    bdcGzYzQO.zhbs = "HYSWXX";
    var gzyzParamMap ={};
    gzyzParamMap.xmid =xmid;
    gzyzParamMap.gzlslid =processInsId;
    gzyzParamMap.fybh = fybh;
    bdcGzYzQO.paramMap = gzyzParamMap;
    gzyzCommon(2,bdcGzYzQO,function (data) {
        getReturnData("/slym/sw/sfws/spfwszt",{gzlslid:processInsId,fybh:deleteWhitespace(fybh)},"GET",function (data) {
            removeModal();
            if (data === '1' || data === 1) {
                ityzl_SHOW_WARN_LAYER('当前项目已完税');
                loadSwxx();
            } else {
                ityzl_SHOW_WARN_LAYER('当前项目未完税');
            }
        },function (error) {
            delAjaxErrorMsg(error);

        });
    });
    
}
