package it.besolution.utils;

import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.icon.Icon;

/**
 * Ico-Moon icons from https://icomoon.io/
 * 
 * @author Marcus
 *
 */
@JsModule("frontend://src/customIcons.js")
public enum CustomIcon {

  
    ZOOM_OUT("zoom-out"),
    CHECKBOX_CHECKED("checkbox-checked"),
    CHECKBOX_UNCHECKED("checkbox-unchecked"),
    SETTINGS("settings"),
    EARTH("earth"),
    POWER("power"),
    PLAY("play"),
    DATABASE("database"),
    USERS("users"),
    DISPLAY("display"),
    MENU("menu");

    private String iconString;
    
    private CustomIcon(String icon) {
        this.iconString = icon;
    }

    @SuppressWarnings("deprecation")
	public Icon create() {
        Icon icon = new Icon("icomoon", iconString);
        icon.getStyle().set("vertical-align", "middle");
        return icon;
    }
    
    /**
     * Addes some margin to the right
     * 
     * @param marginRight Amount in px
     * @return
     */
    public Icon create(int marginRight) {
        Icon image = create();
        image.getStyle().set("margin-right", marginRight + "px");
        return image;
    }

    /**
     *  Addes a size and some margin to the right
     * @param marginRight
     * @param size
     * @return
     */
    public Icon create(int marginRight, int size) {
        Icon image = create(marginRight);
        image.setSize(size + "px");
        return image;
    }
}
