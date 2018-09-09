package com.npci.shirotutorial.security.web.listener;

import org.slf4j.Logger;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.inject.Inject;

public class DebugPhaseListener implements PhaseListener {

    private static final long serialVersionUID = 1069270717751614672L;

    @Inject
    Logger logger;

    @Override
    public void afterPhase(PhaseEvent event) {
        logger.info("--afterPhase " + event.getPhaseId().toString());
    }

    @Override
    public void beforePhase(PhaseEvent event) {
        logger.info("--beforePhase " + event.getPhaseId().toString());
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }
}
