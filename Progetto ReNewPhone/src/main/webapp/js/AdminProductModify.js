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
var foundDevice = null;

var brandSelect = $("#brandSelect");
var deviceSelect = $("#deviceSelect");
var storageSelect = $("#storageSelect");
var ramSelect = $("#ramSelect");
var displaySizesSelect = $("#displaySizesSelect");
var colorSelect = $("#colorSelect");
var stateSelect = $("#stateSelect");

$(document).ready(function () {
	loadBrands();

	brandSelect.change(function (e) {
		//console.clear();
		updateBrandAndGoOn();
	});

	deviceSelect.change(function (e) {
		//console.clear();
		updateDeviceAndGoOn();
	});

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
		findProduct(-1, false);
	});

	$("#searchIdSelect").prop("disabled", true);
	$("#searchButton").prop("disabled", true);

	$("#searchIdCheck").change(function (e) {
		if (this.checked) {
			// disabilita tutte le select
			$("#brandSelect").prop("disabled", true);
			$("#deviceSelect").prop("disabled", true);
			$("#storageSelect").prop("disabled", true);
			$("#ramSelect").prop("disabled", true);
			$("#displaySizesSelect").prop("disabled", true);
			$("#colorSelect").prop("disabled", true);
			$("#stateSelect").prop("disabled", true);

			$("#searchIdSelect").prop("disabled", false);
			$("#searchButton").prop("disabled", false);

		} else {
			// abilita tutte le select
			$("#brandSelect").prop("disabled", false);
			$("#deviceSelect").prop("disabled", false);
			$("#storageSelect").prop("disabled", false);
			$("#ramSelect").prop("disabled", false);
			$("#displaySizesSelect").prop("disabled", false);
			$("#colorSelect").prop("disabled", false);
			$("#stateSelect").prop("disabled", false);

			$("#searchIdSelect").prop("disabled", true);
			$("#searchButton").prop("disabled", true);
		}
	});

	$("#searchButton").click(function (e) {
		findProduct($("#searchIdSelect").val(), true);
	});

})

/**
 * Carica i brand disponibili nel database nella select con id "brandSelect".
 * Chiama loadDevices().
 */
function loadBrands() {
	brandSelect.empty();

	$.ajax({
		url: "AjaxProductEvalServlet",
		data: {
			action: "getBrands"
		},
		type: "GET",
		success: function (response) {
			$(response).each(function () {
				brandSelect.append('<option value="' + this + '">' + this + '</option>');
			})

			brandSelect.val(brandSelect.find("option:first").val());
			updateBrandAndGoOn(response);
		},
		error: function (e) {
			alert("Errore: " + e);
		}
	});
}

function updateBrandAndGoOn(response) {
	console.log("Brands Disponibili: ") 
	console.log(response);

	currentDevice.brand = brandSelect.val();
	console.log("Brand Attivo: " + currentDevice.brand);

	loadDevices();
}

/**
 * Carica i modelli disponibili nel database secondo il modello nella select con id "modelSelect".
 * Chiama load().
 */
function loadDevices() {
	deviceSelect.empty();

	if (currentDevice.brand != undefined) {
		$.ajax({
			url: "AjaxProductServlet",
			data: {
				filterBrand: currentDevice.brand,
				productsPerLoading: 999
			},
			type: "GET",
			success: function (response) {


				$(response).each(function () {
					deviceSelect.append('<option value="' + this.groupName + '">' + this.groupName + '</option>');
				});

				deviceSelect.val(deviceSelect.find("option:first").val());
				updateDeviceAndGoOn(response);
			},
			error: function (e) {
				alert("Errore: " + e);
			}
		});
	} else {
		//$("#formAfterBrand").hide();
	}
}

function updateDeviceAndGoOn(response) {
	console.log("Dispositivi Disponibili: ") 
	console.log(response);

	currentDevice.name = deviceSelect.val();
	console.log("Device Attivo: " + currentDevice.name);

	writeDeviceData();
}

/**
 * Scrive i dati del dispositivo selezionato nella variabile currentDevice.
 */
