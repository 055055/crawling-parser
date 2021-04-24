package com.wemakeprice.parser.web;

import com.wemakeprice.parser.service.ParserService;
import com.wemakeprice.parser.web.dto.ParserReqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequestMapping("/v1/parser")
@Controller
@RequiredArgsConstructor
public class ParserController {

    private final ParserService parserService;


    @GetMapping
    public String parserForm(){
        return "parser-form.html";

    }

    @PostMapping
    public @ResponseBody
    ResponseEntity<?> requestParsing(@RequestBody @Valid ParserReqDto req){
        log.debug("req = " +req.toString());
        return new ResponseEntity<>(parserService.requestParsing(req), HttpStatus.CREATED);
    }




}
