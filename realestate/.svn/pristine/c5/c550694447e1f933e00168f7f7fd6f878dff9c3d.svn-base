function loadButtonArea(ymlx) {
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var gzlslid = getQueryString("processInsId");
        var zxlc = getQueryString("zxlc");
        var lclx;
        getReturnData("/slym/xm/getlclx", {gzlslid: gzlslid}, "GET", function (data) {
            if (data !== null) {
                lclx = data;
            }
        }, function (xhr) {
            delAjaxErrorMsg(xhr)
        }, false);
        var qlxx =null;
        getReturnData("/slym/ql/qlmc", {gzlslid: gzlslid, zxlc: zxlc}, 'GET', function (data) {
            if (isNotBlank(data)) {
                qlxx = data;
            }
        }, function (err) {
            console.log(err);
        }, false);

        // 不是6的情况要隐藏审核不通过的按钮
        if(qlxx &&qlxx.length >0 &&qlxx[0].bdcXm != null && qlxx[0].bdcXm.sply!= 6){
            setTimeout(function(){$("#shbtg").hide()},200)
        }

        var form = layui.form;
        var laytpl = layui.laytpl;
        var json = {lclx: lclx, qlxx: qlxx, printBtn: slymPrintBtn};
        var tpl = buttonAreaTpl.innerHTML, view = document.getElementById('buttonArea');
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
        form.render();
        getStateById(readOnly, formStateId, ymlx);
        disabledAddFa();
        if (ymlx !== "sfxx") {
            titleShowUi();
        }
        //收费信息页面没有收费信息时都不可编辑
        if (ymlx === "sfxx") {
            titleShowUiSfxx();
            loadBtn();
        }
        //证书预览
        $("#createZs").click(function () {
            createZs();
        });
        //推送省直房改办
        $("#tsSzfgb").click(function () {
            tsSzfgb();
        });
        // 通知外网缴费
        $("#tswwjf").click(function () {
            tswwjf();
        });
        // 地籍调查表/海籍调查表
        $("#djdcb").click(function () {
            djdcb(processInsId);
        });
        //档案调用
        $("#dady").click(function () {
            dady(processInsId);
        });
        //同步权籍数据
        $("#synchLpbData").click(function () {
            isConfirm("synchLpbData","是否确认同步权籍数据？","synchLpbDataToLc");
        });
        //查看权籍附件
        $("#showLpbFj").click(function () {
            showLpbFj("");
        });

        //评价器
        $("#evaluate").click(function () {
            evaluate();
        });

        //人证对比
        $("#rzdb").click(function () {
            rzdb();
        });

        //是否由EMS推送
        $("#emsPush").click(function () {
            markEmsPush();
        });

        //一窗办理
        $("#tsYcsl").click(function () {
            syncYcsl();
        });

        //更新证书信息
        $("#updateZs").click(function () {
            updateZs();
        });
        // 打印电子发票
        $("#printDzfp").click(function(){
            printDzfp();
        });
        // 查看变更记录
        $("#ckbgjl").click(function(){
            showBgjl();
        });

        // 展示人证对比
        $("#showRzdb").click(function(){
            showRzdb();
        });

        // 获取比对结果
        $("#hqdbjg").click(function(){
            hqdbjg();
        });

        // 审核不通过
        $("#shbtg").click(function () {
            shbtg();
        });

        // 一张图附件
        $("#yztfj").click(function () {
            yztfj();
        });

        //    关联证书
        $("#glzs").click(function () {
            sessionStorage.removeItem('plwlzs' + processInsId);
            glzs();
        });

        // 不良记录
        $("#bljl").click(function () {
            bljl();
        });

        // 修正流程
        $("#xzlc").click(function () {
            xzlc();
        });

        // 权属证明
        $("#uploadQszm").click(function () {
            uploadQszm();
        });

        //获取舒城存量房交易信息
        $("#queryClfJyxxForSc").click(function () {
            queryShuChengJyxx("clf");
        });

        //获取舒城商品房交易信息
        $("#querySpfJyxxForSc").click(function () {
            queryShuChengJyxx("spf");
        });

        //舒城获取内部共享材料
        $("#getNbgxcl").click(function () {
            getNbgxcl();
        });

        //安徽省好差评，舒城
        $("#pjqhcp").click(function () {
            getPjqHcp();
        });

        // 合肥调用好差评
        $("#hfpjqhcp").click(function () {
            getHfPjqHcp();
        });

        // 蚌埠调用好差评
        $("#bbpjqhcp").click(function () {
            getBbPjqHcp();
        });

        // 同步一张网信息
        $("#tbyzwxx").click(function () {
            tbyzwxx();
        });

        // 舒城推送云签
        $("#tsyq").click(function () {
            tsyq();
        });

        //按钮压盖
        if($(".title-btn-area").height()>48){
            $(".bdc-tab-box").css("padding-top","66px");
            $(".layui-tab.fixed-content").css("padding-top","66px");
        }
        //不满足换行条件时，又遮盖了
        if (($(".content-title").width() - $(".title-btn-area").width()) < 179 && $(".title-btn-area").height() < 49){
            $(".title-btn-area .layui-btn").css("padding","0 10px");
        }
    });
}
