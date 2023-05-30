<?php
echo '<?xml version="1.0" encoding="1.0" ?>';
$user = $_GET['user'];
$password = $_GET['pw'];
echo <<<XML
<tag user="$user" pw="$password" />
XML;
?>