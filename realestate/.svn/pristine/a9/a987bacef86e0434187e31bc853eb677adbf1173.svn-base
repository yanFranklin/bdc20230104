var $, table,jquery, laytpl, layer;
var zjdid = getQueryString("zjdid");
var zdList = getMulZdList("djlx");
var temp;
var zjzt = getQueryString("zjzt");
layui.use(['table','jquery','laytpl','layer', 'form'],function () {
    table = layui.table;
    $ = layui.jquery;
    laytpl = layui.laytpl;
    layer = layui.layer;
    form = layui.form;

    $(function () {
        var operateBtn = '<button type="button" class="layui-btn layui-btn-danger layui-btn-xs bdc-major-btn" onclick="modifyZjxx(this)" id="xg" name="xg">修改</button>' + '<button type="button" class="layui-btn layui-btn-danger layui-btn-xs bdc-delete-btn" onclick="deleteZjxx(this)" id="sc" name="sc">删除</button>';
        var reverseList = ['time'];

        table.render({
            elem: '#pageTable',
            title: '不动产质检',
            defaultToolbar: [''],
            method: 'GET',
            url: getContextPath() + "/rest/v1.0/zjhc/zjxx/" + zjdid,
            even: true,
            cols: [[
                {field:'zjbh', title:'质检编号', width:250, fixed: 'left', unresize: true},
                {field:'zjsj', title:'质检日期', width:200},
                {field:'lcmc', title:'业务名称', width:300},
                {field:'djlx', title:'登记类型', width:180, templet:function(d) {
                        if(zdList.djlx) {
                            d.djlx = getZdMc(zdList.djlx, d.djlx);
                            return d.djlx;
                        }
                    }},
                {field:'slbh', title:'业务编号', width:200},
                {field:'zjzt', title:'质检结果', fixed: 'right', templet:function (d) {
                        if(d.zjzt == '-1'){
                            return '<span style="color:red">不通过</span>';
                        }else if (d.zjzt == '1') {
                            return '<span style="color:green">通过</span>';
                        }else if(d.zjzt == '0'){
                            return '<span>未质检</span>';
                        }

                    }},
                {field:'operate', title:'操作', width:100, templet:function(d) {
                    return operateBtn;
                    }}
            ]],
            parseData: function (res) {
                return {
                    "code": 0,
                    "msg": "",
                    "count": 10,
                    "data": res,
                }
            },
            done: function (data) {
                $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');

                reverseTableCell(reverseList);

                if($('.layui-table-body>.layui-table').height() == 0){
                    $('.layui-table-body .layui-none').html('<img src="../images/table-none.png" alt="">无数据');
                }
                temp = data;

                if (zjzt == 1) {
                    // 将所有按钮置为不可点击
                    $('button').attr('disabled', 'off');
                    // 修改删除按钮置为灰色、悬浮禁止
                    $('button').attr('disabled', 'off');
                    $('button').css('backgroundColor', '#999');
                    $('button').css('cursor', 'not-allowed');
                    $('button').hover(function() {
                        $(this).css('cursor','not-allowed');
                    });
                }
            }
        });
        $('.bdc-table-box').on('mouseenter','td',function () {
            if($(this).text() && reverseList.indexOf($(this).attr("data-field")) != -1){
                $(this).append('<div class="layui-table-grid-down"><i class="layui-icon layui-icon-down"></i></div>');
            }
            $('.layui-table-grid-down').on('click',function () {
                setTimeout(function () {
                    $('.layui-table-tips-main .bdc-table-date').html(reverseString($('.layui-table-tips-main .bdc-table-date').html()));
                },20);
            });
        });
        function reverseString(str) {
            // 实际应用需加上str判空处理
            str = str.replace(/&lt;/g, '>');
            str = str.replace(/&gt;/g, '<');
            var RexStr = /\（|\）|\(|\)|\【|\】|\【|\】|\[|\]|\[|\]/g;
            str = str.replace(RexStr, function(MatchStr) {
                switch (MatchStr) {
                    case "(":
                        return ")";
                        break;
                    case ")":
                        return "(";
                        break;
                    case "（":
                        return '）';
                        break;
                    case "）":
                        return "（";
                        break;
                    case "[":
                        return "]";
                        break;
                    case "]":
                        return "[";
                        break;
                    case "【":
                        return "】";
                        break;
                    case "】":
                        return "【";
                        break;
                }
            });
            return str.split("").reverse().join("");
        }
        function reverseTableCell(reverseList) {
            var getTd = $('.layui-table-view .layui-table td');
            for(var i = 0; i < getTd.length; i++){
                reverseList.forEach(function (v) {
                    if($(getTd[i]).attr('data-field') == v){
                        var getTdCell = $(getTd[i]).find('.layui-table-cell');
                        getTdCell.css('direction','rtl');
                        var tdHtml = reverseString(getTdCell.html());
                        getTdCell.html('<span class="bdc-table-date">'+ tdHtml +'</span>');
                    }
                });
            }
        }
        function reverseAddCell(reverseList) {
            reverseList.forEach(function (v) {
                var getClass = '.bdc-'+v;
                for(var i= 0; i < $(getClass).length; i++){
                    var $this = $($(getClass)[i]);
                    var tdHtml = reverseString($this.html());
                    // console.log(tdHtml);
                    $this.html(tdHtml);
                }

            });
        }
        // 办结按钮
        form.on("submit(finishBtn)", function (data) {
            addModel();
            $.ajax({
                url: getContextPath() + "/rest/v1.0/zjhc/zjd/zjzt/" + zjdid,
                type: 'GET',
                contentType: 'application/json',
                success: function (result) {
                    removeModal();
                    if (result.success == true) {
                        successMsg("办结成功");
                    } else {
                        warnMsg(result.message);
                    }
                },
                error: function (result) {
                    removeModal();
                    delAjaxErrorMsg(e,"办结失败！");
                }
            });
        });

    });

});


