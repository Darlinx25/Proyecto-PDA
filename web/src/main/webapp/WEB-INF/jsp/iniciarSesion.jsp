<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Iniciar sesión</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light d-flex justify-content-center align-items-center vh-100">

  <form action="/login" method="post" class="card p-5 shadow" style="max-width: 600px; border-radius: 15px;">

    <h2 class="text-center mb-4">Iniciar Sesión</h2>

    <div class="mb-3">
      <label for="nombre" class="form-label">Usuario</label>
      <input type="text" id="nombre" name="nombre" class="form-control" required>
    </div>

    <div class="mb-3">
      <label for="password" class="form-label">Contraseña</label>
      <input type="password" id="password" name="password" class="form-control" minlength="8" maxlength="24" required>
    </div>

    <button type="submit" class="btn btn-success w-100 mb-3">Iniciar Sesión</button>

    <p class="text-center text-muted mb-0">
      ¿No tenés cuenta? <a href="/crear-cuenta" class="fw-bold">Registrate</a>
    </p>

  </form>

</body>
</html>
