package com.mengxiu.lottery.core.plugin.config;

import lombok.*;

/**
 * @Description plugin config
 * @Date 2022/10/14 15:02
 * @Created by <a href="mailto:nydia_lhq@hotmail.com">lvhuaqiang</a>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PluginConfig {

    private MatchCache matchCache = new MatchCache();

    /**
     * the match cache.
     */
    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class MatchCache {

        private boolean enabled;

        /**
         * Max free memory, unit mb.
         */
        private Integer maxFreeMemory = 256;
    }

}
