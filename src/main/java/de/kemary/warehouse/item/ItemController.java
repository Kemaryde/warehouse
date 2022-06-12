package de.kemary.warehouse.item;

import de.kemary.warehouse.Type;
import de.kemary.warehouse.tag.TagRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.awt.print.Book;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/item")
@AllArgsConstructor
public class ItemController {
    final ItemRepository itemRepository;
    final TagRepository tagRepository;

    @GetMapping
    public List<ItemDTO> getItems(@RequestParam(required = false)String tagsAsList){
        List<ItemDTO> returnItems = new ArrayList<>();
        if(tagsAsList != null){
            int i = 0;
            var allItems = itemRepository.findAll();
            while (allItems.size() != i){
                var item = allItems.get(i);
                ItemDTO itemDTO = new ItemDTO();
                var returnItem = new ItemDTO();
                returnItem.setBarcode(item.getBarcode());
                returnItem.setFracturedBy(item.getFracturedBy());
                returnItem.setId(item.getId());
                returnItem.setName(item.getName());
                returnItem.setImageURL(item.getImageURL());
                returnItem.setDescription(item.getDescription());
                returnItem.setTagList(itemDTO.getTagList(allItems.get(i).getTagString(), tagRepository));
                returnItems.add(returnItem);
                i++;
            }
        }else {
            int i = 0;
            var allItems = itemRepository.findAll();
            while (allItems.size() >= i) {
                var item = allItems.get(0);
                var returnItem = new ItemDTO();
                returnItem.setBarcode(item.getBarcode());
                returnItem.setFracturedBy(item.getFracturedBy());
                returnItem.setId(item.getId());
                returnItem.setName(item.getName());
                returnItem.setImageURL(item.getImageURL());
                returnItem.setDescription(item.getDescription());
                returnItem.setTags(item.getTagString());
                returnItems.add(returnItem);
                i++;
            }
        }
        return returnItems;
    }

    @PostMapping
    public Object postItem(@RequestBody ItemDTO item) throws IOException, JSONException {
        if (itemRepository.findItemByNameAndFracturedBy(item.getName(), item.getFracturedBy()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        var url = String.format("https://de.openfoodfacts.org/api/v2/search?code=%s&fields=image_front_url,url,code,product_name_de,brands,product_name", item.getBarcode());
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        if(item.getBarcode() != null){
            Request request = new Request.Builder()
                    .url(url)
                    .method("GET", null)
                    .build();
            Response response = client.newCall(request).execute();
            assert response.body() != null;
            var json = new JSONObject(response.body().string());
            json = json.getJSONArray("products").getJSONObject(0);
            var newItem = new Item(json.getString("product_name_de") != null ? json.getString("product_name_de") : json.getString("product_name"), Type.NULL, json.getString("brands"), item.getBarcode(), json.getString("image_front_url"), item.getTagString(item.getTagList()), item.getDescription());
            itemRepository.save(newItem);
            return json.toString();
        }else {
            Item newItem;
            if(item.getTags() != null){
                newItem = new Item(item.getName(), item.getType(), item.getFracturedBy(), item.getBarcode(), item.getImageURL(), item.getTags(), item.getDescription());
            }else {
                newItem = new Item(item.getName(), item.getType(), item.getFracturedBy(), item.getBarcode(), item.getImageURL(), item.getTagString(item.getTagList()), item.getDescription());
            }
            itemRepository.save(newItem);
        }
        return null;
    }
}
