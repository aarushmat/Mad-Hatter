<?php

require_once "db.inc.php";
echo '<?xml version="1.0" encoding="UTF-8" ?>';
if(!isset($_GET['magic']) || $_GET['magic'] != "NechAtHa6RuzeR8x") {
    echo '<hatter status="no" msg="magic" />';
    exit;
}

// Get the hatting id from the request
$id = $_GET['id'];
$user = $_GET['user'];
$password = $_GET['pw'];
// Connect to the database
$pdo = pdo_connect();


// Delete the hatting from the database
getUser($pdo, $user, $password);
$idQ = $pdo->quote($id);
$query = "delete from hatting where id=$idQ";
$result = $pdo->exec($query);

// Check if the deletion was successful
if ($result) {
    echo '<hatter status="yes" msg="deleted" />';
} else {
    echo '<hatter status="no" msg="data not found" />';
}
function getUser($pdo, $user, $password) {
    // Does the user exist in the database?
    $userQ = $pdo->quote($user);
    $query = "SELECT id, password from hatteruser where user=$userQ";

    $rows = $pdo->query($query);
    if($row = $rows->fetch()) {
        // We found the record in the database
        // Check the password
        if($row['password'] != $password) {
            echo '<hatter status="no" msg="password error" />';
            exit;
        }

        return $row['id'];
    }

    echo '<hatter status="no" msg="user error" />';
    exit;
}
