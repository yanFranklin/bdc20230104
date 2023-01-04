var table, $, laytpl, layer,form;
//字典列表
var zdList;
// 获取是否是一手房
var isFirstHand = getQueryString("firsthand");
var gzlslid = getQueryString('processInsId');
var lclx = getQueryString("lclx");
var formStateId = getQueryString("formStateId");
var readOnly = getQueryString("readOnly");
// 截图base64数据
var screenshot = "", xmid;

layui.use(['table','jquery','laytpl','layer','form'],function () {
    table = layui.table;
    form = layui.form;
    $ = layui.jquery;
    laytpl = layui.laytpl;
    layer = layui.layer;
    $(function () {

        //获取字典列表、加载基本信息
        setTimeout(function () {
            addModel();
            try {
                $.when(loadButtonArea(),loadZdData(),loadYmxx()).done(function () {
                    removeModal();
                })
            } catch (e) {
                removeModal();
                delAjaxErrorMsg(e,"加载失败");
            }
        }, 50);

        //滚动时头部固定
        var $contentTitle = $('.bdc-content-fix:visible');
        if($contentTitle.length != 0){
            console.log('aaa');
            defaultStyle();
        }
        function defaultStyle() {
            if($(window).scrollTop() > 10){
                $contentTitle.css('top','0');
            }else if($(window).scrollTop() <= 10){
                $contentTitle.css('top','10px');
            }
        }

        $(window).resize(function(){
            if($contentTitle.length != 0){
                defaultStyle();
            }
        });
        $(window).on('scroll',function () {
            if($contentTitle.length != 0){
                if($(this).scrollTop() > 10){
                    $contentTitle.css('top','0');
                }else if($(this).scrollTop() <= 10){
                    $contentTitle.css('top','10px');
                }
            }
        });


    });


});

// 加载页面信息
function loadYmxx(){
    var jtcyxx;
    addModel();
    $.ajax({
        url: getContextPath() + '/slym/jtcy/ymxx/' + gzlslid,
        type: "GET",
        dataType: 'json',
        async: false,
        success: function (data) {
            removeModal();
            if (isNotBlank(data)) {
                xmid = data.bdcSlXmDO.xmid;
                jtcyxx = filterData(data);
            }
        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr)
        }
    });
    // 一手房
    if (isFirstHand == 'true') {
        generateYsfYmxx(jtcyxx);
    } else {
        generateEsfYmxx(jtcyxx);
    }
}
//修改曾用名
function updateCym(obj){
    console.log(obj);
}
// 格式化返回数据
function filterData(data){
    // 买方成员表信息
    var zrfcyb = [];
    if(null != data.bdcQlrGroupDTOLists && data.bdcQlrGroupDTOLists.length > 0){
        $.each(data.bdcQlrGroupDTOLists, function(index, sqrDTOList){
            $.each(sqrDTOList, function(i, sqrDTO){
                zrfcyb.push(sqrDTO);
            });
        });
    }
    // 卖方成员表信息
    var zcfcyb = [];
    if(null != data.bdcYwrGroupDTOLists && data.bdcYwrGroupDTOLists.length > 0){
        $.each(data.bdcYwrGroupDTOLists, function(index, sqrDTOList){
            $.each(sqrDTOList, function(i, sqrDTO){
                zcfcyb.push(sqrDTO);
            });
        });
    }
    return {
        fwxx : [{
            zl: data.bdcSlXmDO.zl,
            bdcdyh: data.bdcSlXmDO.bdcdyh,
            jzmj: isNotBlank(data.bdcSlFwxxDO) && isNotBlank(data.bdcSlFwxxDO.jzmj)?data.bdcSlFwxxDO.jzmj:"",
            ssxz: data.bdcSlJbxxDO.ssxz
        }],
        zrfcyb : zrfcyb,
        zcfcyb : zcfcyb,
        zrfzfxx : data.bdcZrfZfxxDTOList == null? []:data.bdcZrfZfxxDTOList,
        zcfzfxx : data.bdcZcfZfxxDTOList == null? []:data.bdcZcfZfxxDTOList,
    }
}

