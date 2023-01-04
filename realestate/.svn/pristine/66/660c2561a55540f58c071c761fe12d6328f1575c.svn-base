<!DOCTYPE html>
<html lang="zh-cn">

<head>
    <meta charset="utf-8">
    <title>调查信息</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link rel="stylesheet" href="../lib/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="../lib/bdcui/css/common.css">
    <link rel="stylesheet" href="../lib/bdcui/css/form.css">
    <link rel="stylesheet" href="../lib/bdcui/css/mask.css">
    <script src="../js/mask.js?v=1.4045"></script>
    <script src="../lib/layui/layui.js"></script>
    <script src="../lib/js/jquery.min.js"></script>
    <script src="../js/common.js"></script>
</head>
<body>
<div class="bdc-form-div">
    <form class="layui-form setOverflow" lay-filter="form">
        <button class="layui-btn bdc-major-btn layui-hide" lay-submit="" id="saveDcxx" lay-filter="saveDcxx"
                type="button">提交
        </button>
        <div class="form-margin-area">
            <div class="basic-info">
                <!-- 填报字段 -->
                <div class="layui-form-item layui-hide">
                    <input type="text" class="layui-input" name="bdcdyfwlx" id="bdcdyfwlx"
                           value="${bdcdyfwlx!}">
                    <input type="text" class="layui-input" name="fwIndex" id="fwIndex" value="${fwIndex!}">
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline margin-top-ipt">
                        <label class="layui-form-label">调查者</label>
                        <div class="layui-input-inline">
                            <input type="text" autocomplete="off" class="layui-input" name="dcz" id="dcz">
                        </div>
                    </div>
                    <div class="layui-inline margin-top-ipt">
                        <label class="layui-form-label">调查时间</label>
                        <div class="layui-input-inline">
                            <input type="text" autocomplete="off" class="layui-input" name="dcsj" id="dcsj">
                        </div>
                    </div>
                </div>
                <div class="layui-form-item change-textarea-margin">
                    <label class="layui-form-label">产权来源</label>
                    <div class="layui-input-inline">
                        <textarea class="layui-textarea change-textarea-width" autocomplete="off"
                                  name="cqly"></textarea>
                    </div>
                </div>
                <div class="layui-form-item change-textarea-margin">
                    <label class="layui-form-label">共有情况</label>
                    <div class="layui-input-inline">
                        <textarea class="layui-textarea change-textarea-width" autocomplete="off"
                                  name="gyqk"></textarea>
                    </div>
                </div>
                <div class="layui-form-item change-textarea-margin">
                    <label class="layui-form-label">附加说明</label>
                    <div class="layui-input-inline">
                        <textarea class="layui-textarea change-textarea-width" autocomplete="off"
                                  name="fjsm"></textarea>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
<script>
    var bdcdyfwlx = "${bdcdyfwlx!}";
    var fwIndex = "${fwIndex!}";
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var form = layui.form;
        var $ = layui.jquery;
        var laydate = layui.laydate;
        laydate.render({
            elem: '#dcsj',
            type: 'datetime'
        });
        //form初始化
        form.render();
        loadDcxx();
        form.on("submit(saveDcxx)",function(data){
            var postData = data.field;
            postData.fwIndex = fwIndex;
            postData.bdcdyfwlx = bdcdyfwlx;
            var formLayer = layer;
            if (parent.layer) {
                formLayer = parent.layer
            }
            // loading加载
            addModel();
            $.ajax({
                url: "../dcxx/savedcxx",
                dataType: "json",
                data: postData,
                success: function (data) {
                    removeModal();
                    if (data && data.success) {
                        formLayer.msg(data.msg);
                    } else {
                        formLayer.alert("提交失败")
                    }
                },
                error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr,this)
                }
            });

        });

        function loadDcxx(){
            // loading加载
            addModel();
            $.ajax({
                url: "../dcxx/querydcxx",
                dataType: "json",
                data: {
                    bdcdyfwlx: bdcdyfwlx,
                    fwIndex: fwIndex
                },
                success: function (data) {
                    removeModal();
                    //处理查询出来的数据
                    fillDcxx(data)
                },
                error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr,this)
                }
            });
        }
        function fillDcxx(data){
            form.val("form", data)
        }

    });
</script>
</body>

</html>
