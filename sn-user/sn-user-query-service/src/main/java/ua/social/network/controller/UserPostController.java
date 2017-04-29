package ua.social.network.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.social.network.dto.PostDto;
import ua.social.network.exception.EntityNotFoundException;
import ua.social.network.query.PostMapper;

import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * @author Mykola Yashchenko
 */
@RestController
@RequestMapping("/users/posts")
public class UserPostController extends AbstractController<PostDto, PostMapper> {

    private static final String USER_NAME = "user_name";

    @Autowired
    public UserPostController(PostMapper mapper) {
        super(mapper);
    }

    @GetMapping("/{id}")
    public PostDto get(@PathVariable("id") String id, @RequestParam Map<String, Object> requestParams) {
        PostDto entity = getEntity(id, requestParams);
        if (entity == null) {
            throw new EntityNotFoundException("User with id %s doesn't exist", id);
        }
        return entity;
    }

    @GetMapping
    public List<PostDto> getList(Principal principal, @RequestParam Map<String, Object> requestParams) {
        requestParams.putIfAbsent(USER_NAME, principal.getName());
        return getEntityList(requestParams);
    }
}