package ru.cft.shifttest.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.*;
import ru.cft.shifttest.api.dto.seller.SellerCreatePatchDto;
import ru.cft.shifttest.api.dto.seller.SellerInfoDto;
import ru.cft.shifttest.core.service.SellerService;

import java.util.List;

import static ru.cft.shifttest.api.Path.*;

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

    @GetMapping(SELLERS_GET_ID)
    public SellerInfoDto getById(@PathVariable Integer id) {
        return sellerService.getById(id);
    }

    @PatchMapping(SELLERS_PATCH_ID)
    public SellerInfoDto update(@PathVariable Integer id, @RequestBody SellerCreatePatchDto sellerUpdateRequest) {
        return sellerService.update(id, sellerUpdateRequest);
    }

    @DeleteMapping(SELLERS_DELETE_ID)
    public void delete(@PathVariable Integer id) {
        sellerService.delete(id);
    }

    @GetMapping(SELLERS_BEST)
    public SellerInfoDto getBestSeller(@RequestParam(required = false) String period) {
        return sellerService.getBestSeller(period);
    }

    @GetMapping(SELLERS_COST)
    public List<SellerInfoDto> getSellersLessThanCost(@PathVariable Integer cost, @RequestParam(required = false) String period) {
        return sellerService.getSellersLessThanCost(cost, period);
    }

    @GetMapping(SELLERS_COST_PERIOD)
    public List<SellerInfoDto> getSellersLessThanCost(@PathVariable Integer cost, @RequestParam(required = false) String begin, @RequestParam(required = false) String end) {
        return sellerService.getSellersLessThanCost(cost, begin, end);
    }

    @GetMapping(SELLERS_BEST_PERIOD)
    public String getBestPeriodOfSeller(@RequestParam(required = true) Integer id, @RequestParam(required = true) String period) {
        return sellerService.getBestPeriodOfSeller(id, period);
    }
}
