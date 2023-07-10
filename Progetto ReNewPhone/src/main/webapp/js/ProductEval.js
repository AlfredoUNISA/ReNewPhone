$(document).ready(function () {
	loadBrands();

	$("#brandSelect").change(function (e) {
		e.preventDefault();
		getDevices();
	});

	$("#modelSelect").change(function () {
		var modelValue = $("#modelSelect").val();
		if (modelValue != "seleziona")
			$("#formAfterModel").show();
		else
			$("#formAfterModel").hide();
	});

	$("#submitButton").click(function (e) {
		e.preventDefault();
		sendEval();
	})

})

/**
 * Carica i brand disponibili nel database nella select con id "brandSelect"
 */
function loadBrands() {
	$.ajax({
		url: "AjaxProductEvalServlet",
		data: {
			action: "getBrands"
		},
		type: "GET",
		success: function (response) {
			var brands = response;
			$(brands).each(function () {
				var brand = this;
				var html = '<option value="' + brand + '">' + brand + '</option>'
				$("#brandSelect").append(html);
			})
		},
		error: function (e) {
			alert("Errore: " + e);
		}
	});
}

/**
 * Carica i modelli disponibili nel database secondo il modello nella select con id "modelSelect"
 */
function getDevices() {
	$("#modelSelect").empty();
	$("#modelSelect").append('<option value="seleziona"> SELEZIONA </option>');

	var marca = $("#brandSelect").val();
	if (marca != "seleziona") {
		$("#formAfterBrand").show();
		$.ajax({
			url: "AjaxProductServlet",
			data: {
				filterBrand: marca,
				productsPerLoading: 999
			},
			type: "GET",
			success: function (response) {
				var marcaResponse = response;
				$(marcaResponse).each(function () {
					var x = this.groupName;
					var html = '<option value="' + x + '">' + x + '</option>'
					$("#modelSelect").append(html);
				})
			},
			error: function (e) {
				console.log("Errore: " + e);
			}
		});
	} else {
		$("#formAfterBrand").hide();
	}
}

/**
 * Invia i dati inseriti dall'utente al server per calcolare il valore del prodotto
 */
function sendEval() {
	var marca = $("#brandSelect").val();
	var modello = $("#modelSelect").val();
	var condizione = $("#condizione").val();
	var colore = $("#colore").val();
	var storage = $("#storage").val();

	$.ajax({
		url: "AjaxProductEvalServlet",
		data: {
			action: "evaluate",
			brand: marca,
			model: modello,
			condition: condizione,
			storage: storage
		},
		type: "GET",
		success: function (response) {
			// Gestisci la risposta dal server
			var html = '<h2><b> Il tuo ' + marca + ' ' + modello + '(' + storage + 'GB) '  + response + '&euro;';
			$("#risultato").html(html);
		},
		error: function (e) {
			console.log("Errore: " + e);
		}
	});
}