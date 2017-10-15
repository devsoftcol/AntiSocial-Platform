let entityMap = {
    '&': '&amp;',
    '<': '&lt;',
    '>': '&gt;',
    '"': '&quot;',
    "'": '&#39;',
    '/': '&#x2F;',
    '`': '&#x60;',
    '=': '&#x3D;'
};

/**
 * AppendData class.
 * @constructor
 * Empty
 *
 * @author Ant Kaynak - Github/Exercon
 */

function AppendData() {}

AppendData.prototype.appendNoDataLeft = function () {
    $('#articles').append(`<div class="thumbnail no-data"><h3>
        <span class="fa-stack fa-lg">
        <i class="fa fa-newspaper-o fa-stack-1x" aria-hidden="true"></i>
        <i class="fa fa-ban fa-stack-2x text-danger" aria-hidden="true"></i>
        </span>
        </h3></div>`);
};

AppendData.prototype.escapeHtml = function (data) {
    return String(data).replace(/[&<>"'`=\/]/g, function (s) {
        return entityMap[s];
    });
};


AppendData.prototype.appendArticle = function(data){
    for (let i = 0; i < Object.keys(data).length; i++) {
        let article = data[i];

        $('#articles').append(`<div class="article">
            <div class="panel panel-default">
              <div class="panel-body">
                <div class="article">
                  <p class="article-category">${this.escapeHtml(article.category)}</p>
                  <p class="article-date">${this.escapeHtml(article.articleDate)}</p>
                    <a href="/article/${article.id}" class="article-header"><h3>${this.escapeHtml(article.articleHeader)}</h3></a>
                    <p style="white-space:pre-line" class="article-body">${this.escapeHtml(article.articleBody)}</p>
                    <figure class="snip">
                        <a href="/user/${this.escapeHtml(article.userSsoId)}">
                            <img src="/resources/pic/${this.escapeHtml(article.userSsoId)}.jpg"/>
                        </a>
                        <figcaption>
                            <a href="/user/${this.escapeHtml(article.userSsoId)}">
                            <h3 id="article-owner">${this.escapeHtml(article.articleOwner)}</h3>
                            </a>
                            <div class="icons"></div>
                        </figcaption>
                    </figure>
                </div>
                <hr class="hr">
                <h4 id="comment-count">Comments <p style="display: inline-block; padding-right: 5px;" id="comment_count_${article.id}">${Object.keys(article.commentDTOS).length}</p>
                <i class="fa fa-comments" aria-hidden="true"></i>
                </h4>
                <div id="comments_${article.id}" class="comments"></div>
                <textarea id="commentBody_${article.id}" maxlength="750" class="form-control expandable" placeholder="Comment" rows="2"  />
                 <button type="button" class="btn btn-md btn-default" onclick="onComment(${article.id})">Send</button>
              </div>
            </div>
           </div>`);

        for (let j = 0; j < Object.keys(article.commentDTOS).length; j++) {
            this.appendComment(article.commentDTOS[j], article);
        }
    }
};

AppendData.prototype.appendCommentDelete = function(data,article) {
    if(article.requestSsoId === data.commentOwnerSsoId){
        return `<p onclick="onCommentDelete(${data.id},${data.articleID})" class="comment-delete">&times;</p>`;
    }
    return '';
};

AppendData.prototype.appendComment = function (data,article) {
    let comments = data;

    $('#comments_' + comments.articleID).append(`<div id="comment_${comments.id}">
        <p class="comment-date">${this.escapeHtml(comments.commentDate)}</p>
        ${this.appendCommentDelete(data,article)}
        <div class="clear"></div>
        <a href="/user/${this.escapeHtml(comments.commentOwnerSsoId)}"><img src = "/resources/pic/${this.escapeHtml(comments.commentOwnerSsoId)}.jpg"/></a>
        <a href="/user/${this.escapeHtml(comments.commentOwnerSsoId)}"><h5>${this.escapeHtml(comments.commentOwner)}</h5></a>
        <p style="white-space:pre-line">${this.escapeHtml(comments.commentBody)}</p>
        </div>`);
};


AppendData.prototype.appendCommentNew = function (data) {
    let comments = data;

    $('#comments_' + comments.articleID).append(`<div id="comment_${comments.id}">
        <p class="comment-date">${this.escapeHtml(comments.commentDate)}</p>
        <p onclick="onCommentDelete(${comments.id},${comments.articleID})" class="comment-delete">&times;</p>
        <div class="clear"></div>
        <a href="/user/${this.escapeHtml(comments.commentOwnerSsoId)}"><img src = "/resources/pic/${this.escapeHtml(comments.commentOwnerSsoId)}.jpg"/></a>
        <a href="/user/${this.escapeHtml(comments.commentOwnerSsoId)}"><h5>${this.escapeHtml(comments.commentOwner)}</h5></a>
        <p style="white-space:pre-line">${this.escapeHtml(comments.commentBody)}</p>
        </div>`);
};