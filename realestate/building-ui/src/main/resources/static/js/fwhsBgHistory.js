//调用控件
// con_relation(config);

function reloadTable(data) {
    var configParam = {
        //控件设置
        widgetConfig: {
            domeId: "relationMap",
            //控件标题 默认：“”
            title: "",
            //标题位置 默认：lt（左上）。参数：{“l”：左，“c”：“水平居中”，“r”：右，“t”：“上”，“b”：“下”}
            titlePosition: "",
            //控件大小范围 :默认：500，500
            area: {width:960,height:400},
            //方向 默认：“h”  参数：{“h”：水平,"v"：垂直}
            direction: "",
            //单行限制 即每个数据框最多的字符数 默认4
            lineLimit: 0,
            //是否启用关系动画 默认：false
            isStartLineEffect: true,
            //样式设置 具体参考控件初始化方法
            color: {},

            fontSize: {}

        },
        //数据设置
        dataConfig: {
            //数据
            data: data,
            //关联字段  即数据关系链接字段 默认：“id”
            connectParam: "",
            //关联子项  即数据中子项集合的字段 默认：“children”
            children: "",
            //显示字段名称 即数据项显示的字段 默认：“name”
            displayParam: "fjh"
        },
        //方法触发回调
        labelEvent: {
            pannelHover: function (data) {
                return data.id;
            },
            click: function (data) {
                parent.layer.open({
                    type: 2,
                    title: "户室信息",
                    maxmin: true,
                    area: ['100%', '100%'],
                    fixed: false, //不固定
                    content: '../fwhs/historyhs?fwHsIndex=' + data.id + "&last=" + data.last
                });
            },
        },
        lineEvent: {
            pannelHover: function (data) {
                var length=data.data.coords.length;
                var bgrq=data.data.coords[length-2];
                var bglx=data.data.coords[length-1];
                return bgrq+"进行"+bglx;
            },
            /*click: function (data) {
                var length=data.data.coords.length;
                var bgbh=data.data.coords[length-2];
                parent.layer.open({
                    type: 2,
                    title: "变更信息",
                    maxmin: true,
                    area: ['100%', '100%'],
                    fixed: false, //不固定
                    content: '../bgxx/infobgxx?bgbh=' +bgbh
                });
            },*/
        }
    };

    con_relationWidget(configParam);
}

