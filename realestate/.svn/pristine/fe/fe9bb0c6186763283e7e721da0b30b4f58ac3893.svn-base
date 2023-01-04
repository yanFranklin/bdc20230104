var currentUserID;
/**
 * 是否过滤规则验证
 * @type {boolean} 默认不过滤
 */
var glGzyz = false;

var confirmSize = 0;
var alertSize = 0;
// 登簿返回的验证信息
var yzxx;
// 工作流实例ID
var gzlslid;

var gzlslidArr;
// 办结的数据
var endData;

//获取页面版本
var version = getQueryString("version");

var reverseList = [];
reverseList.push('zl');
layui.use(['jquery', 'table', 'layer'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer;
    $(function () {
        // 获取到对应的用户信息
        queryCurrentUserId();
        /**
         * 监听台账查询 input 点击 显示 x 清空 input 中的内容
         */
        $('.layui-form-item .layui-input-inline').on('click', '.layui-icon-close', function () {
            $(this).siblings('.layui-input').val('');
        });
        $('.layui-form-item .layui-input-inline .layui-input').on('focus', function () {
            $(this).siblings('.layui-icon-close').removeClass('bdc-hide');
        }).on('blur', function () {
            var $this = $(this);
            setTimeout(function () {
                $this.siblings('.layui-icon-close').addClass('bdc-hide');
            }, 150)
        });
        $('.bdc-content').css('min-height', $('body').height() - 56);

        //监听回车事件
        var isSearch = true;
        //判断回车操作
        $('.bdc-table-box').on('focus', '.layui-laypage-skip .layui-input', function () {
            isSearch = false;
        }).on('blur', '.layui-laypage-skip .layui-input', function () {
            isSearch = true;
        });
        $(document).keydown(function (event) {
            if (event.keyCode === 13 &&isSearch) { //绑定回车
                $("#search").click();
            }
        });

        /**
         * 渲染补录信息表格
         * @type {string}
         */
        var url = "/realestate-register-ui/rest/v1.0/blxx/blsh";
        var tableId = '#blTable';
        var toolbar = '#toolbar';
        renderblTable(url, tableId, toolbar);

        /**
         * 查询点击事件
         */
        $('#search').on('click', function () {
            // 获取查询内容
            var obj = {};
            $(".search").each(function (i) {
                var value = $(this).val();
                var name = $(this).attr('name');
                // 此处去除查询条件中的空格
                obj[name] = deleteWhitespace(value);
            });
            // 查询时间长，添加遮罩
            addModel();
            // 重新请求
            table.reload("blTable", {
                where: obj,
                page: {
                    curr: 1  //重新从第 1 页开始
                },
                done: function(){
                    removeModel();
                    setTableHeight();
                    reverseTableCell(reverseList);
                }
            });
        });

        /**
         * 监听头工具栏事件
         */
        table.on('toolbar(blTable)', function (obj) {
            var layEvent = obj.event; //获得 lay-event 对应的值
            var checkData = table.checkStatus(obj.config.id).data;
            var selectedNum = checkData.length;
            // 蚌埠 可批量转发补录审核信息
            if(version === 'bengbu' && layEvent === "forward" && selectedNum > 1) {
                checkIsPlForward(checkData);
                return false;
            }
            if (selectedNum !== 1) {
                warnMsg('请选择一条数据进行操作！');
                return false;
            }
            if (layEvent === "forward") {
                // 判断能否转发
                chechIsForward(checkData[0]);
            } else if (layEvent === "back") {
                // 判断能否退回
                if (checkData[0].blzt === '3' || checkData[0].blzt === '1') {
                    warnMsg("当前状态不可以退回");
                    return false;
                } else if (checkData[0].shryid != null || checkData[0].sxh === "2") {
                    //县级退回，复审退初审，顺序号为2
                    addModel();
                    $.ajax({
                        url: getContextPath() + '/rest/v1.0/blxx/blsh/back?blshid=' + checkData[0].blshid,
                        type: 'GET',
                        success: function (data) {
                            removeModel();
                            if (data == true) {
                                successMsg("退回成功!");
                                $('#search').click();
                            } else {
                                warnMsg("当前用户不是审核人，不允许退回!");
                            }
                        }, error: function (xhr, status, error) {
                            removeModel();
                            delAjaxErrorMsg(xhr);
                        }
                    });
                } else {
                    warnMsg("节点无法退回！")
                }
            }
        });

        /**
         * 监听工具栏事件，工具栏进入到编辑页面可以对于数据进行操作
         */
        table.on('tool(blTable)', function (obj) {
            var layEvent = obj.event; //获得 lay-event 对应的值
            var data = obj.data; //获得当前行数据
            // 确认 gzlslid 是有值的
            if (isNullOrEmpty(obj.data.gzlslid)) {
                layer.msg("数据异常，未获取到工作流实例 id");
            } else if (layEvent === 'check') {
                window.open("./xxblUpdateinfo.html?processInsId=" + obj.data.gzlslid + "&type=check");
            }
        });
        /**
         * 判断能否退回
         * @param data 选中的数据
         */
        function chechIsForward(data) {
            if (data.blzt === '1') {
                warnMsg("此数据已经完成审核不允许再次转发。");
                return false;
            }
            if (data.blshlx === '1') {
                // 市级只有一个审核节点，如果审核人员名称为空则可以转发
                if (isNullOrEmpty(data.shryid)) {
                    forward(data);
                } else {
                    queryCurrentUserId();
                    // 当前用户就是审核人员，执行办结操作
                    if (currentUserID === data.shryid) {
                        end(data);
                    } else {
                        warnMsg("无法重复转发，请等待审核。");
                    }
                }
            } else {
                // 县级有 2 个审核节点
                if (data.sxh === '1') {
                    // 顺序号为 1
                    queryCurrentUserId();
                    // 当前用户就是审核人员，可以再次转发; 当前人员是受理人，未转发过
                    if (currentUserID === data.shryid || (currentUserID === data.slrid && data.shryid == null)) {
                        forward(data);
                    } else {
                        warnMsg("无法重复转发，请等待审核。");
                    }
                } else {
                    queryCurrentUserId();
                    // 当前用户就是受理人员，如果顺序号为2,且审核人员id是空的，可以转发
                    if(data.sxh === '2' && currentUserID === data.slrid && data.shryid == null) {
                        forward(data);
                    } else if(currentUserID === data.shryid) {
                        //当前人员是审核人员，县级直接办结
                        end(data);
                    } else {
                        warnMsg("无法重复转发，请等待审核。");
                    }
                }

            }
        }

    });

    /**
     * 验证是否能批量转发 蚌埠
     * @param data
     * @returns {boolean}
     */
    function checkIsPlForward(data){
        var forwardArray = [];
        var endArray = [];
        $.each(data, function (index,item){
            if (item.blzt === '1') {
                warnMsg("有数据已经完成审核不允许再次转发！");
                return false;
            }
            queryCurrentUserId();
            // 蚌埠市只会选市级转发 blshlx为1
            if (item.blshlx === '1') {
                // 市级只有一个审核节点，如果审核人员名称为空则可以转发
                if (isNullOrEmpty(item.shryid)) {
                    forwardArray.push(item);
                } else {
                    // 当前用户就是审核人员，执行办结操作
                    if (currentUserID === item.shryid) {
                        endArray.push(item);
                    } else {
                        warnMsg("有数据重复转发，请核查！");
                        return false;
                    }
                }
            }
        });
        if(forwardArray.length > 0 && endArray.length > 0){
            warnMsg("所选数据不在同一节点，请重新选择！");
            return false;
        }
        if(forwardArray.length > 0) {
            plForward(forwardArray);
        }
        if(endArray.length > 0) {
            plEnd(endArray);
        }
    }

    function plForward(forwardArrayData){
        var blshIds = [];
        $.each(forwardArrayData, function (index,item){
            blshIds.push(item.blshid);
        });
        sessionStorage.removeItem("blshIds");
        sessionStorage.setItem("blshIds",blshIds);
        layer.open({
            type: 2,
            skin: 'layui-layer-lan',
            title: '任务转发',
            area: ['960px', '490px'],
            content: '../xxbl/send.html?version=bengbu'
        });
    }

    /**
     * 转发数据
     * @param data
     */
    function forward(data) {
        layer.open({
            type: 2,
            skin: 'layui-layer-lan',
            title: '任务转发',
            area: ['960px', '490px'],
            content: '../xxbl/send.html?blshid=' + data.blshid
        });
    }

    /**
     * 获取当前用户的 ID (同步)
     */
    function queryCurrentUserId() {
        $.ajax({
            url: getContextPath() + '/rest/v1.0/user/info',
            type: 'GET',
            async: false,
            success: function (data) {
                currentUserID = data.id;
            }, error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr);
            }
        });
    }

    /**
     * 渲染补录数据
     * @param url 地址
     * @param tableId 表格 id
     * @param toolbar 表格工具栏
     */
    function renderblTable(url, tableId, toolbar) {
        addModel();
        table.render({
            elem: tableId,
            url: url,
            toolbar: toolbar,
            title: '补录审核表格',
            method: 'GET',
            even: true,
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            defaultToolbar: ['filter'],
            limits: commonLimits,
            cols: [[
                {type: 'checkbox', width: 50, fixed: 'left'},
                {field: 'blshid', title: '补录审核id', hide: true},
                {field: 'xmid', title: '项目ID', hide: true},
                {field: 'gzlslid', title: '工作流实例ID', hide: true},
                {field: 'bdcqzh', templet: "#bdcqzhTpl", minWidth: 280, title: '不动产权证号', event: 'check'},
                {field: 'qlr', minWidth: 100, title: '权利人'},
                {field: 'gzldymc', minWidth: 100, title: '流程名称'},
                {field: 'zl', minWidth: 200, title: '坐落'},
                {field: 'slrid', title: '受理人id', hide: true},
                {field: 'shryid', title: '审核人员id', hide: true},
                {field: 'bdcdyh', templet: '#bdcdyhTpl', minWidth: 270, title: '不动产单元号'},
                {field: 'blzt', minWidth: 90, templet: '#blztTemplate', title: '补录状态'},
                {field: 'blshlx', minWidth: 120, templet: '#blshlxTemplate', title: ' 补录审核类型'},
                {field: 'bllx', minWidth: 90, templet: '#bllxTemplate', title: '补录类型'},
                // {field: 'blsj', title: '补录时间', width: 200, sort: true},
                {field: 'qszt', title: '权属状态', templet: '#qsztTemplate', minWidth: 90, hide: true},
                {field: 'qszt', title: '限制状态', templet: '#xzqlZtTemplate', minWidth: 90}
                // {fixed: 'right', field: 'blshid', title: '审核状态', minWidth: 130, templet: '#blZtTemplate'}
            ]],
            parseData: function (res) { //res 即为原始返回的数据
                return {
                    code: res.code, //解析接口状态
                    msg: res.msg, //解析提示文本
                    count: res.totalElements, //解析数据长度
                    data: res.content //解析数据列表
                }
            },
            page: true,
            done: function () {
                removeModel();
                setTableHeight();
                reverseTableCell(reverseList);
            }
        });
    }
});

