package com.wemakeprice.parser.service;

import com.wemakeprice.parser.web.dto.ParserReqDto;
import com.wemakeprice.parser.web.dto.ParserResDto;

public interface ParserService {
    ParserResDto requestParsing(ParserReqDto req);

}
