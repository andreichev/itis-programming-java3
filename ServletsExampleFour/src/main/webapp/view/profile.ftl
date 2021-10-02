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
</head>
<body>
<h1>Profile</h1>

<#if user.avatarId??>
    <img class="avatar" alt="IMAGE" src="/files/${user.avatarId}" />
<#else>
    <img class="avatar" alt="IMAGE" src="/no-avatar.png" />
</#if>

<div class="name">${user.userName}</div>

</body>
</html>