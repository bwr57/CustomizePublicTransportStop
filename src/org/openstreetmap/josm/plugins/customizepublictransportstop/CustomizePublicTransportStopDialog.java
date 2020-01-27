// License: GPL. For details, see LICENSE file.
package org.openstreetmap.josm.plugins.customizepublictransportstop;

import static org.openstreetmap.josm.tools.I18n.tr;
import static org.openstreetmap.josm.tools.I18n.trc;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.openstreetmap.josm.gui.MainApplication;


/**
 * Dialog for setting stop area properties
 * 
 * @author Rodion Scherbakov
 */
public class CustomizePublicTransportStopDialog implements ActionListener, ItemListener, KeyListener {
    private static final String CANCEL_COMMAND = "cancel";
    private static final String SAVE_COMMAND = "save";

    private static final String PUBLIC_TRANSPORT_LANG_SECTION_NAME = "PublicTransport";
    private static final String CANCEL_BUTTON_CAPTION = "Cancel";
    private static final String SAVE_BUTTON_CAPTION = "Save";
    private static final String AREA_CAPTION = "Area";
    private static final String COVER_CAPTION = "Cover";
    private static final String SHELTER_CAPTION = "Shelter";
    private static final String BENCH_CAPTION = "Bench";
    private static final String SUBWAY_STATION_CAPTION = "Subway station";
    private static final String RAILWAY_STOP_CAPTION = "Railway stop";
    private static final String RAILWAY_STATION_CAPTION = "Railway station";
    private static final String TRAM_CAPTION = "Tram";
    private static final String TROLLEYBUS_CAPTION = "Trolleybus";
    private static final String SHARE_TAXI_CAPTION = "Share taxi";
    private static final String BUS_CAPTION = "Bus";
    private static final String BUS_STATION_CAPTION = "Bus station";
    private static final String ASSIGN_TRANSPORT_TYPE_CAPTION = "Assign transport type to platform";
    private static final String NETWORK_LEVEL_CAPTION = "Network level";
    private static final String OPERATOR_CAPTION = "Operator";
    private static final String NETWORK_CAPTION = "Network";
    private static final String NAME_EN_CAPTION = "Name (en.)";
    private static final String NAME_CAPTION = "Name";
    private static final String STOP_CUSTOMIZING_DIALOG_CAPTION = "Stop customizing";
    public static final String LONG_DISTANCE_NETWORK_CAPTION = "Long distance";
    public static final String REGIONAL_NETWORK_CAPTION = "Regional";
    public static final String COMMUTER_NETWORK_CAPTION = "Commuter";
    public static final String CITY_NETWORK_CAPTION = "City transport";
    public static final String HIGH_SPEED_NETWORK_CAPTION = "High speed";
    public static final String TOURISM_NETWORK_CAPTION = "Tourism";
    public static final String SCHOOL_NETWORK_CAPTION = "School transport";
    public static final String FACTORY_NETWORK_CAPTION = "Factory";

    public static final String REF_CAPTION = "Ref";
    public static final String LOCAL_REF_CAPTION = "Platform ref";
    public static final String LAYER_CAPTION = "Layer";
    public static final String PASSENGER_INFORMATION_DISPLAY_CAPTION = "Timesheet";
    public static final String DEPARTURES_BOARD_CAPTION = "Board";
    public static final String TACTILE_PAVING_CAPTION = "Tactile paving";
    public static final String LIT_CAPTION = "Lit";
    public static final String BIN_CAPTION = "Bin";
    public static final String SURFACE_CAPTION = "Surface";
    public static final String ON_DEMAND_CAPTION = "On demand";

    public static final String DEPARTURES_BOARD_NO_CAPTION = "No";
    public static final String DEPARTURES_BOARD_YES_CAPTION = "Yes";
    public static final String DEPARTURES_BOARD_TIMETABLE_CAPTION = "Timetable";
    public static final String DEPARTURES_BOARD_DELAY_CAPTION = "Delay";
    public static final String DEPARTURES_BOARD_REALTIME_CAPTION = "Realtime";

    public static final String TACTILE_PAVING_NO_CAPTION = "No";
    public static final String TACTILE_PAVING_YES_CAPTION = "Yes";
    public static final String TACTILE_PAVING_CONTRASTED_CAPTION = "Contrasted";
    public static final String TACTILE_PAVING_PRIMITIVE_CAPTION = "Primitive paving";
    public static final String TACTILE_PAVING_INCORRECT_CAPTION = "Incorrect";

