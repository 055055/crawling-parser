package com.wemakeprice.parser.web.dto;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class ParserResDto {

    private String quotientData;
    private String remainderData;
    private String mergeData;
    private String crawlingData;
    private int unitNum;
    private int quotientNum;
    private int remainderNum;
    private int mergeDataLength;


    @Builder
    public ParserResDto(String quotientData, String remainderData, Integer unitNum, Integer quotientNum, Integer remainderNum) {
        this.quotientData = quotientData;
        this.remainderData = remainderData;
        this.unitNum = unitNum;
        this.quotientNum = quotientNum;
        this.remainderNum = remainderNum;
    }
}
