var ramValues = [];
var displaySizeValues = [];
var storageValues = [];
var colorValues = [];
var stateValues = [];

var json;
var jsonIndex = -1;
var currentProductId = -1;
var currentProductQuantity = -1;
var currentUserId = '<%= CURRENT_USER_ID %>';
var isAdmin = "<%=IS_CURRENT_USER_ADMIN%>";

$(document).ready(function () {
	json = getJson();
	//console.log(json);

	insertInArray();

	initializeHTML();

	updatePrice();

	if (isAdmin == true || isAdmin == "true") {
		$("#quantitySelect").hide();
		$("#addToCartBtn").hide();
		$(".buyProduct").empty();
		$(".buyProduct").append("<br><b>Non puoi aggiungere prodotti al carrello se sei un admin</b><hr>");
	}

	$('#addToCartBtn').click(function (e) {
		currentProductQuantity = $('#quantitySelect').val();
		$.ajax({
			type: "POST",
			url: "my-cart?action=add&user=" + currentUserId + "&product=" + currentProductId + "&quantity=" + currentProductQuantity,
			success: function () {
				alert("Prodotto aggiunto al carrello");
			},
			error: function () {
				alert("Errore durante l'aggiunta al carrello");
			}
		});
	});

	if (isAdmin == true || isAdmin == "true") {
		// find the json index of the product with currentProductId
		for (var i = 0; i < json.length; i++) {
			if (json[i].id == currentProductId) {
				jsonIndex = i;
				break;
			}
		}
		

		$(".modifyProduct").append('<button id="modifyBtn">Modifica prodotto</button>');

		$("#modifyBtn").click(function (e) {
			//console.log(json[jsonIndex]);
			$("#modifyBtn").hide();
			$("#modifyProductForm").show();
			$("#modifyProductForm").append('<br>');
			
			$("#modifyProductForm").append('Nome: <input type="text" name="name" value="' + json[jsonIndex].name + '"/><br><br>');
			$("#modifyProductForm").append('Prezzo: <input type="number" name="price" value="' + json[jsonIndex].price + '"/><br><br>');
			$("#modifyProductForm").append('Categoria: <input type="text" name="category" value="' + json[jsonIndex].category + '"/><br><br>');
			$("#modifyProductForm").append('Brand: <input type="text" name="brand" value="' + json[jsonIndex].brand + '"/><br><br>');
			$("#modifyProductForm").append('Anno:<input type="number" name="year" value="' + json[jsonIndex].year + '"/><br><br>');
			$("#modifyProductForm").append('RAM:<input type="number" name="ram" value="' + json[jsonIndex].ram + '"/><br><br>');
			$("#modifyProductForm").append('Display:<input type="number" name="display_size" value="' + json[jsonIndex].display_size + '"/><br><br>');
			$("#modifyProductForm").append('Memoria:<input type="number" name="storage" value="' + json[jsonIndex].storage + '"/><br><br>');
			$("#modifyProductForm").append('Colore:<input type="text" name="color" value="' + json[jsonIndex].color + '"/><br><br>');
			$("#modifyProductForm").append('Stato:<input type="text" name="state" value="' + json[jsonIndex].state + '"/><br><br>');
			$("#modifyProductForm").append('Quantit\u00e0 in Magazzino:<input type="number" name="quantity" value="' + json[jsonIndex].quantity + '"/><br><br>');
			$("#modifyProductForm").append('<input type="submit" value="Modifica" id="submitModify"/>');
			
			$("#submitModify").click(function (e) { 
				e.preventDefault();
				modifyProduct();
			});
		});
	}
});

