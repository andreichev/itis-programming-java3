<html>
<head>
    <title>Title</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>

    <style>
        body {
            font-size: 22px;
        }

        table {
            border: 1px solid black;
        }
    </style>
</head>
<body>

<form id="add-user-form" method="post" action="/registration" enctype="application/x-www-form-urlencoded">
    <label>Имя
        <div>
            <input type="text" name="firstName" placeholder="Имя" required/>
        </div>
    </label>

    <label>
        Пароль
        <div>
            <input type="password" name="password" placeholder="Пароль" required/>
        </div>
    </label>

    <label>Фамилия
        <div>
            <input type="text" name="lastName" placeholder="Фамилия" required/>
        </div>
    </label>

    <label>Курс по выбору
        <div>
            <input type="text" name="courseName" placeholder="Курс по выбору" required/>
        </div>
    </label>

    <label>
        Возраст
        <div>
            <input type="number" name="age" placeholder="Возраст" required/>
        </div>
    </label>
    <div>
        <input value="SEND" type="submit">
    </div>
</form>

<script>

    $(document).ready(() => {
        let form = $("#add-user-form")
        let firstNameInput = form.find("[name='firstName']")
        let passwordInput = form.find("[name='password']")
        let lastNameInput = form.find("[name='lastName']")
        let courseNameInput = form.find("[name='courseName']")
        let ageInput = form.find("[name='age']")

        form.submit(() => {
            let registrationForm = {
                firstName: firstNameInput.val(),
                password: passwordInput.val(),
                lastName: lastNameInput.val(),
                courseName: courseNameInput.val(),
                age: ageInput.val(),
            }

            $.ajax({
                url: "/registration",
                method: "POST",
                data: JSON.stringify(registrationForm),
                contentType: "application/json"
            }).done((data) => {
                document.location = "/profile"
                // alert("Created user with id " + data.id);
            }).fail(() => {
                alert("Try again");
            });

            return false
        })
    })
</script>

</body>
</html>
