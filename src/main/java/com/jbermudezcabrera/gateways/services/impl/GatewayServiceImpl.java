package com.jbermudezcabrera.gateways.services.impl;

import com.jbermudezcabrera.gateways.domain.Device;
import com.jbermudezcabrera.gateways.domain.Gateway;
import com.jbermudezcabrera.gateways.repositories.DeviceRepository;
import com.jbermudezcabrera.gateways.repositories.GatewayRepository;
import com.jbermudezcabrera.gateways.services.DeviceNotFoundException;
import com.jbermudezcabrera.gateways.services.GatewayNotFoundException;
import com.jbermudezcabrera.gateways.services.GatewayService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GatewayServiceImpl implements GatewayService {

  private final GatewayRepository repository;
  private final DeviceRepository deviceRepository;

  public GatewayServiceImpl(GatewayRepository repository, DeviceRepository deviceRepository) {
    this.repository = repository;
    this.deviceRepository = deviceRepository;
  }

  @Override
  public List<Gateway> getAll() {
    return repository.findAll();
  }

  @Override
  public Gateway getById(Long id) {
    return getGateway(id);
  }

  @Override
  public Gateway create(Gateway gateway) {
    return repository.save(gateway);
  }

  @Override
  public Gateway replace(Gateway newGateway, Long id) {
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

  @Override
  public void delete(Long id) {
    repository.deleteById(id);
  }

  @Override
  public List<Device> getDevices(Long gatewayId) {
    return getGateway(gatewayId).getDevices();
  }

  @Override
  public Device getDevice(Long gatewayId, Long deviceId) {
    checkGatewayExists(gatewayId);

    return deviceRepository.findById(deviceId)
                           .orElseThrow(() -> new DeviceNotFoundException(deviceId));
  }

  @Override
  public Device createDevice(Long gatewayId, Device device) {
    Gateway gateway = getGateway(gatewayId);

    gateway.addDevice(device);
    repository.saveAndFlush(gateway);

    return device;
  }

  @Override
  public Device replaceDevice(Long gatewayId, Device newDevice, Long deviceId) {
    checkGatewayExists(gatewayId);

    return deviceRepository.findById(deviceId)
                           .map(device -> {
                             device.setUid(newDevice.getUid());
                             device.setVendor(newDevice.getVendor());
                             device.setStatus(newDevice.getStatus());
                             device.setCreated(newDevice.getCreated());
                             return deviceRepository.save(device);
                           })
                           .orElseGet(() -> {
                             newDevice.setId(deviceId);
                             return deviceRepository.save(newDevice);
                           });
  }

  @Override
  public void deleteDevice(Long gatewayId, Long deviceId) {
    checkGatewayExists(gatewayId);

    deviceRepository.deleteById(deviceId);
  }

  private void checkGatewayExists(Long gatewayId) {
    boolean missingGateway = !repository.existsById(gatewayId);

    if (missingGateway) {
      throw new GatewayNotFoundException(gatewayId);
    }
  }

  private Gateway getGateway(Long gatewayId) {
    return repository.findById(gatewayId)
                     .orElseThrow(() -> new GatewayNotFoundException(gatewayId));
  }
}
