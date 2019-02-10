package com.jbermudezcabrera.gateways.services.impl;

import com.jbermudezcabrera.gateways.model.Device;
import com.jbermudezcabrera.gateways.model.Gateway;
import com.jbermudezcabrera.gateways.repositories.DeviceRepository;
import com.jbermudezcabrera.gateways.repositories.GatewayRepository;
import com.jbermudezcabrera.gateways.services.DeviceNotFoundException;
import com.jbermudezcabrera.gateways.services.GatewayNotFoundException;
import com.jbermudezcabrera.gateways.services.GatewayService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    checkUniqueSerialNumber(gateway.getSerialNumber());
    checkDevicesLimit(gateway);
    return repository.save(gateway);
  }

  @Override
  public Gateway replace(Gateway newGateway, Long id) {
    checkUniqueSerialNumber(newGateway.getSerialNumber(), id);

    return repository.findById(id)
                     .map(gateway -> {
                       gateway.setName(newGateway.getName());
                       gateway.setSerialNumber(newGateway.getSerialNumber());
                       gateway.setIpv4Address(newGateway.getIpv4Address());
                       return repository.save(gateway);
                     })
                     .orElseGet(() -> {
                       checkDevicesLimit(newGateway);

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
    Gateway gateway = getGateway(gatewayId);

    return gateway.getDevices()
                  .stream()
                  .filter(device -> device.getId().equals(deviceId))
                  .findFirst()
                  .orElseThrow(() -> new DeviceNotFoundException(deviceId, gatewayId));
  }

  @Override
  public Device createDevice(Long gatewayId, Device device) {
    Gateway gateway = getGateway(gatewayId);

    checkDevicesLimitNotReached(gateway);

    device.setGateway(gateway);
    return deviceRepository.save(device);
  }

  @Override
  public Device replaceDevice(Long gatewayId, Device newDevice, Long deviceId) {
    Gateway gateway = repository.findById(gatewayId).orElseThrow(() -> new GatewayNotFoundException(gatewayId));

    return deviceRepository.findById(deviceId)
                           .map(device -> {
                             device.setUid(newDevice.getUid());
                             device.setVendor(newDevice.getVendor());
                             device.setStatus(newDevice.getStatus());
                             device.setCreated(newDevice.getCreated());
                             return deviceRepository.save(device);
                           })
                           .orElseGet(() -> {
                             checkDevicesLimitNotReached(gateway);

                             newDevice.setGateway(gateway);
                             newDevice.setId(deviceId);

                             return deviceRepository.save(newDevice);
                           });
  }

  @Override
  public void deleteDevice(Long gatewayId, Long deviceId) {
    Gateway gateway = getGateway(gatewayId);

    boolean removed = false;

    for (Device device : gateway.getDevices().toArray(new Device[0])) {
      if (device.getId().equals(deviceId)) {
        gateway.removeDevice(device);
        removed = true;
        break;
      }
    }

    if (removed) {
      repository.save(gateway);
    } else {
      throw new DeviceNotFoundException(deviceId, gatewayId);
    }
  }

  private Gateway getGateway(Long gatewayId) {
    return repository.findById(gatewayId)
                     .orElseThrow(() -> new GatewayNotFoundException(gatewayId));
  }

  private void checkUniqueSerialNumber(String serialNumber) {
    if (!StringUtils.isEmpty(serialNumber) && repository.existsBySerialNumber(serialNumber)) {
      throw new IllegalArgumentException("Serial number must be unique");
    }
  }

  private void checkUniqueSerialNumber(String serialNumber, long excludedId) {
    if (!StringUtils.isEmpty(serialNumber) && repository.existsBySerialNumberAndIdNot(serialNumber, excludedId)) {
      throw new IllegalArgumentException("Serial number must be unique");
    }
  }

  private void checkDevicesLimit(Gateway gateway) {
    if (gateway.getDevices().size() <= 10) {
      return;
    }

    throw new IllegalArgumentException("Only 10 devices are allowed for a gateway.");
  }

  private void checkDevicesLimitNotReached(Gateway gateway) {
    if (gateway.getDevices().size() < 10) {
      return;
    }

    throw new IllegalArgumentException("Only 10 devices are allowed for a gateway.");
  }
}
