package com.agency04.heist.event;

import com.agency04.heist.model.Heist;
import com.agency04.heist.enums.HeistStatus;
import com.agency04.heist.service.HeistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class OnNewHeistListener {
    private static final Logger LOG = LoggerFactory.getLogger(OnNewHeistListener.class);

    @Autowired
    private HeistService heistService;

    @Autowired
    private ThreadPoolTaskScheduler scheduler;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @EventListener
    public void handleNewHeistEvent(NewHeistEvent newHeistEvent) {
        final Long heistId = newHeistEvent.getHeistId();
        final Date startTime = Date.from(newHeistEvent.getStartTime());
        final Date endTime = Date.from(newHeistEvent.getEndTime());

        LOG.debug("handleNewHeistEvent: heistId={}, startTime={}, endTime={}", heistId, startTime, endTime);

        scheduler.schedule(() -> {
            Heist foundHeist = heistService.findById(heistId).orElse(null);
            LOG.debug("START heist found {}", foundHeist);
            if (foundHeist != null && foundHeist.getStatus() == HeistStatus.READY) {
                LOG.debug("Going to start {}", foundHeist.getName());
                heistService.start(foundHeist);
                LOG.info("Heist {} has started!", foundHeist.getName());
            }
        }, startTime);

        scheduler.schedule(() -> {
            Heist foundHeist = heistService.findById(heistId).orElse(null);
            LOG.debug("END heist found {}", foundHeist);
            if (foundHeist != null && foundHeist.getStatus() == HeistStatus.IN_PROGGRESS) {
                LOG.debug("Going to finish {}", foundHeist);
                heistService.finish(foundHeist);
                LOG.info("Heist {} has finished!", foundHeist.getName());
            }
        }, endTime);
    }
}
