// McNeils Nylo Selection
package net.runelite.client.plugins.nylonpchighlight.NyloSelectionOverlay;

import lombok.Getter;
import lombok.Setter;
import net.runelite.api.Client;
import net.runelite.client.plugins.nylonpchighlight.NyloSelectionOverlay.NyloSelectionBox;
import net.runelite.client.ui.overlay.Overlay;
import java.awt.*;

public class NyloSelectionManager extends Overlay {
    @Getter
    private final NyloSelectionBox melee;

    @Getter
    private final NyloSelectionBox mage;

    @Getter
    private final NyloSelectionBox range;

    @Getter
    private final Client client;

    @Getter
    @Setter
    private boolean isHidden = true;

    @Getter
    private Rectangle meleeBounds = new Rectangle();

    @Getter
    private Rectangle rangeBounds = new Rectangle();

    @Getter
    private Rectangle mageBounds = new Rectangle();

    public NyloSelectionManager(NyloSelectionBox melee, NyloSelectionBox mage, NyloSelectionBox range, Client client)
    {
        this.mage = mage;
        this.melee = melee;
        this.range = range;
        this.client = client;
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        if (!this.isInNyloRegion())
        {
            return null;
        }

        Dimension meleeD = melee.render(graphics);
        graphics.translate(meleeD.width + 1, 0);
        Dimension rangeD = range.render(graphics);
        graphics.translate(rangeD.width + 1, 0);
        Dimension mageD = mage.render(graphics);
        graphics.translate(-meleeD.width - rangeD.width - 2, 0);

        meleeBounds = new Rectangle(getBounds().getLocation(), meleeD);
        rangeBounds = new Rectangle(new Point(getBounds().getLocation().x + meleeD.width + 1, getBounds().y), rangeD);
        mageBounds = new Rectangle(new Point(getBounds().getLocation().x + meleeD.width + 1 + rangeD.width + 1, getBounds().y), mageD);

        return new Dimension(meleeD.width + rangeD.width + mageD.width, Math.max(Math.max(meleeD.height, rangeD.height), mageD.height));
    }

    public boolean isInNyloRegion() {
        return this.client.isInInstancedRegion() && this.client.getMapRegions().length > 0 && this.client.getMapRegions()[0] == 13122;
    }
}
