
let isLoggedIn = false;

const btnMascota = document.getElementById("btn-mascota");
const modalLogged = document.getElementById("modal-logged");
const modalNotLogged = document.getElementById("modal-not-logged");
const btnClose = document.getElementById("btn-close");

btnMascota.addEventListener("click", () => {
    if (isLoggedIn) {
        modalLogged.style.display = "block";
    } else {
        modalNotLogged.style.display = "block";
    }
});

btnClose.addEventListener("click", () => {
    modalNotLogged.style.display = "none";
});

