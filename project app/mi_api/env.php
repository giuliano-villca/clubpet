<?php

class Cconexion {

    public static function ConexionBD() {

        $servidor = 'DESKTOP-4S5EVQS\SQLEXPRESS';
        $dbname   = 'hiper_card5';
        $username = 'sa';
        $password = '1234';

        try {
            $conn = new PDO("sqlsrv:Server=$servidor;Database=$dbname", $username, $password);
            $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
            return $conn;

        } catch (PDOException $exp) {
            http_response_code(500);
            echo json_encode([
                "success" => false,
                "error" => "Error al conectar con la base: " . $exp->getMessage()
            ]);
            exit();
        }
    }
}
?>