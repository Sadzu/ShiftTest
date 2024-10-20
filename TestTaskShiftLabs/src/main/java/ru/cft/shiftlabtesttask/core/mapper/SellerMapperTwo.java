package ru.cft.shiftlabtesttask.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.cft.shiftlabtesttask.api.dto.seller.SellerCreatePatchDto;
import ru.cft.shiftlabtesttask.api.dto.seller.SellerInfoDto;
import ru.cft.shiftlabtesttask.core.entity.Seller;

@Mapper
public interface SellerMapperTwo {
    SellerInfoDto map(Seller seller);

    void map(@MappingTarget Seller seller, SellerCreatePatchDto sellerDto);
}
