var userId;
var tableDiv;
var sumDiv;
var sum;

$(document).ready(function () {
	userId = "<%=CURRENT_USER_ID%>";
	tableDiv = $(".tableCart");
	sumDiv = $(".sumCart");

	loadCart();

	function loadCart() {
		$.ajax({
			type: "GET",
			url: "my-cart",
			data: {
				action: "show",
				user: userId
			},
			dataType: "json",
			success: function (response) {
				console.log(response);
				var cart = response.cartList;
				sum = response.sum;

				if (cart.length == 0) {
					tableDiv.html("");
					sumDiv.html("");

					tableDiv.append("<h2>Il carrello \u00E8 vuoto!</h2>");

					// invalidate submit button
					$("#submitBtn").prop("disabled", true);
				} else {
					tableDiv.html("");
					sumDiv.html("");
					tableDiv.append("<table>" +
						"<thead>" +
						"<tr>" +
						"<th>Nome</th>" +
						"<th>Prezzo Singolo</th>" +
						"<th>Quantit\u00E0</th>" +
						"<th>Rimuovi</th>" +
						"</tr>" +
						"</thead>" +
						"<tbody>" +
						"</tbody>" +
						"</table>");
					var tableBody = tableDiv.find("tbody");
					for (var i = 0; i < cart.length; i++) {
						var product = cart[i];
						tableBody.append("<tr>" +
							"<td><a href='products?action=details&name=" + product.name + "'>" + product.name + "</a></td>" +
							"<td><b><i>" + product.price + " &euro;</i></b></td>" +
							"<td>" + product.quantity + "</td>" +
							"<td><button class='removeBtn' value='" + product.id + "'>-</button></td>" +
							"</tr>");
					}
					sumDiv.append("<h2>Totale: <b><i>" + sum + " &euro;</i></b></h2>");
					$("#submitBtn").prop("disabled", false);
				}
			}
		});
	}

	tableDiv.on("click", ".removeBtn", function () {
		var productId = $(this).val();
		var conf = confirm("Sei sicuro di voler rimuovere questo prodotto (id=" + productId + ") dal carrello?");

		if (conf) {
			$.ajax({
				type: "GET",
				url: "my-cart",
				data: {
					action: "delete",
					user: userId,
					product: productId
				},
				success: function () {
					loadCart();
				}
			});
		}
	});

	$("#submitBtn").click(function () {
		if (userId == -1) {
			alert("Devi effettuare il login per procedere all'acquisto!");
			return;
		}
		else {

			var conf = confirm("Sei sicuro di voler procedere all'acquisto?");

			if (conf) {
				$.ajax({
					type: "GET",
					url: "my-cart",
					data: {
						action: "finalize",
						user: userId,
						total: sum
					},
					success: function () {
						loadCart();
						alert("Acquisto effettuato con successo!");
					}
				});
			}
		}
	});
});
