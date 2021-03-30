package com.company.spacetrans.report;

import io.jmix.core.security.Authenticated;
import io.jmix.reports.ReportImportExport;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Objects;

/**
 * Automatically imports reports from Reports.zip to the database on every server start.
 */
@Component
public class ReportImportListener {

    private static final Logger LOG = LoggerFactory.getLogger(ReportImportListener.class);

    @Autowired
    private ReportImportExport reportImportExportAPI;

    @Autowired
    private ResourceLoader resourceLoader;

    @Authenticated
    @EventListener
    public void applicationContextStarted(ApplicationReadyEvent event) {
        try {
            byte[] reportsZip;
            try (InputStream is = resourceLoader.getResource("classpath:reports/Report for entity _Waybill_.zip").getInputStream()) {
                Objects.requireNonNull(is);
                reportsZip = IOUtils.toByteArray(is);
            }
            reportImportExportAPI.importReports(reportsZip);
        } catch (Exception e) {
            LOG.error("Failed to import reports on server start", e);
        }
    }
}