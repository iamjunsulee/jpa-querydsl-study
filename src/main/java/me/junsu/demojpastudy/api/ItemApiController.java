package me.junsu.demojpastudy.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import me.junsu.demojpastudy.domain.Book;
import me.junsu.demojpastudy.domain.Item;
import me.junsu.demojpastudy.service.ItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ItemApiController {
    private final ItemService itemService;

    @PostMapping("/api/addBook")
    public ItemResponse registerItem(@RequestBody ItemRequest itemRequest) {
        //상품 카테고리가 필요해보임. 상품 타입이 뭔줄 알아야 해당 객체를 생성할 수 있지 않을까..?
        Book book = new Book(itemRequest.getItemName(), itemRequest.getStockQuantity(), itemRequest.getItemPrice(), itemRequest.getAuthor());
        Long saveItemId = itemService.saveItem(book);
        return new ItemResponse(saveItemId);
    }

    @GetMapping("/api/books")
    public Result<List<ItemDto>> getAllBooks() {
        List<Item> allItems = itemService.getAllItems();
        List<ItemDto> itemDtos = allItems.stream()
                .map(item -> new ItemDto(item.getId(), item.getName(), item.getPrice(), item.getStockQuantity()))
                .collect(Collectors.toList());
        return new Result<>(itemDtos);
    }

    @GetMapping("/api/items")
    public PageResult<List<ItemDto>> getAllItemsWithPage(@RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 2);
        Page<Item> itemsWithPage = itemService.getAllItemsWithPage(pageable);
        List<Item> items = itemsWithPage.getContent();
        List<ItemDto> itemDtoList = items.stream()
                .map(item -> new ItemDto(item.getId(), item.getName(), item.getPrice(), item.getStockQuantity()))
                .collect(Collectors.toList());
        return new PageResult<>(itemDtoList, itemsWithPage.getNumber(), itemsWithPage.getTotalPages(), itemsWithPage.getTotalElements());
    }

    @GetMapping("/api/items/{id}")
    public Result<ItemDto> getItemById(@PathVariable Long id) {
        Item item = itemService.findById(id);
        ItemDto itemDto = new ItemDto(item.getId(), item.getName(), item.getPrice(), item.getStockQuantity());
        return new Result<>(itemDto);
    }

    @DeleteMapping("/api/items/{id}")
    public void deleteItem(@PathVariable Long id) {
        itemService.deleteById(id);
    }

    @PutMapping("/api/items/{id}")
    public ItemResponse updateItem(@PathVariable Long id, @RequestBody ItemRequest itemRequest) {
        itemService.updateItem(id, itemRequest);
        return new ItemResponse(id);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class PageResult<T> {
        private T items;
        private int currentPage;
        private int totalPages;
        private long totalItems;
    }

    @Data
    @AllArgsConstructor
    static class ItemDto {
        private Long id;
        private String itemName;
        private int itemPrice;
        private int stockQuantity;
        //private String author;    //흠 접근이 안되는데 어떡하지
    }

    @Data
    @AllArgsConstructor
    static class ItemResponse {
        private Long id;
    }
    @Data
    public static class ItemRequest {
        private String itemName;
        private int itemPrice;
        private int stockQuantity;
        private String author;
    }
}
