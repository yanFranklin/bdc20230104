var reverseList = ['zl'];
layui.use(['jquery', 'element', 'form', 'table', 'laydate', ], function () {
    //获取字典
    var $ = layui.jquery;
    var form = layui.form;
    var table = layui.table;
    var laytpl = layui.laytpl;
    $(function () {
        // 获取页面表单标识，用于权限控制
        var moduleCode = $.getUrlParam('moduleCode');
        // 加载Table
        table.render({
            elem: '#bdcdyTable',
            toolbar: '#toolbar',
            title: '注销列表',
            defaultToolbar: ["filter"],
            request: {
                limitName: 'size', //每页数据量的参数名，默认：limit
            },
            even: true,
            cols: [[
                {field: 'xh', type: 'numbers', width: 60, title: '序号'},
                {field: 'htbh', title: '合同号', width: 160, align: 'center'},
                {field: 'fwbm', minWidth: 200, title: '丘号'},
                {field: 'zl', minWidth: 200, title: '坐落'},
                {field: 'basj', minWidth: 120, title: '备案时间',templet: function (d) {
                        return Format(formatChinaTime(new Date(d.basj)), "yyyy-MM-dd");}
                },
                {field: 'qlrmc', title: '权利人', width: 160, align: 'center'},
                {field: 'bazt', title: '合同状态', width: 100, align: 'center',templet: '#htztTpl'},
                {field: 'zxyy', title: '注销原因', width: 200, align: 'center'},
                {field: 'zxshr', title: '注销审核人', width: 100, align: 'center'},
                {field: 'zxsj', title: '注销时间', width: 160, align: 'center', templet: function (d) {
                        if (d.zxsj && d.zxsj !== '' && d.zxsj !== null && d.zxsj !== undefined) {   //条件必须写完善，否则会出现错误，如：不写undefined 的话，也会默认认为有，但是实则没有，就会渲染当前时间了
                            return layui.util.toDateString(d.zxsj*1000, 'yyyy-MM-dd HH:mm:ss')
                        } else {
                            return '';
                        }
                    }},
                {title: '操作', minWidth: 160, templet: '#checkoutTpl',align: 'center',fixed: 'right'},

            ]],
            data: [],
            page: true,
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
                reverseTableCell(reverseList);
                // 控制按钮权限
                if(moduleCode){
                    setElementAttrByModuleAuthority(moduleCode);
                }
            }
        });

        /**
         * 点击查询
         */
        $('#search').on('click', function () {
            search();
        });
        // 回车查询
        $('.required').on('keydown', function (event) {
            if (event.keyCode == 13) {
                search();
            }
        });

        //查询
        function search() {
            // 获取查询参数
            var obj = {};
            $(".cxtj").each(function (i) {
                var name = $(this).attr('name');
                var value = $(this).val();
                if (value) {
                    value = value.replace(/\s*/g, "");
                }
                obj[name] = value;
            });
            var getSearch = form.val("searchFilter");
            if(isNullOrEmpty(getSearch.htbh)
                && isNullOrEmpty(getSearch.qlrmc)
                && isNullOrEmpty(getSearch.zl)
                && isNullOrEmpty(getSearch.fwbh)){
                warnMsg("请至少输入一个查询条件！");
                return;
            }

            addModel();
            // 重新请求
            table.reload("bdcdyTable", {
                //查询接口
                url: getContextPath() + "/htzx",

                where: obj,
                page: {
                    curr: 1, //重新从第 1 页开始
                    limits: [10, 50, 100, 200, 500]
                },
                done: function (res, curr, count) {
                    currentPageData = res.data;
                    reverseTableCell(reverseList);
                    removeModal();
                    setHeight();
                }
            });
        }

        table.on('tool(bdcspfTable)', function(obj) {
            var data = obj.data;
            //注销合同
            if (obj.event === 'checkout') {
                checkout(data);
            }
            //附件上传
            if (obj.event === 'info'){
                fj(data);
            }
        });

        function checkout(data){
            var baid = data.baid;
            layer.open({
                title: '注销操作',
                type: 1,
                area: ['430px'],
                btn: ['提交', '取消'],
                content: $('#checkout-reason')
                ,yes: function(index, layero){
                    addModel();
                    var tfyy = $("#tfyy").val();
                    if (tfyy == null || tfyy == ""){
                        ityzl_SHOW_WARN_LAYER("注销原因不能为空");
                        removeModal();
                        return;
                    }
                    $.ajax({
                        type: "POST",
                        //退房提交的回调接口url
                        url: getContextPath() + "/htzx/htzt?baid=" + baid + "&tfyy=" + encodeURI(tfyy),
                        contentType: "application/json;charset=utf-8",
                        dataType: "json",
                        async: true,
                        success: function (data) {
                            if (data === 2){
                                ityzl_SHOW_SUCCESS_LAYER("注销成功");
                            }else {
                                ityzl_SHOW_WARN_LAYER("注销失败");
                            }
                            layer.close(index);
                            $("#zxyyForm")[0].reset();
                            layui.form.render();
                            search();
                            removeModal();
                        }
                    });
                }
            });
        }

    });

});

