$(document).ready(function(){
    // var url="login.do";
    // var params={
    //     "account":"桂预",
    //     "password":"GYH123"
    // };
    // console.log(params);
    // $.post(url,params,function(result){
    //     console.log(result);
    // });
});



function fileChange(target) {
    var fileSize = 0;
    if (!target.files) {
        var filePath = target.value;
        var fileSystem = new ActiveXObject("Scripting.FileSystemObject");
        var file = fileSystem.GetFile (filePath);
        fileSize = file.Size;
    } else {
        fileSize = target.files[0].size;
    }
    var size = fileSize / 1024;
    if(size>2000){
        alert("附件不能大于2M");
        target.value="";
        return
    }
    var name=target.value;
    var fileName = name.substring(name.lastIndexOf(".")+1).toLowerCase();
    if(fileName !="xls" && fileName !="xlsx"){
        alert("请选择execl格式文件上传！");
        target.value="";
        return
    }
}

function filefujianChange(target) {
    var fileSize = 0;
    if (!target.files) {
        var filePath = target.value;
        var fileSystem = new ActiveXObject("Scripting.FileSystemObject");
        var file = fileSystem.GetFile (filePath);
        fileSize = file.Size;
    } else {
        fileSize = target.files[0].size;
    }
    var size = fileSize / 1024;
    if(size>2000){
        alert("附件不能大于2M");
        target.value="";
        return
    }
    var name=target.value;
    var fileName = name.substring(name.lastIndexOf(".")+1).toLowerCase();
    if(fileName !="jpg" && fileName !="jpeg" && fileName !="pdf" && fileName !="png" && fileName !="dwg" && fileName !="gif" ){
        alert("请选择图片格式文件上传(jpg,png,gif,dwg,pdf,gif等)！");
        target.value="";
        return
    }
}