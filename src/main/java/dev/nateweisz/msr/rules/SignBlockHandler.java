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
public class SignBlockHandler extends BlockPlacementRule {
    public SignBlockHandler(@NotNull Block block) {
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
        // Convert from Minecraft's -180 to 180 range to 0-360 for calculations
        if (yaw < 0) {
            yaw += 360;
        }

        // Convert yaw to 0-15 rotation value
        // Each rotation step is 22.5 degrees (360 / 16)
        return Math.round(yaw / 22.5f) % 16;
    }
}