// 加载一手房信息
function generateYsfYmxx(data){
    var zrfcyb = data.zrfcyb;
    $('#notFirstHand').remove();
    var reverseList = ['zl'];
    table.render({
        elem: '#bcblfwxx',
        title: '本次办理房屋信息',
        defaultToolbar: ['filter'],
        even: true,
        cols: [[
            {field:'zl', title:'坐落'},
            {field:'bdcdyh',title:'不动产单元号'},
            {field:'jzmj', width: 200,title:'面积'},
            {field:'ssxz', title:'所属乡镇'}
        ]],
        data: data.fwxx,
        page: false,
        done: function () {
            removeModal();
            $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');

            reverseTableCell(reverseList);

            if($('.layui-table-body>.layui-table').height() == 0){
                $('.layui-table-body .layui-none').html('<img src="../images/table-none.png" alt="">无数据');
            }
        }
    });

    table.render({
        elem: '#zrfcyb',
        title: '买方成员表',
        toolbar: '#cybTpl',
        defaultToolbar: [],
        even: true,
        cols: [[
            {field: 'xh', title: '序号', width: 50, type: 'numbers'},
            {field:'hh', title:'户号'},
            {field:'id', title:'id', hide: true},

            {field:'mc', title:'姓名'},
            {field:'zjh', width:300,title:'身份证号'},
            {field:'cym', width:200,title:'曾用名',edit: 'text'},
            {field:'xb', width:100, title:'性别'},
            {field:'ysqrgx',width:200, title:'与户主关系'},
            {field:'hyzk', width:200, title:'婚姻状态',templet: '#selectTpl'},
            {field:'sc', width: 100, title:'操作', templet:function(d) {
                if(d.ysqrgx == "本人"){
                    return '<button type="button" class="layui-btn layui-btn-xs bdc-major-btn" ' +
                        'onclick="addJtcy(\''+d.id+'\')" name="addJtcy">新增</button>';
                }else{
                    var sqlids;
                    if(isNotBlank(d.sqridList)&& d.sqridList.length >0){
                        sqlids = d.sqridList.join(",");
                    }
                    return '<button type="button" class="layui-btn layui-btn-danger layui-btn-xs bdc-delete-btn sc" ' +
                        'onclick="deleteItem(this,\''+d.id+'\')" name="sc" data-sqrids="'+sqlids+'">删除</button>';
                }
            }},
        ]],
        data: data.zrfcyb,
        page: false,
        done: function (res, curr, count) {
            $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');

            form.render('select','zrfcyb');
            $('tr').each(function(e){
                var $cr=$(this);
                var dataindex = $cr.attr("data-index");
                $.each(res.data,function(index,value){
                    if(value.LAY_TABLE_INDEX==dataindex){
                        $cr.find('input').val(value.hyzk);
                    }
                });

            });
            reverseTableCell(reverseList);

            if($('.layui-table-body>.layui-table').height() == 0){
                $('.layui-table-body .layui-none').html('<img src="../images/table-none.png" alt="">无数据');
            }
        }
    });
    form.on('select(type)', function (data) {
        console.log(data.value);
        var elem = $(data.elem);
        var  id = elem.data("id");
        var  ysqrgx = elem.data("ysqrgx");
        var lx = '';
        if(ysqrgx!="本人"){
            lx = "1";
        }
        var hyzk = data.value;
        $.ajax({
            type: "POST",
            url: getContextPath() +  "/slym/jtcy/updateJtcyxx",
            data: {'id':id ,'hyzk':hyzk,'lx':lx},
            success:function (data) {
                if(data){
                    layer.msg("修改成功");
                }else{
                    layer.msg("修改失败");
                }
            },error:function (e) {
                response.fail(e, '');
            }
        });
    });

    table.render({
        elem: '#zrfzfxx',
        title: '买方住房信息',
        toolbar: '#zfxxTpl',
        defaultToolbar: [],
        even: true,
        cols: [[
            {field: 'xh', title: '序号', width: 50, type: 'numbers'},
            {field:'zl', title:'房屋坐落'},
            {field:'ghyt', width:100, title:'房屋用途'},
            {field:'bdcqzh', title:'不动产权证号'},
            {field:'qlrmc', width:250, title:'权利人名称'},
            {field:'qlrzjh', title:'权利人证件号'},
        ]],
        data: data.zrfzfxx,
        page: false,
        done: function () {
            $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');

            reverseTableCell(reverseList);

            if($('.layui-table-body>.layui-table').height() == 0){
                $('.layui-table-body .layui-none').html('<img src="../images/table-none.png" alt="">无数据');
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
    //监听单元格编辑
    table.on('edit(zrfcyb)', function (obj) {
        var lx = '';
        //修改曾用名
        var value = obj.value //得到修改后的值
            ,data = obj.data //得到所在行所有键
        if(data.ysqrgx!="本人"){
            lx = "1";
        }
        $.ajax({
            type: "POST",
            url: getContextPath() +  "/slym/jtcy/updateJtcyxx",
            data: {'id':data.id ,'cym':value,'lx':lx},
            success:function (data) {
                if(data){
                    layer.msg("修改成功");
                }else{
                    layer.msg("修改失败");
                }
            },error:function (e) {
                response.fail(e, '');
            }
        });

        // layer.msg('[ID: '+ data.id +'] ' + field + ' 字段更改为：'+ value);
    });
    // 始终完整显示坐落和房屋坐落
    showCompleteCol('zl');
}

// 加载二手房信息
function generateEsfYmxx(data){
    // 二手房
    $('#firstHand').remove();

    var reverseList = ['zl'];

    table.render({
        elem: '#bcblfwxx',
        title: '本次办理房屋信息',
        even: true,
        cols: [[
            {field:'zl', title:'坐落'},
            {field:'bdcdyh', title:'不动产单元号'},
            {field:'jzmj',width: 200, title:'面积'},
            {field:'ssxz', title:'所属乡镇'}
        ]],
        data: data.fwxx,
        page: false,
        done: function () {
            $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');

            reverseTableCell(reverseList);

            if($('.layui-table-body>.layui-table').height() == 0){
                $('.layui-table-body .layui-none').html('<img src="../images/table-none.png" alt="">无数据');
            }
        }
    });

    table.render({
        elem: '#zrfcyb',
        title: '买方成员表',
        even: true,
        toolbar: '#cybTpl',
        defaultToolbar: [],
        cols: [[
            {field: 'xh', title: '序号', width: 50, type: 'numbers'},
            {field:'hh', title:'户号'},
            {field:'mc', title:'姓名'},
            {field:'zjh', width:300,title:'身份证号'},
            {field:'cym', width:200,title:'曾用名',edit: 'text'},
            {field:'xb', width:100, title:'性别'},
            {field:'ysqrgx',width:200, title:'与户主关系'},
            {field:'hyzk', width:200, title:'婚姻状态',templet:'#esfmfxxTpl'},
            {field:'sc', width: 100, title:'操作', templet:function(d) {
                if(d.ysqrgx == "本人"){
                    return '<button type="button" class="layui-btn layui-btn-xs bdc-major-btn" ' +
                        'onclick="addJtcy(\''+d.id+'\')" name="addJtcy">新增</button>';
                }else{
                    var sqlids;
                    if(isNotBlank(d.sqridList)&& d.sqridList.length >0){
                        sqlids = d.sqridList.join(",");
                    }
                    return '<button type="button" class="layui-btn layui-btn-danger layui-btn-xs bdc-delete-btn sc" ' +
                        'onclick="deleteItem(this,\''+d.id+'\')" name="sc" data-sqrids="'+sqlids+'">删除</button>';
                }
            }},
        ]],
        data: data.zrfcyb,
        page: false,
        done: function (res, curr, count) {
            $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');
            form.render('select','notFirstHandzrfcyb');
            $('tr').each(function(e){
                var $cr=$(this);
                var dataindex = $cr.attr("data-index");
                $.each(res.data,function(index,value){
                    if(value.LAY_TABLE_INDEX==dataindex){
                        $cr.find('input').val(value.hyzk);
                    }
                });

            });
            reverseTableCell(reverseList);

            if($('.layui-table-body>.layui-table').height() == 0){
                $('.layui-table-body .layui-none').html('<img src="../images/table-none.png" alt="">无数据');
            }
        }
    });
    form.on('select(esfmf)', function (data) {
        console.log(data.value);
        var elem = $(data.elem);
        var  id = elem.data("id");
        var  ysqrgx = elem.data("ysqrgx");
        var lx = '';
        if(ysqrgx!="本人"){
            lx = "1";
        }
        var hyzk = data.value;
        $.ajax({
            type: "POST",
            url: getContextPath() +  "/slym/jtcy/updateJtcyxx",
            data: {'id':id ,'hyzk':hyzk,'lx':lx},
            success:function (data) {
                if(data){
                    layer.msg("修改成功");
                }else{
                    layer.msg("修改失败");
                }
            },error:function (e) {
                response.fail(e, '');
            }
        });
    });
    table.render({
        elem: '#zrfzfxx',
        title: '买方住房信息',
        even: true,
        toolbar: '#zfxxTpl',
        defaultToolbar: [],
        cols: [[
            {field: 'xh', title: '序号', width: 50, type: 'numbers'},
            {field:'zl', title:'房屋坐落'},
            {field:'ghyt', width:100, title:'房屋用途'},
            {field:'bdcqzh', title:'不动产权证号'},
            {field:'qlrmc', width:250, title:'权利人名称'},
            {field:'qlrzjh', title:'权利人证件号'},
        ]],
        data: data.zrfzfxx,
        page: false,
        done: function () {
            $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');

            reverseTableCell(reverseList);

            if($('.layui-table-body>.layui-table').height() == 0){
                $('.layui-table-body .layui-none').html('<img src="../images/table-none.png" alt="">无数据');
            }
        }
    });

    table.render({
        elem: '#zcfcyb',
        title: '卖方成员表',
        toolbar: '#cybTpl',
        defaultToolbar: [],
        even: true,
        cols: [[
            {field: 'xh', title: '序号', width: 50, type: 'numbers'},
            {field:'hh', title:'户号'},
            {field:'mc', title:'姓名'},
            {field:'zjh', width:300,title:'身份证号'},
            {field:'cym', width:200,title:'曾用名',edit: 'text'},
            {field:'xb', width:100, title:'性别'},
            {field:'ysqrgx',width:200, title:'与户主关系'},
            {field:'hyzk', width:200, title:'婚姻状态',templet:'#zcfhyzkTpl'},
            {field:'sc', width: 100, title:'操作', templet:function(d) {
                if(d.ysqrgx == "本人"){
                    return '<button type="button" class="layui-btn layui-btn-xs bdc-major-btn" ' +
                        'onclick="addJtcy(\''+d.id+'\')" name="addJtcy">新增</button>';
                }else{
                    var sqlids;
                    if(isNotBlank(d.sqridList)&& d.sqridList.length >0){
                        sqlids = d.sqridList.join(",");
                    }
                    return '<button type="button" class="layui-btn layui-btn-danger layui-btn-xs bdc-delete-btn sc" ' +
                        'onclick="deleteItem(this,\''+d.id+'\')" name="sc" data-sqrids="'+sqlids+'">删除</button>';
                }
            }},
        ]],
        data: data.zcfcyb,
        page: false,
        done: function (res, curr, count) {
            $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');
            form.render('select','zcfcyb');
            $("[lay-id='zcfcyb'] tbody tr").each(function(e){
                var $cr=$(this);
                var dataindex = $cr.attr("data-index");
                $.each(res.data,function(index,value){
                    if(value.LAY_TABLE_INDEX==dataindex){
                        $cr.find('input').val(value.hyzk);
                    }
                });

            });
            reverseTableCell(reverseList);

            if($('.layui-table-body>.layui-table').height() == 0){
                $('.layui-table-body .layui-none').html('<img src="../images/table-none.png" alt="">无数据');
            }
        }
    });
    form.on('select(zcfhyzk)', function (data) {
        console.log(data.value);
        var elem = $(data.elem);
        var  id = elem.data("id");
        var  ysqrgx = elem.data("ysqrgx");
        var lx = '';
        if(ysqrgx!="本人"){
            lx = "1";
        }
        var hyzk = data.value;
        $.ajax({
            type: "POST",
            url: getContextPath() +  "/slym/jtcy/updateJtcyxx",
            data: {'id':id ,'hyzk':hyzk,'lx':lx},
            success:function (data) {
                if(data){
                    layer.msg("修改成功");
                }else{
                    layer.msg("修改失败");
                }
            },error:function (e) {
                response.fail(e, '');
            }
        });
    });
    table.render({
        elem: '#zcfzfxx',
        title: '卖方住房信息',
        toolbar: '#zfxxTpl',
        defaultToolbar: [],
        even: true,
        cols: [[
            {field: 'xh', title: '序号', width: 50, type: 'numbers'},
            {field:'zl', title:'房屋坐落'},
            {field:'ghyt', width:100, title:'房屋用途'},
            {field:'bdcqzh', title:'不动产权证号'},
            {field:'qlrmc', width:250, title:'权利人名称'},
            {field:'qlrzjh', title:'权利人证件号'},
        ]],
        data: data.zcfzfxx,
        page: false,
        done: function () {
            $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');

            reverseTableCell(reverseList);

            if($('.layui-table-body>.layui-table').height() == 0){
                $('.layui-table-body .layui-none').html('<img src="../images/table-none.png" alt="">无数据');
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
    //监听单元格编辑
    table.on('edit(notFirstHandzrfcyb)', function (obj) {
        var lx = '';
        //修改曾用名
        var value = obj.value //得到修改后的值
            ,data = obj.data //得到所在行所有键
        if(data.ysqrgx!="本人"){
            lx = "1";
        }
        $.ajax({
            type: "POST",
            url: getContextPath() +  "/slym/jtcy/updateJtcyxx",
            data: {'id':data.id ,'cym':value,'lx':lx},
            success:function (data) {
                if(data){
                    layer.msg("修改成功");
                }else{
                    layer.msg("修改失败");
                }
            },error:function (e) {
                response.fail(e, '');
            }
        });

        // layer.msg('[ID: '+ data.id +'] ' + field + ' 字段更改为：'+ value);
    });

    //监听单元格编辑
    table.on('edit(zcfcyb)', function (obj) {
        var lx = '';
        //修改曾用名
        var value = obj.value //得到修改后的值
            ,data = obj.data //得到所在行所有键
        if(data.ysqrgx!="本人"){
            lx = "1";
        }
        $.ajax({
            type: "POST",
            url: getContextPath() +  "/slym/jtcy/updateJtcyxx",
            data: {'id':data.id ,'cym':value,'lx':lx},
            success:function (data) {
                if(data){
                    layer.msg("修改成功");
                }else{
                    layer.msg("修改失败");
                }
            },error:function (e) {
                response.fail(e, '');
            }
        });

        // layer.msg('[ID: '+ data.id +'] ' + field + ' 字段更改为：'+ value);
    });

    // 始终完整显示坐落和房屋坐落
    showCompleteCol('zl');
}

// 显示完整的列内容
function showCompleteCol(field) {
    var mostWidth = 0;
    var innerSpan = $(document).find('[data-field='+field+']').find('span');
    var outerDiv = $(document).find('[data-field='+field+']').find('div');

    innerSpan.css('display', 'inline-block');
    innerSpan.each(function(index, element) {
        if (parseInt($(element).css('width')) > mostWidth) {
            mostWidth = parseInt($(element).css('width'));
        }
    });
    outerDiv.css('minWidth', mostWidth + 20 + 'px');
}

// 删除行
function deleteItem(item, id, sqridList) {
    var url = getContextPath() + "/slym/jtcy?jtcyid=" + id;
    var sqrids = $(item).data("sqrids")
    if (isNotBlank(sqrids)) {
        url = getContextPath() + "/slym/jtcy/deleteBatchBdcSlJtcy?jtcyid=" + id +"&sqridList="+ sqrids.split(",");
    }

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
                url: url,
                type: 'DELETE',
                success: function (data) {
                    ityzl_SHOW_SUCCESS_LAYER("删除成功");
                    $(item).parents('tr').remove();
                }, error: function (xhr, status, error) {
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
}

// 新增家庭成员
function addJtcy(sqrid){
    var url = getContextPath() + "/nantong/jtcyxx/addjtcy.html?sqrid=" + sqrid;
    layer.open({
        title: '新增家庭成员信息',
        type: 2,
        area: ['960px', '400px'],
        content: url,
        cancel: function () {
            loadYmxx();
        }
    });
}


// 加载按钮
function loadButtonArea(){
    var tpl = buttonAreaTpl.innerHTML, view = document.getElementById('buttonArea');
    //渲染数据
    laytpl(tpl).render({lclx: lclx}, function (html) {
        view.innerHTML = html;
    });
    getStateById(readOnly, formStateId, "jtcyxx");
    disabledAddFa();
}

// 同步登记权利人信息
function syncDjQlr(){
    addModel();
    $.ajax({
        url: getContextPath() + "/slxxcsh/syncSlxx",
        type: 'GET',
        dataType: 'json',
        data: {
            gzlslid: gzlslid
        },
        success: function (data) {
            removeModal();
            ityzl_SHOW_SUCCESS_LAYER("同步完成");
            loadYmxx();
        },
        error: function (err) {
            removeModal();
            delAjaxErrorMsg(err);
        }
    });
}

// 民证查询
function queryHyxx() {
    addModel();
    $.ajax({
        url: getContextPath() + "/slym/jtcy/hyxx/"+ gzlslid,
        data:{isFirstHand:isFirstHand},
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            removeModal();
            ityzl_SHOW_SUCCESS_LAYER("查询完成");
            loadYmxx();
        },
        error: function (err) {
            removeModal();
            delAjaxErrorMsg(err);
        }
    });
}

// 户籍查询
function queryHjxx() {
    addModel();
    $.ajax({
        url: getContextPath() + "/slym/jtcy/hjxx/"+ gzlslid,
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            removeModal();
            ityzl_SHOW_SUCCESS_LAYER("查询完成");
            loadYmxx();
        },
        error: function (err) {
            removeModal();
            delAjaxErrorMsg(err);
        }
    });
}

// 家庭住房查询
function getAllFwtcxx() {
    addModel();
    $.ajax({
        url: getContextPath() + "/ycsl/getAllFwtcxx/"+ xmid,
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            removeModal();
            ityzl_SHOW_SUCCESS_LAYER("查询完成");
            loadYmxx();
        },
        error: function (err) {
            removeModal();
            delAjaxErrorMsg(err);
        }
    });
}

// 核税
function tsjsxx(){
    addModel();
    $.ajax({
        url: getContextPath() + "/slym/sw/tsHsxx/" + gzlslid,
        type: "GET",
        data: {},
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success: function (data) {
            removeModal();
            if (isNotBlank(data) && data.code == "1") {
                ityzl_SHOW_SUCCESS_LAYER("推送成功");
            } else {
                ityzl_SHOW_WARN_LAYER("推送失败：" + data.message);
            }
        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
        }
    });
}

// 隐藏页面按钮
function hideButton(){
    $("#buttonArea").hide();
    $("button").hide();
}
// 为TD TH 增加宽度属性
function addTableCellWidth(){
    var getTh = $('.layui-table-view .layui-table th');
    $.each(getTh, function(i, th){
        $(th).css('width', $(th).width());
    });
    var getTd = $('.layui-table-view .layui-table td');
    $.each(getTd, function(i, td){
        $(td).css('width', $(td).width());
    });
}


// 页面截图
function shotScreen(){
    hideButton();
    addTableCellWidth();
    // 防止屏幕截屏不全，页面置顶
    window.pageYoffset = 0;
    document.documentElement.scrollTop = 0;
    document.body.scrollTop = 0;
    html2canvas($('#jtcyxxBody'), {
        onrendered: function(canvas) {
            screenshot = canvas.toDataURL();
            $("#screenshot").attr("src", screenshot);

            var index = layer.open({
                title: '截图',
                type: 1,
                skin: 'bdc-spf-layer',
                area: ['430px'],
                btn: ['上传', '取消'],
                content: $('#bdc-popup-small-shot'),
                yes: function (index, layero) {
                    var data = {
                        gzlslid : gzlslid,
                        base64 : screenshot
                    };
                    $.ajax({
                        url: getContextPath() + "/slym/jtcy/upload/screenshot",
                        type: 'POST',
                        dataType: 'json',
                        async: false,
                        contentType: 'application/json;charset=utf-8',
                        data: JSON.stringify(data),
                        success: function (data) {
                            ityzl_SHOW_SUCCESS_LAYER("上传成功");
                            layer.close(index);
                        },
                        error: function (err) {
                            var responseText = JSON.parse(err.responseText);
                            ityzl_SHOW_WARN_LAYER(responseText.msg);
                        }
                    });
                }, end : function (){
                    location.reload();
                }
            });
            layer.full(index);
        }
    });
}

function loadZdData() {
    $.ajax({
        url: getContextPath() + "/bdczd",
        type: 'POST',
        dataType: 'json',
        async: false,
        success: function (data) {
            if (isNotBlank(data)) {
                zdList = data;
            }
        }
    });
}