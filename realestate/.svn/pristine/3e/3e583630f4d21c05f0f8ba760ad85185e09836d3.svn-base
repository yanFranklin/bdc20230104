/**
 *
 */
layui.use(['table','laytpl','layer'],function () {
    var table = layui.table,
        $ = layui.jquery,
        layer = layui.layer;
    form =layui.form;
    $(function () {

        var zdList = getMulZdList("qllx");
        var cqxmid =getQueryString("cqxmid");

        sessionStorage.sdglcheckedData = '{}';
        sessionStorage.dyhgz_sdglxx = '{}';
        //获取已选信息
        var xmsdData = {};
        if(sessionStorage.getItem("dyhgz_yxxmsdxx") &&sessionStorage.getItem("dyhgz_yxxmsdxx") !="undefined") {
            xmsdData = JSON.parse(sessionStorage.getItem("dyhgz_yxxmsdxx"));
        }
        var xmxxData =[];
        if(xmsdData ==null ||xmsdData.xmxx ==null){
            xmxxData =[];
        }else {
            xmxxData = xmsdData.xmxx;
        }
        var sdxxData =[];
        if(sdxxData ==null ||xmsdData.dysdxx ==null){
            sdxxData =[];
        }else {
            sdxxData = xmsdData.dysdxx;
        }
        var ygxxData =[];
        if(ygxxData ==null ||xmsdData.ygxx ==null){
            ygxxData =[];
        }else {
            ygxxData = xmsdData.ygxx;
        }
        //无产权历史关系表格渲染
        renderWcqlsgxTable();

        $("#xmsl").html(xmxxData.length +"条");
        $("#sdsl").html(sdxxData.length +"条");
        $("#ygsl").html(ygxxData.length +"条");
        table.render({
            elem: '#pageTable',
            title: '已选项目信息',
            even: true,
            limit: 3000,
            cols: [[
                {field:'qlr', title:'权利人', width: 150},
                {field:'ywr', title:'义务人', width: 150},
                {field:'zl', title:'坐落', minWidth: 150},
                {field:'djyy', title:'登记原因', width: 200},
                {field:'qllx', title:'权利类型', width: 250,templet: function (d) {
                    return changeDmToMc(d.qllx,zdList.qllx);

                    }},
                {field:'bdcdyh', title:'不动产单元号',width: 260},
                {field:'slbh', title:'受理编号', width: 150},
                {field:'slr', title:'受理人', width: 80}
            ]],
            data: xmxxData
        });
        table.render({
            elem: '#dysdTable',
            title: '单元锁定信息',
            even: true,
            limit: 3000,
            cols: [[
                {field:'bdcdyh', title:'不动产单元号', width: 300},
                {field:'sdr', title:'锁定人', width: 100},
                {field:'sdzt', title:'锁定状态', width: 100,templet: function (d) {
                        if(d.sdzt ==0){
                            return '解锁';
                        }else{
                            return '锁定';
                        }
                    }},
                {field:'sdyy', title:'锁定原因'}
            ]],
            data: sdxxData
        });
        table.render({
            elem: '#dyygable',
            title: '预告信息',
            even: true,
            limit: 3000,
            cols: [[
                {field:'qlr', title:'权利人', width: 150},
                {field:'ywr', title:'义务人', width: 150},
                {field:'zl', title:'坐落', minWidth: 150},
                {field:'djyy', title:'登记原因', width: 200},
                {field:'bdcdyh', title:'不动产单元号',width: 260},
                {field:'slbh', title:'受理编号', width: 120},
                {field:'slr', title:'受理人', width: 80}
            ]],
            data: ygxxData
        });



        //清空
        $("#clear").click(function () {
            layer.confirm('您确定要清空吗？', {
                title: '提示',
                btn: ['确定', '取消'] //按钮
            }, function (index) {
                sessionStorage.removeItem("dyhgz_yxxmsdxx");
                sessionStorage.removeItem("dyhgz_yxid");
                sessionStorage.removeItem("dyhgz_cqxmid");
                window.location.reload();
                layer.close(index);
            });

        });

        //替换单元号
        $("#thdyh").click(function () {
            var xmsdData ={};
            if(sessionStorage.getItem("dyhgz_yxxmsdxx") &&sessionStorage.getItem("dyhgz_yxxmsdxx") !="undefined") {
                xmsdData = JSON.parse(sessionStorage.getItem("dyhgz_yxxmsdxx"));
            }
            if(xmsdData ==null ||!xmsdData.yxcount||xmsdData.yxcount ===0){
                layer.alert("请先选择需要更新的数据");
                return false;
            }
            //合并手动关联信息
            dealSdglxx();
            //获取权籍管理代码
            var xmid =xmxxData[0].xmid;
            var qjgldm="";
            getReturnData("/rest/v1.0/dyhgz/xmfb?xmid="+xmid,{},"GET",function (data){
                qjgldm =data.qjgldm;

            },function (error){
                delAjaxErrorMsg(error);
            },false);
            layer.open({
                title: '不动产单元列表',
                type: 2,
                area: ['1300px', '450px'],
                content: '../dyhgz/bdcdyList.html?qjgldm='+qjgldm
                , cancel: function (index, layero) {
                    parent.layer.close(index);
                }
                , success: function () {

                }
            });
        });

        //关联单元锁定
        $("#gldysd").click(function () {
            var url="/realestate-inquiry-ui/view/dtcx/commonCx.html?cxdh=bdcdyplgxgldysd";
            var layerIndex = layer.open({
                title: '单元锁定信息',
                type: 2,
                area: ['960px', '450px'],
                content: url
                , cancel: function (index, layero) {
                    parent.layer.close(index);
                }
                , success: function () {

                }
            });
            layer.full(layerIndex);
        });

        //关联预告
        $("#glyg").click(function () {
            var url="/realestate-inquiry-ui/view/dtcx/commonCx.html?cxdh=bdcdyplgglyg";
            var layerIndex = layer.open({
                title: '预告信息',
                type: 2,
                area: ['960px', '450px'],
                content: url
                , cancel: function (index, layero) {
                    parent.layer.close(index);
                }
                , success: function () {

                }
            });
            layer.full(layerIndex);
        })

        //渲染无产权历史关系表格
        function renderWcqlsgxTable(){
            //查询单元号未关联历史关系项目信息
            $.ajax({
                url: getContextPath() + '/rest/v1.0/dyhgz/wcqlsgx',
                type: 'POST',
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(xmxxData),
                async:false,
                success: function (data) {
                    removeModal();
                    var wcqlsgxList =[];
                    dyhGzWcqlsgxDTO =data;
                    if((data.xzqlXmList ==null ||data.xzqlXmList.length ===0) &&(data.bdcDysdList ==null ||data.bdcDysdList.length ===0)){
                        //没有数据

                    }else{
                        $(".wcqlsgx").removeClass("bdc-hide");
                        if(data.xzqlXmList !=null &&data.xzqlXmList.length >0) {
                            for (var i = 0; i < data.xzqlXmList.length; i++) {
                                data.xzqlXmList[i].lx = "xm";
                                wcqlsgxList.push(data.xzqlXmList[i]);
                            }
                        }
                        if(data.bdcDysdList !=null &&data.bdcDysdList.length >0) {
                            for (var i = 0; i < data.bdcDysdList.length; i++) {
                                data.bdcDysdList[i].lx = "sd";
                                wcqlsgxList.push(data.bdcDysdList[i]);
                            }
                        }
                        table.render({
                            elem: '#wcqlsgxTable',
                            title: '手动关联信息',
                            even: true,
                            limit: 3000,
                            id: 'xmid',
                            cols: [[
                                {type: 'checkbox', fixed: 'left'},
                                {field:'qlr', title:'权利人', width: 140},
                                {field:'ywr', title:'义务人', width: 140},
                                {field:'zl', title:'坐落', minWidth: 150},
                                {field:'djyy', title:'登记原因（锁定原因）', width: 180,templet: function (d) {
                                        if(d.lx=="sd"){
                                            return d.sdyy?d.sdyy:"";
                                        }else{
                                            return d.djyy?d.djyy:"";
                                        }

                                    }},
                                {field:'qllx', title:'类型', width: 100,templet: function (d) {
                                        if(d.lx =="sd"){
                                            return '锁定';
                                        }else {
                                            return changeDmToMc(d.qllx, zdList.qllx);
                                        }

                                    }},
                                {field:'bdcdyh', title:'不动产单元号',width: 260},
                                {field:'slbh', title:'受理编号', width: 150},
                                {field:'slr', title:'受理人（锁定人）', width: 150,templet: function (d) {
                                        if(d.lx=="sd"){
                                            return d.sdr?d.sdr:"";
                                        }else{
                                            return d.slr?d.slr:"";
                                        }
                                    }},
                            ]],
                            text: {
                                none: '未查询到数据'
                            },
                            data: wcqlsgxList
                        });

                        //点击查询
                        $('#search').on('click', function () {
                            var qlrmc =$("#qlrmc").val();
                            var ywrmc =$("#ywrmc").val();
                            var zl =$("#zl").val();
                            var searchDataList =[];
                            for(var i=0;i<wcqlsgxList.length;i++) {
                                if(isNullOrEmpty(qlrmc) ||(isNotBlank(wcqlsgxList[i].qlr) &&wcqlsgxList[i].qlr.indexOf(qlrmc) >-1)){
                                    if(isNullOrEmpty(ywrmc) ||(isNotBlank(wcqlsgxList[i].ywr) &&wcqlsgxList[i].ywr.indexOf(ywrmc) >-1)){
                                        if(isNullOrEmpty(zl) ||(isNotBlank(wcqlsgxList[i].qlr) &&wcqlsgxList[i].zl.indexOf(zl) >-1)){
                                            searchDataList.push(wcqlsgxList[i]);
                                        }
                                    }
                                }
                            }
                            table.reload('xmid', {
                                data: searchDataList
                            });

                        });
                        var isSearch = true;
                        $('.bdc-table-box').on('focus', '.layui-laypage-skip .layui-input', function () {
                            isSearch = false;
                        }).on('blur', '.layui-laypage-skip .layui-input', function () {
                            isSearch = true;
                        });
                        //绑定回车键
                        $(document).keydown(function (event) {
                            if (event.keyCode == 13 &&isSearch) {
                                $("#search").click();
                            }
                        });

                        //监听复选框
                        table.on('checkbox(wcqlsgxTable)', function (obj) {
                            var checkedData = obj.type == 'one' ? [obj.data] : table.checkStatus("xmid").data;
                            var myCheckedData = JSON.parse(sessionStorage.sdglcheckedData);
                            if(myCheckedData.xmxx ==null){
                                myCheckedData.xmxx ={};
                            }
                            if(myCheckedData.dysdxx ==null){
                                myCheckedData.dysdxx ={};
                            }
                            $.each(checkedData, function (i, v) {
                                if (obj.checked) {
                                    if(v.lx=="sd"){
                                        //.增加已选中项
                                        myCheckedData.dysdxx[v.dysdid] = v;

                                    }else {
                                        //.增加已选中项
                                        myCheckedData.xmxx[v.xmid] = v;
                                    }
                                } else {
                                    if(v.lx =="sd"){
                                        //.删除
                                        myCheckedData.dysdxx = deleteCheckedById(myCheckedData.dysdxx, v.dysdid);

                                    }else {
                                        //.删除
                                        myCheckedData.xmxx = deleteCheckedById(myCheckedData.xmxx, v.xmid);
                                    }
                                }
                            });
                            sessionStorage.sdglcheckedData = JSON.stringify(myCheckedData);
                        });
                    }

                }
                , error: function (xhr, status, error) {
                    removeModal();
                    delAjaxErrorMsg(xhr);
                }
            });


        }

        //处理手动关联信息
        function dealSdglxx(){
            if(sessionStorage.getItem("sdglcheckedData") &&sessionStorage.getItem("sdglcheckedData") !="undefined") {
                var myCheckedData = JSON.parse(sessionStorage.sdglcheckedData);
                if ($.isEmptyObject(myCheckedData) || ($.isEmptyObject(myCheckedData.xmxx) && $.isEmptyObject(myCheckedData.dysdxx))) {
                    return;
                }
                var sdxmsdData={};
                sdxmsdData.xmxx=[];
                sdxmsdData.dysdxx=[];
                if (!$.isEmptyObject(myCheckedData.xmxx)) {
                    $.each(myCheckedData.xmxx, function (key, value) {
                        sdxmsdData.xmxx.push(value);
                    });
                }
                if (!$.isEmptyObject(myCheckedData.dysdxx)) {
                    var dysdxx = [];
                    $.each(myCheckedData.dysdxx, function (key, value) {
                        dysdxx.push(value);
                        sdxmsdData.dysdxx.push(value);
                    });
                }
                sessionStorage.setItem("dyhgz_sdglxx", JSON.stringify(sdxmsdData));
            }
        }
    });

    //字典项代码转名称
    function changeDmToMc(dm, zdList) {
        var mc = "";
        if (isNotBlank(zdList)) {
            for (var i = 0; i < zdList.length; i++) {
                var zd = zdList[i];
                if (dm == zd.DM) {
                    mc = zd.MC;
                    break;
                }
            }
        }
        return mc;

    }

    //删除选中数据
    function deleteCheckedById(checkedData, id) {
        var myCheckedData = {};
        $.each(checkedData, function (key, value) {
            if (key != id) {
                myCheckedData[key] = value;
            }
        });
        return myCheckedData;
    }



});