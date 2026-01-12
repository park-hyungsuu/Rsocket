package com.hyungsuu.apigate.samaple.vo;


import lombok.Data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
  
  private String username;
  private String message;
  
}