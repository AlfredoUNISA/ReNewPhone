function toggleFilterPage(){
	$(document).ready(function() {
                    if ($(".slide-toggle").val()== "Apri Filtri") 
                    	$(".slide-toggle").val("Chiudi Filtri");
                    else 
                    	$(".slide-toggle").val("Apri Filtri");
                    
                    $(".box").animate({
                        width: "toggle"
                    });
	});            
}