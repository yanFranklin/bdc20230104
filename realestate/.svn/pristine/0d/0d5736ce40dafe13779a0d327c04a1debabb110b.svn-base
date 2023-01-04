/**
 * author: <a href="mailto:zhuyong@gtmap.cn>zhuyong</a>
 * version 1.0.0  2020/08/12
 * describe: 海门多个物合并一个不动产单元操作台账页面处理JS
 */
var HBBDCDY_GZYZ_ZHGZ = 'HBBDCDY_GZYZ_ZHGZ';
var reverseList = ['zl'];

layui.use(['table','laytpl','laydate','layer', 'form'],function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer,
        form = layui.form;

    // 当前页数据
    var currentPageData = new Array();

    // 获取权利拆分标识参数
    var qlcfbs = $.getUrlParam("qlcfbs");
    $("#qlcfbs").val(qlcfbs);

    // 获取字典信息
    getZdList();

    /**
     * 加载Table数据列表
     */
    table.render({
        elem: '#bdcdyTable',
        toolbar: '#toolbar',
        title: '多个物合并一个不动产单元数据查询展示台账',
        defaultToolbar: ['filter'],
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        cellMinWidth: 80,
        even: true,
        cols: [[
            {type: 'checkbox', fixed: 'left'},
            {minWidth:120, sort:false, field:'slbh',  title:'受理编号' },
            {minWidth:250, sort:false, field:'bdcqzh',title:'不动产权证号'},
            {minWidth:120, sort:false, field:'qlr',   title:'权利人'},
            {minWidth:200, sort:false, field:'qlrzjh',title:'权利人证件号'},
            {minWidth:250, sort:false, field:'zl',    title:'坐落'},
            {minWidth:100, sort:false, field:'zh',    title:'幢号'},
            {minWidth:100, sort:false, field:'zcs',   title:'总层数'},
            {minWidth:100, sort:false, field:'szc',   title:'所在层'},
            {minWidth:100, sort:false, field:'fjh',   title:'房间号'},
            {minWidth:120, sort:false, field:'ghyt',  title:'规划用途',
                templet: function (d) {
                    if (!isNullOrEmpty(d.ghyt) && !isEmptyObject(zdList) && !isEmptyObject(zdList.fwyt)) {
                        $.each(zdList.fwyt, function (index, item) {
                            if (d.ghyt == item.DM) {
                                return item.MC;
                            }
                        });
                    }
                    return "";
                }
            },
            {minWidth: 180,sort:false, field: 'djsj',title: '登记时间',
                templet: function (d) {
                    return format(d.djsj);
                }
            },
            {width: 280, sort:false, field: 'bdcdyh',title: '不动产单元号',
                templet: function (d) {
                    return formatBdcdyh(d.bdcdyh);
                }
            },
            {width: 280, sort:false, field: 'qlcfbs',title: '权利拆分标识', hide: true},
            {field: 'sfyhb', width: 120, title: '合并状态', fixed: 'right', sort: false,
                templet: function (d) {
                    if(d && d.qlcfbs && d.qlcfbs == "BDCDYHBJL") {
                        return '<span class="bdc-yy" style="color:#ef7106;">新记录</span>';
                    } else {
                        if(d && d.sfyhb && (d.sfyhb == true || d.sfyhb == "true")) {
                            return '<span class="bdc_change_red bdc-cf" style="color:#EE0000;">已合并</span>';
                        } else {
                            return '<span class="" style="color:#32b032;">未合并</span>';
                        }
                    }
                }
            },
            {field: 'qszt', width: 100, title: '权属状态', fixed: 'right', sort: false,
                templet: function (d) {
                    return formatQsztWithCx(d.qszt, 0);
                }
            },
            {field: 'bdcdyZtDTO', width: 120, title: '限制状态', fixed: 'right', sort: false,
                templet: function (d) {
                    return formatXzzt(d.bdcdyZtDTO);
                }
            },
        ]],
        data: [],
        limit: 50,
        limits: [50, 100, 150, 200, 250],
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
        }
    });

    // 监听复选框事件，缓存选中的行数据
    table.on('checkbox(bdcdyTable)', function (obj) {
        // 获取选中或者取消的数据
        var data = obj.type == 'one' ? [obj.data] : currentPageData;

        $.each(data, function (i, v) {
            if (obj.checked) {
                //.增加已选中项
                layui.sessionData('checkedBdcdy', {
                    key: v.xmid, value: v
                });
            } else {
                //.删除
                layui.sessionData('checkedBdcdy', {
                    key: v.xmid, remove: true
                });
            }
        });
    });


    //头工具栏事件
    table.on('toolbar(bdcdyTable)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id);
        switch(obj.event){
            case 'hbBdcdy':
                hbBdcdy();
                break;
        };
    });

    /**
     * 进行合并不动产单元操作
     */
    function hbBdcdy() {
        var checkedBdcdy = layui.sessionData('checkedBdcdy');
        if ($.isEmptyObject(checkedBdcdy)) {
            warnMsg(" 请选择需要合并的数据！");
            return;
        }

        if(Object.keys(checkedBdcdy).length <= 1) {
            warnMsg(" 请选择需要合并的多条数据！");
            return;
        }

        // 检查是否存在已合并记录
        var sfyhb = false;
        $.each(checkedBdcdy, function (key, value) {
            if(value.sfyhb == true || value.sfyhb == "true") {
                sfyhb = true;
            }
        });
        if(sfyhb) {
            warnMsg("存在已经合并过的记录，请重选！");
            return;
        }

        // 检查是否存在新生成的记录
        var sfxjl = false;
        $.each(checkedBdcdy, function (key, value) {
            if(value.qlcfbs == "BDCDYHBJL") {
                sfxjl = true;
            }
        });
        if(sfxjl) {
            warnMsg("存在新合并生成的记录，请重选！");
            return;
        }

        // 检查是否存在非现势记录
        var sfls = false;
        $.each(checkedBdcdy, function (key, value) {
            if(value.qszt != 1) {
                sfls = true;
            }
        });
        if(sfls) {
            warnMsg("存在非现势产权记录，请重选！");
            return;
        }

        var data = new Array();
        $.each(checkedBdcdy, function (key, value) {
            data.push(value);
        });

        // 规则验证
        check(data, HBBDCDY_GZYZ_ZHGZ).fail(function(){
            return;
        }).done(function(){
            bdcdyhPp(data);
        });
    }

    /**
     * 合并之前进行规则验证
     * @param data 验证数据
     * @param zhbs 规则标识
     */
    function check(data, zhbs){
        if(isNullOrEmpty(zhbs)){
            warnMsg("未配置验证规则，请联系管理员！");
        }

        var deferred = $.Deferred();
        if ($.isEmptyObject(data)) {
            return deferred.resolve();
        }
        
        var checkParams = new Array();
        $.each(data,function (index,value) {
            checkParams.push({
                xmid : value.xmid,
                bdcdyh : value.bdcdyh,
                qlcfbs : value.qlcfbs
            });
        });

        addModel();
        $.ajax({
            url: "/realestate-register-ui/rest/v1.0/bdcdy/hb/bdcdy/gzyz",
            type: 'POST',
            dataType: 'json',
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify({
                bdcGzYzsjDTOList : checkParams,
                zhbs: zhbs
            }),
            success: function (data) {
                removeModel();
                if(data.length == 0){
                    return deferred.resolve();
                }
                var content = new Array();
                $.each(data,function(i,value){
                    var xmid = getXmidOfGzyzxx(value);
                    var action = "";
                    if(!isNullOrEmpty(xmid)){
                        action = "查看";
                    }

                    content.push("<img src=\"../../static/lib/bdcui/images/warn.png\" style='width: 16px;' alt=\"\">");
                    content.push(value.tsxx);
                    content.push('&nbsp;&nbsp;<a class="viewbdcxm" href="javascrit:;" onclick="openXmxx(\'' + xmid + '\');return false">' + action + '</a>');
                    content.push("<br/>");
                });

                layer.confirm(content.join(""),{
                    btn: ["继续合并","取消合并"],
                    area: '530px'
                },function(index, layero){
                    saveDetailLog("HBBDCDYHLGZ", "合并不动产单元-忽略规则验证", {"data": data});
                    deferred.resolve();
                    layer.close(index);
                },function(index){
                    deferred.reject();
                    layer.close(index);
                })
            },
            error: function (xhr, status, error) {
                removeModel();
                failMsg("系统验证发生异常，请重试或联系管理员！");
            }
        });
        return deferred;
    }

    // 从规则验证结果获取XMID信息
    function getXmidOfGzyzxx(data) {
        var xmid = "";
        if(data && data.xzxx){
            var xzxx = data.xzxx;
            $.each(xzxx, function (key, value) {
                if(key != "RULERESULT"){
                    // 包含XMID、BDCDYH这些信息的验证结果，不同规则验证设置的数据量变量不一样，所以动态获取
                    if(value && value[0] && value[0].XMID){
                        xmid = value[0].XMID;
                    }
                }
            })
        }
        return xmid;
    }

    /**
     * 进行不动产单元号匹配
     * @param data
     */
    function bdcdyhPp(data) {
        var win = layer.open({
            type: 2,
            title: '匹配不动产单元',
            anim: -1,
            shadeClose: true,
            maxmin: true,
            shade: false,
            area: ['80%', '80%'],
            offset: 'auto',
            content: [ "ppbdcdyh.html", 'yes'],
            btn: ['确定', '取消'],
            yes: function (layero, index) {
                layer.close(win);

                var localData = layui.data('ppbdcdyh');
                var ppBdcdyh = localData.data;

                if(isNullOrEmpty(ppBdcdyh)) {
                    warnMsg("未匹配不动产单元号，请先匹配");
                } else {
                    newBdcdy(data, ppBdcdyh);
                }
            }
        });
    }

    /**
     * 合并生成新的不动产单元记录
     * @param data 需要合并的记录
     * @param ppBdcdyh 匹配到的新的记录不动产单元号
     */
    function newBdcdy(data, ppBdcdyh) {
        addModel();
        $.ajax({
            url: "/realestate-register-ui/rest/v1.0/bdcdy/hb/bdcdy/new/" + ppBdcdyh,
            type: 'POST',
            dataType: 'json',
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(data),
            success: function (data) {
                removeModel();

                if(data && !isNullOrEmpty(data.xmid)) {
                    layer.alert("合并操作成功，新记录项目ID：" + data.xmid + "，受理编号：" + data.slbh);
                    search();
                } else {
                    failMsg("未生成新的合并记录，请重试");
                }
            },
            error: function (xhr, status, error) {
                removeModel();
                failMsg("合并发生异常，请重试或联系管理员！");
            }
        });
    }

    // 执行默认查询
    search();

    function search() {
        // 每次查询清除下选中缓存数据
        layui.sessionData("checkedBdcdy", null);

        // 获取查询内容
        var obj = {};
        $(".search").each(function(i){
            var value= $(this).val();
            var name= $(this).attr('name');
            obj[name]=value;
        });

        if(isNullOrEmpty($("#qlcfbs").val())){
            warnMsg("未获取到权利拆分标识数据，无法操作！");
            return false;
        }

        addModel();
        // 重新请求
        table.reload("bdcdyTable", {
            where: obj
            ,url: "/realestate-register-ui/rest/v1.0/bdcdy/hb/bdcdy"
            , page: {
                curr: 1  //重新从第 1 页开始
            }
            , done: function (res, curr, count) {
                removeModel();
                currentPageData = res.data;
                setHeight();
                reverseTableCell(reverseList);
            }
        });
    }

    /**
     * 点击查询
     */
    $('#search').on('click',function () {
        search();
    });

    /**
     * 回车查询
     */
    $('.search').on('keydown', function (event) {
        if (event.keyCode == 13) {
            search();
        }
    });

    /**
     * 重置
     */
    $('#reset').on('click',function () {
    });
});

//查看项目信息
function openXmxx(xmid) {
    $.ajax({
        url: "/realestate-inquiry-ui/history/queryBdcXmDoByXmid",
        type: 'GET',
        async: true,
        contentType: 'application/json;charset=utf-8',
        data: {xmid: xmid},
        success: function (data) {
            if(isNotBlank(data) && isNotBlank(data[0])){
                window.open('/realestate-portal-ui/view/new-page.html?type=lsgx&processinsid=' + data[0].gzlslid);
            }else{
                warnMsg(" 请求失败，未获取到项目信息！");
            }
        },
        error: function (err) {
            removeModel();
            layer.closeAll();
            warnMsg(" 请求失败，请检查数据连接！");
        }
    });
}