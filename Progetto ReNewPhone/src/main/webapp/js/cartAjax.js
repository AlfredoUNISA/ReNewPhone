$(document).ready(function () {
	var userId = "<%=CURRENT_USER_ID%>";
	
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
				var cart = response.cartList;
				var sum = response.sum;
				console.log(cart);
				console.log(sum);

				var tableDiv = $(".tableCart");
				var sumDiv = $(".sumCart");
				
				if(cart == null){
					tableDiv.html("");
					sumDiv.html("");
					tableDiv.append("<h2>Il carrello è vuoto!</h2>");
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
								"<td><button id='" + product.id + "'>-</button></td>" +
								"</tr>");
					}
					sumDiv.append("<h2>Totale: <b><i>" + sum + " &euro;</i></b></h2>");
				}



			}
		});
	}

});

