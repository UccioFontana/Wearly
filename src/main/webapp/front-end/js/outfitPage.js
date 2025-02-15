document.addEventListener("DOMContentLoaded", function () {
    const outfits = document.querySelectorAll(".outfit");
    const popupOverlay = document.getElementById("popupOverlay");
    const popup = document.getElementById("popup");

    outfits.forEach(outfit => {
        outfit.addEventListener("click", function () {
            const outfitId = outfit.id || outfit.getAttribute("id");
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
                     <button onclick="closePopup()">✖</button>
                    <h2>Edit Outfit</h2>
                    <label for="outfitName" style="font-weight: bold">Name:</label>
                    <input type="text" id="outfitName" value="${outfitName}">
                    <label for="outfitDesc" style="font-weight: bold">Description:</label>
                    <textarea id="outfitDesc">${outfitDesc}</textarea>
                    <h3>Clothing Items</h3>
                    <div class="capi-container">${capiHTML}</div>
                    <button onclick="saveChanges(${outfitId})">Save</button>
                    <button style="background: darkred" onclick="deleteC(${outfitId})">Delete</button>
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


function deleteC(id){

    const xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if(xhr.readyState==4 && xhr.status==200){
            try {
                const response = JSON.parse(xhr.responseText);
                if (response.success) {
                    alert("Cancellation made");
                    location.reload();
                } else {
                    alert("Cancellation not made");
                    location.reload();
                }
            } catch (e) {
                alert("Errore nella risposta del server");
            }
        }
    };

    const url = `outfitCRUDServlet?type=delete&id=${encodeURIComponent(id)}`;
    xhr.open("GET", url, true);
    xhr.send();

}


function closePopup() {
    document.getElementById("popupOverlay").style.display = "none";
    document.getElementById("popup").style.display = "none";
}

function saveChanges(outfitId) {
    const newName = document.getElementById("outfitName").value;
    const newDesc = document.getElementById("outfitDesc").value;
    const popup = document.getElementById("popup");

    const nameRegex = /^[A-Za-zÀ-ÖØ-öø-ÿ\s]+$/;


    if (!nameRegex.test(newName)) {
        alert('The name must not contain numbers or special characters.');
        return;
    }

    if (!nameRegex.test(newDesc)) {
        alert('The description must not contain numbers or special characters.');
        return;
    }

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


    window.location.href = `outfitCRUDServlet?type=update&id=${outfitId}&nome=${encodeURIComponent(newName)}&descrizione=${encodeURIComponent(newDesc)}&idCapi=${encodeURIComponent(idCapi)}`;
}



let selectedClothes = [];

function openPopup2() {
    document.getElementById('popupOverlay2').style.display = 'block';
    document.getElementById('popup2').style.display = 'block';
    loadClothes();
}

function closePopup2() {
    document.getElementById('popupOverlay2').style.display = 'none';
    document.getElementById('popup2').style.display = "none";
}

function loadClothes() {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', 'retrieveCapiServlet', true);
    xhr.onload = function () {
        if (xhr.status === 200) {
            let clothes = JSON.parse(xhr.responseText);
            let container = document.getElementById('clothingContainer');
            container.innerHTML = '';
            clothes.forEach(cloth => {
                let div = document.createElement('div');
                div.classList.add('clothing-item');
                div.dataset.id = cloth.id;
                div.innerHTML = `<img src="${cloth.immagine}" alt="${cloth.nome}"><p>${cloth.nome}</p>`;
                div.onclick = function () { selectClothing(cloth.id, div); };
                container.appendChild(div);
            });
        }
    };
    xhr.send();
}

function selectClothing(id, element) {
    let index = selectedClothes.indexOf(id);
    if (index === -1) {
        selectedClothes.push(id);
        element.classList.add('selected');
    } else {
        selectedClothes.splice(index, 1);
        element.classList.remove('selected');
    }
}

function saveOutfit() {
    let name = document.getElementById('outfitName').value.trim();
    let description = document.getElementById('outfitDescription').value.trim();
    let clothes = selectedClothes.join(',');

    console.log(description)

    const nameRegex = /^[A-Za-zÀ-ÖØ-öø-ÿ\s]+$/;

    if(name ==="" || description===""){
        alert('Fill in all fields before sending.');
        return;
    }


    if (!nameRegex.test(name)) {
        alert('The name must not contain numbers or special characters.');
        return;
    }

    if (!nameRegex.test(description)) {
        alert('The description must not contain numbers or special characters.');
        return;
    }

    window.location.href = `outfitCRUDServlet?type=create&name=${encodeURIComponent(name)}&description=${encodeURIComponent(description)}&clothes=${encodeURIComponent(clothes)}`;
    alert('Add done.');
}
