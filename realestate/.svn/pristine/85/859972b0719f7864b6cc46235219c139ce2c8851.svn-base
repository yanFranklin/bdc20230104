<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0">
    <title>楼盘表</title>
    <script src="../lib/js/jquery.min.js"></script>
    <script src="../lib/js/jquerysession.js?v=01"></script>
    <link rel="stylesheet" href="../lib/font-awesome-4.7.0/css/font-awesome.css">
    <link rel="stylesheet" href="../lib/layui/css/layui.css?v=2019-09-02" media="all">
    <link rel="stylesheet" href="../lib/bdcui/css/form.css?v=2019-09-02"/>
    <link rel="stylesheet" href="../lib/form-select/formSelects-v4.css?v=2019-09-02"/>
    <link rel="stylesheet" href="../css/index.css?v=2019-09-02">
    <link rel="stylesheet" href="../css/state.css?v=2019-09-02">
    <link rel="stylesheet" href="../css/building.css?v=2019-09-02">
    <link rel="stylesheet" href="../css/yztsxx.css?v=2019-09-02">
    <link rel="stylesheet" href="../lib/bdcui/css/mask.css">
    <link rel="stylesheet" href="../lib/bdcui/css/popup.css">
    <script src="../lib/layui/layui.js"></script>
    <script src="../js/common.js?v=2019-09-02"></script>
    <script src="../js/redirect.js?v=2019-09-02"></script>
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
        <p class="bdc-content-title" id="ljzTitle"></p>
        <div class="layui-hide">
            <div lay-filter="tsxx" id="tsxx"></div>
        </div>
        <!--tab栏切换-->
        <div class="layui-tab" lay-filter="tabFilter" id="tabDiv">
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
                    <!--展示信息-->
                    <#--<div class="bdc-zsxx clear">-->
                        <#--<p class="bdc-zsnr"><span>&nbsp;&nbsp;&nbsp;总套数：</span>70</p>-->
                        <#--<p class="bdc-zsnr"><span>地下套数：</span>30</p>-->
                        <#--<p class="bdc-zsnr"><span>地上套数：</span>40</p>-->
                        <#--<p class="bdc-zsnr"><span>坐落：</span>友谊县友谊镇030地籍子区144号华兴综合楼</p>-->
                    <#--</div>-->
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
                    <!--展示信息-->
                    <#--<div class="bdc-zsxx clear">-->
                        <#--<p class="bdc-zsnr"><span>总套数：</span>70</p>-->
                        <#--<p class="bdc-zsnr"><span>地下套数：</span>30</p>-->
                        <#--<p class="bdc-zsnr"><span>地上套数：</span>40</p>-->
                        <#--<p class="bdc-zsnr"><span>坐落：</span>友谊县友谊镇030地籍子区144号华兴综合楼</p>-->
                    <#--</div>-->
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
                        <#--<table id="hsTableView" lay-filter="hsTableLayFilter">-->
                            <#---->
                        <#--</table>-->
                        <#--<table id="newTableView" class="layui-table"></table>-->
                        <table id="hsTableView" lay-filter="hsTableLayFilter">
                        </table>
                        <#--<table id="newTableView" lay-filter="demo" class="layui-table"></table>-->
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
                    <select name="yt" xm-select="selectYt" xm-select-skin="default" xm-select-show-count="2" xm-select-height="34px" xm-select-search="" xm-select-search-type="dl"></select>
                </div>
            </div>
            <#--<div class="layui-inline">-->
                <#--<label class="layui-form-label">坐落</label>-->
                <#--<div class="layui-input-inline">-->
                    <#--<input type="text" autocomplete="off" placeholder="请输入" class="layui-input bdc-zl">-->
                    <#--<i class="layui-icon layui-icon-close bdc-hide"></i>-->
                <#--</div>-->
            <#--</div>-->
        </div>
    </div>
