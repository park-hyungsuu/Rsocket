package com.hyungsuu.apigate.samaple.web;


import java.time.Duration;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hyungsuu.apigate.samaple.vo.UserBaseVo;
import com.hyungsuu.apigate.samaple.vo.UserResVo;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class RSocketController {


	@Autowired
    private RSocketRequester rSocketRequester;


	
	@GetMapping("/request-response1")
//	@RequestMapping(value="/request-response1", method=RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public Mono<String> sendRequestResponse() {
        log.info("Sending request / response");

        
		return rSocketRequester.route("responder-request-response.{id}", 123)
                .data("data to send")
                .retrieveMono(String.class);
        
    }
    
    @RequestMapping(value="/request-response", method=RequestMethod.POST, consumes = "application/json;charset=UTF-8", produces="application/json;charset=UTF-8")
    public ResponseEntity<UserResVo> sendRequestResponse(@RequestBody UserBaseVo userBaseVo) {
        log.info("Sending request / response");
     
        Mono<String> monoString= rSocketRequester
        		.route("responder-request-response.{id}", 123)
                .data("data to send")
                .retrieveMono(String.class);
        String result = monoString.block();
        UserResVo userResVo = new UserResVo();
        userResVo.setSuccess();
        userResVo.setData(result);
		return new ResponseEntity<UserResVo>(userResVo, HttpStatus.OK); 
    }
    
    
    @GetMapping("/fire-forget")
    public void sendFireAndForget() {
        log.info("Sending fire-and-forget");
        rSocketRequester.route("responder-fire-forget")
                .data(UUID.randomUUID().toString())
                .send()
                .subscribe();
    }
    
    @GetMapping(value = "/channel-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> sendStream() {
        log.info("Sending channel stream ...");
        Flux<String> data = Flux.interval(Duration.ofSeconds(1))
                .take(10)
                .map(i -> UUID.randomUUID().toString());
        return rSocketRequester.route("responder-channel-stream")
                .data(data)
                .retrieveFlux(String.class)
                .doOnNext(log::info);
    }
 
  
    
  
}