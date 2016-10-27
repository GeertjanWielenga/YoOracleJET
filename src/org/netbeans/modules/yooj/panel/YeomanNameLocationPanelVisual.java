package org.netbeans.modules.yooj.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import org.netbeans.spi.project.ui.support.ProjectChooser;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.filesystems.FileUtil;

public class YeomanNameLocationPanelVisual extends JPanel implements DocumentListener {

    public static final String PROP_PROJECT_NAME = "projectName";

    private String hybridYeomanCommand;

    private List<String> platformList = new ArrayList<>();

    private String template;

    private YeomanNameLocationWizardPanel panel;
    private String selectedPlatforms;

    public YeomanNameLocationPanelVisual(YeomanNameLocationWizardPanel panel) {
        initComponents();
        this.panel = panel;
        // Register listener on the textFields to make the automatic updates
        projectNameTextField.getDocument().addDocumentListener(this);
        projectLocationTextField.getDocument().addDocumentListener(this);
        androidPlatformChoice.addActionListener(new SwitchPlatformActionListener("android"));
        iosPlatformChoice.addActionListener(new SwitchPlatformActionListener("ios"));
        windowsPlatformChoice.addActionListener(new SwitchPlatformActionListener("windows"));
        blankTemplateChoice.setActionCommand("blank");
        basicTemplateChoice.setActionCommand("basic");
        navbarTemplateChoice.setActionCommand("navbar");
        navdrawerTemplateChoice.setActionCommand("navdrawer");
        blankTemplateChoice.addActionListener(new SwitchTemplateActionListener());
        basicTemplateChoice.addActionListener(new SwitchTemplateActionListener());
        navbarTemplateChoice.addActionListener(new SwitchTemplateActionListener());
        navdrawerTemplateChoice.addActionListener(new SwitchTemplateActionListener());
        platformList.add("android");
        setHybridYeomanCommand(
                "HybridOracleJETApp",
                templateButtonGroup.getSelection().getActionCommand(),
                platformList);
    }

    public String getHybridYeomanCommand() {
        return hybridYeomanCommand;
    }

    public void setHybridYeomanCommand(String appName, String templateName, List<String> platforms) {
        //Format: oraclejet:hybrid app --appName=app --template=navBar --platforms=android
        StringJoiner joiner = new StringJoiner(",");
        for (String platform : platforms) {
            if (!joiner.toString().contains(platform)) {
                joiner.add(platform);
            }
        }
        setSelectedPlatforms(joiner.toString());
        hybridYeomanCommand = "yo oraclejet:hybrid --appName=" + appName + " --template=" + templateName + " --platforms=" + joiner.toString();
        commandPreviewLabel.setText(hybridYeomanCommand);
    }

