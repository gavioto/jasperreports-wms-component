/*
 * JasperReports/iReport WMS Component
 * 
 * Copyright (C) 2013 Sourcepole AG
 * 
 * JasperReports/iReport WMS Component is free software: you can redistribute
 * it and/or modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * JasperReports/iReport WMS Component is distributed in the hope that it will
 * be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with JasperReports/iReport WMS Component.
 * If not, see <http://www.gnu.org/licenses/>.
 */
package com.sourcepole.jasperreports.wmsmap;

import java.io.IOException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRGenericPrintElement;
import net.sf.jasperreports.engine.JRPrintImage;
import net.sf.jasperreports.engine.export.JRExporterGridCell;
import net.sf.jasperreports.engine.export.ooxml.GenericElementXlsxHandler;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporterContext;

/**
 * WMS map element handler to produce XLSX report output.
 */
public class WmsMapElementXlsxHandler implements GenericElementXlsxHandler {
  private static final WmsMapElementXlsxHandler INSTANCE = new WmsMapElementXlsxHandler();

  public static WmsMapElementXlsxHandler getInstance() {
    return INSTANCE;
  }

  @Override
  public void exportElement(JRXlsxExporterContext exporterContext,
      JRGenericPrintElement element, JRExporterGridCell gridCell, int colIndex,
      int rowIndex) {
    try {
      JRXlsxExporter exporter = (JRXlsxExporter) exporterContext.getExporter();
      JRPrintImage image = getImage(exporterContext, element);
      exporter.exportImage(image, gridCell,
          colIndex, rowIndex, 0, 0, null);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean toExport(JRGenericPrintElement element) {
    return true;
  }

  @Override
  public JRPrintImage getImage(JRXlsxExporterContext exporterContext,
      JRGenericPrintElement element) throws JRException {
    try {
      return WmsMapElementImageProvider
          .getImage(exporterContext.getJasperReportsContext(), element);
    } catch (IOException e) {
      throw new JRException(e);
    }
  }
}
