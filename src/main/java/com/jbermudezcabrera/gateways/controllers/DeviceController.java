package com.jbermudezcabrera.gateways.controllers;

import com.jbermudezcabrera.gateways.domain.Device;
import com.jbermudezcabrera.gateways.domain.Gateway;
import com.jbermudezcabrera.gateways.repositories.DeviceRepository;
import com.jbermudezcabrera.gateways.repositories.GatewayRepository;
import com.jbermudezcabrera.gateways.services.DeviceNotFoundException;
import com.jbermudezcabrera.gateways.services.GatewayNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
class DeviceController {

  private final DeviceRepository repository;
  private final GatewayRepository gatewayRepository;

  public DeviceController(DeviceRepository repository, GatewayRepository gatewayRepository) {
    this.repository = repository;
    this.gatewayRepository = gatewayRepository;
  }

  @GetMapping("/devices")
  List<Device> all() {
    return repository.findAll();
  }

  @PostMapping("/devices/{gatewayId}")
  Device newDevice(@Valid @RequestBody Device newDevice, @PathVariable long gatewayId) {
    Gateway gateway = gatewayRepository.findById(gatewayId).orElseThrow(() -> new GatewayNotFoundException(gatewayId));
    gateway.addDevice(newDevice);

    gatewayRepository.saveAndFlush(gateway);
    return newDevice;
  }

  @GetMapping("/devices/{id}")
  Device one(@PathVariable Long id) {
    return repository.findById(id)
                     .orElseThrow(() -> new DeviceNotFoundException(id));
  }

  @PutMapping("/devices/{id}")
  Device replaceDevice(@Valid @RequestBody Device newDevice, @PathVariable Long id) {
    return repository.findById(id)
                     .map(device -> {
                       device.setUid(newDevice.getUid());
                       device.setVendor(newDevice.getVendor());
                       device.setStatus(newDevice.getStatus());
                       device.setCreated(newDevice.getCreated());
                       return repository.save(device);
                     })
                     .orElseGet(() -> {
                       newDevice.setId(id);
                       return repository.save(newDevice);
                     });
  }

  @DeleteMapping("/devices/{id}")
  void deleteDevice(@PathVariable Long id) {
    repository.deleteById(id);
  }
}