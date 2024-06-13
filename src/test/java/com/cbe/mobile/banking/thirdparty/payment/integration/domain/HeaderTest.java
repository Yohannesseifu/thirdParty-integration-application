package com.cbe.mobile.banking.thirdparty.payment.integration.domain;

import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.ApiRequestTestSamples.*;
import static com.cbe.mobile.banking.thirdparty.payment.integration.domain.HeaderTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.cbe.mobile.banking.thirdparty.payment.integration.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HeaderTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Header.class);
        Header header1 = getHeaderSample1();
        Header header2 = new Header();
        assertThat(header1).isNotEqualTo(header2);

        header2.setId(header1.getId());
        assertThat(header1).isEqualTo(header2);

        header2 = getHeaderSample2();
        assertThat(header1).isNotEqualTo(header2);
    }

    @Test
    void apiRequestTest() {
        Header header = getHeaderRandomSampleGenerator();
        ApiRequest apiRequestBack = getApiRequestRandomSampleGenerator();

        header.setApiRequest(apiRequestBack);
        assertThat(header.getApiRequest()).isEqualTo(apiRequestBack);

        header.apiRequest(null);
        assertThat(header.getApiRequest()).isNull();
    }
}
