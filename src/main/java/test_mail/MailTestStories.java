package test_mail;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

import de.codecentric.jbehave.junit.monitoring.JUnitReportingRunner;
import org.jbehave.core.Embeddable;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.i18n.LocalizedKeywords;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.model.ExamplesTableFactory;
import org.jbehave.core.model.TableTransformers;
import org.jbehave.core.parsers.RegexPrefixCapturingPatternParser;
import org.jbehave.core.parsers.RegexStoryParser;
import org.jbehave.core.reporters.CrossReference;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.core.steps.ParameterControls;
import org.jbehave.core.steps.ParameterConverters;
import org.jbehave.core.steps.ParameterConverters.ExamplesTableConverter;
import org.junit.runner.RunWith;
import test_mail.steps.MailTestSteps;

import static java.util.Arrays.asList;
import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;
import static org.jbehave.core.reporters.Format.HTML_TEMPLATE;
import static org.jbehave.core.reporters.Format.XML_TEMPLATE;
import static org.jbehave.core.reporters.StoryReporterBuilder.Format.*;

/**
 * <p>
 * {@link Embeddable} class to run multiple textual stories via JUnit.
 * </p>
 * <p>
 * Stories are specified in classpath and correspondingly the {@link LoadFromClasspath}
 * story loader is configured.
 * </p> 
 */
@RunWith(JUnitReportingRunner.class)
public class MailTestStories extends JUnitStories {

    private final CrossReference xref = new CrossReference();

    public MailTestStories() {
        configuredEmbedder().embedderControls().doGenerateViewAfterStories(true)
                .doIgnoreFailureInStories(false).doIgnoreFailureInView(true)
                .doVerboseFailures(true).useThreads(1).useStoryTimeoutInSecs(90);
    }

    @Override
    public Configuration configuration() {
        Class<? extends Embeddable> embeddableClass = this.getClass();
        Properties viewResources = new Properties();
        viewResources.put("decorateNonHtml", "true");
        viewResources.put("reports", "ftl/jbehave-reports-with-totals.ftl");
        ParameterConverters parameterConverters = new ParameterConverters();
        ExamplesTableFactory examplesTableFactory =
                new ExamplesTableFactory(
                        new LocalizedKeywords(),
                        new LoadFromClasspath(embeddableClass),
                        parameterConverters,
                        new ParameterControls(),
                        new TableTransformers()
                );
        parameterConverters.addConverters(new ParameterConverters.DateConverter(
                new SimpleDateFormat("yyyy-MM-dd")),
                new ExamplesTableConverter(examplesTableFactory));
        return new MostUsefulConfiguration()
                .useStoryLoader(new LoadFromClasspath(embeddableClass))
                .useStoryParser(new RegexStoryParser(examplesTableFactory))
                .useStoryReporterBuilder(new StoryReporterBuilder()
                        .withCodeLocation(CodeLocations.codeLocationFromClass(embeddableClass))
                        .withDefaultFormats()
                        .withViewResources(viewResources)
                        .withFormats(Format.CONSOLE, Format.TXT, Format.HTML)
                        .withFailureTrace(true)
                        .withFailureTraceCompression(true)
                        .withCrossReference(xref)
                )
                .useParameterConverters(parameterConverters)
                .useStepPatternParser(new RegexPrefixCapturingPatternParser("%"))
                .useStepMonitor(xref.getStepMonitor())
                .useStoryReporterBuilder(new StoryReporterBuilder()
                        .withReporters(new AllureReporter())
                );
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), new MailTestSteps());
    }

    @Override
    protected List<String> storyPaths() {
        URL codeLocation = codeLocationFromClass(this.getClass());
        return new StoryFinder().findPaths(codeLocation,
                "**/*.story", "");
    }
}