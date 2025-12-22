package com.simple.encurtador_url.service;

import java.math.BigInteger;
import java.net.URI;
import java.security.SecureRandom;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.simple.encurtador_url.Entities.Url;
import com.simple.encurtador_url.Infra.persistence.JpaUrlRepository;


@Service
public class UrlService {
  private final JpaUrlRepository repo;

  public UrlService(JpaUrlRepository repo) {
    this.repo = repo;

  }

  private Url save(String code, String Url) {
    Url u = new Url();
    u.setCode(code);
    u.setOriginalUrl(Url);
    return repo.save(u);
  }

  private String getUrlByCode(String code) {
    return repo.findByCode(code)
        .map(Url::getOriginalUrl)
        .orElse("");
  }

  private Optional<Url> getByOriginalUrl(String url) {
    return repo.findByOriginalUrl(url);
  }

  private static boolean isValidHttpUrl(String url) {
    try {
      URI uri = URI.create(url);
      String scheme = uri.getScheme();
      return scheme != null && (scheme.equals("http") || scheme.equals("https"));
    } catch (IllegalArgumentException e) {
      return false;
    }
  }

  public String encurtar(String url) {
    if (!isValidHttpUrl(url))
      throw new IllegalArgumentException("Url inválida");

    String baseUrl = "http://localhost:8080/";
    return getByOriginalUrl(url)
        .map(u -> baseUrl + u.getCode())
        .orElseGet(() -> {
          String hash = generateHash(14);
          save(hash, url);
          return baseUrl + hash;
        });

  }

  private String generateHash(int byteLength) {
    SecureRandom secureRandom = new SecureRandom();
    byte[] token = new byte[byteLength];
    secureRandom.nextBytes(token);
    return new BigInteger(1, token).toString(16);
  }

  public String redirectUri(String code) {
    return getUrlByCode(code);
  }

}
