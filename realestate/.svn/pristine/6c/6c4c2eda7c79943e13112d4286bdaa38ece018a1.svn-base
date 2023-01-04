//获取收件材料信息,渲染到页面
var sjclNumber = 0;
//用于存放所有的收件材料id
var sjclidLists = [];
var sjclids = new Set();
//权利信息项目信息对象
var qlxx =[];
var layer, laytpl, form, formSelects,table,$;
var processInsId = $.getUrlParam('processInsId');
//是否只读
var readOnly = $.getUrlParam('readOnly');
//表单ID
var formStateId = $.getUrlParam('formStateId');
//证书模板标志
var zsmodel = getQueryString("zsmodel");
//是否注销流程
var zxlc = $.getUrlParam('zxlc');
//工作流实例ID
var gzlslid = $.getUrlParam('processInsId');
var zdList =[];
layui.use(['form','jquery','laydate','element','layer','laytpl'],function () {
        $ = layui.jquery;
        var element = layui.element;
        form = layui.form;
        layer = layui.layer;
        var laydate = layui.laydate;
        laytpl = layui.laytpl;

    $(function () {

        /**
         * 字典
         */
         getCommonZd(function (data) {
             zdList =data;
         });

        //初始化tab及tab下的内容
        generateTabContent(gzlslid,zxlc,zdList);

        //表单保存
        saveCommonForm(function (data) {
            addModel();
            setTimeout(function () {
                try {
                    $.when(saveJyqData()).done(function () {
                        //重新加载
                        reloadQlxxTab(gzlslid,zxlc,zdList);
                        removeModal();
                        ityzl_SHOW_SUCCESS_LAYER("保存成功");
                    });
                } catch (e) {
                    removeModal();
                    if (e.message) {
                        showAlertDialog(e.message);
                    } else {
                        delAjaxErrorMsg(e);
                    }
                    return;
                }
            }, 10);

        });



        //监听第一次单击tab
        var qlIndex = 0;
        element.on('tab(tabFilter)', function (data) {
            if (qlIndex === 0) {
                qlIndex++;
                addModel();
                setTimeout(function () {
                    //加载权利tab内容
                    generateQlxxTabContent(gzlslid,zxlc,zdList);

                },0);

            }

        });


        //新增相关功能
        //新增
        $(document).on('click', '.bdc-item-add', function () {
            var $parent = $(this).parents(".basic-info");
            // 获取新增序号
            var index = $parent.find('.bdc-form-add').length + 1;
            var json = {index:index,zd:zdList};
            //获取权利项目信息
            var qlxmxx = form.val("cbfFilter");
            json.qlxmxx =qlxmxx;
            var getTpl = addCbfTpl.innerHTML;
            laytpl(getTpl).render(json, function(html){
                $parent.append(html);

                //页面渲染
                renderJyqForm();
            });
        });
        //收起、展开
        var $info = $(document).find('#fbfcbfxx');
        $info.on('click','.bdc-show-more',function () {
            var $this = $(this);
            if($this.html() == '收起'){
                $this.html('展开');
            }else {
                $this.html('收起');
            }
            $this.parent().parent().siblings('.bdc-more-item').toggleClass('bdc-hide');
        });
        // 加载家庭成员
        $info.on('click', '.loadJtcy', function(){
            // 校验承包方信息
            var $parentDiv = $(this).parents(".cbf-info");
            var cbfmc = $parentDiv.find("#cbfmc").val();
            var cbfzjzl = $parentDiv.find("#cbfzjzl").val();
            var cbfzjh = $parentDiv.find("#cbfzjh").val();
            var hasValue = isNotBlank(cbfmc) && isNotBlank(cbfzjzl) && isNotBlank(cbfzjh);
            if(!hasValue){
                ityzl_SHOW_WARN_LAYER("请正确填写承包方信息");
                return;
            }
            // 先进行展开操作
            if($parentDiv.find('.bdc-show-more').html() == '展开'){
                $parentDiv.find('.bdc-show-more').click();
            }
            addModel();
            getReturnData("/rest/v1.0/jtcy/queryJtcyxx/"+cbfzjh, "", 'GET', function (data) {
                removeModal();
                var jtcyxx = {zd : zdList};
                if(!isNotBlank(data)){
                    ityzl_SHOW_WARN_LAYER("无家庭成员信息，请录入家庭成员!");
                    jtcyxx.jtcyList = [{
                        jtcymc : cbfmc,
                        jtcyzjh : cbfzjh,
                        yhzgx : "02",
                    }];
                }else{
                    jtcyxx.jtcyList = data;
                }
                // 将表格中的户主信息加载在家庭信息成员处, 先清空tbody里面的表格，在重新渲染
                $parentDiv.find(".bdc-jt-table").empty();
                var showJtcyxx = jtcyxxTpl.innerHTML, jtcyTable = $parentDiv.find(".bdc-jt-table")[0];
                //渲染数据
                laytpl(showJtcyxx).render(jtcyxx, function (html) {
                    jtcyTable.innerHTML = html;
                    form.render();
                });
            }, function (err) {
                removeModal();
                delAjaxErrorMsg(err);
            });
        });


        //承包方删除
        $info.on('click','.bdc-item-delete',function () {
            deleteCbf($(this));

        });
        
        // 家庭成员删除
        $(document).on('click','.bdc-jtcy-add',function () {
            var $parent = $(this).parents('tbody');
            var xh =$parent.find("tr:last-child").find(".bdc-jtcy-xh").html();
            var hkbbm =$parent.find("tr:last-child").find("#hkbbm").val();
            var json ={};
            json.xh =parseInt(xh)+1;
            json.hkbbm =hkbbm;
            json.zd=zdList;
            var getTpl = addJtcyTpl.innerHTML;
            laytpl(getTpl).render(json, function(html){
                $parent.append(html);
                form.render();
            });
        });

        //家庭成员删除
        $(document).on('click','.bdc-jtcy-del',function () {
            deleteBdcJtcy($(this));

        });

        //监听收件材料复选框选择
        form.on('checkbox(qxCheckbox)', function (data) {
            $('td input[name=yxTable]').each(function (index, item) {
                item.checked = data.elem.checked;
                var qxData = item.value;
                //如果是选中的则添加，否则全部删除
                if (item.checked) {
                    sjclids.add(qxData)
                } else {
                    sjclids.delete(qxData);
                }
            });
            form.render('checkbox');
        });
        //单个的
        form.on('checkbox(yxCheckbox)', function (data) {
            var checkedLength = $('td .yx+.layui-form-checked[lay-skin=primary]').length;
            var checkBoxLength = $('td .yx+.layui-form-checkbox[lay-skin=primary]').length;
            var qxCheckBox = $('.gray-tr input[name=qxTable]')[0];
            var sjclid = data.value;
            if (sjclids.has(sjclid)) {
                sjclids.delete(sjclid);
            } else {
                sjclids.add(sjclid);
            }
            //判断是否全选，全选的时候勾选最上面的复选框
            if (checkedLength == checkBoxLength) {
                qxCheckBox.checked = true;
            } else {
                qxCheckBox.checked = false;
            }
            form.render('checkbox');
        });

        //监听面积单位
        form.on('checkbox(mjdw)', function () {
            $("[name='mjdw']").prop("checked", "");
            $(this).prop("checked", "checked");
            form.render('checkbox');
        });

        //监听证件种类
        form.on('select(zjzl)', function (data) {
            var $zjh = $(data.elem).parents(".bdc-qlr-info").find("input[name=zjh]");
            addSfzYz(data.value, $zjh);
        });

        //证书预览
        $("#createZs").click(function () {
            createZs();
        });

        // 重新定位地块信息列表下拉框
        $(document).on('click','#dkxxTable div.layui-form-select',function(){
            var width = $(this).width();
            var left = $(this).parents('td').offset().left + 50;
            var height = $(this).find('dd').length * 40;
            var top = $(this).parents('td').offset().top - 180;
            if(height < 130){
                top =top + 100;
            }
            $(this).find('dl').css({'min-width':width+'px', "height":height+'px', top:top +'px', 'margin-left':left+'px'});
        });

    });

   //初始化tab及tab下的内容
    function generateTabContent(gzlslid,zxlc,zdList) {
        getReturnData("/slym/ql/plzh", {processInsId: gzlslid, zxlc: zxlc}, 'GET', function (data) {
            if (isNotBlank(data)) {
                qlxx = data;
            }
        }, function (err) {
            console.log(err);
        }, false);

        var json = {
            qlxx: qlxx
        };

        if(qlxx.length ===0){
            ityzl_SHOW_WARN_LAYER("未查询到项目信息");
        }

        //渲染tab页
        var liTpl = liTableTpl.innerHTML, liView = $('#tableUi');
        laytpl(liTpl).render(json, function (html) {
            liView.append(html);
        });
        //渲染tab内容区
        var tabTpl= tabContentTpL.innerHTML,
            tabContentView = document.getElementById("tabContent");
        laytpl(tabTpl).render(json, function (html) {
            tabContentView.innerHTML = html;
        });

        //渲染基本信息
        var jbxxJson = {
            bdcxmxx: qlxx[0].bdcXm,
            zd: zdList
        };
        var jbTpl = jbxxTpl.innerHTML, jbxxView = document.getElementById('sljbXx');
        //渲染数据
        laytpl(jbTpl).render(jbxxJson, function (html) {
            jbxxView.innerHTML = html;
        });
        //加载收件材料
        loadSjcl();

        //页面渲染
        renderJyqForm();


    }

    //加载权利tab下的内容
    function generateQlxxTabContent(gzlslid,zxlc,zdList){
        if(!isNotBlank(gzlslid)){
            ityzl_SHOW_WARN_LAYER("缺失工作流实例ID!");
            return false;
        }
        if(qlxx.length >0){
            var qlxxList =[];
            //查询出流程权利信息与单元信息
            var data={};
            if (zxlc === "true") {
                //注销流程
                data["sfyql"] = true;
            }else{
                data["sfyql"] = false;
            }
            data["gzlslid"] = gzlslid;
            data["glzdjbxx"] = true;
            getReturnData("/slym/ql/listQl/all?sfyql="+data.sfyql,JSON.stringify(data),"POST",function (data) {
                qlxxList=data;

            },function (error) {
                
            },false);
            for (var i = 0; i < qlxx.length; i++) {
                //获取当前tab
                var $tab =$('.layui-tab-item:nth-child(' + (i+2) + ')');
                var xmid =qlxx[i].bdcXm.xmid;
                //当前tab对应的项目ID集合
                var groupXmidList =qlxx[i].groupXmidList;

                var qltabJson ={};
                //获取当前tab权利信息
                var tabQlxxList =[];
                //合同总面积
                var countCbMj = 0;
                //实测总面积
                var countScMj = 0;
                //经营总面积
                var countJyMj = 0;
                for (var qlxxLength = 0; qlxxLength < qlxxList.length; qlxxLength++) {
                    if(groupXmidList.indexOf(qlxxList[qlxxLength].xmid) >-1){
                        if(isNotBlank(qlxxList[qlxxLength].cbmj)) {
                            countCbMj += parseFloat(qlxxList[qlxxLength].cbmj);
                        }
                        if(isNotBlank(qlxxList[qlxxLength].scmj)) {
                            countScMj += parseFloat(qlxxList[qlxxLength].scmj);
                        }
                        if(isNotBlank(qlxxList[qlxxLength].jymj)) {
                            countJyMj += parseFloat(qlxxList[qlxxLength].jymj);
                        }
                        //宗地四至
                        tabQlxxList.push(qlxxList[qlxxLength]);
                    }
                }
                countCbMj =formatFloat(countCbMj);
                countScMj =formatFloat(countScMj);
                countJyMj =formatFloat(countJyMj);
                qltabJson.countCbMj=countCbMj;
                qltabJson.countScMj=countScMj;
                qltabJson.countJyMj=countJyMj;
                qltabJson.zd=zdList;
                //此处使用bdcXm中的一些固定属性(qllx),填报字段不能使用此全局变量,刷新会出现问题
                qltabJson.bdcXm=qlxx[i].bdcXm;
                qltabJson.tabQlxxList=tabQlxxList;
                //总地块数
                qltabJson.count=tabQlxxList.length;
                //获取登记原因字典
                getReturnData("/slym/xm/queryDjxlDjyy", {djxl: qltabJson.bdcXm.djxl}, 'GET', function (data) {
                    if (isNotBlank(data)) {
                        qltabJson.djxldjyyZd = data;
                    }
                }, function (err) {
                    console.log(err);
                }, false);
                //区分经营权与土地承包
                if(qlxx[i].bdcXm.qllx ===35){
                    //经营权
                    qltabJson.tabJyqlxxList=tabQlxxList;
                    //加载经营信息
                    generateJyxx(xmid,$tab,tabQlxxList,qltabJson);

                    //加载经营地块信息
                    generateJyDkxx(groupXmidList, $tab, qltabJson);


                }else {
                    qltabJson.tabCbqlxxList=tabQlxxList;
                    //加载发包方承包方信息
                    generateFbfcbfxx(xmid, $tab, tabQlxxList, qltabJson);

                    //加载地块信息
                    generateDkxx(groupXmidList, $tab, qltabJson);
                }

            }

            //增加身份证号校验
            $("[lay-filter=zjzl]").each(function () {
                var $zjh = $(this).parents(".bdc-qlr-info").find("input[name=zjh]");
                addSfzYz($(this).val(), $zjh);
            });

            //页面渲染
            renderJyqForm();

        }
        removeModal();

    }

    //刷新权利页面
    function reloadQlxxTab(gzlslid,zxlc,zdList){
        getReturnData("/slym/ql/plzh", {processInsId: gzlslid, zxlc: zxlc}, 'GET', function (data) {
            if (isNotBlank(data)) {
                qlxx = data;
            }
        }, function (err) {
            console.log(err);
        }, false);
        generateQlxxTabContent(gzlslid,zxlc,zdList);

    }

    //加载发包方承包方信息(土地承包权)
    function generateFbfcbfxx(xmid,$tab,tabQlxxList,fbfcbfxx){
        //获取发包方信息
        getReturnData("/slym/qlr/listBdcQlr", JSON.stringify({xmid: xmid, qlrlb: 2}), 'POST', function (data) {
            if(data.length ===0){
                data =[];
                data.push({});
            }
            fbfcbfxx.fbfxxList = data;
        }, function (err) {
            delAjaxErrorMsg(err);
        }, false);
        //获取承包方以及家庭成员信息
        getReturnData("/slym/qlr/listBdcQlr", JSON.stringify({xmid: xmid, qlrlb: 1}), 'POST', function (data) {
            var cbfAndJtcyxxList =[];
            if(data.length >0){
                for (var qlrlength = 0; qlrlength < data.length; qlrlength++) {
                    var cbfAndJtcy ={};
                    cbfAndJtcy.cbfxx =data[qlrlength];
                    getReturnData("/rest/v1.0/jtcy/qlrid/"+data[qlrlength].qlrid,{},"GET",function (jtcydata) {
                        cbfAndJtcy.jtcyList =jtcydata;
                        
                    },function (error) {
                        delAjaxErrorMsg(error);
                    },false);
                    cbfAndJtcyxxList.push(cbfAndJtcy);

                }
            }else{
                var cbfAndJtcyxx ={};
                cbfAndJtcyxx.cbfxx={};
                cbfAndJtcyxxList.push(cbfAndJtcyxx);
            }
            fbfcbfxx.cbfAndJtcyxxList =cbfAndJtcyxxList;
        }, function (err) {
            delAjaxErrorMsg(err);
        }, false);

        fbfcbfxx.tabQlxx=tabQlxxList[0];

        var fbfcbfTpl = fbfcbfxxTpL.innerHTML, fbfcbfView = $tab.find("#fbfcbfxx")[0];
        //渲染数据
        laytpl(fbfcbfTpl).render(fbfcbfxx, function (html) {
            fbfcbfView.innerHTML = html;
        });

    }

    //加载地块信息
    function generateDkxx(groupXmidList,$tab,dkxxJson){
        var dkxxTpl = cbdkxxTpL.innerHTML, dkxxView = $tab.find("#cbdkxx")[0];
        //渲染数据
        laytpl(dkxxTpl).render(dkxxJson, function (html) {
            dkxxView.innerHTML = html;
        });
    }

    //加载经营地块信息
    function generateJyDkxx(groupXmidList,$tab,dkxxJson){
        var dkxxTpl = jydkxxTpL.innerHTML, dkxxView = $tab.find("#cbdkxx")[0];
        //渲染数据
        laytpl(dkxxTpl).render(dkxxJson, function (html) {
            dkxxView.innerHTML = html;
        });
    }





    //加载承包信息经营信息(经营权)
    function generateJyxx(xmid,$tab,tabQlxxList,jyxx) {
        //获取流出方信息
        getReturnData("/slym/qlr/listBdcQlr", JSON.stringify({xmid: xmid, qlrlb: 2}), 'POST', function (data) {
            if(data.length ===0){
                data =[];
                data.push({});
            }
            jyxx.lcfxxList = data;
        }, function (err) {
            delAjaxErrorMsg(err);
        }, false);
        //获取经营方信息
        getReturnData("/slym/qlr/listBdcQlr", JSON.stringify({xmid: xmid, qlrlb: 1}), 'POST', function (data) {
            var jyfxxList =[];
            if(data.length >0){
                jyfxxList =data;
            }else{
                var jyfxx ={};
                jyfxxList.push(jyfxx);
            }
            jyxx.jyfxxList =jyfxxList;
        }, function (err) {
            delAjaxErrorMsg(err);
        }, false);
        jyxx.tabQlxx=tabQlxxList[0];
        //农用地经营权
        var nydjyqxxTpl = nydjyqTpl.innerHTML, nydjyqView = $tab.find("#fbfcbfxx")[0];
        //渲染数据
        laytpl(nydjyqxxTpl).render(jyxx, function (html) {
            nydjyqView.innerHTML = html;
        });
        
    }

    //保存页面数据
    function saveJyqData(){

        //保存不动产项目信息
        saveXmxx();

        //保存收件材料
         saveSjcl();

        // 保存不动产权利信息
        saveQlxx();

        // 保存权利人和家庭成员
        saveQlrAndJtcy();

    }

    //保存不动产项目信息
    function saveXmxx(){
        for (var i = 1; i <= $('.layui-tab-item').length; i++) {
            var tabXmid = $('.layui-tab-item:nth-child(' + i + ')').data('xmid');
            var tabXmidList =[];
            qlxx.forEach(function (v) {
                if (v.bdcXm.xmid == tabXmid) {
                    tabXmidList =v.groupXmidList;
                }
            });
            if(tabXmidList.length ===0){
                continue;
            }
            //获取需要更新的内容
            var bdcXmData={};
            //获取共有项目信息
            var bdcXmCommonArray =$(".bdcxmCommon");
            if(bdcXmCommonArray.length >0) {
                bdcXmCommonArray.serializeArray().forEach(function (item, index) {
                    bdcXmData[item.name] = item.value;
                });
            }
            //获取当前tab项目信息
            var bdcXmArray = $('.layui-tab-item:nth-child(' + i + ') .bdcxm');
            if (bdcXmArray !== null && bdcXmArray.length > 0) {
                bdcXmArray.serializeArray().forEach(function (item, index) {
                    bdcXmData[item.name] = item.value;
                });
            }

            //获取表格中项目信息,如果表格项目信息允许编辑，循环保存项目信息,否则一起保存
            var xmArrData =$(".layui-tab-item:nth-child(" + i + ") #dkxxTable").find(".table-bdcxm").serializeArray();
            if(xmArrData != null &&xmArrData.length >0) {
                $(".layui-tab-item:nth-child(" + i + ") #dkxxTable").each(function () {
                    $(this).find("tr").each(function(index,value){
                        if($(value).find(".table-bdcxm").length > 0){
                            var bdcxmxx = {};
                           $(value).find(".table-bdcxm").serializeArray().forEach(function (item, i) {
                               bdcxmxx[item.name] = item.value;
                            });
                           $.extend(bdcxmxx, bdcXmData);
                            console.info(bdcxmxx);
                           if(isNotBlank(bdcxmxx)){
                               bdcxmxx.xmid = $(value).find("#table-xmid").val();
                               getReturnData("/slym/xm", JSON.stringify(bdcxmxx), 'PATCH', function (data) {
                               }, function (err) {
                                   delAjaxErrorMsg(err);
                               }, false);
                           }
                        }
                    });
                });
            }else if(isNotBlank(bdcXmData)) {
                var bdcDjxxUpdateQO = {};
                var whereMap = {};
                whereMap.gzlslid = processInsId;
                whereMap.xmids = tabXmidList;
                bdcDjxxUpdateQO.whereMap = whereMap;
                bdcDjxxUpdateQO.jsonStr = JSON.stringify(bdcXmData);
                getReturnData("/slym/xm/updateBatchBdcXm", JSON.stringify(bdcDjxxUpdateQO), 'PATCH', function (data) {
                }, function (err) {
                    throw err;
                }, false);
            }
        }
    }

    //保存权利人和家庭成员
    function saveQlrAndJtcy(){
        var qlrAndJtcyList =[];
        $(".bdc-qlr-info").each(function (index,item) {
            var cbfAndJtcy ={};
            var $formadd =$(this);
            var cbf ={};
            var cbfArray =$formadd.find(".bdc-qlr");
            cbfArray.serializeArray().forEach(function (item, index) {
                cbf[item.name] = item.value;
            });
            // 承包方名称可能是disabled状态 需要手动获取一下值
            var cbfmc = $(item).find("#cbfmc").val()
            cbf.qlrmc = cbfmc;
            if(isNotBlank(cbf.qlrmc)) {
                var getQlrid = $formadd.find("#qlrid").val();
                var getXmid = $formadd.parents(".layui-tab-item").data('xmid');
                cbf.qlrid =getQlrid;
                cbf.xmid =getXmid;
                var jtcyList = [];
                $formadd.find(".jtcy-table tbody tr").each(function () {
                    var jtcy = {};
                    var jtcyArray = $(this).find(".jtcy");
                    jtcyArray.serializeArray().forEach(function (item, index) {
                        jtcy[item.name] = item.value;
                    });
                    var jtcymc = $(this).find("input[name=jtcymc]").val();
                    if (isNotBlank(jtcymc)) {
                        var getJtcyid = $(this).find("#jtcyid").val();
                        var hkbbm = $(this).find("#hkbbm").val();
                        jtcy.jtcyid = getJtcyid;
                        jtcy.hkbbm = hkbbm;
                        jtcyList.push(jtcy);
                    }

                });

                //判断承包方家庭成员是否存在户主,不存在需要自动将户主信息添加到家庭成员
                if(jtcyList.length ===0 &&$formadd.hasClass("cbf-info")){
                    var jtcy = {};
                    jtcy.jtcymc=cbf.qlrmc;
                    jtcy.jtcyzjh=cbf.zjh;
                    jtcy.yhzgx="02";
                    jtcyList.push(jtcy);
                }
                cbfAndJtcy.qlr = cbf;
                cbfAndJtcy.jtcyList = jtcyList;
                qlrAndJtcyList.push(cbfAndJtcy);
            }
        });

        if(qlrAndJtcyList.length >0){
            console.log(qlrAndJtcyList);
            getReturnData("/rest/v1.0/jtcy/qlrAndjtcy/"+processInsId,JSON.stringify(qlrAndJtcyList),"PATCH",function (data) {


                
            },function (error) {
                delAjaxErrorMsg(error);
            },false)
        }

    }

    //保存权利信息
    function saveQlxx(){
        for (var i = 1; i <= $('.layui-tab-item').length; i++) {
            var tabXmid = $('.layui-tab-item:nth-child(' + i + ')').data('xmid');
            var tabClassName = $('.layui-tab-item:nth-child(' + i + ')').data('classname');
            var tabXmidList =[];
            qlxx.forEach(function (v) {
                if (v.bdcXm.xmid == tabXmid) {
                    tabXmidList =v.groupXmidList;
                }
            });
            if(tabXmidList.length ===0){
                continue;
            }
            //获取需要更新的内容
            var bdcQlData={};
            //获取当前tab权利信息
            var bdcQlArray = $('.layui-tab-item:nth-child(' + i + ') .qlxx');
            bdcQlArray.serializeArray().forEach(function (item, index) {
                bdcQlData[item.name] = item.value;
            });

            //获取表格中权利信息,如果表格权利信息允许编辑，循环保存权利信息,否则一起保存
            var qlArrData =$(".layui-tab-item:nth-child(" + i + ") #dkxxTable").find(".table-qlxx").serializeArray();
            if(qlArrData != null &&qlArrData.length >0) {
                $(".layui-tab-item:nth-child(" + i + ") #dkxxTable").each(function () {
                    $(this).find("tr").each(function(index,value){
                        if($(value).find(".table-qlxx").length > 0){
                            var qlxx = {};
                            $(value).find(".table-qlxx").serializeArray().forEach(function (item, i) {
                                qlxx[item.name] = item.value;
                            });
                            $.extend(qlxx, bdcQlData);
                            console.info(qlxx);
                            if(isNotBlank(qlxx)){
                                qlxx.qlid = $(value).find("#table-qlid").val();
                                getReturnData("/slym/ql?className=" + tabClassName, JSON.stringify(qlxx), 'PATCH', function (data) {
                                }, function (err) {
                                    throw err;
                                }, false);
                            }
                        }
                    });
                });
            }else if(isNotBlank(bdcQlData)) {
                // if(isNotBlank(tabClassName) &&tabClassName.indexOf("BdcNydjyqDO") >-1){
                //     //经营权起止时间要在承包起止时间范围内
                //     var jyqssj =bdcQlData.cbqssj;
                //     var jyjssj =bdcQlData.cbjssj;
                //     //承包起止时间
                //     var cbqssj =$('.layui-tab-item:nth-child(' + i + ')').find("#cbqssj").val();
                //     var cbjssj =$('.layui-tab-item:nth-child(' + i + ')').find("#cbjssj").val();
                //     if(jyqssj < cbqssj ||jyjssj >cbjssj){
                //         throw new Error("经营起止时间不在承包期限内，请检查！");
                //     }
                // }
                var bdcDjxxUpdateQO = {};
                var whereMap = {};
                whereMap.gzlslid = processInsId;
                whereMap.xmids = tabXmidList;
                if (zxlc === "true") {
                    whereMap.sfyql = true;
                }
                bdcDjxxUpdateQO.className = tabClassName;
                bdcDjxxUpdateQO.whereMap = whereMap;
                bdcDjxxUpdateQO.jsonStr = JSON.stringify(bdcQlData);
                getReturnData("/slym/ql/updateBatchBdcQl", JSON.stringify(bdcDjxxUpdateQO), 'PATCH', function (data) {

                }, function (err) {
                    throw err;
                }, false);
            }
        }

    }

    //删除家庭成员
    function deleteBdcJtcy($elem){
        var $tr=$elem.parents('tr');
        var jtcyid =$tr.find("input[name=jtcyid]").val();
        var url = getContextPath() + "/rest/v1.0/jtcy?jtcyid=" + jtcyid+"&gzlslid="+processInsId;
        var deleteIndex = layer.open({
            type: 1,
            skin: 'bdc-small-tips',
            title: '确认删除',
            area: ['320px'],
            content: '是否确认删除？',
            btn: ['确定', '取消'],
            btnAlign: 'c',
            yes: function () {
                addModel();
                //确定操作
                if (isNotBlank(jtcyid)) {
                    $.ajax({
                        url: url,
                        type: 'DELETE',
                        success: function (data) {
                            removeModal();
                            ityzl_SHOW_SUCCESS_LAYER("删除成功");
                            $tr.remove();

                        }, error: function (xhr, status, error) {
                            delAjaxErrorMsg(xhr)
                        }
                    });
                } else {
                    removeModal();
                    ityzl_SHOW_SUCCESS_LAYER("删除成功");
                    $tr.remove();
                }
                layer.close(deleteIndex);
            },
            btn2: function () {
                //取消
                layer.close(deleteIndex);
            }
        });

    }

    //删除承包方
    function deleteCbf($elem){
        var $cbfForm=$elem.parents('.bdc-form-add');
        var qlrid =$cbfForm.find("input[name=qlrid]").val();
        var url = getContextPath() + "/rest/v1.0/jtcy/qlrAndjtcy?qlrid=" + qlrid+"&gzlslid="+processInsId;
        var deleteIndex = layer.open({
            type: 1,
            skin: 'bdc-small-tips',
            title: '确认删除',
            area: ['320px'],
            content: '是否确认删除？',
            btn: ['确定', '取消'],
            btnAlign: 'c',
            yes: function () {
                addModel();
                //确定操作
                if (isNotBlank(qlrid)) {
                    $.ajax({
                        url: url,
                        type: 'DELETE',
                        success: function (data) {
                            removeModal();
                            ityzl_SHOW_SUCCESS_LAYER("删除成功");
                            $cbfForm.remove();
                            var $item = $('#cbfxx .bdc-form-btn p');
                            $.each($item, function (index, item) {
                                $(item).html(index+1);
                            })

                        }, error: function (xhr, status, error) {
                            delAjaxErrorMsg(xhr)
                        }
                    });
                } else {
                    removeModal();
                    ityzl_SHOW_SUCCESS_LAYER("删除成功");
                    $cbfForm.remove();
                    var $item = $('#cbfxx .bdc-form-btn p');
                    $.each($item, function (index, item) {
                        $(item).html(index+1);
                    })
                }
                layer.close(deleteIndex);
            },
            btn2: function () {
                //取消
                layer.close(deleteIndex);
            }
        });

    }

    //查看权利信息
    window.showQlxx =function(xmid, qllx, bdcdyfwlx, sfyql) {
        if (xmid !== "" && xmid !== null) {
            qllx = parseInt(qllx);
            bdcdyfwlx = parseInt(bdcdyfwlx);
            var qllxym = getQlxxYm(qllx, bdcdyfwlx);
            var url;
            //展示原权利，不可编辑
            if (sfyql) {
                url = "/realestate-register-ui/view/qlxx/" + qllxym + ".html?xmid=" + xmid + "&formStateId=" + formStateId + "&readOnly=true" + "&isCrossOri=false";
            } else {
                url = "/realestate-register-ui/view/qlxx/" + qllxym + ".html?xmid=" + xmid + "&formStateId=" + formStateId + "&readOnly=" + readOnly + "&isCrossOri=false";
            }
            var index = layer.open({
                type: 2,
                area: ['1150px', '600px'],
                fixed: false, //不固定
                title: "权利信息",
                maxmin: true,
                content: url,
                btnAlign: "c",
                zIndex: 2147483647,
                success: function () {
                }
                ,cancel: function () {
                    reloadQlxxTab(gzlslid,zxlc,zdList);
                }

            });
            layer.full(index);
        } else {
            ityzl_SHOW_INFO_LAYER("无原权利信息")
        }
    }

});

