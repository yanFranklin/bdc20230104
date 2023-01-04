<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0">
    <title>土地楼盘表</title>
    <script src="../lib/js/jquery.min.js"></script>
    <script src="../lib/js/jquerysession.js?v=01"></script>
    <link rel="stylesheet" href="../lib/font-awesome-4.7.0/css/font-awesome.css">
    <link rel="stylesheet" href="../lib/layui/css/layui.css?v=2019-09-02" media="all">
    <link rel="stylesheet" href="../lib/bdcui/css/form.css?v=2019-09-02"/>
    <link rel="stylesheet" href="../lib/form-select/formSelects-v4.css?v=2019-09-02"/>
    <link rel="stylesheet" href="../css/state.css?v=2019-09-02">
    <link rel="stylesheet" href="../css/building.css?v=2019-09-02">
    <link rel="stylesheet" href="../css/yztsxx.css?v=2019-09-02">
    <link rel="stylesheet" href="../lib/bdcui/css/application.css">
    <link rel="stylesheet" href="../lib/bdcui/css/mask.css">
    <link rel="stylesheet" href="../lib/bdcui/css/popup.css">
    <link rel="stylesheet" href="../css/index.css?v=2019-09-021">
    <!--添加水印-->
    <script src="../static/lib/bdcui/js/common.js"></script>
    <script src="../lib/layui/layui.js"></script>
    <script src="../js/common.js?v=2019-09-02"></script>
    <script src="../js/redirect.js?v=2019-09-02"></script>
    <@glo.globalVars />
</head>
<style>
    body {
        padding: 10px !important;
        box-sizing: border-box;
        background-color: #eaedf1;
    }