    public static final String SURFACE_UNKNOWN_CAPTION = "Unknown";
    public static final String SURFACE_PAVED_CAPTION = "Paved";
    public static final String SURFACE_ASPHALT_CAPTION = "Asphalt";
    public static final String SURFACE_CONCRETE_CAPTION = "Concrete";
    public static final String SURFACE_PAVING_STONES_CAPTION = "Paving stones";
    public static final String SURFACE_SETT_CAPTION = "Sett";
    public static final String SURFACE_UNHEUN_COBBLESTONE_CAPTION = "Unheun cobblestone";
    public static final String SURFACE_COBBLESTONE_CAPTION = "Cobblestone";
    public static final String SURFACE_METAL_CAPTION = "Metal";
    public static final String SURFACE_WOOD_CAPTION = "Wood";
    public static final String SURFACE_UNPAVED_CAPTION = "Unpaved";
    public static final String SURFACE_COMPACTED_CAPTION = "Compacted";
    public static final String SURFACE_FINE_GRAVEL_CAPTION = "Fine gravel";
    public static final String SURFACE_GRAVEL_CAPTION = "Gravel";
    public static final String SURFACE_PEBBLESTONE_CAPTION = "Pebblestone";
    public static final String SURFACE_DIRT_CAPTION = "Dirt";
    public static final String SURFACE_GRASS_CAPTION = "Grass";
    public static final String SURFACE_GRASS_PAVER_CAPTION = "Grass paver";
    public static final String SURFACE_GROUND_CAPTION = "Ground";
    public static final String SURFACE_MUD_CAPTION = "Mud";
    public static final String SURFACE_SAND_CAPTION = "Sand";
    public static final String SURFACE_WOODCHIPS_CAPTION = "Woodchips";
    public static final String SURFACE_SNOW_CAPTION = "Snow";
    public static final String SURFACE_ICE_CAPTION = "Ice";
    public static final String SURFACE_SALT_CAPTION = "Salt";
    private static final String REGULAR_STOP_CAPTION = "Regular";

    private static final String TRANSPORT_TYPES_CAPTION = "Transport types";
    private static final String PLATFORM_ATTRIBUTES_CAPTION = "Platform attributes";
    private static final String SET_DEFAULT_VALUES_CAPTION = "Set default values";


