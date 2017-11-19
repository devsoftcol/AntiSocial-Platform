
/**
 * Infinite Scroll JS
 * Handles loading data when reached to the end of the page.
 * Handles commenting and comment deleting.
 *
 *  @author Ant Kaynak - Github/Exercon
 * */



let start = 0;
let thread = false;
let appendData;

$(document).ready(function () {
    appendData = new AppendData();
    getArticles();
    window.onscroll = function (e) {
        //In Mac pageYOffset is float so we need to adjust it with Math.ceil.
        if (((window.innerHeight + Math.ceil(window.pageYOffset + 1)) >= document.body.offsetHeight) && !thread) {
            start = start + 5;
            getArticles();
        }
    };
});



function getArticles() {
    thread = true;
    $('#loader').fadeIn("slow");
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: 'GET',
        url: scrollUrl + start
    }).then(function (data) {
        $('#loader').fadeOut("slow");
        if (Object.keys(data).length === 0) {
            appendData.appendNoDataLeft();
            //Thread should stay true so we would not invoke this function again.
            return;
        }
        appendData.appendArticle(data);
        setTimeout(function () {
            thread = false;
        }, 1000);
    });
}

function onComment(articleId){
    let commentBody = $('#commentBody_'+articleId);
    if(commentBody.val().trim() === null || commentBody.val().trim() === ""){return;}
    $('#loader').fadeIn("slow");
    let headers = {};
    headers["X-CSRF-Token"] = $("input[name='_csrf']").val();
    let commentData = {
        "commentBody":commentBody.val(),
        "articleID": articleId
    };

    $.ajax({
        type: "POST",
        headers: headers,
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        url: "/comment/add",
        data: JSON.stringify(commentData),
        success: function (data) {
            $('#loader').fadeOut("slow");
            if(data === null){alert("An error occurred while sending comment.");}
            if(data.errorAccess === true){
                alert("You must login to comment!");
                return;
            }
            commentBody.val("");
            appendData.appendCommentNew(data);
            let a = $('#comment_count_'+articleId);
            a[0].innerText = parseInt(a[0].innerText) + 1;
        },
        error: function () {
            $('#loader').fadeOut("slow");
            alert("An error occurred while sending comment.");
        }
    });
}


function onCommentDelete(commentId, articleId){
    $('#loader').fadeIn("slow");
    let headers = {};
    headers["X-CSRF-Token"] = $("input[name='_csrf']").val();

    $.ajax({
        type: "DELETE",
        headers: headers,
        url: "/comment/delete/"+commentId,
        statusCode: {
            200: function( data ) {
                $('#comment_'+commentId).remove();
                let a = $('#comment_count_'+articleId);
                a[0].innerText = parseInt(a[0].innerText) - 1;

            },
            400: function( data ) {
                alert("bad request");
            },
            403: function( data ) {
                alert("forbidden");
            }
        },
        success: function (data) {
            $('#loader').fadeOut("slow");
        },
        error: function () {
            $('#loader').fadeOut("slow");
            alert("An error occurred while deleting comment.");
        }
    });

}

































































