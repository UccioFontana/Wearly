const lineContainer = document.querySelector('.line-container');

window.addEventListener('scroll', () => {
    const scrollPosition = window.scrollY;

    const threshold = 50;

    if (scrollPosition > threshold) {
        lineContainer.classList.add('active');
    } else {
        lineContainer.classList.remove('active');
    }
});



function toggleFAQ(faqElement, textElement, iconElement) {
    faqElement.addEventListener("click", function() {
        var currentRotation = parseFloat(iconElement.getAttribute("data-rotation") || 0);
        var currentX = parseFloat(iconElement.getAttribute("width"));
        var newRotation = currentRotation + 180;
        iconElement.setAttribute("transform", "rotate(" + newRotation + ")");
        iconElement.setAttribute("data-rotation", newRotation);

        if (textElement.style.display === "block") {
            textElement.style.display = "none";
            var newX = currentX - 10;
            iconElement.setAttribute("width",newX);
        } else {
            textElement.style.display = "block";
            var newX = currentX + 10;
            iconElement.setAttribute("width",newX);
        }
    });
}

toggleFAQ(document.getElementById("first_faq"), document.getElementById("first_faq_text"), document.getElementById("first_faq_icon"));
toggleFAQ(document.getElementById("second_faq"), document.getElementById("second_faq_text"), document.getElementById("second_faq_icon"));
toggleFAQ(document.getElementById("third_faq"), document.getElementById("third_faq_text"), document.getElementById("third_faq_icon"));
toggleFAQ(document.getElementById("forth_faq"), document.getElementById("forth_faq_text"), document.getElementById("forth_faq_icon"));
toggleFAQ(document.getElementById("fifth_faq"), document.getElementById("fifth_faq_text"), document.getElementById("fifth_faq_icon"));
toggleFAQ(document.getElementById("sixth_faq"), document.getElementById("sixth_faq_text"), document.getElementById("sixth_faq_icon"));
