package com.jbermudezcabrera.gateways.repositories;

import com.jbermudezcabrera.gateways.domain.Gateway;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GatewayRepository extends JpaRepository<Gateway, Long> {
}
