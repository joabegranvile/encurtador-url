package com.simple.encurtador_url.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simple.encurtador_url.Dtos.EncutadorRequest;
import com.simple.encurtador_url.service.UrlService;

@RestController()
@RequestMapping("/api")
public class UrlController {
  private final UrlService service;
  public UrlController(UrlService service) {
    this.service = service;
  }
  @PostMapping("/encurtar")
  public String encurtar(@RequestBody EncutadorRequest request) {
    return service.encurtar(request.url());
  }
}
