$(".fhNav").hover(function(){},function(){
	$(".bottomLine").css("width",parseFloat($(".selectedNav").eq(0).width()+20)+"px");
	$(".bottomLine").css("left",parseFloat($(".selectedNav").eq(0)[0].offsetLeft)+"px");	
})
$(".nav li").hover(function(){
	$(".bottomLine").css("width",parseFloat($(this).width()+20)+"px");
	$(".bottomLine").css("left",parseFloat($(this)[0].offsetLeft)+"px");	
});
$(".nav li").on("click",function(){
	$(".nav li").removeClass("selectedNav");
	$(this).addClass("selectedNav");
	$(".bottomLine").css("width",parseFloat($(this).width()+20)+"px");
	$(".bottomLine").css("left",parseFloat($(this)[0].offsetLeft)+"px");	
})