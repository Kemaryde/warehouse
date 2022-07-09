package de.kemary.warehouse.tag;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequestMapping("/tag")
@RestController
@RequiredArgsConstructor
public class TagController {
    final TagRepository tagRepository;


    @PostMapping
    public String createTag(@RequestBody TagDTO tagDTO, HttpServletResponse response){
        var tag = tagRepository.findAllByName(tagDTO.getName());
        if(tag.isPresent()) {
            response.setStatus(HttpStatus.CONFLICT.value());
            return tag.get().getId().toString();
        }else{
            var newTag = new Tag(tagDTO.getName());
            response.setStatus(HttpStatus.CREATED.value());
            tagRepository.save(newTag);
            return newTag.getId().toString();
        }
    }

    @GetMapping
    public List<Tag> getTags(){
        return tagRepository.findAll();
    }

}
