/**
 * Created by ypp on 2020/3/2.
 * 配置页面js
 */
layui.config({
    base: '../../../static/' //静态资源所在路径
}).extend({response:'lib/bdcui/js/response',formSelects:'lib/form-select/formSelects-v4'});

var zdx = {
    pzlxZd : [],
    pzzxtZd : [],
    gnmkZd : []
};
layui.use(['table','form','layer', 'upload','formSelects', 'util'], function(){
    var table = layui.table,
        form = layui.form,
        $ = layui.jquery,
        formSelects = layui.formSelects,
        upload = layui.upload,
        util = layui.util,
        layer = layui.layer;

    formSelects.config('selectPzZxt', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);

    $(function(){
        var BASE_URL = getContextPath() +"/rest/v1.0/tsywpz";
        $('#search').on('click',function(){
            var getSearch = form.val("searchFilter");
            // 重新请求
            table.reload("pzTableId", {
                where: getSearch
                , page: {
                    curr: 1  //重新从第 1 页开始
                }
            });
            // 加载上传配置项事件绑定
            importBdcTsywPz();
        });
        $('#import').on('click',function(){
            $("#importExcelFile").val("");
            $("#importExcelFile").click();
        });

        //字典
        queryZd();
        function queryZd(){
            $.ajax({
                url: getContextPath()+"/rest/v1.0/lcpz/zdxx",
                type: "POST",
                data:'tsywpzlx,tsywpzmk,tsywpzzxt',
                contentType: 'application/json',
                dataType: "json",
                async:false,
                success: function (data) {
                    if(data){
                        if(data.tsywpzlx){
                            zdx.pzlxZd = data.tsywpzlx;
                            appendSelect(data.tsywpzlx, '#pzlx');
                        }
                        if(data.tsywpzmk){
                            zdx.gnmkZd = data.tsywpzmk;
                            appendSelect(data.tsywpzmk, '#gnmk');
                        }
                        if(data.tsywpzzxt){
                            zdx.pzzxtZd = data.tsywpzzxt;
                            formSelects.data('selectPzZxt','local',{arr: data.tsywpzzxt});
                        }
                        form.render('select');
                    }
                    function appendSelect(data, element){
                        $.each(data, function (index, item) {
                            $(element).append('<option value="' + item.DM + '">' + item.MC + '</option>');
                        });
                    }
                },
                error: function (e) {
                    response.fail(e, '获取字典项失败！');
                }
            });
        }

        var isSearch = true;
        //监听回车事件
        $(document).keydown(function (event) {
            if (event.keyCode == 13 && isSearch) { //绑定回车
                $(".bdc-search-box #search").click();
            }
        });
        //判断回车操作
        $('.bdc-table-box').on('focus', '.layui-laypage-skip .layui-input', function () {
            isSearch = false;
        }).on('blur', '.layui-laypage-skip .layui-input', function () {
            isSearch = true;
        });

        /**
         * 加载Table数据列表
         */
        table.render({
            elem: '#pzTable'
            ,id: 'pzTableId'
            ,page: true //开启分页
            ,defaultToolbar: ['filter']
            ,toolbar: "#toolbarDemo"
            ,url: BASE_URL+"/page"
            ,even: true
            ,cols: [[ //表头
                {type: 'checkbox', fixed: 'left',width:50}
                ,{field: 'pzmc', title: '配置名',sort:true}
                ,{field: 'pzz', title: '配置值'}
                ,{field: 'pzsm', title: '配置说明'}
                ,{field: 'pzlx', title: '配置类型',
                    templet: function (d) {
                        return changeDmToMc(d.pzlx, zdx.pzlxZd);
                    },sort:true}
                ,{field: 'pzzt', title: '配置状态',toolbar: '#ztTpl',sort:true}
                ,{field: 'pzzxt', title: '配置子系统',templet: function (d) {
                        return changeDmToMc(d.pzzxt, zdx.pzzxtZd);
                    },sort:true}
                ,{field: 'gnmk', title: '功能模块',templet: function (d) {
                        return changeDmToMc(d.gnmk, zdx.gnmkZd);
                    },sort:true}
                ,{field: 'sfxycq', title: '是否需要重启',templet: function (d) {
                        if(d.sfxycq ==0){
                            return '否'

                        }else{
                            return '是'
                        }
                    },sort:true}
                ,{title: '操作', width: 100,fixed: 'right',toolbar: '#barDemo'}
            ]]
            ,request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            }
            ,autoSort: false
            ,parseData: function (res) {
                    return {
                        code: res.code, //解析接口状态
                        msg: res.msg, //解析提示文本
                        count: res.totalElements, //解析数据长度
                        data: res.content //解析数据列表
                    }
                }
            ,done: function () {
                $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');
                $('.bdc-percentage-container').css('padding-top',$('.bdc-search-content').height() + 10);


                if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
                    $('.layui-table-body .layui-none').html('<img src="../../../static/lib/registerui/image/table-none.png"  alt="">无数据');
                }else {
                    $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                    $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
                }
            }
        });

        //头工具栏事件
        table.on('toolbar(pzFilter)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            switch (obj.event) {
                case 'add':
                    addPz();
                    break;
                case 'exportPzx':
                    exportBdcTsywPz(checkStatus.data);
                    break;
                case 'modelPz':
                    modelPz(checkStatus.data);
                    break;
                case 'delete':
                    deleteBdcTsywpz(checkStatus.data);
                    break;
            }

        });

        //监听工具条
        table.on('tool(pzFilter)', function(obj){
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            console.log(data);

            if(layEvent === 'edit'){ //配置
                if(data.pzlx ===1){
                    //赋值
                    $('.bdc-text-pzsm').html(data.pzsm);
                    $("#bdc-text-pzz").val(data.pzz);
                    //普通字符串
                    layer.open({
                        title: '配置',
                        type: 1,
                        area: ['430px'],
                        btn: ['提交', '取消'],
                        content: $('#textPz')
                        ,yes: function(index, layero){
                            //提交 的回调
                            var textpzz = $("#bdc-text-pzz").val();
                            if(isNotBlank(textpzz)){
                                var pzz = textpzz;
                                savePzzWithGzyz(pzz,data);
                            }

                        }
                        ,btn2: function(index, layero){
                            //取消 的回调
                            layer.close(index);
                        }
                    });

                }else if(data.pzlx ===2 ||data.pzlx ===3 ||data.pzlx ===4 ||data.pzlx ===5||data.pzlx ===6){
                    var url =getContextPath() + "/view/config/tsywpz/xxpz.html?pzid="+data.pzid;
                    if(data.pzlx ===2){
                        //流程多选
                        url =url +"&type=lc";
                    }else{
                        //字典项
                        url =url +"&type=zd";
                    }
                    sessionStorage.tsywpzLcIsChange = false;
                    layer.open({
                        title: "配置",
                        type: 2,
                        area: ['100%','100%'],
                        content: url,
                        cancel: function(index, layero){
                            if(sessionStorage.tsywpzLcIsChange){
                                table.reload('pzTableId');
                            }
                            sessionStorage.removeItem('tsywpzLcIsChange');
                        }
                    });
                }else if(data.pzlx ===7){
                    $(".bdc-radio-pzsm").html(data.pzsm);
                    var radioSelectState = '';
                    //布尔型
                    layer.open({
                        title: '配置',
                        type: 1,
                        area: ['430px'],
                        btn: ['提交', '取消'],
                        content: $('#bdc-popup-radio')
                        ,success: function(){
                            if(data.pzz == 'true'){
                                $('.bdc-radio-yes').attr("checked","checked");
                            }else {
                                $('.bdc-radio-no').attr("checked","checked");
                            }
                            form.render('radio');
                            form.on('radio(boolFilter)', function(data){
                                radioSelectState = data.value;
                            });
                        }
                        ,yes: function(index, layero){
                            //提交 的回调
                            if(radioSelectState != ''){
                                savePzzWithGzyz(radioSelectState,data);
                            }
                            layer.close(index);
                        }
                        ,btn2: function(index, layero){
                            //取消 的回调
                            layer.close(index);
                        }
                    });

                }else if(data.pzlx ===8){
                    //数组字符串
                    sessionStorage.tsywpzBgData = JSON.stringify(data);
                    sessionStorage.tsywpzBgIsChange = false;
                    layer.open({
                        title: "配置",
                        type: 2,
                        area: ['960px','500px'],
                        content: getContextPath() + "/view/config/tsywpz/bglx.html",
                        cancel: function(index, layero){
                            if(sessionStorage.tsywpzBgIsChange){
                                table.reload('pzTableId');
                            }
                            sessionStorage.removeItem('tsywpzBgIsChange');
                        }
                    });

                }else if(data.pzlx ===9){
                    layer.open({
                        title: "配置",
                        type: 2,
                        area: ['960px','500px'],
                        content: getContextPath() + "/view/config/tsywpz/xsdlx.html",
                        success: function(layero,index){
                            var iframeWin = window[layero.find('iframe')[0]['name']];
                            iframeWin.renderForm(data);
                        },
                        end: function(){
                            table.reload('pzTableId');
                        }
                    });
                }
            }
            if(layEvent === 'modify'){
                modifyBdcTsywpz(data);
            }
        });

        /**
         * 监听排序事件
         */
        table.on('sort(pzFilter)', function (obj) {
            var getSearch = form.val("searchFilter");
            getSearch.sort =obj.field+","+obj.type;
            table.reload('pzTableId', {
                initSort: obj
                ,where:getSearch
            });
        });

        // 加载上传配置项事件绑定
        importBdcTsywPz();

        //3. 输入框删除图标
        if(!(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion.match(/9./i)=="9."))
        {
            //监听input点击
            $('.layui-form-item .layui-input-inline').on('click','.layui-icon-close',function () {
                $(this).siblings('.layui-input').val('');
            });

            $('.layui-form-item .layui-input-inline .layui-input').on('focus',function () {
                $(this).siblings('.layui-icon-close').removeClass('bdc-hide');
            }).on('blur',function () {
                var $this = $(this);
                setTimeout(function () {
                    $this.siblings('.layui-icon-close').addClass('bdc-hide');
                },150)
            });
        }

        //字典项代码转名称
        function changeDmToMc(dm, zdList) {
            if(dm){
                var mc = dm.toString();
                if (isNotBlank(zdList)) {
                    for (var i = 0; i < zdList.length; i++) {
                        var zd = zdList[i];
                        if(mc.indexOf(zd.DM)>-1){
                            mc = mc.replace(zd.DM, zd.MC);
                        }
                    }
                }
                return mc;
            }else{
                return "";
            }
        }

        //保存配置值(包括规则验证)
        function savePzzWithGzyz(pzz,tsywpzDO){
            //获取配置值
            addModel();
            if(isNotBlank(tsywpzDO.pzyzzhbs)) {
                //调用规则验证
                var bdcGzYzQO = {};
                bdcGzYzQO.zhbs = tsywpzDO.pzyzzhbs;
                var gzyzParamMap = {};
                gzyzParamMap.pzz = pzz;
                bdcGzYzQO.paramMap = gzyzParamMap;
                gzyzCommon(bdcGzYzQO, function (data) {
                    console.log("验证通过");
                    //保存方法
                    savePzz(pzz,tsywpzDO.pzid,"save");
                });
            }else{
                //保存方法
                savePzz(pzz,tsywpzDO.pzid,"save");
            }

        }

        //保存配置值
        function savePzz(pzz,pzid,type){
            $.ajax({
                type: 'PUT',
                url: getContextPath() +"/rest/v1.0/tsywpz/update",
                contentType: 'application/json',
                dataType: "json",
                data: JSON.stringify({pzid: pzid,pzz: pzz}),
                success: function (data) {
                    removeModal();
                    if(data == 1){
                        if(type ==="delete") {
                            delSuccessMsg();
                        }else{
                            saveSuccessMsg();
                            table.reload('pzTableId');
                        }
                    }
                }
            });

        }

        //新增配置
        function addPz() {
            var index = layer.open({
                type: 2,
                title: "新增特殊业务配置",
                area: ['1150px', '600px'],
                fixed: false, //不固定
                maxmin: true, //开启最大化最小化按钮
                content:["../tsywpz/addOrEditPz.html?action=add", 'yes'],
                success: function (layero, index) {
                },
                end:function(){
                    table.reload('pzTableId');
                }

            });
            layer.full(index);
            
        }

        //功能模块配置
        function modelPz(data){
            if(!data || data.length == 0){
                layer.msg('<img src="../../../static/lib/bdcui/images/warn-small.png" alt="">请选择需要配置的记录');
                return;
            }

            layui.use(['jquery', 'form', 'laytpl'], function () {
                var laytpl = layui.laytpl;
                var tpl = modelPzTpl.innerHTML;
                var view = document.getElementById('modelpzdiv');
                laytpl(tpl).render(zdx.gnmkZd, function (html) {
                    view.innerHTML = html;
                });
            });

            form.render('select');
            layer.open({
                type : 1,
                title: "配置功能模块",
                area: ['430px','300px'],
                btn : ['确定','取消'],
                content: $("#modelpzdiv"),
                yes: function(index, layero){
                    //提交 的回调
                    var gnmkpz = $("#gnmkpz").val();
                    var bdcTsywpzAry = [];
                    $.each(data,function(index, item){
                        bdcTsywpzAry.push({
                            pzid : item.pzid,
                            gnmk : gnmkpz
                        });
                    });
                    $.ajax({
                        url: getContextPath()+"/rest/v1.0/tsywpz/pl/modifyGnmk",
                        type: "POST",
                        data: JSON.stringify(bdcTsywpzAry),
                        contentType: 'application/json',
                        dataType: "json",
                        success: function (data) {
                            layer.msg('<img src="../../../static/lib/bdcui/images/success-small.png" alt="">修改成功');
                            layer.close(index);
                            setTimeout(function () {
                                table.reload('pzTableId');
                            }, 500);
                        },
                        error:function(){
                            layer.msg('<img src="../../../static/lib/bdcui/images/warn-small.png" alt="">修改失败，请重试！');
                        }
                    });
                }
                ,btn2: function(index, layero){
                    //取消 的回调
                    layer.close(index);
                }
            });
        }

        /**
         * 配置项导出
         * @param data
         */
        function exportBdcTsywPz(data){
            if(!data || data.length == 0){
                layer.msg('<img src="../../../static/lib/bdcui/images/warn-small.png" alt="">请选择需要导出的记录');
                return;
            }

            $("#filedata").val(JSON.stringify(data));
            $("#tsywpzfile").submit();

        }

        /**
         * 配置项导入
         * @param
         */
        function importBdcTsywPz(){
            upload.render({
                elem: '#importPzx' //绑定元素
                ,url: BASE_URL + '/import' //上传接口
                ,accept: 'file'
                ,acceptMime: 'file/txt'
                ,exts: 'txt'
                ,before: function(obj){
                    addModel();
                }
                ,done: function(res){
                    removeModal();
                    //上传完毕回调
                    if(res.code == 0){
                        layer.msg('<img src="../../../static/lib/bdcui/images/success-small.png" alt="">导入成功，即将刷新页面');
                        setTimeout(function () {
                            location.reload();
                        }, 500);

                    } else {
                        if(isNotBlank(res.msg)){
                            layer.msg('<img src="../../../static/lib/bdcui/images/warn-small.png" alt="">'+res.msg);

                        }else{
                            layer.msg('<img src="../../../static/lib/bdcui/images/warn-small.png" alt="">导入失败，请选择正确文件！');
                        }
                    }
                }
                ,error: function(){
                    removeModal();
                    layer.msg('<img src="../../../static/lib/bdcui/images/warn-small.png" alt="">导入失败，请选择正确文件！');
                }
                ,error: function(){
                    removeModal();
                    layer.msg('<img src="../../../static/lib/bdcui/images/warn-small.png" alt="">导入失败！');
                }
            });
        }

        /**
         * 配置内容删除
         */
        function deleteBdcTsywpz(data){
            if(!data || data.length == 0){
                layer.msg('<img src="../../../static/lib/bdcui/images/warn-small.png" alt="">请选择需要删除配置的记录');
                return;
            }
            var deleteIndex = layer.open({
                type: 1,
                skin: 'bdc-small-tips',
                title: '确认删除',
                area: ['320px'],
                content: '是否确认删除？',
                btn: ['确定', '取消'],
                btnAlign: 'c',
                yes: function () {
                    var ids  = [];
                    $.each(data,function(index, item){
                        ids.push(item.pzid);
                    });
                    addModel();
                    $.ajax({
                        type: 'DELETE',
                        url: getContextPath() +"/rest/v1.0/tsywpz",
                        contentType: 'application/json',
                        dataType: "json",
                        data: JSON.stringify(ids),
                        success: function () {
                            removeModal();
                            layer.msg('<img src="../../../static/lib/bdcui/images/success-small.png" alt="">删除成功');
                            setTimeout(function () {
                                table.reload('pzTableId');
                            }, 500);
                        },
                        error: function(err){
                            removeModal();
                            delAjaxErrorMsg(err);
                        }
                    });
                    layer.close(deleteIndex);
                },
                btn2: function () {
                    //取消
                    layer.close(deleteIndex);
                }
            });


        }

        //配置项修改
        function modifyBdcTsywpz(data){
            var index = layer.open({
                type: 2,
                title: "修改特殊业务配置",
                area: ['1150px', '600px'],
                fixed: false, //不固定
                maxmin: true, //开启最大化最小化按钮
                content:["addOrEditPz.html?action=modify&pzid="+data.pzid, 'yes'],
                success: function (layero, index) {

                },
                end:function(){
                    table.reload('pzTableId');
                }
            });
            layer.full(index);
        }
    });
});