//加载收件材料信息
function loadSjcl() {
    getReturnData("/slym/sjcl/list/pl", {processInsId: processInsId}, 'GET', function (data) {
        var json = {
            bdcSlSjclDOList: data,
            zd: zdList
        };
        if (data !== null && data.length > 0) {
            for (var i = 0; i < data.length; i++) {
                sjclidLists.push(data[i].sjclid);
            }
            sjclNumber = data.length;
        }
        var tpl = sjclTpl.innerHTML,
            view = document.getElementById('sjclxx');
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });

        form.render("checkbox")
    }, function (err) {
        console.log(err);
    },false);
    
}


// 证书预览
function createZs(xmid, bdcdyh) {
    if (!isNotBlank(xmid)) {
        xmid = "";
    }
    if (!isNotBlank(bdcdyh)) {
        bdcdyh = "";
    }
    var url = "/realestate-register-ui/rest/v1.0/bdcZs?processInsId=" + processInsId + "&xmid=" + xmid + "&bdcdyh=" + bdcdyh + "&zsmodel=" + zsmodel + "&zsyl=true&time=" + new Date().getTime();
    layerCommonOpenFull(url, "证书预览", $(window).width() + "px", $(window).height() + "px");
}

//添加身份证号验证
function addSfzYz(zjzl, $zjh){
    var zjhVerify =  $zjh.attr("lay-verify");
    if(zjzl == '1'){
        if (isNotBlank(zjhVerify)) {
            if (zjhVerify.indexOf("identitynew") < 0) {
                $zjh.attr("lay-verify", zjhVerify + "|identitynew");
            }
        } else {
            $zjh.attr("lay-verify", "identitynew");
        }
    }else {
        //移除证件号验证
        if (isNotBlank(zjhVerify) && zjhVerify.indexOf("identitynew") > -1) {
            $zjh.attr("lay-verify", zjhVerify.replace("identitynew", ""));
        }
        if (isNotBlank(zjhVerify) && zjhVerify.indexOf("zjhlength") < 0) {
            $zjh.attr("lay-verify", zjhVerify + "|zjhlength");
        } {
            $zjh.attr("lay-verify", "zjhlength");
        }
    }
}

