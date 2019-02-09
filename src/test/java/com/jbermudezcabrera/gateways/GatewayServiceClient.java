package com.jbermudezcabrera.gateways;

import com.jbermudezcabrera.gateways.domain.Device;
import com.jbermudezcabrera.gateways.domain.Gateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@SuppressWarnings("SameParameterValue")
public class GatewayServiceClient {

  private final RestTemplate restTemplate;
  private int port;

  @Autowired
  public GatewayServiceClient(RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate = restTemplateBuilder.build();
  }

  Gateway getGateway(Long id) {
    return restTemplate.getForObject(createURL("/gateways/{id}"), Gateway.class, id);
  }

  Gateway createGateway(Gateway gateway) {
    ResponseEntity<Gateway> response = restTemplate.postForEntity(createURL("gateways/"), gateway, Gateway.class);
    return response.getBody();
  }

  Device createDevice(long gatewayId, Device device) {
    ResponseEntity<Device> response = restTemplate.postForEntity(createURL("gateways/{id}/devices"),
                                                                 device,
                                                                 Device.class,
                                                                 gatewayId);
    return response.getBody();
  }

  void removeDevice(long gatewayId, long deviceId) {
    restTemplate.delete(createURL("/gateways/{gatewayId}/devices/{id}"), gatewayId, deviceId);
  }

  void setPort(int port) {
    this.port = port;
  }

  private String createURL(String uri) {
    return "http://localhost:" + port + uri;
  }
}
