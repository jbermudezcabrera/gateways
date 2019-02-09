package com.jbermudezcabrera.gateways.services;

import com.jbermudezcabrera.gateways.model.Device;
import com.jbermudezcabrera.gateways.model.Gateway;

import java.util.List;

public interface GatewayService {

  List<Gateway> getAll();

  Gateway getById(Long id);

  Gateway create(Gateway gateway);

  Gateway replace(Gateway newGateway, Long id);

  void delete(Long id);

  List<Device> getDevices(Long gatewayId);

  Device getDevice(Long gatewayId, Long deviceId);

  Device createDevice(Long gatewayId, Device device);

  Device replaceDevice(Long gatewayId, Device device, Long deviceId);

  void deleteDevice(Long gatewayId, Long deviceId);
}
