var slbh;
var $,laytpl,form;
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function() {
    form = layui.form;
    $ = layui.jquery;
    var element = layui.element;
    laytpl = layui.laytpl;
    var layer = layui.layer;

    // 监听页面上申请人证件种类修改
    form.on('select(zjzl)', function (data) {
        var $zjh = $(data.elem).parents("td").next().find("input");
        addSfzYz(data.value, $zjh);
        form.render();
    });
});

//新增申请人
function addQlr(xmid) {
    var url = getContextPath() + "/nantong/ycsl/sqr.html?processInsId=" + processInsId + "&formStateId="
        + formStateId +"&xmid="+xmid;
    layer.open({
        type: 2,
        area: ['960px', '575px'],
        fixed: false, //不固定
        title: "新增申请人",
        content: url,
        btnAlign: "c"
    });
    form.render();
    resetSelectDisabledCss();
}

//权利人详情展示
function showQlr(sqrid, xmid, qlrlb, elem) {
    addModel();
    var url = getContextPath() + "/nantong/ycsl/sqr.html?processInsId=" + processInsId + "&sqrid=" + sqrid  + "&xmid=" + xmid + "&formStateId=" + formStateId + "&readOnly=" + readOnly;
    layer.open({
        type: 2,
        area: ['960px', '575px'],
        fixed: false, //不固定
        title: "申请人详细信息",
        content: url,
        btnAlign: "c"
    });
    removeModal();
    form.render();
    renderDate(form, "");
    disabledAddFa();
}

//删除权利人
function deleteSqr(sqrid, sxh, qlrlb) {
    var xmid = "";
    var url = "/slym/sqr/sqrxx?gzlslid=" + processInsId + "&sqrid=" + sqrid ;
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
            getReturnData(url, {}, 'DELETE', function (data) {
                removeModal();
                loadYcslxx();
                ityzl_SHOW_SUCCESS_LAYER("删除成功");
            }, function (err) {
                delAjaxErrorMsg(err)
            });
            layer.close(deleteIndex);
        },
        btn2: function () {
            //取消
            layer.close(deleteIndex);
        }
    });
}

//权利人顺序号修改
function changeSqrSxh(sqrid, czlx) {
    getReturnData("/slym/sqr/sxh", {
        sqrid: sqrid,
        czlx: czlx,
        gzlslid: processInsId
    }, 'GET', function (data) {
        if (data > 0) {
            ityzl_SHOW_SUCCESS_LAYER("调整成功");
            loadYcslxx();
        }
    }, function (err) {
        delAjaxErrorMsg(err);
    });
}

//关闭panel
function cancelEdit() {
    layer.closeAll();
}

// 加载权力信息与不动产单元信息统一入口
function generateQlxxAndBdcdyxx(data){
    // 加载权利信息
    generateQlxx(data,bdcSlXm);
    // 渲染领证人信息
    generateLzrxx({
        bdcSlXm: bdcSlXm,
        zd : zdList
    });

    // 加载不动产单元信息
    generateBdcdyxx({
        bdcSlXm : bdcSlXm,
        zd : zdList
    });

}

// 加载不动产单元信息
function generateBdcdyxx(data){
    var tpl = bdcdyxxTpl.innerHTML, view = document.getElementById('bdcdyxx');
    laytpl(tpl).render(data, function (html) {
        view.innerHTML = html;
    });
    form.render();
}

// 渲染领证人信息
function generateLzrxx(data){
    getReturnData("/slym/lzr/lzrxx", {xmid: data.bdcSlXm.xmid}, 'GET', function (lzrList) {
        var renderData = {
            lzrList : lzrList,
            zd : zdList
        }
        var tpl = lzxxTpl.innerHTML, view = document.getElementById('lzxx');
        laytpl(tpl).render(renderData, function (html) {
            view.innerHTML = html;
        });
        form.render();
    }, function (err) {
        console.log(err);
    },false);
}

/**
 * 初始化加载权利信息模块数据信息
 * @param ycslYmxxDTO 一窗受理页面信息
 * @param bdcSlXm 不动产受理项目信息
 */