    private class SwitchTemplateActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            template = templateButtonGroup.getSelection().getActionCommand();
            if (template.equals("blank")) {
                descriptionLabel.setText("No starter content, only the Oracle JET distribution.");
            } else if (template.equals("basic")) {
                descriptionLabel.setText("A basic starter without any navigation.");
            } else if (template.equals("navbar")) {
                descriptionLabel.setText("A starter with a navigation bar.");
            } else if (template.equals("navdrawer")) {
                descriptionLabel.setText("A starter with a navigation drawer.");
            }
            setHybridYeomanCommand(
                    getProjectName(),
                    template,
                    platformList);
        }
    }

    private class SwitchPlatformActionListener implements ActionListener {
        private final String platform;
        private SwitchPlatformActionListener(String platform) {
            this.platform = platform;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!platformList.contains(platform)) {
                platformList.add(platform);
            }
            if (!androidPlatformChoice.isSelected()&&platformList.contains("android")){
                platformList.remove("android");
            }
            if (!iosPlatformChoice.isSelected()&&platformList.contains("ios")){
                platformList.remove("ios");
            }
            if (!windowsPlatformChoice.isSelected()&&platformList.contains("windows")){
                platformList.remove("windows");
            }
            setHybridYeomanCommand(
                    getProjectName(),
                    getSelectedTemplate(),
                    platformList);
            panel.fireChangeEvent(); // Notify that the panel changed
        }
    }

    public String getProjectName() {
        return this.projectNameTextField.getText();
    }

    public void setSelectedPlatforms(String selectedPlatforms) {
        this.selectedPlatforms = selectedPlatforms;
    }

    public String getSelectedPlatforms() {
        return selectedPlatforms;
    }

    public String getSelectedTemplate() {
        return templateButtonGroup.getSelection().getActionCommand();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        templateButtonGroup = new javax.swing.ButtonGroup();
        projectNameLabel = new javax.swing.JLabel();
        projectNameTextField = new javax.swing.JTextField();
        projectLocationLabel = new javax.swing.JLabel();
        projectLocationTextField = new javax.swing.JTextField();
        browseButton = new javax.swing.JButton();
        createdFolderLabel = new javax.swing.JLabel();
        createdFolderTextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        commandPreviewLabel = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        navbarTemplateChoice = new javax.swing.JRadioButton();
        descriptionLabel = new javax.swing.JLabel();
        basicTemplateChoice = new javax.swing.JRadioButton();
        blankTemplateChoice = new javax.swing.JRadioButton();
        navdrawerTemplateChoice = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        androidPlatformChoice = new javax.swing.JCheckBox();
        iosPlatformChoice = new javax.swing.JCheckBox();
        windowsPlatformChoice = new javax.swing.JCheckBox();

        projectNameLabel.setLabelFor(projectNameTextField);
        org.openide.awt.Mnemonics.setLocalizedText(projectNameLabel, org.openide.util.NbBundle.getMessage(YeomanNameLocationPanelVisual.class, "YeomanNameLocationPanelVisual.projectNameLabel.text")); // NOI18N

        projectLocationLabel.setLabelFor(projectLocationTextField);
        org.openide.awt.Mnemonics.setLocalizedText(projectLocationLabel, org.openide.util.NbBundle.getMessage(YeomanNameLocationPanelVisual.class, "YeomanNameLocationPanelVisual.projectLocationLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(browseButton, org.openide.util.NbBundle.getMessage(YeomanNameLocationPanelVisual.class, "YeomanNameLocationPanelVisual.browseButton.text")); // NOI18N
        browseButton.setActionCommand(org.openide.util.NbBundle.getMessage(YeomanNameLocationPanelVisual.class, "YeomanNameLocationPanelVisual.browseButton.actionCommand")); // NOI18N
        browseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseButtonActionPerformed(evt);
            }
        });

        createdFolderLabel.setLabelFor(createdFolderTextField);
        org.openide.awt.Mnemonics.setLocalizedText(createdFolderLabel, org.openide.util.NbBundle.getMessage(YeomanNameLocationPanelVisual.class, "YeomanNameLocationPanelVisual.createdFolderLabel.text")); // NOI18N

        createdFolderTextField.setEditable(false);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(YeomanNameLocationPanelVisual.class, "YeomanNameLocationPanelVisual.jLabel4.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(commandPreviewLabel, org.openide.util.NbBundle.getMessage(YeomanNameLocationPanelVisual.class, "YeomanNameLocationPanelVisual.commandPreviewLabel.text")); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(YeomanNameLocationPanelVisual.class, "YeomanNameLocationPanelVisual.jPanel1.border.title"))); // NOI18N

        templateButtonGroup.add(navbarTemplateChoice);
        org.openide.awt.Mnemonics.setLocalizedText(navbarTemplateChoice, org.openide.util.NbBundle.getMessage(YeomanNameLocationPanelVisual.class, "YeomanNameLocationPanelVisual.navbarTemplateChoice.text")); // NOI18N

        descriptionLabel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(descriptionLabel, org.openide.util.NbBundle.getMessage(YeomanNameLocationPanelVisual.class, "YeomanNameLocationPanelVisual.descriptionLabel.text")); // NOI18N

        templateButtonGroup.add(basicTemplateChoice);
        org.openide.awt.Mnemonics.setLocalizedText(basicTemplateChoice, org.openide.util.NbBundle.getMessage(YeomanNameLocationPanelVisual.class, "YeomanNameLocationPanelVisual.basicTemplateChoice.text")); // NOI18N

        templateButtonGroup.add(blankTemplateChoice);
        blankTemplateChoice.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(blankTemplateChoice, org.openide.util.NbBundle.getMessage(YeomanNameLocationPanelVisual.class, "YeomanNameLocationPanelVisual.blankTemplateChoice.text")); // NOI18N

        templateButtonGroup.add(navdrawerTemplateChoice);
        org.openide.awt.Mnemonics.setLocalizedText(navdrawerTemplateChoice, org.openide.util.NbBundle.getMessage(YeomanNameLocationPanelVisual.class, "YeomanNameLocationPanelVisual.navdrawerTemplateChoice.text")); // NOI18N

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(YeomanNameLocationPanelVisual.class, "YeomanNameLocationPanelVisual.jLabel2.text")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(blankTemplateChoice)
                        .addGap(24, 24, 24)
                        .addComponent(basicTemplateChoice)
                        .addGap(18, 18, 18)
                        .addComponent(navbarTemplateChoice)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(navdrawerTemplateChoice)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(descriptionLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(blankTemplateChoice)
                    .addComponent(basicTemplateChoice)
                    .addComponent(navbarTemplateChoice)
                    .addComponent(navdrawerTemplateChoice))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(descriptionLabel)
                    .addComponent(jLabel2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(YeomanNameLocationPanelVisual.class, "YeomanNameLocationPanelVisual.jPanel2.border.title"))); // NOI18N

        androidPlatformChoice.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(androidPlatformChoice, org.openide.util.NbBundle.getMessage(YeomanNameLocationPanelVisual.class, "YeomanNameLocationPanelVisual.androidPlatformChoice.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(iosPlatformChoice, org.openide.util.NbBundle.getMessage(YeomanNameLocationPanelVisual.class, "YeomanNameLocationPanelVisual.iosPlatformChoice.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(windowsPlatformChoice, org.openide.util.NbBundle.getMessage(YeomanNameLocationPanelVisual.class, "YeomanNameLocationPanelVisual.windowsPlatformChoice.text")); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(androidPlatformChoice)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(iosPlatformChoice)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(windowsPlatformChoice)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(androidPlatformChoice)
                    .addComponent(iosPlatformChoice)
                    .addComponent(windowsPlatformChoice))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(projectNameLabel)
                            .addComponent(projectLocationLabel)
                            .addComponent(createdFolderLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(projectNameTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                            .addComponent(projectLocationTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                            .addComponent(createdFolderTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(browseButton))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(commandPreviewLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(projectNameLabel)
                    .addComponent(projectNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(projectLocationLabel)
                    .addComponent(projectLocationTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(browseButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(createdFolderLabel)
                    .addComponent(createdFolderTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(commandPreviewLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void browseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseButtonActionPerformed
        String command = evt.getActionCommand();
        if ("BROWSE".equals(command)) {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(null);
            chooser.setDialogTitle("Select Project Location");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            String path = this.projectLocationTextField.getText();
            if (path.length() > 0) {
                File f = new File(path);
                if (f.exists()) {
                    chooser.setSelectedFile(f);
                }
            }
            if (JFileChooser.APPROVE_OPTION == chooser.showOpenDialog(this)) {
                File projectDir = chooser.getSelectedFile();
                projectLocationTextField.setText(FileUtil.normalizeFile(projectDir).getAbsolutePath());
            }
            panel.fireChangeEvent();
        }

    }//GEN-LAST:event_browseButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox androidPlatformChoice;
    private javax.swing.JRadioButton basicTemplateChoice;
    private javax.swing.JRadioButton blankTemplateChoice;
    private javax.swing.JButton browseButton;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel commandPreviewLabel;
    private javax.swing.JLabel createdFolderLabel;
    private javax.swing.JTextField createdFolderTextField;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JCheckBox iosPlatformChoice;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton navbarTemplateChoice;
    private javax.swing.JRadioButton navdrawerTemplateChoice;
    private javax.swing.JLabel projectLocationLabel;
    private javax.swing.JTextField projectLocationTextField;
    private javax.swing.JLabel projectNameLabel;
    private javax.swing.JTextField projectNameTextField;
    private javax.swing.ButtonGroup templateButtonGroup;
    private javax.swing.JCheckBox windowsPlatformChoice;
    // End of variables declaration//GEN-END:variables

    @Override
    public void addNotify() {
        super.addNotify();
        //same problem as in 31086, initial focus on Cancel button
        projectNameTextField.requestFocus();
    }

    boolean valid(WizardDescriptor wizardDescriptor) {

        if (!androidPlatformChoice.isSelected()&&!iosPlatformChoice.isSelected()&&!windowsPlatformChoice.isSelected()) {
            wizardDescriptor.putProperty("WizardPanel_errorMessage",
                    "Must select at least one platform.");
            return false;
        }

        if (projectNameTextField.getText().length() == 0) {
            // TODO if using org.openide.dialogs >= 7.8, can use WizardDescriptor.PROP_ERROR_MESSAGE:
            wizardDescriptor.putProperty("WizardPanel_errorMessage",
                    "Project Name is not a valid folder name.");
            return false; // Display name not specified
        }
        File f = FileUtil.normalizeFile(new File(projectLocationTextField.getText()).getAbsoluteFile());
        if (!f.isDirectory()) {
            String message = "Project Folder is not a valid path.";
            wizardDescriptor.putProperty("WizardPanel_errorMessage", message);
            return false;
        }
        final File destFolder = FileUtil.normalizeFile(new File(createdFolderTextField.getText()).getAbsoluteFile());

        File projLoc = destFolder;
        while (projLoc != null && !projLoc.exists()) {
            projLoc = projLoc.getParentFile();
        }
        if (projLoc == null || !projLoc.canWrite()) {
            wizardDescriptor.putProperty("WizardPanel_errorMessage",
                    "Project Folder cannot be created.");
            return false;
        }

        if (FileUtil.toFileObject(projLoc) == null) {
            String message = "Project Folder is not a valid path.";
            wizardDescriptor.putProperty("WizardPanel_errorMessage", message);
            return false;
        }

        File[] kids = destFolder.listFiles();
        if (destFolder.exists() && kids != null && kids.length > 0) {
            // Folder exists and is not empty
            wizardDescriptor.putProperty("WizardPanel_errorMessage",
                    "Project Folder already exists and is not empty.");
            return false;
        }
        wizardDescriptor.putProperty("WizardPanel_errorMessage", "");
        return true;
    }

    void store(WizardDescriptor d) {
        String name = projectNameTextField.getText().trim();
        String folder = createdFolderTextField.getText().trim();

        d.putProperty("projdir", new File(folder));
        d.putProperty("name", name);
        d.putProperty("template", getSelectedTemplate());
        d.putProperty("platforms", getSelectedPlatforms());
        d.putProperty("hybridYeomanCommand", hybridYeomanCommand);
    }

    void read(WizardDescriptor settings) {
        File projectLocation = (File) settings.getProperty("projdir");
        if (projectLocation == null || projectLocation.getParentFile() == null || !projectLocation.getParentFile().isDirectory()) {
            projectLocation = ProjectChooser.getProjectsFolder();
        } else {
            projectLocation = projectLocation.getParentFile();
        }
        this.projectLocationTextField.setText(projectLocation.getAbsolutePath());

        String projectName = (String) settings.getProperty("name");
        if (projectName == null) {
//            projectName = settings.getProperty("selectedGenerator").toString().replace(":", "-");
//            final String type = Templates.getTemplate(settings).getAttribute("type").toString();
            final String type = getSelectedTemplate();
            settings.putProperty("type", type);
            settings.putProperty("platforms", getSelectedPlatforms());
            projectName = "HybridOracleJETApp";
        }
        this.projectNameTextField.setText(projectName);
        this.projectNameTextField.selectAll();
    }

    void validate(WizardDescriptor d) throws WizardValidationException {
        // nothing to validate
    }

    // Implementation of DocumentListener --------------------------------------
    public void changedUpdate(DocumentEvent e) {
        updateTexts(e);
        if (this.projectNameTextField.getDocument() == e.getDocument()) {
            firePropertyChange(PROP_PROJECT_NAME, null, this.projectNameTextField.getText());
        }
    }

    public void insertUpdate(DocumentEvent e) {
        updateTexts(e);
        if (this.projectNameTextField.getDocument() == e.getDocument()) {
            firePropertyChange(PROP_PROJECT_NAME, null, this.projectNameTextField.getText());
        }
    }

    public void removeUpdate(DocumentEvent e) {
        updateTexts(e);
        if (this.projectNameTextField.getDocument() == e.getDocument()) {
            firePropertyChange(PROP_PROJECT_NAME, null, this.projectNameTextField.getText());
        }
    }

    /**
     * Handles changes in the Project name and project directory,
     */
    private void updateTexts(DocumentEvent e) {

        Document doc = e.getDocument();

        if (doc == projectNameTextField.getDocument() || doc == projectLocationTextField.getDocument()) {
            // Change in the project name

            String projectName = projectNameTextField.getText();
            String projectFolder = projectLocationTextField.getText();

            setHybridYeomanCommand(
                    projectName,
                    template,
                    platformList);

            //if (projectFolder.trim().length() == 0 || projectFolder.equals(oldName)) {
            createdFolderTextField.setText(projectFolder + File.separatorChar + projectName);
            //}

        }
        panel.fireChangeEvent(); // Notify that the panel changed
    }

}
