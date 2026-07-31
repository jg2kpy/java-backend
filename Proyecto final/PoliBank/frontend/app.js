// URL base del backend Spring Boot
const BASE_URL = 'http://localhost:8080';

// Credenciales del usuario autenticado (guardadas en memoria, no en localStorage)
let credenciales = null; // { username, password }

// =====================================================================
// Utilidades HTTP
// =====================================================================

function authHeader() {
  return 'Basic ' + btoa(credenciales.username + ':' + credenciales.password);
}

async function get(ruta) {
  const respuesta = await fetch(BASE_URL + ruta, {
    method: 'GET',
    headers: {
      'Authorization': authHeader(),
      'Content-Type': 'application/json'
    }
  });
  return respuesta;
}

async function post(ruta, cuerpo, conAuth = true) {
  const cabeceras = { 'Content-Type': 'application/json' };
  if (conAuth) {
    cabeceras['Authorization'] = authHeader();
  }
  const respuesta = await fetch(BASE_URL + ruta, {
    method: 'POST',
    headers: cabeceras,
    body: JSON.stringify(cuerpo)
  });
  return respuesta;
}

// =====================================================================
// Navegación entre secciones
// =====================================================================

function mostrarAuth() {
  document.getElementById('seccion-auth').hidden = false;
  document.getElementById('seccion-dashboard').hidden = true;
}

function mostrarDashboard() {
  document.getElementById('seccion-auth').hidden = true;
  document.getElementById('seccion-dashboard').hidden = false;
  document.getElementById('nav-username').textContent = credenciales.username;
}

function mostrarLogin() {
  document.getElementById('form-login').hidden = false;
  document.getElementById('form-registro').hidden = true;
  limpiarErrores();
}

function mostrarRegistro() {
  document.getElementById('form-login').hidden = true;
  document.getElementById('form-registro').hidden = false;
  limpiarErrores();
}

function limpiarErrores() {
  const loginError = document.getElementById('login-error');
  const registroError = document.getElementById('registro-error');
  loginError.hidden = true;
  loginError.textContent = '';
  registroError.hidden = true;
  registroError.textContent = '';
}

// =====================================================================
// Autenticación
// =====================================================================

async function login() {
  const username = document.getElementById('login-username').value.trim();
  const password = document.getElementById('login-password').value;

  if (!username || !password) {
    mostrarError('login-error', 'Completá usuario y contraseña.');
    return;
  }

  // Guardamos credenciales temporalmente para hacer el request
  credenciales = { username, password };

  try {
    // Verificamos las credenciales intentando obtener la cuenta
    const respuesta = await get('/cuentas/me');

    if (respuesta.ok) {
      mostrarDashboard();
      cargarDashboard();
    } else if (respuesta.status === 401) {
      credenciales = null;
      mostrarError('login-error', 'Usuario o contraseña incorrectos.');
    } else {
      credenciales = null;
      mostrarError('login-error', 'Error al iniciar sesión. Intentá de nuevo.');
    }
  } catch (error) {
    credenciales = null;
    mostrarError('login-error', 'No se pudo conectar al servidor. Verificá que el backend esté corriendo.');
  }
}

async function registrar() {
  const username = document.getElementById('reg-username').value.trim();
  const password = document.getElementById('reg-password').value;
  const nombre = document.getElementById('reg-nombre').value.trim();

  if (!username || !password || !nombre) {
    mostrarError('registro-error', 'Completá todos los campos.');
    return;
  }

  try {
    const respuesta = await post('/usuarios', { username, password, nombre }, false);

    if (respuesta.ok) {
      // Auto-login después del registro exitoso
      credenciales = { username, password };
      mostrarDashboard();
      cargarDashboard();
    } else if (respuesta.status === 400) {
      const datos = await respuesta.json();
      mostrarError('registro-error', datos.message || 'El usuario ya existe o los datos son inválidos.');
    } else {
      mostrarError('registro-error', 'Error al registrar. Intentá de nuevo.');
    }
  } catch (error) {
    mostrarError('registro-error', 'No se pudo conectar al servidor.');
  }
}

function logout() {
  credenciales = null;
  // Limpiar campos
  document.getElementById('login-username').value = '';
  document.getElementById('login-password').value = '';
  // Volver al login
  mostrarLogin();
  mostrarAuth();
}

// =====================================================================
// Dashboard
// =====================================================================

async function cargarDashboard() {
  cargarSaldo();
  cargarMovimientos();
}

async function cargarSaldo() {
  document.getElementById('saldo-monto').textContent = 'Cargando...';
  try {
    const respuesta = await get('/cuentas/me');
    if (respuesta.ok) {
      const cuenta = await respuesta.json();
      document.getElementById('saldo-monto').textContent = formatearMonto(cuenta.saldo);
      document.getElementById('saldo-fecha').textContent =
        cuenta.fechaCreacion ? 'Cuenta desde: ' + cuenta.fechaCreacion : '';
    }
  } catch (error) {
    document.getElementById('saldo-monto').textContent = 'Error';
  }
}

