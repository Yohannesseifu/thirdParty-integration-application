package com.cbe.mobile.banking.thirdparty.payment.integration.service.impl;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.ThirdPartyIntegration;
import com.cbe.mobile.banking.thirdparty.payment.integration.repository.ThirdPartyIntegrationRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.ThirdPartyIntegrationService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.ThirdPartyIntegration}.
 */
@Service
@Transactional
public class ThirdPartyIntegrationServiceImpl implements ThirdPartyIntegrationService {

    private final Logger log = LoggerFactory.getLogger(ThirdPartyIntegrationServiceImpl.class);

    private final ThirdPartyIntegrationRepository thirdPartyIntegrationRepository;

    public ThirdPartyIntegrationServiceImpl(ThirdPartyIntegrationRepository thirdPartyIntegrationRepository) {
        this.thirdPartyIntegrationRepository = thirdPartyIntegrationRepository;
    }

    @Override
    public ThirdPartyIntegration save(ThirdPartyIntegration thirdPartyIntegration) {
        log.debug("Request to save ThirdPartyIntegration : {}", thirdPartyIntegration);
        return thirdPartyIntegrationRepository.save(thirdPartyIntegration);
    }

    @Override
    public ThirdPartyIntegration update(ThirdPartyIntegration thirdPartyIntegration) {
        log.debug("Request to update ThirdPartyIntegration : {}", thirdPartyIntegration);
        return thirdPartyIntegrationRepository.save(thirdPartyIntegration);
    }

    @Override
    public Optional<ThirdPartyIntegration> partialUpdate(ThirdPartyIntegration thirdPartyIntegration) {
        log.debug("Request to partially update ThirdPartyIntegration : {}", thirdPartyIntegration);

        return thirdPartyIntegrationRepository
            .findById(thirdPartyIntegration.getId())
            .map(existingThirdPartyIntegration -> {
                if (thirdPartyIntegration.getIsDraft() != null) {
                    existingThirdPartyIntegration.setIsDraft(thirdPartyIntegration.getIsDraft());
                }
                if (thirdPartyIntegration.getIntegrationName() != null) {
                    existingThirdPartyIntegration.setIntegrationName(thirdPartyIntegration.getIntegrationName());
                }
                if (thirdPartyIntegration.getCompanyName() != null) {
                    existingThirdPartyIntegration.setCompanyName(thirdPartyIntegration.getCompanyName());
                }
                if (thirdPartyIntegration.getDescription() != null) {
                    existingThirdPartyIntegration.setDescription(thirdPartyIntegration.getDescription());
                }
                if (thirdPartyIntegration.getIconPath() != null) {
                    existingThirdPartyIntegration.setIconPath(thirdPartyIntegration.getIconPath());
                }
                if (thirdPartyIntegration.getEnabled() != null) {
                    existingThirdPartyIntegration.setEnabled(thirdPartyIntegration.getEnabled());
                }
                if (thirdPartyIntegration.getAccountNumber() != null) {
                    existingThirdPartyIntegration.setAccountNumber(thirdPartyIntegration.getAccountNumber());
                }
                if (thirdPartyIntegration.getMinimumAmount() != null) {
                    existingThirdPartyIntegration.setMinimumAmount(thirdPartyIntegration.getMinimumAmount());
                }
                if (thirdPartyIntegration.getMaximumAmount() != null) {
                    existingThirdPartyIntegration.setMaximumAmount(thirdPartyIntegration.getMaximumAmount());
                }
                if (thirdPartyIntegration.getCurrencyCode() != null) {
                    existingThirdPartyIntegration.setCurrencyCode(thirdPartyIntegration.getCurrencyCode());
                }
                if (thirdPartyIntegration.getPaymentConfirmationTemplate() != null) {
                    existingThirdPartyIntegration.setPaymentConfirmationTemplate(thirdPartyIntegration.getPaymentConfirmationTemplate());
                }
                if (thirdPartyIntegration.getPaymentSuccessTemplate() != null) {
                    existingThirdPartyIntegration.setPaymentSuccessTemplate(thirdPartyIntegration.getPaymentSuccessTemplate());
                }
                if (thirdPartyIntegration.getPaymentErrorTemplate() != null) {
                    existingThirdPartyIntegration.setPaymentErrorTemplate(thirdPartyIntegration.getPaymentErrorTemplate());
                }
                if (thirdPartyIntegration.getIntegrationCategory() != null) {
                    existingThirdPartyIntegration.setIntegrationCategory(thirdPartyIntegration.getIntegrationCategory());
                }
                if (thirdPartyIntegration.getVisiblity() != null) {
                    existingThirdPartyIntegration.setVisiblity(thirdPartyIntegration.getVisiblity());
                }
                if (thirdPartyIntegration.getConfirmRecipientIdentity() != null) {
                    existingThirdPartyIntegration.setConfirmRecipientIdentity(thirdPartyIntegration.getConfirmRecipientIdentity());
                }

                return existingThirdPartyIntegration;
            })
            .map(thirdPartyIntegrationRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ThirdPartyIntegration> findAll() {
        log.debug("Request to get all ThirdPartyIntegrations");
        return thirdPartyIntegrationRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ThirdPartyIntegration> findOne(UUID id) {
        log.debug("Request to get ThirdPartyIntegration : {}", id);
        return thirdPartyIntegrationRepository.findById(id);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete ThirdPartyIntegration : {}", id);
        thirdPartyIntegrationRepository.deleteById(id);
    }
}
