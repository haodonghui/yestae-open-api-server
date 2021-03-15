/**
 * 
 *
 * https://www.yestae.com/home
 *
 * copyright yyh
 */

package com.yestae.common.utils;


/**
 * Redis所有Keys
 *
 * @author daniel
 */
public class RedisKeys {

    public static String getSysConfigKey(String key){
        return "sys:config:" + key;
    }
    
    public static String AUTH_TOKEN_PREFIX = "openapi:auth:";


    /**
     * key前缀
     */
    public static String OPENAPI_PERFIX = "openapi:";

    /**
     * 接口信息key
     */
    public static String INTERFACE_HASH = OPENAPI_PERFIX + "interface_hash";

    /**
     * 商户信息key
     */
    public static  String MCH_HASH =  OPENAPI_PERFIX + "mch_hash";
    /**
     * 商户信息key
     */
    public static String MCH_APP_HASH =  OPENAPI_PERFIX + "mch_app_hash";

    /**
     * 商户应用开通接口信息key
     */
    public static String MCH_APP_INTERFACE_HASH =  OPENAPI_PERFIX + "mch_app_interface_hash";

    /**
     * ip白名单key
     */
    public static String IP_WHITE_SET = OPENAPI_PERFIX + "ip_white_set";

    /**
     * 商户应用映射key
     */
    public static String MCH_APP_MAPPING_HASH =  OPENAPI_PERFIX + "mch_app_mapping_hash";


}