    private String[] serviceCaptionStrings = {CITY_NETWORK_CAPTION, COMMUTER_NETWORK_CAPTION, REGIONAL_NETWORK_CAPTION,
            LONG_DISTANCE_NETWORK_CAPTION, HIGH_SPEED_NETWORK_CAPTION, TOURISM_NETWORK_CAPTION, SCHOOL_NETWORK_CAPTION,
            FACTORY_NETWORK_CAPTION};
    private String[] serviceTagValues = {OSMTags.CITY_NETWORK_TAG_VALUE, OSMTags.COMMUTER_NETWORK_TAG_VALUE,
            OSMTags.REGIONAL_NETWORK_TAG_VALUE, OSMTags.LONG_DISTANCE_NETWORK_TAG_VALUE,
            OSMTags.HIGH_SPEED_NETWORK_TAG_VALUE, OSMTags.TOURISM_NETWORK_TAG_VALUE, OSMTags.SCHOOL_NETWORK_TAG_VALUE,
            OSMTags.FACTORY_NETWORK_TAG_VALUE};
    private String[] onDemandCaptionStrings = { REGULAR_STOP_CAPTION, CITY_NETWORK_CAPTION, COMMUTER_NETWORK_CAPTION, REGIONAL_NETWORK_CAPTION,
            LONG_DISTANCE_NETWORK_CAPTION, HIGH_SPEED_NETWORK_CAPTION, TOURISM_NETWORK_CAPTION, SCHOOL_NETWORK_CAPTION,
            FACTORY_NETWORK_CAPTION};
    private String[] onDemandTagValues = { OSMTags.REGULAR_STOP_TAG_VALUE, OSMTags.CITY_NETWORK_TAG_VALUE,
            OSMTags.COMMUTER_NETWORK_TAG_VALUE,
            OSMTags.REGIONAL_NETWORK_TAG_VALUE, OSMTags.LONG_DISTANCE_NETWORK_TAG_VALUE,
            OSMTags.HIGH_SPEED_NETWORK_TAG_VALUE, OSMTags.TOURISM_NETWORK_TAG_VALUE, OSMTags.SCHOOL_NETWORK_TAG_VALUE,
            OSMTags.FACTORY_NETWORK_TAG_VALUE};
    private String[] departuresBoardCaptionStrings= { DEPARTURES_BOARD_NO_CAPTION, DEPARTURES_BOARD_YES_CAPTION,
            DEPARTURES_BOARD_TIMETABLE_CAPTION,
            DEPARTURES_BOARD_DELAY_CAPTION, DEPARTURES_BOARD_REALTIME_CAPTION };
    private String[] departuresBoardTagValues={OSMTags.NO_TAG_VALUE, OSMTags.DEPARTURES_BOARD_YES_TAG_VALUE,
            OSMTags.DEPARTURES_BOARD_TIMETABLE_TAG_VALUE,
            OSMTags.DEPARTURES_BOARD_DELAY_TAG_VALUE, OSMTags.DEPARTURES_BOARD_REALTIME_TAG_VALUE };
    private String[] tactilePavingCaptionStrings= { TACTILE_PAVING_NO_CAPTION, TACTILE_PAVING_YES_CAPTION,
            TACTILE_PAVING_PRIMITIVE_CAPTION,
            TACTILE_PAVING_CONTRASTED_CAPTION, TACTILE_PAVING_INCORRECT_CAPTION };
    private String[] tactilePavingTagValues={OSMTags.NO_TAG_VALUE, OSMTags.TACTILE_PAVING_YES_TAG_VALUE,
            OSMTags.TACTILE_PAVING_PRIMITIVE_TAG_VALUE,
            OSMTags.TACTILE_PAVING_CONTRASTED_TAG_VALUE, OSMTags.TACTILE_PAVING_INCORRECT_TAG_VALUE };
    private String[] surfaceCaptionStrings = { SURFACE_UNKNOWN_CAPTION, SURFACE_PAVED_CAPTION, SURFACE_ASPHALT_CAPTION,
                     SURFACE_CONCRETE_CAPTION,
                     SURFACE_PAVING_STONES_CAPTION, SURFACE_SETT_CAPTION, SURFACE_UNHEUN_COBBLESTONE_CAPTION,
                     SURFACE_COBBLESTONE_CAPTION, SURFACE_METAL_CAPTION, SURFACE_WOOD_CAPTION, SURFACE_UNPAVED_CAPTION,
                     SURFACE_COMPACTED_CAPTION, SURFACE_FINE_GRAVEL_CAPTION, SURFACE_GRAVEL_CAPTION, 
                     SURFACE_PEBBLESTONE_CAPTION, SURFACE_DIRT_CAPTION, SURFACE_GRASS_CAPTION, SURFACE_GRASS_PAVER_CAPTION, 
                     SURFACE_GROUND_CAPTION, SURFACE_MUD_CAPTION, SURFACE_SAND_CAPTION, SURFACE_WOODCHIPS_CAPTION,
                     SURFACE_SNOW_CAPTION, SURFACE_ICE_CAPTION, SURFACE_SALT_CAPTION };
    private String[] surfaceTagValues = { OSMTags.UNKNOWN_TAG_VALUE, OSMTags.SURFACE_PAVED_TAG_VALUE,
            OSMTags.SURFACE_ASPHALT_TAG_VALUE,
            OSMTags.SURFACE_CONCRETE_TAG_VALUE, OSMTags.SURFACE_PAVING_STONES_TAG_VALUE,
            OSMTags.SURFACE_SETT_TAG_VALUE, OSMTags.SURFACE_UNHEUN_COBBLESTONE_TAG_VALUE,
            OSMTags.SURFACE_COBBLESTONE_TAG_VALUE, OSMTags.SURFACE_METAL_TAG_VALUE,
            OSMTags.SURFACE_WOOD_TAG_VALUE, OSMTags.SURFACE_UNPAVED_TAG_VALUE,
            OSMTags.SURFACE_COMPACTED_TAG_VALUE, OSMTags.SURFACE_FINE_GRAVEL_TAG_VALUE,
            OSMTags.SURFACE_GRAVEL_TAG_VALUE, OSMTags.SURFACE_PEBBLESTONE_TAG_VALUE, OSMTags.SURFACE_DIRT_TAG_VALUE,
            OSMTags.SURFACE_GRASS_TAG_VALUE, OSMTags.SURFACE_GRASS_PAVER_TAG_VALUE,
            OSMTags.SURFACE_GROUND_TAG_VALUE, OSMTags.SURFACE_MUD_TAG_VALUE, OSMTags.SURFACE_SAND_TAG_VALUE,
            OSMTags.SURFACE_WOODCHIPS_TAG_VALUE, OSMTags.SURFACE_SNOW_TAG_VALUE, OSMTags.SURFACE_ICE_TAG_VALUE,
            OSMTags.SURFACE_SALT_TAG_VALUE  };
    private String[] layerCaptionStrings = { "-5", "-4", "-3", "-2", "-1", "0", "1", "2", "3", "4", "5" };
    private String[] layerTagValues = { "-5", "-4", "-3", "-2", "-1", "0", "1", "2", "3", "4", "5" };