//页面渲染
function renderJyqForm() {
    //初始化共有方式
    dealGyfs();

    //表单渲染
    form.render();

    //渲染日期
    renderDate(form,"");

    //权限控制
    getStateById(readOnly, formStateId, "slymjyq");

    //重新渲染select
    form.render("select");

    //不可编辑加锁
    disabledAddFa();
    
}

//处理共有方式，flag=check,进行验证提示,否则直接修改页面数据
function dealGyfs(flag,checkMsg){
    //承包方默认共同共有,经营方按权利人个数判断
    for (var i = 1; i <= $('.layui-tab-item').length; i++) {
        var $tab =$('.layui-tab-item:nth-child(' + i + ')');

        var qlrsize =$tab.find(".bdc-qlr-info").length;
        $tab.find(".bdc-qlr-info").find("select[name=gyfs]").each(function () {
            var gyfsVal =$(this).val();
            if(qlrsize <= 1){
                //默认单独所有
                if(gyfsVal !=="0"){
                    if(flag ==="check"){
                        checkMsg ="共有方式不正确,单个权利人默认单独所有！";
                        $(this).addClass('layui-form-danger');
                    }else {
                        $(this).val("0");
                    }
                }
            }else if(qlrsize >1){
                //默认共同共有
                if(gyfsVal ==="0" ||!isNotBlank(gyfsVal)){
                    if(flag ==="check"){
                        checkMsg ="共有方式不正确,多个权利人不能单独所有！";
                        $(this).addClass('layui-form-danger');
                    }else {
                        $(this).val("1");
                    }
                }
            }
        });
        if(!isNotBlank(checkMsg) &&qlrsize ===2){
            //2个权利人默认共有方式一致
            var $gyfs1 =$($tab.find(".bdc-qlr-info").find("select[name=gyfs]")[0]);
            var $gyfs2 =$($tab.find(".bdc-qlr-info").find("select[name=gyfs]")[1]);
            var gyfs1Val =$gyfs1.val();
            var gyfs2Val =$gyfs2.val();
            if(gyfs1Val !== gyfs2Val){
                if(flag ==="check"){
                    checkMsg ="共有方式不正确,两个权利人共有方式不一致！";
                    $gyfs1.addClass('layui-form-danger');
                    $gyfs2.addClass('layui-form-danger');
                }else {
                    $gyfs1.val("1");
                    $gyfs2.val("1");
                }
            }

        }
    }
    if(isNotBlank(checkMsg)){
        return checkMsg;
    }
}

