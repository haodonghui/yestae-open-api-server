package com.yestae.common.utils;

import java.nio.ByteBuffer;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;

public abstract class UuidUtils {

    public static String base64Uuid() {
        UUID uuid = UUID.randomUUID();
        return base64Uuid(uuid);
    }

    public static String decodeBase64Uuid(String compressedUuid) {

        byte[] byUuid = Base64.decodeBase64(compressedUuid);

        ByteBuffer bb = ByteBuffer.wrap(byUuid);
        UUID uuid = new UUID(bb.getLong(), bb.getLong());
        return uuid.toString();
    }

    public static String encodeBase64Uuid(String uuidString) {
        UUID uuid = UUID.fromString(uuidString);
        return base64Uuid(uuid);
    }

    public static String getUUID22(String uuidString) {
        UUID uuid = UUID.fromString(uuidString);
        return base64Uuid(uuid);
    }

    public static String uuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public static String uuid32() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "");
    }

    protected static String base64Uuid(UUID uuid) {

        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());

        return Base64.encodeBase64URLSafeString(bb.array());
    }
    
    /**
     * 
     * 获取数字编号型UUID
     *
     * @return
     * @throws
     */
    public static String getNumbernicUUID(){
    	return IdWorker.getIdStr();
    }

}