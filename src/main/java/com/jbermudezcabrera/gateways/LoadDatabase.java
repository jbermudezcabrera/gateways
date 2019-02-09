package com.jbermudezcabrera.gateways;

import com.jbermudezcabrera.gateways.domain.Device;
import com.jbermudezcabrera.gateways.domain.Gateway;
import com.jbermudezcabrera.gateways.domain.Status;
import com.jbermudezcabrera.gateways.repositories.GatewayRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(GatewayRepository repository) {
    Gateway gateway = new Gateway();
    gateway.setIpv4Address("127.0.0.1");
    gateway.setSerialNumber("01");
    gateway.setName("Sample Gateway with five devices");

    gateway.addDevice(new Device(1, "Vendor A", Status.ONLINE));
    gateway.addDevice(new Device(2, "Vendor B", Status.OFFLINE));
    gateway.addDevice(new Device(3, "Vendor C", Status.ONLINE));
    gateway.addDevice(new Device(4, "Vendor D", Status.OFFLINE));
    gateway.addDevice(new Device(5, "Vendor E", Status.ONLINE));

    return args -> log.info(String.format("Preloading a sample Gateway: id=[%s]", repository.save(gateway).getId()));
  }
}