function generateQlxx(ycslYmxxDTO, bdcSlXm){
    if(isNotBlank(ycslYmxxDTO)){
        // 根据登记小类获取登记原因
        var djyyList = tabModel.loadDjyy(bdcSlXm.djxl);
        if(isNotBlank(djyyList)){
            // 渲染权利信息
            var data = {
                zd : zdList,
                bdcSlJyxx : ycslYmxxDTO.bdcSlJyxxDO,
                bdcSlFwxx : ycslYmxxDTO.bdcSlFwxxDO,
                bdcSlXm : bdcSlXm,
                djyyList :  djyyList
            };
            var tpl = qlxxTpl.innerHTML, view = document.getElementById('qlxx');
            laytpl(tpl).render(data, function (html) {
                view.innerHTML = html;
            });
            // 加载附记与权利其他状况信息
            qlxxModel.loadFjAndQlqtzk(bdcSlXm.xmid);
            // 添加权利信息页面表单监听事件，同步权利信息至交易与税收页面
            qlxxModel.syncFwxxData();
        }
    }
}

// 保存不动产受理房屋信息
function saveBdcSlFwxx(className){
    var fwxxData = new Object();
    if($(className).length != 0){
        fwxxData = $(className).serializeObject();
        if(qlxxModel.checkSzcAndZcs(fwxxData.szc,fwxxData.zcs)){
            $.ajax({
                url: getContextPath() + "/ycsl/fwxx",
                type: 'PATCH',
                dataType: 'json',
                async: false,
                contentType: "application/json",
                data: JSON.stringify(fwxxData),
                success: function (data) {
                    // ityzl_SHOW_SUCCESS_LAYER("提交成功");
                }, error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr)
                }
            });
        }
    }
}

// 保存不动产受理项目信息
function saveBdcSlXm(className) {
    var bdcSlXmData = new Object();
    if($(className).length != 0){
        bdcSlXmData = $(className).serializeObject();
        $.ajax({
            url: getContextPath() + "/ycsl/xmxx",
            type: 'PATCH',
            dataType: 'json',
            async: false,
            contentType: "application/json",
            data: JSON.stringify(bdcSlXmData),
            success: function (data) {
                // ityzl_SHOW_SUCCESS_LAYER("提交成功");
            }, error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        });
    }
}

// 保存不动产受理交易信息
function saveBdcSlJyxx(className) {
    var bdcSlJyxxData = new Object();
    if($(className).length != 0){
        bdcSlJyxxData = $(className).serializeObject();
        $.ajax({
            url: getContextPath() + "/ycsl/htxx",
            type: 'PATCH',
            dataType: 'json',
            async: false,
            contentType: "application/json",
            data: JSON.stringify(bdcSlJyxxData),
            success: function (data) {
                // ityzl_SHOW_SUCCESS_LAYER("提交成功");
            }, error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        });
    }
}

// 保存不动产领证人信息
function saveBdcSlLzrxx(className){
    var lzrxxList = new Array();
    var xmid = bdcSlXm.xmid;
    if($(className).length != 0){
        var data = $(className).serializeArray();
        var bdcSlLzrSingleData = {};
        for (var j = 0; j < data.length; j++) {
            var name = data[j].name;
            bdcSlLzrSingleData[name] = data[j].value;
            if ((j + 1 < data.length && data[j + 1].name === 'lzrid') || j + 1 == data.length) {
                bdcSlLzrSingleData["xmid"] = xmid;
                lzrxxList.push(bdcSlLzrSingleData);
                bdcSlLzrSingleData = {};
            }
        }
    }

    // 踢掉没有填写名称的领证人
    var lzrList = new Array();
    for(var i=0;i<lzrxxList.length;i++){
        if(lzrxxList[i].lzrmc !=""){
            lzrList.push(lzrxxList[i]);
        }
    }
    if(lzrList.length > 0){
        $.ajax({
            url: getContextPath() + "/slym/lzr/lzrxx",
            type: 'PATCH',
            dataType: 'json',
            async: false,
            contentType: "application/json",
            data: JSON.stringify(lzrList),
            success: function (data) {
                generateLzrxx({ bdcSlXm: bdcSlXm, zd : zdList });
            }, error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        });
    }
}