    private JDialog jDialog = null;
    private JTextField textFieldName = null;
    private JTextField textFieldNameEn;
    private JTextField textFieldRef = null;
    private JTextField textFieldNetwork;
    private JTextField textFieldOperator;
    private JComboBox<String> comboBoxService;
    private JComboBox<String> comboBoxOnDemand;
    private JCheckBox checkBoxIsBus;
    private JCheckBox checkBoxIsTrolleybus;
    private JCheckBox checkBoxIsShareTaxi;
    private JCheckBox checkBoxIsBusStation;
    private JCheckBox checkBoxIsAssignTransportType;
    private JCheckBox checkBoxIsTram;
    private JCheckBox checkBoxIsSubwayStation;
    private JCheckBox checkBoxIsTrainStation;
    private JCheckBox checkBoxIsTrainStop;
    private JTextField textFieldLocalRef = null;
    private JComboBox<String> comboBoxLayer;
    private JComboBox<String> comboBoxSurface;
    private JComboBox<String> comboBoxTactilePaving;
    private JCheckBox checkBoxIsPassengerInformationDisplay;
    private JComboBox<String> comboBoxDeparturesBoard;
    private JCheckBox checkBoxIsBench;
    private JCheckBox checkBoxIsShelter;
    private JCheckBox checkBoxIsCover;
    private JCheckBox checkBoxIsLit;
    private JCheckBox checkBoxIsBin;
    private JCheckBox checkBoxIsArea;
    private JCheckBox checkBoxIsSetDefaultValues;

    /**
     * Stop area
     */
    private StopArea stopArea;
    /**
     * Customize stop action object for callback
     */
    private CustomizeStopAction customizeStopAction;

    /**
     * Map of check boxes
     */
    private HashMap<JCheckBox, Boolean> checkBoxValues = new HashMap<JCheckBox, Boolean>();

    /**
     * Previous stop name
     */
    private static String previousName;
    /**
     * Previous english stop name
     */
    private static String previousNameEn;
    /**
     * Network name at previous call
     */
    private static String previousNetwork = null;
    /**
     * Operator name at previous call
     */
    private static String previousOperator = null;

    private static Boolean previousIsSetDefaultValues = false;

    /**
     * Reference to dialog object
     */
    private static CustomizePublicTransportStopDialog customizePublicTransportStopDialogInstance;

    /**
     * Construct dialog and fill controls
     * 
     * @param customizeStopAction Stop area customizing action
     * @param stopArea Stop area
     * @return Reference to dialog
     */
    public static CustomizePublicTransportStopDialog showCustomizePublicTransportStopDialog(
            CustomizeStopAction customizeStopAction, StopArea stopArea) {
        if (customizePublicTransportStopDialogInstance == null) {
            customizePublicTransportStopDialogInstance = new CustomizePublicTransportStopDialog(customizeStopAction,
                    stopArea);
        } else {
            customizePublicTransportStopDialogInstance.setCustomizeStopAction(customizeStopAction);
            customizePublicTransportStopDialogInstance.setStopArea(stopArea);
        }
        customizePublicTransportStopDialogInstance.setVisible(true);
        return customizePublicTransportStopDialogInstance;
    }

    /**
     * Constructor of dialog
     */
    public CustomizePublicTransportStopDialog() {
        Frame frame = JOptionPane.getFrameForComponent(MainApplication.getMainFrame());
        jDialog = new JDialog(frame, tr(STOP_CUSTOMIZING_DIALOG_CAPTION), false);
        JPanel contentPane = createContentPane();

        jDialog.add(contentPane);

        jDialog.pack();
        jDialog.setLocationRelativeTo(frame);

        jDialog.addKeyListener(this);
        contentPane.addKeyListener(this);

    }

    private void setupGridBagConstraints(GridBagConstraints layoutCons,
                                         Integer row, Integer column, Integer rowSpan) {
        layoutCons.gridx = column;
        layoutCons.gridy = row;
        layoutCons.gridwidth = rowSpan;
    }

    private void setupGridBagConstraints(GridBagConstraints layoutCons,
                                         Integer row, Integer column) {
        setupGridBagConstraints(layoutCons, row, column, 1);
    }

    private void addLabel(JPanel contentPane, GridBagLayout gridbag, GridBagConstraints layoutCons, String labelText,
                          Integer row, Integer column, Integer rowSpan, Boolean isBold) {
        JLabel newLabel = new JLabel(tr(labelText));
        setupGridBagConstraints(layoutCons, row, column, rowSpan);
        if(isBold)
            newLabel.setFont(
                    newLabel.getFont().deriveFont(newLabel.getFont().getStyle() | Font.BOLD));
        gridbag.setConstraints(newLabel, layoutCons);
        newLabel.addKeyListener(this);
        contentPane.add(newLabel);
    }

    private JTextField addTextField(JPanel contentPane, GridBagLayout gridbag, GridBagConstraints layoutCons,
                                    String text, Integer textLenght,
                                    Integer row, Integer column, Integer rowSpan) {
        JTextField newTextField = new JTextField(text, textLenght);
        setupGridBagConstraints(layoutCons, row, column, rowSpan);
        gridbag.setConstraints(newTextField, layoutCons);
        newTextField.addKeyListener(this);
        contentPane.add(newTextField);
        return newTextField;
    }

