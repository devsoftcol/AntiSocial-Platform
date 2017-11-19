

/**
 * Navigation Bar JS
 * Handles interactions in the navigation bar.
 * Handles background theme.
 * Handles category button's search actions.
 *
 *  @author Ant Kaynak - Github/Exercon
 * */


$(document).ready(function () {

    let theme = Cookies.get('theme');
    if(theme === undefined){
        Cookies.set('theme','default');
        theme = Cookies.get('theme');
    }
    if(theme === 'default'){
        $('body').css('background-color','#f6f6f6');
    }else if(theme === 'dark'){
        $('body').css('background-color','#323232');
    }

    $('#search').focus(function () {
        $('.fade-black').fadeIn("fast");
    });

    $('#search').blur(function () {
        $('.fade-black').fadeOut("fast");
    });

    let thread = null;
    function findSearchBar(t) {
        if (!t.trim() || t.trim().length < 3) {
            return;
        }
        let bar = $('#search-result');
        bar.html("");
        searchArticles(t, bar);
        searchUsers(t, bar);

    }

    $('#search').keyup(function () {
        clearTimeout(thread);
        let $this = $(this);
        thread = setTimeout(function () {
            findSearchBar($this.val())
        }, 1000);
    });


    let thread2 = null;
    $('#search-category').keyup(function () {
        clearTimeout(thread2);
        let $this = $(this);
        thread2 = setTimeout(function () {
            findCategory($this.val())
        }, 500);
    });


});

function findCategory(t) {
    let listcategories = $('.search-category-name');
    let categories = $('.search-category-name p');
    let categorySize = Object.keys(categories).length;
    if(!t.trim()){
        for (let i = 0; i < categorySize - 2; i++) {
            listcategories[i].style.display = "inline-block";
        }
        return;
    }
    for (let i = 0; i < categorySize - 2; i++) {
        let a = categories[i].innerHTML.toLowerCase();
        if (a.includes(t.toLowerCase())) {
            listcategories[i].style.display = "inline-block";
        } else {
            listcategories[i].style.display = "none";
        }
    }
}

function searchArticles(t, bar) {
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: 'GET',
        url: "/search/article?wildcard=" + t
    }).then(function (data) {
        let resultSize = Object.keys(data).length;
        if( resultSize === 0){
            bar.append('<li class="dropdown-header">No Article Found</li>');
            return ;
        }
        bar.append('<li class="dropdown-header">Articles : '+resultSize+'</li>');
        for (let i = 0; i < Object.keys(data).length; i++) {
            let link = "/article/" + data[i].id;
            let article = data[i].articleHeader.replace("<", "&lt;");
            bar.append(`<li><a href=${link}><i style="padding-right: 5px" class="fa fa-newspaper-o" aria-hidden="true"></i>${article}</a></li>`);
        }
    });
}

function searchUsers(t, bar) {
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: 'GET',
        url: "/search/user?wildcard=" + t
    }).then(function (data) {
        let resultSize = Object.keys(data).length;
        if( resultSize === 0){
            bar.append('<li class="dropdown-header">No User Found</li>');
            return ;
        }
        bar.append('<li class="dropdown-header">Users : '+resultSize+'</li>');
        for (let i = 0; i < resultSize; i++) {
            let link = "/user/" + data[i].ssoId;
            let name = data[i].name.replace("<", "&lt;");
            bar.append(`<li><a href=${link}><i style="padding-right: 5px" class="fa fa-user" aria-hidden="true"></i>${name}</a></li>`);
        }
    });
}




