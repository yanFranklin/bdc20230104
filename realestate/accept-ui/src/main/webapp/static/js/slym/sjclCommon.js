/**
 * @author "<a href="mailto:liaoxiang@gtmap.cn>liaoxiang</a>"
 * @version 1.0, 2019/11/18
 * @description 收件材料操作公共JS
 */
var xmlx = "";
//新增收件材料
function addSjcl(elem) {
    var appendEl = $("#addSjclTable");
    var trELArray = $(appendEl).find("tr");
    var djxl = "";
    if (trELArray.length > 1) {
        $(appendEl).find("tr[class='bdc-table-none']").remove();
        //移除后长度减一
        trELArray = $(appendEl).find("tr");
    }
    sjclNumber = trELArray.length;
    if (isNotBlank(elem)) {
        djxl = $(elem).parents(".layui-tab").find("input[name='djxl']").val();
    }
    //根据登记小类，工作流实例id，登记原因获取所有的收件材料配置，作为下拉选项
    var sjclpzList = [];
    if (addSjclFromDjyypz) {
        sjclpzList = getSjclPzList(processInsId, djxl)
    }
    var addSjclxx = {
        gzlslid: processInsId,
        sjclNumber: sjclNumber,
        djxl: djxl,
        sjclpzList: sjclpzList,
        zd: zdList
    };
    var clmcfilter = "select(clmc" + djxl + ")";
    // var clmcfilter = "select(clmc)";
    form.on(clmcfilter, function (data) {
        clmcSqbmDz(data, sjclpzList);
        changeSjlx(data, sjclpzList);
        form.render('select');
    });

    var getTpl = addSjclTpl.innerHTML;
    laytpl(getTpl).render(addSjclxx, function (html) {
        appendEl.append(html);
        form.render();
    });
    //控制权限
    resetSelectDisabledCss();

    $(appendEl.find('tr:last-child td input')[0]).focus();
    //滚动到新增收件材料栏
    $('html,body').animate({scrollTop: $(appendEl.find('tr:last-child td input')[2]).offset().top}, 800);
    //默认赋值
    $('.bdc-jt-table').find('tr:last').find('input[name="fs"]').val("1");
    $('.bdc-jt-table').find('tr:last').find('input[name="ys"]').val("1");
    $('.bdc-jt-table').find('tr:last').find('select[name="sjlx"]').find('option[value="1"]').attr("selected","selected");
    form.render();
}
// 复制收件材料
function copySjcl(djxl) {
    if(isNullOrEmpty(djxl)){
        djxl = '';
    }
    // 添加遮罩
    addModel();
    // 打开复制收件材料页面
    var url;
    url = '/realestate-accept-ui/changzhou/slym/copySjcl.html?gzlslid=' + processInsId+'&&djxl=' + djxl;
    var copySjclIndex = layer.open({
        type: 2,
        // area: ['960px', '600px'],
        area: ['1150px', '575px'],
        title: '复制收件材料',
        content: url,
        maxmin: true,
        resize: true,
        end: function(){
            loadSjcl();
        },
    });
    removeModal();
}


// 受理页面组合复制收件材料
function copySlymzhSjcl(djxl) {
    // 添加遮罩
    addModel();
    var djxlmc=$("#djxlmc").val();
    // 打开复制收件材料页面
    var url;
    url = '/realestate-accept-ui/changzhou/slym/copySjcl.html?gzlslid=' + processInsId + '&&djxl=' + djxl + '&&clfzlx=slymzh';
    var copySjclIndex = layer.open({
        type: 2,
        // area: ['960px', '600px'],
        area: ['1150px', '575px'],
        title: '复制收件材料',
        content: url,
        maxmin: true,
        resize: true,
        end: function(){
            loadSjcl();
        },
    });
    removeModal();
}

