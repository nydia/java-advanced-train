package net.beeapm.server.stream;

import net.beeapm.agent.annotation.BeePlugin;
import net.beeapm.agent.annotation.BeePluginType;
import net.beeapm.server.core.stream.AbstractStreamProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author agent
 * @date 2022/08/27
 */
@BeePlugin(type = BeePluginType.STREAM, name = "servlet")
public class ServletStreamProvider extends AbstractStreamProvider {
    private static final Logger logger = LoggerFactory.getLogger(ServletStreamProvider.class);

    @Override
    public void start() {
        logger.info("ServletStreamProvider start ............................................");
    }
}
