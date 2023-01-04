<div class="bdc-upload-dragdiv" style="position: fixed;width:80%;
     padding: 30px;max-height: 80%;overflow: hidden" id="fcfhtdiv">
    <div class="title-sign bdc-title-sign-btn">
        <div class="bdc-tab-a" style="position: absolute;
    top: 0;
    right: 0;">
            <button type="button" class="layui-btn layui-btn-sm bdc-major-btn" id="zoomIn" onclick="zoomIn()" style="visibility: hidden">放大
            </button>
            <button type="button" class="layui-btn layui-btn-sm bdc-secondary-btn" id="zoomOut" onclick="zoomOut()" style="visibility: hidden">缩小
            </button>
        </div>
    </div>
    <div class="layui-upload-drag img-drag" id="fcfhtImgDiv">
    </div>
</div>
<script type="text/html" id="fcfhtUploadTpl">
    {{# if(d.base64){ }}
    <img id="fcfhtImg" class="upload-img" src="{{d.base64}}" alt="">
    {{# }else{ }}
    <div class="upload-icon">
        <i class="layui-icon">&#xe654;</i>
        <span>无分层分户图</span>
    </div>
    <img id="fcfhtImg" class="upload-img" style="max-width: 900px" src="" alt="">
    <div class="video-icon"></div>
    {{# } }}
</script>
