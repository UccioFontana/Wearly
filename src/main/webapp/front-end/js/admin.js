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