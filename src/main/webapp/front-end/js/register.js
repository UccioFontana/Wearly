function validateR(){
    const firstName = document.getElementById('firstName').value;
    const lastName = document.getElementById('lastName').value;
    const passwordInput = document.querySelector("input[name='password']");
    const password = passwordInput.value;
    const regex = /.{8,}/;
    const nameRegex = /^[A-Za-zÀ-ÖØ-öø-ÿ]+$/;

    if (registerFields.style.display === "none"){
        return true;
    }

    console.log(firstName)

    if (firstName==="") {
        alert('Fill in the name field');
        return false;
    }
    if (lastName==="") {
        alert('Fill in the surname field');
        return false;
    }


        if (!nameRegex.test(firstName)) {
        alert('The name must not contain numbers or special characters.');
        return false;
    }

    if (!nameRegex.test(lastName)) {
        alert('The surname must not contain numbers or special characters.');
        return false;
    }

    if (!regex.test(password)) {
        alert('The password must be at least 8 characters long.');

        return false;
    } else {
        return true;
    }
}