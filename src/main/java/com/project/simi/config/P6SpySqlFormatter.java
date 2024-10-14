package com.project.simi.config;

import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

import jakarta.annotation.PostConstruct;

import org.hibernate.engine.jdbc.internal.FormatStyle;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ClassUtils;

import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.P6SpyOptions;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

@Configuration
public class P6SpySqlFormatter implements MessageFormattingStrategy {
    @PostConstruct
    public void setLogMessageFormat() {
        P6SpyOptions.getActiveInstance().setLogMessageFormat(this.getClass().getName());
    }

    @Override
    public String formatMessage(
            int connectionId,
            String now,
            long elapsed,
            String category,
            String prepared,
            String sql,
            String url) {
        return String.format(
                "[%s] | %d ms | %s",
                category, elapsed, StackTrace() + highlight(formatSql(category, sql)));
    }

    private String highlight(String sql) {
        return FormatStyle.HIGHLIGHT.getFormatter().format(sql);
    }

    private String StackTrace() {
        return Arrays.stream(new Throwable().getStackTrace())
                .filter(
                        trace ->
                                trace.toString().startsWith("com.project.simi")
                                        && !trace.toString()
                                                .contains(ClassUtils.getUserClass(this).getName()))
                .map(StackTraceElement::toString)
                .collect(Collectors.joining(" <- "));
    }

    private String formatSql(String category, String sql) {
        if (isBlank(sql) && isNotStatement(category)) return "";

        String formattedSql = sql.trim().toLowerCase(Locale.ROOT);
        if (isDDL(formattedSql)) {
            formattedSql = FormatStyle.DDL.getFormatter().format(formattedSql);
        } else {
            formattedSql = FormatStyle.BASIC.getFormatter().format(formattedSql);
        }

        return formattedSql;
    }

    private static boolean isDDL(String formattedSql) {
        return formattedSql.startsWith("create")
                || formattedSql.startsWith("alter")
                || formattedSql.startsWith("comment");
    }

    private static boolean isNotStatement(String category) {
        return !Category.STATEMENT.getName().equals(category);
    }

    private static boolean isBlank(String formattedSql) {
        return formattedSql == null || formattedSql.isBlank();
    }
}
