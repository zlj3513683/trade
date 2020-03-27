
package com.posppay.newpay.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;


/**
 * format:
 * <p>
 * private static final String KEY_CHESS_STOREPATH = "chessStorePath";
 * <p>
 * public static final String getChessStorePath(){
 * return SystemProperty.getInstance().getSystProperty(KEY_CHESS_STOREPATH);
 * }
 *
 * @author Administrator
 */
@Component
@ConfigurationProperties(prefix = "sysproperty")
@Data
public class XpospSysProperty {

    public static String ESIGNATURE_PATH;
    public static String STAR_POS_TPDU;


    public Map<String, String> esignature = new HashMap<>();
    public Map<String, String> starpos = new HashMap<>();

    @PostConstruct
    public void init() {
        ESIGNATURE_PATH = esignature.get("path");
        STAR_POS_TPDU = starpos.get("tpdu");
    }
}
