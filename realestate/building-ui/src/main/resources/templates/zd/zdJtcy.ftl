<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <title>家庭成员</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link rel="stylesheet" href="../static/lib/font-awesome-4.7.0/css/font-awesome.min.css" media="all">
    <link rel="stylesheet" href="../static/lib/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="../static/lib/bdcui/css/search.css"/>
    <link rel="stylesheet" href="../static/lib/bdcui/css/table.css"/>
    <!--popup.css放到form-tab.css，防止样式冲突-->
    <link rel="stylesheet" href="../static/lib/bdcui/css/popup.css">
    <link rel="stylesheet" href="../static/lib/bdcui/css/form-tab.css"/>
    <link rel="stylesheet" href="../static/lib/bdcui/css/mask.css">
    <style>
        .bdc-form-div {
            padding-top: 10px;
        }

        .layui-disabled {
            color: #333 !important;
        }

        .layui-disabled:hover {
            color: #333 !important;
        }
    </style>
</head>
<body>
<div class="bdc-form-div bdc-not-full-bg">
    <!--全局 标题和操作按钮 开始-->
    <!--全局 标题和操作按钮 结束-->
    <div class="layui-tab fixed-content" lay-filter="tabFilter">
        <div class="layui-tab-content" id="contentTable">
            <div class="layui-tab-item layui-show">
                <div class="form-margin-area">
                    <div class="basic-info">
                        <div id="view"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<script type="text/html" id="tpl">
    <form name="" class="common-form sl-from layui-form bdc-form-table" lay-filter="zdJtcyForm">
        <div class="layui-form-item" id="">
            <table class="layui-table bdc-jt-table" border="1" id="">
                <thead>
                <tr class="gray-tr">
                    <th style="min-width: 100px">姓名</th>
                    <th style="min-width: 50px">性别</th>
                    <th style="min-width: 120px">身份证号码</th>
                    <th style="min-width: 80px">与户主关系</th>
                    <th style="min-width: 100px">户口所在地</th>
                </tr>
                </thead>
                {{# if(d.jtcy && d.jtcy.length > 0){ }}
                {{# layui.each(d.jtcy, function(index, item){ }}
                <tr>
                    <td>
                        <div class="bdc-td-box">
                            <input type="text" name="xm" title="{{item.xm || ''}}"
                                   id="xm"
                                   class="layui-input"
                                   value="{{item.xm || ''}}">
                        </div>
                    </td>

                    <td>
                        <div class="bdc-td-box">
                            <select name="xb" lay-search="" lay-filter="xb" >
                                <option value="">请选择</option>
                                {{# layui.each(d.zd.SZdQlrxbDO, function(index, zdItem){ }}
                                {{# if(zdItem.DM==item.xb){ }}
                                <option value="{{zdItem.DM}}" selected>{{zdItem.MC}}</option>
                                {{# }else{ }}
                                <option value="{{zdItem.DM}}">{{zdItem.MC}}</option>
                                {{# } }}
                                {{# }); }}
                            </select>
                        </div>
                    </td>

                    <td>
                        <div class="bdc-td-box">
                            <input type="text" name="sfzhm" title="{{item.sfzhm || ''}}"
                                   id="sfzhm"
                                   class="layui-input"
                                   value="{{item.sfzhm || ''}}" >
                        </div>
                    </td>

                    <td>
                        <div class="bdc-td-box">
                            <select name="gx" lay-search="" lay-filter="gx" >
                                <option value="">请选择</option>
                                {{# layui.each(d.zd.SZdCbyhzgxDO, function(index, zdItem){ }}
                                {{# if(zdItem.DM==item.gx){ }}
                                <option value="{{zdItem.DM}}" selected>{{zdItem.MC}}</option>
                                {{# }else{ }}
                                <option value="{{zdItem.DM}}">{{zdItem.MC}}</option>
                                {{# } }}
                                {{# }); }}
                            </select>
                        </div>
                    </td>

                    <td>
                        <div class="bdc-td-box">
                            <input type="text" name="hkszd" title="{{item.hkszd || ''}}"
                                   id="hkszd"
                                   class="layui-input"
                                   value="{{item.hkszd || ''}}" >
                        </div>
                    </td>
                </tr>
                {{# }); }}
                {{# } else { }}
                <tr class="bdc-table-none">
                    <td colspan="5">
                        <div class="layui-none"><img
                                    src="../../static/lib/bdcui/images/table-none.png" alt="">无数据
                        </div>
                    </td>
                </tr>
                {{# } }}
            </table>
        </div>
    </form>
</script>
<script src="../static/lib/layui/layui.js"></script>
<script src="../static/lib/jquery/jquery.min.js"></script>
<script src="../js/common.js"></script>
<script src="../static/lib/bdcui/js/common.js"></script>
<script src="../js/zd/zdJtcy.js"></script>
</body>
</html>
