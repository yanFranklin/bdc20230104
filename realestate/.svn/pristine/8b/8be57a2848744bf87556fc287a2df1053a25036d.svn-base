/**
 * Created by Administrator on 2017/10/26 0026.
 */
    var Slideicon = function (element,options) {
        this.element = element;
        this.options = {
            cover:options.cover,
            index:options.index,
            callback:options.callback
        };
        this.init();
    };
    Slideicon.prototype.init = function () {
        var _this = this;
        this.element.on('click','li',function(){
            $(this).nextAll().removeClass('active');
            $(this).prevAll().removeClass('active');
            var width = $(this).width();
            var left = ($(this).index())*width;
            _this.options.cover.attr("style","left:"+left+"px");
            $(this).addClass("active");
            params = $(this).attr('type');
            _this.options.callback(params)
        });
    };

