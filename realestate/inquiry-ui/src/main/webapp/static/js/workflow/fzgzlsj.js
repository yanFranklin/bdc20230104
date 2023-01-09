/*
* 复制工作流事件的js
* */

/**
 * Created by Administrator on 2019/5/29.
 */
layui.config({
    base: '../../static/' //静态资源所在路径
}).extend({response: 'lib/bdcui/js/response', formSelects: 'lib/form-select/formSelects-v4'});
layui.use(['jquery', 'formSelects', 'form', 'response'], function () {
    var $ = layui.jquery,
        formSelects = layui.formSelects,
        response = layui.response,
        form = layui.form;
    var BASE_URL = '/realestate-inquiry-ui/rest/v1.0';
    $(function () {
        $('.bdc-content').css('min-height', $('body').height() - 56);
    });
    formSelects.config('fzlcmc', {
        keyName: 'name',            //自定义返回数据中name的key, 默认 name
        keyVal: 'processDefKey'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('fzjdmc', {
        keyName: 'name',            //自定义返回数据中name的key, 默认 name
        keyVal: 'name'            //自定义返回数据中value的key, 默认 value
    }, true);

    formSelects.config('addlcmc', {
        keyName: 'name',            //自定义返回数据中name的key, 默认 name
        keyVal: 'processDefKey'            //自定义返回数据中value的key, 默认 value
    }, true);

    /**
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取工作流定义名称
     */
    $.ajax({
        url: BASE_URL + '/mryj/gzldymcs',
        type: "GET",
        dataType: "json",
        async: false,
        success: function (data) {
            if (data) {
                formSelects.data('fzlcmc', 'local', {arr: data});
                formSelects.data('addlcmc', 'local', {arr: data});
                form.render('select');
            }
        }
    });


    form.on('submit(submitBtn)', function (data) {
        addModel();
        var fzgzldyid = formSelects.value('fzlcmc', 'val');
        var jdmcs = formSelects.value('fzjdmc', 'val');
        var addgzldyid = formSelects.value('addlcmc', 'val');
        if (isNullOrEmpty(fzgzldyid)) {
            removeModal();
            warnMsg("复制流程名称不可为空");
            return false;
        }
        if (isNullOrEmpty(addgzldyid)) {
            removeModal();
            warnMsg('新增流程名称不可为空');
            return false;
        }
        if (fzgzldyid[0] === addgzldyid[0]) {
            removeModal();
            warnMsg("复制流程名称和新增流程不可相同");
            return false;
        }
        copyGzlsjPz(fzgzldyid[0], jdmcs, addgzldyid);
        // 禁止提交后刷新
        return false;
    });

    formSelects.on('fzlcmc', function (id, vals, val, isAdd, isDisabled) {
        if (isAdd) {
            //id:           点击select的id
            //vals:         当前select已选中的值
            //val:          当前select点击的值
            //isAdd:        当前操作选中or取消
            //isDisabled:   当前选项是否是disabled
            //false为清空不查询
            if (val.value) {
                queryJdmc(val.value);
            }
            form.render('select');
            // formSelects.render('jdmc');
        } else {
            //取消勾选
            //判断vals的长度，如果长度为2，取消后只有一条数据，需要获取节点名称
            form.render('select');
            formSelects.render('fzjdmc');
        }
    });

    /**
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 复制 登记小类配置
     */
    function fzDjxlpz(gzldyid, djxl, bfzDjxl) {
        var returnData = '';
        $.ajax({
            url: BASE_URL + "/lcpz/fz/djxlPz",
            type: "get",
            data: {gzldyid: gzldyid, newdjxl: djxl[0].DM, bfzDjxl: bfzDjxl},
            contentType: 'application/json',
            dataType: "json",
            async: false,
            success: function (data) {
                returnData = data;
            },
            error: function (e) {
                response.fail(e, '');
            }
        });
        return returnData;
    }

    /*
    * 复制工作流事件配置
    * */
    function copyGzlsjPz(fzgzldyid, fzjdmc, addlcdyid) {
        //1.检查复制的流程是否有工作流事件的配置,没有不允许复制
        var result = checkGzlsj(fzgzldyid);
        if (!result) {
            removeModal();
            warnMsg("当前选择复制的流程未关联接口");
            return false;
        }
        //2.检查新增的流程是否已存在工作流事件的配置,提示是否覆盖
        var res = checkGzlsj(addlcdyid[0]);
        if (res) {
            removeModal();
            layer.confirm("当前流程已存在关联接口,是否覆盖?", {title: '提示'}, function (index) {
                copy(fzgzldyid, fzjdmc, addlcdyid, index)
            });
        } else {
            removeModal();
            copy(fzgzldyid, fzjdmc, addlcdyid)
        }
    }

    function showtsxx(data) {
        if (data != undefined && data.fzsl != null && data.fznr != null) {
            var imgInfo = data.fzsl > 0 ? '<img src="../../../static/lib/bdcui/images/success-small.png" alt="">' : '<img src="../../../static/lib/bdcui/images/warn.png" alt="">';
            var info = '<p>' + imgInfo + '成功复制' + data.fzsl + '条数据，' + data.fznr + '</p>';
            $('#fztsxx').before(info);
        }
    }

    //查询节点名称拼接
    function queryJdmc(gzldyid) {
        $.ajax({
            url: BASE_URL + '/gzl/jdmc',
            type: "GET",
            data: {gzldyid: gzldyid},
            dataType: "json",
            success: function (data) {
                if (data) {
                    //清除之前的下拉选项
                    document.getElementById("fzjdmc").options.length = 0;
                    $("#fzjdmc").append('<option value="">请选择</option>');
                    formSelects.data('fzjdmc', 'local', {arr: data});
                    formSelects.value('fzjdmc', []);
                    form.render('select');
                }
            }
        });
    }

    function copy(fzgzldyid, fzjdmc, addlcdyid, index) {
        addModel();
        //复制数据
        var bdcGzlsjFzDTO = {};
        bdcGzlsjFzDTO.fzlcdyid = fzgzldyid;
        bdcGzlsjFzDTO.jdmcList = fzjdmc;
        bdcGzlsjFzDTO.addlcdyidList = addlcdyid;
        $.ajax({
            url: BASE_URL + '/gzl/sj/copy',
            type: "POST",
            data: JSON.stringify(bdcGzlsjFzDTO),
            contentType: 'application/json',
            success: function (data) {
                removeModal();
                console.log("复制成功");
                successMsg("复制成功");
                closeWin();
            }
        });
        layer.close(index);
    }

    function checkGzlsj(gzldyid) {
        var result = false;
        $.ajax({
            url: BASE_URL + '/gzl/fzlc/gzlsj',
            type: "GET",
            data: {gzldyid: gzldyid},
            dataType: "json",
            async: false,
            success: function (data) {
                result = data;
            }
        });
        return result;
    }


    /**
     * 关闭弹出页面
     */
    window.closeWin = function () {
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
        parent.$('#search').click();
    };

    window.closeParentWindow = function () {
        var index = parent.parent.layer.getFrameIndex(window.name);
        parent.parent.layer.close(index);
    };

});