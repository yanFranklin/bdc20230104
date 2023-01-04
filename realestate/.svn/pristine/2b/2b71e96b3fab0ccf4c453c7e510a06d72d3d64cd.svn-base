/**
 * 身份证
 */
function ClearIDCard() {
    CVR_IDCard.Name="";
    CVR_IDCard.NameL="";
    CVR_IDCard.Sex="";
    //CVR_IDCard.SexL="";
    CVR_IDCard.Nation="";
    //CVR_IDCard.NationL="";
    CVR_IDCard.Born="";
    //CVR_IDCard.BornL="";
    CVR_IDCard.Address="";
    CVR_IDCard.CardNo="";
    CVR_IDCard.Police="";
    CVR_IDCard.Activity="";
    CVR_IDCard.NewAddr="";

    return true;
}
function ReadIDCardShe(xmId,sfzhId ,dzId){
    var CVR_IDCard=document.getElementById("CVR_IDCard");

    if(CVR_IDCard==undefined || CVR_IDCard==null){
        try{
            $("body").append('<OBJECT classid="clsid:10946843-7507-44FE-ACE8-2B3483D179B7"	  ' +
                'codebase="CVR100.cab#version=3,0,3,3"	  ' +
                'id="CVR_IDCard"           ' +
                'name="CVR_IDCard"	  width=0	  height=0	  align=center	  hspace=0	  vspace=0> </OBJECT>');
        }catch(e){}
    }
    ClearIDCard();
    CVR_IDCard.PhotoPath="C:/";
    CVR_IDCard.TimeOut=2;
    var strReadResult=CVR_IDCard.ReadCard;
    if(typeof(strReadResult)=="undefined"){
        alert("请检查身份证读卡器是否安装成功！");
        return;
    }
    if(strReadResult==0){
        var pName=CVR_IDCard.Name;
        var pCardNo=CVR_IDCard.CardNo;
        var pSex=CVR_IDCard.SexL;   //var pSexL=CVR_IDCard.SexL;
        var pNation=CVR_IDCard.NationL;  //var pNationL=CVR_IDCard.NationL;
        var pBorn=CVR_IDCard.BornL;      //var pBornL=CVR_IDCard.BornL;
        var pAddress=CVR_IDCard.Address;
        var pPolice=CVR_IDCard.Police;
        var pActivity=CVR_IDCard.Activity;
        var pNewAddr=CVR_IDCard.NewAddr;
        var pActivityLFrom=CVR_IDCard.ActivityLFrom;
        var pActivityLTo=CVR_IDCard.ActivityLTo;
        var pPhotoBuffer=CVR_IDCard.GetPhotoBuffer;
        if(pName==null || pName==""){
            alert("读取身份证失败，请拿起身份证重新放置！");
            return true;
        }
        if(xmId!=null && xmId!=""){
            //校验用户名是否一致?
            // var xm = document.getElementById(xmId).value;
            // if(xm == trimStr(pName)){
            //     // console.info("same");
            // }
            document.getElementById(xmId).value = trimStr(pName);
        }
        if(sfzhId!=null && sfzhId!="" ){
             document.getElementById(sfzhId).value = trimStr(pCardNo);
        }
        if(dzId!=null && dzId!="" ){
             document.getElementById(dzId).value = trimStr(pAddress);
        }
    }else{
        ClearIDCard();
        alert("读取身份证失败，请拿起身份证重新放置！");
    }
    return true;
}



function ReadIDCardNew(mcCol,mcRow,sfzhCol,sfzhRow,addressCol,addressRow){

    var CVR_IDCard=document.getElementById("CVR_IDCard");

    if(CVR_IDCard==undefined || CVR_IDCard==null){
        try{
            $("body").append('<OBJECT classid="clsid:10946843-7507-44FE-ACE8-2B3483D179B7"	  codebase="CVR100.cab#version=3,0,3,3"	  id="CVR_IDCard"           name="CVR_IDCard"	  width=0	  height=0	  align=center	  hspace=0	  vspace=0> </OBJECT>');

        }catch(e){

        }
    }
    ClearIDCard();
    ReadIDCard(mcCol,mcRow,sfzhCol,sfzhRow,addressCol,addressRow);
}





function ReadIDCard(mcCol,mcRow,sfzhCol,sfzhRow,addressCol,addressRow) {
    CVR_IDCard.PhotoPath="C:/";
    CVR_IDCard.TimeOut=2;

    var strReadResult=CVR_IDCard.ReadCard;

    if(typeof(strReadResult)=="undefined"){
		//alert("请检查身份证读卡器是否安装成功！");
		return;
	}
    if(strReadResult==0){
        var pName=CVR_IDCard.Name;
        var pCardNo=CVR_IDCard.CardNo;
        var pSex=CVR_IDCard.SexL;   //var pSexL=CVR_IDCard.SexL;
        var pNation=CVR_IDCard.NationL;  //var pNationL=CVR_IDCard.NationL;
        var pBorn=CVR_IDCard.BornL;      //var pBornL=CVR_IDCard.BornL;
        var pAddress=CVR_IDCard.Address;
        var pPolice=CVR_IDCard.Police;
        var pActivity=CVR_IDCard.Activity;
        var pNewAddr=CVR_IDCard.NewAddr;
        var pActivityLFrom=CVR_IDCard.ActivityLFrom;
        var pActivityLTo=CVR_IDCard.ActivityLTo;
        var pPhotoBuffer=CVR_IDCard.GetPhotoBuffer;
        if(pName==null || pName==""){
            alert("读取身份证失败，请拿起身份证重新放置！");
            return true;
        }
        var write = contentPane.curLGP.write;

        if(mcCol!=null && mcCol!="" && mcRow!=null && mcRow!=""){

            contentPane.setCellValue(mcCol+mcRow,null, trimStr(pName));
        }
        if(sfzhCol!=null && sfzhCol!="" && sfzhRow!=null && sfzhRow!=""){

            contentPane.setCellValue(sfzhCol+sfzhRow,null,trimStr(pCardNo));
        }
        if(addressCol!=null && addressCol!="" && addressRow!=null && addressRow!=""){
            contentPane.setCellValue(addressCol+addressRow,null, trimStr(pAddress));
        }
    }else{
        ClearIDCard();
 		alert("读取身份证失败，请拿起身份证重新放置！");
    }

    return true;
}


function DoStopRead() {
    CVR_IDCard.DoStopRead;
    return true;
}
//检测设备state 0 为正常 -1不正常
function DoCheckReader() {
    var State=CVR_IDCard.GetState;
    return State;
    // return true;
}
//
function trimStr(str){return str.replace(/(^\s*)|(\s*$)/g,"");}