async function cargarMovimientos() {
  const contenedor = document.getElementById('movimientos-contenido');
  contenedor.innerHTML = '<p class="texto-vacio">Cargando...</p>';

  try {
    const respuesta = await get('/cuentas/me/movimientos');
    if (respuesta.ok) {
      const movimientos = await respuesta.json();
      renderizarMovimientos(movimientos);
    }
  } catch (error) {
    contenedor.innerHTML = '<p class="texto-vacio">Error al cargar movimientos.</p>';
  }
}

function renderizarMovimientos(movimientos) {
  const contenedor = document.getElementById('movimientos-contenido');

  if (movimientos.length === 0) {
    contenedor.innerHTML = '<p class="texto-vacio">Sin movimientos todavía.</p>';
    return;
  }

  const tabla = document.createElement('table');
  tabla.className = 'tabla-movimientos';

  // Encabezado
  tabla.innerHTML = `
    <thead>
      <tr>
        <th>Tipo</th>
        <th>Monto</th>
        <th>Saldo posterior</th>
        <th>Fecha</th>
      </tr>
    </thead>
  `;

  const cuerpo = document.createElement('tbody');

  for (const mov of movimientos) {
    const esDebito = mov.tipo === 'DEBITO';
    const fila = document.createElement('tr');
    fila.innerHTML = `
      <td>
        <span class="badge ${esDebito ? 'badge-debito' : 'badge-credito'}">
          ${mov.tipo}
        </span>
      </td>
      <td class="${esDebito ? 'monto-debito' : 'monto-credito'}">
        ${esDebito ? '−' : '+'} ${formatearMonto(mov.monto)}
      </td>
      <td>${formatearMonto(mov.saldoPosterior)}</td>
      <td>${mov.fecha || '—'}</td>
    `;
    cuerpo.appendChild(fila);
  }

  tabla.appendChild(cuerpo);
  contenedor.innerHTML = '';
  contenedor.appendChild(tabla);
}

// =====================================================================
// Transferencia
// =====================================================================

async function transferir() {
  const destino = document.getElementById('tf-destino').value.trim();
  const montoTexto = document.getElementById('tf-monto').value;
  const monto = parseInt(montoTexto, 10);
  const mensajeEl = document.getElementById('tf-mensaje');

  mensajeEl.hidden = true;
  mensajeEl.className = 'tf-mensaje';

  if (!destino || !montoTexto) {
    mostrarMensajeTransferencia('Completá el usuario destino y el monto.', 'error');
    return;
  }

  if (isNaN(monto) || monto <= 0) {
    mostrarMensajeTransferencia('El monto debe ser un número mayor a 0.', 'error');
    return;
  }

  try {
    const respuesta = await post('/transferencias', { destinoUsername: destino, monto });

    if (respuesta.status === 201) {
      mostrarMensajeTransferencia(
        `Transferencia de ${formatearMonto(monto)} enviada a ${destino}.`,
        'exito'
      );
      // Limpiar formulario y refrescar datos
      document.getElementById('tf-destino').value = '';
      document.getElementById('tf-monto').value = '';
      cargarSaldo();
      cargarMovimientos();
    } else {
      const datos = await respuesta.json();
      const mensaje = datos.message || 'No se pudo realizar la transferencia.';
      mostrarMensajeTransferencia(mensaje, 'error');
    }
  } catch (error) {
    mostrarMensajeTransferencia('Error de conexión con el servidor.', 'error');
  }
}

// =====================================================================
// Utilidades UI
// =====================================================================

function mostrarError(idElemento, mensaje) {
  const el = document.getElementById(idElemento);
  el.textContent = mensaje;
  el.hidden = false;
}

function mostrarMensajeTransferencia(texto, tipo) {
  const el = document.getElementById('tf-mensaje');
  el.textContent = texto;
  el.className = 'tf-mensaje ' + tipo;
  el.hidden = false;
}

function formatearMonto(monto) {
  // Formato guaraníes: sin decimales, separador de miles con punto
  return monto.toLocaleString('es-PY') + ' Gs';
}

// =====================================================================
// Atajos de teclado (Enter en formularios)
// =====================================================================

document.addEventListener('keydown', function(evento) {
  if (evento.key !== 'Enter') return;

  const loginVisible = !document.getElementById('form-login').hidden;
  const registroVisible = !document.getElementById('form-registro').hidden;
  const dashboardVisible = !document.getElementById('seccion-dashboard').hidden;

  if (loginVisible) {
    login();
  } else if (registroVisible) {
    registrar();
  }
});
