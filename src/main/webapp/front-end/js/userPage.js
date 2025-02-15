function typeWriterEffect(text, elementId, speed) {
    const element = document.getElementById(elementId);
    if (!element) return; // Evita errori se l'elemento non esiste

    element.textContent = ""; // Cancella il contenuto iniziale
    let index = 0;

    function type() {
        if (index < text.length) {
            element.textContent += text.charAt(index);
            index++;
            setTimeout(type, speed);
        }
    }

    type(); // Avvia l'animazione
}

// Ottieni il testo originale dall'elemento e avvia l'effetto
window.onload = function () {
    const welcomeBackText = document.getElementById("welcomeBackText").textContent.trim();
    typeWriterEffect(welcomeBackText, "welcomeBackText", 100);
};

/*
 _______ USA QUESTO QUANDO ABBIAMO LA JSP CHE PRENDE IL NOME UTENTE____
const welcomeBackText = document.getElementById("welcomeBackText");

const userName = welcomeBackText.dataset.username;

const text = `Hello, ${userName}`;

const typingSpeed = 100;

let index = 0;
function typeWriter() {
    if (index < text.length) {
        welcomeBackText.textContent += text.charAt(index);
        index++;
        setTimeout(typeWriter, typingSpeed);
    }
}

window.onload = typeWriter;*/


const lineContainer = document.getElementById('lineContainer');


let i = 0;
const interval = setInterval(() => {
    const textWidth = welcomeBackText.offsetWidth;
    lineContainer.style.width = `${textWidth}px`;

    i++;
    if (i === 20) {
        clearInterval(interval); // Ferma l'intervallo quando `i` raggiunge 20
    }
}, 1000); // Ritardo di 1000ms (1 secondo)

function updateUser(idUtente){
    const nome = document.getElementById("newNome").value;
    const cognome = document.getElementById("newCognome").value;
    const email = document.getElementById("newEmail").value;
    const password = document.getElementById("newPassword").value;
    const regex = /.{8,}/;
    const nameRegex = /^[A-Za-zÀ-ÖØ-öø-ÿ]+$/;
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

    if(nome ==="" && cognome === "" && email === "" && password===""){
        alert("You have not entered any fields to edit.");
        return;
    }

    if(nome !== ""){
        if (!nameRegex.test(nome)) {
            alert('The name must not contain numbers or special characters.');
            return;
        }
    }

    if(cognome !== ""){
        if (!nameRegex.test(cognome)) {
            alert('The surname must not contain numbers or special characters.');
            return;
        }
    }
    if(email !==""){
        if( !emailRegex.test(email)){
            alert('The email does not conform to the correct format.');
            return;
        }
    }


    if(password !== ""){
        if (!regex.test(password)) {
            alert('The password must be at least 8 characters long.');
            return;
        }
    }





    const xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if(xhr.readyState==4 && xhr.status==200){
            alert("Change made.");

        }
    };

    const url = `userPageUpdateServlet?idUtente=${encodeURIComponent(idUtente)}&nome=${encodeURIComponent(nome)}&cognome=${encodeURIComponent(cognome)}&email=${encodeURIComponent(email)}&password=${encodeURIComponent(password)}`;
    xhr.open("GET", url, true);
    xhr.send();


}

