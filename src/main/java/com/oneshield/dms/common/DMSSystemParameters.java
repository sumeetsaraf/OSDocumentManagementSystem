package com.oneshield.dms.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "user")
public class DMSSystemParameters
{
// post introduction of spring boot version >2.1 we merge maxDocumentContentSize and maximumDocumentContentSizeInMB
// to one field using DataSizeUnit class
    private boolean optimisticMode;
    private int maximumDocumentContentSizeInMB;

    public DMSSystemParameters() {
    }

    public boolean isOptimisticMode()
    {
        return optimisticMode;
    }

    public void setOptimisticMode(
                                  boolean optimisticMode)
    {
        this.optimisticMode = optimisticMode;
    }

    public void setMaximumDocumentContentSizeInMB(
                                                  int maximumDocumentContentSizeInMB)
    {
        this.maximumDocumentContentSizeInMB = maximumDocumentContentSizeInMB;
    }

}
