package ru.cft.shifttest.core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.cft.shifttest.api.dto.seller.BestPeriodDto;
import ru.cft.shifttest.api.dto.seller.SellerCreatePatchDto;
import ru.cft.shifttest.api.dto.seller.SellerInfoDto;
import ru.cft.shifttest.core.entity.Seller;
import ru.cft.shifttest.core.exception.ErrorCode;
import ru.cft.shifttest.core.exception.ServiceException;
import ru.cft.shifttest.core.mapper.SellerMapper;
import ru.cft.shifttest.core.repository.SellerRepository;
import ru.cft.shifttest.core.repository.TransactionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.cft.shifttest.core.Messages.SELLER_LESS_AMOUNT_NOT_FOUNT;
import static ru.cft.shifttest.core.Messages.SELLER_NOT_FOUND;
import static ru.cft.shifttest.core.exception.HttpStatuses.INVALID_REQUEST;
import static ru.cft.shifttest.core.exception.HttpStatuses.OBJECT_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class SellerServiceImpl implements SellerService {
    private final SellerRepository _sellerRepository;
    private final SellerMapper _sellerMapper;
    private final TransactionRepository _transactionRepository;
    private static final ArrayList<String> PERIODS_LIST = new ArrayList<>(Arrays.asList("hour", "day", "month", "quarter", "year"));

    @Override
    public SellerInfoDto create(SellerCreatePatchDto newSeller) {
        Seller seller = buildNewSeller(newSeller);

        if (seller.getName() == null || seller.getName().isEmpty()) {
            throw new ServiceException(new ErrorCode(INVALID_REQUEST), "Invalid name");
        }
        if (seller.getContactInfo() == null || seller.getContactInfo().isEmpty()) {
            throw new ServiceException(new ErrorCode(INVALID_REQUEST), "Invalid contact");
        }

        _sellerRepository.save(seller);

        return _sellerMapper.map(seller);
    }

    @Override
    public SellerInfoDto getById(Integer id) {
        return _sellerMapper.map(getByIdOrThrow(id));
    }

    @Override
    public SellerInfoDto update(Integer id, SellerCreatePatchDto sellerDto) {
        if (sellerDto == null || ((sellerDto.name() == null || sellerDto.name().isEmpty()) && (sellerDto.contactInfo() == null || sellerDto.contactInfo().isEmpty()))) {
            throw new ServiceException(new ErrorCode(INVALID_REQUEST), "Empty patch request");
        }

        Seller seller = getByIdOrThrow(id);
        patchSeller(sellerDto, seller);
        _sellerRepository.save(seller);

        return _sellerMapper.map(seller);
    }

    @Override
    public void delete(Integer id) {
        Seller seller = getByIdOrThrow(id);
        _sellerRepository.delete(seller);
    }

    @Override
    public SellerInfoDto getBestSeller(String period) {
        int maxId = _sellerRepository.getMaxId();
        int maxAmountId = -1;
        int maxAmount = -1;
        Integer count = 1;

        if (period != null && !period.isEmpty()) {
            if (!PERIODS_LIST.contains(period)) {
                throw new ServiceException(new ErrorCode(INVALID_REQUEST), "Invalid period: " + period);
            }
            if (period.equals("quarter")) {
                period = "3 month";
            } else {
                period = count + " " + period;
            }

            for (int i = 1; i <= maxId; i++) {
                if (_transactionRepository.getSumAmountBySellerIdWithPeriod(i, period).isPresent()) {
                    int amount = _transactionRepository.getSumAmountBySellerIdWithPeriod(i, period).get();
                    if (amount > maxAmount) {
                        maxAmount = amount;
                        maxAmountId = i;
                    }
                }
            }
        } else {
            for (int i = 1; i <= maxId; i++) {
                if (_transactionRepository.getSumAmountBySellerId(i).isPresent()) {
                    int amount = _transactionRepository.getSumAmountBySellerId(i).get();
                    if (amount > maxAmount) {
                        maxAmount = amount;
                        maxAmountId = i;
                    }
                }
            }
        }

        if (maxAmount == -1) {
            throw new ServiceException(new ErrorCode(OBJECT_NOT_FOUND), "Best seller not found");
        }

        return _sellerMapper.map(getByIdOrThrow(maxAmountId));
    }

    @Override
    public List<SellerInfoDto> getSellersLessThanCost(Integer cost, String period) {
        List<SellerInfoDto> result = new ArrayList<>();
        int maxId = _sellerRepository.getMaxId();

        if (period != null && !period.isEmpty()) {
            int count = 1;
            if (!PERIODS_LIST.contains(period)) {
                throw new ServiceException(new ErrorCode(INVALID_REQUEST), "Invalid period: " + period);
            }
            if (period.equals("quarter")) {
                count = 3;
                period = count + " month";
            } else {
                period = count + " " + period;
            }
            for (int i = 1; i <= maxId; i++) {
                if (_transactionRepository.getSumAmountBySellerIdWithPeriod(i, period).isPresent()) {
                    int amount = _transactionRepository.getSumAmountBySellerIdWithPeriod(i, period).get();
                    if (amount < cost) {
                        result.add(_sellerMapper.map(getByIdOrThrow(i)));
                    }
                }
            }
        } else {
            for (int i = 1; i <= maxId; i++) {
                if (_transactionRepository.getSumAmountBySellerId(i).isPresent()) {
                    int amount = _transactionRepository.getSumAmountBySellerId(i).get();
                    if (amount < cost) {
                        result.add(_sellerMapper.map(getByIdOrThrow(i)));
                    }
                }
            }
        }

        if (result.isEmpty()) {
            throw new ServiceException(new ErrorCode(OBJECT_NOT_FOUND), String.format(SELLER_LESS_AMOUNT_NOT_FOUNT, cost));
        }

        return result;
    }

    @Override
    public List<SellerInfoDto> getSellersLessThanCost(Integer cost, String begin, String end) {
        List<SellerInfoDto> result = new ArrayList<>();
        Integer maxId = _sellerRepository.getMaxId();
        if (begin != null && !begin.isEmpty() && end != null && !end.isEmpty()) {
            if (maxId == 0) {
                throw new ServiceException(new ErrorCode(INVALID_REQUEST), "No sellers");
            }
            for (int i = 1; i <= maxId; i++) {
                if (_transactionRepository.getSumAmountBySellerIdWithPeriod(i, begin, end).isPresent()) {
                    int amount = _transactionRepository.getSumAmountBySellerIdWithPeriod(i, begin, end).get();
                    if (amount < cost) {
                        result.add(_sellerMapper.map(getByIdOrThrow(i)));
                    }
                }
            }
        } else {
            for (int i = 1; i <= maxId; i++) {
                if (_transactionRepository.getSumAmountBySellerId(i).isPresent()) {
                    int amount = _transactionRepository.getSumAmountBySellerId(i).get();
                    if (amount < cost) {
                        result.add(_sellerMapper.map(getByIdOrThrow(i)));
                    }
                }
            }
        }
        if (result.isEmpty()) {
            throw new ServiceException(new ErrorCode(OBJECT_NOT_FOUND), String.format(SELLER_LESS_AMOUNT_NOT_FOUNT, cost));
        }

        return result;
    }

    private Seller buildNewSeller(SellerCreatePatchDto newSeller) {
        return Seller.builder()
                .name(newSeller.name())
                .contactInfo(newSeller.contactInfo())
                .registrationDate(LocalDateTime.now())
                .build();
    }

    private Seller getByIdOrThrow(Integer id) {
        return _sellerRepository.findById(id)
                .orElseThrow(() -> new ServiceException(new ErrorCode(OBJECT_NOT_FOUND), String.format(SELLER_NOT_FOUND, id)));
    }

    private void patchSeller(SellerCreatePatchDto dto, Seller seller) {
        if (!dto.name().isEmpty()) {
            seller.setName(dto.name());
        }
        if (!dto.contactInfo().isEmpty()) {
            seller.setContactInfo(dto.contactInfo());
        }
    }

    @Override
    public String getBestPeriodOfSeller(Integer id, String period) {
        if (period == null || period.isEmpty() || !PERIODS_LIST.contains(period) || period.equals("quarter")) {
            throw new ServiceException(new ErrorCode(INVALID_REQUEST), "Invalid period: " + period);
        }
        log.info(id + " " + period);
        if (_transactionRepository.getBestPeriodOfSeller(period, id).isPresent()) {
            String response = _transactionRepository.getBestPeriodOfSeller(period, id).get()[0];
            if (period.equals("day")) {
                response = response.split(" ")[0];
            } else if (period.equals("week")) {
                response = response.split(" ")[0];
            } else if (period.equals("month")) {
                response = response.split(" ")[0].substring(0, 7);
            } else if (period.equals("year")) {
                response = response.split(" ")[0].substring(0, 3);
            }
            return response;
        } else {
            throw new ServiceException(new ErrorCode(OBJECT_NOT_FOUND), String.format("Transactions of seller with id: %d not found", id));
        }
    }
}
