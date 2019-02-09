package com.jbermudezcabrera.gateways.services.impl;

import com.jbermudezcabrera.gateways.domain.Gateway;
import com.jbermudezcabrera.gateways.repositories.GatewayRepository;
import com.jbermudezcabrera.gateways.services.GatewayNotFoundException;
import com.jbermudezcabrera.gateways.services.GatewayService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GatewayServiceImpl implements GatewayService {

  private final GatewayRepository repository;

  public GatewayServiceImpl(GatewayRepository repository) {
    this.repository = repository;
  }

  @Override
  public List<Gateway> getAll() {
    return repository.findAll();
  }

  @Override
  public Gateway getById(Long id) {
    return repository.findById(id)
                     .orElseThrow(() -> new GatewayNotFoundException(id));
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
}