    private JTextField addTextField(JPanel contentPane, GridBagLayout gridbag, GridBagConstraints layoutCons,
                                    String caption, String value, Integer valueLenght,
                                    Integer row, Integer column, Boolean isBold) {
        addLabel(contentPane, gridbag, layoutCons, tr(caption), row, column, 1, isBold);
        return addTextField(contentPane, gridbag, layoutCons, value, valueLenght, row, column + 1, 1);
    }

    private JCheckBox addCheckBox(JPanel contentPane, GridBagLayout gridbag, GridBagConstraints layoutCons,
                                  String caption, Integer row, Integer column, Integer rowSpan) {
        JCheckBox newCheckBox = new JCheckBox(trc(PUBLIC_TRANSPORT_LANG_SECTION_NAME, caption));
        setupGridBagConstraints(layoutCons, row, column, rowSpan);
        gridbag.setConstraints(newCheckBox, layoutCons);
        newCheckBox.addItemListener(this);
        newCheckBox.addKeyListener(this);
        contentPane.add(newCheckBox);
        return newCheckBox;
    }

    private JCheckBox addCheckBox(JPanel contentPane, GridBagLayout gridbag, GridBagConstraints layoutCons,
                                  String caption, Integer row, Integer column) {
        return addCheckBox(contentPane, gridbag, layoutCons, tr(caption), row, column, 1);
    }

    private JComboBox<String> addComboBox(JPanel contentPane, GridBagLayout gridbag, GridBagConstraints layoutCons,
                                          String[] valueCaptions, Integer selectedIndex,
                                          Integer row, Integer column) {
        String[] valueCaptionsStrings = new String[valueCaptions.length];
        for (int i = 0; i < valueCaptions.length; i++) {
            valueCaptionsStrings[i] = tr(valueCaptions[i]);
        }
        JComboBox<String> newComboBox = new JComboBox<String>(valueCaptionsStrings);
        newComboBox.setSelectedIndex(selectedIndex);
        setupGridBagConstraints(layoutCons, row, column + 1);
        gridbag.setConstraints(newComboBox, layoutCons);
        newComboBox.addKeyListener(this);
        contentPane.add(newComboBox);
        return newComboBox;
    }

    private JComboBox<String> addComboBox(JPanel contentPane, GridBagLayout gridbag, GridBagConstraints layoutCons,
                                          String caption, String[] valueCaptions, Integer selectedIndex,
                                          Integer row, Integer column, Boolean isBold) {
        addLabel(contentPane, gridbag, layoutCons, tr(caption), row, column, 1, isBold);
        return addComboBox(contentPane, gridbag, layoutCons, valueCaptions, selectedIndex, row, column);
    }

