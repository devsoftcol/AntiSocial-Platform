

/**
 * Login JS
 * Handles register validations on the client side.
 *
 *  @author Ant Kaynak - Github/Exercon
 * */


$(document).ready(function () {

    $('#errorSsoId').css('display', 'none');
    $('#errorEmail').css('display', 'none');
    $('#errorPassword').css('display', 'none');
    $('#errorPasswordRepeat').css('display', 'none');
    $('#validSsoId').css('display', 'none');
    $('#validEmail').css('display', 'none');
    $('#validPassword').css('display', 'none');
    $('#validPasswordRepeat').css('display', 'none');

    let thread = null;
    $('#ssoId').keyup(function () {
        clearTimeout(thread);
        let $this = $(this);
        thread = setTimeout(function () {
            findSsoId($this.val())
        }, 500);
    });

    let thread2 = null;
    $('#email').keyup(function () {
        clearTimeout(thread2);
        let $this = $(this);
        thread2 = setTimeout(function () {
            findEmail($this.val())
        }, 500);
    });

    let thread3 = null;
    $('#password').keyup(function () {
        clearTimeout(thread3);
        let $this = $(this);
        thread3 = setTimeout(function () {
            validatePassword($this.val())
        }, 500);
    });

    let thread4 = null;
    $('#passwordRepeat').keyup(function () {
        clearTimeout(thread4);
        let $this = $(this);
        thread4 = setTimeout(function () {
            validatePasswordRepeat($this.val())
        }, 500);
    });

});


function findSsoId(t) {
    if (!t.trim()) {
        return;
    }

    let iconError = $('#errorSsoId');
    let iconValid = $('#validSsoId');

    if(t.trim().length < 6 || t.trim().length > 14){
        setError(iconValid, iconError);
        return;
    }

    let patt = new RegExp("^[a-zA-Z]+$");
    let res = patt.test(t);
    if(!res){
        setError(iconValid, iconError);
        return;
    }

    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: 'GET',
        url: "/user/validSsoId?ssoId=" + t
    }).then(function (data) {
        if(data){
            setError(iconValid, iconError);
            return;
        }
        setValid(iconValid, iconError);
    });

}

function findEmail(t) {
    if (!t.trim()) {
        return;
    }

    let iconError = $('#errorEmail');
    let iconValid = $('#validEmail');

    let patt = new RegExp("\\S+@\\S+");
    let res = patt.test(t);

    if(!res){
        iconError.css('display', 'block');
        iconValid.css('display', 'none');
        return;
    }

    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: 'GET',
        url: "/user/validEmail?email=" + t
    }).then(function (data) {
        if(data){
            setError(iconValid, iconError);
            return;
        }
        setValid(iconValid, iconError);
    });
}

function validatePassword(t) {
    if (!t.trim()) {
        return;
    }
    validatePasswordRepeat($('#passwordRepeat').val());
    let iconError = $('#errorPassword');
    let iconValid = $('#validPassword');
    let patt = new RegExp("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@*#!.$%^&+=])(?=\\S+$).{8,}$");
    let res = patt.test(t);
    if(res){
        setValid(iconValid, iconError);
        return;
    }
    setError(iconValid, iconError);
}

function validatePasswordRepeat(t) {
    if (!t.trim()) {
        return;
    }
    let iconError = $('#errorPasswordRepeat');
    let iconValid = $('#validPasswordRepeat');
    if(t === $('#password').val()){
        setValid(iconValid, iconError);
        return;
    }
    setError(iconValid, iconError);

}

function setValid(valid, error) {
    error.css('display', 'none');
    valid.css('display', 'block');
}

function setError(valid, error) {
    error.css('display', 'block');
    valid.css('display', 'none');
}