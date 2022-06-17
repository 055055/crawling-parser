package com.wemakeprice.parser.web.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@ToString
@Getter
@Setter
public class ParserReqDto {

    @NotBlank(message = "url을 입력해 주세요.")
    private String url;

    @NotBlank(message = "파싱 타입을 입력해 주세요.")
    private String type;

    @Min(value = 1, message = "출력단위묶음을 입력해 주세요.(1이상)")
    private int unitNum;
}
