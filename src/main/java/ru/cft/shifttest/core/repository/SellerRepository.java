package ru.cft.shifttest.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.cft.shifttest.core.entity.Seller;

import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Integer> {
    Optional<Seller> findById(Integer id);

    @Query(nativeQuery = true, value = "select max(id) from sellers")
    Integer getMaxId();
}
