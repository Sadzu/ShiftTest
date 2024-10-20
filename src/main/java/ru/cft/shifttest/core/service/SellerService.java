package ru.cft.shifttest.core.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.cft.shifttest.api.dto.seller.SellerCreatePatchDto;
import ru.cft.shifttest.api.dto.seller.SellerInfoDto;

import java.util.List;

@Service
public interface SellerService {
    @Transactional
    SellerInfoDto create(SellerCreatePatchDto newSeller);

    SellerInfoDto getById(Integer id);

    @Transactional
    SellerInfoDto update(Integer id, SellerCreatePatchDto sellerDto);

    @Transactional
    void delete(Integer id);

    SellerInfoDto getBestSeller(String period);

    List<SellerInfoDto> getSellersLessThanCost(Integer cost, String period);
}
