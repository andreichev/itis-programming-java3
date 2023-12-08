<html>
<head>
    <title>Sign In</title>
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

<form id="sign-in-form" method="post" action="/sign-in" enctype="application/x-www-form-urlencoded">
    <label>Имя
        <div>
            <input type="text" name="name" placeholder="Имя" required/>
        </div>
    </label>

    <label>
        Пароль
        <div>
            <input type="password" name="password" placeholder="Пароль" required/>
        </div>
    </label>
    <div>
        <input value="SEND" type="submit">
    </div>
</form>

<a href="/registration">REGISTRATION</a>

<script>

    $(document).ready(() => {
        let form = $("#sign-in-form")
        let firstNameInput = form.find("[name='name']")
        let passwordInput = form.find("[name='password']")

        form.submit(() => {
            let signInForm = {
                name: firstNameInput.val(),
                password: passwordInput.val()
            }

            $.ajax({
                url: "/sign-in",
                method: "POST",
                data: JSON.stringify(signInForm),
                contentType: "application/json"
            }).done((data) => {
                document.location = "/profile"
                // alert("Created user with id " + data.id);
            }).fail((data, textStatus, xhr) => {
                console.log(data);
                alert(data.responseJSON.message);
            });

            return false
        })
    })
</script>

</body>
</html>
