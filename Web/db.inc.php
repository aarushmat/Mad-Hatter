<?php

function pdo_connect() {
    try {
        // Production server
        $dbhost="mysql:host=mysql-user.cse.msu.edu;dbname=mathura5";
        $user = "mathura5";
        $password = "A61002886";
        return new PDO($dbhost, $user, $password);
    } catch(PDOException $e) {
        echo '<hatter status="no" msg="Unable to select database" />';
        exit;
    }
}
