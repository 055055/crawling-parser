package com.wemakeprice.parser.error;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {
    private ServiceError serviceError;

    public ServiceException() {
        super();
    }

    public ServiceException(ServiceError serviceError) {
        super(new ServiceException());
        this.serviceError = serviceError;
    }

    public ServiceException(Throwable cause, ServiceError serviceError) {
        super(cause);
        this.serviceError = serviceError;
    }
}
