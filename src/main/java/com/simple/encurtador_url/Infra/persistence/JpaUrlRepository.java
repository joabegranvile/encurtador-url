package com.simple.encurtador_url.Infra.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simple.encurtador_url.Entities.Url;

@Repository
public interface JpaUrlRepository extends JpaRepository<Url, Long> {

  Optional<Url> findByCode(String code);

  Optional<Url> findByOriginalUrl(String originalUrl);

}
