<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <title>地籍调查表</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link rel="stylesheet" href="../static/lib/font-awesome-4.7.0/css/font-awesome.min.css" media="all">
    <link rel="stylesheet" href="../static/lib/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="../static/lib/bdcui/css/form.css"/>
    <link rel="stylesheet" href="../static/lib/bdcui/css/search.css"/>
    <link rel="stylesheet" href="../static/lib/bdcui/css/table.css"/>
    <link rel="stylesheet" href="../static/lib/bdcui/css/popup.css">
    <link rel="stylesheet" href="../static/lib/bdcui/css/form-tab.css"/>
    <link rel="stylesheet" href="../static/lib/bdcui/css/mask.css">
    <link rel="stylesheet" href="../static/lib/viewerjs/dist/viewer.css">
    <link rel="stylesheet" href="../static/lib/bdcui/css/scan.css">
    <link rel="stylesheet" href="../css/building.css">
    <link rel="stylesheet" href="../static/lib/form-select/formSelects-v4.css">
    <script src="../lib/js/jquery.min.js"></script>
<#--    <script src="../lib/layui/layui.js"></script>-->
    <!-- layui-v2.6.8 -->
    <script src="../lib/bdcui/js/layui-v2.6.8/layui.js"></script>
    <script src="../js/djdcb.js"></script>
    <script src="../js/mask.js"></script>
    <script src="../lib/bdcui/js/table.js?v=2.1"></script>
    <script src="../js/common.js?v=2.1"></script>
    <script src="../lib/bdcui/js/common.js"></script>
<#--    <script src="../js/scan.js"></script>-->
    <script src="../js/lsgx/common.js"></script>
    <script src="../lib/bdcui/js/viewer.js"></script>
<#--    <script src="../lib/bdcui/js/response.js"></script>-->
</head>
<script>
    var tabArr = [];
    <#list tabNameList as tabName>
    tabArr.push('${tabName}');
    </#list>
