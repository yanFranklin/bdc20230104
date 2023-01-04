/**
 * 户室变更图展示
 */
(function (obj) {
    // 默认选项
    var defaultOpts = {
        nodes: {
            chosen: {
                node: function(values, id, selected, hovering) {
                    // console.log(values, id, selected, hovering)
                }
            }
        },
        edges: {
            arrows: {
                to: {
                    enabled: true,
                    scaleFactor:0.5,
                    type: "arrow"
                }
            },
            color: {
                color: '#999',
                hover: '#1d87d1'
            },
            length : 800
        },
        interaction: {
            hover: true,
            dragView: true,
            dragNodes: false,
            selectConnectedEdges: false
        }
    };
    var defaultNodesImage = {
        color: {
            unselectedColor: {
                nowColor: {
                    fillColor: '#abcdf7',
                    backgroundColor: '#eaf4fe'
                },
                preColor: {
                    fillColor: '#d0d5da',
                    backgroundColor: '#eaedf1'
                }
            },
            selectedColor: {
                fillColor: '#1d87d1',
                backgroundColor: '#eaf4fe'
            }
        },
        width: 2100,  // 节点宽度
        height: 300   // 节点高度
    };

    function Flow(id, opts) {
        if(!id) {
            throw new Error('请输入容器id')
        }
        this.id = document.getElementById(id);
        this.graph = null;
        this.nodes = null;
        this.edges = null;

        opts.options = opts.options || defaultOpts;
        for(var key in defaultOpts) {
            if(!opts['options'][key]) {
                opts['options'][key] = defaultOpts[key];
            }
        }
        opts.nodesImage = opts.nodesImage || defaultNodesImage;
        for(var key in defaultNodesImage) {
            if(!opts['nodesImage'][key]) {
                opts['nodesImage'][key] = defaultNodesImage[key];
            }
        }

        for(var key in opts) {
            this[key] = opts[key];
        }
        console.log(this)
        var nodes = [],
            edges = [],
            that = this,
            data = that.data,
            nodesImageAttr = that.nodesImage,
            width = nodesImageAttr.width,
            height = nodesImageAttr.height;

        if(data instanceof Array) {
            data.forEach(function (item, index) {
                if(isNullOrEmpty(item.zl)){
                    return true;
                }

                var zlLength = item.zl.length;
                var zlStr = item.zl;
                if(zlLength > 50){
                    item.zl1 = zlStr.slice(0,25);
                    console.log(zlStr);
                    item.zl2 = zlStr.slice(25,50) + '...';
                }else if(zlLength > 25) {
                    item.zl1 = zlStr.slice(0,25);
                    console.log(zlStr);
                    item.zl2 = zlStr.slice(25,zlLength);
                }else {
                    item.zl1 = zlStr;
                    console.log(zlStr);
                    item.zl2 = '';
                }
                var unselectedSvg, selectedSvg, unselectedUrl, selectedUrl;
                selectedSvg = '<svg width="'+ width +'" height="'+height +'" version="1.1" xmlns="http://www.w3.org/2000/svg">'+
                    '<rect width="'+width +'" height="'+height +'" style="fill:'+ nodesImageAttr.color.selectedColor.fillColor +';stroke:'+nodesImageAttr.color.selectedColor.fillColor+'; stroke-width: 5;"/>'+
                    '<rect width="'+width +'" height="'+2*height/3 +'" style="fill:'+nodesImageAttr.color.selectedColor.backgroundColor+';stroke:'+ nodesImageAttr.color.selectedColor.fillColor +'; stroke-width: 5;"/>'+
                    '<text x="20" y="'+ (height/5 +20) +'" font-size="80px" style="fill:#000">'+item.zl1 +'</text>'+
                    '<text x="20" y="'+ (height/2+15) +'" font-size="80px" style="fill:#000">'+item.zl2 +'</text>'+
                    '<text x="20" y="'+ (height- 25) +'" font-size="80px" style="fill:#000">不动产单元号：'+item.bdcdyh +'</text>'+
                    '</svg>';
                selectedUrl = "data:image/svg+xml;charset=utf-8,"+ encodeURIComponent(selectedSvg);
                if(index ==0) {
                    unselectedSvg = '<svg width="'+ width +'" height="'+height +'" version="1.1" xmlns="http://www.w3.org/2000/svg">'+
                        '<rect width="'+width +'" height="'+height +'" style="fill:'+ nodesImageAttr.color.unselectedColor.nowColor.fillColor +';stroke:'+nodesImageAttr.color.unselectedColor.nowColor.fillColor+'; stroke-width: 5;"/>'+
                        '<rect width="'+width +'" height="'+2*height/3 +'" style="fill:'+nodesImageAttr.color.unselectedColor.nowColor.backgroundColor+';stroke:'+ nodesImageAttr.color.unselectedColor.nowColor.fillColor +'; stroke-width: 5;"/>'+
                        '<text x="20" y="'+ (height/5 +20) +'" font-size="80px" style="fill:#000">'+item.zl1 +'</text>'+
                        '<text x="20" y="'+ (height/2+15) +'" font-size="80px" style="fill:#000">'+item.zl2 +'</text>'+
                        '<text x="20" y="'+ (height- 25) +'" font-size="80px" style="fill:#000">不动产单元号：'+item.bdcdyh +'</text>'+
                        '</svg>';
                } else {
                    unselectedSvg = '<svg width="'+ width +'" height="'+height +'" version="1.1" xmlns="http://www.w3.org/2000/svg">'+
                        '<rect width="'+width +'" height="'+height +'" style="fill:'+ nodesImageAttr.color.unselectedColor.preColor.fillColor+';stroke:'+ nodesImageAttr.color.unselectedColor.preColor.fillColor+'; stroke-width: 5;"/>'+
                        '<rect width="'+width +'" height="'+2*height/3 +'" style="fill:'+nodesImageAttr.color.unselectedColor.preColor.backgroundColor +';stroke:'+ nodesImageAttr.color.unselectedColor.preColor.fillColor+'; stroke-width: 5;"/>'+
                        '<text x="20" y="'+ (height/5 +20) +'" font-size="80px" style="fill:#000">'+item.zl1 +'</text>'+
                        '<text x="20" y="'+ (height/2+15) +'" font-size="80px" style="fill:#000">'+item.zl2 +'</text>'+
                        '<text x="20" y="'+ (height- 25) +'" font-size="80px" style="fill:#000">不动产单元号：'+item.bdcdyh +'</text>'+
                        '</svg>';
                }

                unselectedUrl = "data:image/svg+xml;charset=utf-8,"+ encodeURIComponent(unselectedSvg);
                nodes[index] = item;
                nodes[index]["shape"] = 'image';
                nodes[index]["image"] = {
                    unselected: unselectedUrl,
                    selected: selectedUrl
                };
                nodes[index]["label"] = '';
                nodes[index]["physics"] = true;

                if(item["from"].length>0) {
                    var itemFrom = item["from"];
                    itemFrom.forEach(function(item2) {
                        edges.push({
                            from: item2,
                            to: item["id"],
                            label: item ['bgrq'].substring(0, 10)+":"+item['bglx']
                        });
                    });
                }
                that.nodes = nodes;
                that.edges = edges;
            });
        } else {
            throw new Error('传入的数据不是数组类型')
        }
        this.init();
    }



    Flow.prototype = {
        constructor: Flow,
        init: function () {
            var that = this;
            var data = {
                nodes: that.nodes,
                edges: that.edges,
            };
            var options = that.options;
            that.graph = new vis.Network(this.id, data, options);
            that.graph.on('click', function (params) {
                var getNode;
                data.nodes.forEach(function(v){
                    if(v.id == params.nodes[0]){
                        getNode = v;
                    }
                });
                that.nodeClick(getNode);
            })
        }
    };
    if(typeof define !== "undefined" && typeof define === "function") {
        define("Flow",[],function(){
            return Flow;
        });
    }else {
        obj.Flow = Flow;
    }
})(window);