var filesCount = 20;

function openFileIIs(){
    try{
        // var obj=new ActiveXObject("wscript.shell");
        // if(obj){
        //     obj.Run("\\", 1, false );
        //     obj=null;
        // }

        var Message = "\u8bf7\u9009\u62e9\u6587\u4ef6\u5939"; //选择框提示信息
        var Shell = new ActiveXObject("Shell.Application");
        var Folder = Shell.BrowseForFolder(0, Message, 64, 17); //起始目录为：我的电脑
        //var Folder = Shell.BrowseForFolder(0, Message, 0); //起始目录为：桌面
        if (Folder != null) {
            Folder = Folder.items(); // 返回 FolderItems 对象
            Folder = Folder.item(); // 返回 Folderitem 对象
            Folder = Folder.Path; // 返回路径
            if (Folder.charAt(Folder.length - 1) != "\\") {
                Folder = Folder + "\\";
            }
            document.getElementById("ext").value = Folder;
            return Folder;
        }

    }catch(e){
        alert("请确定是否存在该盘符或文件");
    }
}
document.getElementById('choose').onchange = function(e) {
    var actual_filesSize = 0;
    //是否选中文件夹  文件夹是否为空  数量和大小是否超过限制
    //判断是否选中文件
    var file = $("#fileFolder").val();
    if (file != "") {
        var files = e.target.files;            // files是选中的文件夹数组
        //文件数量
        var actual_filesCount = files.length;
        if (actual_filesCount > filesCount) {
            $("#tips").html(file);
            document.getElementById("tips").style.color = "red";
            return;
        }else{
            $("#tips").html(file);
        }
    }
}