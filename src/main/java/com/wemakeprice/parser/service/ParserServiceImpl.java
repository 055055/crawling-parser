package com.wemakeprice.parser.service;


import com.wemakeprice.parser.error.ServiceError;
import com.wemakeprice.parser.error.ServiceException;
import com.wemakeprice.parser.type.ParsingType;
import com.wemakeprice.parser.web.dto.ParserReqDto;
import com.wemakeprice.parser.web.dto.ParserResDto;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class ParserServiceImpl implements ParserService{


    @Override
    public ParserResDto requestParsing(ParserReqDto req) throws ServiceException {

        try {
           String data = getCrawlingDataByType(req.getUrl(), req.getType());
           String mergeData = dataSortingAndMerge(data);
           ParserResDto res =  calculOutput(req.getUnitNum(), mergeData);
           res.setMergeData(mergeData);
           res.setMergeDataLength(mergeData.length());
           res.setCrawlingData(data);
           return res;

        }catch (ServiceException se){
            log.info("ServiceException : {}",se.getServiceError());
            throw new ServiceException(se.getServiceError());
        }catch (Exception e){
            log.info("Exception : {}",e.getMessage(),e);
            throw new ServiceException(e, ServiceError.SERVICE_ERROR);
        }

    }

    public String getCrawlingDataByType(String url, String parsingType) throws ServiceException {
        try{
            Document document = Jsoup.connect(url).get();
            log.debug("document = " + document);
            switch (ParsingType.valueOf(parsingType.toUpperCase())){
                case HTML:
                     return document.html();
                case TEXT:
                     return  document.text();
                default:
                    throw new ServiceException(ServiceError.PARSING_DATA_TYPE_INVALID);
            }

        }catch (ServiceException se){
            log.info("ServiceException : {}",se.getServiceError());
            throw new ServiceException(se.getServiceError());
        }catch (Exception e){
            log.info("Exception : {}",e.getMessage(),e);
            throw new ServiceException(e, ServiceError.PARSING_HTML_REQUEST_ERROR);
        }
    }


    public String dataSortingAndMerge(String data) throws ServiceException {
        final String TARGET_CHARACTERS = "0123456789AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz";
        final int sameCharSorting = 0;
        List<Integer> numbers = new ArrayList();
        List<String> strings = new ArrayList();

        try{
            for (int i = 0; i < data.length(); i++) {
                char[] target = data.toCharArray();
                int targetIndex = TARGET_CHARACTERS.indexOf(target[i]);
                if (targetIndex > -1 && targetIndex<10) numbers.add(Character.getNumericValue(target[i]));
                if (targetIndex > -1 && targetIndex>=10) strings.add(String.valueOf(target[i]));
            }

            if(numbers.isEmpty() && strings.isEmpty()) throw new ServiceException(ServiceError.PARSING_DATA_IS_EMPTY);

            log.debug("numbers before sort : {}",numbers);
            log.debug("strings before sort : {}",strings);


            Collections.sort(numbers);
            Collections.sort(strings, (s1, s2) -> {
                int sameChar = String.CASE_INSENSITIVE_ORDER.compare(s1, s2);
                if (sameChar == sameCharSorting) sameChar = s1.compareTo(s2);

                return sameChar;
            });

            log.debug("numbers after sort : {}",numbers);
            log.debug("strings after sort : {}",strings);

            return crossMergeStringsAndNumbers(strings,numbers);

        }catch (ServiceException se){
            log.info("ServiceException : {}",se.getServiceError());
            throw new ServiceException(se.getServiceError());
        }catch (Exception e){
            log.info("Exception : {}",e.getMessage(),e);
            throw new ServiceException(e, ServiceError.PARSING_DATA_CROSS_MERGE_ERROR);
        }

    }

    public String crossMergeStringsAndNumbers(List strings, List numbers) throws ServiceException {
        try{
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < Math.max(numbers.size(),strings.size()); i++) {
                if (i < strings.size()) sb.append(strings.get(i));
                if (i < numbers.size()) sb.append(numbers.get(i));
            }
            return sb.toString();

        }catch (Exception e){
            log.info("Exception : {}",e.getMessage(),e);
            throw new ServiceException(e, ServiceError.PARSING_DATA_CROSS_MERGE_ERROR);
        }

    }


    public ParserResDto calculOutput(int unitNum, String data) throws ServiceException {

       try{
           int  quotient = data.length() / unitNum;
           int  remainder = data.length() % unitNum;

           String quotientData = data.substring(0, (quotient * unitNum));
           String remainderData = data.substring(quotientData.length());
           return ParserResDto.builder()
                   .quotientNum(quotient)
                   .quotientData(quotientData)
                   .remainderNum(remainder)
                   .remainderData(remainderData)
                   .unitNum(unitNum)
                   .build();
       }catch (Exception e){
        log.info("Exception : {}",e.getMessage(),e);
        throw new ServiceException(e, ServiceError.PARSING_DATA_CALCULATE_ERROR);
       }
    }



}
