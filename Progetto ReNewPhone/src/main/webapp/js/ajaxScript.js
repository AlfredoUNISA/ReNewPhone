$(document).ready(function () {
	var currentPage = 1;
	var productsPerPage = 9;
	// Analizza la stringa JSON in un oggetto JavaScript
	var productsNum = '${productsNum}';

	loadProducts();

	var totalPages = Math.ceil(productsNum / productsPerPage);

	for (var i = 1; i <= totalPages; i++) {
		var pageNumber = i;
		var link = $('<a>', {
			id: pageNumber,
			text: pageNumber + " ",
			href: '#',
			click: function () {
				currentPage = this.id;
				loadProducts(); // Funzione per caricare i prodotti tramite Ajax
				return false;
			}
		});

		$('#pagination').append(link);
	}

	var minRam = Number.MAX_SAFE_INTEGER;
	var maxRam = Number.MIN_SAFE_INTEGER;
	var minPrice = Number.MAX_SAFE_INTEGER;
	var maxPrice = Number.MIN_SAFE_INTEGER;
	var minStorage = Number.MAX_SAFE_INTEGER;
	var maxStorage = Number.MIN_SAFE_INTEGER;


	function loadProducts() {
		$.ajax({
			url: 'products?action=getProducts&productsPerPage=' + productsPerPage + '&page=' + currentPage,
			success: function () {
				var resultJSON = '${productsJson}';
				console.log(resultJSON);
				var result = JSON.parse(resultJSON);
				// Itera sui prodotti e visualizzali
				$(result).each(function () {
					// Crea il markup HTML per un singolo prodotto
					var html = '<div id="Product">' +
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
			},
			error: function () {
				alert("Errore caricamento prodotti");
			}
		})
	};
});