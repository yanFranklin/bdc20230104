/**
 * Created by ypp on 2020/3/3.
 * 详细配置js
 */
layui.use(['form','layer','table','laytpl'], function(){
    var form = layui.form,
        $ = layui.jquery,
        layer = layui.layer,
        table = layui.table,
        laytpl = layui.laytpl;

    $(function(){
        //配置
        var pzid = $.getUrlParam('pzid');
        $('.bdc-dpz-content').height($('.bdc-container').outerHeight(true) - $('.bdc-tips-words').outerHeight(true) - $('.bdc-top-content').outerHeight(true) - 154);
        var type = $.getUrlParam("type");//根据type判断是流程多选还是字典项多选，获取保存数据
        //配置信息
        var pzData ={};
        //字典表
        var zdb = [];
        //已配置列表
        var ypzList = [],ypzStr = '';
        //复选框选择list
        var selectDpzList = [];

        //需要先查询配置对象，用于后续页面判断
        getTsymPz(pzid);

        //获取字典表
        getZdb();

        //默认加载已配置列表
        renderYpz(pzData);

        //默认获取待配置列表，并渲染到页面
        renderDpzList();

        //点击删除配置
        $('.bdc-ypz-content').on('click','.bdc-delete-pz',function(){
            var getName = $(this).parent().data('name');
            ypzList.splice($.inArray(getName,ypzList),1);
            ypzStr = ypzList.join(',');
            $(this).parent().remove();
            savePzz(ypzStr,"delete");
        });

        //点击收起
        $('.bdc-up-btn').on('click',function(){
            var $this = $(this);
            $this.addClass('bdc-hide');
            $this.siblings('.bdc-down-btn').removeClass('bdc-hide');
            $this.parent().siblings('.bdc-content-js').addClass('bdc-hide');
        });
        $('.bdc-down-btn').on('click',function(){
            var $this = $(this);
            $this.addClass('bdc-hide');
            $this.siblings('.bdc-up-btn').removeClass('bdc-hide');
            $this.parent().siblings('.bdc-content-js').removeClass('bdc-hide');
        });

        //监听待配置的查询
        $('.bdc-search-box .layui-input').on('focus', function () {
            $(this).siblings('.layui-icon-close').removeClass('bdc-hide');
        });
        $('.bdc-search-box .layui-input').bind('keyup', function (event) {
            if (event.keyCode == "13") {
                //回车执行查询
                var inputText = $(this).val();
                var searchDpzList = [];
                zdb.forEach(function(v){
                    if(ypzStr.indexOf(v.zdxsjz) == -1 && v.zdxxsz.indexOf(inputText) != -1){
                        v.checked=false;
                        searchDpzList.push(v);
                        if(selectDpzList.length >0 &&selectDpzList.indexOf(v.zdxsjz) >-1){
                            v.checked=true;
                        }
                    }
                });
                var getPzListTpl = zdyzdxpzListTpl.innerHTML;
                laytpl(getPzListTpl).render(searchDpzList, function(html){
                    $('.bdc-pz-item').html(html);
                    form.render('checkbox');
                    var $span = $('.bdc-pz-item .layui-form-checkbox[lay-skin=primary]>span');
                    for(var i = 0,len = $span.length; i < len; i++){
                        if($span[i].innerHTML.length > 7){
                            $($span[i]).attr('title',$span[i].innerHTML);
                        }
                    }
                });
            }
        });
        //点击搜索框的删除
        $('.bdc-search-box .layui-icon-close').on('click', function () {
            $('.bdc-search-box .layui-input').val('');
            $(this).addClass('bdc-hide');
            renderDpzList();
        });

        //监听复选框选择
        form.on('checkbox(pzxFilter)', function(data){
            if(pzData.pzlx ===4 ||pzData.pzlx ===6){
                //单选
                $("[type='checkbox']").prop("checked", "");
                $(this).prop("checked", "checked");
                form.render('checkbox');
                selectDpzList =[];
            }
            if(data.elem.checked){
                selectDpzList.push($(data.elem).data('key'));
            }else {
                selectDpzList.splice($.inArray($(data.elem).data('key'),selectDpzList),1);
            }


        });

        //单击 保存
        $('.bdc-save-pz').on('click',function(){
            //pzList 为最终配置内容
            var savePzStr;
            if(isNotBlank(ypzStr)) {
                if(selectDpzList.length >0) {
                    savePzStr = ypzStr + ',' + selectDpzList.join(',');
                }else{
                    savePzStr =ypzStr;
                }
            }else{
                savePzStr = selectDpzList.join(',');
            }
            if(pzData.pzlx ===4 ||pzData.pzlx ===6 &&selectDpzList.length >0){
                //单选
                savePzStr = selectDpzList.join(',');
            }

            // 已配置与保存配置内容都为空时，配置状态应为未配置
            if(!(!isNotBlank(ypzStr) && !isNotBlank(savePzStr))){
                //获取配置值
                addModel();
                if(isNotBlank(pzData.pzyzzhbs)) {
                    //调用规则验证
                    var bdcGzYzQO = {};
                    bdcGzYzQO.zhbs = pzData.pzyzzhbs;
                    var gzyzParamMap = {};
                    gzyzParamMap.pzz = ypzStr;
                    bdcGzYzQO.paramMap = gzyzParamMap;
                    gzyzCommon(bdcGzYzQO, function (data) {
                        console.log("验证通过");
                        //保存方法
                        savePzz(savePzStr,"save");
                    });
                }else{
                    //保存方法
                    savePzz(savePzStr,"save");
                }
            }
        });

        //查询配置详细
        function getTsymPz(pzid){
            $.ajax({
                type: 'GET',
                url: getContextPath() +"/rest/v1.0/tsywpz?pzid="+pzid,
                async:false,
                success: function (data) {
                    //console.log(data);
                    pzData=data;
                    $('.bdc-sm-js').html(data.pzsm);
                }
            });

        }

        //获取配置字典表
        function getZdb(){
            //流程列表
            $.ajax({
                type: 'POST',
                url: getContextPath() +"/rest/v1.0/tsywpz/pzzdxx",
                async: false,
                contentType: 'application/json',
                dataType: "json",
                data: JSON.stringify(pzData),
                success: function (data) {
                    //console.log(data);
                    zdb = data;
                }
            });
        }
        //加載已配置
        function renderYpz(data){
            $('.bdc-sm-js').html(data.pzsm);
            if(data.pzz != null){
                ypzStr = data.pzz;
                ypzList = data.pzz.split(",");
                var ypzHtml = '';
                ypzList.forEach(function(v){
                    var getName = '';
                    zdb.forEach(function(value){
                        if (value.zdxsjz == v) {
                            getName = value.zdxxsz;
                        }
                    });
                    if(isNotBlank(getName)){
                        ypzHtml += '<a href="javascript:;" data-name="'+ v +'" title="'+getName+'">' + '<span>'+getName+
                            '</span><img class="bdc-delete-pz" src="../../../static/image/circle-rem.png" alt=""></a>'
                    }

                });
                $('.bdc-ypz-content').html(ypzHtml);
            }
        }
        //加载待配置
        function renderDpzList(){
            var dpzList = [];
            zdb.forEach(function(v){
                if(ypzStr.indexOf(v.zdxsjz) == -1){
                    v.checked=false;
                    dpzList.push(v);
                    if(selectDpzList.length >0 &&selectDpzList.indexOf(v.zdxsjz) >-1){
                        v.checked=true;
                    }
                }
            });

            var getPzListTpl = zdyzdxpzListTpl.innerHTML;
            laytpl(getPzListTpl).render(dpzList, function(html){
                $('.bdc-pz-item').html(html);
                form.render('checkbox');
                var $span = $('.bdc-pz-item .layui-form-checkbox[lay-skin=primary]>span');
                for(var i = 0,len = $span.length; i < len; i++){
                    if($span[i].innerHTML.length > 7){
                        $($span[i]).attr('title',$span[i].innerHTML);
                    }
                }
            });
        }
        //保存配置值
        function savePzz(pzz,type){
            $.ajax({
                type: 'PUT',
                url: getContextPath() +"/rest/v1.0/tsywpz/update",
                contentType: 'application/json',
                dataType: "json",
                data: JSON.stringify({pzid: pzid,pzz: pzz}),
                success: function (data) {
                    removeModal();
                    if(data == 1){
                        if(type ==="delete") {
                            delSuccessMsg();
                            renderDpzList();
                        }else{
                            ypzStr = pzz;
                            layer.msg('<img src="../../../static/lib/bdcui/images/success-small.png" alt="">提交成功');
                            setTimeout(function(){document.location.reload(); }, 1000);

                        }
                        sessionStorage.tsywpzLcIsChange = true;
                    }
                }
            });

        }

    });
});