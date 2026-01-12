package com.hyungsuu.apigate.samaple.web;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.rsocket.RSocketRequester;

import org.springframework.stereotype.Controller;

import com.hyungsuu.apigate.samaple.vo.Message;

import lombok.extern.slf4j.Slf4j;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Controller
public class RSocketClientController {
  
	@Autowired
    private RSocketRequester rSocketRequester;
	
    @MessageMapping("responder-request-response.{id}")
    public Mono<String> handleRequestResponse(@DestinationVariable Integer id,
                                       @Payload String payload) {
        log.info("Request / Response"+id);

        return Mono.just("Hello " + payload + " with id of " + id)
                .doOnNext(log::info);
    }
    
    @MessageMapping("responder-fire-forget")
    public void handleFireAndForget(String payload) {
        log.info("Fire and Forget");
        log.info(payload);
    }
  
    @MessageMapping("responder-channel-stream")
    public Flux<String> handleStream(@Payload Flux<String> payloads) {
        log.info("Channel Stream");
       
        return payloads.map(request -> request.toUpperCase())
                .doOnNext(log::info);
    }

}


//import reactor.core.publisher.Mono;
//import org.springframework.messaging.rsocket.RSocketRequester;
//
//public class RSocketClientExample {
//
//    private final RSocketRequester requester;
//
//    public RSocketClientExample(RSocketRequester requester) {
//        this.requester = requester;
//    }
//
//    public Mono<String> sendRequestAndReceiveResponse(String dataToSend) {
//        return requester.rsocketClient()
//            .requestResponse() // Request-Response 상호작용 시작
//            .route("service.endpoint.route") // 서버의 특정 라우트 지정
//            .data(dataToSend) // 보낼 데이터 (페이로드) 설정
//            .retrieveMono(String.class); // 단일 응답을 Mono<String>으로 받음
//    }
//}