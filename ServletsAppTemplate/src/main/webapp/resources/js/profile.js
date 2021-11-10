function deletePost(tagId, postId) {

}

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
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            success: function (data) {
                let postDto = JSON.parse(data)
                form.find("#content").val("")
                let postTag = $("<div></div>")
                let options = { year: "numeric", month: "long", day: "numeric", hour: "numeric", minute: "numeric" }
                postTag.append("<div class=\"light_blue text\">" + new Date(postDto.createdAt).toLocaleString('ru-RU', options) + "</div>")
                postTag.append("<div class=\"text\">Автор: " + postDto.author.firstName + "</div>")
                postTag.append("<div class=\"text\">" + postDto.content + "</div>")
                postTag.append("<div class=\"divider\"></div>")
                postTag.hide()
                postsList.prepend(postTag)
                postTag.show(300)

                let postsCountSpan = $("#postsCount")
                postsCountSpan.html(parseInt(postsCountSpan.html()) + 1)
            }
        })
        return false
    })
})