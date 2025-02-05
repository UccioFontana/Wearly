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
                            if (textElement) textElement.id = "text" + capo.id;

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

    document.getElementById("filterSelect").addEventListener("change", function() {
        let filtro = this.value; // Ottieni il valore selezionato

        fetch("filterCapiServlet?filtro=" + filtro) // Richiesta alla servlet
            .then(response => response.json()) // Converti la risposta in JSON
            .then(data => {
                let container = document.getElementById("innerItemsContainer");
                container.innerHTML = ""; // Svuota il contenitore delle card

                if (data.length === 0) {
                    container.innerHTML = "<p>No saved clothing items</p>";
                } else {
                    data.forEach(c => {
                        let card = `
                <div class="col-md-3">
                    <div class="wsk-cp-product">
                        <div class="wsk-cp-img">
                            <img src="${c.immagine}" alt="Product" class="img-responsive" height="330px"/>
                        </div>
                        <div class="wsk-cp-text">
                            <div class="category">
                                <span style="font-size: 0.5rem;">${c.stato}</span>
                            </div>
                            <div class="title-product">
                                <h3 style="font-size: 0.8rem;">${c.nome}</h3>
                            </div>
                            <div class="description-prod">
                                <p style="font-size: 0.8rem;">${c.descrizione}</p>
                            </div>
                            <div class="card-footer">
                                <div class="wcf-left"><span class="price">${c.stagione}</span></div>
                                <div class="wcf-right"><a href="#" class="buy-btn"><i class="zmdi zmdi-shopping-basket"></i></a></div>
                            </div>
                        </div>
                    </div>
                </div>`;
                        container.innerHTML += card; // Aggiungi la nuova card
                    });
                }
            })
            .catch(error => console.error("Errore nella richiesta:", error));
    });


function saveOutfitAI() {
    let buttons = ["button1", "button2", "button3"];
    let chosenOutfitId = null;

    // Trova il bottone selezionato
    buttons.forEach(buttonId => {
        let button = document.getElementById(buttonId);
        if (button && button.textContent === "Selected!") {
            chosenOutfitId = buttonId;
        }
    });

    if (!chosenOutfitId) {
        alert("No selected outfit!");
        return;
    }

    // Estrai il numero dal bottone (es. "button3" → "3")
    let outfitNumber = chosenOutfitId.replace("button", "");

    // Seleziona la categoria corrispondente
    let categoryRow = document.getElementById("category" + outfitNumber);

    if (!categoryRow) {
        console.warn("No categoryRow found for outfit", outfitNumber);
        return;
    }

    // Trova gli <h3> e ottiene gli ID numerici
    let h3Elements = categoryRow.querySelectorAll(".category h3");
    let ids = [];

    h3Elements.forEach(h3 => {
        let id = h3.id; // Es. "text13"
        let match = id.match(/\d+/); // Estrai numero con RegEx
        if (match) ids.push(match[0]); // Aggiungi solo se esiste un numero
    });

    if (ids.length < 3) {
        alert("Errore: non sono stati trovati 3 ID validi.");
        return;
    }

    let outfitName = document.querySelector("input.outfitForms").value.trim();
    let outfitDescription = document.querySelector("textarea.outfitForms").value.trim();

    if (!outfitName || !outfitDescription) {
        alert("Compila tutti i campi prima di salvare!");
        return;
    }

    // Crea la query string per la servlet
    let queryParams = `?id1=${ids[0]}&id2=${ids[1]}&id3=${ids[2]}&name=${encodeURIComponent(outfitName)}&desc=${encodeURIComponent(outfitDescription)}`;

    // Reindirizza alla servlet con i parametri
    window.location.href = "saveOutfitAIServlet" + queryParams;
}

document.addEventListener("DOMContentLoaded", function () {
    // Seleziona tutti gli elementi cliccabili dei capi d'abbigliamento
    let clothingItems = document.querySelectorAll(".wsk-cp-product");

    clothingItems.forEach(item => {
        item.addEventListener("click", function () {

            // Trova i dati all'interno dell'elemento cliccato
            let imageSrc = item.querySelector(".wsk-cp-img img").src;
            let name = item.querySelector(".title-product h3").textContent.trim();
            let description = item.querySelector(".description-prod p").textContent.trim();
            let category = item.querySelector(".category span").textContent.trim();

            // Precompila il form nel popup
            document.getElementById("image").value = ""; // I file input non possono essere valorizzati per sicurezza
            document.getElementById("name").value = name;
            document.getElementById("description").value = description;


            // Seleziona il valore giusto per la categoria
            let categorySelect = document.getElementById("category");
            for (let option of categorySelect.options) {
                if (option.value.toLowerCase() === category.toLowerCase()) {
                    categorySelect.value = option.value;
                    break;
                }
            }

            // Mostra il popup
            document.getElementById("popupOverlay2").style.display = "block";
            document.getElementById("popup2").style.display = "block";
        });
    });
});