/**
 * 公告收件材料
 */
var zdListsjlx;
var zdListsqbm;
var gzlslid = $.getUrlParam('gzlslid');
layui.use(['form', 'jquery', 'laydate', 'element', 'table', 'laytpl'], function () {
    var $ = layui.jquery,  form = layui.form,  table = layui.table, laytpl = layui.laytpl;
    // 收件材料信息
    var sjclData = [];
    $(function () {
        setTimeout("loadData()", 10);
        // 初始化
        init();
        function init() {
            addModel();
            // 查询收件材料信息
            $.ajax({
                url: getContextPath() + "/rest/v1.0/ggxx/sjcl/"+gzlslid,
                type: 'GET',
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                //data: {processInsId: gzlslid},
                success: function (data) {
                    generateSjcl(data,);
                    removeModal();
                }, error: function (xhr) {
                    delAjaxErrorMsg(xhr);
                    removeModal();
                }
            });
        }

        // 组织收件材料
        function generateSjcl(data) {
            var getTpl = sjclTpl.innerHTML
                ,view = document.getElementById('sjclView');

            laytpl(getTpl).render({bdcSlSjcls:data,zdListsjlx:zdListsjlx,zdListsqbm:zdListsqbm}, function(html){
                view.innerHTML = html;
            });
            form.render('select');
            table.render();
        }


    });

    // 关闭弹出页面
    window.closeWin = function () {
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    };
});
//仅仅用于查看文件管理器并且设置权限
function checksjcl() {
    var bdcSlWjscDTO = queryBdcSlWjscDTO('', true);
    bdcSlWjscDTO.spaceId = gzlslid;
    bdcSlWjscDTO.storageUrl = document.location.protocol + "//" + document.location.host + "/storage";
    var url = "/realestate-inquiry-ui/changzhou/ggtzlr/sjd.html?paramJson=" + encodeURI(JSON.stringify(bdcSlWjscDTO));
    openSjcl(url,"材料查看");
}

//查询收件材料所需参数（打开附件上传使用）
function queryBdcSlWjscDTO(clmc,sfhqqx) {
    var bdcSlWjscDTO = {};
    $.ajax({
        url: '/realestate-inquiry-ui/rest/v1.0/ggxx/bdcSlWjscDTO',
        type: 'GET',
        async: false,
        data: {sfhqqx: sfhqqx},
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


//打开附件上传页面
function openSjcl(url,title) {
    var width = $(window).width() + "px";
    var height = $(window).height() + "px";
    var index = layer.open({
        title: title,
        type: 2,
        content: url,
        area: [width, height],
        maxmin: true
    });
    layer.full(index);
}

function loadData() {
    zdListsjlx = getMulZdList('sjlx');
    zdListsqbm = getSlMulZdList('sqbm');
}
/**
 * 获取字典
 * @param callback 回调函数
 */
window.getCommonZd = function (callback) {
    var zdList = getZdList();
    if (zdList) {
        callback(zdList);
    }
};