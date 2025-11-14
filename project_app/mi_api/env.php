<?php
$email = $_POST['email'];
$password = $_POST['password'];

$servidor = 'DESKTOP-4S5EVQS\SQLEXPRESS';
$dbname   = 'cp';
$username = 'sa';
$dbpass   = '1234';

$bdd = new PDO("sqlsrv:Server=$servidor;Database=$dbname", $username, $dbpass);

// Consulta directa 
$sql = "SELECT id_usuario, nombre_usuario, email, nombre, apellido, foto_perfil
        FROM Usuarios
        WHERE email='$email' AND password='$password'";

$resultado = $bdd->query($sql);

// Devolver respuesta JSON
echo json_encode($resultado->fetchAll(PDO::FETCH_ASSOC));

$bdd = null;

?>