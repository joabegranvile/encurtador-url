package com.simple.encurtador_url.service;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.springframework.stereotype.Service;

import com.simple.encurtador_url.Entities.Url;
import com.simple.encurtador_url.Infra.persistence.JpaUrlRepository;


@Service
public class UrlService {
  private final JpaUrlRepository repo;

  public UrlService(JpaUrlRepository repo) {
    this.repo = repo;

  }

  public Url save(String code, String Url) {
    Url u = new Url();
    u.setCode(code);
    u.setOriginalUrl(Url);
    return repo.save(u);
  }
  public String encurtar(String url) {
    int byteLength = 14;
    String baseUrl = "https://granvile/";
    String hash = generateHash(byteLength);
    save(hash, url);
    String finalUrl = baseUrl + hash;
    return finalUrl;
  }

  private String generateHash(int byteLength) {
    SecureRandom secureRandom = new SecureRandom();
    byte[] token = new byte[byteLength];
    secureRandom.nextBytes(token);
    return new BigInteger(1, token).toString(16);
  }
}
