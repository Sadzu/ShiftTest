package ru.cft.shifttest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.cft.shifttest.api.dto.seller.SellerCreatePatchDto;
import ru.cft.shifttest.api.dto.seller.SellerInfoDto;
import ru.cft.shifttest.core.entity.Seller;
import ru.cft.shifttest.core.exception.ServiceException;
import ru.cft.shifttest.core.mapper.SellerMapper;
import ru.cft.shifttest.core.repository.SellerRepository;
import ru.cft.shifttest.core.repository.TransactionRepository;
import ru.cft.shifttest.core.service.SellerServiceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SellerServiceTests {
    @Mock
    private SellerRepository _sellerRepository;

    @Mock
    private SellerMapper sellerMapper;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    SellerServiceImpl sellerService;

    @Test
    public void testCreationFailedByName() {
        SellerCreatePatchDto sellerToSave = new SellerCreatePatchDto("", "contactInfo");

        Assertions.assertThrowsExactly(ServiceException.class, () -> sellerService.create(sellerToSave));
    }

    @Test
    public void testCreationFailedByContactInfo() {
        SellerCreatePatchDto sellerToSave = new SellerCreatePatchDto("name", "");

        Assertions.assertThrowsExactly(ServiceException.class, () -> sellerService.create(sellerToSave));
    }

    @Test
    public void testCreationSuccessful() {
        SellerCreatePatchDto sellerToSave = new SellerCreatePatchDto("name", "contacts");
        SellerInfoDto returnedSeller = new SellerInfoDto(1, sellerToSave.name(), sellerToSave.contactInfo(), LocalDateTime.of(1000, 10, 10, 10, 10));
        when(sellerMapper.map(any())).thenReturn(returnedSeller);
        SellerInfoDto result = sellerService.create(sellerToSave);
        Assertions.assertEquals(returnedSeller, result);
    }

    @Test
    public void testGetBestSellerWithoutPeriodSuccess() {
        when(_sellerRepository.getMaxId()).thenReturn(2);
        when(transactionRepository.getSumAmountBySellerId(1)).thenReturn(Optional.of(1000));
        when(transactionRepository.getSumAmountBySellerId(2)).thenReturn(Optional.of(2000));
        Seller seller = Seller.builder()
                .id(2)
                .name("name")
                .contactInfo("contactInfo")
                .registrationDate(LocalDateTime.of(1000, 10, 10, 10, 10))
                .build();
        when(_sellerRepository.findById(2)).thenReturn(Optional.of(seller));

        SellerInfoDto returned = new SellerInfoDto(2, "name", "contactInfo", LocalDateTime.of(1000, 10, 10, 10, 10));
        when(sellerMapper.map(seller)).thenReturn(returned);

        SellerInfoDto actual = sellerService.getBestSeller(null);

        Assertions.assertEquals(actual, returned);
    }

    @Test
    public void testGetBestSellerWithPeriodSuccess() {
        when(_sellerRepository.getMaxId()).thenReturn(3);
        when(transactionRepository.getSumAmountBySellerIdWithPeriod(1, "1 day")).thenReturn(Optional.of(0));
        when(transactionRepository.getSumAmountBySellerIdWithPeriod(2, "1 day")).thenReturn(Optional.of(1000));
        when(transactionRepository.getSumAmountBySellerIdWithPeriod(3, "1 day")).thenReturn(Optional.of(2000));
        Seller seller = Seller.builder()
                .id(3)
                .name("name")
                .contactInfo("contactInfo")
                .registrationDate(LocalDateTime.of(1000, 10, 10, 10, 10))
                .build();
        when(_sellerRepository.findById(3)).thenReturn(Optional.of(seller));
        SellerInfoDto returned = new SellerInfoDto(3, "name", "contactInfo", LocalDateTime.of(1000, 10, 10, 10, 10));
        when(sellerMapper.map(seller)).thenReturn(returned);
        SellerInfoDto actual = sellerService.getBestSeller("day");
        Assertions.assertEquals(actual, returned);
    }

    @Test
    public void testGetSellersLessThanCostWithoutPeriodSuccess() {
        when(_sellerRepository.getMaxId()).thenReturn(3);
        when(transactionRepository.getSumAmountBySellerId(1)).thenReturn(Optional.of(10));
        when(transactionRepository.getSumAmountBySellerId(2)).thenReturn(Optional.of(1000));
        when(transactionRepository.getSumAmountBySellerId(3)).thenReturn(Optional.of(2000));

        Seller seller_0 = Seller.builder()
                .id(1)
                .name("name")
                .contactInfo("contactInfo")
                .registrationDate(LocalDateTime.of(1000, 10, 10, 10, 10))
                .build();
        Seller seller_1 = Seller.builder()
                .id(2)
                .name("name")
                .contactInfo("contactInfo")
                .registrationDate(LocalDateTime.of(1000, 10, 10, 10, 10))
                .build();
        Seller seller_2 = Seller.builder()
                .id(3)
                .name("name")
                .contactInfo("contactInfo")
                .registrationDate(LocalDateTime.of(1000, 10, 10, 10, 10))
                .build();

        when(_sellerRepository.findById(1)).thenReturn(Optional.of(seller_0));
        when(_sellerRepository.findById(2)).thenReturn(Optional.of(seller_1));
        when(_sellerRepository.findById(3)).thenReturn(Optional.of(seller_2));

        SellerInfoDto mappedSeller_0 = new SellerInfoDto(1, "name", "contactInfo", LocalDateTime.of(1000, 10, 10, 10, 10));
        SellerInfoDto mappedSeller_1 = new SellerInfoDto(2, "name", "contactInfo", LocalDateTime.of(1000, 10, 10, 10, 10));
        SellerInfoDto mappedSeller_2 = new SellerInfoDto(3, "name", "contactInfo", LocalDateTime.of(1000, 10, 10, 10, 10));

        List<SellerInfoDto> result = List.of(mappedSeller_0, mappedSeller_1, mappedSeller_2);

        when(sellerMapper.map(seller_0)).thenReturn(mappedSeller_0);
        when(sellerMapper.map(seller_1)).thenReturn(mappedSeller_1);
        when(sellerMapper.map(seller_2)).thenReturn(mappedSeller_2);

        List<SellerInfoDto> actual = sellerService.getSellersLessThanCost(3000, "");

        Assertions.assertEquals(actual, result);
    }

    @Test
    public void testGetSellersLessThanCostWithPeriodSuccess() {
        String period = "day";

        when(_sellerRepository.getMaxId()).thenReturn(3);
        when(transactionRepository.getSumAmountBySellerIdWithPeriod(1, 1 + " " + period)).thenReturn(Optional.of(10));
        when(transactionRepository.getSumAmountBySellerIdWithPeriod(2, 1 + " " + period)).thenReturn(Optional.of(1000));
        when(transactionRepository.getSumAmountBySellerIdWithPeriod(3, 1 + " " + period)).thenReturn(Optional.of(2000));

        Seller seller_0 = Seller.builder()
                .id(1)
                .name("name")
                .contactInfo("contactInfo")
                .registrationDate(LocalDateTime.of(1000, 10, 10, 10, 10))
                .build();
        Seller seller_1 = Seller.builder()
                .id(2)
                .name("name")
                .contactInfo("contactInfo")
                .registrationDate(LocalDateTime.of(1000, 10, 10, 10, 10))
                .build();
        Seller seller_2 = Seller.builder()
                .id(3)
                .name("name")
                .contactInfo("contactInfo")
                .registrationDate(LocalDateTime.of(1000, 10, 10, 10, 10))
                .build();

        when(_sellerRepository.findById(1)).thenReturn(Optional.of(seller_0));
        when(_sellerRepository.findById(2)).thenReturn(Optional.of(seller_1));
        when(_sellerRepository.findById(3)).thenReturn(Optional.of(seller_2));

        SellerInfoDto mappedSeller_0 = new SellerInfoDto(1, "name", "contactInfo", LocalDateTime.of(1000, 10, 10, 10, 10));
        SellerInfoDto mappedSeller_1 = new SellerInfoDto(2, "name", "contactInfo", LocalDateTime.of(1000, 10, 10, 10, 10));
        SellerInfoDto mappedSeller_2 = new SellerInfoDto(3, "name", "contactInfo", LocalDateTime.of(1000, 10, 10, 10, 10));

        List<SellerInfoDto> result = List.of(mappedSeller_0, mappedSeller_1, mappedSeller_2);

        when(sellerMapper.map(seller_0)).thenReturn(mappedSeller_0);
        when(sellerMapper.map(seller_1)).thenReturn(mappedSeller_1);
        when(sellerMapper.map(seller_2)).thenReturn(mappedSeller_2);

        List<SellerInfoDto> actual = sellerService.getSellersLessThanCost(3000, period);

        Assertions.assertEquals(actual, result);
    }

    @Test
    public void testGetBestSellersWithPeriodWrongPeriod() {
        String period = "sosal?";
        Assertions.assertThrowsExactly(ServiceException.class, () -> sellerService.getBestSeller(period));
    }

    @Test
    public void testGetSellersLessThanCostWithPeriodWrongPeriod() {
        String period = "sosal?";
        Assertions.assertThrowsExactly(ServiceException.class, () -> sellerService.getSellersLessThanCost(3000, period));
    }

    @Test
    public void testUpdateSuccess() {
        SellerCreatePatchDto patch = new SellerCreatePatchDto("name_new", "contactInfo_new");

        Seller sellerBefore = Seller.builder()
                .id(1)
                .name("name")
                .contactInfo("contactInfo")
                .registrationDate(LocalDateTime.of(1000, 10, 10, 10, 10))
                .build();

        Seller sellerAfter = Seller.builder()
                .id(1)
                .name("name_new")
                .contactInfo("contactInfo_new")
                .registrationDate(LocalDateTime.of(1000, 10, 10, 10, 10))
                .build();

        when(_sellerRepository.findById(1)).thenReturn(Optional.of(sellerBefore));
        SellerInfoDto result = new SellerInfoDto(1, "name_new", "contactInfo_new", LocalDateTime.of(1000, 10, 10, 10, 10));
        when(sellerMapper.map(sellerAfter)).thenReturn(result);

        Assertions.assertEquals(sellerService.update(1, new SellerCreatePatchDto("name_new", "contactInfo_new")), result);
    }

    @Test
    public void testUpdateFailNulls() {
        SellerCreatePatchDto patch = new SellerCreatePatchDto(null, null);
        Assertions.assertThrowsExactly(ServiceException.class, () -> sellerService.update(1, patch));
    }

    @Test
    public void testUpdateSuccessEmptyName() {
        SellerCreatePatchDto patch = new SellerCreatePatchDto("", "contactInfo_new");

        Seller sellerBefore = Seller.builder()
                .id(1)
                .name("name")
                .contactInfo("contactInfo")
                .registrationDate(LocalDateTime.of(1000, 10, 10, 10, 10))
                .build();

        Seller sellerAfter = Seller.builder()
                .id(1)
                .name("name")
                .contactInfo("contactInfo_new")
                .registrationDate(LocalDateTime.of(1000, 10, 10, 10, 10))
                .build();

        when(_sellerRepository.findById(1)).thenReturn(Optional.of(sellerBefore));
        SellerInfoDto result = new SellerInfoDto(1, "name", "contactInfo_new", LocalDateTime.of(1000, 10, 10, 10, 10));
        when(sellerMapper.map(sellerAfter)).thenReturn(result);

        Assertions.assertEquals(sellerService.update(1, new SellerCreatePatchDto("name", "contactInfo_new")), result);
    }
}
