package nstream.starter;

import nstream.adapter.common.patches.MemberPatch;
import swim.structure.Value;

/**
 * A "low-code" {@link MemberPatch} extension with (in a fresh clone of this
 * repository) a corresponding "no-code" {@link
 * nstream.adapter.common.patches.GroupPatch GroupPatch} described in {@code
 * server.recon}.
 * <p>Note how this trait is added to {@link VehicleAgent} instances in a
 * "mixin"-style fashion within {@code server.recon}.
 */
public class PolarityMemberAgent extends MemberPatch {

  @Override
  protected String extractGroupFromEvent(Value event) {
    final float indicator = event.get("latitude").floatValue();
    return indicator < 34.f ? "south" : "north";
  }

}
