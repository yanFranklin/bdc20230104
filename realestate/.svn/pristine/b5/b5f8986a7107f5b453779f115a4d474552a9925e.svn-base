/**
 * Created by ypp on 2020/3/2.
 * 配置页面js
 */
layui.config({
    base: '../../../static/' //静态资源所在路径
}).extend({response:'lib/bdcui/js/response',formSelects:'lib/form-select/formSelects-v4'});
var lcmcData = [];
var zdyzdxData ={};
layui.use(['table','form','layer','formSelects'], function(){
    var table = layui.table,
        form = layui.form,
        formSelects = layui.formSelects,
        $ = layui.jquery,
        layer = layui.layer;



    $(function(){

        // 工作流定义名称
        getReturnData('/rest/v1.0/process/gzldymcs', '', 'GET', function (data) {
            data.forEach(function (v) {
                lcmcData.push({"name": v.name, "value": v.processDefKey});
            });
            formSelects.data('selectLcmc', 'local', {
                arr: lcmcData
            });
            formSelects.data('selectLcmcAdd', 'local', {
                arr: lcmcData
            });
        },function (error){
            delAjaxErrorMsg(error);
        },false);

        $.ajax({
            type: 'POST',
            url: getContextPath() +"/rest/v1.0/tsywpz/zdyzdx?zdyzdxbs=gxjkml",
            dataType: "json",
            async:false,
            success: function (data) {
                console.log(data);
                var zdyzdxArr=data.gxjkml;
                for (var i = 0; i < zdyzdxArr.length; i++) {
                    var zdyzdx = zdyzdxArr[i];
                    zdyzdxData[zdyzdx.zdxsjz]=zdyzdx.zdxxsz;
                }
            },
            error: function(err){
                delAjaxErrorMsg(err);
            }
        });

        var BASE_URL = getContextPath() +"/rest/v1.0/tsywpz";
        $('#search').on('click',function(){
            var getSearch = form.val("searchFilter");
            if(isNullOrEmpty(getSearch.lcmc)){
                getSearch.pzmc="gxjkml.";
            }else{
                getSearch.pzmc="gxjkml."+getSearch.lcmc;
            }
            // 重新请求
            table.reload("pzTableId", {
                where: getSearch
                , page: {
                    curr: 1  //重新从第 1 页开始
                }
            });
        });

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
            ,url: getContextPath() +"/rest/v1.0/gxjkpz/lcpz/page"
            ,even: true
            ,where: {
              'pzmc': "gxjkml"
            }
            ,cols: [[ //表头
                {type: 'checkbox', fixed: 'left',width:50}
                ,{field: 'gzldymc', title: '流程名称' ,minWidth: 100}
                ,{field: 'pzz', title: '父目录名称' ,minWidth: 300,templet: function (d) {
                    return getFmlmcs(d.fmldm);
                    }}
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


                if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
                    $('.layui-table-body .layui-none').html('<img src="../../../static/lib/registerui/image/table-none.png"  alt="">无数据');
                }else {
                    $('.bdc-percentage-container').css('padding-top',$('.bdc-search-content').height() + 10);
                    console.info($('.bdc-table-box').height());
                    $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                    $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
                }
            }
        });

        //头工具栏事件
        table.on('toolbar(pzFilter)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            switch (obj.event) {

            }

        });

        //监听工具条
        table.on('tool(pzFilter)', function(obj){
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            if(layEvent === 'edit'){ //配置
                var pzid =savePzx(data.gzldyid,data.gzldymc,data.pzid);
                var url =getContextPath() + "/view/config/tsywpz/xxpz.html?pzid="+pzid+"&type=zd";
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
            }

        });




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


        //新增配置
        function addPz() {
            $("#pzsm").val("");
            formSelects.value('selectLcmcAdd',[]);
            //小弹出层
            layer.open({
                title: '新增流程配置',
                type: 1,
                area: ['430px','350px'],
                btn: ['提交', '取消'],
                content: $('#bdc-popup-small')
                ,yes: function(index, layero){
                    //提交 的回调
                    var pzsm =$("#pzsm").val();
                    var pzmc="gxjkml."+formSelects.value('selectLcmcAdd','val');
                    var bdcTsywPzDTO={};
                    bdcTsywPzDTO.pzsm=pzsm;
                    bdcTsywPzDTO.pzmc=pzmc;
                    bdcTsywPzDTO.pzlx=5;
                    bdcTsywPzDTO.pzzzdxbs="gxjkml";
                    addModel();
                    $.ajax({
                        url: BASE_URL + "/saveBdcTsywPz",
                        type: "POST",
                        data: JSON.stringify(bdcTsywPzDTO),
                        contentType: 'application/json',
                        success: function (data) {
                            if (data) {
                                saveSuccessMsg();
                                removeModal();
                                layer.close(index);
                                table.reload('pzTableId');

                            }
                        },
                        error: function (xhr, status, error) {
                            delAjaxErrorMsg(xhr);
                            return false;
                        }
                    });
                }
                ,btn2: function(index, layero){
                    //取消 的回调

                }
                ,cancel: function(){
                    //右上角关闭回调
                    //return false 开启该代码可禁止点击该按钮关闭
                }
                ,success: function () {

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

        /**
         * 保存配置项
         */
        function savePzx(gzldyid,gzldymc,pzid){
            if(!isNullOrEmpty(pzid)){
                return pzid;
            }
            var bdcTsywPzDTO={};
            bdcTsywPzDTO.pzid=pzid;
            bdcTsywPzDTO.pzsm="【"+gzldymc+"】"+"共享接口配置";
            bdcTsywPzDTO.pzmc="gxjkml."+gzldyid;
            bdcTsywPzDTO.pzlx=5;
            bdcTsywPzDTO.pzzzdxbs="gxjkml";
            $.ajax({
                url: BASE_URL + "/saveBdcTsywPz",
                type: "POST",
                data: JSON.stringify(bdcTsywPzDTO),
                contentType: 'application/json',
                async:false,
                success: function (data) {
                    pzid =data;
                },
                error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr);

                }
            });
            return pzid;

        }

        function getLcmc(gzldyid){
            var mc = "";
            if (isNotBlank(lcmcData)) {
                for (var i = 0; i < lcmcData.length; i++) {
                    var lc = lcmcData[i];
                    if (gzldyid == lc.value) {
                        mc = lc.name;
                        break;
                    }
                }
            }
            return mc;
        }

        function getFmlmcs(fmldms){
            var fmlmcs="";
            if(!isNullOrEmpty(fmldms)){
                var fmldmArr =fmldms.split(",");
                for (var i = 0; i < fmldmArr.length; i++) {
                    var fmldm = fmldmArr[i];
                    var fmlmc =changeDmToMc(fmldm);
                    if(isNullOrEmpty(fmlmcs)){
                        fmlmcs =fmlmc;
                    }else{
                        fmlmcs+=","+fmlmc;
                    }

                }
            }
            return fmlmcs;

        }

        function changeDmToMc(dm){
            if (isNotBlank(zdyzdxData)) {
                return zdyzdxData[dm]
            }
            return "";
        }

    });
});
