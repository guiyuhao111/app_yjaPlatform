$(document).ready(function () {
     $("#uploadFormId").on("click","#uploadFile",uploadFile)
     $("#apply").on("click","#applyFile",applyFile)
})
function uploadFile() {
    var params=getParams();
    console.log(params);
    var url="userAuth.do";
    $("#uploadFormId").ajaxSubmit({
        type:"post",
        url:url,
        data:params,
        success:function(result){
            if(result.state==1){
                alert("文件上传成功");
            }else{
                alert(result.message);
            }
        }
    });
}
function getParams() {
    var param={
        "authName":"桂预豪",
        "authCardNo":"421127199501062812",
        "token":"f2e0d29e826546054c955bf363a3325b"
    }
    return param;
}

function applyFile() {
    var params=getApplyParams();
    console.log(params);
    var url="appUpdataPageInfo.do";
    $("#apply").ajaxSubmit({
        type:"post",
        url:url,
        data:params,
        success:function(result){
            console.log(result)
            if(result.state==1){
                alert("文件上传成功");
            }else{
                alert(result.message);
            }
        }
    });
}
function getApplyParams() {
    var param={
        id:1,
        img:"222",
        classType:"loansEdit",
        title:"国信",
        content:"dsahjkdhk"
    }
    return param;
}