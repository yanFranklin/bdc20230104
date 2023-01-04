/**
 * Created by Ypp on 2020/1/3.
 * 规则展现js
 */
layui.config({
    base: '../static/' //静态资源所在路径
}).extend({
    formSelects: 'lib/form-select/formSelects-v4',response:'lib/bdcui/js/response'
});
//流程
var lcList;

layui.use(['form','jquery','table','layer','formSelects','response'],function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        formSelects = layui.formSelects,
        response = layui.response,
        layer = layui.layer;

    $(function(){
        // 获取页面表单标识，用于权限控制
        var moduleCode = $.getUrlParam('moduleCode');
        if(moduleCode){
            setElementAttrByModuleAuthority(moduleCode);
        }
        //输入框删除图标
        if(!(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion.match(/9./i)=="9.")) {
            //监听input点击
            $('.layui-form-item .layui-input-inline').on('click','.layui-icon-close',function () {
                $(this).siblings('.layui-input').val('');
                $(this).siblings().find('.layui-input').val('');
                $(this).siblings('select').val('');
            });

            $('.layui-form-item .layui-input-inline .layui-input').on('focus',function () {
                $(this).siblings('.layui-icon-close').removeClass('bdc-hide');
                $(this).parents('.layui-input-inline').find('.layui-icon-close').removeClass('bdc-hide');
                $(this).siblings('.layui-edge').addClass('bdc-hide');
            }).on('blur',function () {
                var $this = $(this);
                setTimeout(function () {
                    $this.siblings('.layui-icon-close').addClass('bdc-hide');
                    $this.parents('.layui-input-inline').find('.layui-icon-close').addClass('bdc-hide');
                    $this.siblings('.layui-edge').removeClass('bdc-hide');
                },150)
            });
        }

        table.render({
            elem: '#pageTable',
            id:'pageTableId',
            toolbar: '#toolbarDemo',
            title: '规则表',
            request: {
                limitName: 'size', //每页数据量的参数名，默认：limit
                loadTotal: true,
                loadTotalBtn: false
            },
            defaultToolbar: ['filter'],
            even: true,
            cols: [[
                {title:'', toolbar: '#barDemo', width:60, fixed: 'left'},
                {type: 'checkbox', width:50, fixed: 'left'},
                {field:'GZID', title:'ID',hide:true},
                {field:'GZFZ', title:'规则分组',hide:true},
                {field:'GZCODE', title:'规则code'},
                {field:'GZMS', title:'规则描述'},
                {field:'GZLX', title:'规则类型', templet: '#gzlxTpl'},
                {field:'BHLC', title:'包含流程',templet: '#bhlcTpl'},
                {field:'QCLC', title:'去除流程',templet: '#qclcTpl'},
                {field:'TSXX', title:'提示信息'},
                {field:'GZDJ', title:'规则等级', templet: '#gzdjTpl'},
                {field:'SFHL', title:'是否忽略', templet: function (d) {
                    if (d.SFHL == 0) {
                        return '<span class="bdc-red"> 不忽略 </span>';
                    } else if (d.SFHL == 1) {
                        return '<span class="bdc-gray"> 忽略 </span>';
                     }else{
                        return "";
                    }
                }}
            ]],
            data: [],
            parseData: function (res) { //res 即为原始返回的数据
                return {
                    "code": res.code, //解析接口状态
                    "msg": res.message, //解析提示文本
                    "count": res.totalElements, //解析数据长度
                    "data": res.content //解析数据列表
                };
            },
            page: true,
            done: function () {
                $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');
                if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
                    $('.layui-table-body .layui-none').html('<img src="../static/lib/bdcui/images/table-none.png" alt="">无数据');
                }else {
                    $('.layui-table-main.layui-table-body').height($('.bdc-table-box').height() - 131);
                }
            }
        });
        var isSubmit = true;
        form.verify({
            required: function(value,item) {
                if(value == ''){
                    $(item).addClass('layui-form-danger');
                    isSubmit = false;
                }
            }
        });

        //获取字典表
        $.ajax({
            type: "get",
            url: getContextPath() + "/rest/v1.0/queryGzxx/lcList",
            async:false,
            dataType:"json",
            success: function (data) {
                lcList=data;
                var select=[];
                $.each(data, function (index, item) {
                    select.push({"name":item.name,"value":item.processDefKey})
                });
                formSelects.data('bhlcSelect', 'local', {
                    arr: select
                });
                formSelects.data('qclcSelect', 'local', {
                    arr: select
                });
            }, error: function (e) {
                response.fail(e, '')
            }
        });

        //获取字典表
        $.ajax({
            type: "get",
            url: getContextPath() + "/rest/v1.0/queryGzxx/gzfzList",
            async:false,
            dataType:"json",
            success: function (data) {
                if(data){
                    $.each(data, function (index, item) {
                        $('#gzfz').append(new Option(item.mc, item.dm));
                    });
                }
            }, error: function (e) {
                response.fail(e, '')
            }
        });
        layui.form.render("select");

        var pzIndex;
        //头工具栏事件
        table.on('toolbar(test)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id);
            switch(obj.event){
                case 'add':
                    pzIndex=layer.open({
                        title: '新增配置',
                        type: 1,
                        skin: 'bdc-xzpz-layer',
                        area: ['960px','400px'],
                        content: $('#bdc-popup-large')
                        ,cancel: function(){
                            formReset();
                        }
                        ,success: function () {
                        }
                    });
                    break;
                case 'update':
                    var selectedNum = checkStatus.data.length;
                    if (selectedNum != 1) {
                        layer.msg('请选择一条数据进行操作！');
                        return false;
                    }
                    pzIndex=layer.open({
                        title: '修改配置',
                        type: 1,
                        skin: 'bdc-xzpz-layer',
                        area: ['960px','400px'],
                        content: $('#bdc-popup-large')
                        ,cancel: function(){
                            formReset();
                        }
                        ,success: function () {
                            var data=checkStatus.data[0];
                            $("#gzid").val(data.GZID);
                            $("#gzcode").val(data.GZCODE);
                            $("#gzms").val(data.GZMS);
                            $("#gzlx").val(data.GZLX);
                            $("#gzfz").val(data.GZFZ);
                            if(!isNullOrEmpty(data.BHLC)){
                                formSelects.value('bhlcSelect',data.BHLC.split(","));
                            }
                            if(!isNullOrEmpty(data.QCLC)){
                                formSelects.value('qclcSelect',data.QCLC.split(","));
                            }
                            $("#gzdj").val(data.GZDJ);
                            $("#tsxx").val(data.TSXX);
                            layui.form.render("select");
                        }
                    });
                    break;
                case 'delete':
                    var selectedNum = checkStatus.data.length;
                    if (selectedNum == 0) {
                        layer.msg('请选择数据进行操作！');
                        return false;
                    }
                    deleteGz(checkStatus,"pageTableId");
                    break;
                case 'ignore':
                    var selectedNum = checkStatus.data.length;
                    if (selectedNum == 0) {
                        layer.msg('请选择数据进行操作！');
                        return false;
                    }
                    ignoreOrSelectGz(checkStatus,"pageTableId",1);
                    break;
                case 'select':
                    var selectedNum = checkStatus.data.length;
                    if (selectedNum == 0) {
                        layer.msg('请选择数据进行操作！');
                        return false;
                    }
                    ignoreOrSelectGz(checkStatus,"pageTableId",0);
                    break;
            }
        });

        form.on('submit', function(data){
            if(!isSubmit){
                layer.msg('必填项不能为空', {icon: 5});
                isSubmit = true;
                return false;
            }else{
                $.ajax({
                    type: "post",
                    url: getContextPath() + "/rest/v1.0/queryGzxx/saveGzxx",
                    contentType: "application/json;charset=utf-8",
                    dataType: "json",
                    data: JSON.stringify(form.val('xzpzFilter')),
                    success: function (data) {
                        if (data) {
                            layer.msg(data.result);
                            renderTable("pageTableId");
                            if(isNullOrEmpty($("#gzid").val())){
                                var logoutIndex = layer.confirm('是否继续编辑？', {
                                    title: '提示',
                                    btn: ['继续', '关闭'] //按钮
                                }, function (index) {
                                    $("#gzid").val(data.gzid);
                                    layer.close(logoutIndex);
                                },function () {
                                    formReset();
                                    layer.close(pzIndex);
                                });
                            }else{
                                formReset();
                                layer.close(pzIndex);
                            }
                        }
                    }, error: function (e) {
                        response.fail(e, '')
                    }, complete: function () {
                        removeModal();
                    }
                });
                return false;
            }
        });

        //监听保存
        $('#savePz').on('click',function(){
        });

        //监听取消
        $('#cancelPz').on('click',function(){
            formReset();
            layer.close(pzIndex);
        });
        //监听可能原因及解决方案
        $('#knyy').on('click',function(){
            console.log('bbb');
        });
        //监听行内操作
        table.on('tool(test)', function(obj){
            var data = obj.data;
            var $tr = $(obj.tr[0]);
            var $trHeader = $(obj.tr[1]);
            if(obj.event === 'show'){
                // 显示按钮
                $(this).toggleClass('bdc-hide').siblings('.bdc-show-btn').toggleClass('bdc-hide');
                if(data.GZLX==1){
                    $.ajax({
                        url:getContextPath()+"/rest/v1.0/queryGzxx/querySql?gzcode="+ data.GZCODE,
                        type:'post',
                        dataType:'json',
                        success:function (data) {
                            var word = data.querySql;
                            if(isNullOrEmpty(word)){
                                layer.msg('未添加SQL，请联系开发人员!', {icon: 5});
                            }else{
                                addTr($tr,$trHeader,word);
                            }
                        }, error: function (e) {
                            response.fail(e, '')
                        }, complete: function () {
                            removeModal();
                        }
                    });
                }else{
                    layer.msg('服务类型规则无法查看SQL!', {icon: 5});
                }
            } else if(obj.event === 'hide'){
                //隐藏按钮列
                $(this).toggleClass('bdc-hide').siblings('.bdc-show-btn').toggleClass('bdc-hide');
                $tr.next().remove();
                $trHeader.next().remove();
            }
        });

        //查询
        $("#search").click(function(){
            // 获取查询内容
            var obj = {};
            obj["gzcode"] = $("#code").val();
            obj["tsxx"] = $("#tsxxSearch").val();
            obj["sfhl"] = $("#sfhlSearch").val();
            // 重新请求
            table.reload("pageTableId", {
                url:getContextPath()+"/rest/v1.0/queryGzxx/getGzxxListPagesJson",
                where: obj
                , page: {
                    curr: 1  //重新从第 1 页开始
                }
            });
        })

        //可能原因功能
        $("#knyyLink").click(function(){
            jjfaTable();
        })


        //显示表格
        $("#search").click();

        //新增tr，显示sql内容
        function addTr($tr,$trHeader,word){
            $tr.after('<tr><td colspan="2"></td><td class="bdc-add-td-content" colspan="10">' +
                '<span>查询SQL：</span>' +
                word +
                '</td></tr>');
            $trHeader.after('<tr class="bdc-fixed-left"><td colspan="2"><div class="layui-table-cell"></div></td></tr>');
            $trHeader.next().find('.layui-table-cell').height($tr.next().find('.bdc-add-td-content').height());
        }


        //删除规则
        function deleteGz(checkStatus,currentId) {
            var gzids = [];
            $.each(checkStatus.data, function (key, item) {
                gzids.push(item.GZID);
            });
            //提交 的回调
            addModel('删除中');
            $.ajax({
                type: "post",
                url: getContextPath() + "/rest/v1.0/queryGzxx/delGzxx",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: JSON.stringify(gzids),
                success: function (data) {
                    if (data) {
                        layer.msg(data.result);
                        renderTable(currentId);
                    }
                }, error: function (e) {
                    response.fail(e, '')
                }, complete: function () {
                    removeModal();
                }
            });
        }

        //规则忽略
        function ignoreOrSelectGz(checkStatus,currentId,sfhl){
            var gzids = [];
            $.each(checkStatus.data, function (key, item) {
                gzids.push(item.GZID);
            });
            //提交 的回调
            var tsxx =(sfhl ==1)?"忽略中":"选择中";
            addModel(tsxx);
            var sfhlGzVO ={};
            sfhlGzVO.gzidList =gzids;
            sfhlGzVO.sfhl =sfhl;
            $.ajax({
                type: "POST",
                url: getContextPath() + "/rest/v1.0/queryGzxx/sfhlGzxx",
                contentType: 'application/json;charset=utf-8',
                data: JSON.stringify(sfhlGzVO),
                success: function (data) {
                    if (data) {
                        layer.msg(data.result);
                        renderTable(currentId);
                    }
                }, error: function (e) {
                    response.fail(e, '')
                }, complete: function () {
                    removeModal();
                }
            });

        }

    })


    //表单清空
    function formReset(id) {
        if(isNullOrEmpty(id)){
            $("#gzForm")[0].reset();
            formSelects.value('bhlcSelect',[]);
            formSelects.value('qclcSelect',[]);
        }else{
            $("#"+id)[0].reset();
        }
    }

    //解决方案表格处理
    function jjfaTable() {
        var gzid=$("#gzid").val();
        //空的话提示保存
        if(isNullOrEmpty(gzid)){
            layer.msg("请先保存规则!");
            return;
        }else{
            layer.open({
                title: '可能原因及解决方案',
                type: 1,
                area: ['960px','420px'],
                content: $('#bdcJjfaLayer')
                ,success: function () {
                    table.render({
                        id: 'jjfaTableId',
                        elem: '#jjfaTable',
                        toolbar: '#toolbarJjfa',
                        title: '解决方案表格',
                        defaultToolbar: ['filter'],
                        request: {
                            limitName: 'size', //每页数据量的参数名，默认：limit
                            loadTotal: true,
                            loadTotalBtn: false
                        },
                        even: true,
                        cols: [[
                            {type: 'checkbox', width:50, fixed: 'left'},
                            {field:'KNYY', title:'可能原因'},
                            {field:'JJFA', title:'解决方案'}
                        ]],
                        url: getContextPath()+"/rest/v1.0/queryYyAndJjfa/queryYyAndJjfaPageJson?gzid="+gzid,
                        parseData: function (res) { //res 即为原始返回的数据
                            return {
                                "code": res.code, //解析接口状态
                                "msg": res.message, //解析提示文本
                                "count": res.totalElements, //解析数据长度
                                "data": res.content //解析数据列表
                            };
                        },
                        page: true,
                        done: function () {
                            $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');
                            if($('.layui-layer .bdc-table-box .layui-table-main>.layui-table').height() == 0){
                                $('.layui-layer .layui-table-body .layui-none').html('<img src="../static/lib/bdcui/images/table-none.png" alt="">无数据');
                            }
                            //头工具栏事件
                            table.on('toolbar(jjfaFilter)', function(obj){
                                var checkStatus = table.checkStatus(obj.config.id);
                                switch(obj.event){
                                    case 'add':
                                        layer.open({
                                            title: '新增解决方案',
                                            type: 1,
                                            area: ['960px','400px'],
                                            btn: ['提交', '取消'],
                                            content: $('#bdc-popup-jjfa')
                                            ,yes: function(index, layero){
                                                var knyy=$("#knyy").val();
                                                var jjfa=$("#jjfa").val();
                                                if(isNullOrEmpty(knyy) || isNullOrEmpty(jjfa)){
                                                    layer.msg('必填项不能为空', {icon: 5});
                                                    return false;
                                                }
                                                addModel();
                                                $.ajax({
                                                    type: "POST",
                                                    url: getContextPath() + "/rest/v1.0/queryYyAndJjfa/saveYyAndJjfa",
                                                    traditional:true,
                                                    data: {knyy: knyy, jjfa: jjfa,gzid:gzid},
                                                    success:function (data) {
                                                        layer.msg(data.result);
                                                        renderTable("jjfaTableId");
                                                        formReset("jjfaForm");
                                                        layer.close(index);
                                                    },error:function (e) {
                                                        response.fail(e, '');
                                                    }, complete: function () {
                                                        removeModal();
                                                    }
                                                });
                                            }
                                            ,btn2: function(){
                                                formReset("jjfaForm");
                                            }
                                            ,cancel: function(){
                                                formReset("jjfaForm");
                                            }
                                        });
                                        break;
                                    case 'update':
                                        var selectedNum = checkStatus.data.length;
                                        if (selectedNum != 1) {
                                            layer.msg('请选择一条数据进行操作！');
                                            return false;
                                        }
                                        pzIndex=layer.open({
                                            title: '修改解决方案',
                                            type: 1,
                                            area: ['960px','400px'],
                                            btn: ['提交', '取消'],
                                            content:  $('#bdc-popup-jjfa')
                                            ,yes: function(index, layero){
                                                var knyy=$("#knyy").val();
                                                var jjfa=$("#jjfa").val();
                                                if(isNullOrEmpty(knyy) || isNullOrEmpty(jjfa)){
                                                    layer.msg('必填项不能为空', {icon: 5});
                                                    return false;
                                                }
                                                addModel();
                                                $.ajax({
                                                    type: "POST",
                                                    url: getContextPath() + "/rest/v1.0/queryYyAndJjfa/saveYyAndJjfa",
                                                    traditional:true,
                                                    data: {knyy: knyy, jjfa: jjfa,id:$("#id").val()},
                                                    success:function (data) {
                                                        layer.msg(data.result);
                                                        renderTable("jjfaTableId");
                                                        formReset("jjfaForm");
                                                        layer.close(index);
                                                    },error:function (e) {
                                                        response.fail(e, '');
                                                    }, complete: function () {
                                                        removeModal();
                                                    }
                                                });
                                            }
                                            ,btn2: function(){
                                                formReset("jjfaForm");
                                            }
                                            ,cancel: function(){
                                                formReset("jjfaForm");
                                            },success: function () {
                                                var data=checkStatus.data[0];
                                                $("#id").val(data.ID);
                                                $("#knyy").val(data.KNYY);
                                                $("#jjfa").val(data.JJFA);
                                            }
                                        });
                                        break;
                                    case 'delete':
                                        var selectedNum = checkStatus.data.length;
                                        if (selectedNum == 0) {
                                            layer.msg('请选择数据进行操作！');
                                            return false;
                                        }
                                        var ids = [];
                                        $.each(checkStatus.data, function (key, item) {
                                            ids.push(item.ID);
                                        });
                                        //提交 的回调
                                        addModel('删除中');
                                        $.ajax({
                                            type: "post",
                                            url: getContextPath() + "/rest/v1.0/queryYyAndJjfa/delYyAndJjfa",
                                            contentType: "application/json;charset=utf-8",
                                            dataType: "json",
                                            data: JSON.stringify(ids),
                                            success: function (data) {
                                                if (data) {
                                                    layer.msg(data.result);
                                                    renderTable("jjfaTableId");
                                                }
                                            }, error: function (e) {
                                                response.fail(e, '')
                                            }, complete: function () {
                                                removeModal();
                                            }
                                        });
                                        break;
                                }
                            });
                        }
                    });
                }
            });
        }
    }
});

//获取流程名
function getLcmc(lcs){
    var result=[];
    if(lcList && !isNullOrEmpty(lcs)){
        var lcData=lcs.split(",");
        lcData.forEach( function(lc){
            lcList.forEach( function(item){
                if(lc==item.processDefKey){
                    result.push(item.name);
                    return;
                }
            })
        })
    }
    return result.join(",");
}