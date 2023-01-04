<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0">
    <title>楼盘表</title>
    <script src="../lib/js/jquery.min.js"></script>
    <script src="../lib/js/jquerysession.js?v=01"></script>
    <link rel="stylesheet" href="../lib/font-awesome-4.7.0/css/font-awesome.css">
    <link rel="stylesheet" href="../lib/layui/css/layui.css?v=2019-05-311124" media="all">
    <link rel="stylesheet" href="../lib/bdcui/css/form.css?v=2019-05-311124"/>
    <link rel="stylesheet" href="../css/index.css?v=2019-05-311124">
    <link rel="stylesheet" href="../css/state.css?v=2019-05-311124">
    <link rel="stylesheet" href="../css/building.css?v=2019-05-311124">
    <link rel="stylesheet" href="../lib/bdcui/css/mask.css?v=2019-05-311124">
    <script src="../lib/layui/layui.js"></script>
    <script src="../js/common.js?v=2019-05-311124"></script>
    <script src="../js/redirect.js?v=2019-05-311124"></script>
    <@glo.globalVars />
</head>
<body>
<div class="bdc-container">
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
        <input type="hidden" id="djh" value=""/>
        <input type="hidden" id="param" value="${param!}"/>
        <p class="bdc-content-title" id="ljzTitle">坐落:</p>
        <!--tab栏切换-->
        <div class="layui-tab" lay-filter="tabFilter">
            <ul class="layui-tab-title layui-hide">
                <li id="zdTabLi" class="layui-hide">宗地基本信息</li>
                <li id="zrzTabLi" class="layui-hide">自然幢信息</li>
                <li id="ljzTabLi" class="layui-hide">逻辑幢信息</li>
                <li id="ychsTabLi" class="layui-hide">预测楼盘表</li>
                <li id="hsTabLi" class="layui-hide">楼盘表</li>
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
                        <!--表格展示-->
                        <table id="ychsTableView" lay-filter="ychsTableLayFilter"></table>
                    </div>
                </div>
                <div class="layui-tab-item layui-show" id="hsTab">
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
                        <!--表格展示-->
                        <table id="hsTableView" lay-filter="hsTableLayFilter"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--右侧主内容结束-->
</div>
</body>
<script type="text/html" id="toolbarTpl">
    <div class="layui-btn-container">
        {{# if(d.btns){ }}
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
    </div>
</script>
<script type="text/html" id="showColumnTpl">
    <div class="layui-btn-container bdc-export-tools">
        {{# if(d.show){ }}
            {{# layui.each(d.show, function(index, item){ }}
                <a href="javascript:;">
                    <input type="checkbox"
                           data-key="{{item.value}}"
                           data-info="{{item.refInfo}}"
                           data-status="{{item.refStatus}}"
                           lay-filter="showOthers"
                           title="{{item.desc}}"
                           lay-skin="primary">
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
                                   title="{{item.desc}}"
                                   lay-skin="primary">
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
<script>
    // 外围系统传递的参数JSON
    var param = $.parseJSON(${param!});
</script>
<script src="../js/lpb/resource.js?v=2019-05-311124"></script>
<script src="../js/lpb/index.js?v=2019-05-311124"></script>
<script src="../js/fwhs/hsbg.js?v=2019-05-311124"></script>
<script src="../js/fwhs/fwhsgl.js?v=2019-05-311124"></script>
<script src="../js/fwljz/ljzgl.js?v=2019-05-311124"></script>
<script src="../js/lpb/hsTab.js?v=2019-05-311124"></script>
<script src="../js/lpb/ychsTab.js?v=2019-05-311124"></script>
<script src="../js/lpb/hbCheck.js?v=2019-05-311124"></script>
<script src="../js/lpb/zdtab.js?v=2019-05-311124"></script>
<script src="../js/lpb/zrztab.js?v=2019-05-311124"></script>
<script src="../js/lpb/ljztab.js?v=2019-05-311124"></script>
<script src="../js/lpb/zdTree.js?v=2019-05-311124"></script>
<script src="../js/lpb/btn.js?v=2019-05-311124"></script>
<script src="../js/lpb/state.js?v=2019-05-311124"></script>
<script src="../js/lpb/showColumn.js?v=2019-05-311124"></script>
<script src="../js/lpb/cart.js?v=2019-05-311124"></script>
<script src="../js/mask.js?v=2019-05-311124"></script>
</html>