//删除收件材料(单个删除)
function deleteSjcl(sjclid, obj, sfbc) {
    //防止删除之前新增了很多数据没保存，删除后会刷新table丢失
    if (sfbc && sfbc === '1') {
        ityzl_SHOW_WARN_LAYER("必传文件不允许删除");
        return false;
    }
    if (isNotBlank(sjclid)) {
        sjclNumber = sjclNumber - 1;
        var deleteIndex = layer.open({
            type: 1,
            skin: 'bdc-small-tips',
            title: '确认删除',
            area: ['320px'],
            content: '是否确认删除？',
            btn: ['确定', '取消'],
            btnAlign: 'c',
            yes: function () {
                //确定操作
                getReturnData("/slym/sjcl?sjclid=" + sjclid, {
                    processInsId: processInsId,
                    sfcxql: true
                }, 'DELETE', function (data) {
                    //删除收件材料时清空勾选的内容和session
                    sjclids = new Set();
                    sessionStorage.removeItem("yxsjclids");
                    if (scbcsjcl) {
                        saveSjcl()
                    } else {
                        loadSjcl();
                    }
                }, function (err) {
                    delAjaxErrorMsg(err);
                });
                layer.close(deleteIndex);
            },
            btn2: function () {
                //取消
                layer.close(deleteIndex);
            }
        });
    } else {
        $(obj).parent().parent().remove();
    }
}

//收件材料顺序号修改
function changeSjclSxh(sjclid, czlx) {
    if (isNotBlank(sjclid)) {
        getReturnData("/slym/sjcl/sxh", {sjclid: sjclid, czlx: czlx}, 'GET', function (data) {
            if (data > 0) {
                loadSjcl();
            }
        }, function (err) {
            delAjaxErrorMsg(err);
        });
    } else {
        loadSjcl();
    }
}

//删除收件材料批量
function deleteSjclPl(sfbc) {
    //防止删除之前新增了很多数据没保存，删除后会刷新table丢失
    var sjclidsArray = [];
    sjclids.forEach(function (element, sameElement, set) {
        sjclidsArray.push(sameElement)
    });

    if (null != sjclidsArray && sjclidsArray.length > 0) {
        var deleteSjcl = true;
        if (isNotBlank(sfbc) && sfbc) {
            $('td input[name=yxTable]').each(function (index, item) {
                //如果是选中的则添加，否则全部删除
                if (item.checked && $(item).data('sfbc') === 1) {
                    deleteSjcl = false;
                    ityzl_SHOW_WARN_LAYER("存在必传文件不可删除");
                    return false;
                }
            });
        }
        if (deleteSjcl) {
            sjclNumber = sjclNumber - 1;
            var deleteIndex = layer.open({
                type: 1,
                skin: 'bdc-small-tips',
                title: '确认删除',
                area: ['320px'],
                content: '是否确认删除？',
                btn: ['确定', '取消'],
                btnAlign: 'c',
                yes: function () {
                    addModel("正在删除");
                    //确定操作
                    getReturnData("/slym/sjcl/deleteBdcsjclPl?sjclids=" + sjclidsArray, {
                        processInsId: processInsId,
                        sfcxql: true
                    }, 'DELETE', function (data) {
                        removeModal();
                        sjclids.clear();
                        loadSjcl();
                    }, function (err) {
                        delAjaxErrorMsg(err);
                    });

                    layer.close(deleteIndex);
                },
                btn2: function () {
                    //取消
                    layer.close(deleteIndex);
                }
            });
        }
    } else {
        layer.alert("请选择最少一条数据进行删除！", {title: '提示'});
    }

}

//附件上传（单个上传）
function scfj(element) {
    var clmcEl = $(element).parent().parent().find("input[name='clmc']")[0];
    var clmc = $(clmcEl).val();
    var djxl = "";
    var djxlEl = $(element).parent().parent().find("input[name='djxl']");
    if (djxlEl && djxlEl.length > 0) {
        djxl = $(djxlEl[0]).val();
    }
    uploadSjcl(clmc, djxl);

}

/**
 * @param clmc 材料名称
 * @param djxl 登记小类
 *  收件材料上传
 */
function uploadSjcl(clmc, djxl) {
    var sjclArrayPllc = $(".sjcl").serializeArray();
    updateAllSjcl(sjclArrayPllc, clmc, djxl);

}

