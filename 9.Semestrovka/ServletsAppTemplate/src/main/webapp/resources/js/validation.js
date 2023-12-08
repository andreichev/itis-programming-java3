/**
 * Created by apple on 19.10.16.
 */

$(document).ready(function () {
    $(".form-center-content").each(function () {

        var form = $(this);
        
        function addClassEmptyIfNoValue() {
            form.find(".form-control").each(function () {
                if($(this).val() != ''){
                    $(this).removeClass("empty-field")
                } else {
                    $(this).addClass("empty-field")
                }
            });
        }
        
        function lightEmpty(interval) {
            var emptyFields = form.find('.empty-field');

            lightField(emptyFields, interval)
        }

        function lightField(field, interval){
            field.css({
                'border-color' : 'red',
                'border-width' : '1px',
                'box-shadow' : 'inset 0px 0px 5px rgba(100, 0, 0, 70)'
            });

            setTimeout(function () {
                field.removeAttr('style')
            }, interval)
        }


        function isValidPhone(phone) {
            var pattern = new RegExp(/^[0-9]+$/i);
            return pattern.test(phone);
        }

        function checkOther(){
            var phone = $('input[name=phone]');
            if( ! isValidPhone(phone.val())){
                lightField(phone, 600);
                return false;
            }

            return true;
        }

        form.submit(function() {

            addClassEmptyIfNoValue();
            var countOfEmptyFields = form.find('.empty-field').size();

            if(countOfEmptyFields > 0){
                lightEmpty(600)
            } else {

                if(checkOther()){
                    $.ajax({
                        url: "register",
                        type: "POST",
                        data: {
                            "name" : $('input[name=name]').val(),
                            "image" : "no",
                            "phone" : $('input[name=phone]').val(),
                            "password" : $('input[name=password]').val(),
                            "password2" : $('input[name=password2]').val(),
                            "DOB" : $('#DOB').val(),
                            "city" : $('select[name=city] option:selected').val(),
                            "otryad" : $('select[name=otryad] option:selected').val(),
                            "email" : $('input[name=mail]').val(),
                            "surname" : $('input[name=surname]').val(),
                            "sex" : $('input[name=sex]:checked').val(),
                            "patronymic" : $('input[name=patronymic]').val(),
                            "dolshnost" : "S"
                        }
                    }).done(function(data) {
                        if(data == "success"){
                            form.html("Успешно!");

                            setTimeout(function () {
                                location.href = "/"
                            }, 500);

                        } else {
                            var message = JSON.parse(data);
                            alert(message[0]);
                        }
                    });
                }
            }

            return false
        })
    })
});