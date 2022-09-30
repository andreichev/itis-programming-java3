<html>
<head>
    <title>Title</title>
</head>
<body>

<table>
    <th>ID</th>
    <th>FIRST NAME</th>
    <th>LAST NAME</th>
    <th>COURSE NAME</th>
    <th>AGE</th>

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
</table>
<a href="/user/add">ADD USER</a>

</body>
</html>
