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
            alert("Delete made.");
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
    const regex = /.{8,}/;
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    const nameRegex = /^[A-Za-zÀ-ÖØ-öø-ÿ\s]+$/;

    if(nome ==="" || cognome==="" || email==="" || password===""){
        alert("Fill in all fields before sending.");
        return;
    }


    if (!nameRegex.test(nome)) {
        alert('The name must not contain numbers or special characters.');
        return false;
    }

    if (!nameRegex.test(cognome)) {
        alert('The surname must not contain numbers or special characters.');
        return false;
    }


    if (!regex.test(password)) {
        alert('The password must be at least 8 characters long.');
        return;
    }
    if( !emailRegex.test(email)){
        alert('The email does not conform to the correct format.');
        return;
    }

    const xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function() {
        if(xhr.readyState==4 && xhr.status==200){
            try {
                const response = JSON.parse(xhr.responseText);
                if (response.success) {
                    alert("User added.");
                    location.reload();
                } else {
                    alert("User NOT added.");
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
    const nameRegex = /^[A-Za-zÀ-ÖØ-öø-ÿ]+$/;
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;


    if(nome ==="" && cognome === "" && email === "" ){
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


    const xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if(xhr.readyState==4 && xhr.status==200){
            alert("Edit made.");
            location.reload();
        }
    };

    const url = `adminPageCRUDServlet?idUtente=${encodeURIComponent(idUtente)}&nome=${encodeURIComponent(nome)}&cognome=${encodeURIComponent(cognome)}&email=${encodeURIComponent(email)}&type=update`;
    xhr.open("GET", url, true);
    xhr.send();

}