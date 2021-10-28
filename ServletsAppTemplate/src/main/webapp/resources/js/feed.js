/**
 * Created by apple on 01.11.16.
 */
function updateAllFeed() {
    $.ajax({
        url: "?action=get_news",
        type: "GET"
    }).done(function(data) {
        var newsContainer = $('#news_container');
        newsContainer.html(data);
        //newsContainer.hide();
        //newsContainer.show(350);
    })
}

$(document).ready(function () {
    updateAllFeed();

    $("#new_feed").each(function () {
        var form = $(this);

        form.submit(function() {
            var text = $('#news_item_text').val();
            if (text == '') return false;

            $.ajax({
                url: "/feed",
                type: "POST",
                data: {
                    "text" : text
                }
            }).done(function(data) {
                if(data == "success"){
                    form[0].reset();

                    setTimeout(function () {
                        updateAllFeed();
                    }, 100);

                } else {
                    alert(data)
                }
            });

            return false;
        })
    })
});

function deleteFeedItem(id) {
    $.ajax({
        url: "/feed?action=delete&id=" + id,
        type: "GET"
    }).done(function(data) {
        updateAllFeed()
    })
}