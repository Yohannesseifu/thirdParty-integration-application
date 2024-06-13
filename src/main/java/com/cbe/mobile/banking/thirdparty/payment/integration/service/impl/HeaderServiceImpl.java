package com.cbe.mobile.banking.thirdparty.payment.integration.service.impl;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.Header;
import com.cbe.mobile.banking.thirdparty.payment.integration.repository.HeaderRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.HeaderService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.Header}.
 */
@Service
@Transactional
public class HeaderServiceImpl implements HeaderService {

    private final Logger log = LoggerFactory.getLogger(HeaderServiceImpl.class);

    private final HeaderRepository headerRepository;

    public HeaderServiceImpl(HeaderRepository headerRepository) {
        this.headerRepository = headerRepository;
    }

    @Override
    public Header save(Header header) {
        log.debug("Request to save Header : {}", header);
        return headerRepository.save(header);
    }

    @Override
    public Header update(Header header) {
        log.debug("Request to update Header : {}", header);
        return headerRepository.save(header);
    }

    @Override
    public Optional<Header> partialUpdate(Header header) {
        log.debug("Request to partially update Header : {}", header);

        return headerRepository
            .findById(header.getId())
            .map(existingHeader -> {
                if (header.getName() != null) {
                    existingHeader.setName(header.getName());
                }
                if (header.getValueStr() != null) {
                    existingHeader.setValueStr(header.getValueStr());
                }

                return existingHeader;
            })
            .map(headerRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Header> findAll() {
        log.debug("Request to get all Headers");
        return headerRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Header> findOne(UUID id) {
        log.debug("Request to get Header : {}", id);
        return headerRepository.findById(id);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete Header : {}", id);
        headerRepository.deleteById(id);
    }
}
