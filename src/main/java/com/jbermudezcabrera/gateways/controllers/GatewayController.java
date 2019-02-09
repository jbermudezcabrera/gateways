package com.jbermudezcabrera.gateways.controllers;

import com.jbermudezcabrera.gateways.domain.Gateway;
import com.jbermudezcabrera.gateways.services.GatewayService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
class GatewayController {

  private final GatewayService service;

  public GatewayController(GatewayService service) {
    this.service = service;
  }

  @GetMapping("/gateways")
  List<Gateway> all() {
    return service.getAll();
  }

  @PostMapping("/gateways")
  Gateway newGateway(@Valid @RequestBody Gateway newGateway) {
    return service.create(newGateway);
  }

  @GetMapping("/gateways/{id}")
  Gateway one(@PathVariable Long id) {
    return service.getById(id);
  }

  @PutMapping("/gateways/{id}")
  Gateway replaceGateway(@Valid @RequestBody Gateway newGateway, @PathVariable Long id) {
    return service.replace(newGateway, id);
  }

  @DeleteMapping("/gateways/{id}")
  void deleteGateway(@PathVariable Long id) {
    service.delete(id);
  }
}