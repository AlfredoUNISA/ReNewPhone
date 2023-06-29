$(document).ready(function () {
	// Prendi la stringa JSON dei dettagli del prodotto
	var productDetailsStr = '<%= request.getAttribute("product-details") %>;'
	
	// Rimuovi ; finale
	productDetailsStr = productDetailsStr.substring(0, productDetailsStr.length - 1);

	var json = JSON.parse(productDetailsStr);

	console.log(json);

	
	var ramValues = [];
	var displaySizeValues = [];
	var storageValues = [];
	var colorValues = [];

	$(json).each(function () {
		// se non esiste gia' il valore, aggiungilo
		if (!ramValues.includes(this.ram))
			ramValues.push(this.ram);

		if (!displaySizeValues.includes(this.display_size))
			displaySizeValues.push(this.display_size);
		
		if (!storageValues.includes(this.storage))
			storageValues.push(this.storage);

		if (!colorValues.includes(this.color))
			colorValues.push(this.color);
	});

	// appendi i valori sotto forma di pulsanti
	var ramContainer = $("#ramContainer");
	ramContainer.append('RAM: ');
	$(ramValues).each(function () {
		ramContainer.append('<button type="button" class="ramBtn">' + this + " GB" + '</button>');
	});
	ramContainer.append('<br><br>');

	var displaySizeContainer = $("#displaySizeContainer");
	displaySizeContainer.append('Display: ');
	$(displaySizeValues).each(function () {
		displaySizeContainer.append('<button type="button" class="displaySizeBtn">' + this + " \"" + '</button>');
	});
	displaySizeContainer.append('<br><br>');

	var storageContainer = $("#storageContainer");
	storageContainer.append('Memoria: ');
	$(storageValues).each(function () {
		storageContainer.append('<button type="button" class="storageBtn">' + this + " GB" + '</button>');
	});
	storageContainer.append('<br><br>');

	var colorContainer = $("#colorContainer");
	colorContainer.append('Colore: ');
	$(colorValues).each(function () {
		colorContainer.append('<button type="button" class="colorBtn">' + this + '</button>');
	});
	colorContainer.append('<br><br>');

	

	
	
	
});