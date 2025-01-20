const text = "Hello, Uccio";

const typingSpeed = 200;

const welcomeBackText = document.getElementById("welcomeBackText");

let index = 0;
function typeWriter() {
    if (index < text.length) {
        welcomeBackText.textContent += text.charAt(index);
        index++;
        setTimeout(typeWriter, typingSpeed);
    }
}

window.onload = typeWriter;

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



