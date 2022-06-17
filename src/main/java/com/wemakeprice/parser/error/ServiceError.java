package com.wemakeprice.parser.error;

import com.wemakeprice.parser.web.dto.ResultError;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ServiceError {
    REQUEST_VALIDATION("4001", "요청값 벨리데이션 체크", HttpStatus.BAD_REQUEST),
    PARSING_HTML_REQUEST_ERROR("4003", "HTML 파싱 요청 에러", HttpStatus.INTERNAL_SERVER_ERROR),
    PARSING_DATA_TYPE_INVALID("4004", "올바른 데이터 타입이 아닙니다.", HttpStatus.BAD_REQUEST),
    PARSING_DATA_IS_EMPTY("4005", "파싱할 데이터가 없습니다.", HttpStatus.BAD_REQUEST),
    SERVICE_ERROR("5000", "내부 서버 오류", HttpStatus.INTERNAL_SERVER_ERROR),
    PARSING_DATA_CROSS_MERGE_ERROR("5001", "내부 서버 오류", HttpStatus.INTERNAL_SERVER_ERROR),
    PARSING_DATA_CALCULATE_ERROR("5002", "내부 서버 오류", HttpStatus.INTERNAL_SERVER_ERROR);

    private String code;
    private String message;
    private HttpStatus httpStatus;

    ServiceError(String resultCode, String resultMessage, HttpStatus httpStatus) {
        this.code = resultCode;
        this.message = resultMessage;
        this.httpStatus = httpStatus;
    }

    public ResultError getResultError() {
        return ResultError.builder()
                .code(this.code)
                .message(this.message)
                .httpStatus(this.httpStatus)
                .build();
    }
}
