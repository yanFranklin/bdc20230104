function Scrollbar() {
    this.options = {
        total: 0,   //数据总数
        pos: 0,     //当前滚动位置
        itemSize: 20,  // 滚动条每滚动一下的尺寸
        size: 200,  //滚动条尺寸 px
        length:200, // 滚动条的样式尺寸
        contentLength:200,// 内容的长度
        position:'Y' // 区分是X轴还是Y轴的滚动条
    };
}
Scrollbar.prototype = (function() {
    function setOptions(options) {
        for (var attr in options) {
            this.options[attr] = options[attr];
        }
        // Refresh(this);
    }
    function Refresh(_this) {
        if (!_this.created) return;
        if(isX(_this)){
            //设置控件宽度
            _this.bar.style.width = _this.options.length + "px";
            //设置X轴内容宽度
            var ch = _this.options.total * _this.options.itemSize;
            _this.content.style.width = ch + "px";
        }else{
            //设置控件高度
            _this.bar.style.height = _this.options.length + "px";
            //设置Y轴内容高度
            var ch = _this.options.total * _this.options.itemSize;
            _this.content.style.height = ch + "px";
        }

    }
    //获取滚动位置
    function getPos() {
        var pos;
        if(isX(this)){
            var left = this.bar.scrollLeft;
            left = left * (this.options.length/this.options.size);
            pos = parseInt(left / this.options.itemSize);
        }else{
            var top = this.bar.scrollTop;
            pos = parseInt(top / this.options.itemSize);
            if (pos == 0 && this.bar.scrollTop > 0) {
                pos = 1
            }
        }

        return pos;
    }

    //每页可展示的数据数量
    function getPageItems() {
        if(isX(this)){
            var w=document.documentElement.clientWidth;
            return (parseInt(w/110))+1;
        }
        return parseInt(this.options.size / this.options.itemSize) + 1;
    }

    //滚动事件响应
    function OnScroll(_this) {
        var pos = _this.getPos();
        if (pos == _this.options.pos) return;
        _this.options.pos = pos;
        _this.onScroll(pos);
    }

    function windowAddMouseWheel(resource,pos,yScrollId) {
        var _this=this;
        var scrollFunc = function (e) {
            var yScroll = resource.yScroll;
            var top = _this.bar.scrollTop - e.wheelDelta;
            _this.bar.scrollTop = top;
            var pos = parseInt(top / _this.options.itemSize);
            // 用当前滚动位置的起始下标值 + 当页页容量 计算 是否已达到最后一帧
            if(pos+ yScroll.getPageItems() <= yScroll.options.total) {
                e = e || window.event;
                if (e.wheelDelta) {
                    if (pos < 0) {
                        _this.options.pos = 0;
                        _this.onScroll(0);yScroll
                    } else {
                        _this.options.pos = pos;
                        _this.onScroll(pos);
                    }

                } else if (e.detail) {
                    var top = _this.bar.scrollTop - e.wheelDelta;
                    _this.bar.scrollTop = top;
                    var pos = parseInt(top / _this.options.itemSize);
                    if (pos < 0) {
                        _this.options.pos = 0;
                        _this.onScroll(0);
                    } else {
                        _this.options.pos = pos;
                        _this.onScroll(pos);
                    }
                }
            }
        };
        if (document.addEventListener) {
            document.addEventListener('DOMMouseScroll', scrollFunc, false);
        }
        window.onmousewheel = document.onmousewheel = scrollFunc;
    }

    //滚动条创建
    function CreateAt(dom) {
        var _this = this;

        var bar = document.createElement("div");
        var content = document.createElement("div");
        bar.appendChild(content);
        if(isX(_this)){
            bar.style.overflowY = "hidden";
            bar.style.overflowX = "scroll";
            bar.style.height = "20px";
            content.style.height = "1px";
        }else{
            bar.style.width = "20px";
            bar.style.overflowY = "scroll";
            bar.style.overflowX = "hidden";
            content.style.width = "1px";
        }
        if (bar.attachEvent) {
            bar.attachEvent("onscroll", function() { OnScroll(_this); });
        }
        else {//firefox兼容
            bar.addEventListener("scroll", function() { OnScroll(_this); }, false);
        }
        content.style.backgroundColor = "white";

        this.bar = bar;
        this.content = content;

        if (typeof (dom) == "string") {
            dom = document.getElementById(dom);
        }
        dom.innerHTML = "";
        dom.appendChild(this.bar);
        this.created = true;
        Refresh(this);
    }

    function isX(_this){
        return _this.options.position == 'X';
    }

    return {
        windowAddMouseWheel:windowAddMouseWheel,
        setOptions: setOptions,
        CreateAt: CreateAt,
        getPos: getPos,
        getPageItems: getPageItems,
        Refresh:Refresh,
        onScroll: null  //模拟滚动条事件
    };
})();