/**
 * 办结数据
 * @param checkData
 */
function end(checkData) {
    addModel("办结中");
    $.ajax({
        url: getContextPath() + '/rest/v1.0/blxx/blsh/end?blshid=' + checkData.blshid + "&gzlslid="+ checkData.gzlslid + "&glGzyz=" + glGzyz + "&bllx=" +checkData.bllx,
        type: 'GET',
        async: false,
        success: function (data) {
            endData = {};
            if (data && data.length > 0) {
                endData = checkData;
                // 封装提示信息
                showXzxx(data,checkData.gzlslid);
                removeModel();
            } else {
                successMsg("办结成功！");
                $('#search').click();
                removeModel();
            }
        }, error: function (xhr, status, error) {
            removeModel();
            delAjaxErrorMsg(xhr);
        }
    });
}

function plEnd(endArrayData){
    addModel("办结中");
    $.ajax({
        url: getContextPath() + '/rest/v1.0/blxx/blsh/plEnd?glGzyz=' + glGzyz,
        type: 'POST',
        contentType: "application/json",
        data: JSON.stringify(endArrayData),
        async: false,
        success: function (data) {
            endData = {};
            if (data && data.length > 0) {
                endData = endArrayData;
                gzlslidArr = [];
                $.each(endArrayData,function (index,item){
                    gzlslidArr.push(item.gzlslid);
                });
                // 封装提示信息
                showXzxx(data);
                removeModel();
            } else {
                successMsg("办结成功！");
                $('#search').click();
                removeModel();
            }
        }, error: function (xhr, status, error) {
            removeModel();
            delAjaxErrorMsg(xhr);
        }
    });
}

