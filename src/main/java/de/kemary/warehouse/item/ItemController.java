package de.kemary.warehouse.item;

import de.kemary.warehouse.Type;
import lombok.AllArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/item")
@AllArgsConstructor
public class ItemController {
    final ItemRepository itemRepository;

    @GetMapping
    public List<Item> getItems(){
        return itemRepository.findAll();
    }

    @PostMapping
    public Object postItem(@RequestBody Item item) throws IOException, JSONException {
        if (itemRepository.findItemByNameAndFracturedBy(item.getName(), item.getFracturedBy()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);//4018077672972
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
            var newItem = new Item(json.getString("product_name_de") != null ? json.getString("product_name_de") : json.getString("product_name"), Type.NULL, json.getString("brands"), item.getBarcode(), json.getString("image_front_url"));
            itemRepository.save(newItem);
            return json.toString();
        }else {
            var newItem = new Item(item.getName(), item.getType(), item.getFracturedBy(), item.getBarcode(), item.getImageURL());
            itemRepository.save(newItem);
        }
        return null;
    }
}
