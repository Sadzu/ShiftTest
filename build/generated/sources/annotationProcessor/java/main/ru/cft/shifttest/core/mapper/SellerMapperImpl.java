package ru.cft.shifttest.core.mapper;

import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.cft.shifttest.api.dto.seller.SellerInfoDto;
import ru.cft.shifttest.core.entity.Seller;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-22T06:51:18+0700",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.5.jar, environment: Java 21.0.6 (Amazon.com Inc.)"
)
@Component
public class SellerMapperImpl implements SellerMapper {

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
}