/**
 * 文件导入
 */
function importFile(fileObj) {
    addModel();
    $("#uploadFile").ajaxSubmit({
        type: 'post',
        url: getContextPath()+"/rest/v1.0/tsywpz/yml/import/excel",
        success: function (res) {
            removeModal();
            var resJson = JSON.parse(res);
            if(resJson.result){
                if(isNotBlank(resJson.data)){
                    layer.confirm('导入成功,部分配置项信息缺失', {
                        btn: ['查看详情', '取消'] //可以无限个按钮
                    }, function(index, layero){
                        showXxqsImportData(resJson.data);
                        layer.close(index);
                    }, function(index){
                        layer.close(index);
                        setTimeout(function () {
                            location.reload();
                        }, 500);
                    });
                }else{
                    layer.msg(resJson.message);
                }
            }else{
                layer.msg(res.message);
            }
        },
        error: function (XmlHttpRequest, textStatus, errorThrown) {
            removeModal();
            layer.msg("导入失败！");
        }
    });
    return false;
}

/**
 * 展示录入不全的配置信息
 * @param data 录入不全的配置内容
 */
function showXxqsImportData(data){
    var json = exculdeNoConfigSystem(data);
    if(json.length == 0){
        layer.msg("无信息缺失的配置内容");
        return;
    }
    var html = [];
    html.push("<table class=\"layui-table bdc-form-table\" id='import-pz-table'><colgroup><col width=\"350\"><col width=\"400\"><col width=\"300\"></colgroup><thead><tr><th>配置名称</th><th>配置值</th><th>配置说明</th><th>操作</th></tr></thead><tbody>");
    $.each(json, function(index, value){
        html.push("<tr><input type=\"hidden\" name=\"pzid\" value='"+value.pzid+"'><td>"+value.pzmc+"</td><td>"+value.pzz+"</td><td class=\"bdc-td-box\"><input type=\"text\" name=\"pzsm\" value='"+value.pzsm+"'></td>" +
            "<td><button type=\"button\" class=\"layui-btn layui-btn-danger layui-btn-xs bdc-delete-btn\"\n" +
            "                                id=\"pzdel\" name=\"pzdel\"\n" +
            "                                onclick=\"deletePz('"+value.pzid+"',this)\">\n" +
            "                            删除\n" +
            "                        </button></td></tr>");
    });
    html.push("</tbody></table>");
    layer.open({
        title: "配置信息缺失详细内容",
        content: html.join(""),
        btn: ['提交', '取消'],
        area: 'auto',
        maxHeight: 500,
        maxWidth: 1100,
        yes : function(index, layero){
            addModel();
            var pzxx =[];
            $("#import-pz-table tr").each(function () {
                var pz ={};
                var pzid =$(this).find('[name="pzid"]').val();
                if(isNotBlank(pzid)) {
                    pz.pzid =pzid;
                    pz.pzsm = $(this).find('[name="pzsm"]').val();
                    pzxx.push(pz);
                }
            });
            savePzxx(pzxx,index);
        },
        btn2: function (index) {
            //取消
            setTimeout(function () {
                location.reload();
            }, 500);
            layer.close(index);
        }
    });

}

