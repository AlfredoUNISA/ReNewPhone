var currentDevice = {
	id: -1,
	brand: "",
	name: "",
	storages: [],
	rams: [],
	displaySizes: [],
	colors: [],
	states: []
};

var jsonCurrentDevice;
var found;

var currentUserId = '<%= CURRENT_USER_ID %>';
var isAdmin = "<%= IS_CURRENT_USER_ADMIN %>";

var storageSelect;
var ramSelect;
var displaySizesSelect;
var colorSelect;
var stateSelect;

$(document).ready(function () {
	storageSelect = $("#storageSelect");
	ramSelect = $("#ramSelect");
	displaySizesSelect = $("#displaySizesSelect");
	colorSelect = $("#colorSelect");
	stateSelect = $("#stateSelect");

	jsonCurrentDevice = getJson();
	console.log("JSON: ");
	console.table(jsonCurrentDevice);

	jsonCurrentDevice.forEach(element => {
		if (currentDevice.storages.includes(element.storage) == false)
			currentDevice.storages.push(element.storage);
	});

	console.log("Storages Disponibili: ");
	console.log(currentDevice.storages);

	loadStorage();

	storageSelect.change(function (e) {
		//console.clear();
		updateStorageAndGoOn();
	});

	ramSelect.change(function (e) {
		//console.clear();
		updateRamAndGoOn();
	});

	displaySizesSelect.change(function (e) {
		//console.clear();
		updateDisplaySizeAndGoOn();
	});

	colorSelect.change(function (e) {
		//console.clear();
		updateColorAndGoOn();
	});

	stateSelect.change(function (e) {
		//console.clear();
		findProductId();
	});

	$('#addToCartBtn').click(function (e) {
		var currentProductQuantity = $('#quantitySelect').val();
		if (isAdmin == "true" || isAdmin == true) {
			alert("Non puoi aggiungere prodotti al carrello se sei un amministratore");
		} else {
			$.ajax({
				type: "POST",
				url: "my-cart",
				data: {
					"action": "add",
					"user": currentUserId,
					"product": found.id,
					"quantity": currentProductQuantity
				},
				success: function () {
					alert("Prodotto aggiunto al carrello");
				},
				error: function () {
					alert("Errore durante l'aggiunta al carrello");
				}
			});
		}
	});

})

/**
 * Ottiene il JSON con tutti i dettagli del prodotto
 * @returns JSON dei dettagli del prodotto
 */
function getJson() {
	// Prendi la stringa JSON dei dettagli del prodotto attraverso l'attributo JSP
	var jsonString = '<%= request.getAttribute("product-details-json") %>;'
	// Rimuovi il ; finale
	jsonString = jsonString.substring(0, jsonString.length - 1);

	// console.log(jsonString);

	return JSON.parse(jsonString);
}

function loadStorage() {
	storageSelect.empty();
	console.log(storageSelect);

	$(currentDevice.storages).each(function () {
		storageSelect.append('<option value="' + this + '">' + this + ' GB</option>');
	});
	storageSelect.val(storageSelect.find("option:first").val());

	updateStorageAndGoOn();
}

function updateStorageAndGoOn() {
	// Da questo punto in poi, già abbiamo i valori necessari in currentDevice
	console.log("Storage Attivo: " + storageSelect.val());
	loadRam();
}

function loadRam() {
	// Cerca i valori di ram disponibili per lo storage selezionato
	ramSelect.empty();
	currentDevice.rams = [];

	jsonCurrentDevice.forEach(element => {
		if (element.storage == storageSelect.val() &&
			(currentDevice.rams == undefined || currentDevice.rams.length == 0 || currentDevice.rams.includes(element.ram) == false)) {
			currentDevice.rams.push(element.ram);
		}
	});

	$(currentDevice.rams).each(function () {
		ramSelect.append('<option value="' + this + '">' + this + ' GB</option>');
	});
	ramSelect.val(ramSelect.find("option:first").val());

	updateRamAndGoOn();
}

function updateRamAndGoOn() {
	console.log("Ram Attiva: " + ramSelect.val());
	loadDisplaySize();
}

