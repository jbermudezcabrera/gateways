package com.jbermudezcabrera.gateways.services;

import com.jbermudezcabrera.gateways.domain.Gateway;

import java.util.List;

public interface GatewayService {

  List<Gateway> getAll();

  Gateway getById(Long id);

  Gateway create(Gateway gateway);

  Gateway replace(Gateway newGateway, Long id);

  void delete(Long id);
}
