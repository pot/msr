package dev.nateweisz.msr.rules;


import dev.nateweisz.msr.MSRHandler;
import dev.nateweisz.msr.utils.DirectionUtils;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.block.rule.BlockPlacementRule;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Handler for the following types, all furnace types, crafting table, enchantment table, brewing stand, anvil,
 * crafting table, grindstone, lectern, smithing table, stonecutter, and barrel.
 */
@MSRHandler(
        predicate = "^(smoker|.*furnace|.*table|brewing_stand|anvil|grindstone|lectern|stonecutter|barrel)$"
)
public class DirectionalBlockRule extends BlockPlacementRule {
    protected DirectionalBlockRule(@NotNull Block block) {
        super(block);
    }

    @Override
    public @Nullable Block blockPlace(@NotNull PlacementState placement) {
        if (placement == null || placement.playerPosition() == null) {
            return null;
        }

        String direction = DirectionUtils.getDirection(placement.playerPosition().yaw());
        if (direction == null) {
            return null;
        }

        return block.withProperty("facing", direction);
    }

}
