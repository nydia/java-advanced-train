package net.beeapm.agent.reporter;

import net.beeapm.agent.loader.AbstractLoader;
import java.util.*;
/**
 *
 * @author agent
 * @date 2022/10/20
 */
public class ReporterLoader extends AbstractLoader {
    public static Map<String, AbstractReporter> loadReporters() {
        return load(new String[]{"reporter"},"bee-reporter.def");
    }
}
