<div class="bdc-table-box" id="tuxknrList"></div>
<div class="bdc-upload-dragdiv" style="position: fixed;left: 50%;top: 50%;transform: translate(-50%,-50%);
    padding: 30px;overflow-y: auto;max-height: 480px" id="zdtdiv">
    <div class="layui-upload-drag img-drag" id="zdtImg">
    </div>
</div>
<script type="text/html" id="uploadTpl">
    {{# if(d.base64){ }}
    <img id="img" class="upload-img" style="max-width: 900px" src="{{d.base64}}" alt="">
    {{# }else{ }}
    <div class="upload-icon">
        <i class="layui-icon">&#xe654;</i>
        <span>无宗地图</span>
    </div>
    <img id="img" class="upload-img" style="max-width: 900px" src="" alt="">
    <div class="video-icon"></div>
    {{# } }}
</script>
<script type="text/html" id="tuxknrListTpl">
    <table class="layui-table tuxknr-table-view" border="1" name="">
        <tbody>
        <tr class="gray-tr">
            <td width="40px">序号</td>
            <td width="700px">附件名称</td>
            <td width="100px">操作</td>
        </tr>
        {{# if(!d.data || d.data.length==0) { }}
        <tr class="bdc-table-none">
            <td colspan="3">
                <div class="layui-none">
                    <img src="../static/lib/bdcui/images/table-none.png" alt="">无数据</div>
            </td>
        </tr>
        {{# } else { }}
        {{# layui.each(d.data, function(index, item){ }}
        <tr>
            <td>{{ index+1 }}</td>
            <td>{{ item.tcm || ''}}</td>
            <td><span class="layui-btn layui-btn-xs bdc-secondary-btn" onclick="downTux('{{item.url||\'\'}}')">下载</span></td>
        </tr>
        {{# }); }}
        {{# } }}
        </tbody>
    </table>
</script>

