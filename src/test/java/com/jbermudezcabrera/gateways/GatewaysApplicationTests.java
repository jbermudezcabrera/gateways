package com.jbermudezcabrera.gateways;

import com.jbermudezcabrera.gateways.model.Device;
import com.jbermudezcabrera.gateways.model.Gateway;
import com.jbermudezcabrera.gateways.model.Status;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GatewaysApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GatewaysApplicationTests {
  @LocalServerPort
  private int port;

  @Autowired
  private GatewayServiceClient client;

  @Before
  public void setUp() {
    client.setPort(port);
  }

  @Test
  public void whenCallingGetGateway_thenClientMakeCorrectCall() {
    // given sample gateway created during context initialization

    // when
    Gateway gateway = client.getGateway(1L);

    // then
    Assert.assertEquals("Expected 'Sample Gateway with five devices'",
                        "Sample Gateway with five devices",
                        gateway.getName());
  }

  @Test
  public void whenCallingCreateGateway_thenClientMakeCorrectCall() {
    // given
    Gateway newGateway = new Gateway();
    newGateway.setSerialNumber("02");
    newGateway.setName("Sample 2");

    // when
    Gateway gateway = client.createGateway(newGateway);

    // then
    Assert.assertNotNull(gateway);
    Assert.assertNotNull(gateway.getId());
    Assert.assertEquals(gateway.getName(), "Sample 2");
  }

  @Test
  public void whenCallingCreateDevice_thenDeviceIsAddedToGateway() {
    // given sample gateway created during context initialization
    int devicesCount = client.getGateway(1L).getDevices().size();

    // when
    Device device = client.createDevice(1L, new Device(99, "Me", Status.ONLINE));

    // then
    Assert.assertNotNull(device);
    Assert.assertNotNull(device.getId());
    Assert.assertEquals(device.getUid(), 99);
    Assert.assertEquals(devicesCount + 1, client.getGateway(1L).getDevices().size());
  }

  @Test
  public void whenCallingDeleteDevice_thenDeviceIsRemovedFromGateway() {
    // given sample gateway created during context initialization
    int devicesCount = client.getGateway(1L).getDevices().size();

    // when
    client.removeDevice(1L, 3L);

    // then
    Assert.assertEquals(devicesCount - 1, client.getGateway(1L).getDevices().size());
  }
}

