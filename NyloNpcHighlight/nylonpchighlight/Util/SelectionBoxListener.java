// McNeils Nylo Selection
package net.runelite.client.plugins.nylonpchighlight.Util;

import com.google.inject.Provides;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.input.MouseAdapter;
import net.runelite.client.plugins.nylonpchighlight.NyloNpcHighlightConfig;
import net.runelite.client.plugins.nylonpchighlight.NyloNpcHighlightPlugin;

import javax.inject.Inject;
import java.awt.event.MouseEvent;

public class SelectionBoxListener extends MouseAdapter {
    @Inject
    private NyloNpcHighlightConfig config;

    @Inject
    private NyloNpcHighlightPlugin plugin;

    @Provides
    NyloNpcHighlightConfig getConfig(ConfigManager configManager) {
        return (NyloNpcHighlightConfig)configManager.getConfig(NyloNpcHighlightConfig.class);
    }

    @Override
    public MouseEvent mouseReleased(MouseEvent event) {
        if (plugin.getNyloSelectionManager().isInNyloRegion()) {
            return event;
        }

        if(plugin.getNyloSelectionManager().getBounds().contains(event.getPoint())) {
            event.consume();
            return event;
        }
        return event;
    }

    @Override
    public MouseEvent mouseClicked(MouseEvent event)
    {
        if (!plugin.getNyloSelectionManager().isInNyloRegion())
        {
            return event;
        }
        if (event.getButton() == MouseEvent.BUTTON1 && plugin.getNyloSelectionManager().getBounds().contains(event.getPoint()))
        {
            if (plugin.getNyloSelectionManager().getMeleeBounds().contains(event.getPoint()))
            {
                config.setHighlightMelee(!config.HighlightMelee());
                plugin.getNyloSelectionManager().getMelee().setSelected(config.HighlightMelee());
            }
            else if (plugin.getNyloSelectionManager().getRangeBounds().contains(event.getPoint()))
            {
                config.setHighlightRange(!config.HighlightRange());
                plugin.getNyloSelectionManager().getRange().setSelected(config.HighlightRange());
            }
            else if (plugin.getNyloSelectionManager().getMageBounds().contains(event.getPoint()))
            {
                config.setHighlightMage(!config.HighlightMage());
                plugin.getNyloSelectionManager().getMage().setSelected(config.HighlightMage());
            }
            event.consume();
        }
        return event;
    }

    @Override
    public MouseEvent mouseMoved(MouseEvent event)
    {
        if (!plugin.getNyloSelectionManager().isInNyloRegion())
        {
            return event;
        }

        plugin.getNyloSelectionManager().getMelee().setHovered(false);
        plugin.getNyloSelectionManager().getRange().setHovered(false);
        plugin.getNyloSelectionManager().getMage().setHovered(false);

        if (plugin.getNyloSelectionManager().getBounds().contains(event.getPoint()))
        {
            if (plugin.getNyloSelectionManager().getMeleeBounds().contains(event.getPoint()))
            {
                plugin.getNyloSelectionManager().getMelee().setHovered(true);
            }
            else if (plugin.getNyloSelectionManager().getRangeBounds().contains(event.getPoint()))
            {
                plugin.getNyloSelectionManager().getRange().setHovered(true);
            }
            else if (plugin.getNyloSelectionManager().getMageBounds().contains(event.getPoint()))
            {
                plugin.getNyloSelectionManager().getMage().setHovered(true);
            }
        }
        return event;
    }
}
