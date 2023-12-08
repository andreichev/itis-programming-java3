<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Sign Up</title>
    <link rel="stylesheet" href="/resources/css/style.css">
</head>
<body>
<h1>Sign Up</h1>

<form method="post">
    <label>First name
        <input name="firstName" type="text">
    </label>
    <label>Last name
        <input name="lastName" type="text">
    </label>
    <label>Email
        <input name="email" type="email">
    </label>
    <label>Password
        <input name="password" type="password">
    </label>
    <label>Age
        <input name="age" type="number" min="3" max="100">
    </label>
    <input type="submit">
</form>

</body>
</html>