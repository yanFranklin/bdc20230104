/**
 * author: <a href="mailto:yaoyi@gtmap.cn>yaoyi</a>
 * version 1.0.0  2022/09/14
 * describe: 日志详情
 */
var logXqDataCache;
var zdList = [];
var eszdmcMap = {};

function loadXqData(data, zd){
    layui.use(['form', 'jquery', 'element', 'table', 'layer','laydate', 'laytpl'], function () {
        var layer = layui.layer;
        var table = layui.table;
        zdList = zd;
        if(isNotBlank(zdList) && isNotBlank(zdList.eszdmc)){
            layui.each(zdList.eszdmc, function(index, item){
                eszdmcMap[item.DM] = item.MC;
            });
        }

        if (isNotBlank(data)) {
            logXqDataCache = data;
            var res = []
            for(var key in data){
                var val = data[key];
                if(isNotBlank(key) && isNotBlank(fmtData(val))){
                    res.push({key: convertKeyToMc(key), val: val});
                }
            }
            table.render({
                elem: '#xq',
                limit: 999,
                data: res,
                id: 'xq',
                even: true,
                cols: [[
                    {field: 'key', title: '字段名', width:200},
                    {field: 'val', title: '数据', width:400},
                ]]
            });
        }
    });
}

function convertKeyToMc(key){
    if(isNotBlank(eszdmcMap)){
        var value = eszdmcMap[key];
        if(isNotBlank(value)){
            return value;
        }else{
            return key;
        }
    }
}

function fmtData(data){
    if(isNullOrEmpty(data)){
        return "";
    }
    if(data == "unknown"){
        return "";
    }
    return data;
}
