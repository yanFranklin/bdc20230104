function zdTreeShow(){
    var showTree = false;
    if (resourceData.links) {
        for (var i = 0; i < resourceData.links.length; i++) {
            var link = resourceData.links[i];
            if (link.rel == "zdtreejson") {
                showTree = true;
            }
        }
    }
    if(showTree){
        showZdTree();
    }else{
        hideZdTree();
        resizePx();
    }
}

// 初始化宗地目录树
function initZdTree() {
    var zdTreeUrl = '';
    if (resourceData.links) {
        for (var i = 0; i < resourceData.links.length; i++) {
            var link = resourceData.links[i];
            if (link.rel == "zdtreejson") {
                zdTreeUrl = link.href;
            }
        }
    }
    if(zdTreeUrl){
        // 加载左侧目录树
        $.ajax({
            url: zdTreeUrl,
            dataType: "json",  //指定服务器返回的数据类型为jsonp
            success: function (data) {
                layui.use(['laytpl', 'form'], function () {
                    var laytpl = layui.laytpl;
                    var zdTree = zdTreeTpl.innerHTML
                        , zdTreeView = document.getElementById('asideCon');
                    laytpl(zdTree).render(data, function (html) {
                        zdTreeView.innerHTML = html;
                    });
                });
            }
        });
    }
}

function showZdTree(){
    $(".bdc-aside-open").removeClass("layui-hide");
}

function hideZdTree(){
    $(".bdc-aside-open").addClass("layui-hide");
}

// 侧边栏编剧
function resizePx(){
    $('.bdc-container').css('padding-left','0');
}
