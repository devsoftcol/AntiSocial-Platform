package com.antisocial.controller;


import com.antisocial.dto.ArticleDTO;
import com.antisocial.dto.CommentDTO;
import com.antisocial.model.Article;
import com.antisocial.model.Category;
import com.antisocial.model.Comment;
import com.antisocial.model.User;
import com.antisocial.service.ArticleService;
import com.antisocial.service.CategoryService;
import com.antisocial.service.CommentService;
import com.antisocial.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.RequestContextUtils;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


@RestController
public class ArticleRestController {

    /**
     * Rest EndPoint for Article Controller.
     * Handles search bar article name search
     * Retrieving article data for main page, category page and single article page
     * Adding and deleting comment
     * Formatting retrieved date and timezones
     * And converting data to DTO.
     *
     * @author Ant Kaynak - Github/Exercon
     */


    private final ArticleService articleService;
    private final UserService userService;
    private final CommentService commentService;
    private final CategoryService categoryService;

    @Autowired
    public ArticleRestController(ArticleService articleService, UserService userService, CommentService commentService, CategoryService categoryService){
        this.articleService = articleService;
        this.userService = userService;
        this.commentService = commentService;
        this.categoryService = categoryService;
    }


    @RequestMapping(value = "/search/article", method = RequestMethod.GET)
    public List<ArticleDTO> getWildcardArticles(@RequestParam("wildcard") String wildcard){
        if(wildcard.length() > 240 || wildcard.length() < 3){
            return null;
        }
        List<Article> list = articleService.getArticlesByHeader(wildcard);
        if(list.isEmpty()){return null;}
        List<ArticleDTO> dtoList = new ArrayList<>();
        for(Article i : list){
            ArticleDTO articleDTO = new ArticleDTO();
            articleDTO.setArticleHeader(i.getArticleHeader());
            articleDTO.setID(i.getID());
            dtoList.add(articleDTO);
        }

        return dtoList;
    }

    @RequestMapping(value= "/get/home/{start}", method = RequestMethod.GET)
    public List<ArticleDTO> getArticles(@PathVariable("start") int start, HttpServletRequest request){
        List<Article> list = articleService.getArticles(5,start);
        if(list == null){
            return null;
        }
        ZoneId zoneId = resolveZoneId(request);
        return articleToDTO(list, TimeZone.getTimeZone(zoneId));
    }

    @RequestMapping(value = "/get/article/{articleID}", method = RequestMethod.GET)
    public List<ArticleDTO> getArticle(@PathVariable("articleID") Long articleID, HttpServletRequest request){
        List<Article> list = new ArrayList<>();
        Article article = articleService.getArticleById(articleID);
        if(article == null){
            return null;
        }
        list.add(article);

        ZoneId zoneId = resolveZoneId(request);
        return articleToDTO(list, TimeZone.getTimeZone(zoneId));
    }

    @RequestMapping(value = "/get/category/{category}/{start}", method = RequestMethod.GET)
    public List<ArticleDTO> getArticles(@PathVariable("category") String categoryName, @PathVariable("start") int start, HttpServletRequest request){
        Category category = categoryService.getCategoryByName(categoryName);
        if(category == null){return null;}
        List<Article> list = articleService.getArticlesByCategory(category, 5, start);
        if(list == null){ return null; }

        ZoneId zoneId = resolveZoneId(request);
        return articleToDTO(list, TimeZone.getTimeZone(zoneId));

    }

    @RequestMapping(value = "/comment/add", method = RequestMethod.POST)
    public CommentDTO createComment(@Valid @RequestBody CommentDTO commentDTO, BindingResult br){
        if(br.hasErrors()){return null;}
        if(userService.getPrincipal() == null){
            CommentDTO errorDTO = new CommentDTO();
            errorDTO.setErrorAccess(true);
            return errorDTO;
        }
        User user = userService.getUserBySsoId(userService.getPrincipal(), true);
        Comment comment = new Comment(user.getName(), user.getSsoId(), commentDTO.getCommentBody());
        articleService.addComment(comment, commentDTO.getArticleID());
        Long commentID = commentService.saveComment(comment);
        return new CommentDTO(user.getName(), user.getSsoId(), commentID, commentDTO.getCommentBody(), "just now", commentDTO.getArticleID(), false);
    }

    @RequestMapping(value = "/comment/delete/{commentID}", method = RequestMethod.DELETE)
    public ResponseEntity deleteComment(@PathVariable("commentID") Long commentID){
        Comment comment = commentService.getCommentById(commentID);
        if(comment == null){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        if(!comment.getCommentOwnerSsoId().equals(userService.getPrincipal())){
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        commentService.deleteComment(commentID);
        return new ResponseEntity(HttpStatus.OK);
    }




    private List<ArticleDTO> articleToDTO(List<Article> list, TimeZone timeZone){
        List<ArticleDTO> articleDTOList = new ArrayList<>();
        for(Article article : list){
            List<CommentDTO> commentDTOList = new ArrayList<>();
            for(Comment comment : article.getCommentList()){
                commentDTOList.add(new CommentDTO(comment.getCommentOwner(),
                        comment.getCommentOwnerSsoId(),
                        comment.getID(),
                        comment.getCommentBody(),
                        getFormattedDate(comment.getCommentDate(), timeZone),
                        comment.getArticleID().getID(),
                        false
                ));
            }
            articleDTOList.add(new ArticleDTO(
                    article.getID(),
                    article.getUserID().getName(),
                    article.getCategory().getCategoryName(),
                    article.getArticleHeader(),
                    article.getArticleBody(),
                    getFormattedDate(article.getArticleDate(), timeZone),
                    article.getUserID().getSsoId(), commentDTOList,
                    userService.getPrincipal()
            ));
        }

        return articleDTOList;
    }
    
    
    // You can create a Date Utils class for the methods below
    private ZoneId resolveZoneId(HttpServletRequest request) {
        TimeZone timeZone = RequestContextUtils.getTimeZone(request);
        return (timeZone != null ? timeZone.toZoneId() : ZoneId.systemDefault());
    }

    private String getFormattedDate(Date date, TimeZone timeZone){
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd-MM-yyyy");
        dateFormat.setTimeZone(timeZone);
        return dateFormat.format(date);
    }


}