function updateAllSjcl(sjclArray, clmc, djxl) {
    var flag = 1;
    var sjclList = [];
    var sjcl = {};
    //批注收件材料list
    var pzSjclList = [];
    if (sjclArray.length === 0) {
        layer.alert("未添加材料,无法上传!");
        return false;
    }
    for (var i = 0; i < sjclArray.length; i++) {
        var name = sjclArray[i].name;
        sjcl[name] = sjclArray[i].value;
        // 以xmid为每一组收件材料的起始数据，作为分割依据
        if ((i + 1 < sjclArray.length && sjclArray[i + 1].name === 'xmid') || i + 1 == sjclArray.length) {
            if (sjcl.name === "") {
                flag = 0;
                return;
            }
            sjclList.push(sjcl);
            //有问题的批注件或者 非受理节点的收取部门为登记的件
            if (sjcl.sfpz == 1 || (jdmc !== "受理" && isNotBlank(sjcl.sqbm) && sjcl.sqbm.indexOf("登记") > -1)) {
                pzSjclList.push(sjcl);
            }
            sjcl = {};
        }
    }
    var sNodeFileCountSet;
    if (pzSjclList.length !== 0 && isNullOrEmpty(clmc)) {
        var fileCountInfoList = [];
        for (var i = 0; i < pzSjclList.length; i++) {
            fileCountInfoList.push({"ProID": pzSjclList[i].wjzxid, "ProName": pzSjclList[i].clmc, "Count": 1});
        }
        if (fileCountInfoList.length != 0) {
            sNodeFileCountSet = {"FileCountInfos": fileCountInfoList};
        }
    }
    if (flag === 1) {
        var path = "/slym/sjcl/list";
        if (zhsjcl && (xmlx === 2 ||xmlx ===4)) {
            path = "/slym/sjcl/zhsjcl"
        }
        getReturnData(path, JSON.stringify(sjclList), 'PATCH', function (data) {
            if (data > 0) {
                var bdcSlWjscDTO = queryBdcSlWjscDTO(clmc, false, djxl);
                var spaceId = processInsId;
                if (isNotBlank(clmc)) {
                    spaceId = encodeURI(clmc);
                }
                bdcSlWjscDTO.sNodeFileCountSet = JSON.stringify(sNodeFileCountSet);
                bdcSlWjscDTO.spaceId = spaceId;
                bdcSlWjscDTO.storageUrl = document.location.protocol + "//" + document.location.host + "/storage";
                // bdcSlWjscDTO.storageUrl = "http://192.168.2.99:8030/storage";
                if (sjclexe) {
                    //以下参数采用exe模式调用
                    var url = "fcm://";
                    //拼接storage;地址
                    url += document.location.host + "/storage;"
                    // url += "192.168.2.99:8030/storage;"
                    //拼接token;
                    url += bdcSlWjscDTO.token + ";";
                    //拼接clientid；
                    url += "clientId;";
                    //拼接spaceid；
                    url += encodeURI(spaceId) + ";"
                    //拼接nodeid；
                    if (isNotBlank(bdcSlWjscDTO.nodeId)) {
                        url += bdcSlWjscDTO.nodeId + ";";
                    } else {
                        url += ";";
                    }
                    //拼接权限
                    if (isNotBlank(sNodeFileCountSet)) {
                        url += JSON.stringify(sNodeFileCountSet) + ";";
                    } else {
                        url += "1;";
                    }
                    url += bdcSlWjscDTO.sFrmOption + ";";
                    window.location = url;
                } else {
                    var url = getContextPath() + "/view/slym/sjd.html?paramJson=" + encodeURI(JSON.stringify(bdcSlWjscDTO));
                    openSjcl(url, true, "附件上传");
                }

                // loadSjcl();
            }
        }, function (err) {
            delAjaxErrorMsg(err)
        }, false);
    } else {
        layer.alert("材料名称为空,无法上传!");
    }
}

