package template.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import template.api.model.ItemDTO;
import template.service.ItemsService;

import java.util.List;
import java.util.Objects;

@RestController
public class ItemsController implements ItemsApi {

    // VIOLATION: Field injection instead of constructor injection
    // @Autowired
    // private ItemsService service;

    // FIX: Constructor injection
    private final ItemsService service;

    public ItemsController(ItemsService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<ItemDTO> getItem(Long id) {
        return service.getItem(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<ItemDTO>> getItems() {
        return ResponseEntity.ok(service.getItems().stream().toList());
    }

    @Override
    public ResponseEntity<Void> postItem(ItemDTO itemDTO) {
        if (itemDTO.getId() != null) {
            return ResponseEntity.badRequest().build();
        }

        service.postItem(itemDTO);
        return ResponseEntity.ok().build();
    }


    @Override
    public ResponseEntity<Void> putItem(Long itemId, ItemDTO itemDTO) {
        if (!hasValidId(itemId, itemDTO)) {
            return ResponseEntity.badRequest().build();
        }

        service.putItem(itemId, itemDTO);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteItem(Long id) {
        if (service.getItem(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        service.deleteItem(id);
        return ResponseEntity.ok().build();
    }

    private boolean hasValidId(Long itemId, ItemDTO itemDTO) {
        return itemDTO.getId() == null || Objects.equals(itemId, itemDTO.getId());
    }

    // VIOLATION: REST endpoint returns void instead of ResponseEntity
    // @PostMapping("/items/notify")
    // public void notifyItemCreated(@RequestBody ItemDTO itemDTO) {
    //     System.out.println("Item notification: " + itemDTO.getName());
    // }

    // FIX: Return ResponseEntity instead of void
    @PostMapping("/items/notify")
    public ResponseEntity<Void> notifyItemCreated(@RequestBody ItemDTO itemDTO) {
        System.out.println("Item notification: " + itemDTO.getName());
        return ResponseEntity.ok().build();
    }

}
