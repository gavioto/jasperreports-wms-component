package com.sourcepole.ireport.components.wmsmap;

import com.jaspersoft.ireport.designer.palette.actions.CreateReportElementAction;
import com.jaspersoft.ireport.designer.utils.Misc;
import com.sourcepole.jasperreports.wmsmap.StandardWmsMapComponent;
import net.sf.jasperreports.engine.component.ComponentKey;
import net.sf.jasperreports.engine.design.JRDesignComponentElement;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JasperDesign;

/**
 * WMS Map Action.
 */
public class CreateWmsMapAction extends CreateReportElementAction {

  @Override
  public JRDesignElement createReportElement(final JasperDesign jd) {
    JRDesignComponentElement component = new JRDesignComponentElement();
    StandardWmsMapComponent mapComponent = new StandardWmsMapComponent();
    mapComponent.setWmsServiceUrl("http://");
    mapComponent.setWmsVersion("1.1.1");
    mapComponent.setSrs("");
    mapComponent.setTransparent(Boolean.FALSE);
    mapComponent.setBBoxExpression(Misc.createExpression(null, "\"0.0,0.0,0.0,0.0\""));
    mapComponent.setLayersExpression(Misc.createExpression(null, "\"layer1,layer2\""));
    mapComponent.setStylesExpression(Misc.createExpression(null, "\"style1,style2\""));

    component.setComponent(mapComponent);
    ComponentKey key = new ComponentKey("http://sourcepole.com/jasperreports/components", "wmp", "wmsmap");
        component.setComponentKey(key);
    component.setWidth(200);
    component.setHeight(200);
    return component;
  }
}