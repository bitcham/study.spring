package hello.upload.domain;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ItemRepository {

    private final Map<Long, Item> items = new ConcurrentHashMap<Long, Item>();
    private long sequence = 0L;

    public Item save(Item item) {
        item.setId(++sequence);
        items.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id) {
        return items.get(id);
    }
}
