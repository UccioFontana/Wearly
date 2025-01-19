window.addEventListener('load', function() {
    setTimeout(function() {
        document.getElementById('loading-screen').style.display = 'none';
        document.getElementById('content').style.display = 'block';
    }, 1600);
});

function redirectToPage(url, delay) {
    setTimeout(function() {
        window.location.href = url;
    }, delay);
}

redirectToPage("home.jsp", 2000);