    private JPanel createContentPane() {
        JPanel contentPane = new JPanel();
        GridBagLayout gridbag = new GridBagLayout();
        contentPane.setLayout(gridbag);
        GridBagConstraints layoutCons = new GridBagConstraints();
        layoutCons.insets = new Insets(5, 3, 1, 3);
        layoutCons.fill = GridBagConstraints.BOTH;
        layoutCons.weightx = 0.225;

        addLabel(contentPane, gridbag, layoutCons, " ", 0, 0, 1, false);
        addLabel(contentPane, gridbag, layoutCons, " ", 0, 3, 1, false);
        addLabel(contentPane, gridbag, layoutCons, " ", 0, 6, 1, false);

        textFieldRef = addTextField(contentPane, gridbag, layoutCons, REF_CAPTION, "", 25, 0, 1, false);

        layoutCons.insets = new Insets(1, 3, 1, 3);

        textFieldName = addTextField(contentPane, gridbag, layoutCons, NAME_CAPTION, "", 25, 1, 1, false);
        textFieldNameEn = addTextField(contentPane, gridbag, layoutCons, NAME_EN_CAPTION, "", 25, 1, 4, false);
        textFieldLocalRef = addTextField(contentPane, gridbag, layoutCons, LOCAL_REF_CAPTION, "", 25, 2, 1, false);
        comboBoxLayer = addComboBox(contentPane, gridbag, layoutCons, LAYER_CAPTION, layerCaptionStrings,
                                    5, 2, 4, false);
        textFieldNetwork = addTextField(contentPane, gridbag, layoutCons, NETWORK_CAPTION, "", 25, 3, 1, false);
        textFieldOperator = addTextField(contentPane, gridbag, layoutCons, OPERATOR_CAPTION, "", 25, 3, 4, false);
        comboBoxService = addComboBox(contentPane, gridbag, layoutCons, NETWORK_LEVEL_CAPTION, serviceCaptionStrings,
                0, 4, 1, false);
        comboBoxOnDemand = addComboBox(contentPane, gridbag, layoutCons, ON_DEMAND_CAPTION, onDemandCaptionStrings,
                0, 4, 4, false);
        addLabel(contentPane, gridbag, layoutCons, TRANSPORT_TYPES_CAPTION, 5, 1, 2, true);
        checkBoxIsBus = addCheckBox(contentPane, gridbag, layoutCons, BUS_CAPTION, 6,1);
        checkBoxIsShareTaxi = addCheckBox(contentPane, gridbag, layoutCons, SHARE_TAXI_CAPTION, 6, 2);
        checkBoxIsTrolleybus = addCheckBox(contentPane, gridbag, layoutCons, TROLLEYBUS_CAPTION, 7, 1);
        checkBoxIsBusStation = addCheckBox(contentPane, gridbag, layoutCons, BUS_STATION_CAPTION, 7, 2);
        checkBoxIsTram = addCheckBox(contentPane, gridbag, layoutCons, TRAM_CAPTION, 6, 4);
        checkBoxIsSubwayStation = addCheckBox(contentPane, gridbag, layoutCons, SUBWAY_STATION_CAPTION, 6, 5);
        checkBoxIsTrainStop = addCheckBox(contentPane, gridbag, layoutCons, RAILWAY_STOP_CAPTION, 7, 4);
        checkBoxIsTrainStation = addCheckBox(contentPane, gridbag, layoutCons, RAILWAY_STATION_CAPTION, 7, 5);
        checkBoxIsAssignTransportType = addCheckBox(contentPane, gridbag, layoutCons, ASSIGN_TRANSPORT_TYPE_CAPTION, 8, 1, 2);
        checkBoxIsArea = addCheckBox(contentPane, gridbag, layoutCons, AREA_CAPTION, 8, 5);
        addLabel(contentPane, gridbag, layoutCons, tr(PLATFORM_ATTRIBUTES_CAPTION), 9, 1, 1, true);
        checkBoxIsBench = addCheckBox(contentPane, gridbag, layoutCons, BENCH_CAPTION, 10, 1);
        checkBoxIsShelter = addCheckBox(contentPane, gridbag, layoutCons, SHELTER_CAPTION, 10, 2);
        checkBoxIsCover = addCheckBox(contentPane, gridbag, layoutCons, COVER_CAPTION, 11, 1);
        checkBoxIsLit = addCheckBox(contentPane, gridbag, layoutCons, LIT_CAPTION, 11, 2);
        comboBoxDeparturesBoard = addComboBox(contentPane, gridbag, layoutCons, DEPARTURES_BOARD_CAPTION,
                departuresBoardCaptionStrings,0, 10, 4, false);
        checkBoxIsBin = addCheckBox(contentPane, gridbag, layoutCons, BIN_CAPTION, 11, 4);
        comboBoxSurface = addComboBox(contentPane, gridbag, layoutCons, SURFACE_CAPTION, surfaceCaptionStrings,
                0, 12, 1, false);
        comboBoxTactilePaving = addComboBox(contentPane, gridbag, layoutCons, TACTILE_PAVING_CAPTION,
                tactilePavingCaptionStrings,0, 12, 4, false);
        checkBoxIsSetDefaultValues = addCheckBox(contentPane, gridbag, layoutCons, SET_DEFAULT_VALUES_CAPTION, 13, 4);

        JButton buttonSave = new JButton(tr(SAVE_BUTTON_CAPTION));
        layoutCons.gridx = 2;
        layoutCons.gridy = 14;
        layoutCons.insets = new Insets(10, 0, 10, 3);
        layoutCons.fill = GridBagConstraints.PAGE_END;
        layoutCons.anchor = GridBagConstraints.EAST;
        gridbag.setConstraints(buttonSave, layoutCons);
        buttonSave.setActionCommand(SAVE_COMMAND);
        buttonSave.addActionListener(this);
        buttonSave.addKeyListener(this);
        contentPane.add(buttonSave);

        JButton buttonCancel = new JButton(tr(CANCEL_BUTTON_CAPTION));
        layoutCons.gridx = 4;
        layoutCons.gridy = 14;
        layoutCons.fill = GridBagConstraints.LINE_START;
        layoutCons.anchor = GridBagConstraints.WEST;
        layoutCons.insets = new Insets(10, 3, 10, 0);
        gridbag.setConstraints(buttonCancel, layoutCons);
        buttonCancel.setActionCommand(CANCEL_COMMAND);
        buttonCancel.addActionListener(this);
        buttonCancel.addKeyListener(this);
        contentPane.add(buttonCancel);

        return contentPane;
    }

    /**
     * Constructor of dialog with filling of controls
     * 
     * @param customizeStopAction Stop area customizing action
     * @param stopArea Stop area
     */
    public CustomizePublicTransportStopDialog(CustomizeStopAction customizeStopAction, StopArea stopArea) {
        this();
        setValues(stopArea);
        this.customizeStopAction = customizeStopAction;
        this.stopArea = stopArea;
    }

    /**
     * Return stop area
     * 
     * @return Stop area
     */
    public StopArea getStopArea() {
        return stopArea;
    }

