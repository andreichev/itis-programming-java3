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

<table id="users-list">
    <th>ID</th>
    <th>FIRST NAME</th>
    <th>LAST NAME</th>
    <th>COURSE NAME</th>
    <th>AGE</th>
    <#if usersForFtl?has_content>
        <#list usersForFtl as user>

            <tr>
                <td>${user.id}
                </td>
                <td>${user.firstName}
                </td>
                <td>${user.lastName}
                </td>
                <td>${user.courseName}
                </td>
                <td>${user.age}
                </td>
            </tr>

        </#list>
    <#else>
        <p id="no-users-label">NO USERS</p>
    </#if>
</table>

<form id="add-user-form" method="post" action="/user/add" enctype="application/x-www-form-urlencoded">
    <label>Имя
        <div>
            <input type="text" name="firstName" placeholder="Имя" required/>
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

    function addUserToTable(user) {
        let table = $("#users-list")

        let noDataLabel = $("#no-users-label")
        noDataLabel.hide()

        let tableRow = $("<tr>")
        tableRow.append($("<td>" + user.id + "</td>"))
        tableRow.append($("<td>" + user.firstName + "</td>"))
        tableRow.append($("<td>" + user.lastName + "</td>"))
        tableRow.append($("<td>" + user.courseName + "</td>"))
        tableRow.append($("<td>" + user.age + "</td>"))

        table.append(tableRow)
    }

    $(document).ready(() => {
        let form = $("#add-user-form")
        let firstNameInput = form.find("[name='firstName']")
        let lastNameInput = form.find("[name='lastName']")
        let courseNameInput = form.find("[name='courseName']")
        let ageInput = form.find("[name='age']")

        form.submit(() => {

            let user = {
                firstName: firstNameInput.val(),
                lastName: lastNameInput.val(),
                courseName: courseNameInput.val(),
                age: ageInput.val(),
            }

            $.ajax({
                url: "/user/add",
                method: "POST",
                data: JSON.stringify(user),
                contentType: "application/json"
            }).done((data) => {
                addUserToTable(data)
            })

            return false
        })
    })
</script>

</body>
</html>
