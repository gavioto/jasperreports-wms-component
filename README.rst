================================================
Web Map Service map extension for Jasper Reports
================================================

`JasperReports`_ is the world's most popular open source reporting engine. It is able to use data coming from any kind of data source and produce pixel-perfect documents that can be viewed, printed or exported in a variety of document formats including HTML, PDF, Excel, OpenOffice and Word. Report templates can be created with the `iReport Designer`_ on the desktop.

This extension allows to embedding maps served with the standardized `Web Map Service (WMS) protocol`_.


JasperReports Extension for WMS Maps
====================================

The extension requires Jasperreports/iReport version 5.0.x. The extension consists of 2 artifacts:

- A iReport Designer Plugin that adds the WMS Map report element to the iReport component palette as well as editing WMS properties
- A JasperReports extension that provides rendering capabilities for WMS map report elements

The extension supports all JasperReports output formats (HTML, PDF, etc.).


Install the iReport Plugin
--------------------------

Download ``com-sourcepole-ireport-wms-*.nbm`` from https://github.com/sourcepole/jasperreports-wms-component/releases

In iReport Designer:

- choose 'Tools' -> 'Plugins' from the menu
- to uninstall a previous version of the plugin:

  - select tab 'Installed'
  - select the Entry in the list
  - click 'Uninstall' (requires restart of iReport)

- to install a new version of the plugin:

  - select tab 'Downloaded'
  - click 'Add Plugins...'
  - choose the plugin package (``com-sourcepole-ireport-wms-*.nbm``) from the file system
  - click 'Install'
  - in the dialog that appears, click 'next'
  - check 'Accept License' and click 'install'
  - a warning appears that the plugin is not signed, click 'Continue'
  - if the plugin could be installed, click 'Finish'
  - in the plugin dialog, click 'close'



Creating reports with WMS map elements
======================================

If the iReport plugin has been installed properly in iReport Designer, an additional component WMS Map should be available in the components Palette.
Simply drag-and-drop the palette element to add new WMS map elements. If the element is selected, the Properties Inspector provides WMS specific input fields (Service URL, Bounding Box, etc.).

Using report parameters WMS map element
---------------------------------------

Like with any report element expressions may be used for some of the WMS map parameters:

- Bounding Box
- Layers
- Styles
- URL Parameters (extra parameters appended to the WMS request url)

To use a report parameter, e.g. for BBOX:

1. Create a new report parameter, e.g. MAP_BBOX (default value expression ``"634849.96085766,244281.95484911,635310.33560906,244655.89909163"``)
2. Reference this report parameter in the map element's bounding box property using the JasperReports expression syntax (``$P{MAP_BBOX}``)

.. image:: src/doc/image/wms_report_parameter.png

Example:

- WMS URL: ``http://wms.qgiscloud.com/olten/Solarkataster``
- WMS Version: ``1.3``
- SRS/CRS: ``EPSG:21781``
- Bbox: ``$P{MAP_BBOX}``
- Layers: ``Hintergrund,Kataster``
- Styles:
- Image format: ``image/png``
- Transparent: ``FALSE``
- URL Parameters: ``"DPI=150"``

When previewing the report in iReport, a prompt is shown to enter the MAP_BBOX parameter (unless the ``Use as a prompt`` option for the report parameter has been unchecked). Sample BBOX: ``634849.96085766,244281.95484911,635310.33560906,244655.89909163``


WMS protocol
------------

The example above generates the following WMS GetMap request:

http://wms.qgiscloud.com/olten/Solarkataster?SERVICE=WMS&REQUEST=GetMap&VERSION=1.3.0&BBOX=634849.96085766,244281.95484911,635310.33560906,244655.89909163&LAYERS=Hintergrund,Kataster&STYLE=,&CRS=EPSG%3A21781&FORMAT=image/png&HEIGHT=314&WIDTH=338&TRANSPARENT=FALSE&DPI=150

Remarks:

- In WMS version 1.3 the parameter "SRS" has been renamed to "CRS".
- The DPI parameter is not standardized and vendor dependent. QGIS Server uses "DPI", UMN Mapserver "MAP_RESOLUTION" and GeoServer "FORMAT_OPTIONS=dpi:96"


JasperReport server
===================

Install the extension in JasperReport server
--------------------------------------------

Download ``jasperreports-wms-component-*.jar`` from https://github.com/sourcepole/jasperreports-wms-component/releases

Assuming a standard installation of JasperReports server on Apache Tomcat, just copy the extension JAR to the `lib` directory of the web application:

``cp jasperreports-wms-component-*.jar $TOMCAT_HOME/webapps/jasperserver/WEB-INF/lib``



Development
===========

Building from source
--------------------

Source code is available on https://github.com/sourcepole/jasperreports-wms-component.


Prerequisites to build the extension from source:

- JDK 6 or higher
- NetBeans Platform (to build the iReport Plugin) and iReport installation
- Apache Maven 3.0.x (http://maven.apache.org, to build the JasperReports extension)

To build the JasperReports extension:

- run ``mvn clean install`` in the project directory
- the extension artifact JAR file may be found in the ``target`` directory: ``jasperreports-wms-component-x.y.z.jar``.

To build the iReport Plugin

- add iReport Platform to NetBeans (see: http://community.jaspersoft.com/wiki/introduction-custom-components-ireport-designer#Creation_of_the_plugin_in_NetBeans) 
- open the iReport Plugin project (located in subdirectory ``ireport-wms-plugin``) in NetBeans
- right-click the Project and select 'Properties'
- in 'Libraries', select the tab 'Wrapped Jars', and add the JasperReports extension JAR created before; click 'OK' to discard the properties dialog
- right-click the Project again and choose 'Create NBM'
- the plugin artifact ``com-sourcepole-ireport-wms.nbm`` may be found in the ``build`` directory



Copyright and License
=====================

Copyright (c) 2013 Sourcepole AG

JasperReports/iReport WMS Component is free software: you can redistribute 
it and/or modify it under the terms of the GNU Lesser General Public License 
as published by the Free Software Foundation, either version 3 of the 
License, or (at your option) any later version.

JasperReports/iReport WMS Component is distributed in the hope that it will
be useful, but WITHOUT ANY WARRANTY; without even the implied warranty 
of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
See the GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with JasperReports/iReport WMS Component. 
If not, see <http://www.gnu.org/licenses/>.


.. _JasperReports: http://community.jaspersoft.com/
.. _iReport Designer: http://community.jaspersoft.com/project/ireport-designer
.. _Web Map Service (WMS) protocol: http://en.wikipedia.org/wiki/Web_Map_Service
