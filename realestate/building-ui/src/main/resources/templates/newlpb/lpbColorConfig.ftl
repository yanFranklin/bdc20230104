<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>颜色配置</title>
    <script src="../lib/js/jquery.min.js"></script>
    <script src="../lib/js/jquerysession.js?v=01"></script>
    <link rel="stylesheet" href="../lib/layui/css/layui.css?v=2019-05-311124" media="all">
    <link rel="stylesheet" href="../lib/bdcui/css/mask.css?v=2019-05-311124">
    <link rel="stylesheet" href="../css/configColor.css?v=2020-05-311124"/>
    <script src="../lib/layui/layui.js"></script>
    <script src="../js/mask.js"></script>
    <script src="../js/common.js?v=2019-05-311124"></script>
    <script src="../js/yancheng/lpb/configColor.js?v=20210302"></script>
</head>
<body>
<div class="bdc-container">
    <!--标题  开始-->
    <div class="bdc-title">
        <span>颜色配置</span>
        <div class="bdc-btn-box">
            <a href="javascript:;" class="layui-btn bdc-major-btn" id="saveColor">保存</a>
        </div>
    </div>
    <div id="status"></div>
    <!--标题  结束-->

    <!--颜色选择区  结束-->

    <!--样例展示区  开始-->
    <div class="bdc-demo add-back-color">
        <p class="bdc-demo-title">预览</p>
        <table class="layui-table">
            <tbody>
            <tr>
                <td class="bdc-wdj-back">
                    <p>9-1512室</p>
                    <p class="bdc-state-show">
                        <span class="bdc-zjgcdy"></span>
                        <span class="bdc-ydy"></span>
                        <span class="bdc-dy"></span>
                        <span class="bdc-cf"></span>
                    </p>
                    <p class="bdc-state-show">
                        <span class="bdc-ks"></span>
                        <span class="bdc-cq"></span>
                    </p>
                    <p>成套住宅</p>
                    <p>27.01</p>
                </td>
                <td class="bdc-wdj-back">
                    <p>9-1512室</p>
                    <p class="bdc-state-show">
                        <span class="bdc-ycf"></span>
                        <span class="bdc-dj"></span>
                        <span class="bdc-yy"></span>
                    </p>
                    <p class="bdc-state-show">
                        <span class="bdc-ys"></span>
                    </p>
                    <p>成套住宅</p>
                    <p>27.01</p>
                </td>
                <td class="bdc-wdj-back">
                    <p>9-1512室</p>
                    <p class="bdc-state-show">
                        <span class="bdc-ydy"></span>
                        <span class="bdc-dy"></span>
                    </p>
                    <p class="bdc-state-show">
                        <span class="bdc-ks"></span>
                        <span class="bdc-cq"></span>
                    </p>
                    <p>成套住宅</p>
                    <p>27.01</p>
                </td>
                <td class="bdc-dj-back">
                    <p>9-1512室</p>
                    <p class="bdc-state-show">
                        <span class="bdc-ydy"></span>
                        <span class="bdc-dy"></span>
                    </p>
                    <p class="bdc-state-show">
                        <span class="bdc-ks"></span>
                        <span class="bdc-cq"></span>
                    </p>
                    <p>成套住宅</p>
                    <p>27.01</p>
                </td>
                <td class="bdc-dj-back">
                    <p>9-1512室</p>
                    <p class="bdc-state-show">
                        <span class="bdc-ydy"></span>
                        <span class="bdc-dy"></span>
                    </p>
                    <p class="bdc-state-show">
                        <span class="bdc-ks"></span>
                        <span class="bdc-cq"></span>
                    </p>
                    <p>成套住宅</p>
                    <p>27.01</p>
                </td>
                <td class="bdc-dj-back">
                    <p>9-1512室</p>
                    <p class="bdc-state-show">
                        <span class="bdc-ydy"></span>
                        <span class="bdc-dy"></span>
                    </p>
                    <p class="bdc-state-show">
                        <span class="bdc-ks"></span>
                        <span class="bdc-cq"></span>
                    </p>
                    <p>成套住宅</p>
                    <p>27.01</p>
                </td>
                <td class="bdc-zx-back">
                    <p>9-1512室</p>
                    <p class="bdc-state-show">
                        <span class="bdc-ydy"></span>
                        <span class="bdc-dy"></span>
                        <span class="bdc-zs"></span>
                    </p>
                    <p class="bdc-state-show">
                        <span class="bdc-ks"></span>
                        <span class="bdc-cq"></span>
                        <span class="bdc-zm"></span>
                    </p>
                    <p>成套住宅</p>
                    <p>27.01</p>
                </td>
                <td class="bdc-zx-back">
                    <p>9-1512室</p>
                    <p class="bdc-state-show">
                        <span class="bdc-ydy"></span>
                        <span class="bdc-dy"></span>
                        <span class="bdc-bfz"></span>
                    </p>
                    <p class="bdc-state-show">
                        <span class="bdc-ks"></span>
                        <span class="bdc-cq"></span>
                    </p>
                    <p>成套住宅</p>
                    <p>27.01</p>
                </td>
                <td class="bdc-zx-back">
                    <p>9-1512室</p>
                    <p class="bdc-state-show">
                        <span class="bdc-ydy"></span>
                        <span class="bdc-dy"></span>
                    </p>
                    <p class="bdc-state-show">
                        <span class="bdc-ks"></span>
                        <span class="bdc-cq"></span>
                    </p>
                    <p>成套住宅</p>
                    <p>27.01</p>
                </td>
            </tr>
            <tr>
                <td class="bdc-ba-back">
                    <p>9-1512室</p>
                    <p class="bdc-state-show">
                        <span class="bdc-ycf"></span>
                        <span class="bdc-dj"></span>
                    </p>
                    <p class="bdc-state-show">
                        <span class="bdc-ys"></span>
                        <span class="bdc-cq"></span>
                    </p>
                    <p>成套住宅</p>
                    <p>27.01</p>
                </td>
                <td class="bdc-ba-back">
                    <p>9-1512室</p>
                    <p class="bdc-state-show">
                        <span class="bdc-yy"></span>
                    </p>
                    <p class="bdc-state-show">
                        <span class="bdc-ks"></span>
                    </p>
                    <p>成套住宅</p>
                    <p>27.01</p>
                </td>
                <td class="bdc-ba-back">
                    <p>9-1512室</p>
                    <p class="bdc-state-show">
                        <span class="bdc-dyi"></span>
                        <span class="bdc-ydy"></span>
                    </p>
                    <p class="bdc-state-show">
                        <span class="bdc-ks"></span>
                        <span class="bdc-ys"></span>
                    </p>
                    <p>成套住宅</p>
                    <p>27.01</p>
                </td>
                <td class="bdc-wdj-back">
                    <p>9-1512室</p>
                    <p class="bdc-state-show">
                        <span class="bdc-ycf"></span>
                        <span class="bdc-dj"></span>
                    </p>
                    <p class="bdc-state-show">
                        <span class="bdc-ys"></span>
                        <span class="bdc-cq"></span>
                    </p>
                    <p>成套住宅</p>
                    <p>27.01</p>
                </td>
                <td class="bdc-wdj-back">
                    <p>9-1512室</p>
                    <p class="bdc-state-show">
                        <span class="bdc-yy"></span>
                    </p>
                    <p class="bdc-state-show">
                        <span class="bdc-ks"></span>
                    </p>
                    <p>成套住宅</p>
                    <p>27.01</p>
                </td>
                <td class="bdc-dj-back">
                    <p>9-1512室</p>
                    <p class="bdc-state-show">
                        <span class="bdc-dyi"></span>
                        <span class="bdc-ydy"></span>
                    </p>
                    <p class="bdc-state-show">
                        <span class="bdc-ks"></span>
                        <span class="bdc-ys"></span>
                    </p>
                    <p>成套住宅</p>
                    <p>27.01</p>
                </td>
                <td class="bdc-wdj-back">
                    <p>9-1512室</p>
                    <p class="bdc-state-show">
                        <span class="bdc-yy"></span>
                    </p>
                    <p class="bdc-state-show">
                        <span class="bdc-ks"></span>
                    </p>
                    <p>成套住宅</p>
                    <p>27.01</p>
                </td>
                <td class="bdc-wdj-back">
                    <p>9-1512室</p>
                    <p class="bdc-state-show">
                        <span class="bdc-dyi"></span>
                        <span class="bdc-ydy"></span>
                    </p>
                    <p class="bdc-state-show">
                        <span class="bdc-ks"></span>
                        <span class="bdc-ys"></span>
                        <span class="bdc-ba"></span>
                    </p>
                    <p>成套住宅</p>
                    <p>27.01</p>
                </td>
                <td class="bdc-wdj-back">
                    <p>9-1512室</p>
                    <p class="bdc-state-show">
                        <span class="bdc-dyi"></span>
                        <span class="bdc-ydy"></span>
                    </p>
                    <p class="bdc-state-show">
                        <span class="bdc-ks"></span>
                        <span class="bdc-ys"></span>
                        <span class="bdc-ba"></span>
                    </p>
                    <p>成套住宅</p>
                    <p>27.01</p>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <!--样例展示区  结束-->
