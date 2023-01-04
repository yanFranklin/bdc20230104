
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function() {
    var element = layui.element;
    var layer = layui.layer;


});

//加载交易与税收tab内容
function generateJyss(data) {

    //加载卖方信息
    generateCmfxx(data.bdcSlZcfDTOList);

    //加载买方信息
    generateMsfxx(data.bdcSlZrfDTOList);

    //加载房屋交易信息
    generateFwjyxx(data.bdcSlJyxxDO,data.bdcSlFwxxDO,bdcSlXm);

    //加载房屋信息模块
    generateFwxx(data.bdcSlFwxxDO);

    //加载发票信息模块
    generateFpxx(data.bdcZrfSwxxList,data.bdcSlJyxxDO);

    //加载承受方税款信息模块
    generateMsfSwxx(data.bdcZrfSwxxList);
}



//加载卖方信息模块
function generateCmfxx(sqrList){
    var tpl = zlfzcfxxTpl.innerHTML,
        view = document.getElementById('zlfzcfxx');
    var json ={
        cmfxxList:sqrList
    };
    //渲染数据
    laytpl(tpl).render(json, function(html) {
        view.innerHTML = html;
    });

}

//加载买方信息模块
function generateMsfxx(sqrList){
    var tpl = zlfzrfxxTpl.innerHTML,
        view = document.getElementById('zlfzrfxx');
    var json ={
        msfxxList:sqrList,
        zd:zdList
    };
    //渲染数据
    laytpl(tpl).render(json, function(html) {
        view.innerHTML = html;
    });

}

//加载房屋交易模块
function generateFwjyxx(jyxx,fwxx,jbxx){
    var tpl = fwjyxxTpl.innerHTML,
        view = document.getElementById('fwjyxx');
    var json ={
        jyxx:jyxx,
        fwxx:fwxx,
        jbxx:jbxx,
        zd:zdList
    };
    //渲染数据
    laytpl(tpl).render(json, function(html) {
        view.innerHTML = html;
    });

}

//加载房屋信息模块
function generateFwxx(fwxx){
    var tpl = fwxxTpl.innerHTML,
        view = document.getElementById('fwxx');
    var json ={
        fwxx:fwxx,
        zd:zdList
    };
    //渲染数据
    laytpl(tpl).render(json, function(html) {
        view.innerHTML = html;
    });

}

//加载发票信息模块
function generateFpxx(swxxList,jyxx){
    var tpl = fpxxTpl.innerHTML,
        view = document.getElementById('fpxx');
    var hsxx ={};
    if(swxxList != null &&swxxList.length >0){
        var swxxDTO =swxxList[0];
        if(swxxDTO){
            hsxx =swxxDTO.bdcSlHsxxDO
        }

    }
    var json={
        zd:zdList,
        hsxx:hsxx,
        jyxx:jyxx
    };
    //渲染数据
    laytpl(tpl).render(json, function(html) {
        view.innerHTML = html;
    });

}

//加载承受方税款信息模块
function generateMsfSwxx(swxxList){
    var tpl = csfskxxTpl.innerHTML,
        view = document.getElementById('csfskxx');
    var swxxDTO ={};
    var jexj ={ynsexj:0,
               jmskxj:0,
               jzexj:0,
               sjnse:0};
    if(swxxList != null &&swxxList.length >0){
        swxxDTO =swxxList[0];

        var swxx=swxxDTO.bdcSlHsxxMxDOList;
        for (var i=0; i < swxx.length;i++){
            jexj.ynsexj += swxx[i].ynse;
            jexj.jmskxj += swxx[i].cqbczsjmsk;
            jexj.jzexj += swxx[i].zzsxgmnsrjze;
            jexj.sjnse += swxx[i].sjnse;
        }
    }
    var json={
        zd:zdList,
        swxxDTO:swxxDTO,
        jexj:jexj
    };
    //渲染数据
    laytpl(tpl).render(json, function(html) {
        view.innerHTML = html;
    });

}

/**
 * 保存交易税收页签的申报房屋套次字段
 */
function saveBdcSlZrfZcf(className){
    var bdcSlSqrData = [];
    var sqrlist = [];
    if($(className).length != 0){
        bdcSlSqrData = $(className).serializeArray();
        var bdcSlSqrSingleData = {};
        for (var j = 0; j < bdcSlSqrData.length; j++) {
            var name = bdcSlSqrData[j].name;
            bdcSlSqrSingleData[name] = bdcSlSqrData[j].value;
            var obj = bdcSlSqrData[j];
            // 以qlrid为每一组权利人的起始数据，作为分割依据
            if ((j + 1 < bdcSlSqrData.length && bdcSlSqrData[j + 1].name === 'sqrid') || j + 1 == bdcSlSqrData.length) {
                sqrlist.push(bdcSlSqrSingleData);
                bdcSlSqrSingleData = {};
            }
        }
    }else{
        // 没有申请人 认为是成功的
        return true;
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



