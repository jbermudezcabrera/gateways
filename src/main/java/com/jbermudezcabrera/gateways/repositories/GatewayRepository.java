package com.jbermudezcabrera.gateways.repositories;

import com.jbermudezcabrera.gateways.model.Gateway;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GatewayRepository extends JpaRepository<Gateway, Long> {
  boolean existsBySerialNumber(String serialNumber);

  boolean existsBySerialNumberAndIdNot(String serialNumber, long id);
}
