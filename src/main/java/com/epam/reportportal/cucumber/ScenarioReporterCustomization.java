package com.epam.reportportal.cucumber;

import com.epam.ta.reportportal.ws.model.StartTestItemRQ;
import gherkin.formatter.model.Feature;
import io.reactivex.Maybe;

import java.util.Calendar;

import static com.epam.reportportal.cucumber.Utils.extractTags;

public class ScenarioReporterCustomization extends ScenarioReporter {

    @Override
    protected void beforeFeature(Feature feature) {
        StartTestItemRQ rq = new StartTestItemRQ();
        Maybe<String> root = getRootItemId();

        //set link from .feature file like a description for rq
        rq.setDescription(feature.getDescription());

        //set "Feature: FeatureName" like feature name.
        rq.setName(Utils.buildStatementName(feature, null, AbstractReporter.COLON_INFIX, null));

        rq.setTags(extractTags(feature.getTags()));
        rq.setStartTime(Calendar.getInstance().getTime());
        rq.setType(getFeatureTestItemType());
        if (null == root) {
            currentFeatureId = RP.get().startTestItem(rq);
        } else {
            currentFeatureId = RP.get().startTestItem(root, rq);
        }
    }
}