function showXzxx(yzResult,processInsId) {
    if (yzResult.length > 0) {
        // 加载提示信息
        loadDbTsxx(yzResult,processInsId);
        layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
            var $ = layui.jquery,
                layer = layui.layer;
            warnLayer = layer.open({
                type: 1,
                title: '提示信息',
                skin: 'bdc-tips-right',
                shade: [0],
                area: ['600px'],
                offset: 'r', //右下角弹出
                time: 5000000, //2秒后自动关闭
                anim: 2,
                content: $('#tsxx').html(),
                success: function () {
                    $('.layui-layer-ico .layui-layer-close .layui-layer-close1').on('click', function () {
                        layer.close(warnLayer);
                        generateDbTs();
                    })
                },
                cancel: function () {
                    layer.close(warnLayer);
                    generateDbTs();
                }
            });
        });
    }
}

function loadDbTsxx(data,processInsId) {
    yzxx = JSON.stringify(data);
    gzlslid = processInsId;
    $.each(data, function (index, item) {
        if (item.yzlx == 1) {
            confirmSize++;
            $("#confirmInfo").append('<p><img src="../../static/lib/bdcui/images/warn.png" alt="">' + item.tsxx + '<a href="javascrit:;" onclick="removeDb(this);return false">忽略</a></p>');
        } else {
            alertSize++;
            $("#alertInfo").append('<p><img src="../../static/lib/bdcui/images/error.png" alt="">' + item.tsxx + '</p>');
        }
    });
    //当只存在提示信息时展现全部忽略按钮，存在警告不展示忽略按钮
    if (alertSize > 0) {
        $("#ignoreAll").remove();
    }
}

