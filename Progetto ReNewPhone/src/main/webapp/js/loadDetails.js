var ramValues = [];
var displaySizeValues = [];
var storageValues = [];
var colorValues = [];
var stateValues = [];

var json;

$(document).ready(function () {
	json = getJson();
	console.log(json);

	insertInArray();

	initializeHTML();

	getSelectedValuesAndUpdatePrice();

	getSelectedValuesAndUpdatePrice();
});

/**
 * Ottiene il JSON con tutti i dettagli del prodotto
 * @returns JSON dei dettagli del prodotto
 */
function getJson() { 
	// Prendi la stringa JSON dei dettagli del prodotto attraverso l'attributo JSP
	var jsonString = '<%= request.getAttribute("product-details") %>;'

	// Rimuovi il ; finale
	jsonString = jsonString.substring(0, jsonString.length - 1);

	// console.log(jsonString);
	
	return JSON.parse(jsonString);
}

/**
 * Inserisce tutti i valori dei dettagli del prodotto in un array
 */
function insertInArray() {
	$(json).each(function () {
		if (!ramValues.includes(this.ram))
			ramValues.push(this.ram);

		if (!displaySizeValues.includes(this.display_size))
			displaySizeValues.push(this.display_size);

		if (!storageValues.includes(this.storage))
			storageValues.push(this.storage);

		if (!colorValues.includes(this.color))
			colorValues.push(this.color);

		if (!stateValues.includes(this.state))
			stateValues.push(this.state);
	});
}

/**
 * Appendi i valori ai rispettivi container nell'HTML e se ce ci sono più di un valore, crea i radio button.
 * Seleziona anche i primi valori di ogni gruppo.
 */
function initializeHTML() {
	// RAM
	var ramContainer = $("#ramContainer");
	ramContainer.append('RAM: ');
	if (ramValues.length == 1) {
		ramContainer.append('<b>' + ramValues[0] + ' GB' + '</b>');
	} else {
		$(ramValues).each(function () {
			ramContainer.append('<input type="radio" class="ramBtn" onChange="getSelectedValuesAndUpdatePrice()" value="' + this + '" name="ramGroup"/>' + this + ' GB');
		});
	}
	ramContainer.append('<br><br>');

	// Display size
	var displaySizeContainer = $("#displaySizeContainer");
	displaySizeContainer.append('Display: ');
	if (displaySizeValues.length == 1) {
		displaySizeContainer.append('<b>' + displaySizeValues[0] + " \"" + '</b>');
	} else {
		$(displaySizeValues).each(function () {
			displaySizeContainer.append('<input type="radio" class="displaySizeBtn" onChange="getSelectedValuesAndUpdatePrice()" value="' + this + '" name="displayGroup"/>' + this + " \"");
		});
	}
	displaySizeContainer.append('<br><br>');

	// Storage
	var storageContainer = $("#storageContainer");
	storageContainer.append('Memoria: ');
	if (storageValues.length == 1) {
		storageContainer.append('<b>' + storageValues[0] + " GB" + '</b>');
	} else {
		$(storageValues).each(function () {
			storageContainer.append('<input type="radio" class="storageBtn" onChange="getSelectedValuesAndUpdatePrice()" value="' + this + '" name="storageGroup"/>' + this + ' GB');
		});
	}
	storageContainer.append('<br><br>');

	// Color
	var colorContainer = $("#colorContainer");
	colorContainer.append('Colore: ');
	if (colorValues.length == 1) {
		colorContainer.append('<b>' + colorValues[0] + '</b>');
	} else {
		$(colorValues).each(function () {
			colorContainer.append('<input type="radio" class="colorBtn" onChange="getSelectedValuesAndUpdatePrice()" value="' + this + '" name="colorGroup"/>' + this);
		});
	}
	colorContainer.append('<br><br>');

	// Stato
	var stateContainer = $("#stateContainer");
	stateContainer.append('Stato: ');
	if (stateValues.length == 1) {
		stateContainer.append('<b>' + stateValues[0] + '</b>');
	} else {
		$(stateValues).each(function () {
			stateContainer.append('<input type="radio" class="stateBtn" onChange="getSelectedValuesAndUpdatePrice()" value="' + this + '" name="stateGroup"/>' + this);
		});
	}
	stateContainer.append('<br><br>');

	// Seleziona il primo valore di ogni gruppo
	$(".ramBtn").first().prop("checked", true);
	$(".displaySizeBtn").first().prop("checked", true);
	$(".storageBtn").first().prop("checked", true);
	$(".colorBtn").first().prop("checked", true);
	$(".stateBtn").first().prop("checked", true);
}

/**
 * Ottiene i valori selezionati dai radio button e aggiorna il prezzo
 * @returns array con i valori selezionati
 */
function getSelectedValuesAndUpdatePrice() {
	var selectedRam = $(".ramBtn:checked").val();
	if (selectedRam == undefined)
		selectedRam = ramValues[0];

	var selectedDisplaySize = $(".displaySizeBtn:checked").val();
	if (selectedDisplaySize == undefined)
		selectedDisplaySize = displaySizeValues[0];

	var selectedStorage = $(".storageBtn:checked").val();
	if (selectedStorage == undefined)
		selectedStorage = storageValues[0];

	var selectedColor = $(".colorBtn:checked").val();
	if (selectedColor == undefined)
		selectedColor = colorValues[0];

	var selectedState = $(".stateBtn:checked").val();
	if (selectedState == undefined)
		selectedState = stateValues[0];

	// Stampa i valori selezionati
	// console.log("selected ram: " + selectedRam);
	// console.log("selected display size: " + selectedDisplaySize);
	// console.log("selected storage: " + selectedStorage);
	// console.log("selected color: " + selectedColor);
	// console.log("-------------------");

	var values = [selectedRam, selectedDisplaySize, selectedStorage, selectedColor, selectedState];
	
	// search the values in the json and return the price
	var price = 0;
	$(json).each(function () {
		if (this.ram == values[0] && this.display_size == values[1] && this.storage == values[2] && this.color == values[3] && this.state == values[4])
			price = this.price;
	});
	$("#priceContainer").html('<b>' + price + ' &euro;');
	
	return values;
}