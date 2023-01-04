/**
 * 常州证照印章自定义查询台账js
 * (和 zzqzCz.html页面逻辑类似，但是由于原台账后台查询逻辑和电子证照共用、当前台账需要关联印章状态表查询以及考虑台账变动性，现增加自定义查询台账)
 */

/**
 * 推送生成电子印章
 * @param obj
 * @param data
 * @returns {boolean}
 */
function sc(obj, data) {

    if (data.ZZID && !isNullOrEmpty(data.ZZID)) {
        warnMsg("已生成过电子印章，不能再次生成！")
        return false;
    }

    addModel();
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/zzqzcz/zsdzqz?zsid=" + data.ZSID,
        type: "PUT",
        success: function () {
            removeModal();
            successMsg("推送生成电子印章成功！");
            searchData();;
        },
        error: function (xhr, status, error) {
            removeModal();
            failMsg("生成印章失败，请重试！");
            searchData();
        }
    })

}

/**
 * 注销电子印章
 * @param obj
 * @param data
 * @returns {boolean}
 */
function zx(obj, data) {

    if (data.ZTXX && !isNullOrEmpty(data.ZTXX) && 5 === data.ZTXX) {
        warnMsg("当前印章已注销（或作废），无法再次注销！")
        return false;
    }

    if (!data || isNullOrEmpty(data.ZZID)) {
        warnMsg("当前记录未生成印章，无法注销！")
        return false;
    }

    layer.open({
        type: 1,
        skin: 'bdc-small-tips',
        title: '确认注销',
        area: ['320px'],
        content: '是否确认注销？',
        btn: ['确定', '取消'],
        btnAlign: 'c',
        yes: function (index) {
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/zzqzcz/zsdzqz?zsid=" + data.ZSID + "&bgyy=1",
                type: "DELETE",
                success: function () {
                    removeModal();
                    successMsg("注销成功！");
                    searchData();;
                },
                error: function (xhr, status, error) {
                    removeModal();
                    failMsg("注销印章失败，请重试！");
                }
            });

            layer.close(index);
        },
        btn2: function (index) {
            layer.close(index);
        }
    });
}

/**
 * 作废电子印章
 * @param obj
 * @param data
 * @returns {boolean}
 */
function zf(obj, data) {

    layer.open({
        type: 1,
        skin: 'bdc-small-tips',
        title: '确认作废',
        area: ['320px'],
        content: '是否确认作废？',
        btn: ['确定', '取消'],
        btnAlign: 'c',
        yes: function (index) {
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/zzqzcz/zsdzqz?zsid=" + data.ZSID + "&bgyy=2",
                type: "DELETE",
                success: function () {
                    removeModal();
                    successMsg("作废成功！");
                    searchData();
                },
                error: function (xhr, status, error) {
                    removeModal();
                    failMsg("作废印章失败，请重试！");
                }
            });

            layer.close(index);
        },
        btn2: function (index) {
            layer.close(index);
        }
    });
}

/**
 * 下载电子印章
 * @param obj
 * @param data
 * @returns {boolean}
 */
function xz(obj, data) {

    if (!data || isNullOrEmpty(data.ZZID)) {
        warnMsg("当前记录未生成印章，无法下载！")
        return false;
    }

    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/zzqzcz/zsdzqz/xz?zsid=" + data.ZSID,
        type: "GET",
        success: function () {
            removeModal();
            successMsg("下载成功！");
            searchData();
        },
        error: function (xhr, status, error) {
            removeModal();
            failMsg("下载印章失败，请重试！");
            searchData();
        }
    });
}

/**
 * 查看电子印章
 * @param obj
 * @param data
 * @returns {boolean}
 */
function ck(obj, data) {

    if (!data || isNullOrEmpty(data.ZZDZ)) {
        warnMsg("当前印章未下载，无法查看！")
        return false;
    }
    window.open("/realestate-register-ui/changzhou/dzzz/priviewDzzz.html?zsid=" + data.ZSID);
}


function searchData() {
    tableReload('pageTable', {data: JSON.stringify(queryObject)}, dataUrl);
}

/**
 * 批量生成电子签章
 */
function plsc(){
    if (!checkeddata || checkeddata.length ===0) {
        layer.alert("至少选择一条数据", {title: '提示'});
        return false;
    }
    // 1、组织数据
    var ysczqxx=[], dzzqxx = [];
    var zsidList = [];
    $.each(checkeddata, function(i, value){
        if(zsidList.indexOf(value.ZSID) == -1){
            zsidList.push(value.ZSID);
            dzzqxx.push({
                slbh : value.SLBH,
                zsid : value.ZSID,
                bdcqzh : value.BDCQZH
            });
        }
        if (value.ZZID && !isNullOrEmpty(value.ZZID)) {
            ysczqxx.push(value.BDCQZH);
        }
    });

    // 2、验证是否存在已生成的电子签章数据
    if(ysczqxx.length > 0){
        warnMsg(ysczqxx.join(",")+ ",已生成过电子印章，不能再次生成！");
        return false;
    }
    console.info(dzzqxx);

    $.ajax({
        url: getContextPath() + "/rest/v1.0/zzqzcz/pl/zsdzqz",
        data: JSON.stringify(dzzqxx),
        type: "POST",
        contentType: "application/json",
        success: function (data) {
            removeModal();
            if(isNotBlank(data)){
                alertMsg(data);
            }else{
                searchData();
            }
        },
        error: function (xhr, status, error) {
            removeModal();
            failMsg("下载印章失败，请重试！");
            searchData();
        }
    });
}

/**
 * 批量下载
 */
function plxz(){
    if (!checkeddata || checkeddata.length ===0) {
        layer.alert("至少选择一条数据", {title: '提示'});
        return false;
    }
    // 1、组织数据
    var ysczqxx=[], dzzqxx = [];
    var zsidList = [];
    $.each(checkeddata, function(i, value){
        if(zsidList.indexOf(value.ZSID) == -1){
            zsidList.push(value.ZSID);
            dzzqxx.push({
                slbh : value.SLBH,
                zsid : value.ZSID,
                bdcqzh : value.BDCQZH
            });
        }
        if (isNullOrEmpty(value.ZZID)) {
            ysczqxx.push(value.BDCQZH);
        }
    });

    // 2、验证是否存在已生成的电子签章数据
    if(ysczqxx.length > 0){
        warnMsg(ysczqxx.join(",")+ ",当前记录未生成印章，无法下载！");
        return false;
    }
    console.info(dzzqxx);

    $.ajax({
        url: getContextPath() + "/rest/v1.0/zzqzcz/pl/zsdzqz/xz",
        data: JSON.stringify(dzzqxx),
        type: "POST",
        contentType: "application/json",
        success: function (data) {
            removeModal();
            if(isNotBlank(data)){
                alertMsg(data);
            }else{
                searchData();
            }
        },
        error: function (xhr, status, error) {
            removeModal();
            failMsg("下载印章失败，请重试！");
            searchData();
        }
    });
}

