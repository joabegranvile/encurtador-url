package com.simple.encurtador_url.controller;

import java.io.IOException;
import java.net.http.HttpHeaders;

import org.springframework.beans.factory.parsing.Location;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simple.encurtador_url.Dtos.EncutadorRequest;
import com.simple.encurtador_url.service.UrlService;

import jakarta.servlet.http.HttpServletResponse;

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

  @GetMapping("/{code}")
  public void redirect(@PathVariable String code, HttpServletResponse response) throws IOException {
    String url = service.redirectUri(code);
    response.setStatus(HttpServletResponse.SC_FOUND);
    response.setHeader("Location", url);
  }
}