</style>
<body>
<div class="bdc-container hide-map" style="padding-top: 50px; padding-left: 0px;">
    <div class="bdc-lc-container " >
        <div class="bdc-lc-title">
            <p style="padding: 0 35px 0 0;">电子沙盘<span></span></p>
            <p id="lszd">地籍号：<span></span></p>
            <p id="tdzl" style="padding: 0 35px;">坐落：<span></span></p>
            <p id="lhdyqlzt" style="display: none;"><img src="../image/lhdya.png" alt=""></p>
            <p id="sfxxmzt" style="display: none;"><img src="../image/sfxxm.png" alt=""></p>
            <div class="bdc-operate-show first-click bdc-hide">
                <i class="layui-icon layui-icon-up"></i>
                <i class="layui-icon layui-icon-down bdc-hide"></i>
            </div>
        </div>
        <div id="relationMap" class="bdc-table-box">
            <#--<div class="map-item" style="width: 18%">
                <p>隶属宗地:</p><br>
                <p name="lszd"></p>
            </div>
            <div class="map-item" style="width: 11%">
                <p>实测建筑面积:</p><br>
                <p name="scjzmj"></p>
            </div>
            <div class="map-item" style="width: 11%">
                <p>预测建筑面积:</p><br>
                <p name="ycjzmj"></p>
            </div>
            <div class="map-item" style="width: 14%">
                <p>房屋结构:</p><br>
                <p name="fwjg"></p>
            </div>
            <div class="map-item zl-item">
                <p>坐落:</p><br>
                <p name="zldz"></p>
            </div>
            <div class="map-item bz-item">
                <p name="bz">备注: <span></span></p>
            </div>-->
        </div>
    </div>

    <div class="bdc-aside-zoom bdc-aside-close bdc-hide">
        <i class="layui-icon layui-icon-left"></i>
    </div>
    <div class="bdc-aside-zoom bdc-aside-open layui-hide">
        <i class="layui-icon layui-icon-right"></i>
    </div>
    <!--左侧菜单栏开始-->
    <div class="bdc-menu-aside">
        <div class="aside" id="asideCon"></div>
    </div>
    <!--左侧菜单栏结束-->

    <!--右侧主内容开始-->
    <div class="bdc-right-content">
        <input type="hidden" id="fwDcbIndex" value="${fwDcbIndex!}"/>
        <input type="hidden" id="bdcdyfwlx" value="${bdcdyfwlx!}"/>
        <input type="hidden" id="code" value="${code!}"/>
        <input type="hidden" id="zrzh" value=""/>
        <input type="hidden" id="djh" value="${djh!}"/>
        <input type="hidden" id="param" value="${param!}"/>
        <p class="bdc-content-title" id="ljzTitle"></p>
        <br>
        <p class="bdc-content-title bztitle" id="bzTitle"></p>
        <div class="layui-hide">
            <div lay-filter="tsxx" id="tsxx"></div>
        </div>
        <!--tab栏切换-->
        <div class="layui-tab" lay-filter="tabFilter" id="tabDiv">
            <#--<ul class="layui-tab-title layui-hide">-->
            <ul class="" style="display: none;">
                <li id="hsTabLi" class="layui-this">电子沙盘</li>
            </ul>
            <div class="layui-tab-content double-side">
                <div class="layui-tab-item lpb-tab layui-hide" id="zdTab">
                </div>
                <div class="layui-tab-item lpb-tab layui-hide" id="zrzTab">
                </div>
                <div class="layui-tab-item lpb-tab layui-hide" id="ljzTab">
                </div>
                <div class="layui-tab-item" id="ychsTab">
                    <input type="hidden" id="ycfwDcbIndex" value=""/>
                    <!--状态展示-->
                    <div class="layui-tab-item layui-show">
                        <div class="bdc-state-show-box">
                            <!--状态展示-->
                            <div class="bdc-state-show">
                            </div>
                            <div class="bdc-state-zoom layui-hide">
                                <i class="layui-icon layui-icon-down bdc-hide"></i>
                                <i class="layui-icon layui-icon-up"></i>
                            </div>
                        </div>
                        <div id="ychsYScroll" style="float:right;z-index:9999;position: relative;top:88px"></div>
                        <!--表格展示-->
                        <table id="ychsTableView" lay-filter="ychsTableLayFilter"></table>
                        <div id="ychsXScroll" style="margin-left: 180px"></div>
                    </div>
                </div>
                <div class="layui-tab-item layui-show" id="hsTab">
                    <div class="layui-tab-item layui-show">
                        <div class="bdc-state-show-box">
                            <!--状态展示-->
                            <div class="bdc-state-show">
                                <p>权利状态：
                                    <span><i class="bdc-ks"  style="background-color:rgb(0,153,68)!important"></i>已预告</span>
                                    <span><i class="bdc-ys" style="background-color:#e87a73!important"></i>抵押</span>
                                    <span><i class="bdc-cq" style="background-color:rgb(102,204,255)!important"></i>使用权未核减</span>
                                    <span><i class="bdc-cf" style="background-color: #e50012 !important"></i>查封</span>
                                </p>
                            </div>
                            <div class="bdc-state-zoom layui-hide">
                                <i class="layui-icon layui-icon-down bdc-hide"></i>
                                <i class="layui-icon layui-icon-up"></i>
                            </div>
                        </div>
                        <!-- 竖向 滚动条-->
                        <div id="hsYScroll" style="float:right;z-index:9999;position: relative;top:51px"></div>
                        <table id="hsTableView" lay-filter="hsTableLayFilter"></table>
                        <!-- 横向滚动条 -->
                        <div id="hsXScroll" style="margin-left: 180px"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--右侧主内容结束-->
</div>
<#--筛选条件弹出层-->
<div id="bdc-popup-small" class="bdc-hide">
    <div class="layui-form">
        <div class="layui-form-item pf-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">用途</label>
                <div class="layui-input-inline">
                    <select name="yt" xm-select="selectYt" xm-select-skin="default" xm-select-show-count="2"
                            xm-select-height="34px" xm-select-search="" xm-select-search-type="dl"></select>
                </div>
            </div>
<#--            <div class="layui-inline">-->
<#--                <label class="layui-form-label">交易状态</label>-->
<#--                <div class="layui-input-inline">-->
<#--                    <select name="jyzt" xm-select="selectJyzt" xm-select-skin="default" xm-select-show-count="2"-->
<#--                            xm-select-height="34px" xm-select-search="" xm-select-search-type="dl"></select>-->
<#--                </div>-->
<#--            </div>-->
        </div>
    </div>