var qlxxModel ={
    // 初始化附记和权利其他状况按钮事件
    initEvent : (function(){
        var resetData = function($btn){
            var $textarea = $btn.parent(".layui-input-inline").find("textarea");
            console.info($textarea);
            if($textarea.length >0){
                $textarea.val("");
                //清空原有数据并保存受理页面
                $('#saveData').click();
            }
        }
        return function(){
            //重新生成权利其他状况，置空权利数据
            $(".bdc-form-div").on('click','.reset', function () {
                resetData($(this));
            });
            // 领证人证件类型添加初始化事件
            form.on('select(lzrzjhFilter)', function (data) {
                var $zjh = $(data.elem).parents(".layui-inline").next().find("input");
                addSfzYz(data.value, $zjh);
            });
        }
    })(),
    // 所在层与总层数验证
    checkSzcAndZcs : function(szc, zcs){
        if (szc === "0") {
            throw error = new Error("所在层不能为0");
            return false;
        }
        if (isNotBlank(szc) && isNotBlank(zcs) && parseInt(szc) > parseInt(zcs)) {
            throw error = new Error("所在层不能大于总层数");
            return false;
        }
        return true;
    },
    // 加载权利其他状况和附记内容
    loadFjAndQlqtzk : (function(){
        var queryQlqtzkFjMb = function(xmid, $qlqtzkfj, mode) {
            getReturnData("/slym/ql/queryQlqtzkFjMb", {xmid: xmid, mode: mode}, "GET", function (data) {
                if (isNotBlank(data)) {
                    //赋值权利其他状况或者附记
                    $qlqtzkfj.val(data);
                }
            }, function (error) {
                delAjaxErrorMsg(error);
            })
        }
        return function (xmid) {
            //定位权利其他状况元素
            var $qlqtzk = $("#qlxx").find("textarea[name='qlqtzk']");
            //如果权利其他状况没有值,加载模板数据
            if ($qlqtzk.length > 0 &&!isNotBlank($qlqtzk.val())) {
                //加载权利其他状况
                queryQlqtzkFjMb(xmid, $qlqtzk, "2");
            }
            //定位附记元素
            var $fj = $("#qlxx").find("textarea[name='fj']");
            //如果附记没有值,加载模板数据
            if ($fj.length > 0 &&!isNotBlank($fj.val())) {
                //加载附记
                queryQlqtzkFjMb(xmid, $fj, "3");
            }
        }
    })(xmid),
    // 同步权利信息模块相关数据至交易与税收模块中
    syncFwxxData : (function(){
        // 计算土地权利面积
        var sumTdqlMj = function($fttdmj, $dytdmj){
            var fttdmj = $fttdmj.val();
            var dytdmj = $dytdmj.val();
            var fttdmjX = 0;
            var dytdmjX = 0;
            if (fttdmj != "") {
                fttdmjX = parseFloat(fttdmj) * 100;
            }
            if (dytdmj != "") {
                dytdmjX = parseFloat(dytdmj) * 100;
            }
            var $syqmj =$("input[name='tdsyqmj']");
            if($syqmj.length >0) {
                $syqmj.val((fttdmjX + dytdmjX) / 100);
            }
        }
        return function(){
            // 监听房屋类型，同步更新交易与税收页面的房屋类型
            form.on('select(fwlx)',function (data) {
                $("#fwxx").find("select[name='fwlx']").val(data.value);
                form.render("select");
                disabledAddFa();
                resetSelectDisabledCss();
            });
            // 监听房屋性质，同步更新交易与税收页面的访问房屋性质
            form.on('select(fwxz)',function (data) {
                $("#fwxx").find("select[name='fwxz']").val(data.value);
                form.render("select");
                disabledAddFa();
                resetSelectDisabledCss();
            });
            // 分摊土地面积和独用土地面积监听
            var $fttdmj = $("input[name='fttdmj']");
            var $dytdmj = $("input[name='dytdmj']");
            if ($fttdmj.length > 0 && $dytdmj.length > 0) {
                $fttdmj.on('change', function () {
                    sumTdqlMj($fttdmj, $dytdmj);
                });
                $dytdmj.on('change', function () {
                    sumTdqlMj($fttdmj, $dytdmj);
                });
            }
            // 验证所在层与总层数
        }
    })(),
}
// Tab模块，通用方法信息
var tabModel = {
    // 通过不动产登记小类获取当前登记小类相关的登记原因信息
    loadDjyy : function(djxl){
        if(isNotBlank(djxl)){
            var djxlList;
            getReturnData("/slym/xm/queryDjxlDjyy", {djxl: djxl}, 'GET', function (data) {
                if (isNotBlank(data)) {
                    djxlList = data;
                }
            }, function (err) {
                console.log(err);
            },false);
            return djxlList;
        }
    },
}

