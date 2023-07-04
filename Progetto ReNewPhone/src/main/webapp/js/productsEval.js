
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
			$("#submitButton").click(function(e) {
				e.preventDefault();
				sendEval();
			})

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
		$("#modelSelect").empty();
		$("#modelSelect").append('<option value="seleziona"> SELEZIONA </option>');
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
			var marca = $("#brandSelect").val();
                var modello = $("#modelSelect").val();
                var condizione = $("#condizione").val();
                var colore = $("#colore").val();
                var storage = $("#storage").val();

                $.ajax({
                    url: "AjaxProductEvalServlet?action=evaluate&brand="+marca+'&model='+modello+
                    "&condition="+condizione+"&storage="+storage, // Indirizzo del tuo endpoint Java per l'inserimento
                    method: "GET",
                    success: function(response) {
                        // Gestisci la risposta dal server
                        var html='<h2><b> Il tuo '+ marca+' '+modello+'('+storage+'GB) '+colore+' vale= '+ response+'&euro;';
                        $("#risultato").html(html);
                    },
                    error: function(xhr, status, error) {
                        // Gestisci l'errore della richiesta Ajax
                        console.log("Errore Ajax: " + error);
                    }
                });
}