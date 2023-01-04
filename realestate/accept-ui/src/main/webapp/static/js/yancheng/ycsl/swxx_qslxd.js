//字典列表
var zdList;
//地址栏获取数据
var processInsId = getQueryString("processInsId");
var formStateId = getQueryString("formStateId");
var readOnly = getQueryString("readOnly");
var xmid = getQueryString("xmid");
var slbh,zdList;
var $,laytpl,form;

layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl','laydate'], function () {
    var form = layui.form,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table,
        layer = layui.layer,
        laydatev = layui.laydate;

    $(function () {

        addModel();
        //获取字典列表、加载基本信息、加载收件材料
        setTimeout(function () {
            try {
                $.when(loadButtonArea(), loadZdData(),loadSwxx()).done(function () {
                    removeModal();
                })
            } catch (e) {
                removeModal();
                delAjaxErrorMsg(e, "加载失败");
                return
            }
        }, 50);

        // 加载Table
        table.render({
            elem: '#skdrxxTable',
            // toolbar: '#toolbarDemo',
            title: '税款导入信息',
            defaultToolbar: ['filter'],
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            even: true,
            cols: [[
                {field: 'bz', title: '备注', width:'90%'},
                {fixed: 'right', title:'操作', toolbar: '#barDemo', width:'10%'}
            ]],
            data: [],
            page: true,
            parseData: function (res) {
                return {
                    code: res.code, //解析接口状态
                    msg: res.msg, //解析提示文本
                    count: res.totalElements, //解析数据长度
                    data: res.content //解析数据列表
                }
            },
            done: function () {
                setHeight();
            }
        });

        // 监听导入按钮
        table.on('tool(skdrxxTable)', function(obj){
            if(obj.event === 'import'){
                // 导入操作
                drSwxx(obj.data);
            }
        });

        $(document).on('click','#cxswxx',function () {
            cxSwxx();
        })


        //按钮加载
        function loadButtonArea() {
            var tpl = buttonAreaTpl.innerHTML, view = document.getElementById('buttonArea');
            //渲染数据
            laytpl(tpl).render({}, function (html) {
                view.innerHTML = html;
            });
            form.render();
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
        // 加载税务信息
        window.loadSwxx = function (){
            getReturnData("/slym/xm/listBdcXm", {gzlslid: processInsId}, 'GET', function (xmxxList) {
                if(xmxxList &&xmxxList.length >0) {
                    xmid = xmxxList[0].xmid;
                    slbh= xmxxList[0].slbh;
                    $.ajax({
                        url: getContextPath() + "/slym/sw/getHsxx",
                        type: 'GET',
                        dataType: 'json',
                        async: false,
                        data: {gzlslid: processInsId, xmid: xmid,hsxxlx:2},
                        success: function (data) {
                            var bdcZrfSwxx;
                            if(isNotBlank(data.bdcZrfSwxxList)){
                                bdcZrfSwxx = data.bdcZrfSwxxList[0];
                            }
                            generateSwxx(bdcZrfSwxx);
                            removeModal();
                        }
                    });
                }else{
                    layer.alert("当前未生成项目数据，请确认！");
                }
            }, function (err) {
                console.log(err);
            },false);

        };

        //加载核税信息
        function generateSwxx(data) {
            var tpl;
            if(data){
                var json = data;
                tpl = sfhyTpl.innerHTML;
                //渲染数据
                laytpl(tpl).render(json, function (html) {
                    $('.bdc-sw-table tbody').html(html);
                });
                renderDate();
                form.render("select");
                resetSelectDisabledCss();

            }else {
                tpl = noneTpl.innerHTML;
                //渲染数据
                laytpl(tpl).render([], function (html) {
                    $('.bdc-sw-table tbody').html(html);
                });
            }

        }

        // 打开合同查询弹窗
        function cxSwxx() {
            // 打开弹窗之前先通过gzlslid传htbh 有则默认填入htbh

            //小弹出层
            layer.open({
                title: '合同查询',
                type: 1,
                area: ['430px','200px'],
                btn: ['提交', '取消'],
                content: $('#bdc-popup-small')
                ,yes: function(index, layero){
                    //提交 的回调
                    var htbh ='',nsrsbh ='';
                    if(isNotBlank($('#htbh').val())){
                        htbh = $('#htbh').val();
                        $('#htbh').val('');
                    }
                    //纳税人识别号取值
                    if(isNotBlank($('#nsrsbh').val())){
                        nsrsbh = $('#nsrsbh').val();
                        $('#nsrsbh').val('');
                    }
                    addModel();
                    setTimeout(function () {
                        getSwxx (htbh,nsrsbh);
                    }, 50);


                    parent.layer.close(index);

                }, success: function () {
                    // addModel();
                    var currentHtbh = getHtbhByGzlslid();
                }
            });
        }


        function getHtbhByGzlslid(){
            // processInsId = "3780147"
            //var htbh = "";
            $.ajax({
                url: getContextPath() + "/slym/sw/getHtbh/"+processInsId,
                type: 'GET',
                success: function (data) {
                    //htbh = data;
                    var htbh = data.htbabm;
                    var nsrsbh = data.qlrzjh;

                    $('#nsrsbh').val(nsrsbh);
                    removeModal();
                },
                error: function (xhr, status, error) {
                }
            });
            return htbh;

        }


        // 查询税款信息
        function getSwxx (htbh,nsrsbh){
            $.ajax({
                url: getContextPath() + "/slym/sw/getQslxd",
                type: 'GET',
                dataType: 'json',
                async: false,
                data: {xmid: xmid, slbh: slbh,gxlx:"0",htbh:htbh,nsrsbh:nsrsbh,beanName:"sw_qslxdhq"},
                success: function (data) {
                    removeModal();
                    if(data){
                        drSwxx(data);
                    }else{
                        var msg = "未查询到完税信息";
                        ityzl_SHOW_WARN_LAYER(msg);
                    }

                },
                error: function (xhr, status, error) {
                    removeModal();
                    delAjaxErrorMsg(xhr)
                }
            });
        };

        //导入税款信息
        function drSwxx(data) {
            addModel();
            var swxx =data;
            $.ajax({
                url: getContextPath() + "/slym/sw/saveQslxdResponse?xmid=" + xmid + "&gxlx=1",
                type: "POST",
                data:JSON.stringify(swxx),
                contentType: 'application/json',
                success: function (data) {
                    loadSwxx();
                    layer.closeAll();
                    if(!isNullOrEmpty(swxx.fcjyqslxdlist) &&!isNullOrEmpty(swxx.fcjyqslxdlist.fhxx)){
                        ityzl_SHOW_SUCCESS_LAYER(swxx.fcjyqslxdlist.fhxx);
                    }
                },
                error: function (xhr, status, error) {
                    removeModal();
                    delAjaxErrorMsg(xhr)
                }
            });
        }

        //打开税款表单弹窗
        function openSkTable(data) {
            layer.open({
                title: '税款信息',
                type: 1,
                area:['960px','500px'],
                // btn: [ '取消'],
                content: $('#bdc-popup-large')
                ,cancel: function(){
                    //右上角关闭回调
                    //return false 开启该代码可禁止点击该按钮关闭
                }
                ,success: function () {
                    table.reload('skdrxxTable',{
                        data:data,
                        height:'420px',
                        done: function () {
                            $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');
                        }
                    });
                }
            });
        }

    });

});