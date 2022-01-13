package me.junsu.demojpastudy.repository;

import me.junsu.demojpastudy.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
