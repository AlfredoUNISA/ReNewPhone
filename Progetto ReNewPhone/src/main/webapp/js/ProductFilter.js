var productsPerLoading = 8; // Cambiare questo per modificare il numero anche nella servlet
var countLoadings = 0;
$(document).ready(function(){
		$('#loadMoreFilteredButton').hide();
		$(".slide-toggle").click(function(){
		toggleFilterPage();
	})
	$(".processButton").click(function(){
		$("#loadMoreButton").hide();
		$("#loadMoreFilteredButton").show();
		countLoadings=0; //resetto i caricamenti così non verranno saltati i prodotti già caricati all'inizio
		$(".productsGrid").empty(); //tolgo tutti gli elementi non filtrati caricati in precedenza
		filterProducts();
	})
	$('#loadMoreFilteredButton').click(function () {
		filterProducts();
	});	
})
function brandSelectAndRedirect(brand){
	var url = 'products?brandSelect='+brand;
    window.location.href = url;
}

function GetParameterValues(param) {
	var url = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
	for (var i = 0; i < url.length; i++) {
		var urlparam = url[i].split('=');
		if (urlparam[0] == param) {
			return urlparam[1];
		}
	}
}


function toggleFilterPage(){
                    if ($(".slide-toggle").val()== "Apri Filtri") 
                    	$(".slide-toggle").val("Chiudi Filtri");
                    else 
                    	$(".slide-toggle").val("Apri Filtri");
                    
                    $(".filterBox").animate({
                        width: "toggle"           
				});
			}

function filterProducts() {
				console.log(GetParameterValues('brandSelect'))
			  if(GetParameterValues('brandSelect')==null)
						  	var brand = $("#brandSelect").val();
			  else{
				  $("#brandSelect").val(GetParameterValues('brandSelect'))
			  	  var brand=GetParameterValues('brandSelect');
			  	}
			  var priceMin = $("#priceRange input:first-of-type").val();
			  var priceMax = $("#priceRange input:last-of-type").val();
			  var memoryMin = $("#memoryRange input:first-of-type").val();
			  var memoryMax = $("#memoryRange input:last-of-type").val();
			  var ramMin = $("#ramRange input:first-of-type").val();
			  var ramMax = $("#ramRange input:last-of-type").val();
			  var yearMin = $("#yearRange input:first-of-type").val();
			  var yearMax = $("#yearRange input:last-of-type").val();

			  //console.log(priceMin);
			  //console.log(priceMax);
			  //console.log(memoryMin);
			  //console.log(memoryMax);
			  //console.log(brand);
			  //console.log(ramMin);
			  //console.log(ramMax);
			  loadMoreFilteredProducts(priceMin,priceMax,memoryMin,memoryMax,brand,ramMin,ramMax,yearMin,yearMax);
			 
}
function loadMoreFilteredProducts(priceMin,priceMax,memoryMin,memoryMax,filterBrand,ramMin,ramMax,yearMin,yearMax) {
	$.ajax({
		url: 'AjaxProductServlet?countLoadings=' + countLoadings + '&productsPerLoading=' + productsPerLoading +
		'&priceMin=' + priceMin + '&priceMax='+priceMax+'&memoryMin='+memoryMin+ '&memoryMax='+memoryMax+
		'&ramMin='+ramMin+ '&ramMax='+ramMax+ '&filterBrand='+filterBrand+'&yearMin='+yearMin+'&yearMax='+yearMax,
		success: function (response) {
			// Recupera il JSON dei prodotti
			var resultJSON = response;
			console.log(resultJSON);
			if(resultJSON.length!=0){
			// Itera sui prodotti e visualizzali
			$(resultJSON).each(function () {
				// Creazione del valore da visualizzare per RAM
				var ramValue;
				if (this.minMaxValues.minRam == this.minMaxValues.maxRam) {
					ramValue = this.minMaxValues.minRam + " GB";
				} else {
					ramValue = this.minMaxValues.minRam + " GB ~ " + this.minMaxValues.maxRam + " GB";
				}

				// Creazione del valore da visualizzare per Display Size
				var displaySizeValue;
				if (this.minMaxValues.minDisplaySize == this.minMaxValues.maxDisplaySize) {
					displaySizeValue = this.minMaxValues.minDisplaySize + " \"";
				} else {
					displaySizeValue = this.minMaxValues.minDisplaySize + " \" ~ " + this.minMaxValues.maxDisplaySize + " \"";
				}

				// Creazione del valore da visualizzare per Storage
				var storageValue;
				if (this.minMaxValues.minStorage == this.minMaxValues.maxStorage) {
					storageValue = this.minMaxValues.minStorage + " GB";
				} else {
					storageValue = this.minMaxValues.minStorage + " GB ~ " + this.minMaxValues.maxStorage + " GB";
				}

				// Creazione del valore da visualizzare per Price
				var priceValue;
				if (this.minMaxValues.minPrice == this.minMaxValues.maxPrice) {
					priceValue = this.minMaxValues.minPrice + " &euro;";
				} else {
					priceValue = this.minMaxValues.minPrice + " &euro; ~ " + this.minMaxValues.maxPrice + " &euro;";
				}
				var html =
					'<div id="Product">' +
					'<img class="productImg" alt="' + this.model + '" src="resources/' + this.model + '.jpg">' +
					'<div class="productInfo">' +
					'<p>' + this.groupName + '</p>' +
					'<p>RAM: ' + ramValue + '</p>' +
					'<p>Dimensioni: ' + displaySizeValue + '</p>' +
					'<p>RAM: ' + storageValue + '</p>' +
					'<p>Marca: ' + this.brand + '</p>' +
					'<p>Anno: ' + this.year + '</p>' +
					'<p>Prezzo: ' + priceValue + '</p>' +
					'<a href="products?action=details&name=' + this.groupName + '"> <input type="button"  value="Dettagli">   </a>' +
					'</div>' +
					'</div>';

				// Aggiungi il markup HTML al contenitore dei prodotti
				$('.productsGrid').append(html);
			});
			countLoadings++;
			console.log(countLoadings - 1 + ", " + countLoadings);
		} else {
			$("#loadMoreButton").hide();
			$("#loadMoreFilteredButton").hide();
		}
		},
		error: function (status, error) {
			console.error("Errore durante la chiamata AJAX: " + status + " - " + error);
		}
	});
}


