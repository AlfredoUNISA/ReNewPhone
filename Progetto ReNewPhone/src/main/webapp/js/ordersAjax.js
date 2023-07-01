$(document).ready(function () {
	var currentPage = 1;
	var ordersPerPage = 3; // Numero di ordini da visualizzare per pagina

	loadOrders(currentPage);

	// Gestione del clic sulle pagine
	$(document).on("click", ".pagination-link", function (e) {
		e.preventDefault();
		currentPage = parseInt($(this).text());
		loadOrders(currentPage);
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
					row.append($("<td>").text(this.id_user));
					row.append($("<td>").text(this.total));
					tableBody.append(row);
				});
				
				// Genera la navigazione della paginazione
				var pagination = $(".pagination");
				pagination.empty();
				console.log(pagination);
				
				var totalPages = Math.ceil(data.totalCount / ordersPerPage);
				console.log(totalPages);
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
				alert("Errore durante il caricamento degli ordini.");
			},
		});
	}
});
