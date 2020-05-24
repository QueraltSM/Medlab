(function ($) {
    
    "use strict";
    
    var input = $('.validate-input .input100');

    $('.validate-form').on('submit',function(){
        var check = true;

        for(var i=0; i<input.length; i++) {
            if(validate(input[i]) === false){
                showValidate(input[i]);
                check=false;
            }
        }
        return check;
    });
    $('.validate-form .input100').each(function(){
        $(this).focus(function(){
           hideValidate(this);
        });
    });
    
    function validate (input) {
        if($(input).attr('type') === 'email_signup' || $(input).attr('name') === 'email') {
            if($(input).val().trim().match(/^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{1,5}|[0-9]{1,3})(\]?)$/) === null) {
                return false;
            }
        } else if($(input).attr('type') === 'password' || $(input).attr('name') === 'password') {
            if($(input).val().trim().match("((?=.*[a-z])(?=.*\\d)(?=.*[A-Z]){8,40})") === null) {
                return false;
            }
        }  else if($(input).attr('name') === 'license_number') {
            if($(input).val().trim().match("[0-9]{9}$") === null) {
                return false;
            }
        } else if($(input).attr('name') === 'fullname') {
            if($(input).val().trim().match("[A-Za-z]{3,40}") === null) {
                return false;
            }
        } else {
            if($(input).val().trim() === ''){
                return false;
            }
        }
    }
    
    function showValidate(input) {
        var thisAlert = $(input).parent();
        $(thisAlert).addClass('alert-validate');
    }
    
    function hideValidate(input) {
        var thisAlert = $(input).parent();
        $(thisAlert).removeClass('alert-validate');
    }
})(jQuery);