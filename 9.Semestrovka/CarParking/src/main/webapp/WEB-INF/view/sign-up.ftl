<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Sign Up</title>
    <link rel="stylesheet" href="resources/css/style.css">
    <link rel="stylesheet" href="resources/css/sign-in.css">
</head>
<body>

<form class="form-center-content" method="post">
    <div class="form-signin-heading">Sign Up</div>
    <label>First name
        <input class="form-control" name="firstName" type="text">
    </label>
    <label>Last name
        <input class="form-control" name="lastName" type="text">
    </label>
    <label>Email
        <input class="form-control" name="email" type="email">
    </label>
    <label>Password
        <input class="form-control" name="password" type="password">
    </label>
    <label>Birthdate
        <input class="form-control" name="birthdate" type="date">
    </label>
    <#if errorMessage??>
        <div class="error_message">${errorMessage}</div>
    </#if>
    <input class="login-button" type="submit">
</form>

</body>
</html>