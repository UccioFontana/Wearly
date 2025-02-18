function validateT(){
    const ticketSubject = document.getElementById('ticketSubject').value;
    const ticketMessaage = document.getElementById('ticketMessage').value;

    const nameRegex = /^[A-Za-zÀ-ÖØ-öø-ÿ\s]+$/;

    console.log(ticketSubject)
    console.log(ticketMessaage)


    if (ticketSubject==="") {
        alert('Fill in the subject field');
        return false;
    }
    if (ticketMessaage==="") {
        alert('Fill in the message field');
        return false;
    }


    if (!nameRegex.test(ticketSubject)) {
        alert('The subject must not contain numbers or special characters.');
        return false;
    }

    if (!nameRegex.test(ticketMessaage)) {
        alert('The message must not contain numbers or special characters.');
        return false;
    }

    return true;
}