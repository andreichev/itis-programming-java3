/**
 * Created by apple on 20.12.16.
 */

$(document).ready(function () {
    var progressBar = $('#progressbar');

    $('input[name=image]').change(function(){
        var file = this.files[0];
        var url = $(this).attr("url");

        if (file) {
            upload(file, url);
        }
    });


    function upload(file, url) {

        var xhr = new XMLHttpRequest();

        // обработчики можно объединить в один,
        // если status == 200, то это успех, иначе ошибка
        xhr.onload = xhr.onerror = function() {
            if (this.status == 200) {
                document.location.href = '/settings';
            } else {
                alert("error " + this.status);
            }
        };

        // обработчик для закачки
        xhr.upload.onprogress = function(event) {
            var percentComplete = Math.ceil(event.loaded / event.total * 100);

            progressBar.val(percentComplete).text('Загружено ' + percentComplete + '%');
        };

        xhr.open("POST", url, true);
        xhr.send(file);
    }
});