package de.kemary.warehouse.item;

import de.kemary.warehouse.Type;
import de.kemary.warehouse.tag.Tag;
import de.kemary.warehouse.tag.TagRepository;
import de.kemary.warehouse.tag.TagService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class ItemDTO {
    private Long id;
    private String fracturedBy;
    private String name;
    private Type type;
    private String description;
    private Long barcode;
    private String imageURL;
    private List<Tag> tagList;
    private String tags;

    public List<Tag> getTagList(String tagString, TagRepository tagRepository){
        TagService tagService = new TagService();
        return tagService.getTagsAsList(tagString, tagRepository);
    }
    public String getTagString(List<Tag> tagList){
        TagService tagService = new TagService();
        return tagService.getTagsAsString(tagList);
    }
}
