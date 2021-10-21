<#-- @ftlvariable name="user" type="ru.itis.servletsapp.dto.UserDto" -->
<#-- @ftlvariable name="posts" type="java.util.List<ru.itis.servletsapp.dto.PostDto>" -->

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Profile</title>

    <style>
        .avatar {
            width: 100px;
            height: 100px;
            display: inline;
            border-radius: 50px;
            justify-content: center;
        }

        .name {
            display: inline;
        }
    </style>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

    <script>
        $(document).ready(function () {
            let form = $("#add-post-form")
            let postsList = $("#post-list")
            form.on('submit', function () {
                let content = form.find("#content").val();
                if (content === '') {
                    return false
                }
                $.ajax("/add-post", {
                    method: "POST",
                    data: "content=" + content,
                    headers: {
                        'Content-Type':'application/x-www-form-urlencoded'
                    },
                    success: function(data) {
                        form.find("#content").val("")
                        postsList.append("<div>" + content + "</div>")
                    }
                })
                return false
            })
        })
    </script>
</head>
<body>
<h1>Profile</h1>

<#if user.avatarId??>
    <img class="avatar" alt="IMAGE" src="/files/${user.avatarId}" />
<#else>
    <img class="avatar" alt="IMAGE" src="/no-avatar.png" />
</#if>

<div class="name">${user.firstName}</div>

<form id="add-post-form" action="/add-post" method="post">
    <label>
        Enter post text:
        <input id="content" type="text" name="content">
    </label>
    <input type="submit">
</form>

<div id="post-list">
<#list posts as post>
    <div>${post.content}</div>
</#list>
</div>

</body>
</html>