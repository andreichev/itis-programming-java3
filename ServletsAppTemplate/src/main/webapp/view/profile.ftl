<#-- @ftlvariable name="user" type="ru.itis.servletsapp.dto.UserDto" -->
<#-- @ftlvariable name="posts" type="java.util.List<ru.itis.servletsapp.dto.PostDto>" -->

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Профиль</title>

    <link href="/resources/css/style.css" rel="stylesheet">
    <link href="/resources/css/menu.css" rel="stylesheet">
    <link href="/resources/css/profile.css" rel="stylesheet">

    <script src="/resources/js/jquery.min.js"></script>
    <script src="/resources/js/profile.js"></script>
</head>
<body>

<div class="container">

    <#include "menu.ftl">

    <div class="center-content">
        <div class="container">
            <div class="title">Профиль</div>
            <div id="profile" class="white-container">

                <#if user.avatarId??>
                    <img class="user-avatar" alt="IMAGE" src="/files/${user.avatarId}"/>
                <#else>
                    <img class="user-avatar" alt="IMAGE" src="/no-avatar.png"/>
                </#if>

                <div class="user-info-text">
                    <div class="user-info">${user.firstName}</div>
                    <div class="user-info">${user.lastName}</div>
                    <div class="user-info">${user.email}</div>
                </div>

            </div>

            <form id="add-post-form" action="/add-post" method="post">
                <label>
                    Ваша запись:
                    <textarea id="content" class="input_green" required name="content"></textarea>
                </label>
                <input class="button1" value="Отправить" type="submit">
            </form>

            <div class="divider"></div>

            <div id="post-list">
                <#list posts as post>
                    <div id="post${post.id}">
                        <button value="X" onclick="deletePost('post${post.id}', '${post.id}')"></button>
                        <div class="light_blue text">${post.createdAt?string("dd MMMM yyyy 'г.,' HH:mm")}</div>
                        <div class="text">Автор: ${post.author.lastName ! " NO NAME"} ${post.author.firstName ! " NO NAME"}</div>
                        <div class="text">${post.content}</div>

                        <#if post_index < posts?size - 1>
                            <div class="divider"></div>
                        </#if>
                    </div>
                </#list>
                <div style="text-align: center">Всего <span id="postsCount">${posts?size}</span> записей</div>
            </div>
        </div>
    </div>

</div>
</body>
</html>