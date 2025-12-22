package com.simple.encurtador_url.Infra.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simple.encurtador_url.Entities.Url;

public interface JpaUrlRepository  extends  JpaRepository<Url, Long> {

  Optional<Url> findByCode(String code);
  
}
