function updateRow(id){
    const t =document.getElementById("update"+id);
    if(t.style.display === 'none'|| t.style.display === ""){
        t.style.display = "table-row";
    }
    else
        t.style.display='none';
}


function deleteUser(idUtente){
    const xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if(xhr.readyState==4 && xhr.status==200){
            alert("Cancellazione effettuata con successo");
            location.reload();
        }
    };

    const url = `adminPageCRUDServlet?idUtente=${encodeURIComponent(idUtente)}&type=delete`;
    xhr.open("GET", url, true);
    xhr.send();
}


function showRow(num){
    const t =document.getElementById("addRow"+num);
    if(t.style.display === 'none'|| t.style.display === ""){
        t.style.display = "table-row";
    }
    else
        t.style.display='none';
}

function addUser(){
    const nome = document.getElementById("nome").value;
    const cognome = document.getElementById("cognome").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    if(nome ==="" || cognome==="" || email==="" || password===""){
        alert("riempi tutti i campi prima di inviare");
        return;
    }

    const xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function() {
        if(xhr.readyState==4 && xhr.status==200){
            try {
                const response = JSON.parse(xhr.responseText);
                if (response.success) {
                    alert("Utente aggiunto con successo");
                    location.reload();
                } else {
                    alert("Impossibile effettuare l'aggiunto");
                    location.reload();
                }
            } catch (e) {
                alert("Errore nella risposta del server");
            }
        }
    };

    const url = `adminPageCRUDServlet?nome=${encodeURIComponent(nome)}&cognome=${encodeURIComponent(cognome)}&email=${encodeURIComponent(email)}&password=${encodeURIComponent(password)}&type=add`;
    xhr.open("GET", url, true);
    xhr.send();

}



function updateUser(idUtente){
    const nome = document.getElementById("n"+idUtente).value;
    const cognome = document.getElementById("c"+idUtente).value;
    const email = document.getElementById("e"+idUtente).value;

    if(nome ==="" && cognome === "" && email === "" && tipo===""){
        alert("non hai inserito nessun campo da modificare");
        return;
    }
    const xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if(xhr.readyState==4 && xhr.status==200){
            alert("Modifica effettuata con successo");
            location.reload();
        }
    };

    const url = `adminPageCRUDServlet?idUtente=${encodeURIComponent(idUtente)}&nome=${encodeURIComponent(nome)}&cognome=${encodeURIComponent(cognome)}&email=${encodeURIComponent(email)}&type=update`;
    xhr.open("GET", url, true);
    xhr.send();

}