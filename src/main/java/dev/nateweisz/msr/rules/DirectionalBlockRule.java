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
        return block.withProperty("facing", DirectionUtils.getDirection(placement.playerPosition().yaw()));
    }

}
