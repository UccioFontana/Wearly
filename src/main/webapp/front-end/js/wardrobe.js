document.addEventListener("DOMContentLoaded", function () {
    const generateButton = document.querySelector(".addItemButton:nth-child(2)"); // Secondo bottone
    const popup = document.getElementById("popup");
    const overlay = document.getElementById("popupOverlay");
    const closePopup = document.getElementById("closePopup");

    generateButton.addEventListener("click", function () {
        popup.style.display = "block";
        overlay.style.display = "block";
    });

    closePopup.addEventListener("click", function () {
        popup.style.display = "none";
        overlay.style.display = "none";
    });

    overlay.addEventListener("click", function () {
        popup.style.display = "none";
        overlay.style.display = "none";
    });
});

function closePopup() {
    document.getElementById("popup").style.display = "none";
    document.getElementById("popupOverlay").style.display = "none";
    const buttons = document.querySelectorAll('.selectButton');

    // Rimuovi la classe 'selected' da tutti i pulsanti
    buttons.forEach(btn => {btn.classList.remove('selected'); btn.textContent = "Seleziona"});


}

// Funzione per aprire il popup (da chiamare quando necessario)
function openPopup() {
    console.log("sono qui");

    // Mostra il popup e il loader
    document.getElementById("popup").style.display = "flex";
    document.getElementById("popupOverlay").style.display = "block";
    document.getElementById("loadingOverlay").style.display = "flex"; // Mostra il loader

    var xhr = new XMLHttpRequest();
    xhr.open("GET", "outfitAIServlet", true);

    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            document.getElementById("loadingOverlay").style.display = "none"; // Nasconde il loader

            if (xhr.status === 200) {
                try {
                    var response = JSON.parse(xhr.responseText);
                    console.log(response);

                    var outfitRows = document.querySelectorAll(".outfitRow");

                    response.forEach(function(outfit, index) {
                        if (index >= outfitRows.length) return;

                        var outfitRow = outfitRows[index];
                        var categories = outfitRow.querySelectorAll(".category");

                        console.log("Nome Outfit: ", outfit.nome);
                        console.log("Descrizione: ", outfit.descrizione);

                        outfit.listaCapi.forEach(function(capo, capoIndex) {
                            if (capoIndex >= categories.length) return;

                            var category = categories[capoIndex];
                            var imgElement = category.querySelector("img");
                            var textElement = category.querySelector("h3");

                            if (imgElement) imgElement.src = capo.immagine;
                            if (textElement) textElement.textContent = capo.nome;

                            console.log(capo.nome + ": " + capo.descrizione);
                        });
                    });
                } catch (e) {
                    console.error("Errore nell'analizzare la risposta JSON:", e);
                    alert("Si è verificato un errore nel ricevere i dati.");
                }
            } else {
                console.error('Errore nella richiesta:', xhr.status, xhr.statusText);
                alert("Si è verificato un errore durante la richiesta.");
            }
        }
    };
    xhr.send();
}



// Funzione per gestire la selezione dei pulsanti
function selectButton(button) {
    // Ottieni tutti i pulsanti con la classe 'addItemButton'
    const buttons = document.querySelectorAll('.selectButton');

    // Rimuovi la classe 'selected' da tutti i pulsanti
    buttons.forEach(btn => {btn.classList.remove('selected'); btn.textContent = "Select"});

    // Aggiungi la classe 'selected' al pulsante cliccato
    button.classList.add('selected');
    button.textContent = "Selected!"
}

// Aggiungi l'evento di clic su ogni pulsante
const buttons = document.querySelectorAll('.selectButton');
buttons.forEach(button => {
    button.addEventListener('click', function() {
        selectButton(button);
    });
});


function openPopup2() {
    document.getElementById("popup2").style.display = "flex";
    document.getElementById("popupOverlay2").style.display = "block";
}
function closePopup2() {
    document.getElementById("popup2").style.display = "none";
    document.getElementById("popupOverlay2").style.display = "none";
}
