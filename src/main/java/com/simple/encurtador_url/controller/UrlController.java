package com.simple.encurtador_url.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class UrlController {
  @PostMapping("/encurtar")
  public String encurtar(@RequestBody String url) {
    return null;
  }
}
