document.addEventListener("DOMContentLoaded", function () {
    const outfits = document.querySelectorAll(".outfit");
    const popupOverlay = document.getElementById("popupOverlay");
    const popup = document.getElementById("popup");

    outfits.forEach(outfit => {
        outfit.addEventListener("click", function () {
            const outfitId = this.getAttribute("data-id");
            const outfitName = this.querySelector(".outfit-header").innerText;
            const outfitDesc = this.querySelector(".outfit-description").innerText;
            const capoElements = this.querySelectorAll(".capo-card");

            let capiHTML = "";
            capoElements.forEach(capo => {
                const capoId = capo.id || capo.getAttribute("id"); // Usa id invece di data-id
                const capoName = capo.querySelector(".capo-name").innerText;
                const capoImg = capo.querySelector("img").src;
                capiHTML += `
                    <div class="capo-edit" data-id="${capoId}">
                        <img src="${capoImg}" alt="${capoName}" class="capo-preview">
                        <span class="capo-name">${capoName}</span>
                        <button class="remove-capo" data-id="${capoId}">&times;</button>
                    </div>
                `;
            });

            popup.innerHTML = `
                <div class="popup-content">
                    <h2>Modifica Outfit</h2>
                    <label for="outfitName">Nome:</label>
                    <input type="text" id="outfitName" value="${outfitName}">
                    <label for="outfitDesc">Descrizione:</label>
                    <textarea id="outfitDesc">${outfitDesc}</textarea>
                    <h3>Capi d'Abbigliamento</h3>
                    <div class="capi-container">${capiHTML}</div>
                    <button onclick="saveChanges(${outfitId})">Salva</button>
                    <button onclick="closePopup()">Annulla</button>
                </div>`;

            popupOverlay.style.display = "block";
            popup.style.display = "block";
        });
    });

    popupOverlay.addEventListener("click", closePopup);

    // Event delegation per la rimozione dinamica dei capi
    popup.addEventListener("click", function (event) {
        if (event.target.classList.contains("remove-capo")) {
            const capoElement = event.target.closest(".capo-edit");
            if (capoElement) {
                capoElement.remove();
            }
        }
    });
});

function closePopup() {
    document.getElementById("popupOverlay").style.display = "none";
    document.getElementById("popup").style.display = "none";
}

function saveChanges(outfitId) {
    const newName = document.getElementById("outfitName").value;
    const newDesc = document.getElementById("outfitDesc").value;
    const popup = document.getElementById("popup");

    // Debug per controllare gli elementi dentro il popup
    console.log("Popup HTML generato:", popup.innerHTML);

    const idCapi = Array.from(popup.querySelectorAll(".capo-edit"))
        .map(capo => {
            const id = capo.getAttribute("data-id");
            console.log("Capo trovato:", capo, "ID:", id); // Debug
            return id;
        })
        .filter(id => id !== null) // Rimuove eventuali null
        .join(",");


    window.location.href = `outfitCRUDServlet?id=${outfitId}&nome=${encodeURIComponent(newName)}&descrizione=${encodeURIComponent(newDesc)}&idCapi=${encodeURIComponent(idCapi)}`;
}
