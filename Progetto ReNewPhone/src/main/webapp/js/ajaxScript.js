var productsPerLoading = 24; // Cambiare questo per modificare il numero anche nella servlet
var countLoadings = 0;

$(document).ready(function () {
	$('#loadMoreButton').click(function () {
		loadMoreProducts()
	});
});

$(document).ready(function () {
	loadMoreProducts();
});

function loadMoreProducts() {
	$.ajax({
		url: 'AjaxProductServlet?countLoadings=' + countLoadings + '&productsPerLoading=' + productsPerLoading,
		success: function (response) {
			// Recupera il JSON dei prodotti
			var resultJSON = response;
			console.log(resultJSON);

			// Itera sui prodotti e visualizzali
			$(resultJSON).each(function () {
				// Crea il markup HTML per un singolo prodotto
				var html =
					'<div id="Product">' +
					'<img class="productImg" alt="' + this.model + '" src="resources/' + this.model + '.jpg">' +
					'<div class="productInfo">' +
					'<p>' + this.groupName + '</p>' +
					'<p>RAM: ' + this.minMaxValues.minRam + "~" + this.minMaxValues.maxRam + ' GB</p>' +
					'<p>Dimensioni: ' + this.minMaxValues.minDisplaySize + "~" + this.minMaxValues.maxDisplaySize + '"</p>' +
					'<p>Memoria: ' + this.minMaxValues.minStorage + "~" + this.minMaxValues.maxStorage + ' GB</p>' +
					'<p>Marca: ' + this.brand + '</p>' +
					'<p>Anno: ' + this.year + '</p>' +
					'<p>Prezzo: ' + this.minMaxValues.minPrice + "~" + this.minMaxValues.maxPrice + ' &euro;</p>' +
					'<p><a href="products?action=details&name=' + this.groupName + '">Dettagli</a></p>' +
					'</div>' +
					'</div>';

				// Aggiungi il markup HTML al contenitore dei prodotti
				$('.productsGrid').append(html);
			});
			countLoadings++;
			console.log(countLoadings - 1 + ", " + countLoadings);
		},
		error: function (status, error) {
			console.error("Errore durante la chiamata AJAX: " + status + " - " + error);
		}
	});
}
