var nslxdUrl = getIP() + "/realestate-accept-ui/static/printmodel/nslxd.fr3";
var processInsId = getQueryString("processInsId");

layui.use(['jquery', 'laydate', 'form', 'laytpl'], function () {
    var laydate = layui.laydate,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        form = layui.form;

    var loadQlxx = function(processInsId) {
        var deferred = $.Deferred();
        $.ajax({
            url: getContextPath()+'/rest/v1.0/nslxd/data',
            type: 'POST',
            dataType: 'json',
            data: {processInsId: processInsId},
            success: function (data) {
                deferred.resolve(data[0]);
            },
            error : function(err){
                console.info(err);
                deferred.reject(err);
            }
        });
        return deferred;
    }
    var renderTpl = function(data){
        var getNslxdTpl = nslxdTpl.innerHTML;
        laytpl(getNslxdTpl).render(data, function (html) {
            $(".bdc-nslxd-view").html(html);
        });
    }

    $(function () {

        var sessionData = sessionStorage.getItem("nslxdData");
        //当工作流实例id为空并且浏览器存储对象不为空时，则为nslxdList页面查看详情信息，进入到单个nslxd页面。
        if(null == processInsId && sessionData){
            $("#printButton").hide();
            renderTpl(JSON.parse(sessionData));
            //加载完成后清除纳税联系单存储对象
            sessionStorage.removeItem("nslxdData");
            //通过列表查询纳税联系单详情时，隐藏打印按钮

        }else{
            loadQlxx(processInsId).done(function (data) {
                renderTpl(data);
            });
        }

        window.printNslxd = function() {
            var dataUrl = getIP() + getContextPath() + "/rest/v1.0/nslxd/print/"+processInsId+"/xml/list";
            print(nslxdUrl, dataUrl,false);
        }
    });
});