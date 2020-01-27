// License: GPL. For details, see LICENSE file.
package org.openstreetmap.josm.plugins.customizepublictransportstop;

import java.util.ArrayList;
import java.util.Collection;

import org.openstreetmap.josm.data.osm.DataSet;
import org.openstreetmap.josm.data.osm.Node;
import org.openstreetmap.josm.data.osm.OsmPrimitive;
import org.openstreetmap.josm.data.osm.Relation;
import org.openstreetmap.josm.data.osm.RelationMember;

/**
 * Operation of constructing of stop area object from selected JOSM object
 * 
 * @author Rodion Scherbakov
 */
public class CreateStopAreaFromSelectedObjectOperation extends StopAreaOperationBase {

    /**
     * Constructor of operation object
     * 
     * @param currentDataSet Current Josm data set
     */
    public CreateStopAreaFromSelectedObjectOperation(DataSet currentDataSet) {
        super(currentDataSet);
    }

    /**
     * Parsing of josm object tags and customizing of stop area object
     * 
     * @param member Josm object
     */
    public void parseTags(StopArea stopArea, OsmPrimitive member) {
        if (stopArea.name == null)
            stopArea.name = getTagValue(member, OSMTags.NAME_TAG);
        if (stopArea.nameEn == null)
            stopArea.nameEn = getTagValue(member, OSMTags.NAME_EN_TAG);
        if (stopArea.operator == null)
            stopArea.operator = getTagValue(member, OSMTags.OPERATOR_TAG);
        if (stopArea.network == null)
            stopArea.network = getTagValue(member, OSMTags.NETWORK_TAG);
        if (stopArea.service == null)
            stopArea.service = getTagValue(member, OSMTags.SERVICE_TAG);
        if (OSMTags.LOCAL_NETWORK_TAG_VALUE.equals(stopArea.service))
            stopArea.service = OSMTags.COMMUTER_NETWORK_TAG_VALUE;
        if (stopArea.onDemand == null)
            stopArea.onDemand = getTagValue(member, OSMTags.ON_DEMAND_TAG);
        if (compareTag(member, OSMTags.BUS_TAG, OSMTags.YES_TAG_VALUE))
            stopArea.isBus = true;
        if (compareTag(member, OSMTags.TROLLEYBUS_TAG, OSMTags.YES_TAG_VALUE))
            stopArea.isTrolleybus = true;
        if (compareTag(member, OSMTags.SHARE_TAXI_TAG, OSMTags.YES_TAG_VALUE))
            stopArea.isShareTaxi = true;
        if (!(stopArea.isBus || stopArea.isShareTaxi || stopArea.isTrolleybus)
                && compareTag(member, OSMTags.HIGHWAY_TAG, OSMTags.BUS_STOP_TAG_VALUE))
            stopArea.isBus = true;
        if (compareTag(member, OSMTags.TRAM_TAG, OSMTags.YES_TAG_VALUE)
                || compareTag(member, OSMTags.RAILWAY_TAG, OSMTags.TRAM_STOP_TAG_VALUE))
            stopArea.isTram = true;
        if (compareTag(member, OSMTags.RAILWAY_TAG, OSMTags.HALT_TAG_VALUE))
            stopArea.isTrainStop = true;
        if (compareTag(member, OSMTags.RAILWAY_TAG, OSMTags.STATION_TAG_VALUE)) {
            if (compareTag(member, OSMTags.STATION_TAG_VALUE, OSMTags.SUBWAY_TAG_VALUE)) {
                stopArea.isSubwayStation = true;
                if (compareTag(member, OSMTags.TRAIN_TAG, OSMTags.YES_TAG_VALUE)) {
                    stopArea.isTrainStation = true;
                }
            }
            else
                stopArea.isTrainStation = true;
        }
        if (compareTag(member, OSMTags.AMENITY_TAG, OSMTags.BUS_STATION_TAG_VALUE)) {
            stopArea.isBusStation = true;
        }

        if(stopArea.ref == null)
            stopArea.ref = getTagValue(member, OSMTags.REF_TAG);
        if(stopArea.departuresBoard == null)
            stopArea.departuresBoard = getTagValue(member, OSMTags.DEPARTURES_BOARD_TAG);
        if(stopArea.onDemand == null)
            stopArea.onDemand = getTagValue(member, OSMTags.ON_DEMAND_TAG);
        if (compareTag(member, OSMTags.PASSENGER_INFORMATION_DISPLAY_TAG, OSMTags.YES_TAG_VALUE))
            stopArea.passengerInformationDisplay = true;

        if (member == stopArea.selectedObject) {
            if (compareTag(member, OSMTags.BENCH_TAG, OSMTags.YES_TAG_VALUE))
                stopArea.isBench = true;
            if (compareTag(member, OSMTags.SHELTER_TAG, OSMTags.YES_TAG_VALUE))
                stopArea.isShelter = true;
            if (compareTag(member, OSMTags.COVERED_TAG, OSMTags.YES_TAG_VALUE))
                stopArea.isCovered = true;
            if (compareTag(member, OSMTags.AREA_TAG, OSMTags.YES_TAG_VALUE))
                stopArea.isArea = true;

            if(stopArea.localRef == null)
                stopArea.localRef = getTagValue(member, OSMTags.LOCAL_REF_TAG);
            if(stopArea.layer == null)
                stopArea.layer = getTagValue(member, OSMTags.LAYER_TAG);
            if (compareTag(member, OSMTags.LIT_TAG, OSMTags.YES_TAG_VALUE))
                stopArea.isLit = true;
            if (compareTag(member, OSMTags.BIN_TAG, OSMTags.YES_TAG_VALUE))
                stopArea.isBin = true;
            if(stopArea.surface == null)
                stopArea.surface = getTagValue(member, OSMTags.SURFACE_TAG);
            if(stopArea.tactilePaving == null)
                stopArea.tactilePaving = getTagValue(member, OSMTags.TACTILE_PAVING_TAG);
        }
    }