function fj(data){
    var id = data.baid;
    var bdcSlWjscDTO = queryBdcSlWjscDTO();
    var host = document.location.host
    bdcSlWjscDTO.spaceId = id;
    bdcSlWjscDTO.storageUrl = document.location.protocol + "//" + host + "/storage";
    var url = getContextPath() + "/view/slym/sjd.html?paramJson=" + encodeURI(JSON.stringify(bdcSlWjscDTO));

    $.ajax({
        url:  getContextPath() + "/htzx/fj",
        type: 'POST',
        data: {id: id},
        // contentType: "application/json;charset=utf-8",
        // dataType: "json",
        success: function (DownUrlid) {
            if (isNullOrEmpty(DownUrlid)) {
                uploadFj(id);
                openSjcl(url, "附件上传");
            } else {
                openSjcl(url, "附件上传");
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

function uploadFj(id,fjwjjmc){
    $.ajax({
        url:  getContextPath() + "/htzx/createFjgl?id=" + id + '&fjwjjmc=' + fjwjjmc,
        type: 'GET',
        dataType: "text",
        success: function (sid) {
            if (isNullOrEmpty(sid)) {
                layer.msg("文件夹生成失败");
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

function openSjcl(url, title) {
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

var Base64={
    _keyStr:"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",
    encode:function(e){
        var t="";
        var n,r,i,s,o,u,a;
        var f=0;
        e=Base64._utf8_encode(e);
        while(f<e.length){
            n=e.charCodeAt(f++);
            r=e.charCodeAt(f++);
            i=e.charCodeAt(f++);
            s=n>>2;
            o=(n&3)<<4|r>>4;
            u=(r&15)<<2|i>>6;
            a=i&63;
            if(isNaN(r)){
                u=a=64
            }else if(isNaN(i)){
                a=64
            }
            t=t+this._keyStr.charAt(s)+this._keyStr.charAt(o)+this._keyStr.charAt(u)+this._keyStr.charAt(a)
        }
        return t
    },
    decode:function(e){
        var t="";
        var n,r,i;
        var s,o,u,a;
        var f=0;
        e=e.replace(/[^A-Za-z0-9+/=]/g,"");
        while(f<e.length){
            s=this._keyStr.indexOf(e.charAt(f++));
            o=this._keyStr.indexOf(e.charAt(f++));
            u=this._keyStr.indexOf(e.charAt(f++));
            a=this._keyStr.indexOf(e.charAt(f++));
            n=s<<2|o>>4;
            r=(o&15)<<4|u>>2;
            i=(u&3)<<6|a;
            t=t+String.fromCharCode(n);
            if(u!=64){
                t=t+String.fromCharCode(r)
            }
            if(a!=64){
                t=t+String.fromCharCode(i)
            }
        }
        t=Base64._utf8_decode(t);
        return t
    },
    _utf8_encode:function(e){
        e=e.replace(/rn/g,"n");
        var t="";
        for(var n=0;n<e.length;n++){
            var r=e.charCodeAt(n);
            if(r<128){
                t+=String.fromCharCode(r)
            }else if(r>127&&r<2048){
                t+=String.fromCharCode(r>>6|192);
                t+=String.fromCharCode(r&63|128)
            }else{
                t+=String.fromCharCode(r>>12|224);
                t+=String.fromCharCode(r>>6&63|128);
                t+=String.fromCharCode(r&63|128)
            }
        }
        return t
    },
    _utf8_decode:function(e){
        var t="";
        var n=0;
        var r=c1=c2=0;
        while(n<e.length){
            r=e.charCodeAt(n);
            if(r<128){
                t+=String.fromCharCode(r);
                n++
            }else if(r>191&&r<224){
                c2=e.charCodeAt(n+1);
                t+=String.fromCharCode((r&31)<<6|c2&63);
                n+=2
            }else{
                c2=e.charCodeAt(n+1);
                c3=e.charCodeAt(n+2);
                t+=String.fromCharCode((r&15)<<12|(c2&63)<<6|c3&63);
                n+=3
            }
        }
        return t
    }
};

/**
 * 查询收件材料所需参数（打开附件上传使用）
 */
function queryBdcSlWjscDTO() {
    var bdcSlWjscDTO = {};
    $.ajax({
        url:  getContextPath() + '/htzx/getFileCs',
        type: 'GET',
        async: false,
        contentType: 'application/json;charset=utf-8',
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



