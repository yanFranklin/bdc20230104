layui.use(['layer', 'laydate', 'table', 'jquery', 'form',], function () {
    var layer = layui.layer;
    var $ = layui.jquery;
    var table = layui.table;
    var form = layui.form;
    var laydate = layui.laydate;
    formSelects = layui.formSelects;
    laydate.render({
        elem: '#startTime'
        , type: 'datetime'
    });
    laydate.render({
        elem: '#endTs'
        , type: 'datetime'
    });
    formSelects.config('selectServiceName', {
        keyName: 'name',            //自定义返回数据中name的key, 默认 name
        keyVal: 'value'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('selectSpanName', {
        keyName: 'name',            //自定义返回数据中name的key, 默认 name
        keyVal: 'value'            //自定义返回数据中value的key, 默认 value
    }, true);

    /**
     * 加载Table数据列表
     */
        // 获取zipkin的地址
    var zipKinUrl;
    $.ajax({
        url: '/realestate-inquiry-ui/getAppLogIp',
        type: 'get',
        async: false,
        success: function (data) {
            zipKinUrl = data;
        },
        error: function (e) {
            showErrorInfo();
        }
    });

    // 获取service
    var serviceDate;
    $.ajax({
        url: 'http://' + zipKinUrl + '/api/v1/services',
        type: 'get',
        async: false,
        success: function (data) {
            var temp = new Array();
            var i = 0;
            data.forEach(val => {
                temp.push({name: val, value: i})
                i++;
            })
            formSelects.data('selectServiceName', 'local', {arr: temp})
        },
        error: function (e) {
            showErrorInfo();
        }
    });
    // 获取span
    var spanData;
    $.ajax({
        url: 'http://' + zipKinUrl + '/api/v1/spans?serviceName=exchange-app',
        type: 'get',
        async: false,
        success: function (data) {
            var temp = new Array();
            var i = 0;
            data.forEach(val => {
                temp.push({name: val, value: i})
                i++;
            })
            formSelects.data('selectSpanName', 'local', {arr: temp})
        },
        error: function (e) {
            showErrorInfo();
        }
    });
    // 动态更换span
    layui.formSelects.on('selectServiceName', function (id, vals, val, isAdd, isDisabled) {
        //id:           点击select的id
        //vals:         当前select已选中的值
        //val:          当前select点击的值
        //isAdd:        当前操作选中or取消
        //isDisabled:   当前选项是否是disabled
        // alert("选择了: " + val.name);
        //如果return false, 那么将取消本次操作
        $.ajax({
            url: 'http://' + zipKinUrl + '/api/v1/spans',
            type: 'get',
            async: false,
            data: {serviceName: val.name},
            success: function (data) {
                var temp = new Array();
                var i = 0;
                data.forEach(val => {
                    temp.push({name: val, value: i})
                    i++;
                })
                formSelects.data('selectSpanName', 'local', {arr: temp})
            },
            error: function (e) {
                showErrorInfo();
            }
        });

    });
    // 默认查询
    addModel();
    var zipData;
    $.ajax({
        url: 'http://' + zipKinUrl + '/api/v1/traces',
        type: 'get',
        async: false,
        data: {
            sortOrder: 'duration-desc',
            annotationQuery: '',
            endTs: Date.parse(new Date()),
            limit: 10,
            lookback: 604800000,
            serviceName: 'accept-app',
            spanName: 'all'
        },
        success: function (data) {
            zipData = data;
        },

    })
    var zipKin = new Array();
    if (zipData != null) {
        for (let i = 0; i < zipData.length; ++i) {
            zipData[i].forEach(function (v) {
                zipKin.push({
                    traceId: v.traceId,
                    name: v.name,
                    // timestamp: new Date(v.timestamp / 1000).toLocaleString(),
                    timestamp: formatDate(v.timestamp / 1000),
                    duration: v.duration / 1000000,
                    binaryAnnotations: JSON.stringify(v.binaryAnnotations),
                    annotations: JSON.stringify(v.annotations)
                });
            });

        }
    }
    removeModal();
    table.render({
        elem: '#pageTable',
        toolbar: "#toolbarDemo",
        defaultToolbar: ['filter'],
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        cellMinWidth: 80,
        where: {},
        even: true,
        cols: [[
            {type: 'radio'},
            // {type: 'checkbox', fixed: 'left', width: '5%'},
            // {
            //     fixed: 'id', field: '', title: '序号', width: '5%', templet: function (d) {
            //         return (d.LAY_INDEX);
            //     }
            // },
            {field: 'traceId', title: '链路Id', width: '20%'},
            {field: 'name', title: '路径', width: '15%'},
            {field: 'timestamp', title: '时间戳', width: '15%'},
            {field: 'duration', title: '耗时(s)', width: '10%'},
            {field: 'binaryAnnotations', title: '方法调用链', width: '20%'},
            {field: 'annotations', title: '应用链路调用详情', width: '20%'}
        ]],
        data: zipKin,
        autoSort: false,
        page: true,
        limits: [10, 50, 100],
        limit: 10, //每页默认显示的数量
        parseData: function (res) {
        },
        done: function (res) {
            // 已移除记录背景标红
            if (res) {
                res.data.forEach(function (v, index) {
                    var binary = JSON.parse(v.binaryAnnotations);
                    if (binary[0].key == 'error') {
                        $(".layui-table-body tr[data-index='" + index + "']").css("background", "#E9967A");
                    }
                });
            }
            setHeight();
        }
    });

    //监听行单击事件（双击事件为：rowDouble）
    table.on('rowDouble(pageTable)', function (obj) {
        var data = obj.data;

        window.open(document.location.protocol + "//" + zipKinUrl + "/zipkin/traces/" + data.traceId);
        //标注选中样式
        // obj.tr.addClass('layui-table-click').siblings().removeClass('layui-table-click');
    });
    //监听单元格事件

    $('#search').on('click', function () {
        // 获取zipkin数据
        addModel();
        var annotationQuery = $("#annotationQuery").val();
        var endTs = Date.parse($("#endTs").val());
        if (isNullOrEmpty(endTs)) {
            endTs = Date.parse(new Date());
        }
        var startTs = Date.parse($("#startTime").val());
        // var qllxArray = formSelects.value('selectQllx');
        // if(djxlArray.length!=0){
        //     djxl = djxlArray[0].DM;
        // }
        var serviceArr = formSelects.value('selectServiceName');
        var spanArr = formSelects.value('selectSpanName');
        var serviceName;
        var spanName = 'all';
        if (serviceArr.length != 0) {
            serviceName = serviceArr[0].name;
        }
        if (spanArr.length != 0) {
            spanName = spanArr[0].name;
        }
        var limitNum = 10;
        if (!isNullOrEmpty($("#limitNum").val())) {
            limitNum = $("#limitNum").val();
        }
        var lookback = 604800000;
        if (!isNullOrEmpty(endTs) && !isNullOrEmpty(startTs)) {
            lookback = endTs - startTs;
            if (startTs > endTs) {
                removeModal();
                warnMsg("结束时间不能大于开始时间!");
                return false;
            }
        }
        if (isNullOrEmpty(serviceName)) {
            removeModal();
            warnMsg("请输入需要查询的数据！");
            return false;
        }
        var queryZip = {
            sortOrder: 'duration-desc',
            annotationQuery: annotationQuery,
            endTs: endTs,
            limit: limitNum,
            lookback: lookback,
            serviceName: serviceName,
            spanName: spanName
        };
        // data: {
        //     sortOrder: 'duration-desc',
        //         annotationQuery: '',
        //         endTs: Date.parse(new Date()),
        //         limit: 10,
        //         lookback: 604800000,
        //         serviceName: 'accept-app',
        //         spanName: 'all'
        // },
        tableReload(queryZip);
        return false;
    });
    //头工具栏事件
    table.on('toolbar(pageTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        var data = checkStatus.data[0];
        if (isNullOrEmpty(data)) {
            warnMsg("请选择数据!");
            return false;
        }
        switch (obj.event) {
            case 'download':
                downLoad(data.traceId);
                break;
            case 'view':
                window.open(document.location.protocol + "//" + zipKinUrl + "/zipkin/traces/" + data.traceId);
                break;
        }
        ;
    });

    function downLoad(traceId) {
        var zipData;
        // http://192.168.2.99:8003/api/v1/trace/df3c0ccfa8c5c5c6
        $.ajax({
            url: 'http://' + zipKinUrl + '/api/v1/trace/' + traceId,
            type: 'get',
            async: false,
            success: function (result) {
                zipData = result;
            },
        })
        // $.ajax({
        //     url: '/realestate-inquiry-ui/download',
        //     type: 'post',
        //     async: false,
        //     data: {zipData: JSON.stringify(zipData)},
        //     success: function (data) {
        //         var temp = new Array();
        //         var i = 0;
        //         data.forEach(val => {
        //             temp.push({name: val, value: i})
        //             i++;
        //         })
        //     },
        //     error: function (e) {
        //         showErrorInfo();
        //     }
        // });
        // 设置Excel基本信息
        $("#data").val(JSON.stringify(zipData));
        $("#form").submit();
    }

    function tableReload(queryCond) {
        var zipData;
        $.ajax({
            url: 'http://' + zipKinUrl + '/api/v1/traces',
            type: 'get',
            async: false,
            data: queryCond,
            success: function (data) {
                zipData = data;
            },

        })
        var zipKin = new Array();
        if (zipData != null) {
            for (let i = 0; i < zipData.length; ++i) {
                zipData[i].forEach(function (v) {
                    zipKin.push({
                        traceId: v.traceId,
                        name: v.name,
                        timestamp: formatDate(v.timestamp / 1000),
                        duration: v.duration / 1000000,
                        binaryAnnotations: JSON.stringify(v.binaryAnnotations),
                        annotations: JSON.stringify(v.annotations)
                    });
                });

            }
        }
        removeModal();
        table.render({
            elem: '#pageTable',
            toolbar: "#toolbarDemo",
            defaultToolbar: ['filter'],
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            cellMinWidth: 80,
            where: {},
            even: true,
            cols: [[
                {type: 'radio'},
                {field: 'traceId', title: 'traceId', width: '20%'},
                {field: 'name', title: '路径', width: '15%'},
                {field: 'timestamp', title: '时间戳', width: '15%'},
                {field: 'duration', title: '耗时(s)', width: '20%'},
                {field: 'binaryAnnotations', title: '方法调用链', width: '20%'},
                {field: 'annotations', title: '应用链路调用详情', width: '20%'}
            ]],
            data: zipKin,
            autoSort: false,
            page: true,
            limits: [10, 50, 100],
            limit: 10, //每页默认显示的数量
            done: function (res) {
                // 已移除记录背景标红
                if (res) {
                    res.data.forEach(function (v, index) {
                        $(".layui-table-body tr[data-index='" + index + "']").append();
                        var binary = JSON.parse(v.binaryAnnotations);
                        if (binary[0].key == 'error') {
                            $(".layui-table-body tr[data-index='" + index + "']").css("background", "#E9967A");
                        }
                    });
                }
                setHeight();
            }
        });

    }

});