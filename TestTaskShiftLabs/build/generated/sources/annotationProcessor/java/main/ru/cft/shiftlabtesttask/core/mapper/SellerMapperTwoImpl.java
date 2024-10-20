package ru.cft.shiftlabtesttask.core.mapper;

import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import ru.cft.shiftlabtesttask.api.dto.seller.SellerCreatePatchDto;
import ru.cft.shiftlabtesttask.api.dto.seller.SellerInfoDto;
import ru.cft.shiftlabtesttask.core.entity.Seller;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-18T21:03:22+0700",
    comments = "version: 1.6.2, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.5.jar, environment: Java 21.0.5 (Amazon.com Inc.)"
)
public class SellerMapperTwoImpl implements SellerMapperTwo {

    @Override
    public SellerInfoDto map(Seller seller) {
        if ( seller == null ) {
            return null;
        }

        Integer id = null;
        String name = null;
        String contactInfo = null;
        LocalDateTime registrationDate = null;

        id = seller.getId();
        name = seller.getName();
        contactInfo = seller.getContactInfo();
        registrationDate = seller.getRegistrationDate();

        SellerInfoDto sellerInfoDto = new SellerInfoDto( id, name, contactInfo, registrationDate );

        return sellerInfoDto;
    }

    @Override
    public void map(Seller seller, SellerCreatePatchDto sellerDto) {
        if ( sellerDto == null ) {
            return;
        }

        seller.setName( sellerDto.name() );
        seller.setContactInfo( sellerDto.contactInfo() );
    }
}
