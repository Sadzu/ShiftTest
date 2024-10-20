package ru.cft.shiftlabtesttask.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.cft.shiftlabtesttask.api.dto.seller.SellerCreatePatchDto;
import ru.cft.shiftlabtesttask.api.dto.seller.SellerInfoDto;
import ru.cft.shiftlabtesttask.core.service.SellerService;

import java.util.List;

import static api.Path.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SellerController {
    private final SellerService sellerService;

    @PostMapping(SELLERS)
    public SellerInfoDto create(@RequestBody SellerCreatePatchDto sellerCreateRequest) {
        log.info("Create seller: {}", sellerCreateRequest);
        return sellerService.create(sellerCreateRequest);
    }

    @GetMapping(SELLERS_ID)
    public SellerInfoDto getById(@PathVariable Integer id) {
        return sellerService.getById(id);
    }

    @PatchMapping(SELLERS_ID)
    public SellerInfoDto update(@PathVariable Integer id, @RequestBody SellerCreatePatchDto sellerUpdateRequest) {
        return sellerService.update(id, sellerUpdateRequest);
    }

    @DeleteMapping(SELLERS_ID)
    public void delete(@PathVariable Integer id) {
        sellerService.delete(id);
    }

    @GetMapping(SELLERS_BEST)
    public SellerInfoDto getBestSeller() {
        return sellerService.getBestSeller();
    }

    @GetMapping(SELLERS_COST)
    public List<SellerInfoDto> getSellersLessThanCost(@PathVariable Integer cost) {
        return sellerService.getSellersLessThanCost(cost);
    }
}
