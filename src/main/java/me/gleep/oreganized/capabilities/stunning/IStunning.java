package me.gleep.oreganized.capabilities.stunning;

import me.gleep.oreganized.capabilities.engravedblockscap.EngravedBlocks.Face;
import net.minecraft.core.BlockPos;

import java.util.HashMap;
import java.util.HashSet;

public interface IStunning {
    void setPreviousPos(BlockPos pos);
    BlockPos getPreviousPos();
    void setRemainingStunTime(int time);
    int getRemainingStunTime();
}
