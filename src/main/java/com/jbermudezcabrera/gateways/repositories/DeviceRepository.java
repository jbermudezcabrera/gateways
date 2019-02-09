package com.jbermudezcabrera.gateways.repositories;

import com.jbermudezcabrera.gateways.domain.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
}
