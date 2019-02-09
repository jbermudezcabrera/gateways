package com.jbermudezcabrera.gateways.web;

import com.jbermudezcabrera.gateways.model.Device;
import com.jbermudezcabrera.gateways.model.Gateway;
import com.jbermudezcabrera.gateways.services.GatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
class GatewayController {

  private final GatewayService service;

  @Autowired
  public GatewayController(GatewayService service) {
    this.service = service;
  }

  // Gateways management

  @GetMapping("/gateways")
  List<Gateway> getGateways() {
    return service.getAll();
  }

  @GetMapping("/gateways/{id}")
  Gateway getGateway(@PathVariable Long id) {
    return service.getById(id);
  }

  @PostMapping("/gateways")
  Gateway createGateway(@Valid @RequestBody Gateway newGateway) {
    return service.create(newGateway);
  }

  @PutMapping("/gateways/{id}")
  Gateway replaceGateway(@Valid @RequestBody Gateway newGateway, @PathVariable Long id) {
    return service.replace(newGateway, id);
  }

  @DeleteMapping("/gateways/{id}")
  void deleteGateway(@PathVariable Long id) {
    service.delete(id);
  }

  // Gateway's devices management

  @GetMapping("/gateways/{gatewayId}/devices")
  List<Device> getGatewayDevices(@PathVariable Long gatewayId) {
    return service.getDevices(gatewayId);
  }

  @GetMapping("/gateways/{gatewayId}/devices/{id}")
  Device getGatewayDevice(@PathVariable Long gatewayId, @PathVariable Long id) {
    return service.getDevice(gatewayId, id);
  }

  @PostMapping("/gateways/{gatewayId}/devices")
  Device createGatewayDevice(@Valid @RequestBody Device newDevice, @PathVariable long gatewayId) {
    return service.createDevice(gatewayId, newDevice);
  }

  @PutMapping("/gateways/{gatewayId}/devices/{id}")
  Device replaceGatewayDevice(@Valid @RequestBody Device newDevice, @PathVariable Long gatewayId, @PathVariable Long id) {
    return service.replaceDevice(gatewayId, newDevice, id);
  }

  @DeleteMapping("/gateways/{gatewayId}/devices/{id}")
  void deleteGatewayDevice(@PathVariable Long gatewayId, @PathVariable Long id) {
    service.deleteDevice(gatewayId, id);
  }
}