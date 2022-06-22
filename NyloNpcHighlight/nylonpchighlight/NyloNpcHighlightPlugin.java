package net.runelite.client.plugins.nylonpchighlight;

import com.google.inject.Provides;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.SkillIconManager;
import net.runelite.client.game.npcoverlay.NpcOverlayService;
import net.runelite.client.input.MouseManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.nylonpchighlight.Constants.NyloNamesConstants;
import net.runelite.client.plugins.nylonpchighlight.NyloSelectionOverlay.NyloSelectionBox;
import net.runelite.client.plugins.nylonpchighlight.NyloSelectionOverlay.NyloSelectionManager;
import net.runelite.client.plugins.nylonpchighlight.Util.SelectionBoxListener;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.ui.overlay.components.InfoBoxComponent;
import javax.inject.Inject;
import java.util.*;

@Slf4j
@PluginDescriptor(
        name = "Nylo Npc Hightlight",
        description = "Highlight Nylocas based on role",
        tags = {"tob", "theatre", "nylo", "nylocas"}
)

public class NyloNpcHighlightPlugin extends Plugin {
    @Inject
    private Client client;
    @Inject
    private NyloNpcHighlightConfig config;
    @Inject
    private NyloNpcHighlightOverlay nyloNpcHighlightOverlay;
    @Inject
    private OverlayManager overlayManager;
    @Inject
    private NpcOverlayService npcOverlayService;
    @Inject
    private SkillIconManager skillIconManager;
    @Inject
    private MouseManager mouseManager;
    @Inject
    private SelectionBoxListener selectionBoxListener;

    @Provides
    NyloNpcHighlightConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(NyloNpcHighlightConfig.class);
    }

    @Getter
    private final HashMap<NPC, String> NylosToBeHighlighted = new HashMap<>();
    @Getter
    private NyloSelectionManager nyloSelectionManager;

    @Override
    protected void startUp() throws Exception {
        initSelectionManager();
        mouseManager.registerMouseListener(selectionBoxListener);
        overlayManager.add(nyloNpcHighlightOverlay);
        overlayManager.add(nyloSelectionManager);
    }

    @Override
    protected void shutDown() throws Exception {
        NylosToBeHighlighted.clear();
        overlayManager.remove(nyloNpcHighlightOverlay);
        overlayManager.remove(nyloSelectionManager);
    }

    @Subscribe
    public void onNpcSpawned(NpcSpawned npcSpawned)
    {
        if (!isInNyloRegion()) return;

        final NPC npc = npcSpawned.getNpc();
        final String npcName = npc.getName();

        if (npcName == null) return;

        addNylosToHighlightList(npc, npcName);
    }

    @Subscribe
    public void onNpcDespawned(NpcDespawned npcDespawned)
    {
        final NPC npc = npcDespawned.getNpc();
        NylosToBeHighlighted.remove(npc);
    }

    private void initSelectionManager() {
        InfoBoxComponent box = new InfoBoxComponent();
        box.setImage(skillIconManager.getSkillImage(Skill.ATTACK));
        NyloSelectionBox nyloMeleeOverlay = new NyloSelectionBox(box);
        nyloMeleeOverlay.setSelected(config.HighlightMelee());

        box = new InfoBoxComponent();
        box.setImage(skillIconManager.getSkillImage(Skill.MAGIC));
        NyloSelectionBox nyloMageOverlay = new NyloSelectionBox(box);
        nyloMageOverlay.setSelected(config.HighlightMage());

        box = new InfoBoxComponent();
        box.setImage(skillIconManager.getSkillImage(Skill.RANGED));
        NyloSelectionBox nyloRangeOverlay = new NyloSelectionBox(box);
        nyloRangeOverlay.setSelected(config.HighlightRange());

        nyloSelectionManager = new NyloSelectionManager(nyloMeleeOverlay, nyloMageOverlay, nyloRangeOverlay, client);
        nyloSelectionManager.setHidden(!isInNyloRegion());
    }

    private void addNylosToHighlightList(NPC npc, String npcName) {
        if (npcName.contains("Nylocas")) NylosToBeHighlighted.put(npc, npcName);
    }

    boolean isInNyloRegion() {
        return this.client.isInInstancedRegion() && this.client.getMapRegions().length > 0 && this.client.getMapRegions()[0] == 13122;
    }
}
