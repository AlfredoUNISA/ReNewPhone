var productsPerLoading = 8; // Cambiare questo per modificare il numero anche nella servlet
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
			//console.log(resultJSON);

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

				// Creazione del markup HTML per un singolo prodotto
				var html =
					'<div id="Product">' +
					'<img class="productImg" alt="' + this.model + '" src="resources/' + this.model + '.jpg">' +
					'<div class="productInfo">' +
					'<p>' + this.groupName + '</p>' +
					'<p>RAM: ' + ramValue + '</p>' +
					'<p>Dimensioni: ' + displaySizeValue + '</p>' +
					'<p>HD: ' + storageValue + '</p>' +
					'<p>Marca: ' + this.brand + '</p>' +
					'<p>Anno: ' + this.year + '</p>' +
					'<p>Prezzo: ' + priceValue + '</p>' +
					'<p><a href="products?action=details&name=' + this.groupName + '">Dettagli</a></p>' +
					'</div>' +
					'</div>';

				// Aggiungi il markup HTML al contenitore dei prodotti
				$('.productsGrid').append(html);
			});
			countLoadings++;
			//console.log(countLoadings - 1 + ", " + countLoadings);
		},
		error: function (status, error) {
			console.error("Errore durante la chiamata AJAX: " + status + " - " + error);
		}
	});
}
