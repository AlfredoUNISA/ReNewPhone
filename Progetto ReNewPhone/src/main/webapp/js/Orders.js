/**
 * UTENTE
 */
$(document).ready(function () {
	var currentPage = 1;
	var ordersPerPage = 3; // Numero di ordini da visualizzare per pagina

	loadOrders(currentPage);

	// Gestione del clic sulle pagine
	$(document).on("click", ".pagination-link", function (e) {
		e.preventDefault();
		$(".userDetails").html("");
		$(".orderDetails").html("");
		currentPage = parseInt($(this).text());
		loadOrders(currentPage);
	});

	$(document).on("click", ".order-details-link", function (e) {
		e.preventDefault();
		var id = $(this).closest("tr").find("td:first").text();
		var id_user = "<%= CURRENT_USER_BEAN.getId() %>";
		var total = $(this).closest("tr").find("td:eq(1)").text();
		console.log(id_user);
		$.ajax({
			type: "GET",
			url: "orderDetails",
			data: {
				id: id,
				id_user: id_user,
				total: total
			},
			dataType: "json",
			success: function (response) {

				$(".orderDetails").empty();

				// Scrivi i dettagli dei prodotti
				var products = response.products;
				var productsDetails = "<h2>Prodotti Ordinati</h2>" + "<table>" +
					"<th>ID</th><th>Nome</th><th>Prezzo</th><th>Quantit\u00E0 Ordinata</th>" + 
					"<th>RAM</th><th>Storage</th><th>Colore</th><th>Stato</th><th>Display</th>";

				for (var i = 0; i < products.length; i++) {
					var product = products[i];
					productsDetails +=
						"<tr>" +
						"<td>" + product.id + "</td>" +
						"<td><a href='products?action=details&name=" + product.name + "'>" + product.name + "</a></td>" +
						"<td><b><i>" + product.price + " &euro;</i></b></td>" +
						"<td><b><i>" + product.quantity + "</i></b></td>" +
						"<td>" + product.ram + "</td>" +
						"<td>" + product.storage + "</td>" +
						"<td>" + product.color + "</td>" +
						"<td>" + product.state + "</td>" +
						"<td>" + product.display_size + "</td>" +
						"</tr>";
				}
				productsDetails += "</table>";
				$(".orderDetails").append(productsDetails);
			}
		});

	});

	function loadOrders(page) {
		$.ajax({
			url: "orders",
			data: {
				page: page,
				pageSize: ordersPerPage,
			},
			dataType: "json",
			success: function (data) {
				console.log(data);

				var tableBody = $("table tbody");
				tableBody.empty();

				$(data.orders).each(function () {
					var row = $("<tr>");
					row.append($("<td>").text(this.id));
					row.append($("<td>").text(this.total));
					row.append($("<td>").text(this.order_date));
					row.append($("<td>").html("<a href='#' class='order-details-link'>Dettagli</a>"));
					tableBody.append(row);
				});

				// Genera la navigazione della paginazione
				var pagination = $(".pagination");
				pagination.empty();

				var totalPages = Math.ceil(data.totalCount / ordersPerPage);
				for (var i = 1; i <= totalPages; i++) {
					var link = $("<a>", {
						class: "pagination-link",
						href: "#",
						text: i,
					});
					if (i === currentPage) {
						link.addClass("active");
					}
					pagination.append(link);
					pagination.append(" ");
				}

			},
			error: function () {
				alert("Errore durante il caricamento degli ordini. Prova a riloggarti.");
			},
		});
	}
});