//查看原承包方信息
function ycbfxxView(xmid){
    //获取原项目
    getReturnData("/slym/ql/queryYxmid",{xmid:xmid},"GET",function (yxmid) {
        if(!isNotBlank(yxmid)){
            ityzl_SHOW_WARN_LAYER("未查询到原项目信息");
            return false;
        }

        var url =getContextPath()+"/view/slym/cbfxx.html?xmid="+yxmid;
        layer.open({
            type: 2,
            area: ['960px', '575px'],
            fixed: false, //不固定
            title: "原承包方信息",
            content: url,
            btnAlign: "c"
        });
        
    },function (error) {
        delAjaxErrorMsg(error);
    });





}

//提交前验证处理数据,返回验证信息
function checkCommitData(){
    var checkMsg="";
    //家庭成员名称不为空,验证家庭成员证件号
    for (var i = 0; i < $(".jtcy-table tbody tr").length; i++) {
        var $jtcyTr =$($(".jtcy-table tbody tr")[i]);
        var jtcymc = $jtcyTr.find("input[name=jtcymc]").val();
        if(isNotBlank(jtcymc)) {
            var $jtcyzjh =$jtcyTr.find("input[name=jtcyzjh]");
            var jtcyzjh = $jtcyzjh.val();
            var msg = checkSfzZjh(jtcyzjh);
            if (isNotBlank(msg)) {
                checkMsg ="家庭成员"+msg;
                $jtcyzjh.addClass('layui-form-danger');
                break;
            }
        }
    }

    //验证共有方式
    if(!isNotBlank(checkMsg)) {
        checkMsg = dealGyfs("check", checkMsg);
    }
    return checkMsg;


}