</script>
<body>
<div class="bdc-form-div building-notitle" style="overflow: auto;">
    <!--全局 标题和操作按钮 开始-->
    <!--全局 标题和操作按钮 结束-->
    <div id="djdcbContent" class="layui-tab fixed-content" lay-filter="tabFilter">
        <#--        <div class="bdc-content-fix">-->

        <#--        </div>-->
        <ul class="layui-tab-title" id="liTable">
            <li <#if !jyq??>class="layui-this" </#if> id="zddcb">宗地调查表</li>
            <li id="jyqdkxx" class="layui-hide <#if jyq??>layui-this</#if>">土地经营权地块调查表</li>
            <li id="qsdc">权属调查信息</li>
            <li id="jzbsb">界址标示表</li>
            <li id="fwxx" class="layui-hide">房屋信息</li>
            <li id="zdt">宗地图</li>
            <li id="fcfht" class="layui-hide">分层分户图</li>
            <li id="fcfht2" class="layui-hide">分层分户图</li>
            <li id="fcfhthefei" class="layui-hide">分层分户图</li>
            <li id="omp" class="layui-hide">不动产单元定位</li>
            <li id="sllmxx" class="layui-hide">森林林木信息表</li>
            <li id="cbzdxx" class="layui-hide">土地承包经营权、农用地其他使用权调查表</li>
            <li id="flmjxx" class="layui-hide">承包经营权宗地分类面积表</li>
            <div class="title-btn-area" style="position: absolute;    left: 95%;
    z-index: 999;
    top: 4px;">
                <button class="layui-btn bdc-secondary-btn print-btn" type="button" id="printButton">打印</button>
            </div>
        </ul>
        <ul class="title-ui" style="display: none" id="print">
            <li class="print" onclick="printData('zddcb')" id="">宗地调查表</li>
            <li class="print" onclick="printData('qsdcxx')" id="">权属调查信息</li>
            <li class="print" onclick="printData('jzbsb')" id="printSqd">界址标识表</li>
            <li class="print" onclick="printData('fwxx')" id="printSqd">房屋信息</li>
            <li class="print" onclick="printData('zdt')" id="printSqd">宗地图</li>
            <li class="print" onclick="printData('fcfht')" id="printSqd">分层分户图</li>
        </ul>
        <div class="layui-tab-content" id="contentTable">
            <div class="layui-form-item layui-hide">
                <input type="text" class="layui-input" name="bdcdyh" id="bdcdyh" value="${bdcdyh!}">
                <input type="text" class="layui-input" name="qjgldm" id="qjgldm" value="${qjgldm!}">
                <input type="text" class="layui-input" name="dzwtzm" id="dzwtzm" value="${dzwtzm!}">
                <input type="text" class="layui-input" name="cbzd" id="cbzd" value="${cbzd!}" />
                <input type="text" class="layui-input" name="jyq" id="jyq" value="${jyq!}" />
            </div>
            <!-- 宗地信息-->
            <div class="layui-tab-item <#if !jyq??>layui-show </#if>" id="zdxxdcb">
                <div class="form-margin-area">
                    <div id="zddcbContent">
                        <#include "djdcbZdxx.ftl" />
                    </div>
                </div>
            </div>

            <!-- 土地经营权地块调查表 TAB -->
            <div class="layui-tab-item <#if jyq??>layui-show </#if>" id="jyqdcb">
                <div class="form-margin-area">
                    <div id="jyqdkxxContent" class="djdcb-iframe-div">
                        <#include "jyqdkxx.ftl" />
                    </div>
                </div>
            </div>
            <!-- 权属调查信息 -->
            <div class="layui-tab-item">
                <div class="form-margin-area">
                    <div id="qsdcContent">
                        <#include "djdcbQsdc.ftl" />
                    </div>
                </div>
            </div>
            <!-- 宗地界址信息 -->
            <div class="layui-tab-item">
                <div class="form-margin-area">
                    <div id="jzbsbContent">
                        <#include "djdcbZdjzxxList.ftl" />
                    </div>
                </div>
            </div>
            <!-- 户室信息 -->
            <div class="layui-tab-item">
                <div class="form-margin-area">
                    <div id="fwxxContent">
                        <#include "djdcbFwhsInfo.ftl" />
                    </div>
                </div>
            </div>
            <!-- 宗地图 -->
            <div class="layui-tab-item">
                <div class="form-margin-area">
                    <div id="zdtContent">
                        <#include "zdtView.ftl" />
                    </div>
                </div>
            </div>
            <!-- 分层分户图 -->
            <div class="layui-tab-item">
                <div class="form-margin-area">
                    <div id="fcfhtContent">
                        <#include "fcfhtView.ftl" />
                    </div>
                </div>
            </div>
            <!-- 分层分户图2 -->
            <div class="layui-tab-item">
                <div class="form-margin-area">
                    <div id="fcfht2Content">
                        <#include "fcfht2View.ftl" />
                    </div>
                </div>
            </div>
            <!-- 分层分户图2 -->
            <div class="layui-tab-item">
                <div class="form-margin-area">
                    <div id="fcfht2Content">
                        <#include "fcfhthefeiView.ftl" />
                    </div>
                </div>
            </div>
            <!-- 一张图定位 -->
            <div class="layui-tab-item">
                <div class="form-margin-area">
                    <div id="ompContent" class="djdcb-iframe-div">
                        <#include "ompView.ftl" />
                    </div>
                </div>
            </div>
            <!-- 森林林木信息TAB -->
            <div class="layui-tab-item">
                <div class="form-margin-area">
                    <div id="sllmxxContent" class="djdcb-iframe-div">
                        <#include "sllmxx.ftl" />
                    </div>
                </div>
            </div>

            <!-- 土地承包经营权、农用地其他使用权调查表 TAB -->
            <div class="layui-tab-item">
                <div class="form-margin-area">
                    <div id="cbzdxxContent" class="djdcb-iframe-div">
                        <#include "cbzdxx.ftl" />
                    </div>
                </div>
            </div>
            <!-- 承包经营权宗地分类面积表 TAB-->
            <div class="layui-tab-item">
                <div class="form-margin-area">
                    <div id="flmjxxContent" class="djdcb-iframe-div">
                        <#include "flmjxx.ftl" />
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>
</body>
<script src="../js/djdcb/bdcdjdcb.js?v=1"></script>
<script src="../js/djdcb/djdcbZdxx.js"></script>
<script src="../js/djdcb/djdcbQsdc.js"></script>
<script src="../js/djdcb/djdcbZdjzxxList.js"></script>
<script src="../lib/bdcui/js/form.js"></script>
<script src="../js/form.js"></script>
<script src="../js/djdcb/djdcbFwhsInfo.js"></script>
<script src="../js/djdcb/zdtView.js"></script>
<script src="../js/djdcb/fcfhtView.js"></script>
<script src="../js/djdcb/wmmView.js"></script>
<script src="../js/djdcb/fcfhthefeiView.js"></script>
<script src="../js/djdcb/ompView.js"></script>
<script src="../js/djdcb/djdcbSllmxx.js"></script>
<script src="../js/djdcb/djdcbCbzdxx.js?v=3"></script>
<script src="../js/djdcb/djdcbFlmjxx.js"></script>
<script src="../static/lib/bdcui/js/print/print-common.js"></script>
<script src="../js/print.js"></script>
</html>