</div>
</body>
<script type="text/html" id="mainTrTpl">
    {{# if(d){}}
    <#--单层循环-->
    {{#  layui.each(d.cFwList, function(index, item){ }}

        {{#  layui.each(item.cellElem, function(ind, val){ }}
    <tr>
        {{# layui.each(val, function(i, v){ }}
        <td style="width:125px;height: 120px;">
            {{# if(v.ZRZH==null&&v.BZGHMJBL==null&&(v.YGZT==0 && v.LHDYZT==0&&v.LHSDZT==0)||(v.YGZT==null && v.LHDYZT==null&&v.LHSDZT==null)){ }}
            <div>
            </div>

            {{# }else{ }}
            <div>
                <a onclick="toLpbView('{{v.FW_DCB_INDEX}}')"><p><span class="bdc-fh" title="" style="cursor: pointer;" data-wlcs="" >幢号：{{v.DH==null?'':v.DH}}</span>
                </p></a>
                <p>自然幢号：{{v.ZRZH==null?'':v.ZRZH}}</p>
                <p>量化占比：{{v.BZGHMJBL==null?'':v.BZGHMJBL}}</p>
                {{# if((v.YGZT==0 && v.LHDYZT==null)||(v.YGZT==0 && v.LHDYZT==0&&v.LHSDZT==1)||(v.YGZT==null && v.LHDYZT==null&&v.LHSDZT==1)){ }}
                <p>无权利状态</p>
                {{# }else{ }}
                <p class="bdc-table-state bdc-ql-state">
                    <span mark=""
                          {{# if(v.YGZT==1){ }}
                          style="background-color:rgb(0,153,68)!important"
                          {{#  }  }} class="bdc-dyi " title="已预告"></span>
                    <span mark="" {{#if(v.LHDYZT!=0){}}
                          style="background-color:#e87a73!important"
                          {{#  }  }} class="" title="抵押"></span>
                    <span mark="" {{#if(v.LHSDZT==0){}}
                          style="background-color:rgb(102,204,255)!important"
                          {{#  }  }} class="" title="使用权未核减"></span>
                    <span mark="" {{#if(v.LHCFZT!=0){}}
                          style="background-color: #e50012 !important"
                          {{#  }  }} class="" title="查封"></span>
                </p>
                {{# } }}
            </div>


            {{# } }}

        </td>
        {{# }); }}
    </tr>
    {{# }); }}
    {{# }); }}
    {{# } }}
</script>
<script type="text/html" id="headerTpl">
    <tr>
        <th data-field="wlcs" data-key="1-0-0" rowspan="2" style="height: 37px;" >
            <div class="layui-table-cell laytable-cell-1-0-0" align="center">
                <span>物理层数</span>
            </div>
        </th>
        <th data-field="dycs" data-key="1-0-1" rowspan="2" style="height: 37px;" >
            <div class="layui-table-cell laytable-cell-1-0-1" align="center">
                <span>定义层数</span>
            </div>
        </th>
    </tr>
</script>
<script type="text/html" id="toolbarTpl">
    <div class="layui-btn-container">

        {{# if(true){ }}
        {{# layui.each(d.btns, function(index, item){ }}
        <button class="layui-btn layui-btn-sm {{ item.btnClass }}" type="button"
                id="{{item.value}}"
                {{# if(item.btnEvent){ }}
                lay-event="{{ item.btnEvent }}"
                {{# }else if(item.btnCallback){ }}
                onclick="btnCallBack('{{item.btnCallback}}')"
                {{# } }}
        >{{ item.desc }}
        </button>
        {{# }); }}
        {{# } }}
        {{# if(d.moreBtns){ }}
        {{# for(var key in d.moreBtns){ }}
        <div class="bdc-more-box">
            <button class="layui-btn layui-btn-sm bdc-table-second-btn bdc-btn-more">
                {{ key }} <i class="layui-icon layui-icon-down"></i>
            </button>
            <div class="bdc-table-btn-more">
                {{# layui.each(eval('d.moreBtns.'+key), function(index, item){ }}
                <a href="javascript:void(0);" onclick="moreClick('{{item.btnEvent}}')">{{ item.desc }}</a>
                {{# }); }}
            </div>
        </div>
        {{# } }}
        {{# } }}
        {{# if(d.hssl){ }}
        <button class="layui-btn layui-btn-sm bdc-table-second-btn" type="button"
                id="hssl"
                onclick="selectHssl('{{d.hssl || \'\'}}')">
            每行展示数量:{{d.hssl||''}}
        </button>
        {{# } }}
    </div>
</script>
<script type="text/html" id="showColumnTpl">
    <div class="layui-btn-container bdc-export-tools layui-form" lay-filter="showColumForm">
        {{# if(d.show){ }}
        {{# layui.each(d.show, function(index, item){ }}
        <a href="javascript:;">
            <input type="checkbox"
                   data-key="{{item.value}}"
                   data-info="{{item.refInfo}}"
                   data-status="{{item.refStatus}}"
                   data-showfunction="{{item.showFunction}}"
                   lay-filter="showOthers"
                   title="{{item.desc}}"
                   lay-skin="primary"
                   {{#if(item.defaultShow){}}
                   checked="checked"
                   {{#}}}

            >
        </a>
        {{# }); }}
        {{# } }}
        {{# if(d.more && d.more.length > 0){ }}
        <span id="showMore" title="显示更多" class="bdc-more-box">
                <i class="fa fa-list" aria-hidden="true"></i>
                <div class="bdc-table-btn-more">
                    {{# layui.each(d.more, function(index, item){ }}
                        <span>
                            <input type="checkbox"
                                   data-key="{{item.value}}"
                                   data-info="{{item.refInfo}}"
                                   data-status="{{item.refStatus}}"
                                   lay-filter="showOthers"
                                   data-showfunction="{{item.showFunction}}"
                                   title="{{item.desc}}"
                                   lay-skin="primary"
                                   {{#if(item.defaultShow){}}
                                   checked="checked"
                                   {{#}}}
                            >
                        </span>
                    {{# }); }}
                </div>
            </span>
        {{# } }}
    </div>
</script>
<script type="text/html" id="hsToolbar">
</script>
<script type="text/html" id="ychsToolbar">
</script>
<script type="text/html" id="ychsState">
</script>
<script type="text/html" id="zdTreeTpl">
    {{# if(d.zdDjdcbDO && d.zdDjdcbDO.djh){ }}
    <h4>宗地号：{{ d.zdDjdcbDO.djh }}</h4>
    <div id="aside">
        <ul id="accordion" class="accordion">
            {{# layui.each(d.zrzList, function(index, item){ }}
            {{# if(item.fwLjzDOList){ }}
            <li class="open">
                {{# }else{ }}
            <li>
                {{# } }}
                <div class="link" onclick="showLjz('{{d.zdDjdcbDO.djh}}','{{item.zrzh}}')">
                    <i class="fa fa-caret-right"></i>
                    <img src="../image/file.png" alt="图标" class="docu-icon"/>
                    自然幢 {{item.zrzh}}
                </div>
                {{# if(item.fwLjzDOList){ }}
                {{# $("#zrzh").val(item.zrzh); }}
                <ul class="submenu" id="{{d.zdDjdcbDO.djh}}_{{item.zrzh}}" style="display: block;">
                    {{# layui.each(item.fwLjzDOList, function(index, ljz){ }}
                    <li>
                        {{# if(ljz.fwDcbIndex == $("#fwDcbIndex").val()){ }}
                        <a href="javascript:;" onclick="treeClickLjz('{{ljz.fwDcbIndex}}')" class="active">
                            {{# }else{ }}
                            <a href="javascript:;" onclick="treeClickLjz('{{ljz.fwDcbIndex}}')">
                                {{# } }}
                                {{# if(ljz.fwmc){}}
                                <img src="../image/log.png" alt="图标" class="docu-icon">
                                <span>{{ljz.fwmc ||''}}</span>
                                {{# }else{ }}
                                <img src="../image/log.png" alt="图标" class="docu-icon">
                                <span>{{ljz.ljzh ||''}}</span>
                                {{# } }}
                            </a>
                    </li>
                    {{# }); }}
                </ul>
                {{# }else{ }}
                <ul class="submenu" id="{{d.zdDjdcbDO.djh}}_{{item.zrzh}}"></ul>
                {{# } }}
            </li>
            {{# }); }}
        </ul>
    </div>
    {{# } }}
</script>
<script type="text/html" id="zdTreeLjzListTpl">
    {{# layui.each(d.data, function(index, ljz){ }}
    <li>
        {{# if(ljz.fwDcbIndex == $("#fwDcbIndex").val()){ }}
        <a href="javascript:;" onclick="treeClickLjz('{{ljz.fwDcbIndex}}')" class="active">
            {{# }else{ }}
            <a href="javascript:;" onclick="treeClickLjz('{{ljz.fwDcbIndex}}')">
                {{# } }}
                {{# if(ljz.fwmc){}}
                <img src="../image/log.png" alt="图标" class="docu-icon">
                <span>{{ljz.fwmc ||''}}</span>
                {{# }else{ }}
                <img src="../image/log.png" alt="图标" class="docu-icon">
                <span>{{ljz.ljzh ||''}}</span>
                {{# } }}
            </a>
    </li>
    {{# }); }}
</script>

<script type="text/html" id="DmMcTpl">
    {{# layui.each(d, function(index, zdItem){ }}
    <option value="{{zdItem.DM}}">{{zdItem.MC}}</option>
    {{# }); }}
</script>
<script type="text/html" id="tsxxTpl">
    <a class="layui-btn layui-btn-sm bdc-ignore-btn" id="ignoreAll" onclick="removeAll()">忽略全部</a>
    <div class="bdc-right-tips-box" id="bottomTips">
        <#--提示信息<i class="layui-icon layui-icon-close"></i>-->
        <div class="tsxx" id="alertInfo"></div>
        <div class="tsxx" id="confirmInfo"></div>
    </div>
</script>
<!--展示信息-->
<script type="text/html" id="zsxxTpl">
    <div class="bdc-zsxx clear">
        {{# layui.each(d, function(index, column){ }}
        <p class="bdc-zsnr"><span>{{column.desc}}：</span>{{column.value}}</p>
        {{# }); }}
    </div>
</script>
<script>
    // 外围系统传递的参数JSON
    var param = $.parseJSON(${param!});
    // 单个BDCDYH 选中样式
    var urlBdcdyh = '${bdcdyh!'kk'}';
    //获取页面版本
    var version = '${version!}';
    //获取展示逻辑装信息
    var showljzxx = '${showljzxx!}';
    //默认是否展现
    var defultShowLjzxx = '${defaultShow!}';
    //获取每行户室个数
    var chscount = '${chscount!}';
    layui.config({
        base: '../lib/' //此处路径请自行处理, 可以使用绝对路径
    }).extend({
        formSelects: 'form-select/formSelects-v4'
    });
    // 工作流实例ID
    var gzlslid = '${gzlslid!}';
    //    加载页面添加按钮定义id
    var showBtnDyids = '${showBtnDyidList!}';

    //    配置的列宽值
    var lpbtdwidth = '${lpbtdwidth!}';
    //    楼盘表页面是否展现交易状态，默认展现
    var showjyzt = '${showjyzt!}';
    //三维户室图url地址
    var swhstUrl = '${swhsturl!}';
    // 权籍管理代码
    var qjgldm = '${qjgldm!}';
    $(".bdc-lc-title #lszd span").html('${djh!}');
    $(".bdc-lc-title #tdzl span").html('${zl!}');

</script>
<script src="../js/newlpb/scrollbar.js?v=2020-05-27"></script>
<#--楼盘表换行 无横向滚动条-->
<script src="../js/yancheng/tdlpb/resource.js"></script>
<script src="../js/yancheng/tdlpb/index.js?v=2020-07-30"></script>
<script src="../js/yancheng/tdlpb/hsTab.js?v=2019-09-02"></script>
<script src="../js/yancheng/tdlpb/btn.js"></script>
<script src="../js/fwhs/hsbg.js?v=2019-09-02"></script>
<script src="../js/fwhs/fwhsgl.js?v=2019-09-02"></script>
<script src="../js/fwljz/ljzgl.js?v=2019-09-02"></script>
<script src="../js/newlpb/ychsTab.js?v=2019-09-02"></script>
<script src="../js/newlpb_linefeed/hbCheck.js?v=2021-04-01"></script>
<script src="../js/newlpb/zdtab.js?v=2019-09-02"></script>
<script src="../js/newlpb/zrztab.js?v=2019-09-02"></script>
<script src="../js/newlpb/ljztab.js?v=2019-09-02"></script>
<script src="../js/newlpb/zdTree.js?v=2019-09-02"></script>
<#--楼盘表底色显示状态-->
<script src="../js/newlpb_linefeed/state.js?v=2020-06-23-1"></script>
<script src="../js/newlpb/showColumn.js?v=2020-07-30"></script>
<#--楼盘表换行 物理层全选调整-->
<script src="../js/newlpb_linefeed/cart.js"></script>
<script src="../js/mask.js?v=2019-09-02"></script>
<script src="../js/newlpb/gzyz.js"></script>
<script src="../js/print.js"></script>
<script src="../static/lib/bdcui/js/print/print-common.js"></script>
</html>