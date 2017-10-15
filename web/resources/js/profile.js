

/**
 * Profile JS
 * Handles interactions in the user profile page.
 *
 *  @author Ant Kaynak - Github/Exercon
 * */

$(document).ready(function () {
    let commentTab = $('.user-comment');
    let articleTab = $('.user-article');
    let showComment = $('#showcomment');
    let showArticle =  $('#showarticle');
    $(showComment).on('click',function () {
        commentTab.fadeIn();
        articleTab.hide();
        showComment.addClass("active-tab");
        showArticle.removeClass("active-tab");
    });

    $(showArticle).on('click',function () {
        commentTab.hide();
        articleTab.fadeIn();
        showArticle.addClass("active-tab");
        showComment.removeClass("active-tab");
    });

    $('.pp').on('change', function () {
        $('.pp-form').submit();
    });

    $('.bg').on('change', function () {
        $('.bg-form').submit();
    });

    $("#loader").fadeOut("slow");
});