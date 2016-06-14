<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Xero Callback</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
</head>
<body>
<div class="container">
	<h1>Xero API - Java</h1>
	
	<form action="./RequestResource" method="post">
		<select name="method">
			<option value="GET" selected>GET</option>
			<option value="POST">POST</option>
		</select>
		<br>
		<select name="resource">
			<option value="Organisation" selected>Organisation</option>
			<option value="Contact" >Contact</option>
		</select>
		<br>
		<textarea rows="10" cols="50" name="body">
	<Contacts>
		<Contact>
			<Name>Sid Maestre</Name>
		</Contact>
	</Contacts>
		</textarea>
		<br>
		<input type="submit" value="submit">
	</form>
</div>
</body>
</html>