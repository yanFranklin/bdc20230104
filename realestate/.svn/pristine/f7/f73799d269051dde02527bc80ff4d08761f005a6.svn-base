/**
 * Created by Administrator on 2019/5/9.
 */
var dragobj={};
window.onerror=function(){return false};
function on_ini(){
    String.prototype.inc=function(s){return this.indexOf(s)>-1?true:false};
    var agent=navigator.userAgent;
    window.isOpr=agent.inc("Opera");
    window.isIE=agent.inc("IE")&&!isOpr;
    window.isMoz=agent.inc("Mozilla")&&!isOpr&&!isIE;
    if(isMoz){
        Event.prototype.__defineGetter__("x",function(){return this.clientX+2});
        Event.prototype.__defineGetter__("y",function(){return this.clientY+2});
    }
    basic_ini();
}
function basic_ini(){
    window.$$=function(obj){return typeof(obj)=="string"?document.getElementById(obj):obj};
    window.oDel=function(obj){if($$(obj)!=null){$$(obj).parentNode.removeChild($$(obj))}}
}
var getFather = document.getElementsByClassName('bdc-module');
var hasMove=false;
window.onload=function(){
    on_ini();
    var o=document.getElementsByClassName("bdc-common-title");
    // console.log(o.length);
    for(var i=0;i<o.length;i++){
        o[i].onmousedown=function(e){
            if(dragobj.o!=null)
                return false;

            for(var i = 0; i < getFather.length; i++){
                getFather[i].style.height = '38px';
            }

            e=e||window.event;
            dragobj.o=this.parentNode;

           // 第一个两行的特殊情况
           if($(dragobj.o).attr('id') == 'm0' || $(dragobj.o).attr('id') == 'm1'){
               $('#m0').removeClass('bdc-merge');
               $('#m1').removeClass('bdc-merge');
           }

            dragobj.xy=getxy(dragobj.o);
            dragobj.xx=new Array((e.x-dragobj.xy[1]),(e.y-dragobj.xy[0]));
            dragobj.o.style.width=dragobj.xy[2]+"px";
            dragobj.o.style.height=dragobj.xy[3]+"px";
            dragobj.o.style.left=(e.x-dragobj.xx[0])+"px";
            dragobj.o.style.top=(e.y-dragobj.xx[1])+"px";
            dragobj.o.style.position="absolute";
            var om=document.createElement("div");
            dragobj.otemp=om;
            om.style.width=dragobj.xy[2]+"px";
            om.style.height=dragobj.xy[3]+"px";
            dragobj.o.parentNode.insertBefore(om,dragobj.o);
            return false
        }
    }
};
document.onselectstart=function(){return false};
window.onfocus=function(){document.onmouseup()};
window.onblur=function(){document.onmouseup()};
document.onmouseup=function(){
    if(hasMove){
        if(dragobj.o!=null){
            dragobj.o.style.width="auto";
            dragobj.o.style.height="auto";
            dragobj.otemp.parentNode.insertBefore(dragobj.o,dragobj.otemp);
            console.log($(dragobj.o).prev().attr('id'));
            console.log($(dragobj.o).attr('id'));
            // console.log($(dragobj.o).parent());
            var content = 'left';
            if($(dragobj.o).parent().hasClass('bdc-right')){
                content = 'right';
            }
            var moveData = {
                "code": $(dragobj.o).attr('id'),
                "name": true,
                "content": content,
                "type": "SYMK",
                "typeName": $(dragobj.o).prev().attr('id'),
                "sequenceNumber": 1
            };
            console.log(moveData);
            getReturnData("/rest/v1.0/collect/module",JSON.stringify(moveData),"POST",function (data) {
                console.log(data);
            });
            dragobj.o.style.position="";
            oDel(dragobj.otemp);
            dragobj={};

            for(var i = 0; i < getFather.length; i++){
                getFather[i].style.height = 'auto';
            }
        }
    }
    hasMove =false;
};
document.onmousemove=function(e){
    hasMove = true;
    e=e||window.event;
    if(dragobj.o!=null){
        // console.log(dragobj.o);
        // console.log($(dragobj.o).parent());
        if($(dragobj.o).parent().hasClass('bdc-left')){
            console.log('aaa');
            console.log(e.x);
            dragobj.o.style.left=(e.x-dragobj.xx[0] + 90)+"px";
            dragobj.o.style.top=(e.y-dragobj.xx[1] - 40)+"px";
            createtmpl(e)
        }else {
            console.log('bbb');
            console.log(e.x);
            dragobj.o.style.left=(e.x-dragobj.xx[0] - 90)+"px";
            dragobj.o.style.top=(e.y-dragobj.xx[1] - 40)+"px";
            createtmpl(e)
        }

    }
};
function getxy(e){
    // console.log(e);
    var a=new Array();
    var t=e.offsetTop-20;
    var l=e.offsetLeft;
    var w=e.offsetWidth;
    var h=e.offsetHeight;
    while(e=e.offsetParent){
        t+=e.offsetTop;
        l+=e.offsetLeft;
    }
    a[0]=t;a[1]=l;a[2]=w;a[3]=h;
    return a;
}
function inner(o,e){
    var a=getxy(o);
    if(e.x>a[1]&&e.x<(a[1]+a[2])&&e.y>a[0]&&e.y<(a[0]+a[3])){
        if(e.y<(a[0]+a[3]/2))
            return 1;
        else
            return 2;
    }else
        return 0;
}
function createtmpl(e){
    for(var i=0;i<6;i++){
        if($$("m"+i)==dragobj.o)
            continue;
        var b=inner($$("m"+i),e);

        if(b==0)
            continue;
        dragobj.otemp.style.width=$$("m"+i).offsetWidth;
        var $mi;
        // console.log($$("m" + i));
        if(i == 0 || i == 1){
            if($($$("m"+i).parentNode).hasClass('bdc-handle')){
                $mi = $$("m"+i).parentNode;
            }else {
                $mi = $$("m"+i);
            }
        }else {
            $mi = $$("m"+i);
        }
        // if(i == 0){
        //     $mi = $$("m"+i).parentNode;
        // }else if( i == 1){
        //     $mi = $$("m"+i).parentNode.parentNode;
        // }else {
        //     $mi = $$("m"+i);
        // }
        if(b==1){
            $mi.parentNode.insertBefore(dragobj.otemp,$mi)
        }else{
            if($$("m"+i).nextSibling==null){
                $mi.parentNode.appendChild(dragobj.otemp)
            }else{
                $mi.parentNode.insertBefore(dragobj.otemp,$mi.nextSibling)
            }
        }
        return
    }
    for(var j=0;j<2;j++){
        if($$("dom"+j).innerHTML.inc("div")||$$("dom"+j).innerHTML.inc("DIV"))
            continue;
        var op=getxy($$("dom"+j));
        if(e.x>(op[1]+10)&&e.x<(op[1]+op[2]-10)){
            $$("dom"+j).appendChild(dragobj.otemp);
            dragobj.otemp.style.width=(op[2]-10)+"px"
        }
    }
}