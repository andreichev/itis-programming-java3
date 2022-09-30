<html>
<head>
    <title>Add User</title>
</head>
<body>

<h1>Add user page</h1>

<form method="post" action="/user/add" enctype="application/x-www-form-urlencoded">
    <label>Имя
        <div>
        <input type="text" name="firstName" placeholder="Имя"/>
        </div>
    </label>

    <label>Фамилия
        <div>
        <input type="text" name="lastName" placeholder="Фамилия"/>
        </div>
    </label>

    <label>Курс по выбору
        <div>
        <input type="text" name="courseName" placeholder="Курс по выбору"/>
        </div>
    </label>

    <label>
        Возраст
        <div>
        <input type="number" name="age" placeholder="Возраст"/>
        </div>
    </label>
    <div>
    <input value="SEND" type="submit">
    </div>
</form>

</body>
</html>
