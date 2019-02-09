package com.jbermudezcabrera.gateways.controllers;

import com.jbermudezcabrera.gateways.domain.Gateway;
import com.jbermudezcabrera.gateways.repositories.GatewayRepository;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
class GatewayController {

  private final GatewayRepository repository;

  public GatewayController(GatewayRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/gateways")
  List<Gateway> all() {
    return repository.findAll();
  }

  @PostMapping("/gateways")
  Gateway newGateway(@Valid @RequestBody Gateway newGateway) {
    return repository.save(newGateway);
  }

  @GetMapping("/gateways/{id}")
  Gateway one(@PathVariable Long id) {
    return repository.findById(id)
                     .orElseThrow(() -> new GatewayNotFoundException(id));
  }

  @PutMapping("/gateways/{id}")
  Gateway replaceGateway(@Valid @RequestBody Gateway newGateway, @PathVariable Long id) {
    return repository.findById(id)
                     .map(gateway -> {
                       gateway.setName(newGateway.getName());
                       gateway.setSerialNumber(newGateway.getSerialNumber());
                       gateway.setIpv4Address(newGateway.getIpv4Address());
                       return repository.save(gateway);
                     })
                     .orElseGet(() -> {
                       newGateway.setId(id);
                       return repository.save(newGateway);
                     });
  }

  @DeleteMapping("/gateways/{id}")
  void deleteGateway(@PathVariable Long id) {
    repository.deleteById(id);
  }
}