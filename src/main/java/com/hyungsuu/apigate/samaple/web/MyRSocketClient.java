package com.hyungsuu.apigate.samaple.web;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class MyRSocketClient {
	@Autowired
    private RSocketRequester rSocketRequester;

 //   private final RSocketRequester rsocketRequester;

//    public MyRSocketClient(RSocketRequester.Builder builder) {
        // Connect over TCP (or WebSocket) and block until connected
 //       this.rsocketRequester = builder
 //               .tcp("localhost", 7575);
//    }

    // Example of making a request-response call
    public Mono<String> sendMessage(String message) {
  
    	
        return rSocketRequester
                .route("service.route") // Specify the route
                .data(message)         // Set the data payload
                .retrieveMono(String.class); // Define the response type
 
    }
    
//  public Mono<String> sendRequestAndReceiveResponse(String dataToSend) {
//  return requester.rsocketClient()
//      .requestResponse() // Request-Response 상호작용 시작
//      .route("service.endpoint.route") // 서버의 특정 라우트 지정
//      .data(dataToSend) // 보낼 데이터 (페이로드) 설정
//      .retrieveMono(String.class); // 단일 응답을 Mono<String>으로 받음
//}
}