</div>
</body>
<script type="text/html" id="newTableTpl">
    <thead>
    <tr>
        <th rowspan="2" lay-data="{field:'wlcs',width:90,align:'center',fixed:'left'}">物理层数</th>
        <th rowspan="2" lay-data="{field:'dycs',width:90,align:'center',fixed:'left'}">定义层数</th>
        {{#  layui.each(d.data.dyList, function(index, item){ }}
            {{# if(item.colspan == 1){ }}
                <th rowspan="2" lay-data="{field:'testRow{{index}}',width:110}">{{item.dyh}}</th>
            {{# }else{ }}
                <th colspan="{{item.colspan}}" lay-data="{field:'testCol{{index}}'}">{{item.dyh}}</th>
            {{# } }}
        {{#  }); }}
    </tr>
    <tr>
        {{#  layui.each(d.data.dyList, function(index, item){ }}
        {{# if(item.colspan != 1){ }}
        {{# for(var i = 0; i < item.colspan; i++){ }}
        <th lay-data="{field:'testAll{{index}}{{i}}',width:110}">{{i}}</th>
        {{# } }}
        {{# } }}
        {{#  }); }}
    </tr>
    </thead>
    <tbody>
    {{# if (d.data.cFwList && d.data.cFwList.length > 0) { }}
    {{# var item = d.data.cFwList[0]; }}
    <tr>
        <td>{{item.wlcs?item.wlcs:''}}</td>
        <td>{{item.dycs?item.dycs:''}}</td>
        <!--先循环放单列的-->
        {{#  layui.each(item.dyFwList, function(ind, value){ }}
        {{#  if(value.maxHs == 1){ }}
        {{#  if(value.fwhsResourceDTOList.length === 0){ }}
        <td></td>
        {{# }else{ }}
        {{#  layui.each(value.fwhsResourceDTOList, function(i, v){ }}
            {{#  if(JSON.stringify(v) == "{}"){ }}
            <td></td>
            {{# } else {  }}
                {{# if(v && v.info && v.info.fwHsIndex){}}
                    <td>
                        <div class="bdc-td-show">
                            <div class="layui-form cell-checkbox">
                                <input type="checkbox" name="house" lay-skin="primary" lay-filter='checkFilter' >
                            </div>
                        </div>
                    </td>
                {{# } }}
            {{# } }}
        {{#  }); }}
        {{#  } }}
        {{# } }}
        {{#  }); }}

        <!--二次循环放多列的-->
        {{#  layui.each(item.dyFwList, function(ind, value){ }}
        {{#  if(value.maxHs != 1){ }}
        {{#  if(value.fwhsResourceDTOList.length === 0){ }}
            {{# for(var i = 0; i < value.maxHs; i++){ }}
                <td></td>
            {{# } }}
        {{# }else{ }}
            {{#  for(var i = 0; i < value.maxHs; i++){ }}
                {{#  var v = value.fwhsResourceDTOList[i];}}
                    {{#  if(!v || JSON.stringify(v) == "{}"){ }}
                        <td></td>
                    {{# } else {  }}
                        {{# if(v && v.info && v.info.fwHsIndex){}}
                            <td>
                                <div class="bdc-td-show">
                                    <div class="layui-form cell-checkbox" lay-filter="tableFilter">
                                        <input type="checkbox" name="house" lay-skin="primary" lay-filter='checkFilter' >
                                        <div class="layui-unselect layui-form-checkbox bdc-checkbox" lay-skin="primary"><i class="layui-icon layui-icon-ok"></i></div>
                                    </div>
                                </div>
                            </td>
                    {{#  } }}
                {{#  } }}
            {{#  } }}
        {{# for(var j = value.fwhsResourceDTOList.length; j < value.maxHs; j++){ }}
        <td></td>
        {{# } }}
        {{#  } }}
        {{# } }}
        {{#  }); }}
    </tr>
    {{# }else{ }}
        <!--车库 begin newTableTpl 只循环第一层 -->
        {{#if(d.data.lpbCkVo && d.data.lpbCkVo.ckList){}}
            {{# var wlcs = d.data.lpbCkVo.wlcs;}}
            {{# var dycs = d.data.lpbCkVo.dycs;}}
            {{# var ckList = d.data.lpbCkVo.ckList;}}
            {{# var maxnum = d.data.lpbCkVo.maxnum;}}
            {{# var count = 0;}}
            <tr>
                <td>{{wlcs}}</td>
                <td>{{dycs}}</td>
                {{# for(var j = 0 ;j < maxnum ; j++){ }}
                    {{# if(ckList[count]){}}
                        {{# var v = ckList[count];}}
                        {{# if(v && v.info && v.info.fwHsIndex){}}
                            <td>
                                <div class="bdc-td-show">
                                    <div class="layui-form cell-checkbox" lay-filter="tableFilter">
                                        <input type="checkbox" name="house" lay-skin="primary" lay-filter='checkFilter' >
                                        <div class="layui-unselect layui-form-checkbox bdc-checkbox" lay-skin="primary"><i class="layui-icon layui-icon-ok"></i></div>
                                    </div>
                                </div>
                            </td>
                        {{# } }}
                    {{# }else{}}
                        <td><div class="layui-table-cell"></div></td>
                    {{# } }}
                    {{# count++; }}
                {{# } }}
            </tr>
        {{# } }}
        <!--车库 end newTableTpl -->
    {{# } }}
    </tbody>
</script>
<script type="text/html" id="tableMainTpl">
    <#--</script>-->
<#--<script type="text/html" id="tableMainTpl2">-->
    <table cellspacing="0" cellpadding="0" border="0" class="layui-table">
        <tbody>
        {{# if(d.data){}}
        {{#  layui.each(d.data.cFwList, function(index, item){ }}
        <tr data-index="0" wlcs="{{item.wlcs}}">
            <td data-field="wlcs" data-key="1-0-0" align="center" class="">
                <div class="layui-table-cell laytable-cell-1-0-0">{{item.wlcs}}</div></td>
            <td data-field="dycs" data-key="1-0-1" align="center" class="">
                <div class="layui-table-cell laytable-cell-1-0-1">{{item.dycs}}</div></td>
            {{#  layui.each(item.dyFwList, function(ind, value){ }}
            {{#  if(value.fwhsResourceDTOList.length === 0){ }}
                {{#  if(value.maxHs == 1){ }}
                    <td><div class="layui-table-cell"></div></td>
                {{# }else { }}
                    {{# for(var i = 0; i < value.maxHs; i++){ }}
                        <td><div class="layui-table-cell"></div></td>
                    {{# } }}
                {{# } }}
            {{# }else{ }}
                {{#  for(var i = 0; i < value.maxHs; i++){ }}
                    {{#  var v = value.fwhsResourceDTOList[i];  }}
                    {{#  if(!v || JSON.stringify(v) == "{}"){ }}
                        <td><div class="layui-table-cell"></div></td>
                    {{# } else {  }}
                        {{# if(v && v.info && v.info.fwHsIndex){}}
                            {{# var jsoninfo = JSON.stringify(v.info); }}
                            {{# var dyh = v.info && v.info.dyh ? v.info.dyh.value:''; }}
                            {{# var fwHsIndex = v.info && v.info.fwHsIndex ? v.info.fwHsIndex.value:''; }}
                            {{# var bdcdyh = v.info && v.info.bdcdyh ? v.info.bdcdyh.value:''; }}
                            {{# var ghytdm = v.info && v.info.ghyt && v.info.ghyt.dm ? v.info.ghyt.dm:''; }}
                            {{# var zl = v.info && v.info.zl ? v.info.zl.value:''; }}
                            {{# var fjhHtml = v.info && v.info.fjh ? v.info.fjh.html:''; }}
                            {{# var qlztHtml = v.info && v.info.qlztHtml ? v.info.qlztHtml:''; }}
                            {{# var jyztHtml = v.info && v.info.jyztHtml ? v.info.jyztHtml:''; }}
                            {{# var descHtml = v.info && v.info.descHtml ? v.info.descHtml:''; }}
                            {{# var checkedflag = v.info && v.info.checkedflag ? v.info.checkedflag.value:''; }}
                            <td class="">
                                <div class="layui-table-cell">
                                    <div class="bdc-td-show">
                                        <div class="layui-form cell-checkbox" lay-filter="tableFilter">
                                            <input type="checkbox" name="house" lay-skin="primary" lay-filter='checkFilter'
                                                   data-dyh="{{dyh}}"
                                                   data-bdcdyh="{{bdcdyh}}"
                                                   data-ghyt="{{ghytdm}}"
                                                   data-index='{{fwHsIndex}}'
                                                   data-zl='{{zl}}' />
                                            <div class="layui-unselect layui-form-checkbox bdc-checkbox" lay-skin="primary"><i class="layui-icon layui-icon-ok"></i></div>
                                        </div>
                                        {{fjhHtml}}
                                        {{qlztHtml}}
                                        {{jyztHtml}}
                                        {{descHtml}}
                                    </div>
                                </div>
                                {{# if(urlBdcdyh == bdcdyh || checkedflag == '1'){}}
                                <div class="bdc-border" style="pointer-events:none"></div><img class="bdc-mark" style="pointer-events:none" src="../image/mark.png" />
                                {{# }}}
                            </td>
                        {{#  } }}
                    {{# } }}
                {{#  } }}
            {{#  } }}
            {{#  }); }}
        </tr>
        {{#  }); }}
        {{# } }}

        <!--车库 begin tableMainTpl -->
            {{#if(d.data.lpbCkVo && d.data.lpbCkVo.ckList){}}
                {{# var rowspan = d.data.lpbCkVo.rowspan;}}
                {{# var wlcs = d.data.lpbCkVo.wlcs;}}
                {{# var dycs = d.data.lpbCkVo.dycs;}}
                {{# var ckList = d.data.lpbCkVo.ckList;}}
                {{# var maxnum = d.data.lpbCkVo.maxnum;}}
                {{# var count = 0;}}
                {{# for(var i = 0 ; i < rowspan ; i++){}}
                    <tr data-index="0">
                        <td data-field="wlcs" data-key="1-0-0" align="center" class="">
                            <div class="layui-table-cell laytable-cell-1-0-0">{{wlcs}}</div>
                        </td>
                        <td data-field="dycs" data-key="1-0-1" align="center" class="">
                            <div class="layui-table-cell laytable-cell-1-0-1">{{dycs}}</div>
                        </td>
                        {{# for(var j = 0 ;j < maxnum ; j++){ }}
                            {{# if(ckList[count]){}}
                                {{# var v = ckList[count];}}
                                {{# if(v && v.info && v.info.fwHsIndex){}}
                                    {{# var jsoninfo = JSON.stringify(v.info); }}
                                    {{# var dyh = v.info && v.info.dyh ? v.info.dyh.value:''; }}
                                    {{# var bdcdyh = v.info && v.info.bdcdyh ? v.info.bdcdyh.value:''; }}
                                    {{# var ghytdm = v.info && v.info.ghyt&& v.info.ghyt.dm  ? v.info.ghyt.dm:''; }}
                                    {{# var zl = v.info && v.info.zl ? v.info.zl.value:''; }}
                                    {{# var fjhHtml = v.info && v.info.fjh ? v.info.fjh.html:''; }}
                                    {{# var qlztHtml = v.info && v.info.qlztHtml ? v.info.qlztHtml:''; }}
                                    {{# var jyztHtml = v.info && v.info.jyztHtml ? v.info.jyztHtml:''; }}
                                    {{# var descHtml = v.info && v.info.descHtml ? v.info.descHtml:''; }}
                                    {{# var checkedflag = v.info && v.info.checkedflag ? v.info.checkedflag.value:''; }}
                                    <td class="">
                                        <div class="layui-table-cell">
                                            <div class="bdc-td-show">
                                                <div class="layui-form cell-checkbox" lay-filter="tableFilter">
                                                    <input type="checkbox" name="house" lay-skin="primary" lay-filter='checkFilter'
                                                           data-dyh="{{dyh}}"
                                                           data-bdcdyh="{{bdcdyh}}"
                                                           data-ghyt="{{ghytdm}}"
                                                           data-index='{{v.info.fwHsIndex.value}}'
                                                           data-zl="'{{zl}}'"/>
                                                    <div class="layui-unselect layui-form-checkbox bdc-checkbox" lay-skin="primary"><i class="layui-icon layui-icon-ok"></i></div>
                                                </div>
                                                {{fjhHtml}}
                                                {{qlztHtml}}
                                                {{jyztHtml}}
                                                {{descHtml}}
                                            </div>
                                        <div>
                                        {{# if(urlBdcdyh == bdcdyh || checkedflag == '1'){}}
                                        <div class="bdc-border" style="pointer-events:none"></div><img class="bdc-mark" style="pointer-events:none" src="../image/mark.png" />
                                        {{# }}}
                                    </td>
                                {{# } }}
                            {{# }else{}}
                                <td><div class="layui-table-cell"></div></td>
                            {{# } }}
                            {{# count++; }}
                        {{# } }}
                    </tr>
                {{# } }}
            {{# } }}
        <!--车库 end tableMainTpl -->
        </tbody>
    </table>
</script>

<script type="text/html" id="tableFixedTpl">
    {{# if(d){}}
        {{#  layui.each(d.cFwList, function(index, item){ }}
                <tr wlcs="{{item.wlcs}}">
                    <td data-field="wlcs" align="center" class="">
                        <div class="layui-table-cell">{{item.wlcs}}</div></td>
                    <td data-field="dycs" align="center" class="">
                        <div class="layui-table-cell">{{item.dycs}}</div></td>
                </tr>
        {{#  }); }}

    <!--车库 start tableFixedTpl  -->
        {{#if(d.lpbCkVo && d.lpbCkVo.ckList){}}
            {{# var rowspan = d.lpbCkVo.rowspan;}}
            {{# var wlcs = d.lpbCkVo.wlcs;}}
            {{# var dycs = d.lpbCkVo.dycs;}}
            {{# for(var i = 0 ; i < rowspan ; i++){}}
                <tr wlcs="{{wlcs}}">
                    <td data-field="wlcs" align="center" class="">
                        <div class="layui-table-cell">{{wlcs}}</div></td>
                    <td data-field="dycs" align="center" class="">
                        <div class="layui-table-cell">{{dycs}}</div></td>
                </tr>
            {{#}}}
        {{#}}}
    <!--车库 end tableFixedTpl  -->
    {{# } }}
</script>
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
                                   data-showfunction="{{item.showFunction}}"
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

    layui.config({
        base: '../lib/' //此处路径请自行处理, 可以使用绝对路径
    }).extend({
        formSelects: 'form-select/formSelects-v4'
    });
    // 工作流实例ID
    var gzlslid = '${gzlslid!}';
</script>
<script src="../js/newlpb/resource.js?v=2019-09-02"></script>
<script src="../js/newlpb/index.js?v=2019-09-02"></script>
<script src="../js/fwhs/hsbg.js?v=2019-09-02"></script>
<script src="../js/fwhs/fwhsgl.js?v=2019-09-02"></script>
<script src="../js/fwljz/ljzgl.js?v=2019-09-02"></script>
<script src="../js/newlpb/hsTab.js?v=2019-09-02"></script>
<script src="../js/newlpb/ychsTab.js?v=2019-09-02"></script>
<script src="../js/newlpb/hbCheck.js?v=2019-09-02"></script>
<script src="../js/newlpb/zdtab.js?v=2019-09-02"></script>
<script src="../js/newlpb/zrztab.js?v=2019-09-02"></script>
<script src="../js/newlpb/ljztab.js?v=2019-09-02"></script>
<script src="../js/newlpb/zdTree.js?v=2019-09-02"></script>
<script src="../js/newlpb/btn.js?v=2019-09-02"></script>
<script src="../js/newlpb/state.js?v=2019-09-02"></script>
<script src="../js/newlpb/showColumn.js?v=2019-09-02"></script>
<script src="../js/newlpb/cart.js?v=2019-09-02"></script>
<script src="../js/mask.js?v=2019-09-02"></script>
<script src="../js/newlpb/gzyz.js?v=2019-09-02"></script>
</html>