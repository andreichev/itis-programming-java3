<#-- @ftlvariable name="user" type="ru.itis.sessionsexample.model.User" -->
<!DOCTYPE html>
<html lang="ru">
<head>
    <style>
        .container {
            margin: 0 50px;
        }
        .userData {
            font-size: 30px;
            margin-top: 30px;
        }
        a {
            font-size: 20px;
            margin-top: 30px;
        }
    </style>
    <title>PROFILE</title>
</head>

<body>

<div class="container">
    <h1>Profile</h1>
    <div class="userData">${user.name}</div>
    <div class="userData">${user.email}</div>
    <a href="/sign-out">SIGN OUT</a>
</div>

</body>
</html>