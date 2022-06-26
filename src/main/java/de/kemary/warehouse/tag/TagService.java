package de.kemary.warehouse.tag;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TagService {
    public String getTagsAsString(List<Tag> tagList){
        int i = 0;
        String returnString = null;
        if(tagList == null){
            return "";
        }
        while(tagList.size() != i){
            if(returnString == null) {
                returnString = tagList.get(i).getId().toString();
            }else {
                returnString = returnString + "," + tagList.get(i).getId().toString();
            }
            i++;
        }
        return returnString;
    }
    public List<Tag> getTagsAsList(String tagString, TagRepository tagRepository){
        List<Tag> tagList = new ArrayList<>();
        if(tagString.length() == 0){
            var tag = tagRepository.findById(Long.getLong(tagString));
            if(tag.isEmpty())
                return tagList;
            tagList.add(tag.get());
        }else {
            int i = 0;
            while (tagString.length()-1 > i) {
                tagRepository.findById(Long.valueOf(Arrays.stream(tagString.split(",")).toList().get(i))).ifPresent(tagList::add);
                i++;
            }
        }
        return tagList;
    }
}