function generateDbTs() {
    confirmSize = 0;
    alertSize = 0;
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var form = layui.form;
        var laytpl = layui.laytpl;
        var json = {};
        var tpl = tsxxTpl.innerHTML, view = document.getElementById("tsxx");
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
        form.render();
    })
}

/**
 * 提示信息忽略方法
 * @param elem 忽略对象
 */
function removeDb(elem) {
    $(elem).parent().remove();
    confirmSize--;
    //在没有警告提示下创建
    if (alertSize === 0 && confirmSize === 0) {
        glGzyz = true;
        layer.close(warnLayer);
        if(Array.isArray(endData)){
            // 批量
            // 保存登簿日志，保存验证信息
            var param = {};
            param.yzxx = yzxx;
            param.gzlslidArr = gzlslidArr;
            saveDbLog(param);
            // 重置模板信息
            generateDbTs();
            // 调用方法办结
            plEnd(endData);
        }else{
            // 保存登簿日志，保存验证信息
            var param = {};
            param.yzxx = yzxx;
            param.gzlslid = gzlslid;
            saveDbLog(param);

            // 重置模板信息
            generateDbTs();
            // 调用方法办结
            end(endData);
        }
    } else {
        removeModel();
    }
}

function removeAllDb() {
    glGzyz = true;
    layer.close(warnLayer);
    // 保存登簿日志，保存验证信息
    var param = {};
    if(Array.isArray(endData)){
        // 批量
        param.yzxx = yzxx;
        param.gzlslidArr = gzlslidArr;
        saveDbLog(param);
        plEnd(endData);
    }else{
        param.yzxx = yzxx;
        param.gzlslid = gzlslid;
        saveDbLog(param);
        end(endData);
    }




    // 调用方法办结

}

function saveDbLog(param) {
    $.ajax({
        url: '/realestate-register-ui/rest/v1.0/log/dbxx',
        dataType: "json",
        type: "POST",
        data: {'privateAttrMap': param},
        success: function (data) {
        }
    });
}