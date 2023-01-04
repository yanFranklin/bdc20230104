var $, form, laytpl;
var gzlslid = $.getUrlParam("gzlslid");
layui.use(['form', 'jquery', 'layer', 'laydate', 'element', 'table', 'laytpl'], function () {
    var layer = layui.layer,
        element = layui.element;
    laytpl = layui.laytpl;
    form = layui.form;

    // addModel();
    // getSearch();

    $(document).on('click', '#search', function () {
        search();
    })

    // 获取查询参数
    function getSearch() {
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/gx/cxywcs/qlr",
            type: "POST",
            data: JSON.stringify({"gzlslid": gzlslid}),
            contentType: 'application/json',
            success: function (data) {
                console.log('qlr:');
                console.log(data);
                removeModal();

                if (data && data.length > 0) {
                    var getData = {
                        "cxywcs": []
                    };
                    for (var i = 0; i < data.length; i++) {
                        getData.cxywcs.push({"qlrmc": data[i].qlrmc});
                    }

                    var searchData = getData;
                    // 展示查询条件，加载表格内容
                    console.log('searchData:');
                    console.log(searchData);
                    // generateTable(searchData, false);
                    generateSearchCondition(searchData);

                    // 查询条件不为空进行查询
                    if (searchData && searchData.cxywcs && searchData.cxywcs.length > 0) {
                        $('#search').trigger('click');
                    }
                }

            },
            error: function (xhr, status, error) {
                removeModal();
                delAjaxErrorMsg(xhr)
            }
        });
    }

    function generateSearchCondition(searchData) {
        $('#cszh').val(searchData.cxywcs[0].qlrmc);
        $('#xsemqxm').val(searchData.cxywcs[0].qlrmc);
        $('#xsefqxm').val(searchData.cxywcs[0].qlrmc);

        searchData.cxywcs.forEach(function (item, index) {
            $('#cszh-select').append('<option value="' + item.qlrmc + '">' + item.qlrmc + '</option>');
            $('#xsemqxm-select').append('<option value="' + item.qlrmc + '">' + item.qlrmc + '</option>');
            $('#xsefqxm-select').append('<option value="' + item.qlrmc + '">' + item.qlrmc + '</option>');
        });

        form.render();
    }

    form.on('select(cszh-select)', function (data) {
        $(this).parents('.layui-input-inline').find("input[name='cszh']").val(data.value);
        $(this).parents('.layui-input-inline').find("dl").css({"display": "none"});
        form.render();
        resetSelectDisabledCss();
    });

    form.on('select(xsemqxm-select)', function (data) {
        $(this).parents('.layui-input-inline').find("input[name='xsemqxm']").val(data.value);
        $(this).parents('.layui-input-inline').find("dl").css({"display": "none"});
        form.render();
        resetSelectDisabledCss();
    });

    form.on('select(xsefqxm-select)', function (data) {
        $(this).parents('.layui-input-inline').find("input[name='xsefqxm']").val(data.value);
        $(this).parents('.layui-input-inline').find("dl").css({"display": "none"});
        form.render();
        resetSelectDisabledCss();
    });
});
// 出生证明信息
/*  var cszmData = [];
  $(function () {
      // 初始化
      init();

      // 点击查询
      $('#search').on('click', function () {
          search();
      });

      // 表格信息
      function init() {
          table.render({
              elem: '#pageTable',
              toolbar: '#toolbarDemo',
              title: '查询',
              defaultToolbar: ['filter', 'print', 'exports'],
              even: true,
              cols: [[
                  {type: 'numbers', width: 50, fixed: 'left'},
                  {field: 'XSEXM', title: '新生儿姓名', width: 150},
                  {field: 'XSEXB', title: '新生儿性别', width: 200},
                  {field: 'XSECSRQ', title: '出生时间', width: 150},
                  {field: 'CSZH', title: '出生医学证明编号', width: 150},
                  {field: 'XSEMQXM', title: '母亲姓名', width: 150},
                  {field: 'XSEMQSFZH', title: '母亲证件编号', width: 150},
                  {field: 'XSEMQZJLX', title: '母亲证件类型', width: 150},
                  {field: 'XSEFQXM', title: '父亲姓名', width: 150},
                  {field: 'XSEFQSFZH', title: '父亲证件编码', width: 150},
                  {field: 'XSEFQZJLX', title: '父亲证件类型', width: 150}
              ]],
              data: cszmData,
              page: true,
              done: function () {
                  setHeight();
              }
          });
      }*/

// 查询事假
function search() {
    var wjh = $("#wjh").val();

    if (isNullOrEmpty(wjh)) {
        warnMsg("请输入查询接口完整条件！");
        return;
    }
    addModel();
    var searchData = {"wjh": wjh, "beanName": "bengbu_tdzchb"};
    // 查询土地资产信息
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/queryByExchange/bengbu/tdzchb",
        type: 'GET',
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        // data: encodeURI(JSON.stringify(searchData)),
        data: {
            "wjh": wjh, "beanName": "bengbu_tdzchb"
        },
        success: function (data) {
            if (data) {
                var tpl = csxxTpl.innerHTML, view = document.getElementById('csxxTable');
                //渲染数据
                laytpl(tpl).render(data, function (html) {
                    view.innerHTML = html;
                });
                form.render();
                removeModal();
                /*设置高度同步*/
                var tdHeight = $(".bdc-jt-table td").height();
                $("input.layui-input.sjj-center").height(tdHeight);
            } else {
                layer.msg("未查询到信息", {icon: 5, time: 3000});
                removeModal();

            }
        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
        }
    });

}

// });