function con_relationWidget(_config) {
    //检查数据
    if (!document.getElementById(_config.widgetConfig.domeId)) {
        alert("获取操作节点失败");
    }
    if (_config.dataConfig.data.length == 0) {
        alert("数据错误");
    }

    //获取操作节点
    var elem = document.getElementById(_config.widgetConfig.domeId);

    //初始化数据
    var config = (function () {
        return {
            //控件设置
            widgetConfig: {
                domeId: _config.widgetConfig.domeId,
                //控件标题 默认：“”
                title: _config.widgetConfig.title || "",
                //标题位置 默认：lt（左上）。参数：{“l”：左，“c”：“水平居中”，“r”：右，“t”：“上”，“b”：“下”}
                titlePosition: _config.widgetConfig.titlePosition || "rt",
                //控件总刻度大小范围 :默认：500，500
                area: {width: _config.widgetConfig.area.width || 500, height: _config.widgetConfig.area.height || 500},
                //方向 默认：“h”  参数：{“h”：水平,"v"：垂直}
                direction: _config.widgetConfig.direction || "v",
                //单行限制 即每个数据框最多的字符数 默认4
                lineLimit: parseInt(_config.widgetConfig.lineLimit || 4),
                //是否启用关系动画 默认：false
                isStartLineEffect: _config.widgetConfig.isStartLineEffect || false,
                //样式设置 具体参考控件初始化方法
                color: {
                    //控件背景
                    backgroundColor: _config.widgetConfig.color.backgroundColor || "#fff",
                    //浮窗背景
                    tooltipBackgroundColor: _config.widgetConfig.color.tooltipBackgroundColor || "#EAF4FE",
                    //浮窗字体颜色
                    tooltipColor: _config.widgetConfig.color.tooltipColor || "#3B67E2",
                    //连线颜色
                    lineColor: _config.widgetConfig.color.lineColor || "#999",
                    //节点边框
                    borderColor: _config.widgetConfig.color.borderColor || "#999",
                    //节点字体颜色
                    labelColor: _config.widgetConfig.color.labelColor || "#000"
                },
                fontSize: {
                    //节点边框大小
                    borderWidth: _config.widgetConfig.color.borderWidth || 0.5,
                    //节点字体大小
                    labelFontSize: _config.widgetConfig.fontSize.labelFontSize || 14,
                    //节点内边距
                    labelPadding: _config.widgetConfig.fontSize.labelPadding || 10
                }

            },

            //数据设置
            dataConfig: {
                //数据
                data: _config.dataConfig.data,
                //关联字段  即数据关系链接字段 默认：“id”
                connectParam: _config.dataConfig.connectParam || "id",
                //关联子项  即数据中子项集合的字段 默认：“children”
                children: _config.dataConfig.children || "children",
                //显示字段名称 即数据项显示的字段 默认：“name”
                displayParam: _config.dataConfig.displayParam || "name"
            },
            //方法触发回调
            labelEvent: {
                pannelHover: _config.labelEvent.pannelHover || null,
                click: _config.labelEvent.click || null,
                doubleClick: _config.labelEvent.doubleClick || null,
            },
            lineEvent: {
                pannelHover: _config.lineEvent.pannelHover || null,
                click: _config.lineEvent.click || null,
                doubleClick: _config.lineEvent.doubleClick || null,
            }

        };
    })();

    //根据value获取数据项
    function getDataItemByLabelParam(param) {
        for (var p = 0, q = config.dataConfig.data.length; p < q; p++) {
            var _child = config.dataConfig.data[p];
            for (var s = 0, t = config.dataConfig.data[p].length; s < t; s++) {
                if (config.dataConfig.data[p][s].position.x == param.data.value[0] && config.dataConfig.data[p][s].position.y == param.data.value[1]) {
                    return config.dataConfig.data[p][s];
                }
            }
        }
    }

    //字符串截断处理
    function dealLabelParam(_data) {
        var _name = _data[config.dataConfig.displayParam];
        var len = 0;
        var nameArr = _name.split("");
        var str = "";
        for (var q = 0, p = _name.length; q < p; q++) {
            if (len > config.widgetConfig.lineLimit * 2) {
                str += "\n";
                len = 0;
            }
            var un = _name.charCodeAt(q);
            if (un >= 0 && un <= 127) {
                len += 1;
            } else if (un > 127) {
                len += 2;
            }
            str += nameArr.shift();
        }
        return str;
    }

    //将数据转换成echarts单元格数据
    var initEchartData = function (_baseForUniqueName) {
        var echartData = [];
        for (var i = 0, j = config.dataConfig.data.length; i < j; i++) {
            for (var n = 0, m = config.dataConfig.data[i].length; n < m; n++) {
                var poisition;
                if (config.widgetConfig.direction == "h") {
                    poisition = {
                        x: (config.widgetConfig.area.width / j / 2) * (1 + 2 * i),
                        y: (config.widgetConfig.area.height / m / 2) * (1 + 2 * n),
                    };
                    echartData.push({
                        name: "label" + (++_baseForUniqueName),
                        draggable: false,
                        fixed: true,
                        value: [poisition.x, poisition.y],
                        label: {
                            normal: {
                                position: "inside",
                                show: true,
                                textStyle: {
                                    fontSize: config.widgetConfig.fontSize.labelFontSize,
                                    color: config.widgetConfig.color.labelColor,
                                    fontFamily: 'microsoftYaHei'
                                },
                                formatter: function (val) {
                                    return dealLabelParam(getDataItemByLabelParam(val));
                                }
                            }
                        }
                    });
                    config.dataConfig.data[i][n].position = poisition;
                }

                if (config.widgetConfig.direction == "v") {
                    poisition = {
                        x: (config.widgetConfig.area.height / m / 2) * (1 + 2 * n),
                        y: config.widgetConfig.area.width - (config.widgetConfig.area.width / j / 2) * (1 + 2 * i),
                    };
                    echartData.push({
                        name: "label" + (++_baseForUniqueName),
                        draggable: false,
                        fixed: true,
                        value: [poisition.x, poisition.y],
                        label: {
                            normal: {
                                position: "inside",
                                show: true,
                                textStyle: {
                                    fontSize: config.widgetConfig.fontSize.labelFontSize,
                                    color: config.widgetConfig.color.labelColor,
                                    fontFamily: 'microsoftYaHei'
                                },
                                formatter: function (val) {
                                    return dealLabelParam(getDataItemByLabelParam(val));
                                }
                            }
                        }
                    });
                    config.dataConfig.data[i][n].position = poisition;
                }
            }
        }
        //限定控件面积
        elem.style.width = config.widgetConfig.area.width + "px";
        elem.style.height = config.widgetConfig.area.height + "px";
        return echartData;
    }(1);

    //将数据关系转换成线条
    var initEchartLines = function (data) {
        var children = config.dataConfig.children;
        var connectId = config.dataConfig.connectParam;
        var lines = [];
        for (var i = 0, j = data.length; i < j; i++) {
            if (i + 1 < j) {
                for (var n = 0, m = data[i].length; n < m; n++) {
                    var _coords = [];
                    for (var p = 0, q = data[i][n][children].length; p < q; p++) {
                        var _child = data[i][n][children][p];
                        for (var s = 0, t = data[i + 1].length; s < t; s++) {
                            if (_child == data[i + 1][s][connectId]) {
                                var bglx=data[i+1][s].bglx;
                                var bgrq=data[i+1][s].bgrq;
                                _coords.push({
                                    coords: [
                                        [data[i][n].position.x, data[i][n].position.y],
                                        [data[i + 1][s].position.x, data[i + 1][s].position.y],
                                        [bgrq],
                                        [bglx]
                                    ]
                                });
                            }
                        }
                    }
                    if (_coords.length > 0) {
                        lines.push({
                            type: 'lines',
                            coordinateSystem: 'cartesian2d',
                            z: 1,
                            symbol: 'arrow',
                            lineStyle: {
                                normal: {
                                    color: config.widgetConfig.color.lineColor,
                                    width: 1,
                                }
                            },
                            effect: {
                                show: config.widgetConfig.isStartLineEffect,
                                period: 6,
                                trailLength: 0,
                                color: '#000',
                                symbol: "arrow",
                                symbolSize: config.widgetConfig.fontSize.labelFontSize / 2
                            },
                            data: _coords
                        });
                    }
                }
            }
        }
        return lines;
    }(config.dataConfig.data);

    initEchartLines.unshift({
        type: 'graph', //图表类型
        coordinateSystem: 'cartesian2d', //直角坐标系
        symbol: 'rect',
        left:"center",
        symbolSize: function (value, param) {
            return function (val) {
                var x = 0;
                var y = 1;
                var len = 0;
                for (var q = 0, p = val[config.dataConfig.displayParam].length; q < p; q++) {
                    var un = val[config.dataConfig.displayParam].charCodeAt(q);
                    if (un >= 0 && un <= 127) {
                        len += 1;
                    } else if (un > 127) {
                        len += 2;
                    }
                    if (len >= config.widgetConfig.lineLimit * 2) {
                        x = config.widgetConfig.lineLimit * 1.5;
                        len = 0;
                        y += 1;
                    }
                }
                if (!x) {
                    x = len;
                }
                var paramSetting = [x * config.widgetConfig.fontSize.labelFontSize + config.widgetConfig.fontSize.labelPadding, y * config.widgetConfig.fontSize.labelFontSize + config.widgetConfig.fontSize.labelPadding];
                return [60,30];
            }(getDataItemByLabelParam(param));
        },
        symbolOffset:[0,'50%'],//相对自身下一50%，是箭头显示
        itemStyle: {
            color: config.widgetConfig.color.backgroundColor,
            borderColor: config.widgetConfig.color.borderColor,
            borderWidth: config.widgetConfig.fontSize.borderWidth
        },
        data: initEchartData
    });


    //初始化图表配置项
    var option = {
        title: {
            text: config.widgetConfig.title
            // 设置标题位置：
            // left: 'auto',left 的值可以是像 20 这样的具体像素值，可以是像 '20%' 这样相对于容器高宽的百分比，也可以是 'left', 'center', 'right'。
            // top: 'auto',top 的值可以是像 20 这样的具体像素值，可以是像 '20%' 这样相对于容器高宽的百分比，也可以是 'top', 'middle', 'bottom'。
            // right: 'auto',right 的值可以是像 20 这样的具体像素值，可以是像 '20%' 这样相对于容器高宽的百分比。
            // bottom: 'auto',bottom 的值可以是像 20 这样的具体像素值，可以是像 '20%' 这样相对于容器高宽的百分比。
        },
        tooltip: {
            trigger: "item",
            backgroundColor: config.widgetConfig.color.tooltipBackgroundColor,
            showDelay: 0,
            hideDelay: 0,
            enterable: true,
            transitionDuration: 0,
            extraCssText: "z-index:100",
            formatter: function (param, ticket, callback) {
                if (param.seriesType == "graph") {
                    var _value = getDataItemByLabelParam(param);
                    return config.labelEvent.pannelHover.call(this, _value);
                } else if (param.seriesType == "lines") {
                    return config.lineEvent.pannelHover.call(this, param);
                }
            },
            textStyle: {
                color: config.widgetConfig.color.tooltipColor
            }
        },
        //背景颜色
        backgroundColor: config.backgroundColor,
        //坐标系显示设置：否
        xAxis: {
            show: false,
            min:0,
            max:config.widgetConfig.area.height
        },
        yAxis: {
            show: false,
            min:0,
            max:config.widgetConfig.area.width
        },
        //系列列表
        series: initEchartLines
    };

    //数据绑定
    var _mychart = echarts.init(elem);
    _mychart.setOption(option);
    _mychart.on('click', function (param) {
        if (param.seriesType == "graph") {
            var _value = getDataItemByLabelParam(param);
            config.labelEvent.click.call(this, _value);
        } else if (param.seriesType == "lines") {
            config.lineEvent.click.call(this, param);
        }
    });
    _mychart.on('dblclick', function (param) {
        if (param.seriesType == "graph") {
            var _value = getDataItemByLabelParam(param);
            config.labelEvent.doubleClick.call(this, _value);
        } else if (param.seriesType == "lines") {
            config.lineEvent.doubleClick.call(this, param);
        }
    });
}