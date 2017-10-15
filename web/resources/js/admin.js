

/**
 * Admin JS
 * Handles interactions in admin page.
 *
 *  @author Ant Kaynak - Github/Exercon
 * */




let ssoIdInput = $("input[id='ssoId']");
let nameInput = $("input[id='name']");
let emailInput = $("input[id='email']");
let stateInput = $("input[id='state']");
let typeInput = $("input[id='type']");

$(document).ready(function() {


    $(".slidebar li:first").attr("id","active");
    $(".result div:first").fadeIn();

    $('.slidebar a').click(function(e) {
        e.preventDefault();
        if ($(this).closest("li").attr("id") !== "active"){
            $(".result div").hide();
            $(".slidebar li").attr("id","");
            $(this).parent().attr("id","active");
            $('#' + $(this).attr('name')).fadeIn();
        }
    });

    $('.ct').on('change', function () {
        let form = $(this).attr('id');
        console.log("id "+ form);
        $('#form_'+form).submit();
    });

    let thread = null;
    function findAdminBar(t) {
        if (!t.trim() || t.trim().length < 5) {
            return;
        }
        ssoIdInput.val("");
        nameInput.val("");
        emailInput.val("");
        stateInput.val("");
        typeInput.val("");
        searchAdmin(t);

    }

    $('#search-admin').keyup(function () {
        clearTimeout(thread);
        let $this = $(this);
        thread = setTimeout(function () {
            findAdminBar($this.val())
        }, 500);
    });

});

function searchAdmin(t) {
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: 'GET',
        url: "/admin/search/user/" + t
    }).then(function (data) {
        console.log(data);
        let resultSize = Object.keys(data).length;
        if( resultSize === 0 || resultSize === null){
            //bar.append('<li class="dropdown-header">User not found</li>');
            alert("User not found");
            return ;
        }


            ssoIdInput.val(data.ssoId);
            nameInput.val(data.name);
            emailInput.val(data.email);
            stateInput.val(data.state);
            typeInput.val(data.type);

    });
}