function loadDisplaySize() {
	// Cerca i valori di displaySize disponibili in base alla ram selezionata e allo storage selezionato
	displaySizesSelect.empty();
	currentDevice.displaySizes = [];

	jsonCurrentDevice.forEach(element => {
		if (element.ram == ramSelect.val() && element.storage == storageSelect.val() &&
			(currentDevice.displaySizes == undefined || currentDevice.displaySizes.length == 0 || currentDevice.displaySizes.includes(element.display_size) == false)) {
			currentDevice.displaySizes.push(element.display_size);
		}

	});

	$(currentDevice.displaySizes).each(function () {
		displaySizesSelect.append('<option value="' + this + '">' + this + ' \"</option>');
	});
	displaySizesSelect.val(displaySizesSelect.find("option:first").val());

	updateDisplaySizeAndGoOn();
}

function updateDisplaySizeAndGoOn() {
	console.log("DisplaySize Attivo: " + displaySizesSelect.val());
	loadColor();
}

function loadColor() {
	// Cerca i valori di color disponibili in base alla displaySize selezionata e alla ram selezionata e allo storage selezionato
	colorSelect.empty();
	currentDevice.colors = [];

	jsonCurrentDevice.forEach(element => {
		if (element.display_size == displaySizesSelect.val() && element.ram == ramSelect.val() && element.storage == storageSelect.val() &&
			(currentDevice.colors == undefined || currentDevice.colors.length == 0 || currentDevice.colors.includes(element.color) == false)) {
			currentDevice.colors.push(element.color);
		}
	});

	$(currentDevice.colors).each(function () {
		colorSelect.append('<option value="' + this + '">' + this + '</option>');
	});
	colorSelect.val(colorSelect.find("option:first").val());

	updateColorAndGoOn();
}

function updateColorAndGoOn() {
	console.log("Color Attivo: " + colorSelect.val());
	loadState();
}

function loadState() {
	// Cerca i valori di state disponibili in base alla color selezionata e alla displaySize selezionata e alla ram selezionata e allo storage selezionato
	stateSelect.empty();
	currentDevice.states = [];

	jsonCurrentDevice.forEach(element => {
		if (element.color == colorSelect.val() && element.display_size == displaySizesSelect.val() && element.ram == ramSelect.val() &&
			element.storage == storageSelect.val() && (currentDevice.states == undefined || currentDevice.states.length == 0 || currentDevice.states.includes(element.state) == false)) {
			currentDevice.states.push(element.state);
		}
	});

	$(currentDevice.states).each(function () {
		stateSelect.append('<option value="' + this + '">' + this + '</option>');
	});
	stateSelect.val(stateSelect.find("option:first").val());

	console.log(currentDevice);
	findProductId();
}

/**
 * Invia i dati inseriti dall'utente al server per calcolare il valore del prodotto
 */
function findProductId() {
	// Cerca all'interno del json, l'id del prodotto in base a tutti i valori selezionati
	jsonCurrentDevice.forEach(element => {
		if (element.storage == storageSelect.val() && element.ram == ramSelect.val() && element.display_size == displaySizesSelect.val() &&
			element.color == colorSelect.val() && element.state == stateSelect.val()) {
			found = element;
		}
	});
	console.log("ID: " + found.id);

	$("#priceContainer").html("");

	if (found.quantity == 0) {
		$("#quantitySelect").prop("disabled", true);
		$("#addToCartBtn").prop("disabled", true);
	} else {
		$("#quantitySelect").prop("disabled", false);
		$("#addToCartBtn").prop("disabled", false);
		for (var i = 1; i <= 5; i++)
			$("#quantityOption" + i).prop("disabled", false);
	}

	if (found.quantity < 5) {
		for (var i = 1; i <= 5; i++) {
			//console.log("Quantity: " + quantity);
			//console.log("i: " + i);
			if (i <= found.quantity)
				$("#quantityOption" + i).prop("disabled", false);
			else
				$("#quantityOption" + i).prop("disabled", true);
		}
	}

	$("#priceContainer").html(found.price + " \u20AC");
	// diminuisci la dimensione del font per remainingQuantityContainer
	$("#remainingQuantityContainer").css("font-size", "small");

	if (found.quantity == 0)
		$("#remainingQuantityContainer").html("Non disponibile");
	else
		$("#remainingQuantityContainer").html("Ancora " + found.quantity + " in magazzino");

}


  function closeBanner() {
      var banner = document.querySelector('.banner');
      banner.style.display = 'none';
    }