//查询收件材料所需参数（打开附件上传使用）
function queryBdcSlWjscDTO(clmc, sfhqqx, djxl) {
    var bdcSlWjscDTO = {};
    $.ajax({
        url: '/realestate-accept-ui/slym/sjcl/bdcSlWjscDTO',
        type: 'GET',
        async: false,
        data: {processInsId: processInsId, clmc: clmc, sfhqqx: sfhqqx, djxl: djxl},
        success: function (data) {
            if (isNotBlank(data)) {
                bdcSlWjscDTO = data;
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
    return bdcSlWjscDTO;
}

//打开附件上传页面
function openSjcl(url,sfsx, title) {
    var width = $(window).width() + "px";
    var height = $(window).height() + "px";
    var index = layer.open({
        title: title,
        type: 2,
        content: url,
        area: [width, height],
        maxmin: true,
        end: function () {
            if(sfsx) {
                addModel();
                var url = getContextPath() + "/slym/sjcl/ys";
                if (zhsjcl && (xmlx === 2 || xmlx === 4)) {
                    url = getContextPath() + "/slym/sjcl/ys/zhlc"
                }
                $.ajax({
                    url: url,
                    type: 'PATCH',
                    dataType: 'json',
                    data: {gzlslid: processInsId},
                    success: function (data) {
                        loadSjcl();
                        removeModal();
                    }, error: function (xhr, status, error) {
                        delAjaxErrorMsg(xhr)
                    }
                });
            }
        }
    });
    layer.full(index);
}

//刷新页数
function refreshYs() {
    addModel();
    var url = getContextPath() + "/slym/sjcl/ys";
    if (zhsjcl && (xmlx === 2 || xmlx === 4)) {
        url = getContextPath() + "/slym/sjcl/ys/zhlc"
    }
    $.ajax({
        url: url,
        type: 'PATCH',
        dataType: 'json',
        data: {gzlslid: processInsId},
        success: function (data) {
            loadSjcl();
            removeModal();
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

//收件材料保存
function saveSjcl() {
    var sjclArrayPllc = $(".sjcl").serializeArray();
    saveAllSjcl(sjclArrayPllc);
}

function saveAllSjcl(sjclArray) {
    var sjclList = [];
    var sjcl = {};
    var flag = 1;
    for (var j = 0; j < sjclArray.length; j++) {
        var name = sjclArray[j].name;
        sjcl[name] = sjclArray[j].value;
        if (sjcl.clmc === "") {
            flag = 0;
            loadSjcl();
        }
        // 以xmid为每一组收件材料的起始数据，作为分割依据
        if ((j + 1 < sjclArray.length && sjclArray[j + 1].name === 'xmid') || j + 1 == sjclArray.length) {
            sjclList.push(sjcl);
            sjcl = {};
        }
    }
    if (flag === 1) {
        var path = "/slym/sjcl/list";
        if (zhsjcl && (xmlx === 2 || xmlx === 4)) {
            path = "/slym/sjcl/zhsjcl"
        }
        // //保存前根据文件夹名称去重保存
        var sjclmcArray = [];
        var sjclMcList = [];
        var sameMcList = [];
        var appendEl = $("#addSjclTable");
        if (zhsjcl && (xmlx === 2 || xmlx === 4)) {
            appendEl = $('.sjclTable')
        }
        var trELArray = $(appendEl).find("tr");
        for (var i = 0; i < trELArray.length; i++) {
            var sjcl = {};
            sjcl.clmc = $(trELArray[i]).find(".clmc").val();
            sjcl.djxl = $(trELArray[i]).find("input[name='djxl']").val();
            if (sjcl.clmc) {
                sjclMcList.push(sjcl);
            }
        }
        for (var j = 0; j < sjclMcList.length; j++) {
            //判断是否存在重复的，重复的页面提示
            if (sjclmcArray.indexOf(sjclMcList[j].clmc + sjclMcList[j].djxl) === -1) {
                sjclmcArray.push(sjclMcList[j].clmc + sjclMcList[j].djxl);
            } else {
                sameMcList.push(sjclMcList[j].clmc);
            }
        }
        if (sameMcList.length > 0) {
            removeModal();
            //把原先的收件材料的所有删除按钮隐藏
            for (var i = 0; i < trELArray.length; i++) {
                var sjclid = $(trELArray[i]).find("input[name='sjclid']").val();
                var clmc = $(trELArray[i]).find(".clmc").val();
                if (sjclid) {
                    $(trELArray[i]).find("#sjcldelete").remove();
                } else {
                    // 如果材料名称是存在同名文件夹的，加上提示框
                    if (sameMcList.indexOf(clmc) > -1) {
                        $($(trELArray[i]).find(".clmc")[0].parentNode).addClass('add-red-background');
                    }
                }
            }
            throw new Error("以下文件夹存在重复，请先删除:<br>" + sameMcList);
            return false;
        }
        getReturnData(path, JSON.stringify(sjclList), 'PATCH', function (data) {
            if (data >= 0) {
                loadSjcl();
            }
        }, function (err) {
            throw err;
        }, false);
    }
}

//仅仅用于查看文件管理器并且设置权限
function checksjcl() {
    var bdcSlWjscDTO = queryBdcSlWjscDTO('', true);
    bdcSlWjscDTO.spaceId = processInsId;
    bdcSlWjscDTO.storageUrl = document.location.protocol + "//" + document.location.host + "/storage";
    // bdcSlWjscDTO.storageUrl = "http://192.168.2.99:8030/storage";
    if (sjclexe) {
        //以下参数采用exe模式调用
        var url = "fcm://";
        //拼接storage;地址
        url += document.location.host + "/storage;"
        // url += "192.168.2.99:8030/storage;"
        //拼接token;
        url += bdcSlWjscDTO.token + ";";
        //拼接clientid；
        url += "clientId;";
        //拼接spaceid；
        url += encodeURI(processInsId) + ";"
        //拼接nodeid；
        if (isNotBlank(bdcSlWjscDTO.nodeId)) {
            url += bdcSlWjscDTO.nodeId + ";";
        } else {
            url += ";";
        }
        //拼接权限
        url += "1;";
        url += bdcSlWjscDTO.sFrmOption + ";";
        window.location = url;
    } else {
        var url = getContextPath() + "/view/slym/sjd.html?paramJson=" + encodeURI(JSON.stringify(bdcSlWjscDTO));
        openSjcl(url, false, "附件上传");
    }
}

//加载收件材料模块
function loadSjclTpl(gzlslid,laytpl) {
    getReturnData("/slym/sjcl/list/pl", {processInsId: gzlslid}, 'GET', function (data) {
        var json = {
            bdcSlSjclDOList: data,
            zd: zdList
        };
        if (data !== null && data.length > 0) {
            for (var i = 0; i < data.length; i++) {
                sjclidLists.push(data[i].sjclid);
            }
        }
        sjclNumber = data.length;
        var tpl = sjclTpl.innerHTML,
            view = document.getElementById('sjclxx');
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });

        resetSelectDisabledCss();

    }, function (err) {
        delAjaxErrorMsg(err);
    }, false);
}

//人脸识别模块，打开附件上传页面调用cs控件识别
function rlsb(elem) {
    getReturnData("/sjcl/folder", {gzlslid: processInsId, wjjmc: "人像采集"}, 'GET', function (data) {
        if (isNullOrEmpty(data)) {
            ityzl_SHOW_WARN_LAYER("人像采集文件夹生成失败");
        }
    }, function (err) {
        delAjaxErrorMsg(err)
    }, false);
    //获取当前点击行的权利人名称和证件号
    var $tr = $(elem).parents('tr');
    var qlrmc = $tr.find(".mc").val();
    var qlrzjh = $tr.find(".zjh").val();
    var bdcSlWjscDTO = queryBdcSlWjscDTO('', false);
    bdcSlWjscDTO.spaceId = processInsId;
    bdcSlWjscDTO.storageUrl = document.location.protocol + "//" + document.location.host + "/storage";
    var sFrmOption = bdcSlWjscDTO.sFrmOption;
    if (isNullOrEmpty(sFrmOption)) {
        sFrmOption = "";
    }
    var sFrmObj = JSON.parse(sFrmOption);
    //人脸识别必要参数
    sFrmObj.FaceVerity = 1;
    sFrmObj.FV_UserName = qlrmc;
    sFrmObj.FV_UserID = qlrzjh;
    sFrmObj.CanEdit = 1;
    bdcSlWjscDTO.sFrmOption = JSON.stringify(sFrmObj);
    var url = getContextPath() + "/view/slym/sjd.html?paramJson=" + encodeURI(JSON.stringify(bdcSlWjscDTO));
    console.log("人脸识别url参数" + JSON.stringify(bdcSlWjscDTO));
    openSjcl(url, true, "人脸识别");
}

/*
* 考虑新增收件材料方法使用地方较多，且需要增加登记小类区分，修改量比较到，重新加新增组合收件材料
* */
//使用此方法注意sjcl div层要有sjcl 的class
function addZhSjcl(elem, djxl) {
    var appendEl = $("#addSjclTable");
    if (isNotBlank(elem) && isNotBlank(djxl)) {
        appendEl = $(elem).parents('.sjcl').find("table[name='addsjcl" + djxl + "']")
    }
    //新增之前判断当前的下拉是否展开，未展开的话先点击展开
    var hideDiv = $(elem).parents('.sjcl').find(".showsjcl" + djxl + "");
    if (isNotBlank(hideDiv) && hideDiv.hasClass("bdc-hide")) {
        $(elem).parent('.bdc-form-btn').find(".bdc-show-more").click();
    }
    var trELArray = $(appendEl).find("tr");
    if (trELArray.length > 1) {
        $(appendEl).find("tr[class='bdc-table-none']").remove();
        //移除后长度减一
        trELArray = $(appendEl).find("tr");
    }
    sjclNumber = trELArray.length;
    //根据登记小类，工作流实例id，登记原因获取所有的收件材料配置，作为下拉选项
    var sjclpzList = [];
    if (addSjclFromDjyypz) {
        sjclpzList = getSjclPzList(processInsId, djxl)
    }
    var addSjclxx = {
        gzlslid: processInsId,
        sjclNumber: sjclNumber,
        djxl: djxl,
        sjclpzList: sjclpzList,
        zd: zdList
    };
    var clmcfilter = "select(clmc" + djxl + ")";
    form.on(clmcfilter, function (data) {
        clmcSqbmDz(data, sjclpzList);
        changeSjlx(data, sjclpzList);
        form.render('select', djxl);
    });
    var getTpl = addSjclTpl.innerHTML;
    laytpl(getTpl).render(addSjclxx, function (html) {
        appendEl.append(html);
        form.render(null, djxl);
    });
    //控制权限
    resetSelectDisabledCss();

    $(appendEl.find('tr:last-child td input')[0]).focus();
    //滚动到新增收件材料栏
    $('html,body').animate({scrollTop: $(appendEl.find('tr:last-child td input')[2]).offset().top}, 800);
    //默认赋值
    $('.bdc-jt-table').find('tr:last').find('input[name="fs"]').val("1");
    $('.bdc-jt-table').find('tr:last').find('input[name="ys"]').val("1");
    if (!addSjclFromDjyypz) {
        $('.bdc-jt-table').find('tr:last').find('select[name="sjlx"]').find('option[value="1"]').attr("selected", "selected");

    }
    // form.render(null,djxl);
}

function getSjclPzList(gzlslid, djxl) {
    var sjclpzList = [];
    getReturnData("/sjcl/djyysjclpz", {gzlslid: gzlslid, djxl: djxl}, "GET", function (data) {
        if (data) {
            sjclpzList = data;
        }
    }, function (xhr) {
        delAjaxErrorMsg(xhr)
    }, false);
    return sjclpzList;
}

// 材料名称收取部门对照
function clmcSqbmDz(data,sjclpzList){
    var clmc = data.value;
    var sqbm = $(data.elem).parents("tr").find('select[name="sqbm"]');
    if (isNotBlank(sjclpzList)) {
        $.each(sjclpzList, function (index, item) {
            if (item.clmc == clmc) {
                var sqbmMc = changeDmToMc(item.sqbm, zdList.sqbm);
                sqbm.val(sqbmMc);
                return false;
            }
        })
    }
}

//根据材料名称读取对应的收件类型
function changeSjlx(data, sjclpzList) {
    var clmc = data.value;
    var sjlx = $(data.elem).parents("tr").find('select[name="sjlx"]');
    if (isNotBlank(sjclpzList)) {
        $.each(sjclpzList, function (index, item) {
            if (item.clmc === clmc) {
                sjlx.val(item.sjlx);
                return false;
            }
        })
    }
}

// 附件挂接
function fjgj() {
    // 添加遮罩
    addModel();
    // 打开复制收件材料页面
    var url;
    url = '/realestate-inquiry-ui/view/dtcx/commonCx.html?cxdh=fjgj&gzlslid=' + processInsId ;
    var copySjclIndex = layer.open({
        type: 2,
        // area: ['960px', '600px'],
        area: ['1150px', '575px'],
        title: '附件挂接',
        content: url,
        maxmin: true,
        resize: true
    });
    removeModal();
}


// 从第三方获取获取委托信息文件保存到附件材料委托材料文件夹中
function hqwtcl(processInsId){

    addModel();
    $.ajax({
        type: "POST",
        url: getContextPath() + "/slym/sjcl/fjxz",
        traditional: true,
        data: {gzlslid: processInsId},
        success: function (data) {
            removeModal();
            ityzl_SHOW_SUCCESS_LAYER("保存成功");
        }, error: function (e) {
            removeModal();
            ityzl_SHOW_WARN_LAYER("失败");
        }
    });
}