function modifyProduct() {
	var name = $("input[name='name']").val();
	var price = $("input[name='price']").val();
	var category = $("input[name='category']").val();
	var brand = $("input[name='brand']").val();
	var year = $("input[name='year']").val();
	var ram = $("input[name='ram']").val();
	var display_size = $("input[name='display_size']").val();
	var storage = $("input[name='storage']").val();
	var color = $("input[name='color']").val();
	var state = $("input[name='state']").val();
	var quantity = $("input[name='quantity']").val();

	$.ajax({
		type: "POST",
		url: "admin-modify",
		data: {
			"id": currentProductId,
			"name": name,
			"price": price,
			"category": category,
			"brand": brand,
			"year": year,
			"ram": ram,
			"display_size": display_size,
			"storage": storage,
			"color": color,
			"state": state,
			"quantity": quantity
		},
		success: function () {
			// send redirrect
			window.location.replace("products?action=details&name=" + name);
		},
		error: function () {
			alert.log("Errore con la modifica del prodotto");
		}
	});

}

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
 * Inserisce tutti i valori dei dettagli dal json in un array
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
	var categoryContainer = $("#categoryContainer");
	categoryContainer.append('Categoria: <b>' + json[0].category + '</b><br><br>');

	var brandContainer = $("#brandContainer");
	brandContainer.append('Marca: <b>' + json[0].brand + '</b><br><br>');

	var yearContainer = $("#yearContainer");
	yearContainer.append('Anno: <b>' + json[0].year + '</b><br><br><hr>');

	// RAM
	var ramContainer = $("#ramContainer");
	ramContainer.append('RAM: ');
	if (ramValues.length == 1) {
		ramContainer.append('<b>' + ramValues[0] + ' GB' + '</b>');
	} else {
		$(ramValues).each(function () {
			ramContainer.append('<input type="radio" class="ramBtn" onChange="updatePrice()" value="' + this + '" name="ramGroup"/>' + this + ' GB');
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
			displaySizeContainer.append('<input type="radio" class="displaySizeBtn" onChange="updatePrice()" value="' + this + '" name="displayGroup"/>' + this + " \"");
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
			storageContainer.append('<input type="radio" class="storageBtn" onChange="updatePrice()" value="' + this + '" name="storageGroup"/>' + this + ' GB');
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
			colorContainer.append('<input type="radio" class="colorBtn" onChange="updatePrice()" value="' + this + '" name="colorGroup"/>' + this);
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
			stateContainer.append('<input type="radio" class="stateBtn" onChange="updatePrice()" value="' + this + '" name="stateGroup"/>' + this);
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
 * Ottiene i valori selezionati dai radio button incluso il prezzo
 * @returns array con i valori selezionati
 */
function getSelectedValues() {
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



	var values = [selectedRam, selectedDisplaySize, selectedStorage, selectedColor, selectedState];

	// search the values in the json and return the price
	var price = -1;
	var quantity = 0;
	var id = 0;
	$(json).each(function () {
		if (this.ram == values[0] && this.display_size == values[1] && this.storage == values[2] && this.color == values[3] && this.state == values[4]) {
			price = this.price;
			quantity = this.quantity;
			id = this.id;
		}
	});

	//console.log("selected ram: " + selectedRam);
	//console.log("selected display size: " + selectedDisplaySize);
	//console.log("selected storage: " + selectedStorage);
	//console.log("selected color: " + selectedColor);
	//console.log("price: " + price);
	//console.log("quantity: " + quantity);
	//console.log("id: " + id);
	//console.log("-------------------");

	values.push(price);
	values.push(quantity);
	values.push(id);
	return values;
}

/**
 * Aggiorna il prezzo, la quantità in magazzino e l'id nella pagina
 */
function updatePrice() {
	var values = getSelectedValues();
	var price = values[5];
	var quantity = values[6];
	var id = values[7];


	if (quantity == 0) {
		$("#quantitySelect").prop("disabled", true);
		$("#addToCartBtn").prop("disabled", true);
	} else {
		$("#quantitySelect").prop("disabled", false);
		$("#addToCartBtn").prop("disabled", false);
		for (var i = 1; i <= 5; i++)
			$("#quantityOption" + i).prop("disabled", false);
	}

	if (quantity < 5) {
		for (var i = 1; i <= 5; i++) {
			//console.log("Quantity: " + quantity);
			//console.log("i: " + i);
			if (i <= quantity)
				$("#quantityOption" + i).prop("disabled", false);
			else
				$("#quantityOption" + i).prop("disabled", true);
		}
	}


	var error = false;
	if (price == -1) {
		error = true;
		$("#priceContainer").html('<b>Non disponibile</b>');
	} else {
		$("#priceContainer").html('<b>' + price + ' &euro;');
	}

	if (error == true || quantity == 0) {
		error = true;
		$("#quantityContainer").html('<b>Non in magazzino</b>');
	} else {
		$("#quantityContainer").html('<b>In magazzino: ' + quantity + '</b>');
		currentProductQuantity = quantity;
	}

	if (error == true || id == 0) {
		error = true;
		$("#idContainer").html('<b>Non disponibile</b>');
	} else {
		$("#idContainer").html('<b>ID: ' + id + '</b>');
		currentProductId = id;
	}

}