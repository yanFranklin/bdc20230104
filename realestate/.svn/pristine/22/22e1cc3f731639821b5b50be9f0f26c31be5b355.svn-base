
layui.use(['form','layer','table','laytpl'], function(){
    var form = layui.form,
        $ = layui.jquery,
        layer = layui.layer,
        table = layui.table,
        laytpl = layui.laytpl;

    $(function(){
        //点击收起
        $('.bdc-up-btn').on('click',function(){
            var $this = $(this);
            $this.addClass('bdc-hide');
            $this.siblings('.bdc-down-btn').removeClass('bdc-hide');
            $this.parent().siblings('.bdc-content-js').addClass('bdc-hide');
        });
        $('.bdc-down-btn').on('click',function(){
            var $this = $(this);
            $this.addClass('bdc-hide');
            $this.siblings('.bdc-up-btn').removeClass('bdc-hide');
            $this.parent().siblings('.bdc-content-js').removeClass('bdc-hide');
        });

        //单击 保存
        $('#bdc-save-pz').on('click',function(){
            var pzid = $("#pzid").val();
            var pzz = $("textarea[name='pzz']").val();
            savaBgPz(pzid, pzz);
        });

        function savaBgPz(pzid, pzz){
            $.ajax({
                type: 'PUT',
                url: getContextPath() +"/rest/v1.0/tsywpz/update",
                contentType: 'application/json',
                dataType: "json",
                data: JSON.stringify({pzid: pzid, pzz: pzz}),
                success: function (data) {
                    removeModal();
                    if(data == 1){
                        saveSuccessMsg();
                    }
                }
            });
        }

    });
});

function renderForm(data){
    $(".bdc-text-pzsm").html(data.pzsm);
    $("#pzid").val(data.pzid);
    if(isNotBlank(data.pzz)){
        var resultXml = formatXml(data.pzz);
        $("textarea[name='pzz']").val(resultXml);
    }
    layui.form.render();
}

function getXsdExample(){
    var resultXml = formatXml(xsdMb);
    $("textarea[name='pzz']").val(resultXml);
}

function xsdFormat(){
    var xml = $("textarea[name='pzz']").val();
    if(isNotBlank(xml)){
        var resultXml = formatXml(xml);
        $("textarea[name='pzz']").val(resultXml);
    }
}

var xsdMb = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">"
    + "<!--业务报文ID--><xs:element name=\"BizMsgID\"><xs:simpleType><xs:restriction base=\"xs:string\"><xs:pattern value=\"[\\d]{18}\"/></xs:restriction></xs:simpleType></xs:element>"
    + "<!--权利类型--><xs:element name=\"RightType\"><xs:simpleType><xs:restriction base=\"xs:string\"><xs:enumeration value=\"1\"/></xs:restriction></xs:simpleType></xs:element>"
    + "<!--业务流水号--><xs:element name=\"RecFlowID\"><xs:simpleType><xs:restriction base=\"xs:string\"><xs:minLength value=\"1\"/><xs:maxLength value=\"50\"/></xs:restriction></xs:simpleType></xs:element>"
    + "<!--不动产单元号--><xs:element name=\"EstateNum\"><xs:simpleType><xs:restriction base=\"xs:string\"><xs:pattern value=\"[^\\\\s]{6,28}\"/></xs:restriction></xs:simpleType></xs:element>"
    +"<!--容积率--><xs:attribute name=\"RJL\"><xs:simpleType><xs:restriction base=\"xs:decimal\"><xs:totalDigits value=\"4\"/><xs:fractionDigits value=\"2\"/></xs:restriction></xs:simpleType></xs:attribute>"
    +"</xs:schema>";
