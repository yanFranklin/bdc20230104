
layui.config({
    base: '../static/' //静态资源所在路径
}).extend({
    response:'lib/bdcui/js/response'
});

var gzfzList=[];
/**
 * Created by Ypp on 2020/1/3.
 * 质检js
 */
layui.use(['form','jquery','table','laydate','layer','response'],function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        response = layui.response,
        laydate = layui.laydate,
        layer = layui.layer;

    $(function(){

        // 获取页面表单标识，用于权限控制
        var moduleCode = getQueryString("moduleCode");
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
            title: '质检表格',
            defaultToolbar: ['filter'],
            request: {
                limitName: 'size', //每页数据量的参数名，默认：limit
                loadTotal: true,
                loadTotalBtn: false
            },
            even: true,
            cols: [[
                {field:'GZFZ', title:'检查项',templet: '#gzfzTpl'},
                {field:'GZDJ', title:'规则等级', templet: '#jcdjTpl'},
                {field:'GZMS', title:'规则描述'},
                {field:'GZCODE', title:'规则code',templet: "#gzCodeTpl"},
                {field:'SL', title:'错误数'},
                {field:'XMSL', title:'错误项目数'},
                {title:'操作', templet: '#czTpl',width: '100'},
                {field:'GZID', title:'id',hide:true}
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
            done: function (res) {
                $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');
                if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
                    $('.layui-table-body .layui-none').html('<img src="../static/lib/bdcui/images/table-none.png" alt="">无数据');
                }else {
                    $('.layui-table-main.layui-table-body').height($('.bdc-table-box').height() - 81);
                }
                merge(res);
                //查看sql
                viewSql();
                //解决方案表格处理
                jjfaTable();
                //查看项目
                viewXm();
            }
        });

        //获取字典表
        $.ajax({
            type: "get",
            url: getContextPath() + "/rest/v1.0/queryGzxx/gzxxList",
            async:false,
            dataType:"json",
            success: function (data) {
                if(data){
                    $('#gzid').append(new Option("请选择", ""));
                    $('#gzcode').append(new Option("请选择", ""));
                    $.each(data, function (index, item) {
                        $('#gzid').append(new Option(item.gzms, item.gzid));
                        $('#gzcode').append(new Option(item.gzcode, item.gzcode));
                    });
                }
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
                    $('#gzfz').append(new Option("请选择", ""));
                    $.each(data, function (index, item) {
                        $('#gzfz').append(new Option(item.mc, item.dm));
                    });
                    gzfzList=data;
                }
            }, error: function (e) {
                response.fail(e, '')
            }
        });
        layui.form.render("select");
        //查询
        $("#search").click(function(){
            // 重新请求
            table.reload("pageTableId", {
                url:getContextPath()+"/rest/v1.0/queryGzxx/getGzxxGroupPagesJson",
                where: form.val('zjForm')
                , page: {
                    curr: 1  //重新从第 1 页开始
                }
            });
        })

        //显示表格
        $("#search").click();



        function merge(res) {
            var data = res.data;
            var mergeIndex = 0;//定位需要添加合并属性的行数
            var mark = 1; //这里涉及到简单的运算，mark是计算每次需要合并的格子数
            var columsName = "GZFZ";//需要合并的列名称
            var columsIndex = 0;//需要合并的列索引值

            var trArr = $(".layui-table-body>.layui-table").find("tr");//所有行
            for (var i = 1; i < res.data.length; i++) { //这里循环表格当前的数据
                var tdCurArr = trArr.eq(i).find("td").eq(columsIndex);//获取当前行的当前列
                var tdPreArr = trArr.eq(mergeIndex).find("td[data-field="+ columsName +"]");//获取相同列的第一列
                if (data[i][columsName] === data[i-1][columsName]) { //后一行的值与前一行的值做比较，相同就需要合并
                    mark += 1;
                    tdPreArr.each(function () {//相同列的第一列增加rowspan属性
                        $(this).attr("rowspan", mark);
                    });
                    tdCurArr.each(function () {//当前行隐藏
                        $(this).css("display", "none");
                    });
                }else {
                    mergeIndex = i;
                    mark = 1;//一旦前后两行的值不一样了，那么需要合并的格子数mark就需要重新计算
                }
            }
            mergeIndex = 0;
            mark = 1;
        }

        //查看sql
        function viewSql(){
            //单击规则Code
            $('.bdc-gz-sql').on('click',function(){
                var gzcode=$(this).text();
                $.ajax({
                    url:getContextPath()+"/rest/v1.0/queryGzxx/querySql?gzcode="+gzcode,
                    type:'post',
                    async:false,
                    dataType:'json',
                    success:function (data) {
                        layer.open({
                            title: gzcode,
                            type: 1,
                            skin: 'bdc-gz-code-layer',
                            area: ['430px'],
                            btn: ['关闭'],
                            content: data.querySql
                            ,yes: function(index, layero){
                                layer.close(index);
                            }
                        });
                    }, error: function (e) {
                        response.fail(e, '')
                    }
                });
            });
        }

        //查看项目
        function viewXm(){
            //查看项目
            $('.bdc-show-xm').on('click',function(){
                var gzid=$(this).attr("param");
                layer.open({
                    title: '错误项目',
                    type: 1,
                    area: ['960px','600px'],
                    content: $('#bdcCwxmLayer')
                    ,success: function () {
                        $.ajax({
                            url:getContextPath()+"/rest/v1.0/queryGzxx/queryCwXmData?gzid="+gzid,
                            type:'post',
                            dataType:'json',
                            success:function (data) {
                                table.render({
                                    id: 'cwxmTableId',
                                    elem: '#cwxmTable',
                                    url:getContextPath()+"/rest/v1.0/queryGzxx/queryCwXmData?gzid="+gzid,
                                    title: '解决方案表格',
                                    defaultToolbar: ['filter'],
                                    request: {
                                        limitName: 'size', //每页数据量的参数名，默认：limit
                                        loadTotal: true,
                                        loadTotalBtn: false
                                    },
                                    even: true,
                                    cols: [[
                                        {field:'XMID', title:'项目ID'},
                                        {field:'SLBH', title:'受理编号'}
                                    ]],
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
                                        }
                                    }
                                });
                            }, error: function (e) {
                                response.fail(e, '')
                            }
                        });
                    }
                });
            });
        }

        //解决方案表格处理
        function jjfaTable() {
            //点击表格中查看
            $('.bdc-show-jjfa').on('click',function(){
                var gzid=$(this).attr("param");
                layer.open({
                    title: '可能原因及解决方案',
                    type: 1,
                    area: ['960px','420px'],
                    content: $('#bdcJjfaLayer')
                    ,success: function () {
                        $.ajax({
                            url:getContextPath()+"/rest/v1.0/queryYyAndJjfa/queryYyAndJjfa?gzid="+gzid,
                            type:'post',
                            dataType:'json',
                            success:function (data) {
                                var jjfaData=[];
                                if(data){
                                    jjfaData=data;
                                }
                                table.render({
                                    id: 'jjfaTableId',
                                    elem: '#jjfaTable',
                                    toolbar: '#toolbarJjfa',
                                    title: '解决方案表格',
                                    defaultToolbar: ['filter'],
                                    even: true,
                                    cols: [[
                                        {type: 'checkbox', width:50, fixed: 'left'},
                                        {field:'knyy', title:'可能原因'},
                                        {field:'jjfa', title:'解决方案'}
                                    ]],
                                    data: jjfaData,
                                    limit: 1000,
                                    done: function () {
                                        $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');
                                        if($('.layui-layer .bdc-table-box .layui-table-main>.layui-table').height() == 0){
                                            $('.layui-layer .layui-table-body .layui-none').html('<img src="../static/lib/bdcui/images/table-none.png" alt="">无数据');
                                        }else {
                                            $('.layui-layer .layui-table-main.layui-table-body').height($('.layui-layer .bdc-table-box').height() - 91);
                                        }
                                    }
                                });

                                //头工具栏事件
                                table.on('toolbar(jjfaFilter)', function(obj){
                                    var checkStatus = table.checkStatus(obj.config.id);
                                    switch(obj.event){
                                        case 'view':
                                            var selectedNum = checkStatus.data.length;
                                            if (selectedNum != 1) {
                                                layer.msg('请选择一条数据查看！');
                                                return false;
                                            }
                                            layer.open({
                                                title: '解决方案',
                                                type: 1,
                                                area: ['960px','400px'],
                                                content: $('#bdc-popup-jjfa')
                                                ,cancel: function(){
                                                    formReset("jjfaForm");
                                                }
                                                ,success: function () {
                                                    var data=checkStatus.data[0];
                                                    $("#jjfa").val(data.jjfa);
                                                    $("#knyy").val(data.knyy);
                                                }
                                            });
                                            break;
                                    }
                                });
                            }, error: function (e) {
                                response.fail(e, '')
                            }
                        });
                    }
                });
            });
        }
    })

    //表单清空
    function formReset(id) {
        $("#"+id)[0].reset();
    }
});

//获取规则分组
function getGzfzXx(dm){
    var result="";
    if(gzfzList && !isNullOrEmpty(dm)){
        gzfzList.forEach( function(item){
            if(dm==item.dm){
                result=item.mc;
                return result;
            }
        })
    }
    return result;
}