</div>
</body>
<script type="text/html" id="statusTpl">
    <!--颜色选择区  开始-->
    {{# if(d.colorCofigs.length == 0) { }}
    <div class="bdc-color-module change-module-width">
        <p class="bdc-config-title">登记状态</p>
        <div class="bdc-config-color clear">
            <div class="bdc-color-list">
                <div class="bdc-one-color bdc-wdj-config"></div>
                <div class="layui-input-inline" style="width: 100px;">
                    <input type="text" value="" placeholder="字体颜色" class="layui-input" id="bdc-wdj-font" disabled="off">
                </div>
                <div class="bdc-one-color bdc-wdj-font"></div>
                未登记
            </div>
            <div class="bdc-color-list">
                <div class="bdc-one-color bdc-dj-config"></div>
                <div class="layui-input-inline" style="width: 100px;">
                    <input type="text" value="" placeholder="字体颜色" class="layui-input" id="bdc-dj-font" disabled="off">
                </div>
                <div class="bdc-one-color bdc-dj-font"></div>
                已登记
            </div>
            <div class="bdc-color-list">
                <div class="bdc-one-color bdc-zx-config"></div>
                <div class="layui-input-inline" style="width: 100px;">
                    <input type="text" value="" placeholder="字体颜色" class="layui-input" id="bdc-zx-font" disabled="off">
                </div>
                <div class="bdc-one-color bdc-zx-font"></div>
                已注销
            </div>
        </div>
    </div>
    <div class="bdc-color-module change-module-width">
        <p class="bdc-config-title">备案状态</p>
        <div class="bdc-config-color clear">
            <div class="bdc-color-list">
                <div class="bdc-one-color bdc-ba-config"></div>
                <div class="layui-input-inline" style="width: 100px;">
                    <input type="text" value="" placeholder="字体颜色" class="layui-input" id="bdc-ba-font" disabled="off">
                </div>
                <div class="bdc-one-color bdc-ba-font"></div>
                备案
            </div>
        </div>
    </div>
    <div class="bdc-color-module">
        <p class="bdc-config-title">交易状态</p>
        <div class="bdc-config-color clear">
            <div class="bdc-color-list">
                <div class="bdc-one-color bdc-ks-config"></div>
                可售
            </div>
            <div class="bdc-color-list">
                <div class="bdc-one-color bdc-ys-config"></div>
                已售
            </div>
            <div class="bdc-color-list">
                <div class="bdc-one-color bdc-cq-config"></div>
                草签
            </div>
            <div class="bdc-color-list">
                <div class="bdc-one-color bdc-wg-config"></div>
                物管用房
            </div>
            <div class="bdc-color-list">
                <div class="bdc-one-color bdc-az-config"></div>
                安置房
            </div>
            <div class="bdc-color-list">
                <div class="bdc-one-color bdc-bl-config"></div>
                保留房
            </div>
            <div class="bdc-color-list">
                <div class="bdc-one-color bdc-fpt-config"></div>
                非普通住宅
            </div>
        </div>
    </div>
    <div class="bdc-color-module">
        <p class="bdc-config-title">权利状态</p>
        <div class="bdc-config-color clear">
            <div class="bdc-color-list">
                <div class="bdc-one-color bdc-yg-config"></div>
                已预告
            </div>
            <div class="bdc-color-list">
                <div class="bdc-one-color bdc-zjgcdy-config"></div>
                在建工程抵押
            </div>
            <div class="bdc-color-list">
                <div class="bdc-one-color bdc-ydya-config"></div>
                预抵押
            </div>
            <div class="bdc-color-list">
                <div class="bdc-one-color bdc-dya-config"></div>
                抵押
            </div>
            <div class="bdc-color-list">
                <div class="bdc-one-color bdc-ycf-config"></div>
                预查封
            </div>
            <div class="bdc-color-list">
                <div class="bdc-one-color bdc-cf-config"></div>
                查封
            </div>
            <div class="bdc-color-list">
                <div class="bdc-one-color bdc-sd-config"></div>
                锁定
            </div>
            <div class="bdc-color-list">
                <div class="bdc-one-color bdc-yy-config"></div>
                异议
            </div>
            <div class="bdc-color-list">
                <div class="bdc-one-color bdc-dyi-config"></div>
                地役
            </div>
            <div class="bdc-color-list">
                <div class="bdc-one-color bdc-jzq-config"></div>
                居住权
            </div>
        </div>
    </div>
    <div class="bdc-color-module">
        <p class="bdc-config-title">发证状态</p>
        <div class="bdc-config-color clear">
            <div class="bdc-color-list">
                <div class="bdc-one-color bdc-zs-config"></div>
                生成证书
            </div>
            <div class="bdc-color-list">
                <div class="bdc-one-color bdc-zm-config"></div>
                生成证明书
            </div>
            <div class="bdc-color-list">
                <div class="bdc-one-color bdc-bfz-config"></div>
                只登簿不发证
            </div>
        </div>
    </div>
    {{# }else{ }}
    {{# layui.each(d.colorCofigs, function(index, colorConfig){ }}
    <div class="bdc-color-module change-module-width">
        <p class="bdc-config-title">{{colorConfig.title || ''}}</p>
        <div class="bdc-config-color clear">
            {{# for(var i in colorConfig.status) { }}
            <div class="bdc-color-list">
                <div class="bdc-one-color {{colorConfig.status[i] || ''}}-config"></div>
                {{#if(colorConfig.background){ }}
                {{# console.log(colorConfig.status[i]) }}
                <a class="bdc-one-color {{colorConfig.status[i] || ''}}-a"
                   onmouseenter="renderFont('{{colorConfig.status[i] || \'\'}}')">{{i || ''}}</a>
                <div class="bdc-one-color {{colorConfig.status[i] || ''}}-font bdc-hide"></div>
                {{# } else{ }}
                {{i || ''}}
                {{# } }}
            </div>
            {{# } }}
        </div>
    </div>
    {{# }); }}
    {{# } }}
</script>
</html>