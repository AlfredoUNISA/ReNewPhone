
 $(document).ready(function() {
	 loadBrands();
            $("#brandSelect").change(function(e) {
                e.preventDefault();
				getDevices();
			});
			$("#modelSelect").change(function() {
				var modelValue= $("#modelSelect").val();
				if(modelValue!="seleziona")
		        	$("#formAfterModel").show();
		        else	
		        	$("#formAfterModel").hide();
		        });
})








function loadBrands(){
	$.ajax({
		url:"AjaxProductEvalServlet?action=getBrands",
		method:"GET",
		success: function(response){
			var brands=response;
			$(brands).each(function () {
				var brand=this;
				var html='<option value="'+brand+'">'+brand+'</option>'
				$("#brandSelect").append(html);
			})
			
			
			
		},
		error: function(e){
			alert("Errore: "+e);
		}
		
		
		
		
	});
}       

function getDevices(){
		$("#modello").empty();
		var marca=$("#brandSelect").val();
		if(marca!="seleziona"){
			$("#formAfterBrand").show();
			$.ajax({
                    url: "AjaxProductServlet?filterBrand="+marca+"&productsPerLoading=999", // Indirizzo del tuo endpoint Java per l'inserimento
                    method: "GET",
                    success: function(response) {
                       	var marcaResponse=response;
						$(marcaResponse).each(function () {
							var x=this.groupName;
							var html='<option value="'+x+'">'+x+'</option>'
							$("#modelSelect").append(html);
						})
						
			
			
                    },
                    error: function(xhr, status, error) {
                        // Gestisci l'errore della richiesta Ajax
                        console.log("Errore Ajax: " + error);
                    }
                });
		} else {
			$("#formAfterBrand").hide();
		}
	
	
}


 
function sendEval(){
			var marca = $("#marca").val();
                var modello = $("#modello").val();
                var anno = $("#anno").val();
                var condizione = $("#condizione").val();

                $.ajax({
                    url: "AjaxProductEvalServlet?action=getModels", // Indirizzo del tuo endpoint Java per l'inserimento
                    method: "GET",
                    data: {
                        marca: marca,
                        modello: modello,
                        anno: anno,
                        condizione: condizione
                    },
                    success: function(response) {
                        // Gestisci la risposta dal server
                        $("#risultato").html(response);
                    },
                    error: function(xhr, status, error) {
                        // Gestisci l'errore della richiesta Ajax
                        console.log("Errore Ajax: " + error);
                    }
                });
}