    /**
     * Set stop area and fill controls
     * 
     * @param newStopArea Stop area
     */
    public void setStopArea(StopArea newStopArea) {
        setValues(newStopArea);
        this.stopArea = newStopArea;
    }

    /**
     * Returns stop area customizing action
     * 
     * @return Stop area customizing action
     */
    public CustomizeStopAction getCustomizeStopAction() {
        return customizeStopAction;
    }

    /**
     * Set stop area customizing action
     * 
     * @param newCustomizeStopAction Stop area customizing action
     */
    public void setCustomizeStopAction(CustomizeStopAction newCustomizeStopAction) {
        customizeStopAction = newCustomizeStopAction;
    }

    /**
     * Set value in check boxes map
     * 
     * @param checkBox Check box
     * @param value Value of check box
     */
    public void setCheckBoxValue(JCheckBox checkBox, Boolean value) {
        checkBoxValues.put(checkBox, value);
        checkBox.setSelected(value);
    }

    /**
     * Returns value of check box
     * 
     * @param checkBox Check box
     * @return Value of check box
     */
    public Boolean getCheckBoxValue(JCheckBox checkBox) {
        try {
            if (checkBoxValues.containsKey(checkBox)) {
                return checkBoxValues.get(checkBox);
            }
            return false;
        } catch (Exception ex) {
            MessageBox.ok(ex.getMessage());
        }
        return false;
    }

    /**
     * Callback method for check boxes Set values in check boxes map
     */
    @Override
    public void itemStateChanged(ItemEvent event) {
        JCheckBox checkBox = (JCheckBox) event.getSource();
        if (event.getStateChange() == ItemEvent.DESELECTED) {
            checkBoxValues.put(checkBox, false);
        } else if (event.getStateChange() == ItemEvent.SELECTED) {
            checkBoxValues.put(checkBox, true);
        }
    }

    /**
     * Show or hide dialog
     * 
     * @param isVisible Flag of dialog visibility
     */
    public void setVisible(Boolean isVisible) {
        if (jDialog != null)
            jDialog.setVisible(isVisible);
    }

    /**
     * Get index of network level
     * 
     * @param currentValue Network level name
     * @return Index of network level
     */
    private int getArrayIndex(String[] arrayValues, String currentValue) {
        if(null != arrayValues) {
            for (int i = 0; i < arrayValues.length; i++) {
                if (arrayValues[i].equals(currentValue)) {
                    return i;
                }
            }
        }
        return 0;
    }

    private void setComboBoxValue(JComboBox comboBox, String[] comboBoxValues, String currentValue)
    {
        if(null != comboBox) {
            comboBox.setSelectedIndex(getArrayIndex(comboBoxValues, currentValue));
        }
    }

    /**
     * Setting values of controls from stop area
     * 
     * @param stopArea Stop area
     */
    public void setValues(StopArea stopArea) {
        if (stopArea == null)
            return;
        if (stopArea.name != null)
            textFieldName.setText(stopArea.name);
        else if (previousName != null)
            textFieldName.setText(previousName);
        if (stopArea.nameEn != null)
            textFieldNameEn.setText(stopArea.nameEn);
        else if (previousNameEn != null)
            textFieldNameEn.setText(previousNameEn);
        if (stopArea.network != null)
            textFieldNetwork.setText(stopArea.network);
        else if (previousNetwork != null)
            textFieldNetwork.setText(previousNetwork);
        if (stopArea.operator != null)
            textFieldOperator.setText(stopArea.operator);
        else if (previousOperator != null)
            textFieldOperator.setText(previousOperator);
//        if (stopArea.additionalSelectedObject != null)
//            textFieldOperator.setText(String.valueOf(stopArea.additionalSelectedObject.getId()));
        setComboBoxValue(comboBoxService, serviceTagValues, stopArea.service);
        setCheckBoxValue(checkBoxIsBus, stopArea.isBus);
        setCheckBoxValue(checkBoxIsShareTaxi, stopArea.isShareTaxi);
        setCheckBoxValue(checkBoxIsTrolleybus, stopArea.isTrolleybus);
        setCheckBoxValue(checkBoxIsBusStation, stopArea.isBusStation);
        setCheckBoxValue(checkBoxIsAssignTransportType, stopArea.isAssignTransportType);
        setCheckBoxValue(checkBoxIsTram, stopArea.isTram);
        setCheckBoxValue(checkBoxIsTrainStation, stopArea.isTrainStation);
        setCheckBoxValue(checkBoxIsTrainStop, stopArea.isTrainStation);
        setCheckBoxValue(checkBoxIsTrainStop, stopArea.isTrainStop);
        setCheckBoxValue(checkBoxIsBench, stopArea.isBench);
        setCheckBoxValue(checkBoxIsShelter, stopArea.isShelter);
        setCheckBoxValue(checkBoxIsCover, stopArea.isCovered);
        setCheckBoxValue(checkBoxIsArea, stopArea.isArea);

        if (stopArea.ref != null)
            textFieldRef.setText(stopArea.ref);
        if (stopArea.localRef != null)
            textFieldLocalRef.setText(stopArea.localRef);
        setComboBoxValue(comboBoxLayer, layerTagValues, stopArea.layer);
        setComboBoxValue(comboBoxOnDemand, onDemandTagValues, stopArea.onDemand);
        setCheckBoxValue(checkBoxIsSubwayStation, stopArea.isSubwayStation);
        setCheckBoxValue(checkBoxIsLit, stopArea.isLit);
        setCheckBoxValue(checkBoxIsBin, stopArea.isBin);
        setComboBoxValue(comboBoxDeparturesBoard, departuresBoardTagValues, stopArea.departuresBoard);
        setComboBoxValue(comboBoxSurface, surfaceTagValues, stopArea.surface);
        setComboBoxValue(comboBoxTactilePaving, tactilePavingTagValues, stopArea.tactilePaving);

        setCheckBoxValue(checkBoxIsSetDefaultValues, previousIsSetDefaultValues);
    }

