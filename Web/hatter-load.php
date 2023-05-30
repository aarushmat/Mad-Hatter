<?php

require_once "db.inc.php";
echo '<?xml version="1.0" encoding="UTF-8" ?>';

if(!isset($_GET['magic']) || $_GET['magic'] != "NechAtHa6RuzeR8x") {
    echo '<hatter status="no" msg="magic" />';
    exit;
}

// Process in a function
process($_GET['id'],$_GET['user'], $_GET['pw']);

/**
 * Process the query
 * @param $id the hatting id
 * @param $user the user to look for
 * @param $password the user password
 */
function process($id,$user, $password) {
    // Connect to the database
    $pdo = pdo_connect();

    getUser($pdo, $user, $password);
    $idQ = $pdo->quote($id);
    $query = "select name, uri, type, x, y, angle, scale, color, IF(feather=1, 'yes', 'no') as feather from hatting where id=$idQ";
    $rows = $pdo->query($query);

    if ($row = $rows->fetch()) {
        $name = $row['name'];
        $uri = $row['uri'];
        $type = $row['type'];
        $x = $row['x'];
        $y = $row['y'];
        $angle = $row['angle'];
        $scale = $row['scale'];
        $color = $row['color'];
        $feather = $row['feather'];

        echo "<hatter status=\"yes\">";
        echo "<hatting id=\"$id\" name=\"$name\" uri=\"$uri\" type=\"$type\" x=\"$x\" y=\"$y\" angle=\"$angle\" scale=\"$scale\" color=\"$color\" feather=\"$feather\" />\r\n";
        echo "</hatter>";
    } else {
        echo '<hatter status="no" msg="image" />';
        exit;
    }
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
