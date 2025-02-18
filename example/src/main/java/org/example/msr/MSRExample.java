package org.example.msr;

import dev.nateweisz.msr.MSRPlacementRules;
import net.minestom.server.MinecraftServer;
import net.minestom.server.instance.block.rule.BlockPlacementRule;

public class MSRExample {
    public static void main(String[] args) {
        MinecraftServer server = MinecraftServer.init();

        MSRPlacementRules.registerAll();

        MSRPlacementRules.register(
                new BlockPlacementRule[]{
                        MSRPlacementRules.BAMBOO_SIGN,
                        MSRPlacementRules.WARPED_HANGING_SIGN
                }
        );

        server.start("127.0.0.1", 25565);
    }
}
