package com.project.simi.domain.oauth.cache;

import java.net.URI;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.nimbusds.jose.jwk.JWKSet;

public class JWKSetCache {
    private static final ConcurrentHashMap<String, JWKSet> jwkCache = new ConcurrentHashMap<>();
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(JWKSetCache::shutdown));
    }

    public static JWKSet getCachedJWKSet(String jwkUrl) throws Exception {
        return jwkCache.computeIfAbsent(
                jwkUrl,
                url -> {
                    try {
                        return JWKSet.load(URI.create(url).toURL());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    public static void startJWKSetCacheRefresher(String jwkUrl, long refreshIntervalSeconds) {
        scheduler.scheduleAtFixedRate(
                () -> {
                    try {
                        JWKSet newJwkSet = JWKSet.load(URI.create(jwkUrl).toURL());
                        jwkCache.put(jwkUrl, newJwkSet);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                refreshIntervalSeconds,
                refreshIntervalSeconds,
                TimeUnit.SECONDS);
    }

    public static void shutdown() {
        scheduler.shutdown();
    }
}