    /**
     * Test, are transport types assigned to platforms
     * 
     * @param platform Platform object
     * @return true, if transport types assigned to this platforms
     */
    public boolean testIsTransportTypeAssigned(OsmPrimitive platform) {
        String[] transportTypes = new String[] {OSMTags.BUS_TAG, OSMTags.TROLLEYBUS_TAG, OSMTags.SHARE_TAXI_TAG,
                OSMTags.TRAM_TAG, OSMTags.TRAIN_TAG};
        for (String transportType : transportTypes) {
            if (compareTag(platform, transportType, OSMTags.YES_TAG_VALUE))
                return true;
        }
        return false;
    }

    /**
     * Setting of stop area from selected josm object
     * 
     * @param stopArea Selected stop area
     */
    public void   fromSelectedObject(StopArea stopArea) {
        Collection<OsmPrimitive> selectedObjects = new ArrayList<OsmPrimitive>();
        selectedObjects.add(stopArea.selectedObject);
        for (Relation rel : OsmPrimitive.getParentRelations(selectedObjects)) {
            if (compareTag(rel, OSMTags.TYPE_TAG, OSMTags.PUBLIC_TRANSPORT_TAG)
                    && compareTag(rel, OSMTags.PUBLIC_TRANSPORT_TAG, OSMTags.STOP_AREA_TAG_VALUE)) {
                stopArea.stopAreaRelation = rel;
            }
            if (stopArea.stopAreaRelation != null)
                break;
        }

        if (stopArea.stopAreaRelation != null) {
            parseTags(stopArea, stopArea.stopAreaRelation);
            parseTags(stopArea, stopArea.selectedObject);
            for (RelationMember member : stopArea.stopAreaRelation.getMembers()) {
                if (member.getMember() instanceof Node && compareTag(member.getMember(), OSMTags.PUBLIC_TRANSPORT_TAG,
                        OSMTags.STOP_POSITION_TAG_VALUE)) {
                    stopArea.isStopPointExists = true;
                    stopArea.stopPoints.add(member.getNode());
                } else if (compareTag(member.getMember(), OSMTags.PUBLIC_TRANSPORT_TAG, OSMTags.PLATFORM_TAG_VALUE)) {
                    stopArea.platforms.add(member.getMember());
                } else {
                    stopArea.otherMembers.add(member.getMember());
                }
                parseTags(stopArea, member.getMember());
            }
            if (!stopArea.platforms.contains(stopArea.selectedObject)) {
                parseTags(stopArea, stopArea.selectedObject);
                stopArea.platforms.add(stopArea.selectedObject);
            }
        } else {
            parseTags(stopArea, stopArea.selectedObject);
            stopArea.platforms.add(stopArea.selectedObject);
        }
        for (OsmPrimitive platform : stopArea.platforms) {
            if (testIsTransportTypeAssigned(platform)) {
                stopArea.isAssignTransportType = true;
                break;
            }
        }
        if (!(stopArea.isBus || stopArea.isTrolleybus || stopArea.isShareTaxi) && stopArea.selectedObject != null
                && (compareTag(stopArea.selectedObject, OSMTags.HIGHWAY_TAG, OSMTags.BUS_STOP_TAG_VALUE)
                        || stopArea.isBusStation)) {
            stopArea.isBus = true;
        }
        if(null == stopArea.layer)
            stopArea.layer = "0";
    }

    /**
     * Construct stop area object from selected JOSM object
     * 
     * @param stopArea Original stop area object
     * @return Stop area objects with settings of selected JOSM object and included
     *         it stop area relation
     */
    @Override
    public StopArea performCustomizing(StopArea stopArea) {
        if (getCurrentDataSet() == null)
            return null;
        Collection<OsmPrimitive> selectedObjects = getCurrentDataSet().getSelectedNodesAndWays();
        if(!(null != selectedObjects && !selectedObjects.isEmpty()))
            return null;
        OsmPrimitive selectedObject = selectedObjects.iterator().next();
        if (selectedObject == null)
            return null;
        OsmPrimitive additionalSelectedObject = null;
        if(selectedObjects.size() > 1)
        {
            OsmPrimitive[] selectedObjectsArr = new OsmPrimitive[selectedObjects.size()];
            selectedObjects.toArray(selectedObjectsArr);
            additionalSelectedObject = selectedObjectsArr[1];
        }
        if (stopArea == null)
            if (additionalSelectedObject == null)
                stopArea = new StopArea(selectedObject);
            else
                stopArea = new StopArea(selectedObject, additionalSelectedObject);
        fromSelectedObject(stopArea);
        return stopArea;
    }

}
