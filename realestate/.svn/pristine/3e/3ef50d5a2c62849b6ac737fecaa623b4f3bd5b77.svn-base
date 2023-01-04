$(document).ready(function () {
    // 获取参数
    var xmid = $.getUrlParam('xmid');
    // 表单ID
    var formStateId = $.getUrlParam('formStateId');
    // 获取表单权限参数readOnly
    var readOnly = $.getUrlParam('readOnly');

    layui.use(['element', 'form', 'jquery', 'laydate', 'laytpl', 'layer', 'table'], function () {
        var form = layui.form;
        var $ = layui.jquery;
        var laytpl = layui.laytpl;
        var table = layui.table;
        // 获取调查表信息
        getDcbxx(xmid);
        // 获取海域使用权信息
        getHysyq(xmid);

        /**
         * 字典
         */
        var zdList = getZdList();
        if (zdList) {
            // 渲染字典项
            $.each(zdList.djlx, function (index, item) {
                $('#djlx').append('<option value="' + item.DM + '">' + item.MC + '</option>');
            });
            $.each(zdList.mjdw, function (index, item) {
                $('#mjdw').append('<option value="' + item.DM + '">' + item.MC + '</option>');
            });
            $.each(zdList.qlsdfs, function (index, item) {
                $('#qlsdfs').append('<option value="' + item.DM + '">' + item.MC + '</option>');
            });

            form.render('select');
        }

        /**
         * 获取调查表信息
         * @param xmid
         */
        function getDcbxx(xmid) {
            $.ajax({
                url: BASE_URL + '/hyxx/' + xmid + '/hjdcb',
                type: "GET",
                dataType: "json",
                success: function (data) {
                    if (data) {
                        // 宗海调查表
                        var zhDjdcb = data.djDcbResponseDTO;
                        changeZhDjdcbZd(zhDjdcb);
                        // 宗海权属调查
                        var zhQsdc = zhDjdcb.zhQsdcDO;
                        // 宗海权利人
                        var zhQlrList = data.qlrList;
                        // 宗海及内部单元记录表
                        var zhZhjnbdyjlbList = zhDjdcb.zhZhjnbdyjlbList;
                        // 宗海界址标示表
                        // var zhJzbsbList = zhDjdcb.zhJzbsbList;

                        form.val('form', zhDjdcb);
                        form.val('form', zhQsdc);

                        // 渲染权利人信息
                        renderQlrxx(zhQlrList);
                        // 渲染宗海内部记录表
                        renderZhjnbdyjlb(zhZhjnbdyjlbList);

                        // 对于坐落字段加title
                        if (!isNullOrEmpty(zhDjdcb.zl) && $("[name='zl']")) {
                            $("[name='zl']").attr("title", zhDjdcb.zl);
                        }
                    }
                    /**
                     * 渲染日期
                     */
                    renderDate();
                }, error: function (e) {
                    delAjaxErrorMsg(e);
                }
            });
        }

        /**
         * 获取海域使用权
         * @param xmid
         */
        function getHysyq(xmid) {
            $.ajax({
                url: BASE_URL + '/qlxx/' + xmid,
                type: "GET",
                dataType: "json",
                success: function (data) {
                    if (data) {
                        var param = {};
                        param.syqqssj = data.syqqssj;
                        param.syqjssj = data.syqjssj;
                        form.val('form', param);
                    }
                    /**
                     * 渲染日期
                     */
                    renderDate();
                }, error: function (e) {
                    delAjaxErrorMsg(e);
                }
            });
        }

        /**
         *
         * @param 宗海及内部单元记录表属性结构描述
         */
        function renderZhjnbdyjlb(data) {
            if (data) {
                form.val('form', data[0]);
                var syjze = 0.00;
                $.each(data, function (index, item) {
                    if (item.syjse) {
                        syjze += item.syjse;
                    }
                });
                if (syjze > 0) {
                    $("#syjze").val(syjze.toFixed(2));
                }
            }

        }

        /**
         * 权力人信息
         * @param data
         */
        function renderQlrxx(data) {
            // 封装权利人列表格式数据
            var qlrxx = {};
            qlrxx.title = "权利人信息";

            if (data && zdList.zjzl) {
                // 转换字典
                for (var i = 0, len = data.length; i < len; i++) {
                    for (var j = 0, zdlen = zdList.zjzl.length; j < zdlen; j++) {
                        if (zdList.zjzl[j].DM == data[i].zjzl) {
                            data[i].zjzl = zdList.zjzl[j].MC;
                        }
                    }

                    for (var j = 0, zdlen = zdList.qlrlx.length; j < zdlen; j++) {
                        if (zdList.qlrlx[j].DM == data[i].qlrlx) {
                            data[i].qlrlx = zdList.qlrlx[j].MC;
                        }
                    }
                }
            }

            qlrxx.list = data;
            var getTpl = qlrTpl.innerHTML;
            laytpl(getTpl).render(qlrxx, function (html) {
                $("#tbody2").before(html)
            });
            form.render();
        }

        /**
         * 宗海地籍调查表，字典项转换
         * @param zhDjdcb
         */
        function changeZhDjdcbZd(zhDjdcb) {
            if (zhDjdcb) {
                if (zhDjdcb.xmxz) {
                    for (var j = 0, zdlen = zdList.xmxz.length; j < zdlen; j++) {
                        if (zdList.xmxz[j].DM == zhDjdcb.xmxz) {
                            zhDjdcb.xmxz = zdList.xmxz[j].MC;
                            break;
                        }
                    }
                }
                if (zhDjdcb.yhlxa) {
                    for (var j = 0, zdlen = zdList.hysylxa.length; j < zdlen; j++) {
                        if (zdList.hysylxa[j].DM == zhDjdcb.yhlxa) {
                            zhDjdcb.yhlxa = zdList.hysylxa[j].MC;
                            break;
                        }
                    }
                }
                if (zhDjdcb.yhlxb) {
                    for (var j = 0, zdlen = zdList.hysylxb.length; j < zdlen; j++) {
                        if (zdList.hysylxb[j].DM == zhDjdcb.yhlxb) {
                            zhDjdcb.yhlxb = zdList.hysylxb[j].MC;
                            break;
                        }
                    }
                }
            }
        }
    });
});