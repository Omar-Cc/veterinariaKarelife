
let carrito = JSON.parse(localStorage.getItem('carrito')) || {};
let total = calcularTotal();

function agregarAlCarrito(nombre, precio, descripcion, imagen, cantidad) {
    cantidad = parseInt(cantidad);

    if (carrito[nombre]) {
        carrito[nombre].cantidad += cantidad;
    } else {
        carrito[nombre] = { precio, descripcion, imagen, cantidad };
    }
    localStorage.setItem('carrito', JSON.stringify(carrito));
}

function calcularTotal() {
    return Object.values(carrito).reduce((acc, item) => acc + item.precio * item.cantidad, 0);
}

function mostrarCarrito() {
    const carritoLista = document.getElementById('carrito');
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
}

function eliminarDelCarrito(nombre) {
    delete carrito[nombre];
    localStorage.setItem('carrito', JSON.stringify(carrito));
    mostrarCarrito();
}

// Solo llamar a mostrarCarrito en la página del carrito
if (window.location.pathname.includes('carrito.html')) {
    mostrarCarrito();
}





// {
//     Ricocat {
//         "precio": 12,
//         "descripcion": "", 
//         "imagen": ruta de acceso a la imagen, 
//         "cantidad": 12
//     }
//     Salmon{
//         "precio": 12,
//         "descripcion": "", 
//         "imagen": ruta de acceso a la imagen, 
//         "cantidad": 7
//     }
// }