function writeDeviceData() {
	$.ajax({
		type: "GET",
		url: "admin-modify",
		data: {
			action: "getDeviceData",
			name: currentDevice.name
		},
		dataType: "json",
		success: function (response) {
			jsonCurrentDevice = response.matches;
			console.log("Match Dispositivo Attuale: ")
			console.table(jsonCurrentDevice);

			currentDevice.storages = response.storages;

			loadStorage();
		},
		error: function (e) {
			console.log("Errore: " + e);
		}
	});
}

function loadStorage() {
	// Il nome è sempre lo stesso, quindi non serve cercarlo nel json
	storageSelect.empty();
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

	findProduct(-1, false);
}

/**
 * Invia i dati inseriti dall'utente al server per calcolare il valore del prodotto
 */
function findProduct(id, withID) {
	// Cerca all'interno del json, l'id del prodotto in base a tutti i valori selezionati
	if (withID == false) {
		var found;
		jsonCurrentDevice.forEach(element => {
			if (element.storage == storageSelect.val() && element.ram == ramSelect.val() && element.display_size == displaySizesSelect.val() &&
				element.color == colorSelect.val() && element.state == stateSelect.val()) {
				found = element;
			}
		});
		id = found.id;
		console.log("ID: " + id);
	}

	$.ajax({
		type: "GET",
		url: "admin-modify",
		data: {
			action: "getDeviceWithID",
			id: id
		},
		dataType: "json",
		success: function (response) {

			foundDevice = response.product;
			$("#result").empty();
			
			$("#result").append("ID: " + foundDevice.id + "<br>");
			$("#result").append("Name: " + foundDevice.name + "<br>");
			$("#result").append("Brand: " + foundDevice.brand + "<br>");
			$("#result").append("RAM: " + foundDevice.ram + " GB<br>");
			$("#result").append("Storage: " + foundDevice.storage + " GB<br>");
			$("#result").append("Display Size: " + foundDevice.display_size + " \"<br>");
			$("#result").append("Category: " + foundDevice.category + "<br>");
			$("#result").append("Price: " + foundDevice.price + " \u20AC<br>"); 
			$("#result").append("Quantity: " + foundDevice.quantity + "<br>");
			$("#result").append("Color: " + foundDevice.color + "<br>");
			$("#result").append("State: " + foundDevice.state + "<br>");
			$("#result").append("Year: " + foundDevice.year + "<br>");
			$("#result").append("Model: " + foundDevice.model + "<br>");

		
			$("#searchIdSelect").val(foundDevice.id);
			startModify();
		},
		error: function (e) {
			console.log("Error: " + e);
		}
	});
}

function startModify() {
	console.log("Start Modify");
	$("#result").empty();
	$("#result").append("ID: " + foundDevice.id + "<br>");
	$("#result").append("Name: " + foundDevice.name + "<br>");
	$("#result").append("Brand: " + foundDevice.brand + "<br>");
	$("#result").append("Category: " + foundDevice.category + "<br>");
	$("#result").append("Color: " + foundDevice.color + "<br>");
	$("#result").append("State: " + foundDevice.state + "<br>");
	$("#result").append("Year: " + foundDevice.year + "<br>");
	$("#result").append("Model: " + foundDevice.model + "<br>");

	$("#modifyRam").val(foundDevice.ram);
	$("#modifyStorage").val(foundDevice.storage);
	$("#modifyDisplay").val(foundDevice.display_size);
	$("#modifyPrice").val(foundDevice.price); 
	$("#modifyQuantity").val(foundDevice.quantity);

	$("#modifyButton").click(function (e) { 
		e.preventDefault();
		$.ajax({
			type: "POST",
			url: "admin-modify",
			data: {
				action: "modify",
				id: foundDevice.id,
				name: foundDevice.name,
				ram: $("#modifyRam").val(),
				display_size: $("#modifyDisplay").val(),
				storage: $("#modifyStorage").val(),
				price: $("#modifyPrice").val(),
				quantity: $("#modifyQuantity").val(),
				color: foundDevice.color,
				brand: foundDevice.brand,
				year: foundDevice.year,
				state: foundDevice.state
			},
			success: function () {
				alert("Modifica effettuata con successo");
				location.reload();
			},
			error: function (e) {
				alert("Error: " + e);
			}
		});
	});

}