// 页面上方保存按钮 保存权利人
function saveSqr(){
    var bdcSlSqrData = [];
    var sqrlist = [];
    var qlrnum = 0;
    var ywrnum = 0;
    var gyfs = "";
    var ywrgyfs = "";
    if($(".sqr").length != 0){
        bdcSlSqrData = $(".sqr").serializeArray();
        var bdcSlSqrSingleData = {};
        for (var j = 0; j < bdcSlSqrData.length; j++) {
            var name = bdcSlSqrData[j].name;
            bdcSlSqrSingleData[name] = bdcSlSqrData[j].value;
            var obj = bdcSlSqrData[j];
            // 以qlrid为每一组权利人的起始数据，作为分割依据
            if ((j + 1 < bdcSlSqrData.length && bdcSlSqrData[j + 1].name === 'sqrid') || j + 1 == bdcSlSqrData.length) {
                if (bdcSlSqrSingleData.sqrlb === "1") {
                    qlrnum++;
                    gyfs += bdcSlSqrSingleData.gyfs + ",";
                }
                if (bdcSlSqrSingleData.sqrlb == '2') {
                    ywrnum++;
                    ywrgyfs += bdcSlSqrSingleData.gyfs + ",";
                }

                toUpperClass(bdcSlSqrSingleData, ["zjh", "dlrzjh", "frzjh", "lzrzjh"]);
                sqrlist.push(bdcSlSqrSingleData);
                bdcSlSqrSingleData = {};
            }
        }
    }else{
        // 没有申请人 认为是成功的
        return true;
    }
    //验证权利人共有方式
    if (!checkGyfs(gyfs, qlrnum)) {
        throw err = new Error('权利人共有方式不正确，请检查！');
        return false;
    }

    //验证义务人共有方式
    if (!checkGyfs(ywrgyfs, ywrnum)) {
        throw err = new Error('义务人共有方式不正确，请检查！');
        return false;
    }

    // 验证共有比例
    if (!checkAddGybl(sqrlist)) {
        throw err = new Error('共有比例不正确，请检查！');
    }
    console.log(sqrlist)
    var count = saveAllSqrs(sqrlist);
    // 更新的数量 == 页面的数量 则说明更新成功
    if(bdcSlSqrData.length == count){
        return true;
    }else{
        return false;
    }

}

/**
 * 上方保存按钮 需要保存所有申请人
 * @param sqrList
 */
function saveAllSqrs(sqrList){
    var count = 0;
    for(var i = 0;i<sqrList.length;i++){
        var bdcQlrData = sqrList[i];
        //申请人名称单独处理
        var BdcSlSqrDTO = {};
        var bdcSlSqrDTOList =[];
        BdcSlSqrDTO['BdcSlSqrDO'] =bdcQlrData;
        BdcSlSqrDTO['bdcSlJtcyDOList'] =[];
        bdcSlSqrDTOList.push(BdcSlSqrDTO);
        var url =  getContextPath() + "/slym/sqr/sqrxx?gzlslid=" + processInsId;
        $.ajax({
            url: url,
            type: 'PATCH',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(bdcSlSqrDTOList),
            async: false,
            success: function (data) {
                if (isNotBlank(data)) {
                    count++;
                }
            },
            error: function (e) {
                delAjaxErrorMsg(e);
            }
        });
    }
    return count;
}

