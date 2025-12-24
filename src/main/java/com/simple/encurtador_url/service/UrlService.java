package com.simple.encurtador_url.service;

import java.net.URI;
import java.security.SecureRandom;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.simple.encurtador_url.Entities.Url;
import com.simple.encurtador_url.Infra.persistence.JpaUrlRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UrlService {
  private final JpaUrlRepository repo;
  private static final String BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
  private static final SecureRandom RANDOM = new SecureRandom();
  private static final int HASH_LENGTH = 6;

  private static String generateBase62Hash(int length) {
    StringBuilder sb = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      sb.append(BASE62.charAt(RANDOM.nextInt(BASE62.length())));
    }
    return sb.toString();
  }

  @Transactional
  private Url save(String code, String originalUrl) {
    Url u = Url.builder()
        .code(code)
        .originalUrl(originalUrl)
        .build();
    return repo.save(u);
  }

  private String getUrlByCode(String code) {
    return repo.findByCode(code)
        .map(Url::getOriginalUrl)
        .orElseThrow(() -> new IllegalArgumentException("Cdigo de url nao encotrada"));
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
          String hash;
          do {
            hash = generateBase62Hash(HASH_LENGTH);
          } while (repo.findByCode(hash).isPresent());

          save(hash, url);
          return baseUrl + hash;
        });

  }

  public String redirectUri(String code) {
    return getUrlByCode(code);
  }

}
