package dev.nateweisz.msr.rules;

import dev.nateweisz.msr.MSRHandler;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.block.BlockFace;
import net.minestom.server.instance.block.rule.BlockPlacementRule;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@MSRHandler(
        predicate = ".*sign$"
)
public class SignBlockRule extends BlockPlacementRule {
    protected SignBlockRule(@NotNull Block block) {
        super(block);
    }

    public @Nullable Block blockPlace(@NotNull PlacementState placement) {
        Block block;
        if (placement.blockFace() == BlockFace.TOP) {
            // place the sign directionally based on the player's position
            int rotation = calculateRotation(placement.playerPosition().yaw());
            block = this.block.withProperty("rotation", String.valueOf(rotation));
        }

        // TODO: place on the wall, should we make dif handlers for the dif sign types.
        block = this.block;


        return block;
    }

    private static int calculateRotation(float yaw) {
        yaw += 180; // convert it to all positive yaw values

        return Math.round(yaw / 22.5f) % 16;
    }
}
