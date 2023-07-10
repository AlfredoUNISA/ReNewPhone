var n_img = 3;
var corrente = 1;

function succ(){
    corrente++;
    if(corrente > n_img){
        corrente = 1;
    }
  
    for(var i = n_img; i > 0; i--){
        document.querySelector("#img_slider img:nth-child(" + i + ")").style.display = "none";
    }
    document.querySelector("#img_slider img:nth-child(" + corrente + ")").style.display = "block";
}

function prec(){
    corrente--;
    if(corrente == 0){
        corrente = n_img;
    }
  
    for(var i = n_img; i > 0; i--){
        document.querySelector("#img_slider img:nth-child(" + i + ")").style.display = "none";
    }
    document.querySelector("#img_slider img:nth-child(" + corrente + ")").style.display = "block";
}

setInterval(succ, 7000);


function toggleAnswer(element) {
      element.nextElementSibling.classList.toggle("show");
    }
    
    // Aggiungi un gestore di eventi per catturare il click sulle stelle
    const stars = document.querySelectorAll(".rating > span");
    
    stars.forEach(function(star, index) {
      star.addEventListener("click", function() {
        resetStars();
        highlightStars(index);
      });
    });
    
    function resetStars() {
      stars.forEach(function(star) {
        star.innerHTML = "&#9734;";
      });
    }
    
    function highlightStars(index) {
      for (let i = 0; i <= index; i++) {
        stars[i].innerHTML = "&#9733;";
      }
    }
    
 function inviaValutazione() {
      var rating = document.querySelector('input[name="rating"]:checked');
      if (rating) {
        var valutazione = rating.value;
        // Puoi qui eseguire le operazioni desiderate con la valutazione selezionata
        console.log("Valutazione:", valutazione);
        alert("Grazie per la tua valutazione di " + valutazione + " stelle!");
      } else {
        alert("Per favore, seleziona una valutazione.");
      }
    }
    
document.addEventListener("DOMContentLoaded", function() {
  var popupOverlay = document.querySelector(".popup-overlay");
  var closePopup = document.querySelector(".close-popup");
  var emailForm = document.querySelector("form");
  var emailInput = document.querySelector("input[type='email']");

  closePopup.addEventListener("click", function() {
    popupOverlay.style.display = "none";
  });

  emailForm.addEventListener("submit", function(event) {
    event.preventDefault();
    var emailAddress = emailInput.value;
    
    alert("Iscrizione avvenuta con successo per l'indirizzo: " + emailAddress);
    emailInput.value = ""; // Resetta il valore dell'input
    popupOverlay.style.display = "none"; // Chiudi il popup
  });
});