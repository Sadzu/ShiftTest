package ru.cft.shifttest.core.mapper;

import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.cft.shifttest.api.dto.seller.SellerInfoDto;
import ru.cft.shifttest.core.entity.Seller;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-19T19:51:28+0700",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.5.jar, environment: Java 21.0.5 (Amazon.com Inc.)"
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
