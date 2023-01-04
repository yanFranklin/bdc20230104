/**
 * author: <a href="mailto:yaoyi@gtmap.cn>yaoyi</a>
 * version 1.0.0  2021/10/18
 * describe: 质检核查
 */
var laydate, form;
var djlx = $.getUrlParam("djlx");
var ywfl = getParam("ywfl");

layui.use(['form', 'jquery', 'element', 'table', 'laydate'], function () {
    var $ = layui.jquery,
        laydate = layui.laydate,
        table = layui.table,
        form = layui.form;


    $(function () {

        // 初始化加载质检项目信息
        randomZjXmxx();

        table.on('tool(pageTable)', function (obj) {
            var layEvent = obj.event; //获得 lay-event 对应的值
            var bdcdyh = obj.data.bdcdyh;
            var xmid = obj.data.xmid;

            if (obj.event === 'zjDad') {
                openZjDad(obj.data);
            }
            if (obj.event === 'ywDad') {
                openYwDad(obj.data);
            }
        });

    });

    /**
     * 随机筛选 100 条质检项目信息
     * @param obj 查询条件
     */
    function randomZjXmxx(){
        addModel();
        var obj =  {djlx: djlx, ywfl: ywfl};
        $.ajax({
            url: getContextPath()+"/rest/changzhou/zjhc/list/zjxmxx",
            type: 'POST',
            dataType: 'json',
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(obj),
            success: function (data) {
                removeModal();
                loadTable(data);
            },
            error: function (xhr, status, error) {
                removeModal();
                delAjaxErrorMsg(xhr);
            }
        });
    }

    function loadTable(data){
        // 加载Table
        table.render({
            elem: "#pageTable",
            title: "质检核查",
            defaultToolbar: ['filter'],
            toolbar: "#toolbarDemo",
            even: true,
            page: false,
            limit: 100,
            cols: [[
                {type: 'checkbox', fixed: 'left'},
                {field: 'slbh', minWidth: 100, title: '受理编号'},
                {field: 'gzldymc', minWidth: 200, title: '业务类别'},
                {field: 'qlr', minWidth: 100, title: '申请人'},
                {field: 'slsj', minWidth: 150, title: '受理时间'},
                {field: 'slr', minWidth: 100, title: '受理人'},
                {field: 'zl', minWidth: 250, title: '坐落'},
                {field: 'hdr', minWidth: 100, title: '质检人员'},
                {field: 'cz', title: '操作', width: 210, templet: '#zjhccz', align: "center", fixed:"right"}
            ]],
            data: data,
            page: false,
            done: function () {
                removeModal();
                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
                if ($('.bdc-list-tab .layui-tab-content .layui-show .layui-table-main>.layui-table').height() == 0) {
                    $('.bdc-list-tab .layui-tab-content .layui-show .layui-table-body .layui-none').html('<img src="../static/lib/bdcui/images/table-none.png" alt="">无数据');
                } else {
                    $('.bdc-list-tab .layui-tab-content .layui-show .layui-table-body').height($('.bdc-content-box').height() - 196 - $('.bdc-task-tab').innerHeight() - $('.bdc-list-tab .layui-show .bdc-search-box').height());
                    $('.bdc-list-tab .layui-tab-content .layui-show .layui-table-fixed .layui-table-body').height($('.bdc-content-box').height() - 196 - $('.bdc-task-tab').innerHeight() - $('.bdc-list-tab .layui-show .bdc-search-box').height() - 17);
                }
                var reverseList = ['zl'];
                reverseTableCell(reverseList,"pageTable");
            }
        });
    }

});

function openZjDad(data){
    console.info(data);
    getZjid(data).done(function(res){
        console.info(res);
        var url = getContextPath()+"/changzhou/zj/zjqk.html?zjid=" + res ;
        layer.open({
            type: 2,
            area: ['960px', '575px'],
            fixed: false, //不固定
            content: url,
            btnAlign: "c"
        });
    });

}

function getZjid(data){
    var deferred = $.Deferred();
    if(!isNotBlank(data.zjid)){
        addModel();
        $.ajax({
            url: getContextPath() + "/rest/changzhou/zjhc/sc/zjqk",
            type: 'POST',
            dataType: 'json',
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(data),
            success: function (res) {
                console.info(res);
                removeModal();
                deferred.resolve(res.zjid);
            },
            error: function (xhr, status, error) {
                removeModal();
                delAjaxErrorMsg(xhr);
                deferred.reject();
            }
        });
    }else{
        deferred.resolve(data.zjid);
    }
    return deferred;
}

function openYwDad(data){
    window.open("/realestate-portal-ui/view/new-page.html?name=" + data.gzlslid +"&type=list", data.slbh);
}

// 中文字符编码
function getParam(name){
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(null != r){
        return decodeURIComponent(r[2]);
    }else{
        return null;
    }
}

