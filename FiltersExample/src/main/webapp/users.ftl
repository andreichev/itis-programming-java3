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

</body>
</html>
