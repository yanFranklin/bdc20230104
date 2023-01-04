
layui.use(['form','jquery','laydate','element','table'],function () {
    var $ = layui.jquery,
        table = layui.table;

    $(function () {

        // 获取参数
        var processInsId = $.getUrlParam('processInsId');
        var readOnly = $.getUrlParam('readOnly');
        var formStateId = $.getUrlParam('formStateId');
        var xmxx=[];
        var qjgldm="";
        var searchData={};

        //获取项目
        getXmxx();

        var search ={
            gzlslid:processInsId,
            qjgldm:qjgldm
        };


        var isSearch = true;
        $('.bdc-table-box').on('focus', '.layui-laypage-skip .layui-input', function () {
            isSearch = false;
        }).on('blur', '.layui-laypage-skip .layui-input', function () {
            isSearch = true;
        });
        $(document).keydown(function (event) {
            if (event.keyCode == "13") {
                if (isSearch) {
                    $("#search").click();
                }
            }
        });

        $('#search').on('click',function () {
            // 获取查询内容
            $(".search").each(function (i) {
                var value = $(this).val();
                var name = $(this).attr('name');
                search[name] = value;
            });
            // 重新请求
            tableReload("fwTable", search);
            return false;
        });

        if (!(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion.match(/9./i) == "9.")) {
            //监听input点击
            $('.layui-form-item .layui-input-inline').on('click', '.layui-icon-close', function () {
                $(this).siblings('.layui-input').val('');
            });

            $('.layui-form-item .layui-input-inline .layui-input').on('focus', function () {
                $(this).siblings('.layui-icon-close').removeClass('bdc-hide');
            }).on('blur', function () {
                var $this = $(this);
                setTimeout(function () {
                    $this.siblings('.layui-icon-close').addClass('bdc-hide');
                }, 150)
            });
        }


        /**
         * 加载Table数据列表
         */
        table.render({
            elem: '#fwTable',
            id: 'fwTable',
            toolbar: '#toolbarDemo',
            title: '房屋信息',
            defaultToolbar: [],
            url: '/realestate-register-ui/rest/v1.0/tddysf/fwdyh/page',
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            where:search,
            even: true,
            cols: [[
                {type: 'checkbox', fixed: 'left'},
                {type: 'numbers', fixed: 'left'},
                {field: 'bdcdyh', title: '不动产单元号', minWidth: 250},
                {field: 'zl', title: '坐落', minWidth: 250},
                {field: 'fjh', title: '房间号', minWidth: 50},
                {field: 'jzmj', title: '建筑面积', minWidth: 50,templet: function (d) {
                        if(d.scjzmj >=0){
                            return d.scjzmj;
                        }else if(d.ycjzmj >=0){
                            return d.ycjzmj;
                        }else{
                            return '';
                        }
                    }},
                {field: 'tnjzmj', title: '套内建筑面积', minWidth: 50,templet: function (d) {
                        if(d.scjzmj >=0){
                            if(d.sctnjzmj >=0) {
                                return d.sctnjzmj;
                            }else{
                                return '';
                            }
                        }else if(d.yctnjzmj >=0){
                            return d.yctnjzmj;
                        }else{
                            return '';
                        }
                    }},
                {field: 'ftjzmj', title: '分摊建筑面积', minWidth: 50,templet: function (d) {
                        if(d.scjzmj >=0){
                            if(d.scftjzmj >=0) {
                                return d.scftjzmj;
                            }else{
                                return '';
                            }
                        }else if(d.ycftjzmj >=0){
                            return d.ycftjzmj;
                        }else{
                            return '';
                        }
                    }}
            ]],
            text: {
                none: '未查询到数据'
            },
            autoSort: false,
            page: true,
            limits: commonLimits,
            parseData: function (res) {
                return {
                    code: res.code, //解析接口状态
                    msg: res.msg, //解析提示文本
                    count: res.totalElements, //解析数据长度
                    data: res.content //解析数据列表
                }
            },
            done: function () {
                var searchHeight = 131;
                setTableHeight(searchHeight);

                // 设置字符过多，省略样式
                var reverseList = ['zl'];
                reverseTableCell(reverseList);

                // 获取表单控制权限
                if (readOnly || !isNullOrEmpty(formStateId)) {
                    getStateById(readOnly, formStateId, "tddysffw");
                }

            }
        });

        //主页面头工具栏事件
        table.on('toolbar(fwTable)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            switch (obj.event) {
                case 'add':
                    addFwdyh();
                    break;
                case 'delete':
                    deleteFwdyh(checkStatus.data);
                    break;
            }
        });

        //获取项目信息
        function getXmxx(){
            getReturnData("/rest/v1.0/bdcdy/xmxx/"+processInsId,{},"GET",function (data){
                xmxx =data;
                var djhList =[];
                for(var i=0;i<xmxx.length;i++) {
                    if(isNotBlank(xmxx[i].bdcdyh) &&xmxx[i].bdcdyh.length===28 &&xmxx[i].bdclx==1) {
                        djhList.push(xmxx[i].bdcdyh.substring(0, 19));
                    }
                }
                searchData.djhList=djhList.join(",");
                getReturnData("/rest/v1.0/qlxx/xmfb/"+data[0].xmid,{},"GET",function (fb){
                    qjgldm =fb.qjgldm;
                    searchData.qjgldm=qjgldm;
                },function (error){
                    delAjaxErrorMsg(error);
                },false);
            },function (error){
                delAjaxErrorMsg(error);

            },false);
        }

        //新增单元号
        function addFwdyh(){
            if(xmxx != null &&xmxx.length >0 &&xmxx[0].qszt !=0){
                warnMsg("当前项目已登簿,不允许新增");
                return false;
            }
            layer.open({
                title: '房屋户室列表',
                type: 2,
                area: ['960px', '450px'],
                content: '../tddysf/fwhsList.html?gzlslid='+processInsId
                , cancel: function (index, layero) {
                    parent.layer.close(index);
                }
                , success: function (layero,index) {
                    var iframeWin = window[layero.find('iframe')[0]['name']];//得到iframe页的窗口对象，执行iframe页的方法：
                    iframeWin.setSearchData(searchData);//调用子页面的方法，得到子页面返回的ids
                }
            });
        }

        //删除单元号
        function deleteFwdyh(checkData){
            if(xmxx != null &&xmxx.length >0 &&xmxx[0].qszt !=0){
                warnMsg("当前项目已登簿,不允许删除");
                return false;
            }
            if (checkData.length === 0) {
                showAlertDialog("未选择数据");
            } else {
                var bdcdyhArr=[];
                for (var i = 0; i < checkData.length; i++) {
                    var bdcdyh = checkData[i].bdcdyh;
                    bdcdyhArr.push(bdcdyh);
                }
                var bdcTddysfDyh = {};
                bdcTddysfDyh.scfs=1;
                bdcTddysfDyh.qjgldm=qjgldm;
                bdcTddysfDyh.gzlslid = processInsId;
                bdcTddysfDyh.bdcdyhList = bdcdyhArr;
                getReturnData("/rest/v1.0/tddysf/qxglfwdyh", JSON.stringify(bdcTddysfDyh), "DELETE", function () {
                    successMsg("取消关联成功");
                    parent.layer.closeAll();
                    parent.$("#search").click();
                }, function (error) {
                    delAjaxErrorMsg(error);
                });
            }

        }
    });
});





