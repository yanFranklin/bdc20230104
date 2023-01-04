<!DOCTYPE html>
<html lang="zh-cn">

<head>
    <meta charset="utf-8">
    <title>坐落刷新</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link rel="stylesheet" href="../lib/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="../lib/bdcui/css/common.css">
    <link rel="stylesheet" href="../lib/bdcui/css/form.css">
    <link rel="stylesheet" href="../lib/bdcui/css/form-tab.css?v=1.003"/>
    <link rel="stylesheet" href="../lib/bdcui/css/mask.css">
    <link rel="stylesheet" href="../css/building.css?4333">
    <script src="../js/mask.js?v=1.4045"></script>
    <script src="../lib/layui/layui.js"></script>
    <script src="../lib/js/jquery.min.js"></script>
    <script src="../js/common.js"></script>
</head>
<body>
<div class="bdc-form-div">
    <form class="layui-form setOverflow" lay-filter="form">
        <div class="bdc-content-fix">
        <div class="content-title layui-clear">
            <p>坐落刷新</p>
            <div class="title-btn-area">
                <button class="layui-btn bdc-secondary-btn" id="chooseZd" type="button">选择宗地</button>
                <button class="layui-btn bdc-secondary-btn" id="chooseLjz" type="button">选择逻辑幢</button>
                <button class="layui-btn bdc-major-btn" lay-submit="" lay-filter="subBtn"
                        id="subBtn" type="button">提交</button>
            </div>
        </div>
        </div>
        <div class="form-margin-area building-table-area">
            <div class="layui-form-item layui-hide">
                <div class="layui-inline margin-top-ipt">
                    <label class="layui-form-label change-label-width">fwDcbIndex</label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" name="fwDcbIndex" id="fwDcbIndex"
                               value="">
                    </div>
                </div>
            </div>
            <div class="basic-info">
                <div class="layui-form-item">
                    <div class="layui-inline margin-top-ipt bdc-two-column">
                        <label class="layui-form-label">宗地号</label>
                        <div class="layui-input-inline">
                            <input type="text" class="layui-input" name="djh" id="djh" readonly>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline margin-top-ipt bdc-two-column">
                        <label class="layui-form-label">逻辑幢</label>
                        <div class="layui-input-inline">
                            <input type="text" class="layui-input" id="fwmc" readonly>

                        </div>
                    </div>
                </div>
                <div class="layui-form-item change-textarea-margin">
                    <label class="layui-form-label">拼接规则</label>
                    <div class="layui-input-inline change-textarea-width" id="pjgzBtns">
                    </div>
                </div>
                <div class="layui-form-item layui-show">
                    <div class="layui-inline bdc-two-column">
                        <label class="layui-form-label">拼接元素类型</label>
                        <div class="layui-input-inline">
                            <input type="radio" name="pjtype" lay-filter="pjtype" value="zd" title="表字段" checked="" class="change-radio-top">
                            <input type="radio" name="pjtype" lay-filter="pjtype" value="xzq" title="行政区" class="change-radio-top">
                            <input type="radio" name="pjtype" lay-filter="pjtype" value="wz" title="文字" class="change-radio-top">
                        </div>
                    </div>
                </div>

                <!--表字段类型 -->
                <div id="zdDiv" class="layui-form-item layui-show pjtype">
                        <label class="layui-form-label">表字段</label>
                        <div class="layui-input-inline zlsxinput">
                            <select id="chooseTable" lay-filter="chooseTable">
                                <option value="">请选择表</option>
                                <option value="FW_LJZ">FW_LJZ</option>
                                <option value="FW_HS">FW_HS</option>
                            </select>
                        </div>
                        <div class="layui-input-inline zlsxinput">
                            <select id="chooseColumn"  lay-filter="chooseColumn">
                                <option value="">请选择字段</option>
                            </select>
                        </div>
                        <div class="layui-input-inline zlsxinput">
                            <button class="layui-btn bdc-secondary-btn"  type="button" id="addZd">
                                <i class="layui-icon">&#xe654;</i>
                            </button>
                        </div>
                </div>
                <!--行政区类型 -->
                <div id="xzqDiv"  class="layui-form-item layui-show layui-hide pjtype">
                        <label class="layui-form-label change-label-width bdc-label-newline">行政区代码位数</label>
                        <div class="layui-input-inline zlsxinput">
                            <select name="xzqdmLen" id="xzqdmLen">
                                <option value="2">2</option>
                                <option value="4">4</option>
                                <option value="6">6</option>
                                <option value="9">9</option>
                                <option value="12">12</option>
                            </select>
                        </div>
                        <div class="layui-input-inline zlsxinput">
                            <button class="layui-btn bdc-secondary-btn" type="button" id="addXzq">
                                <i class="layui-icon">&#xe654;</i>
                            </button>
                        </div>
                </div>

                <!-- 文字类型 -->
                <div id="wzDiv"  class="layui-form-item layui-show layui-hide pjtype">
                        <label class="layui-form-label">拼接文字</label>
                        <div class="layui-input-inline zlsxinput">
                            <input type="text" class="layui-input" id="pjwz" />
                        </div>
                        <div class="layui-input-inline zlsxinput">
                            <button class="layui-btn bdc-secondary-btn" type="button" id="addWz">
                                <i class="layui-icon">&#xe654;</i>
                            </button>
                        </div>
                </div>

                <div class="layui-form-item ">
                    <label class="layui-form-label">仅刷新空值</label>
                    <div class="layui-input-inline">
                        <input type="checkbox" lay-filter="nullOnlyF"  id="nullOnly" checked="" lay-skin="switch" lay-text="ON|OFF" />
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
<script type="text/html" id="chooseTableTpl">
    {{# layui.each(d.data, function(index, item){ }}
    <option value="{{ item }}">{{ item }}</option>
    {{# }); }}
</script>
<script type="text/html" id="pjgzTpl">
    {{# layui.each(d.data, function(index, item){ }}
    <button class="layui-btn bdc-secondary-btn" type="button">
        {{ item }}
        <i class="layui-icon layui-unselect layui-tab-close" lay-event="closeTab" >&#x1006;</i>
    </button>
    {{# }); }}
</script>
<script src="../js/manage/zlsx.js"></script>

</body>

</html>