//根据主键删除配置项
function deletePz(pzid,elem){
    var deleteIndex = layer.open({
        type: 1,
        skin: 'bdc-small-tips',
        title: '确认删除',
        area: ['320px'],
        content: '是否确认删除？',
        btn: ['确定', '取消'],
        btnAlign: 'c',
        yes: function () {
            var ids  = [];
            ids.push(pzid);
            addModel();
            $.ajax({
                type: 'DELETE',
                url: getContextPath() +"/rest/v1.0/tsywpz",
                contentType: 'application/json',
                dataType: "json",
                data: JSON.stringify(ids),
                success: function () {
                    removeModal();
                    $(elem).parents("tr").remove();
                },
                error: function(err){
                    removeModal();
                    delAjaxErrorMsg(err);
                }
            });
            layer.close(deleteIndex);
        },
        btn2: function () {
            //取消
            layer.close(deleteIndex);
        }
    });


}

//保存配置信息
function savePzxx(pzxx,layerIndex){
    if(pzxx &&pzxx.length >0){
        $.ajax({
            type: 'PUT',
            url: getContextPath() +"/rest/v1.0/tsywpz/update/pl",
            contentType: 'application/json',
            dataType: "json",
            data: JSON.stringify(pzxx),
            success: function (data) {
                removeModal();
                setTimeout(function () {
                    location.reload();
                }, 500);
                layer.close(layerIndex);
            }
        });
    }else{
        removeModal();
        setTimeout(function () {
            location.reload();
        }, 500);
        layer.close(index);
    }
}

/**
 * 过滤掉非字典项中配置子系统的配置信息
 */
function exculdeNoConfigSystem(data){
    var unExportData = JSON.parse(data);
    var filterData = [];
    $.each(unExportData, function(index, data){
        var pzzxt = data.pzzxt;
        if(isNotBlank(pzzxt)){
            $.each(zdx.pzzxtZd, function (index, zxt) {
                if(pzzxt.indexOf(zxt.DM) > -1){
                    filterData.push(data);
                    return false;
                }
            })
        }
    });
    return filterData;
}