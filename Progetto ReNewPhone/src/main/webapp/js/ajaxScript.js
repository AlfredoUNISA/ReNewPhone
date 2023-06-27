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
		url: 'products?countLoadings=' + countLoadings,
		success: function () {
			// Recupera il JSON dei prodotti
			var resultJSON = '${productsJson}';

			// Converte il JSON in un array di oggetti
			var result = JSON.parse(resultJSON);

			// Itera sui prodotti e visualizzali
			$(result).each(function () {
				// Crea il markup HTML per un singolo prodotto
				var html =
					'<div id="Product">' +
					'<img class="productImg" alt="' + this.model + '" src="resources/' + this.model + '.jpg">' +
					'<div class="productInfo">' +
					'<p>' + this.name + '</p>' +
					'<p>RAM: ' + this.ram + ' GB</p>' +
					'<p>Dimensioni: ' + this.display_size + '"</p>' +
					'<p>Memoria: ' + this.storage + ' GB</p>' +
					'<p>Marca: ' + this.brand + '</p>' +
					'<p>Anno: ' + this.year + '</p>' +
					'<p>Prezzo: ' + this.price + ' &euro;</p>' +
					'<p><a href="products?action=details&name=' + this.name + '">Dettagli</a></p>' +
					'</div>' +
					'</div>';

				// Aggiungi il markup HTML al contenitore dei prodotti
				$('.productsGrid').append(html);
			});
			countLoadings++;
			console.log(countLoadings - 1 + ", " + countLoadings);
		},
		error: function () {
			console.error("Errore durante la chiamata AJAX: " + status + " - " + error);
		}
	});
}
