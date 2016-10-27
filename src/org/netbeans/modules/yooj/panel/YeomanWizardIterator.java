package org.netbeans.modules.yooj.panel;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import javax.swing.JComponent;
import javax.swing.event.ChangeListener;
import org.netbeans.api.extexecution.ExecutionDescriptor;
import org.netbeans.api.extexecution.ExecutionService;
import org.netbeans.api.extexecution.ExternalProcessBuilder;
import org.netbeans.api.extexecution.input.LineProcessor;
import org.netbeans.api.extexecution.print.ConvertedLine;
import org.netbeans.api.extexecution.print.LineConvertor;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ui.OpenProjects;
import org.netbeans.modules.yooj.options.YeomanOptionsPanelController;
import org.openide.WizardDescriptor;
import org.openide.awt.StatusDisplayer;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle;
import org.openide.util.NbPreferences;

public class YeomanWizardIterator implements WizardDescriptor.InstantiatingIterator {

    private int index;
    private WizardDescriptor.Panel[] panels;
    private WizardDescriptor wiz;

    public static YeomanWizardIterator createIterator() {
        return new YeomanWizardIterator();
    }

    private WizardDescriptor.Panel[] createPanels() {
        return new WizardDescriptor.Panel[]{
            new YeomanNameLocationWizardPanel()
        };
    }

    private String[] createSteps() {
        return new String[]{
            NbBundle.getMessage(YeomanWizardIterator.class, "LBL_CreateProjectStep")
        };
    }

    @Override
    public Set instantiate() throws IOException {
//        final String type = Templates.getTemplate(wiz).getAttribute("type").toString();
        final String type = (String) wiz.getProperty("type");
        String message = "Creating "+ type +" Oracle JET application...";
        ProgressHandle ph = ProgressHandleFactory.createSystemHandle(message);
        ph.start();
        createYoApp(ph, type);
        return Collections.emptySet();
    }

    private Process process;

    private void createYoApp(final ProgressHandle handle, final String type) throws IOException {
        final File dirF = FileUtil.normalizeFile((File) wiz.getProperty("projdir"));
        final String projectName = (String) wiz.getProperty("name");
        dirF.mkdirs();
        handle.start(100);
        try {
            final DialogLineProcessor dialogProcessor = new DialogLineProcessor();
            Callable<Process> callable = new Callable<Process>() {
                @Override
                public Process call() throws Exception {
                    String yo = NbPreferences.forModule(YeomanOptionsPanelController.class).get("yoExecutableLocation", "");
                    //Format: oraclejet:hybrid app --appName=app --template=navBar --platforms=android
                    if (type.equals("blank")) {
                        process
                                = new ExternalProcessBuilder(yo).
                                addArgument("oraclejet:hybrid").
                                addArgument(projectName).
                                addArgument("--appName=" + projectName).
                                addArgument("--template=blank").
                                addArgument("--platforms="+wiz.getProperty("platform")).
                                workingDirectory(new File(dirF.getParent())).call();
                    } else if (type.equals("navbar")) {
                        process
                                = new ExternalProcessBuilder(yo).
                                addArgument("oraclejet:hybrid").
                                addArgument(projectName).
                                addArgument("--appName=" + projectName).
                                addArgument("--template=navbar").
                                addArgument("--platforms="+wiz.getProperty("platform")).
                                workingDirectory(new File(dirF.getParent())).call();
                    } else if (type.equals("navdrawer")) {
                        process
                                = new ExternalProcessBuilder(yo).
                                addArgument("oraclejet:hybrid").
                                addArgument(projectName).
                                addArgument("--appName=" + projectName).
                                addArgument("--template=navdrawer").
                                addArgument("--platforms="+wiz.getProperty("platform")).
                                workingDirectory(new File(dirF.getParent())).call();
                    }
                    dialogProcessor.setWriter(new OutputStreamWriter(process.getOutputStream()));
                    return process;
                }
            };
            ExecutionDescriptor descriptor = new ExecutionDescriptor()
                    .frontWindow(true)
                    .inputVisible(true)
                    .postExecution(new Runnable() {
                        @Override
                        public void run() {
                            StatusDisplayer.getDefault().setStatusText("Created: " + dirF.getPath());
                        }
                    })
                    .outConvertorFactory(new ExecutionDescriptor.LineConvertorFactory() {
                        @Override
                        public LineConvertor newLineConvertor() {
                            return new Numbered();
                        }
                    });
            ExecutionService service = ExecutionService.newService(callable, descriptor, "Yeoman");
            Future<Integer> future = service.run();
            try {
                future.get();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            } catch (ExecutionException ex) {
                Exceptions.printStackTrace(ex.getCause());
            }
        } finally {
            handle.progress(100);
            handle.finish();
        }
        FileObject dir = FileUtil.toFileObject(dirF);
        Project p = FileOwnerQuery.getOwner(dir);
        OpenProjects.getDefault().open(new Project[]{p}, true, true);
    }

    private class Numbered implements LineConvertor {
        private int number;
        @Override
        public List<ConvertedLine> convert(String line) {
            List<ConvertedLine> result = Collections.singletonList(ConvertedLine.forText(number + ": " + line, null));
            number++;
            return result;
        }
    }

    private static class DialogLineProcessor implements LineProcessor {

        private Writer writer;

        @Override
        public void processLine(String line) {
            Writer answerWriter;
            synchronized (this) {
                answerWriter = writer;
            }
            if (answerWriter != null) {
                try {
                    answerWriter.write("y\n"); // NOI18N
                    answerWriter.flush();
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        }

        public void setWriter(Writer writer) {
            synchronized (this) {
                this.writer = writer;
            }
        }

        @Override
        public void close() {
            // noop
        }

        @Override
        public void reset() {
            // noop
        }
    }

    @Override
    public void initialize(WizardDescriptor wiz) {
        this.wiz = wiz;
        index = 0;
        panels = createPanels();
        // Make sure list of steps is accurate.
        String[] steps = createSteps();
        for (int i = 0; i < panels.length; i++) {
            Component c = panels[i].getComponent();
            if (steps[i] == null) {
                // Default step name to component name of panel.
                // Mainly useful for getting the name of the target
                // chooser to appear in the list of steps.
                steps[i] = c.getName();
            }
            if (c instanceof JComponent) { // assume Swing components
                JComponent jc = (JComponent) c;
                // Step #.
                jc.putClientProperty("WizardPanel_contentSelectedIndex", new Integer(i));
                // Step name (actually the whole list for reference).
                jc.putClientProperty(WizardDescriptor.PROP_IMAGE, ImageUtilities.loadImage("org/netbeans/modules/yo/resources/yeoman-large.png", true));
                jc.putClientProperty("WizardPanel_contentData", steps);
            }
        }
    }

    public void uninitialize(WizardDescriptor wiz) {
        this.wiz.putProperty("unscrambledtextfield1", null);
        this.wiz.putProperty("projdir", null);
        this.wiz.putProperty("name", null);
        this.wiz = null;
        panels = null;
    }

    public String name() {
        return MessageFormat.format("{0} of {1}",
                new Object[]{new Integer(index + 1), new Integer(panels.length)});
    }

    public boolean hasNext() {
        return index < panels.length - 1;
    }

    public boolean hasPrevious() {
        return index > 0;
    }

    public void nextPanel() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        index++;
    }

    public void previousPanel() {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }
        index--;
    }

    public WizardDescriptor.Panel current() {
        return panels[index];
    }

    // If nothing unusual changes in the middle of the wizard, simply:
    public final void addChangeListener(ChangeListener l) {
    }

    public final void removeChangeListener(ChangeListener l) {
    }

}
