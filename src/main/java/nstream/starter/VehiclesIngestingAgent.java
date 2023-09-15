package nstream.starter;

import nstream.adapter.kafka.KafkaIngestingPatch;
import swim.api.SwimLane;
import swim.api.lane.CommandLane;

/**
 * A skeletal {@link KafkaIngestingPatch} extension.
 * <p>"No-code" type ingesting agents configure {@code KafkaIngestingPatch} in
 * {@code server.recon}. "Low-code" variations first create a base class that
 * extends {@code KafkaIngestingPatch}, then utilize that class in {@code
 * server.recon}.
 * <p>This class is unused in a fresh clone of the repository. If you wish to
 * modify and/or use any custom logic here, ensure server.recon points to this
 * class instead of {@code KafkaIngestingPatch}.
 */
public class VehiclesIngestingAgent extends KafkaIngestingPatch<Integer, String> {

  @SwimLane("triggerReception")
  CommandLane<String> triggerReception = this.<String>commandLane()
      .onCommand(s -> {
        if ("start".equals(s)) {
          stageReception();
        }
      });

  @Override
  public void didStart() {
    System.out.println(nodeUri() + ": didStart");
  }

}