/**
 * gyfs 共有方式集合，多个用英文逗号隔开
 * qlrnum 权利人个数
 * xmid 用于判断当前登记小类是否默认共同共有
 * 验证共有方式是否正确：1.一个权利人为单独所有 2.两个权利人共有方式不为单独所有且保持一致 3.三个及三个以上共有方式不为单独所有
 * 返回结果：是否验证通过
 */
function checkGyfs(gyfs, qlrnum,xmid) {
    if (qlrnum === 1) {
        // 如果 传递过XMID 则判断当前项目 的登记小类 是否为 默认共有方式为 GTGY
        if(xmid){
            var checkflag = false;
            getReturnData("/slym/qlr/checkdefaultgyfs", {gyfs: gyfs, xmid: xmid}, "GET", function (data) {
                checkflag = data;
            }, function () {
            }, false);
            if(checkflag){
                return true;
            }
        }
        //一个权利人时共有方式必须为单独所有
        if(!dgGyfsYz){
            return true;
        }
        if (gyfs.indexOf("1") < 0 && gyfs.indexOf("2") < 0 && gyfs.indexOf("3") < 0) {
            return true;
        }
    } else if (qlrnum === 2) {
        //两个权利人时共有方式不能存在单独所有，且两个共有方式保持一致
        var gyfsArr = gyfs.split(",");
        if (gyfsArr.length > 1 && gyfsArr[0] === gyfsArr[1] && gyfsArr[0] !== "0") {
            return true;
        }
    } else {
        //多个权利人时共有方式不能存在单独所有
        if (gyfs.indexOf("0") < 0) {
            return true;
        }
    }
    return false;
}

/**
 * qlrList 权利人集合
 * 验证共有比例之和为1：支持百分数，小数，分数（多个共有比例为统一类型）
 * 返回结果：是否验证通过
 */
function checkAddGybl(qlrList) {
    var sumgybl = 0;
    var gyblList = [];
    var qlrs = [];
    //分母相乘
    var fenmumultiply = 1;
    //分子之和
    var fenzicount = 0;
    for (var k in qlrList) {
        if (qlrList[k].qlrlb === "1" ||(qlrList[k].sqrlb === "1")) {
            qlrs.push(qlrList[k]);
        }
    }
    if (isNotBlank(qlrs)) {
        for (var k in qlrs) {
            if (qlrs[k].gyfs === "2") {
                if (isNotBlank(qlrs[k].qlbl)) {
                    var qlbl = qlrs[k].qlbl;
                    if (isNaN(qlbl)) {
                        if (qlbl.indexOf("%") > -1) {
                            gyblList.push(parseFloat(qlbl.substr(0, qlbl.length - 1)));
                        } else if (qlbl.indexOf("/") > -1) {
                            var fenmu = parseInt(qlbl.split("/")[1]);
                            fenmumultiply = fenmumultiply * fenmu;
                        }
                    } else {
                        gyblList.push(parseFloat(qlrs[k].qlbl) * 100);

                    }
                } else {
                    //按份共有共有比例必须有值
                    return false;
                }
            } else if (isNotBlank(qlrs[k].qlbl)) {
                throw err = new Error('非按份共有请勿填写共有比例！');
            }

        }
    } else {
        //无权利人直接通过
        return true;
    }
    if (gyblList.length > 0) {
        for (var i in gyblList) {
            sumgybl += formatFloat(gyblList[i]);
        }
        if (formatFloat(sumgybl) === 100) {
            return true;

        }
    } else if (fenmumultiply > 1) {
        for (var k in qlrList) {
            if (qlrList[k].qlrlb === "1" && isNotBlank(qlrList[k].qlbl)) {
                var qlbl = qlrList[k].qlbl;
                if (qlbl.indexOf("/") > -1) {
                    var fenmu = parseInt(qlbl.split("/")[1]);
                    var fenzi = parseInt(qlbl.split("/")[0]);
                    fenzicount += fenmumultiply / fenmu * fenzi;
                }
            }
        }
        if (fenmumultiply === fenzicount) {
            return true;

        }
    } else {
        return true;

    }
    return false;
}