let carrito = JSON.parse(localStorage.getItem('carrito')) || {};
console.log('Carrito cargado del localStorage:', carrito);
let total = calcularTotal();

function agregarAlCarrito(nombre, precio, descripcion, imagen, cantidad) {
    cantidad = parseInt(cantidad);

    if (carrito[nombre]) {
        carrito[nombre].cantidad += cantidad;
    } else {
        carrito[nombre] = { precio, descripcion, imagen, cantidad };
    }
    localStorage.setItem('carrito', JSON.stringify(carrito));
    console.log('Producto agregado:', carrito);
}

function calcularTotal() {
    return Object.values(carrito).reduce((acc, item) => acc + item.precio * item.cantidad, 0);
}

function mostrarCarrito() {
    console.log('Mostrando carrito');
    const carritoLista = document.getElementById('carrito');
    if (!carritoLista) {
        console.error('Elemento con ID "carrito" no encontrado');
        return;
    }
    carritoLista.innerHTML = '';

    for (const [nombre, info] of Object.entries(carrito)) {
        const subtotal = info.precio * info.cantidad;
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td><button class="btn-eliminar" onclick="eliminarDelCarrito('${nombre}')"><i class="fa-solid fa-trash icono"></i></i></button></td>
            <td><img src="${info.imagen}" alt="${nombre}" class="imagen-producto" style="max-width: 50px;"></td>
            <td>${nombre}</td>
            <td>${info.descripcion}</td>
            <td>${info.cantidad}</td>
            <td>S/. ${info.precio}</td>
            <td>S/. ${subtotal}</td>
        `;
        carritoLista.appendChild(tr);
    }

    total = calcularTotal();
    document.getElementById('total').textContent = `Total: S/. ${total}`;
    console.log('Carrito mostrado:', carrito);
}

function eliminarDelCarrito(nombre) {
    delete carrito[nombre];
    localStorage.setItem('carrito', JSON.stringify(carrito));
    mostrarCarrito();
    console.log('Producto eliminado:', carrito);
}

// Solo llamar a mostrarCarrito en la página del carrito
window.addEventListener('DOMContentLoaded', (event) => {
    // Solo mostrar el carrito en la página de carrito
    if (window.location.pathname.includes('/carrito')) {
        mostrarCarrito();
    }
});