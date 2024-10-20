package ru.cft.shifttest.core.mapper;

import org.mapstruct.Mapper;
import ru.cft.shifttest.api.dto.seller.SellerInfoDto;
import ru.cft.shifttest.core.entity.Seller;

@Mapper
public interface SellerMapper {
    SellerInfoDto map(Seller seller);
}