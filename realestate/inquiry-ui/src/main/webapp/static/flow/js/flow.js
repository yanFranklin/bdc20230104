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
                    scaleFactor: 0.5,
                    type: "arrow"
                }
            },
            color: {
                color: '#999',
                hover: '#1d87d1'
            },
            // length: 300
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
        width: 188,
        height: 86
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
                var unselectedSvg, selectedSvg, unselectedUrl, selectedUrl;
                selectedSvg = '<svg xmlns="http://www.w3.org/2000/svg" width="' + width +'" height="'+ height+'">' +
                    '<rect width="'+ width + '" height="'+ height +'" style="fill:'+ nodesImageAttr.color.selectedColor.fillColor +'; stroke: '+ nodesImageAttr.color.selectedColor.fillColor+'; stroke-width: 5;"></rect>' +
                    '<foreignObject x="10" y="10" width="'+ (width-20) +'" height="'+ height+'">' +
                    '<div xmlns="http://www.w3.org/1999/xhtml" style="font-size:12px; background-color: '+ nodesImageAttr.color.selectedColor.backgroundColor+'; padding: 8px" >'+item.zl +'</div>' +
                    '<div xmlns="http://www.w3.org/1999/xhtml" style="font-size:12px; padding: 6px 10px 16px 0;  color: #fff">状态：'+item.zt +'</div>' +
                    '</foreignObject>' +
                    '</svg>';
                selectedUrl = "data:image/svg+xml;charset=utf-8,"+ encodeURIComponent(selectedSvg);
                if(index ==0) {
                    unselectedSvg = '<svg xmlns="http://www.w3.org/2000/svg" width="'+ width +'" height="'+height +'">' +
                        '<rect width="'+width+'" height="'+ height+'" style="fill:'+ nodesImageAttr.color.unselectedColor.nowColor.fillColor +'; stroke: '+nodesImageAttr.color.unselectedColor.nowColor.fillColor+'; stroke-width: 5;"></rect>' +
                        '<foreignObject x="10" y="10" width="'+(width-20) +'" height="'+ height+'">' +
                        '<div xmlns="http://www.w3.org/1999/xhtml" style="font-size:12px; background-color: '+nodesImageAttr.color.unselectedColor.nowColor.backgroundColor+'; padding: 8px" >'+item.zl +'</div>' +
                        '<div xmlns="http://www.w3.org/1999/xhtml" style="font-size:12px; padding: 6px 10px 16px 0;">状态：'+item.zt +'</div>' +
                        '</foreignObject>' +
                        '</svg>';
                } else {
                    unselectedSvg = '<svg xmlns="http://www.w3.org/2000/svg" width="'+ width+'" height="'+ height+'">' +
                        '<rect width="'+ width +'" height="'+ height +'" style="fill:'+ nodesImageAttr.color.unselectedColor.preColor.fillColor+'; stroke: '+ nodesImageAttr.color.unselectedColor.preColor.fillColor+'; stroke-width: 5;"></rect>' +
                        '<foreignObject x="10" y="10" width="'+ (width-20)+'" height="'+ height+'">' +
                        '<div xmlns="http://www.w3.org/1999/xhtml" style="font-size:12px; background-color: '+nodesImageAttr.color.unselectedColor.preColor.backgroundColor +'; padding: 8px;" >'+item.zl +'</div>' +
                        '<div xmlns="http://www.w3.org/1999/xhtml" style="font-size:12px; padding: 6px 10px 16px 0; ">状态：'+item.zt +'</div>' +
                        '</foreignObject>' +
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
                            label: item['zt']
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
                that.nodeClick(params);
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