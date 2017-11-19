

/**
 * Single Article JS
 * Handles interactions in the single article page
 * ( One particular article with the articleID URL )
 * Handles commenting and comment deleting.
 *
 *  @author Ant Kaynak - Github/Exercon
 * */

let appendData;

$(document).ready(function () {
    appendData = new AppendData();
    getArticle();
});



function getArticle() {
    $('#loader').fadeIn("slow");
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: 'GET',
        url: scrollUrl
    }).then(function (data) {
        $('#loader').fadeOut("slow");
        if (Object.keys(data).length === 0) {
            appendData.appendNoDataLeft();
            return;
        }
        appendData.appendArticle(data);
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



