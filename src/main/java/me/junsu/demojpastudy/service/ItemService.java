package me.junsu.demojpastudy.service;

import lombok.RequiredArgsConstructor;
import me.junsu.demojpastudy.api.ItemApiController;
import me.junsu.demojpastudy.domain.Item;
import me.junsu.demojpastudy.repository.ItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional
    public Long saveItem(Item item) {
        Item savedItem = itemRepository.save(item);
        return savedItem.getId();
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Page<Item> getAllItemsWithPage(Pageable pageable) {
        return itemRepository.findAll(pageable);
    }

    public void deleteById(Long id) {
        itemRepository.deleteById(id);
    }

    public Item findById(Long id) {
        return itemRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public void updateItem(Long id, ItemApiController.ItemRequest itemRequest) {
        Item item = this.findById(id);
        item.updateItemInfo(itemRequest.getItemName(), itemRequest.getStockQuantity(), itemRequest.getItemPrice());
    }
}
