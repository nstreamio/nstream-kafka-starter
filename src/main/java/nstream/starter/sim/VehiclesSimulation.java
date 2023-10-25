package nstream.starter.sim;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import swim.api.space.Space;
import swim.structure.Record;
import swim.structure.Value;

public final class VehiclesSimulation {

  private VehiclesSimulation() {
  }

  private static final Map<Integer, String> ROUTES = new HashMap<>();

  private static final Map<Integer, List<Location>> LOCATIONS = new HashMap<>();

  public static void seed(Space space) {
    // routes
    loadResourceLines("routes.csv", s -> {
      final String[] split = s.split(",");
      if (split.length > 1) {
        ROUTES.put(Integer.parseInt(split[0]), split[1]);
      }
    });
    // locations
    loadResourceLines("locations.csv", s -> {
      final String[] split = s.split(",");
      if (split.length > 1) {
        final int vehicle = Integer.parseInt(split[0]);
        final List<Location> path;
        final boolean seen;
        if (LOCATIONS.containsKey(vehicle)) {
          path = LOCATIONS.get(vehicle);
          seen = true;
        } else {
          path = new ArrayList<>(500);
          seen = false;
        }
        path.add(new Location(split));
        if (!seen) {
          LOCATIONS.put(vehicle, path);
        }
        space.command("/vehicle/" + vehicle, "unused", Value.extant());
      }
    });
  }

  public static int incrementIdx(int vehicle, int idx) {
    return (idx + 1) % LOCATIONS.get(vehicle).size();
  }

  public static Value locationAsJson(int vehicle, int idx, long timestamp) {
    final Location loc = LOCATIONS.get(vehicle).get(idx);
    return Record.create(9)
        .slot("id", loc.vehicle)
        .slot("routeId", loc.route)
        .slot("dir", loc.inbound ? "inbound" : "outbound")
        .slot("latitude", loc.latitude)
        .slot("longitude", loc.longitude)
        .slot("speed", loc.speed)
        .slot("bearing", loc.bearing.toString())
        .slot("routeName", ROUTES.get(loc.route))
        .slot("timestamp", timestamp);
  }

  private static class Location {

    private final int vehicle;
    private final int route;
    private final boolean inbound;
    private final float latitude;
    private final float longitude;
    private final int speed;
    private final Bearing bearing;

    private Location(String[] split) {
      this.vehicle = Integer.parseInt(split[0]);
      this.route = Integer.parseInt(split[1]);
      this.inbound = "i".equals(split[2]);
      this.latitude = Float.parseFloat(split[3]);
      this.longitude = Float.parseFloat(split[4]);
      this.speed = Integer.parseInt(split[5]);
      this.bearing = Bearing.valueOf(split[6]);
    }

    private enum Bearing {
      N, NE, E, SE, S, SW, W, NW
    }

  }

  private static void loadResourceLines(String resourceName, Consumer<String> onLine) {
    try (InputStream is = loadResource(resourceName);
         BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
      br.lines().forEach(onLine);
    } catch (Exception e) {
      throw new RuntimeException("Failed to load lines from resource " + resourceName, e);
    }
  }

  private static InputStream loadResource(String resourceName) {
    return VehiclesSimulation.class.getClassLoader()
        .getResourceAsStream(resourceName);
  }

}
