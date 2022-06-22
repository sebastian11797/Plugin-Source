package net.runelite.client.plugins.nylonpchighlight;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.Perspective;
import net.runelite.client.plugins.nylonpchighlight.Constants.NyloNamesConstants;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

public class NyloNpcHighlightOverlay extends Overlay {
    private final Client client;
    private final NyloNpcHighlightConfig config;
    private final NyloNpcHighlightPlugin plugin;

    private Graphics2D graphics;

    @Inject
    private NyloNpcHighlightOverlay(Client client, NyloNpcHighlightPlugin plugin, NyloNpcHighlightConfig config) {
        this.client = client;
        this.config = config;
        this.plugin = plugin;
        this.setPosition(OverlayPosition.DYNAMIC);
        this.setLayer(OverlayLayer.ABOVE_SCENE);
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        if (plugin.isInNyloRegion()) {
            for (NPC nylo : plugin.getNylosToBeHighlighted().keySet()) {
                String nyloName = "";
                if (nylo.getName() != null) nyloName = nylo.getName();

                if (!nylo.isDead()) {
                    if (nyloName.contains(NyloNamesConstants.MELEE) && config.HighlightMelee())
                        renderTile(graphics, nylo, Color.LIGHT_GRAY);
                    if (nyloName.contains(NyloNamesConstants.MAGE) && config.HighlightMage())
                        renderTile(graphics, nylo, Color.CYAN);
                    if (nyloName.contains(NyloNamesConstants.RANGE) && config.HighlightRange())
                        renderTile(graphics, nylo, Color.GREEN);
                }
            }
        }
        return null;
    }

    public void renderTile(Graphics2D graphics, NPC npc, Color color) {
        int size = npc.getComposition().getSize();
        Polygon poly = Perspective.getCanvasTileAreaPoly(this.client, npc.getLocalLocation(), size);
        if (poly != null) {
            graphics.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 255));
            graphics.setStroke(new BasicStroke(1));
            graphics.draw(poly);
            graphics.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 0));
            graphics.fill(poly);
        }
    }
}
