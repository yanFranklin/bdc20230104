/**
 * 自定义查询Excel导入查询日志台账操作JS
 */

// 下载Excel查询文件
function xzexcel(obj,data){
    debugger;
    if(data && !isNullOrEmpty(data.EXCEL)) {
        window.open(data.EXCEL);
    } else {
        warnMsg("未查找到查询Excel文件路径！");
    }
}
