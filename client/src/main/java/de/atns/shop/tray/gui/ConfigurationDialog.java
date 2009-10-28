package de.atns.shop.tray.gui;

import de.atns.shop.tray.data.ShopConfiguration;

/**
 * Created by IntelliJ IDEA.
 * User: tbaum
 * Date: 27.05.2008
 * Time: 12:07:54
 */
public interface ConfigurationDialog {

    boolean isDataComplete(ShopConfiguration shopConfiguration);

    boolean showDialog(ShopConfiguration shopConfiguration);
}
