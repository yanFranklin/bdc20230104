/**
* @author  <a href="mailto:zhuyong@gtmap.cn>zhuyong</a>
* @version 1.0, 2019/03/25
* @description 新增子规则页面相关数据处理JS
*/
// 子规则全局变量
var bdcGzZgzDTO = {"bdcGzSjlDTOList":[], "bdcGzBdsTsxxDOList":[]};

layui.use(['jquery','table','laytpl','layer','form'],function () {
    var table = layui.table;
    var $ = layui.jquery;
    var layer = layui.layer;
    var form = layui.form;
    //监听下拉框
    form.on('select(yxjList)',function (data){
        var yxjlist=document.getElementById("yxjList");
        document.getElementById('yxj').value=document.getElementById('yxjList').value;
        document.getElementById('yxjShow').value=yxjlist.options[yxjlist.selectedIndex].text;
    })
    //监听input
    var jbxxform=document.forms['jbxxFrom'],
        yxj=jbxxform.elements['yxjShow'];
    yxj.onchange=function (){
        document.getElementById('yxj').value=document.getElementById('yxjShow').value;
    }
    /**
     * 切换到结果校验Tab页同步下数据
     */
    $(".jgjyTab").click(function () {
        //参数列表
        setCsArray();
        //数据流结果变量列表
        setJgblArray();
        // 规则表达式列表
        setGzbdsArray();
    })

    //参数列表
    function setCsArray() {
        var csArray = new Array();
        var order = 1;
        for (var i in bdcGzZgzDTO.bdcGzSjlDTOList) {
            var bdcGzSjlDTO = bdcGzZgzDTO.bdcGzSjlDTOList[i];
            for (var j in bdcGzSjlDTO.bdcGzSjlCsDOList) {
                var bdcGzSjlCsDO = bdcGzSjlDTO.bdcGzSjlCsDOList[j];
                var repeat = false;
                // 重复参数只显示一个
                for(var p in csArray){
                    if(csArray[p].sjlcszl == getParamType(bdcGzSjlCsDO.sjlcszl) && csArray[p].sjlcsmc == bdcGzSjlCsDO.sjlcsmc){
                        repeat = true;
                        break;
                    }
                }

                if(!repeat && ( 1 == bdcGzSjlCsDO.sjlcszl || 2 == bdcGzSjlCsDO.sjlcszl)){
                    csArray.push({
                        "xh": order++,
                        "sjlcszl": getParamType(bdcGzSjlCsDO.sjlcszl),
                        "sjlcsmc": bdcGzSjlCsDO.sjlcsmc
                    });
                }
            }
        }

        table.render({
            elem: '#cslb',
            title: '参数信息表',
            cols: [[
                {field: 'xh', title: '序号', width: 100, align: 'center'},
                {field: 'sjlcszl', title: '类型', align: 'center'},
                {field: 'sjlcsmc', title: '名称', align: 'center'},
            ]],
            data: csArray,
            page: false,
            done: function () {
                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
            }
        });
    }

    function getParamType(typeKey) {
        var cstype = typeKey;
        if (typeKey == 1) {
            cstype = "常量";
        } else if (typeKey == 2) {
            cstype = "入参";
        } else if (typeKey == 3) {
            cstype = "动参";
        } else if (typeKey == 4) {
            cstype = "实体";
        }
        return cstype;
    }

    // 数据流结果变量列表
    function setJgblArray(){
        var jgblArray = new Array();
        var order = 1;
        for(var i in bdcGzZgzDTO.bdcGzSjlDTOList){
            var bdcGzSjlDTO = bdcGzZgzDTO.bdcGzSjlDTOList[i];
            jgblArray.push({
                "xh": order++,
                "sjlmc":bdcGzSjlDTO.sjlmc,
                "jgblbs":bdcGzSjlDTO.jgblbs
            });
        }
        table.render({
            elem: '#jglb',
            title: '参数信息表',
            cols: [[
                {field:'xh',   title:'序号',  width:100 , align:'center'},
                {field:'sjlmc', title:'数据流名称', align:'center'},
                {field:'jgblbs', title:'变量名称', align:'center'},
            ]],
            data: jgblArray,
            page: false,
            done: function () {
                $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');
            }
        });
    }

    // 规则表达式列表
    function setGzbdsArray(){
        var gzbdsArray = new Array();
        var order = 1;
        for(var i in bdcGzZgzDTO.bdcGzBdsTsxxDOList){
            var bdsTsxxDO = bdcGzZgzDTO.bdcGzBdsTsxxDOList[i];
            gzbdsArray.push({
                "xh": order++,
                "gzbds":bdsTsxxDO.gzbds,
                "tsxx":bdsTsxxDO.tsxx
            });
        }

        table.render({
            elem: '#gzbds',
            toolbar: '#gzbdsBtn',
            defaultToolbar: [],
            title: '规则表达式',
            cols: [[
                {type: 'checkbox', fixed: 'left'},
                {field:'xh',   title:'序号', width:60, align:'center'},
                {field:'gzbds', title:'规则表达式', align:'center',edit: 'text'},
                {field:'tsxx', title:'提示信息', align:'center',edit: 'text'},
            ]],
            data: gzbdsArray,
            page: false,
            done: function () {
                $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');
            }
        });
    }

    //规则验证表头工具栏事件
    table.on('toolbar(gzbds)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id);
        switch(obj.event){
            case 'add':
                addGzbds();
                break;
            case 'del':
                deleteGzbds(checkStatus.data);
                break;
        };
    });

    // 新增规则表达式
    function addGzbds(){
        var bdcGzBdsTsxxDOList = bdcGzZgzDTO.bdcGzBdsTsxxDOList;
        if(table.cache.gzbds && table.cache.gzbds.length > 0){
            bdcGzBdsTsxxDOList.push({'xh': bdcGzBdsTsxxDOList[bdcGzBdsTsxxDOList.length - 1].xh + 1, 'gzbds': '', 'tsxx': ''});
        } else {
            bdcGzBdsTsxxDOList.push({'xh': 1, 'gzbds': '', 'tsxx': ''});
        }

        table.reload("gzbds", {data: bdcGzBdsTsxxDOList});
        bdcGzZgzDTO["bdcGzBdsTsxxDOList"] = bdcGzBdsTsxxDOList;
    }

    // 删除规则表达式
    function deleteGzbds(data){
        if(!data || data.length == 0){
            layer.alert("<div style='text-align: center'>请选择需要删除的记录！</div>", {title: '提示'});
            return;
        }

        var newTableData = [];
        var tableData = table.cache.gzbds;
        for(var index in tableData){
            var isExist = false;
            for(var i in data){
                if(tableData[index].xh == data[i].xh){
                    isExist = true;
                    break;
                }
            }

            if(false == isExist){
                newTableData.push(tableData[index]);
            }
        }

        // 重新索引序号
        var order = 1;
        for(var index in newTableData){
            newTableData[index].xh = order++;
        }

        table.reload("gzbds", {data: newTableData});
        bdcGzZgzDTO["bdcGzBdsTsxxDOList"] = newTableData;
    }

    //编辑完成更新数据
    table.on('edit(gzbds)', function(obj){
        bdcGzZgzDTO["bdcGzBdsTsxxDOList"] = table.cache.gzbds;
    });

    //子规则保存事件
    $("#submit-btn").click(function () {
        var gzmc = $("#gzmc").val();
        if(isNullOrEmpty(gzmc)){
            layer.alert("<div style='text-align: center'>请输入子规则名称！</div>", {title: '提示'});
            return;
        }
        bdcGzZgzDTO["gzmc"] = gzmc;
        var yxj = $("#yxj").val();
        if(isNullOrEmpty(yxj) || !/^[0-9]*$/.test(yxj)){
            layer.alert("<div style='text-align: center'>子规则优先级请输入数字！</div>", {title: '提示'});
            return;
        }

        // 验证规则表达式操作符配置是否正确
        if(!checkGzBds()) {
            return;
        }

        bdcGzZgzDTO["gzid"] = $("#gzid").val();
        bdcGzZgzDTO["yxj"] = yxj;
        bdcGzZgzDTO["pzr"] = $("#pzr").val();
        bdcGzZgzDTO["pzrq"] = $("#pzrq").val();
        bdcGzZgzDTO["ytsm"] = $("#ytsm").val();
        bdcGzZgzDTO["jydm"] = $("#jydm").val();
        bdcGzZgzDTO["pcqh"] = $("#pcqh").val();
        //数据中SQL加密
        bdcGzZgzDTO.bdcGzSjlDTOList.forEach(function (value){
            if(!isNullOrEmpty(value.sjnr)){
                value.sjnr = Base64.encode(value.sjnr);
            }
        })
        bdcGzZgzDTO["bdcGzSjlDTOList"] = bdcGzZgzDTO.bdcGzSjlDTOList;

        if("4" === yxj) {
            var lwtsxx = layer.open({
                title: '提示',
                type: 1,
                area: ['650px', '450px'],
                btn: ['继续保存', '取消'],
                content: '<div id="bdc-popup-small">'+
                    '       <form class="layui-form" action="">'+
                    '           <div class="layui-form-item pf-form-item">'+
                    '               <div class="layui-inline">'+
                    '                   <label class="layui-form-label" style="width: 630px; text-align: left;">当前子规则优先级设置了“可例外”，特做以下提示：</label>'+
                    '                   <label class="layui-form-label" style="width: 630px; text-align: left;">1、例外子规则一般用于特定业务流程即使存在限制信息也能办件；</label>'+
                    '                   <label class="layui-form-label" style="width: 630px; text-align: left;">2、如果“新建项目”组合规则关联此例外规则，那么“流程转发”也需要关联；</label>'+
                    '                   <label class="layui-form-label" style="width: 630px; text-align: left;">3、数据流SQL查询结果需要返回字段bdcdyh、xmid，即限制权利对应的单元号、项目ID；</label>'+
                    '                   <label class="layui-form-label" style="width: 630px; text-align: left;">4、如果非权利数据，xmid可以返回对应主键id，因为例外本质上是排除对应限制数据；</label>'+
                    '                   <label class="layui-form-label" style="width: 630px; text-align: left;">5、业务系统验证到例外规则，点击例外，例外信息会保存到BDC_GZLW_SH表；</label>'+
                    '                   <label class="layui-form-label" style="width: 630px; text-align: left;">6、数据流SQL需要有排除已审核通过记录的内容，例如：</label>'+
                    '                   <label class="layui-form-label" style="width: 630px; text-align: left;">&nbsp;&nbsp;&nbsp;&nbsp;SELECT xmid,qlid,bdcdyh FROM bdc_cf WHERE qszt IN (0,1) AND bdcdyh = #{1}</label>'+
                    '                   <label class="layui-form-label" style="width: 630px; text-align: left; font-weight: bold">&nbsp;&nbsp;&nbsp;&nbsp;AND  xmid  NOT  IN  ( SELECT  xmid  FROM  bdc_gzlw_sh  WHERE</label>'+
                    '                   <label class="layui-form-label" style="width: 630px; text-align: left; font-weight: bold">&nbsp;&nbsp;&nbsp;&nbsp;shzt = 1 AND bdcdyh = #{2} AND gzid = &{3} AND slbh = #{4} )</label>'+
                    '               </div>'+
                    '           </div>'+
                    '       </form>'+
                    '     </div>'
                ,yes: function(index, layero){
                    layer.close(lwtsxx);
                    save(bdcGzZgzDTO);
                }
            });
        } else {
            save(bdcGzZgzDTO);
        }
    });

    function save(bdcGzZgzDTO) {
        $.ajax({
            url: inquiryUiUrl+ '/zgz/saveBdcGzZgz',
            type: "POST",
            data: JSON.stringify(bdcGzZgzDTO),
            contentType: 'application/json',
            dataType: "text",
            success: function (data) {
                if (!isNullOrEmpty(data)) {
                    layer.msg('<img src="../../../static/lib/bdcui/images/success-small.png" alt="">保存成功，请执行验证！');

                    $("#gzid").val(data);
                    saveAddLog();
                } else {
                    layer.msg('<img src="../../../static/lib/bdcui/images/error-small.png" alt="">保存失败，请检查内容');
                }
                //数据中SQL解密
                bdcGzZgzDTO.bdcGzSjlDTOList.forEach(function (value){
                    if(!isNullOrEmpty(value.sjnr)){
                        value.sjnr = Base64.decode(value.sjnr);
                    }
                })
                bdcGzZgzDTO["bdcGzSjlDTOList"] = bdcGzZgzDTO.bdcGzSjlDTOList;
            },
            error: function (data) {
                layer.msg('<img src="../../../static/lib/bdcui/images/error-small.png" alt="">保存失败，请检查内容');
            }
        })
    }

    // 保存新增子规则日志
    function saveAddLog(){
        $.ajax({
            url: BASE_URL + "/zgz/queryBdcGzZgzDTOByGzid",
            type: 'GET',
            contentType: 'application/json',
            data: {"gzid": $("#gzid").val()},
            dataType: "json",
            success: function (data) {
                if (data) {
                    saveDetailLog("ZGZXZ", "子规则配置-新增", {"GZNR": JSON.stringify(data)});
                }
            }
        });
    }

    //子规则校验事件
    $("#check-btn").click(function () {
        // 先验证配置是否全面
        checkConfig(false);

        // 验证规则表达式操作符配置是否正确
        if(!checkGzBds()) {
            return;
        }

        var zgz = bdcGzZgzDTO;
        zgz["gzmc"] = $("#gzmc").val();
        zgz["gzid"] = $("#gzid").val();
        zgz["yxj"] = $("#yxj").val();
        zgz["pzr"] = $("#pzr").val();
        zgz["pzrq"] = $("#pzrq").val();
        zgz["ytsm"] = $("#ytsm").val();
        zgz["jydm"] = $("#jydm").val();

        // 缓存规则数据
        layui.sessionData('zgzData', {
            key: 'data'
            ,value: JSON.stringify(zgz)
        });

        // 再校验规则是否能正确运行
        layer.open({
            type: 2,
            title: '子规则验证',
            anim: -1,
            shadeClose: true,
            maxmin: true,
            shade: false,
            area: ['960px', '520px'],
            offset: 'auto',
            content: ["checkZgz.html", 'yes'],
            end: function () {
            }
        });
    });

    // 配置日期
    $("#pzrq").val(format(new Date()));

    // 校验代码内容初始化
    (function () {
        $.ajax({
            url: inquiryUiUrl+ '/zgz/code/init',
            type: "GET",
            dataType: "text",
            success: function (data) {
                $("#jydm").val(data);
            },
            error: function (data) {
                warnMsg("动态代码初始化失败，请重试！");
            }
        });
    })();

    // 代码格式化
    $("#code-format").click(function () {
        var code = $("#jydm").val();
        if(isNullOrEmpty(code)){
            warnMsg("未输入代码内容！");
            return;
        }

        $.ajax({
            url: inquiryUiUrl+ '/zgz/code/format',
            data: JSON.stringify({"jydm": code}),
            type: "POST",
            contentType: 'application/json',
            dataType: "text",
            success: function (data) {
                $("#jydm").val(data);
                successMsg("格式化成功！");
            },
            error: function (e) {
                var text = JSON.parse(e.responseText);
                layer.alert("动态代码格式化失败，原因<br>" + text.detail);
            }
        });
    });
});