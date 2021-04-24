package com.wemakeprice.parser.error;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ExceptionTest {

    @DisplayName("UnsupportedOperationException Message 테스트 성공")
    @Test
    public void EXCEPTION_MESSAGE_TEST_SUCCESS(){
        Throwable exception = assertThrows(UnsupportedOperationException.class, () -> {
            throw new UnsupportedOperationException("Not supported");
        });
        assertEquals(exception.getMessage(), "Not supported");
    }

    @DisplayName("ServiceException SERVICE_ERROR.SERIVCE_ERROR 테스트 성공")
    @Test
    public void SERVICE_EXCEPTION_MESSAGE_TEST_SUCCESS(){
        ServiceException exception = assertThrows(ServiceException.class, () -> {
            throw new ServiceException(ServiceError.SERVICE_ERROR);
        });
        assertEquals(exception.getServiceError().getMessage(), ServiceError.SERVICE_ERROR.getMessage());
    }

}