// 删除按钮
function deleteZjxx(obj) {
    var getThisIndex = $(obj).parents('tr').attr('data-index');
    var zjid = temp.data[getThisIndex].zjid;

    var deleteIndex = layer.open({
        type: 1,
        skin: 'bdc-small-tips',
        title: '确认删除',
        area: ['320px'],
        content: '是否确认删除？',
        btn: ['确定', '取消'],
        btnAlign: 'c',
        yes: function () {
            $.ajax({
                url: getContextPath() + "/rest/v1.0/zjhc/zjxx/"+ zjid,
                type: 'DELETE',
                success: function (data) {
                    layer.msg('删除成功');
                    if($(obj).parents('tbody').find('tr').length == 1){
                        deleteZjd();
                    }
                    $(obj).parents('tr').remove();
                },
                error: function (e) {
                    removeModal();
                    delAjaxErrorMsg(e,"删除失败！");
                }
            });
            layer.close(deleteIndex);
        },
        btn2: function () {
            //取消
            layer.close(deleteIndex);
        }
    });
    form.render();
}

// 删除质检单
function deleteZjd(){
    $.ajax({
        url: getContextPath() + "/rest/v1.0/zjhc/zjd/"+ zjdid,
        type: 'DELETE',
        success: function (data) {
            successMsg('质检单删除成功,页面即将关闭')
            setTimeout(function(){
                window.opener.location.reload();
                window.close();
                }, 3000);
        },
        error: function (e) {
            removeModal();
            delAjaxErrorMsg(e,"删除质检单失败！");
        }
    });
}

// 修改按钮
function modifyZjxx(obj) {
    var width = $(window).width() + "px";
    var height = $(window).height() + "px";
    var getThisIndex = $(obj).parents('tr').attr('data-index');
    var zjid = temp.data[getThisIndex].zjid;
    layer.open({
        title: '修改',
        type: 2,
        content: "/realestate-inquiry-ui/view/zj/bdczjbg.html?zjid="+ zjid,
        fixed: true,
        area: [width, height],
        btnAlign: 'c',
        success: function(layero, index) {
        },
        cancel: function(layero, index) {
            return;
        },
        end: function(){
            table.reload('pageTable');
        }
    });
}

function getZdMc(zd, zdx) {
    var zdmc = "";
    $.each(zd, function (index, item) {
        if(item.DM == zdx) {
            zdmc = item.MC;
        }
    });
    return zdmc;
}