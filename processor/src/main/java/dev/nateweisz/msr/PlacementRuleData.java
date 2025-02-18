package dev.nateweisz.msr;

import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.block.rule.BlockPlacementRule;
import net.minestom.server.utils.NamespaceID;

import java.util.List;

public record PlacementRuleData(
        String ruleClass,
        Block block
) {}