    /**
     * Returns text box value or null
     * 
     * @param textField Text box
     * @return Text box value or null
     */
    public String getTextFromControl(JTextField textField) {
        if (textField.getText().isEmpty())
            return null;
        return textField.getText();
    }

    /**
     * Load values from controls and saving in stop area fields
     * 
     * @return Stop area
     */
    public StopArea saveValues() {
        StopArea stopArea = this.stopArea;
        try {
            if (stopArea == null)
                stopArea = new StopArea();
            stopArea.ref = getTextFromControl(textFieldRef);
            stopArea.name = getTextFromControl(textFieldName);
            previousName = stopArea.name;
            stopArea.nameEn = getTextFromControl(textFieldNameEn);
            previousNameEn = stopArea.nameEn;
            stopArea.localRef = getTextFromControl(textFieldLocalRef);
            stopArea.layer = layerTagValues[comboBoxLayer.getSelectedIndex()];
            stopArea.network = getTextFromControl(textFieldNetwork);
            previousNetwork = stopArea.network;
            stopArea.operator = getTextFromControl(textFieldOperator);
            previousOperator = stopArea.operator;
            stopArea.service = serviceTagValues[comboBoxService.getSelectedIndex()];
            stopArea.onDemand = onDemandTagValues[comboBoxOnDemand.getSelectedIndex()];
            stopArea.isBus = getCheckBoxValue(checkBoxIsBus);
            stopArea.isShareTaxi = getCheckBoxValue(checkBoxIsShareTaxi);
            stopArea.isTrolleybus = getCheckBoxValue(checkBoxIsTrolleybus);
            stopArea.isBusStation = getCheckBoxValue(checkBoxIsBusStation);
            stopArea.isTram = getCheckBoxValue(checkBoxIsTram);
            stopArea.isSubwayStation = getCheckBoxValue(checkBoxIsSubwayStation);
            stopArea.isTrainStop = getCheckBoxValue(checkBoxIsTrainStop);
            stopArea.isTrainStation = getCheckBoxValue(checkBoxIsTrainStation);
            stopArea.isAssignTransportType = getCheckBoxValue(checkBoxIsAssignTransportType);
            stopArea.isBench = getCheckBoxValue(checkBoxIsBench);
            stopArea.isShelter = getCheckBoxValue(checkBoxIsShelter);
            stopArea.isCovered = getCheckBoxValue(checkBoxIsCover);
            stopArea.isLit = getCheckBoxValue(checkBoxIsLit);
            stopArea.isBin = getCheckBoxValue(checkBoxIsBin);
            stopArea.departuresBoard = departuresBoardTagValues[comboBoxDeparturesBoard.getSelectedIndex()];
            stopArea.surface = surfaceTagValues[comboBoxSurface.getSelectedIndex()];
            stopArea.tactilePaving = tactilePavingTagValues[comboBoxTactilePaving.getSelectedIndex()];
            stopArea.isArea = getCheckBoxValue(checkBoxIsArea);
            stopArea.isSetDefaultValues = getCheckBoxValue(checkBoxIsSetDefaultValues);
            previousIsSetDefaultValues = stopArea.isSetDefaultValues;
        } catch (Exception ex) {
            MessageBox.ok(ex.getMessage());
        }
        return stopArea;
    }

    /**
     * Callback method for buttons event
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        if (SAVE_COMMAND.equals(event.getActionCommand())) {
            setVisible(false);
            if (customizeStopAction != null) {
                StopArea stopArea = saveValues();
                customizeStopAction.performCustomizing(stopArea);
            }
        } else {
            setVisible(false);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
            setVisible(false);
        }
   }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}