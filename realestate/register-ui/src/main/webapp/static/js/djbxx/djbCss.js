layui.use(['jquery'], function () {
    var $ = layui.$;

    //点击详情页表格
    $('.bdc-table-box').on('click', '.bdc-tr-box', function () {
        var $this = $(this);
        showTips($this);
    });

    var showMoreTop = 0;
    var showMoreLeft = 0;
    var $showMoreDiv = '';
    var scrollTop = 0;
    var scrollLeft = 0;
    var showDivWidth = 0;
    $(window).on('scroll', function (e) {
        scrollLeft = $(this).scrollLeft();
        scrollTop = $(this).scrollTop();

        if ($showMoreDiv != '') {
            $showMoreDiv.css({top: showMoreTop - scrollTop, left: showMoreLeft - scrollLeft});
        }
    });
    var halfWidth = ($('body').width()) / 2 + 250;

    $('.bdc-table-box').on('click', '.bdc-tr-content', function () {
        var $this = $(this);
        showTips($this);
    });

    function showTips($this) {
        $('.bdc-table-box').find('.bdc-table-click-show').remove();
        var getTitle = $this.data('title');
        showMoreLeft = $this.offset().left;
        showMoreTop = $this.offset().top;

        if (getTitle) {
            if (!$this.children().is('.bdc-table-click-show')) {
                $this.append('<div class="bdc-table-click-show"><img src="../../static/lib/bdcui/images/delete.png" alt="">' + getTitle + '</div>');
                $('.bdc-table-click-show').css('minWidth', $this.width());
                $showMoreDiv = $('.bdc-table-click-show');
                showDivWidth = $showMoreDiv.width();
                // console.log('halfWidth:'+halfWidth);
                // console.log('left:'+(showMoreLeft + showDivWidth - 80));
                if (halfWidth > (showMoreLeft + showDivWidth - 80)) {
                    showMoreTop = $this.offset().top + 5;
                    showMoreLeft = $this.offset().left + 20;
                } else {
                    showMoreTop = $this.offset().top + 5;
                    showMoreLeft = $this.offset().left - showDivWidth;
                }
                $showMoreDiv.css({
                    top: showMoreTop - scrollTop,
                    left: showMoreLeft - scrollLeft
                });
            }
        }
    }
    $('.bdc-table-box').on('click', '.bdc-table-click-show', function (event) {
        event.stopPropagation();
    });

    //点击删除小圈圈
    $('.bdc-table-box').on('click', '.bdc-table-click-show img', function (event) {
        event.stopPropagation();
        $(this).parent().remove();
    });


});

//设置附记栏
function changeFjHeight() {
    var maxHeight = 38;
    $('.bdc-fj .bdc-tr-box-content').each(function () {
        if (maxHeight < $(this).height()) {
            maxHeight = $(this).height()
        }
    });
    $('.bdc-tr-box.bdc-fj').height(maxHeight);
}
// 设置背景色
function changeQsztColor(dataArr){
    qsztindex = undefined;
    // 判断改变色值
    if (dataArr.length != 0) {
        for (var i = 0; i < dataArr.length; i++) {
            if (dataArr[i].qszt == 1) {
                qsztindex = i;
            }
            //djbxssjys为common.js传过来的十六进制色值
            if (qsztindex != undefined && djbxssjys != "") {
                var changediv = ".bdc-tr-width:nth-child(" + (qsztindex + 1) + ")";
                $(changediv).css("background-color", djbxssjys);
            }
        }
    }
}
/**
 * 设置多幢项目信息列宽
 */
function resetXmdzWidth() {
    for (var i = 0; i < 2; i++) {
        //获取动态添加的表格有几列
        var getWidth = Math.floor(100 / $($('.bdc-tr-table-js-' + [i])).find('.bdc-tr-td').length * 100)/100 + '%';
        // console.log(getWidth);
        $('.bdc-tr-table-js-' + [i] + ' ' + '.bdc-tr-td').css('width', getWidth);
    }
}



